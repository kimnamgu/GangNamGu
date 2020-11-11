/**rchExamList
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 dao
 * 설명:
 */
package nexti.ejms.research.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import nexti.ejms.commapproval.model.commapprovalBean;
import nexti.ejms.commapproval.model.commapprovalManager;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.research.model.ResearchSubBean;
import nexti.ejms.research.model.ResearchExamBean;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.FileManager;
import nexti.ejms.util.Utils;

public class ResearchDAO2 {
	
	private static Logger logger = Logger.getLogger(ResearchDAO2.class);
	
	/**
	 * 문항순서 변경
	 * @param rchno
	 * @param sessionId
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public int changeFormseq(int rchno, String sessionId, int[] formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {			
			con = ConnectionManager.getConnection(false);
			
			String[][] tableName = {{"RCHSUB", "RCHSUBFILE", "RCHEXAM", "RCHEXAMFILE"},
									{"RCHSUB_TEMP", "RCHSUBFILE_TEMP", "RCHEXAM_TEMP", "RCHEXAMFILE_TEMP"}};
			
			//문항번호에 +50 한 후 변경할 번호로 업데이트 : 두번 돌아야함
			int MAX_FORMSEQ_CNT = 60;
			for ( int repeat = 0; repeat <= MAX_FORMSEQ_CNT; repeat += MAX_FORMSEQ_CNT ) {
				for ( int i = 0; i < formseq.length; i++ ) {
					int newFormseq = 0;
					int oldFormseq = 0;
					if ( repeat == 0 ) {
						newFormseq = i + 1 + MAX_FORMSEQ_CNT;
						oldFormseq = i + 1;
					} else {
						newFormseq = formseq[i];
						oldFormseq = i + 1 + MAX_FORMSEQ_CNT;
					}
					
					if ( rchno == 0 ) {
						for ( int j = 0;  j < tableName[1].length; j++ ) {
							StringBuffer sql = new StringBuffer();
							sql.append("UPDATE " + tableName[1][j] + "\n");
							sql.append("SET FORMSEQ = ?\n");
							sql.append("WHERE SESSIONID LIKE ?\n");
							sql.append("AND FORMSEQ = ?\n");
							
							pstmt = con.prepareStatement(sql.toString());
							pstmt.setInt(1, newFormseq);
							pstmt.setString(2, sessionId);
							pstmt.setInt(3, oldFormseq);
							
							result += pstmt.executeUpdate();
							pstmt.clearParameters();
							pstmt.close();
						}
					} else {
						for ( int j = 0;  j < tableName[0].length; j++ ) {
							StringBuffer sql = new StringBuffer();
							sql.append("UPDATE " + tableName[0][j] + "\n");
							sql.append("SET FORMSEQ = ?\n");
							sql.append("WHERE RCHNO = ?\n");
							sql.append("AND FORMSEQ = ?\n");
							
							pstmt = con.prepareStatement(sql.toString());
							pstmt.setInt(1, newFormseq);
							pstmt.setInt(2, rchno);
							pstmt.setInt(3, oldFormseq);
							
							result += pstmt.executeUpdate();
							pstmt.clearParameters();
							pstmt.close();
						}
					}
				}
			}
			
			con.commit();
		} catch ( Exception e ) {
			try { con.rollback(); } catch ( Exception ex) {}
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt);
			throw e;
		} finally {
			try { con.setAutoCommit(true); } catch ( Exception ex) {}
			ConnectionManager.close(con, pstmt);
		}
		
		return result;
	}
	
	public int getRchSubCount(int rchno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) \n");
			sql.append("FROM RCHSUB \n");
			sql.append("WHERE RCHNO = ? \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, rchno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	public List getRchIndividualList(int rchno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ANSUSRSEQ, R.CRTUSRID, CRTUSRNM, DEPT_ID, DEPT_FULLNAME, CLASS_NAME \n");
			sql.append("FROM RCHANS R, USR U \n");
			sql.append("WHERE R.CRTUSRID = U.USER_ID(+) \n");
			sql.append("AND RCHNO = ? \n");
			sql.append("GROUP BY ANSUSRSEQ, R.CRTUSRID, CRTUSRNM, DEPT_ID, DEPT_FULLNAME, CLASS_NAME \n");
			sql.append("ORDER BY ANSUSRSEQ \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, rchno);
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				HashMap hs = new HashMap();
				hs.put("ANSUSRSEQ", Utils.nullToEmptyString(rs.getString("ANSUSRSEQ")));
				hs.put("USER_ID", Utils.nullToEmptyString(rs.getString("CRTUSRID")));
				hs.put("USER_NAME", Utils.nullToEmptyString(rs.getString("CRTUSRNM")));
				hs.put("DEPT_ID", Utils.nullToEmptyString(rs.getString("DEPT_ID")));
				hs.put("DEPT_NAME", Utils.nullToEmptyString(rs.getString("DEPT_FULLNAME")));
				hs.put("CLASS_NAME", Utils.nullToEmptyString(rs.getString("CLASS_NAME")));
				hs.put("ANSLIST", getRchIndividualAnsList(con, rchno, rs.getInt("ANSUSRSEQ")));
				
				if ( result == null ) result = new ArrayList();
				result.add(hs);
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	public List getRchIndividualAnsList(Connection con, int rchno, int ansusrseq) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ANSUSRSEQ, FORMSEQ, ANSCONT, OTHER \n");
			sql.append("FROM RCHANS R, USR U \n");
			sql.append("WHERE R.CRTUSRID = U.USER_ID(+) \n");
			sql.append("AND RCHNO = ? \n");
			sql.append("AND ANSUSRSEQ = ? \n");
			sql.append("ORDER BY ANSUSRSEQ, FORMSEQ \n");
			
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, rchno);
			pstmt.setInt(2, ansusrseq);
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				HashMap hs = new HashMap();
				hs.put("ANSUSRSEQ", Utils.nullToEmptyString(rs.getString("ANSUSRSEQ")));
				hs.put("FORMSEQ", Utils.nullToEmptyString(rs.getString("FORMSEQ")));
				hs.put("ANSCONT", Utils.nullToEmptyString(rs.getString("ANSCONT")));
				hs.put("OTHER", Utils.nullToEmptyString(rs.getString("OTHER")));
				
				if ( result == null ) result = new ArrayList();
				result.add(hs);
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
	
	public List getRchObjectResultList(Connection con, int rchno) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT RE.FORMSEQ, RE.EXAMSEQ, NVL(ANSCONT, 0) ANSCONT, ROUND(NVL(ANSCONT, 0) / ANSUSRSEQ_CNT * 100) || '%' RATE \n");
			sql.append("FROM RCHSUB RS, RCHEXAM RE, (SELECT COUNT(DISTINCT ANSUSRSEQ) ANSUSRSEQ_CNT FROM RCHANS WHERE RCHNO = ?), \n");
			sql.append("     (SELECT RE.RCHNO, RE.FORMSEQ, RE.EXAMSEQ, COUNT(ANSCONT) ANSCONT \n");
			sql.append("      FROM RCHEXAM RE, RCHANS RA \n");
			sql.append("      WHERE RE.RCHNO = RA.RCHNO \n");
			sql.append("      AND RE.FORMSEQ = RA.FORMSEQ \n");
			sql.append("      AND TO_CHAR(RE.EXAMSEQ) IN ( \n");
			sql.append("          SELECT SUBSTR(',' || RA.ANSCONT || ',', \n");
			sql.append("                        INSTR(',' || RA.ANSCONT || ',', ',', 1, LEVEL) + 1, \n");
			sql.append("                        INSTR(',' || RA.ANSCONT || ',', ',', 1, LEVEL + 1) - INSTR(',' || RA.ANSCONT || ',', ',', 1, LEVEL) - 1) SUB \n");
			sql.append("          FROM DUAL \n");
			sql.append("          CONNECT BY LEVEL <= LENGTH(',' || RA.ANSCONT || ',') - LENGTH(REPLACE(',' || RA.ANSCONT || ',', ',')) - 1 \n");
			sql.append("          ) \n");
			sql.append("      AND RE.RCHNO = ? \n");
			sql.append("      GROUP BY RE.RCHNO, RE.FORMSEQ, RE.EXAMSEQ) RA \n");
			sql.append("WHERE RS.RCHNO = RE.RCHNO \n");
			sql.append("AND RS.FORMSEQ = RE.FORMSEQ \n");
			sql.append("AND RE.RCHNO = RA.RCHNO(+) \n");
			sql.append("AND RE.FORMSEQ = RA.FORMSEQ(+) \n");
			sql.append("AND RE.EXAMSEQ = RA.EXAMSEQ(+) \n");
			sql.append("AND RS.FORMTYPE IN ('01', '02') \n");
			sql.append("AND RS.RCHNO = ? \n");
			sql.append("ORDER BY RS.FORMSEQ, RE.EXAMSEQ \n");
			
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, rchno);
			pstmt.setInt(2, rchno);
			pstmt.setInt(3, rchno);
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				HashMap hs = new HashMap();
				hs.put("FORMSEQ", Utils.nullToEmptyString(rs.getString("FORMSEQ")));
				hs.put("ANSCONT", Utils.nullToEmptyString(rs.getString("ANSCONT")));
				hs.put("RATE", Utils.nullToEmptyString(rs.getString("RATE")));
				
				if ( result == null ) result = new ArrayList();
				result.add(hs);
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
	
	public int getRchObjectResultFormseqMaxCount(Connection con, int rchno) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(DISTINCT RS.FORMSEQ) \n");
			sql.append("FROM RCHSUB RS, RCHEXAM RE \n");
			sql.append("WHERE RS.RCHNO = RE.RCHNO \n");
			sql.append("AND RS.FORMSEQ = RE.FORMSEQ \n");
			sql.append("AND RS.FORMTYPE IN ('01', '02') \n");
			sql.append("AND RS.RCHNO = ? \n");
			
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, rchno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {
				result = rs.getInt(1);
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
	
	public int getRchObjectResultExamseqMaxCount(Connection con, int rchno) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(DISTINCT RE.EXAMSEQ) \n");
			sql.append("FROM RCHSUB RS, RCHEXAM RE \n");
			sql.append("WHERE RS.RCHNO = RE.RCHNO \n");
			sql.append("AND RS.FORMSEQ = RE.FORMSEQ \n");
			sql.append("AND RS.FORMTYPE IN ('01', '02') \n");
			sql.append("AND RS.RCHNO = ? \n");
			
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, rchno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {
				result = rs.getInt(1);
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
	
	public List getRchSubjectResultList(Connection con, int rchno) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT RS.FORMSEQ, \n");
			sql.append("       CASE WHEN RS.FORMTYPE IN ('01', '02') AND RS.EXAMTYPE = 'Y' AND RA.OTHER IS NOT NULL THEN OTHER \n");
			sql.append("            WHEN RS.FORMTYPE IN ('03', '04') AND RA.ANSCONT IS NOT NULL THEN ANSCONT END ANSCONT \n");
			sql.append("FROM RCHSUB RS, RCHANS RA \n");
			sql.append("WHERE RS.RCHNO = RA.RCHNO \n");
			sql.append("AND RS.FORMSEQ = RA.FORMSEQ \n");
			sql.append("AND ((RS.FORMTYPE IN ('01', '02') AND RS.EXAMTYPE = 'Y' AND RA.OTHER IS NOT NULL) \n");
			sql.append("     OR (RS.FORMTYPE IN ('03', '04') AND RA.ANSCONT IS NOT NULL)) \n");
			sql.append("AND RS.RCHNO = ? \n");
			sql.append("ORDER BY RS.FORMSEQ, RA.ANSUSRSEQ \n");
			
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, rchno);
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				HashMap hs = new HashMap();
				hs.put("FORMSEQ", Utils.nullToEmptyString(rs.getString("FORMSEQ")));
				hs.put("ANSCONT", Utils.nullToEmptyString(rs.getString("ANSCONT")));
				
				if ( result == null ) result = new ArrayList();
				result.add(hs);
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
	
	public int getRchSubjectResultFormseqCount(Connection con, int rchno) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(DISTINCT RS.FORMSEQ) \n");
			sql.append("FROM RCHSUB RS, RCHANS RA \n");
			sql.append("WHERE RS.RCHNO = RA.RCHNO \n");
			sql.append("AND RS.FORMSEQ = RA.FORMSEQ \n");
			sql.append("AND ((RS.FORMTYPE IN ('01', '02') AND RS.EXAMTYPE = 'Y' AND RA.OTHER IS NOT NULL) \n");
			sql.append("     OR (RS.FORMTYPE IN ('03', '04') AND RA.ANSCONT IS NOT NULL)) \n");
			sql.append("AND RS.RCHNO = ? \n");
			
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, rchno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {
				result = rs.getInt(1);
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
	
	public int getRchSubjectResultExamseqMaxCount(Connection con, int rchno) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT MAX(COUNT(RS.FORMSEQ)) \n");
			sql.append("FROM RCHSUB RS, RCHANS RA \n");
			sql.append("WHERE RS.RCHNO = RA.RCHNO \n");
			sql.append("AND RS.FORMSEQ = RA.FORMSEQ \n");
			sql.append("AND ((RS.FORMTYPE IN ('01', '02') AND RS.EXAMTYPE = 'Y' AND RA.OTHER IS NOT NULL) \n");
			sql.append("     OR (RS.FORMTYPE IN ('03', '04') AND RA.ANSCONT IS NOT NULL)) \n");
			sql.append("AND RS.RCHNO = ? \n");
			sql.append("GROUP BY RS.FORMSEQ \n");
			
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, rchno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {
				result = rs.getInt(1);
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
	 * 설문문항첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List getRchSubFile(Connection conn, String sessionId, int rchno) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM RCHSUBFILE_TEMP \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
			} else {
				sql.append("SELECT RCHNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM RCHSUBFILE \n");
				sql.append("WHERE RCHNO=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				ResearchSubBean rchSubBean = new ResearchSubBean();
				
				rchSubBean.setSessionId(sessionId);
				rchSubBean.setRchno(rchno);
				rchSubBean.setFormseq(rs.getInt("FORMSEQ"));
				rchSubBean.setFileseq(rs.getInt("FILESEQ"));
				rchSubBean.setFilenm(rs.getString("FILENM"));
				rchSubBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				rchSubBean.setFilesize(rs.getInt("FILESIZE"));
				rchSubBean.setExt(rs.getString("EXT"));				
				rchSubBean.setOrd(rs.getInt("ORD"));				
				
				if ( result == null ) {
					result = new ArrayList();
				}
				result.add(rchSubBean);
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
	 * 설문문항보기첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public List getRchExamFile(Connection conn, String sessionId, int rchno, int formseq) throws Exception {
	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM RCHEXAMFILE_TEMP \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
			} else {
				sql.append("SELECT RCHNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM RCHEXAMFILE \n");
				sql.append("WHERE RCHNO=? \n");
			}
			if ( formseq != 0 ) {
				sql.append("AND FORMSEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
			if ( formseq != 0 ) {
				pstmt.setInt(2, formseq);
			}
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				ResearchExamBean rchExamBean = new ResearchExamBean();
				
				rchExamBean.setSessionId(sessionId);
				rchExamBean.setRchno(rchno);
				rchExamBean.setFormseq(rs.getInt("FORMSEQ"));
				rchExamBean.setExamseq(rs.getInt("EXAMSEQ"));
				rchExamBean.setFileseq(rs.getInt("FILESEQ"));
				rchExamBean.setFilenm(rs.getString("FILENM"));
				rchExamBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				rchExamBean.setFilesize(rs.getInt("FILESIZE"));
				rchExamBean.setExt(rs.getString("EXT"));				
				rchExamBean.setOrd(rs.getInt("ORD"));
				
				if ( result == null ) {
					result = new ArrayList();
				}
				result.add(rchExamBean);
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
	 * 보기파일 삭제 후 순서정렬(1,3,5->1,2,3)
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public int setOrderRchExamFile(Connection conn, String sessionId, int rchno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("UPDATE RCHEXAMFILE_TEMP R \n");
				sql.append("SET R.EXAMSEQ = (SELECT NEWEXAMSEQ \n");
				sql.append("                 FROM (SELECT ROWNUM NEWEXAMSEQ, EXAMSEQ \n");
				sql.append("                       FROM (SELECT SESSIONID, FORMSEQ, EXAMSEQ \n");
				sql.append("                             FROM RCHEXAMFILE_TEMP \n");
				sql.append("                             WHERE SESSIONID LIKE ? \n");
				sql.append("                             AND FORMSEQ = ? \n");
				sql.append("                             ORDER BY EXAMSEQ)) \n");
				sql.append("                 WHERE EXAMSEQ = R.EXAMSEQ) \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ = ? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM RCHEXAM_TEMP \n");
				sql.append("                    WHERE SESSIONID LIKE ? \n"); 
				sql.append("                    AND FORMSEQ = ?) \n");
			} else {
				sql.append("UPDATE RCHEXAMFILE R \n");
				sql.append("SET R.EXAMSEQ = (SELECT NEWEXAMSEQ \n");
				sql.append("                 FROM (SELECT ROWNUM NEWEXAMSEQ, EXAMSEQ \n");
				sql.append("                       FROM (SELECT RCHNO, FORMSEQ, EXAMSEQ \n");
				sql.append("                             FROM RCHEXAMFILE \n");
				sql.append("                             WHERE RCHNO = ? \n");
				sql.append("                             AND FORMSEQ = ? \n");
				sql.append("                             ORDER BY EXAMSEQ)) \n");
				sql.append("                 WHERE EXAMSEQ = R.EXAMSEQ) \n");
				sql.append("WHERE RCHNO = ? \n");
				sql.append("AND FORMSEQ = ? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM RCHEXAM \n");
				sql.append("                    WHERE RCHNO = ? \n"); 
				sql.append("                    AND FORMSEQ = ?) \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
				pstmt.setString(3, sessionId);
				pstmt.setInt(4, formseq);
				pstmt.setString(5, sessionId);
				pstmt.setInt(6, formseq);
			} else {
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, rchno);
				pstmt.setInt(4, formseq);
				pstmt.setInt(5, rchno);
				pstmt.setInt(6, formseq);
			}
			
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
	 * 사용하지않는 설문문항보기첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public List getRchExamUnusedFile(Connection conn, String sessionId, int rchno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM RCHEXAMFILE_TEMP R \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM  RCHEXAM_TEMP \n");
				sql.append("                    WHERE SESSIONID LIKE R.SESSIONID \n");
				sql.append("                    AND FORMSEQ=R.FORMSEQ) \n");
			} else {
				sql.append("SELECT RCHNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM RCHEXAMFILE R \n");
				sql.append("WHERE RCHNO=? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM  RCHEXAM \n");
				sql.append("                    WHERE RCHNO=R.RCHNO \n");
				sql.append("                    AND FORMSEQ=R.FORMSEQ) \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
			} else {
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
			}
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				ResearchExamBean rchExamBean = new ResearchExamBean();
				
				rchExamBean.setSessionId(sessionId);
				rchExamBean.setRchno(rchno);
				rchExamBean.setFormseq(rs.getInt("FORMSEQ"));
				rchExamBean.setExamseq(rs.getInt("EXAMSEQ"));
				rchExamBean.setFileseq(rs.getInt("FILESEQ"));
				rchExamBean.setFilenm(rs.getString("FILENM"));
				rchExamBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				rchExamBean.setFilesize(rs.getInt("FILESIZE"));
				rchExamBean.setExt(rs.getString("EXT"));				
				rchExamBean.setOrd(rs.getInt("ORD"));
				
				if ( result == null ) {
					result = new ArrayList();
				}
				result.add(rchExamBean);
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
	 * 설문문항첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public ResearchSubBean getRchSubFile(Connection conn, String sessionId, int rchno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResearchSubBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM RCHSUBFILE_TEMP \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ=? \n");
			} else {
				sql.append("SELECT RCHNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM RCHSUBFILE \n");
				sql.append("WHERE RCHNO=? \n");
				sql.append("AND FORMSEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
			} else {
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
			}
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new ResearchSubBean();
				
				result.setSessionId(sessionId);
				result.setRchno(rchno);
				result.setFormseq(rs.getInt("FORMSEQ"));
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
	 * 설문문항보기첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public ResearchExamBean getRchExamFile(Connection conn, String sessionId, int rchno, int formseq, int examseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResearchExamBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM RCHEXAMFILE_TEMP \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ=? \n");
			} else {
				sql.append("SELECT RCHNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM RCHEXAMFILE \n");
				sql.append("WHERE RCHNO=? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, examseq);
			} else {
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, examseq);
			}
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new ResearchExamBean();
				
				result.setSessionId(sessionId);
				result.setRchno(rchno);
				result.setFormseq(rs.getInt("FORMSEQ"));
				result.setExamseq(rs.getInt("EXAMSEQ"));
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
	 * 설문문항첨부파일 추가
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param fileBean
	 * @return
	 * @throws Exception
	 */
	public int addRchSubFile(Connection conn, String sessionId, int rchno, int formseq, FileBean fileBean) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("INSERT INTO \n");
				sql.append("RCHSUBFILE_TEMP(SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?) \n");
			} else {				
				sql.append("INSERT INTO \n");
				sql.append("RCHSUBFILE(RCHNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?) \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, fileBean.getFileseq());
			pstmt.setString(4, fileBean.getFilenm());
			pstmt.setString(5, fileBean.getOriginfilenm());
			pstmt.setInt(6, fileBean.getFilesize());
			pstmt.setString(7, fileBean.getExt());
			pstmt.setInt(8, fileBean.getFileseq());
			
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
	 * 설문문항보기첨부파일 추가
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param examseq
	 * @param fileBean
	 * @return
	 * @throws Exception
	 */
	public int addRchExamFile(Connection conn, String sessionId, int rchno, int formseq, int examseq, FileBean fileBean) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("INSERT INTO \n");
				sql.append("RCHEXAMFILE_TEMP(SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) \n");
			} else {
				sql.append("INSERT INTO \n");
				sql.append("RCHEXAMFILE(RCHNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) \n");
			}
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, examseq);
			pstmt.setInt(4, fileBean.getFileseq());
			pstmt.setString(5, fileBean.getFilenm());
			pstmt.setString(6, fileBean.getOriginfilenm());
			pstmt.setInt(7, fileBean.getFilesize());
			pstmt.setString(8, fileBean.getExt());
			pstmt.setInt(9, fileBean.getFileseq());
			
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
	 * 설문문항첨부파일 삭제
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param fileseq
	 * @return
	 * @throws Exception
	 */
	public int delRchSubFile(Connection conn, String sessionId, int rchno, int formseq, int fileseq) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("DELETE FROM RCHSUBFILE_TEMP \n");
				sql.append("WHERE SESSIONID LIKE ? AND FORMSEQ=? AND FILESEQ=? \n");
			} else {
				sql.append("DELETE FROM RCHSUBFILE \n");
				sql.append("WHERE RCHNO=? AND FORMSEQ=? AND FILESEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, fileseq);
			
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
	 * 설문문항보기첨부파일 삭제
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param examseq
	 * @param fileseq
	 * @return
	 * @throws Exception
	 */
	public int delRchExamFile(Connection conn, String sessionId, int rchno, int formseq, int examseq, int fileseq) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("DELETE FROM RCHEXAMFILE_TEMP \n");
				sql.append("WHERE SESSIONID LIKE ? AND FORMSEQ=? AND EXAMSEQ=? AND FILESEQ=? \n");
			} else {
				sql.append("DELETE FROM RCHEXAMFILE \n");
				sql.append("WHERE RCHNO=? AND FORMSEQ=? AND EXAMSEQ=? AND FILESEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, examseq);
			pstmt.setInt(4, fileseq);
			
			pstmt.executeUpdate();
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
	 * 내설문목록
	 * @param usrid
	 * @param sch_title
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getResearchMyList(String usrid, String deptcd, String initentry, String isSysMgr, String groupyn, String sch_title, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List reserchList = null;
		int bindPos = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("\n SELECT (CNT-SEQ+1) BUNHO, SEQ, RCHNO, TITLE, RANGE, GROUPYN, STRDT, ENDDT, TDAY, CHRGUSRID ");
			selectQuery.append("\n   FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* 									");
			selectQuery.append("\n           FROM (SELECT ROWNUM SEQ, A2.* 										");
			selectQuery.append("\n		             FROM (SELECT A.RCHNO, A.TITLE, A.RANGE, A.GROUPYN, REPLACE(SUBSTR(STRDT,6),'-','/') STRDT,  ");
			selectQuery.append("\n							  REPLACE(SUBSTR(ENDDT,6),'-','/') ENDDT, TRUNC(TO_DATE(ENDDT,'YYYY-MM-DD')-SYSDATE)TDAY, CHRGUSRID ");
			selectQuery.append("\n                         FROM RCHMST A 										");
			selectQuery.append("\n                     	   WHERE GROUPYN LIKE ?									");
			
			if(initentry.equals("first")){
				selectQuery.append("	                   AND A.COLDEPTCD = ?							 	    \n");
				selectQuery.append("                       AND A.CHRGUSRID = ?  								\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) selectQuery.append("\n  AND A.COLDEPTCD LIKE ?                           \n");
					if( !"".equals(sch_userid) ) selectQuery.append("\n  AND A.CHRGUSRID LIKE ?                           \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) selectQuery.append("\n  AND A.COLDEPTNM LIKE ? \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) selectQuery.append("\n  AND A.CHRGUSRNM LIKE ? \n");
				}else{
					selectQuery.append("                     AND A.COLDEPTCD = ?								\n");
					selectQuery.append("                     AND A.CHRGUSRID = ? 								\n");
				}
			}
				
			if(!"".equals(sch_title)||sch_title != null){
				selectQuery.append("\n                     AND A.TITLE LIKE  ?								  	");
			}

			selectQuery.append("\n                          ORDER BY TDAY DESC, A.ENDDT DESC, A.CRTDT DESC, A.UPTDT DESC) A2) A1) 						");
			selectQuery.append("\n  WHERE SEQ BETWEEN ? AND ? 													");

			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setString(++bindPos, "%"+groupyn);
			
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
			
			if(!sch_title.equals("")||sch_title!= null){
				pstmt.setString(++bindPos, "%"+ sch_title +"%");
			}
			pstmt.setInt(++bindPos, start);
			pstmt.setInt(++bindPos, end);
									
			rs = pstmt.executeQuery();
			
			reserchList = new ArrayList();
			
			while (rs.next()) {
				ResearchBean Bean = new ResearchBean();
				
				Bean.setSeqno(rs.getInt("BUNHO"));
				Bean.setRchno(rs.getInt("RCHNO"));
				Bean.setTitle(rs.getString("TITLE"));
				Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				Bean.setGroupyn(rs.getString("GROUPYN"));
				Bean.setStrdt(rs.getString("STRDT"));
				Bean.setEnddt(rs.getString("ENDDT"));
				Bean.setTday(rs.getInt("TDAY"));
				Bean.setChrgusrid(rs.getString("CHRGUSRID"));
				
				reserchList.add(Bean);
				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return reserchList;
	}
	
	/**
	 * 내설문목록그룹
	 * @param usrid
	 * @param sch_title
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getResearchGrpMyList(String usrid, String deptcd, String initentry, String isSysMgr, String sch_title, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List reserchList = null;
		int bindPos = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("\n SELECT (CNT-SEQ+1) BUNHO, SEQ, RCHGRPNO, TITLE, RANGE, STRDT, ENDDT, TDAY, CHRGUSRID ");
			selectQuery.append("\n   FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* 									");
			selectQuery.append("\n           FROM (SELECT ROWNUM SEQ, A2.* 										");
			selectQuery.append("\n		             FROM (SELECT A.RCHGRPNO, A.TITLE, A.RANGE, REPLACE(SUBSTR(STRDT,6),'-','/') STRDT,  ");
			selectQuery.append("\n							  REPLACE(SUBSTR(ENDDT,6),'-','/') ENDDT, TRUNC(TO_DATE(ENDDT,'YYYY-MM-DD')-SYSDATE)TDAY, CHRGUSRID ");
			selectQuery.append("\n                         FROM RCHGRPMST A 										");
			selectQuery.append("\n                     	   WHERE 1 = 1											");
			
			if(initentry.equals("first")){
				selectQuery.append("	                   AND A.COLDEPTCD = ?							 	    \n");
				selectQuery.append("                       AND A.CHRGUSRID = ?  								\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) selectQuery.append("\n  AND A.COLDEPTCD LIKE ?                           \n");
					if( !"".equals(sch_userid) ) selectQuery.append("\n  AND A.CHRGUSRID LIKE ?                           \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) selectQuery.append("\n  AND A.COLDEPTNM LIKE ? \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) selectQuery.append("\n  AND A.CHRGUSRNM LIKE ? \n");
				}else{
					selectQuery.append("                     AND A.COLDEPTCD = ?								\n");
					selectQuery.append("                     AND A.CHRGUSRID = ? 								\n");
				}
			}
				
			if(!"".equals(sch_title)||sch_title != null){
				selectQuery.append("\n                     AND A.TITLE LIKE  ?								  	");
			}

			selectQuery.append("\n                          ORDER BY TDAY DESC, A.ENDDT DESC, A.CRTDT DESC, A.UPTDT DESC) A2) A1) 						");
			selectQuery.append("\n  WHERE SEQ BETWEEN ? AND ? 													");

			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
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
			
			if(!sch_title.equals("")||sch_title!= null){
				pstmt.setString(++bindPos, "%"+ sch_title +"%");
			}
			pstmt.setInt(++bindPos, start);
			pstmt.setInt(++bindPos, end);
									
			rs = pstmt.executeQuery();
			
			reserchList = new ArrayList();
			
			while (rs.next()) {
				ResearchBean Bean = new ResearchBean();
				
				Bean.setSeqno(rs.getInt("BUNHO"));
				Bean.setRchgrpno(rs.getInt("RCHGRPNO"));
				Bean.setTitle(rs.getString("TITLE"));
				Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				Bean.setStrdt(rs.getString("STRDT"));
				Bean.setEnddt(rs.getString("ENDDT"));
				Bean.setTday(rs.getInt("TDAY"));
				Bean.setChrgusrid(rs.getString("CHRGUSRID"));
				
				reserchList.add(Bean);
				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return reserchList;
	}
	
	/**
	 * 내설문목록 건
	 * @param usrid
	 * @param sch_title
	 * @return
	 * @throws Exception
	 */
	public int getResearchMyTotCnt(String usrid, String deptcd, String sch_title, String mode) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		int bindPos = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n SELECT COUNT(CHRGUSRID) 		");
			selectQuery.append("\n   FROM RCHMST 				");
			selectQuery.append("\n  WHERE GROUPYN = 'N' 		");
			selectQuery.append("\n    AND CHRGUSRID = ? 		");
			selectQuery.append("\n    AND COLDEPTCD = ? 		");
			
			if("main".equals(mode)){
				selectQuery.append("\n  AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN STRDT AND ENDDT ");
			}
			
			if(!sch_title.equals("")||sch_title!=null){
				selectQuery.append("\n  AND TITLE LIKE  ?		");
			}

			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setString(++bindPos, usrid);
			pstmt.setString(++bindPos, deptcd);
			if(!sch_title.equals("")||sch_title!=null){
				pstmt.setString(++bindPos, "%"+ sch_title +"%");
			}			
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}

		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return totalCount;
	}
	
	/**
	 * 내설문목록 건
	 * @param usrid
	 * @param sch_title
	 * @return
	 * @throws Exception
	 */
	public int getResearchTotCnt(String usrid, String deptcd, String initentry, String isSysMgr, String groupyn, String sch_title, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, String mode) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		int bindPos = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n SELECT COUNT(CHRGUSRID) 			");
			selectQuery.append("\n   FROM RCHMST A					");
			selectQuery.append("\n  WHERE GROUPYN LIKE ?				");
			
			if(initentry.equals("first")){
				selectQuery.append("	                   AND A.COLDEPTCD = ?							 	    \n");
				selectQuery.append("                       AND A.CHRGUSRID = ?  								\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) selectQuery.append("\n  AND A.COLDEPTCD LIKE ?                           \n");
					if( !"".equals(sch_userid) ) selectQuery.append("\n  AND A.CHRGUSRID LIKE ?                           \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) selectQuery.append("\n  AND A.COLDEPTNM LIKE ? \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) selectQuery.append("\n  AND A.CHRGUSRNM LIKE ? \n");
				}else{
					selectQuery.append("                     AND A.COLDEPTCD = ?								\n");
					selectQuery.append("                     AND A.CHRGUSRID = ? 								\n");
				}
			}
			
			if("main".equals(mode)){
				selectQuery.append("\n  AND TO_CHAR(SYSDATE,'YYYY-MM-DD') BETWEEN A.STRDT AND A.ENDDT ");
			}
			
			if(!sch_title.equals("")||sch_title!=null){
				selectQuery.append("\n  AND A.TITLE LIKE  ?			");
			}

			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
			pstmt.setString(++bindPos, "%"+groupyn);
			
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
			
			if(!sch_title.equals("")||sch_title!=null){
				pstmt.setString(++bindPos, "%"+ sch_title +"%");
			}			
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}

		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return totalCount;
	}
	
	/**
	 * 내설문그룹목록 건
	 * @param usrid
	 * @param sch_title
	 * @return
	 * @throws Exception
	 */
	public int getResearchGrpTotCnt(String usrid, String deptcd, String initentry, String isSysMgr, String sch_title, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, String mode) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		int bindPos = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n SELECT COUNT(CHRGUSRID) 			");
			selectQuery.append("\n   FROM RCHGRPMST A				");
			selectQuery.append("\n  WHERE 1 = 1						");
			
			if(initentry.equals("first")){
				selectQuery.append("	                   AND A.COLDEPTCD = ?							 	    \n");
				selectQuery.append("                       AND A.CHRGUSRID = ?  								\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) selectQuery.append("\n  AND A.COLDEPTCD LIKE ?                           \n");
					if( !"".equals(sch_userid) ) selectQuery.append("\n  AND A.CHRGUSRID LIKE ?                           \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) selectQuery.append("\n  AND A.COLDEPTNM LIKE ? \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) selectQuery.append("\n  AND A.CHRGUSRNM LIKE ? \n");
				}else{
					selectQuery.append("                     AND A.COLDEPTCD = ?								\n");
					selectQuery.append("                     AND A.CHRGUSRID = ? 								\n");
				}
			}
			
			if("main".equals(mode)){
				selectQuery.append("\n  AND TO_CHAR(SYSDATE,'YYYY-MM-DD') BETWEEN A.STRDT AND A.ENDDT ");
			}
			
			if(!sch_title.equals("")||sch_title!=null){
				selectQuery.append("\n  AND A.TITLE LIKE  ?			");
			}

			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
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
			
			if(!sch_title.equals("")||sch_title!=null){
				pstmt.setString(++bindPos, "%"+ sch_title +"%");
			}			
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}

		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return totalCount;
	}
	
	/*
	 * 설문 답변자 목록 가져오기
	 */
	public List getRchAnsusrList(int rchno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        List result = null;

		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("\n SELECT RCHNO, ANSUSRSEQ, CRTUSRID, NVL(CRTUSRNM, '익명' || ANSUSRSEQ) CRTUSRNM ");
			selectQuery.append("\n FROM RCHANS ");
			selectQuery.append("\n WHERE RCHNO = ? ");
			selectQuery.append("\n GROUP BY RCHNO, ANSUSRSEQ, CRTUSRID, CRTUSRNM ");
			
			con = ConnectionManager.getConnection();
			pstmt =	con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, rchno);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				if (result == null) {
					result = new ArrayList();
				}
				
				ResearchBean rbean = new ResearchBean();
				rbean.setRchno(rs.getInt("RCHNO"));
				rbean.setAnsusrseq(rs.getInt("ANSUSRSEQ"));
				rbean.setCrtusrid(rs.getString("CRTUSRID"));
				rbean.setCrtusrnm(rs.getString("CRTUSRNM"));
				
				result.add(rbean);
			}			

		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(con,pstmt,rs);
	     }
		return result;
	}
	
	/**
	 * 설문조사 마스터 가져오기
	 * @param usrid
	 * @param sch_title
	 * @return
	 * @throws Exception
	 */
	public ResearchBean getRchMst(int rchno, String sessionId) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		ResearchBean Bean = null;
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(rchno == 0){
			selectQuery.append("\n SELECT SESSIONID, TITLE, STRDT, SUBSTR(A.STRDT,1,4)||'년 '||SUBSTR(A.STRDT,6,2)||'월 '||SUBSTR(A.STRDT,9,2)||'일' STRYMD, ");
			selectQuery.append("\n        ENDDT, SUBSTR(A.ENDDT,1,4)||'년 '||SUBSTR(A.ENDDT,6,2)||'월 '||SUBSTR(A.ENDDT,9,2)||'일' ENDYMD, ");
			selectQuery.append("\n        COLDEPTCD, COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, ");
			selectQuery.append("\n        DUPLICHECK, LIMITCOUNT, GROUPYN, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, ");
			selectQuery.append("\n        (SELECT DEPT_TEL FROM DEPT WHERE DEPT_ID = A.COLDEPTCD) DEPT_TEL, ");
			selectQuery.append("\n        (SELECT SUBSTR(MAX(SYS_CONNECT_BY_PATH(TARGET, ', ')), 3) ");
			selectQuery.append("\n         FROM (SELECT ROWNUM RN, TARGET ");
			selectQuery.append("\n               FROM (SELECT TGTNAME || '(' || TGTCODE || ')' TARGET ");
			selectQuery.append("\n                     FROM RCHOTHERTARGET_TEMP ");
			selectQuery.append("\n                     WHERE SESSIONID LIKE ? ");
			selectQuery.append("\n                     AND TGTGBN = '1' ");
			selectQuery.append("\n                     ORDER BY TGTCODE)) ");
			selectQuery.append("\n         START WITH RN = 1 ");
			selectQuery.append("\n         CONNECT BY PRIOR RN = RN - 1) OTHERTGTNM ");
			selectQuery.append("\n   FROM RCHMST_TEMP A				");
			selectQuery.append("\n  WHERE SESSIONID LIKE ? 		");
		}else{
			selectQuery.append("\n SELECT RCHNO, TITLE, STRDT, SUBSTR(A.STRDT,1,4)||'년 '||SUBSTR(A.STRDT,6,2)||'월 '||SUBSTR(A.STRDT,9,2)||'일' STRYMD, ");
			selectQuery.append("\n        ENDDT, SUBSTR(A.ENDDT,1,4)||'년 '||SUBSTR(A.ENDDT,6,2)||'월 '||SUBSTR(A.ENDDT,9,2)||'일' ENDYMD, ");
			selectQuery.append("\n        COLDEPTCD, COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, ");
			selectQuery.append("\n        DUPLICHECK, LIMITCOUNT, GROUPYN, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, ");
			selectQuery.append("\n        (SELECT DEPT_TEL FROM DEPT WHERE DEPT_ID = A.COLDEPTCD) DEPT_TEL, ");
			selectQuery.append("\n        (SELECT SUBSTR(MAX(SYS_CONNECT_BY_PATH(TARGET, ', ')), 3) ");
			selectQuery.append("\n         FROM (SELECT ROWNUM RN, TARGET ");
			selectQuery.append("\n               FROM (SELECT TGTNAME || '(' || TGTCODE || ')' TARGET ");
			selectQuery.append("\n                     FROM RCHOTHERTARGET ");
			selectQuery.append("\n                     WHERE RCHNO = ? ");
			selectQuery.append("\n                     AND TGTGBN = '1' ");
			selectQuery.append("\n                     ORDER BY TGTCODE)) ");
			selectQuery.append("\n         START WITH RN = 1 ");
			selectQuery.append("\n         CONNECT BY PRIOR RN = RN - 1) OTHERTGTNM ");
			selectQuery.append("\n   FROM RCHMST A				");
			selectQuery.append("\n  WHERE RCHNO = ? 		");
		}
		try {
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);
				pstmt.setString(2, sessionId);
			}else{
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, rchno);
			}

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Bean = new ResearchBean();
				if(rchno == 0){
					Bean.setRchno(rchno);
					Bean.setSessionId(rs.getString("SESSIONID"));
				}else{
					Bean.setRchno(rs.getInt("RCHNO"));
					Bean.setSessionId(sessionId);
				}
					
				Bean.setTitle(rs.getString("TITLE"));
				Bean.setStrdt(rs.getString("STRDT"));
				Bean.setStrymd(rs.getString("STRYMD"));
				Bean.setEnddt(rs.getString("ENDDT"));
				Bean.setEndymd(rs.getString("ENDYMD"));
				Bean.setColdeptcd(rs.getString("COLDEPTCD"));
				Bean.setColdeptnm(rs.getString("COLDEPTNM"));
				Bean.setColdepttel(rs.getString("COLDEPTTEL"));
				Bean.setChrgusrid(rs.getString("CHRGUSRID"));
				Bean.setChrgusrnm(rs.getString("CHRGUSRNM"));
				Bean.setSummary(rs.getString("SUMMARY"));	
				Bean.setExhibit(rs.getString("EXHIBIT"));
				Bean.setOpentype(rs.getString("OPENTYPE"));
				Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				Bean.setImgpreview(rs.getString("IMGPREVIEW"));
				Bean.setDuplicheck(rs.getString("DUPLICHECK"));
				Bean.setLimitcount(rs.getInt("LIMITCOUNT"));
				Bean.setGroupyn(rs.getString("GROUPYN"));
				Bean.setTgtdeptnm(rs.getString("TGTDEPTNM"));
				Bean.setHeadcont(rs.getString("HEADCONT"));
				Bean.setTailcont(rs.getString("TAILCONT"));
				Bean.setTelno(rs.getString("DEPT_TEL"));
				Bean.setAnscount(rs.getInt("ANSCOUNT"));
				Bean.setOthertgtnm(rs.getString("OTHERTGTNM"));
			}			

		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return Bean;
	}
	
	/**
	 * 설문조사그룹 마스터 가져오기
	 * @param usrid
	 * @param sch_title
	 * @return
	 * @throws Exception
	 */
	public ResearchBean getRchGrpMst(int rchgrpno, String sessionId) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		ResearchBean Bean = null;
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(rchgrpno == 0){
			selectQuery.append("\n SELECT SESSIONID, TITLE, STRDT, SUBSTR(A.STRDT,1,4)||'년 '||SUBSTR(A.STRDT,6,2)||'월 '||SUBSTR(A.STRDT,9,2)||'일' STRYMD, ");
			selectQuery.append("\n        ENDDT, SUBSTR(A.ENDDT,1,4)||'년 '||SUBSTR(A.ENDDT,6,2)||'월 '||SUBSTR(A.ENDDT,9,2)||'일' ENDYMD, ");
			selectQuery.append("\n        COLDEPTCD, COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, ");
			selectQuery.append("\n        DUPLICHECK, LIMITCOUNT, GROUPYN, RCHNOLIST, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, ");
			selectQuery.append("\n        (SELECT DEPT_TEL FROM DEPT WHERE DEPT_ID = A.COLDEPTCD) DEPT_TEL, ");
			selectQuery.append("\n        (SELECT SUBSTR(MAX(SYS_CONNECT_BY_PATH(TARGET, ', ')), 3) ");
			selectQuery.append("\n         FROM (SELECT ROWNUM RN, TARGET ");
			selectQuery.append("\n               FROM (SELECT TGTNAME || '(' || TGTCODE || ')' TARGET ");
			selectQuery.append("\n                     FROM RCHGRPOTHERTARGET_TEMP ");
			selectQuery.append("\n                     WHERE SESSIONID LIKE ? ");
			selectQuery.append("\n                     AND TGTGBN = '1' ");
			selectQuery.append("\n                     ORDER BY TGTCODE)) ");
			selectQuery.append("\n         START WITH RN = 1 ");
			selectQuery.append("\n         CONNECT BY PRIOR RN = RN - 1) OTHERTGTNM ");
			selectQuery.append("\n   FROM RCHGRPMST_TEMP A				");
			selectQuery.append("\n  WHERE SESSIONID LIKE ? 		");
		}else{
			selectQuery.append("\n SELECT RCHGRPNO, TITLE, STRDT, SUBSTR(A.STRDT,1,4)||'년 '||SUBSTR(A.STRDT,6,2)||'월 '||SUBSTR(A.STRDT,9,2)||'일' STRYMD, ");
			selectQuery.append("\n        ENDDT, SUBSTR(A.ENDDT,1,4)||'년 '||SUBSTR(A.ENDDT,6,2)||'월 '||SUBSTR(A.ENDDT,9,2)||'일' ENDYMD, ");
			selectQuery.append("\n        COLDEPTCD, COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, ");
			selectQuery.append("\n        DUPLICHECK, LIMITCOUNT, GROUPYN, RCHNOLIST, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, ");
			selectQuery.append("\n        (SELECT DEPT_TEL FROM DEPT WHERE DEPT_ID = A.COLDEPTCD) DEPT_TEL, ");
			selectQuery.append("\n        (SELECT SUBSTR(MAX(SYS_CONNECT_BY_PATH(TARGET, ', ')), 3) ");
			selectQuery.append("\n         FROM (SELECT ROWNUM RN, TARGET ");
			selectQuery.append("\n               FROM (SELECT TGTNAME || '(' || TGTCODE || ')' TARGET ");
			selectQuery.append("\n                     FROM RCHGRPOTHERTARGET ");
			selectQuery.append("\n                     WHERE RCHGRPNO = ? ");
			selectQuery.append("\n                     AND TGTGBN = '1' ");
			selectQuery.append("\n                     ORDER BY TGTCODE)) ");
			selectQuery.append("\n         START WITH RN = 1 ");
			selectQuery.append("\n         CONNECT BY PRIOR RN = RN - 1) OTHERTGTNM ");
			selectQuery.append("\n   FROM RCHGRPMST A				");
			selectQuery.append("\n  WHERE RCHGRPNO = ? 		");
		}
		try {
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
			if(rchgrpno == 0){
				pstmt.setString(1, sessionId);
				pstmt.setString(2, sessionId);
			}else{
				pstmt.setInt(1, rchgrpno);
				pstmt.setInt(2, rchgrpno);
			}

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Bean = new ResearchBean();
				if(rchgrpno == 0){
					Bean.setRchgrpno(rchgrpno);
					Bean.setSessionId(rs.getString("SESSIONID"));
				}else{
					Bean.setRchgrpno(rs.getInt("RCHGRPNO"));
					Bean.setSessionId(sessionId);
				}
					
				Bean.setTitle(rs.getString("TITLE"));
				Bean.setStrdt(rs.getString("STRDT"));
				Bean.setStrymd(rs.getString("STRYMD"));
				Bean.setEnddt(rs.getString("ENDDT"));
				Bean.setEndymd(rs.getString("ENDYMD"));
				Bean.setColdeptcd(rs.getString("COLDEPTCD"));
				Bean.setColdeptnm(rs.getString("COLDEPTNM"));
				Bean.setColdepttel(rs.getString("COLDEPTTEL"));
				Bean.setChrgusrid(rs.getString("CHRGUSRID"));
				Bean.setChrgusrnm(rs.getString("CHRGUSRNM"));
				Bean.setSummary(rs.getString("SUMMARY"));	
				Bean.setExhibit(rs.getString("EXHIBIT"));
				Bean.setOpentype(rs.getString("OPENTYPE"));
				Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				Bean.setImgpreview(rs.getString("IMGPREVIEW"));
				Bean.setDuplicheck(rs.getString("DUPLICHECK"));
				Bean.setLimitcount(rs.getInt("LIMITCOUNT"));
				Bean.setGroupyn(rs.getString("GROUPYN"));
				Bean.setRchnolist(rs.getString("RCHNOLIST"));
				Bean.setTgtdeptnm(rs.getString("TGTDEPTNM"));
				Bean.setHeadcont(rs.getString("HEADCONT"));
				Bean.setTailcont(rs.getString("TAILCONT"));
				Bean.setTelno(rs.getString("DEPT_TEL"));
				Bean.setAnscount(rs.getInt("ANSCOUNT"));
				Bean.setOthertgtnm(rs.getString("OTHERTGTNM"));
				
				List rchGrpList = commapprovalManager.instance().getResearchGrpList(rchgrpno, sessionId);
				String title = "";
				if ( rchGrpList.size() > 1 ) {
					title = ((commapprovalBean)rchGrpList.get(0)).getTgtname();
					title = "[" + title + "] 설문조사 외 " + (rchGrpList.size() - 1) + "종";
				} else if ( rchGrpList.size() == 1 ) {
					title = ((commapprovalBean)rchGrpList.get(0)).getTgtname();
				}
				Bean.setRchname(title);
			}			

		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return Bean;
	}
	
	/**
	 * 
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public List getRchSubList(int rchno, String sessionId, int examcount, String mode) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List rchSubList = null;
		StringBuffer selectQuery = new StringBuffer();
		if(rchno ==0){
			selectQuery.append("\n SELECT A.FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD ");
			selectQuery.append("\n   FROM RCHSUB_TEMP A, RCHSUBFILE_TEMP B ");
			selectQuery.append("\n  WHERE A.SESSIONID = B.SESSIONID(+) ");
			selectQuery.append("\n    AND A.FORMSEQ = B.FORMSEQ(+) ");
			selectQuery.append("\n    AND A.SESSIONID LIKE ? 		");
			selectQuery.append("\n  ORDER BY FORMSEQ 			");			
		}else{
			selectQuery.append("\n SELECT A.FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD ");
			selectQuery.append("\n   FROM RCHSUB A, RCHSUBFILE B ");
			selectQuery.append("\n  WHERE A.RCHNO = B.RCHNO(+) ");
			selectQuery.append("\n    AND A.FORMSEQ = B.FORMSEQ(+) ");
			selectQuery.append("\n    AND A.RCHNO = ? 			");
			selectQuery.append("\n  ORDER BY FORMSEQ 			");	
		}
		try {
		
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(rchno ==0 ){
				pstmt.setString(1, sessionId);
			}else{
				pstmt.setInt(1, rchno);
			}
			
									
			rs = pstmt.executeQuery();
			
			rchSubList = new ArrayList();
			
			while (rs.next()) {
				ResearchSubBean Bean = new ResearchSubBean();
				int formseq = rs.getInt("FORMSEQ");
				
				Bean.setFormseq(rs.getInt("FORMSEQ"));
				Bean.setFormgrp(rs.getString("FORMGRP"));
				Bean.setFormtitle(rs.getString("FORMTITLE"));
				Bean.setFormtype(rs.getString("FORMTYPE"));
				Bean.setSecurity(rs.getString("SECURITY"));
				Bean.setExamtype(rs.getString("EXAMTYPE"));
				Bean.setFileseq(rs.getInt("FILESEQ"));
				Bean.setFilenm(rs.getString("FILENM"));
				Bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				Bean.setFilesize(rs.getInt("FILESIZE"));
				Bean.setExt(rs.getString("EXT"));				
				Bean.setOrd(rs.getInt("ORD"));
				Bean.setExamList(rchExamList(rchno, sessionId, formseq, examcount, mode));

				rchSubList.add(Bean);		
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return rchSubList;
	}
	
	/**
	 * 
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public List getResultSubList(int rchno, String range, RchSearchBean schbean, String sessionId) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List rchSubList = null;
		StringBuffer selectQuery = new StringBuffer();
		
		String[] sch_exam = schbean.getSch_exam(); 
		int cnt=0;

		selectQuery.append("\n SELECT A.FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD ");
		selectQuery.append("\n   FROM RCHSUB A, RCHSUBFILE B ");
		selectQuery.append("\n  WHERE A.RCHNO = B.RCHNO(+) ");
		selectQuery.append("\n    AND A.FORMSEQ = B.FORMSEQ(+) ");
		selectQuery.append("\n    AND A.RCHNO = ? 			");
		selectQuery.append("\n  ORDER BY FORMSEQ 			");	

		try {
		
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());

			pstmt.setInt(1, rchno);
						
			rs = pstmt.executeQuery();
			
			rchSubList = new ArrayList();
			
			while (rs.next()) {
				ResearchSubBean Bean = new ResearchSubBean();
				int formseq = rs.getInt("FORMSEQ");
				
				Bean.setFormseq(rs.getInt("FORMSEQ"));
				Bean.setFormgrp(rs.getString("FORMGRP"));
				Bean.setFormtitle(rs.getString("FORMTITLE"));
				Bean.setFormtype(rs.getString("FORMTYPE"));
				Bean.setSecurity(rs.getString("SECURITY"));
				Bean.setExamtype(rs.getString("EXAMTYPE"));
				if("01".equals(rs.getString("FORMTYPE")) ||"02".equals(rs.getString("FORMTYPE"))){
					if(sch_exam != null){
						for(int i=0; i<sch_exam.length; i++){
							if(cnt == i){
								Bean.setSch_exam(sch_exam[i]);
								break;
							}
						}
					}else{
						Bean.setSch_exam("%");
					}
					cnt++;
					Bean.setExamList(getResultExamList(rchno, range, schbean, formseq));
					if("Y".equals(rs.getString("EXAMTYPE"))){
						Bean.setOtherList(rchOtherList(rchno, range, schbean, formseq));
					}
				}else{
					Bean.setExamList(rchAnsList(rchno, range, schbean, formseq));
				}
				Bean.setFileseq(rs.getInt("FILESEQ"));
				Bean.setFilenm(rs.getString("FILENM"));
				Bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				Bean.setFilesize(rs.getInt("FILESIZE"));
				Bean.setExt(rs.getString("EXT"));				
				Bean.setOrd(rs.getInt("ORD"));
				
				rchSubList.add(Bean);
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return rchSubList;
	}	
	
	/**
	 * 단답,복수형 답변결과
	 * @param rchno
	 * @param range
	 * @param schbean
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List getResultExamList(int rchno, String range, RchSearchBean schbean, int formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List rchExamList = null;	
		String[] schexam = null;
		String inexamans = "";
		if(schbean.getSch_exam()!=null){
			
			schexam = schbean.getSch_exam();
			
			for(int i=0; i<schexam.length; i++){
				if("".equals(inexamans)){
					if(!"%".equals(schexam[i])){
						inexamans = schexam[i];
					}
				}else{
					if(!"%".equals(schexam[i])){
						inexamans = inexamans + "," + schexam[i];
					}
				}
			}
		}
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("SELECT RA.RCHNO, RA.FORMSEQ, RE.EXAMSEQ, RE.EXAMCONT, \n");
		if(inexamans.equals("") == true) {
			selectQuery.append("       SUM(CASE WHEN INSTR(',' || REPLACE(RA.ANSCONT, ',', ',,') || ',', ',' || RE.EXAMSEQ || ',') > 0 THEN 1 ELSE 0 END) CNT \n");
		} else {
			String[] selectExam = inexamans.split("[,]");
			String prevFormNumber = "";
			
			selectQuery.append("       SUM(CASE WHEN INSTR(',' || REPLACE(RA.ANSCONT, ',', ',,') || ',', ',' || RE.EXAMSEQ || ',') > 0 THEN \n");
			for(int i = 0; i < selectExam.length; i++) {
				int div = 0;
				if ( Integer.parseInt("0" + prevFormNumber) > 9 ) div = 1;
				int selectExamLength = selectExam[i].length();
				String formNumber = selectExam[i].substring(0, selectExamLength / 2 + div);
				String examNumber = selectExam[i].substring(selectExamLength / 2 + div);
				prevFormNumber = formNumber;
				
				selectQuery.append("       CASE WHEN RA.ANSUSRSEQ IN (SELECT ANSUSRSEQ \n");
				selectQuery.append("                                 FROM RCHANS \n");
				selectQuery.append("                                 WHERE RCHNO = RA.RCHNO \n");
				selectQuery.append("                                 AND FORMSEQ = " + formNumber + " \n");
				if(examNumber.equals("0") == true) {
					selectQuery.append("                                 AND OTHER IS NOT NULL) THEN \n");
				} else {
					selectQuery.append("                                 AND INSTR(',' || REPLACE(ANSCONT, ',', ',,') || ',', '," + examNumber + ",') > 0) THEN \n");
				}
			}
			selectQuery.append("1 \n");
			for(int i = 0; i < selectExam.length; i++) {
				selectQuery.append("END \n");
			}
			selectQuery.append("ELSE 0 END) CNT \n");
		}
		
		if(range.equals("2") == true) {	//외부망
			selectQuery.append("FROM RCHANS RA, RCHEXAM RE \n");
			selectQuery.append("WHERE RA.RCHNO = RE.RCHNO \n");
			selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ \n"); 
			selectQuery.append("AND RA.RCHNO = ? \n");
			selectQuery.append("AND RA.FORMSEQ = ? \n");
		} else {	//내부망
			/*[USR_EXT] 테이블 삭제
			selectQuery.append("FROM RCHANS RA, RCHEXAM RE, USR U, USR_EXT UE \n");
			selectQuery.append("WHERE RA.RCHNO = RE.RCHNO \n");
			selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ \n");
			if("%".equals(schbean.getSch_deptcd()) && "%".equals(schbean.getSch_sex()) && "".equals(schbean.getSch_age())){
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID(+) \n");
				selectQuery.append("AND RA.CRTUSRID = UE.USER_ID(+) \n");
			} else {
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID \n");
				selectQuery.append("AND RA.CRTUSRID = UE.USER_ID \n");
			}
			selectQuery.append("AND RA.RCHNO = ? \n");
			selectQuery.append("AND RA.FORMSEQ = ? \n");
			selectQuery.append("AND NVL(U.DEPT_ID, ' ') LIKE ? \n");
			selectQuery.append("AND NVL(UE.SEX, 'M') LIKE ? \n");
			if(schbean.getSch_age().equals("") == false) {
				selectQuery.append("AND NVL(UE.AGE, 20) = ? \n");
			}
			*/
			selectQuery.append("FROM RCHANS RA, RCHEXAM RE, USR U \n");
			selectQuery.append("WHERE RA.RCHNO = RE.RCHNO \n");
			selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ \n");
			if("%".equals(schbean.getSch_deptcd()) && "%".equals(schbean.getSch_sex()) && "".equals(schbean.getSch_age())){
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID(+) \n");
			} else {
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID \n");
			}
			selectQuery.append("AND RA.RCHNO = ? \n");
			selectQuery.append("AND RA.FORMSEQ = ? \n");
			selectQuery.append("AND NVL(U.DEPT_ID, ' ') LIKE ? \n");
			selectQuery.append("AND NVL(U.SEX, 'M') LIKE ? \n");
			if(schbean.getSch_age().equals("") == false) {
				selectQuery.append("AND NVL(U.AGE, 20) = ? \n");
			}
		}
		
		if(schbean.getSch_ansusrseq() > 0) {
			selectQuery.append("AND RA.ANSUSRSEQ = " + schbean.getSch_ansusrseq() + "  \n");
		}
		
		selectQuery.append("GROUP BY RA.RCHNO, RA.FORMSEQ, RE.EXAMSEQ, RE.EXAMCONT \n");
		selectQuery.append("ORDER BY RA.RCHNO, RA.FORMSEQ, RE.EXAMSEQ \n");
		
		try {
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(range.equals("2") == true) {	//외부망
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
			} else {
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
				
				pstmt.setString(3, schbean.getSch_deptcd());
				pstmt.setString(4, schbean.getSch_sex());
				
				if(schbean.getSch_age().equals("") == false){
					pstmt.setInt(5, Integer.parseInt(schbean.getSch_age().toString()));
				}	
			}		

			rs = pstmt.executeQuery();
			
			rchExamList = new ArrayList();
			while (rs.next()) {
				ResearchExamBean bean = new ResearchExamBean();
						
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setExamseq(rs.getInt("EXAMSEQ"));
				bean.setExamcont(rs.getString("EXAMCONT"));
				bean.setExamcnt(rs.getInt("CNT"));
				
				ResearchExamBean rchExamBean = getRchExamFile(con, "", rchno, bean.getFormseq(), bean.getExamseq());
				if ( rchExamBean != null ) {
					bean.setFileseq(rchExamBean.getFileseq());
					bean.setFilenm(rchExamBean.getFilenm());
					bean.setOriginfilenm(rchExamBean.getOriginfilenm());
					bean.setFilesize(rchExamBean.getFilesize());
					bean.setExt(rchExamBean.getExt());				
					bean.setOrd(rchExamBean.getOrd());
				}
				
				rchExamList.add(bean);
			}	
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return rchExamList;
	}		
	
	/**
	 * 단답,복수형 기타 답변결과
	 * @param rchno
	 * @param range
	 * @param schbean
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List rchOtherList(int rchno, String range, RchSearchBean schbean, int formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List rchOtherList = null;	
		String[] schexam = null;		
		String inexamans = "";
		if(schbean.getSch_exam()!=null){
			
			schexam = schbean.getSch_exam();
			
			for(int i=0; i<schexam.length; i++){
				if("".equals(inexamans)){
					if(!"%".equals(schexam[i])){
						inexamans = schexam[i];
					}
				}else{
					if(!"%".equals(schexam[i])){
						inexamans = inexamans + "," + schexam[i];
					}
				}
			}
		}
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("SELECT DISTINCT RA.RCHNO, RA.ANSUSRSEQ, RA.FORMSEQ, RA.OTHER ");
		
		if(range.equals("2") == true) {	//외부망
			selectQuery.append("FROM RCHANS RA, RCHEXAM RE ");
			selectQuery.append("WHERE RA.RCHNO = RE.RCHNO ");
			selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ "); 
			selectQuery.append("AND RA.RCHNO = ? ");
			selectQuery.append("AND RA.FORMSEQ = ? ");
			selectQuery.append("AND RA.OTHER IS NOT NULL ");
		} else {	//내부망			
			selectQuery.append("FROM RCHANS RA, RCHEXAM RE, USR U ");
			selectQuery.append("WHERE RA.RCHNO = RE.RCHNO ");
			selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ ");
			selectQuery.append("AND RA.OTHER IS NOT NULL ");
			if("%".equals(schbean.getSch_deptcd()) && "%".equals(schbean.getSch_sex()) && "".equals(schbean.getSch_age())){
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID(+) ");
			} else {
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID ");
			}
			selectQuery.append("AND RA.RCHNO = ? ");
			selectQuery.append("AND RA.FORMSEQ = ? ");
			selectQuery.append("AND NVL(U.DEPT_ID, ' ') LIKE ? ");
			selectQuery.append("AND NVL(U.SEX, 'M') LIKE ? ");
			if(schbean.getSch_age().equals("") == false) {
				selectQuery.append("AND NVL(U.AGE, 20) = ? ");
			}
		}
		if(inexamans.equals("") == false) {
			String[] selectExam = inexamans.split("[,]");
			String prevFormNumber = "";

			for(int i = 0; i < selectExam.length; i++) {
				int div = 0;
				if ( Integer.parseInt("0" + prevFormNumber) > 9 ) div = 1;
				int selectExamLength = selectExam[i].length();
				String formNumber = selectExam[i].substring(0, selectExamLength / 2 + div);
				String examNumber = selectExam[i].substring(selectExamLength / 2 + div);
				prevFormNumber = formNumber;
				
				selectQuery.append("AND RA.ANSUSRSEQ IN (SELECT ANSUSRSEQ ");
				selectQuery.append("                    FROM RCHANS ");
				selectQuery.append("                    WHERE RCHNO = RA.RCHNO ");
				selectQuery.append("                    AND FORMSEQ = " + formNumber + " ");
				if(examNumber.equals("0") == true) {
					selectQuery.append("                    AND OTHER IS NOT NULL) ");
				} else {
					selectQuery.append("                    AND INSTR(',' || REPLACE(ANSCONT, ',', ',,') || ',', '," + examNumber + ",') > 0) ");
				}
			}
		}

		if(schbean.getSch_ansusrseq() > 0) {
			selectQuery.append("AND RA.ANSUSRSEQ = " + schbean.getSch_ansusrseq() + "  ");
		}
		
		selectQuery.append("ORDER BY RA.RCHNO, RA.FORMSEQ, RA.OTHER ");
		
		try {
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(range.equals("2") == true) {	//외부망
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
			} else {
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
				
				pstmt.setString(3, schbean.getSch_deptcd());
				pstmt.setString(4, schbean.getSch_sex());
				
				if(schbean.getSch_age().equals("") == false){
					pstmt.setInt(5, Integer.parseInt(schbean.getSch_age().toString()));
				}	
			}		

			rs = pstmt.executeQuery();
			
			rchOtherList = new ArrayList();
			while(rs.next()) {
				ResearchAnsBean bean = new ResearchAnsBean();
						
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setOther(rs.getString("OTHER"));
				
				rchOtherList.add(bean);
			}						
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return rchOtherList;
	}	
	
	/**
	 * 단문,장문형 답변결과
	 * @param rchno
	 * @param range
	 * @param schbean
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List rchAnsList(int rchno, String range, RchSearchBean schbean, int formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List rchAnsList = null;	
		String[] schexam = null;		
		String inexamans = "";
		if(schbean.getSch_exam()!=null){
			
			schexam = schbean.getSch_exam();
			
			for(int i=0; i<schexam.length; i++){
				if("".equals(inexamans)){
					if(!"%".equals(schexam[i])){
						inexamans = schexam[i];
					}
				}else{
					if(!"%".equals(schexam[i])){
						inexamans = inexamans + "," + schexam[i];
					}
				}
			}
		}
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("SELECT DISTINCT RA.RCHNO, RA.ANSUSRSEQ, RA.FORMSEQ, RA.ANSCONT ");
		
		if(range.equals("2") == true) {	//외부망
			selectQuery.append("FROM RCHANS RA, RCHEXAM RE ");
			selectQuery.append("WHERE RA.RCHNO = RE.RCHNO(+) ");
			selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ(+) "); 
			selectQuery.append("AND RA.RCHNO = ? ");
			selectQuery.append("AND RA.FORMSEQ = ? ");
		} else {	//내부망			
			selectQuery.append("FROM RCHANS RA, RCHEXAM RE, USR U ");
			selectQuery.append("WHERE RA.RCHNO = RE.RCHNO(+) ");
			selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ(+) ");
			if("%".equals(schbean.getSch_deptcd()) && "%".equals(schbean.getSch_sex()) && "".equals(schbean.getSch_age())){
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID(+) ");
			} else {
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID ");
			}
			selectQuery.append("AND RA.RCHNO = ? ");
			selectQuery.append("AND RA.FORMSEQ = ? ");
			selectQuery.append("AND NVL(U.DEPT_ID, ' ') LIKE ? ");
			selectQuery.append("AND NVL(U.SEX, 'M') LIKE ? ");
			if(schbean.getSch_age().equals("") == false) {
				selectQuery.append("AND NVL(U.AGE, 20) = ? ");
			}
		}
		if(inexamans.equals("") == false) {
			String[] selectExam = inexamans.split("[,]");
			String prevFormNumber = "";

			for(int i = 0; i < selectExam.length; i++) {
				int div = 0;
				if ( Integer.parseInt("0" + prevFormNumber) > 9 ) div = 1;
				int selectExamLength = selectExam[i].length();
				String formNumber = selectExam[i].substring(0, selectExamLength / 2 + div);
				String examNumber = selectExam[i].substring(selectExamLength / 2 + div);
				prevFormNumber = formNumber;
				
				selectQuery.append("AND RA.ANSUSRSEQ IN (SELECT ANSUSRSEQ ");
				selectQuery.append("                    FROM RCHANS ");
				selectQuery.append("                    WHERE RCHNO = RA.RCHNO ");
				selectQuery.append("                    AND FORMSEQ = " + formNumber + " ");
				if(examNumber.equals("0") == true) {
					selectQuery.append("                    AND OTHER IS NOT NULL) ");
				} else {
					selectQuery.append("                    AND INSTR(',' || REPLACE(ANSCONT, ',', ',,') || ',', '," + examNumber + ",') > 0) ");
				}
			}
		}

		if(schbean.getSch_ansusrseq() > 0) {
			selectQuery.append("AND RA.ANSUSRSEQ = " + schbean.getSch_ansusrseq() + "  ");
		}
		
		selectQuery.append("ORDER BY RA.RCHNO, RA.FORMSEQ, RA.ANSCONT ");
		
		try {
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(range.equals("2") == true) {	//외부망
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
			} else {
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
				
				pstmt.setString(3, schbean.getSch_deptcd());
				pstmt.setString(4, schbean.getSch_sex());
				
				if(schbean.getSch_age().equals("") == false){
					pstmt.setInt(5, Integer.parseInt(schbean.getSch_age().toString()));
				}	
			}		

			rs = pstmt.executeQuery();
			
			rchAnsList = new ArrayList();
			while (rs.next()) {
				ResearchAnsBean bean = new ResearchAnsBean();
						
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setAnscont(rs.getString("ANSCONT"));
				
				rchAnsList.add(bean);
			}						
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return rchAnsList;
	}
	
	/**
	 * 
	 * @param rchno
	 * @param sessionId
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List rchExamList(int rchno, String sessionId, int formseq, int examcount, String mode) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List rchExamList = null;	
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(rchno ==0){
			selectQuery.append("\n SELECT T1.SESSIONID, T1.FORMSEQ, T1.EXAMSEQ, EXAMCONT,  0 CNT, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD ");
			selectQuery.append("\n   FROM RCHEXAM_TEMP T1, RCHEXAMFILE_TEMP T2 ");
			selectQuery.append("\n  WHERE T1.SESSIONID = T2.SESSIONID(+) ");
			selectQuery.append("\n    AND T1.FORMSEQ = T2.FORMSEQ(+) ");
			selectQuery.append("\n    AND T1.EXAMSEQ = T2.EXAMSEQ(+) ");
			selectQuery.append("\n    AND T1.SESSIONID LIKE ? 	");
			selectQuery.append("\n    AND T1.FORMSEQ = ? 	");
			selectQuery.append("\n ORDER BY EXAMSEQ			");
			
		}else{
			selectQuery.append("\n SELECT T1.RCHNO, T1.FORMSEQ, T1.EXAMSEQ, T1.EXAMCONT, ");
			selectQuery.append("\n 	      (SELECT COUNT(DISTINCT ANSUSRSEQ) FROM RCHANS WHERE T1.RCHNO = RCHNO AND T1.FORMSEQ = FORMSEQ AND ANSCONT LIKE '%'||T1.EXAMSEQ||'%' ) CNT, ");
			selectQuery.append("\n        FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD ");
			selectQuery.append("\n   FROM RCHEXAM T1, RCHEXAMFILE T2 ");
			selectQuery.append("\n  WHERE T1.RCHNO = T2.RCHNO(+) ");
			selectQuery.append("\n    AND T1.FORMSEQ = T2.FORMSEQ(+) ");
			selectQuery.append("\n    AND T1.EXAMSEQ = T2.EXAMSEQ(+) ");
			selectQuery.append("\n    AND T1.RCHNO = ? 	");
			selectQuery.append("\n    AND T1.FORMSEQ = ? ");
			selectQuery.append("\n ORDER BY EXAMSEQ			");
		}
		
		try {

			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
			}else{
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
			}

			
			rs = pstmt.executeQuery();
			
			rchExamList = new ArrayList();
			int cnt = 0;
			while (rs.next()) {
				ResearchExamBean bean = new ResearchExamBean();
						
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setExamseq(rs.getInt("EXAMSEQ"));
				bean.setExamcont(rs.getString("EXAMCONT"));
				bean.setExamcnt(rs.getInt("CNT"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));
				
				rchExamList.add(bean);
				cnt = cnt + 1;
			}						
			
			if("".equals(mode)){
				//비어있는 보기를 examcount개까지 채운다.
				for(int i=0;i<examcount-cnt;i++){
					ResearchExamBean bean = new ResearchExamBean();
					rchExamList.add(bean);
				}
			}
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return rchExamList;
	}	
	
	
	/**
	 * 
	 * @param rchno
	 * @param sessionId
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public int rchAnsCnt(int rchno, int formseq, int examseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;	
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT COUNT(DISTINCT ANSUSRSEQ) ");
		selectQuery.append("\n   FROM RCHANS		");
		selectQuery.append("\n  WHERE RCHNO = ? 	");
		selectQuery.append("\n    AND FORMSEQ = ? 	");
		selectQuery.append("\n    AND ANSCONT LIKE '%'||?||'%' ");

		
		try {

			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, rchno);
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, examseq);

			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				result = rs.getInt(1);
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
	 * 설문조사 전체 저장
	 * @param Bean
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchAllSave(ResearchBean Bean, ResearchForm rchForm, ServletContext context, String saveDir) throws Exception {
		Connection con = null;
        int result = 0;
        
		try {
			con = ConnectionManager.getConnection(false);
			
			//임시테이블에 값이 있거나, 정규테이블에 값이 있을때는 업데이트 한다.
			if(checkCnt(Bean.getSessionId())|| Bean.getRchno()> 0 ){
				//마스터 저장
				result = rchUptMst(con, Bean, 0);							
				//문항저장(보기포함)
				result += rchInsSub(con, Bean.getListrch(), Bean.getSessionId(), Bean.getRchno());

				//파일 업로드
				File saveFolder = new File(context.getRealPath(saveDir));
				if(!saveFolder.exists()) saveFolder.mkdirs();
				
				String[] formattitleFileYN = Bean.getFormtitleFileYN();
				String[] examcontFileYN = Bean.getExamcontFileYN();
				String[] formtitle = Bean.getFormtitle();
				String[] examcont = Bean.getExamcont();
				int examcount = Bean.getExamcount();
				
				for ( int i = 0; formtitle != null && i < formtitle.length; i++ ) {
					ResearchSubBean rchSubBean = getRchSubFile(con, Bean.getSessionId(), Bean.getRchno(), i+1);
					
					if ( rchSubBean != null &&
							(formattitleFileYN[i] == null || 
									(Bean.getFormtitleFile()[i] != null && Bean.getFormtitleFile()[i].getFileName().equals("") == false)) ) {
						delRchSubFile(con, Bean.getSessionId(), Bean.getRchno(), i+1, 1);
						
						String delFile = rchSubBean.getFilenm();
						if ( delFile != null && delFile.trim().equals("") == false) {
							FileManager.doFileDelete(context.getRealPath(delFile));
						}
					}
					
					if ( Bean.getFormtitleFile()[i] != null && Bean.getFormtitleFile()[i].getFileName().equals("") == false ) {
						FileBean subFileBean = FileManager.doFileUpload(Bean.getFormtitleFile()[i], context, saveDir);
						
						if( subFileBean != null ) {
							int addResult = 0;
							subFileBean.setFileseq(1);
							addResult = addRchSubFile(con, Bean.getSessionId(), Bean.getRchno(), i+1, subFileBean);
							if ( addResult == 0 ) {
								throw new Exception("첨부실패:DB업데이트");
							}
						} else {
							throw new Exception("첨부실패:파일업로드");
						}
					}
										
					List subList = null;
					subList = getRchSubList(rchForm.getRchno(), rchForm.getSessionId(), examcount, "");
					int subcount = subList.size();
					
					int prevExamcount = 0;
					while ( prevExamcount < rchForm.getExamcontFile().length
							&& rchForm.getExamcontFile()[prevExamcount] != null ) {
						prevExamcount++;
					}
					prevExamcount = prevExamcount / subcount;
					
					if ( examcount >= prevExamcount ) {
						for ( int j = 0; examcont != null && j < prevExamcount; j++ ) {
							ResearchExamBean rchExamBean = getRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i+1, j+1);
							try {
								if ( rchExamBean != null &&
										(examcont[i*prevExamcount+j].trim().equals("") == true || examcontFileYN[i*prevExamcount+j] == null || 
												(Bean.getExamcontFile()[i*prevExamcount+j] != null && Bean.getExamcontFile()[i*prevExamcount+j].getFileName().equals("") == false)) ) {
									delRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i+1, j+1, 1);
									
									String delFile = rchExamBean.getFilenm();
									if ( delFile != null && delFile.trim().equals("") == false) {
										FileManager.doFileDelete(context.getRealPath(delFile));
									}
								}
							} catch ( Exception e ) {}
								
							try {
								if ( Bean.getExamcontFile()[i*prevExamcount+j] != null && Bean.getExamcontFile()[i*prevExamcount+j].getFileName().equals("") == false ) {
									FileBean examFileBean = FileManager.doFileUpload(Bean.getExamcontFile()[i*prevExamcount+j], context, saveDir);
									
									if(examFileBean != null) {
										int addResult = 0;
										examFileBean.setFileseq(1);
										addResult = addRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i+1, j+1, examFileBean);
										if ( addResult == 0 ) {
											throw new Exception("첨부실패:DB업데이트");
										}
									} else {
										throw new Exception("첨부실패:파일업로드");
									}
								}
							} catch ( Exception e ) {} 
						}
					
						setOrderRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i+1);
					}
					
					List rchExamUnusedList = getRchExamUnusedFile(con, Bean.getSessionId(), Bean.getRchno(),  i+1);
					
					for ( int k = 0; rchExamUnusedList != null && k < rchExamUnusedList.size(); k++ ) {
						ResearchExamBean rchExamBean = (ResearchExamBean)rchExamUnusedList.get(k);
						
						if ( rchExamBean != null ) {
							delRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i+1, rchExamBean.getExamseq(), rchExamBean.getFileseq());
							
							String delFile = rchExamBean.getFilenm();
							if ( delFile != null && delFile.trim().equals("") == false) {
								FileManager.doFileDelete(context.getRealPath(delFile));
							}
						}
					}
				}
				
			} else {
				//마스터 저장 (TEMP)
				result = rchInsMst(con, Bean);
			}							
			con.commit();
		 } catch (Exception e) {
			con.rollback();
			logger.error("ERROR",e);
			ConnectionManager.close(con);
			throw e;
	     } finally {
	    	 con.setAutoCommit(true);
	    	 ConnectionManager.close(con);
	     }
		return result;
	}
	
	/**
	 * 설문조사그룹 전체 저장
	 * @param Bean
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchGrpAllSave(ResearchBean Bean, ResearchForm rchForm) throws Exception {
		Connection con = null;
        int result = 0;
        
		try {
			con = ConnectionManager.getConnection(false);
			
			//임시테이블에 값이 있거나, 정규테이블에 값이 있을때는 업데이트 한다.
			if(checkCntGrp(Bean.getSessionId())|| Bean.getRchgrpno()> 0 ){
				//마스터 저장
				result = rchGrpUptMst(con, Bean, 0);
			} else {
				//마스터 저장 (TEMP)
				result = rchGrpInsMst(con, Bean);
			}							
			con.commit();
		 } catch (Exception e) {
			con.rollback();
			logger.error("ERROR",e);
			ConnectionManager.close(con);
			throw e;
	     } finally {
	    	 con.setAutoCommit(true);
	    	 ConnectionManager.close(con);
	     }
		return result;
	}

	
	/**
	 * 설문조사 저장
	 * @param sessi
	 * @return
	 * @throws Exception
	 */
	public int rchInsComp(String sessionId, ResearchBean Bean, ServletContext context, String saveDir) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		int rchno = 0;	
		
		try{				
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);

			if(Bean.getMdrchno()==0){
				//신청양식 번호가져오기
				StringBuffer selectQuery1 = new StringBuffer();
				selectQuery1.append("SELECT RCHSEQ.NEXTVAL FROM DUAL ");
	
				pstmt = con.prepareStatement(selectQuery1.toString());
				
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					rchno = rs.getInt(1);
				}
				if (pstmt != null){
					try {
						rs.close();
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}	
	
				//양식마스터 복사
				StringBuffer insertQuery2 = new StringBuffer();
				insertQuery2.append("\n INSERT INTO RCHMST ");
				insertQuery2.append("\n SELECT " + rchno + ", TITLE, STRDT, ENDDT, COLDEPTCD, ");
				insertQuery2.append("\n        COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, DUPLICHECK, LIMITCOUNT, ");
				insertQuery2.append("\n        GROUPYN, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), CRTUSRID, '', '' ");
				insertQuery2.append("\n   FROM RCHMST_TEMP ");
				insertQuery2.append("\n  WHERE SESSIONID LIKE ? "); 
	
				pstmt = con.prepareStatement(insertQuery2.toString());
				pstmt.setString(1, sessionId);
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
				
				//양식 문항 복사
				StringBuffer selectQuery3 = new StringBuffer();
				selectQuery3.append("\n INSERT INTO RCHSUB ");
				selectQuery3.append("\n SELECT " + rchno + ", FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE ");
				selectQuery3.append("\n   FROM RCHSUB_TEMP ");
				selectQuery3.append("\n  WHERE SESSIONID LIKE ? "); 
				
				pstmt = con.prepareStatement(selectQuery3.toString());
				pstmt.setString(1, sessionId);
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}	
				
				//양식 보기 복사
				StringBuffer selectQuery4 = new StringBuffer();
				selectQuery4.append("\n INSERT INTO RCHEXAM ");
				selectQuery4.append("\n SELECT " + rchno + ", FORMSEQ, EXAMSEQ, EXAMCONT ");
				selectQuery4.append("\n   FROM RCHEXAM_TEMP ");
				selectQuery4.append("\n  WHERE SESSIONID LIKE ? "); 
				pstmt = con.prepareStatement(selectQuery4.toString());
				pstmt.setString(1, sessionId);
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}							
			} else {
				rchUptMst(con, Bean, Bean.getMdrchno());
				
				rchInsSub(con, Bean.getListrch(), Bean.getSessionId(), Bean.getMdrchno());
				
				StringBuffer delSubQuery = new StringBuffer();
				delSubQuery.append("\n DELETE FROM RCHDEPT 	");
				delSubQuery.append("\n  WHERE RCHNO = ? 	");
				
				pstmt = con.prepareStatement(delSubQuery.toString());
				pstmt.setInt(1, Bean.getMdrchno());
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
				
				StringBuffer delSubQuery1 = new StringBuffer();
				delSubQuery1.append("\n DELETE FROM RCHDEPTLIST 	");
				delSubQuery1.append("\n  WHERE RCHNO = ? 	");
				
				pstmt = con.prepareStatement(delSubQuery1.toString());
				pstmt.setInt(1, Bean.getMdrchno());
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
				
				StringBuffer delSubQuery2 = new StringBuffer();
				delSubQuery2.append("\n DELETE FROM RCHOTHERTARGET 	");
				delSubQuery2.append("\n  WHERE RCHNO = ? 	");
				
				pstmt = con.prepareStatement(delSubQuery2.toString());
				pstmt.setInt(1, Bean.getMdrchno());
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
				
				rchno = Bean.getMdrchno();
			}
			
			//원본첨부파일 삭제
			delRchSubExamAllFile(con, "", rchno, context);
			
			//첨부파일 복사
			copyRchSubExamFile(con, sessionId, rchno, context, saveDir, "SAVE");
			
			//대상부서지정 복사
			StringBuffer selectQuery5 = new StringBuffer();
			selectQuery5.append("\n INSERT INTO RCHDEPT ");
			selectQuery5.append("\n SELECT '" + rchno + "', TGTCODE, TGTNAME, TGTGBN ");
			selectQuery5.append("\n   FROM RCHDEPT_TEMP  ");
			selectQuery5.append("\n  WHERE SESSIONID LIKE ? "); 
			pstmt = con.prepareStatement(selectQuery5.toString());
			pstmt.setString(1, sessionId);
			
			pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}	
			
			//대상부서리스트지정 복사
			StringBuffer selectQuery6 = new StringBuffer();
			selectQuery6.append("\n INSERT INTO RCHDEPTLIST ");
			selectQuery6.append("\n SELECT '" + rchno + "', SEQ, GRPCD, GRPNM, GRPGBN ");
			selectQuery6.append("\n   FROM RCHDEPTLIST_TEMP  ");
			selectQuery6.append("\n  WHERE SESSIONID LIKE ? "); 
			pstmt = con.prepareStatement(selectQuery6.toString());
			pstmt.setString(1, sessionId);
			
			pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			//기타대상 복사
			StringBuffer selectQuery7 = new StringBuffer();
			selectQuery7.append("\n INSERT INTO RCHOTHERTARGET ");
			selectQuery7.append("\n SELECT '" + rchno + "', TGTCODE, TGTNAME, TGTGBN ");
			selectQuery7.append("\n   FROM RCHOTHERTARGET_TEMP  ");
			selectQuery7.append("\n  WHERE SESSIONID LIKE ? "); 
			pstmt = con.prepareStatement(selectQuery7.toString());
			pstmt.setString(1, sessionId);
			
			pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}

			con.commit();
		} catch (Exception e) {
			rchno = -1;
			con.rollback();
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
	     } finally {	
	    	 con.setAutoCommit(true);
	    	 ConnectionManager.close(con,pstmt,rs);	   
	     }	
		return rchno;
	}
	
	/**
	 * 설문조사그룹 저장
	 * @param sessi
	 * @return
	 * @throws Exception
	 */
	public int rchGrpInsComp(String sessionId, ResearchBean Bean) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		int rchgrpno = 0;	
		
		try{				
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);

			if(Bean.getMdrchgrpno()==0){
				//신청양식 번호가져오기
				StringBuffer selectQuery1 = new StringBuffer();
				selectQuery1.append("SELECT RCHGRPSEQ.NEXTVAL FROM DUAL ");
	
				pstmt = con.prepareStatement(selectQuery1.toString());
				
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					rchgrpno = rs.getInt(1);
				}
				if (pstmt != null){
					try {
						rs.close();
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}	
	
				//양식마스터 복사
				StringBuffer insertQuery2 = new StringBuffer();
				insertQuery2.append("\n INSERT INTO RCHGRPMST ");
				insertQuery2.append("\n SELECT " + rchgrpno + ", TITLE, STRDT, ENDDT, COLDEPTCD, ");
				insertQuery2.append("\n        COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, DUPLICHECK, LIMITCOUNT, ");
				insertQuery2.append("\n        GROUPYN, RCHNOLIST, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), CRTUSRID, '', '' ");
				insertQuery2.append("\n   FROM RCHGRPMST_TEMP ");
				insertQuery2.append("\n  WHERE SESSIONID LIKE ? "); 
	
				pstmt = con.prepareStatement(insertQuery2.toString());
				pstmt.setString(1, sessionId);
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
			} else {
				rchGrpUptMst(con, Bean, Bean.getMdrchgrpno());
				
				StringBuffer delSubQuery = new StringBuffer();
				delSubQuery.append("\n DELETE FROM RCHGRPDEPT 	");
				delSubQuery.append("\n  WHERE RCHGRPNO = ? 	");
				
				pstmt = con.prepareStatement(delSubQuery.toString());
				pstmt.setInt(1, Bean.getMdrchgrpno());
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
				
				StringBuffer delSubQuery1 = new StringBuffer();
				delSubQuery1.append("\n DELETE FROM RCHGRPDEPTLIST 	");
				delSubQuery1.append("\n  WHERE RCHGRPNO = ? 	");
				
				pstmt = con.prepareStatement(delSubQuery1.toString());
				pstmt.setInt(1, Bean.getMdrchgrpno());
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
				
				StringBuffer delSubQuery2 = new StringBuffer();
				delSubQuery2.append("\n DELETE FROM RCHGRPOTHERTARGET 	");
				delSubQuery2.append("\n  WHERE RCHGRPNO = ? 	");
				
				pstmt = con.prepareStatement(delSubQuery2.toString());
				pstmt.setInt(1, Bean.getMdrchgrpno());
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
				
				rchgrpno = Bean.getMdrchgrpno();
			}
			
			//대상부서지정 복사
			StringBuffer selectQuery5 = new StringBuffer();
			selectQuery5.append("\n INSERT INTO RCHGRPDEPT ");
			selectQuery5.append("\n SELECT '" + rchgrpno + "', TGTCODE, TGTNAME, TGTGBN ");
			selectQuery5.append("\n   FROM RCHGRPDEPT_TEMP  ");
			selectQuery5.append("\n  WHERE SESSIONID LIKE ? "); 
			pstmt = con.prepareStatement(selectQuery5.toString());
			pstmt.setString(1, sessionId);
			
			pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}	
			
			//대상부서리스트지정 복사
			StringBuffer selectQuery6 = new StringBuffer();
			selectQuery6.append("\n INSERT INTO RCHGRPDEPTLIST ");
			selectQuery6.append("\n SELECT '" + rchgrpno + "', SEQ, GRPCD, GRPNM, GRPGBN ");
			selectQuery6.append("\n   FROM RCHGRPDEPTLIST_TEMP  ");
			selectQuery6.append("\n  WHERE SESSIONID LIKE ? "); 
			pstmt = con.prepareStatement(selectQuery6.toString());
			pstmt.setString(1, sessionId);
			
			pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			//기타대상 복사
			StringBuffer selectQuery7 = new StringBuffer();
			selectQuery7.append("\n INSERT INTO RCHGRPOTHERTARGET ");
			selectQuery7.append("\n SELECT '" + rchgrpno + "', TGTCODE, TGTNAME, TGTGBN ");
			selectQuery7.append("\n   FROM RCHGRPOTHERTARGET_TEMP  ");
			selectQuery7.append("\n  WHERE SESSIONID LIKE ? "); 
			pstmt = con.prepareStatement(selectQuery7.toString());
			pstmt.setString(1, sessionId);
			
			pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}

			con.commit();
		} catch (Exception e) {
			rchgrpno = -1;
			con.rollback();
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
	     } finally {	
	    	 con.setAutoCommit(true);
	    	 ConnectionManager.close(con,pstmt,rs);	   
	     }	
		return rchgrpno;
	}
	
	/**
	 * 설문조사 저장
	 * @param conn
	 * @param Bean
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchInsMst(Connection conn, ResearchBean Bean) throws Exception {
		PreparedStatement pstmt = null;
        int result = 0;
        int bindPos = 0;
        int[] ret = null;
        
		String summary = null;
		String tgtdeptnm = null;
		String headcont = null;
		String tailcont = null;
		
		if(Bean.getSummary() != null) {
			summary = Bean.getSummary().replaceAll("'", "''");
		} else {
			summary = "";
		}
				System.out.println("summary :"+summary);
		if(Bean.getTgtdeptnm() != null) {
			tgtdeptnm = Bean.getTgtdeptnm().replaceAll("'", "''");
		} else {
			tgtdeptnm = "";
		}
				System.out.println("tgtdeptnm :"+tgtdeptnm);
		if(Bean.getHeadcont() != null) {
			headcont = Bean.getHeadcont().replaceAll("'", "''");
		} else {
			headcont = "";
		}
			System.out.println("headcont :"+headcont);
		if(Bean.getTailcont() != null) {
			tailcont = Bean.getTailcont().replaceAll("'", "''");
		} else {
			tailcont = "";
		}
			System.out.println("tailcont :"+tailcont);
		String insMstQuery = "";

		System.out.println("title :"+ Bean.getTitle());
		insMstQuery = "INSERT INTO RCHMST_TEMP" +
		"(		SESSIONID,	TITLE,		STRDT,		ENDDT,		COLDEPTCD, " +
        "       COLDEPTNM,	COLDEPTTEL, CHRGUSRID,	CHRGUSRNM,	SUMMARY,	EXHIBIT,	OPENTYPE, " +
        "       RANGE,		IMGPREVIEW,	DUPLICHECK, LIMITCOUNT, GROUPYN,	TGTDEPTNM,	HEADCONT, " +
        "       TAILCONT,	ANSCOUNT,	CRTDT,		CRTUSRID) " +
        "VALUES(?,			?,			?,			?,			?, " +
        "       ?,			?,			?,			?,			'" + summary + "', ?,	?, " +
        "       ?,			?,			?,			?,			?,			'"+ tgtdeptnm+ "', '"+ headcont+ "', " +
        "		'" + tailcont + "',	0,	TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ?) ";	
			
		pstmt = conn.prepareStatement(insMstQuery);
		
		pstmt.setString(++bindPos, Bean.getSessionId());
		pstmt.setString(++bindPos, Bean.getTitle());		//제목
		pstmt.setString(++bindPos, Bean.getStrdt());		//시작일
		pstmt.setString(++bindPos, Bean.getEnddt());		//종료일
		pstmt.setString(++bindPos, Bean.getColdeptcd());	//담당부서코드
		pstmt.setString(++bindPos, Bean.getColdeptnm());	//담당부서명
		pstmt.setString(++bindPos, Bean.getColdepttel());   //담당부서전화
		pstmt.setString(++bindPos, Bean.getChrgusrid());	//담당자ID
		pstmt.setString(++bindPos, Bean.getChrgusrnm());	//담당자명
		pstmt.setString(++bindPos, Bean.getExhibit());		//결과공개여부
		pstmt.setString(++bindPos, Bean.getOpentype());		//조사방법
		pstmt.setString(++bindPos, ( "1".equals(Bean.getRange()) ) ? Bean.getRange() : Bean.getRangedetail());		//범위
		pstmt.setString(++bindPos, Bean.getImgpreview());	//첨부그림미리보기
		pstmt.setString(++bindPos, Bean.getDuplicheck());	//중복답변체크
		pstmt.setInt(++bindPos, Bean.getLimitcount());		//목표응답수
		pstmt.setString(++bindPos, Bean.getGroupyn());		//설문그룹여부
		pstmt.setString(++bindPos, Bean.getChrgusrid());	//생성자ID

		result = pstmt.executeUpdate();
		
		if (pstmt != null){
			try {
				pstmt.close();
				pstmt = null;
			} catch(SQLException ignored){ }
		}
			
		StringBuffer insSubQuery = new StringBuffer();
			
		insSubQuery.append("\n INSERT INTO RCHSUB_TEMP(SESSIONID, FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE ) ");
		insSubQuery.append("\n VALUES (?, ?, '', '', '1', 'N', 'N') ");	
		
		pstmt = conn.prepareStatement(insSubQuery.toString());
		
		for(int i=0;i<Bean.getFormcount();i++){

			pstmt.setString(1, Bean.getSessionId());
			pstmt.setInt(2, i+1);
			
			pstmt.addBatch();		
		}	   
		
		ret = pstmt.executeBatch();
		result = result + ret.length;	
		
		if (pstmt != null){
			try {
				pstmt.close();
				pstmt = null;
			} catch(SQLException ignored){ }
		}
		
		return result;
	}
	
	/**
	 * 설문조사그룹 저장
	 * @param conn
	 * @param Bean
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchGrpInsMst(Connection conn, ResearchBean Bean) throws Exception {
		PreparedStatement pstmt = null;
        int result = 0;
        int bindPos = 0;
        
		String summary = null;
		String tgtdeptnm = null;
		String headcont = null;
		String tailcont = null;
		
		if(Bean.getSummary() != null) {
			summary = Bean.getSummary().replaceAll("'", "''");
		} else {
			summary = "";
		}
				
		if(Bean.getTgtdeptnm() != null) {
			tgtdeptnm = Bean.getTgtdeptnm().replaceAll("'", "''");
		} else {
			tgtdeptnm = "";
		}
		
		if(Bean.getHeadcont() != null) {
			headcont = Bean.getHeadcont().replaceAll("'", "''");
		} else {
			headcont = "";
		}
		
		if(Bean.getTailcont() != null) {
			tailcont = Bean.getTailcont().replaceAll("'", "''");
		} else {
			tailcont = "";
		}
		
		String insMstQuery = "";

		insMstQuery = "INSERT INTO RCHGRPMST_TEMP" +
		"(		SESSIONID,	TITLE,		STRDT,		ENDDT,		COLDEPTCD, " +
        "       COLDEPTNM,	COLDEPTTEL, CHRGUSRID,	CHRGUSRNM,	SUMMARY,	EXHIBIT,	OPENTYPE, " +
        "       RANGE,		IMGPREVIEW,	DUPLICHECK, LIMITCOUNT, GROUPYN,	RCHNOLIST,	TGTDEPTNM,	HEADCONT, " +
        "       TAILCONT,	ANSCOUNT,	CRTDT,		CRTUSRID) " +
        "VALUES(?,			?,			?,			?,			?, " +
        "       ?,			?,			?,			?,			'" + summary + "', ?,	?, " +
        "       ?,			?,			?,			?,			?,			?,	'"+ tgtdeptnm+ "', '"+ headcont+ "', " +
        "		'" + tailcont + "',	0,	TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ?) ";	
			
		pstmt = conn.prepareStatement(insMstQuery);
		
		pstmt.setString(++bindPos, Bean.getSessionId());
		pstmt.setString(++bindPos, Bean.getTitle());		//제목
		pstmt.setString(++bindPos, Bean.getStrdt());		//시작일
		pstmt.setString(++bindPos, Bean.getEnddt());		//종료일
		pstmt.setString(++bindPos, Bean.getColdeptcd());	//담당부서코드
		pstmt.setString(++bindPos, Bean.getColdeptnm());	//담당부서명
		pstmt.setString(++bindPos, Bean.getColdepttel());   //담당부서전화
		pstmt.setString(++bindPos, Bean.getChrgusrid());	//담당자ID
		pstmt.setString(++bindPos, Bean.getChrgusrnm());	//담당자명
		pstmt.setString(++bindPos, Bean.getExhibit());		//결과공개여부
		pstmt.setString(++bindPos, Bean.getOpentype());		//조사방법
		pstmt.setString(++bindPos, ( "1".equals(Bean.getRange()) ) ? Bean.getRange() : Bean.getRangedetail());		//범위
		pstmt.setString(++bindPos, Bean.getImgpreview());	//첨부그림미리보기
		pstmt.setString(++bindPos, Bean.getDuplicheck());	//중복답변체크
		pstmt.setInt(++bindPos, Bean.getLimitcount());		//목표응답수
		pstmt.setString(++bindPos, Bean.getGroupyn());		//설문그룹여부
		pstmt.setString(++bindPos, Bean.getRchnolist());	//설문그룹여부
		pstmt.setString(++bindPos, Bean.getChrgusrid());	//생성자ID

		result = pstmt.executeUpdate();
		
		if (pstmt != null){
			try {
				pstmt.close();
				pstmt = null;
			} catch(SQLException ignored){ }
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param conn
	 * @param subList
	 * @param formcount
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchInsSub(Connection conn, List subList, String sessionId, int rchno) throws Exception {
		PreparedStatement pstmt = null;
        int[] ret = null; 
        int result = 0;

		StringBuffer delSubQuery = new StringBuffer();
		String insSubQuery = "";
		
		if(rchno ==0 ){
			delSubQuery.append("\n DELETE FROM RCHSUB_TEMP 	");
			delSubQuery.append("\n  WHERE SESSIONID LIKE ? 	");

			insSubQuery = "INSERT INTO RCHSUB_TEMP" +
			"(		 SESSIONID,	FORMSEQ,	FORMGRP,	FORMTITLE,		" +
			"		 FORMTYPE, 	SECURITY, 	EXAMTYPE	 ) " +
	        "VALUES( ?,			?,			?,			?,			" +
	        "		 ?, 		?,			? ) ";
		}else{
			delSubQuery.append("\n DELETE FROM RCHSUB 	");
			delSubQuery.append("\n  WHERE RCHNO = ? 	");

			insSubQuery = "INSERT INTO RCHSUB" +
			"(		 RCHNO,		FORMSEQ,	FORMGRP,	FORMTITLE,		" +
			"		 FORMTYPE, 	SECURITY,   EXAMTYPE	 ) " +
	        "VALUES( ?,			?,			?,			?,			" +
	        "		 ?, 		?,			? ) ";	
		}
		
		try {		
			
			pstmt = conn.prepareStatement(delSubQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);
			}else{
				pstmt.setInt(1, rchno);
			}

			result = pstmt.executeUpdate();

			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			pstmt = conn.prepareStatement(insSubQuery);	
			
			if(subList != null){
				ResearchSubBean subBean = null;
				for(int i=0; i<subList.size(); i++){
					subBean = (ResearchSubBean)subList.get(i);
					if(rchno==0){
						pstmt.setString(1, sessionId);
					}else{
						pstmt.setInt(1, rchno);
					}	
					pstmt.setInt(2, subBean.getFormseq());
					pstmt.setString(3, subBean.getFormgrp());
					pstmt.setString(4, subBean.getFormtitle());
					pstmt.setString(5, subBean.getFormtype());
					pstmt.setString(6, subBean.getSecurity());
					pstmt.setString(7, subBean.getExamtype());
					
					if ( ("01".equals(subBean.getFormtype()) || "02".equals(subBean.getFormtype())) && subBean.getExamList() != null ) {
						int cnt = 0;
						for ( ; cnt < subBean.getExamList().size(); cnt++ ) {
							ResearchExamBean examBean = (ResearchExamBean)subBean.getExamList().get(cnt);
							if ( examBean != null && !"".equals(Utils.nullToEmptyString(examBean.getExamcont())) ) break;
						}
						if ( cnt == subBean.getExamList().size() ) {
							pstmt.setString(5, "03");
							pstmt.setString(6, "N");
							pstmt.setString(7, "N");
						}
					}
					
					pstmt.addBatch();
				}	
			}

			ret = pstmt.executeBatch();
			result = result + ret.length;
			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			if(subList != null){
				ResearchSubBean subBean = null;
				for(int i=0; i<subList.size(); i++){
					subBean = (ResearchSubBean)subList.get(i);
					result += rchInsExam(conn, subBean.getExamList(), subBean.getFormseq(), sessionId, rchno, i);
				}
			}

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
	 * 항목추가 (빈항목 저장)
	 */
	public int insAddSub(int rchno, String sessionId) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result = 0;
		StringBuffer insertQuery = new StringBuffer();

		if(rchno == 0){
			insertQuery.append("INSERT INTO RCHSUB_TEMP (SESSIONID, FORMSEQ, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE ) ");
			insertQuery.append("VALUES(?, ?, '', '1', 'N', 'N') ");		
		} else {
			insertQuery.append("INSERT INTO RCHSUB (RCHNO, FORMSEQ, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE ) ");
			insertQuery.append("VALUES(?, ?, '', '1', 'N', 'N') ");
		}			
		
		try{			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
			
			pstmt.setInt(2, getMaxSubSeq(rchno, sessionId));
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			 result = -1;
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(con,pstmt);   
	     }
		return result;
	}
	
	/**
	 * 
	 * @param rchno
	 * @param sessionId
	 * @param formcount
	 * @return
	 * @throws Exception
	 */
	public int insMakeSub(int rchno, String sessionId, int formcount) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		int precnt = getCntSubSeq(rchno, sessionId);
		int maxseq = getMaxSubSeq(rchno, sessionId);
		int result = 0;
		int[] ret = null;
		
		StringBuffer insertQuery = new StringBuffer();
		StringBuffer selectQuery = new StringBuffer();	
		//삭제 처리
		StringBuffer delteQuery = new StringBuffer();	
		
				
		if(rchno == 0){
			insertQuery.append("INSERT INTO RCHSUB_TEMP(SESSIONID, FORMSEQ, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE ) ");
			insertQuery.append("VALUES(?, ?, '', '1', 'N', 'N') ");	
			
			delteQuery.append("DELETE FROM RCHSUB_TEMP WHERE SESSIONID LIKE ? AND FORMSEQ = ? ");
			
			selectQuery.append("SELECT NVL(MAX(FORMSEQ),0) FROM RCHSUB_TEMP WHERE SESSIONID LIKE ? ");
		} else {
			insertQuery.append("INSERT INTO RCHSUB(RCHNO, FORMSEQ, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE ) ");
			insertQuery.append("VALUES(?, ?, '', '1', 'N', 'N') ");
			
			delteQuery.append("DELETE FROM RCHSUB WHERE RCHNO = ? AND FORMSEQ = ? ");
			
			selectQuery.append("SELECT NVL(MAX(FORMSEQ),0) FROM RCHSUB WHERE RCHNO = ? ");
		}		
		
		try{			
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			if(formcount == precnt){
				return 0;  
			} else if(formcount > precnt) {
				int cnt = formcount - precnt;
				
				pstmt = con.prepareStatement(insertQuery.toString());
				for(int i=0;i<cnt;i++){
					
					if(rchno == 0){
						pstmt.setString(1, sessionId);
					} else {
						pstmt.setInt(1, rchno);
					}
					
					pstmt.setInt(2, maxseq+i);
					pstmt.addBatch();	
				}
				ret = pstmt.executeBatch();
				result = result + ret.length;	
				
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
			} else if(formcount < precnt){
				//항목 차이 수량 만큼 삭제
				int cnt = precnt - formcount;				
				
				for(int i=0;i<cnt;i++){
										
					pstmt = con.prepareStatement(selectQuery.toString());
					
					if(rchno == 0){
						pstmt.setString(1, sessionId);	
					} else {
						pstmt.setInt(1, rchno);
					}
								
					rs = pstmt.executeQuery();	
					
					int formseq = 0;
					if ( rs.next() ){
						formseq = rs.getInt(1);
					}	
					
					if (pstmt != null){
						try {
							rs.close();
							pstmt.close();
							pstmt = null;
						} catch(SQLException ignored){ }
					}
										
					pstmt = con.prepareStatement(delteQuery.toString());
					
					if(rchno == 0){
						pstmt.setString(1, sessionId);	
					} else {
						pstmt.setInt(1, rchno);
					}					
					pstmt.setInt(2, formseq);
								
					result += pstmt.executeUpdate();
					
					if (pstmt != null){
						try {
							pstmt.close();
							pstmt = null;
						} catch(SQLException ignored){ }
					}
					
					//보기 삭제
					result += rchDelExam(con, rchno, sessionId, formseq);
				}
			}			
			
			con.commit();
		} catch (Exception e) {
			try{
				result = -1;
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt,rs);
			 throw e;
	     } finally {	
	    	 try{ 
		 		con.setAutoCommit(true);
	    	 } catch (Exception e){
				logger.error("ERROR",e);
//				throw e;
	    	 }
			ConnectionManager.close(con,pstmt,rs);	   
	     }	
		return result;
	}	
	
	/**
	 * 
	 * @param conn
	 * @param subList
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchInsExam(Connection conn, List examList, int formseq, String sessionId, int rchno, int cnt) throws Exception {
		PreparedStatement pstmt = null;
        int result = 0;
        int[] ret = null;

		StringBuffer delExamQuery = new StringBuffer();
		String insExamQuery = "";
		
		if(rchno ==0 ){
			delExamQuery.append("\n DELETE FROM RCHEXAM_TEMP ");
			delExamQuery.append("\n  WHERE SESSIONID LIKE ? 	");
			
			insExamQuery = "INSERT INTO RCHEXAM_TEMP" +
			"(		SESSIONID,		FORMSEQ,	EXAMSEQ,	EXAMCONT	 ) " +
	        "VALUES(?,			?,			?,			?				) ";
		}else{
			delExamQuery.append("\n DELETE FROM RCHEXAM ");
			delExamQuery.append("\n  WHERE RCHNO = ? 	");
			
			insExamQuery = "INSERT INTO RCHEXAM" +
			"(		RCHNO,		FORMSEQ,	EXAMSEQ,	EXAMCONT	 ) " +
	        "VALUES(?,			?,			?,			?			) ";			
		}
		
		if(cnt == 0){
			pstmt = conn.prepareStatement(delExamQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);
			}else{
				pstmt.setInt(1, rchno);
			}
	
			result = pstmt.executeUpdate();
						
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
		}
		
		ResearchExamBean examBean = null;
		pstmt = conn.prepareStatement(insExamQuery);
		if(examList != null){
			for(int j=0; j<examList.size();j++){
				examBean = (ResearchExamBean)examList.get(j);
				
				if ( "".equals(Utils.nullToEmptyString(examBean.getExamcont())) ) continue;

				if(rchno == 0){
					pstmt.setString(1, sessionId);
				}else{
					pstmt.setInt(1, rchno);
				}
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, examBean.getExamseq() );
				pstmt.setString(4, examBean.getExamcont());
				
				pstmt.addBatch();
			}
			ret = pstmt.executeBatch();
			result = result +ret.length;	
		}
		
		if (pstmt != null){
			try {
				pstmt.close();
				pstmt = null;
			} catch(SQLException ignored){ }
		}
		     
		return result;
	}	
	
	
	/**
	 * 설문조사 수정
	 * @param conn
	 * @param Bean
	 * @param subList
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchUptMst(Connection conn, ResearchBean Bean, int mdrchno) throws Exception {
		PreparedStatement pstmt = null;
        int result = 0;

		try {
			String summary = null;
			String tgtdeptnm = null;
			String headcont = null;
			String tailcont = null;
			String query = null;
			
			if(Bean.getSummary() != null) {
				summary = Bean.getSummary().replaceAll("'", "''");
			} else {
				summary = "";
			}
			
			if(Bean.getTgtdeptnm() != null) {
				tgtdeptnm = Bean.getTgtdeptnm().replaceAll("'", "''");
			} else {
				tgtdeptnm = "";
			}
			
			if(Bean.getHeadcont() != null) {
				headcont = Bean.getHeadcont().replaceAll("'", "''");
			} else {
				headcont = "";
			}
			
			if(Bean.getTailcont() != null) {
				tailcont = Bean.getTailcont().replaceAll("'", "''");
			} else {
				tailcont = "";
			}		
			
			if(mdrchno==0){
				query =	"UPDATE RCHMST_TEMP" +
				"   SET	TITLE = ?,		STRDT = ?,		ENDDT = ?,		COLDEPTCD = ?,  COLDEPTNM = ?,	COLDEPTTEL = ?, " +
				"	    CHRGUSRID = ?,	CHRGUSRNM = ?,	SUMMARY = '" + summary + "',	EXHIBIT = ?,	OPENTYPE = ?, 	RANGE = ?,	IMGPREVIEW = ?,	DUPLICHECK = ?, LIMITCOUNT = ?, " +
				"		GROUPYN = ?, 	TGTDEPTNM = '"+ tgtdeptnm+ "',	HEADCONT = '"+ headcont+ "',	TAILCONT = '" + tailcont + "',	UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')," +
		        "		UPTUSRID = ?    " +
		        " WHERE SESSIONID LIKE ? ";
			}else{
				query =	"UPDATE RCHMST" +
				"   SET	TITLE = ?,		STRDT = ?,		ENDDT = ?,		COLDEPTCD = ?,  COLDEPTNM = ?,	COLDEPTTEL = ?, " +
				"	    CHRGUSRID = ?,	CHRGUSRNM = ?,	SUMMARY = '" + summary + "',	EXHIBIT = ?,	OPENTYPE = ?, 	RANGE = ?,	IMGPREVIEW = ?,	DUPLICHECK = ?, LIMITCOUNT = ?, " +
				"		GROUPYN = ?, 	TGTDEPTNM = '"+ tgtdeptnm+ "',	HEADCONT = '"+ headcont+ "',	TAILCONT = '" + tailcont + "',	UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')," +
		        "		UPTUSRID = ?    " +
		        " WHERE RCHNO =  ?  ";
			}

			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, Bean.getTitle());		//제목
			pstmt.setString(2, Bean.getStrdt());		//시작일
			pstmt.setString(3, Bean.getEnddt());		//종료일
			pstmt.setString(4, Bean.getColdeptcd());	//담당부서코드
			pstmt.setString(5, Bean.getColdeptnm());	//담당부서명
			pstmt.setString(6, Bean.getColdepttel());   //담당부서전화
			pstmt.setString(7, Bean.getChrgusrid());	//담당자ID
			pstmt.setString(8, Bean.getChrgusrnm());	//담당자명
			pstmt.setString(9, Bean.getExhibit());		//경과공개여부
			pstmt.setString(10, Bean.getOpentype());	//조사방법
			pstmt.setString(11, ( "1".equals(Bean.getRange()) ) ? Bean.getRange() : Bean.getRangedetail());		//범위
			pstmt.setString(12, Bean.getImgpreview());	//첨부그림미리보기
			pstmt.setString(13, Bean.getDuplicheck());	//중복답변체크
			pstmt.setInt(14, Bean.getLimitcount());	//목표응답수
			pstmt.setString(15, Bean.getGroupyn());	//설문그룹여부
			pstmt.setString(16, Bean.getChrgusrid());	//생성자ID
			
			if(mdrchno ==0){
				pstmt.setString(17, Bean.getSessionId());
			}else{
				pstmt.setInt(17, mdrchno);
			}

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
	 * 설문조사그룹 수정
	 * @param conn
	 * @param Bean
	 * @param subList
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchGrpUptMst(Connection conn, ResearchBean Bean, int mdrchgrpno) throws Exception {
		PreparedStatement pstmt = null;
        int result = 0;

		try {
			String summary = null;
			String tgtdeptnm = null;
			String headcont = null;
			String tailcont = null;
			String query = null;
			
			if(Bean.getSummary() != null) {
				summary = Bean.getSummary().replaceAll("'", "''");
			} else {
				summary = "";
			}
			
			if(Bean.getTgtdeptnm() != null) {
				tgtdeptnm = Bean.getTgtdeptnm().replaceAll("'", "''");
			} else {
				tgtdeptnm = "";
			}
			
			if(Bean.getHeadcont() != null) {
				headcont = Bean.getHeadcont().replaceAll("'", "''");
			} else {
				headcont = "";
			}
			
			if(Bean.getTailcont() != null) {
				tailcont = Bean.getTailcont().replaceAll("'", "''");
			} else {
				tailcont = "";
			}		
			
			if(mdrchgrpno==0){
				query =	"UPDATE RCHGRPMST_TEMP" +
				"   SET	TITLE = ?,		STRDT = ?,		ENDDT = ?,		COLDEPTCD = ?,  COLDEPTNM = ?,	COLDEPTTEL = ?, " +
				"	    CHRGUSRID = ?,	CHRGUSRNM = ?,	SUMMARY = '" + summary + "',	EXHIBIT = ?,	OPENTYPE = ?, 	RANGE = ?,	IMGPREVIEW = ?,	DUPLICHECK = ?, LIMITCOUNT = ?, " +
				"		GROUPYN = ?, 	RCHNOLIST = ?,	TGTDEPTNM = '"+ tgtdeptnm+ "',	HEADCONT = '"+ headcont+ "',	TAILCONT = '" + tailcont + "',	UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')," +
		        "		UPTUSRID = ?    " +
		        " WHERE SESSIONID LIKE ? ";
			}else{
				query =	"UPDATE RCHGRPMST" +
				"   SET	TITLE = ?,		STRDT = ?,		ENDDT = ?,		COLDEPTCD = ?,  COLDEPTNM = ?,	COLDEPTTEL = ?, " +
				"	    CHRGUSRID = ?,	CHRGUSRNM = ?,	SUMMARY = '" + summary + "',	EXHIBIT = ?,	OPENTYPE = ?, 	RANGE = ?,	IMGPREVIEW = ?,	DUPLICHECK = ?, LIMITCOUNT = ?, " +
				"		GROUPYN = ?, 	RCHNOLIST = ?,	TGTDEPTNM = '"+ tgtdeptnm+ "',	HEADCONT = '"+ headcont+ "',	TAILCONT = '" + tailcont + "',	UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')," +
		        "		UPTUSRID = ?    " +
		        " WHERE RCHGRPNO =  ?  ";
			}

			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, Bean.getTitle());		//제목
			pstmt.setString(2, Bean.getStrdt());		//시작일
			pstmt.setString(3, Bean.getEnddt());		//종료일
			pstmt.setString(4, Bean.getColdeptcd());	//담당부서코드
			pstmt.setString(5, Bean.getColdeptnm());	//담당부서명
			pstmt.setString(6, Bean.getColdepttel());   //담당부서전화
			pstmt.setString(7, Bean.getChrgusrid());	//담당자ID
			pstmt.setString(8, Bean.getChrgusrnm());	//담당자명
			pstmt.setString(9, Bean.getExhibit());		//경과공개여부
			pstmt.setString(10, Bean.getOpentype());	//조사방법
			pstmt.setString(11, ( "1".equals(Bean.getRange()) ) ? Bean.getRange() : Bean.getRangedetail());		//범위
			pstmt.setString(12, Bean.getImgpreview());	//첨부그림미리보기
			pstmt.setString(13, Bean.getDuplicheck());	//중복답변체크
			pstmt.setInt(14, Bean.getLimitcount());		//목표응답수
			pstmt.setString(15, Bean.getGroupyn());		//설문그룹여부
			pstmt.setString(16, Bean.getRchnolist());	//설문조사목록
			pstmt.setString(17, Bean.getChrgusrid());	//생성자ID
			
			if(mdrchgrpno ==0){
				pstmt.setString(18, Bean.getSessionId());
			}else{
				pstmt.setInt(18, mdrchgrpno);
			}

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
	 * 설문조사 보기 첨부파일 삭제
	 * @param conn
	 * @param rchExamBean
	 * @param context
	 * @throws Exception
	 */
	public int delRchExamAllFile(Connection conn, String sessionId, int rchno, int formseq, ServletContext context) throws Exception {
		
		int result = 0;

		ResearchSubBean rchSubBean = getRchSubFile(conn, sessionId, rchno, formseq);
		
		if ( rchSubBean != null ) {
			delRchSubFile(conn, sessionId, rchno, formseq, rchSubBean.getFileseq());
			
			String delFile = rchSubBean.getFilenm();
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}
		
		List rchExamList = getRchExamFile(conn, sessionId, rchno, formseq);
		
		for ( int i = 0; rchExamList != null && i < rchExamList.size(); i++ ) {
			ResearchExamBean rchExamBean = (ResearchExamBean)rchExamList.get(i);
			
			if ( rchExamBean != null ) {
				delRchExamFile(conn, sessionId, rchno, formseq, rchExamBean.getExamseq(), rchExamBean.getFileseq());
				
				String delFile = rchExamBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		}

		return result;
	}

	/**
	 * 설문조사 전체 첨부파일 삭제
	 * @param conn
	 * @param rchExamBean
	 * @param context
	 * @throws Exception
	 */
	public int delRchSubExamAllFile(Connection conn, String sessionId, int rchno, ServletContext context) throws Exception {
		
		int result = 0;
		
		List rchSubList = getRchSubFile(conn, sessionId, rchno);
		
		for ( int i = 0; rchSubList != null && i < rchSubList.size(); i++ ) {
			ResearchSubBean rchSubBean = (ResearchSubBean)rchSubList.get(i);
		
			if ( rchSubBean != null ) {
				delRchSubFile(conn, sessionId, rchno, rchSubBean.getFormseq(), rchSubBean.getFileseq());
				
				String delFile = rchSubBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		}
		
		List rchExamList = getRchExamFile(conn, sessionId, rchno, 0);
		
		for ( int i = 0; rchExamList != null && i < rchExamList.size(); i++ ) {
			ResearchExamBean rchExamBean = (ResearchExamBean)rchExamList.get(i);
			
			if ( rchExamBean != null ) {
				delRchExamFile(conn, sessionId, rchno, rchExamBean.getFormseq(), rchExamBean.getExamseq(), rchExamBean.getFileseq());
				
				String delFile = rchExamBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param reqformno
	 * @param sessi
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public int rchDelSub(int rchno, String sessionId, int formseq, ServletContext context) throws Exception{
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result =0;
		StringBuffer sql = new StringBuffer();
		
		if(rchno == 0){
			sql.append("DELETE FROM RCHSUB_TEMP WHERE SESSIONID LIKE ? AND FORMSEQ = ? ");
		} else {
			sql.append("DELETE FROM RCHSUB WHERE RCHNO = ? AND FORMSEQ = ? ");		
		}
		
		try {
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
		    
			pstmt = con.prepareStatement(sql.toString());		
		    if(rchno == 0){
		    	pstmt.setString(1, sessionId);	
		    } else {
		    	pstmt.setInt(1, rchno);	
		    }				
		    pstmt.setInt(2, formseq);
			result = pstmt.executeUpdate();			
			
			ConnectionManager.close(pstmt);
			
			//보기내용 삭제
			result += rchDelExam(con, rchno, sessionId, formseq);
			
			delRchExamAllFile(con, sessionId, rchno, formseq, context);
			
			//삭제 후 질문번호 재배치
			String kfield = null;
			String[] updateTable = null;
			String[] updateTable1 = {"RCHSUB_TEMP", "RCHEXAM_TEMP", "RCHSUBFILE_TEMP", "RCHEXAMFILE_TEMP"};
			String[] updateTable2 = {"RCHSUB", "RCHEXAM", "RCHSUBFILE", "RCHEXAMFILE"};
			if(rchno == 0){
				updateTable = updateTable1;
				kfield = "SESSIONID";
			} else {
				updateTable = updateTable2;
				kfield = "RCHNO";
			}
			
			for ( int i = 0; i < updateTable.length; i++ ) {
				sql.delete(0, sql.capacity());
				sql.append("UPDATE "+updateTable[i]+" SET FORMSEQ = FORMSEQ - 1 WHERE "+kfield+" = ? AND FORMSEQ > ? ");
				
				pstmt = con.prepareStatement(sql.toString());		
			    if(rchno == 0){
			    	pstmt.setString(1, sessionId);	
			    } else {
			    	pstmt.setInt(1, rchno);	
			    }				
			    pstmt.setInt(2, formseq);
				result = pstmt.executeUpdate();
				
				ConnectionManager.close(pstmt);
			}
			
			con.commit();
		} catch (Exception e) {
			try{
				result = -1;
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);	 
			 throw e;
	     } finally {	
	    	 try{ 
		 		con.setAutoCommit(true);
	    	 } catch (Exception e){
				logger.error("ERROR",e);
//				throw e;
	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
	
	/**
	 * 
	 * @param con
	 * @param rchno
	 * @param sessionId
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	private int rchDelExam(Connection con, int rchno, String sessionId, int formseq) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		StringBuffer delteQuery = new StringBuffer();		
		if(rchno == 0){	
			delteQuery.append("DELETE FROM RCHEXAM_TEMP WHERE SESSIONID LIKE ? AND FORMSEQ = ? ");
		} else {
			delteQuery.append("DELETE FROM RCHEXAM WHERE RCHNO = ? AND FORMSEQ = ? ");
		}	
	 
		try{
			pstmt = con.prepareStatement(delteQuery.toString());
			
			if(rchno == 0){	
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}	
			
			pstmt.setInt(2, formseq);
			
			result = pstmt.executeUpdate();			
		
		}catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);	 
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		return result;
	}	
	
	/**
	 * 임시저장된 설문조사 체크
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public boolean checkCnt(String sessionId) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) FROM RCHMST_TEMP WHERE SESSIONID LIKE ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, sessionId);			
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				if(rs.getInt(1) > 0){
					result = true;
				}
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
	 * 임시저장된 설문조사그룹 체크
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public boolean checkCntGrp(String sessionId) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) FROM RCHGRPMST_TEMP WHERE SESSIONID LIKE ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, sessionId);			
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				if(rs.getInt(1) > 0){
					result = true;
				}
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
	 * 
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int getMaxSubSeq(int rchno, String sessionId) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;

		
		StringBuffer selectQuery = new StringBuffer();

		if(rchno == 0){
			selectQuery.append("SELECT NVL(MAX(FORMSEQ),0)+1 FROM RCHSUB_TEMP WHERE SESSIONID LIKE ? ");
		} else {
			
			selectQuery.append("SELECT NVL(MAX(FORMSEQ),0)+1 FROM RCHSUB WHERE RCHNO = ? ");
		}	
		
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);	
			} else {
				pstmt.setInt(1, rchno);
			}
						
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
	 * 
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	private int getCntSubSeq(int rchno, String sessionId) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(rchno == 0){
			selectQuery.append("SELECT COUNT(*) FROM RCHSUB_TEMP WHERE SESSIONID LIKE ? ");
		} else {
			selectQuery.append("SELECT COUNT(*) FROM RCHSUB WHERE RCHNO = ? ");
		}	
		
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);	
			} else {
				pstmt.setInt(1, rchno);
			}
						
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
	 * 기존설문조사 가져오기
	 * @param CommCollDocSearchBean searchBean
	 * @param int start
	 * @param int end
	 * @return List 취합문서목록(ArrayList)
	 * @throws Exception 
	 */
	public List getUsedRchList(ResearchBean schBean, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT (CNT-SEQ+1) BUNHO, X.* " +
							   "FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* " +
							   "      FROM (SELECT ROWNUM SEQ, A2.*  " +
							   "            FROM (SELECT T1.RCHNO, T1.TITLE, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, RANGE " +
							   "                  FROM RCHMST  T1, CCD T2 " +
							   "                  WHERE T1.RANGE = T2.CCDSUBCD " +
							   "                    AND T2.CCDCD = '013' " +
							   "   				 	AND T1.OPENTYPE = '1' \n");
			//부서 조건
			if (Utils.isNotNull(schBean.getSch_deptcd())) {
			selectQuery.append("                    AND T1.COLDEPTCD IN (SELECT T1.DEPT_ID \n" +
							   "                                          FROM DEPT T1 \n" +
							   "                                         START WITH T1.DEPT_ID = '"+schBean.getSch_deptcd()+"' \n" +
							   "                                       CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
			}
			//제목 조건
			if (Utils.isNotNull(schBean.getSch_title())) {
			selectQuery.append("                    AND T1.TITLE LIKE '%"+schBean.getSch_title()+"%' \n");
			}
			//년도 조건
			if (Utils.isNotNull(schBean.getSelyear())) {
			selectQuery.append("                    AND TO_CHAR(TO_DATE(T1.ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') = '"+schBean.getSelyear()+"' \n");
			}
			//모든조건이 없을경우
			selectQuery.append("				  ORDER BY T1.ENDDT DESC, T1.CRTDT DESC, T1.UPTDT DESC) A2) A1) X \n" +
							   "WHERE SEQ BETWEEN ? AND ? \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
									
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while (rs.next()) {
				ResearchBean Bean = new ResearchBean();
				
				Bean.setSeqno(rs.getInt("BUNHO"));
				Bean.setRchno(rs.getInt("RCHNO"));
				Bean.setTitle(rs.getString("TITLE"));		
				Bean.setStrdt(rs.getString("STRDT"));
				Bean.setEnddt(rs.getString("ENDDT"));
				Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				
				list.add(Bean);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return list;
	}
	
	/**
	 * 기존설문조사그룹 가져오기
	 * @param CommCollDocSearchBean searchBean
	 * @param int start
	 * @param int end
	 * @return List 취합문서목록(ArrayList)
	 * @throws Exception 
	 */
	public List getUsedRchGrpList(ResearchBean schBean, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT (CNT-SEQ+1) BUNHO, X.* " +
							   "FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* " +
							   "      FROM (SELECT ROWNUM SEQ, A2.*  " +
							   "            FROM (SELECT T1.RCHGRPNO, T1.TITLE, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, RANGE " +
							   "                  FROM RCHGRPMST  T1, CCD T2 " +
							   "                  WHERE T1.RANGE = T2.CCDSUBCD " +
							   "                    AND T2.CCDCD = '013' " +
							   "   				 	AND T1.OPENTYPE = '1' \n");
			//부서 조건
			if (Utils.isNotNull(schBean.getSch_deptcd())) {
			selectQuery.append("                    AND T1.COLDEPTCD IN (SELECT T1.DEPT_ID \n" +
							   "                                          FROM DEPT T1 \n" +
							   "                                         START WITH T1.DEPT_ID = '"+schBean.getSch_deptcd()+"' \n" +
							   "                                       CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
			}
			//제목 조건
			if (Utils.isNotNull(schBean.getSch_title())) {
			selectQuery.append("                    AND T1.TITLE LIKE '%"+schBean.getSch_title()+"%' \n");
			}
			//년도 조건
			if (Utils.isNotNull(schBean.getSelyear())) {
			selectQuery.append("                    AND TO_CHAR(TO_DATE(T1.ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') = '"+schBean.getSelyear()+"' \n");
			}
			//모든조건이 없을경우
			selectQuery.append("				  ORDER BY T1.ENDDT DESC, T1.CRTDT DESC, T1.UPTDT DESC) A2) A1) X \n" +
							   "WHERE SEQ BETWEEN ? AND ? \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
									
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while (rs.next()) {
				ResearchBean Bean = new ResearchBean();
				
				Bean.setSeqno(rs.getInt("BUNHO"));
				Bean.setRchgrpno(rs.getInt("RCHGRPNO"));
				Bean.setTitle(rs.getString("TITLE"));		
				Bean.setStrdt(rs.getString("STRDT"));
				Bean.setEnddt(rs.getString("ENDDT"));
				Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				
				list.add(Bean);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return list;
	}
	
	/**
	 * 기존설문조사 가져오기 건수
	 * @param String deptcd
	 * @return int 취합문서개수
	 * @throws Exception 
	 */
	public int getUsedRchTotCnt(ResearchBean Bean) throws Exception {
		Connection con = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*)CNT \n" +
							   "FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n" +
							   "      FROM (SELECT ROWNUM SEQ, A2.*  \n" +
							   "            FROM (SELECT RCHNO, TITLE, STRDT, ENDDT, RANGE \n" +
							   "                    FROM RCHMST  \n" +
							   "                   WHERE OPENTYPE = '1' \n");
			//부서 조건
			if (Utils.isNotNull(Bean.getSch_deptcd())) {
			selectQuery.append("                     AND COLDEPTCD IN (SELECT T1.DEPT_ID \n" +
							   "                                           FROM DEPT T1 \n" +
							   "                                          START WITH T1.DEPT_ID = '"+Bean.getSch_deptcd()+"' \n" +
							   "                                        CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
			}
			//제목 조건
			if (Utils.isNotNull(Bean.getSch_title())) {
			selectQuery.append("                     AND TITLE LIKE '%"+Bean.getSch_title()+"%' \n");
			}
			//년도 조건
			if (Utils.isNotNull(Bean.getSelyear())) {
			selectQuery.append("                     AND TO_CHAR(TO_DATE(ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') = '"+Bean.getSelyear()+"' \n");
			}			
			selectQuery.append("				   ORDER BY CRTDT DESC) A2) A1) X \n" +
							   " ");
			con = ConnectionManager.getConnection();
			
			pstmt =	con.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(con,pstmt,rs);
	     }
		return totalCount;
	}
	
	/**
	 * 기존설문조사그룹 가져오기 건수
	 * @param String deptcd
	 * @return int 취합문서개수
	 * @throws Exception 
	 */
	public int getUsedRchGrpTotCnt(ResearchBean Bean) throws Exception {
		Connection con = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*)CNT \n" +
							   "FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n" +
							   "      FROM (SELECT ROWNUM SEQ, A2.*  \n" +
							   "            FROM (SELECT RCHGRPNO, TITLE, STRDT, ENDDT, RANGE \n" +
							   "                    FROM RCHGRPMST  \n" +
							   "                   WHERE OPENTYPE = '1' \n");
			//부서 조건
			if (Utils.isNotNull(Bean.getSch_deptcd())) {
			selectQuery.append("                     AND COLDEPTCD IN (SELECT T1.DEPT_ID \n" +
							   "                                           FROM DEPT T1 \n" +
							   "                                          START WITH T1.DEPT_ID = '"+Bean.getSch_deptcd()+"' \n" +
							   "                                        CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
			}
			//제목 조건
			if (Utils.isNotNull(Bean.getSch_title())) {
			selectQuery.append("                     AND TITLE LIKE '%"+Bean.getSch_title()+"%' \n");
			}
			//년도 조건
			if (Utils.isNotNull(Bean.getSelyear())) {
			selectQuery.append("                     AND TO_CHAR(TO_DATE(ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') = '"+Bean.getSelyear()+"' \n");
			}			
			selectQuery.append("				   ORDER BY CRTDT DESC) A2) A1) X \n" +
							   " ");
			con = ConnectionManager.getConnection();
			
			pstmt =	con.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(con,pstmt,rs);
	     }
		return totalCount;
	}
	
	/**
	 * 설문참여목록
	 * @param CommCollDocSearchBean searchBean
	 * @param int start
	 * @param int end
	 * @return List 취합문서목록(ArrayList)
	 * @throws Exception 
	 */
	public List getRchParticiList(String usrid, String deptcd, String schtitle, String groupyn, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT (CNT-SEQ+1) BUNHO, X.* \n");
			selectQuery.append("FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n");
			selectQuery.append("      FROM (SELECT ROWNUM SEQ, A2.* \n");
			selectQuery.append("             FROM ( \n");
			if ( !"Y".equals(groupyn) ) {
			selectQuery.append("                   SELECT DISTINCT 'N' GBN, T1.RCHNO, T1.TITLE, COLDEPTNM, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, T1.CRTDT, T1.UPTDT \n");
			selectQuery.append("                     FROM RCHMST T1, RCHDEPT T2, RCHOTHERTARGET T3 \n");
			selectQuery.append("                    WHERE T1.RCHNO = T2.RCHNO(+) \n");
			selectQuery.append("                      AND T1.RCHNO = T3.RCHNO(+) \n");
			selectQuery.append("					  AND T1.OPENTYPE = '1' \n");
			selectQuery.append("					  AND T1.RANGE = '1' \n");
			selectQuery.append("					  AND T1.GROUPYN LIKE 'N' \n");
			selectQuery.append("					  AND T3.TGTGBN(+) = '1' \n");
			selectQuery.append("                      AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN T1.STRDT AND T1.ENDDT \n");
			selectQuery.append("                      AND T1.TITLE LIKE '%"+Utils.nullToEmptyString(schtitle)+"%' \n");
			selectQuery.append("                      AND ((CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+deptcd+"' THEN 1 ELSE 0 END = 1 \n");
			selectQuery.append("                           OR CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+usrid+"' THEN 1 ELSE 0 END = 1) \n");
			selectQuery.append("                          OR CASE NVL(T3.TGTCODE, '0') WHEN '0' THEN 1 WHEN (SELECT GRADE_ID FROM USR WHERE USER_ID = '"+usrid+"') THEN 1 ELSE 0 END = 1) \n");
			selectQuery.append("                      AND 0 < (SELECT DECODE(LIMITCOUNT, 0, COUNT(DISTINCT ANSUSRSEQ) + 1, LIMITCOUNT) - COUNT(DISTINCT ANSUSRSEQ) ");
			selectQuery.append("                               FROM RCHMST RM, RCHANS RA ");
			selectQuery.append("                               WHERE RM.RCHNO = RA.RCHNO(+) ");
			selectQuery.append("                               AND RM.RCHNO = T1.RCHNO ");
			selectQuery.append("                               GROUP BY LIMITCOUNT) ");
			selectQuery.append("                      AND 0 = CASE DUPLICHECK WHEN '2' THEN (SELECT COUNT(*) FROM RCHANS \n");
			selectQuery.append("                                                             WHERE T1.RCHNO = RCHNO \n");
			selectQuery.append("                                                             AND CRTUSRID = '"+usrid+"') ELSE 0 END \n");
			}
			
			if ( !"Y".equals(groupyn) && !"N".equals(groupyn) ) {
			selectQuery.append("                   UNION ALL \n");
			}
			
			if ( !"N".equals(groupyn) ) {
			selectQuery.append("                   SELECT DISTINCT 'Y' GBN, T1.RCHGRPNO RCHNO, T1.TITLE, COLDEPTNM, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, T1.CRTDT, T1.UPTDT \n");
			selectQuery.append("                     FROM RCHGRPMST T1, RCHGRPDEPT T2, RCHGRPOTHERTARGET T3 \n");
			selectQuery.append("                    WHERE T1.RCHGRPNO = T2.RCHGRPNO(+) \n");
			selectQuery.append("                      AND T1.RCHGRPNO = T3.RCHGRPNO(+) \n");
			selectQuery.append("					  AND T1.OPENTYPE = '1' \n");
			selectQuery.append("					  AND T1.RANGE = '1' \n");
			selectQuery.append("					  AND T1.GROUPYN LIKE '%' \n");
			selectQuery.append("					  AND T3.TGTGBN(+) = '1' \n");
			selectQuery.append("                      AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN T1.STRDT AND T1.ENDDT \n");
			selectQuery.append("                      AND T1.TITLE LIKE '%"+Utils.nullToEmptyString(schtitle)+"%' \n");
			selectQuery.append("                      AND ((CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+deptcd+"' THEN 1 ELSE 0 END = 1 \n");
			selectQuery.append("                           OR CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+usrid+"' THEN 1 ELSE 0 END = 1) \n");
			selectQuery.append("                          OR CASE NVL(T3.TGTCODE, '0') WHEN '0' THEN 1 WHEN (SELECT GRADE_ID FROM USR WHERE USER_ID = '"+usrid+"') THEN 1 ELSE 0 END = 1) \n");
			selectQuery.append("                      AND TRIM(RCHNOLIST) IS NOT NULL \n");
			}
			
			selectQuery.append("				    ORDER BY ENDDT DESC, CRTDT DESC, UPTDT DESC) A2) A1) X \n");
			selectQuery.append("WHERE SEQ BETWEEN ? AND ? \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
									
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while (rs.next()) {
				ResearchBean Bean = new ResearchBean();
				
				Bean.setSeqno(rs.getInt("BUNHO"));
				Bean.setGroupyn(rs.getString("GBN"));
				if ( "Y".equals(Bean.getGroupyn()) ) {
					Bean.setRchgrpno(rs.getInt("RCHNO"));
				} else {
					Bean.setRchno(rs.getInt("RCHNO"));
				}
				Bean.setTitle(rs.getString("TITLE"));
				Bean.setColdeptnm(rs.getString("COLDEPTNM"));
				Bean.setStrdt(rs.getString("STRDT"));
				Bean.setEnddt(rs.getString("ENDDT"));
				
				list.add(Bean);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return list;
	}
	
	/**
	 * 설문참여목록 갯수 가져오기
	 * @param String deptcd
	 * @return int 취합문서개수
	 * @throws Exception 
	 */
	public int getRchParticiTotCnt(String usrid, String deptcd, String schtitle, String groupyn) throws Exception {
		Connection con = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*)CNT \n");
			selectQuery.append("FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* ");
			selectQuery.append("      FROM (SELECT ROWNUM SEQ, A2.*  ");
			selectQuery.append("             FROM ( \n");
			if ( !"Y".equals(groupyn) ) {
			selectQuery.append("                   SELECT DISTINCT 'N' GBN, T1.RCHNO, T1.TITLE, COLDEPTNM, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, T1.CRTDT, T1.UPTDT \n");
			selectQuery.append("                     FROM RCHMST T1, RCHDEPT T2, RCHOTHERTARGET T3 \n");
			selectQuery.append("                    WHERE T1.RCHNO = T2.RCHNO(+) \n");
			selectQuery.append("                      AND T1.RCHNO = T3.RCHNO(+) \n");
			selectQuery.append("					  AND T1.OPENTYPE = '1' \n");
			selectQuery.append("					  AND T1.RANGE = '1' \n");
			selectQuery.append("					  AND T1.GROUPYN LIKE 'N' \n");
			selectQuery.append("					  AND T3.TGTGBN(+) = '1' \n");
			selectQuery.append("                      AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN T1.STRDT AND T1.ENDDT \n");
			selectQuery.append("                      AND T1.TITLE LIKE '%"+Utils.nullToEmptyString(schtitle)+"%' \n");
			selectQuery.append("                      AND ((CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+deptcd+"' THEN 1 ELSE 0 END = 1 \n");
			selectQuery.append("                           OR CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+usrid+"' THEN 1 ELSE 0 END = 1) \n");
			selectQuery.append("                          OR CASE NVL(T3.TGTCODE, '0') WHEN '0' THEN 1 WHEN (SELECT GRADE_ID FROM USR WHERE USER_ID = '"+usrid+"') THEN 1 ELSE 0 END = 1) \n");
			selectQuery.append("                      AND 0 < (SELECT DECODE(LIMITCOUNT, 0, COUNT(DISTINCT ANSUSRSEQ) + 1, LIMITCOUNT) - COUNT(DISTINCT ANSUSRSEQ) ");
			selectQuery.append("                               FROM RCHMST RM, RCHANS RA ");
			selectQuery.append("                               WHERE RM.RCHNO = RA.RCHNO(+) ");
			selectQuery.append("                               AND RM.RCHNO = T1.RCHNO ");
			selectQuery.append("                               GROUP BY LIMITCOUNT) ");
			selectQuery.append("                      AND 0 = CASE DUPLICHECK WHEN '2' THEN (SELECT COUNT(*) FROM RCHANS \n");
			selectQuery.append("                                                             WHERE T1.RCHNO = RCHNO \n");
			selectQuery.append("                                                             AND CRTUSRID = '"+usrid+"') ELSE 0 END \n");
			}
			
			if ( !"Y".equals(groupyn) && !"N".equals(groupyn) ) {
			selectQuery.append("                   UNION ALL \n");
			}
			
			if ( !"N".equals(groupyn) ) {
			selectQuery.append("                   SELECT DISTINCT 'Y' GBN, T1.RCHGRPNO RCHNO, T1.TITLE, COLDEPTNM, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, T1.CRTDT, T1.UPTDT \n");
			selectQuery.append("                     FROM RCHGRPMST T1, RCHGRPDEPT T2, RCHGRPOTHERTARGET T3 \n");
			selectQuery.append("                    WHERE T1.RCHGRPNO = T2.RCHGRPNO(+) \n");
			selectQuery.append("                      AND T1.RCHGRPNO = T3.RCHGRPNO(+) \n");
			selectQuery.append("					  AND T1.OPENTYPE = '1' \n");
			selectQuery.append("					  AND T1.RANGE = '1' \n");
			selectQuery.append("					  AND T1.GROUPYN LIKE '%' \n");
			selectQuery.append("					  AND T3.TGTGBN(+) = '1' \n");
			selectQuery.append("                      AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN T1.STRDT AND T1.ENDDT \n");
			selectQuery.append("                      AND T1.TITLE LIKE '%"+Utils.nullToEmptyString(schtitle)+"%' \n");
			selectQuery.append("                      AND ((CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+deptcd+"' THEN 1 ELSE 0 END = 1 \n");
			selectQuery.append("                           OR CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+usrid+"' THEN 1 ELSE 0 END = 1) \n");
			selectQuery.append("                          OR CASE NVL(T3.TGTCODE, '0') WHEN '0' THEN 1 WHEN (SELECT GRADE_ID FROM USR WHERE USER_ID = '"+usrid+"') THEN 1 ELSE 0 END = 1) \n");
			selectQuery.append("                      AND TRIM(RCHNOLIST) IS NOT NULL \n");
			}
			
			selectQuery.append("				    ORDER BY ENDDT DESC, CRTDT DESC, UPTDT DESC) A2) A1) X \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt =	con.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(con,pstmt,rs);
	     }
		return totalCount;
	}	
	
	/**
	 * 
	 * @param conn
	 * @param subList
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchAnsSave(List ansList, int rchno, String usrid, String usrnm) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int[] ret = null; 
        int result = 0;
        int maxseq = 0;

		StringBuffer selectQuery1 = new StringBuffer();
		StringBuffer selectQuery2 = new StringBuffer();
		StringBuffer deleteQuery = new StringBuffer();
		StringBuffer updateQuery = new StringBuffer();
		String insertQuery = "";
		
		selectQuery1.append("\n SELECT DISTINCT ANSUSRSEQ FROM RCHANS WHERE RCHNO = ? AND CRTUSRID = ?");
		selectQuery2.append("\n SELECT NVL(MAX(ANSUSRSEQ),0) FROM RCHANS WHERE RCHNO = ? ");
		
		deleteQuery.append("\n DELETE FROM RCHANS WHERE RCHNO = ? AND CRTUSRID = ?");
		
		insertQuery = "INSERT INTO RCHANS " +
					  "(RCHNO,	ANSUSRSEQ,	FORMSEQ,	ANSCONT,	OTHER, " +
					  " CRTDT,	CRTUSRID,	CRTUSRNM) " +
			          "VALUES(?, ?, ?, ?, ?, " +
			          "       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '"+usrid+"', '"+ usrnm +"') ";		
		
		updateQuery.append("\n UPDATE RCHMST SET ANSCOUNT = (SELECT COUNT(DISTINCT ANSUSRSEQ) FROM RCHANS WHERE RCHNO = ?) WHERE RCHNO = ? "); 
		try {
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//답변기록이 있는지 확인
			pstmt = con.prepareStatement(selectQuery1.toString());
			pstmt.setInt(1, rchno);
			pstmt.setString(2, usrid);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() && rs.getInt(1) > 1 ) { //중복답변 가능하도록 처리(&& rs.getInt(1) > 1 제거시 중복답변 안됨)
				maxseq = rs.getInt(1);
				
				ConnectionManager.close(pstmt, rs);
				pstmt = con.prepareStatement(deleteQuery.toString());
				
				pstmt.setInt(1, rchno);
				pstmt.setString(2, usrid);
		
				pstmt.executeUpdate();
			} else {
				ConnectionManager.close(pstmt, rs);
				
				pstmt = con.prepareStatement(selectQuery2.toString());
				pstmt.setInt(1, rchno);
		
				rs = pstmt.executeQuery();
				if ( rs.next() ){
					maxseq = rs.getInt(1) + 1;
				}
			}
			
			ConnectionManager.close(pstmt, rs);
			
			ResearchAnsBean ansBean = null;
			
			pstmt = con.prepareStatement(insertQuery);
			for(int j=0; j<ansList.size();j++){
				ansBean = (ResearchAnsBean)ansList.get(j);
	
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, maxseq);
				pstmt.setInt(3, ansBean.getFormseq());
				pstmt.setString(4, ansBean.getAnscont());
				pstmt.setString(5, ansBean.getOther());
				
				pstmt.addBatch();

			}
			ret = pstmt.executeBatch();
			result = result + ret.length;	
			
			ConnectionManager.close(pstmt);
			
			pstmt = con.prepareStatement(updateQuery.toString());
			
			pstmt.setInt(1, rchno);
			pstmt.setInt(2, rchno);
			
			result += pstmt.executeUpdate();	
			ConnectionManager.close(pstmt);
			
			con.commit();
		}catch(SQLException e){
			con.rollback();
			logger.error("ERROR",e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
	    }finally {	     
	    	con.setAutoCommit(true);
			ConnectionManager.close(con,pstmt,rs);
	    }
		     
		return result;
	}	
	
	/**
	 * 설문조사 기존설문가져오기
	 * @param sessi
	 * @return
	 * @throws Exception
	 */
	public int rchChoice(int rchno, String sessionId, String usrid, String usrnm, String deptcd, String deptnm, String coltel, ServletContext context, String saveDir) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;	
		int result = 0;
		
		try{				
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);	
			
			//양식마스터 복사
			StringBuffer insertQuery2 = new StringBuffer();
			insertQuery2.append("\n INSERT INTO RCHMST_TEMP ");
			insertQuery2.append("\n SELECT '" + sessionId + "', TITLE, STRDT, ENDDT, '"+ deptcd +"', ");
			insertQuery2.append("\n        '" + deptnm + "', '" + coltel + "', '" + usrid + "', '" + usrnm + "', SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, DUPLICHECK, LIMITCOUNT, GROUPYN, TGTDEPTNM, ");
			insertQuery2.append("\n        HEADCONT, TAILCONT, 0, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" +usrid + "', '', '' ");
			insertQuery2.append("\n   FROM RCHMST ");
			insertQuery2.append("\n  WHERE RCHNO = ? "); 
					System.out.println("log :"+insertQuery2.toString());
			pstmt = con.prepareStatement(insertQuery2.toString());
			pstmt.setInt(1, rchno);
			
			result = pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}	
			
			//양식 문항 복사
			StringBuffer selectQuery3 = new StringBuffer();
			selectQuery3.append("\n INSERT INTO RCHSUB_TEMP ");
			selectQuery3.append("\n SELECT '" + sessionId + "', FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE ");
			selectQuery3.append("\n   FROM RCHSUB ");
			selectQuery3.append("\n  WHERE RCHNO = ? "); 
			
			pstmt = con.prepareStatement(selectQuery3.toString());
			pstmt.setInt(1, rchno);
			
			result = result + pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}	
			
			//양식 보기 복사
			StringBuffer selectQuery4 = new StringBuffer();
			selectQuery4.append("\n INSERT INTO RCHEXAM_TEMP ");
			selectQuery4.append("\n SELECT '" + sessionId + "', FORMSEQ, EXAMSEQ, EXAMCONT ");
			selectQuery4.append("\n   FROM RCHEXAM ");
			selectQuery4.append("\n  WHERE RCHNO = ? "); 
			pstmt = con.prepareStatement(selectQuery4.toString());
			pstmt.setInt(1, rchno);
			
			result = result + pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}	
			
			//대상부서지정 복사
			StringBuffer selectQuery5 = new StringBuffer();
			selectQuery5.append("\n INSERT INTO RCHDEPT_TEMP ");
			selectQuery5.append("\n SELECT '" + sessionId + "', TGTCODE, TGTNAME, TGTGBN ");
			selectQuery5.append("\n   FROM RCHDEPT ");
			selectQuery5.append("\n  WHERE RCHNO = ? "); 
			pstmt = con.prepareStatement(selectQuery5.toString());
			pstmt.setInt(1, rchno);
			
			result = result + pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}	
			
			//대상부서리스트지정 복사
			StringBuffer selectQuery6 = new StringBuffer();
			selectQuery6.append("\n INSERT INTO RCHDEPTLIST_TEMP ");
			selectQuery6.append("\n SELECT '" + sessionId + "', SEQ, GRPCD, GRPNM, GRPGBN ");
			selectQuery6.append("\n   FROM RCHDEPTLIST ");
			selectQuery6.append("\n  WHERE RCHNO = ? "); 
			pstmt = con.prepareStatement(selectQuery6.toString());
			pstmt.setInt(1, rchno);
			
			result = result + pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			//기타대상 복사
			StringBuffer selectQuery7 = new StringBuffer();
			selectQuery7.append("\n INSERT INTO RCHOTHERTARGET_TEMP ");
			selectQuery7.append("\n SELECT '" + sessionId + "', TGTCODE, TGTNAME, TGTGBN ");
			selectQuery7.append("\n   FROM RCHOTHERTARGET ");
			selectQuery7.append("\n  WHERE RCHNO = ? "); 
			pstmt = con.prepareStatement(selectQuery7.toString());
			pstmt.setInt(1, rchno);
			
			result = result + pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			//첨부파일 복사
			result += copyRchSubExamFile(con, sessionId, rchno, context, saveDir, "VIEW");
			
			con.commit();
		} catch (Exception e) {
			con.rollback();
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt);
			throw e;
	     } finally {	
	    	 con.setAutoCommit(true);
	    	 ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
	
	/**
	 * 설문조사그룹 기존설문가져오기
	 * @param sessi
	 * @return
	 * @throws Exception
	 */
	public int rchGrpChoice(int rchgrpno, String sessionId, String usrid, String usrnm, String deptcd, String deptnm, String coltel) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;	
		int result = 0;
		
		try{				
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);	
			
			//양식마스터 복사
			StringBuffer insertQuery2 = new StringBuffer();
			insertQuery2.append("\n INSERT INTO RCHGRPMST_TEMP ");
			insertQuery2.append("\n SELECT '" + sessionId + "', TITLE, STRDT, ENDDT, '"+ deptcd +"', ");
			insertQuery2.append("\n        '" + deptnm + "', '" + coltel + "', '" + usrid + "', '" + usrnm + "', SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, DUPLICHECK, LIMITCOUNT, GROUPYN, RCHNOLIST, TGTDEPTNM, ");
			insertQuery2.append("\n        HEADCONT, TAILCONT, 0, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" +usrid + "', '', '' ");
			insertQuery2.append("\n   FROM RCHGRPMST ");
			insertQuery2.append("\n  WHERE RCHGRPNO = ? "); 

			pstmt = con.prepareStatement(insertQuery2.toString());
			pstmt.setInt(1, rchgrpno);
			
			result = pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}	
			
			//대상부서지정 복사
			StringBuffer selectQuery5 = new StringBuffer();
			selectQuery5.append("\n INSERT INTO RCHGRPDEPT_TEMP ");
			selectQuery5.append("\n SELECT '" + sessionId + "', TGTCODE, TGTNAME, TGTGBN ");
			selectQuery5.append("\n   FROM RCHGRPDEPT ");
			selectQuery5.append("\n  WHERE RCHGRPNO = ? "); 
			pstmt = con.prepareStatement(selectQuery5.toString());
			pstmt.setInt(1, rchgrpno);
			
			result = result + pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}	
			
			//대상부서리스트지정 복사
			StringBuffer selectQuery6 = new StringBuffer();
			selectQuery6.append("\n INSERT INTO RCHGRPDEPTLIST_TEMP ");
			selectQuery6.append("\n SELECT '" + sessionId + "', SEQ, GRPCD, GRPNM, GRPGBN ");
			selectQuery6.append("\n   FROM RCHGRPDEPTLIST ");
			selectQuery6.append("\n  WHERE RCHGRPNO = ? "); 
			pstmt = con.prepareStatement(selectQuery6.toString());
			pstmt.setInt(1, rchgrpno);
			
			result = result + pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			//기타대상 복사
			StringBuffer selectQuery7 = new StringBuffer();
			selectQuery7.append("\n INSERT INTO RCHGRPOTHERTARGET_TEMP ");
			selectQuery7.append("\n SELECT '" + sessionId + "', TGTCODE, TGTNAME, TGTGBN ");
			selectQuery7.append("\n   FROM RCHGRPOTHERTARGET ");
			selectQuery7.append("\n  WHERE RCHGRPNO = ? "); 
			pstmt = con.prepareStatement(selectQuery7.toString());
			pstmt.setInt(1, rchgrpno);
			
			result = result + pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			con.commit();
		} catch (Exception e) {
			con.rollback();
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt);
			throw e;
	     } finally {	
	    	 con.setAutoCommit(true);
	    	 ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
	
	/**
	 * 설문조사 첨부파일 복사
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param context
	 * @param saveDir
	 * @param mode	( VIEW : 설문보기, SAVE : 설문저장 ) 
	 * @return
	 * @throws Exception
	 */
	public int copyRchSubExamFile(Connection conn, String sessionId, int rchno, ServletContext context, String saveDir, String mode) throws Exception {
		int result = 0;
		
		List rchSubFile = null;
		if ( mode.equals("VIEW") ) {
			rchSubFile = getRchSubFile(conn, "", rchno);
		} else if ( mode.equals("SAVE") ) {
			rchSubFile = getRchSubFile(conn, sessionId, 0);
		}
		for ( int i = 0; rchSubFile != null && i < rchSubFile.size(); i++ ) {
			ResearchSubBean rchSubBean = (ResearchSubBean)rchSubFile.get(i);
			
			String newFilenm = "";
			try {
				newFilenm = FileManager.doFileCopy(context.getRealPath(rchSubBean.getFilenm()));
			} catch (FileNotFoundException e) {
				continue;
			}
			
			if( newFilenm.equals("") == false ) {
				FileBean subFileBean = new FileBean();
				subFileBean.setFileseq(rchSubBean.getFileseq());
				subFileBean.setFilenm(saveDir + newFilenm);
				subFileBean.setOriginfilenm(rchSubBean.getOriginfilenm());
				subFileBean.setFilesize(rchSubBean.getFilesize());
				subFileBean.setExt(rchSubBean.getExt());
				
				int addResult = 0;
				if ( mode.equals("VIEW") ) {
					addResult = addRchSubFile(conn, sessionId, 0, rchSubBean.getFormseq(), subFileBean);
				} else if ( mode.equals("SAVE") ) {
					addResult = addRchSubFile(conn, "", rchno, rchSubBean.getFormseq(), subFileBean);
				}
				if ( addResult == 0 ) {
					throw new Exception("첨부실패(저장):DB업데이트");
				}
				result += addResult;
			} else {
				throw new Exception("첨부실패(저장):파일업로드");
			}
		}
		 
		List rchExamFile = null;
		if ( mode.equals("VIEW") ) {
			rchExamFile = getRchExamFile(conn, "", rchno, 0);
		} else if ( mode.equals("SAVE") ) {
			rchExamFile = getRchExamFile(conn, sessionId, 0, 0);
		}
		for ( int i = 0; rchExamFile != null && i < rchExamFile.size(); i++ ) {
			ResearchExamBean rchExamBean = (ResearchExamBean)rchExamFile.get(i);
			
			String newFilenm = "";
			try {
				newFilenm = FileManager.doFileCopy(context.getRealPath(rchExamBean.getFilenm()));
			} catch (FileNotFoundException e) {
				continue;
			}
			
			if( newFilenm.equals("") == false ) {
				FileBean subFileBean = new FileBean();
				subFileBean.setFileseq(rchExamBean.getFileseq());
				subFileBean.setFilenm(saveDir + newFilenm);
				subFileBean.setOriginfilenm(rchExamBean.getOriginfilenm());
				subFileBean.setFilesize(rchExamBean.getFilesize());
				subFileBean.setExt(rchExamBean.getExt());
				
				int addResult = 0;
				if ( mode.equals("VIEW") ) {
					addResult = addRchExamFile(conn, sessionId, 0, rchExamBean.getFormseq(), rchExamBean.getExamseq(), subFileBean);
				} else if ( mode.equals("SAVE") ) {
					addResult = addRchExamFile(conn, "", rchno, rchExamBean.getFormseq(), rchExamBean.getExamseq(), subFileBean);
				}
				if ( addResult == 0 ) {
					throw new Exception("첨부실패(저장):DB업데이트");
				}
				result += addResult;
			} else {
				throw new Exception("첨부실패(저장):파일업로드");
			}
		}
		
		return result;
	}
	
	/**
	 * 설문조사 임시테이블 삭제
	 * @param sessionid
	 * @throws Exception
	 */
	public void delResearchTempData(Connection conn, String sessionid) throws Exception {

		PreparedStatement pstmt = null;
		
		try {			
			String[] table = {"RCHMST_TEMP", "RCHSUB_TEMP", "RCHEXAM_TEMP",
							  "RCHDEPT_TEMP", "RCHDEPTLIST_TEMP", "RCHOTHERTARGET_TEMP"};
			
			for(int i = 0; i < table.length; i++) {
				
				String sql = 
					"DELETE " +
					"FROM " + table[i] + " " +
					"WHERE SESSIONID LIKE ?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, sessionid);
				pstmt.executeUpdate();
				ConnectionManager.close(pstmt);
			
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
	}
	
	/**
	 * 설문조사그룹 임시테이블 삭제
	 * @param sessionid
	 * @throws Exception
	 */
	public void delResearchGrpTempData(Connection conn, String sessionid) throws Exception {

		PreparedStatement pstmt = null;
		
		try {			
			String[] table = {"RCHGRPMST_TEMP", "RCHGRPDEPT_TEMP", "RCHGRPDEPTLIST_TEMP", "RCHGRPOTHERTARGET_TEMP"};
			
			for(int i = 0; i < table.length; i++) {
				
				String sql = 
					"DELETE " +
					"FROM " + table[i] + " " +
					"WHERE SESSIONID LIKE ?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, sessionid);
				pstmt.executeUpdate();
				ConnectionManager.close(pstmt);
			
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
	}
	
	/**
	 * 설문조사 삭제
	 * @param sessionid
	 * @throws Exception
	 */
	public int ResearchDlete(Connection conn, int rchno) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result= 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			String[] table = {"RCHMST", "RCHSUB", "RCHEXAM", "RCHANS",
							  "RCHDEPT", "RCHDEPTLIST", "RCHOTHERTARGET"};
			
			for(int i = 0; i < table.length; i++) {
				
				String sql = 
					"DELETE " +
					"FROM " + table[i] + " " +
					"WHERE RCHNO LIKE ?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, rchno);
				result += pstmt.executeUpdate();
				ConnectionManager.close(pstmt);

			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 설문조사그룹 삭제
	 * @param sessionid
	 * @throws Exception
	 */
	public int ResearchGrpDlete(Connection conn, int rchgrpno) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result= 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			String[] table = {"RCHGRPMST", "RCHGRPDEPT", "RCHGRPDEPTLIST", "RCHGRPOTHERTARGET"};
			
			for(int i = 0; i < table.length; i++) {
				
				String sql = 
					"DELETE " +
					"FROM " + table[i] + " " +
					"WHERE RCHGRPNO LIKE ?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, rchgrpno);
				result += pstmt.executeUpdate();
				ConnectionManager.close(pstmt);

			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 설문 마감 	
	 */
	public int rchClose(int rchno, String userid) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result = 0;
				
		try{
			//설문을 마감할 경우 하루전 날짜로 셋팅한다.
			StringBuffer updateQuery = new StringBuffer();
			
			updateQuery.append("UPDATE RCHMST SET ENDDT = TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD') ");
			updateQuery.append("WHERE RCHNO = ? ");
			updateQuery.append("  AND CHRGUSRID = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
			
			pstmt.setInt(1, rchno);
			pstmt.setString(2, userid);
				
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {		
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
		} finally {	       
	    	 ConnectionManager.close(con,pstmt);   
		}
		return result;
	}
	
	/**
	 * 설문그룹 마감 	
	 */
	public int rchGrpClose(int rchgrpno, String userid) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result = 0;
				
		try{
			//설문을 마감할 경우 하루전 날짜로 셋팅한다.
			StringBuffer updateQuery = new StringBuffer();
			
			updateQuery.append("UPDATE RCHGRPMST SET ENDDT = TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD') ");
			updateQuery.append("WHERE RCHGRPNO = ? ");
			updateQuery.append("  AND CHRGUSRID = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
			
			pstmt.setInt(1, rchgrpno);
			pstmt.setString(2, userid);
				
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {		
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);   
			 throw e;
		} finally {	       
	    	 ConnectionManager.close(con,pstmt);   
		}
		return result;
	}

	/**
	 * 설문 기간상태 가져오기
	 * @param rchno
	 * @param deptcd
	 * @return
	 * @throws Exception
	 */
	public int rchState(int rchno, String deptcd, String userid, int grpDuplicheck, int grpLimitcount) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
				
		try{
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(T1.RCHNO) \n");
			selectQuery.append("FROM RCHMST T1, RCHDEPT T2, RCHOTHERTARGET T3 \n");
			selectQuery.append("WHERE T1.RCHNO = T2.RCHNO(+) \n");
			selectQuery.append("AND T1.RCHNO = T3.RCHNO(+) \n");
			selectQuery.append("AND T1.RCHNO = ? \n");
			if ( grpDuplicheck == -1 && grpLimitcount == -1 ) {
				selectQuery.append("AND TO_CHAR(SYSDATE,'YYYY-MM-DD') BETWEEN STRDT AND ENDDT \n");
				selectQuery.append("AND ((CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+deptcd+"' THEN 1 ELSE 0 END = 1 \n");
				selectQuery.append("     OR CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+userid+"' THEN 1 ELSE 0 END = 1) \n");
				selectQuery.append("    OR CASE NVL(T3.TGTCODE, '0') WHEN '0' THEN 1 WHEN (SELECT GRADE_ID FROM USR WHERE USER_ID = '"+userid+"') THEN 1 ELSE 0 END = 1) \n");
			}
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, rchno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				result = rs.getInt(1);
			}
			
			if ( result > 0 ) {
				selectQuery.delete(0, selectQuery.capacity());
				if ( grpLimitcount == -1 ) {
					selectQuery.append("\n SELECT DECODE(LIMITCOUNT, 0, COUNT(DISTINCT ANSUSRSEQ) + 1, LIMITCOUNT) - COUNT(DISTINCT ANSUSRSEQ) ");
				} else {
					selectQuery.append("\n SELECT DECODE(" + grpLimitcount + ", 0, COUNT(DISTINCT ANSUSRSEQ) + 1, " + grpLimitcount + ") - COUNT(DISTINCT ANSUSRSEQ) ");
				}
				selectQuery.append("\n FROM RCHMST RM, RCHANS RA ");
				selectQuery.append("\n WHERE RM.RCHNO = RA.RCHNO(+)  ");
				selectQuery.append("\n AND RM.RCHNO = ? ");
				selectQuery.append("\n GROUP BY LIMITCOUNT ");
				
				ConnectionManager.close(pstmt, rs);
				pstmt = con.prepareStatement(selectQuery.toString());
				
				pstmt.setInt(1, rchno);
				
				rs = pstmt.executeQuery();
				
				if ( rs.next() ) {
					result = rs.getInt(1);
					if ( result < 0 ) result = 0;
				}
			}
			
			if ( result > 0 ) {
				selectQuery.delete(0, selectQuery.capacity());				
				selectQuery.append("\n SELECT COUNT(R1.RCHNO)");
				selectQuery.append("\n FROM RCHANS R1, RCHMST R2");
				selectQuery.append("\n WHERE R1.RCHNO = R2.RCHNO");
				if ( grpDuplicheck == -1 ) {
					selectQuery.append("\n AND R2.DUPLICHECK = '2'");
				} else {
					selectQuery.append("\n AND '" + grpDuplicheck + "' = '2'");
				}
				selectQuery.append("\n AND R1.RCHNO = ? AND R1.CRTUSRID = ?");
				
				ConnectionManager.close(pstmt, rs);
				pstmt = con.prepareStatement(selectQuery.toString());
				
				pstmt.setInt(1, rchno);
				pstmt.setString(2, userid);
				
				rs = pstmt.executeQuery();
				
				if ( rs.next() ){
					result = rs.getInt(1);
					
					if ( result > 0 ) {
						result = -1;
					} else {
						result = 1;
					}
				}
			}
			
		} catch (Exception e) {		
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt, rs);   
			 throw e;
		} finally {	       
	    	 ConnectionManager.close(con,pstmt, rs);   
		}
		return result;
	}
	
	/**
	 * 설문 기간상태 가져오기
	 * @param rchgrpno
	 * @param deptcd
	 * @return
	 * @throws Exception
	 */
	public int rchGrpState(int rchgrpno, String deptcd, String userid) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try{
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(T1.RCHGRPNO) \n");
			selectQuery.append("FROM RCHGRPMST T1, RCHGRPDEPT T2, RCHGRPOTHERTARGET T3 \n");
			selectQuery.append("WHERE T1.RCHGRPNO = T2.RCHGRPNO(+) \n");
			selectQuery.append("AND T1.RCHGRPNO = T3.RCHGRPNO(+) \n");
			selectQuery.append("AND TO_CHAR(SYSDATE,'YYYY-MM-DD') BETWEEN STRDT AND ENDDT \n");
			selectQuery.append("AND T1.RCHGRPNO = ? \n");
			selectQuery.append("AND ((CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+deptcd+"' THEN 1 ELSE 0 END = 1 \n");
			selectQuery.append("     OR CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+userid+"' THEN 1 ELSE 0 END = 1) \n");
			selectQuery.append("    OR CASE NVL(T3.TGTCODE, '0') WHEN '0' THEN 1 WHEN (SELECT GRADE_ID FROM USR WHERE USER_ID = '"+userid+"') THEN 1 ELSE 0 END = 1) \n");	
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, rchgrpno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {		
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt, rs);   
			throw e;
		} finally {	       
			ConnectionManager.close(con,pstmt, rs);   
		}
		return result;
	}
	
	/**
	 * 
	 * @param usrid
	 * @param deptcd
	 * @param schtitle
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getRchOutsideList() throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append(" SELECT T1.RCHNO  " +
							   "   FROM RCHMST T1 " +
							   "  WHERE T1.OPENTYPE = '1'" +
							   "	AND T1.RANGE LIKE '2%' \n");
			selectQuery.append("    AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN T1.STRDT AND T1.ENDDT \n");	
			selectQuery.append("  ORDER BY CRTDT DESC \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
									
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while (rs.next()) {
				ResearchAnsBean Bean = new ResearchAnsBean();
				Bean.setRchno(rs.getInt("RCHNO"));
				
				list.add(Bean);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt, rs);   
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}		
		return list;
	}
	
	/**
	 * 문항당 보기개수 가져오기
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int getRchSubExamcount(int rchno, String sessionId) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			if ( rchno == 0 ) {
				selectQuery.append("SELECT MAX(COUNT(EXAMSEQ)) \n");
				selectQuery.append("FROM RCHEXAM_TEMP \n");
				selectQuery.append("WHERE SESSIONID LIKE ? \n");
				selectQuery.append("GROUP BY FORMSEQ \n");
			} else {
				selectQuery.append("SELECT MAX(COUNT(EXAMSEQ)) \n");
				selectQuery.append("FROM RCHEXAM \n");
				selectQuery.append("WHERE RCHNO = ? \n");
				selectQuery.append("GROUP BY FORMSEQ \n");
			}
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if ( rchno == 0) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
									
			rs = pstmt.executeQuery();
						
			if ( rs.next() ) {
				result = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt, rs);   
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}		
		return result;
	}
	
	/**
	 * 설문조사 - 관리자인경우 검색 조건에 해당하는 값 가져오기
	 */
	public String getSearch(String groupyn, String sch_title, String sch_deptcd, String sch_userid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
			
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT NVL(SUM(COUNT(*)),0) AS CNT					\n");
			selectQuery.append(" FROM RCHMST								 	    \n");
			selectQuery.append(" WHERE COLDEPTCD LIKE  '%"+sch_deptcd+"%' 			\n");
			selectQuery.append(" AND CHRGUSRID LIKE    '%"+sch_userid+"%'			\n");
			selectQuery.append(" AND TITLE LIKE '%"+sch_title+"%'					\n");
			selectQuery.append(" AND GROUPYN LIKE '%"+groupyn+"'						\n");
			selectQuery.append(" GROUP BY COLDEPTNM, CHRGUSRNM, TITLE				\n");

			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("CNT") == 1){
					ConnectionManager.close(pstmt, rs);
					
					if(!"".equals(sch_deptcd)&& sch_deptcd != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT COLDEPTCD						 \n");
						selectQuery.append(" FROM RCHMST							 \n");
						selectQuery.append(" WHERE COLDEPTCD LIKE '%"+sch_deptcd+"%' \n");
						selectQuery.append(" AND TITLE LIKE '%"+sch_title+"%' 		 \n");
						selectQuery.append(" AND GROUPYN LIKE '%"+groupyn+"' 		 \n");

						pstmt = con.prepareStatement(selectQuery.toString());
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							result = rs.getString("COLDEPTCD");
						}
						ConnectionManager.close(pstmt, rs);
						
					}
					
					if(!"".equals(sch_userid)&& sch_userid != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT CHRGUSRID						 	\n");
						selectQuery.append(" FROM RCHMST								\n");
						selectQuery.append(" WHERE CHRGUSRID LIKE '%"+sch_userid+"%' 	\n");
						selectQuery.append(" AND TITLE LIKE '%"+sch_title+"%' 		 	\n");
						selectQuery.append(" AND GROUPYN LIKE '%"+groupyn+"'		 	\n");

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
			ConnectionManager.close(con,pstmt, rs);   
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
	
	/**
	 * 설문조사그룹 - 관리자인경우 검색 조건에 해당하는 값 가져오기
	 */
	public String getGrpSearch(String groupyn, String sch_title, String sch_deptcd, String sch_userid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
			
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT NVL(SUM(COUNT(*)),0) AS CNT					\n");
			selectQuery.append(" FROM RCHGRPMST								 	    \n");
			selectQuery.append(" WHERE COLDEPTCD LIKE  '%"+sch_deptcd+"%' 			\n");
			selectQuery.append(" AND CHRGUSRID LIKE    '%"+sch_userid+"%'			\n");
			selectQuery.append(" AND TITLE LIKE '%"+sch_title+"%'					\n");
			selectQuery.append(" AND GROUPYN LIKE '%"+groupyn+"'						\n");
			selectQuery.append(" GROUP BY COLDEPTNM, CHRGUSRNM, TITLE				\n");

			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("CNT") == 1){
					ConnectionManager.close(pstmt, rs);
					
					if(!"".equals(sch_deptcd)&& sch_deptcd != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT COLDEPTCD						 \n");
						selectQuery.append(" FROM RCHGRPMST							 \n");
						selectQuery.append(" WHERE COLDEPTCD LIKE '%"+sch_deptcd+"%' \n");
						selectQuery.append(" AND TITLE LIKE '%"+sch_title+"%' 		 \n");
						selectQuery.append(" AND GROUPYN LIKE '%"+groupyn+"' 		 \n");

						pstmt = con.prepareStatement(selectQuery.toString());
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							result = rs.getString("COLDEPTCD");
						}
						ConnectionManager.close(pstmt, rs);
						
					}
					
					if(!"".equals(sch_userid)&& sch_userid != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT CHRGUSRID						 	\n");
						selectQuery.append(" FROM RCHGRPMST								\n");
						selectQuery.append(" WHERE CHRGUSRID LIKE '%"+sch_userid+"%' 	\n");
						selectQuery.append(" AND TITLE LIKE '%"+sch_title+"%' 		 	\n");
						selectQuery.append(" AND GROUPYN LIKE '%"+groupyn+"'		 	\n");

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
			ConnectionManager.close(con,pstmt, rs);   
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
	
	/**
	 * 설문공개여부 확인
	 * return : 0 ==> 비공개,  1==> 공개
	 */
	public int checkExhibit(int rchno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) \n");
			sql.append("FROM RCHMST \n");
			sql.append("WHERE RCHNO = ? \n");			
			sql.append("  AND EXHIBIT = '1' \n");        //공개설문
			sql.append("  AND RANGE LIKE '2%' \n");      //인터넷설문			
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setInt(1, rchno);			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(con,pstmt, rs);   
			throw e;
		} finally {	       
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
}