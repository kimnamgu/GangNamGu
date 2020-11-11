/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통처리현황 dao
 * 설명:
 */
package nexti.ejms.commtreat.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

public class CommTreatDAO {
	private static Logger logger = Logger.getLogger(CommTreatDAO.class);

	/**
	 * 취합대상정보 가져오기
	 * @param sysdocno
	 * @param tgtdeptcd
	 * @param sch_deptid
	 * @param sch_submitsate
	 * @param sch_userid
	 * @param sch_inputstate
	 * @return
	 * @throws Exception
	 */
	public List getFormationGroup(Connection con, String sessionId, int sysdocno, String userid, String tgtdeptcd, String sch_deptid, String sch_submitsate, String sch_userid, String sch_inputstate, boolean isOnlyDeptCount) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();

			if ( sysdocno == 0 ) {
				sql.append(" SELECT LEVEL, GRPGBN, SESSIONID, TGTDEPTCD, TGTDEPTNM, TGTDEPTFULLNM, SUBMITSTATE, SUBMITSTATENM, MODIFYYN,             \n"); 
				sql.append("        RETURNCOMMENT, INUSRSENDDT, APPNTUSRNM, SUBMITDT, PREDEPTCD, DEPT_RANK, INPUTUSRID, INPUTUSRNM,                  \n");
				sql.append("        CHRGUNITCD, CHRGUNITNM, INPUTSTATE, INPUTSTATENM, INPUTCOMP, ORGGBN, '' MSG_USER_SN                              \n");
				sql.append(" FROM (                                                                                                                  \n");
				sql.append("       SELECT *                                                                                                          \n");
				sql.append("       FROM (                                                                                                            \n");
				sql.append("       SELECT 2 GRPGBN, T.SESSIONID, TGTDEPTCD, TGTDEPTNM, NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), TGTDEPTNM) TGTDEPTFULLNM, \n");
				sql.append("              SUBMITSTATE, C1.CCDNAME SUBMITSTATENM, MODIFYYN,                                                           \n");
				sql.append("              RETURNCOMMENT, INUSRSENDDT, APPNTUSRNM, '' SUBMITDT, PREDEPTCD, DEPT_RANK,                                 \n");
				sql.append("              '' INPUTUSRID, '' INPUTUSRNM, 0 CHRGUNITCD, '' CHRGUNITNM, '' INPUTSTATE, '' INPUTSTATENM, '' INPUTCOMP, ORGGBN \n");
				sql.append("       FROM DEPT D, TGTDEPT_TEMP T, INPUTUSR_TEMP I, CCD C1, CCD C2                                                      \n");
				sql.append("       WHERE D.DEPT_ID = T.TGTDEPTCD                                                                                     \n");
				sql.append("       AND T.SESSIONID = I.SESSIONID(+) AND T.TGTDEPTCD = I.TGTDEPT(+)                                                   \n");
				sql.append("       AND T.SUBMITSTATE = C1.CCDSUBCD(+) AND C1.CCDCD(+) = '004'                                                        \n");
				sql.append("       AND I.INPUTSTATE = C2.CCDSUBCD(+) AND C2.CCDCD(+) = '005'                                                         \n");
				sql.append("       AND T.SESSIONID LIKE ?                                                                                               \n");
				sql.append("       GROUP BY T.SESSIONID, TGTDEPTCD, TGTDEPTNM,  DEPT_FULLNAME, SUBMITSTATE, C1.CCDNAME, MODIFYYN,                    \n");
				sql.append("              RETURNCOMMENT, INUSRSENDDT, APPNTUSRNM, PREDEPTCD, DEPT_RANK, ORGGBN				                         \n");
				sql.append("       UNION ALL                                                                                                         \n");
				sql.append("       SELECT 3 GRPGBN, T.SESSIONID, TGTDEPTCD||'U' TGTDEPTCD,         		                                             \n");
				sql.append("       	 			TGTDEPTNM, NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), TGTDEPTNM) TGTDEPTFULLNM,                             \n");
				sql.append("              SUBMITSTATE, C1.CCDNAME SUBMITSTATENM, MODIFYYN,                                                           \n");
				sql.append("              RETURNCOMMENT, INUSRSENDDT, APPNTUSRNM, '' SUBMITDT, TGTDEPTCD, DEPT_RANK,                                 \n");
				sql.append("              INPUTUSRID, INPUTUSRNM, CHRGUNITCD, CHRGUNITNM, INPUTSTATE, C2.CCDNAME INPUTSTATENM, INPUTCOMP, ORGGBN     \n");
				sql.append("       FROM  DEPT D, TGTDEPT_TEMP T, INPUTUSR_TEMP I, CCD C1, CCD C2                                                     \n");
				sql.append("       WHERE D.DEPT_ID = T.TGTDEPTCD                                                                                     \n");
				sql.append("       AND T.SESSIONID = I.SESSIONID AND T.TGTDEPTCD = I.TGTDEPT(+)                                                      \n");
				sql.append("       AND T.SUBMITSTATE = C1.CCDSUBCD(+) AND C1.CCDCD(+) = '004'                                                        \n");
				sql.append("       AND I.INPUTSTATE = C2.CCDSUBCD(+) AND C2.CCDCD(+) = '005'                                                         \n");
				sql.append("       AND T.SESSIONID LIKE ?                                                                                               \n");
				sql.append("       UNION ALL                                                                                                         \n");
				sql.append("       SELECT 1 GRPGBN, ?, ?, '', '00',                                                                                  \n");
				sql.append("              NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL                       \n");
				sql.append("       FROM DUAL) A                        										                                         \n");
				sql.append(" )                                                                                                                       \n");
				sql.append(" WHERE TGTDEPTCD LIKE (? || '%')                  	                                                                     \n");
				sql.append(" AND NVL(INPUTUSRID, ' ') LIKE NVL(?, '%')                                                                               \n");
				if ( !"".equals(sch_submitsate) ) {
				sql.append("AND SUBMITSTATE IN ('" + sch_submitsate.replaceAll("[,]", "','") + "')                                        			 \n");
				}
				if ( !"".equals(sch_inputstate) ) {
				sql.append("AND INPUTSTATE IN ('" + sch_inputstate.replaceAll("[,]", "','") + "')                                         			 \n");
				}
				sql.append("START WITH TGTDEPTCD = ?                                                                                      			 \n");
				sql.append("CONNECT BY PRIOR TGTDEPTCD = PREDEPTCD                                                                        			 \n");
				sql.append("ORDER SIBLINGS BY ORGGBN, GRPGBN DESC, DEPT_RANK                                                               			 \n");
				
			} else {
				if ( isOnlyDeptCount ) {
				sql.append("SELECT DISTINCT LEVEL, GRPGBN, SYSDOCNO, TGTDEPTCD, ORGGBN     	                                                  		 \n");
				} else {
				sql.append(" SELECT LEVEL, GRPGBN, SYSDOCNO, TGTDEPTCD, TGTDEPTNM, TGTDEPTFULLNM, SUBMITSTATE, SUBMITSTATENM, MODIFYYN,              \n"); 
				sql.append("        RETURNCOMMENT, INUSRSENDDT, APPNTUSRNM, SUBMITDT, PREDEPTCD, DEPT_RANK, INPUTUSRID, INPUTUSRNM,                  \n");
				sql.append("        CHRGUNITCD, CHRGUNITNM, INPUTSTATE, INPUTSTATENM, INPUTCOMP, ORGGBN, MSG_USER_SN                                 \n");
				}
				sql.append(" FROM (                                                                                                                  \n");
				sql.append("       SELECT *                                                                                                          \n");
				sql.append("       FROM (                                                                                                            \n");
				sql.append("       SELECT 2 GRPGBN, T.SYSDOCNO, TGTDEPTCD, TGTDEPTNM, NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), TGTDEPTNM) TGTDEPTFULLNM,  \n");
				sql.append("              SUBMITSTATE, C1.CCDNAME SUBMITSTATENM, MODIFYYN,                                                           \n");
				sql.append("              RETURNCOMMENT, INUSRSENDDT, APPNTUSRNM, SUBMITDT, PREDEPTCD, DEPT_RANK,                                    \n");
				sql.append("              '' INPUTUSRID, '' INPUTUSRNM, 0 CHRGUNITCD, '' CHRGUNITNM, '' INPUTSTATE, '' INPUTSTATENM, '' INPUTCOMP, ORGGBN \n");
				sql.append("       FROM DEPT D, TGTDEPT T, INPUTUSR I, CCD C1, CCD C2                                                                \n");
				sql.append("       WHERE D.DEPT_ID = T.TGTDEPTCD                                                                                     \n");
				sql.append("       AND T.SYSDOCNO = I.SYSDOCNO(+) AND T.TGTDEPTCD = I.TGTDEPT(+)                                                     \n");
				sql.append("       AND T.SUBMITSTATE = C1.CCDSUBCD(+) AND C1.CCDCD(+) = '004'                                                        \n");
				sql.append("       AND I.INPUTSTATE = C2.CCDSUBCD(+) AND C2.CCDCD(+) = '005'                                                         \n");
				sql.append("       AND T.SYSDOCNO = ?                                                                                                \n");
				sql.append("       GROUP BY T.SYSDOCNO, TGTDEPTCD, TGTDEPTNM,  DEPT_FULLNAME, SUBMITSTATE, C1.CCDNAME, MODIFYYN,                     \n");
				sql.append("              RETURNCOMMENT, INUSRSENDDT, APPNTUSRNM, SUBMITDT, PREDEPTCD, DEPT_RANK, ORGGBN                             \n");
				sql.append("       UNION ALL                                                                                                         \n");
				sql.append("       SELECT 3 GRPGBN, T.SYSDOCNO, TGTDEPTCD||'U' TGTDEPTCD,                                                            \n");
				sql.append("       	 			TGTDEPTNM, NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), TGTDEPTNM) TGTDEPTFULLNM,                             \n");
				sql.append("              SUBMITSTATE, C1.CCDNAME SUBMITSTATENM, MODIFYYN,                                                           \n");
				sql.append("              RETURNCOMMENT, INUSRSENDDT, APPNTUSRNM, SUBMITDT, TGTDEPTCD, DEPT_RANK,                                    \n");
				sql.append("              INPUTUSRID, INPUTUSRNM, CHRGUNITCD, CHRGUNITNM, INPUTSTATE, C2.CCDNAME INPUTSTATENM, INPUTCOMP, ORGGBN     \n");
				sql.append("       FROM  DEPT D, TGTDEPT T, INPUTUSR I, CCD C1, CCD C2                                                               \n");
				sql.append("       WHERE D.DEPT_ID = T.TGTDEPTCD                                                                                     \n");
				sql.append("       AND T.SYSDOCNO = I.SYSDOCNO AND T.TGTDEPTCD = I.TGTDEPT(+)                                                        \n");
				sql.append("       AND T.SUBMITSTATE = C1.CCDSUBCD(+) AND C1.CCDCD(+) = '004'                                                        \n");
				sql.append("       AND I.INPUTSTATE = C2.CCDSUBCD(+) AND C2.CCDCD(+) = '005'                                                         \n");
				sql.append("       AND T.SYSDOCNO = ?                                                                                                \n");
				sql.append("       UNION ALL                                                                                                         \n");
				sql.append("       SELECT 1 GRPGBN, SYSDOCNO, 'M_' || COLDEPTCD, COLDEPTNM, '00',                                                    \n");
				sql.append("              NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL                       \n");
				sql.append("       FROM (SELECT * FROM DOCMST WHERE SYSDOCNO = ?)) A                                                                 \n");
				sql.append(" ), (SELECT USER_ID MSG_USER_ID, USER_SN MSG_USER_SN FROM USR)                                                                                                                       \n");
				sql.append(" WHERE INPUTUSRID = MSG_USER_ID(+)                 	                                                                     \n");
				sql.append(" AND TGTDEPTCD LIKE (? || '%')                  	                                                                     \n");
				sql.append(" AND NVL(INPUTUSRID, ' ') LIKE NVL(?, '%')                                                                               \n");
				if ( !"".equals(sch_submitsate) ) {
				if ( !"all".equals(sch_submitsate) ) 
				sql.append("AND SUBMITSTATE IN ('" + sch_submitsate.replaceAll("[,]", "','") + "')                                        			 \n");
				}
				if ( !"".equals(sch_inputstate) ) {
				sql.append("AND INPUTSTATE IN ('" + sch_inputstate.replaceAll("[,]", "','") + "')                                         			 \n");
				}
				if ( isOnlyDeptCount ) {
				sql.append("AND GRPGBN IN (1,2)													                                        			 \n");
				}
				sql.append("START WITH TGTDEPTCD = ?                                                                                      			 \n");
				sql.append("CONNECT BY PRIOR TGTDEPTCD = PREDEPTCD                                                                        			 \n");
				if ( !isOnlyDeptCount ) {
				sql.append("ORDER SIBLINGS BY ORGGBN, GRPGBN DESC, DEPT_RANK                                                               			 \n");
				}
			}

