/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 자료이관 dao
 * 설명:
 */
package nexti.ejms.dataTransfer.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.util.Utils;

public class DataTransferDAO {
	
	private static Logger logger = Logger.getLogger(DataTransferDAO.class);
	
	/**
	 * 새 이력번호 가져오기
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public int getNextHistoryNo(Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT NVL(MAX(HISTORYNO), 0) + 1 FROM DTRANS_HISTORY \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 새 처리번호 가져오기
	 * @param conn
	 * @param historyno
	 * @return
	 * @throws Exception
	 */
	public int getNextSeq(Connection conn, int historyno) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT NVL(MAX(SEQ), 0) + 1 FROM DTRANS_HISTORY WHERE HISTORYNO = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, historyno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 원본자료 부서목록
	 * @return
	 * @throws Exception
	 */
	public List getOrgDeptList(String orggbn) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DISTINCT COLDEPTCD, COLDEPTNM, DEPT_RANK, DEPT_DEPTH 	\n");
			sql.append("FROM (SELECT COLDEPTCD, COLDEPTNM FROM DOCMST 					\n");
			sql.append("      UNION ALL 												\n");
			sql.append("      SELECT COLDEPTCD, COLDEPTNM FROM RCHMST 					\n");
			sql.append("      UNION ALL 												\n");
			sql.append("      SELECT COLDEPTCD, COLDEPTNM FROM REQFORMMST) A, DEPT B 	\n");
			sql.append("WHERE A.COLDEPTCD = B.DEPT_ID(+) 								\n");
			sql.append("AND B.ORGGBN LIKE ?              								\n");
			sql.append("ORDER BY TO_NUMBER(DEPT_RANK), DEPT_DEPTH 						\n");
			
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, "%"+orggbn+"%");
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				if ( rs.getString("COLDEPTCD") == null || rs.getString("COLDEPTNM") == null ) continue;
				DataTransferBean dtBean = new DataTransferBean();
				dtBean.setDeptid(rs.getString("COLDEPTCD"));
				dtBean.setDeptnm(rs.getString("COLDEPTNM"));
				
				if ( result == null ) result = new ArrayList();
				result.add(dtBean);
			}
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 원본자료 사용자목록
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public List getOrgUserList(String deptid, String orggbn) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DISTINCT CHRGUSRCD, CHRGUSRNM, COLDEPTCD, GRADE_ID, CLASS_ID \n");
			sql.append("FROM (SELECT CHRGUSRCD, CHRGUSRNM, COLDEPTCD FROM DOCMST \n");
			sql.append("      UNION ALL \n");
			sql.append("      SELECT CHRGUSRID, CHRGUSRNM, COLDEPTCD FROM RCHMST \n");
			sql.append("      UNION ALL \n");
			sql.append("      SELECT CHRGUSRID, CHRGUSRNM, COLDEPTCD FROM REQFORMMST) A, USR B, DEPT C \n");
			sql.append("WHERE A.CHRGUSRCD = B.USER_ID(+) \n");
			sql.append("AND B.DEPT_ID = C.DEPT_ID(+) \n");
			sql.append("AND C.ORGGBN = ? \n");
			sql.append("AND A.COLDEPTCD LIKE ? \n");
			sql.append("ORDER BY GRADE_ID, CLASS_ID \n");
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( orggbn.equals("") == true ) orggbn = "%";
			pstmt.setString(1, orggbn);
			
			if ( deptid.equals("") == true ) deptid = "%";
			pstmt.setString(2, deptid);
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				if ( rs.getString("CHRGUSRCD") == null || rs.getString("CHRGUSRNM") == null ) continue;
				DataTransferBean dtBean = new DataTransferBean();
				dtBean.setUserid(rs.getString("CHRGUSRCD"));
				dtBean.setUsernm(rs.getString("CHRGUSRNM"));
				
				if ( result == null ) result = new ArrayList();
				result.add(dtBean);
			}
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 대상자료 부서목록
	 * @return
	 * @throws Exception
	 */
	public List getTgtDeptList(String orggbn) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DEPT_ID, DEPT_NAME 							\n");
			sql.append("FROM DEPT 											\n");
			sql.append("WHERE USE_YN = 'Y' 									\n");
			sql.append("AND DEPT_ID != ? 									\n");		
			sql.append("AND ORGGBN LIKE ? 									\n");			
			sql.append("START WITH DEPT_ID = ? 								\n");
			sql.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID 			\n");
			sql.append("ORDER SIBLINGS BY TO_NUMBER(DEPT_RANK), DEPT_DEPTH	\n");
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, appInfo.getRootid());
			if ( orggbn.equals("") == true ) orggbn = "%";
			pstmt.setString(2, orggbn);
			pstmt.setString(3, appInfo.getRootid());
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				if ( rs.getString("DEPT_ID") == null || rs.getString("DEPT_NAME") == null ) continue;
				DataTransferBean dtBean = new DataTransferBean();
				dtBean.setDeptid(rs.getString("DEPT_ID"));
				dtBean.setDeptnm(rs.getString("DEPT_NAME"));
				
				if ( result == null ) result = new ArrayList();
				result.add(dtBean);
			}
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 대상자료 사용자목록
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public List getTgtUserList(String deptid) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT USER_ID, USER_NAME 			\n");
			sql.append("FROM USR							\n");
			sql.append("WHERE USE_YN = 'Y' 					\n");
			sql.append("AND DEPT_ID LIKE ? 					\n");
			sql.append("ORDER BY GRADE_ID, CLASS_ID 		\n");
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( deptid.equals("") == true ) deptid = "%";
			pstmt.setString(1, deptid);
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				if ( rs.getString("USER_ID") == null || rs.getString("USER_NAME") == null ) continue;
				DataTransferBean dtBean = new DataTransferBean();
				dtBean.setUserid(rs.getString("USER_ID"));
				dtBean.setUsernm(rs.getString("USER_NAME"));
				
				if ( result == null ) result = new ArrayList();
				result.add(dtBean);
			}
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 원본자료 자료목록
	 * @param deptid
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List getOrgDataList(String orggbn, String deptid, String userid) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT NO, TYPE, TITLE, DEPTNM, DEPTID, USERNM, USERID, SUBSTR(A.UPTDT, 0, 10) UPTDT, SUBSTR(A.CRTDT, 0, 10) CRTDT, \n");
			sql.append("       NVL2(B.DEPT_ID, 'Y', 'N') EXISTDEPTID, NVL2(C.USER_ID, 'Y', 'N') EXISTUSERID \n");
			sql.append("FROM (SELECT SYSDOCNO NO, 'COLLECT' TYPE, DOCTITLE TITLE, COLDEPTNM DEPTNM, COLDEPTCD DEPTID, \n");
			sql.append("             CHRGUSRNM USERNM, CHRGUSRCD USERID, UPTDT, CRTDT \n");
			sql.append("      FROM DOCMST \n");
			sql.append("      UNION ALL \n");
			sql.append("      SELECT RCHNO, 'RESEARCH', TITLE, COLDEPTNM, COLDEPTCD, CHRGUSRNM, CHRGUSRID, UPTDT, CRTDT \n");
			sql.append("      FROM RCHMST \n");
			sql.append("      UNION ALL \n");
			sql.append("      SELECT REQFORMNO, 'REQUEST', TITLE, COLDEPTNM, COLDEPTCD, CHRGUSRNM, CHRGUSRID, UPTDT, CRTDT \n");
			sql.append("      FROM REQFORMMST) A, DEPT B, USR C \n");
			sql.append("WHERE A.DEPTID = B.DEPT_ID(+) \n");
			sql.append("AND A.USERID = C.USER_ID(+) \n");
			sql.append("AND B.ORGGBN LIKE ? \n");
			sql.append("AND DEPTID LIKE ? \n");
			sql.append("AND USERID LIKE ? \n");
			sql.append("ORDER BY TYPE, TO_NUMBER(DEPT_RANK), DEPT_DEPTH, GRADE_ID, CLASS_ID \n");
			
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql.toString());

			if ( orggbn.equals("") == true ) orggbn = "%";
			if ( deptid.equals("") == true ) deptid = "%";
			if ( userid.equals("") == true ) userid = "%";
			pstmt.setString(1, orggbn);
			pstmt.setString(2, deptid);
			pstmt.setString(3, userid);
			
			rs = pstmt.executeQuery();
			
			int seq = 0;
			while ( rs.next() == true ) {
				DataTransferBean dtBean = new DataTransferBean();
				dtBean.setSeq(++seq);
				dtBean.setNo(rs.getInt("NO"));
				dtBean.setType(rs.getString("TYPE"));
				dtBean.setTitle(rs.getString("TITLE"));
				dtBean.setDeptnm(rs.getString("DEPTNM"));
				dtBean.setDeptid(rs.getString("DEPTID"));
				dtBean.setUsernm(rs.getString("USERNM"));
				dtBean.setUserid(rs.getString("USERID"));
				dtBean.setExistdeptid(rs.getString("EXISTDEPTID"));
				dtBean.setExistuserid(rs.getString("EXISTUSERID"));
				dtBean.setUptdt(Utils.nullToEmptyString(rs.getString("UPTDT")));
				
				if ( dtBean.getUptdt().equals("") == true ) {
					dtBean.setUptdt(Utils.nullToEmptyString(rs.getString("CRTDT")));
				}
				
				if ( result == null ) result = new ArrayList();
				result.add(dtBean);
			}
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 취합테이블 자료
	 * @param no
	 * @return
	 * @throws Exception
	 */
	public DataTransferBean selectCollectTable(Connection conn, int no) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DataTransferBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT SYSDOCNO, COLDEPTCD, COLDEPTNM, CHRGUSRCD, CHRGUSRNM, CRTDT, CRTUSRID, UPTDT, UPTUSRID, CHRGUNITCD, CHRGUNITNM 	\n");
			sql.append("FROM DOCMST 																											\n");
			sql.append("WHERE SYSDOCNO = ? 																										\n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new DataTransferBean();
				
				result.setNo(rs.getInt("SYSDOCNO"));
				result.setDeptid(rs.getString("COLDEPTCD"));
				result.setDeptnm(rs.getString("COLDEPTNM"));
				result.setUserid(rs.getString("CHRGUSRCD"));
				result.setUsernm(rs.getString("CHRGUSRNM"));
				result.setCrtdt(rs.getString("CRTDT"));
				result.setCrtusrid(rs.getString("CRTUSRID"));
				result.setUptdt(rs.getString("UPTDT"));
				result.setUptusrid(rs.getString("UPTUSRID"));
				result.setChrgunitid(rs.getInt("CHRGUNITCD"));
				result.setChrgunitnm(rs.getString("CHRGUNITNM"));
			}
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close( pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 설문테이블 자료
	 * @param no
	 * @return
	 * @throws Exception
	 */
	public DataTransferBean selectResearchTable(Connection conn, int no) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DataTransferBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT RCHNO, COLDEPTCD, COLDEPTNM, CHRGUSRID, CHRGUSRNM, CRTDT, CRTUSRID, UPTDT, UPTUSRID, COLDEPTTEL \n");
			sql.append("FROM RCHMST \n");
			sql.append("WHERE RCHNO = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new DataTransferBean();
				
				result.setNo(rs.getInt("RCHNO"));
				result.setDeptid(rs.getString("COLDEPTCD"));
				result.setDeptnm(rs.getString("COLDEPTNM"));
				result.setUserid(rs.getString("CHRGUSRID"));
				result.setUsernm(rs.getString("CHRGUSRNM"));
				result.setCrtdt(rs.getString("CRTDT"));
				result.setCrtusrid(rs.getString("CRTUSRID"));
				result.setUptdt(rs.getString("UPTDT"));
				result.setUptusrid(rs.getString("UPTUSRID"));
				result.setDepttel(rs.getString("COLDEPTTEL"));
			}
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 신청테이블 자료
	 * @param no
	 * @return
	 * @throws Exception
	 */
	public DataTransferBean selectRequestTable(Connection conn, int no) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DataTransferBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT REQFORMNO, COLDEPTCD, COLDEPTNM, CHRGUSRID, CHRGUSRNM, CRTDT, CRTUSRID, UPTDT, UPTUSRID, COLDEPTTEL \n");
			sql.append("FROM REQFORMMST \n");
			sql.append("WHERE REQFORMNO = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new DataTransferBean();
				
				result.setNo(rs.getInt("REQFORMNO"));
				result.setDeptid(rs.getString("COLDEPTCD"));
				result.setDeptnm(rs.getString("COLDEPTNM"));
				result.setUserid(rs.getString("CHRGUSRID"));
				result.setUsernm(rs.getString("CHRGUSRNM"));
				result.setCrtdt(rs.getString("CRTDT"));
				result.setCrtusrid(rs.getString("CRTUSRID"));
				result.setUptdt(rs.getString("UPTDT"));
				result.setUptusrid(rs.getString("UPTUSRID"));
				result.setDepttel(rs.getString("COLDEPTTEL"));
			}
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 설문그룹테이블 자료
	 * @param no
	 * @return
	 * @throws Exception
	 */
	public DataTransferBean selectResearchGroupTable(Connection conn, int no) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DataTransferBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT RCHGRPNO, COLDEPTCD, COLDEPTNM, CHRGUSRID, CHRGUSRNM, CRTDT, CRTUSRID, UPTDT, UPTUSRID, COLDEPTTEL \n");
			sql.append("FROM RCHGRPMST \n");
			sql.append("WHERE RCHGRPNO = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new DataTransferBean();
				
				result.setNo(rs.getInt("RCHGRPNO"));
				result.setDeptid(rs.getString("COLDEPTCD"));
				result.setDeptnm(rs.getString("COLDEPTNM"));
				result.setUserid(rs.getString("CHRGUSRID"));
				result.setUsernm(rs.getString("CHRGUSRNM"));
				result.setCrtdt(rs.getString("CRTDT"));
				result.setCrtusrid(rs.getString("CRTUSRID"));
				result.setUptdt(rs.getString("UPTDT"));
				result.setUptusrid(rs.getString("UPTUSRID"));
				result.setDepttel(rs.getString("COLDEPTTEL"));
			}
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 자료이관이력 저장
	 * @param conn
	 * @param dtBean
	 * @return
	 * @throws Exception
	 */
	public int insertHistory(Connection conn, DataTransferBean dtBean) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO DTRANS_HISTORY \n");
			sql.append("DTRANS_HISTORY(HISTORYNO,	SEQ,		TABLENAME,	COLUMNNAME,	OLDVALUE, \n");
			sql.append("               CAUSE,		CRTUSRID,	CRTDT) \n");
			sql.append("VALUES        (?,			?,			?,			?,			?, \n");
			sql.append("               ?,			?,			TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')) \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, dtBean.getHistoryno());
			pstmt.setInt(2, dtBean.getSeq());
			pstmt.setString(3, dtBean.getTablename());
			pstmt.setString(4, dtBean.getColumnname());
			pstmt.setString(5, dtBean.getOldvalue());
			pstmt.setString(6, dtBean.getCause());
			pstmt.setString(7, dtBean.getCrtusrid());
			
			result = pstmt.executeUpdate();			
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 취합테이블 변경
	 * @param conn
	 * @param dtBean
	 * @return
	 * @throws Exception
	 */
	public int updateCollectTable(Connection conn, DataTransferBean dtBean) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE TGTDEPT \n");
			sql.append("SET PREDEPTCD = 'M_' || (SELECT DEPT_ID FROM USR WHERE USER_ID = ?) \n");
			sql.append("WHERE SYSDOCNO = ? \n");
			sql.append("AND PREDEPTCD LIKE 'M_%' \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, dtBean.getUserid());
			pstmt.setInt(2, dtBean.getNo());
			
			ConnectionManager.close(pstmt);
			sql.delete(0, sql.capacity());
			
			sql.append("UPDATE TGTLIST \n");
			sql.append("SET PREDEPTCD = 'M_' || (SELECT DEPT_ID FROM USR WHERE USER_ID = ?) \n");
			sql.append("WHERE SYSDOCNO = ? \n");
			sql.append("AND PREDEPTCD LIKE 'M_%' \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, dtBean.getUserid());
			pstmt.setInt(2, dtBean.getNo());
			
			ConnectionManager.close(pstmt);
			sql.delete(0, sql.capacity());
			
			sql.append("UPDATE DOCMST \n");
			sql.append("SET COLDEPTCD = (SELECT DEPT_ID FROM USR WHERE USER_ID = ?), \n");
			sql.append("    COLDEPTNM = (SELECT DEPT_NAME FROM USR WHERE USER_ID = ?), \n");
			sql.append("    CHRGUSRCD = ?, \n");
			sql.append("    CHRGUSRNM = (SELECT USER_NAME FROM USR WHERE USER_ID = ?), \n");
			sql.append("    CRTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), \n");
			sql.append("    CRTUSRID = ?, \n");
			sql.append("    UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), \n");
			sql.append("    UPTUSRID = ?, \n");
			sql.append("    CHRGUNITCD = (SELECT CHRGUNITCD FROM USR WHERE USER_ID = ?), \n");
			sql.append("    CHRGUNITNM = (SELECT CHRGUNITNM FROM CHRGUNIT \n");
			sql.append("                  WHERE DEPT_ID = (SELECT DEPT_ID FROM USR WHERE USER_ID = ?) \n");
			sql.append("                  AND CHRGUNITCD = (SELECT CHRGUNITCD FROM USR WHERE USER_ID = ?)) \n");
			sql.append("WHERE SYSDOCNO = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, dtBean.getUserid());
			pstmt.setString(2, dtBean.getUserid());
			pstmt.setString(3, dtBean.getUserid());
			pstmt.setString(4, dtBean.getUserid());
			pstmt.setString(5, dtBean.getUserid());
			pstmt.setString(6, dtBean.getUserid());
			pstmt.setString(7, dtBean.getUserid());
			pstmt.setString(8, dtBean.getUserid());
			pstmt.setString(9, dtBean.getUserid());
			pstmt.setInt(10, dtBean.getNo());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 설문테이블 변경
	 * @param conn
	 * @param dtBean
	 * @return
	 * @throws Exception
	 */
	public int updateResearchTable(Connection conn, DataTransferBean dtBean) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE RCHMST \n");
			sql.append("SET COLDEPTCD = (SELECT DEPT_ID FROM USR WHERE USER_ID = ?), \n");
			sql.append("    COLDEPTNM = (SELECT DEPT_NAME FROM USR WHERE USER_ID = ?), \n");
			sql.append("    CHRGUSRID = ?, \n");
			sql.append("    CHRGUSRNM = (SELECT USER_NAME FROM USR WHERE USER_ID = ?), \n");
			sql.append("    CRTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), \n");
			sql.append("    CRTUSRID = ?, \n");
			sql.append("    UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), \n");
			sql.append("    UPTUSRID = ?, \n");
			sql.append("    COLDEPTTEL = (SELECT TEL FROM USR WHERE USER_ID = ?) \n");
			sql.append("WHERE RCHNO = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, dtBean.getUserid());
			pstmt.setString(2, dtBean.getUserid());
			pstmt.setString(3, dtBean.getUserid());
			pstmt.setString(4, dtBean.getUserid());
			pstmt.setString(5, dtBean.getUserid());
			pstmt.setString(6, dtBean.getUserid());
			pstmt.setString(7, dtBean.getUserid());
			pstmt.setInt(8, dtBean.getNo());
			
			result = pstmt.executeUpdate();			
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 신청테이블 변경
	 * @param conn
	 * @param dtBean
	 * @return
	 * @throws Exception
	 */
	public int updateRequestTable(Connection conn, DataTransferBean dtBean) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE REQFORMMST \n");
			sql.append("SET COLDEPTCD = (SELECT DEPT_ID FROM USR WHERE USER_ID = ?), \n");
			sql.append("    COLDEPTNM = (SELECT DEPT_NAME FROM USR WHERE USER_ID = ?), \n");
			sql.append("    CHRGUSRID = ?, \n");
			sql.append("    CHRGUSRNM = (SELECT USER_NAME FROM USR WHERE USER_ID = ?), \n");
			sql.append("    CRTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), \n");
			sql.append("    CRTUSRID = ?, \n");
			sql.append("    UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), \n");
			sql.append("    UPTUSRID = ?, \n");
			sql.append("    COLDEPTTEL = (SELECT DEPT_TEL FROM DEPT \n");
			sql.append("                  WHERE DEPT_ID = (SELECT DEPT_ID FROM USR WHERE USER_ID = ?)) \n");
			sql.append("WHERE REQFORMNO = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, dtBean.getUserid());
			pstmt.setString(2, dtBean.getUserid());
			pstmt.setString(3, dtBean.getUserid());
			pstmt.setString(4, dtBean.getUserid());
			pstmt.setString(5, dtBean.getUserid());
			pstmt.setString(6, dtBean.getUserid());
			pstmt.setString(7, dtBean.getUserid());
			pstmt.setInt(8, dtBean.getNo());
			
			result = pstmt.executeUpdate();			
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 설문그룹테이블 변경
	 * @param conn
	 * @param dtBean
	 * @return
	 * @throws Exception
	 */
	public int updateResearchGroupTable(Connection conn, DataTransferBean dtBean) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE RCHGRPMST \n");
			sql.append("SET COLDEPTCD = (SELECT DEPT_ID FROM USR WHERE USER_ID = ?), \n");
			sql.append("    COLDEPTNM = (SELECT DEPT_NAME FROM USR WHERE USER_ID = ?), \n");
			sql.append("    CHRGUSRID = ?, \n");
			sql.append("    CHRGUSRNM = (SELECT USER_NAME FROM USR WHERE USER_ID = ?), \n");
			sql.append("    CRTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), \n");
			sql.append("    CRTUSRID = ?, \n");
			sql.append("    UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), \n");
			sql.append("    UPTUSRID = ?, \n");
			sql.append("    COLDEPTTEL = (SELECT TEL FROM USR WHERE USER_ID = ?) \n");
			sql.append("WHERE RCHGRPNO = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, dtBean.getUserid());
			pstmt.setString(2, dtBean.getUserid());
			pstmt.setString(3, dtBean.getUserid());
			pstmt.setString(4, dtBean.getUserid());
			pstmt.setString(5, dtBean.getUserid());
			pstmt.setString(6, dtBean.getUserid());
			pstmt.setString(7, dtBean.getUserid());
			pstmt.setInt(8, dtBean.getNo());
			
			result = pstmt.executeUpdate();			
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
}