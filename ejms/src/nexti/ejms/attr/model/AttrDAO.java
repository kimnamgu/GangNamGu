/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 속성목록관리 dao
 * 설명:
 */
package nexti.ejms.attr.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.util.Utils;

public class AttrDAO {
	private static Logger logger = Logger.getLogger(AttrDAO.class);
	
	/**
	 * 속성목록 새목록코드
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public int getNextListcd() throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int result = -1;
		
		try {
			String query;
			
			query = "SELECT NVL(MAX(TO_NUMBER(LISTCD)), 999) + 1 " +
					"FROM ATTLISTMST " +
					"WHERE TO_NUMBER(LISTCD) > 999 ";
			
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}	
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 속성목록 새일련번호
	 * @param conn
	 * @param listcd
	 * @return
	 * @throws Exception
	 */
	public int getNextListcdSeq(String listcd) throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int result = -1;
		
		try {
			String query;
			
			query = "SELECT NVL(MAX(SEQ), 0) + 1 " +
					"FROM ATTLISTDTL " +
					"WHERE LISTCD LIKE ? ";
			
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, listcd);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}	
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 속성목록 가져오기
	 * @return
	 * @throws Exception
	 */	
	public AttrBean getFormatAttList(String listcd) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		AttrBean result = null;
		ArrayList listdtl = null;
		
		try {
			String sql =
				"SELECT ALM.LISTCD, LISTNM, CRTDT, CRTUSRID, CRTUSRGBN, \n" +
				"       SEQ, LISTDTLNM, ATTR_DESC \n" +
				"FROM ATTLISTMST ALM, ATTLISTDTL ALD \n" +
				"WHERE ALM.LISTCD = ALD.LISTCD(+) \n" +
				"AND ALM.LISTCD LIKE ? \n" +
				"ORDER BY CRTUSRGBN, LISTNM, SEQ, LISTCD \n";
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, listcd);
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {				
				if ( result == null ) {
					result = new AttrBean();
					result.setListcd(rs.getString("LISTCD"));
					result.setListnm(rs.getString("LISTNM"));
					result.setCrtdt(rs.getString("CRTDT"));
					result.setCrtusrid(rs.getString("CRTUSRID"));
					result.setCrtusrgbn(rs.getString("CRTUSRGBN"));
					
					listdtl = new ArrayList();
					AttrBean attdtlbean = new AttrBean();
					attdtlbean.setSeq(rs.getInt("SEQ"));
					attdtlbean.setListdtlnm(Utils.nullToEmptyString(rs.getString("LISTDTLNM")));
					listdtl.add(attdtlbean);
				} else if ( result != null ) {
					AttrBean attdtlbean = new AttrBean();
					attdtlbean.setSeq(rs.getInt("SEQ"));
					attdtlbean.setListdtlnm(Utils.nullToEmptyString(rs.getString("LISTDTLNM")));
					listdtl.add(attdtlbean);
				}
			}
			
			if ( result != null ) {
				result.setListdtlList(listdtl);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 속성목록 리스트 가져오기
	 * @return
	 * @throws Exception
	 */	
	public List getFormatAttList(String crtusrid, String crtusrgbn) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List result = null;
		AttrBean attbean = null;
		ArrayList listdtl = null;
		String listcd = "";
		
		try {
			String sql =
				"SELECT ALM.LISTCD, LISTNM, CRTDT, CRTUSRID, CRTUSRGBN, \n" +
				"       SEQ, LISTDTLNM, ATTR_DESC \n" +
				"FROM ATTLISTMST ALM, ATTLISTDTL ALD \n" +
				"WHERE ALM.LISTCD = ALD.LISTCD(+) \n" +
				"AND CRTUSRID LIKE ? \n" +
				"AND CRTUSRGBN LIKE ? \n" +
				"ORDER BY CRTUSRGBN, LISTNM, SEQ, LISTCD \n";
			
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, crtusrid);
			pstmt.setString(2, crtusrgbn);
			rs = pstmt.executeQuery();
			
			result = new ArrayList();
			
			while ( rs.next() ) {
				if ( listcd.equals(rs.getString("LISTCD")) == false && attbean != null ) {
					attbean.setListdtlList(listdtl);
					result.add(attbean);
				}
				
				if ( listcd.equals(rs.getString("LISTCD")) == false ) {
					attbean = new AttrBean();
					attbean.setListcd(rs.getString("LISTCD"));
					attbean.setListnm(rs.getString("LISTNM"));
					attbean.setCrtdt(rs.getString("CRTDT"));
					attbean.setCrtusrid(rs.getString("CRTUSRID"));
					attbean.setCrtusrgbn(rs.getString("CRTUSRGBN"));
					listcd = attbean.getListcd();
					
					listdtl = new ArrayList();
					AttrBean attdtlbean = new AttrBean();
					attdtlbean.setSeq(rs.getInt("SEQ"));
					attdtlbean.setListdtlnm(Utils.nullToEmptyString(rs.getString("LISTDTLNM")));
					listdtl.add(attdtlbean);
				} else if ( listcd.equals(rs.getString("LISTCD")) == true ) {
					AttrBean attdtlbean = new AttrBean();
					attdtlbean.setSeq(rs.getInt("SEQ"));
					attdtlbean.setListdtlnm(Utils.nullToEmptyString(rs.getString("LISTDTLNM")));
					listdtl.add(attdtlbean);
				}
			}
			
			if ( attbean != null ) {
				attbean.setListdtlList(listdtl);
				result.add(attbean);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	/** 
	 * 속성목록 마스터 추가	 
	 */
	public int insertAttrMst (AttrBean bean) throws Exception {
		Connection conn = null;      
		PreparedStatement pstmt = null;
		int result = 0 ;
		
		try {
			StringBuffer insertQuery = new StringBuffer();

			insertQuery.append("INSERT INTO ATTLISTMST (LISTCD, LISTNM, CRTDT, CRTUSRID, CRTUSRGBN) ");			
			insertQuery.append("VALUES(?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD hh24:mi:ss'), ?, ?)");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(insertQuery.toString());	
			
			pstmt.setString(1, bean.getListcd());
			pstmt.setString(2, bean.getListnm());
			pstmt.setString(3, bean.getCrtusrid());
			pstmt.setString(4, bean.getCrtusrgbn());
			
			result = pstmt.executeUpdate();			
		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn, pstmt);
			throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt); 	   
	     }
	     return result;
	}
	
	/** 
	 * 속성목록 디테일추가	 
	 */
	public int insertAttrDtl (AttrBean bean) throws Exception {
		Connection conn = null;      
		PreparedStatement pstmt = null;
		int result = 0 ;
		
		try {
			StringBuffer insertQuery = new StringBuffer();

			insertQuery.append("INSERT INTO ATTLISTDTL (LISTCD, SEQ, LISTDTLNM, ATTR_DESC) ");		
			insertQuery.append("VALUES(?, ?, ?, ? )");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(insertQuery.toString());	
			
			pstmt.setString(1, bean.getListcd());
			pstmt.setInt(2, bean.getSeq());
			pstmt.setString(3, bean.getListdtlnm());		
			pstmt.setString(4, bean.getAttr_desc());
			
			result = pstmt.executeUpdate();			
		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn, pstmt);
			throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt); 	   
	     }
	     return result;
	}

	/** 
	 * 속성목록 마스터 수정
	 */
	public int modifyAttrMst (AttrBean bean) throws Exception {
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();
			
			updateQuery.append("UPDATE ATTLISTMST SET LISTNM=? ");
			updateQuery.append("WHERE LISTCD = ? ");		
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(updateQuery.toString());		
		    
			pstmt.setString(1, bean.getListnm());
			pstmt.setString(2, bean.getListcd());			
			
			result = pstmt.executeUpdate();			
		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn, pstmt);
			throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt);   
	     }	     
	     return result;
	}
	
	/** 
	 * 속성목록 디테일 수정
	 */
	public int modifyAttrDtl (AttrBean bean) throws Exception {
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();
			
			updateQuery.append("UPDATE ATTLISTDTL SET LISTDTLNM = ?, ATTR_DESC = ? ");
			updateQuery.append("WHERE LISTCD = ? ");	
			updateQuery.append("  AND SEQ = ? ");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(updateQuery.toString());		
		    
			pstmt.setString(1, bean.getListdtlnm());
			pstmt.setString(2, bean.getAttr_desc());
			pstmt.setString(3, bean.getListcd());
			pstmt.setInt(4, bean.getSeq());
			
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
	 * 속성목록 마스터 삭제
	 * 해당 디테일정보 함께 삭제
	 */
	public int deleteAttrMst (String p_listcd) throws Exception {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();

			StringBuffer deleteQuery = new StringBuffer();
			deleteQuery.append("DELETE FROM ATTLISTMST ");
			deleteQuery.append("WHERE LISTCD =? ");
		    pstmt = conn.prepareStatement(deleteQuery.toString());			    
			pstmt.setString(1, p_listcd);					
			result = pstmt.executeUpdate();
			try { pstmt.close(); } catch (Exception e) {}

			deleteQuery = new StringBuffer();
			deleteQuery.append("DELETE FROM ATTLISTDTL ");
			deleteQuery.append("WHERE LISTCD =? ");
		    pstmt = conn.prepareStatement(deleteQuery.toString());			    
			pstmt.setString(1, p_listcd);					
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
	 * 속성목록 디테일 삭제
	 */
	public int deleteAttrDtl (String p_listcd, int p_seq) throws Exception {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer deleteQuery = new StringBuffer();

			deleteQuery.append("DELETE FROM ATTLISTDTL ");
			deleteQuery.append("WHERE LISTCD =? AND SEQ = ?");
			
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(deleteQuery.toString());			    
			pstmt.setString(1, p_listcd);
			pstmt.setInt(2, p_seq);
			
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
	 * 속성목록 - 검색조건없음	
	 * param : gbn - 시스템용(s), 관리자용(a)구분
	 */
	public List attrMstList (String gbn) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		List ccdBeanList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();

			selectQuery.append("SELECT LISTCD, LISTNM, CRTDT, CRTUSRID, CRTUSRGBN ");			
			selectQuery.append("FROM ATTLISTMST ");				
			
		    if ("s".equalsIgnoreCase(gbn)) {
		    	//시스템용
		    	selectQuery.append("WHERE TO_NUMBER(LISTCD) <= 100 ");
		    } else if ("a".equalsIgnoreCase(gbn)) {
		    	//관리자용
		    	selectQuery.append("WHERE TO_NUMBER(LISTCD) BETWEEN 101 AND 999 ");		    	
		    }
		    
		    selectQuery.append("AND CRTUSRGBN = '0' ");
			selectQuery.append("ORDER BY LPAD(LISTCD, 5, '0') ");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
			rs = pstmt.executeQuery();
			
			ccdBeanList =  new ArrayList();

			while (rs.next()) {
				AttrBean bean = new AttrBean();
				bean.setListcd(rs.getString("LISTCD"));
				bean.setListnm(rs.getString("LISTNM"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				bean.setCrtusrgbn(rs.getString("CRTUSRGBN"));
				
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
	 * 속성목록 디테일보기
	 */
	public AttrBean attrDtlInfo(String p_listcd, int p_seq) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		AttrBean bean = null;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("SELECT LISTCD, SEQ, LISTDTLNM, ATTR_DESC ");
	       	selectQuery.append("FROM ATTLISTDTL ");
	       	selectQuery.append("WHERE LISTCD = ? ");	       	
	       	selectQuery.append("  AND SEQ = ? ");
	       	
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());
		    
			pstmt.setString(1, p_listcd);
			pstmt.setInt(2, p_seq);
			
			rs = pstmt.executeQuery();
						
			if ( rs.next() ){
				bean = new AttrBean();
				
				bean.setListcd(rs.getString("LISTCD"));
				bean.setSeq(rs.getInt("SEQ"));
				bean.setListdtlnm(rs.getString("LISTDTLNM"));
				bean.setAttr_desc(rs.getString("ATTR_DESC"));
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
	 * 속성목록 마스터보기
	 */
	public AttrBean attrMstInfo(String p_listcd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		AttrBean bean = null;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("SELECT LISTCD, LISTNM, CRTDT, CRTUSRID, CRTUSRGBN ");
	       	selectQuery.append("FROM ATTLISTMST ");
	       	selectQuery.append("WHERE LISTCD = ? ");	       	
	     	       	
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());
		    
			pstmt.setString(1, p_listcd);
			
			rs = pstmt.executeQuery();
						
			if ( rs.next() ){
				bean = new AttrBean();
				
				bean.setListcd(rs.getString("LISTCD"));
				bean.setListnm(rs.getString("LISTNM"));
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
	 * 마스터 코드 존재유무 
	 * return true : 존재함
	 * return false : 존재안함
	 */
	public boolean existedMst(String p_listcd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("SELECT COUNT(*) ");
	       	selectQuery.append("FROM ATTLISTMST ");
	       	selectQuery.append("WHERE LISTCD = ? ");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
			pstmt.setString(1, p_listcd);			

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
	 * 디테일 코드 존재유무 
	 * return true : 존재함
	 * return false : 존재안함
	 */
	public boolean existedDtl(String p_listcd, int seq) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("SELECT COUNT(*) ");
	       	selectQuery.append("FROM ATTLISTDTL ");
	       	selectQuery.append("WHERE LISTCD = ? ");
	       	selectQuery.append("  AND SEQ = ? ");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
			pstmt.setString(1, p_listcd);	
			pstmt.setInt(2, seq);

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
	 * 특정 마스터코드(LISTCD)에 대한 Detail List
	 */
	public List attrDtlList(String p_listcd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		List subList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();

			selectQuery.append("SELECT LISTCD, SEQ, LISTDTLNM, ATTR_DESC ");
	       	selectQuery.append("FROM ATTLISTDTL ");
	       	selectQuery.append("WHERE LISTCD = ? ");
	       	selectQuery.append("ORDER BY SEQ ");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());
		    
		    pstmt.setString(1, p_listcd);
		    
			rs = pstmt.executeQuery();
			
			subList =  new ArrayList();

			while (rs.next()) {
				AttrBean bean = new AttrBean();
				
				bean.setListcd(rs.getString("LISTCD"));
				bean.setSeq(rs.getInt("SEQ"));
				bean.setListdtlnm(rs.getString("LISTDTLNM"));	
				bean.setAttr_desc(rs.getString("ATTR_DESC"));
				
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
	 * 속성마스터명 가져오기
	 */
	public String getMst_Name(String p_listcd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		String result = "";
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("SELECT LISTNM FROM ATTLISTMST ");
	       	selectQuery.append("WHERE LISTCD = ? ");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
		    
			pstmt.setString(1, p_listcd);

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
	 * 속성 디테일명 가져오기*/
	public String getDtl_Name(String p_listcd, int seq) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		String result = "";
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append("SELECT LISTDTLNM FROM ATTLISTDTL ");
	       	selectQuery.append("WHERE LISTCD = ? " +
	       			           "  AND SEQ = ? ");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
		    
			pstmt.setString(1, p_listcd);
			pstmt.setInt(2, seq);

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