			String rootTgtdeptcd = CommTreatManager.instance().getPreDeptcd(con, sysdocno, tgtdeptcd, false);
			
			String coldeptcd = tgtdeptcd;
			if ( sysdocno > 0 ) {
				coldeptcd = ColldocManager.instance().getColldoc(sysdocno).getColdeptcd();
			}
			if ( sch_deptid.equals(coldeptcd) ) {
				sch_deptid = CommTreatManager.instance().getPreDeptcd(con, sysdocno, sch_deptid, false);
			}
			
			int substring = 0;
			if ( !"".equals(Utils.nullToEmptyString(userid)) ) {
				substring = commfunction.getDeptFullNameSubstringIndex(userid);
			}

			pstmt = con.prepareStatement(sql.toString());

			int idx = 0;
			if ( sysdocno == 0 ) {
				pstmt.setInt(++idx, substring);
				pstmt.setString(++idx, sessionId);
				pstmt.setInt(++idx, substring);
				pstmt.setString(++idx, sessionId);
				pstmt.setString(++idx, sessionId);
				pstmt.setString(++idx, rootTgtdeptcd);
				pstmt.setString(++idx, sch_deptid);
				pstmt.setString(++idx, sch_userid);
				pstmt.setString(++idx, rootTgtdeptcd);
			} else {
				pstmt.setInt(++idx, substring);
				pstmt.setInt(++idx, sysdocno);
				pstmt.setInt(++idx, substring);
				pstmt.setInt(++idx, sysdocno);
				pstmt.setInt(++idx, sysdocno);
				pstmt.setString(++idx, sch_deptid);
				pstmt.setString(++idx, sch_userid);
				pstmt.setString(++idx, rootTgtdeptcd);
			}
			rs = pstmt.executeQuery();

