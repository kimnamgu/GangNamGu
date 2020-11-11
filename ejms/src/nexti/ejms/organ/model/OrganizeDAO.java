/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 dao
 * 설명:
 */
package nexti.ejms.organ.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.user.model.UserBean;

public class OrganizeDAO {
	
	private static Logger logger = Logger.getLogger(OrganizeDAO.class);
	
	/**
	 * 관리자 여부
	 * @throws Exception 
	 */
	public String isSysMgr(String user_id) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT NVL(MGRYN, 'N') MGRYN 	\n");
			selectQuery.append(" FROM USR 						\n");
			selectQuery.append(" WHERE UPPER(USER_ID) = ? 		\n");
		
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, user_id.toUpperCase());					
						
			rs = pstmt.executeQuery();	
			
			if ( rs.next() ){
				result = rs.getString("MGRYN");
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
	 * 관리자 정보 가져오기(1명만 불러옴, 없을 경우 일반 사용자)
	 * @throws Exception 
	 */
	public String getManager() throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT USER_ID FROM USR U, DEPT D \n");
			sql.append("WHERE U.DEPT_ID = D.DEPT_ID AND ORGGBN = '001' \n");
			sql.append("AND ROWNUM = 1 AND MGRYN = 'Y' \n");
			sql.append("UNION ALL \n");
			sql.append("SELECT USER_ID FROM USR U, DEPT D \n");
			sql.append("WHERE U.DEPT_ID = D.DEPT_ID AND ORGGBN = '001' \n");
			sql.append("AND ROWNUM = 1 \n");
			sql.append("AND (SELECT COUNT(*) FROM USR WHERE MGRYN = 'Y') = 0 \n");
//			System.out.println("sql : "+sql.toString());
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());				
						
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {
				result = rs.getString(1);
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
	 * 관리자 정보 가져오기
	 * @throws Exception 
	 */
	public List managerList(String user_gbn, String rep_dept, String user_id) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List mgrlist = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			String root_id = appInfo.getRootid();
			selectQuery.append(" SELECT USER_ID, USER_NAME, TRIM(SUBSTR(D.DEPT_FULLNAME, DEPT_LENGTH+1, LENGTH(D.DEPT_FULLNAME))) AS DEPT_FULLNAME \n");
			selectQuery.append(" FROM USR A, DEPT D, \n");
			selectQuery.append(" (SELECT LENGTH(DEPT_FULLNAME) AS DEPT_LENGTH FROM DEPT WHERE DEPT_ID = '"+root_id+"')B \n");
			selectQuery.append(" WHERE A.DEPT_ID = D.DEPT_ID(+) \n");
			selectQuery.append(" AND MGRYN = 'Y' \n");
			selectQuery.append(" AND A.DEPT_ID IN (SELECT DEPT_ID \n");
			selectQuery.append("                   FROM DEPT \n");
			selectQuery.append("                   START WITH DEPT_ID = '"+rep_dept+"' \n");
			selectQuery.append("                   CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID) \n");
			if ( !user_gbn.equals("001") ) {
				selectQuery.append(" AND ORGGBN = '"+user_gbn+"' \n");
			}
			selectQuery.append("ORDER BY ORGGBN, DEPT_DEPTH, DEPT_RANK, USR_RANK \n");           
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());				
						
			rs = pstmt.executeQuery();					
			
			mgrlist = new ArrayList();
			
			while ( rs.next() ){
				UserBean user = new UserBean();
				
				user.setUser_id(rs.getString("USER_ID")); 
				user.setUser_name(rs.getString("USER_NAME"));
				user.setDept_fullname(rs.getString("DEPT_FULLNAME"));	
				
				mgrlist.add(user);
			}			
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return mgrlist;
	}	
	
	/**
	 * 관리자 정보 가져오기
	 * @throws Exception 
	 */
	public int existManagerList(String user) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT USER_ID, MGRYN 	\n");		
			selectQuery.append(" FROM USR				\n");	
			selectQuery.append(" WHERE USER_ID = ? 		\n");	
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());				
			
			pstmt.setString(1, user);
			rs = pstmt.executeQuery();			
			
			if (rs.next()) {
				String mgryn = rs.getString("MGRYN");
				if (mgryn.equals("1")) {
					result = 2;
				} else {
					result = 1;
				}
			} else {
				result = 0;
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
	 * 관리자 삭제	
	 * 관리자여부 필드를 일반사용자로 업데이트
	 * @throws Exception 
	 */
	public int deleteMgr (String userid) throws Exception{
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append(" UPDATE USR SET MGRYN = 'N' \n");
			updateQuery.append(" WHERE USER_ID = ? 			\n");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(updateQuery.toString());		
		    
			pstmt.setString(1, userid);		
			
			result = pstmt.executeUpdate();			
			
		 } catch (Exception e) {
			 logger.error("ERROR", e);
			 ConnectionManager.close(conn,pstmt);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt);   
	     }	     
	     return result;
	}
	
	/** 
	 * 관리자 지정	
	 * 관리자여부 필드를 관리자로 업데이트
	 * @throws Exception 
	 */
	public int insertMgr (String userid) throws Exception{
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append(" UPDATE USR SET MGRYN = 'Y' \n");
			updateQuery.append(" WHERE USER_ID = ? 			\n");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(updateQuery.toString());		
		    
			pstmt.setString(1, userid);		
			
			result = pstmt.executeUpdate();			
			
		 } catch (Exception e) {
			 logger.error("ERROR", e);
	    	 ConnectionManager.close(conn,pstmt);   
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt);   
	     }	     
	     return result;
	}
	
}
