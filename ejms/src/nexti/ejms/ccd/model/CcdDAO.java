/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 코드관리 dao
 * 설명:
 */
package nexti.ejms.ccd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;

public class CcdDAO {
	private static Logger logger = Logger.getLogger(CcdDAO.class);
	
	/**
	 * 코드명칭 가져오기
	 */
	public String getCcdName(String ccdcd, String ccdsubcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT CCDNAME ");
			selectQuery.append("  FROM CCD ");
			selectQuery.append("WHERE CCDCD = ? ");	
			selectQuery.append("  AND CCDSUBCD = ? ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, ccdcd);			
			pstmt.setString(2, ccdsubcd);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				result = rs.getString("CCDNAME");				
			}
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
	
	/** 공통코드 추가	 */
	public int insertCcd (CcdBean bean) throws Exception {
		Connection conn = null;      
		PreparedStatement pstmt = null;
		int result = 0 ;
		
		try {
			StringBuffer insertQuery = new StringBuffer();

			insertQuery.append("INSERT INTO CCD (CCDCD,   CCDSUBCD, CCDNAME,   CCDDESC, BIGO, ");
			insertQuery.append("                 CRTUSRID, CRTDT) ");
			insertQuery.append("VALUES(?, ?, ?, ?, ?,       ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD hh24:mi:ss') )");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(insertQuery.toString());	
			
			pstmt.setString(1, bean.getCcdcd());
			pstmt.setString(2, bean.getCcdsubcd());
			pstmt.setString(3, bean.getCcdname());
			pstmt.setString(4, bean.getCcddesc());
			pstmt.setString(5, bean.getBigo());
			pstmt.setString(6, bean.getCrtusrid());		
			
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
	 * 공통코드 수정	
	 */
	public int modifyCcd (CcdBean bean) throws Exception {
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();

			updateQuery.append("UPDATE CCD SET CCDNAME=?, CCDDESC=?, BIGO=?, UPTUSRID=?, UPTDT=TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') ");
			updateQuery.append("WHERE CCDCD=? AND CCDSUBCD=? ");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(updateQuery.toString());		
		    
			pstmt.setString(1, bean.getCcdname());
			pstmt.setString(2, bean.getCcddesc());
			pstmt.setString(3, bean.getBigo());
			pstmt.setString(4, bean.getUptusrid());
			pstmt.setString(5, bean.getCcdcd());
			pstmt.setString(6, bean.getCcdsubcd());
			
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
	 * 공통코드 삭제	
	 */
	public int deleteCcd (String p_ccd_cd, String p_ccd_sub_cd) throws Exception {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer deleteQuery = new StringBuffer();

			deleteQuery.append("DELETE FROM CCD ");
			deleteQuery.append("WHERE CCDCD =? AND CCDSUBCD LIKE ? ");
			
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(deleteQuery.toString());			    
			pstmt.setString(1, p_ccd_cd);
			pstmt.setString(2, p_ccd_sub_cd);
			
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
	 * 공통코드 목록 - 검색조건없음	
	 * param : gbn - 시스템용(s), 관리자용(a)구분
	 */
	public List mainCodeList (String gbn) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		List ccdBeanList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();

			selectQuery.append("SELECT CCDCD,    CCDSUBCD, CCDNAME,   CCDDESC, BIGO, ");
			selectQuery.append("       CRTUSRID, CRTDT,    UPTUSRID,  UPTDT ");
			selectQuery.append("FROM CCD ");
			selectQuery.append("WHERE SUBSTR(CCDSUBCD,1,1) = '.' ");		
			
		    if ("s".equalsIgnoreCase(gbn)) {
		    	//시스템용
		    	selectQuery.append("AND TO_NUMBER(CCDCD) <= 100 ");
		    } else if ("a".equalsIgnoreCase(gbn)) {
		    	//관리자용
		    	selectQuery.append("AND TO_NUMBER(CCDCD) > 100 ");		    	
		    }
		    
			selectQuery.append("ORDER BY LPAD(CCDCD,5,'0') ");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
			rs = pstmt.executeQuery();
			
			ccdBeanList =  new ArrayList();

			while (rs.next()) {
				CcdBean bean = new CcdBean();
				bean.setCcdcd(rs.getString("CCDCD"));
				bean.setCcdsubcd(rs.getString("CCDSUBCD"));
				bean.setCcdname(rs.getString("CCDNAME"));
				bean.setCcddesc(rs.getString("CCDDESC"));
				bean.setBigo(rs.getString("BIGO"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setUptusrid(rs.getString("UPTUSRID"));
				bean.setUptdt(rs.getString("UPTDT"));
				
				ccdBeanList.add(bean);					
			}	
		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);   
			throw e;
	     } finally {	       
	    	ConnectionManager.close(conn,pstmt,rs);   
	     }
	     return ccdBeanList;
	}

	/** 
	 * 공통코드 상세보기
	 */
	public CcdBean detailCcd(String p_ccd_cd, String p_ccd_sub_cd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		CcdBean bean = null;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("SELECT CCDCD,    CCDSUBCD, CCDNAME,   CCDDESC, BIGO, ");
	       	selectQuery.append("       CRTUSRID, CRTDT,    UPTUSRID,  UPTDT ");
	       	selectQuery.append("FROM CCD ");
	       	selectQuery.append("WHERE CCDCD = ? AND CCDSUBCD=? ");
		
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
			pstmt.setString(1, p_ccd_cd);
			pstmt.setString(2, p_ccd_sub_cd);

			rs = pstmt.executeQuery();
						
			if ( rs.next() ){
				bean = new CcdBean();
				
				bean.setCcdcd(rs.getString("CCDCD"));
				bean.setCcdsubcd(rs.getString("CCDSUBCD"));
				bean.setCcdname(rs.getString("CCDNAME"));
				bean.setCcddesc(rs.getString("CCDDESC"));
				bean.setBigo(rs.getString("BIGO"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setUptusrid(rs.getString("UPTUSRID"));
				bean.setUptdt(rs.getString("UPTDT"));
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
	 * 공통코드 존재유무 
	 * return true : 존재함
	 * return false : 존재안함
	 */
	public boolean existedCcd(String p_ccd_cd, String p_ccd_sub_cd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("SELECT COUNT(*) FROM CCD ");
	       	selectQuery.append("WHERE CCDCD = ? AND CCDSUBCD=? ");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
			pstmt.setString(1, p_ccd_cd);
			pstmt.setString(2, p_ccd_sub_cd);

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
	 * 특정 마스터코드(CCD_CD)에 대한 SUB List
	 */
	public List subCodeList(String ccd_cd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		List subList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();

		    selectQuery.append("SELECT CCDSUBCD, CCDNAME, CCDDESC,   BIGO, ");
		    selectQuery.append("       CRTUSRID,   CRTDT,  UPTUSRID, UPTDT ");
		    selectQuery.append("FROM CCD ");
		    selectQuery.append("WHERE CCDCD = ? ");
		    selectQuery.append("AND SUBSTR(CCDSUBCD,1,1) <> '.' ");
		    selectQuery.append("ORDER BY LPAD(CCDSUBCD,5,'0')");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());
		    
		    pstmt.setString(1, ccd_cd);
		    
			rs = pstmt.executeQuery();
			
			subList =  new ArrayList();

			while (rs.next()) {
				CcdBean bean = new CcdBean();
				
				bean.setCcdsubcd(rs.getString("CCDSUBCD"));				
				bean.setCcdname(rs.getString("CCDNAME"));
				bean.setCcddesc(rs.getString("CCDDESC"));
				bean.setBigo(rs.getString("BIGO"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setUptusrid(rs.getString("UPTUSRID"));
				bean.setUptdt(rs.getString("UPTDT"));
				
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
	 * 주코드명 가져오기
	 */
	public String getCcd_Name(String p_ccd_cd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		String result = "";
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("SELECT CCDNAME FROM CCD ");
	       	selectQuery.append("WHERE CCDCD = ? AND CCDSUBCD='...' ");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
			pstmt.setString(1, p_ccd_cd);

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
	 * 부코드명 가져오기	 */
	public String getCcd_SubName(String p_ccd_cd, String p_sub_ccd_cd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		String result = "";
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("SELECT CCDNAME FROM CCD ");
	       	selectQuery.append("WHERE CCDCD = ? AND CCDSUBCD = ? ");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, p_ccd_cd);
			pstmt.setString(2, p_sub_ccd_cd);

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
}
