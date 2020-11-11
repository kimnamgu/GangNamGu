/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 전자결재 dao
 * 설명:
 */
package nexti.ejms.elecAppr.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;

public class ElecApprDAO {
	
	private static Logger logger = Logger.getLogger(ElecApprDAO.class);
	
	/**
	 * 양식결재번호가져오기
	 */
	public int getNextSancSeq() throws Exception {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			String query;
			
			query = "SELECT SANCSEQ.NEXTVAL FROM DUAL";
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
	
		return result;
	}
	
	/**
	 * 결재정보 가져오기
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public ElecApprBean selectColldocSancInfo(int sysdocno, String tgtdeptcd, String inputusrid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		ElecApprBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT SYSDOCNO, TGTDEPTCD, INPUTUSRID, SEQ, GUBUN, SANCRESULT, SANCUSRID, \n");
			sql.append("       SANCUSRNM, SANCYN, SANCDT, CRTUSRID, UPTUSRID, SUBMITDT, \n");
			sql.append("       TO_CHAR(TO_DATE(CRTDT, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY\"년 \"MM\"월 \"DD\"일') CRTDT, \n");
			sql.append("       TO_CHAR(TO_DATE(UPTDT, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY\"년 \"MM\"월 \"DD\"일') UPTDT \n");
			sql.append("FROM SANCTGT \n");
			sql.append("WHERE SYSDOCNO = ? \n");
			sql.append("AND TGTDEPTCD = ? \n");
			sql.append("AND INPUTUSRID = ? \n");
			sql.append("ORDER BY SEQ DESC \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, tgtdeptcd);
			pstmt.setString(3, inputusrid);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new ElecApprBean();
				result.setSysdocno(rs.getInt("SYSDOCNO"));
				result.setTgtdeptcd(rs.getString("TGTDEPTCD"));
				result.setInputusrid(rs.getString("INPUTUSRID"));
				result.setSeq(rs.getInt("SEQ"));
				result.setGubun(rs.getString("GUBUN"));
				result.setSancresult(rs.getString("SANCRESULT"));
				result.setSancusrid(rs.getString("SANCUSRID"));
				result.setSancusrnm(rs.getString("SANCUSRNM"));
				result.setSancyn(rs.getString("SANCYN"));
				result.setSancdt(rs.getString("SANCDT"));
				result.setCrtusrid(rs.getString("CRTUSRID"));
				result.setUptusrid(rs.getString("UPTUSRID"));
				result.setSubmitdt(rs.getString("SUBMITDT"));
				result.setCrtdt(rs.getString("CRTDT"));
				result.setUptdt(rs.getString("UPTDT"));
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
	
		return result;
	}
	
	/**
	 * 결재정보 가져오기
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public ElecApprBean selectColldocSancInfo(int seq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		ElecApprBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT SYSDOCNO, TGTDEPTCD, INPUTUSRID, SEQ, GUBUN, SANCRESULT, SANCUSRID, \n");
			sql.append("       SANCUSRNM, SANCYN, SANCDT, CRTUSRID, UPTUSRID, SUBMITDT, \n");
			sql.append("       TO_CHAR(TO_DATE(CRTDT, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY\"년 \"MM\"월 \"DD\"일') CRTDT, \n");
			sql.append("       TO_CHAR(TO_DATE(UPTDT, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY\"년 \"MM\"월 \"DD\"일') UPTDT \n");
			sql.append("FROM SANCTGT \n");
			sql.append("WHERE SEQ = ? \n");
			sql.append("ORDER BY SEQ DESC \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, seq);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new ElecApprBean();
				result.setSysdocno(rs.getInt("SYSDOCNO"));
				result.setTgtdeptcd(rs.getString("TGTDEPTCD"));
				result.setInputusrid(rs.getString("INPUTUSRID"));
				result.setSeq(rs.getInt("SEQ"));
				result.setGubun(rs.getString("GUBUN"));
				result.setSancresult(rs.getString("SANCRESULT"));
				result.setSancusrid(rs.getString("SANCUSRID"));
				result.setSancusrnm(rs.getString("SANCUSRNM"));
				result.setSancyn(rs.getString("SANCYN"));
				result.setSancdt(rs.getString("SANCDT"));
				result.setCrtusrid(rs.getString("CRTUSRID"));
				result.setUptusrid(rs.getString("UPTUSRID"));
				result.setSubmitdt(rs.getString("SUBMITDT"));
				result.setCrtdt(rs.getString("CRTDT"));
				result.setUptdt(rs.getString("UPTDT"));
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
	
		return result;
	}
	
	/**
	 * 결재정보 입력
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public int insertColldocSancInfo(ElecApprBean eaBean) throws Exception {
		Connection con = null;
		PreparedStatement pstmt= null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("DELETE SANCTGT \n");
			sql.append("WHERE SYSDOCNO = ? \n");
			sql.append("AND TGTDEPTCD = ? \n");
			sql.append("AND INPUTUSRID = ? \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, eaBean.getSysdocno());
			pstmt.setString(2, eaBean.getTgtdeptcd());
			pstmt.setString(3, eaBean.getInputusrid());
			
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);

			sql.delete(0, sql.capacity());
			sql.append("INSERT INTO SANCTGT(SYSDOCNO, TGTDEPTCD, INPUTUSRID, SEQ, SANCYN, SUBMITDT, CRTDT, CRTUSRID, UPTDT, UPTUSRID) \n");
			sql.append("VALUES(?, ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), \n");
			sql.append("       ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ?) \n");
			
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, eaBean.getSysdocno());
			pstmt.setString(2, eaBean.getTgtdeptcd());
			pstmt.setString(3, eaBean.getInputusrid());
			pstmt.setInt(4, eaBean.getSeq());
			pstmt.setString(5, eaBean.getSancyn());
			pstmt.setString(6, eaBean.getCrtusrid());
			pstmt.setString(7, eaBean.getUptusrid());
			
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt);
		}
	
		return result;
	}
	
	/**
	 * 결재정보 수정
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public int updateColldocSancInfo(ElecApprBean eaBean) throws Exception {
		Connection con = null;
		PreparedStatement pstmt= null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("UPDATE SANCTGT \n");
			sql.append("SET GUBUN = ?, SANCRESULT = ?, SANCUSRID = ?, SANCUSRNM = ?, SANCYN = ?, \n");
			sql.append("    SANCDT = ?, UPTUSRID = ?, \n");
			sql.append("    UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') \n");
			sql.append("WHERE SEQ = ? \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setString(1, eaBean.getGubun());
			pstmt.setString(2, eaBean.getSancresult());
			pstmt.setString(3, eaBean.getSancusrid());
			pstmt.setString(4, eaBean.getSancusrnm());
			pstmt.setString(5, eaBean.getSancyn());
			pstmt.setString(6, eaBean.getSancdt());
			pstmt.setString(7, eaBean.getUptusrid());
			pstmt.setInt(8, eaBean.getSeq());
			
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt);
		}
	
		return result;
	}
	
	/**
	 * 결재정보 가져오기
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public ElecApprBean selectRequestSancInfo(int reqformno, int reqseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		ElecApprBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT REQFORMNO, REQSEQ, SEQ, GUBUN, SANCRESULT, SANCUSRID, \n");
			sql.append("       SANCUSRNM, SANCYN, SANCDT, CRTUSRID, UPTUSRID, SUBMITDT, \n");
			sql.append("       TO_CHAR(TO_DATE(CRTDT, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY\"년 \"MM\"월 \"DD\"일') CRTDT, \n");
			sql.append("       TO_CHAR(TO_DATE(UPTDT, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY\"년 \"MM\"월 \"DD\"일') UPTDT \n");
			sql.append("FROM REQSANC \n");
			sql.append("WHERE REQFORMNO = ? \n");
			sql.append("AND REQSEQ = ? \n");
			sql.append("ORDER BY SEQ DESC \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new ElecApprBean();
				result.setReqformno(rs.getInt("REQFORMNO"));
				result.setReqseq(rs.getInt("REQSEQ"));
				result.setSeq(rs.getInt("SEQ"));
				result.setGubun(rs.getString("GUBUN"));
				result.setSancresult(rs.getString("SANCRESULT"));
				result.setSancusrid(rs.getString("SANCUSRID"));
				result.setSancusrnm(rs.getString("SANCUSRNM"));
				result.setSancyn(rs.getString("SANCYN"));
				result.setSancdt(rs.getString("SANCDT"));
				result.setCrtusrid(rs.getString("CRTUSRID"));
				result.setUptusrid(rs.getString("UPTUSRID"));
				result.setSubmitdt(rs.getString("SUBMITDT"));
				result.setCrtdt(rs.getString("CRTDT"));
				result.setUptdt(rs.getString("UPTDT"));
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
	
		return result;
	}
	
	/**
	 * 결재정보 가져오기
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public ElecApprBean selectRequestSancInfo(int seq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		ElecApprBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT REQFORMNO, REQSEQ, SEQ, GUBUN, SANCRESULT, SANCUSRID, \n");
			sql.append("       SANCUSRNM, SANCYN, SANCDT, CRTUSRID, UPTUSRID, SUBMITDT, \n");
			sql.append("       TO_CHAR(TO_DATE(CRTDT, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY\"년 \"MM\"월 \"DD\"일') CRTDT, \n");
			sql.append("       TO_CHAR(TO_DATE(UPTDT, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY\"년 \"MM\"월 \"DD\"일') UPTDT \n");
			sql.append("FROM REQSANC \n");
			sql.append("WHERE SEQ = ? \n");
			sql.append("ORDER BY SEQ DESC \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, seq);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new ElecApprBean();
				result.setReqformno(rs.getInt("REQFORMNO"));
				result.setReqseq(rs.getInt("REQSEQ"));
				result.setSeq(rs.getInt("SEQ"));
				result.setGubun(rs.getString("GUBUN"));
				result.setSancresult(rs.getString("SANCRESULT"));
				result.setSancusrid(rs.getString("SANCUSRID"));
				result.setSancusrnm(rs.getString("SANCUSRNM"));
				result.setSancyn(rs.getString("SANCYN"));
				result.setSancdt(rs.getString("SANCDT"));
				result.setCrtusrid(rs.getString("CRTUSRID"));
				result.setUptusrid(rs.getString("UPTUSRID"));
				result.setSubmitdt(rs.getString("SUBMITDT"));
				result.setCrtdt(rs.getString("CRTDT"));
				result.setUptdt(rs.getString("UPTDT"));
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
	
		return result;
	}
	
	/**
	 * 결재정보 입력
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public int insertRequestSancInfo(ElecApprBean eaBean) throws Exception {
		Connection con = null;
		PreparedStatement pstmt= null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("DELETE REQSANC \n");
			sql.append("WHERE REQFORMNO = ? \n");
			sql.append("AND REQSEQ = ? \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, eaBean.getReqformno());
			pstmt.setInt(2, eaBean.getReqseq());
			
			pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			sql.delete(0, sql.capacity());
			sql.append("INSERT INTO REQSANC(REQFORMNO, REQSEQ, SEQ, SANCYN, SUBMITDT, CRTDT, CRTUSRID, UPTDT, UPTUSRID) \n");
			sql.append("VALUES(?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), \n");
			sql.append("       ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ?) \n");
			
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, eaBean.getReqformno());
			pstmt.setInt(2, eaBean.getReqseq());
			pstmt.setInt(3, eaBean.getSeq());
			pstmt.setString(4, eaBean.getSancyn());
			pstmt.setString(5, eaBean.getCrtusrid());
			pstmt.setString(6, eaBean.getUptusrid());
			
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt);
		}
	
		return result;
	}
	
	/**
	 * 결재정보 수정
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public int updateRequestSancInfo(ElecApprBean eaBean) throws Exception {
		Connection con = null;
		PreparedStatement pstmt= null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE REQSANC \n");
			sql.append("SET GUBUN = ?, SANCRESULT = ?, SANCUSRID = ?, SANCUSRNM = ?, SANCYN = ?, \n");
			sql.append("    SANCDT = ?, UPTUSRID = ?, \n");
			sql.append("    UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') \n");
			sql.append("WHERE SEQ = ? \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setString(1, eaBean.getGubun());
			pstmt.setString(2, eaBean.getSancresult());
			pstmt.setString(3, eaBean.getSancusrid());
			pstmt.setString(4, eaBean.getSancusrnm());
			pstmt.setString(5, eaBean.getSancyn());
			pstmt.setString(6, eaBean.getSancdt());
			pstmt.setString(7, eaBean.getUptusrid());
			pstmt.setInt(8, eaBean.getSeq());
			
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt);
		}
	
		return result;
	}
	
	/**
	 * 결재건이 있는지 체크
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public boolean isSancneed() throws Exception {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			con = ConnectionManager.getConnection(false);
			
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE SANCTGT \n");
			sql.append("SET SANCYN = 1, SANCRESULT = '삭제된문서' \n");
			sql.append("WHERE (SYSDOCNO, TGTDEPTCD, INPUTUSRID) \n");
			sql.append("      IN (SELECT SYSDOCNO, TGTDEPTCD, INPUTUSRID \n");
			sql.append("          FROM SANCTGT \n");
			sql.append("          WHERE SANCYN != 1 \n");
			sql.append("          MINUS \n");
			sql.append("          SELECT D.SYSDOCNO, TGTDEPTCD, INPUTUSRID \n");
			sql.append("          FROM DOCMST D, TGTDEPT T, INPUTUSR I \n");
			sql.append("          WHERE D.SYSDOCNO = T.SYSDOCNO \n");
			sql.append("          AND T.SYSDOCNO = I.SYSDOCNO \n");
			sql.append("          AND T.TGTDEPTCD = I.TGTDEPT) \n");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			
			ConnectionManager.close(pstmt);
			sql.delete(0, sql.capacity());
			sql.append("UPDATE REQSANC \n");
			sql.append("SET SANCYN = 1, SANCRESULT = '삭제된문서' \n");
			sql.append("WHERE (REQFORMNO, REQSEQ) \n");
			sql.append("      IN (SELECT REQFORMNO, REQSEQ \n");
			sql.append("          FROM REQSANC \n");
			sql.append("          WHERE SANCYN != 1 \n");
			sql.append("          MINUS \n");
			sql.append("          SELECT R.REQFORMNO, REQSEQ \n");
			sql.append("          FROM REQFORMMST R, REQMST RM \n");
			sql.append("          WHERE R.REQFORMNO = RM.REQFORMNO) \n");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			
			ConnectionManager.close(pstmt);
			sql.delete(0, sql.capacity());
			sql.append("SELECT COUNT(*) FROM SANCTGT WHERE SANCYN != 1");
			
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				if ( rs.getInt(1) > 0 ) {
					result = true;
				}
			}
			
			ConnectionManager.close(pstmt, rs);
			sql.delete(0, sql.capacity());
			sql.append("SELECT COUNT(*) FROM REQSANC WHERE SANCYN != 1");
			
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				if ( rs.getInt(1) > 0 ) {
					result = true;
				}
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
	
		return result;
	}
	
	/**
	 * 취합문서 결재건이 있는지 체크
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public boolean isColldocSancneed(int seq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT COUNT(*) FROM SANCTGT WHERE SANCYN = 0 AND SEQ = ?");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, seq);
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				if ( rs.getInt(1) > 0 ) {
					result = true;
				}
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
	
		return result;
	}
	
	/**
	 * 신청서 결재건이 있는지 체크
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public boolean isRequestSancneed(int seq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT COUNT(*) FROM REQSANC WHERE SANCYN = 0 AND SEQ = ?");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, seq);
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				if ( rs.getInt(1) > 0 ) {
					result = true;
				}
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
	
		return result;
	}
}