			while ( rs.next() ) {
				CommTreatBean ctbean = new CommTreatBean();
				
				if ( isOnlyDeptCount ) {
					if ( rs.getInt("LEVEL") == 1 ) continue;
					ctbean.setSysdocno(sysdocno);
					ctbean.setTgtdeptcd(Utils.nullToEmptyString(rs.getString("TGTDEPTCD")));
				} else {
					ctbean.setLevel(rs.getInt("LEVEL"));
					ctbean.setGrpgbn(rs.getInt("GRPGBN"));
					ctbean.setSessionid(sessionId);
					ctbean.setSysdocno(sysdocno);
					ctbean.setTgtdeptcd(Utils.nullToEmptyString(rs.getString("TGTDEPTCD")));
					ctbean.setTgtdeptnm(Utils.nullToEmptyString(rs.getString("TGTDEPTNM")));
					ctbean.setTgtdeptfullnm(Utils.nullToEmptyString(rs.getString("TGTDEPTFULLNM")));
					ctbean.setSubmitstate(Utils.nullToEmptyString(rs.getString("SUBMITSTATE")));
					ctbean.setSubmitstatenm(Utils.nullToEmptyString(rs.getString("SUBMITSTATENM")));
					ctbean.setModifyyn(Utils.nullToEmptyString(rs.getString("MODIFYYN")));
					ctbean.setReturncomment(Utils.nullToEmptyString(rs.getString("RETURNCOMMENT")));
					ctbean.setInusrsenddt(Utils.nullToEmptyString(rs.getString("INUSRSENDDT")));
					ctbean.setAppntusrnm(Utils.nullToEmptyString(rs.getString("APPNTUSRNM")));
					ctbean.setSubmitdt(Utils.nullToEmptyString(rs.getString("SUBMITDT")));
					ctbean.setPredeptcd(Utils.nullToEmptyString(rs.getString("PREDEPTCD")));
					ctbean.setInputusrid(Utils.nullToEmptyString(rs.getString("INPUTUSRID")));
					ctbean.setInputusrnm(Utils.nullToEmptyString(rs.getString("INPUTUSRNM")));
					ctbean.setChrgunitcd(rs.getInt("CHRGUNITCD"));
					ctbean.setChrgunitnm(Utils.nullToEmptyString(rs.getString("CHRGUNITNM")));
					ctbean.setInputstate(Utils.nullToEmptyString(rs.getString("INPUTSTATE")));
					ctbean.setInputstatenm(Utils.nullToEmptyString(rs.getString("INPUTSTATENM")));
					ctbean.setInputcomp(Utils.nullToEmptyString(rs.getString("INPUTCOMP")));
					
					ctbean.setInputusersn(Utils.nullToEmptyString(rs.getString("MSG_USER_SN")));
				}
				
				if ( result == null ) result = new ArrayList();
				result.add(ctbean);
			}			
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 이전대상부서 가져오기
	 * @param con
	 * @param sysdocno
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public String getPreDeptcd(Connection con, int sysdocno, String deptid, boolean isDeliveried) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {
			if ( sysdocno <= 0 ) {
				result = "M_" + deptid;
			} else {
				String coldeptcd = ColldocManager.instance().getColldoc(sysdocno).getColdeptcd();
				if ( deptid.equals(coldeptcd) && !isDeliveried ) {
					result = "M_" + coldeptcd;
				} else {
					StringBuffer sql = new StringBuffer();
					sql.append("SELECT TGTDEPTCD                                                   \n");
					sql.append("FROM (SELECT TGTDEPTCD                                             \n");
					sql.append("      FROM TGTDEPT T, (SELECT DEPT_ID                              \n");
					sql.append("                       FROM DEPT                                   \n");
					sql.append("                       START WITH DEPT_ID = ?                      \n");
					sql.append("                       CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID) D \n");
					sql.append("      WHERE T.SYSDOCNO = ?                                         \n");
					sql.append("      AND T.TGTDEPTCD = D.DEPT_ID                                  \n");
					if ( isDeliveried ) {
						sql.append("      AND T.SUBMITSTATE < '02'                                 \n");
					}
					sql.append("      ORDER BY ROWNUM DESC)                                        \n");
					sql.append("WHERE ROWNUM = 1                                                   \n");
					
					pstmt = con.prepareStatement(sql.toString());
					int idx = 0;
					pstmt.setString(++idx, deptid);
					pstmt.setInt(++idx, sysdocno);
					
					rs = pstmt.executeQuery();
					
					if ( rs.next() ) {
						result = rs.getString("TGTDEPTCD");
					}
				}
				
				if ( isDeliveried ) {
					StringBuffer sql = new StringBuffer();
					sql.append("SELECT COUNT(*) \n");
					sql.append("FROM TGTDEPT \n");
					sql.append("WHERE SYSDOCNO = ? \n");
					sql.append("AND TGTDEPTCD = ? \n");
					
					ConnectionManager.close(pstmt, rs);
					pstmt = con.prepareStatement(sql.toString());
					int idx = 0;
					pstmt.setInt(++idx, sysdocno);
					pstmt.setString(++idx, deptid);
					
					rs = pstmt.executeQuery();
					
					if ( rs.next() && rs.getInt(1) > 0 ) {
						result = deptid;
					}
				}
				
			}
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/** 
	 * 문서상태 변경
	 * @param sysdocno : 시스템 문서번호
	 * @param usrid : 사용자ID
	 * @param docstate : 문서상태(01 : 작성중, 02 : 검토대기, 03 : 승인대기, 04 : 취합진행, 05 : 마감대기, 06 : 마감완료)
	 * @return int : 처리결과
	 * @throws Exception : SQL관련 예외
	 */
	public int updateDocState(int sysdocno, String usrid, String docstate) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("\n UPDATE DOCMST                                              ");
		updateQuery.append("\n   SET DOCSTATE = ?                                         ");
		updateQuery.append("\n     , UPTDT    = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') ");
		updateQuery.append("\n     , UPTUSRID = ?                                         ");
		updateQuery.append("\n WHERE SYSDOCNO = ?                                         ");
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
			
			pstmt.setString(1, docstate);
			pstmt.setString(2, usrid);
			pstmt.setInt(3, sysdocno);
				
			result = pstmt.executeUpdate();
		} catch(Exception e){
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt);
		}
		
		return result;
	}
	
	/** 
	 * 제출상태 변경
	 * @param sysdocno : 시스템 문서번호
	 * @param usrid : 사용자ID
	 * @param deptcd : 부서코드
	 * @param submitstate : 제출상태(01 : 배부대기, 02 : 입력진행, 03 : 검토대기, 04 : 승인대기, 05 : 제출완료, 06 : 반송)
	 * @return int : 처리결과
	 * @throws Exception : SQL관련 예외
	 */
	public int updateSubmitState(int sysdocno, String deptcd, String usrid, String submitstate) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("UPDATE TGTDEPT \n");
		updateQuery.append("   SET SUBMITSTATE = ? \n");
		updateQuery.append("     , SUBMITDT	   = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') \n");
		updateQuery.append("     , UPTDT       = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') \n");
		updateQuery.append("     , UPTUSRID    = ? \n");
		updateQuery.append(" WHERE SYSDOCNO    = ? \n");
		updateQuery.append("   AND TGTDEPTCD   = ? \n");
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
			
			pstmt.setString(1, submitstate);
			pstmt.setString(2, usrid);
			pstmt.setInt(3, sysdocno);
			pstmt.setString(4, deptcd);
				
			result = pstmt.executeUpdate();
		} catch(Exception e){
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt);
		}
		
		return result;
	}
		
	/**
	 * 제출부서 결재 여부 확인
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return String : 승인/검토 결재 여부
	 * @throws Exception 
	 */
	public int IsDocSanctgt(int sysdocno, String deptcd, String userid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int result = 0;
		
		try {
			String sql = 
				"SELECT COUNT(*) " +
				"FROM SANCTGT " +
				"WHERE SYSDOCNO = ? " +
				"  AND TGTDEPTCD = ? " +
				"  AND INPUTUSRID = ? ";
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
			pstmt.setString(3, userid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next() == true) {
				if(rs.getInt(1) > 0) {	
					sql = 
						"SELECT COUNT(*) " +
						"FROM SANCTGT " +
						"WHERE SYSDOCNO = ? " +
						"  AND TGTDEPTCD = ? " +
						"  AND INPUTUSRID = ? " +
						"  AND SANCYN = '1' ";
					
					try {rs.close();} catch(Exception e) {}
					try {pstmt.close();} catch(Exception e) {}
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, sysdocno);
					pstmt.setString(2, deptcd);
					pstmt.setString(3, userid);
					
					rs = pstmt.executeQuery();
					
					if(rs.next() == true) {
						result = rs.getInt(1);
					}				
				} else {
					result = -1;
				}
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
	
	/**
	 * 배부하기 상세 - 입력담당자 데이터 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return String : 입력담당자명
	 * @throws Exception 
	 */
	public String getInputUsrnm(int sysdocno, String deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String inputusrnm = "";
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT INPUTUSRNM \n");
			selectQuery.append("  FROM INPUTUSR \n");
			selectQuery.append(" WHERE SYSDOCNO = ? \n");
			selectQuery.append("   AND TGTDEPT  = ? ");
			selectQuery.append(" ORDER BY INPUTUSRNM DESC ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
									
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				if(inputusrnm != "") {
					inputusrnm = inputusrnm + ", " + rs.getString("INPUTUSRNM");
				} else {
					inputusrnm = inputusrnm + rs.getString("INPUTUSRNM");
				}
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return inputusrnm;
	}
	
	/**
	 * 제출부서 결재선 승인/검토 데이터 가져오기
	 * 
	 * @param gubun : 승인/검토 구분(1 : 검토, 2 : 승인)
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return String : 결재선 승인/검토자
	 * @throws Exception 
	 */
	public String getApprovalUsrnm(String gubun, int sysdocno, String deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String approvalusrnm = "";
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT SANCUSRNM \n");
			selectQuery.append("  FROM SANCTGT \n");
			selectQuery.append(" WHERE SYSDOCNO = ? \n");
			selectQuery.append("   AND TGTDEPTCD = ? \n");
			selectQuery.append("   AND GUBUN = ? ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
			pstmt.setString(3, gubun);
									
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				if(approvalusrnm != "") {
					approvalusrnm = approvalusrnm + ", " + rs.getString("SANCUSRNM");
				} else {
					approvalusrnm = approvalusrnm + rs.getString("SANCUSRNM");
				}
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return approvalusrnm;
	}
	
	/**
	 * 취합부서 결재선 승인/검토 데이터 가져오기
	 * 
	 * @param gubun : 승인/검토 구분(1 : 검토, 2: 승인)
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * @param user_id : 사용자ID
	 * 
	 * @return String : 결재선 승인/검토자
	 * @throws Exception 
	 */
	public String getApprovalUsrnm1(String gubun, int sysdocno, String deptcd, String user_id) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String approvalusrnm = "";
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(sysdocno == 0){
			selectQuery.append("\n SELECT SANCUSRNM, 	");
			selectQuery.append("\n        SANCYN		");
			selectQuery.append("\n   FROM SANCCOL_TEMP	");
			selectQuery.append("\n  WHERE USER_ID = ? 	");
			selectQuery.append("\n    AND GUBUN = ? 	");
		}else{
			selectQuery.append("\n SELECT SANCUSRNM, 	");
			selectQuery.append("\n        SANCYN		");
			selectQuery.append("\n   FROM SANCCOL 		");
			selectQuery.append("\n  WHERE SYSDOCNO = ? 	");
			selectQuery.append("\n    AND GUBUN = ? 	");
		}
		
		try {
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(sysdocno ==0){
				pstmt.setString(1, user_id);
				pstmt.setString(2, gubun);
			}else{
				pstmt.setInt(1, sysdocno);
				pstmt.setString(2, gubun);
			}
						
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String sancynnm = "";
				if(rs.getString("SANCYN").equals("1")){
					sancynnm = "(결재완료)";
				}else{
					sancynnm = "(결재대기)";
				}
				if(approvalusrnm != "") {
					approvalusrnm = approvalusrnm + ", " + rs.getString("SANCUSRNM")+"<span class='style4'>" +sancynnm +"</span>";
				} else {
					approvalusrnm = approvalusrnm + rs.getString("SANCUSRNM")+"<span class='style4'>" +sancynnm +"</span>";
				}
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return approvalusrnm;
	}
	
	/**
	 * 제출현황목록 가져오기
	 * @param sysdocno : 시스템문서번호
	 * @param gbn : 제출구분 - 대상전체(all),완료(comp),미제출(nocomp)
	 * @return List 제출대상목록(CollprocDeptStatusBean)
	 * @throws Exception 
	 */
	public List getTgtdeptList(int sysdocno, String gbn) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		List list = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT A.TGTDEPTCD, " +
					           "       B1.DEPT_NAME TGTDEPTNM, " +
					           "       B1.DEPT_TEL, " +
					           "       DECODE(A.SUBMITSTATE,'05','Y','N')SUBMITYN, " +
					           "       A.SUBMITSTATE, " +
					           "       A.SUBMITDT, " +
					           "       C.CCDNAME SUBMITSTATENM, " +
					           "       NVL(A.MODIFYYN,'0')MODIFYYN, " +
					           "       NVL(A.RETURNCOMMENT,'사유없음')RETURNCOMMENT " +
					           "FROM TGTDEPT A, DEPT B1, CCD C " +
					           "WHERE A.TGTDEPTCD = B1.DEPT_ID " +
					           "  AND C.CCDCD = '004' AND A.SUBMITSTATE = C.CCDSUBCD " +
					           "  AND A.SYSDOCNO = ? ");
			if ("comp".equalsIgnoreCase(gbn)) {				//제출완료건만
				selectQuery.append("  AND SUBMITSTATE IN ('05') ");
			} else if ("nocomp".equalsIgnoreCase(gbn)) {	//미제출건만
				selectQuery.append("  AND SUBMITSTATE NOT IN ('05') ");
			}
			selectQuery.append("ORDER BY B1.DEPT_RANK ");
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			list = new ArrayList();
			
			while ( rs.next() ){
				CommTreatDeptStatusBean bean = new CommTreatDeptStatusBean();
				
				bean.setModifyyn(rs.getString("MODIFYYN"));
				bean.setReturncomment(rs.getString("RETURNCOMMENT"));
				bean.setSubmitdt(rs.getString("SUBMITDT"));
				bean.setSubmitstate(rs.getString("SUBMITSTATE"));
				bean.setSubmitstatenm(rs.getString("SUBMITSTATENM"));
				bean.setSubmityn(rs.getString("SUBMITYN"));
				bean.setTgtdeptcd(rs.getString("TGTDEPTCD"));
				bean.setTgtdeptnm(rs.getString("TGTDEPTNM"));
				bean.setTgtdepttel(rs.getString("DEPT_TEL"));
				
				list.add(bean);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn, pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return list;
	}		
	
	/** 
	 * 제출부서 수정가능여부 변경	
	 * @param sysdocno : 시스템 문서번호
	 * @param tgtdeptcd : 부서코드
	 * @param modifyyn : 수정가능여부
	 * @return 없음
	 * @throws Exception 
	 */
	public void updateTgtdept(int sysdocno, String tgtdeptcd, String modifyyn) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("UPDATE TGTDEPT \n");
		updateQuery.append("   SET MODIFYYN = NVL('"+modifyyn+"','0') \n");
		updateQuery.append(" WHERE SYSDOCNO    = ? \n");
		updateQuery.append("   AND TGTDEPTCD   = ? ");
		
		try{
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(updateQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, tgtdeptcd);
				
			pstmt.executeUpdate();
		} catch(Exception e){
			logger.error("ERROR",e);
			ConnectionManager.close(conn, pstmt);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt);
		}
		
		return;
	}

	/**
	 * 문서양식 수정권한 가져오기
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return String : 수정권한 여부 
	 * @throws Exception 
	 */
	public String getTgtModifyYn(int sysdocno, String deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String modifyyn = "0";
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT NVL(MODIFYYN,'0')MODIFYYN ");
		selectQuery.append("\n   FROM TGTDEPT     ");
		selectQuery.append("\n  WHERE SYSDOCNO  = ?          ");
		selectQuery.append("\n    AND TGTDEPTCD = ?          ");
		
		try {
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				modifyyn = rs.getString("MODIFYYN");
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return modifyyn;
	}
	
	/**
	 * 배부일시 데이터 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * 
	 * @return String : 배부일시
	 * @throws Exception 
	 */
	public String getTreatStatusDeliveryDT(int sysdocno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT DECODE(DELIVERYDT,'','',SUBSTR(DELIVERYDT,1,4)||'년'||SUBSTR(DELIVERYDT,6,2)||'월'||SUBSTR(DELIVERYDT,9,2)||'일 '||SUBSTR(DELIVERYDT,12,2)||'시'||SUBSTR(DELIVERYDT,15,2)||'분') DELIVERYDT \n");
			selectQuery.append("  FROM DOCMST \n");
			selectQuery.append(" WHERE SYSDOCNO = ? ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
									
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				result = rs.getString("DELIVERYDT");
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
	
	/**
	 * 제출부서 제출상태 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return String : 제출상태
	 * @throws Exception 
	 */
	public String getTgtdeptState(int sysdocno, String deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n SELECT SUBMITSTATE   ");
			selectQuery.append("\n   FROM TGTDEPT       ");
			selectQuery.append("\n  WHERE SYSDOCNO  = ? ");
			selectQuery.append("\n    AND TGTDEPTCD = ? ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
									
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				result = rs.getString("SUBMITSTATE");
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
	 * 문서상태 조회
	 * 
	 * @param sysdocno
	 * 
	 * @return CommTreatBean : 문서상태를 가지고 있는 Bean
	 * @throws Exception 
	 */
	public CommTreatBean getDocState(int sysdocno) throws Exception{
		Connection conn = null;
	    ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		CommTreatBean Bean = new CommTreatBean();
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT A.DOCSTATE, B.CCDNAME,	\n");
			selectQuery.append("       SUBSTR(A.ENDDT,1,4)||'년'||SUBSTR(A.ENDDT,6,2)||'월'||SUBSTR(A.ENDDT,9,2)||'일 '||SUBSTR(A.ENDDT,12,2)||'시'||SUBSTR(A.ENDDT,15,2)||'분' ENDDT, \n");
			selectQuery.append("       DECODE(A.DELIVERYDT,'','',SUBSTR(A.DELIVERYDT,1,4)||'년'||SUBSTR(A.DELIVERYDT,6,2)||'월'||SUBSTR(A.DELIVERYDT,9,2)||'일 '||SUBSTR(A.DELIVERYDT,12,2)||'시'||SUBSTR(A.DELIVERYDT,15,2)||'분') DELIVERYDT \n");
			selectQuery.append("  FROM DOCMST A, CCD B 			\n");
			selectQuery.append(" WHERE A.DOCSTATE = B.CCDSUBCD	\n");
			selectQuery.append("   AND A.SYSDOCNO = ?			\n");
			selectQuery.append("   AND B.CCDCD = '003'			\n");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				Bean.setDocstate(rs.getString("DOCSTATE"));
				Bean.setDocstatenm(rs.getString("CCDNAME"));
				Bean.setEnddt(rs.getString("ENDDT"));
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
	 * 공통함수 - 입력담당자 중 입력완료한 담당자 데이터 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return String : 입력완료 담당자명 
	 * @throws Exception 
	 */
	public List inputUsr(int sysdocno, String deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List inputList = null;
				
		try {
			StringBuffer selectQuery = new StringBuffer();
			/*[USR_EXT] 테이블 삭제
			selectQuery.append("SELECT (SELECT CHRGUNITNM FROM CHRGUNIT WHERE CHRGUNITCD = A.CHRGUNITCD AND DEPT_ID = B.TGTDEPT) CHRGUNITNM \n");
			selectQuery.append("     , B.INPUTUSRNM \n");
			selectQuery.append("  FROM USR_EXT A, INPUTUSR B \n");
			selectQuery.append(" WHERE A.USER_ID  = B.INPUTUSRID \n");
			selectQuery.append("   AND B.SYSDOCNO = ? \n");
			selectQuery.append("   AND B.TGTDEPT  = ? \n");
			selectQuery.append("   AND B.INPUTSTATE IN ('03', '04') \n");
			selectQuery.append(" ORDER BY CHRGUNITNM, B.INPUTUSRNM ");
			*/

			selectQuery.append("SELECT CHRGUNITNM, INPUTUSRNM \n");
			selectQuery.append("  FROM INPUTUSR \n");
			selectQuery.append(" WHERE SYSDOCNO = ? \n");
			selectQuery.append("   AND TGTDEPT  = ? \n");
			selectQuery.append("   AND INPUTSTATE IN ('03', '04') \n");
			selectQuery.append(" ORDER BY CHRGUNITNM, INPUTUSRNM ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
									
			rs = pstmt.executeQuery();
			
			inputList = new ArrayList();			
			while(rs.next()) {				
				String[] info = new String[2];
				
				info[0] = rs.getString("CHRGUNITNM");
				info[1] = rs.getString("INPUTUSRNM");
				
				inputList.add(info);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return inputList;
	}
	
	/**
	 * 공통함수 - 입력담당자 중 미입력 담당자 데이터 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return String : 미입력담당자명 
	 * @throws Exception 
	 */
	public List inputUsrX(int sysdocno, String deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List inputXList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT INPUTUSRNM \n");
			selectQuery.append("  FROM INPUTUSR \n");
			selectQuery.append(" WHERE SYSDOCNO = ? \n");
			selectQuery.append("   AND TGTDEPT  = ? \n");
			selectQuery.append("   AND INPUTSTATE IN ('01', '02', '05') ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
									
			rs = pstmt.executeQuery();
			
			inputXList = new ArrayList();
			while(rs.next()) {
				InputUsrBean usr = new InputUsrBean();
				usr.setInputusrnm(rs.getString("INPUTUSRNM"));	
				
				inputXList.add(usr);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return inputXList;
	}
	
	/**
	 * 입력하기 상세(처리현황) - 입력담당자의 담당단위명과 담당자 성명 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param usrid : 사용자ID
	 * @param deptcd : 부서코드
	 * 
	 * @return CommTreatBean : 입력담당자의 담당단위명과 담당자 성명을 담고 있는 Bean
	 * @throws Exception 
	 */
	public CommTreatBean getInputChrgunitName(int sysdocno, String usrid, String deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		CommTreatBean treatBean = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT (SELECT CHRGUNITCD FROM CHRGUNIT WHERE CHRGUNITCD = A.CHRGUNITCD AND DEPT_ID = B.TGTDEPT) CHRGUNITCD \n");
			selectQuery.append("     , B.TGTDEPT, B.INPUTUSRNM \n");
			selectQuery.append("  FROM USR A, INPUTUSR B \n");
			selectQuery.append(" WHERE A.USER_ID    = B.INPUTUSRID \n");
			selectQuery.append("   AND B.SYSDOCNO   = ? \n");
			selectQuery.append("   AND B.INPUTUSRID = ? \n");
			selectQuery.append("   AND B.TGTDEPT    = ? ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, usrid);
			pstmt.setString(3, deptcd);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				treatBean = new CommTreatBean();
				
				treatBean.setChrgunitcd(rs.getInt("CHRGUNITCD"));
				treatBean.setInputdeptcd(rs.getString("TGTDEPT"));
				treatBean.setAppntusrnm(rs.getString("INPUTUSRNM"));
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return treatBean;
	}
	
	/**
	 * 입력완료 상세 - 진행상태/입력완료일시/입력완료 사유 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * 
	 * @return CommTreatBean : 진행상태/입력완료일시/입력완료 사유를 담고 있는 Bean
	 * @throws Exception 
	 */
	public CommTreatBean getInputCompTreatData(int sysdocno, String deptcd, String usrid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		CommTreatBean treatBean = new CommTreatBean();
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT (SELECT CCDNAME FROM CCD WHERE CCDCD = '004' AND CCDSUBCD = A.SUBMITSTATE) SUBMITSTATE \n");
			selectQuery.append("     , (SELECT CCDNAME FROM CCD WHERE CCDCD = '005' AND CCDSUBCD = B.INPUTSTATE) INPUTSTATE \n");
			selectQuery.append("     , SUBSTR(B.INPUTCOMP,1,4)||'년'||SUBSTR(B.INPUTCOMP,6,2)||'월'||SUBSTR(B.INPUTCOMP,9,2)||'일 '||SUBSTR(B.INPUTCOMP,12,2)||'시'||SUBSTR(B.INPUTCOMP,15,2)||'분' INPUTCOMP, INPUTUSRNM \n");
			selectQuery.append("  FROM TGTDEPT A, INPUTUSR B \n");
			selectQuery.append(" WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("   AND A.TGTDEPTCD = B.TGTDEPT \n");
			selectQuery.append("   AND A.SYSDOCNO = ? \n");
			selectQuery.append("   AND A.TGTDEPTCD = ? \n");
			selectQuery.append("   AND B.INPUTUSRID = ? ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
			pstmt.setString(3, usrid);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				treatBean.setSubmitstate(rs.getString("SUBMITSTATE"));
				treatBean.setInputstate(rs.getString("INPUTSTATE"));
				treatBean.setInputcomp(rs.getString("INPUTCOMP"));				
				treatBean.setInputusrnm(rs.getString("INPUTUSRNM"));				
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return treatBean;
	}
	
	/**
	 * 담당단위 이름 가져오기
	 * 
	 * @param deptcd : 부서코드
	 * @param chrgunitcd : 담당단위코드
	 * 
	 * @return String : 담당단위 이름
	 * @throws Exception 
	 */
	public String getChrgunitName(String deptcd, int chrgunitcd) throws Exception{
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		String chrgunitnm = "";
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT CHRGUNITNM \n");
			selectQuery.append("  FROM CHRGUNIT \n");
			selectQuery.append(" WHERE DEPT_ID    = ? \n");
			selectQuery.append("   AND CHRGUNITCD = ? ");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, deptcd);
			pstmt.setInt(2, chrgunitcd);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				chrgunitnm = rs.getString("CHRGUNITNM");
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return chrgunitnm;
	}
	
	/** 
	 * 입력하기 상세(처리현황) - 입력담당자 담당단위 변경 (INPUTUSR TABLE)
	 * 
	 * @param conn : Connection 객체
	 * @param chrgunitcd : 변경할 담당단위코드
	 * @param chrgunitnm : 변경할 담당단위명
	 * @param usrid : 사용자ID
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 실행결과	
	 * @throws Exception : SQL관련 예외
	 */
	public int changeInputusrChrgunit(Connection conn, int chrgunitcd, String chrgunitnm, String usrid, int sysdocno, String deptcd) throws Exception {
		PreparedStatement pstmt = null;
		int fetchcnt = 0;
		
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("UPDATE INPUTUSR \n");
		updateQuery.append("   SET CHRGUNITCD   = ? \n");
		updateQuery.append("     , CHRGUNITNM   = ? \n");
		updateQuery.append("     , UPTDT        = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') \n");
		updateQuery.append("     , UPTUSRID     = ? \n");
		updateQuery.append(" WHERE SYSDOCNO     = ? \n");
		updateQuery.append("   AND TGTDEPT      = ? \n");
		updateQuery.append("   AND INPUTUSRID   = ? ");
		
		try{
			pstmt = conn.prepareStatement(updateQuery.toString());
			pstmt.setInt(1, chrgunitcd);
			pstmt.setString(2, chrgunitnm);
			pstmt.setString(3, usrid);
			pstmt.setInt(4, sysdocno);
			pstmt.setString(5, deptcd);
			pstmt.setString(6, usrid);
				
			fetchcnt = pstmt.executeUpdate();
		} catch(Exception e){
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return fetchcnt;
	}
	
	/** 
	 * 입력하기 상세(처리현황) - 입력담당자 담당단위 변경 (USR_EXT TABLE)
	 * 
	 * @param conn : Connection 객체
	 * @param chrgunitcd : 변경할 담당단위코드
	 * @param usrid : 사용자ID	
	 * 
	 * @return int : 실행결과
	 * @throws Exception : SQL관련 예외
	 */
	public int changeUsrextChrgunit(Connection conn, int chrgunitcd, String usrid) throws Exception {
		PreparedStatement pstmt = null;
		int fetchcnt = 0;
		
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("UPDATE USR \n");
		updateQuery.append("   SET CHRGUNITCD = ? \n");
		updateQuery.append("     , UPTDT      = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') \n");
		updateQuery.append("     , UPTUSRID   = ? \n");
		updateQuery.append(" WHERE USER_ID    = ? ");
		
		try{
			pstmt = conn.prepareStatement(updateQuery.toString());
			pstmt.setInt(1, chrgunitcd);
			pstmt.setString(2, usrid);
			pstmt.setString(3, usrid);
				
			fetchcnt = pstmt.executeUpdate();
		} catch(Exception e){
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return fetchcnt;
	}
	
	/** 
	 * 입력하기 상세(처리현황) - 입력담당자 담당단위 변경 (DATALINEFRM TABLE)
	 * 
	 * @param conn : Connection 객체
	 * @param chrgunitcd : 변경할 담당단위코드
	 * @param usrid : 사용자ID
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 실행결과	
	 * @throws Exception : SQL관련 예외
	 */
	public int changeDataLineFrmChrgunit(Connection conn, int chrgunitcd, String usrid, int sysdocno, String deptcd) throws Exception {
		PreparedStatement pstmt = null;
		int fetchcnt = 0;
		
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("\n UPDATE DATALINEFRM     ");
		updateQuery.append("\n    SET CHRGUNITCD = ?  ");
		updateQuery.append("\n  WHERE SYSDOCNO   = ?  ");
		updateQuery.append("\n    AND TGTDEPTCD  = ?  ");
		updateQuery.append("\n    AND INPUTUSRID = ?  ");
		
		try{
			pstmt = conn.prepareStatement(updateQuery.toString());
			pstmt.setInt(1, chrgunitcd);
			pstmt.setInt(2, sysdocno);
			pstmt.setString(3, deptcd);
			pstmt.setString(4, usrid);
				
			fetchcnt = pstmt.executeUpdate();
		} catch(Exception e){
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return fetchcnt;
	}
	
	/** 
	 * 입력하기 상세(처리현황) - 입력담당자 담당단위 변경 (DATAFIXEDFRM TABLE)
	 * 
	 * @param conn : Connection 객체
	 * @param chrgunitcd : 변경할 담당단위코드
	 * @param usrid : 사용자ID
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 실행결과	
	 * @throws Exception : SQL관련 예외
	 */
	public int changeDataFixedFrmChrgunit(Connection conn, int chrgunitcd, String usrid, int sysdocno, String deptcd) throws Exception {
		PreparedStatement pstmt = null;
		int fetchcnt = 0;
		
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("\n UPDATE DATAFIXEDFRM    ");
		updateQuery.append("\n    SET CHRGUNITCD = ?  ");
		updateQuery.append("\n  WHERE SYSDOCNO   = ?  ");
		updateQuery.append("\n    AND TGTDEPTCD  = ?  ");
		updateQuery.append("\n    AND INPUTUSRID = ?  ");
		
		try{
			pstmt = conn.prepareStatement(updateQuery.toString());
			pstmt.setInt(1, chrgunitcd);
			pstmt.setInt(2, sysdocno);
			pstmt.setString(3, deptcd);
			pstmt.setString(4, usrid);
				
			fetchcnt = pstmt.executeUpdate();
		} catch(Exception e){
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return fetchcnt;
	}
	
	/** 
	 * 입력하기 상세(처리현황) - 입력담당자 담당단위 변경 (DATABOOKFRM TABLE)
	 * 
	 * @param conn : Connection 객체
	 * @param chrgunitcd : 변경할 담당단위코드
	 * @param usrid : 사용자ID
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 실행결과	
	 * @throws Exception : SQL관련 예외
	 */
	public int changeDataBookFrmChrgunit(Connection conn, int chrgunitcd, String usrid, int sysdocno, String deptcd) throws Exception {
		PreparedStatement pstmt = null;
		int fetchcnt = 0;
		
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("\n UPDATE DATABOOKFRM     ");
		updateQuery.append("\n    SET CHRGUNITCD = ?  ");
		updateQuery.append("\n  WHERE SYSDOCNO   = ?  ");
		updateQuery.append("\n    AND TGTDEPTCD  = ?  ");
		updateQuery.append("\n    AND INPUTUSRID = ?  ");
		
		try{
			pstmt = conn.prepareStatement(updateQuery.toString());
			pstmt.setInt(1, chrgunitcd);
			pstmt.setInt(2, sysdocno);
			pstmt.setString(3, deptcd);
			pstmt.setString(4, usrid);
				
			fetchcnt = pstmt.executeUpdate();
		} catch(Exception e){
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return fetchcnt;
	}
}
