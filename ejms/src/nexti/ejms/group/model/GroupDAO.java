/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배포목록관리 dao
 * 설명:
 */
package nexti.ejms.group.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.util.commfunction;

public class GroupDAO {
	private static Logger logger = Logger.getLogger(GroupDAO.class);
	
	/** (max+1)코드 값 가져오기	
	 * @throws Exception */
	public int getMaxSEQ(int grplistcd, String type, String crtusrgbn) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int  maxSeq = 0;
		
		StringBuffer selectQuery = new StringBuffer();

		if( type.equals("0") == true ) {
			if ( crtusrgbn.equals("0") == true ) {
				selectQuery.append("SELECT NVL(MAX(GRPLISTCD), 0) + 1	");
				selectQuery.append("FROM GROUPMST						");
			} else {
				selectQuery.append("SELECT NVL(MAX(GRPLISTCD), 999) + 1	");
				selectQuery.append("FROM GROUPMST							");
				selectQuery.append("WHERE GRPLISTCD > 999					");
			}
		} else {
			selectQuery.append("SELECT NVL(MAX(SEQ),0) + 1 	");
			selectQuery.append("  FROM GRPLISTDTL 		");
			selectQuery.append(" WHERE GRPLISTCD = '"+grplistcd+ "' ");
		}
		
		try {

			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				maxSeq = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return maxSeq;
	}
	
