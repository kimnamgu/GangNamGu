/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 부서정보 dao
 * 설명:
 */
package nexti.ejms.dept.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.util.commfunction;

public class DeptDAO {
	private static Logger logger = Logger.getLogger(DeptDAO.class);
	
	/**
	 * 하위부서 목록가져오기
	 * @param deptid
	 * @return
	 */
	public List getSubDeptList(String deptid, boolean includeTopDept) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DEPT_ID, DEPT_NAME, DEPT_FULLNAME, UPPER_DEPT_ID, TOP_DEPT_ID, ");
			sql.append("       DEPT_DEPTH, DEPT_RANK, DEPT_TEL, DEPT_FAX, ORGGBN, ");
			sql.append("       MAIN_YN, USE_YN, CON_YN, CRTDT, CRTUSRID, UPTDT, UPTUSRID ");
			sql.append("FROM DEPT ");
			sql.append("WHERE MAIN_YN = 'Y' AND USE_YN = 'Y' \n");
			if ( !includeTopDept ) {
				sql.append("AND LEVEL > 1 ");
			}
			sql.append("START WITH DEPT_ID = ? ");
			sql.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID ");
			sql.append("ORDER SIBLINGS BY DEPT_RANK ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, deptid);
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				DeptBean dbean = new DeptBean();
				dbean.setDept_id(rs.getString("DEPT_ID")); 
				dbean.setDept_name(rs.getString("DEPT_NAME"));
				dbean.setDept_fullname(rs.getString("DEPT_FULLNAME"));
				dbean.setUpper_dept_id(rs.getString("UPPER_DEPT_ID"));
				dbean.setTop_dept_id(rs.getString("TOP_DEPT_ID"));
				dbean.setDept_depth(rs.getString("DEPT_DEPTH"));
				dbean.setDept_rank(rs.getString("DEPT_RANK"));
				dbean.setDept_tel(rs.getString("DEPT_TEL"));
				dbean.setDept_fax(rs.getString("DEPT_FAX"));
				dbean.setOrggbn(rs.getString("ORGGBN"));
				dbean.setMain_yn(rs.getString("MAIN_YN"));
				dbean.setUse_yn(rs.getString("USE_YN"));
				dbean.setCon_yn(rs.getString("CON_YN"));
				dbean.setCrtdt(rs.getString("CRTDT"));
				dbean.setCrtusrid(rs.getString("CRTUSRID"));
				dbean.setUptdt(rs.getString("UPTDT"));
				dbean.setUptusrid(rs.getString("UPTUSRID"));
				
				if ( result == null ) result = new ArrayList();
				result.add(dbean);
			}
		} catch ( Exception e ) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}	
		
		return result;
	}
		
	/**
	 * 부서 정보
	 */
	public DeptBean getDeptInfo(String deptid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DeptBean dept = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT DEPT_ID, DEPT_NAME, DEPT_FULLNAME, UPPER_DEPT_ID, \n");
			selectQuery.append(" 		TOP_DEPT_ID, DEPT_DEPTH, DEPT_RANK, DEPT_TEL, DEPT_FAX, \n");
			selectQuery.append(" 		ORGGBN, MAIN_YN, USE_YN, CON_YN, CRTDT, CRTUSRID, UPTDT, UPTUSRID \n");
			selectQuery.append(" FROM DEPT \n");
			selectQuery.append(" WHERE DEPT_ID = ? \n");			

			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, deptid);			
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				dept = new DeptBean();
				dept.setDept_id(rs.getString("DEPT_ID")); 
				dept.setDept_name(rs.getString("DEPT_NAME"));
				dept.setDept_fullname(rs.getString("DEPT_FULLNAME"));
				dept.setUpper_dept_id(rs.getString("UPPER_DEPT_ID"));
				dept.setTop_dept_id(rs.getString("TOP_DEPT_ID"));
				dept.setDept_depth(rs.getString("DEPT_DEPTH"));
				dept.setDept_rank(rs.getString("DEPT_RANK"));
				dept.setDept_tel(rs.getString("DEPT_TEL"));
				dept.setDept_fax(rs.getString("DEPT_FAX"));
				dept.setOrggbn(rs.getString("ORGGBN"));
				dept.setMain_yn(rs.getString("MAIN_YN"));
				dept.setUse_yn(rs.getString("USE_YN"));
				dept.setCon_yn(rs.getString("CON_YN"));
				dept.setCrtdt(rs.getString("CRTDT"));
				dept.setCrtusrid(rs.getString("CRTUSRID"));
				dept.setUptdt(rs.getString("UPTDT"));
				dept.setUptusrid(rs.getString("UPTUSRID"));
			}			
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return dept;
	}		
	
	/**
	 * 담당단위 정보 가져오기
	 */
	public List chrgUnitList(String deptid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List chrgList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT DEPT_ID,  CHRGUNITCD, CHRGUNITNM, ORD, SUBSTR(CRTDT,1,10) CRTDT, ");
			selectQuery.append("       CRTUSRID, UPTDT,      UPTUSRID ");
			selectQuery.append("FROM CHRGUNIT ");	
			selectQuery.append("WHERE DEPT_ID = ? ");
			selectQuery.append("ORDER BY ORD, CHRGUNITCD ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());				
			pstmt.setString(1, deptid);
			
			rs = pstmt.executeQuery();					
			
			chrgList = new ArrayList();
			
			while ( rs.next() ){
				ChrgUnitBean chrg = new ChrgUnitBean();
				
				chrg.setDept_id(rs.getString("DEPT_ID"));
				chrg.setChrgunitcd(rs.getInt("CHRGUNITCD"));
				chrg.setChrgunitnm(rs.getString("CHRGUNITNM"));
				chrg.setOrd(rs.getInt("ORD"));
				chrg.setCrtdt(rs.getString("CRTDT"));
				chrg.setCrtusrid(rs.getString("CRTUSRID"));
				chrg.setUptdt(rs.getString("UPTDT"));
				chrg.setUptusrid(rs.getString("UPTUSRID"));
				
				chrgList.add(chrg);
			}			
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return chrgList;
	}
	
	/**
	 * 배부담당 정보 가져오기
	 */
	public List deliveryUserList(String deptid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List chrgList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT USER_ID, USER_NAME || ' - ' || CLASS_NAME USER_NAME \n");
			selectQuery.append("FROM USR \n");
			selectQuery.append("WHERE DELIVERY_YN = 'Y' \n");
			selectQuery.append("AND DEPT_ID = ? \n");
			selectQuery.append("ORDER BY USR_RANK \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());				
			pstmt.setString(1, deptid);
			
			rs = pstmt.executeQuery();					
			
			chrgList = new ArrayList();
			
			while ( rs.next() ){
				ChrgUnitBean chrg = new ChrgUnitBean();
				
				chrg.setUser_id(rs.getString("USER_ID"));
				chrg.setUser_name(rs.getString("USER_NAME"));
				
				chrgList.add(chrg);
			}			
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return chrgList;
	}
	
	/**
	 * 개인별 담당단위정보 가져오기
	 * @param String user_id
	 * @return ChrgUnitBean 담당단위정
	 */
	public ChrgUnitBean chrgUnitInfo(String user_id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ChrgUnitBean chrg = null;
		
		try {
			/*[USR_EXT] 테이블 삭제
			String sql =
				"SELECT  A.DEPT_ID, A.CHRGUNITCD, A.CHRGUNITNM, A.ORD, " +
				"        A.CRTDT, A.CRTUSRID, A.UPTDT, A.UPTUSRID " +
				"FROM CHRGUNIT A, USR B, USR_EXT C " +
				"WHERE A.DEPT_ID = B.DEPT_ID " +
				"  AND A.CHRGUNITCD = C.CHRGUNITCD " +
				"  AND B.USER_ID = C.USER_ID " +
				"  AND B.USER_ID = ? ";					
			*/
			
			String sql =
				"SELECT  A.DEPT_ID, A.CHRGUNITCD, A.CHRGUNITNM, A.ORD, " +
				"        A.CRTDT, A.CRTUSRID, A.UPTDT, A.UPTUSRID " +
				"FROM CHRGUNIT A, USR B " +
				"WHERE A.DEPT_ID = B.DEPT_ID " +
				"  AND A.CHRGUNITCD = B.CHRGUNITCD " +
				"  AND B.USER_ID = ? ";			
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user_id);			
						
			rs = pstmt.executeQuery();					
			
			if(rs.next()){
				chrg = new ChrgUnitBean();
				
				chrg.setDept_id(rs.getString("DEPT_ID"));
			
				chrg.setChrgunitcd(rs.getInt("CHRGUNITCD"));
				chrg.setChrgunitnm(rs.getString("CHRGUNITNM"));
				chrg.setOrd(rs.getInt("ORD"));
				chrg.setCrtdt(rs.getString("CRTDT"));
				chrg.setCrtusrid(rs.getString("CRTUSRID"));
				chrg.setUptdt(rs.getString("UPTDT"));
				chrg.setUptusrid(rs.getString("UPTUSRID"));
			}			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(conn,pstmt,rs);
		}	
		
		return chrg;
	}
	
	/**
	 * 담당단위정보 가져오기
	 */
	public ChrgUnitBean chrgUnitInfo(String dept_id, int chrgunitcd) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ChrgUnitBean chrg = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT DEPT_ID,  CHRGUNITCD, CHRGUNITNM, ORD, CRTDT, ");
			selectQuery.append("       CRTUSRID, UPTDT,      UPTUSRID ");
			selectQuery.append("FROM CHRGUNIT ");
			selectQuery.append("WHERE DEPT_ID = ? ");
			selectQuery.append("  AND CHRGUNITCD = ? ");
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(selectQuery.toString());
			
			pstmt.setString(1, dept_id);	
			pstmt.setInt(2, chrgunitcd);
						
			rs = pstmt.executeQuery();					
			
			if(rs.next()){
				chrg = new ChrgUnitBean();
				
				chrg.setDept_id(rs.getString("DEPT_ID"));			
				chrg.setChrgunitcd(rs.getInt("CHRGUNITCD"));
				chrg.setChrgunitnm(rs.getString("CHRGUNITNM"));
				chrg.setOrd(rs.getInt("ORD"));
				chrg.setCrtdt(rs.getString("CRTDT"));
				chrg.setCrtusrid(rs.getString("CRTUSRID"));
				chrg.setUptdt(rs.getString("UPTDT"));
				chrg.setUptusrid(rs.getString("UPTUSRID"));
			}			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(conn,pstmt,rs);
		}	
		
		return chrg;
	}

	/**
	 * 개인별 담당단위코드 가져오기
	 * @param String user_id
	 * @return int 담당단위코드
	 */
	public int getChrgunitcd(String user_id) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int code = 0;
		
		try {
			String sql =
				"SELECT NVL(CHRGUNITCD, 0)" +
				"FROM USR " +
				"WHERE USER_ID = ? ";				
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user_id);
			
			rs = pstmt.executeQuery();					
			
			if(rs.next()){
				code = rs.getInt(1);
			}			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(conn,pstmt,rs);
		}	
		
		return code;
	}	
	
	/**
	 * 개인별 담당단위명칭 가져오기
	 * @param String dept_code
	 * @param int chrg_code
	 * @return String 담당단위명칭
	 */
	public String getChrgunitnm(String dept_code, int chrg_code) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String name = "";
		
		try {
			String sql =
				"SELECT CHRGUNITNM " +
				"FROM CHRGUNIT " +
				"WHERE DEPT_ID = ? " +
				"  AND CHRGUNITCD = ? ";					
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dept_code);
			pstmt.setInt(2, chrg_code);			
						
			rs = pstmt.executeQuery();					
			
			if(rs.next()){
				name = rs.getString(1);
			}			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(conn,pstmt,rs);
		}	
		
		return name;
	}	
	
	/** 
	 * 대상부서 목록
	 */
	public List tgtDeptList(String orggbn, String user_id, String dept_depth) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List tgtList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			/*
			selectQuery.append(" SELECT DEPT_ID, DEPT_NAME 		\n");
			selectQuery.append(" FROM DEPT 						\n");
			selectQuery.append(" WHERE MAIN_YN = 'Y' 			\n");
			selectQuery.append(" ORDER BY TO_NUMBER(DEPT_RANK) 	\n");
			*/

			/*
			selectQuery.append(" SELECT DEPT_ID, DEPT_NAME																\n");
			selectQuery.append(" FROM DEPT                                                                              \n");
			if(orggbn.equals("001")){
			selectQuery.append(" START WITH DEPT_ID = '"+rootid+"'                                                      \n");
			}else{
			selectQuery.append(" START WITH DEPT_ID =                                                                   \n");
			selectQuery.append(" (                                                                                      \n");
			selectQuery.append("  SELECT DEPT_ID FROM DEPT   							                                \n");
			selectQuery.append(" 	WHERE DEPT_DEPTH = '"+dept_depth+"'                                                 \n");
			selectQuery.append(" 	START WITH DEPT_ID IN (SELECT DEPT_ID FROM USR WHERE USER_ID = '"+user_id+"')       \n");
			selectQuery.append(" 	CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID                                   	        \n");
			selectQuery.append(" )                                                                                      \n");
			}
			selectQuery.append(" CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                               \n");
			selectQuery.append(" AND ORGGBN = '"+orggbn+"'                                                              \n");
			selectQuery.append(" AND MAIN_YN = 'Y'		                         	                                    \n");
			selectQuery.append(" AND USE_YN = 'Y'		                         	                                    \n");
			selectQuery.append(" ORDER SIBLINGS BY TO_NUMBER(DEPT_RANK)                                                 \n");
			*/
			
			String rootid = appInfo.getRootid();
			int subLen = commfunction.getDeptFullNameSubstringIndex(user_id);
			selectQuery.append(" SELECT DEPT_ID, DEPT_FULLNAME										                    \n");
			selectQuery.append(" FROM                                                                                   \n");
			selectQuery.append(" ( SELECT DEPT_ID, NVL(TRIM(SUBSTR(DEPT_FULLNAME, "+subLen+")), DEPT_NAME) DEPT_FULLNAME, \n");
			selectQuery.append("          ORGGBN, MAIN_YN, USE_YN, DEPT_RANK                                            \n");
			selectQuery.append("  FROM DEPT                                                                             \n");
			if(orggbn.equals("001")){
			selectQuery.append(" START WITH DEPT_ID = '"+rootid+"'                                                      \n");
			}else{
			selectQuery.append(" START WITH DEPT_ID =                                                                   \n");
			selectQuery.append(" (                                                                                      \n");
			selectQuery.append("  SELECT DEPT_ID FROM DEPT   							                                \n");
			selectQuery.append(" 	WHERE DEPT_DEPTH = '"+dept_depth+"'                                                 \n");
			selectQuery.append(" 	START WITH DEPT_ID IN (SELECT DEPT_ID FROM USR WHERE USER_ID = '"+user_id+"')       \n");
			selectQuery.append(" 	CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID                                   	        \n");
			selectQuery.append(" )                                                                                      \n");
			}
			selectQuery.append("  CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID   	                                        \n");
			selectQuery.append("  ORDER SIBLINGS BY TO_NUMBER(DEPT_RANK)                                                \n");
			selectQuery.append(" )                                                                                      \n");
			selectQuery.append(" WHERE ORGGBN = '"+orggbn+"'                                                            \n");
			selectQuery.append(" AND MAIN_YN = 'Y'		                         	                                    \n");
			selectQuery.append(" AND USE_YN = 'Y'                                                                       \n");
			selectQuery.append(" AND DEPT_ID != '"+rootid+"'                                                 			\n");

			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());				
						
			rs = pstmt.executeQuery();					
			
			tgtList = new ArrayList();
			
			while ( rs.next() ){
				DeptBean dept = new DeptBean();
				
				dept.setDept_id(rs.getString("DEPT_ID"));
				dept.setDept_fullname(rs.getString("DEPT_FULLNAME"));
				
				tgtList.add(dept);
			}			
			
		} catch(SQLException e) {
			logger.error("ERROR", e);			
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return tgtList;
	}
	
	/** 
	 * 담당단위 코드 존재유무 
	 * return true : 존재함
	 * return false : 존재안함
	 */
	public boolean existedChrg(String dept_id) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("SELECT COUNT(*) FROM CHRGUNIT ");
	       	selectQuery.append("WHERE DEPT_ID = ? ");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
			pstmt.setString(1, dept_id);

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
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
	    	ConnectionManager.close(conn,pstmt,rs);	   
	     }
	     return result;
	}
	
	/** 
	 * 담당단위 코드 존재유무 
	 * return true : 존재함
	 * return false : 존재안함
	 */
	public boolean existedChrg(String dept_id, int chrgunitcd) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {	       	
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) FROM CHRGUNIT ");
			selectQuery.append("WHERE DEPT_ID = ? AND CHRGUNITCD = ? ");
			
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(selectQuery.toString());	
			pstmt.setString(1, dept_id);
			pstmt.setInt(2, chrgunitcd);
			
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
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
		} finally {	       
			ConnectionManager.close(conn,pstmt,rs);	   
		}
		return result;
	}	

	/** 
	 * 담당단위 코드 존재유무 
	 * return true : 존재함
	 * return false : 존재안함
	 */
	public int getExistedChrg(String dept_id) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {	       	
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(CHRGUNITCD) AS CNT FROM CHRGUNIT ");
			selectQuery.append("WHERE DEPT_ID = ? ");
			
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(selectQuery.toString());	
			pstmt.setString(1, dept_id);
			
			rs = pstmt.executeQuery();
			if ( rs.next() ){
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
		} finally {	       
			ConnectionManager.close(conn,pstmt,rs);	   
		}
		return result;
	}	
	
	/**
	 * 새로운 담당단위코드 가져오기
	 * @param dept_id
	 * @return
	 * @throws Exception
	 */
	public int getNextChrgunitcd(String dept_id) throws Exception {
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;

		
		StringBuffer selectQuery = new StringBuffer();
		selectQuery.append("SELECT NVL(MAX(CHRGUNITCD), 0) + 1 FROM CHRGUNIT WHERE DEPT_ID = ? ");	
		
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setString(1, dept_id);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getInt(1);
			}		
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
	
	/** 
	 * 담당단위 추가	 
	 */
	public int insertChrgUnit (ChrgUnitBean bean) throws Exception {
		Connection conn = null;      
		PreparedStatement pstmt = null;
		int result = 0 ;
		
		try {
			StringBuffer insertQuery = new StringBuffer();

			insertQuery.append("INSERT INTO CHRGUNIT (DEPT_ID, CHRGUNITCD, CHRGUNITNM, ORD, CRTDT, ");
			insertQuery.append("            CRTUSRID) ");
			insertQuery.append("VALUES(?,?,?,?,TO_CHAR(SYSDATE, 'YYYY-MM-DD hh24:mi:ss'), ?)");
			//insertQuery.append("INSERT ALL \n");
			//insertQuery.append("WHEN CNT = 0 \n");
			//insertQuery.append("THEN INSERT INTO CHRGUNIT(DEPT_ID, CHRGUNITCD, CHRGUNITNM, ORD, CRTDT, CRTUSRID) \n");
			//insertQuery.append("                 VALUES(?,?,?,?,TO_CHAR(SYSDATE, 'YYYY-MM-DD hh24:mi:ss'), ?) \n");
			//insertQuery.append("SELECT COUNT(*) FROM CHRGUNIT WHERE DEPT_ID = ? \n");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(insertQuery.toString());	
			
			pstmt.setString(1, bean.getDept_id());
			pstmt.setInt(2, bean.getChrgunitcd());
			pstmt.setString(3, bean.getChrgunitnm());
			pstmt.setInt(4, bean.getOrd());
			pstmt.setString(5, bean.getCrtusrid());			
			
			result = pstmt.executeUpdate();			
		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt);
			throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt); 	   
	     }
	     return result;
	}
	
	/** 
	 * 담당단위 수정	
	 */
	public int modifyChrgUnit (ChrgUnitBean bean) throws Exception {
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();

			updateQuery.append("UPDATE CHRGUNIT SET CHRGUNITNM = ?, ORD = ?, UPTUSRID = ?, UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') ");
			updateQuery.append("WHERE DEPT_ID = ? ");
			updateQuery.append("  AND CHRGUNITCD = ? ");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(updateQuery.toString());		
		    
			pstmt.setString(1, bean.getChrgunitnm());
			pstmt.setInt(2, bean.getOrd());
			pstmt.setString(3, bean.getUptusrid());
			pstmt.setString(4, bean.getDept_id());
			pstmt.setInt(5, bean.getChrgunitcd());
			
			result = pstmt.executeUpdate();			
		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt);
			throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt);   
	     }	     
	     return result;
	}

	/** 
	 * 담당단위 삭제	
	 */
	public int deleteChrgUnit (String dept_id, int chrgunitcd) throws Exception {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer deleteQuery = new StringBuffer();

			deleteQuery.append("DELETE FROM CHRGUNIT ");
			deleteQuery.append("WHERE DEPT_ID =? ");
			deleteQuery.append("  AND CHRGUNITCD = ? ");
			
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(deleteQuery.toString());			    
			pstmt.setString(1, dept_id);
			pstmt.setInt(2, chrgunitcd);
			
			result = pstmt.executeUpdate();			
		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt);
			throw e;
	     } finally {	       
	    	ConnectionManager.close(conn,pstmt);	   
	     }
	     return result;
	}
	
	/** 
	 * 배부담당 지정	
	 */
	public int updateDeliveryUser(String userid, String delivery_yn) throws Exception {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE USR ");
			sql.append("SET DELIVERY_YN = ? ");
			sql.append("WHERE USER_ID = ? ");
			
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql.toString());			    
			pstmt.setString(1, delivery_yn);
			pstmt.setString(2, userid);
			
			result = pstmt.executeUpdate();			
		} catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(conn,pstmt);	   
		}
		return result;
	}

	/** 
	 * DEPT테이블에 신규부서 추가	 
	 */
	public int insertDept(Connection conn, DeptBean bean) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0 ;
		
		try {
			StringBuffer insertQuery = new StringBuffer();

			insertQuery.append("INSERT INTO DEPT (DEPT_ID ,MAIN_FL ,CRTDT ,CRTUSRID) ");
			insertQuery.append("VALUES(?,?,TO_CHAR(SYSDATE,'YYYY-MM-DD hh24:mi:ss'), ?)");
		    
		    pstmt = conn.prepareStatement(insertQuery.toString());
			
			pstmt.setString(1, bean.getDept_id());
			pstmt.setString(2, bean.getMain_yn());
			pstmt.setString(3, bean.getCrtusrid());			
			
			result = pstmt.executeUpdate();
	     }finally {	       
	    	 try {pstmt.close();} catch (Exception e) {}
	     }
	     return result;
	}

	/** 
	 * DEPT테이블에 폐지부서 삭제
	 */
	public int deleteDept(Connection conn, String dept_id) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0 ;
		
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("DELETE FROM DEPT WHERE DEPT_ID=? ");
		    
		    pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, dept_id);
			
			result = pstmt.executeUpdate();
	     } finally {	       
	    	 try {pstmt.close();} catch (Exception e) {}
	     }
	     return result;
	}
	
}
