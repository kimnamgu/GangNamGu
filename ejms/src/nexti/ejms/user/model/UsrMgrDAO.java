/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 조직관리 dao
 * 설명:
 */
package nexti.ejms.user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.util.Utils;

import org.apache.log4j.Logger;

public class UsrMgrDAO {
	private static Logger logger = Logger.getLogger(UsrMgrDAO.class);

	/***************************************************** 첫번째 iframe ***************************************************************/
	//부서 리스트
	public List deptList(String gubun, String orggbn, String user_id, String dept_depth) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List deptBeanList = null;
		
		String rootid = appInfo.getRootid();
		
		StringBuffer selectQuery = new StringBuffer();
		/*[DEPT_EXT] 테이블 삭제
		selectQuery.append("SELECT DEPT_ID, DEPT_NAME, UPPER_DEPT_ID, GRPGBN, MAIN_FL, LEVEL-1 DEPT_LEVEL, USE_YN, CON_YN	   \n");
		selectQuery.append("FROM                                                                                               \n");
		selectQuery.append("(                                                                                                  \n");
		selectQuery.append("SELECT D.DEPT_ID, D.DEPT_NAME, D.UPPER_DEPT_ID, '2' GRPGBN, DE.MAIN_FL, D.DEPT_RANK, '' GRADE_ID,  \n");
		selectQuery.append(" D.USE_YN, D.CON_YN																				   \n");
		selectQuery.append("FROM DEPT D, DEPT_EXT DE                                                                           \n");
		selectQuery.append("WHERE D.DEPT_ID = DE.DEPT_ID                                                                       \n");
	    if(!"ALL".equalsIgnoreCase(gubun)){
		selectQuery.append("AND D.USE_YN = '1'                                                                                 \n");
	    }
		selectQuery.append(") T                                                                                                \n");
		selectQuery.append("START WITH DEPT_ID = ?                                                                             \n");
		selectQuery.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                           \n");
		selectQuery.append("ORDER SIBLINGS BY TO_NUMBER(DEPT_RANK), GRADE_ID                                                   \n");
		selectQuery.append(" SELECT DEPT_ID, DEPT_NAME, UPPER_DEPT_ID, '2' GRPGBN, MAIN_YN,  LEVEL-1 DEPT_LEVEL, USE_YN, CON_YN	\n");
		selectQuery.append(" FROM DEPT                                                                                          \n");
		selectQuery.append(" START WITH DEPT_ID = ?                                                                             \n");
		selectQuery.append(" CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                           \n");
		if(!"".equalsIgnoreCase(orggbn)){
		selectQuery.append(" AND ORGGBN = '"+orggbn+"'                                                                 			\n");
	    }else{
		selectQuery.append(" AND ORGGBN = '001'                	                                                                \n");
	    }
		selectQuery.append(" ORDER SIBLINGS BY TO_NUMBER(DEPT_RANK)                                                             \n");
		*/

		selectQuery.append(" SELECT DEPT_ID, DEPT_NAME, UPPER_DEPT_ID, '2' GRPGBN, MAIN_YN,  LEVEL-1 DEPT_LEVEL, USE_YN, CON_YN     \n");
		selectQuery.append(" FROM DEPT                                                                                              \n");
		if(orggbn.equals("001")){
		selectQuery.append(" START WITH DEPT_ID = '"+rootid+"'                                                                 		\n");
		}else{
		selectQuery.append(" START WITH DEPT_ID =                                                                                 	\n");
		selectQuery.append(" (                                                                                                    	\n");
		selectQuery.append("  SELECT DEPT_ID FROM DEPT   							                                                \n");
		selectQuery.append(" 	WHERE DEPT_DEPTH = '"+dept_depth+"'                                                                 \n");
		selectQuery.append(" 	START WITH DEPT_ID IN (SELECT DEPT_ID FROM USR WHERE USER_ID = '"+user_id+"')                       \n");
		selectQuery.append(" 	CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID                                   	                        \n");
		selectQuery.append(" )                                                                                                    	\n");
		}
		selectQuery.append(" CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                             	\n");
		if(!"ALL".equalsIgnoreCase(gubun)){
		selectQuery.append(" AND ORGGBN = '"+orggbn+"'                                                                 			    \n");
		}
		selectQuery.append(" ORDER SIBLINGS BY TO_NUMBER(DEPT_RANK)                                                               	\n");
		