	/** 배포목록 마스타 추가	 */
	public int insertGrpMst (GroupBean bean) throws Exception {
		Connection conn = null;      
		PreparedStatement pstmt = null;
		int result = 0;
		int maxSeq = 0;
		
		try {
			StringBuffer insertQuery = new StringBuffer();

			insertQuery.append("\n INSERT INTO GROUPMST (GRPLISTCD, GRPLISTNM, ORD, CRTDT, CRTUSRID, CRTUSRGBN) ");
			insertQuery.append("\n VALUES (?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD hh24:mi:ss'), ?, ?) ");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(insertQuery.toString());	
			
		    if ( bean.getCrtusrgbn().equals("0") == true ) {
		    	pstmt.setInt(1, bean.getGrplistcd());
				pstmt.setString(2, bean.getGrplistnm());
				pstmt.setInt(3, bean.getGrplistcd());
				pstmt.setString(4, bean.getCrtusrid());
				pstmt.setString(5, bean.getCrtusrgbn());
				
				result = pstmt.executeUpdate();
		    } else {
		    	maxSeq = getMaxSEQ(bean.getGrplistcd(), "0", bean.getCrtusrgbn());
		    	pstmt.setInt(1, maxSeq);
				pstmt.setString(2, bean.getGrplistnm());
				pstmt.setInt(3, maxSeq);
				pstmt.setString(4, bean.getCrtusrid());
				pstmt.setString(5, bean.getCrtusrgbn());
				
				result = pstmt.executeUpdate();
				if ( result > 0 ) {
					result = maxSeq;
				}
		    }
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
	 * 배포목록 마스타 수정	
	 */
	public int modifyGrpMst (GroupBean bean) throws Exception {
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();

			updateQuery.append("\n UPDATE GROUPMST SET GRPLISTNM=?, ORD=?, CRTUSRID=?, CRTDT=TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') ");
			updateQuery.append("\n  WHERE GRPLISTCD=? ");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(updateQuery.toString());		
		    
			pstmt.setString(1, bean.getGrplistnm());
			pstmt.setInt(2, bean.getOrd());
			pstmt.setString(3, bean.getUptusrid());
			pstmt.setInt(4, bean.getGrplistcd());
			
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
	 * 배포목록마스터 삭제	
	 * 해당 디테일정보 함께 삭제
	 */
	public int deleteGrpMst (int grplistcd) throws Exception {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();

			StringBuffer deleteQuery = new StringBuffer();
			deleteQuery.append("DELETE FROM GROUPMST ");
			deleteQuery.append("WHERE GRPLISTCD =?  ");			
		    pstmt = conn.prepareStatement(deleteQuery.toString());			    
			pstmt.setInt(1, grplistcd);			
			result = pstmt.executeUpdate();
			try { pstmt.close(); } catch (Exception e) {}

			deleteQuery = new StringBuffer();
			deleteQuery.append("DELETE FROM GRPLISTDTL ");
			deleteQuery.append("WHERE GRPLISTCD =?  ");			
		    pstmt = conn.prepareStatement(deleteQuery.toString());			    
			pstmt.setInt(1, grplistcd);			
			pstmt.executeUpdate();
			
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
	 * 배포목록 마스타목록	
	 */
	public List getGrpMstList(String user_gbn, String crtusrid, String crtusrgbn) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		List BeanList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			/*
			selectQuery.append("SELECT GRPLISTCD, GRPLISTNM, ORD, CRTDT, CRTUSRID, CRTUSRGBN ");			
			selectQuery.append("FROM GROUPMST ");
			if ( crtusrgbn.equals("0") == true) {
				selectQuery.append("WHERE GRPLISTCD < 1000 ");
				selectQuery.append("AND CRTUSRGBN = '0'");
			} else if ( crtusrgbn.equals("1") == true) {
				selectQuery.append("WHERE GRPLISTCD > 999 ");
				selectQuery.append("AND CRTUSRGBN = '1'");
			}
			selectQuery.append("AND CRTUSRID LIKE ? ");
			selectQuery.append("ORDER BY ORD, GRPLISTCD ");
			*/
			
			selectQuery.append(" SELECT GRPLISTCD, GRPLISTNM, ORD, A.CRTDT, A.CRTUSRID, CRTUSRGBN, C.ORGGBN  \n");
			selectQuery.append(" FROM GROUPMST A, USR B, DEPT C                                              \n");
			selectQuery.append(" WHERE A.CRTUSRID = B.USER_ID            	                                 \n");
			selectQuery.append(" AND B.DEPT_ID = C.DEPT_ID                                                   \n");
			if ( crtusrgbn.equals("0") == true) {
			selectQuery.append(" AND CRTUSRGBN = '0'														 \n");
			} else if ( crtusrgbn.equals("1") == true) {
			selectQuery.append(" AND CRTUSRGBN = '1'														 \n");
			}
			selectQuery.append(" AND A.CRTUSRID LIKE ? 														 \n");
			if(!user_gbn.equals("001")) selectQuery.append(" AND ORGGBN = '"+user_gbn+"'                     \n");
			selectQuery.append(" ORDER BY ORD, GRPLISTCD  													 \n");

		    //System.out.println("selectQuery.toString(): "  +selectQuery.toString());
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());
		    
		    pstmt.setString(1, crtusrid);
			rs = pstmt.executeQuery();
			
			BeanList =  new ArrayList();

			while (rs.next()) {
				GroupBean bean = new GroupBean();
				bean.setGrplistcd(rs.getInt("GRPLISTCD"));
				bean.setGrplistnm(rs.getString("GRPLISTNM"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				bean.setCrtusrgbn(rs.getString("CRTUSRGBN"));
				
				BeanList.add(bean);					
			}	
		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
	    	ConnectionManager.close(conn,pstmt,rs);   
	     }
	     return BeanList;
	}

	/** 
	 * 배포목록 디테일보기
	 */
	public GroupBean getGrpDtlInfo(int grplistcd, int seq) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		GroupBean bean = null;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("SELECT GRPLISTCD, SEQ, CODE, NAME ");
	       	selectQuery.append("FROM GRPLISTDTL ");
	       	selectQuery.append("WHERE GRPLISTCD = ? ");	       	
	       	selectQuery.append("  AND SEQ = ? ");
	       	
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());
		    
			pstmt.setInt(1, grplistcd);
			pstmt.setInt(2, seq);
			
			rs = pstmt.executeQuery();
						
			if ( rs.next() ){
				bean = new GroupBean();
				
				bean.setGrplistcd(rs.getInt("GRPLISTCD"));
				bean.setSeq(rs.getInt("SEQ"));
				bean.setCode(rs.getString("CODE"));
				bean.setName(rs.getString("NAME"));
			}		
		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
	    	ConnectionManager.close(conn,pstmt,rs);	   
	     }	     
	     return bean;
	}
	
