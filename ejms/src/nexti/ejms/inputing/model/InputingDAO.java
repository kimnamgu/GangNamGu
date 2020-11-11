/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력하기 dao
 * 설명:
 */
package nexti.ejms.inputing.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.dept.model.DeptManager;

public class InputingDAO {
	
	private static Logger logger = Logger.getLogger(InputingDAO.class);
	
	/**
	 * 제출부서 입력완료 여부 체크
	 * @param sysdocno 시스템문서번호
	 * @param tgtdeptcd 제출부서코드
	 * @return 제출완료sysdocno,tgtdeptcd,inputusrid(1명)
	 */
	public String[] isTgtdeptInputComplete(Connection con, int sysdocno, String tgtdeptcd) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] result = null;
		try {rs.close();} catch(Exception e) {}
		try {pstmt.close();} catch(Exception e) {}		
		try {			
			String sql =
				"SELECT * " +
				"FROM INPUTUSR " +
				"WHERE SYSDOCNO = ? " +
				"  AND TGTDEPT = ? ";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, tgtdeptcd);
			
			rs = pstmt.executeQuery();
			
			if(rs.next() == true) {
				sql =
					"SELECT COUNT(*) " +
					"FROM INPUTUSR " +
					"WHERE SYSDOCNO = ? " +
					"  AND TGTDEPT = ? " +
					"  AND INPUTSTATE NOT IN('03', '04', '05', '06') ";
				
				try {rs.close();} catch(Exception e) {}
				try {pstmt.close();} catch(Exception e) {}
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, sysdocno);
				pstmt.setString(2, tgtdeptcd);
				
				rs = pstmt.executeQuery();
				
				if(rs.next() == true) {
					if(rs.getInt(1) == 0) {
						sql =
							"SELECT SYSDOCNO, TGTDEPT, INPUTUSRID " +
							"FROM INPUTUSR " +
							"WHERE SYSDOCNO = ? " +
							"  AND TGTDEPT = ? " +
							"  AND INPUTSTATE IN('03', '04', '05', '06') ";
						
						try {rs.close();} catch(Exception e) {}
						try {pstmt.close();} catch(Exception e) {}
						pstmt = con.prepareStatement(sql);
						
						pstmt.setInt(1, sysdocno);
						pstmt.setString(2, tgtdeptcd);
						
						rs = pstmt.executeQuery();
						
						if(rs.next() == true) {
							result = new String[3];
							
							result[0] = rs.getString("SYSDOCNO");
							result[1] = rs.getString("TGTDEPT");
							result[2] = rs.getString("INPUTUSRID");
						}						
					} else {
						result = new String[3];
						
						result[0] = "-1";
						result[1] = "-1";
						result[2] = "-1";
					}

				}
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 입력하기 목록
	 * 
	 * @param userid : 사용자ID
	 * @param start : 목록 시작 인덱스
	 * @param end : 목록 마지막 인덱스
	 * 
	 * @return List : 입력목록
	 * @throws Exception 
	 */
	public List inputingList(String userid, String deptcd, int gubun, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List inList = null;
		
		String sysdate = "SYSDATE";
		if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			sysdate = "(SYSDATE-30/(24*60))";
		}
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			/*
			selectQuery.append("SELECT (CNT-SEQ+1) BUNHO, SEQ, FORMSEQ, FORMKIND, SYSDOCNO, DOCTITLE, DELIVERYDT, COLDEPTNM, CHRGUSRNM, ENDDT, RDAY, RTIME, GUBUN \n");
			selectQuery.append("  FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n");
			selectQuery.append("          FROM (SELECT ROWNUM SEQ, A2.* \n");
			selectQuery.append("                  FROM (SELECT F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, A.DOCTITLE, A.COLDEPTNM, A.CHRGUSRNM, \n");
			selectQuery.append("                               DECODE(A.DELIVERYDT, '', '', SUBSTR(A.DELIVERYDT,1,4)||'년 '||SUBSTR(A.DELIVERYDT,6,2)||'월 '||SUBSTR(A.DELIVERYDT,9,2)||'일') DELIVERYDT, \n");
			selectQuery.append("                               SUBSTR(A.ENDDT,1,4)||'년'||SUBSTR(A.ENDDT,6,2)||'월'||SUBSTR(A.ENDDT,9,2)||'일 '||SUBSTR(A.ENDDT,12,2)||'시'||SUBSTR(A.ENDDT,15,2)||'분' ENDDT, \n");
			selectQuery.append("                               TRUNC(TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS')-SYSDATE) RDAY, \n");
			selectQuery.append("                               CEIL(MOD((TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS')-SYSDATE),1)*24) RTIME, NVL(S.GUBUN, 1) GUBUN \n");
			selectQuery.append("                          FROM DOCMST A, TGTDEPT B, INPUTUSR C, \n");
			selectQuery.append("                               (SELECT SYSDOCNO, FORMSEQ, FORMKIND \n");
			selectQuery.append("                                FROM FORMMST F \n");
			selectQuery.append("                                WHERE FORMSEQ = (SELECT MIN(FORMSEQ) FROM FORMMST WHERE SYSDOCNO = F.SYSDOCNO and to_number(formkind) <= 3)) F, \n");
			selectQuery.append("                               (SELECT SYSDOCNO, 2 GUBUN FROM SANCTGT WHERE SANCYN = 0) S \n");
			selectQuery.append("                         WHERE A.SYSDOCNO   = B.SYSDOCNO \n");
			selectQuery.append("                           AND A.SYSDOCNO   = C.SYSDOCNO \n");
			selectQuery.append("                           AND A.SYSDOCNO   = F.SYSDOCNO \n");
			selectQuery.append("                           AND B.TGTDEPTCD  = C.TGTDEPT \n");
			selectQuery.append("                           AND A.DOCSTATE  = '04' \n");
			selectQuery.append("						   AND B.SUBMITSTATE  = '02' \n");
			selectQuery.append("                           AND C.INPUTUSRID = ? \n");
			selectQuery.append("						   AND C.TGTDEPT = ?    \n");
			selectQuery.append("                           AND C.INPUTSTATE IN ('01', '02') \n");
			selectQuery.append("                           AND C.SYSDOCNO = S.SYSDOCNO(+) \n");			
			*/

			selectQuery.append("SELECT (CNT-SEQ+1) BUNHO, SEQ, FORMSEQ, FORMKIND, SYSDOCNO, DOCTITLE, DELIVERYDT, COLDEPTNM, CHRGUSRNM, ENDDT, RDAY, RTIME, GUBUN, INPUTUSRID \n");
			selectQuery.append("  FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n");
			selectQuery.append("          FROM (SELECT ROWNUM SEQ, A2.* \n");
			selectQuery.append("                  FROM (SELECT F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, A.DOCTITLE, A.COLDEPTNM, A.CHRGUSRNM, C.INPUTUSRID, \n");
			selectQuery.append("                               DECODE(A.DELIVERYDT, '', '', SUBSTR(A.DELIVERYDT,1,4)||'년 '||SUBSTR(A.DELIVERYDT,6,2)||'월 '||SUBSTR(A.DELIVERYDT,9,2)||'일') DELIVERYDT, \n");
			selectQuery.append("                               SUBSTR(A.ENDDT,1,4)||'년'||SUBSTR(A.ENDDT,6,2)||'월'||SUBSTR(A.ENDDT,9,2)||'일 '||SUBSTR(A.ENDDT,12,2)||'시'||SUBSTR(A.ENDDT,15,2)||'분' ENDDT, \n");
			selectQuery.append("                               TRUNC(TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS')-"+sysdate+") RDAY, \n");
			selectQuery.append("                               CEIL(MOD((TO_DATE(A.ENDDT,'YYYY-MM-DD HH24:MI:SS')-"+sysdate+"),1)*24) RTIME, NVL(S.GUBUN, 1) GUBUN \n");
			selectQuery.append("                          FROM DOCMST A, TGTDEPT B, INPUTUSR C, \n");
			selectQuery.append("                               (SELECT SYSDOCNO, FORMSEQ, FORMKIND \n");
			selectQuery.append("                                FROM FORMMST F \n");
			selectQuery.append("                                WHERE FORMSEQ = (SELECT MIN(FORMSEQ) FROM FORMMST WHERE SYSDOCNO = F.SYSDOCNO and to_number(formkind) <= 3)) F, \n");
			selectQuery.append("                               (SELECT SYSDOCNO, 2 GUBUN, INPUTUSRID FROM SANCTGT WHERE SANCYN = 0) S \n");
			selectQuery.append("                         WHERE A.SYSDOCNO   = B.SYSDOCNO \n");
			selectQuery.append("                           AND A.SYSDOCNO   = C.SYSDOCNO \n");
			selectQuery.append("                           AND A.SYSDOCNO   = F.SYSDOCNO \n");
			selectQuery.append("                           AND B.TGTDEPTCD  = C.TGTDEPT \n");
			selectQuery.append("                           AND A.DOCSTATE  = '04' 					\n");
			selectQuery.append("						   AND B.SUBMITSTATE  IN ('02','03','04')	 \n");

			if(initentry.equals("first")){
				selectQuery.append("	                   AND B.TGTDEPTCD = ? 							 	    \n");
				selectQuery.append("                       AND C.INPUTUSRID = ? 								\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) selectQuery.append("\n  AND B.TGTDEPTCD LIKE ?                           \n");
					if( !"".equals(sch_userid) ) selectQuery.append("\n  AND C.INPUTUSRID LIKE ?                          \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) selectQuery.append("\n  AND  B.TGTDEPTNM LIKE ? \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) selectQuery.append("\n  AND C.INPUTUSRNM LIKE ? \n");
				}else{
					selectQuery.append("                     AND B.TGTDEPTCD = ? 								\n");
					selectQuery.append("                     AND C.INPUTUSRID = ? 								\n");
				}
			}
			
			selectQuery.append("                           AND C.INPUTSTATE IN ('01', '02') \n");
			selectQuery.append("                           AND C.SYSDOCNO = S.SYSDOCNO(+) \n");		
			selectQuery.append("                           AND C.INPUTUSRID = S.INPUTUSRID(+) \n");		
			
			if ( gubun == 1 || gubun == 2) {
				selectQuery.append("                       AND NVL(S.GUBUN, 1) = ? \n");
			}
			
			selectQuery.append("                           ORDER BY A.ENDDT DESC, A.CRTDT DESC, A.UPTDT DESC) A2) A1) \n");			
			selectQuery.append("WHERE SEQ BETWEEN ? AND ? ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			int param = 1;
			//pstmt.setString(param++, userid);
			//pstmt.setString(param++, deptcd);

			if(initentry.equals("first")){
				pstmt.setString(param++, deptcd);
				pstmt.setString(param++, userid);
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) pstmt.setString(param++, "%"+ sch_deptcd +"%");
					if( !"".equals(sch_userid) ) pstmt.setString(param++, "%"+ sch_userid +"%");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) pstmt.setString(param++, "%"+ sch_deptnm +"%");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) pstmt.setString(param++, "%"+ sch_usernm +"%");
				}else{
					pstmt.setString(param++, deptcd);
					pstmt.setString(param++, userid);
				}
			}
			
			if ( gubun == 1 || gubun == 2) {
				pstmt.setInt(param++, gubun);
			}
			
			pstmt.setInt(param++, start);
			pstmt.setInt(param++, end);
									
			rs = pstmt.executeQuery();
			
			inList = new ArrayList();
			
			while (rs.next()) {
				InputingBean inputBean = new InputingBean();
				
				inputBean.setSeqno(rs.getInt("BUNHO"));
				inputBean.setFormseq(rs.getInt("FORMSEQ"));
				inputBean.setFormkind(rs.getString("FORMKIND"));
				inputBean.setSysdocno(rs.getInt("SYSDOCNO"));
				inputBean.setDoctitle(rs.getString("DOCTITLE"));		
				inputBean.setDeliverydt(rs.getString("DELIVERYDT"));
				inputBean.setColdeptnm(rs.getString("COLDEPTNM"));
				inputBean.setChrgusrnm(rs.getString("CHRGUSRNM"));
				inputBean.setEnddt(rs.getString("ENDDT"));
				inputBean.setInputusrid(rs.getString("INPUTUSRID"));
				
				if(rs.getInt("RDAY")< 0){
					inputBean.setRemaintime("마감시한 초과");
				} else if(rs.getInt("RDAY") == 0){
					if(rs.getInt("RTIME")<=0){
						inputBean.setRemaintime("마감시한 초과");
					}else if(rs.getInt("RTIME")==1){
						inputBean.setRemaintime(rs.getString("RTIME")+"시간미만");
					}else{
						inputBean.setRemaintime(rs.getString("RTIME")+"시간");
					}
				} else{
					inputBean.setRemaintime(rs.getString("RDAY")+"일 "+rs.getString("RTIME")+"시간");
				}
				
				inputBean.setGubun(rs.getInt("GUBUN"));
				
				inList.add(inputBean);
			}							
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return inList;
	}
	
	/**
	 * 입력완료 목록
	 * 
	 * @param userid : 사용자ID
	 * @param start : 목록 시작 인덱스
	 * @param end : 목록 마지막 인덱스
	 * @param searchtext : 검색어
	 * @param selyear : 선택년도
	 * 
	 * @return List : 입력완료목록
	 * @throws Exception 
	 */
	public List inputCompleteList(String userid, String deptcd, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end, String searchvalue, String selyear) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List completeList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			/*
			selectQuery.append("SELECT (CNT-SEQ+1) BUNHO, SEQ, FORMSEQ, FORMKIND, SYSDOCNO, DOCTITLE, SUBMISTATE, INUSRENDDT, \n");
			selectQuery.append("       INPUTENDREASON, COLDEPTNM, CHRGUSRNM, DELIVERYDT \n");
			selectQuery.append("  FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n");
			selectQuery.append("          FROM (SELECT ROWNUM SEQ, A2.* \n");
			selectQuery.append("                  FROM (SELECT F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, A.DOCTITLE, \n");
			selectQuery.append("			       			   (SELECT CCDNAME FROM CCD WHERE CCDCD = '004' AND CCDSUBCD = B.SUBMITSTATE) SUBMISTATE, \n");
			selectQuery.append("			       			   SUBSTR(C.INPUTCOMP,1,4)||'년 '||SUBSTR(C.INPUTCOMP,6,2)||'월 '||SUBSTR(C.INPUTCOMP,9,2)||'일' INUSRENDDT, \n");
			selectQuery.append("			                   (SELECT CCDNAME FROM CCD WHERE CCDCD = '005' AND CCDSUBCD = C.INPUTSTATE) INPUTENDREASON, \n");
			selectQuery.append("			                   A.COLDEPTNM, A.CHRGUSRNM, \n");
			selectQuery.append("			                   DECODE(A.DELIVERYDT, '', '', SUBSTR(A.DELIVERYDT,1,4)||'년 '||SUBSTR(A.DELIVERYDT,6,2)||'월 '||SUBSTR(A.DELIVERYDT,9,2)||'일') DELIVERYDT \n");
			selectQuery.append("			              FROM DOCMST A, TGTDEPT B, INPUTUSR C, \n");
			selectQuery.append("                               (SELECT SYSDOCNO, FORMSEQ, FORMKIND \n");
			selectQuery.append("                                FROM FORMMST F \n");
			selectQuery.append("                                WHERE FORMSEQ = (SELECT MIN(FORMSEQ) FROM FORMMST WHERE SYSDOCNO = F.SYSDOCNO and to_number(formkind) <= 3)) F \n");
			selectQuery.append("			             WHERE A.SYSDOCNO   = B.SYSDOCNO \n");
			selectQuery.append("			               AND A.SYSDOCNO   = C.SYSDOCNO \n");
			selectQuery.append("			               AND A.SYSDOCNO   = F.SYSDOCNO \n");
			selectQuery.append("			               AND B.TGTDEPTCD  = C.TGTDEPT \n");
			selectQuery.append("			               AND C.INPUTUSRID = ? \n");
			selectQuery.append(" 						   AND C.TGTDEPT = ?    \n");		
			selectQuery.append("			               AND C.INPUTSTATE NOT IN('01', '02') \n");
			selectQuery.append("                           AND A.DOCTITLE LIKE ? \n");
			selectQuery.append("                           AND C.INPUTCOMP LIKE ? \n");
			selectQuery.append("			              ORDER BY A.ENDDT DESC) A2) A1) \n");
			selectQuery.append("WHERE SEQ BETWEEN ? AND ? ");
			*/

			selectQuery.append("SELECT (CNT-SEQ+1) BUNHO, SEQ, FORMSEQ, FORMKIND, SYSDOCNO, DOCTITLE, SUBMISTATE, INUSRENDDT, INPUTUSRID, \n");
			selectQuery.append("       INPUTENDREASON, COLDEPTNM, CHRGUSRNM, DELIVERYDT \n");
			selectQuery.append("  FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n");
			selectQuery.append("          FROM (SELECT ROWNUM SEQ, A2.* \n");
			selectQuery.append("                  FROM (SELECT F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, A.DOCTITLE, C.INPUTUSRID, \n");
			selectQuery.append("			       			   G1.CCDNAME SUBMISTATE, \n");
			selectQuery.append("			       			   SUBSTR(C.INPUTCOMP,1,4)||'년 '||SUBSTR(C.INPUTCOMP,6,2)||'월 '||SUBSTR(C.INPUTCOMP,9,2)||'일' INUSRENDDT, \n");
			selectQuery.append("			                   G2.CCDNAME INPUTENDREASON, \n");
			selectQuery.append("			                   A.COLDEPTNM, A.CHRGUSRNM, \n");
			selectQuery.append("			                   DECODE(A.DELIVERYDT, '', '', SUBSTR(A.DELIVERYDT,1,4)||'년 '||SUBSTR(A.DELIVERYDT,6,2)||'월 '||SUBSTR(A.DELIVERYDT,9,2)||'일') DELIVERYDT \n");
			selectQuery.append("			              FROM DOCMST A, TGTDEPT B, INPUTUSR C, \n");
			selectQuery.append("                               (SELECT SYSDOCNO, FORMSEQ, FORMKIND \n");
			selectQuery.append("                                FROM FORMMST F \n");
			selectQuery.append("                                WHERE FORMSEQ = (SELECT MIN(FORMSEQ) FROM FORMMST WHERE SYSDOCNO = F.SYSDOCNO and to_number(formkind) <= 3)) F, \n");
			selectQuery.append("                                (SELECT * FROM CCD WHERE CCDCD = '004') G1, \n");
			selectQuery.append("                                (SELECT * FROM CCD WHERE CCDCD = '005') G2 \n");
			selectQuery.append("			             WHERE A.SYSDOCNO   = B.SYSDOCNO \n");
			selectQuery.append("			               AND A.SYSDOCNO   = C.SYSDOCNO \n");
			selectQuery.append("			               AND A.SYSDOCNO   = F.SYSDOCNO \n");
			selectQuery.append("			               AND B.TGTDEPTCD  = C.TGTDEPT \n");
			selectQuery.append("			               AND G1.CCDSUBCD = B.SUBMITSTATE \n");
			selectQuery.append("			               AND G2.CCDSUBCD = C.INPUTSTATE \n");
			
			if(initentry.equals("first")){
				selectQuery.append("	                   AND B.TGTDEPTCD = ? 							 	    \n");
				selectQuery.append("                       AND C.INPUTUSRID = ? 								\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) selectQuery.append("\n  AND B.TGTDEPTCD LIKE ?                           \n");
					if( !"".equals(sch_userid) ) selectQuery.append("\n  AND C.INPUTUSRID LIKE ?                          \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) selectQuery.append("\n  AND  B.TGTDEPTNM LIKE ? \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) selectQuery.append("\n  AND C.INPUTUSRNM LIKE ? \n");
				}else{
					selectQuery.append("                     AND B.TGTDEPTCD = ? 								\n");
					selectQuery.append("                     AND C.INPUTUSRID = ? 								\n");
				}
			}
			
			selectQuery.append("			               AND C.INPUTSTATE NOT IN('01', '02') \n");
			selectQuery.append("                           AND A.DOCTITLE LIKE ? \n");
			selectQuery.append("                           AND C.INPUTCOMP LIKE ? \n");
			selectQuery.append("			              ORDER BY A.ENDDT DESC, A.CRTDT DESC, A.UPTDT DESC) A2) A1) \n");
			selectQuery.append("WHERE SEQ BETWEEN ? AND ? ");
			
			con = ConnectionManager.getConnection();
			
			//System.out.println("selectQuery.toString() : "  +selectQuery.toString());
			pstmt = con.prepareStatement(selectQuery.toString());
			int param = 1;
			//stmt.setString(1, userid);
			//pstmt.setString(2, deptcd);
			
			if(initentry.equals("first")){
				pstmt.setString(param++, deptcd);
				pstmt.setString(param++, userid);
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) pstmt.setString(param++, "%"+ sch_deptcd +"%");
					if( !"".equals(sch_userid) ) pstmt.setString(param++, "%"+ sch_userid +"%");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) pstmt.setString(param++, "%"+ sch_deptnm +"%");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) pstmt.setString(param++, "%"+ sch_usernm +"%");
				}else{
					pstmt.setString(param++, deptcd);
					pstmt.setString(param++, userid);
				}
			}
			
			pstmt.setString(param++, "%" + searchvalue + "%");
			pstmt.setString(param++, selyear + "%");
			pstmt.setInt(param++, start);
			pstmt.setInt(param++, end);
									
			rs = pstmt.executeQuery();
			
			completeList = new ArrayList();
			
			while (rs.next()) {
				InputCompleteBean completeBean = new InputCompleteBean();
				
				completeBean.setSeqno(rs.getInt("BUNHO"));
				completeBean.setFormseq(rs.getInt("FORMSEQ"));
				completeBean.setFormkind(rs.getString("FORMKIND"));
				completeBean.setSysdocno(rs.getInt("SYSDOCNO"));
				completeBean.setDoctitle(rs.getString("DOCTITLE"));
				completeBean.setSubmistate(rs.getString("SUBMISTATE"));
				completeBean.setInusrenddt(rs.getString("INUSRENDDT"));
				completeBean.setInputendreason(rs.getString("INPUTENDREASON"));
				completeBean.setColdeptnm(rs.getString("COLDEPTNM"));
				completeBean.setChrgusrnm(rs.getString("CHRGUSRNM"));
				completeBean.setDeliverydt(rs.getString("DELIVERYDT"));
				completeBean.setInputusrid(rs.getString("INPUTUSRID"));
				
				completeList.add(completeBean);
			}							
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return completeList;
	}
	
	/** 
	 * 입력완료처리시 입력상태 변경 처리	
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param usrid : 사용자ID
	 * @param uptusrid : 수정한 사용자ID
	 * @param state : 입력상태(01 : 입력대기, 02 : 임시저장, 03 : 해당없음, 04 : 입력완료, 05 : 마감시한 초과
	 * 
	 * @return int : 처리결과
	 * @throws Exception : SQL관련 예외
	 */
	public int updateInputState(Connection conn, int sysdocno, String usrid, String deptcd, String uptusrid, String state) throws Exception {
		PreparedStatement pstmt = null;
		int fetchcnt = 0;
		
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("UPDATE INPUTUSR \n");
		updateQuery.append("   SET INPUTSTATE = ? \n");
		updateQuery.append("     , CHRGUNITCD   = ? \n");
		updateQuery.append("     , CHRGUNITNM   = ? \n");
		updateQuery.append("     , INPUTCOMP  = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') \n");
		updateQuery.append("     , UPTDT      = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') \n");
		updateQuery.append("     , UPTUSRID   = ? \n");
		updateQuery.append(" WHERE SYSDOCNO   = ? \n");
		updateQuery.append("   AND INPUTUSRID = ? ");
		
		try{
			pstmt = conn.prepareStatement(updateQuery.toString());
			pstmt.setString(1, state);
			pstmt.setInt(2, DeptManager.instance().getChrgunitcd(usrid));
			pstmt.setString(3, DeptManager.instance().getChrgunitnm(deptcd, DeptManager.instance().getChrgunitcd(usrid)));
			pstmt.setString(4, uptusrid);
			pstmt.setInt(5, sysdocno);
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
	 * 입력완료처리시 제출상태 변경 - 상태가 입력진행(02)인것만 처리
	 * 
	 * @param usrid : 사용자ID
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * @param state : 제출상태(01 : 배부대기, 02 : 입력진행, 03 : 검토대기, 04 : 승인대기, 05 : 제출완료, 06 : 반송
	 * 
	 * @return int : 처리결과
	 * @throws Exception : SQL관련 예외
	 */
	public int updateSubmitState(Connection conn, int sysdocno, String usrid, String deptcd, String state) throws Exception {
		PreparedStatement pstmt = null;
		int fetchcnt = 0;
		
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("UPDATE TGTDEPT \n");
		updateQuery.append("   SET SUBMITSTATE = ? \n");
		updateQuery.append("     , SUBMITDT	   = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') \n");
		updateQuery.append("     , UPTDT       = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') \n");
		updateQuery.append("     , UPTUSRID    = ? \n");
		updateQuery.append(" WHERE SYSDOCNO    = ? \n");
		updateQuery.append("   AND TGTDEPTCD   = ? \n" +
				           "   AND SUBMITSTATE = '02' ");
		
		try{
			pstmt = conn.prepareStatement(updateQuery.toString());
			pstmt.setString(1, state);
			pstmt.setString(2, usrid);
			pstmt.setInt(3, sysdocno);
			pstmt.setString(4, deptcd);
				
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
	 * 입력완료처리시 제출부서 결재 기안일시 변경
	 * 
	 * @param usrid : 사용자ID
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * @param gubun : 검토/승인 구분(1 : 검토, 2 : 승인)
	 * 
	 * @return int : 처리결과
	 * @throws Exception : SQL관련 예외
	 */
	public int updateSubmitDT(Connection conn, int sysdocno, String usrid, String deptcd, String gubun) throws Exception {
		PreparedStatement pstmt = null;
		int fetchcnt = 0;
		
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("UPDATE SANCTGT \n");
		updateQuery.append("   SET SUBMITDT   = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') \n");
		updateQuery.append("     , UPTDT      = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') \n");
		updateQuery.append("     , UPTUSRID   = ? \n");
		updateQuery.append(" WHERE SYSDOCNO   = ? \n");
		updateQuery.append("   AND TGTDEPTCD  = ? \n");
		updateQuery.append("   AND GUBUN      = ? ");
		
		try{
			pstmt = conn.prepareStatement(updateQuery.toString());
			pstmt.setString(1, usrid);
			pstmt.setInt(2, sysdocno);
			pstmt.setString(3, deptcd);
			pstmt.setString(4, gubun);
				
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
	 * 입력완료처리시 문서상태 변경
	 * 
	 * @param conn : Connection 객체
	 * @param sysdocno : 시스템 문서번호
	 * @param usrid : 사용자ID
	 * @param docstate : 변경할 문서상태
	 * 
	 * @return int : 처리결과 
	 * @throws Exception : SQL관련 예외
	 */
	public int updateDocState(Connection conn, int sysdocno, String usrid, String docstate) throws Exception {
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
	 * 취합문서 해당없음 처리시 입력된 데이터 삭제
	 * 
	 * @param conn : Connection 객체
	 * @param tblname : 데이터를 삭제할 테이블명
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * 
	 * @return int : 처리결과 
	 * @throws Exception 
	 */
	public int deleteInputData(Connection conn, String tblname, int sysdocno, String deptcd, String usrid) throws Exception {
		PreparedStatement pstmt = null;
		int fetchcnt = 0;
		
		StringBuffer deleteQuery = new StringBuffer();
		deleteQuery.append("\n DELETE                ");
		deleteQuery.append("\n   FROM " + tblname);
		deleteQuery.append("\n  WHERE SYSDOCNO   = ? ");
		deleteQuery.append("\n    AND TGTDEPTCD  = ? ");
		deleteQuery.append("\n    AND INPUTUSRID = ? ");
		
		try{
			pstmt = conn.prepareStatement(deleteQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
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
	 * 입력완료처리시 자신이 마지막 입력자인지 체크
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param usrid : 사용자ID
	 * 
	 * @return true : 마지막 사용자 O, false : 마지막 사용자 X	
	 * @throws Exception 
	 */
	public boolean IsLastInputUsr(Connection conn, int sysdocno, String usrid, String deptcd) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean check = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n SELECT COUNT(*) CNT 					");
			selectQuery.append("\n   FROM INPUTUSR 						");
			selectQuery.append("\n  WHERE SYSDOCNO = ? 					");
			selectQuery.append("\n    AND INPUTSTATE NOT IN('03', '04', '05') ");
			selectQuery.append("\n    AND INPUTUSRID != ? 				");
			selectQuery.append("\n    AND TGTDEPT = ? 					");
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, usrid);
			pstmt.setString(3, deptcd);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				if(rs.getInt("CNT") > 0) {
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
	 * 입력완료처리시 검토자/승인자 존재 여부 확인
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * @param gubun : 검토/승인 구분(1 : 검토, 2 : 승인)
	 * 
	 * @return true : 검토자/승인자 존재 O, false : 검토자/승인자 존재X
	 * @throws Exception 
	 */
	public boolean checkExistApprovalUsr(int sysdocno, String deptcd, String gubun) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean check = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) CNT \n");
			selectQuery.append("  FROM SANCTGT \n");
			selectQuery.append(" WHERE SYSDOCNO  = ? \n");
			selectQuery.append("   AND TGTDEPTCD = ? \n");
			selectQuery.append("   AND GUBUN     = ? ");

			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
			pstmt.setString(3, gubun);
			
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
	 * 입력하기 - 입력담당자 담당단위 지정 여부 확인
	 * 
	 * @param usrid : 사용자ID
	 * 
	 * @return boolean : true - 담당자 지정 O, false - 담당자 지정 X	
	 * @throws Exception 
	 */
	public boolean IsAssignInputUsrcharge(String usrid) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean check = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			/*[USR_EXT] 테이블 삭제
			selectQuery.append("SELECT COUNT(*) CNT \n");
			selectQuery.append("  FROM CHRGUNIT A, USR B, USR_EXT C \n");
			selectQuery.append(" WHERE A.DEPT_ID = B.DEPT_ID \n");
			selectQuery.append("   AND B.USER_ID = C.USER_ID \n");
			selectQuery.append("   AND A.CHRGUNITCD = C.CHRGUNITCD \n");
			selectQuery.append("   AND B.USER_ID = ? ");
			*/

			selectQuery.append(" SELECT COUNT(*) CNT 				\n");
			selectQuery.append(" FROM CHRGUNIT A, USR B				\n");
			selectQuery.append(" WHERE A.DEPT_ID = B.DEPT_ID 		\n");
			selectQuery.append(" AND A.CHRGUNITCD = B.CHRGUNITCD 	\n");
			selectQuery.append(" AND B.USER_ID = ? 					\n");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, usrid);
			
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
	 * 입력하기 상세 - 입력데이터 존재 여부 체크(행추가형)
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * 
	 * @return true : 데이터 존재, false : 데이터 미존재	
	 * @throws Exception 
	 */
	public boolean IsExistDataLineFrm(int sysdocno, String deptcd, String usrid) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean check = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) CNT 	\n");
			selectQuery.append("  FROM DATALINEFRM 		\n");
			selectQuery.append(" WHERE SYSDOCNO   = ? 	\n");
			selectQuery.append("   AND TGTDEPTCD  = ? 	\n");
			selectQuery.append("   AND INPUTUSRID = ? 	");

			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
			pstmt.setString(3, usrid);
			
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
	 * 입력하기 상세 - 입력데이터 존재 여부 체크(고정양식형)
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * 
	 * @return true : 데이터 존재, false : 데이터 미존재	
	 * @throws Exception 
	 */
	public boolean IsExistDataFixedFrm(int sysdocno, String deptcd, String usrid) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean check = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) CNT 	\n");
			selectQuery.append("  FROM DATAFIXEDFRM		\n");
			selectQuery.append(" WHERE SYSDOCNO   = ? 	\n");
			selectQuery.append("   AND TGTDEPTCD  = ? 	\n");
			selectQuery.append("   AND INPUTUSRID = ? 	");

			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
			pstmt.setString(3, usrid);
			
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
	 * 입력하기 상세 - 입력데이터 존재 여부 체크(제본자료형)
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * 
	 * @return true : 데이터 존재, false : 데이터 미존재	
	 * @throws Exception 
	 */
	public boolean IsExistDataBookFrm(int sysdocno, String deptcd, String usrid) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean check = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) CNT 	\n");
			selectQuery.append("  FROM DATABOOKFRM 		\n");
			selectQuery.append(" WHERE SYSDOCNO   = ? 	\n");
			selectQuery.append("   AND TGTDEPTCD  = ? 	\n");
			selectQuery.append("   AND INPUTUSRID = ? 	");

			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
			pstmt.setString(3, usrid);
			
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
	 * 입력하기 목록 갯수 가져오기	
	 * 
	 * @param userid : 사용자ID
	 * 
	 * @return int : 목록 카운트
	 * @throws Exception 
	 */
	public int inputingCnt(String userid, String deptcd, int gubun, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			/*
			selectQuery.append("SELECT COUNT(*) \n");
			selectQuery.append("  FROM DOCMST A, TGTDEPT B, INPUTUSR C, \n");
			selectQuery.append("       (SELECT SYSDOCNO, 2 GUBUN FROM SANCTGT WHERE SANCYN = 0) S \n");
			selectQuery.append(" WHERE A.SYSDOCNO  = B.SYSDOCNO \n");
			selectQuery.append("   AND A.SYSDOCNO  = C.SYSDOCNO \n");
			selectQuery.append("   AND B.TGTDEPTCD = C.TGTDEPT \n");
			selectQuery.append("   AND A.DOCSTATE  = '04' \n");
			selectQuery.append("   AND C.INPUTUSRID = ? \n");
			selectQuery.append("   AND C.TGTDEPT = ?    \n");
			selectQuery.append("   AND B.SUBMITSTATE = '02' \n");
			selectQuery.append("   AND C.INPUTSTATE IN ('01', '02') ");
			selectQuery.append("   AND C.SYSDOCNO = S.SYSDOCNO(+) \n");	
			*/

			selectQuery.append("SELECT COUNT(*)												\n");
			selectQuery.append("  FROM DOCMST A, TGTDEPT B, INPUTUSR C, 					\n");
			selectQuery.append("       (SELECT SYSDOCNO, 2 GUBUN, INPUTUSRID FROM SANCTGT WHERE SANCYN = 0) S \n");
			selectQuery.append(" WHERE A.SYSDOCNO  = B.SYSDOCNO 							\n");
			selectQuery.append("   AND A.SYSDOCNO  = C.SYSDOCNO 							\n");
			selectQuery.append("   AND B.TGTDEPTCD = C.TGTDEPT 								\n");
			selectQuery.append("   AND A.DOCSTATE  = '04' 									\n");
			selectQuery.append("   AND B.SUBMITSTATE  IN ('02','03','04')	 				\n");

			if(initentry.equals("first")){
				selectQuery.append("	AND B.TGTDEPTCD = ? 							 	\n");
				selectQuery.append("    AND C.INPUTUSRID = ? 								\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) selectQuery.append("\n  AND B.TGTDEPTCD LIKE ?                           \n");
					if( !"".equals(sch_userid) ) selectQuery.append("\n  AND C.INPUTUSRID LIKE ?                          \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) selectQuery.append("\n  AND  B.TGTDEPTNM LIKE ? \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) selectQuery.append("\n  AND C.INPUTUSRNM LIKE ? \n");
				}else{
					selectQuery.append("  AND B.TGTDEPTCD = ? 								\n");
					selectQuery.append("  AND C.INPUTUSRID = ? 								\n");
				}
			}
			
			selectQuery.append("   AND C.INPUTSTATE IN ('01', '02') 						\n");
			selectQuery.append("   AND C.SYSDOCNO = S.SYSDOCNO(+) 							\n");
			selectQuery.append("   AND C.INPUTUSRID = S.INPUTUSRID(+)                       \n");
			
			if ( gubun == 1 || gubun == 2) {
				selectQuery.append("AND NVL(S.GUBUN, 1) = ? 								\n");
			}
			
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(selectQuery.toString());
			int param = 1;
			//pstmt.setString(1, userid);
			//pstmt.setString(2, deptcd);

			if(initentry.equals("first")){
				pstmt.setString(param++, deptcd);
				pstmt.setString(param++, userid);
				
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) pstmt.setString(param++, "%"+ sch_deptcd +"%");
					if( !"".equals(sch_userid) ) pstmt.setString(param++, "%"+ sch_userid +"%");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) pstmt.setString(param++, "%"+ sch_deptnm +"%");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) pstmt.setString(param++, "%"+ sch_usernm +"%");
				}else{
					pstmt.setString(param++, deptcd);
					pstmt.setString(param++, userid);
				}
			}
			
			if ( gubun == 1 || gubun == 2) {
				pstmt.setInt(param++, gubun);
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
	 * 입력하기 목록 갯수 가져오기	
	 * 
	 * @param userid : 사용자ID
	 * 
	 * @return int : 목록 카운트
	 * @throws Exception 
	 */
	public int inputingTotCnt(String userid, String deptcd, int gubun) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) \n");
			selectQuery.append("  FROM DOCMST A, TGTDEPT B, INPUTUSR C, \n");
			selectQuery.append("       (SELECT SYSDOCNO, 2 GUBUN, INPUTUSRID FROM SANCTGT WHERE SANCYN = 0) S \n");
			selectQuery.append(" WHERE A.SYSDOCNO  = B.SYSDOCNO \n");
			selectQuery.append("   AND A.SYSDOCNO  = C.SYSDOCNO \n");
			selectQuery.append("   AND B.TGTDEPTCD = C.TGTDEPT \n");
			selectQuery.append("   AND A.DOCSTATE  = '04' \n");
			selectQuery.append("   AND C.INPUTUSRID = ? \n");
			selectQuery.append("   AND C.TGTDEPT = ?    \n");
			selectQuery.append("   AND B.SUBMITSTATE = '02' \n");
			selectQuery.append("   AND C.INPUTSTATE IN ('01', '02') ");
			selectQuery.append("   AND C.SYSDOCNO = S.SYSDOCNO(+) \n");			
			selectQuery.append("   AND C.INPUTUSRID = S.INPUTUSRID(+) \n");			
			
			if ( gubun == 1 || gubun == 2) {
				selectQuery.append("AND NVL(S.GUBUN, 1) = ? \n");
			}
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, userid);
			pstmt.setString(2, deptcd);
			
			if ( gubun == 1 || gubun == 2 ) {
				pstmt.setInt(3, gubun);
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
	 * 입력완료 목록 갯수 가져오기	
	 * 
	 * @param userid : 사용자ID
	 * @param searchvalue : 검색어
	 * @param selyear : 선택년도
	 * 
	 * @return int : 목록 카운트
	 * @throws Exception 
	 */
	public int inputCompleteCnt(String userid, String deptcd, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, String searchvalue, String selyear) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();

			selectQuery.append(" SELECT COUNT(*) 							\n");
			selectQuery.append(" FROM DOCMST A, TGTDEPT B, INPUTUSR C 		\n");
			selectQuery.append(" WHERE A.SYSDOCNO  = B.SYSDOCNO 			\n");
			selectQuery.append(" AND A.SYSDOCNO  = C.SYSDOCNO				\n");
			selectQuery.append(" AND B.TGTDEPTCD = C.TGTDEPT 				\n");
			
			if(initentry.equals("first")){
				selectQuery.append("	                   AND B.TGTDEPTCD = ? 							 	    \n");
				selectQuery.append("                       AND C.INPUTUSRID = ? 								\n");
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) selectQuery.append("\n  AND B.TGTDEPTCD LIKE ?                           \n");
					if( !"".equals(sch_userid) ) selectQuery.append("\n  AND C.INPUTUSRID LIKE ?                          \n");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) selectQuery.append("\n  AND  B.TGTDEPTNM LIKE ? \n");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) selectQuery.append("\n  AND C.INPUTUSRNM LIKE ? \n");
				}else{
					selectQuery.append("                     AND B.TGTDEPTCD = ? 								\n");
					selectQuery.append("                     AND C.INPUTUSRID = ? 								\n");
				}
			}
			
			selectQuery.append("   AND C.INPUTSTATE NOT IN('01', '02') \n");
			selectQuery.append("   AND A.DOCTITLE LIKE ? \n");
			selectQuery.append("   AND C.INPUTCOMP LIKE ? ");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			int param = 1;
			//pstmt.setString(1, userid);
			//pstmt.setString(2, deptcd);
			
			if(initentry.equals("first")){
				pstmt.setString(param++, deptcd);
				pstmt.setString(param++, userid);
			}else{
				if(isSysMgr.equals("001")){
					if( !"".equals(sch_deptcd) ) pstmt.setString(param++, "%"+ sch_deptcd +"%");
					if( !"".equals(sch_userid) ) pstmt.setString(param++, "%"+ sch_userid +"%");
					if( "".equals(sch_deptcd) && !"".equals(sch_deptnm) ) pstmt.setString(param++, "%"+ sch_deptnm +"%");
					if( "".equals(sch_userid) && !"".equals(sch_usernm) ) pstmt.setString(param++, "%"+ sch_usernm +"%");
				}else{
					pstmt.setString(param++, deptcd);
					pstmt.setString(param++, userid);
				}
			}
			
			pstmt.setString(param++, "%" + searchvalue + "%");
			pstmt.setString(param++, "%" + selyear + "%");
			
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
	 * 입력완료 목록 갯수 가져오기	
	 * 
	 * @param userid : 사용자ID
	 * @param searchvalue : 검색어
	 * @param selyear : 선택년도
	 * 
	 * @return int : 목록 카운트
	 * @throws Exception 
	 */
	public int inputCompleteTotCnt(String userid, String deptcd, String searchvalue, String selyear) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) \n");
			selectQuery.append("  FROM DOCMST A, TGTDEPT B, INPUTUSR C \n");
			selectQuery.append(" WHERE A.SYSDOCNO  = B.SYSDOCNO \n");
			selectQuery.append("   AND A.SYSDOCNO  = C.SYSDOCNO \n");
			selectQuery.append("   AND B.TGTDEPTCD = C.TGTDEPT \n");
			selectQuery.append("   AND C.INPUTUSRID = ? \n");
			selectQuery.append("   AND C.TGTDEPT = ?    \n");
			selectQuery.append("   AND C.INPUTSTATE NOT IN('01', '02') \n");
			selectQuery.append("   AND A.DOCTITLE LIKE ? \n");
			selectQuery.append("   AND C.INPUTCOMP LIKE ? ");

			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, userid);
			pstmt.setString(2, deptcd);
			pstmt.setString(3, "%" + searchvalue + "%");
			pstmt.setString(4, "%" + selyear + "%");
			
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
	 * 결재진행중인 건수 가져오기-(입력현황-메인화면)
	 * 
	 * @param usrid : 사용자ID
	 * 
	 * @return int : 결재진행중인 건수
	 * @throws Exception 
	 */
	public int procCount(String usrid, String deptcd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n	SELECT COUNT(*) ");
			selectQuery.append("\n	FROM TGTDEPT A, INPUTUSR B ");
			selectQuery.append("\n 	WHERE A.SYSDOCNO  = B.SYSDOCNO ");
			selectQuery.append("\n    AND A.TGTDEPTCD = B.TGTDEPT ");
			selectQuery.append("\n    AND B.INPUTUSRID = ? ");
			selectQuery.append("\n    AND B.TGTDEPT = ? ");
			selectQuery.append("\n    AND A.SUBMITSTATE IN ('03','04') ");			

			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, usrid);	
			pstmt.setString(2, deptcd);
			
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
	 * 입력하기 - 관리자인경우 검색 조건에 해당하는 값 가져오기 
	 */
	public String getSearchInputing(int gubun, String sch_deptcd, String sch_userid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
			
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT COUNT(*) AS CNT				 		 									\n");
			selectQuery.append(" FROM(										 		 							\n");
			selectQuery.append(" SELECT  B.TGTDEPTCD, C.INPUTUSRID					 		 					\n");
			selectQuery.append("  FROM DOCMST A, TGTDEPT B, INPUTUSR C, 					 		 			\n");
			selectQuery.append("       (SELECT SYSDOCNO, 2 GUBUN, INPUTUSRID FROM SANCTGT WHERE SANCYN = 0) S 	\n");
			selectQuery.append(" WHERE A.SYSDOCNO  = B.SYSDOCNO 		 		 								\n");
			selectQuery.append("   AND A.SYSDOCNO  = C.SYSDOCNO 				 		 						\n");
			selectQuery.append("   AND B.TGTDEPTCD = C.TGTDEPT								 		 			\n");
			selectQuery.append("   AND A.DOCSTATE  = '04' 							 		 					\n");
			selectQuery.append("   AND B.SUBMITSTATE IN ('02','03','04')	 		 							\n");
			selectQuery.append("   AND C.INPUTSTATE IN ('01', '02') 					            			\n");
			selectQuery.append("   AND C.SYSDOCNO = S.SYSDOCNO(+) 			             						\n");		
			selectQuery.append("   AND C.INPUTUSRID = S.INPUTUSRID(+)        									\n");		
			selectQuery.append("   AND B.TGTDEPTCD LIKE  '%"+sch_deptcd+"%'  		 							\n");
			selectQuery.append("   AND C.CHRGUNITCD LIKE '%"+sch_userid+"%'   		 		 					\n");
			if ( gubun == 1 || gubun == 2) {
				selectQuery.append("   AND NVL(S.GUBUN, 1) = "+gubun+"	 		 								\n");
			}
			selectQuery.append("   GROUP BY B.TGTDEPTCD, C.INPUTUSRID)							 		 		\n");
			   
			
			//System.out.println("selectQuery.toString() : "  +selectQuery.toString());
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("CNT") == 1){
					ConnectionManager.close(pstmt, rs);
					
					if(!"".equals(sch_deptcd)&& sch_deptcd != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT C.TGTDEPT AS TGTDEPT			     			          				\n");
						selectQuery.append("  FROM DOCMST A, TGTDEPT B, INPUTUSR C,      		     			     		\n");
						selectQuery.append("       (SELECT SYSDOCNO, 2 GUBUN, INPUTUSRID FROM SANCTGT WHERE SANCYN = 0) S 	\n");
						selectQuery.append(" WHERE A.SYSDOCNO  = B.SYSDOCNO 			     					          	\n");
						selectQuery.append("   AND A.SYSDOCNO  = C.SYSDOCNO 	          						     		\n");
						selectQuery.append("   AND B.TGTDEPTCD = C.TGTDEPT		     	     				     			\n");
						selectQuery.append("   AND A.DOCSTATE  = '04' 					          		     				\n");
						selectQuery.append("   AND B.SUBMITSTATE IN ('02','03','04')     	 	          					\n");
						selectQuery.append("   AND C.INPUTSTATE IN ('01', '02') 	     	          						\n");
						selectQuery.append("   AND C.SYSDOCNO = S.SYSDOCNO(+) 		          		     					\n");		
						selectQuery.append("   AND C.INPUTUSRID = S.INPUTUSRID(+) 		     		     					\n");		
						selectQuery.append("   AND B.TGTDEPTCD LIKE  '%"+sch_deptcd+"%' 	     	     		     		\n");
						if ( gubun == 1 || gubun == 2) {
							selectQuery.append("   AND NVL(S.GUBUN, 1) = "+gubun+"						               		\n");
						}
						selectQuery.append("   GROUP BY C.TGTDEPT							               					\n");

						pstmt = con.prepareStatement(selectQuery.toString());
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							result = rs.getString("TGTDEPT");
						}
						ConnectionManager.close(pstmt, rs);
					}
					
					if(!"".equals(sch_userid)&& sch_userid != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT B.CHRGUSRID AS CHRGUSRID												\n");
						selectQuery.append("  FROM DOCMST A, TGTDEPT B, INPUTUSR C, 										\n");
						selectQuery.append("       (SELECT SYSDOCNO, 2 GUBUN, INPUTUSRID FROM SANCTGT WHERE SANCYN = 0) S 	\n");
						selectQuery.append(" WHERE A.SYSDOCNO  = B.SYSDOCNO 												\n");
						selectQuery.append("   AND A.SYSDOCNO  = C.SYSDOCNO 												\n");
						selectQuery.append("   AND B.TGTDEPTCD = C.TGTDEPT													\n");
						selectQuery.append("   AND A.DOCSTATE  = '04' 														\n");
						selectQuery.append("   AND B.SUBMITSTATE IN ('02','03','04')	 									\n");
						selectQuery.append("   AND C.INPUTSTATE IN ('01', '02') 											\n");
						selectQuery.append("   AND C.SYSDOCNO = S.SYSDOCNO(+) 												\n");		
						selectQuery.append("   AND C.INPUTUSRID = S.INPUTUSRID(+) 											\n");		
						selectQuery.append("   AND C.CHRGUNITCD LIKE  '%"+sch_userid+"%' 									\n");
						if ( gubun == 1 || gubun == 2) {
							selectQuery.append("   AND NVL(S.GUBUN, 1) = "+gubun+"											\n");
						}
						selectQuery.append("   GROUP BY B.CHRGUSRID															\n");

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
	 * 입력완료 - 관리자인경우 검색 조건에 해당하는 값 가져오기
	 */
	public String getSearchInputComplete(String gubun, String sch_deptcd, String sch_userid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
			
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT COUNT(*) AS CNT                     \n");
			selectQuery.append(" FROM DOCMST A, TGTDEPT B, INPUTUSR C       \n");
			selectQuery.append(" WHERE A.SYSDOCNO  = B.SYSDOCNO             \n");
			selectQuery.append(" AND A.SYSDOCNO  = C.SYSDOCNO               \n");
			selectQuery.append(" AND B.TGTDEPTCD = C.TGTDEPT                \n");	
			selectQuery.append(" AND B.TGTDEPTCD LIKE  '%"+sch_deptcd+"%' 	\n");
			selectQuery.append(" AND C.CHRGUNITCD LIKE '%"+sch_userid+"%'   \n");
			selectQuery.append(" AND C.INPUTSTATE NOT IN('01', '02')        \n");
			selectQuery.append(" AND C.INPUTCOMP LIKE '%"+gubun+"%'         \n");
			   
			
			//System.out.println("selectQuery.toString() : "  +selectQuery.toString());
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("CNT") == 1){
					ConnectionManager.close(pstmt, rs);
					
					if(!"".equals(sch_deptcd)&& sch_deptcd != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT C.TGTDEPT AS TGTDEPT				\n");
						selectQuery.append(" FROM DOCMST A, TGTDEPT B, INPUTUSR C       \n");
						selectQuery.append(" WHERE A.SYSDOCNO  = B.SYSDOCNO             \n");
						selectQuery.append(" AND A.SYSDOCNO  = C.SYSDOCNO               \n");
						selectQuery.append(" AND B.TGTDEPTCD = C.TGTDEPT                \n");	
						selectQuery.append(" AND B.TGTDEPTCD LIKE  '%"+sch_deptcd+"%' 	\n");
						selectQuery.append(" AND C.INPUTSTATE NOT IN('01', '02')        \n");
						selectQuery.append(" AND C.INPUTCOMP LIKE '%"+gubun+"%'         \n");

						pstmt = con.prepareStatement(selectQuery.toString());
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							result = rs.getString("TGTDEPT");
						}
						ConnectionManager.close(pstmt, rs);
					}
					
					if(!"".equals(sch_userid)&& sch_userid != null){
						selectQuery = new StringBuffer();
						selectQuery.append(" SELECT B.CHRGUSRID AS CHRGUSRID			\n");
						selectQuery.append(" FROM DOCMST A, TGTDEPT B, INPUTUSR C       \n");
						selectQuery.append(" WHERE A.SYSDOCNO  = B.SYSDOCNO             \n");
						selectQuery.append(" AND A.SYSDOCNO  = C.SYSDOCNO               \n");
						selectQuery.append(" AND B.TGTDEPTCD = C.TGTDEPT                \n");
						selectQuery.append(" AND C.CHRGUNITCD LIKE '%"+sch_userid+"%'   \n");
						selectQuery.append(" AND C.INPUTSTATE NOT IN('01', '02')        \n");
						selectQuery.append(" AND C.INPUTCOMP LIKE '%"+gubun+"%'         \n");

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
