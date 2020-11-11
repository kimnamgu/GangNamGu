/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 dao
 * 설명:
 */
package nexti.ejms.sinchung.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import nexti.ejms.commapproval.model.commapprovalBean;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.sinchung.form.SinchungForm;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.FileManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

public class SinchungDAO {
	
	private static Logger logger = Logger.getLogger(SinchungDAO.class);
	
	/**
	 * 질문순서 변경
	 * @param reqformno
	 * @param sessionId
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public int changeFormseq(int reqformno, String sessionId, int[] formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {			
			con = ConnectionManager.getConnection(false);
			
			String[][] tableName = {{"REQFORMSUB", "REQFORMSUBFILE", "REQFORMEXAM", "REQFORMEXAMFILE"},
									{"REQFORMSUB_TEMP", "REQFORMSUBFILE_TEMP", "REQFORMEXAM_TEMP", "REQFORMEXAMFILE_TEMP"}};
			
			//문항번호에 +50 한 후 변경할 번호로 업데이트 : 두번 돌아야함
			int MAX_FORMSEQ_CNT = 50;
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
					
					if ( reqformno == 0 ) {
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
						}
					} else {
						for ( int j = 0;  j < tableName[0].length; j++ ) {
							StringBuffer sql = new StringBuffer();
							sql.append("UPDATE " + tableName[0][j] + "\n");
							sql.append("SET FORMSEQ = ?\n");
							sql.append("WHERE REQFORMNO = ?\n");
							sql.append("AND FORMSEQ = ?\n");
							
							pstmt = con.prepareStatement(sql.toString());
							pstmt.setInt(1, newFormseq);
							pstmt.setInt(2, reqformno);
							pstmt.setInt(3, oldFormseq);
							
							result += pstmt.executeUpdate();
							pstmt.clearParameters();
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
	
	/**
	 * 특정 처리상태의 전체개수 가져오기
	 * @param userid
	 * @param deptcd
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public int getStateTotalCount(String userid, String deptcd, String state) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) \n");
			sql.append("FROM REQFORMMST R1, REQMST R2 \n");
			sql.append("WHERE R1.REQFORMNO = R2.REQFORMNO \n");
			sql.append("AND R1.STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");
			sql.append("AND (R1.ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR R1.ENDDT IS NULL) ");
			sql.append("AND R1.CHRGUSRID LIKE ? \n");
			sql.append("AND R1.COLDEPTCD LIKE ? \n");
			sql.append("AND STATE = ? \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			int idx = 0;
			pstmt.setString(++idx, userid);
			pstmt.setString(++idx, deptcd);
			pstmt.setString(++idx, state);
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {
				result = rs.getInt(1);
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
	 * 신청문항첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List getReqFormSubFile(Connection conn, String sessionId, int reqformno) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM REQFORMSUBFILE_TEMP \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
			} else {
				sql.append("SELECT REQFORMNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM REQFORMSUBFILE \n");
				sql.append("WHERE REQFORMNO=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, reqformno);
			}
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				ArticleBean atcBean = new ArticleBean();
				
				atcBean.setSessi(sessionId);
				atcBean.setReqformno(reqformno);
				atcBean.setFormseq(rs.getInt("FORMSEQ"));
				atcBean.setFileseq(rs.getInt("FILESEQ"));
				atcBean.setFilenm(rs.getString("FILENM"));
				atcBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				atcBean.setFilesize(rs.getInt("FILESIZE"));
				atcBean.setExt(rs.getString("EXT"));				
				atcBean.setOrd(rs.getInt("ORD"));				
				
				if ( result == null ) {
					result = new ArrayList();
				}
				result.add(atcBean);
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
	 * 신청문항보기첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public List getReqFormExamFile(Connection conn, String sessionId, int reqformno, int formseq) throws Exception {
	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM REQFORMEXAMFILE_TEMP \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
			} else {
				sql.append("SELECT REQFORMNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM REQFORMEXAMFILE \n");
				sql.append("WHERE REQFORMNO=? \n");
			}
			if ( formseq != 0 ) {
				sql.append("AND FORMSEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, reqformno);
			}
			if ( formseq != 0 ) {
				pstmt.setInt(2, formseq);
			}
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				SampleBean spBean = new SampleBean();
				
				spBean.setSessi(sessionId);
				spBean.setReqformno(reqformno);
				spBean.setFormseq(rs.getInt("FORMSEQ"));
				spBean.setExamseq(rs.getInt("EXAMSEQ"));
				spBean.setFileseq(rs.getInt("FILESEQ"));
				spBean.setFilenm(rs.getString("FILENM"));
				spBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				spBean.setFilesize(rs.getInt("FILESIZE"));
				spBean.setExt(rs.getString("EXT"));				
				spBean.setOrd(rs.getInt("ORD"));
				
				if ( result == null ) {
					result = new ArrayList();
				}
				result.add(spBean);
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
	 * 신청내역첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param reqseq
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List getReqMstFile(Connection conn, int reqformno) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT REQFORMNO, REQSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
			sql.append("FROM REQMSTFILE \n");
			sql.append("WHERE REQFORMNO=? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, reqformno);
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				ReqMstBean bean = new ReqMstBean();

				bean.setReqformno(reqformno);
				bean.setReqseq(rs.getInt("REQSEQ"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));				
				
				if ( result == null ) {
					result = new ArrayList();
				}
				result.add(bean);
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
	 * @param reqformno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public int setOrderReqFormExamFile(Connection conn, String sessionId, int reqformno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("UPDATE REQFORMEXAMFILE_TEMP R \n");
				sql.append("SET R.EXAMSEQ = (SELECT NEWEXAMSEQ \n");
				sql.append("                 FROM (SELECT ROWNUM NEWEXAMSEQ, EXAMSEQ \n");
				sql.append("                       FROM (SELECT SESSIONID, FORMSEQ, EXAMSEQ \n");
				sql.append("                             FROM REQFORMEXAMFILE_TEMP \n");
				sql.append("                             WHERE SESSIONID LIKE ? \n");
				sql.append("                             AND FORMSEQ = ? \n");
				sql.append("                             ORDER BY EXAMSEQ)) \n");
				sql.append("                 WHERE EXAMSEQ = R.EXAMSEQ) \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ = ? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM REQFORMEXAM_TEMP \n");
				sql.append("                    WHERE SESSIONID LIKE ? \n"); 
				sql.append("                    AND FORMSEQ = ?) \n");
			} else {
				sql.append("UPDATE REQFORMEXAMFILE R \n");
				sql.append("SET R.EXAMSEQ = (SELECT NEWEXAMSEQ \n");
				sql.append("                 FROM (SELECT ROWNUM NEWEXAMSEQ, EXAMSEQ \n");
				sql.append("                       FROM (SELECT REQFORMNO, FORMSEQ, EXAMSEQ \n");
				sql.append("                             FROM REQFORMEXAMFILE \n");
				sql.append("                             WHERE REQFORMNO = ? \n");
				sql.append("                             AND FORMSEQ = ? \n");
				sql.append("                             ORDER BY EXAMSEQ)) \n");
				sql.append("                 WHERE EXAMSEQ = R.EXAMSEQ) \n");
				sql.append("WHERE REQFORMNO = ? \n");
				sql.append("AND FORMSEQ = ? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM REQFORMEXAM \n");
				sql.append("                    WHERE REQFORMNO = ? \n"); 
				sql.append("                    AND FORMSEQ = ?) \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
				pstmt.setString(3, sessionId);
				pstmt.setInt(4, formseq);
				pstmt.setString(5, sessionId);
				pstmt.setInt(6, formseq);
			} else {
				pstmt.setInt(1, reqformno);
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, reqformno);
				pstmt.setInt(4, formseq);
				pstmt.setInt(5, reqformno);
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
	 * 사용하지않는 신청문항보기첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public List getReqFormExamUnusedFile(Connection conn, String sessionId, int reqformno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM REQFORMEXAMFILE_TEMP R \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM  REQFORMEXAM_TEMP \n");
				sql.append("                    WHERE SESSIONID LIKE R.SESSIONID \n");
				sql.append("                    AND FORMSEQ=R.FORMSEQ) \n");
			} else {
				sql.append("SELECT REQFORMNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM REQFORMEXAMFILE R \n");
				sql.append("WHERE REQFORMNO=? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM  REQFORMEXAM \n");
				sql.append("                    WHERE REQFORMNO=R.REQFORMNO \n");
				sql.append("                    AND FORMSEQ=R.FORMSEQ) \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
			} else {
				pstmt.setInt(1, reqformno);
				pstmt.setInt(2, formseq);
			}
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				SampleBean spBean = new SampleBean();
				
				spBean.setSessi(sessionId);
				spBean.setReqformno(reqformno);
				spBean.setFormseq(rs.getInt("FORMSEQ"));
				spBean.setExamseq(rs.getInt("EXAMSEQ"));
				spBean.setFileseq(rs.getInt("FILESEQ"));
				spBean.setFilenm(rs.getString("FILENM"));
				spBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				spBean.setFilesize(rs.getInt("FILESIZE"));
				spBean.setExt(rs.getString("EXT"));				
				spBean.setOrd(rs.getInt("ORD"));
				
				if ( result == null ) {
					result = new ArrayList();
				}
				result.add(spBean);
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
	 * 신청문항첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public ArticleBean getReqFormSubFile(Connection conn, String sessionId, int reqformno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArticleBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM REQFORMSUBFILE_TEMP \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ=? \n");
			} else {
				sql.append("SELECT REQFORMNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM REQFORMSUBFILE \n");
				sql.append("WHERE REQFORMNO=? \n");
				sql.append("AND FORMSEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
			} else {
				pstmt.setInt(1, reqformno);
				pstmt.setInt(2, formseq);
			}
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new ArticleBean();
				
				result.setSessi(sessionId);
				result.setReqformno(reqformno);
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
	 * 신청문항보기첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public SampleBean getReqFormExamFile(Connection conn, String sessionId, int reqformno, int formseq, int examseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SampleBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM REQFORMEXAMFILE_TEMP \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ=? \n");
			} else {
				sql.append("SELECT REQFORMNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM REQFORMEXAMFILE \n");
				sql.append("WHERE REQFORMNO=? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, examseq);
			} else {
				pstmt.setInt(1, reqformno);
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, examseq);
			}
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new SampleBean();
				
				result.setSessi(sessionId);
				result.setReqformno(reqformno);
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
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 신청문항첨부파일 가져오기
	 * @param conn
	 * @param reqformno
	 * @param reqseq
	 * @return
	 * @throws Exception
	 */
	public ReqMstBean getReqMstFile(Connection conn, int reqformno, int reqseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReqMstBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT REQFORMNO, REQSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
			sql.append("FROM REQMSTFILE \n");
			sql.append("WHERE REQFORMNO=? \n");
			sql.append("AND REQSEQ=? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new ReqMstBean();
				
				result.setReqformno(reqformno);
				result.setReqseq(rs.getInt("REQSEQ"));
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
	 * 신청문항첨부파일 추가
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param fileBean
	 * @return
	 * @throws Exception
	 */
	public int addReqFormSubFile(Connection conn, String sessionId, int reqformno, int formseq, FileBean fileBean) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("INSERT INTO \n");
				sql.append("REQFORMSUBFILE_TEMP(SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?) \n");
			} else {				
				sql.append("INSERT INTO \n");
				sql.append("REQFORMSUBFILE(REQFORMNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?) \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, reqformno);
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
	 * 신청문항보기첨부파일 추가
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param examseq
	 * @param fileBean
	 * @return
	 * @throws Exception
	 */
	public int addReqFormExamFile(Connection conn, String sessionId, int reqformno, int formseq, int examseq, FileBean fileBean) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("INSERT INTO \n");
				sql.append("REQFORMEXAMFILE_TEMP(SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) \n");
			} else {
				sql.append("INSERT INTO \n");
				sql.append("REQFORMEXAMFILE(REQFORMNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) \n");
			}
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, reqformno);
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
	 * 신청내역첨부파일 추가
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param fileBean
	 * @return
	 * @throws Exception
	 */
	public int addReqAnsFile(Connection conn, int reqformno, int reqseq, FileBean fileBean) throws Exception {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
							
			sql.append("INSERT INTO \n");
			sql.append("REQMSTFILE(REQFORMNO, REQSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
			sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?) \n");
			//logger.info(sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			pstmt.setInt(3, fileBean.getFileseq());
			pstmt.setString(4, fileBean.getFilenm());
			pstmt.setString(5, fileBean.getOriginfilenm());
			pstmt.setInt(6, fileBean.getFilesize());
			pstmt.setString(7, fileBean.getExt());
			pstmt.setInt(8, fileBean.getFileseq());
			//logger.info("["+reqformno+"]["+reqseq+"]["+fileBean.getFileseq()+"]["+fileBean.getFilenm()+"]["+fileBean.getOriginfilenm()+"]["+fileBean.getFilesize()+"]["+fileBean.getExt()+"]["+fileBean.getFileseq()+"]");
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
	 * 신청문항첨부파일 삭제
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param fileseq
	 * @return
	 * @throws Exception
	 */
	public int delReqFormSubFile(Connection conn, String sessionId, int reqformno, int formseq, int fileseq) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("DELETE FROM REQFORMSUBFILE_TEMP \n");
				sql.append("WHERE SESSIONID LIKE ? AND FORMSEQ=? AND FILESEQ=? \n");
			} else {
				sql.append("DELETE FROM REQFORMSUBFILE \n");
				sql.append("WHERE REQFORMNO=? AND FORMSEQ=? AND FILESEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, reqformno);
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
	 * 신청문항보기첨부파일 삭제
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param examseq
	 * @param fileseq
	 * @return
	 * @throws Exception
	 */
	public int delReqFormExamFile(Connection conn, String sessionId, int reqformno, int formseq, int examseq, int fileseq) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("DELETE FROM REQFORMEXAMFILE_TEMP \n");
				sql.append("WHERE SESSIONID LIKE ? AND FORMSEQ=? AND EXAMSEQ=? AND FILESEQ=? \n");
			} else {
				sql.append("DELETE FROM REQFORMEXAMFILE \n");
				sql.append("WHERE REQFORMNO=? AND FORMSEQ=? AND EXAMSEQ=? AND FILESEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, reqformno);
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
	 * 신청내역첨부파일 삭제
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param fileseq
	 * @return
	 * @throws Exception
	 */
	public int delReqMstFile(Connection conn, int reqformno, int reqseq, int fileseq) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("DELETE FROM REQMSTFILE \n");
			sql.append("WHERE REQFORMNO=? AND REQSEQ=? AND FILESEQ=? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
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
	 * 메인화면에 보여줄 목록 가져오기 : 구버전용, 최신버전에는 사용안함
	 */
	public List mainShowList(String rep_dept) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List reqList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT BUNHO, REQFORMNO, TITLE, STRDT, ENDDT, CRTDT ");			
			selectQuery.append("FROM (SELECT ROWNUM BUNHO, REQFORMNO, TITLE, ");
			selectQuery.append("             REPLACE(SUBSTR(STRDT,6),'-','/') STRDT, REPLACE(SUBSTR(ENDDT,6),'-','/') ENDDT, CRTDT ");
			selectQuery.append("	  FROM (SELECT REQFORMNO, TITLE, STRDT, ENDDT, CRTDT ");
			selectQuery.append("            FROM REQFORMMST ");
			selectQuery.append("            WHERE COLDEPTCD IN (SELECT DEPT_ID FROM DEPT ");
			selectQuery.append("								CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID ");
			selectQuery.append("								START WITH DEPT_ID = '"+rep_dept+"' ) ");
			selectQuery.append("              AND STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");
			selectQuery.append("              AND (ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR ENDDT IS NULL) ");
			selectQuery.append("              AND RANGE = '1' ");
			selectQuery.append("            ORDER BY CRTDT DESC) ");
			selectQuery.append("      ) ");
			selectQuery.append("WHERE BUNHO BETWEEN 1 AND 5 ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			reqList = new ArrayList();
			
			while (rs.next()) {
				FrmBean bean = new FrmBean();						
				
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setStrdt(rs.getString("STRDT"));
				bean.setEnddt(rs.getString("ENDDT"));
				reqList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return reqList;
	}
	
	/**
	 * 메인화면에 보여줄 목록 가져오기
	 */
	public List mainShowList(String userid, String rep_dept) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List reqList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT BUNHO, REQFORMNO, TITLE, STRDT, ENDDT, CRTDT ");			
			selectQuery.append("FROM (SELECT ROWNUM BUNHO, REQFORMNO, TITLE, ");
			selectQuery.append("             REPLACE(SUBSTR(STRDT,6),'-','/') STRDT, REPLACE(SUBSTR(ENDDT,6),'-','/') ENDDT, CRTDT ");
			selectQuery.append("	  FROM (SELECT REQFORMNO, TITLE, STRDT, ENDDT, CRTDT ");
			selectQuery.append("            FROM REQFORMMST R ");
			selectQuery.append("            WHERE COLDEPTCD IN (SELECT DEPT_ID FROM DEPT ");
			selectQuery.append("								CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID ");
			selectQuery.append("								START WITH DEPT_ID = '"+rep_dept+"' ) ");
			selectQuery.append("              AND STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");
			selectQuery.append("              AND (ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR ENDDT IS NULL) ");
			selectQuery.append("              AND RANGE = '1' ");
			selectQuery.append("              AND 0 < (SELECT DECODE(LIMITCOUNT, 0, COUNT(REQSEQ) + 1, LIMITCOUNT) - COUNT(REQSEQ) ");
			selectQuery.append("                       FROM REQFORMMST RM, REQMST RA ");
			selectQuery.append("                       WHERE RM.REQFORMNO = RA.REQFORMNO(+) ");
			selectQuery.append("                       AND RM.REQFORMNO = R.REQFORMNO ");
			selectQuery.append("                       GROUP BY LIMITCOUNT) ");
			selectQuery.append("              AND 0 = CASE DUPLICHECK WHEN '2' THEN (SELECT COUNT(*) FROM REQMST \n");
			selectQuery.append("                                                     WHERE R.REQFORMNO = REQFORMNO \n");
			selectQuery.append("                                                     AND PRESENTID = ?) ELSE 0 END \n");
			selectQuery.append("            ORDER BY CRTDT DESC) ");
			selectQuery.append("      ) ");
			selectQuery.append("WHERE BUNHO BETWEEN 1 AND 5 ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery();
			
			reqList = new ArrayList();
			
			while (rs.next()) {
				FrmBean bean = new FrmBean();						
				
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setStrdt(rs.getString("STRDT"));
				bean.setEnddt(rs.getString("ENDDT"));
				reqList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return reqList;
	}
	
	/**
	 * 신청이 진행중인 건이 있는지 확인한다.
	 * gbn(1): 결재전, gbn(2):결재후
	 * 양식 수정여부를 확인하기 위해서
	 */
	public int reqMstCnt(int reqformno, String gbn) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(REQFORMNO) ");
			selectQuery.append("FROM REQMST ");
			selectQuery.append("WHERE REQFORMNO = ? ");
			
			if("1".equals(gbn)){
				selectQuery.append("  AND STATE = '01' ");
			} else {
				selectQuery.append("  AND STATE <> '01' ");
			}			
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, reqformno);
								
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
	 * 신청 진행중(접수중)인 신청서 건수
	 */
	public int jupsuCnt(String userid) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(REQFORMNO) ");
			selectQuery.append("FROM REQFORMMST ");
			selectQuery.append("WHERE STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");
			selectQuery.append("  AND (ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR ENDDT IS NULL) ");
			selectQuery.append("  AND CHRGUSRID = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, userid);
						
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
	 * 미처리된 접수 건수
	 */
	public int notProcCnt(String userid) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(A.REQFORMNO) ");
			selectQuery.append("FROM REQMST A, REQFORMMST B ");
			selectQuery.append("WHERE A.REQFORMNO = B.REQFORMNO ");
			selectQuery.append("  AND B.STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");
			selectQuery.append("  AND (B.ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR B.ENDDT IS NULL) ");
			selectQuery.append("  AND A.STATE <= '02' ");
			selectQuery.append("  AND B.CHRGUSRID = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());			
			pstmt.setString(1, userid);
			
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
	 * 신청하기 목록 가져오기
	 */
	public List doSinchungList(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List doList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT BUNHO, REQFORMNO, TITLE, COLDEPTNM, STRDT, ENDDT \n");
			selectQuery.append("FROM (SELECT ROWNUM BUNHO, REQFORMNO, TITLE, COLDEPTNM, REPLACE(SUBSTR(STRDT,6),'-','/') STRDT, \n");
			selectQuery.append("             REPLACE(SUBSTR(ENDDT,6),'-','/') ENDDT \n");
			selectQuery.append("      FROM (SELECT REQFORMNO, TITLE, COLDEPTNM, STRDT, ENDDT \n");
			selectQuery.append("            FROM REQFORMMST R \n");
			selectQuery.append("            WHERE COLDEPTCD IN (SELECT DEPT_ID FROM DEPT \n");
			selectQuery.append("								CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID \n");
			selectQuery.append("								START WITH DEPT_ID = ? ) \n"); 
			selectQuery.append("            AND STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') \n");			
			selectQuery.append("            AND (ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR ENDDT IS NULL) \n");
			selectQuery.append("            AND TITLE LIKE ? \n");
			selectQuery.append("            AND RANGE = '1' \n");
			selectQuery.append("            AND 0 < (SELECT DECODE(LIMITCOUNT, 0, COUNT(REQSEQ) + 1, LIMITCOUNT) - COUNT(REQSEQ) ");
			selectQuery.append("                     FROM REQFORMMST RM, REQMST RA ");
			selectQuery.append("                     WHERE RM.REQFORMNO = RA.REQFORMNO(+) ");
			selectQuery.append("                     AND RM.REQFORMNO = R.REQFORMNO ");
			selectQuery.append("                     GROUP BY LIMITCOUNT) ");
			selectQuery.append("            AND 0 = CASE DUPLICHECK WHEN '2' THEN (SELECT COUNT(*) FROM REQMST \n");
			selectQuery.append("                                               WHERE R.REQFORMNO = REQFORMNO \n");
			selectQuery.append("                                               AND PRESENTID = ?) ELSE 0 END \n");
			selectQuery.append("            ORDER BY ENDDT DESC, CRTDT DESC, UPTDT DESC) \n");
			selectQuery.append("      ) ");
			selectQuery.append("WHERE BUNHO BETWEEN ? AND ? ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
						System.out.println(selectQuery.toString());
			pstmt.setString(1, search.getDeptid());
			pstmt.setString(2, "%" + search.getTitle() + "%");
			pstmt.setString(3, search.getPresentid());
			pstmt.setInt(4, search.getStart_idx());
			pstmt.setInt(5, search.getEnd_idx());
			System.out.println("1 : "+search.getDeptid());
			System.out.println("2 : "+"%" + search.getTitle() + "%");
			System.out.println("3 : "+search.getPresentid());
			System.out.println("4 : "+search.getStart_idx());
			System.out.println("5 : "+search.getEnd_idx());
			rs = pstmt.executeQuery();
			
			doList = new ArrayList();
			
			while (rs.next()) {
				FrmBean bean = new FrmBean();
						
				bean.setBunho(rs.getInt("BUNHO"));
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setColdeptnm(rs.getString("COLDEPTNM"));
				bean.setStrdt(rs.getString("STRDT"));
				bean.setEnddt(rs.getString("ENDDT"));
			
				doList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return doList;
	}
	
	/**
	 * 신청하기 전체 건수 가져오기
	 */
	public int doSinchungTotCnt(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT COUNT(*) \n");
			selectQuery.append("FROM REQFORMMST R \n");
			selectQuery.append("WHERE COLDEPTCD IN (SELECT DEPT_ID FROM DEPT \n");
			selectQuery.append("					CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID \n");
			selectQuery.append("					START WITH DEPT_ID = ? ) \n"); 
			selectQuery.append("AND STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') \n");
			if ( search.isUnlimited() ) {
				selectQuery.append("AND (ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR ENDDT IS NULL) \n");
			} else {
				selectQuery.append("AND ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') \n");
			}
			selectQuery.append("AND TITLE LIKE ? \n");
			selectQuery.append("AND RANGE = '1' \n");
			selectQuery.append("AND 0 < (SELECT DECODE(LIMITCOUNT, 0, COUNT(REQSEQ) + 1, LIMITCOUNT) - COUNT(REQSEQ) ");
			selectQuery.append("         FROM REQFORMMST RM, REQMST RA ");
			selectQuery.append("         WHERE RM.REQFORMNO = RA.REQFORMNO(+) ");
			selectQuery.append("         AND RM.REQFORMNO = R.REQFORMNO ");
			selectQuery.append("         GROUP BY LIMITCOUNT) ");
			selectQuery.append("AND 0 = CASE DUPLICHECK WHEN '2' THEN (SELECT COUNT(*) FROM REQMST \n");
			selectQuery.append("                                   WHERE R.REQFORMNO = REQFORMNO \n");
			selectQuery.append("                                   AND PRESENTID = ?) ELSE 0 END \n");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, search.getDeptid());			
			pstmt.setString(2, "%" + search.getTitle() + "%");
			pstmt.setString(3, search.getPresentid());
			
			rs = pstmt.executeQuery();			
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}			
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return totalCount;
	}
	
	/**
	 * 내신청함 목록 가져오기
	 */
	public List mySinchungList(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List myList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT BUNHO, REQFORMNO, REQSEQ, TITLE, CRTDT, CCDNAME \n");
			selectQuery.append("FROM (SELECT ROWNUM BUNHO, REQFORMNO, REQSEQ, TITLE, CRTDT, CCDNAME \n");			
			selectQuery.append("      FROM (SELECT A.REQFORMNO, A.REQSEQ, B.TITLE, REPLACE(SUBSTR(A.CRTDT,6,11),'-','/') CRTDT, C.CCDNAME \n");
			selectQuery.append("            FROM REQMST A, REQFORMMST B, (SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = '015') C \n");
			selectQuery.append("            WHERE A.REQFORMNO = B.REQFORMNO \n");
			selectQuery.append("              AND A.STATE = C.CCDSUBCD \n");
			selectQuery.append("              AND A.STATE <> '99' \n");
			selectQuery.append("              AND A.CRTUSRID = ? \n");
			selectQuery.append("              AND B.TITLE LIKE ? \n");
			
			//신청중인것만 검색
			if("0".equals(search.getGbn())){
				selectQuery.append("          AND A.STATE <= '02' \n");
			}
			
			selectQuery.append("            ORDER BY A.CRTDT DESC, A.UPTDT DESC, B.ENDDT DESC) \n");
			selectQuery.append("      ) \n");
			selectQuery.append("WHERE BUNHO BETWEEN ? AND ? \n");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
						
			pstmt.setString(1, search.getUserid());
			pstmt.setString(2, "%" + search.getTitle() + "%");
			pstmt.setInt(3, search.getStart_idx());
			pstmt.setInt(4, search.getEnd_idx());
			
			rs = pstmt.executeQuery();
			
			myList = new ArrayList();
			
			while (rs.next()) {
				ReqMstBean bean = new ReqMstBean();
						
				bean.setBunho(rs.getInt("BUNHO"));
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setReqseq(rs.getInt("REQSEQ"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setStatenm(rs.getString("CCDNAME"));
						
				myList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return myList;
	}
	
	/**
	 * 내신청함 전체 건수 가져오기
	 */
	public int mySinchungTotCnt(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT COUNT(A.REQFORMNO) ");
			selectQuery.append("FROM REQMST A, REQFORMMST B ");
			selectQuery.append("WHERE A.REQFORMNO = B.REQFORMNO ");		
			selectQuery.append("  AND A.CRTUSRID = ? ");
			selectQuery.append("  AND B.TITLE LIKE ? ");
			selectQuery.append("  AND A.STATE <> '99' \n");
			
			//신청중인것만 검색
			if("0".equals(search.getGbn())){
				selectQuery.append("AND A.STATE <= '02' ");
			}		
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, search.getUserid());
			pstmt.setString(2, "%" + search.getTitle() + "%");
			
			rs = pstmt.executeQuery();			
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}			
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return totalCount;
	}
	
	/**
	 * 접수내역 목록 가져오기
	 */
	public List reqDataList(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List dataList = null;	
		
		try {
			int subLen = commfunction.getDeptFullNameSubstringIndex(search.getUserid());
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT BUNHO, REQFORMNO, REQSEQ, PRESENTNM, CRTDT, CCDNAME, DEPT_FULLNAME \n");
			selectQuery.append("FROM (SELECT ROWNUM BUNHO, REQFORMNO, REQSEQ, PRESENTNM, REPLACE(CRTDT,'-','/') CRTDT, CCDNAME, DEPT_FULLNAME \n");		
			selectQuery.append("      FROM (SELECT A.REQFORMNO, A.REQSEQ, A.PRESENTNM, SUBSTR(A.CRTDT,6,11) CRTDT, B.CCDNAME, \n");
			selectQuery.append("                   NVL(TRIM(SUBSTR(D.DEPT_FULLNAME, "+subLen+")), D.DEPT_NAME) DEPT_FULLNAME, DEPT_RANK, USR_RANK \n");
			selectQuery.append("			FROM REQMST A, (SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = '015') B, USR C, DEPT D \n");
			selectQuery.append("			WHERE A.STATE = B.CCDSUBCD \n");
			selectQuery.append("			  AND A.PRESENTID = C.USER_ID(+) \n");
			selectQuery.append("			  AND C.DEPT_ID = D.DEPT_ID(+) \n");
			selectQuery.append("			  AND A.STATE <> '99' \n");
			selectQuery.append("              AND A.PRESENTNM LIKE ? \n");
			if ("0".equals(search.getProcFL())){
				selectQuery.append("          AND A.STATE <= '02' \n");   //신청중
 			}
			selectQuery.append("              AND A.REQFORMNO = ? \n");
			selectQuery.append("              AND TO_DATE(SUBSTR(A.CRTDT, 1, 10), 'YYYY\"-\"MM\"-\"DD') \n");
			selectQuery.append("                  BETWEEN TO_DATE(?, 'YYYY\"-\"MM\"-\"DD') AND TO_DATE(?, 'YYYY\"-\"MM\"-\"DD') \n");
			if ( "2".equals(search.getGbn()) ) {	//부서
				selectQuery.append("            ORDER BY DEPT_RANK, USR_RANK, PRESENTNM, A.CRTDT DESC, A.REQSEQ) \n");
			} else if ( "3".equals(search.getGbn()) ) {	//성명
				selectQuery.append("            ORDER BY PRESENTNM, DEPT_RANK, USR_RANK, A.CRTDT DESC, A.REQSEQ) \n");
			} else {	//신청일시
				selectQuery.append("            ORDER BY A.CRTDT DESC, A.REQSEQ, DEPT_RANK, USR_RANK, PRESENTNM) \n");
			}
			selectQuery.append("      ) \n");
			selectQuery.append("WHERE BUNHO BETWEEN ? AND ? \n");
			//logger.info(selectQuery);
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
						
			pstmt.setString(1, "%" + search.getPresentnm() + "%");
			pstmt.setInt(2, search.getReqformno());
			pstmt.setString(3, search.getStrdt());
			if ( "".equals(Utils.nullToEmptyString(search.getEnddt())) ) {
				pstmt.setString(4, "9999-12-31");
			} else {
				pstmt.setString(4, search.getEnddt());
			}
			pstmt.setInt(5, search.getStart_idx());
			pstmt.setInt(6, search.getEnd_idx());
			
			rs = pstmt.executeQuery();
			
			dataList = new ArrayList();
			
			while (rs.next()) {
				ReqMstBean bean = new ReqMstBean();
				
				int reqformno = rs.getInt("REQFORMNO");
				int reqseq = rs.getInt("REQSEQ");
				
				bean.setBunho(rs.getInt("BUNHO"));
				bean.setReqformno(reqformno);
				bean.setReqseq(reqseq);
				bean.setPresentnm(rs.getString("PRESENTNM"));
				bean.setPosition(rs.getString("DEPT_FULLNAME"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setLastsanc(lastSancName(reqformno, reqseq));
				bean.setStatenm(rs.getString("CCDNAME"));
			
				dataList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return dataList;
	}
	
	/**
	 * 접수내역 목록 전체 건수 가져오기
	 */
	public int reqDataTotCnt(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT COUNT(REQSEQ) ");
			selectQuery.append("FROM REQMST ");
			selectQuery.append("WHERE STATE <> '99' ");
			selectQuery.append("  AND PRESENTNM LIKE ? ");
			
			if ( "0".equals(search.getProcFL()) ) {
				selectQuery.append("AND STATE <= '02' ");   //신청중
 			} else if ( "99".equals(search.getProcFL()) ) {
 				selectQuery.append("AND STATE LIKE '%' ");   //모든처리상태포함
 			}
			selectQuery.append("  AND REQFORMNO = ? ");
			selectQuery.append("  AND TO_DATE(SUBSTR(CRTDT, 1, 10), 'YYYY\"-\"MM\"-\"DD')  ");
			selectQuery.append("      BETWEEN TO_DATE(?, 'YYYY\"-\"MM\"-\"DD') AND TO_DATE(?, 'YYYY\"-\"MM\"-\"DD') ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());

			pstmt.setString(1, "%" + search.getPresentnm() + "%");
			pstmt.setInt(2, search.getReqformno());
			pstmt.setString(3, search.getStrdt());
			if ( "".equals(Utils.nullToEmptyString(search.getEnddt())) ) {
				pstmt.setString(4, "9999-12-31");
			} else {
				pstmt.setString(4, search.getEnddt());
			}
			
			rs = pstmt.executeQuery();			

			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}			
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return totalCount;
	}
	
	/**
	 * 신청 내역 가져오기
	 */
	public ReqMstBean reqDataInfo(int reqformno, int reqseq) throws Exception {
		Connection conn = null;
	    ResultSet rs = null;
		PreparedStatement pstmt = null;
		ReqMstBean bean = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT A.PRESENTNM, A.PRESENTID, A.PRESENTSN, A.PRESENTBD,");
			selectQuery.append("       A.POSITION,  A.DUTY,      A.ZIP,       A.ADDR1, A.ADDR2, ");
			selectQuery.append("       A.EMAIL,     A.TEL,       A.CEL,       A.FAX,   B.CCDNAME, ");
			selectQuery.append("       A.STATE,     A.CRTDT,     A.CRTUSRID,  A.UPTDT, A.UPTUSRID, ");
			selectQuery.append("       C.FILESEQ, C.FILENM, C.ORIGINFILENM, C.FILESIZE, C.EXT, C.ORD ");
			selectQuery.append("FROM REQMST A, (SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = '015') B, REQMSTFILE C ");
			selectQuery.append("WHERE A.STATE = B.CCDSUBCD ");
			selectQuery.append("  AND A.REQFORMNO = C.REQFORMNO(+) ");
			selectQuery.append("  AND A.REQSEQ = C.REQSEQ(+) ");
			selectQuery.append("  AND A.REQFORMNO = ? ");
			selectQuery.append("  AND A.REQSEQ = ? ");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
	
			rs = pstmt.executeQuery();
			
			if(rs.next()) {				
				
				bean = new ReqMstBean();
				
				bean.setReqformno(reqformno);
				bean.setReqseq(reqseq);
				bean.setPresentnm(rs.getString("PRESENTNM"));
				bean.setPresentid(rs.getString("PRESENTID"));
				bean.setPresentsn(rs.getString("PRESENTSN"));
				bean.setPresentbd(rs.getString("PRESENTBD"));
				bean.setPosition(rs.getString("POSITION"));
				bean.setDuty(rs.getString("DUTY"));
				bean.setZip(rs.getString("ZIP"));
				bean.setAddr1(rs.getString("ADDR1"));
				bean.setAddr2(rs.getString("ADDR2"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setTel(rs.getString("TEL"));
				bean.setCel(rs.getString("CEL"));
				bean.setFax(rs.getString("FAX"));
				bean.setStatenm(rs.getString("CCDNAME"));
				bean.setState(rs.getString("STATE"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				bean.setUptdt(rs.getString("UPTDT"));
				bean.setUptusrid(rs.getString("UPTUSRID"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));
				bean.setLastsanc(lastSancName(reqformno,reqseq));
				
				bean.setAnscontList(reqDataSubList(reqformno, reqseq));
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
	 * 신청내역 추가항목 가져오기
	 */
	public List reqDataSubList(int reqformno, int reqseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List dataSubList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT REQFORMNO, REQSEQ, FORMSEQ, ANSCONT, OTHER ");
			selectQuery.append("FROM REQSUB ");
			selectQuery.append("WHERE REQFORMNO = ? ");
			selectQuery.append("  AND REQSEQ = ? ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());						
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			
			rs = pstmt.executeQuery();
			
			dataSubList = new ArrayList();
			
			while (rs.next()) {				
				
				ReqSubBean bean = new ReqSubBean();
				
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setReqseq(rs.getInt("REQSEQ"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setAnscont(rs.getString("ANSCONT"));
				bean.setOther(rs.getString("OTHER"));
				
				dataSubList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return dataSubList;
	}

	/**
	 * 신청서 관리목록 가져오기
	 */
	public List reqFormList(SearchBean search, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List reqList = null;	
		try {
			StringBuffer selectQuery = new StringBuffer();			
			selectQuery.append("SELECT BUNHO, REQFORMNO, TITLE, STRDT, ENDDT, TDAY, RANGE, STATE, CRTDT, UPTDT, CHRGUSRID \n");
			selectQuery.append("FROM (SELECT ROWNUM BUNHO, REQFORMNO, TITLE, STRDT, ENDDT, TDAY, RANGE, STATE, CRTDT, UPTDT, CHRGUSRID \n");
			selectQuery.append("      FROM (SELECT REQFORMNO, TITLE, REPLACE(SUBSTR(STRDT,6),'-','/') STRDT, \n");
			selectQuery.append("                   REPLACE(SUBSTR(ENDDT,6),'-','/') ENDDT, TRUNC(TO_DATE(ENDDT,'YYYY-MM-DD')-SYSDATE) TDAY, RANGE, STATE, CRTDT, UPTDT, CHRGUSRID \n");
			selectQuery.append("            FROM (SELECT A.REQFORMNO, MAX(A.TITLE) TITLE, MAX(A.STRDT) STRDT, MAX(A.ENDDT) ENDDT, MAX(A.RANGE) RANGE, \n");
			selectQuery.append("       		             SUM(DECODE(B.STATE,'02',1,'01',1,0)) STATE, A.CRTDT, A.UPTDT, A.CHRGUSRID \n");
			selectQuery.append("                  FROM REQFORMMST A, REQMST B \n");
			selectQuery.append("                  WHERE A.REQFORMNO = B.REQFORMNO(+) \n");
			selectQuery.append("                  AND A.TITLE LIKE '%"+search.getTitle()+"%' \n");
			
			if(initentry.equals("first")){
				selectQuery.append("	                   AND A.COLDEPTCD = ? 							 	\n");
				selectQuery.append("                       AND A.CHRGUSRID = ? 								\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) selectQuery.append("\n  AND A.COLDEPTCD LIKE ?                           \n");
					if( !"".equals(sch_userid) ) selectQuery.append("\n  AND A.CHRGUSRID LIKE ?                           \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) selectQuery.append("\n  AND A.COLDEPTNM LIKE ?  \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) selectQuery.append("\n  AND A.CHRGUSRNM LIKE ?  \n");
				}else{
					selectQuery.append("                     AND A.COLDEPTCD = ? 								\n");
					selectQuery.append("                     AND A.CHRGUSRID = ? 								\n");
				}
			}
					
			if ("0".equals(search.getProcFL())){
				selectQuery.append("              AND B.STATE <= '02' \n");   //신청중
 			}
			
			selectQuery.append("            GROUP BY A.REQFORMNO, A.CRTDT, A.UPTDT, A.CHRGUSRID) \n"); 
			selectQuery.append("            ORDER BY TDAY DESC, ENDDT DESC, STRDT DESC, CRTDT DESC, UPTDT, CHRGUSRID DESC)) \n"); 
			selectQuery.append("WHERE BUNHO BETWEEN ? AND ? \n");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			//System.out.println(selectQuery.toString());
			int param = 1;
			if(initentry.equals("first")){
				pstmt.setString(param++, search.getDeptid());
				System.out.println(param+" : "+search.getDeptid());
				pstmt.setString(param++, search.getUserid());
				System.out.println(param+" : "+search.getUserid());
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) pstmt.setString(param++, "%"+ sch_deptcd +"%");
					if( !"".equals(sch_userid) ) pstmt.setString(param++, "%"+ sch_userid +"%");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) pstmt.setString(param++, "%"+ sch_deptnm +"%");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) pstmt.setString(param++, "%"+ sch_usernm +"%");
				}else{
					pstmt.setString(param++, search.getDeptid());
					pstmt.setString(param++, search.getUserid());
				}
			}
			
			pstmt.setInt(param++, search.getStart_idx());
			pstmt.setInt(param++, search.getEnd_idx());
			rs = pstmt.executeQuery();
			
			reqList = new ArrayList();
			
			while (rs.next()) {
				FrmBean bean = new FrmBean();
				bean.setBunho(rs.getInt("BUNHO"));
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setStrdt(rs.getString("STRDT"));
				bean.setEnddt(rs.getString("ENDDT"));
				bean.setTday(rs.getInt("TDAY"));
				bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				bean.setNotproc(rs.getInt("STATE"));
				bean.setChrgusrid(rs.getString("CHRGUSRID"));
				reqList.add(bean);				
			}							
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return reqList;
	}
	
	/**
	 * 신청서 관리 전체 건수 가져오기
	 */
	public int reqFormTotCnt(SearchBean search, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT COUNT(*) ");
			selectQuery.append("FROM (SELECT A.REQFORMNO ");
			selectQuery.append("      FROM REQFORMMST	A, REQMST B	");
			selectQuery.append("      WHERE A.REQFORMNO = B.REQFORMNO(+) ");
			selectQuery.append("        AND TITLE LIKE '%"+search.getTitle()+"%' ");
			
			if(initentry.equals("first")){
				selectQuery.append("	                   AND A.COLDEPTCD = ? 							 	\n");
				selectQuery.append("                       AND A.CHRGUSRID = ? 								\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) selectQuery.append("\n  AND A.COLDEPTCD LIKE ?                           \n");
					if( !"".equals(sch_userid) ) selectQuery.append("\n  AND A.CHRGUSRID LIKE ?                           \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) selectQuery.append("\n  AND A.COLDEPTNM LIKE ?  \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) selectQuery.append("\n  AND A.CHRGUSRNM LIKE ? \n");
				}else{
					selectQuery.append("                     AND A.COLDEPTCD = ? 								\n");
					selectQuery.append("                     AND A.CHRGUSRID = ? 								\n");
				}
			}
			
			if ("0".equals(search.getProcFL())){
				selectQuery.append("    AND B.STATE <= '02' ");   //신청중
			}
			
			selectQuery.append("      GROUP BY A.REQFORMNO) "); 		
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			int param = 1;
			if(initentry.equals("first")){
				pstmt.setString(param++, search.getDeptid());
				pstmt.setString(param++, search.getUserid());
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) pstmt.setString(param++, "%"+ sch_deptcd +"%");
					if( !"".equals(sch_userid) ) pstmt.setString(param++, "%"+ sch_userid +"%");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) pstmt.setString(param++, "%"+ sch_deptnm +"%");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) pstmt.setString(param++, "%"+ sch_usernm +"%");
				}else{
					pstmt.setString(param++, search.getDeptid());
					pstmt.setString(param++, search.getUserid());
				}
			}
					
			rs = pstmt.executeQuery();			
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}			
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return totalCount;
	}
	
	/**
	 * 기존양식 목록 가져오기
	 */
	public List getUsedList(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List usedList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT BUNHO, REQFORMNO, TITLE, STRDT, ENDDT, RANGE ");
			selectQuery.append("FROM (SELECT ROWNUM BUNHO, REQFORMNO, TITLE, REPLACE(SUBSTR(STRDT,6),'-','/') STRDT, ");
			selectQuery.append("             REPLACE(SUBSTR(ENDDT,6),'-','/') ENDDT, RANGE ");
			selectQuery.append("      FROM (SELECT REQFORMNO, TITLE,  STRDT, ENDDT, RANGE ");
			selectQuery.append("            FROM REQFORMMST ");
			selectQuery.append("            WHERE COLDEPTCD IN (SELECT DEPT_ID FROM DEPT ");
			selectQuery.append("								CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID ");
			selectQuery.append("								START WITH DEPT_ID = ?) "); 			
			selectQuery.append("            AND TITLE LIKE '%"+search.getTitle()+"%' ");
			selectQuery.append("            AND CRTDT LIKE '"+search.getSyear().substring(0, 4)+"%'");
			selectQuery.append("            ORDER BY ENDDT DESC) ");
			selectQuery.append("      ) ");
			selectQuery.append("WHERE BUNHO BETWEEN ? AND ? ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
						
			pstmt.setString(1, search.getDeptid());
			pstmt.setInt(2, search.getStart_idx());
			pstmt.setInt(3, search.getEnd_idx());
			
			rs = pstmt.executeQuery();
			
			usedList = new ArrayList();
			
			while (rs.next()) {
				FrmBean bean = new FrmBean();
						
				bean.setBunho(rs.getInt("BUNHO"));
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setStrdt(rs.getString("STRDT"));
				bean.setEnddt(rs.getString("ENDDT"));
				bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				
				usedList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return usedList;
	}
	
	/**
	 * 기존양식 가져오기 전체 건수 가져오기
	 */
	public int getUsedTotCnt(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT COUNT(*) ");
			selectQuery.append("FROM REQFORMMST ");
			selectQuery.append("WHERE COLDEPTCD IN (SELECT DEPT_ID FROM DEPT ");
			selectQuery.append("					CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID ");
			selectQuery.append("					START WITH DEPT_ID = ? ) "); 
			//selectQuery.append("AND STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");			
			//selectQuery.append("AND (ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR ENDDT IS NULL) ");				
			selectQuery.append("AND TITLE LIKE '%"+search.getTitle()+"%' ");
			selectQuery.append("AND CRTDT LIKE '"+search.getSyear().substring(0, 4)+"%'");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, search.getDeptid());
			
			rs = pstmt.executeQuery();			
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}			
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return totalCount;
	}
	
	/**
	 * 마스터 정보 가져오기
	 */
	public FrmBean reqFormInfo(int reqformno) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		FrmBean bean = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT REQFORMNO, TITLE,     STRDT,     ENDDT,    COLDEPTCD, ");
			selectQuery.append("       COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY,  RANGE, ");
			selectQuery.append("       IMGPREVIEW, DUPLICHECK, LIMITCOUNT, SANCNEED,  BASICTYPE, HEADCONT,  TAILCONT, CRTDT, "); 
			selectQuery.append("       CRTUSRID,  UPTDT,     UPTUSRID,  SIGN(MONTHS_BETWEEN(TO_DATE(ENDDT,'YYYY-MM-DD'), SYSDATE)) CLOSEFL, ");			
			selectQuery.append("       (SELECT COUNT(1) FROM REQFORMSUB  WHERE REQFORMNO = A.REQFORMNO) AS ACNT  ");
			selectQuery.append("FROM REQFORMMST A ");
			selectQuery.append("WHERE REQFORMNO = ? ");

			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, reqformno);

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				bean = new FrmBean();
				
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setStrdt(rs.getString("STRDT"));
				bean.setEnddt(rs.getString("ENDDT"));
				bean.setColdeptcd(rs.getString("COLDEPTCD"));
				bean.setColdeptnm(rs.getString("COLDEPTNM"));
				bean.setColtel(rs.getString("COLDEPTTEL"));
				bean.setChrgusrid(rs.getString("CHRGUSRID"));
				bean.setChrgusrnm(rs.getString("CHRGUSRNM"));
				bean.setSummary(rs.getString("SUMMARY"));
				bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				bean.setImgpreview(rs.getString("IMGPREVIEW"));
				bean.setDuplicheck(rs.getString("DUPLICHECK"));
				bean.setLimitcount(rs.getInt("LIMITCOUNT"));
				bean.setSancneed(rs.getString("SANCNEED"));
				bean.setBasictype(rs.getString("BASICTYPE"));
				bean.setHeadcont(rs.getString("HEADCONT"));
				bean.setTailcont(rs.getString("TAILCONT"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				bean.setUptdt(rs.getString("UPTDT"));
				bean.setUptusrid(rs.getString("UPTUSRID"));
				bean.setAcnt(rs.getInt("ACNT"));
				
				int temp = rs.getInt("CLOSEFL");
				if(temp >= 0){
					bean.setClosefl("N");
				} else {
					bean.setClosefl("Y");
				}						
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
	 * 마스터(TEMP) 정보 가져오기
	 */
	public FrmBean reqFormInfo_temp(String sessi) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		FrmBean bean = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT TITLE,     STRDT,     ENDDT,    COLDEPTCD, COLDEPTNM, COLDEPTTEL, ");
			selectQuery.append("       CHRGUSRID, CHRGUSRNM, SUMMARY,  RANGE, IMGPREVIEW, DUPLICHECK, LIMITCOUNT, ");
			selectQuery.append("       SANCNEED, BASICTYPE, HEADCONT,  TAILCONT, CRTDT,     CRTUSRID, ");
			selectQuery.append("       UPTDT,     UPTUSRID, ");
			selectQuery.append("       (SELECT COUNT(1) FROM REQFORMSUB_TEMP  WHERE SESSIONID = A.SESSIONID) AS ACNT  ");
			selectQuery.append("FROM REQFORMMST_TEMP A ");
			selectQuery.append("WHERE SESSIONID LIKE ? ");
			//logger.info(selectQuery );
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, sessi);
			//logger.info("1 : "+sessi);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				bean = new FrmBean();				
				
				bean.setTitle(rs.getString("TITLE"));
				bean.setStrdt(rs.getString("STRDT"));
				bean.setEnddt(rs.getString("ENDDT"));
				bean.setColdeptcd(rs.getString("COLDEPTCD"));
				bean.setColdeptnm(rs.getString("COLDEPTNM"));
				bean.setColtel(rs.getString("COLDEPTTEL"));
				bean.setChrgusrid(rs.getString("CHRGUSRID"));
				bean.setChrgusrnm(rs.getString("CHRGUSRNM"));
				bean.setSummary(rs.getString("SUMMARY"));
				bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				bean.setImgpreview(rs.getString("IMGPREVIEW"));
				bean.setDuplicheck(rs.getString("DUPLICHECK"));
				bean.setLimitcount(rs.getInt("LIMITCOUNT"));
				bean.setSancneed(rs.getString("SANCNEED"));
				bean.setBasictype(rs.getString("BASICTYPE"));
				bean.setHeadcont(rs.getString("HEADCONT"));
				bean.setTailcont(rs.getString("TAILCONT"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				bean.setUptdt(rs.getString("UPTDT"));
				bean.setUptusrid(rs.getString("UPTUSRID"));
				bean.setAcnt(rs.getInt("ACNT"));
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
	 * 문항목록 가져오기
	 */
	public List reqFormSubList(int reqformno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List reqSubList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT A.REQFORMNO, A.FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, ");
			selectQuery.append("	   SECURITY, HELPEXP, EXAMTYPE, ");
			selectQuery.append("	   FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
			selectQuery.append("FROM REQFORMSUB A, REQFORMSUBFILE B ");
			selectQuery.append("WHERE A.REQFORMNO = B.REQFORMNO(+) ");
			selectQuery.append("  AND A.FORMSEQ = B.FORMSEQ(+) ");
			selectQuery.append("  AND A.REQFORMNO = ? 		");
			selectQuery.append("ORDER BY FORMSEQ 			");	
			//logger.info(selectQuery.toString());
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());						
			pstmt.setInt(1, reqformno);
			
			rs = pstmt.executeQuery();
			
			reqSubList = new ArrayList();
			
			while (rs.next()) {
				int formno = rs.getInt("REQFORMNO");
				int formseq = rs.getInt("FORMSEQ");
				
				ArticleBean bean = new ArticleBean();
				
				bean.setReqformno(formno);
				bean.setFormseq(formseq);
				bean.setFormtitle(rs.getString("FORMTITLE"));
				bean.setRequire(rs.getString("REQUIRE"));
				bean.setFormtype(rs.getString("FORMTYPE"));
				bean.setSecurity(rs.getString("SECURITY"));
				bean.setHelpexp(rs.getString("HELPEXP"));
				bean.setExamtype(rs.getString("EXAMTYPE"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));
				bean.setEx_frsq(rs.getInt("EX_FRSQ"));
				bean.setEx_exsq(rs.getString("EX_EXSQ"));
				
				bean.setSampleList(sampleList(formno, formseq));
				
				reqSubList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return reqSubList;
	}
	
	/**
	 * 문항목록(TEMP) 가져오기
	 */
	public List reqFormSubList_temp(String sessi, int examcount) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List reqSubList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT A.FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, ");
			selectQuery.append("	   SECURITY, HELPEXP, EXAMTYPE, ");
			selectQuery.append("	   FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
			selectQuery.append("FROM REQFORMSUB_TEMP A, REQFORMSUBFILE_TEMP B ");
			selectQuery.append("WHERE A.SESSIONID = B.SESSIONID(+) ");
			selectQuery.append("  AND A.FORMSEQ = B.FORMSEQ(+) ");
			selectQuery.append("  AND A.SESSIONID LIKE ? 		");
			selectQuery.append("ORDER BY FORMSEQ 			");	
			//logger.info(selectQuery);
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());						
			pstmt.setString(1, sessi);
			//logger.info("1 : "+sessi);
			rs = pstmt.executeQuery();
			
			reqSubList = new ArrayList();
			
			while (rs.next()) {				
				int formseq = rs.getInt("FORMSEQ");
				
				ArticleBean bean = new ArticleBean();				
				
				bean.setSessi(sessi);
				bean.setFormseq(formseq);
				bean.setFormtitle(rs.getString("FORMTITLE"));
				bean.setRequire(rs.getString("REQUIRE"));
				bean.setFormtype(rs.getString("FORMTYPE"));
				bean.setSecurity(rs.getString("SECURITY"));
				bean.setHelpexp(rs.getString("HELPEXP"));
				bean.setExamtype(rs.getString("EXAMTYPE"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));
				bean.setEx_frsq(rs.getInt("EX_FRSQ"));
				bean.setEx_exsq(rs.getString("EX_EXSQ"));

				
				bean.setSampleList(sampleList_temp(sessi, formseq, examcount));
				
				reqSubList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return reqSubList;
	}
	
	/**
	 * 문항별 보기목록 가져오기
	 */
	public List sampleList(int reqformno, int formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List sampleList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT A.REQFORMNO, A.FORMSEQ, A.EXAMSEQ, EXAMCONT, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD ");
			selectQuery.append("FROM REQFORMEXAM A, REQFORMEXAMFILE B ");
			selectQuery.append("WHERE A.REQFORMNO = B.REQFORMNO(+) ");
			selectQuery.append("  AND A.FORMSEQ = B.FORMSEQ(+) ");
			selectQuery.append("  AND A.EXAMSEQ = B.EXAMSEQ(+) ");
			selectQuery.append("  AND A.REQFORMNO = ? ");
			selectQuery.append("  AND A.FORMSEQ = ? ");
			selectQuery.append("ORDER BY A.EXAMSEQ ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());						
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			sampleList = new ArrayList();
			int cnt = 0;
			while (rs.next()) {
				SampleBean bean = new SampleBean();
						
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setExamseq(rs.getInt("EXAMSEQ"));
				bean.setExamcont(rs.getString("EXAMCONT"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));
				
				sampleList.add(bean);
				cnt = cnt + 1;
			}						
			
			//비어있는 보기를 examcount개까지 채운다.
			int examcount = getReqSubExamcount(reqformno, "");
			for(int i=0;i<examcount-cnt;i++){
				SampleBean bean = new SampleBean();
				sampleList.add(bean);
			}
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return sampleList;
	}
	
	/**
	 * 문항별 보기목록(TEMP) 가져오기
	 */
	public List sampleList_temp(String sessi, int formseq, int examcount) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List sampleList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT A.FORMSEQ, A.EXAMSEQ, EXAMCONT, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD ");		
			selectQuery.append("FROM REQFORMEXAM_TEMP A, REQFORMEXAMFILE_TEMP B ");
			selectQuery.append("WHERE A.SESSIONID = B.SESSIONID(+) ");
			selectQuery.append("  AND A.FORMSEQ = B.FORMSEQ(+) ");
			selectQuery.append("  AND A.EXAMSEQ = B.EXAMSEQ(+) ");
			selectQuery.append("  AND A.SESSIONID LIKE ? ");
			selectQuery.append("  AND A.FORMSEQ = ? ");
			selectQuery.append("ORDER BY A.EXAMSEQ ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());						
			pstmt.setString(1, sessi);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			sampleList = new ArrayList();
			int cnt = 0;
			while (rs.next()) {
				SampleBean bean = new SampleBean();						
				
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setExamseq(rs.getInt("EXAMSEQ"));
				bean.setExamcont(rs.getString("EXAMCONT"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));
				
				sampleList.add(bean);
				cnt = cnt + 1;
			}						
			
			//비어있는 보기를 examcount개까지 채운다.
			for(int i=0;i<examcount-cnt;i++){
				SampleBean bean = new SampleBean();
				sampleList.add(bean);
			}
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return sampleList;
	}
	
	/**
	 * 임시테이블로 양식 데이터 복사
	 */
	public int copyToTemp(int reqformno, String sessi, ServletContext context, String saveDir) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;			
				
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
            //양식마스터 복사
			StringBuffer insertQuery1 = new StringBuffer();
			insertQuery1.append("INSERT INTO REQFORMMST_TEMP ");
			insertQuery1.append("SELECT '" + sessi + "', TITLE, STRDT, ENDDT, COLDEPTCD, ");
			insertQuery1.append("       COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, RANGE, ");
			insertQuery1.append("       IMGPREVIEW, DUPLICHECK, LIMITCOUNT, SANCNEED, BASICTYPE, HEADCONT, TAILCONT, ");
			insertQuery1.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), CRTUSRID, '', '' ");
			insertQuery1.append("FROM REQFORMMST ");
			insertQuery1.append("WHERE REQFORMNO = ? "); 
			
			pstmt = con.prepareStatement(insertQuery1.toString());
			pstmt.setInt(1, reqformno);
			
			result = pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 문항 복사
			StringBuffer insertQuery2 = new StringBuffer();
			insertQuery2.append("INSERT INTO REQFORMSUB_TEMP ");
			insertQuery2.append("SELECT '" + sessi + "', FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, SECURITY, HELPEXP, EXAMTYPE, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
			insertQuery2.append("FROM REQFORMSUB ");
			insertQuery2.append("WHERE REQFORMNO = ? "); 
			//logger.info(insertQuery2);
			pstmt = con.prepareStatement(insertQuery2.toString());
			pstmt.setInt(1, reqformno);
			//logger.info("1 : "+reqformno);
			result += pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 보기 복사
			StringBuffer insertQuery3 = new StringBuffer();
			insertQuery3.append("INSERT INTO REQFORMEXAM_TEMP ");
			insertQuery3.append("SELECT '" + sessi + "', FORMSEQ, EXAMSEQ, EXAMCONT ");
			insertQuery3.append("FROM REQFORMEXAM ");
			insertQuery3.append("WHERE REQFORMNO = ? "); 
			
			pstmt = con.prepareStatement(insertQuery3.toString());
			pstmt.setInt(1, reqformno);
			
			result += pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//첨부파일 복사
			result += copyReqFormSubExamFile(con, sessi, reqformno, context, saveDir, "VIEW");
			
			con.commit();
		} catch (Exception e) {
			try{
				result = 0;
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
	 * 전체 저장(TEMP)
	 */
	public int saveAll(FrmBean fbean, SinchungForm sForm, ServletContext context, String saveDir) throws Exception {
		//logger.info("saveAll");
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;	
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//임시테이블에 값이 있으면 업데이트 한다.
			if(existCheck_temp(fbean.getSessi())){
								
				//마스터 저장
				result = updateMst(con, fbean, "TEMP");				
				//문항 삭제
				result += deleteArticleAll(con, 0, fbean.getSessi());				
				//보기 삭제
				result += deleteSampleAll(con, 0, fbean.getSessi());
				//문항저장(보기포함)
				result += insertArticleAll(con, fbean.getArticleList(), "TEMP");
				
				//파일 업로드
				File saveFolder = new File(context.getRealPath(saveDir));
				if(!saveFolder.exists()) saveFolder.mkdirs();
				
				String[] formattitleFileYN = fbean.getFormtitleFileYN();
				String[] examcontFileYN = fbean.getExamcontFileYN();
				String[] formtitle = fbean.getFormtitle();
				String[] examcont = fbean.getExamcont();
				int examcount = fbean.getExamcount();
				
				fbean.setReqformno(0);	//0일때 임시테이블이 대상이 됨
				
				for ( int i = 0; formtitle != null && i < formtitle.length; i++ ) {
					ArticleBean atcBean = getReqFormSubFile(con, fbean.getSessi(), fbean.getReqformno(), i+1);
					
					if ( atcBean != null &&
							(formattitleFileYN[i] == null || 
									(fbean.getFormtitleFile()[i] != null && fbean.getFormtitleFile()[i].getFileName().equals("") == false)) ) {
						delReqFormSubFile(con, fbean.getSessi(), fbean.getReqformno(), i+1, 1);
						
						String delFile = atcBean.getFilenm();
						if ( delFile != null && delFile.trim().equals("") == false) {
							FileManager.doFileDelete(context.getRealPath(delFile));
						}
					}
					
					if ( fbean.getFormtitleFile()[i] != null && fbean.getFormtitleFile()[i].getFileName().equals("") == false ) {
						FileBean atcFileBean = FileManager.doFileUpload(fbean.getFormtitleFile()[i], context, saveDir);
						
						if( atcFileBean != null ) {
							int addResult = 0;
							atcFileBean.setFileseq(1);
							addResult = addReqFormSubFile(con, fbean.getSessi(), fbean.getReqformno(), i+1, atcFileBean);
							if ( addResult == 0 ) {
								throw new Exception("첨부실패:DB업데이트");
							}
						} else {
							throw new Exception("첨부실패:파일업로드");
						}
					}
					
					List subList = null;
					if(sForm.getReqformno() == 0){
						subList = reqFormSubList_temp(sForm.getSessi(), examcount);
					} else {
						subList = reqFormSubList(sForm.getReqformno());
					}
					int subcount = subList.size();
					
					int prevExamcount = 0;
					while ( prevExamcount < sForm.getExamcontFile().length
							&& sForm.getExamcontFile()[prevExamcount] != null ) {
						prevExamcount++;
					}
					prevExamcount = prevExamcount / subcount;
					
					if ( examcount >= prevExamcount ) {
					
						for ( int j = 0; examcont != null && j < prevExamcount; j++ ) {
							SampleBean spBean = getReqFormExamFile(con, fbean.getSessi(), fbean.getReqformno(), i+1, j+1);
							try {
								if ( spBean != null &&
										(examcont[i*prevExamcount+j].trim().equals("") == true || examcontFileYN[i*prevExamcount+j] == null || 
												(fbean.getExamcontFile()[i*prevExamcount+j] != null && fbean.getExamcontFile()[i*prevExamcount+j].getFileName().equals("") == false)) ) {
									delReqFormExamFile(con, fbean.getSessi(), fbean.getReqformno(), i+1, j+1, 1);
									
									String delFile = spBean.getFilenm();
									if ( delFile != null && delFile.trim().equals("") == false) {
										FileManager.doFileDelete(context.getRealPath(delFile));
									}
								}
							} catch ( Exception e ) {}
	
							try {
								if ( fbean.getExamcontFile()[i*prevExamcount+j] != null && fbean.getExamcontFile()[i*prevExamcount+j].getFileName().equals("") == false ) {
									FileBean spFileBean = FileManager.doFileUpload(fbean.getExamcontFile()[i*prevExamcount+j], context, saveDir);
									
									if(spFileBean != null) {
										int addResult = 0;
										spFileBean.setFileseq(1);
										addResult = addReqFormExamFile(con, fbean.getSessi(), fbean.getReqformno(), i+1, j+1, spFileBean);
										if ( addResult == 0 ) {
											throw new Exception("첨부실패:DB업데이트");
										}
									} else {
										throw new Exception("첨부실패:파일업로드");
									}
								}
							} catch ( Exception e ) {}
						}

						setOrderReqFormExamFile(con, fbean.getSessi(), fbean.getReqformno(), i+1);
					}
					
					List spUnusedList = getReqFormExamUnusedFile(con, fbean.getSessi(), fbean.getReqformno(), i+1);
					
					for ( int k = 0; spUnusedList != null && k < spUnusedList.size(); k++ ) {
						SampleBean spBean = (SampleBean)spUnusedList.get(k);
						
						if ( spBean != null ) {
							delReqFormExamFile(con, fbean.getSessi(), fbean.getReqformno(), i+1, spBean.getExamseq(), spBean.getFileseq());
							
							String delFile = spBean.getFilenm();
							if ( delFile != null && delFile.trim().equals("") == false) {
								FileManager.doFileDelete(context.getRealPath(delFile));
							}
						}
					}
				}
				
			} else {
				//마스터 저장 (TEMP)
				result = insertMst(con, fbean);
			}		
			
			con.commit();
		} catch (Exception e) {
			try{
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
	 * 항목 만들기
	 */
	public int makeArticle(int reqformno, String sessi, int acnt) throws Exception{
		//logger.info("makeArticle");
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		int tablecnt = getCntFormseq(reqformno, sessi);
		int maxseq = getMaxFormseq(reqformno, sessi);
		int result = 0;
		String tb = "";
		String kfield = "";		
		
		if(reqformno == 0){
			tb = "REQFORMSUB_TEMP";
			kfield = "SESSIONID";			
		} else {
			tb = "REQFORMSUB";
			kfield = "REQFORMNO";
		}		
		
		try{			
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			//logger.info(acnt+"/"+tablecnt);
			if(acnt == tablecnt){
				return 0;  //항목수가 변화가 없으면 Return
			} else if(acnt > tablecnt) {
				//항목 차이 수량 만큼 추가
				int gap = acnt - tablecnt;
				
				for(int i=0;i<gap;i++){
					StringBuffer insertQuery = new StringBuffer();
					
					insertQuery.append("INSERT INTO "+tb+"("+kfield+", FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, ");
					insertQuery.append("                   SECURITY, HELPEXP, EXAMTYPE, EX_FRSQ, EX_EXSQ) ");
					insertQuery.append("VALUES(?, ?, '', 'N', '1',   'N', '', 'N', 0, 0) ");
					//logger.info(insertQuery);
					
					pstmt = con.prepareStatement(insertQuery.toString());
					
					if(reqformno == 0){
						pstmt.setString(1, sessi);
						//logger.info("1 : "+sessi);
					} else {
						pstmt.setInt(1, reqformno);
						//logger.info("1 : "+reqformno);
					}
					
					pstmt.setInt(2, maxseq+i);
					
					result = pstmt.executeUpdate();					
					ConnectionManager.close(pstmt);
				}
			} else if(acnt < tablecnt){
				//항목 차이 수량 만큼 삭제
				int gap = tablecnt - acnt;				
				
				for(int i=0;i<gap;i++){
					//제일 끝에 있는 항목을 가져와서 삭제한다.
					StringBuffer selectQuery = new StringBuffer();					
					selectQuery.append("SELECT NVL(MAX(FORMSEQ),0) FROM "+tb+" WHERE "+kfield+" = ? ");
										
					pstmt = con.prepareStatement(selectQuery.toString());
					
					if(reqformno == 0){
						pstmt.setString(1, sessi);	
					} else {
						pstmt.setInt(1, reqformno);
					}
								
					rs = pstmt.executeQuery();	
					
					int formseq = 0;
					if ( rs.next() ){
						formseq = rs.getInt(1);
					}						
					ConnectionManager.close(pstmt, rs);
					
					//삭제 처리
					StringBuffer delteQuery = new StringBuffer();	
					delteQuery.append("DELETE FROM "+tb+" WHERE "+kfield+" = ? AND FORMSEQ = ? ");
										
					pstmt = con.prepareStatement(delteQuery.toString());
					
					if(reqformno == 0){
						pstmt.setString(1, sessi);	
					} else {
						pstmt.setInt(1, reqformno);
					}					
					pstmt.setInt(2, formseq);
								
					result += pstmt.executeUpdate();
					ConnectionManager.close(pstmt);
					
					//보기 삭제
					result += deleteSample(con, reqformno, sessi, formseq);
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
	 * 완료하기
	 * 임시테이블에서 정규테이블로 복사한다.
	 */
	public int saveComp(String sessi, ServletContext context, String saveDir) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		int reqformno = 0;		
				
		try{				
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			StringBuffer selectQuery1 = new StringBuffer();
			selectQuery1.append("SELECT REQFORMSEQ.NEXTVAL FROM DUAL ");
			
			pstmt = con.prepareStatement(selectQuery1.toString());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				reqformno = rs.getInt(1);
			}
			
			ConnectionManager.close(pstmt, rs);
			
			//양식마스터 복사
			StringBuffer insertQuery1 = new StringBuffer();
			insertQuery1.append("INSERT INTO REQFORMMST ");
			insertQuery1.append("SELECT " + reqformno + ", TITLE, STRDT, ENDDT, COLDEPTCD, ");
			insertQuery1.append("       COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, RANGE, ");
			insertQuery1.append("       IMGPREVIEW, DUPLICHECK, LIMITCOUNT, SANCNEED, BASICTYPE, HEADCONT, TAILCONT, ");
			insertQuery1.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), CRTUSRID, '', '' ");
			insertQuery1.append("FROM REQFORMMST_TEMP ");
			insertQuery1.append("WHERE SESSIONID LIKE ? "); 
			//logger.info("양식마스터 복사 : "+insertQuery1.toString());
			pstmt = con.prepareStatement(insertQuery1.toString());
			pstmt.setString(1, sessi);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 문항 복사
			StringBuffer insertQuery2 = new StringBuffer();
			insertQuery2.append("INSERT INTO REQFORMSUB ");
			insertQuery2.append("SELECT " + reqformno + ", FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, SECURITY, HELPEXP, EXAMTYPE, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
			insertQuery2.append("FROM REQFORMSUB_TEMP ");
			insertQuery2.append("WHERE SESSIONID LIKE ? "); 
			//logger.info("양식 문항 복사 : "+insertQuery2);
			pstmt = con.prepareStatement(insertQuery2.toString());
			pstmt.setString(1, sessi);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 보기 복사
			StringBuffer insertQuery3 = new StringBuffer();
			insertQuery3.append("INSERT INTO REQFORMEXAM ");
			insertQuery3.append("SELECT " + reqformno + ", FORMSEQ, EXAMSEQ, EXAMCONT ");
			insertQuery3.append("FROM REQFORMEXAM_TEMP ");
			insertQuery3.append("WHERE SESSIONID LIKE ? "); 
			//logger.info("양식 보기 복사 : "+insertQuery3);
			pstmt = con.prepareStatement(insertQuery3.toString());
			pstmt.setString(1, sessi);
			
			pstmt.executeUpdate();			
		//	ConnectionManager.close(pstmt);
			
			//원본첨부파일 삭제
			delReqFormSubExamAllFile(con, "", reqformno, context);
			
			//첨부파일 복사
			copyReqFormSubExamFile(con, sessi, reqformno, context, saveDir, "SAVE");
			
			con.commit();
		} catch (Exception e) {
			try{
				reqformno = -1;
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
		return reqformno;
	}
	
	/**
	 * 신청서 첨부파일 복사
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param context
	 * @param saveDir
	 * @param mode	( VIEW : 신청서보기, SAVE : 신청서저장 ) 
	 * @return
	 * @throws Exception
	 */
	public int copyReqFormSubExamFile(Connection conn, String sessionId, int reqformno, ServletContext context, String saveDir, String mode) throws Exception {
		int result = 0;
		
		List atcFile = null;
		if ( mode.equals("VIEW") ) {
			atcFile = getReqFormSubFile(conn, "", reqformno);
		} else if ( mode.equals("SAVE") ) {
			atcFile = getReqFormSubFile(conn, sessionId, 0);
		}
		for ( int i = 0; atcFile != null && i < atcFile.size(); i++ ) {
			ArticleBean atcBean = (ArticleBean)atcFile.get(i);
			
			String newFilenm = "";
			try {
				newFilenm = FileManager.doFileCopy(context.getRealPath(atcBean.getFilenm()));
			} catch (FileNotFoundException e) {
				continue;
			}
			
			if( newFilenm.equals("") == false ) {
				FileBean atcFileBean = new FileBean();
				atcFileBean.setFileseq(atcBean.getFileseq());
				atcFileBean.setFilenm(saveDir + newFilenm);
				atcFileBean.setOriginfilenm(atcBean.getOriginfilenm());
				atcFileBean.setFilesize(atcBean.getFilesize());
				atcFileBean.setExt(atcBean.getExt());
				
				int addResult = 0;
				if ( mode.equals("VIEW") ) {
					addResult = addReqFormSubFile(conn, sessionId, 0, atcBean.getFormseq(), atcFileBean);
				} else if ( mode.equals("SAVE") ) {
					addResult = addReqFormSubFile(conn, "", reqformno, atcBean.getFormseq(), atcFileBean);
				}
				if ( addResult == 0 ) {
					throw new Exception("첨부실패(저장):DB업데이트");
				}
				result += addResult;
			} else {
				throw new Exception("첨부실패(저장):파일업로드");
			}
		}
		 
		List spFile = null;
		if ( mode.equals("VIEW") ) {
			spFile = getReqFormExamFile(conn, "", reqformno, 0);
		} else if ( mode.equals("SAVE") ) {
			spFile = getReqFormExamFile(conn, sessionId, 0, 0);
		}
		for ( int i = 0; spFile != null && i < spFile.size(); i++ ) {
			SampleBean spBean = (SampleBean)spFile.get(i);
			
			String newFilenm = "";
			try {
				newFilenm = FileManager.doFileCopy(context.getRealPath(spBean.getFilenm()));
			} catch (FileNotFoundException e) {
				continue;
			}
			
			if( newFilenm.equals("") == false ) {
				FileBean spFileBean = new FileBean();
				spFileBean.setFileseq(spBean.getFileseq());
				spFileBean.setFilenm(saveDir + newFilenm);
				spFileBean.setOriginfilenm(spBean.getOriginfilenm());
				spFileBean.setFilesize(spBean.getFilesize());
				spFileBean.setExt(spBean.getExt());
				
				int addResult = 0;
				if ( mode.equals("VIEW") ) {
					addResult = addReqFormExamFile(conn, sessionId, 0, spBean.getFormseq(), spBean.getExamseq(), spFileBean);
				} else if ( mode.equals("SAVE") ) {
					addResult = addReqFormExamFile(conn, "", reqformno, spBean.getFormseq(), spBean.getExamseq(), spFileBean);
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
	 * 저장하기(양식 수정)
	 * 정규테이블에서 임시테이블로 복사한다.
	 */
	public int updateComp(int reqformno, String sessi, ServletContext context, String saveDir) throws Exception{
		//logger.info("양식 수정");
		Connection con = null;
		PreparedStatement pstmt = null;			
						
		try{				
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
						
			//마스터 삭제(양식)
			StringBuffer delteQuery1 = new StringBuffer();
			delteQuery1.append("DELETE FROM REQFORMMST WHERE REQFORMNO = ? ");
		
			pstmt = con.prepareStatement(delteQuery1.toString());
			pstmt.setInt(1, reqformno);
		
			pstmt.executeUpdate();						
			ConnectionManager.close(pstmt);	
			
			//문항 삭제(양식)
			deleteArticleAll(con, reqformno, "");				
			//보기 삭제(양식)
			deleteSampleAll(con, reqformno, "");
			
			//원본첨부파일 삭제
			delReqFormSubExamAllFile(con, "", reqformno, context);
			
			//첨부파일 복사
			copyReqFormSubExamFile(con, sessi, reqformno, context, saveDir, "SAVE");
			
			//양식마스터 복사
			StringBuffer insertQuery1 = new StringBuffer();
			insertQuery1.append("INSERT INTO REQFORMMST ");
			insertQuery1.append("SELECT " + reqformno + ", TITLE, STRDT, ENDDT, COLDEPTCD, ");
			insertQuery1.append("       COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, RANGE, ");
			insertQuery1.append("       IMGPREVIEW, DUPLICHECK, LIMITCOUNT, SANCNEED, BASICTYPE, HEADCONT, TAILCONT, ");
			insertQuery1.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), CRTUSRID, '', '' ");
			insertQuery1.append("FROM REQFORMMST_TEMP ");
			insertQuery1.append("WHERE SESSIONID LIKE ? "); 
			//logger.info(insertQuery1);
			pstmt = con.prepareStatement(insertQuery1.toString());
			pstmt.setString(1, sessi);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 문항 복사
			StringBuffer insertQuery2 = new StringBuffer();
			insertQuery2.append("INSERT INTO REQFORMSUB ");
			insertQuery2.append("SELECT " + reqformno + ", FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, SECURITY, HELPEXP, EXAMTYPE, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
			insertQuery2.append("FROM REQFORMSUB_TEMP ");
			insertQuery2.append("WHERE SESSIONID LIKE ? "); 
			//logger.info(insertQuery2);
			pstmt = con.prepareStatement(insertQuery2.toString());
			pstmt.setString(1, sessi);
			//logger.info("1 : "+sessi);
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 보기 복사
			StringBuffer insertQuery3 = new StringBuffer();
			insertQuery3.append("INSERT INTO REQFORMEXAM ");
			insertQuery3.append("SELECT " + reqformno + ", FORMSEQ, EXAMSEQ, EXAMCONT ");
			insertQuery3.append("FROM REQFORMEXAM_TEMP ");
			insertQuery3.append("WHERE SESSIONID LIKE ? "); 
			//logger.info(insertQuery3);
			pstmt = con.prepareStatement(insertQuery3.toString());
			pstmt.setString(1, sessi);
			//logger.info("1 : "+sessi);
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);			
			
			con.commit();
		} catch (Exception e) {
			try{
				reqformno = -1;
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
		return reqformno;
	}
	
	/**
	 * 기존양식 가져오기에서 선택
	 * 임시 테이블로 복사
	 */
	public int selectUsed(int reqformno, String sessi, ServletContext context, String saveDir) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
				
		try{				
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
						
			//양식마스터 복사
			StringBuffer insertQuery1 = new StringBuffer();
			insertQuery1.append("INSERT INTO REQFORMMST_TEMP ");
			insertQuery1.append("SELECT '" + sessi + "', TITLE, STRDT, ENDDT, '', ");
			insertQuery1.append("       '', '', '', '', SUMMARY, RANGE, IMGPREVIEW, DUPLICHECK, LIMITCOUNT, ");
			insertQuery1.append("       SANCNEED, BASICTYPE, HEADCONT, TAILCONT, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), CRTUSRID, '', '' ");
			insertQuery1.append("FROM REQFORMMST ");
			insertQuery1.append("WHERE REQFORMNO = ? "); 
			
			pstmt = con.prepareStatement(insertQuery1.toString());
			pstmt.setInt(1, reqformno);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 문항 복사
			StringBuffer insertQuery2 = new StringBuffer();
			insertQuery2.append("INSERT INTO REQFORMSUB_TEMP ");
			insertQuery2.append("SELECT '" + sessi + "', FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, SECURITY, HELPEXP, EXAMTYPE, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
			insertQuery2.append("FROM REQFORMSUB ");
			insertQuery2.append("WHERE REQFORMNO = ? "); 
			//logger.info("selectUsed : "+insertQuery2);
			pstmt = con.prepareStatement(insertQuery2.toString());
			pstmt.setInt(1, reqformno);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 보기 복사
			StringBuffer insertQuery3 = new StringBuffer();
			insertQuery3.append("INSERT INTO REQFORMEXAM_TEMP ");
			insertQuery3.append("SELECT '" + sessi + "', FORMSEQ, EXAMSEQ, EXAMCONT ");
			insertQuery3.append("FROM REQFORMEXAM ");
			insertQuery3.append("WHERE REQFORMNO = ? "); 
			
			pstmt = con.prepareStatement(insertQuery3.toString());
			pstmt.setInt(1, reqformno);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//첨부파일 복사
			copyReqFormSubExamFile(con, sessi, reqformno, context, saveDir, "VIEW");
			
			con.commit();
		} catch (Exception e) {
			try{				
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
		return reqformno;
	}
	
	/**
	 * 항목추가 (빈항목 저장)
	 */
	public int insertArticle(int reqformno, String sessi) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result = 0;
		String tb = "";
		String kfield = "";		
		
		if(reqformno == 0){
			tb = "REQFORMSUB_TEMP";
			kfield = "SESSIONID";			
		} else {
			tb = "REQFORMSUB";
			kfield = "REQFORMNO";
		}			
		
		try{
			StringBuffer insertQuery = new StringBuffer();
			
			insertQuery.append("INSERT INTO "+tb+"("+kfield+", FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, ");
			insertQuery.append("                   SECURITY, HELPEXP, EXAMTYPE, EX_FRSQ, EX_EXSQ) ");
			insertQuery.append("VALUES(?, ?, '', 'N', '1',   'N', '', 'N', 0, 0) ");
			//logger.info("insertArticle ["+insertQuery+"]");
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			
			if(reqformno == 0){
				pstmt.setString(1, sessi);
				//logger.info("1 : "+sessi);
			} else {
				pstmt.setInt(1, reqformno);
				//logger.info("1 : "+reqformno);
			}
			
			pstmt.setInt(2, getMaxFormseq(reqformno, sessi));
			
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
	 * 특정 문항 삭제
	 */
	public int deleteArticle (int reqformno, String sessi, int formseq, ServletContext context) throws Exception{
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result =0;
		String tb = "";
		String kfield = "";	
		
		if(reqformno == 0){
			tb = "REQFORMSUB_TEMP";
			kfield = "SESSIONID";
		
		} else {
			tb = "REQFORMSUB";
			kfield = "REQFORMNO";			
		}
		
		try {
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM "+tb+" WHERE "+kfield+" = ? AND FORMSEQ = ? ");
			//logger.info("deleteArticle : "+sql);
			pstmt = con.prepareStatement(sql.toString());		
		    if(reqformno == 0){
		    	pstmt.setString(1, sessi);	
		    } else {
		    	pstmt.setInt(1, reqformno);	
		    }				
		    pstmt.setInt(2, formseq);
			result = pstmt.executeUpdate();
			
			ConnectionManager.close(pstmt);
			
			//보기내용 삭제
			result += deleteSample(con, reqformno, sessi, formseq);
			
			delReqFormExamAllFile(con, sessi, reqformno, formseq, context);
			
			//삭제 후 질문번호 재배치
			String[] updateTable = null;
			String[] updateTable1 = {"REQFORMSUB_TEMP", "REQFORMEXAM_TEMP", "REQFORMSUBFILE_TEMP", "REQFORMEXAMFILE_TEMP"};
			String[] updateTable2 = {"REQFORMSUB", "REQFORMEXAM", "REQFORMSUBFILE", "REQFORMEXAMFILE"};
			if(reqformno == 0){
				updateTable = updateTable1;
				kfield = "SESSIONID";
			} else {
				updateTable = updateTable2;
				kfield = "REQFORMNO";
			}
			
			for ( int i = 0; i < updateTable.length; i++ ) {
				sql.delete(0, sql.capacity());
				sql.append("UPDATE "+updateTable[i]+" SET FORMSEQ = FORMSEQ - 1 WHERE "+kfield+" = ? AND FORMSEQ > ? ");
				
				pstmt = con.prepareStatement(sql.toString());		
			    if(reqformno == 0){
			    	pstmt.setString(1, sessi);	
			    } else {
			    	pstmt.setInt(1, reqformno);	
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
	 * 신청서 전체 첨부파일 삭제
	 * @param conn
	 * @param spBean
	 * @param context
	 * @throws Exception
	 */
	public int delReqFormSubExamAllFile(Connection conn, String sessionId, int reqformno, ServletContext context) throws Exception {
		
		int result = 0;
		
		List actList = getReqFormSubFile(conn, sessionId, reqformno);
		
		for ( int i = 0; actList != null && i < actList.size(); i++ ) {
			ArticleBean atcBean = (ArticleBean)actList.get(i);
		
			if ( atcBean != null ) {
				delReqFormSubFile(conn, sessionId, reqformno, atcBean.getFormseq(), atcBean.getFileseq());
				
				String delFile = atcBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		}
		
		List spList = getReqFormExamFile(conn, sessionId, reqformno, 0);
		
		for ( int i = 0; spList != null && i < spList.size(); i++ ) {
			SampleBean spBean = (SampleBean)spList.get(i);
			
			if ( spBean != null ) {
				delReqFormExamFile(conn, sessionId, reqformno, spBean.getFormseq(), spBean.getExamseq(), spBean.getFileseq());
				
				String delFile = spBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 신청서 보기 첨부파일 삭제
	 * @param conn
	 * @param spBean
	 * @param context
	 * @throws Exception
	 */
	public int delReqFormExamAllFile(Connection conn, String sessionId, int reqformno, int formseq, ServletContext context) throws Exception {
		
		int result = 0;

		ArticleBean atcBean = getReqFormSubFile(conn, sessionId, reqformno, formseq);
		
		if ( atcBean != null ) {
			delReqFormSubFile(conn, sessionId, reqformno, formseq, atcBean.getFileseq());
			
			String delFile = atcBean.getFilenm();
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}
		
		List spList = getReqFormExamFile(conn, sessionId, reqformno, formseq);
		
		for ( int i = 0; spList != null && i < spList.size(); i++ ) {
			SampleBean spBean = (SampleBean)spList.get(i);
			
			if ( spBean != null ) {
				delReqFormExamFile(conn, sessionId, reqformno, formseq, spBean.getExamseq(), spBean.getFileseq());
				
				String delFile = spBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		}

		return result;
	}
	
	/**
	 * 신청서 보기 첨부파일 전체삭제
	 * @param conn
	 * @param spBean
	 * @param context
	 * @throws Exception
	 */
	public int delReqMstAllFile(Connection conn, int reqformno, ServletContext context) throws Exception {
		
		int result = 0;

		List ansList = getReqMstFile(conn, reqformno);
		
		for ( int i = 0; ansList != null && i < ansList.size(); i++ ) {
			ReqMstBean ansBean = (ReqMstBean)ansList.get(i);
			
			if ( ansBean != null ) {
				delReqMstFile(conn, reqformno, ansBean.getReqseq(), ansBean.getFileseq());
				
				String delFile = ansBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		}

		return result;
	}
	
	/**
	 * 신청서 보기 첨부파일 삭제
	 * @param conn
	 * @param spBean
	 * @param context
	 * @throws Exception
	 */
	public int delReqMstFile(Connection conn, int reqformno, int reqseq, ServletContext context) throws Exception {
		
		int result = 0;
		
		ReqMstBean ansBean = getReqMstFile(conn, reqformno, reqseq);
		
		if ( ansBean != null ) {
			delReqMstFile(conn, reqformno, reqseq, ansBean.getFileseq());
			
			String delFile = ansBean.getFilenm();
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}
		
		return result;
	}

	/**
	 * 임시 테이블(양식정보) 삭제	
	 */
	public int deleteTempAll(String sessi, ServletContext context) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;	
						
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//마스터 삭제
			StringBuffer delteQuery = new StringBuffer();
			delteQuery.append("DELETE FROM REQFORMMST_TEMP WHERE SESSIONID LIKE ? ");
			
			pstmt = con.prepareStatement(delteQuery.toString());
			pstmt.setString(1, sessi);			
			
			result = pstmt.executeUpdate();				
			ConnectionManager.close(pstmt);
			
			//문항 삭제
			result += deleteArticleAll(con, 0, sessi);				
			//보기 삭제
			result += deleteSampleAll(con, 0, sessi);			
			
			//첨부파일 삭제
			List reqSubList = getReqFormSubFile(con, sessi, 0);
			
			for ( int i = 0; reqSubList != null && i < reqSubList.size(); i++ ) {
				ArticleBean atcBean = (ArticleBean)reqSubList.get(i);
				delReqFormSubFile(con, sessi, 0, atcBean.getFormseq(), atcBean.getFileseq());
				
				String delSubFile = atcBean.getFilenm();
				if ( delSubFile != null && delSubFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delSubFile));
				}
			}
			
			List reqExamList = getReqFormExamFile(con, sessi, 0, 0);
			
			for ( int j = 0; reqExamList != null && j < reqExamList.size(); j++ ) {
				SampleBean spBean = (SampleBean)reqExamList.get(j);
				delReqFormExamFile(con, sessi, 0, spBean.getFormseq(), spBean.getExamseq(), spBean.getFileseq());
				
				String delExamFile = spBean.getFilenm();
				if ( delExamFile != null && delExamFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delExamFile));
				}
			}
			
			con.commit();
		} catch (Exception e) {
			try{
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
	 * 신청서 양식 관련 모든 테이블 삭제	
	 */
	public int deleteAll(int reqformno, String userid, ServletContext context) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;	
						
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//마스터 삭제(양식)
			StringBuffer delteQuery1 = new StringBuffer();
			delteQuery1.append("DELETE FROM REQFORMMST WHERE REQFORMNO = ? AND CHRGUSRID = ? ");
			
			pstmt = con.prepareStatement(delteQuery1.toString());
			pstmt.setInt(1, reqformno);			
			pstmt.setString(2, userid);
			
			result = pstmt.executeUpdate();						
			ConnectionManager.close(pstmt);			
			if(result == 0){return 0;}   //적용된 건이 없으면 오류 처리..
			
			//문항 삭제(양식)
			result += deleteArticleAll(con, reqformno, "");				
			//보기 삭제(양식)
			result += deleteSampleAll(con, reqformno, "");
			
			//첨부파일 삭제
			result += delReqFormSubExamAllFile(con, "", reqformno, context);
			
			//신청내역삭제
			String[] dtable = {"REQMST","REQSUB", "REQSANC"};			
			
			for(int i=0;i<dtable.length;i++){
				StringBuffer delteQuery2 = new StringBuffer();
				delteQuery2.append("DELETE FROM "+dtable[i]+" WHERE REQFORMNO = ? ");
				
				pstmt = con.prepareStatement(delteQuery2.toString());				
				pstmt.setInt(1, reqformno);			
							
				result += pstmt.executeUpdate();
				ConnectionManager.close(pstmt);
			}
			
			//신청내역첨부파일 삭제
			result += delReqMstAllFile(con, reqformno, context);
			
			con.commit();
		} catch (Exception e) {
			try{
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
	 * 신청취소
	 */
	public int cancelSinchung(int reqformno, int reqseq, String userid, ServletContext context) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;	
						
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//마스터삭제(입력정보)
			StringBuffer delteQuery1 = new StringBuffer();
			delteQuery1.append("DELETE FROM REQMST ");
			delteQuery1.append("WHERE REQFORMNO = ? ");
			delteQuery1.append("  AND REQSEQ = ? ");
			delteQuery1.append("  AND CRTUSRID = ? ");
			
			pstmt = con.prepareStatement(delteQuery1.toString());
			pstmt.setInt(1, reqformno);	
			pstmt.setInt(2, reqseq);
			pstmt.setString(3, userid);
			
			result = pstmt.executeUpdate();						
			ConnectionManager.close(pstmt);			
			if(result == 0){return 0;}   //적용된 건이 없으면 오류 처리..
			
			//추가항목 내용 삭제
			StringBuffer delteQuery2 = new StringBuffer();
			delteQuery2.append("DELETE FROM REQSUB ");
			delteQuery2.append("WHERE REQFORMNO = ? ");
			delteQuery2.append("  AND REQSEQ = ? ");		
			
			pstmt = con.prepareStatement(delteQuery2.toString());
			pstmt.setInt(1, reqformno);	
			pstmt.setInt(2, reqseq);	
			
			result += pstmt.executeUpdate();						
			ConnectionManager.close(pstmt);	
			
			//결재내용 삭제
			StringBuffer delteQuery3 = new StringBuffer();
			delteQuery3.append("DELETE FROM REQSANC ");
			delteQuery3.append("WHERE REQFORMNO = ? ");
			delteQuery3.append("  AND REQSEQ = ? ");		
			
			pstmt = con.prepareStatement(delteQuery3.toString());
			pstmt.setInt(1, reqformno);	
			pstmt.setInt(2, reqseq);	
			
			result += pstmt.executeUpdate();						
			ConnectionManager.close(pstmt);	
			
			//첨부파일삭제
			result += delReqMstFile(con, reqformno, reqseq, context);
			
			con.commit();
		} catch (Exception e) {
			try{
				result = 0;
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
	 * 신청취소
	 */
	public int cancelSanc(int reqformno, int reqseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;	
						
		try{			
			//결재내용 삭제
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM REQSANC ");
			sql.append("WHERE REQFORMNO = ? ");
			sql.append("  AND REQSEQ = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			
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
	 * 임시 마스터에 값이 있는지 확인(Temp)
	 */
	public boolean existCheck_temp(String sessi) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) FROM REQFORMMST_TEMP WHERE SESSIONID LIKE ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, sessi);			
						
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
	 * 신청 신청내역 저장
	 */
	public int insertReqData(ReqMstBean mbean, String sessi, ServletContext context, String saveDir) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;					
		int cnt = 0;
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//마스터 저장
			StringBuffer insertQuery1 = new StringBuffer();
			insertQuery1.append("INSERT INTO REQMST(REQFORMNO, REQSEQ, PRESENTNM, PRESENTID, PRESENTSN, PRESENTBD,");
			insertQuery1.append("                   POSITION,  DUTY,   ZIP,       ADDR1,     ADDR2, ");
			insertQuery1.append("                   EMAIL,     TEL,    CEL,       FAX,       STATE, ");
			insertQuery1.append("                   CRTDT,     CRTUSRID) ");
			insertQuery1.append("VALUES(?,?,?,?,?,?,  ?,?,?,?,?,   ?,?,?,?,?,  TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'),?)");
			//logger.info(insertQuery1.toString());
			int reqformno = mbean.getReqformno();
			int reqseq = getMaxReqseq(mbean.getReqformno());
			
			pstmt = con.prepareStatement(insertQuery1.toString());		
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			pstmt.setString(3, mbean.getPresentnm());
			pstmt.setString(4, mbean.getPresentid());
			pstmt.setString(5, mbean.getPresentsn());
			pstmt.setString(6, mbean.getPresentbd());
			pstmt.setString(7, mbean.getPosition());
			pstmt.setString(8, mbean.getDuty());
			//logger.info("reqformno["+reqformno+"]");
			//logger.info("reqseq["+reqseq+"]");
			//logger.info("Presentnm["+mbean.getPresentnm()+"]");
			//logger.info("Presentid["+mbean.getPresentid()+"]");
			//logger.info("Presentsn["+mbean.getPresentsn()+"]");
			//logger.info("Presentbd["+mbean.getPresentbd()+"]");
			//logger.info("Presentbd["+mbean.getPresentbd()+"]");
			//logger.info("Position["+mbean.getPosition()+"]");
			//logger.info("Duty["+mbean.getDuty()+"]");
			
			//주소입력 처리
			String addr = mbean.getAddr1();
			String addr_temp = "";
			String zip_temp = "";
			if(addr != null && addr.length()>9){
				zip_temp = addr.substring(1,7);
				addr_temp = addr.substring(9);
			}
			pstmt.setString(9, zip_temp);
			pstmt.setString(10, addr_temp);
			pstmt.setString(11, mbean.getAddr2());
			pstmt.setString(12, mbean.getEmail());
			pstmt.setString(13, mbean.getTel());
			pstmt.setString(14, mbean.getCel());
			pstmt.setString(15, mbean.getFax());
			pstmt.setString(16, "02");    //신청중
			
			pstmt.setString(17, mbean.getCrtusrid());
			//logger.info("zip_temp["+zip_temp+"]");
			//logger.info("addr_temp["+addr_temp+"]");
			//logger.info("Addr2["+mbean.getAddr2()+"]");
			//logger.info("Email["+mbean.getEmail()+"]");
			//logger.info("Tel["+mbean.getTel()+"]");
			//logger.info("Cel["+mbean.getCel()+"]");
			//logger.info("Fax["+mbean.getFax()+"]");
			
			cnt = pstmt.executeUpdate();		
			ConnectionManager.close(pstmt);			
			
			//추가문항 저장
			if(mbean.getAnscontList() != null){
				
				StringBuffer insertQuery2 = new StringBuffer();
				insertQuery2.append("INSERT INTO REQSUB(REQFORMNO, REQSEQ, FORMSEQ, ANSCONT, OTHER) ");
				insertQuery2.append("VALUES(?,?,?,?,?)");
				//logger.info(insertQuery2.toString());
				pstmt = con.prepareStatement(insertQuery2.toString());
				
				for(int i=0;i<mbean.getAnscontList().size();i++){
					ReqSubBean sbean = (ReqSubBean)mbean.getAnscontList().get(i);
					
					pstmt.clearParameters();
					pstmt.setInt(1, reqformno);
					pstmt.setInt(2, reqseq);
					pstmt.setInt(3, sbean.getFormseq());
					pstmt.setString(4, sbean.getAnscont());
					pstmt.setString(5, sbean.getOther());
					
					cnt += pstmt.executeUpdate();	
				}		
				ConnectionManager.close(pstmt);	
			}

			mbean.setReqseq(reqseq);
			//신청내역 첨부파일 추가
			cnt += addReqMstFile(con, mbean, context, saveDir);
			
			if ( cnt > 0 ) {
		    	 result = reqseq;
		    }
			
			con.commit();
		} catch (Exception e) {
			try{
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
	 * 신청 신청내역 수정
	 */
	public int updateReqData(ReqMstBean mbean, ServletContext context, String saveDir) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;					
				
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//마스터 수정
			StringBuffer updateQuery1 = new StringBuffer();
			updateQuery1.append("UPDATE REQMST SET PRESENTNM = ?, PRESENTID = ?, PRESENTSN = ?, PRESENTBD = ?, POSITION = ?,  DUTY = ?, ");
			updateQuery1.append("                  ZIP = ?,       ADDR1 = ?,     ADDR2 = ?,     EMAIL = ?,     TEL = ?, ");
			updateQuery1.append("                  CEL = ?,       FAX = ?,       UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? ");		
			updateQuery1.append("WHERE REQFORMNO = ? ");
			updateQuery1.append("  AND REQSEQ = ? ");			
			
			int reqformno = mbean.getReqformno();
			int reqseq = mbean.getReqseq();
			
			pstmt = con.prepareStatement(updateQuery1.toString());		
			
			pstmt.setString(1, mbean.getPresentnm());
			pstmt.setString(2, mbean.getPresentid());
			pstmt.setString(3, mbean.getPresentsn());
			pstmt.setString(4, mbean.getPresentbd());
			pstmt.setString(5, mbean.getPosition());
			pstmt.setString(6, mbean.getDuty());
			
			//주소입력 처리
			String addr = mbean.getAddr1();
			String addr_temp = "";
			String zip_temp = "";
			if(addr != null && addr.length()>9){
				zip_temp = addr.substring(1,7);
				addr_temp = addr.substring(9);
			}
			pstmt.setString(7, zip_temp);
			pstmt.setString(8, addr_temp);
		
			pstmt.setString(9, mbean.getAddr2());
			pstmt.setString(10, mbean.getEmail());
			pstmt.setString(11, mbean.getTel());
			pstmt.setString(12, mbean.getCel());
			pstmt.setString(13, mbean.getFax());					
			pstmt.setString(14, mbean.getUptusrid());			
			pstmt.setInt(15, reqformno);
			pstmt.setInt(16, reqseq);
						
			result = pstmt.executeUpdate();		
			ConnectionManager.close(pstmt);		
			
			//추가문항 수정
			if(mbean.getAnscontList() != null){
				
				StringBuffer updateQuery2 = new StringBuffer();
				updateQuery2.append("UPDATE REQSUB SET ANSCONT = ?, OTHER = ? ");
				updateQuery2.append("WHERE REQFORMNO = ? ");
				updateQuery2.append("  AND REQSEQ = ? ");
				updateQuery2.append("  AND FORMSEQ = ? ");				
				
				pstmt = con.prepareStatement(updateQuery2.toString());
				
				for(int i=0;i<mbean.getAnscontList().size();i++){
					ReqSubBean sbean = (ReqSubBean)mbean.getAnscontList().get(i);
					
					pstmt.clearParameters();					
					pstmt.setString(1, sbean.getAnscont());
					pstmt.setString(2, sbean.getOther());
					pstmt.setInt(3, sbean.getReqformno());
					pstmt.setInt(4, sbean.getReqseq());
					pstmt.setInt(5, sbean.getFormseq());
					
					result += pstmt.executeUpdate();	
				}		
				ConnectionManager.close(pstmt);	
			}
			
			if ( mbean.getAttachFileYN().equals("N") == true ||
					(mbean.getAttachFile() != null && mbean.getAttachFile().getFileName().trim().equals("") == false) ) {
				//신청내역 첨부파일 삭제
				result += delReqMstFile(con, reqformno, reqseq, context);
			}
			if (  mbean.getAttachFileYN().equals("N") == false &&
					mbean.getAttachFile() != null && mbean.getAttachFile().getFileName().trim().equals("") == false ) {
				//신청내역 첨부파일 추가
				result += addReqMstFile(con, mbean, context, saveDir);
			}
			
			con.commit();
		} catch (Exception e) {
			try{
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
	 * 신청내역 첨부파일 추가
	 * @param con
	 * @param rmBean
	 * @param context
	 * @param saveDir
	 * @return
	 */
	public int addReqMstFile(Connection con, ReqMstBean rmBean, ServletContext context, String saveDir) throws Exception {
		//logger.info("신청내역 첨부파일 추가");
		int result = 0;
		File saveFolder = new File(context.getRealPath(saveDir));
		if(!saveFolder.exists()) saveFolder.mkdirs();
		//logger.info("saveDir["+saveDir+"]");
		
		ReqMstBean bean = getReqMstFile(con, rmBean.getReqformno(), rmBean.getReqseq());

		//logger.info("AttachFile("+rmBean.getAttachFile()+")");
		//logger.info("AttachFile.getFileName("+rmBean.getAttachFile().getFileName()+")");
		if ( bean != null && (rmBean.getAttachFile() != null && rmBean.getAttachFile().getFileName().equals("") == false) ) {
			delReqMstFile(con, bean.getReqformno(), bean.getReqseq(), bean.getFileseq());
			
			String delFile = bean.getFilenm();
			//logger.info("delFile["+delFile+"]");
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}

		if ( rmBean.getAttachFile() != null && rmBean.getAttachFile().getFileName().equals("") == false ) {
			FileBean fileBean = FileManager.doFileUpload(rmBean.getAttachFile(), context, saveDir);
			
			if(fileBean != null) {
				int addResult = 0;
				fileBean.setFileseq(1);
				addResult = addReqAnsFile(con, rmBean.getReqformno(), rmBean.getReqseq(), fileBean);
				if ( addResult == 0 ) {
					throw new Exception("첨부실패:DB업데이트");
				}
				result = addResult;
			} else {
				throw new Exception("첨부실패:파일업로드");
			}
		}
		
		return result;
	}
	
	/**
	 * 접수처리
	 * gbn: 일괄접수처리토글(all), 접수완료(1), 접수보류(2), 미결재(3), 신청중(4)
	 */
	public int procJupsu(String gbn, int reqformno, String reqseq) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result = 0;
				
		try{
			StringBuffer updateQuery = new StringBuffer();
			
			updateQuery.append("UPDATE REQMST SET STATE = ? ");
			updateQuery.append("WHERE REQFORMNO = ? ");
			updateQuery.append("  AND REQSEQ IN ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
			
			if("1".equals(gbn)){
				pstmt.setString(1, "03");
			} else if("2".equals(gbn)){
				pstmt.setString(1, "04");
			} else if("3".equals(gbn)){
				pstmt.setString(1, "01");
			} else if("99".equals(gbn)){
				pstmt.setString(1, "99");
			} else {
				pstmt.setString(1, "02");
			}
			
			pstmt.setInt(2, reqformno);
			pstmt.setString(3, reqseq);
			
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
	 * 일괄접수처리 토글 : 전체접수완료가 아니면 전체접수완료처리, 전체접수완료처리면 전체보류처리
	 */
	public int procTotalJupsu(int reqformno) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result = 0;
				
		try{
			StringBuffer updateQuery = new StringBuffer();
			
			updateQuery.append("UPDATE REQMST \n");
			updateQuery.append("SET STATE = (SELECT DECODE(SUM(DECODE(STATE, '03', 1, 0)), COUNT(*), '04', '03') \n");
			updateQuery.append("             FROM REQMST \n");
			updateQuery.append("             WHERE REQFORMNO = ?) \n");
			updateQuery.append("WHERE REQFORMNO = ? \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
			
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqformno);				
			
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
	 * 선택접수처리 토글 : 전체접수완료가 아니면 전체접수완료처리, 전체접수완료처리면 전체보류처리
	 */
	public int procSelectJupsu(int reqformno, String reqseq) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result = 0;
		
		try{
			StringBuffer updateQuery = new StringBuffer();
			
			updateQuery.append("UPDATE REQMST \n");
			updateQuery.append("SET STATE = DECODE(STATE, '02', '03', DECODE(STATE, '04', '03', '04')) \n");
			updateQuery.append("WHERE REQFORMNO = ? \n");
			updateQuery.append("AND REQSEQ IN (" + reqseq + ") \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
			
			pstmt.setInt(1, reqformno);	
			
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
	 * 결재선 승인/검토 데이터 가져오기
	 * gubun: 검토(1),  승인(2)
	 */
	public List approvalInfo(String gubun, int reqformno, int reqseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List apprInfoList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT GUBUN, SANCUSRID, SANCUSRNM, SANCYN, SANCDT, SUBMITDT ");
			selectQuery.append("FROM REQSANC ");
			selectQuery.append(" WHERE REQFORMNO = ? ");	
			selectQuery.append("   AND REQSEQ = ? ");
			selectQuery.append("   AND GUBUN = ? ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, reqformno);	
			pstmt.setInt(2, reqseq);
			pstmt.setString(3, gubun);
									
			rs = pstmt.executeQuery();
			 
			apprInfoList = new ArrayList();
			
			while(rs.next()) {
				commapprovalBean bean = new commapprovalBean();
				
				bean.setGubun(rs.getString("GUBUN"));
				bean.setUserId(rs.getString("SANCUSRID"));
				bean.setUserName(rs.getString("SANCUSRNM"));
				bean.setSancYn(rs.getString("SANCYN"));
				bean.setSancdt(rs.getString("SANCDT"));
				bean.setSubmitdt(rs.getString("SUBMITDT"));
				
				apprInfoList.add(bean);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return apprInfoList;
	}
	
	/**
	 * 신청서 결재
	 */
	public int doSanc (int reqformno, int reqseq, String userid) throws Exception{
		Connection con = null;        
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result =0;
				
		try {
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			StringBuffer updateQuery1 = new StringBuffer();
	
			updateQuery1.append("UPDATE REQSANC SET SANCYN = '1', SANCDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ");
			updateQuery1.append("                   SUBMITDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')	");
			updateQuery1.append("WHERE REQFORMNO = ? ");
			updateQuery1.append("  AND REQSEQ = ? ");
			updateQuery1.append("  AND SANCUSRID = ? ");
			
			pstmt = con.prepareStatement(updateQuery1.toString());		
		    pstmt.setInt(1, reqformno);		      
		    pstmt.setInt(2, reqseq);
		    pstmt.setString(3, userid);
			
			result = pstmt.executeUpdate();	
			ConnectionManager.close(pstmt);
			if(result == 0){return 0;}   //적용된 건이 없으면 오류 처리..
			
			//마지막 결재자인지 확인후 마스터 상태변경
			StringBuffer selectQuery1 = new StringBuffer();
			
			selectQuery1.append("SELECT COUNT(REQFORMNO) ");
			selectQuery1.append("FROM REQSANC ");
			selectQuery1.append("WHERE REQFORMNO = ? ");
			selectQuery1.append("  AND REQSEQ = ? ");
			selectQuery1.append("  AND SANCUSRID <> ? ");
			selectQuery1.append("  AND SANCYN = '0' ");
			
			pstmt = con.prepareStatement(selectQuery1.toString());	
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			pstmt.setString(3, userid);
			
			rs = pstmt.executeQuery();
			
			int notSanc = 0;
			if(rs.next()){
				notSanc = rs.getInt(1);
			}
			ConnectionManager.close(pstmt, rs);
			
			//결재자 본인을 제외하고 미결재 건이 없으면 마스터의 상태를 변경한다.
			if(notSanc == 0){
				StringBuffer updateQuery2 = new StringBuffer();
				
				updateQuery2.append("UPDATE REQMST SET STATE = '02' ");				
				updateQuery2.append("WHERE REQFORMNO = ? ");
				updateQuery2.append("  AND REQSEQ = ? ");			
				
				pstmt = con.prepareStatement(updateQuery2.toString());		
			    pstmt.setInt(1, reqformno);		      
			    pstmt.setInt(2, reqseq);			 
				
				result = pstmt.executeUpdate();			
				ConnectionManager.close(pstmt);
			}
			
			con.commit();
		} catch (Exception e) {
			try{	
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
	 * 결재된 건이 있는지 확인
	 */
	public boolean existSanc(int reqformno, int reqseq) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) ");
			selectQuery.append("FROM REQSANC ");
			selectQuery.append("WHERE REQFORMNO = ? ");
			selectQuery.append("  AND REQSEQ = ? ");
			selectQuery.append("  AND SANCYN = '1' ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, reqformno);	
			pstmt.setInt(2, reqseq);
						
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
	 * 신청서 마감 	
	 */
	public int docClose(int reqformno, String userid) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result = 0;
				
		try{
			//신청서를 마감할 경우 하루전 날짜로 셋팅한다.
			StringBuffer updateQuery = new StringBuffer();
			
			updateQuery.append("UPDATE REQFORMMST SET ENDDT = TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD') ");
			updateQuery.append("WHERE REQFORMNO = ? ");
			updateQuery.append("  AND CHRGUSRID = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
			
			pstmt.setInt(1, reqformno);
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
	 * 신청마스터에서 신청 일련번호 가져오기 (REQSEQ)
	 */
	public int getMaxReqseq(int reqformno) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int maxseq = 0;
				
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT NVL(MAX(REQSEQ),0)+1 FROM REQMST WHERE REQFORMNO = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, reqformno);
								
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				maxseq = rs.getInt(1);
			}		
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return maxseq;
	}
	
	/**
	 * 최종결재자 성명 가져오기
	 */
	private String lastSancName(int reqformno, int reqseq) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
				
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT SANCUSRNM \n");
			selectQuery.append("FROM REQSANC \n");
			selectQuery.append("WHERE REQFORMNO = ? \n");
			selectQuery.append("AND REQSEQ = ? \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString("SANCUSRNM");
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
	 * 마스터 INSERT(TEMP)
	 */
	private int insertMst(Connection con, FrmBean fbean) throws Exception{
		PreparedStatement pstmt = null;	
		int result = 0;	
		
		String summary = null;
		if(fbean.getSummary() != null) {
			summary = fbean.getSummary().replaceAll("'", "''");
		} else {
			summary = "";
		}
		summary = new String(summary.getBytes("x-windows-949"), "x-windows-949");
		StringBuffer insertQuery1 = new StringBuffer();
		insertQuery1.append("INSERT INTO REQFORMMST_TEMP(SESSIONID, TITLE,     STRDT,     ENDDT,    COLDEPTCD, ");
		insertQuery1.append("                            COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY,  RANGE, ");
		insertQuery1.append("                            IMGPREVIEW, DUPLICHECK, LIMITCOUNT, SANCNEED,  BASICTYPE, ");
		insertQuery1.append("                            HEADCONT,  TAILCONT, CRTDT, CRTUSRID) ");
		insertQuery1.append("VALUES(?,?,?,?,?,   ?,?,?,?,'"+summary+"',?,   ?,?,?,?,?,?,?,TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'),     ?) ");
		//logger.info(insertQuery1); 
		pstmt = con.prepareStatement(insertQuery1.toString());
		pstmt.setString(1, fbean.getSessi());
		pstmt.setString(2, new String(fbean.getTitle().getBytes("x-windows-949"), "x-windows-949"));
		pstmt.setString(3, fbean.getStrdt());
		pstmt.setString(4, fbean.getEnddt());
		pstmt.setString(5, fbean.getColdeptcd());
		pstmt.setString(6, fbean.getColdeptnm());
		pstmt.setString(7, fbean.getColtel());
		pstmt.setString(8, fbean.getChrgusrid());
		pstmt.setString(9, fbean.getChrgusrnm());
		pstmt.setString(10, ( "1".equals(fbean.getRange()) ) ? fbean.getRange() : fbean.getRangedetail());
		pstmt.setString(11, fbean.getImgpreview());
		pstmt.setString(12, fbean.getDuplicheck());
		pstmt.setInt(13, fbean.getLimitcount());
		pstmt.setString(14, fbean.getSancneed());
		pstmt.setString(15, fbean.getBasictype());
		pstmt.setString(16, new String(fbean.getHeadcont().getBytes("x-windows-949"), "x-windows-949"));
		pstmt.setString(17, new String(fbean.getTailcont().getBytes("x-windows-949"), "x-windows-949"));
		pstmt.setString(18, fbean.getCrtusrid());
		//logger.info("Sessi["+fbean.getSessi()+"]");
		//logger.info("Title["+fbean.getTitle()+"]");
		//logger.info("summary["+summary+"]");
		//logger.info("Strdt["+fbean.getStrdt()+"]");
		//logger.info("Enddt["+fbean.getEnddt()+"]");
		//logger.info("Coldeptcd["+fbean.getColdeptcd()+"]");
		//logger.info("Coldeptnm["+fbean.getColdeptnm()+"]");
		//logger.info("Coltel["+fbean.getColtel()+"]");
		//logger.info("Chrgusrid["+fbean.getChrgusrid()+"]");
		//logger.info("Range["+(( "1".equals(fbean.getRange()) ) ? fbean.getRange() : fbean.getRangedetail())+"]");
		//logger.info("Imgpreview["+fbean.getImgpreview()+"]");
		//logger.info("Duplicheck["+fbean.getDuplicheck()+"]");
		//logger.info("Limitcount["+fbean.getLimitcount()+"]");
		//logger.info("Sancneed["+fbean.getSancneed()+"]");
		//logger.info("Basictype["+fbean.getBasictype()+"]");
		//logger.info("Headcont["+fbean.getHeadcont()+"]");
		//logger.info("Tailcont["+fbean.getTailcont()+"]");
		//logger.info("Crtusrid["+fbean.getCrtusrid()+"]");
		
		result = pstmt.executeUpdate();
		
		
		String title = fbean.getTitle(); // 테스트 
		String [] charSet = {"utf-8","euc-kr","ksc5601","iso-8859-1","x-windows-949"};
		  
		//logger.info("<==============================character set test start ======================================>");
		/*for (int i=0; i<charSet.length; i++) {
		 for (int j=0; j<charSet.length; j++) {
		  try {
			  logger.info("[" + charSet[i] +"," + charSet[j] +"] = " + new String(title.getBytes(charSet[i]), charSet[j]));
			  pstmt.setString(1, charSet[i]+"_"+charSet[j]);
			  pstmt.setString(2, new String(title.getBytes(charSet[i]), charSet[j]));
			  pstmt.executeUpdate();
		  } catch (UnsupportedEncodingException e) {
		   e.printStackTrace();
		  }
		 } 
		}*/
		//logger.info("<==============================character set test end ======================================>");

		ConnectionManager.close(pstmt);
		
		for(int i=0;i<fbean.getAcnt();i++){
			StringBuffer insertQuery2 = new StringBuffer();
			
			insertQuery2.append("INSERT INTO REQFORMSUB_TEMP(SESSIONID, FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, ");
			insertQuery2.append("                            SECURITY, HELPEXP, EXAMTYPE, EX_FRSQ, EX_EXSQ) ");
			insertQuery2.append("VALUES(?, ?, '', 'Y', '1',   'N', '', 'N', 0, 0) ");
			
			pstmt = con.prepareStatement(insertQuery2.toString());
			pstmt.setString(1, fbean.getSessi());
			pstmt.setInt(2, i+1);
			//logger.info("insertMst["+insertQuery2+"]");
			//logger.info("1["+fbean.getSessi()+"]");
			//logger.info("2["+(i+1)+"]");
			result += pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 문항 저장
	 * gbn : 1(REQFORMMST_TEMP), 2(REQFORMMST)
	 */
	private int insertArticleAll(Connection con, List articeList , String gbn) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		String tb = "";
		String kfield = "";
		
		if("TEMP".equals(gbn)){
			tb = "REQFORMSUB_TEMP";
			kfield = "SESSIONID";
		} else {
			tb = "REQFORMSUB";
			kfield = "REQFORMNO";
		}	
		
		if(articeList != null){
			for(int i=0;i<articeList.size();i++){
				ArticleBean abean = (ArticleBean)articeList.get(i);
				
				StringBuffer insertQuery = new StringBuffer();
				insertQuery.append("INSERT INTO "+tb+" ("+kfield+", FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, ");
				insertQuery.append("                    SECURITY, HELPEXP, EXAMTYPE, EX_FRSQ, EX_EXSQ) ");
				insertQuery.append("VALUES(?,?,?,?,?,  ?,?,?, NVL(?, 0), NVL(?, 0) )");
				//logger.info(insertQuery.toString());
				pstmt = con.prepareStatement(insertQuery.toString());		
				
				if("TEMP".equals(gbn)){
					pstmt.setString(1, abean.getSessi());
					//logger.info("Sessi["+abean.getSessi()+"]");
				} else {
					pstmt.setInt(1, abean.getReqformno());
					//logger.info("Reqformno["+abean.getReqformno()+"]");
				}
				
			/*	if (abean.getFormtype().equals("1") || abean.getFormtype().equals("2")) {
					abean.setRequire("N");
				}*/
				
				pstmt.setInt(2, abean.getFormseq());
				pstmt.setString(3, new String(abean.getFormtitle().getBytes("x-windows-949"), "x-windows-949")); 
				pstmt.setString(4, abean.getRequire());
				pstmt.setString(5, abean.getFormtype());
				pstmt.setString(6, abean.getSecurity());
				pstmt.setString(7, abean.getHelpexp());
				pstmt.setString(8, abean.getExamtype());
				pstmt.setInt(9, abean.getEx_frsq());			//2018.2.28 동적 문항 추가(연계된 문항에 보기와 연계)
				pstmt.setString(10, abean.getEx_exsq());		//2018.2.28 동적 문항 추가(연계된 문항에 보기와 연계)
				//logger.info("Formseq["+abean.getFormseq()+"]");
				//logger.info("Formtitle["+abean.getFormtitle()+"]");
				//logger.info("Require["+abean.getRequire()+"]");
				//logger.info("Formtype["+abean.getFormtype()+"]");
				//logger.info("Security["+abean.getSecurity()+"]");
				//logger.info("Helpexp["+abean.getHelpexp()+"]");
				//logger.info("Examtype["+abean.getExamtype()+"]");
				//logger.info("getEx_exsq["+abean.getEx_exsq()+"] getEx_exsq["+abean.getEx_exsq()+"]");
				
				if ( ("1".equals(abean.getFormtype()) || "2".equals(abean.getFormtype())) && abean.getSampleList() != null ) {
					int cnt = 0;
					for ( ; cnt < abean.getSampleList().size(); cnt++ ) {
						SampleBean examBean = (SampleBean)abean.getSampleList().get(cnt);
						if ( examBean != null && !"".equals(Utils.nullToEmptyString(examBean.getExamcont())) ) break;
					}
					if ( cnt == abean.getSampleList().size() ) {
						pstmt.setString(5, "3");
						pstmt.setString(6, "N");
						pstmt.setString(7, "");
						pstmt.setString(8, "N");
					}
				}
				
				result += pstmt.executeUpdate();				
				ConnectionManager.close(pstmt);
				
				//보기 저장
				result += insertSampleAll(con, abean.getSampleList(), abean.getFormseq(), gbn);
			}
		}
		
		return result;
	}

	/**
	 * 보기 저장
	 * gbn : TEMP(REQFORMMST_TEMP), NULL(REQFORMMST)
	 */
	private int insertSampleAll(Connection con, List sampleList, int formseq, String gbn) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		String tb = "";
		String kfield = "";
		
		if("TEMP".equals(gbn)){
			tb = "REQFORMEXAM_TEMP";
			kfield = "SESSIONID";
		} else {
			tb = "REQFORMEXAM";
			kfield = "REQFORMNO";
		}		
		
		if(sampleList != null){
			for(int i=0;i<sampleList.size();i++){
				SampleBean sbean = (SampleBean)sampleList.get(i);
				
				if ( "".equals(Utils.nullToEmptyString(sbean.getExamcont())) ) continue;
				
				StringBuffer insertQuery = new StringBuffer();
				insertQuery.append("INSERT INTO "+tb+" ("+kfield+", FORMSEQ, EXAMSEQ, EXAMCONT) ");
				insertQuery.append("VALUES(?,?,?,?)");
				
				pstmt = con.prepareStatement(insertQuery.toString());	
				
				if("TEMP".equals(gbn)){
					pstmt.setString(1, sbean.getSessi());
				} else {
					pstmt.setInt(1, sbean.getReqformno());
				}
				
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, i+1);
				pstmt.setString(4, new String(sbean.getExamcont().getBytes("x-windows-949"), "x-windows-949"));		
				
				result += pstmt.executeUpdate();				
				ConnectionManager.close(pstmt);
			}
		}
		
		return result;
	}
	
	/**
	 * 마스터 UPDATE
	 * gbn : TEMP(REQFORMMST_TEMP), NULL(REQFORMMST)
	 */
	private int updateMst(Connection con, FrmBean fbean, String gbn) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;		
		String tb = "";
		String kfield = "";
		
		if("TEMP".equals(gbn)){
			tb = "REQFORMMST_TEMP";
			kfield = "SESSIONID";
		} else {
			tb = "REQFORMMST";
			kfield = "REQFORMNO";
		}
		
		String summary = null;
		
		if(fbean.getSummary() != null) {
			summary = fbean.getSummary().replaceAll("'", "''");
		} else { 
			summary = "";
		}
		summary = new String(summary.getBytes("x-windows-949"), "x-windows-949");
		StringBuffer updateQuery = new StringBuffer();
	 	updateQuery.append("UPDATE "+tb+" SET TITLE = ?,     STRDT = ?,     ENDDT = ?,    COLDEPTCD = ?, COLDEPTNM = ?, COLDEPTTEL = ?, ");
	 	updateQuery.append("				  CHRGUSRID = ?, CHRGUSRNM = ?, SUMMARY = '" + summary + "',  RANGE = ?, IMGPREVIEW = ?, DUPLICHECK = ?, LIMITCOUNT = ?, ");
	 	updateQuery.append("                  SANCNEED = ?,  BASICTYPE = ?, HEADCONT = ?,  TAILCONT = ?, UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? ");	 	
	 	updateQuery.append("WHERE "+kfield+" = ? ");
	 	pstmt = con.prepareStatement(updateQuery.toString());						
		pstmt.setString(1, new String(fbean.getTitle().getBytes("x-windows-949"), "x-windows-949"));
		pstmt.setString(2, fbean.getStrdt());
		pstmt.setString(3, fbean.getEnddt());
		pstmt.setString(4, fbean.getColdeptcd());
		pstmt.setString(5, fbean.getColdeptnm());
		pstmt.setString(6, fbean.getColtel());
		pstmt.setString(7, fbean.getChrgusrid());
		pstmt.setString(8, fbean.getChrgusrnm());
		pstmt.setString(9, ( "1".equals(fbean.getRange()) ) ? fbean.getRange() : fbean.getRangedetail());
		pstmt.setString(10, fbean.getImgpreview());
		pstmt.setString(11, fbean.getDuplicheck());
		pstmt.setInt(12, fbean.getLimitcount());
		pstmt.setString(13, fbean.getSancneed());
		pstmt.setString(14, fbean.getBasictype());
		pstmt.setString(15, new String(fbean.getHeadcont().getBytes("x-windows-949"), "x-windows-949"));
		pstmt.setString(16, new String(fbean.getTailcont().getBytes("x-windows-949"), "x-windows-949"));
		pstmt.setString(17, fbean.getUptusrid());
		
		if("TEMP".equals(gbn)){
			pstmt.setString(18, fbean.getSessi());
		} else {
			pstmt.setInt(19, fbean.getReqformno());
		}	
		
		result = pstmt.executeUpdate();
		ConnectionManager.close(pstmt);
		
		return result;
	}
	
	/**
	 * 전체 문항삭제
	 */
	private int deleteArticleAll(Connection con, int reqformno, String sessi) throws Exception {
		PreparedStatement pstmt = null;		
		int result = 0;	
		String tb = "";
		String kfield = "";
		
		if(reqformno == 0){
			tb = "REQFORMSUB_TEMP";
			kfield = "SESSIONID";
		} else {
			tb = "REQFORMSUB";
			kfield = "REQFORMNO";
		}	
		
		StringBuffer delteQuery = new StringBuffer();
		delteQuery.append("DELETE FROM "+tb+" WHERE "+kfield+" LIKE ? ");
	 
		pstmt = con.prepareStatement(delteQuery.toString());
		
		if(reqformno == 0){
			pstmt.setString(1, sessi);
		} else {
			pstmt.setInt(1, reqformno);
		}	
		
		result = pstmt.executeUpdate();	
		ConnectionManager.close(pstmt);
		
		return result;
	}
	
	/**
	 * 전체 보기 삭제
	 */
	private int deleteSampleAll(Connection con, int reqformno, String sessi) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		String tb = "";
		String kfield = "";
		
		if(reqformno == 0){	
			tb = "REQFORMEXAM_TEMP";
			kfield = "SESSIONID";
		} else {
			tb = "REQFORMEXAM";
			kfield = "REQFORMNO";
		}	
		
		StringBuffer delteQuery = new StringBuffer();
		delteQuery.append("DELETE FROM "+tb+" WHERE "+kfield+" LIKE ? ");
	 
		pstmt = con.prepareStatement(delteQuery.toString());
		
		if(reqformno == 0){	
			pstmt.setString(1, sessi);
		} else {
			pstmt.setInt(1, reqformno);
		}	
		
		result = pstmt.executeUpdate();			
		ConnectionManager.close(pstmt);
		
		return result;
	}
	
	/**
	 * 특정 항목의 보기삭제
	 */
	private int deleteSample(Connection con, int reqformno, String sessi, int formseq) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		String tb = "";
		String kfield = "";
		
		if(reqformno == 0){	
			tb = "REQFORMEXAM_TEMP";
			kfield = "SESSIONID";
		} else {
			tb = "REQFORMEXAM";
			kfield = "REQFORMNO";
		}	
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM "+tb+" WHERE "+kfield+" = ? AND FORMSEQ = ? ");
	 
		pstmt = con.prepareStatement(sql.toString());
		if(reqformno == 0){	
			pstmt.setString(1, sessi);
		} else {
			pstmt.setInt(1, reqformno);
		}	
		pstmt.setInt(2, formseq);
		result = pstmt.executeUpdate();
		
		ConnectionManager.close(pstmt);		
		
		return result;
	}
	
	/**
	 * 항목 테이블(REQFORMSUB 또는 REQFORMSUB_TEMP) 의 FORMSEQ MAX값 가져오기	
	 */
	public int getMaxFormseq(int reqformno, String sessi) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int maxseq = 0;
		String tb = "";
		String kfield = "";
		
		if(reqformno == 0){
			tb = "REQFORMSUB_TEMP";
			kfield = "SESSIONID";
		} else {
			tb = "REQFORMSUB";
			kfield = "REQFORMNO";
		}	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT NVL(MAX(FORMSEQ),0)+1 FROM "+tb+" WHERE "+kfield+" = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(reqformno == 0){
				pstmt.setString(1, sessi);	
			} else {
				pstmt.setInt(1, reqformno);
			}
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				maxseq = rs.getInt(1);
			}		
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return maxseq;
	}
	
	/**
	 * 항목 테이블(REQFORMSUB 또는 REQFORMSUB_TEMP) 의 FORMSEQ 갯수 가져오기	 
	 */
	private int getCntFormseq(int reqformno, String sessi) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String tb = "";
		String kfield = "";
		
		if(reqformno == 0){
			tb = "REQFORMSUB_TEMP";
			kfield = "SESSIONID";
		} else {
			tb = "REQFORMSUB";
			kfield = "REQFORMNO";
		}	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) FROM "+tb+" WHERE "+kfield+" = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(reqformno == 0){
				pstmt.setString(1, sessi);	
			} else {
				pstmt.setInt(1, reqformno);
			}
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				count = rs.getInt(1);
			}		
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return count;
	}
	
	/**
	 * 신청서 기간상태 체크하기 : 구버전용, 최신버전에는 사용안함
	 * @param reqformno
	 * @return
	 * @throws Exception
	 */
	public int reqState(int reqformno) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
				
		try{
			//신청을 마감할 경우 하루전 날짜로 셋팅한다.
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n SELECT COUNT(REQFORMNO) ");
			selectQuery.append("\n   FROM REQFORMMST  ");
			selectQuery.append("\n  WHERE REQFORMNO = ? ");
			selectQuery.append("      AND STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");			
			selectQuery.append("      AND (ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR ENDDT IS NULL) ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, reqformno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {		
			 logger.error("ERROR", e);
				ConnectionManager.close(con,pstmt,rs);
			 throw e;
		} finally {	       
	    	 ConnectionManager.close(con,pstmt, rs);   
		}
		return result;
	}
	
	/**
	 * 신청서 기간상태 체크하기
	 * @param reqformno
	 * @return
	 * @throws Exception
	 */
	public int reqState(int reqformno, String userid) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
				
		try{
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n SELECT COUNT(REQFORMNO) ");
			selectQuery.append("\n   FROM REQFORMMST  ");
			selectQuery.append("\n  WHERE REQFORMNO = ? ");
			selectQuery.append("      AND STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");			
			selectQuery.append("      AND (ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR ENDDT IS NULL) ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, reqformno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				result = rs.getInt(1);
			}
			
			if ( result > 0 ) {
				selectQuery.delete(0, selectQuery.capacity());
				selectQuery.append("\n SELECT DECODE(LIMITCOUNT, 0, COUNT(REQSEQ) + 1, LIMITCOUNT) - COUNT(REQSEQ) ");
				selectQuery.append("\n FROM REQFORMMST RM, REQMST RA ");
				selectQuery.append("\n WHERE RM.REQFORMNO = RA.REQFORMNO(+)  ");
				selectQuery.append("\n AND RM.REQFORMNO = ? ");
				selectQuery.append("\n GROUP BY LIMITCOUNT ");
				
				ConnectionManager.close(pstmt, rs);
				pstmt = con.prepareStatement(selectQuery.toString());
				
				pstmt.setInt(1, reqformno);
				
				rs = pstmt.executeQuery();
				
				if ( rs.next() ) {
					result = rs.getInt(1);
					if ( result < 0 ) result = 0;
				}
			}
			
			if ( result > 0 ) {			
				selectQuery.delete(0, selectQuery.capacity());
				selectQuery.append("\n SELECT COUNT(R1.REQFORMNO)");
				selectQuery.append("\n FROM REQMST R1, REQFORMMST R2");
				selectQuery.append("\n WHERE R1.REQFORMNO = R2.REQFORMNO");
				selectQuery.append("\n AND R2.DUPLICHECK = '2'");
				selectQuery.append("\n AND R1.REQFORMNO = ? AND R1.PRESENTID = ?");
				
				ConnectionManager.close(pstmt, rs);
				pstmt = con.prepareStatement(selectQuery.toString());
				
				pstmt.setInt(1, reqformno);
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
			 ConnectionManager.close(con,pstmt,rs);
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
	public List getReqOutsideList() throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append(" SELECT T1.REQFORMNO  " +
							   "   FROM REQFORMMST T1 " +
							   "  WHERE T1.RANGE LIKE '2%' \n");
			selectQuery.append("    AND T1.STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");			
			selectQuery.append("    AND (T1.ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR T1.ENDDT IS NULL)  \n");	
			selectQuery.append("  ORDER BY CRTDT DESC \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
									
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while (rs.next()) {
				ReqMstBean Bean = new ReqMstBean();
				Bean.setReqformno(rs.getInt("REQFORMNO"));
				
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
	 * 문항당 보기개수 가져오기
	 * @param reqformno
	 * @return
	 * @throws Exception
	 */
	public int getReqSubExamcount(int reqformno, String sessionId) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			if ( reqformno == 0 ) {
				selectQuery.append("SELECT MAX(COUNT(EXAMSEQ)) \n");
				selectQuery.append("FROM REQFORMEXAM_TEMP \n");
				selectQuery.append("WHERE SESSIONID LIKE ? \n");
				selectQuery.append("GROUP BY FORMSEQ \n");
			} else {
				selectQuery.append("SELECT MAX(COUNT(EXAMSEQ)) \n");
				selectQuery.append("FROM REQFORMEXAM \n");
				selectQuery.append("WHERE REQFORMNO = ? \n");
				selectQuery.append("GROUP BY FORMSEQ \n");
			}
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if ( reqformno == 0) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, reqformno);
			}
									
			rs = pstmt.executeQuery();
						
			if ( rs.next() ) {
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
	 * 결재진행중인 건수 체크
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int apprProcCount(String userid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT COUNT(*) \n");
			selectQuery.append("FROM REQSANC RS, REQMST RM \n");
			selectQuery.append("WHERE RS.REQFORMNO = RM.REQFORMNO \n");
			selectQuery.append("AND RS.REQSEQ = RM.REQSEQ \n");
			selectQuery.append("AND RS.SANCYN != 1 \n");
			selectQuery.append("AND RM.PRESENTID = ? \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, userid);
									
			rs = pstmt.executeQuery();
			 
			if ( rs.next() == true ) {
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
	 * 신청서 - 관리자인경우 검색 조건에 해당하는 값 가져오기
	 */
	public String getSearch(SearchBean search, String sch_deptcd, String sch_userid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
			
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT NVL(SUM(REQFORMNO),0) AS CNT	 		\n");
			selectQuery.append(" FROM (											\n");
			selectQuery.append(" 	SELECT A.REQFORMNO 							\n");
			selectQuery.append(" 	FROM REQFORMMST	A, REQMST B					\n");
			selectQuery.append("    WHERE A.REQFORMNO = B.REQFORMNO(+) 			\n");
			selectQuery.append("    AND TITLE LIKE '%"+search.getTitle()+"%' 	\n");
			selectQuery.append("    AND COLDEPTCD = '"+sch_deptcd+"'			\n");
			selectQuery.append("    AND CHRGUSRID = '"+sch_userid+"'			\n");
			if ("0".equals(search.getProcFL()))	selectQuery.append("	AND B.STATE <= '02' \n");	//신청중
			selectQuery.append("      GROUP BY A.REQFORMNO)						\n"); 	

			con = ConnectionManager.getConnection();
			//logger.info("selectQuery.toString():  " + selectQuery);
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("CNT") == 1){
					ConnectionManager.close(pstmt, rs);
					
					if(!"".equals(sch_deptcd)&& sch_deptcd != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT COLDEPTCD	 							\n");
						selectQuery.append(" FROM (											\n");
						selectQuery.append(" 	SELECT A.COLDEPTCD AS COLDEPTCD				\n");
						selectQuery.append(" 	FROM REQFORMMST	A, REQMST B					\n");
						selectQuery.append("    WHERE A.REQFORMNO = B.REQFORMNO(+) 			\n");
						selectQuery.append("    AND TITLE LIKE '%"+search.getTitle()+"%' 	\n");
						selectQuery.append("    AND COLDEPTCD = '"+sch_deptcd+"'			\n");
						if ("0".equals(search.getProcFL()))	selectQuery.append("	AND B.STATE <= '02' \n");	//신청중
						selectQuery.append("      GROUP BY A.REQFORMNO)						\n"); 	

						pstmt = con.prepareStatement(selectQuery.toString());
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							result = rs.getString("COLDEPTCD");
						}
						ConnectionManager.close(pstmt, rs);
						
					}
					
					if(!"".equals(sch_userid)&& sch_userid != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT CHRGUSRID	 							\n");
						selectQuery.append(" FROM (											\n");
						selectQuery.append(" 	SELECT A.CHRGUSRID AS CHRGUSRID				\n");
						selectQuery.append(" 	FROM REQFORMMST	A, REQMST B					\n");
						selectQuery.append("    WHERE A.REQFORMNO = B.REQFORMNO(+) 			\n");
						selectQuery.append("    AND TITLE LIKE '%"+search.getTitle()+"%' 	\n");
						selectQuery.append("    AND CHRGUSRID = '"+sch_userid+"'			\n");
						if ("0".equals(search.getProcFL()))	selectQuery.append("	AND B.STATE <= '02' \n");	//신청중
						selectQuery.append("      GROUP BY A.REQFORMNO)						\n"); 

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
}