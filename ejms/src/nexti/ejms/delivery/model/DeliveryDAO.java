/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 dao
 * 설명:
 */
package nexti.ejms.delivery.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;

public class DeliveryDAO {
	
	private static Logger logger = Logger.getLogger(DeliveryDAO.class);
	
	/**
	 * 배부하기 목록
	 * 
	 * @param deptcd : 부서코드
	 * @param start : 목록 시작 인덱스
	 * @param end : 목록 마지막 인덱스
	 * 
	 * @return List : 배부 목록 리스트
	 * @throws Exception 
	 */
	public List deliveryList(String deptcd, String isSysMgr, String sch_deptcd, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List deliList = null;
		int bindPos = 0;
		
		String sysdate = "SYSDATE";
		if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			sysdate = "(SYSDATE-30/(24*60))";
		}
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT (CNT-SEQ+1) BUNHO, SEQ, FORMSEQ, FORMKIND, SYSDOCNO, DOCTITLE, DELIVERYDT, COLDEPTNM, CHRGUSRNM, ENDDT, RDAY, RTIME, USER_ID                                          \n");
			sql.append("  FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.*                                                                                                                                    \n");
			sql.append("          FROM (SELECT ROWNUM SEQ, A2.*                                                                                                                                      \n");
			sql.append("                  FROM (SELECT * FROM (SELECT DISTINCT F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, A.DOCTITLE, A.COLDEPTNM, A.CHRGUSRNM, C.USER_ID,                                   \n");
			sql.append("                               DECODE(A.DELIVERYDT, '', '', SUBSTR(A.DELIVERYDT,1,4)||'년 '||SUBSTR(A.DELIVERYDT,6,2)||'월 '||SUBSTR(A.DELIVERYDT,9,2)||'일') DELIVERYDT,      \n");
			sql.append("                               SUBSTR(A.ENDDT,1,4)||'년'||SUBSTR(A.ENDDT,6,2)||'월'||SUBSTR(A.ENDDT,9,2)||'일 '||SUBSTR(A.ENDDT,12,2)||'시'||SUBSTR(A.ENDDT,15,2)||'분' ENDDT, \n");
			sql.append("                               TRUNC(TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS')-"+sysdate+") RDAY,                                                                                 \n");
			sql.append("                               CEIL(MOD((TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS')-"+sysdate+"),1)*24) RTIME, A.CRTDT, A.UPTDT                                                    \n");
			sql.append("                          FROM DOCMST A, TGTDEPT B,                                                                                                                          \n");
			sql.append("                               (SELECT USER_ID, DEPT_ID FROM (SELECT * FROM USR WHERE USE_YN = 'Y' AND CON_YN = 'Y' AND DEPT_ID = ? ORDER BY LSTLOGINDT, USR_RANK DESC) WHERE ROWNUM = 1) C,  \n");
			sql.append("                               (SELECT SYSDOCNO, FORMSEQ, FORMKIND                                                                                                           \n");
			sql.append("                                FROM FORMMST F                                                                                                                               \n");
			sql.append("                                WHERE FORMSEQ = (SELECT MIN(FORMSEQ) FROM FORMMST WHERE SYSDOCNO = F.SYSDOCNO and to_number(formkind) <= 3)) F                               \n");
			sql.append("                         WHERE A.SYSDOCNO = B.SYSDOCNO                                                                                                                    \n");
			sql.append("                           AND A.SYSDOCNO = F.SYSDOCNO                                                                                                                     \n");
			sql.append(" 						   AND A.DOCSTATE = '04'                                                                                                                             \n");
			sql.append("                           AND B.SUBMITSTATE = '01'                                                                                                                          \n");
			sql.append("                           AND B.TGTDEPTCD = C.DEPT_ID(+)                                                                                                                    \n");
			if ( appInfo.isMultipleDelivery() ) {
				sql.append("                           AND A.SYSDOCNO NOT IN (SELECT DISTINCT SYSDOCNO FROM TGTDEPT WHERE SYSDOCNO = A.SYSDOCNO AND TGTDEPTCD = ? AND SUBMITSTATE > '01')                                       \n");
				sql.append("                           AND B.TGTDEPTCD IN (SELECT A.DEPT_ID                                                                                                          \n");
				sql.append("                                               FROM (SELECT DEPT_ID                                                                                                      \n");
				sql.append("                                                     FROM DEPT                                                                                                           \n");
				sql.append("                                                     START WITH DEPT_ID = (SELECT DEPT_ID                                                                                \n");
				sql.append("                                                                           FROM DEPT                                                                                     \n");
				sql.append("                                                                           WHERE DEPT_DEPTH = 2                                                                          \n");
				sql.append("                                                                           START WITH DEPT_ID = ?                                                                        \n");
				sql.append("                                                                           CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID)                                                     \n");
				sql.append("                                                     CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID) A,                                                                        \n");
				sql.append("                                                    (SELECT DEPT_ID, DEPT_NAME                                                                                           \n");
				sql.append("                                                     FROM DEPT                                                                                                           \n");
				sql.append("                                                     START WITH DEPT_ID = ?                                                                                              \n");
				sql.append("                                                     CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID) B                                                                         \n");
				sql.append("                                               WHERE A.DEPT_ID = B.DEPT_ID)                                                                                              \n");
			} else {
				sql.append("                           AND B.TGTDEPTCD = ?                                                                                                                           \n");
			}
			sql.append("                         ) ORDER BY ENDDT DESC, CRTDT DESC, UPTDT DESC) A2) A1)                                                                                              \n");
			sql.append("WHERE SEQ BETWEEN ? AND ?                                                                                                                                                    \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			if ( appInfo.isMultipleDelivery() ) {
				if ( isSysMgr.equals("001") ) {
					if ( !"".equals(sch_deptcd) ) {
						pstmt.setString(++bindPos, sch_deptcd);
						pstmt.setString(++bindPos, sch_deptcd);
						pstmt.setString(++bindPos, sch_deptcd);
						pstmt.setString(++bindPos, sch_deptcd);
					} else {
						pstmt.setString(++bindPos, "");
						pstmt.setString(++bindPos, deptcd);
						pstmt.setString(++bindPos, deptcd);
						pstmt.setString(++bindPos, deptcd);
					}
			 	} else {
					pstmt.setString(++bindPos, "");
					pstmt.setString(++bindPos, deptcd);
					pstmt.setString(++bindPos, deptcd);
					pstmt.setString(++bindPos, deptcd);
			 	}
			} else {
				if ( isSysMgr.equals("001") ) {
					if ( !"".equals(sch_deptcd) ) {
						pstmt.setString(++bindPos, sch_deptcd);
						pstmt.setString(++bindPos, sch_deptcd);
					} else {
						pstmt.setString(++bindPos, "");
						pstmt.setString(++bindPos, deptcd);
					}
			 	} else {
					pstmt.setString(++bindPos, "");
					pstmt.setString(++bindPos, deptcd);
			 	}
			}
			