	/** 
	 * 배포목록 마스터보기
	 */
	public GroupBean getGrpMstInfo(int grplistcd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		GroupBean bean = null;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("SELECT GRPLISTCD, GRPLISTNM, ORD, CRTDT, CRTUSRID, CRTUSRGBN ");
	       	selectQuery.append("FROM GROUPMST ");
	       	selectQuery.append("WHERE GRPLISTCD = ? ");	       	
	     	       	
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());
		    
			pstmt.setInt(1, grplistcd);
			
			rs = pstmt.executeQuery();
						
			if ( rs.next() ){
				bean = new GroupBean();
				
				bean.setGrplistcd(rs.getInt("GRPLISTCD"));
				bean.setGrplistnm(rs.getString("GRPLISTNM"));
				bean.setOrd(rs.getInt("ORD"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				bean.setCrtusrgbn(rs.getString("CRTUSRGBN"));
			}		
		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
	    	ConnectionManager.close(conn,pstmt,rs);	   
	     }	     
	     return bean;
	}	
	
	/** 
	 * 특정 마스터코드(LISTCD)에 대한 Detail List
	 */
	public List getGrpDtlList(int grplistcd, String codegbn, String userid) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		List subList = null;
		
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("SELECT GRPLISTCD, SEQ, CODE, NAME, CODEGBN              ,                                               \n");
			sql.append("       CASE CODEGBN WHEN '0' THEN (SELECT NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), DEPT_NAME)                \n");
			sql.append("                                   FROM DEPT WHERE DEPT_ID = G.CODE)                                    \n");
			sql.append("                    WHEN '1' THEN (SELECT NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), DEPT_NAME) || ' ' || NAME \n");
			sql.append("                                   FROM USR WHERE USER_ID = G.CODE)                                     \n");
			sql.append("                    ELSE NAME END DISPLAYNAME                                                           \n");
			sql.append("FROM GRPLISTDTL G                                                                                       \n");
			sql.append("WHERE GRPLISTCD = ?                                                                                     \n");
			sql.append("AND CODEGBN LIKE ?                                                                                      \n");
			sql.append("ORDER BY SEQ                                                                                            \n");
		    
	       	int substring = commfunction.getDeptFullNameSubstringIndex(userid);
	       	
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(sql.toString());
		    
		    pstmt.setInt(1, substring);
		    pstmt.setInt(2, substring);
		    pstmt.setInt(3, grplistcd);
		    pstmt.setString(4, codegbn);
		    
			rs = pstmt.executeQuery();
			
			subList =  new ArrayList();