		try {
			//System.out.println("selectQuery.toString() : "  +selectQuery.toString());
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();

			deptBeanList = new ArrayList();
			while (rs.next()) {
				UsrMgrBean bean = new UsrMgrBean();
				bean.setDept_id(rs.getString("DEPT_ID"));
				bean.setDept_name(rs.getString("DEPT_NAME"));
				bean.setUpper_dept_id(rs.getString("UPPER_DEPT_ID"));
				bean.setGrp_gbn(rs.getString("GRPGBN"));
				bean.setMain_yn(rs.getString("MAIN_YN"));
				bean.setDept_level(rs.getInt("DEPT_LEVEL"));
				bean.setUse_yn(rs.getString("USE_YN"));;
				bean.setCon_yn(rs.getString("CON_YN"));
				deptBeanList.add(bean);		
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		return deptBeanList;
	}

	//부서확장 테이블 전체UPDATE 
	public int updateAllExt(String user_id, String orggbn, String rep_dept) {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();

			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append(" UPDATE DEPT SET USE_YN='N', CON_YN='N', MAIN_YN='N', UPTUSRID = ?, UPTDT = TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') \n");
			updateQuery.append(" WHERE ORGGBN = '"+orggbn+"' \n");
			updateQuery.append(" AND DEPT_ID IN (SELECT DEPT_ID \n");
			updateQuery.append("                 FROM DEPT \n");
			updateQuery.append("                 START WITH DEPT_ID = '"+rep_dept+"' \n");
			updateQuery.append("                 CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID) \n");
			pstmt =	conn.prepareStatement(updateQuery.toString());
			pstmt.setString(1, user_id);
			result = pstmt.executeUpdate();
			
		 } catch (SQLException e) {
			 logger.error("ERROR",e);			
	     } finally {
			ConnectionManager.close(conn,pstmt);				
	     }
		return result;
	}
	
