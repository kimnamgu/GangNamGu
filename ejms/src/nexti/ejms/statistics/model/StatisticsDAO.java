/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 사용현황 dao
 * 설명:
 */
package nexti.ejms.statistics.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.util.Utils;

public class StatisticsDAO {
	
	private static Logger logger = Logger.getLogger(StatisticsDAO.class);
		
	/**
	 * 취합건수현황 조회
	 */
	public List getCollsttcs(int gbn, String orggbn, String orggbn_dt, String frDate, String toDate) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = null;
		
		try {
    		String rootid = appInfo.getRootid();
			StringBuffer selectQuery = new StringBuffer();
			
			if ( gbn == 0 ) {
				selectQuery.append("SELECT A.COLDEPTCD, B.DEPT_RANK, REPLACE(B.DEPT_FULLNAME, (SELECT DEPT_FULLNAME FROM DEPT WHERE DEPT_ID = '" + rootid + "')||' ', '') COLDEPTNM, COUNT(*)CHRGCOUNT \n");
				selectQuery.append("FROM DOCMST A, DEPT B \n");
				selectQuery.append("WHERE A.COLDEPTCD (+)= B.DEPT_ID \n");
				selectQuery.append("AND A.BASICDATE BETWEEN ? AND ? \n");
				selectQuery.append("AND A.DOCSTATE >= '04' \n");
				if( !"".equals(orggbn) ) {
					selectQuery.append("AND B.ORGGBN = '"+orggbn+"' \n");
				}
				if( !"".equals(orggbn_dt) ) {
					selectQuery.append("AND B.DEPT_ID = '"+orggbn_dt+"' \n");
				}
				selectQuery.append("GROUP BY A.COLDEPTCD, B.DEPT_RANK, B.DEPT_FULLNAME \n");
				selectQuery.append("ORDER BY B.DEPT_RANK \n");
			} else {
				selectQuery.append("SELECT A.COLDEPTCD, B.DEPT_RANK, REPLACE(B.DEPT_FULLNAME, (SELECT DEPT_FULLNAME FROM DEPT WHERE DEPT_ID = '" + rootid + "')||' ', '') COLDEPTNM, A.CHRGUSRNM, COUNT(*)CHRGCOUNT \n");
				selectQuery.append("FROM DOCMST A, DEPT B \n");
				selectQuery.append("WHERE A.COLDEPTCD (+)= B.DEPT_ID \n");
				selectQuery.append("AND A.BASICDATE BETWEEN ? AND ? \n");
				selectQuery.append("AND A.DOCSTATE >= '04' \n");
				if( !"".equals(orggbn) ) {
					selectQuery.append("AND B.ORGGBN = '"+orggbn+"' \n");
				}
				if( !"".equals(orggbn_dt) ) {
					selectQuery.append("AND B.DEPT_ID = '"+orggbn_dt+"' \n");
				}
				selectQuery.append("GROUP BY A.COLDEPTCD, B.DEPT_RANK, B.DEPT_FULLNAME, A.CHRGUSRNM \n");
				selectQuery.append("ORDER BY B.DEPT_RANK \n");
			}
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			//조회조건 설정
			pstmt.setString(1, frDate);
			pstmt.setString(2, toDate);

			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			int chrgCount = 0;
			
			while (rs.next()) {
				CollsttcsBean bean = new CollsttcsBean();
				chrgCount++;
				
				if ( gbn == 0 ) {
					bean.setSeqno(chrgCount);
					bean.setColDeptNm(rs.getString("COLDEPTNM"));
					bean.setChrgCount(rs.getLong("CHRGCOUNT"));
				} else {
					bean.setSeqno(chrgCount);
					bean.setColDeptNm(rs.getString("COLDEPTNM"));
					bean.setChrgUsrNm(rs.getString("CHRGUSRNM"));
					bean.setChrgCount(rs.getLong("CHRGCOUNT"));
				}
												
				list.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return list;
	}

	/**
	 * 취합건수현황 총건수
	 */
	public long getCollsttcsTotalCount(String frDate, String toDate) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long totCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*)TOTCOUNT \n");
			selectQuery.append("FROM DOCMST A, DEPT B \n");
			selectQuery.append("WHERE A.COLDEPTCD(+) = B.DEPT_ID \n");
			selectQuery.append("AND A.BASICDATE BETWEEN ? AND ? \n");
			selectQuery.append("AND A.DOCSTATE >= '04' \n");

			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			//조회조건 설정
			pstmt.setString(1, frDate);
			pstmt.setString(2, toDate);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				totCount = rs.getLong("TOTCOUNT");
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return totCount;
	}	
	
	/**
	 * 입력건수현황 조회
	 */
	public List getInputsttcs(int gbn, String orggbn, String orggbn_dt, String frDate, String toDate) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = null;
		
		try {
    		String rootid = appInfo.getRootid();
			StringBuffer selectQuery = new StringBuffer();
			
			if ( gbn == 0 ) {
				selectQuery.append("SELECT A1.TGTDEPTCD, REPLACE(B.DEPT_FULLNAME, (SELECT DEPT_FULLNAME FROM DEPT WHERE DEPT_ID = '" + rootid + "')||' ', '') TGTDEPTNM, B.DEPT_RANK, COUNT(*)INPUTCOUNT \n");
				selectQuery.append("FROM INPUTUSR A, TGTDEPT A1, DEPT B, DOCMST C \n");
				selectQuery.append("WHERE A.SYSDOCNO = A1.SYSDOCNO \n");
				selectQuery.append("AND A.SYSDOCNO = C.SYSDOCNO \n");
				selectQuery.append("AND A.TGTDEPT(+) = B.DEPT_ID \n");
				selectQuery.append("AND A.TGTDEPT = A1.TGTDEPTCD \n");
				selectQuery.append("AND A1.UPTDT BETWEEN ?||' 00:00:00' AND ?||' 24:59:59' \n");
				selectQuery.append("AND A1.SUBMITSTATE >= '02' \n");
				selectQuery.append("AND C.DOCSTATE >= '04' \n");
				selectQuery.append("AND A.INPUTSTATE >= '02' \n");
				if( !"".equals(orggbn) ) {
					selectQuery.append("AND B.ORGGBN = '"+orggbn+"' \n");
				}
				if( !"".equals(orggbn_dt) ) {
					selectQuery.append("AND B.DEPT_ID = '"+orggbn_dt+"' \n");
				}
				selectQuery.append("GROUP BY A1.TGTDEPTCD, B.DEPT_RANK, B.DEPT_FULLNAME \n");
				selectQuery.append("ORDER BY B.DEPT_RANK \n");
			} else if ( gbn == 1 ) {
				selectQuery.append("SELECT A1.TGTDEPTCD, REPLACE(B.DEPT_FULLNAME, (SELECT DEPT_FULLNAME FROM DEPT WHERE DEPT_ID = '" + rootid + "')||' ', '') TGTDEPTNM, B.DEPT_RANK, A.INPUTUSRNM, COUNT(*)INPUTCOUNT \n");
				selectQuery.append("FROM INPUTUSR A, TGTDEPT A1, DEPT B, DOCMST C \n");
				selectQuery.append("WHERE A.SYSDOCNO = A1.SYSDOCNO \n");
				selectQuery.append("AND A.SYSDOCNO = C.SYSDOCNO \n");
				selectQuery.append("AND A.TGTDEPT(+) = B.DEPT_ID \n");
				selectQuery.append("AND A.TGTDEPT = A1.TGTDEPTCD \n");
				selectQuery.append("AND A1.UPTDT BETWEEN ?||' 00:00:00' AND ?||' 24:59:59' \n");
				selectQuery.append("AND A1.SUBMITSTATE >= '02' \n");
				selectQuery.append("AND C.DOCSTATE >= '04' \n");
				selectQuery.append("AND A.INPUTSTATE >= '02' \n");
				if( !"".equals(orggbn) ) {
					selectQuery.append("AND B.ORGGBN = '"+orggbn+"' \n");
				}
				if( !"".equals(orggbn_dt) ) {
					selectQuery.append("AND B.DEPT_ID = '"+orggbn_dt+"' \n");
				}
				selectQuery.append("GROUP BY A1.TGTDEPTCD, B.DEPT_RANK, A.INPUTUSRNM, B.DEPT_FULLNAME \n");
				selectQuery.append("ORDER BY B.DEPT_RANK \n");
			} else {
				selectQuery.append("SELECT REPLACE(B.DEPT_FULLNAME, (SELECT DEPT_FULLNAME FROM DEPT WHERE DEPT_ID = '" + rootid + "')||' ', '') COLDEPTNM, DOCTITLE, SUMMARY, SUBSTR(SUBMITDATE, 1, 10) STRDT, SUBSTR(ENDDT, 1, 10) ENDDT, COUNT(*) INPUTCOUNT \n");
				selectQuery.append("FROM INPUTUSR A, TGTDEPT A1, DEPT B, DOCMST C \n");
				selectQuery.append("WHERE A.SYSDOCNO = A1.SYSDOCNO \n");
				selectQuery.append("AND A.SYSDOCNO = C.SYSDOCNO \n");
				selectQuery.append("AND A.TGTDEPT(+) = B.DEPT_ID \n");
				selectQuery.append("AND A.TGTDEPT = A1.TGTDEPTCD \n");
				selectQuery.append("AND A1.UPTDT BETWEEN ?||' 00:00:00' AND ?||' 24:59:59' \n");
				selectQuery.append("AND A1.SUBMITSTATE >= '02' \n");
				selectQuery.append("AND C.DOCSTATE >= '04' \n");
				selectQuery.append("AND A.INPUTSTATE >= '02' \n");
				if( !"".equals(orggbn) ) {
					selectQuery.append("AND B.ORGGBN = '"+orggbn+"' \n");
				}
				if( !"".equals(orggbn_dt) ) {
					selectQuery.append("AND B.DEPT_ID = '"+orggbn_dt+"' \n");
				}
				selectQuery.append("GROUP BY DOCTITLE, SUMMARY, SUBSTR(SUBMITDATE, 1, 10), SUBSTR(ENDDT, 1, 10), B.DEPT_FULLNAME \n");
				selectQuery.append("ORDER BY DOCTITLE, STRDT \n");
			}

			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			//조회조건 설정
			pstmt.setString(1, frDate);
			pstmt.setString(2, toDate);

			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			int inputCount = 0;
			
			while (rs.next()) {
				InputsttcsBean bean = new InputsttcsBean();
				inputCount++;
				
				if ( gbn == 0 ) {
					bean.setSeqno(inputCount);
					bean.setInputDeptNm(rs.getString("TGTDEPTNM"));
					bean.setInputCount(rs.getLong("INPUTCOUNT"));
				} else if ( gbn == 1 ) {
					bean.setSeqno(inputCount);
					bean.setInputDeptNm(rs.getString("TGTDEPTNM"));
					bean.setInputUsrNm(rs.getString("INPUTUSRNM"));
					bean.setInputCount(rs.getLong("INPUTCOUNT"));
				} else {
					bean.setSeqno(inputCount);
					bean.setInputDeptNm(rs.getString("COLDEPTNM"));
					bean.setTitle(rs.getString("DOCTITLE"));
					bean.setSummary(rs.getString("SUMMARY"));
					bean.setStrdt(rs.getString("STRDT"));
					bean.setEnddt(rs.getString("ENDDT"));
					bean.setInputCount(rs.getLong("INPUTCOUNT"));
				}
												
				list.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return list;
	}

	/**
	 * 입력건수현황 총건수
	 */
	public long getInputsttcsTotalCount(String frDate, String toDate) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long totCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*)TOTCOUNT " +
			           		   "FROM INPUTUSR A, TGTDEPT A1, DEPT B, DOCMST C " +
			           		   "WHERE A.SYSDOCNO = A1.SYSDOCNO " +
			           		   "  AND A.SYSDOCNO = C.SYSDOCNO " +
			           		   "  AND A.TGTDEPT (+)= B.DEPT_ID " +
					           "  AND A.TGTDEPT = A1.TGTDEPTCD " +
			           		   "  AND A1.UPTDT BETWEEN ?||' 00:00:00' AND ?||' 24:59:59' " +
			           		   "  AND A1.SUBMITSTATE >= '02' " +
			           		   "  AND C.DOCSTATE >= '04' " +
					           "  AND A.INPUTSTATE >= '02' ");

			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			//조회조건 설정
			pstmt.setString(1, frDate);
			pstmt.setString(2, toDate);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				totCount = rs.getLong("TOTCOUNT");
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return totCount;
	}		
	
	/**
	 * 설문조사사용현황 조회
	 */
	public List getRchsttcs(int gbn, String orggbn, String orggbn_dt, String frDate, String toDate, String range) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = null;
		
		try {
    		String rootid = appInfo.getRootid();
			StringBuffer selectQuery = new StringBuffer();
			
			if ( gbn == 0 ) {
				selectQuery.append("SELECT A.COLDEPTCD, REPLACE(B.DEPT_FULLNAME, (SELECT DEPT_FULLNAME FROM DEPT WHERE DEPT_ID = '" + rootid + "')||' ', '') COLDEPTNM, B.DEPT_RANK, COUNT(*)RCHCNT , SUM(A.ANSCOUNT) ANSCNT \n");
				selectQuery.append("FROM RCHMST A, DEPT B \n");
				selectQuery.append("WHERE A.COLDEPTCD = B.DEPT_ID(+) \n");
				selectQuery.append("AND A.CRTDT BETWEEN ?||' 00:00:00' AND ?||' 24:59:59' \n");
				if (Utils.isNotNull(range)) {
					selectQuery.append("AND A.RANGE LIKE '"+range+"%' \n");
				}
				if( !"".equals(orggbn) ) {
					selectQuery.append("AND B.ORGGBN = '"+orggbn+"' \n");
				}
				if( !"".equals(orggbn_dt) ) {
					selectQuery.append("AND B.DEPT_ID = '"+orggbn_dt+"' \n");
				}
				selectQuery.append("GROUP BY A.COLDEPTCD, B.DEPT_FULLNAME, B.DEPT_RANK \n");
				selectQuery.append("ORDER BY B.DEPT_RANK \n");
			} else if ( gbn == 1 ) {
				selectQuery.append("SELECT A.COLDEPTCD, REPLACE(B.DEPT_FULLNAME, (SELECT DEPT_FULLNAME FROM DEPT WHERE DEPT_ID = '" + rootid + "')||' ', '') COLDEPTNM, A.CHRGUSRNM, B.DEPT_RANK, COUNT(*)RCHCNT , SUM(A.ANSCOUNT) ANSCNT \n");
				selectQuery.append("FROM RCHMST A, DEPT B \n");
				selectQuery.append("WHERE A.COLDEPTCD = B.DEPT_ID(+) \n");
				selectQuery.append("AND A.CRTDT BETWEEN ?||' 00:00:00' AND ?||' 24:59:59' \n");
				if (Utils.isNotNull(range)) {
					selectQuery.append("AND A.RANGE LIKE '"+range+"%' \n");
				}
				if( !"".equals(orggbn) ) {
					selectQuery.append("AND B.ORGGBN = '"+orggbn+"' \n");
				}
				if( !"".equals(orggbn_dt) ) {
					selectQuery.append("AND B.DEPT_ID = '"+orggbn_dt+"' \n");
				}
				selectQuery.append("GROUP BY A.COLDEPTCD, B.DEPT_FULLNAME, A.CHRGUSRNM, B.DEPT_RANK \n");
				selectQuery.append("ORDER BY B.DEPT_RANK \n");
			} else {
				selectQuery.append("SELECT REPLACE(B.DEPT_FULLNAME, (SELECT DEPT_FULLNAME FROM DEPT WHERE DEPT_ID = '" + rootid + "')||' ', '') COLDEPTNM, TITLE, SUMMARY, STRDT, ENDDT, SUM(ANSCOUNT) ANSCNT\n");
				selectQuery.append("FROM RCHMST A, DEPT B\n");
				selectQuery.append("WHERE A.COLDEPTCD = B.DEPT_ID(+)\n");
				selectQuery.append("AND A.CRTDT BETWEEN ?||' 00:00:00' AND ?||' 24:59:59' \n");
				if (Utils.isNotNull(range)) {
					selectQuery.append("AND A.RANGE LIKE '"+range+"%' \n");
				}
				if( !"".equals(orggbn) ) {
					selectQuery.append("AND B.ORGGBN = '"+orggbn+"' \n");
				}
				if( !"".equals(orggbn_dt) ) {
					selectQuery.append("AND B.DEPT_ID = '"+orggbn_dt+"' \n");
				}
				selectQuery.append("GROUP BY B.DEPT_FULLNAME, TITLE, SUMMARY, STRDT, ENDDT\n");
				selectQuery.append("ORDER BY B.DEPT_FULLNAME, TITLE, STRDT\n");
			}

			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			//조회조건 설정
			pstmt.setString(1, frDate);
			pstmt.setString(2, toDate);

			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			int Count = 0;
			
			while (rs.next()) {
				RchsttcsBean bean = new RchsttcsBean();
				Count++;
				
				if ( gbn == 0 ) {
					bean.setSeqno(Count);
					bean.setDeptnm(rs.getString("COLDEPTNM"));
					bean.setRchcount(rs.getLong("RCHCNT"));
					bean.setAnscount(rs.getLong("ANSCNT"));
				} else if ( gbn == 1 ) {
					bean.setSeqno(Count);
					bean.setDeptnm(rs.getString("COLDEPTNM"));
					bean.setUsernm(rs.getString("CHRGUSRNM"));
					bean.setRchcount(rs.getLong("RCHCNT"));
				} else {
					bean.setSeqno(Count);
					bean.setDeptnm(rs.getString("COLDEPTNM"));
					bean.setTitle(rs.getString("TITLE"));
					bean.setSummary(rs.getString("SUMMARY"));
					bean.setStrdt(rs.getString("STRDT"));
					bean.setEnddt(rs.getString("ENDDT"));
					bean.setAnscount(rs.getLong("ANSCNT"));
				}
												
				list.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return list;
	}

	/**
	 * 설문조사 총건수
	 */
	public RchsttcsBean getRchsttcsTotalCount(String frDate, String toDate, String range) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RchsttcsBean bean = new RchsttcsBean();
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*)RCHCNT , SUM(A.ANSCOUNT) ANSCNT " +
					   		   "FROM RCHMST A, DEPT B " +
					   		   "WHERE A.COLDEPTCD=B.DEPT_ID(+) " +
							   "  AND A.CRTDT BETWEEN ?||' 00:00:00' AND ?||' 24:59:59' ");
			if (Utils.isNotNull(range)) {
				selectQuery.append("  AND A.RANGE LIKE '"+range+"%' ");
			}

			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			//조회조건 설정
			pstmt.setString(1, frDate);
			pstmt.setString(2, toDate);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				bean.setSeqno(0);
				bean.setRchcount(rs.getLong("RCHCNT"));
				bean.setAnscount(rs.getLong("ANSCNT"));				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return bean;
	}
	
	/**
	 * 신청서사용현황 조회
	 */
	public List getReqsttcs(int gbn, String orggbn, String orggbn_dt, String frDate, String toDate, String range) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = null;
		
		try {
    		String rootid = appInfo.getRootid();
			StringBuffer selectQuery = new StringBuffer();
			
			if ( gbn == 0 ) {
				selectQuery.append("SELECT A.COLDEPTCD, REPLACE(B.DEPT_FULLNAME, (SELECT DEPT_FULLNAME FROM DEPT WHERE DEPT_ID = '" + rootid + "')||' ', '') COLDEPTNM, B.DEPT_RANK, COUNT(*)REQCNT , SUM(A1.ANSCOUNT) ANSCNT \n");
				selectQuery.append("FROM REQFORMMST A, (SELECT REQFORMNO, COUNT(*)ANSCOUNT FROM REQMST GROUP BY REQFORMNO) A1, DEPT B \n");
				selectQuery.append("WHERE A.REQFORMNO = A1.REQFORMNO \n");
				selectQuery.append("AND A.COLDEPTCD = B.DEPT_ID(+) \n");
				selectQuery.append("AND A.CRTDT BETWEEN ?||' 00:00:00' AND ?||' 24:59:59' \n");
				if (Utils.isNotNull(range)) {
					selectQuery.append("AND A.RANGE LIKE '"+range+"%' \n");
				}
				if( !"".equals(orggbn) ) {
					selectQuery.append("AND B.ORGGBN = '"+orggbn+"' \n");
				}
				if( !"".equals(orggbn_dt) ) {
					selectQuery.append("AND B.DEPT_ID = '"+orggbn_dt+"' \n");
				}
				selectQuery.append("GROUP BY A.COLDEPTCD, B.DEPT_FULLNAME, B.DEPT_RANK \n");
				selectQuery.append("ORDER BY B.DEPT_RANK \n");
			} else if ( gbn == 1 ) {
				selectQuery.append("SELECT A.COLDEPTCD, REPLACE(B.DEPT_FULLNAME, (SELECT DEPT_FULLNAME FROM DEPT WHERE DEPT_ID = '" + rootid + "')||' ', '') COLDEPTNM, A.CHRGUSRNM, B.DEPT_RANK, COUNT(*)REQCNT , SUM(A1.ANSCOUNT) ANSCNT \n");
				selectQuery.append("FROM REQFORMMST A, (SELECT REQFORMNO, COUNT(*)ANSCOUNT FROM REQMST GROUP BY REQFORMNO) A1, DEPT B \n");
				selectQuery.append("WHERE A.REQFORMNO = A1.REQFORMNO \n");
				selectQuery.append("AND A.COLDEPTCD = B.DEPT_ID(+) \n");
				selectQuery.append("AND A.CRTDT BETWEEN ?||' 00:00:00' AND ?||' 24:59:59' \n");
				if (Utils.isNotNull(range)) {
					selectQuery.append("AND A.RANGE LIKE '"+range+"%' \n");
				}
				if( !"".equals(orggbn) ) {
					selectQuery.append("AND B.ORGGBN = '"+orggbn+"' \n");
				}
				if( !"".equals(orggbn_dt) ) {
					selectQuery.append("AND B.DEPT_ID = '"+orggbn_dt+"' \n");
				}
				selectQuery.append("GROUP BY A.COLDEPTCD, B.DEPT_FULLNAME, A.CHRGUSRNM, B.DEPT_RANK \n");
				selectQuery.append("ORDER BY B.DEPT_RANK \n");
			} else {
				selectQuery.append("SELECT REPLACE(B.DEPT_FULLNAME, (SELECT DEPT_FULLNAME FROM DEPT WHERE DEPT_ID = '" + rootid + "')||' ', '') COLDEPTNM, TITLE, SUMMARY, STRDT, ENDDT, SUM(ANSCOUNT) ANSCNT \n");
				selectQuery.append("FROM REQFORMMST A, (SELECT REQFORMNO, COUNT(*)ANSCOUNT FROM REQMST GROUP BY REQFORMNO) A1, DEPT B \n");
				selectQuery.append("WHERE A.REQFORMNO = A1.REQFORMNO \n");
				selectQuery.append("AND A.COLDEPTCD = B.DEPT_ID(+) \n");
				selectQuery.append("AND A.CRTDT BETWEEN ?||' 00:00:00' AND ?||' 24:59:59' \n");
				if (Utils.isNotNull(range)) {
					selectQuery.append("AND A.RANGE LIKE '"+range+"%' \n");
				}
				if( !"".equals(orggbn) ) {
					selectQuery.append("AND B.ORGGBN = '"+orggbn+"' \n");
				}
				if( !"".equals(orggbn_dt) ) {
					selectQuery.append("AND B.DEPT_ID = '"+orggbn_dt+"' \n");
				}
				selectQuery.append("GROUP BY B.DEPT_FULLNAME, TITLE, SUMMARY, STRDT, ENDDT \n");
				selectQuery.append("ORDER BY B.DEPT_FULLNAME, TITLE, STRDT\n");
			}
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			//조회조건 설정
			pstmt.setString(1, frDate);
			pstmt.setString(2, toDate);

			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			int Count = 0;
			
			while (rs.next()) {
				ReqsttcsBean bean = new ReqsttcsBean();
				Count++;
				
				if ( gbn == 0 ) {
					bean.setSeqno(Count);
					bean.setDeptnm(rs.getString("COLDEPTNM"));
					bean.setReqcount(rs.getLong("REQCNT"));
					bean.setAnscount(rs.getLong("ANSCNT"));
				} else if ( gbn == 1 ) {
					bean.setSeqno(Count);
					bean.setDeptnm(rs.getString("COLDEPTNM"));
					bean.setUsernm(rs.getString("CHRGUSRNM"));
					bean.setReqcount(rs.getLong("REQCNT"));
				} else {
					bean.setSeqno(Count);
					bean.setDeptnm(rs.getString("COLDEPTNM"));
					bean.setTitle(rs.getString("TITLE"));
					bean.setSummary(rs.getString("SUMMARY"));
					bean.setStrdt(rs.getString("STRDT"));
					bean.setEnddt(rs.getString("ENDDT"));
					bean.setAnscount(rs.getLong("ANSCNT"));
				}
												
				list.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return list;
	}

	/**
	 * 신청서 총건수
	 */
	public ReqsttcsBean getReqsttcsTotalCount(String frDate, String toDate, String range) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReqsttcsBean bean = new ReqsttcsBean();
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*)REQCNT , SUM(A1.ANSCOUNT) ANSCNT " +
					   		   "FROM REQFORMMST A, (SELECT REQFORMNO, COUNT(*)ANSCOUNT FROM REQMST GROUP BY REQFORMNO) A1, DEPT B " +
					   		   "WHERE A.REQFORMNO = A1.REQFORMNO " +
					   		   "  AND A.COLDEPTCD = B.DEPT_ID(+) " +
							   "  AND A.CRTDT BETWEEN ?||' 00:00:00' AND ?||' 24:59:59' ");
			if (Utils.isNotNull(range)) {
				selectQuery.append("  AND A.RANGE LIKE '"+range+"%' ");
			}

			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			//조회조건 설정
			pstmt.setString(1, frDate);
			pstmt.setString(2, toDate);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				bean.setSeqno(0);
				bean.setReqcount(rs.getLong("REQCNT"));
				bean.setAnscount(rs.getLong("ANSCNT"));				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return bean;
	}			
	
}