			while (rs.next()) {
				GroupBean bean = new GroupBean();
				
				bean.setCode(rs.getString("CODE"));
				bean.setName(rs.getString("NAME"));	
				bean.setDisplayName(rs.getString("DISPLAYNAME"));
				bean.setSeq(rs.getInt("SEQ"));
				bean.setCodegbn(rs.getString("CODEGBN"));
		
				subList.add(bean);					
			}	
		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
	    	ConnectionManager.close(conn,pstmt,rs);	   
	     }	     
	     return subList;
	}

	/** 
	 * 배포목록마스터명 가져오기
	 */
	public String getGrpListName(int grplistcd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		String result = "";
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("SELECT GRPLISTNM FROM GROUPMST ");
	       	selectQuery.append("WHERE GRPLISTCD = ? ");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
		    
			pstmt.setInt(1, grplistcd);

			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				result = rs.getString(1);
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
	 * 배포목록 마스터 존재유무 
	 * return true : 존재함
	 * return false : 존재안함
	 */
	public boolean existedGrp(int grplistcd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("\n SELECT COUNT(*) FROM GROUPMST 	");
	       	selectQuery.append("\n  WHERE GRPLISTCD = ? 			");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
			pstmt.setInt(1, grplistcd);

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
	 * 배포목록 디데일 등록
	 * @param grplistcd
	 * @param deptList
	 * @return
	 * @throws Exception
	 */	
	public int insertGrpDtl(int grplistcd, ArrayList deptList) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int bindPos = 0;
		int cnt = 0;
		int[] ret = null;
		
		int maxSeq = 0;
		
		StringBuffer insertQuery = new StringBuffer();
		StringBuffer deleteQuery = new StringBuffer();

		deleteQuery.append("\n DELETE FROM GRPLISTDTL 	");
		deleteQuery.append("\n  WHERE GRPLISTCD = ? 	");

		insertQuery.append("\n INSERT INTO GRPLISTDTL               ");
		insertQuery.append("\n             (GRPLISTCD, SEQ,         ");
		insertQuery.append("\n              CODE, NAME, CODEGBN )	");
		insertQuery.append("\n      VALUES (?, ?,             		");
		insertQuery.append("\n              ?, ?, ?	)               ");

		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			/*기존등록된 제출부서 그룹 및 부서목록LIST삭제*/
			pstmt = con.prepareStatement(deleteQuery.toString());	
			pstmt.setInt(++bindPos, grplistcd);	

			if(pstmt.executeUpdate()>0){
				cnt++;
			}
			bindPos = 0;
			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			/*제출부서 그룹 및 부서목록LIST등록*/
			if (deptList.size() > 0){
				pstmt = con.prepareStatement(insertQuery.toString());
				
				maxSeq = getMaxSEQ(grplistcd, "1", "0");
				
				GroupBean dept = null;
				for (int i = 0 ; i < deptList.size() ; i++){
					dept = (GroupBean)deptList.get(i);
					
					pstmt.setInt(++bindPos, grplistcd);
					pstmt.setInt(++bindPos, maxSeq++);
					pstmt.setString(++bindPos, dept.getCode());
					pstmt.setString(++bindPos, dept.getName());
					pstmt.setString(++bindPos, dept.getCodegbn());
					pstmt.addBatch();
					
					bindPos = 0;
				}
				
				ret = pstmt.executeBatch();
				cnt += ret.length;
				
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
				
			}					
			con.commit();
		} catch(Exception e){
			con.rollback();
			ConnectionManager.close(con,pstmt);
			throw e;
		} finally {
			con.setAutoCommit(true);
			ConnectionManager.close(con, pstmt);
		}
		return cnt;
	}	
	
	/** 
	 * 배포목록 디테일 삭제	
	 */
	public int deleteGrpDtl (int grplistcd, int seq) throws Exception {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer deleteQuery = new StringBuffer();

			deleteQuery.append("\n DELETE FROM GRPLISTDTL 	");
			deleteQuery.append("\n  WHERE GRPLISTCD =?  	");
			deleteQuery.append("\n    AND SEQ = ?     		");
			
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(deleteQuery.toString());			    
			pstmt.setInt(1, grplistcd);
			pstmt.setInt(2, seq);
			
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
	
	public String getGrpDtlXml(int grplistcd, String userid) throws Exception {
		
		ArrayList deptView = new ArrayList();
		StringBuffer deptViewXML = new StringBuffer();
		
		try{
			deptView = (ArrayList)getGrpDtlList(grplistcd, "%", userid);
			
			if (deptView.size() > 0){
				
				GroupBean bean = null;
				for (int i = 0 ; i < deptView.size() ; i++){
					bean = (GroupBean)deptView.get(i);
					deptViewXML.append("\n	<data id=\"").append(bean.getCode()).append("\">");				
					deptViewXML.append("\n		<userdata id=\"deptId\">").append(bean.getCode()).append("</userdata>");
					deptViewXML.append("\n		<userdata id=\"grpGbn\">").append(bean.getCodegbn()).append("</userdata>");
					deptViewXML.append("\n		<userdata id=\"deptName\">").append(bean.getName()).append("</userdata>");
					deptViewXML.append("\n		<userdata id=\"deptFullName\">").append(bean.getDisplayName()).append("</userdata>");
					deptViewXML.append("\n	</data>");
					bean = null;
				}			
			}			
		}catch(Exception e){
			throw e;
		}
		return (deptViewXML.toString());
	}
	
	/** 
	 * 배포목록디테일 부서명칭 수정
	 */
	public int modifyDetailNameFormCode (String dept_id, String dept_name) throws Exception {
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();

			updateQuery.append("UPDATE GRPLISTDTL \n");
			updateQuery.append("SET NAME=? \n");
			updateQuery.append("WHERE CODE=? \n");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(updateQuery.toString());		
		    
			pstmt.setString(1, dept_name);
			pstmt.setString(2, dept_id);
			
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
}