	//대상여부  테이블 UPDATE 	
	public int updateMainYn(String user_id, String dept_id) {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append(" UPDATE DEPT SET MAIN_YN ='Y', UPTUSRID = ?, UPTDT = TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS') ");
			updateQuery.append(" WHERE DEPT_ID = ? ");
			
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(updateQuery.toString());
			pstmt.setString(1, user_id);
			pstmt.setString(2, dept_id);
			
			result = pstmt.executeUpdate();
			
		 } catch (SQLException e) {
			 logger.error("ERROR",e);			
	     } finally {
			ConnectionManager.close(conn,pstmt);				
	     }
		return result;
	}

	//사용여부  테이블 UPDATE 	
	public int updateUseYn(String dept_id) {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append("UPDATE DEPT SET USE_YN='Y', UPTDT = TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')");
			updateQuery.append("WHERE DEPT_ID = ? ");
			
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(updateQuery.toString());
			pstmt.setString(1, dept_id);
			result = pstmt.executeUpdate();
			
		 } catch (SQLException e) {
			 logger.error("ERROR",e);			
	     } finally {
			ConnectionManager.close(conn,pstmt);				
	     }
		return result;
	}

	//연계여부  테이블 UPDATE 	
	public int updateConYn(String dept_id) {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append(" UPDATE DEPT SET CON_YN='Y', UPTDT = TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ");
			updateQuery.append(" WHERE DEPT_ID = ? 														 ");

			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(updateQuery.toString());
			pstmt.setString(1, dept_id);
			result = pstmt.executeUpdate();
			
		 } catch (SQLException e) {
			 logger.error("ERROR",e);			
	     } finally {
			ConnectionManager.close(conn,pstmt);				
	     }
		return result;
	}

	/***************************************************** 두번째 iframe ***************************************************************/
	//하위 부서 목록 가져오기
	public List childDeptList(String dept_id, String orggbn) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List childDeptList = null;
		
		StringBuffer selectQuery = new StringBuffer();			
		
	    selectQuery.append(" SELECT DEPT_ID, DEPT_NAME, DEPT_RANK, UPPER_DEPT_ID, USE_YN, CON_YN ");
	    selectQuery.append(" FROM DEPT 															 ");
	    selectQuery.append(" WHERE UPPER_DEPT_ID = ? 											 ");
	    selectQuery.append(" AND ORGGBN = '"+orggbn+"'											 ");
	    selectQuery.append(" ORDER BY DEPT_RANK													 ");
	    
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());

			pstmt.setString(1, dept_id);
			rs = pstmt.executeQuery();

			childDeptList = new ArrayList();
			while (rs.next()) {
				UsrMgrBean bean = new UsrMgrBean();
				bean.setDept_id(rs.getString("DEPT_ID"));
				bean.setDept_name(rs.getString("DEPT_NAME"));
				bean.setDept_level(rs.getInt("DEPT_RANK"));
				bean.setUpper_dept_id(rs.getString("UPPER_DEPT_ID"));
				bean.setUse_yn(rs.getString("USE_YN"));
				bean.setCon_yn(rs.getString("CON_YN"));				
				childDeptList.add(bean);		
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		return childDeptList;
	}

	//하위 사용자 목록 가져오기
	public List deptUserList(String dept_id) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List deptUserList = null;
		
		StringBuffer selectQuery = new StringBuffer();			
		
	    selectQuery.append(" SELECT USER_ID, USER_NAME, CLASS_NAME, USE_YN, CON_YN ");
	    selectQuery.append(" FROM USR 											   ");
	    selectQuery.append(" WHERE DEPT_ID = ? 							  		   ");
	    selectQuery.append(" ORDER BY USR_RANK									   ");
	    
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());

			pstmt.setString(1, dept_id);
			rs = pstmt.executeQuery();

			deptUserList = new ArrayList();
			while (rs.next()) {
				UsrMgrBean bean = new UsrMgrBean();
				bean.setUser_id(rs.getString("USER_ID"));	
				bean.setUser_name(rs.getString("USER_NAME"));
				bean.setClass_name(rs.getString("CLASS_NAME"));
				bean.setUse_yn(rs.getString("USE_YN"));
				bean.setCon_yn(rs.getString("CON_YN"));
				deptUserList.add(bean);		
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		return deptUserList;
	}


	//담당자검색
	public List userList(String user_name) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List userList = null;
		
		StringBuffer selectQuery = new StringBuffer();			
		selectQuery.append(" SELECT USER_ID, USER_NAME, A.DEPT_ID, A.DEPT_NAME, B.DEPT_NAME AS UPPER_DEPT_NAME, A.DEPT_FULLNAME \n");
		selectQuery.append(" FROM (                                                                                             \n");
		selectQuery.append(" SELECT USER_ID, USER_NAME, U.DEPT_ID, U.DEPT_NAME, UPPER_DEPT_ID, D.DEPT_FULLNAME, USR_RANK        \n");
		selectQuery.append(" FROM USR U, DEPT D                                                                                 \n");
		selectQuery.append(" WHERE U.DEPT_ID = D.DEPT_ID                                                                        \n");
		selectQuery.append(" AND USER_NAME LIKE ?)A, DEPT B                                                                     \n");
		selectQuery.append(" WHERE A.UPPER_DEPT_ID = B.DEPT_ID                                                                  \n");
		selectQuery.append(" ORDER BY USER_NAME, DEPT_RANK, USR_RANK                                                            \n");
		    
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());

			pstmt.setString(1, "%"+user_name+"%");
			rs = pstmt.executeQuery();

			userList = new ArrayList();
			while (rs.next()) {
				UsrMgrBean bean = new UsrMgrBean();
				bean.setUser_id(rs.getString("USER_ID"));	
				bean.setUser_name(rs.getString("USER_NAME"));
				bean.setDept_id(rs.getString("DEPT_ID"));
				bean.setDept_name(rs.getString("DEPT_NAME"));
				bean.setUpper_dept_name(rs.getString("UPPER_DEPT_NAME"));
				bean.setDept_fullname(rs.getString("DEPT_FULLNAME"));
				userList.add(bean);		
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		return userList;
	}

	//사용자 삭제	
	public int deleteUser(String user_id) {
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer deleteQuery = new StringBuffer();	

			//deleteQuery.append(" DELETE FROM USR WHERE USER_ID = ? ");
			deleteQuery.append(" UPDATE USR SET USE_YN = 'N' WHERE USER_ID = ? ");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(deleteQuery.toString());	
			pstmt.setString(1, user_id);
			result = pstmt.executeUpdate();			
			
		 } catch (SQLException e) {
			 logger.error("ERROR", e);
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt);	   
	     }
	     return result;
	}
	

	//하위부서 삭제	
	public int deleteSub(String p_dept_code){
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer deleteQuery = new StringBuffer();	

			//deleteQuery.append(" DELETE FROM DEPT WHERE DEPT_ID = ? ");
			deleteQuery.append(" UPDATE DEPT SET USE_YN = 'N' WHERE DEPT_ID = ? ");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(deleteQuery.toString());	
			pstmt.setString(1, p_dept_code);
			result = pstmt.executeUpdate();			
			
		 } catch (SQLException e) {
			 logger.error("ERROR", e);
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt);	   
	     }
	     return result;
	}
	
	
	/***************************************************** 세번째 iframe ***************************************************************/
	//하위 부서 상세보기
	public UsrMgrBean deptView(String dept_id) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UsrMgrBean usrMgr = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			/*
			selectQuery.append(" SELECT D.DEPT_ID, BASE_SYS, DEPT_NAME, DEPT_FULLNAME, UPPER_DEPT_ID,   ");
			selectQuery.append(" DEPT_RANK, DEPT_TEL, USE_YN, CON_YN, BIGO								");
			selectQuery.append(" FROM DEPT D, DEPT_EXT DE												");
			selectQuery.append(" WHERE D.DEPT_ID = DE.DEPT_ID											");
			selectQuery.append(" AND D.DEPT_ID = ?														");
			*/

			selectQuery.append(" SELECT D.DEPT_ID, DEPT_NAME, DEPT_FULLNAME, UPPER_DEPT_ID,   			");
			selectQuery.append(" DEPT_RANK, DEPT_TEL, ORGGBN, USE_YN, CON_YN							");
			selectQuery.append(" FROM DEPT D															");
			selectQuery.append(" WHERE DEPT_ID = ?														");

			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, dept_id);			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				usrMgr = new UsrMgrBean();
				usrMgr.setDept_id(rs.getString("DEPT_ID"));
				usrMgr.setDept_name(rs.getString("DEPT_NAME"));
				usrMgr.setDept_fullname(rs.getString("DEPT_FULLNAME"));
				usrMgr.setUpper_dept_id(rs.getString("UPPER_DEPT_ID"));
				
				usrMgr.setDept_rank(rs.getString("DEPT_RANK"));
				usrMgr.setDept_tel(rs.getString("DEPT_TEL"));
				usrMgr.setOrggbn(rs.getString("ORGGBN"));
				usrMgr.setUse_yn_one(rs.getString("USE_YN"));
				usrMgr.setCon_yn_one(rs.getString("CON_YN"));
			}
					
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return usrMgr;
	}
	

	//부서명칭 가져오기
	public String getDeptnm(String p_dept_code){
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try{
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT DEPT_NAME FROM DEPT WHERE DEPT_ID = ? ");			
		
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, p_dept_code);	
			rs = pstmt.executeQuery();				
		
			if ( rs.next() ){
				result = rs.getString(1);
			}		
			
		} catch(SQLException e){
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}
		
		return result;
	}
	
	//하위 사용자 상세보기
	public UsrMgrBean usrView(String user_id) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UsrMgrBean usrMgr = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			/*
			selectQuery.append(" SELECT U.USER_ID, USER_NAME, USER_SN, DEPT_ID, DEPT_NAME, DEPT_FULLNAME, CLASS_ID,  							");		
			selectQuery.append(" CLASS_NAME, POSITION_NAME, GRADE_ID, GRADE_NAME, EMAIL, TEL, MOBILE, USE_YN, CON_YN, PASSWD, EA_ID, GPKI_ID	");		
			selectQuery.append(" FROM USR U, USR_EXT UE 																						");		
			selectQuery.append(" WHERE U.USER_ID = UE.USER_ID 																					");		
			selectQuery.append(" AND U.USER_ID = ? 																								");
			*/		

			selectQuery.append(" SELECT USER_ID, USER_NAME, USER_SN, DEPT_ID, DEPT_NAME, DEPT_FULLNAME, CLASS_ID, CLASS_NAME,   				");		
			selectQuery.append(" POSITION_NAME, GRADE_ID, GRADE_NAME, USR_RANK,	EMAIL, TEL, MOBILE, USE_YN, CON_YN, PASSWD, EA_ID, GPKI_ID		");		
			selectQuery.append(" FROM USR				 																						");		
			selectQuery.append(" WHERE USER_ID = ? 																								");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, user_id);			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				usrMgr = new UsrMgrBean();
				usrMgr.setUser_id(rs.getString("USER_ID"));
				usrMgr.setUser_name(rs.getString("USER_NAME"));
				usrMgr.setUser_sn(rs.getString("USER_SN"));
				usrMgr.setDept_id(rs.getString("DEPT_ID"));
				usrMgr.setDept_name(rs.getString("DEPT_NAME"));
				usrMgr.setDept_fullname(rs.getString("DEPT_FULLNAME"));
				usrMgr.setClass_id(rs.getString("CLASS_ID"));
				
				usrMgr.setClass_name(rs.getString("CLASS_NAME"));
				usrMgr.setPosition_name(rs.getString("POSITION_NAME"));
				usrMgr.setGrade_id(rs.getString("GRADE_ID"));
				usrMgr.setGrade_name(rs.getString("GRADE_NAME"));
				usrMgr.setUsr_rank(rs.getString("USR_RANK"));
				usrMgr.setEmail(rs.getString("EMAIL"));
				usrMgr.setTel(rs.getString("TEL"));
				usrMgr.setMobile(rs.getString("MOBILE"));
				usrMgr.setUse_yn_one(rs.getString("USE_YN"));
				usrMgr.setCon_yn_one(rs.getString("CON_YN"));
				usrMgr.setPasswd(rs.getString("PASSWD"));
				usrMgr.setEa_id(rs.getString("EA_ID"));
				usrMgr.setGpki_id(rs.getString("GPKI_ID"));
			}
					
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return usrMgr;
	}

	//하위 사용자 수정(USR)
	public int modifyUser(UsrMgrBean uBean) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			con = ConnectionManager.getConnection();

			int i = 0;
			
			//패스워드 크기(jsp에서 max 40)
			int passwdBasciSize = 40;
			int passwdLength = uBean.getPasswd().length();
			
			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append(" UPDATE USR 																	");		
			updateQuery.append(" SET USER_NAME = ?, USER_SN = ?, DEPT_ID = ?, DEPT_NAME = ?, POSITION_NAME = ?, ");
			updateQuery.append(" CLASS_ID = ?, CLASS_NAME = ?, EMAIL = ?, TEL = ?, MOBILE = ?, 					");
			updateQuery.append(" USE_YN = ?, CON_YN = ?,  EA_ID = ?,	GPKI_ID = ?, UPTUSRID = ?,		");
			updateQuery.append(" UPTDT	= TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'), USR_RANK = ?						");
			
			//패스워드 
			if(passwdBasciSize >= passwdLength){
				updateQuery.append(" ,PASSWD = ?																	");
			}
			
			updateQuery.append(" WHERE USER_ID = ? 														    	");		

			pstmt = con.prepareStatement(updateQuery.toString());	
			
			pstmt.setString(++i, uBean.getUser_name());			
			pstmt.setString(++i, uBean.getUser_sn());			
			pstmt.setString(++i, uBean.getDept_id());			
			pstmt.setString(++i, uBean.getDept_name());			
			pstmt.setString(++i, uBean.getPosition_name());	
			
			pstmt.setString(++i, uBean.getClass_id());			
			pstmt.setString(++i, uBean.getClass_name());			
			pstmt.setString(++i, uBean.getEmail());		
			pstmt.setString(++i, uBean.getTel());	
			pstmt.setString(++i, uBean.getMobile());
			
			pstmt.setString(++i, uBean.getUse_yn_one());
			pstmt.setString(++i, uBean.getCon_yn_one());
			pstmt.setString(++i, uBean.getEa_id());		
			pstmt.setString(++i, uBean.getGpki_id());	
			pstmt.setString(++i, uBean.getUptusr());
			pstmt.setString(++i, uBean.getUsr_rank());
			if(passwdBasciSize >= passwdLength){
				pstmt.setString(++i, Utils.SHA256(uBean.getPasswd()));
			}
			
			pstmt.setString(++i, uBean.getUser_id());
			
			
			result= pstmt.executeUpdate();
					
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt);
		}	
		
		return result;
	}
	
	
	//부서 존재유무 
	public boolean existedDept(String p_dept_id) {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();

			selectQuery.append(" SELECT COUNT(*) FROM DEPT  WHERE DEPT_ID = ? ");
		
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, p_dept_id);
			rs = pstmt.executeQuery();
			
			int count = 0;
			if ( rs.next() ){
				count = rs.getInt(1);
			}
			
			if ( count == 1 ) {
				result = true;
			} else {
				result = false;
			}
			
		 } catch (SQLException e) {
			 logger.error("ERROR", e);
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt,rs);   
	     }
	     return result;
	}
	
	//하위 사용자 추가(USR)
	public int insertUser(UsrMgrBean uBean) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			con = ConnectionManager.getConnection();

			StringBuffer insertQuery = new StringBuffer();
			
			/*
			insertQuery.append(" INSERT INTO USR(USER_ID, USER_NAME, USER_SN, USER_STAT, USER_STAT_NAME,        ");       
			insertQuery.append(" REGULARITY, ORG_ID, ORG_NAME , DEPT_ID, DEPT_NAME,                 			");
			insertQuery.append(" DEPT_FULLNAME, CLASS_ID, CLASS_NAME, POSITION_ID, POSITION_NAME,           	");
			insertQuery.append(" GRADE_ID, GRADE_NAME, EMAIL, TEL, MOBILE,                   		 			");
			insertQuery.append(" JOIN_DAY, RETIRE_DAY, ADD_INFO1, ADD_INFO2, ADD_INFO3,                			");
			insertQuery.append(" ADD_INFO4, ADD_INFO5, ADD_INFO6, ADD_INFO7, BASE_SYS,                  		");
			insertQuery.append(" REG_DAY,  USE_YN, CON_YN, RDUTY_NAME)											");
			insertQuery.append(" VALUES(?, ?, ?, ?, ?,															");
			insertQuery.append(" ?, ?, ?, ?, ?,																    ");
			insertQuery.append(" ?, ?, ?, ?, ?,																	");	
			insertQuery.append(" ?, ?, ?, ?, ?,																    ");
			insertQuery.append(" ?, ?, ?, ?, ?,																	");	
			insertQuery.append(" ?, ?, ?, ?, ?,																	");	
			insertQuery.append(" TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'), ?, ?, ?)									");	
			*/

			/*
			pstmt.setString(++i, uBean.getUser_id());			
			pstmt.setString(++i, uBean.getUser_name());			
			pstmt.setString(++i, uBean.getUser_sn());		
			pstmt.setString(++i, "AAA");	
			pstmt.setString(++i, "재직");
			
			pstmt.setString(++i, "1");			
			//pstmt.setString(++i, "3460000");			
			pstmt.setString(++i, "" + appInfo.getRootid().substring(0, 7));			
			//pstmt.setString(++i, "수성구");
			pstmt.setString(++i, "");			
			pstmt.setString(++i, uBean.getDept_id());		
			pstmt.setString(++i, uBean.getDept_name());
			
			//pstmt.setString(++i, "대구광역시 수성구 "+uBean.getDept_name());		
			pstmt.setString(++i, ""+uBean.getDept_name());		
			pstmt.setString(++i, uBean.getClass_id());		
			pstmt.setString(++i, uBean.getClass_name());		
			pstmt.setString(++i, "");		
			pstmt.setString(++i, "");

			pstmt.setString(++i, uBean.getGrade_id());			
			pstmt.setString(++i, uBean.getGrade_name());			
			pstmt.setString(++i, uBean.getEmail());			
			pstmt.setString(++i, uBean.getTel());		
			pstmt.setString(++i, uBean.getMobile());
			
			pstmt.setString(++i, "");		
			pstmt.setString(++i, "");		
			pstmt.setString(++i, "");		
			pstmt.setString(++i, "");		
			pstmt.setString(++i, "");

			pstmt.setString(++i, "");			
			pstmt.setString(++i, "");			
			pstmt.setString(++i, "");			
			pstmt.setString(++i, "");		
			pstmt.setString(++i, "");
		
			pstmt.setString(++i, uBean.getUse_yn_one());			
			pstmt.setString(++i, uBean.getCon_yn_one());		
			pstmt.setString(++i, "");
			
			*/
			
			insertQuery.append(" INSERT INTO USR(USER_ID, USER_NAME, USER_SN, DEPT_ID, DEPT_NAME,               ");
			insertQuery.append(" DEPT_FULLNAME, CLASS_ID, CLASS_NAME, POSITION_ID, POSITION_NAME,           	");
			insertQuery.append(" GRADE_ID, GRADE_NAME, USR_RANK,  EMAIL, TEL,                    				"); 
			insertQuery.append(" MOBILE, PASSWD,  MGRYN, EA_ID, LSTLOGINDT,                    					"); 
			insertQuery.append(" USE_YN, CON_YN, CRTDT, CRTUSRID)												");
			insertQuery.append(" VALUES(?, ?, ?, ?, ?,															");
			insertQuery.append(" ?, ?, ?, ?, ?,																    ");
			insertQuery.append(" ?, ?, ?, ?, ?,																	");	
			insertQuery.append(" ?, ?, 'N', ?, ?,																");	
			insertQuery.append(" ?, ?, TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'), ? )									");	
			
			
			int i = 0;
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(++i, uBean.getUser_id());			
			pstmt.setString(++i, uBean.getUser_name());			
			pstmt.setString(++i, uBean.getUser_sn());		
			pstmt.setString(++i, uBean.getDept_id());		
			pstmt.setString(++i, uBean.getDept_name());
				
			pstmt.setString(++i, uBean.getDept_fullname());		
			pstmt.setString(++i, uBean.getClass_id());		
			pstmt.setString(++i, uBean.getClass_name());		
			pstmt.setString(++i, uBean.getPosition_id());		
			pstmt.setString(++i, uBean.getPosition_name());

			pstmt.setString(++i, uBean.getGrade_id());			
			pstmt.setString(++i, uBean.getGrade_name());		
			pstmt.setString(++i, uBean.getUsr_rank());			
			pstmt.setString(++i, uBean.getEmail());			
			pstmt.setString(++i, uBean.getTel());		
			
			pstmt.setString(++i, uBean.getMobile());
			pstmt.setString(++i, uBean.getPasswd());
			pstmt.setString(++i, uBean.getEa_id());
			pstmt.setString(++i, "");		

			pstmt.setString(++i, uBean.getUse_yn_one());
			pstmt.setString(++i, uBean.getCon_yn_one());
			pstmt.setString(++i, uBean.getUptusr());
			
			result= pstmt.executeUpdate();
			
			setUsrGpkiId(con, uBean.getUser_id(), uBean.getGpki_id());

			con.commit();	
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt);
		}	
		
		return result;
	}
	
	//하위 부서 수정(DEPT)
	public int modifyDept(UsrMgrBean uBean) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			con = ConnectionManager.getConnection();
			
			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append(" UPDATE DEPT 																				");		
			updateQuery.append(" SET DEPT_NAME = ?, DEPT_RANK = ?, DEPT_TEL=?, USE_YN = ?, CON_YN = ?, 						");
			updateQuery.append("     UPPER_DEPT_ID = ?, ORGGBN = ?,  UPTUSRID = ?, UPTDT = TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')	");
			updateQuery.append(" WHERE DEPT_ID = ? 																			");		

			pstmt = con.prepareStatement(updateQuery.toString());		
			pstmt.setString(1, uBean.getDept_name());			
			pstmt.setString(2, uBean.getDept_rank());			
			pstmt.setString(3, uBean.getDept_tel());			
			pstmt.setString(4, uBean.getUse_yn_one());			
			pstmt.setString(5, uBean.getCon_yn_one());			
			pstmt.setString(6, uBean.getUpper_dept_id());
			pstmt.setString(7, uBean.getOrggbn());
			pstmt.setString(8, uBean.getUptusr());
			pstmt.setString(9, uBean.getDept_id());			
			result= pstmt.executeUpdate();
					
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt);
		}	
		
		return result;
	}
	
	//하위 부서 추가(DEPT)
	public int insertDept(UsrMgrBean uBean) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			con = ConnectionManager.getConnection();

			StringBuffer insertQuery = new StringBuffer();
			
			insertQuery.append(" INSERT INTO DEPT(DEPT_ID, DEPT_NAME, DEPT_FULLNAME, UPPER_DEPT_ID, TOP_DEPT_ID,	");	
			insertQuery.append(" DEPT_DEPTH, DEPT_RANK, DEPT_TEL, ORGGBN,		         			 				");
			insertQuery.append(" MAIN_YN, USE_YN, CON_YN, CRTDT, CRTUSRID)											");		
			insertQuery.append(" VALUES(?, ?, ?, ?, ?, 																");
			insertQuery.append(" ?, ?, ?, ?,																		");	
			insertQuery.append(" 'Y', ?, ?, TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'), ?)									");	
			
			int i = 0;
			pstmt = con.prepareStatement(insertQuery.toString());	
			pstmt.setString(++i, uBean.getDept_id());			
			pstmt.setString(++i, uBean.getDept_name());				
			pstmt.setString(++i, uBean.getDept_fullname());		
			pstmt.setString(++i, uBean.getUpper_dept_id());
			pstmt.setString(++i, appInfo.getRootid());				
			
			int depth = Integer.parseInt(DeptManager.instance().getDeptInfo(uBean.getUpper_dept_id()).getDept_depth()) + 1;
			pstmt.setString(++i, Integer.toString(depth));
			pstmt.setString(++i, uBean.getDept_rank());		
			pstmt.setString(++i, uBean.getDept_tel());			
			pstmt.setString(++i, ("".equals(Utils.nullToEmptyString(uBean.getOrggbn()))) ? "001" : uBean.getOrggbn());
			
			pstmt.setString(++i, uBean.getUse_yn_one());		
			pstmt.setString(++i, uBean.getCon_yn_one());			
			pstmt.setString(++i, uBean.getUptusr());
			
			result= pstmt.executeUpdate();
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt);
		}	
		
		return result;
	}
	
	//하위 부서 추가(DEPT_EXT)
	public int insertDeptExt(UsrMgrBean uBean) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			con = ConnectionManager.getConnection();

			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append(" INSERT INTO DEPT_EXT(DEPT_ID, MAIN_FL, BIGO, CRTDT, CRTUSR) ");		
			insertQuery.append(" VALUES(?, '1', ?, TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'), ? )	 ");			

			pstmt = con.prepareStatement(insertQuery.toString());	
			pstmt.setString(1, uBean.getDept_id());			
			pstmt.setString(2, uBean.getBigo());				
			pstmt.setString(3, uBean.getUptusr());			
			result= pstmt.executeUpdate();
					
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt);
		}	
		
		return result;
	}
	
	//사용자 비밀번호 초기화
	public int modifyUsrPass(UsrMgrBean uBean) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			con = ConnectionManager.getConnection();
			
			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append(" UPDATE USR																	");		
			updateQuery.append(" SET PASSWD = ?, UPTUSRID = ?, UPTDT = TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')	");			
			updateQuery.append(" WHERE USER_ID = ? 															");		

			pstmt = con.prepareStatement(updateQuery.toString());		
			pstmt.setString(1, Utils.SHA256("1"));			
			pstmt.setString(2, uBean.getUptusr());			
			pstmt.setString(3, uBean.getUser_id());			
			result= pstmt.executeUpdate();
					
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt);
		}	
		
		return result;
	}
	
	//직급검색
	public List roleList() throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List roleList = null;
		
		StringBuffer selectQuery = new StringBuffer();			
		selectQuery.append(" SELECT CLASS_ID, CLASS_NAME			");
		selectQuery.append(" FROM USR								");
		selectQuery.append(" WHERE CLASS_ID IS NOT NULL				");
		selectQuery.append(" GROUP BY CLASS_ID, CLASS_NAME			");
		selectQuery.append(" ORDER BY CLASS_ID						");
		    
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();

			roleList = new ArrayList();
			while (rs.next()) {
				UsrMgrBean bean = new UsrMgrBean();
				bean.setClass_id(rs.getString("CLASS_ID"));	
				bean.setClass_name(rs.getString("CLASS_NAME"));
				roleList.add(bean);		
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		return roleList;
	}

	//USR에 AGE(연령대)랑 SEX(성별) 업데이트
	public int modifyUsrAgeSex(String user_id) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			con = ConnectionManager.getConnection();
			
			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append(" UPDATE USR	UE																									");
			updateQuery.append(" SET SEX = (SELECT CASE LENGTH(USER_SN) WHEN 13 THEN CASE WHEN SUBSTR(USER_SN, 7, 1) IN (2, 4) THEN 'F'			");
			updateQuery.append(" 					ELSE 'M' END  ELSE 'M' END 성별																");
			updateQuery.append(" 			FROM USR U																							");
			updateQuery.append(" 			WHERE U.USER_ID = UE.USER_ID),																		");
			updateQuery.append(" AGE = (SELECT CASE LENGTH(USER_SN) WHEN 13 THEN TRUNC(2009 - TO_NUMBER('19'||SUBSTR(USER_SN, 1, 2)) + 1, -1)	");
			updateQuery.append(" 				ELSE 20 END 연령대																				");
			updateQuery.append(" 		FROM USR U																								");
			updateQuery.append(" 		WHERE U.USER_ID = UE.USER_ID)																			");
			updateQuery.append(" WHERE USER_ID = ? 																								");

			pstmt = con.prepareStatement(updateQuery.toString());		
			pstmt.setString(1, user_id);					
			result= pstmt.executeUpdate();
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt);
		}	
		
		return result;
	}
	
	/**
	 * 전자결재아이디 등록
	 * @param userid
	 * @param gpkiid
	 * @return
	 * @throws Exception
	 */
	public int setEaId(String userid, String eaid) throws Exception {	
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append(" UPDATE USR 			\n");
			sql.append(" SET EA_ID = ? 			\n");
			sql.append(" WHERE USER_ID LIKE ? 	\n");
			
			con = ConnectionManager.getConnection();
			
			pstmt =	con.prepareStatement(sql.toString());
			pstmt.setString(1, eaid);
			pstmt.setString(2, userid);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt);
		}
		
		return result;
	}
	
	/**
	 * 등록된 GPKI_ID의 사용자 ID
	 * @param gpkiid
	 * @return
	 * @throws Exception
	 */
	public String getUsrGpkiId(String gpkiid) throws Exception {	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT USER_ID 		\n");
			sql.append(" FROM USR 				\n");
			sql.append(" WHERE GPKI_ID LIKE ? 	\n");
			
			con = ConnectionManager.getConnection();
			
			pstmt =	con.prepareStatement(sql.toString());
			pstmt.setString(1, gpkiid);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				result = rs.getString("USER_ID");
			}
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 사용자의 GPKI_ID 등록
	 * @param userid
	 * @param gpkiid
	 * @return
	 * @throws Exception
	 */
	public int setUsrGpkiId(Connection con, String userid, String gpkiid) throws Exception {	

		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("UPDATE USR \n");
			sql.append("SET GPKI_ID = '' \n");
			sql.append("WHERE GPKI_ID LIKE ? \n");
			
			pstmt =	con.prepareStatement(sql.toString());
			pstmt.setString(1, gpkiid);
			
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			sql.delete(0, sql.capacity());			
			sql.append("UPDATE USR \n");
			sql.append("SET GPKI_ID = ? \n");
			sql.append("WHERE USER_ID = ? \n");
			
			pstmt =	con.prepareStatement(sql.toString());
			pstmt.setString(1, gpkiid);
			pstmt.setString(2, userid);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
}
