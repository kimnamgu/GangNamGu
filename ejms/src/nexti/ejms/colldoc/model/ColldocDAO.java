/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서 dao
 * 설명:
 */
package nexti.ejms.colldoc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.commtreat.model.CommTreatBean;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.colldoc.model.ColldocBean;
import nexti.ejms.formatBook.model.FileBookBean;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.Utils;

public class ColldocDAO {
	
	private static Logger logger = Logger.getLogger(ColldocDAO.class);
	
	/**
	 * 작성중단된 시스템문서번호 목록
	 * @return String[] 시스템문서번호목
	 * @throws Exception 
	 */
	public String[] getListCancelColldoc() throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String[] result = null;
		
		try {
			String sql = 
				"SELECT SYSDOCNO " +
				"FROM DOCMST " +
		        "WHERE DELYN = '1' " +
		        "  AND DOCSTATE = '01' ";

			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			ArrayList arr = new ArrayList();
			
			while(rs.next()) {
				arr.add(new String(Integer.toString(rs.getInt(1), 10)));
			}
			
			if(arr.size() != 0) {
				result = new String[arr.size()];
				result = (String[])arr.toArray(result);
			}
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt);   
			throw e;
		} finally {	       
			ConnectionManager.close(conn, pstmt);
		}
		
		return result;
	}
	
	/**
	 * 목록에서 보이기/안보이기 설정
	 * @param Connection conn
	 * @param int sysdocno
	 * @return int 취합문서개수
	 * @throws Exception 
	 */
	public int setDelyn(Connection conn, int sysdocno, int delyn) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			String sql = 
				"UPDATE DOCMST " +
				"SET DELYN = ? " +
				"WHERE SYSDOCNO = ? ";
			
			pstmt =	conn.prepareStatement(sql);
			
			pstmt.setInt(1, delyn);
			pstmt.setInt(2, sysdocno);
			
			result = pstmt.executeUpdate();			
		} catch (Exception e) {
			logger.error("ERROR", e);
			try {pstmt.close();} catch(Exception e2) {}
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * 취합문서개수 가져오기
	 * @param String user_id
	 * @param String searchvalue
	 * @return int 취합문서개수
	 * @throws Exception 
	 */
	public int getCountColldoc(String user_id, String initentry, String isSysMgr, String searchvalue, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int totalcount = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT COUNT(*) FROM DOCMST \n");
			sql.append("WHERE DELYN = '0' \n");
			sql.append("AND DOCTITLE LIKE ? \n");
			if(initentry.equals("first")){
				sql.append("AND CHRGUSRCD = ? \n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) sql.append("AND COLDEPTCD LIKE ? \n");
					if( !"".equals(sch_userid) ) sql.append("AND CHRGUSRCD LIKE ? \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) sql.append("AND COLDEPTNM LIKE ? \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) sql.append("AND CHRGUSRNM LIKE ? \n");
				}else{
					sql.append("AND CHRGUSRCD = ? \n");
				}
			}
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(sql.toString());
			
			int idx = 0;
			pstmt.setString(++idx, "%" + searchvalue + "%");
			if(initentry.equals("first")){
				pstmt.setString(++idx, user_id);
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) pstmt.setString(++idx, "%"+ sch_deptcd +"%");
					if( !"".equals(sch_userid) ) pstmt.setString(++idx, "%"+ sch_userid +"%");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) pstmt.setString(++idx, "%"+ sch_deptnm +"%");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) pstmt.setString(++idx, "%"+ sch_usernm +"%");
				}else{
					pstmt.setString(++idx, user_id);
				}
			}
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {
				totalcount = rs.getInt(1);
			}
		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return totalcount;
	}
	
	/**
	 * 취합문서의 양식개수 가져오기
	 * @param int sysdocno
	 * @return int 양식개수
	 * @throws Exception 
	 */
	public int getCountFormat(int sysdocno) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        int count = 0;
        
        try {
        	String query;
        	
        	query = "SELECT COUNT(*) " +
        			"FROM FORMMST " +
        			"WHERE SYSDOCNO = ? ";
        	
        	conn = ConnectionManager.getConnection();
        	
        	pstmt = conn.prepareStatement(query);
        	
        	pstmt.setInt(1, sysdocno);
        	
        	rs = pstmt.executeQuery();
        	
        	if(rs.next())
        		count = rs.getInt(1);
        } catch(Exception e) {
        	logger.error("ERROR", e);
        	ConnectionManager.close(conn,pstmt,rs);
        	throw e;
	    } finally {	       
	    	ConnectionManager.close(conn, pstmt, rs);        	
        }
	    
	    return count;
	}
	
	/**
	 * 최근취합문서 목록가져오기
	 * @param String user_id
	 * @param String searchvalue
	 * @param int start
	 * @param int end
	 * @return List 취합문서리스트(ArrayList)
	 * @throws Exception 
	 */
	public List getListColldoc(String user_id, String initentry, String isSysMgr, String searchvalue, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        List list = null;
		int bindPos = 0;
		
		try {
			/*
			query = "SELECT (CNT-SEQ+1) BUNHO, SYSDOCNO, DOCTITLE, CCDNAME, CRTDT, CHRGUSRNM " +
					"FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* " +
					"      FROM (SELECT ROWNUM SEQ, A2.* " +
					"            FROM (SELECT A.SYSDOCNO, A.DOCTITLE, B.CCDNAME, A.CHRGUSRNM, " +
					"                         TO_CHAR(TO_DATE(A.CRTDT, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY\"년 \"MM\"월 \"DD\"일\"') \"CRTDT\" " +
					"                  FROM DOCMST A, CCD B " +
					"                  WHERE A.DOCGBN = B.CCDSUBCD " + 
					"                    AND B.CCDCD = '001' " +
					"                    AND A.DELYN = '0' " +
					"                    AND A.DOCTITLE LIKE ? " +
					"                    AND A.CHRGUSRCD LIKE ? " +
					"                  ORDER BY A.CRTDT DESC) A2) A1) " +
					"WHERE SEQ BETWEEN ? AND ? ";
			*/
			
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT (CNT-SEQ+1) BUNHO, SYSDOCNO, DOCTITLE, CCDNAME, CRTDT, CHRGUSRCD, CHRGUSRNM                         \n");
			selectQuery.append(" FROM (                                                                                                     \n");
			selectQuery.append("   SELECT (MAX(SEQ)OVER()) CNT, A1.*                                                                        \n");
			selectQuery.append("   FROM (                                                                                                   \n");
			selectQuery.append("     SELECT ROWNUM SEQ, A2.*                                                                                \n");
			selectQuery.append("     FROM (                                                                                                 \n");
			selectQuery.append("       SELECT A.SYSDOCNO, A.DOCTITLE, B.CCDNAME, A.CHRGUSRCD, A.CHRGUSRNM,                                  \n");
			selectQuery.append("              TO_CHAR(TO_DATE(A.CRTDT, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY\"년 \"MM\"월 \"DD\"일\"') \"CRTDT\"    \n");
			selectQuery.append("       FROM DOCMST A, CCD B                                                                                 \n");
			selectQuery.append("       WHERE A.DOCGBN = B.CCDSUBCD                                                                          \n");
			selectQuery.append("       AND B.CCDCD = '001'                                                                                  \n");
			selectQuery.append("       AND A.DELYN = '0'                                                                                    \n");
			selectQuery.append("       AND A.DOCTITLE LIKE ?                                                                                \n");
			
			if(initentry.equals("first")){
				selectQuery.append("       AND A.CHRGUSRCD = ?								 												\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) selectQuery.append("\n  AND A.COLDEPTCD LIKE ?                                             \n");
					if( !"".equals(sch_userid) ) selectQuery.append("\n  AND A.CHRGUSRCD LIKE ?                                             \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) selectQuery.append("\n  AND A.COLDEPTNM LIKE ?                    \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) selectQuery.append("\n  AND A.CHRGUSRNM LIKE ?                    \n");
				}else{
					selectQuery.append("\n       AND A.CHRGUSRCD = ? 								 							            \n");
				}
			}
			selectQuery.append("       ORDER BY A.CRTDT DESC, A.UPTDT DESC) A2) A1)                                                         \n");
			selectQuery.append(" WHERE SEQ BETWEEN ? AND ?                                                                                  \n");
			
			
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(selectQuery.toString());
			
			pstmt.setString(++bindPos, "%" + searchvalue + "%");
			
			if(initentry.equals("first")){
				pstmt.setString(++bindPos, user_id);
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) pstmt.setString(++bindPos, "%"+ sch_deptcd +"%");
					if( !"".equals(sch_userid) ) pstmt.setString(++bindPos, "%"+ sch_userid +"%");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) pstmt.setString(++bindPos, "%"+ sch_deptnm +"%");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) pstmt.setString(++bindPos, "%"+ sch_usernm +"%");
				}else{
					pstmt.setString(++bindPos, user_id);
				}
			}
			pstmt.setInt(++bindPos, start);
			pstmt.setInt(++bindPos, end);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while(rs.next()) {
				ColldocBean bean = new ColldocBean();
				
				bean.setSeqno(rs.getInt("BUNHO"));
				bean.setSysdocno(rs.getInt("SYSDOCNO"));
				bean.setDoctitle(rs.getString("DOCTITLE"));
				bean.setDocgbn(rs.getString("CCDNAME"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setChrgusrcd(rs.getString("CHRGUSRCD"));
				bean.setChrgusrnm(rs.getString("CHRGUSRNM"));		
				
				list.add(bean);
			}
		 } catch(Exception e) {
			 logger.error("ERROR",e);
			 ConnectionManager.close(conn,pstmt,rs);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn, pstmt, rs);
	     }
	     
		return list;		
	}
	
	/**
	 * 취합양식자료목록 가져오기
	 * @param Connection conn
	 * @param int sysdocno
	 * @return List 취합양식자료목록(ArrayList)
	 * @throws Exception 
	 */
	public List getListFormat(Connection conn, int sysdocno) throws Exception {

		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		List list = null;
		
		try {
			String query;
			
			query = "SELECT ROWNUM, FORMSEQ, FORMTITLE, FORMKIND, CCDNAME " +
					"FROM (SELECT A.FORMSEQ, A.FORMTITLE, A.FORMKIND, B.CCDNAME " +
					"      FROM FORMMST A, CCD B " +
					"      WHERE B.CCDCD = '002' " +
					"        AND A.FORMKIND = B.CCDSUBCD " +
					"        AND SYSDOCNO = ? " +
					"      ORDER BY ORD ASC) ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while(rs.next()) {
				ColldocBean cdbean = new ColldocBean();
				
				cdbean.setSeqno(rs.getInt("ROWNUM"));
				cdbean.setFormseq(rs.getInt("FORMSEQ"));
				cdbean.setFormtitle(rs.getString("FORMTITLE"));
				cdbean.setFormkind(rs.getString("FORMKIND"));
				cdbean.setFormkindname(rs.getString("CCDNAME"));
			
				list.add(cdbean);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			try {rs.close();} catch(Exception ex) {}
			try {pstmt.close();} catch(Exception ex) {}
			throw e;
		} finally {	       
			try {rs.close();} catch(Exception ex) {}
			try {pstmt.close();} catch(Exception ex) {}
		}
	
		return list;
	}
	
	/**
	 * 취합문서목록에서 선택한 문서 보기
	 * @param int sysdocno
	 * @return ColldocBean 취합문서데이터
	 * @throws Exception 
	 */
	public ColldocBean getColldoc(int sysdocno) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ColldocBean bean = null;
		
		try {
			String query;
			StringBuffer sancusrnm1, sancusrnm2;
			
			query = "SELECT SANCUSRNM " +
					"FROM SANCCOL " +
					"WHERE GUBUN = '1' " +
					"  AND SYSDOCNO = ? ";
			
			conn = ConnectionManager.getConnection();

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			
			sancusrnm1 = new StringBuffer();
			
			while(rs.next()) 
				sancusrnm1.append(rs.getString("SANCUSRNM") + ",");
			
			if(sancusrnm1.length() != 0)
				sancusrnm1.deleteCharAt(sancusrnm1.length()-1);
			
			try {rs.close();} catch(Exception e) {}
			try {pstmt.close();} catch(Exception e) {}
			
			query = "SELECT SANCUSRNM " +
					"FROM SANCCOL " +
					"WHERE GUBUN = '2' " +
					"  AND SYSDOCNO = ? ";
			
			pstmt = conn.prepareStatement(query);
		
			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			
			sancusrnm2 = new StringBuffer();
			
			while(rs.next()) 
				sancusrnm2.append(rs.getString("SANCUSRNM") + ",");
			
			if(sancusrnm2.length() != 0)				
				sancusrnm2.deleteCharAt(sancusrnm2.length()-1);
			
			try {rs.close();} catch(Exception e) {}
			try {pstmt.close();} catch(Exception e) {}
			
			query = "SELECT DM.SYSDOCNO, DOCNO, DOCTITLE, DOCGBN, BASICDATE, SUBMITDATE, BASIS, SUMMARY,  ENDCOMMENT, " +
					"       SANCRULE, DOCSTATE, DELIVERYDT, TGTDEPTNM, COLDEPTCD, COLDEPTNM, CHRGUNITCD, CHRGUNITNM, " +
					"       CHRGUSRCD, CHRGUSRNM, OPENDT, SEARCHKEY, DELYN, OPENINPUT, CRTDT, CRTUSRID, UPTDT, UPTUSRID, ENDDT, " +
					"       SUBSTR(ENDDT, 1, 10) ENDDT_DATE, " +
					"       SUBSTR(ENDDT, 12, 2) ENDDT_HOUR, " +
					"       SUBSTR(ENDDT, 15, 2) ENDDT_MIN, " +
					"       FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD " +
					"FROM DOCMST DM, DOCMSTFILE DMF " +
					"WHERE DM.SYSDOCNO = DMF.SYSDOCNO(+)" +
					"AND DM.SYSDOCNO = ? ";
			
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {		
				bean = new ColldocBean();
				
				bean.setSysdocno(rs.getInt("SYSDOCNO"));
				bean.setDocno(rs.getString("DOCNO"));
				bean.setDoctitle(rs.getString("DOCTITLE"));
				bean.setDocgbn(rs.getString("DOCGBN"));
				bean.setBasicdate(rs.getString("BASICDATE"));
				bean.setSubmitdate(rs.getString("SUBMITDATE"));
				bean.setBasis(rs.getString("BASIS"));
				bean.setSummary(rs.getString("SUMMARY"));
				bean.setEndcomment(rs.getString("ENDCOMMENT"));
				bean.setSancrule(rs.getString("SANCRULE"));
				bean.setDocstate(rs.getString("DOCSTATE"));
				bean.setDeliverydt(rs.getString("DELIVERYDT"));
				bean.setTgtdeptnm(rs.getString("TGTDEPTNM"));
				bean.setColdeptcd(rs.getString("COLDEPTCD"));
				bean.setColdeptnm(rs.getString("COLDEPTNM"));
				bean.setChrgunitcd(rs.getInt("CHRGUNITCD"));
				bean.setChrgunitnm(rs.getString("CHRGUNITNM"));
				bean.setChrgusrcd(rs.getString("CHRGUSRCD"));
				bean.setChrgusrnm(rs.getString("CHRGUSRNM"));
				bean.setOpendt(rs.getString("OPENDT"));
				bean.setSearchkey(rs.getString("SEARCHKEY"));
				bean.setDelyn(rs.getString("DELYN"));
				bean.setOpeninput(rs.getString("OPENINPUT"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				bean.setUptdt(rs.getString("UPTDT"));
				bean.setUptusrid(rs.getString("UPTUSRID"));
				bean.setEnddt(rs.getString("ENDDT"));
				bean.setEnddt_date(rs.getString("ENDDT_DATE"));
				bean.setEnddt_hour(rs.getString("ENDDT_HOUR"));
				bean.setEnddt_min(rs.getString("ENDDT_MIN"));
				bean.setSancusrnm1(sancusrnm1.toString());
				bean.setSancusrnm2(sancusrnm2.toString());
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));
				bean.setOrd(rs.getInt("ORD"));
			}
		 } catch (Exception e) {
			 logger.error("ERROR",e);
			 ConnectionManager.close(conn, pstmt, rs);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn, pstmt, rs);
	     }
	     
		return bean;
	}
	
	/**
	 * 취합문서 첨부파일 정보 
	 * @param conn
	 * @param sysdocno
	 * @param fileseq
	 * @return
	 * @throws Exception
	 */
	public ColldocFileBean getColldocFile(Connection conn, int sysdocno, int fileseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ColldocFileBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT SYSDOCNO, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
			sql.append("FROM DOCMSTFILE \n");
			sql.append("WHERE SYSDOCNO = ? \n");
			sql.append("AND FILESEQ = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, fileseq);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {		
				result = new ColldocFileBean();
				
				result.setSysdocno(rs.getInt("SYSDOCNO"));
				result.setFileseq(rs.getInt("FILESEQ"));
				result.setFilenm(rs.getString("FILENM"));
				result.setOriginfilenm(rs.getString("ORIGINFILENM"));
				result.setFilesize(rs.getInt("FILESIZE"));
				result.setExt(rs.getString("EXT"));
				result.setOrd(rs.getInt("ORD"));
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 취합문서 첨부파일 정보 
	 * @param conn
	 * @param sysdocno
	 * @return
	 * @throws Exception
	 */
	public List getListColldocFile(Connection conn, int sysdocno) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT SYSDOCNO, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
			sql.append("FROM DOCMSTFILE \n");
			sql.append("WHERE SYSDOCNO = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			
			result = new ArrayList();
			while ( rs.next() ) {
				ColldocFileBean cdfbean = new ColldocFileBean();
				
				cdfbean.setSysdocno(rs.getInt("SYSDOCNO"));
				cdfbean.setFileseq(rs.getInt("FILESEQ"));
				cdfbean.setFilenm(rs.getString("FILENM"));
				cdfbean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				cdfbean.setFilesize(rs.getInt("FILESIZE"));
				cdfbean.setExt(rs.getString("EXT"));
				cdfbean.setOrd(rs.getInt("ORD"));
				
				result.add(cdfbean);
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 취합문서목록에서 선택한 문서 삭제하기
	 * @param String[] deletelist
	 * @throws Exception 
	 */
	public int delColldoc(Connection conn, String[] deletelist) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;

		try {			
			StringBuffer deletedocno;
			
			deletedocno = new StringBuffer();
			
			for(int i = 0; i < deletelist.length; i++)
				deletedocno.append(deletelist[i] + ",");
			deletedocno.deleteCharAt(deletedocno.length()-1);
			
			String query;
			
			query = "DELETE " +
					"FROM DOCMST " +
					"WHERE SYSDOCNO IN (" + deletedocno.toString() + ") ";
			
			pstmt = conn.prepareStatement(query);
			
			result = pstmt.executeUpdate();
			try {pstmt.close();} catch(Exception ex) {}
			
			query = "DELETE " +
					"FROM INPUTUSR " +
					"WHERE SYSDOCNO IN (" + deletedocno.toString() + ") ";

			pstmt = conn.prepareStatement(query);
			
			pstmt.executeUpdate();
			try {pstmt.close();} catch(Exception ex) {} 
			
			query = "DELETE " +
					"FROM SANCCOL " +
					"WHERE SYSDOCNO IN (" + deletedocno.toString() + ") ";
	
			pstmt = conn.prepareStatement(query);
			
			pstmt.executeUpdate();
			try {pstmt.close();} catch(Exception ex) {} 

			query = "DELETE " +
					"FROM SANCTGT " +
					"WHERE SYSDOCNO IN (" + deletedocno.toString() + ") ";

			pstmt = conn.prepareStatement(query);
			
			pstmt.executeUpdate();
			try {pstmt.close();} catch(Exception ex) {} 
			
			query = "DELETE " +
					"FROM TGTDEPT " +
					"WHERE SYSDOCNO IN (" + deletedocno.toString() + ") ";
		
			pstmt = conn.prepareStatement(query);
			
			pstmt.executeUpdate();
			try {pstmt.close();} catch(Exception ex) {}
			
			query = "DELETE " +
					"FROM TGTLIST " +
					"WHERE SYSDOCNO IN (" + deletedocno.toString() + ") ";
				
			pstmt = conn.prepareStatement(query);
		
			pstmt.executeUpdate();
			try {pstmt.close();} catch(Exception ex) {}
			
		 } catch (Exception e) {
			 logger.error("ERROR",e);
			 try {pstmt.close();} catch(Exception ex) {}    
			 throw e;
	     } finally {
	    	 try {pstmt.close();} catch(Exception ex) {}    	
	     }
	     
		return result;		
	}
	
	/**
	 * 취합문서 첨부파일 추가
	 * @param fileBean
	 * @return
	 * @throws Exception
	 */
	public int addColldocFile(Connection conn, FileBean fileBean) throws Exception {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {			
			StringBuffer sql = new StringBuffer();
			
			sql.append("INSERT INTO \n");
			sql.append("DOCMSTFILE(SYSDOCNO,	FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
			sql.append("VALUES    (?,			?,       ?,      ?,            ?,        ?,   ?  ) \n");
			
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, fileBean.getSeq());
			pstmt.setInt(2, getNextColldocFileSeq(conn, fileBean.getFileseq()));
			pstmt.setString(3, fileBean.getFilenm());
			pstmt.setString(4, fileBean.getOriginfilenm());
			pstmt.setInt(5, fileBean.getFilesize());
			pstmt.setString(6, fileBean.getExt());
			pstmt.setInt(7, getNextColldocFileSeq(conn, fileBean.getFileseq()));
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 취합문서 첨부파일 삭제
	 * @param conn
	 * @param sysdocno
	 * @param fileseq
	 * @return
	 * @throws Exception
	 */
	public int delColldocFile(Connection conn, int sysdocno, int fileseq) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {			
			StringBuffer sql = new StringBuffer();
			
			sql.append("DELETE FROM DOCMSTFILE \n");
			sql.append("WHERE SYSDOCNO = ? \n");
			sql.append("AND FILESEQ = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, fileseq);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);  	
		}
		
		return result;		
	}
		
	/**
	 * 문서수정(새로저장)시 양식파일 복사
	 * @param Connection conn
	 * @param FileBookBean fbbean
	 * @param String filenm
	 * @param int newsysdocno
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int copyFormatFile(Connection conn, FileBookBean fbbean, String filenm, int newsysdocno) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			String query =
				"INSERT INTO " +
				"FILEBOOKFRM(SYSDOCNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) " +
				"SELECT "+newsysdocno+", FORMSEQ, FILESEQ, '"+filenm+"', ORIGINFILENM, FILESIZE, EXT, ORD " +
				"FROM FILEBOOKFRM " +
				"WHERE SYSDOCNO = ? " +
				"  AND FORMSEQ = ? " +
				"  AND FILESEQ = ? "; 

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, fbbean.getSysdocno());
			pstmt.setInt(2, fbbean.getFormseq());
			pstmt.setInt(3, fbbean.getFileseq());
			
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * 취합문서 첨부파일 복사
	 * @param Connection conn
	 * @param FileBookBean fbbean
	 * @param String filenm
	 * @param int newsysdocno
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int copyColldocFile(Connection conn, ColldocFileBean cdfbean, String filenm, int newsysdocno) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			String query =
				"INSERT INTO " +
				"DOCMSTFILE(SYSDOCNO, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) " +
				"SELECT "+newsysdocno+", FILESEQ, '"+filenm+"', ORIGINFILENM, FILESIZE, EXT, ORD " +
				"FROM DOCMSTFILE " +
				"WHERE SYSDOCNO = ? " +
				"  AND FILESEQ = ? "; 

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, cdfbean.getSysdocno());
			pstmt.setInt(2, cdfbean.getFileseq());
			
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	
	/**
	 * 문서수정(새로저장)시 취합대상부서,결재선 정보 복사
	 * @param Connection conn
	 * @param String user_id
	 * @param int sysdocno
	 * @param int newsysdocno
	 * @throws Exception 
	 */
	public void copyColldocData(Connection con, String user_id, int sysdocno, int newsysdocno) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {			
			String query;
			
			//취합부서결재 정보 복사
			query = "INSERT INTO " +
					"SANCCOL(SYSDOCNO, SEQ, GUBUN, SANCUSRID, SANCUSRNM, SANCYN, " +
					"        CRTDT, CRTUSRID, UPTDT, UPTUSRID) " +
					"SELECT "+newsysdocno+", SEQ, GUBUN, SANCUSRID, SANCUSRNM, '0', " +
					"       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" + user_id + "', " +
					"       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" + user_id + "' " +
					"FROM SANCCOL " +
					"WHERE SYSDOCNO = ? ";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, sysdocno);
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			//제출부서 정보 복사
			query = "INSERT INTO " +
					"TGTDEPT(SYSDOCNO, TGTDEPTCD, TGTDEPTNM, SUBMITSTATE, MODIFYYN, " +
					"        RETURNCOMMENT, INUSRSENDDT, APPNTUSRNM, SUBMITDT, PREDEPTCD, CRTDT, " +
					"        CRTUSRID, UPTDT, UPTUSRID) " +
					"SELECT "+newsysdocno+", TGTDEPTCD, TGTDEPTNM, SUBMITSTATE, MODIFYYN, " +
					"       '', INUSRSENDDT, APPNTUSRNM, '', PREDEPTCD, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), " +
					"       '" + user_id + "', TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" + user_id + "' " +
					"FROM TGTDEPT " +
					"WHERE SYSDOCNO = ? ";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, sysdocno);
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			//제출부서그룹 정보 복사
			query = "INSERT INTO " +
					"TGTLIST(SYSDOCNO, SEQ, GRPCD, GRPNM, GRPGBN, PREDEPTCD) " +
					"SELECT "+newsysdocno+", SEQ, GRPCD, GRPNM, GRPGBN, PREDEPTCD " +
					"FROM TGTLIST " +
					"WHERE SYSDOCNO = ? ";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, sysdocno);
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			//입력담당자 정보 복사
			query = "INSERT INTO " +
					"INPUTUSR(SYSDOCNO, TGTDEPT, INPUTUSRID, INPUTUSRNM, CHRGUNITCD, CHRGUNITNM, " +
					"         INPUTSTATE, INPUTCOMP, CRTDT, CRTUSRID, UPTDT, UPTUSRID) " +
					"SELECT "+newsysdocno+", TGTDEPT, INPUTUSRID, INPUTUSRNM, CHRGUNITCD, CHRGUNITNM, " +
					"       '01', '', CRTDT, CRTUSRID, UPTDT, UPTUSRID " +
					"FROM INPUTUSR " +
					"WHERE SYSDOCNO = ? ";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, sysdocno);
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			//양식정보 복사
			query = "INSERT INTO " +
					"FORMMST(SYSDOCNO, FORMSEQ, FORMTITLE, FORMKIND, FORMCOMMENT, " +
					"        TBLCOLS, TBLROWS, ORD, CRTDT, CRTUSRID, UPTDT, UPTUSRID) " +
					"SELECT "+newsysdocno+", FORMSEQ, FORMTITLE, FORMKIND, FORMCOMMENT, " +
					"       TBLCOLS, TBLROWS, ORD, " +
					"       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" + user_id + "', " +
					"       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" + user_id + "' " +
					"FROM FORMMST " +
					"WHERE SYSDOCNO = ? ";
	
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, sysdocno);
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			PreparedStatement p = null;
			ResultSet r = null;

			String[] columnName = {"FORMHTML", "FORMHEADERHTML", "FORMBODYHTML", "FORMTAILHTML"};

			query = "SELECT FORMSEQ FROM FORMMST WHERE SYSDOCNO = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, sysdocno);
			rs = pstmt.executeQuery();
			while ( rs.next() ) {
				int formseq = rs.getInt("FORMSEQ");
				
    			String[] columnData = new String[4];
    			for ( int i = 0; i < columnName.length; i++ ) {
    				query = "SELECT " + columnName[i] + " FROM FORMMST WHERE SYSDOCNO = ? AND FORMSEQ = ?";
    				p = con.prepareStatement(query);
    				p.setInt(1, sysdocno);
    				p.setInt(2, formseq);
    				r = p.executeQuery();
    				if ( r.next() ) {
    					columnData[i] = Utils.readClobData(r, columnName[i]);
    				}
    				ConnectionManager.close(p, r);
    			}
    			
    			for ( int i = 0; i < columnName.length; i++ ) {
    				query = "UPDATE FORMMST SET " + columnName[i] + " = EMPTY_CLOB() WHERE SYSDOCNO = ? AND FORMSEQ = ?";
    				p = con.prepareStatement(query);
    				p.setInt(1, newsysdocno);
    				p.setInt(2, formseq);
    				r = p.executeQuery();
    				ConnectionManager.close(p, r);

    				query = "SELECT " + columnName[i] + " FROM FORMMST WHERE SYSDOCNO = ? AND FORMSEQ = ? FOR UPDATE";
    				p = con.prepareStatement(query);
    				p.setInt(1, newsysdocno);
    				p.setInt(2, formseq);
    				r = p.executeQuery();
    				if ( r.next() ) {
    					Utils.writeClobData(r, columnName[i], columnData[i]);
    				}
    				ConnectionManager.close(p, r);
    			}
			}
			ConnectionManager.close(pstmt, rs);
			
			//양식속성 복사
			query = "INSERT INTO " +
					"ATTLINEFRM(SYSDOCNO, FORMSEQ, " +
					"           A, B, C, D, E, F, G, H, I, J, K, L, M, " +
					"           N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
					"           AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, " +
					"           NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ) " +
					"SELECT "+newsysdocno+", FORMSEQ, " +
					"       A, B, C, D, E, F, G, H, I, J, K, L, M, " +
					"       N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
					"       AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, " +
					"       NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ " +
					"FROM ATTLINEFRM " +
					"WHERE SYSDOCNO = ? ";
	
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, sysdocno);
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			//양식속성 복사
			query = "INSERT INTO " +
					"ATTFIXEDFRM(SYSDOCNO, FORMSEQ, " +
					"           A, B, C, D, E, F, G, H, I, J, K, L, M, " +
					"           N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
					"           AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, " +
					"           NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ) " +
					"SELECT "+newsysdocno+", FORMSEQ, " +
					"       A, B, C, D, E, F, G, H, I, J, K, L, M, " +
					"       N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
					"       AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, " +
					"       NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ " +
					"FROM ATTFIXEDFRM " +
					"WHERE SYSDOCNO = ? ";
	
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, sysdocno);
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			//양식속성 복사
			query = "INSERT INTO " +
					"ATTBOOKFRM(SYSDOCNO, FORMSEQ, SEQ, CATEGORYNM, ORD) " +
					"SELECT "+newsysdocno+", FORMSEQ, SEQ, CATEGORYNM, ORD " +
					"FROM ATTBOOKFRM " +
					"WHERE SYSDOCNO = ? ";
	
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, sysdocno);
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
	}
	
	/**
	 * 문서생성시 취합대상부서,결재선 임시테이블 데이터 원본테이블로 복사
	 * @param Connection conn
	 * @param String sessi
	 * @param int sysdocno
	 * @throws Exception 
	 */
	public void addColldocTempData_TGT_SANC(Connection conn, String sessi, int sysdocno) throws Exception {
		
		PreparedStatement pstmt = null;
		
		try {			
			String query;
			
			query = "INSERT INTO " +
					"SANCCOL(SYSDOCNO, SEQ, GUBUN, SANCUSRID, SANCUSRNM, " +
					"        SANCYN, SANCDT, SUBMITDT, CRTDT, CRTUSRID, UPTDT, UPTUSRID) " +
					"SELECT "+sysdocno+", SEQ, GUBUN, SANCUSRID, SANCUSRNM, " +
				    "       SANCYN, SANCDT, '', CRTDT, CRTUSRID, UPTDT, UPTUSRID " +
					"FROM SANCCOL_TEMP " +
					"WHERE SESSIONID LIKE ? ";

			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, sessi);
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			query = "INSERT INTO " +
					"TGTDEPT(SYSDOCNO, TGTDEPTCD, TGTDEPTNM, SUBMITSTATE, " +
					"        MODIFYYN, RETURNCOMMENT, INUSRSENDDT, APPNTUSRNM, SUBMITDT, " +
					"        PREDEPTCD, CRTDT, CRTUSRID, UPTDT, UPTUSRID) " +
					"SELECT "+sysdocno+", TGTDEPTCD, TGTDEPTNM, SUBMITSTATE, " +
					"       MODIFYYN, RETURNCOMMENT, INUSRSENDDT, APPNTUSRNM, '', " +
					"       PREDEPTCD, CRTDT, CRTUSRID, UPTDT, UPTUSRID " +
					"FROM TGTDEPT_TEMP " +
					"WHERE SESSIONID LIKE ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, sessi);
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			query = "INSERT INTO " +
					"TGTLIST(SYSDOCNO, SEQ, GRPCD, GRPNM, GRPGBN, PREDEPTCD) " +
					"SELECT "+sysdocno+", SEQ, GRPCD, GRPNM, GRPGBN, PREDEPTCD " +
					"FROM TGTLIST_TEMP " +
					"WHERE SESSIONID LIKE ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, sessi);
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			query = "INSERT INTO " +
					"INPUTUSR(SYSDOCNO, TGTDEPT, INPUTUSRID, INPUTUSRNM, CHRGUNITCD, CHRGUNITNM, " +
					"         INPUTSTATE, INPUTCOMP, CRTDT, CRTUSRID, UPTDT, UPTUSRID) " +
					"SELECT "+sysdocno+", TGTDEPT, INPUTUSRID, INPUTUSRNM, CHRGUNITCD, CHRGUNITNM, " +
					"       INPUTSTATE, INPUTCOMP, CRTDT, CRTUSRID, UPTDT, UPTUSRID " +
					"FROM INPUTUSR_TEMP " +
					"WHERE SESSIONID LIKE ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, sessi);
			pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
	}
	
	/**
	 * 문서생성시 취합대상부서,결재선 임시테이블 데이터 삭제
	 * @param Connection conn
	 * @param String sessi
	 * @throws Exception 
	 */
	public void delColldocTempData_TGT_SANC(Connection conn, String sessi) throws Exception {
		
		PreparedStatement pstmt = null;
		
		try {			
			String query;
			
			query = "DELETE " +
					"FROM SANCCOL_TEMP " +
					"WHERE SESSIONID LIKE ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, sessi);
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			query = "DELETE " +
					"FROM TGTDEPT_TEMP " +
					"WHERE SESSIONID LIKE ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, sessi);
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			query = "DELETE " +
					"FROM TGTLIST_TEMP " +
					"WHERE SESSIONID LIKE ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, sessi);
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			query = "DELETE " +
					"FROM INPUTUSR_TEMP " +
					"WHERE SESSIONID LIKE ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, sessi);
			pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
	}
	
	/**
	 * 새취합문서 생성
	 * @param Connection conn
	 * @param DataTransferBean cdbean
	 * @return int 새시스템문서번호
	 * @throws Exception 
	 */
	public int newColldoc(Connection conn, ColldocBean cdbean) throws Exception {
		
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        int sysdocno = 0;

		try {
			String summary = null;
			if(cdbean.getSummary() != null) {
				summary = cdbean.getSummary().replaceAll("'", "''");
			} else {
				summary = "";
			}
			
			//시스템문서번호 가져오기
			String query = "SELECT DOCSEQ.NEXTVAL FROM DUAL";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				sysdocno = rs.getInt(1);
			}
			
			ConnectionManager.close(pstmt, rs);
			
			query =
				"INSERT INTO " +
				"DOCMST(SYSDOCNO,	DOCTITLE,	BASICDATE,	BASIS,		ENDDT, " +
		        "       ENDCOMMENT,	SANCRULE,	TGTDEPTNM,	COLDEPTCD,	COLDEPTNM, " +
		        "       CHRGUNITCD,	CHRGUNITNM,	CHRGUSRCD,	CHRGUSRNM,	CRTUSRID, " +
		        "       UPTUSRID, 	DOCGBN,		DOCSTATE,	DELYN,		OPENINPUT,	SUMMARY, " +
		        "		CRTDT,		UPTDT) " +
		        "VALUES(?,			?,			?,			?,			?, " +
		        "       ?,			?,			?,			?,			?, " +
		        "       ?,			?,			?,			?,			?, " +
		        "       ?,			?,			?,			?,			?,	'" + summary + "', " +
		        "		TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')) ";
			
			pstmt = conn.prepareStatement(query);
			
			int idx = 0;
			pstmt.setInt(++idx, sysdocno);					//새시스템문서번호
			pstmt.setString(++idx, cdbean.getDoctitle());	//문서제목
			pstmt.setString(++idx, cdbean.getBasicdate());	//자료기준일
			pstmt.setString(++idx, cdbean.getBasis());		//관련근거
			pstmt.setString(++idx, cdbean.getEnddt());		//마감일시
			pstmt.setString(++idx, cdbean.getEndcomment());	//마감알림말
			pstmt.setString(++idx, cdbean.getSancrule());	//제출자료전결
			pstmt.setString(++idx, cdbean.getTgtdeptnm());	//제출부서명,그룹명
			pstmt.setString(++idx, cdbean.getColdeptcd());	//취합부서코드
			pstmt.setString(++idx, cdbean.getColdeptnm());	//취합부서명
			pstmt.setInt(++idx, cdbean.getChrgunitcd());	//취합담당단위코드
			pstmt.setString(++idx, cdbean.getChrgunitnm());	//취합담당단위명
			pstmt.setString(++idx, cdbean.getUptusrid());	//취합담당자코드
			pstmt.setString(++idx, cdbean.getChrgusrnm());	//취합담당자명
			pstmt.setString(++idx, cdbean.getUptusrid());	//생성자ID
			pstmt.setString(++idx, cdbean.getUptusrid());	//수정자ID
			pstmt.setString(++idx, "01");					//문서구분
			pstmt.setString(++idx, "01");					//문서상태
			pstmt.setString(++idx, "0");					//목록에서 삭제여부
			pstmt.setString(++idx, cdbean.getOpeninput());	//공개입력
			
			pstmt.executeUpdate();
		 } catch (Exception e) {
			 logger.error("ERROR",e);
			 ConnectionManager.close(pstmt);
			 throw e;
	     } finally {
	    	 ConnectionManager.close(pstmt);
	     }
	     
		return sysdocno;
	}
	
	/**
	 * 취합문서 복사
	 * @param Connection conn
	 * @param DataTransferBean cdbean
	 * @return int 새시스템문서번호
	 * @throws Exception 
	 */
	public int copyColldoc(Connection conn, ColldocBean cdbean) throws Exception {
		
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        int sysdocno = 0;

		try {
			//시스템문서번호 가져오기
			String query = "SELECT DOCSEQ.NEXTVAL FROM DUAL";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				sysdocno = rs.getInt(1);
			}
			
			ConnectionManager.close(pstmt, rs);			
			
			query =
				"INSERT INTO " +
				"DOCMST(SYSDOCNO,	DOCTITLE,	BASICDATE,	BASIS,		ENDDT, " +
		        "       ENDCOMMENT,	SANCRULE,	TGTDEPTNM,	COLDEPTCD,	COLDEPTNM, " +
		        "       CHRGUNITCD,	CHRGUNITNM,	CHRGUSRCD,	CHRGUSRNM,	CRTUSRID, " +
		        "       UPTUSRID,	DOCGBN,		DOCSTATE,	DELYN,		OPENINPUT,	SUMMARY, " +
		        "		CRTDT,		UPTDT) " +
		        "SELECT ?, DOCTITLE, BASICDATE,	BASIS,		ENDDT, " +
		        "       ENDCOMMENT,	SANCRULE,	TGTDEPTNM,	COLDEPTCD,	COLDEPTNM, " +
		        "       CHRGUNITCD, CHRGUNITNM,	CHRGUSRCD,	CHRGUSRNM,	'" + cdbean.getUptusrid() + "', "  +
		        "       '" + cdbean.getUptusrid() + "', DOCGBN,		DOCSTATE,	DELYN,	OPENINPUT,	SUMMARY, " +
		        "		TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') " +
		        "FROM DOCMST " +
		        "WHERE SYSDOCNO = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);				//새시스템문서번호
			pstmt.setInt(2, cdbean.getSysdocno());	//기존시스템문서번호
			
			pstmt.executeUpdate();			
		 } catch (Exception e) {
			 logger.error("ERROR",e);
			 ConnectionManager.close(pstmt);
			 throw e;
	     } finally {
	    	 ConnectionManager.close(pstmt);
	     }
	     
		return sysdocno;
	}
	
	/**
	 * 취합문서정보 변경
	 * @param Connection conn
	 * @param DataTransferBean cdbean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int saveColldoc(Connection conn, ColldocBean cdbean) throws Exception {

		PreparedStatement pstmt = null;
        
        int result = 0;

		try {
			String summary = null;
			if(cdbean.getSummary() != null) {
				summary = cdbean.getSummary().replaceAll("'", "''");
			} else {
				summary = "";
			}
			
			String sql =
				"UPDATE DOCMST " +
				"SET DOCTITLE=?,	BASICDATE=?,	BASIS=?,	ENDDT=?,	ENDCOMMENT=?, " +
		        "    SANCRULE=?,	TGTDEPTNM=?,	COLDEPTCD=?,COLDEPTNM=?,CHRGUNITCD=?, " +
		        "    CHRGUNITNM=?,	CHRGUSRCD=?,	CHRGUSRNM=?,	OPENINPUT=?,	UPTUSRID=?,	" +
		        "    UPTDT=TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), " +
		        "    SUMMARY='" + summary + "' " +
		        "WHERE SYSDOCNO = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, cdbean.getDoctitle());
			pstmt.setString(2, cdbean.getBasicdate());
			pstmt.setString(3, cdbean.getBasis());
			pstmt.setString(4, cdbean.getEnddt());
			pstmt.setString(5, cdbean.getEndcomment());
			pstmt.setString(6, cdbean.getSancrule());
			pstmt.setString(7, cdbean.getTgtdeptnm());
			pstmt.setString(8, cdbean.getColdeptcd());
			pstmt.setString(9, cdbean.getColdeptnm());
			pstmt.setInt(10, cdbean.getChrgunitcd());
			pstmt.setString(11, cdbean.getChrgunitnm());
			pstmt.setString(12, cdbean.getUptusrid());
			pstmt.setString(13, cdbean.getChrgusrnm());
			pstmt.setString(14, cdbean.getOpeninput());
			pstmt.setString(15, cdbean.getUptusrid());
			pstmt.setInt(16, cdbean.getSysdocno());
			
			result = pstmt.executeUpdate();
		 } catch (Exception e) {
			 logger.error("ERROR",e);
			 ConnectionManager.close(pstmt);
			 throw e;
	     } finally {	       
	    	 try {pstmt.close();} catch(Exception e) {}
	     }
	     
		return result;
	}
	
	/**
	 * 취합문서 첨부파일 새번호 가져오기
	 * @param Connection conn
	 * @param String year
	 * @param String deptcd
	 * @return int 새문서번호
	 * @throws Exception 
	 */
	public int getNextColldocFileSeq(Connection conn, int sysdocno) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int result = 0;
		
		try {
			String query;
			
			query = "SELECT NVL(MAX(FILESEQ), 0) + 1 " +
					"FROM DOCMSTFILE " +
					"WHERE SYSDOCNO = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}	
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt,rs);
			throw e;
		} finally {
			try {rs.close();} catch(Exception e) {}
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * 새문서번호 가져오기
	 * @param Connection conn
	 * @param String year
	 * @param String deptcd
	 * @return int 새문서번호
	 * @throws Exception 
	 */
	public int getNextDocno(Connection conn, String year, String deptcd) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int seq = 0;
		
		try {
			String query;
			
			query = "SELECT NVL(MAX(SEQ), 0) + 1 " +
					"FROM GETDOCNO " +
					"WHERE YEAR = ? " +
					"  AND DEPTCD = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, year);
			pstmt.setString(2, deptcd);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				seq = rs.getInt(1);
			}	
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt,rs);
			throw e;
		} finally {
			try {rs.close();} catch(Exception e) {}
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return seq;
	}
	
	/**
	 * 문서번호 채번
	 * @param Connection conn
	 * @param String year
	 * @param String deptcd
	 * @param String int seq
	 * @param String deptnm
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int setDocno(Connection conn, String year, String deptcd, int seq, String deptnm) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			String query;
			
			query = "UPDATE GETDOCNO " +
					"SET SEQ = ?, DEPTNM = ?, BIGO = ? " +
					"WHERE YEAR = ? " +
					"  AND DEPTCD = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, seq);
			pstmt.setString(2, deptnm);
			pstmt.setString(3, "");
			pstmt.setString(4, year);	
			pstmt.setString(5, deptcd);
			
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * 취합문서 결재상신 상태로 지정하기
	 * @param Connection conn
	 * @param String user_id
	 * @param int sysdocno
	 * @param String docstate
	 * @param String docno
	 * @throws Exception 
	 */
	public int appovalColldoc(Connection conn, String user_id, int sysdocno, String docstate, String docno) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			String deliverydt = "";
			if(docstate.equals("04") == true) {
				deliverydt = ", DELIVERYDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') ";
			}
			
			String query;
			
			query = "UPDATE DOCMST " +
					"SET DOCSTATE = ?, DOCNO = ?, UPTUSRID = ?, " +
					"    UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), " +
					"    SUBMITDATE = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') " + deliverydt +
					"WHERE SYSDOCNO = ?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, docstate);
			pstmt.setString(2, docno);
			pstmt.setString(3, user_id);
			pstmt.setInt(4, sysdocno);
			
			result = pstmt.executeUpdate();
			try {pstmt.close();} catch(Exception e) {}
			
			query = "UPDATE SANCCOL " +
					"SET SUBMITDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') " +
					"WHERE SYSDOCNO = ? ";

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			result += pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * 진행중인취합문서 목록
	 * @param String deptcd
	 * @param int start
	 * @param int end
	 * @return List 취합문서목록(ArrayList)
	 * @throws Exception 
	 */
	public List getCollProcList(String usrid, String deptcd, String initentry, String isSysMgr, String docstate, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List collprocList = null;
		int bindPos = 0;
		
		String sysdate = "SYSDATE";
		if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			sysdate = "(SYSDATE-30/(24*60))";
		}
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT (CNT-SEQ+1) BUNHO, SEQ, FORMSEQ, FORMKIND, SYSDOCNO, DOCTITLE, CCDNAME, DELIVERYDT, ENDDT, RDAY, RTIME, COLDEPTCD, CHRGUSRCD \n");
			sql.append("  FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n");
			sql.append("          FROM (SELECT ROWNUM SEQ, A2.* \n");
			sql.append("                  FROM (SELECT F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, A.DOCTITLE, B.CCDNAME, A.COLDEPTCD, A.CHRGUSRCD, \n");
			sql.append("                               DECODE(A.DELIVERYDT,'','',SUBSTR(A.DELIVERYDT,1,4)||'년 '||SUBSTR(A.DELIVERYDT,6,2)||'월 '||SUBSTR(A.DELIVERYDT,9,2)||'일') DELIVERYDT, \n");
			sql.append("                               SUBSTR(A.ENDDT,1,4)||'년'||SUBSTR(A.ENDDT,6,2)||'월'||SUBSTR(A.ENDDT,9,2)||'일 '||SUBSTR(A.ENDDT,12,2)||'시'||SUBSTR(A.ENDDT,15,2)||'분' ENDDT, \n");
			sql.append("                               TRUNC(TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS')-"+sysdate+") RDAY, \n");
			sql.append("                               CEIL(MOD((TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS')-"+sysdate+"),1)*24) RTIME \n");
			sql.append("                          FROM DOCMST A, CCD B, \n");
			sql.append("                               (SELECT SYSDOCNO, FORMSEQ, FORMKIND \n");
			sql.append("                                FROM FORMMST F \n");
			sql.append("                                WHERE FORMSEQ = (SELECT MIN(FORMSEQ) FROM FORMMST WHERE SYSDOCNO = F.SYSDOCNO and to_number(formkind) <= 3)) F \n");
			sql.append("                         WHERE A.DOCSTATE = B.CCDSUBCD \n");
			sql.append("                           AND B.CCDCD = '003' \n");
			sql.append("                           AND A.SYSDOCNO = F.SYSDOCNO \n");
			
			if(initentry.equals("first")){
				sql.append("					 AND A.COLDEPTCD = ? 				\n");
				sql.append("       				 AND A.CHRGUSRCD = ?				\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) sql.append("\n  AND A.COLDEPTCD LIKE ?                                             \n");
					if( !"".equals(sch_userid) ) sql.append("\n  AND A.CHRGUSRCD LIKE ?                                             \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) sql.append("\n  AND A.COLDEPTNM LIKE ?                    \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) sql.append("\n  AND A.CHRGUSRNM LIKE ?                    \n");
				}else{
					sql.append("					 AND A.COLDEPTCD = ? 			\n");
					sql.append("\n                   AND A.CHRGUSRCD = ? 			\n");
				}
			}
			
			if(docstate.equals("0")){
				sql.append("                       AND A.DOCSTATE IN  ('02','03','04','05') \n");
			}else if(docstate.equals("1")){
				sql.append("                       AND A.DOCSTATE IN  ('02','03') \n");
			}else if(docstate.equals("2")){
				sql.append("                       AND A.DOCSTATE =  '04' \n");
			}else{
				sql.append("                       AND A.DOCSTATE =  '05' \n");
			}

			sql.append("                         ORDER BY A.ENDDT DESC, A.CRTDT DESC, A.UPTDT DESC) A2) A1) \n");
			sql.append("WHERE SEQ BETWEEN ? AND ? \n");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(sql.toString());
			
			if(initentry.equals("first")){
				pstmt.setString(++bindPos, deptcd);
				pstmt.setString(++bindPos, usrid);
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) pstmt.setString(++bindPos, "%"+ sch_deptcd +"%");
					if( !"".equals(sch_userid) ) pstmt.setString(++bindPos, "%"+ sch_userid +"%");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) pstmt.setString(++bindPos, "%"+ sch_deptnm +"%");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) pstmt.setString(++bindPos, "%"+ sch_usernm +"%");
				}else{
					pstmt.setString(++bindPos, deptcd);
					pstmt.setString(++bindPos, usrid);
				}
			}
			
			pstmt.setInt(++bindPos, start);
			pstmt.setInt(++bindPos, end);
									
			rs = pstmt.executeQuery();
			
			collprocList = new ArrayList();
			
			while (rs.next()) {
				ColldocBean Bean = new ColldocBean();
				
				Bean.setFormseq(rs.getInt("FORMSEQ"));
				Bean.setFormkind(rs.getString("FORMKIND"));
				Bean.setSeqno(rs.getInt("BUNHO"));
				Bean.setSysdocno(rs.getInt("SYSDOCNO"));
				Bean.setDoctitle(rs.getString("DOCTITLE"));		
				Bean.setDeliverydt(rs.getString("DELIVERYDT"));
				Bean.setDocstate(rs.getString("CCDNAME"));
				Bean.setEnddt(rs.getString("ENDDT"));
				Bean.setChrgusrcd(rs.getString("CHRGUSRCD"));
				
				CommTreatBean ctbean = CommTreatManager.instance().getTgtdeptCnt(con, rs.getInt("SYSDOCNO"), rs.getString("COLDEPTCD"));
				Bean.setCnt(ctbean.getScnt() + "/" + ctbean.getTcnt());
				
				if(rs.getInt("RDAY")< 0){
					Bean.setEnddt_date("마감시한 초과");
					
				} else if(rs.getInt("RDAY") == 0){
					if(rs.getInt("RTIME")<=0){
						Bean.setEnddt_date("마감시한 초과");
					}else if(rs.getInt("RTIME")==1){
						Bean.setEnddt_date(rs.getString("RTIME")+"시간미만");
					}else{
						Bean.setEnddt_date(rs.getString("RTIME")+"시간");
					}
				} else{
					Bean.setEnddt_date(rs.getString("RDAY")+"일 "+rs.getString("RTIME")+"시간");
				}
				
				collprocList.add(Bean);
			}							
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return collprocList;
	}
	
	/**
	 * 진행중인 취압문서 목록 갯수 가져오기
	 * @param String deptcd
	 * @return int 취합문서개수
	 * @throws Exception 
	 */
	public int getCollProcTotCnt(String usrid, String deptcd, String initentry, String isSysMgr, String docstate, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		int bindPos = 0;
		
		try {
			StringBuffer sql = new StringBuffer();			
			sql.append("SELECT COUNT(*) 			\n");
			sql.append("  FROM DOCMST A 			\n");
			sql.append("   WHERE 1 =1				\n");
			
			if(initentry.equals("first")){
				sql.append(" AND A.COLDEPTCD = ?  	\n");
				sql.append(" AND A.CHRGUSRCD = ?	\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) sql.append("\n  AND A.COLDEPTCD LIKE ?                                             \n");
					if( !"".equals(sch_userid) ) sql.append("\n  AND A.CHRGUSRCD LIKE ?                                             \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) sql.append("\n  AND A.COLDEPTNM LIKE ?                    \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) sql.append("\n  AND A.CHRGUSRNM LIKE ?                    \n");
				}else{
					sql.append(" AND A.COLDEPTCD = ?  	\n");
					sql.append(" AND A.CHRGUSRCD = ?	\n");
				}
			}
			
			if(docstate.equals("0")){
				sql.append("                           AND A.DOCSTATE IN  ('02','03','04','05') \n");
			}else if(docstate.equals("1")){
				sql.append("                           AND A.DOCSTATE IN  ('02','03') \n");
			}else if(docstate.equals("2")){
				sql.append("                           AND A.DOCSTATE =  '04' \n");
			}else{
				sql.append("                           AND A.DOCSTATE =  '05' \n");
			}

			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(sql.toString());

			if(initentry.equals("first")){
				pstmt.setString(++bindPos, deptcd);
				pstmt.setString(++bindPos, usrid);
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) pstmt.setString(++bindPos, "%"+ sch_deptcd +"%");
					if( !"".equals(sch_userid) ) pstmt.setString(++bindPos, "%"+ sch_userid +"%");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) pstmt.setString(++bindPos, "%"+ sch_deptnm +"%");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) pstmt.setString(++bindPos, "%"+ sch_usernm +"%");
				}else{
					pstmt.setString(++bindPos, deptcd);
					pstmt.setString(++bindPos, usrid);
				}
			}
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return totalCount;
	}
	
	/**
	 * 진행중인 취합문서 취합마감 처리
	 * @param CollprocBean bean
	 * @param String tgtdeptcd
	 * @param String sessionId
	 * @return int 처리건수
	 * @throws Exception 
	 */
	public int collprocClose(CollprocBean bean, String tgtdeptcd, String sessionId) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
        int bindPos = 0;
        int cnt = 0;
        
        StringBuffer updateQuery = new StringBuffer();
        
        updateQuery.append("\n UPDATE DOCMST												");
        updateQuery.append("\n    SET OPENDT 	= ?,										");
        updateQuery.append("\n  	  SEARCHKEY = ?,										");
        updateQuery.append("\n  	  DOCSTATE  = '06',										");
        updateQuery.append("\n        UPTDT     = TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),	");
        updateQuery.append("\n        UPTUSRID  = ?											");
        updateQuery.append("\n	WHERE SYSDOCNO  = ?											");
        
		try {							
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(updateQuery.toString());
			pstmt.setString(++bindPos, bean.getClosedate());
			pstmt.setString(++bindPos, bean.getSearchkey());
			pstmt.setString(++bindPos, sessionId);
			pstmt.setInt(++bindPos, bean.getSysdocno());
			
			if(pstmt.executeUpdate()>0){
				cnt++;
			}
			
		 } catch (Exception e) {
			 logger.error("ERROR",e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(con, pstmt);
	     }
	     
		return cnt;
	}
	
	/**
	 * 진행중인 기안취소 처리
	 * @param int sysdocno
	 * @param String sessionId
	 * @param String tgtdeptcd
	 * @return int 처리건수
	 */
	public int collprocProcess(int sysdocno, String sessionId, String tgtdeptcd) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        int bindPos = 0;
        int cnt = 0;
        
        StringBuffer updateQuery = new StringBuffer();
        StringBuffer updateQuery1 = new StringBuffer();
        //StringBuffer selectQuery = new StringBuffer();
        
        updateQuery.append("\n UPDATE DOCMST												");
        updateQuery.append("\n    SET DOCSTATE  = '01',										");
        updateQuery.append("\n        UPTDT     = TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),	");
        updateQuery.append("\n        UPTUSRID  = ?											");
        updateQuery.append("\n	WHERE SYSDOCNO  = ?											");
        
        updateQuery1.append("\n UPDATE SANCCOL												");
        updateQuery1.append("\n    SET SANCYN  = '0',										");
        updateQuery1.append("\n        UPTDT   = TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),	");
        updateQuery1.append("\n        UPTUSRID = ?											");
        updateQuery1.append("\n	WHERE SYSDOCNO  = ?											");
        
		try {							
			con = ConnectionManager.getConnection();
			
			con.commit();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(updateQuery.toString());
			pstmt.setString(++bindPos, sessionId);
			pstmt.setInt(++bindPos, sysdocno);
			
			if(pstmt.executeUpdate()>0){
				cnt++;
			}
			bindPos = 0;
			
			try {pstmt.close();} catch(Exception e) {}
			
			pstmt = con.prepareStatement(updateQuery1.toString());
			pstmt.setString(++bindPos, sessionId);
			pstmt.setInt(++bindPos, sysdocno);
			
			if(pstmt.executeUpdate()>0){
				cnt++;
			}
			bindPos = 0;
			
			try {pstmt.close();} catch(Exception e) {}

			con.commit();
		 } catch (Exception e) {
			con.rollback();
			ConnectionManager.close(con,pstmt,rs);
			throw e;
	     } finally {	       
			con.setAutoCommit(true);
			ConnectionManager.close(con, pstmt, rs);
	     }
	     
		return cnt;
	}
	
	/**
	 * 취합완료문서 목록
	 * @param String sessionId
	 * @param int start
	 * @param int end
	 * @return List 취합문서목록(ArrayList)
	 * @throws Exception 
	 */
	public List getCollcompList(String usrid, String deptcd, String searchvalue, String selyear, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List collprocList = null;
		int bindPos = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT (CNT-SEQ+1) BUNHO, SEQ, FORMSEQ, FORMKIND, SYSDOCNO, DOCTITLE, DELIVERYDT, ENDDT, RDAY, RTIME, COLDEPTCD, CHRGUSRCD \n");
			sql.append("  FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n");
			sql.append("          FROM (SELECT ROWNUM SEQ, A2.* \n");
			sql.append("                  FROM (SELECT F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, A.DOCTITLE, A.COLDEPTCD, A.CHRGUSRCD,   \n");
			sql.append("                               DECODE(A.DELIVERYDT,'','',SUBSTR(A.DELIVERYDT,1,4)||'년 '||SUBSTR(A.DELIVERYDT,6,2)||'월 '||SUBSTR(A.DELIVERYDT,9,2)||'일') DELIVERYDT, \n");
			sql.append("                               SUBSTR(A.ENDDT,1,4)||'년'||SUBSTR(A.ENDDT,6,2)||'월'||SUBSTR(A.ENDDT,9,2)||'일 '||SUBSTR(A.ENDDT,12,2)||'시'||SUBSTR(A.ENDDT,15,2)||'분' ENDDT, \n");
			sql.append("                               TRUNC(TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS')-SYSDATE) RDAY, \n");
			sql.append("                               CEIL(MOD((TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS')-SYSDATE),1)*24) RTIME \n");
			sql.append("                          FROM DOCMST A,  \n");
			sql.append("                               (SELECT SYSDOCNO, FORMSEQ, FORMKIND \n");
			sql.append("                                FROM FORMMST F \n");
			sql.append("                                WHERE FORMSEQ = (SELECT MIN(FORMSEQ) FROM FORMMST WHERE SYSDOCNO = F.SYSDOCNO and to_number(formkind) <= 3)) F \n");
			sql.append("                         WHERE A.SYSDOCNO = F.SYSDOCNO  \n");
			sql.append("						   AND A.DOCTITLE like ?  \n");
			sql.append("						   AND TO_CHAR(TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') LIKE  ?  \n");
			
			if(initentry.equals("first")){
				sql.append("     				 AND A.COLDEPTCD = ? 				\n");
				sql.append("       				 AND A.CHRGUSRCD = ?				\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) sql.append("\n  AND A.COLDEPTCD LIKE ?                                             \n");
					if( !"".equals(sch_userid) ) sql.append("\n  AND A.CHRGUSRCD LIKE ?                                             \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) sql.append("\n  AND A.COLDEPTNM LIKE ?                    \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) sql.append("\n  AND A.CHRGUSRNM LIKE ?                    \n");
				}else{
					sql.append("     				 AND A.COLDEPTCD = ? 				\n");
					sql.append("       				 AND A.CHRGUSRCD = ?				\n");
				}
			}
			
			sql.append("                           AND A.DOCSTATE IN ('06') \n");
			sql.append("                         ORDER BY A.ENDDT DESC, A.CRTDT DESC, A.UPTDT DESC) A2) A1) \n");
			sql.append("WHERE SEQ BETWEEN ? AND ? \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(++bindPos, "%" + searchvalue + "%");
			pstmt.setString(++bindPos, "%" + selyear + "%");
			
			if(initentry.equals("first")){
				pstmt.setString(++bindPos, deptcd);
				pstmt.setString(++bindPos, usrid);
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) pstmt.setString(++bindPos, "%"+ sch_deptcd +"%");
					if( !"".equals(sch_userid) ) pstmt.setString(++bindPos, "%"+ sch_userid +"%");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) pstmt.setString(++bindPos, "%"+ sch_deptnm +"%");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) pstmt.setString(++bindPos, "%"+ sch_usernm +"%");
				}else{
					pstmt.setString(++bindPos, deptcd);
					pstmt.setString(++bindPos, usrid);
				}
			}
			pstmt.setInt(++bindPos, start);
			pstmt.setInt(++bindPos, end);
									
			rs = pstmt.executeQuery();
			
			collprocList = new ArrayList();
			
			while (rs.next()) {
				ColldocBean Bean = new ColldocBean();
				
				Bean.setSeqno(rs.getInt("BUNHO"));
				Bean.setFormseq(rs.getInt("FORMSEQ"));
				Bean.setFormkind(rs.getString("FORMKIND"));
				Bean.setSysdocno(rs.getInt("SYSDOCNO"));
				Bean.setDoctitle(rs.getString("DOCTITLE"));		
				Bean.setDeliverydt(rs.getString("DELIVERYDT"));
				Bean.setEnddt(rs.getString("ENDDT"));
				Bean.setChrgusrcd(rs.getString("CHRGUSRCD"));
				
				CommTreatBean ctbean = CommTreatManager.instance().getTgtdeptCnt(con, rs.getInt("SYSDOCNO"), rs.getString("COLDEPTCD"));
				Bean.setCnt(ctbean.getScnt() + "/" + ctbean.getTcnt());
				
				if(rs.getInt("RDAY")< 0){
					Bean.setEnddt_date("마감시한 초과");
				} else if(rs.getInt("RDAY") == 0){
					if(rs.getInt("RTIME")<=0){
						Bean.setEnddt_date("마감시한 초과");
					}else if(rs.getInt("RTIME")==1){
						Bean.setEnddt_date(rs.getString("RTIME")+"시간미만");
					}else{
						Bean.setEnddt_date(rs.getString("RTIME")+"시간");
					}
				} else{
					Bean.setEnddt_date(rs.getString("RDAY")+"일 "+rs.getString("RTIME")+"시간");
				}
				
				collprocList.add(Bean);
			}							
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return collprocList;
	}
	
	/**
	 * 취합완료문서 목록 갯수 가져오기
	 * @param String sessionId
	 * @return int 취합문서개수
	 * @throws Exception 
	 */
	public int getCollcompTotCnt(String usrid, String deptcd, String searchvalue, String selyear, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		int bindPos = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) 				\n");
			sql.append("  FROM DOCMST A  				\n");
			sql.append("  WHERE A.DOCTITLE like ?  		\n");
			sql.append("   AND TO_CHAR(TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') LIKE  ?  \n");			

			if(initentry.equals("first")){
				sql.append("  AND A.COLDEPTCD = ?       \n");
				sql.append("  AND A.CHRGUSRCD = ?		\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) sql.append("\n  AND A.COLDEPTCD LIKE ?                                             \n");
					if( !"".equals(sch_userid) ) sql.append("\n  AND A.CHRGUSRCD LIKE ?                                             \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) sql.append("\n  AND A.COLDEPTNM LIKE ?                    \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) sql.append("\n  AND A.CHRGUSRNM LIKE ?                    \n");
				}else{
					sql.append("  AND A.COLDEPTCD = ?       \n");
					sql.append("  AND A.CHRGUSRCD = ?		\n");
				}
			}
			sql.append("   AND A.DOCSTATE IN  ('06') 	\n");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(sql.toString());
			pstmt.setString(++bindPos, "%" + searchvalue + "%");
			pstmt.setString(++bindPos, "%" + selyear + "%");
			
			if(initentry.equals("first")){
				pstmt.setString(++bindPos, deptcd);
				pstmt.setString(++bindPos, usrid);
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) pstmt.setString(++bindPos, "%"+ sch_deptcd +"%");
					if( !"".equals(sch_userid) ) pstmt.setString(++bindPos, "%"+ sch_userid +"%");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) pstmt.setString(++bindPos, "%"+ sch_deptnm +"%");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) pstmt.setString(++bindPos, "%"+ sch_usernm +"%");					
				}else{
					pstmt.setString(++bindPos, deptcd);
					pstmt.setString(++bindPos, usrid);
				}
			}
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return totalCount;
	}	
	
	/**
	 * 취합완료문서 취합마감해제 처리
	 * @param int sysdocno
	 * @param String sessionId
	 * @return int 처리건수
	 * @throws Exception 
	 */
	public int collCompProcess(int sysdocno, String user_id) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        int bindPos = 0;
        int cnt = 0;
        int chkcount = 0;
        
        StringBuffer selectQuery = new StringBuffer();
        StringBuffer updateQuery = new StringBuffer();
        
        selectQuery.append("\n SELECT SUM(DECODE(SUBMITSTATE,'05',0,1))	");
        selectQuery.append("\n   FROM TGTDEPT 							");
        selectQuery.append("\n  WHERE SYSDOCNO = ?  					");
        selectQuery.append("\n  GROUP BY SYSDOCNO 						");
        
        updateQuery.append("\n UPDATE DOCMST												");
        updateQuery.append("\n    SET DOCSTATE  = ?,										");
        updateQuery.append("\n        UPTDT     = TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),	");
        updateQuery.append("\n        UPTUSRID  = ?											");
        updateQuery.append("\n	WHERE SYSDOCNO  = ?											");
        
		try {							
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(++bindPos, sysdocno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				chkcount = rs.getInt(1);
			}
			
			bindPos = 0;
			
			try {pstmt.close();} catch(Exception e) {}
			
			pstmt = con.prepareStatement(updateQuery.toString());
			
			if(chkcount >0){
				pstmt.setString(++bindPos, "04");
				pstmt.setString(++bindPos, user_id);
				pstmt.setInt(++bindPos, sysdocno);
			}else{
				pstmt.setString(++bindPos, "05");
				pstmt.setString(++bindPos, user_id);
				pstmt.setInt(++bindPos, sysdocno);
			}
			
			if(pstmt.executeUpdate()>0){
				cnt++;
			}
			
		 } catch (Exception e) {
			 logger.error("ERROR",e);
			 ConnectionManager.close(con,pstmt,rs);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(con, pstmt, rs);
	     }
	     
		return cnt;
	}
	
	/**
	 * 취합진행주인문서 마감관련 내용 가져오기
	 * @throws Exception 
	 */
	public CollprocBean getCloseView(int sysdocno) throws Exception{
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		CollprocBean Bean = new CollprocBean();
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n SELECT SEARCHKEY, DECODE(OPENDT,	'1900-01-01', '', '9999-12-31', '', '1900-01-04', '', OPENDT) AS OPENDT,											");
			selectQuery.append("\n        DECODE(OPENDT, '1900-01-01', '1', '9999-12-31', '2', '1900-01-04', '4', '1') AS RADIOCHK 	");
			selectQuery.append("\n   FROM DOCMST  															");
			selectQuery.append("\n  WHERE SYSDOCNO = ?														");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				Bean.setSearchkey(rs.getString("SEARCHKEY"));
				Bean.setClosedate(rs.getString("OPENDT"));
				Bean.setRadiochk(rs.getString("RADIOCHK"));
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return Bean;
	}	
	
	/**
	 * 취합현황 건수 가져오기 -(메인화면)
	 * stepGbn : 결재진행(1), 취합진행(2), 마감대기(3)
	 * @throws Exception 
	 */
	public int procCount(String stepGbn, String user_id, String deptcd) throws Exception {		
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) ");
			selectQuery.append("FROM DOCMST " );
			selectQuery.append("WHERE CHRGUSRCD = ? " );
			selectQuery.append("  AND COLDEPTCD = ? ");
			
			if("1".equals(stepGbn)){
				//결재진행
				selectQuery.append("  AND DOCSTATE IN ('02','03') ");
			} else if("2".equals(stepGbn)){
				//취합진행
				selectQuery.append("  AND DOCSTATE = '04' ");
			} else if("3".equals(stepGbn)){
				//마감대기
				selectQuery.append("  AND DOCSTATE = '05' ");
			}			
					
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
			pstmt.setString(1, user_id);
			pstmt.setString(2, deptcd);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() )
				count = rs.getInt(1);
		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return count;
	}
	
	/**
	 * 진행중인 취합문서 상세 - 문서상태, 배부일자, 마감시한/마감알림말 데이터 보기
	 * @throws Exception 
	 */
	public CollprocBean getDocState(int sysdocno) throws Exception{
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		CollprocBean Bean = new CollprocBean();
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT A.CHRGUSRCD, A.DOCSTATE, B.CCDNAME,	\n");
			selectQuery.append("       SUBSTR(A.ENDDT,1,4)||'년'||SUBSTR(A.ENDDT,6,2)||'월'||SUBSTR(A.ENDDT,9,2)||'일 '||SUBSTR(A.ENDDT,12,2)||'시'||SUBSTR(A.ENDDT,15,2)||'분' ENDDT, \n");
			selectQuery.append("       A.ENDCOMMENT, \n");
			selectQuery.append("       SUBSTR(A.DELIVERYDT,1,4)||'년'||SUBSTR(A.DELIVERYDT,6,2)||'월'||SUBSTR(A.DELIVERYDT,9,2)||'일 '||SUBSTR(A.DELIVERYDT,12,2)||'시'||SUBSTR(A.DELIVERYDT,15,2)||'분' DELIVERYDT \n");
			selectQuery.append("  FROM DOCMST A, CCD B 			\n");
			selectQuery.append(" WHERE A.DOCSTATE = B.CCDSUBCD	\n");
			selectQuery.append("   AND A.SYSDOCNO = ?			\n");
			selectQuery.append("   AND B.CCDCD = '003'			\n");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				Bean.setChrgusrcd(rs.getString("CHRGUSRCD"));
				Bean.setDocstate(rs.getString("DOCSTATE"));
				Bean.setDocstatenm(rs.getString("CCDNAME"));
				Bean.setEnddt(rs.getString("ENDDT"));
				Bean.setEndcomment(rs.getString("ENDCOMMENT"));
				Bean.setDeliverydt(rs.getString("DELIVERYDT"));
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return Bean;
	}	
	
	/**
	 * 최근취합문서 - 관리자인경우 검색 조건에 해당하는 값 가져오기
	 */
	public String getSearchDoc(String searchvalue, String sch_deptcd, String sch_userid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
			
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT NVL(SUM(COUNT(*)),0) AS CNT			\n");
			selectQuery.append(" FROM DOCMST A			                 	\n");
			selectQuery.append(" WHERE A.DELYN = '0'                     	\n");	
			selectQuery.append(" AND A.COLDEPTCD LIKE  '%"+sch_deptcd+"%' 	\n");
			selectQuery.append(" AND A.CHRGUSRCD LIKE  '%"+sch_userid+"%'   \n");
			selectQuery.append(" AND A.DOCTITLE LIKE '%"+searchvalue+"%'    \n");
			selectQuery.append(" GROUP BY A.COLDEPTNM, A.CHRGUSRNM	        \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("CNT") == 1){
					ConnectionManager.close(pstmt, rs);
					
					if(!"".equals(sch_deptcd)&& sch_deptcd != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT COLDEPTCD							\n");
						selectQuery.append(" FROM DOCMST A			                 	\n");
						selectQuery.append(" WHERE A.DELYN = '0'                     	\n");
						selectQuery.append(" AND A.COLDEPTCD LIKE '%"+sch_deptcd+"%'  	\n");
						selectQuery.append(" AND A.DOCTITLE LIKE '%"+searchvalue+"%'    \n");

						pstmt = con.prepareStatement(selectQuery.toString());
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							result = rs.getString("COLDEPTCD");
						}
						ConnectionManager.close(pstmt, rs);
					}
					
					if(!"".equals(sch_userid)&& sch_userid != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT CHRGUSRCD							\n");
						selectQuery.append(" FROM DOCMST A			                 	\n");
						selectQuery.append(" WHERE A.DELYN = '0'                     	\n");
						selectQuery.append(" AND A.CHRGUSRCD LIKE '%"+sch_userid+"%'  	\n");
						selectQuery.append(" AND A.DOCTITLE LIKE '%"+searchvalue+"%'    \n");

						pstmt = con.prepareStatement(selectQuery.toString());
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							result = rs.getString("CHRGUSRCD");
						}
						ConnectionManager.close(pstmt, rs);
					}
				}
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
	
	/**
	 * 진행중인취합문서 - 관리자인경우 검색 조건에 해당하는 값 가져오기
	 */
	public String getSearchProc(String docstate, String sch_deptcd, String sch_userid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
			
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT NVL(SUM(COUNT(*)),0) AS CNT			\n");
			selectQuery.append(" FROM DOCMST A			                 	\n");
			
			if(docstate.equals("0")) selectQuery.append(" WHERE A.DOCSTATE IN  ('02','03','04','05') 		\n");
			else if(docstate.equals("1")) selectQuery.append(" WHERE A.DOCSTATE IN  ('02','03') 			\n");
			else if(docstate.equals("2")) selectQuery.append(" WHERE A.DOCSTATE =  '04' 					\n");
			else selectQuery.append("  WHERE A.DOCSTATE =  '05' 											\n");
			
			selectQuery.append(" AND A.COLDEPTCD LIKE  '%"+sch_deptcd+"%' 	\n");
			selectQuery.append(" AND A.CHRGUSRCD LIKE  '%"+sch_userid+"%'   \n");
			selectQuery.append(" GROUP BY A.COLDEPTNM, A.CHRGUSRNM	        \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("CNT") == 1){
					ConnectionManager.close(pstmt, rs);
					
					if(!"".equals(sch_deptcd)&& sch_deptcd != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT COLDEPTCD							\n");
						selectQuery.append(" FROM DOCMST A			                 	\n");
						
						if(docstate.equals("0")) selectQuery.append(" WHERE A.DOCSTATE IN  ('02','03','04','05') 		\n");
						else if(docstate.equals("1")) selectQuery.append(" WHERE A.DOCSTATE IN  ('02','03') 			\n");
						else if(docstate.equals("2")) selectQuery.append(" WHERE A.DOCSTATE =  '04' 					\n");
						else selectQuery.append("  WHERE A.DOCSTATE =  '05' 											\n");
						
						selectQuery.append(" AND A.COLDEPTCD LIKE '%"+sch_deptcd+"%'  	\n");

						pstmt = con.prepareStatement(selectQuery.toString());
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							result = rs.getString("COLDEPTCD");
						}
						ConnectionManager.close(pstmt, rs);
					}
					
					if(!"".equals(sch_userid)&& sch_userid != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT CHRGUSRID							\n");
						selectQuery.append(" FROM DOCMST A			                 	\n");
						
						if(docstate.equals("0")) selectQuery.append(" WHERE A.DOCSTATE IN  ('02','03','04','05') 		\n");
						else if(docstate.equals("1")) selectQuery.append(" WHERE A.DOCSTATE IN  ('02','03') 			\n");
						else if(docstate.equals("2")) selectQuery.append(" WHERE A.DOCSTATE =  '04' 					\n");
						else selectQuery.append("  WHERE A.DOCSTATE =  '05' 											\n");
						
						selectQuery.append(" AND A.CHRGUSRCD LIKE '%"+sch_userid+"%'  	\n");

						pstmt = con.prepareStatement(selectQuery.toString());
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							result = rs.getString("CHRGUSRID");
						}
						ConnectionManager.close(pstmt, rs);
					}
				}
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
	

	/**
	 * 취합완료 - 관리자인경우 검색 조건에 해당하는 값 가져오기
	 */
	public String getSearchComp(String searchvalue, String selyear, String sch_deptcd, String sch_userid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
			
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT COUNT(*) AS CNT																	\n");
			selectQuery.append("  FROM DOCMST A  																		\n");
			selectQuery.append("  WHERE A.DOCTITLE like '%"+searchvalue+"%'  											\n");
			selectQuery.append("  AND TO_CHAR(TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') LIKE  '%"+selyear+"%'  	\n");		
			selectQuery.append("  AND A.COLDEPTCD LIKE  '%"+sch_deptcd+"%' 												\n");
			selectQuery.append("  AND A.CHRGUSRCD LIKE  '%"+sch_userid+"%'   											\n");	
			selectQuery.append("  AND A.DOCSTATE IN  ('06') 															\n");
			selectQuery.append("  GROUP BY A.COLDEPTNM, A.CHRGUSRNM, A.DOCTITLE	        								\n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("CNT") == 1){
					ConnectionManager.close(pstmt, rs);
					
					if(!"".equals(sch_deptcd)&& sch_deptcd != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT COLDEPTCD																		\n");
						selectQuery.append(" FROM DOCMST A			                 												\n");
						selectQuery.append("  WHERE A.DOCTITLE like '%"+searchvalue+"%'  											\n");
						selectQuery.append("  AND TO_CHAR(TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') LIKE  '%"+selyear+"%'  	\n");		
						selectQuery.append("  AND A.COLDEPTCD LIKE  '%"+sch_deptcd+"%' 												\n");	
						selectQuery.append("  AND A.DOCSTATE IN  ('06') 															\n");

						pstmt = con.prepareStatement(selectQuery.toString());
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							result = rs.getString("COLDEPTCD");
						}
						ConnectionManager.close(pstmt, rs);
					}
					
					if(!"".equals(sch_userid)&& sch_userid != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT CHRGUSRCD																		\n");
						selectQuery.append(" FROM DOCMST A			                 												\n");
						selectQuery.append("  WHERE A.DOCTITLE like '%"+searchvalue+"%'  											\n");
						selectQuery.append("  AND TO_CHAR(TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') LIKE  '%"+selyear+"%'  	\n");		
						selectQuery.append("  AND A.CHRGUSRCD LIKE '%"+sch_userid+"%'												\n");
						selectQuery.append("  AND A.DOCSTATE IN  ('06') 															\n");

						pstmt = con.prepareStatement(selectQuery.toString());
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							result = rs.getString("CHRGUSRCD");
						}
						ConnectionManager.close(pstmt, rs);
					}
				}
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
}