			pstmt.setInt(++bindPos, start);
			pstmt.setInt(++bindPos, end);
									
			rs = pstmt.executeQuery();
			
			deliList = new ArrayList();
			
			while (rs.next()) {
				DeliveryBean deli = new DeliveryBean();
				
				deli.setSeqno(rs.getInt("BUNHO"));
				deli.setFormseq(rs.getInt("FORMSEQ"));
				deli.setFormkind(rs.getString("FORMKIND"));
				deli.setSysdocno(rs.getInt("SYSDOCNO"));
				deli.setDoctitle(rs.getString("DOCTITLE"));		
				deli.setDeliverydt(rs.getString("DELIVERYDT"));
				deli.setColdeptnm(rs.getString("COLDEPTNM"));
				deli.setChrgusrnm(rs.getString("CHRGUSRNM"));
				deli.setEnddt(rs.getString("ENDDT"));
				deli.setDept_member_id(rs.getString("USER_ID"));
				
				if(rs.getInt("RDAY")< 0){
					deli.setRemaintime("마감시한 초과");
				} else if(rs.getInt("RDAY") == 0){
					if(rs.getInt("RTIME")<=0){
						deli.setRemaintime("마감시한 초과");
					}else if(rs.getInt("RTIME")==1){
						deli.setRemaintime(rs.getString("RTIME")+"시간미만");
					}else{
						deli.setRemaintime(rs.getString("RTIME")+"시간");
					}
				} else{
					deli.setRemaintime(rs.getString("RDAY")+"일 "+rs.getString("RTIME")+"시간");
				}
				
				deliList.add(deli);
			}							
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return deliList;
	}

	/** 
	 * 배부하기 목록 총갯수 가져오기
	 * 
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 배부하기 목록 총갯수
	 * @throws Exception 
	 */
	public int deliCnt(String deptcd, String isSysMgr, String sch_deptcd) throws Exception {
		Connection conn = null;
	    ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer sql = new StringBuffer();			
			sql.append("SELECT COUNT(*) FROM (SELECT DISTINCT A.SYSDOCNO                                              \n");
			sql.append("FROM DOCMST A, TGTDEPT B 			                                                          \n");
			sql.append("WHERE A.SYSDOCNO = B.SYSDOCNO 		                                                          \n");
			sql.append("AND A.DOCSTATE = '04'                                                                         \n");
			sql.append("AND B.SUBMITSTATE = '01'                                                                      \n");
			if ( appInfo.isMultipleDelivery() ) {
				sql.append("AND A.SYSDOCNO NOT IN (SELECT DISTINCT SYSDOCNO FROM TGTDEPT WHERE SYSDOCNO = A.SYSDOCNO AND TGTDEPTCD = ? AND SUBMITSTATE > '01') \n");
				sql.append("AND B.TGTDEPTCD IN (SELECT A.DEPT_ID                                                      \n");
				sql.append("                    FROM (SELECT DEPT_ID                                                  \n");
				sql.append("                          FROM DEPT                                                       \n");
				sql.append("                          START WITH DEPT_ID = (SELECT DEPT_ID                            \n");
				sql.append("                                                FROM DEPT                                 \n");
				sql.append("                                                WHERE DEPT_DEPTH = 2                      \n");
				sql.append("                                                START WITH DEPT_ID = ?                    \n");
				sql.append("                                                CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID) \n");
				sql.append("                          CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID) A,                    \n");
				sql.append("                         (SELECT DEPT_ID, DEPT_NAME                                       \n");
				sql.append("                          FROM DEPT                                                       \n");
				sql.append("                          START WITH DEPT_ID = ?                                          \n");
				sql.append("                          CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID) B                     \n");
				sql.append("                    WHERE A.DEPT_ID = B.DEPT_ID)                                          \n");
			} else {
				sql.append("AND B.TGTDEPTCD = ?                                                                       \n");
			}
			sql.append(")                                                                                             \n");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(sql.toString());

			int bindPos = 0;
			
			if ( appInfo.isMultipleDelivery() ) {
				if ( isSysMgr.equals("001") ) {
					if ( !"".equals(sch_deptcd) ) {
						pstmt.setString(++bindPos, sch_deptcd);
						pstmt.setString(++bindPos, sch_deptcd);
						pstmt.setString(++bindPos, sch_deptcd);
					} else {
						pstmt.setString(++bindPos, deptcd);
						pstmt.setString(++bindPos, deptcd);
						pstmt.setString(++bindPos, deptcd);
					}
			 	} else {
					pstmt.setString(++bindPos, deptcd);
					pstmt.setString(++bindPos, deptcd);
					pstmt.setString(++bindPos, deptcd);
			 	}
			} else {
				if ( isSysMgr.equals("001") ) {
					if ( !"".equals(sch_deptcd) ) {
						pstmt.setString(++bindPos, sch_deptcd);
					} else {
						pstmt.setString(++bindPos, deptcd);
					}
			 	} else {
					pstmt.setString(++bindPos, deptcd);
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
	 * 배부하기 목록 총갯수 가져오기
	 * 
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 배부하기 목록 총갯수
	 * @throws Exception 
	 */
	public int deliTotCnt(String deptcd) throws Exception {
		Connection conn = null;
	    ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) FROM (SELECT DISTINCT D.SYSDOCNO                                              \n");
			sql.append("FROM DOCMST D, TGTDEPT T                                                                      \n");
			sql.append("WHERE D.SYSDOCNO = T.SYSDOCNO                                                                 \n");
			sql.append("AND D.DOCSTATE = '04'                                                                         \n");
			sql.append("AND T.SUBMITSTATE = '01'                                                                      \n");
			if ( appInfo.isMultipleDelivery() ) {
				sql.append("AND D.SYSDOCNO NOT IN (SELECT DISTINCT SYSDOCNO FROM TGTDEPT WHERE SYSDOCNO = D.SYSDOCNO AND TGTDEPTCD = ? AND SUBMITSTATE > '01') \n");
				sql.append("AND T.TGTDEPTCD IN (SELECT A.DEPT_ID                                                      \n");
				sql.append("                    FROM (SELECT DEPT_ID                                                  \n");
				sql.append("                          FROM DEPT                                                       \n");
				sql.append("                          START WITH DEPT_ID = (SELECT DEPT_ID                            \n");
				sql.append("                                                FROM DEPT                                 \n");
				sql.append("                                                WHERE DEPT_DEPTH = 2                      \n");
				sql.append("                                                START WITH DEPT_ID = ?                    \n");
				sql.append("                                                CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID) \n");
				sql.append("                          CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID) A,                    \n");
				sql.append("                         (SELECT DEPT_ID, DEPT_NAME                                       \n");
				sql.append("                          FROM DEPT                                                       \n");
				sql.append("                          START WITH DEPT_ID = ?                                          \n");
				sql.append("                          CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID) B                     \n");
				sql.append("                    WHERE A.DEPT_ID = B.DEPT_ID)                                          \n");
			} else {
				sql.append("AND T.TGTDEPTCD = ?                                                                       \n");
			}
			sql.append(")                                                                                             \n");			
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(sql.toString());
			
			if ( appInfo.isMultipleDelivery() ) {
				pstmt.setString(1, deptcd);
				pstmt.setString(2, deptcd);
				pstmt.setString(3, deptcd);
			} else {
				pstmt.setString(1, deptcd);
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
	 * 배부하기 상세 - 반송 팝업창 데이터 보기
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * 
	 * @return DeliveryRetDocViewBean : 반송 팝업창 데이터를 담고 있는 Bean
	 * @throws Exception 
	 */
	public DeliveryRetDocViewBean viewReturnDoc(int sysdocno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		DeliveryRetDocViewBean RetDocViewBean = new DeliveryRetDocViewBean();
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n SELECT A.DOCTITLE, A.COLDEPTNM, A.CHRGUNITNM ");
			selectQuery.append("\n      , A.CHRGUSRNM, A.COLDEPTCD, U.TEL       ");
			selectQuery.append("\n   FROM DOCMST A, USR U                       ");
			selectQuery.append("\n  WHERE A.CHRGUSRCD = U.USER_ID               ");
			selectQuery.append("\n    AND A.SYSDOCNO  = ?                       ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				RetDocViewBean.setDoctitle(rs.getString("DOCTITLE"));
				RetDocViewBean.setColdeptnm(rs.getString("COLDEPTNM"));
				RetDocViewBean.setChrgunitnm(rs.getString("CHRGUNITNM"));
				RetDocViewBean.setChrgusrnm(rs.getString("CHRGUSRNM"));
				RetDocViewBean.setColdeptcd(rs.getString("COLDEPTCD"));
				RetDocViewBean.setColdepttel(rs.getString("TEL"));
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return RetDocViewBean;
	}
	
	/** 
	 * 배부처리 - 반송
	 * 
	 * @param conn : Connection 객체
	 * @param RetDocBean : 반송 사유/제출 상태를 담고 있는 Bean
	 * @param userid : 사용자ID
	 * 
	 * @return int : 처리결과 
	 * @throws Exception 
	 */
	public int deliveryReturnDoc(Connection conn, DeliveryRetDocBean RetDocBean, String userid) throws Exception {
		PreparedStatement pstmt = null;
		int fetchcnt = 0;
		
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("UPDATE TGTDEPT ");
		updateQuery.append("   SET RETURNCOMMENT = ? ");
		updateQuery.append("     , SUBMITSTATE   = ? ");
		updateQuery.append("     , UPTDT         = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') \n");
		updateQuery.append("     , UPTUSRID      = ? \n");
		updateQuery.append(" WHERE SYSDOCNO      = ? ");
		updateQuery.append("   AND TGTDEPTCD     = ? ");
		
		try{
			pstmt = conn.prepareStatement(updateQuery.toString());
			pstmt.setString(1, RetDocBean.getReturncomment());
			pstmt.setString(2, RetDocBean.getSubmitstate());
			pstmt.setString(3, userid);
			pstmt.setInt(4, RetDocBean.getSysdocno());
			pstmt.setString(5, RetDocBean.getTgtdeptcd());
				
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
	 * 배부처리시 문서상태 변경
	 * 
	 * @param conn : Connection 객체
	 * @param sysdocno : 시스템 문서번호
	 * @param docstate : 변경할 문서상태
	 * @param usrid : 사용자ID
	 * 
	 * @return int : 처리결과 
	 * @throws Exception : SQL 관련 예외
	 */
	public int updateDocState(Connection conn, int sysdocno, String docstate, String usrid) throws Exception {
		PreparedStatement pstmt = null;
		int fetchcnt = 0;
		
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("\n UPDATE DOCMST                                              ");
		updateQuery.append("\n   SET DOCSTATE = ?                                         ");
		updateQuery.append("\n     , UPTDT    = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') ");
		updateQuery.append("\n     , UPTUSRID = ?                                         ");
		updateQuery.append("\n WHERE SYSDOCNO = ?                                         ");
		
		try{
			pstmt = conn.prepareStatement(updateQuery.toString());
			pstmt.setString(1, docstate);
			pstmt.setString(2, usrid);
			pstmt.setInt(3, sysdocno);
				
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
	 * 공통함수 - 마지막 제출부서인지 아닌지 여부 체크
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return true : 마지막 부서 O, false : 마지막 부서 X	
	 * @throws Exception 
	 */
	public boolean IsLastDeliveryDept(Connection conn, int sysdocno, String deptcd) throws Exception {
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean check = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n SELECT COUNT(*)                                                               ");
			selectQuery.append("\n   FROM TGTDEPT                                                                ");
			selectQuery.append("\n  WHERE SYSDOCNO = ?                                                           ");
			selectQuery.append("\n    AND SUBMITSTATE NOT IN ('05', '06')                                        ");
			selectQuery.append("\n    AND TGTDEPTCD  != ?                                                        ");
			selectQuery.append("\n    AND TGTDEPTCD IN (SELECT TGTDEPTCD                                         ");
			selectQuery.append("\n                      FROM (SELECT TGTDEPTCD, PREDEPTCD                        ");
			selectQuery.append("\n                            FROM TGTDEPT                                       ");
			selectQuery.append("\n                            WHERE SYSDOCNO = ?                                 ");
			selectQuery.append("\n                            UNION ALL                                          ");
			selectQuery.append("\n                            SELECT 'M_' || COLDEPTCD, NULL                     ");
			selectQuery.append("\n                            FROM DOCMST                                        ");
			selectQuery.append("\n                            WHERE SYSDOCNO = ?)                                ");
			selectQuery.append("\n                      CONNECT BY PRIOR TGTDEPTCD = PREDEPTCD                   ");
			selectQuery.append("\n                      START WITH TGTDEPTCD = (SELECT 'M_' || COLDEPTCD         ");
			selectQuery.append("\n                                              FROM DOCMST WHERE SYSDOCNO = ?)) ");
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
			pstmt.setInt(3, sysdocno);
			pstmt.setInt(4, sysdocno);
			pstmt.setInt(5, sysdocno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() && rs.getInt(1) > 0 ) {
				check = false;
			} else {
				ConnectionManager.close(pstmt, rs);
				selectQuery.delete(0, selectQuery.capacity());
				selectQuery.append("SELECT COUNT(*) FROM TGTDEPT WHERE SYSDOCNO = ? AND TGTDEPTCD = ? AND SUBMITSTATE NOT IN ('05', '06') ");
				
				pstmt =	conn.prepareStatement(selectQuery.toString());
				pstmt.setInt(1, sysdocno);
				pstmt.setString(2, deptcd);
				
				rs = pstmt.executeQuery();
				
				if ( rs.next() && rs.getInt(1) > 0 ) {
					check = false;
				} else {					
					check = true;
				}
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(pstmt,rs);
	     }
		return check;
	}
	
	/** 
	 * 배부하기 상세 - 배부
	 * 
	 * @param submitstate : 변경할 제출부서 상태
	 * @param appusrnm : 입력담당지정자 설명
	 * @param usrid : 사용자ID
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 처리결과
	 * @throws Exception 
	 */	
	public int deliveryProcess(String submitstate, String appusrnm, String usrid, int sysdocno, String deptcd) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("\n UPDATE TGTDEPT                                                  ");
		updateQuery.append("\n    SET SUBMITSTATE = ?                                          ");
		updateQuery.append("\n      , INUSRSENDDT  = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') ");
		updateQuery.append("\n      , APPNTUSRNM  = ?                                          ");
		updateQuery.append("\n      , UPTDT       = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')  ");
		updateQuery.append("\n      , UPTUSRID    = ?                                          ");
		updateQuery.append("\n  WHERE SYSDOCNO    = ?                                          ");
		updateQuery.append("\n    AND TGTDEPTCD   = ?                                          ");
		
		try{
			con = ConnectionManager.getConnection(false);
			
			pstmt = con.prepareStatement(updateQuery.toString());
			pstmt.setString(1, submitstate);
			pstmt.setString(2, appusrnm);
			pstmt.setString(3, usrid);
			pstmt.setInt(4, sysdocno);
			pstmt.setString(5, deptcd);
				
			result = pstmt.executeUpdate();
			
			con.commit();
		} catch(Exception e){
			logger.error("ERROR",e);
			ConnectionManager.close(con,pstmt);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt);
		}
		
		return result;
	}
	
	public int deliveryProcess(Connection con, String submitstate, String appusrnm, String usrid, int sysdocno, String sessionId, String deptcd) throws Exception {

		PreparedStatement pstmt = null;
		int result = 0;
		
		StringBuffer updateQuery = new StringBuffer();
		
		if ( sysdocno == 0 ) {
			updateQuery.append("\n UPDATE TGTDEPT_TEMP                                             ");
			updateQuery.append("\n    SET SUBMITSTATE = ?                                          ");
			updateQuery.append("\n      , INUSRSENDDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')  ");
			updateQuery.append("\n      , APPNTUSRNM  = ?                                          ");
			updateQuery.append("\n      , UPTDT       = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')  ");
			updateQuery.append("\n      , UPTUSRID    = ?                                          ");
			updateQuery.append("\n  WHERE sessionId   = ?                                          ");
			updateQuery.append("\n    AND TGTDEPTCD   = ?                                          ");
		} else {
			updateQuery.append("\n UPDATE TGTDEPT                                                  ");
			updateQuery.append("\n    SET SUBMITSTATE = ?                                          ");
			updateQuery.append("\n      , INUSRSENDDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')  ");
			updateQuery.append("\n      , APPNTUSRNM  = ?                                          ");
			updateQuery.append("\n      , UPTDT       = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')  ");
			updateQuery.append("\n      , UPTUSRID    = ?                                          ");
			updateQuery.append("\n  WHERE SYSDOCNO    = ?                                          ");
			updateQuery.append("\n    AND TGTDEPTCD   = ?                                          ");
		}
		
		try{			
			pstmt = con.prepareStatement(updateQuery.toString());
			
			if ( sysdocno == 0 ) {
				pstmt.setString(1, submitstate);
				pstmt.setString(2, appusrnm);
				pstmt.setString(3, usrid);
				pstmt.setString(4, sessionId);
				pstmt.setString(5, deptcd);
			} else {
				pstmt.setString(1, submitstate);
				pstmt.setString(2, appusrnm);
				pstmt.setString(3, usrid);
				pstmt.setInt(4, sysdocno);
				pstmt.setString(5, deptcd);
			}
				
			result = pstmt.executeUpdate();
			
		} catch(Exception e){
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}

	/**
	 * 배부완료 목록
	 * 
	 * @param deptcd : 부서코드
	 * @param start : 목록 시작 인덱스
	 * @param end : 목록 마지막 인덱스
	 * 
	 * @return List : 배부완료 목록 리스트
	 * @throws Exception 
	 */
	public List deliveryCompList(String deptcd, String isSysMgr, String sch_deptcd, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List deliCompList = null;
		
		String sysdate = "SYSDATE";
		if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			sysdate = "(SYSDATE-30/(24*60))";
		}
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT (CNT-SEQ+1) BUNHO, SEQ, FORMSEQ, FORMKIND, SYSDOCNO, DOCTITLE, SUBMITSTATE, DELIVERYDT,                                                                                 \n");
			sql.append("       COLDEPTNM, CHRGUSRNM, ENDDT, RDAY, RTIME, INPUTCNT, DELICNT, USER_ID                                                                                                    \n");
			sql.append("  FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.*                                                                                                                                      \n");
			sql.append("          FROM (SELECT ROWNUM SEQ, A2.*                                                                                                                                        \n");
			sql.append("                  FROM (SELECT F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, A.DOCTITLE, B.SUBMITSTATE, A.COLDEPTNM, A.CHRGUSRNM,                                                         \n");
			sql.append("                               DECODE(A.DELIVERYDT, '', '', SUBSTR(A.DELIVERYDT,1,4)||'년 '||SUBSTR(A.DELIVERYDT,6,2)||'월 '||SUBSTR(A.DELIVERYDT,9,2)||'일') DELIVERYDT,        \n");
			sql.append("                               SUBSTR(A.ENDDT,1,4)||'년'||SUBSTR(A.ENDDT,6,2)||'월'||SUBSTR(A.ENDDT,9,2)||'일 '||SUBSTR(A.ENDDT,12,2)||'시'||SUBSTR(A.ENDDT,15,2)||'분' ENDDT,   \n");
			sql.append("                               TRUNC(TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS')-"+sysdate+") RDAY,                                                                                   \n");
			sql.append("                               CEIL(MOD((TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS')-"+sysdate+"),1)*24) RTIME,                                                                       \n");
			sql.append("                               (SELECT COUNT(*) FROM INPUTUSR WHERE SYSDOCNO = A.SYSDOCNO AND TGTDEPT = B.TGTDEPTCD AND (INPUTSTATE = '03' OR INPUTSTATE = '04')) AS INPUTCNT, \n");
			sql.append("                               (SELECT COUNT(*) FROM INPUTUSR WHERE SYSDOCNO = A.SYSDOCNO AND TGTDEPT = B.TGTDEPTCD) AS DELICNT, USER_ID                                       \n");
			sql.append("                          FROM DOCMST A, TGTDEPT B,                                                                                                                            \n");
			sql.append("                               (SELECT USER_ID, DEPT_ID FROM (SELECT * FROM USR WHERE USE_YN = 'Y' AND CON_YN = 'Y' AND DEPT_ID = ? ORDER BY LSTLOGINDT, USR_RANK DESC) WHERE ROWNUM = 1) C, \n");
			sql.append("                               (SELECT SYSDOCNO, FORMSEQ, FORMKIND                                                                                                             \n");
			sql.append("                                FROM FORMMST F                                                                                                                                 \n");
			sql.append("                                WHERE FORMSEQ = (SELECT MIN(FORMSEQ) FROM FORMMST WHERE SYSDOCNO = F.SYSDOCNO and to_number(formkind) <= 3)) F                                 \n");
			sql.append("                         WHERE A.SYSDOCNO = B.SYSDOCNO                                                                                                                         \n");
			sql.append("                           AND A.SYSDOCNO = F.SYSDOCNO                                                                                                                         \n");
			sql.append(" 						   AND A.DOCSTATE = '04'                                                                                                                               \n");
			sql.append("                           AND B.SUBMITSTATE IN ('02', '03', '04')                                                                                                             \n");
			sql.append("                           AND B.TGTDEPTCD = C.DEPT_ID(+)                                                                                                                      \n");
			sql.append("                           AND B.TGTDEPTCD = ?                                                                                                                                 \n");
//상위부서 배부문서까지 보기 시작			
//			sql.append("                           AND B.TGTDEPTCD IN (SELECT A.DEPT_ID                                                                                                                \n");
//			sql.append("                                               FROM (SELECT DEPT_ID                                                                                                            \n");
//			sql.append("                                                     FROM DEPT                                                                                                                 \n");
//			sql.append("                                                     START WITH DEPT_ID = (SELECT DEPT_ID                                                                                      \n");
//			sql.append("                                                                           FROM DEPT                                                                                           \n");
//			sql.append("                                                                           WHERE DEPT_DEPTH = 2                                                                                \n");
//			sql.append("                                                                           START WITH DEPT_ID = ?                                                                              \n");
//			sql.append("                                                                           CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID)                                                           \n");
//			sql.append("                                                     CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID) A,                                                                              \n");
//			sql.append("                                                    (SELECT DEPT_ID, DEPT_NAME                                                                                                 \n");
//			sql.append("                                                     FROM DEPT                                                                                                                 \n");
//			sql.append("                                                     START WITH DEPT_ID = ?                                                                                                    \n");
//			sql.append("                                                     CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID) B                                                                               \n");
//			sql.append("                                               WHERE A.DEPT_ID = B.DEPT_ID)                                                                                                    \n");
//상위부서 배부문서까지 보기 끝
			sql.append("	                       GROUP BY F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, A.DOCTITLE, B.SUBMITSTATE, A.COLDEPTNM, A.CHRGUSRNM, A.DELIVERYDT, A.ENDDT, B.TGTDEPTCD, A.CRTDT, A.UPTDT, USER_ID \n");
			sql.append("                           ORDER BY A.ENDDT DESC, A.CRTDT DESC, A.UPTDT DESC) A2) A1) 		                                                                                     \n");
			sql.append("WHERE SEQ BETWEEN ? AND ?                                                                                                                                                        \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			int bindPos = 0;
			if ( isSysMgr.equals("001") ) {
				if ( !"".equals(sch_deptcd) ) {
					pstmt.setString(++bindPos, sch_deptcd);
					pstmt.setString(++bindPos, sch_deptcd);
				} else {
					pstmt.setString(++bindPos, "");
					pstmt.setString(++bindPos, deptcd);
				}
		 	} else {
		 		pstmt.setString(++bindPos, "");
				pstmt.setString(++bindPos, deptcd);
		 	}
			
			pstmt.setInt(++bindPos, start);
			pstmt.setInt(++bindPos, end);
									
			rs = pstmt.executeQuery();
			
			deliCompList = new ArrayList();
			
			while (rs.next()) {
				DeliveryCompBean deliComp = new DeliveryCompBean();
				
				deliComp.setSeqno(rs.getInt("BUNHO"));
				deliComp.setFormseq(rs.getInt("FORMSEQ"));
				deliComp.setFormkind(rs.getString("FORMKIND"));
				deliComp.setSysdocno(rs.getInt("SYSDOCNO"));
				deliComp.setDoctitle(rs.getString("DOCTITLE"));
				
				if(rs.getString("SUBMITSTATE").equals("02")) {
					deliComp.setSubmitstate("입력진행");
				} else if(rs.getString("SUBMITSTATE").equals("03")) {
					deliComp.setSubmitstate("검토대기");
				} else if(rs.getString("SUBMITSTATE").equals("04")) {
					deliComp.setSubmitstate("승인대기");
				}
				
				deliComp.setDeliverydt(rs.getString("DELIVERYDT"));
				deliComp.setColdeptnm(rs.getString("COLDEPTNM"));
				deliComp.setChrgusrnm(rs.getString("CHRGUSRNM"));
				deliComp.setEnddt(rs.getString("ENDDT"));
				
				if(rs.getInt("RDAY")< 0){
					deliComp.setRemaintime("마감시한 초과");
				} else if(rs.getInt("RDAY") == 0){
					if(rs.getInt("RTIME")<=0){
						deliComp.setRemaintime("마감시한 초과");
					}else if(rs.getInt("RTIME")== 1){
						deliComp.setRemaintime(rs.getString("RTIME")+"시간미만");
					}else{
						deliComp.setRemaintime(rs.getString("RTIME")+"시간");
					}
				} else{
					deliComp.setRemaintime(rs.getString("RDAY")+"일 "+rs.getString("RTIME")+"시간");
				}
				
				deliComp.setInputperdeli(rs.getInt("INPUTCNT") + "/" + rs.getInt("DELICNT") + "명");
				deliComp.setDept_member_id(rs.getString("USER_ID"));
				
				deliCompList.add(deliComp);
			}							
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return deliCompList;
	}

	/** 
	 * 배부완료 목록 갯수 가져오기
	 * @param deptcd : 부서코드
	 * @return int : 배부완료 목록 총갯수 
	 * @throws Exception 
	 */
	public int deliCompCnt(String deptcd, String isSysMgr, String sch_deptcd) throws Exception {
		Connection conn = null;
	    ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(*)                                                                          \n");
			sql.append(" FROM DOCMST A, TGTDEPT B,                                                                \n");                                                            
			sql.append("      (SELECT SYSDOCNO, FORMSEQ, FORMKIND                                                 \n");                                                            
			sql.append("       FROM FORMMST F                                                                     \n");                                                            
			sql.append("       WHERE FORMSEQ = (SELECT MIN(FORMSEQ) FROM FORMMST WHERE SYSDOCNO = F.SYSDOCNO and to_number(formkind) <= 3)) F  \n");
			sql.append(" WHERE A.SYSDOCNO = B.SYSDOCNO                                                            \n");                                                            
			sql.append(" AND A.SYSDOCNO = F.SYSDOCNO                                                              \n");
			sql.append(" AND A.DOCSTATE = '04'                                                                    \n");
			sql.append(" AND B.SUBMITSTATE IN ('02', '03', '04')                                                  \n");
			sql.append(" AND B.TGTDEPTCD = ?                                                                      \n");
//상위부서 배부문서까지 보기 시작
//			sql.append("AND B.TGTDEPTCD IN (SELECT A.DEPT_ID                                                      \n");
//			sql.append("                    FROM (SELECT DEPT_ID                                                  \n");
//			sql.append("                          FROM DEPT                                                       \n");
//			sql.append("                          START WITH DEPT_ID = (SELECT DEPT_ID                            \n");
//			sql.append("                                                FROM DEPT                                 \n");
//			sql.append("                                                WHERE DEPT_DEPTH = 2                      \n");
//			sql.append("                                                START WITH DEPT_ID = ?                    \n");
//			sql.append("                                                CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID) \n");
//			sql.append("                          CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID) A,                    \n");
//			sql.append("                         (SELECT DEPT_ID, DEPT_NAME                                       \n");
//			sql.append("                          FROM DEPT                                                       \n");
//			sql.append("                          START WITH DEPT_ID = ?                                          \n");
//			sql.append("                          CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID) B                     \n");
//			sql.append("                    WHERE A.DEPT_ID = B.DEPT_ID)                                          \n");
//상위부서 배부문서까지 보기 끝
			
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(sql.toString());
			
			int bindPos = 0;
			if ( isSysMgr.equals("001") ) {
				if ( !"".equals(sch_deptcd) ) {
					pstmt.setString(++bindPos, sch_deptcd);
				} else {
					pstmt.setString(++bindPos, deptcd);
				}
		 	} else {
				pstmt.setString(++bindPos, deptcd);
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
	 * 배부완료 목록 총갯수 가져오기
	 * 
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 배부완료 목록 총갯수 
	 * @throws Exception 
	 */
	public int deliCompTotCnt(String deptcd) throws Exception {
		Connection conn = null;
	    ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) ");
			selectQuery.append("  FROM DOCMST A, TGTDEPT B ");
			selectQuery.append(" WHERE A.SYSDOCNO = B.SYSDOCNO " +
        					   "   AND A.DOCSTATE IN ('04','05')\n ");
			selectQuery.append("   AND B.TGTDEPTCD = ? ");
			selectQuery.append("   AND B.SUBMITSTATE IN ('02', '03', '04') ");
	
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, deptcd);
			
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
	 * 배부하기 - 입력담당자 지정 여부
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return true, false : true - 담당자 지정 O, false - 담당자 지정 X
	 * @throws Exception 
	 */
	public boolean IsAssignInputUsrcharge(int sysdocno, String deptcd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean check = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) CNT \n");
			selectQuery.append("  FROM INPUTUSR \n");
			selectQuery.append(" WHERE SYSDOCNO = ? \n");
			selectQuery.append("   AND TGTDEPT  = ? ");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				if(rs.getInt("CNT") > 0) {
					check = true;
				} else {
					check = false;
				}
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return check;
	}
	
	/** 
	 * 배부하기 - 결재선 승인자 지정 여부
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return true, false : true - 결재선 지정 O, false - 결재선 지정 X
	 * @throws Exception 
	 */
	public boolean IsAssignApprovalUsr(int sysdocno, String deptcd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean check = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) CNT \n");
			selectQuery.append("  FROM SANCTGT \n");
			selectQuery.append(" WHERE SYSDOCNO = ? \n");
			selectQuery.append("   AND TGTDEPTCD  = ? ");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				if(rs.getInt("CNT") > 0) {
					check = true;
				} else {
					check = false;
				}
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return check;
	}
	
	/** 
	 * 배부하기 - 마감시한이내 여부
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * 
	 * @return true, false : true - 마감시한이내 O, false - 마감시한 초과 X
	 * @throws Exception 
	 */
	public boolean IsAssignEndDt(int sysdocno) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean check = true;
		
		String sysdate = "SYSDATE";
		if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			sysdate = "(SYSDATE-30/(24*60))";
		}
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) CNT \n");
			selectQuery.append("  FROM DOCMST \n");
			selectQuery.append(" WHERE SYSDOCNO = ? \n");
			selectQuery.append("   AND ENDDT < TO_CHAR("+sysdate+",'YYYY-MM-DD HH24:MI:SS') ");
			
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				if(rs.getInt("CNT") > 0) {
					check = false;
				} else {
					check = true;
				}
			}
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
	     
		return check;
	}
	
	/**
	 * 배부하기 상세 - 마감시한/마감알림말 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * 
	 * @return DeliveryDetailBean : 마감시한/마감알림말을 담고 있는 Bean
	 * @throws Exception 
	 */
	public DeliveryDetailBean viewDeliveryDetail(int sysdocno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		DeliveryDetailBean detailBean = new DeliveryDetailBean();
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT SUBSTR(ENDDT,1,4)||'년'||SUBSTR(ENDDT,6,2)||'월'||SUBSTR(ENDDT,9,2)||'일 '||SUBSTR(ENDDT,12,2)||'시'||SUBSTR(ENDDT,15,2)||'분' ENDDT \n");
			selectQuery.append("     , ENDCOMMENT \n");
			selectQuery.append("  FROM DOCMST \n");
			selectQuery.append(" WHERE SYSDOCNO = ? ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				detailBean.setEnddt(rs.getString("ENDDT"));
				detailBean.setEndcomment(rs.getString("ENDCOMMENT"));
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return detailBean;
	}

	/**
	 * 배부하기 - 관리자인경우 검색 조건에 해당하는 값 가져오기 
	 */
	public String getSearchDelivery(String sch_deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
			
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT NVL(SUM(COUNT(*)),0) AS CNT	    \n");
			selectQuery.append(" FROM DOCMST A, TGTDEPT B       		\n");
			selectQuery.append(" WHERE A.SYSDOCNO = B.SYSDOCNO  		\n");
			selectQuery.append(" AND B.TGTDEPTCD = '%"+sch_deptcd+"%'	\n");
			selectQuery.append(" AND A.DOCSTATE IN ('04')  				\n");
			selectQuery.append(" AND B.SUBMITSTATE = '01'       		\n");
			selectQuery.append(" GROUP BY B.TGTDEPTNM 					\n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("CNT") == 1){
					ConnectionManager.close(pstmt, rs);
					
					if(!"".equals(sch_deptcd)&& sch_deptcd != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT TGTDEPTCD						\n");
						selectQuery.append(" FROM DOCMST A, TGTDEPT B       		\n");
						selectQuery.append(" WHERE A.SYSDOCNO = B.SYSDOCNO  		\n");
						selectQuery.append(" AND B.TGTDEPTCD = '%"+sch_deptcd+"%'	\n");
						selectQuery.append(" AND A.DOCSTATE IN ('04')  				\n");
						selectQuery.append(" AND B.SUBMITSTATE = '01'       		\n");

						pstmt = con.prepareStatement(selectQuery.toString());
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							result = rs.getString("TGTDEPTCD");
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
	 * 배부하기 - 관리자인경우 검색 조건에 해당하는 값 가져오기 
	 */
	public String getSearchDeliveryComp(String sch_deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
			
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT NVL(SUM(COUNT(*)),0) AS CNT	    	\n");
			selectQuery.append(" FROM DOCMST A, TGTDEPT B       			\n");
			selectQuery.append(" WHERE A.SYSDOCNO = B.SYSDOCNO  			\n");
			selectQuery.append(" AND B.TGTDEPTCD = '%"+sch_deptcd+"%'		\n");
			selectQuery.append(" AND A.DOCSTATE IN ('04')					\n");
			selectQuery.append(" AND B.SUBMITSTATE IN ('02', '03', '04') 	\n");
			selectQuery.append(" GROUP BY B.TGTDEPTNM 						\n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("CNT") == 1){
					ConnectionManager.close(pstmt, rs);
					
					if(!"".equals(sch_deptcd)&& sch_deptcd != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT TGTDEPTCD							\n");
						selectQuery.append(" FROM DOCMST A, TGTDEPT B       			\n");
						selectQuery.append(" WHERE A.SYSDOCNO = B.SYSDOCNO  			\n");
						selectQuery.append(" AND B.TGTDEPTCD = '%"+sch_deptcd+"%'		\n");
						selectQuery.append(" AND A.DOCSTATE IN ('04')  					\n");
						selectQuery.append(" AND B.SUBMITSTATE IN ('02', '03', '04') 	\n");
						selectQuery.append(" AND B.SUBMITSTATE = '01'       			\n");

						pstmt = con.prepareStatement(selectQuery.toString());
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							result = rs.getString("TGTDEPTCD");
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
