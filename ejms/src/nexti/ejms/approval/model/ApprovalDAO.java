/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 결재하기 dao
 * 설명:
 */
package nexti.ejms.approval.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.util.DateTime;

public class ApprovalDAO {
	
	private static Logger logger = Logger.getLogger(ApprovalDAO.class);
	
	/**
	 * 취합부서 결재완료 여부 체크
	 * gubun(1) : 검토, gubun(2) : 승인
	 * @param sysdocno 시스템문서번호
	 * @param gubun 구분
	 * @return 결재완료sysdocno,tgtdeptcd,sancusrid(1명)
	 */
	public String[] isColSancComplete(int sysdocno, String gubun) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] result = null;
		
		try {
			String sql = 
				"SELECT * " +
				"FROM SANCCOL " +
				"WHERE SYSDOCNO = ? " +
				"  AND GUBUN = ? ";
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, gubun);
			
			rs = pstmt.executeQuery();
			
			if(rs.next() == true) {
				if(rs.getInt(1) > 0) {	
					sql = 
						"SELECT COUNT(*) " +
						"FROM SANCCOL " +
						"WHERE SYSDOCNO = ? " +
						"  AND GUBUN = ? " +
						"  AND SANCYN = '0' ";
					
					try {rs.close();} catch(Exception e) {}
					try {pstmt.close();} catch(Exception e) {}
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, sysdocno);
					pstmt.setString(2, gubun);
					
					rs = pstmt.executeQuery();
					
					if(rs.next() == true) {
						if(rs.getInt(1) == 0) {
							sql =
								"SELECT SYSDOCNO, SANCUSRID " +
								"FROM SANCCOL " +
								"WHERE SYSDOCNO = ? " +
								"  AND GUBUN = ? " +
								"  AND SANCYN = '1' " +
								"  AND ROWNUM < 2 ";
							
							try {rs.close();} catch(Exception e) {}
							try {pstmt.close();} catch(Exception e) {}
							pstmt = con.prepareStatement(sql);
							pstmt.setInt(1, sysdocno);
							pstmt.setString(2, gubun);
							
							rs = pstmt.executeQuery();
							
							if(rs.next() == true) {
								result = new String[2];
								
								result[0] = rs.getString("SYSDOCNO");
								result[1] = rs.getString("SANCUSRID");
							}
						} else {
							result = new String[2];
							
							result[0] = "-1";
							result[1] = "-1";
						}
					}				
				}
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 제출부서 결재완료 여부 체크
	 * gubun(1) : 검토, gubun(2) : 승인
	 * @param sysdocno 시스템문서번호
	 * @param tgtdeptcd 제출부서코드
	 * @param gubun 구분
	 * @return 결재완료sysdocno,tgtdeptcd,sancusrid(1명)
	 */
	public String[] isTgtSancComplete(int sysdocno, String tgtdeptcd, String gubun) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] result = null;
		
		try {
			String sql = 
				"SELECT * " +
				"FROM SANCTGT " +
				"WHERE SYSDOCNO = ? " +
				"  AND TGTDEPTCD = ? " +
				"  AND GUBUN = ? ";
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, tgtdeptcd);
			pstmt.setString(3, gubun);
			
			rs = pstmt.executeQuery();
			
			if(rs.next() == true) {
				if(rs.getInt(1) > 0) {	
					sql = 
						"SELECT COUNT(*) " +
						"FROM SANCTGT " +
						"WHERE SYSDOCNO = ? " +
						"  AND TGTDEPTCD = ? " +
						"  AND GUBUN = ? " +
						"  AND SANCYN = '0' ";
					
					try {rs.close();} catch(Exception e) {}
					try {pstmt.close();} catch(Exception e) {}
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, sysdocno);
					pstmt.setString(2, tgtdeptcd);
					pstmt.setString(3, gubun);
					
					rs = pstmt.executeQuery();
					
					if(rs.next() == true) {
						if(rs.getInt(1) == 0) {
							sql =
								"SELECT SYSDOCNO, TGTDEPTCD, SANCUSRID " +
								"FROM SANCTGT " +
								"WHERE SYSDOCNO = ? " +
								"  AND TGTDEPTCD = ? " +
								"  AND GUBUN = ? " +
								"  AND SANCYN = '1' " +
								"  AND ROWNUM < 2 ";
							
							try {rs.close();} catch(Exception e) {}
							try {pstmt.close();} catch(Exception e) {}
							pstmt = con.prepareStatement(sql);
							pstmt.setInt(1, sysdocno);
							pstmt.setString(2, tgtdeptcd);
							pstmt.setString(3, gubun);
							
							rs = pstmt.executeQuery();
							
							if(rs.next() == true) {
								result = new String[3];
								
								result[0] = rs.getString("SYSDOCNO");
								result[1] = rs.getString("TGTDEPTCD");
								result[2] = rs.getString("SANCUSRID");
							}
						} else {
							result = new String[3];
							
							result[0] = "-1";
							result[1] = "-1";
							result[2] = "-1";
						}
					}				
				}
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 결재하기 목록
	 * @throws Exception 
	 */
	public List appList(SearchBean search, String deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List approvalList = null;
		StringBuffer selectQuery = null;
		
		try {
			selectQuery = new StringBuffer();

			selectQuery.append("SELECT (CNT-SEQNO+1) BUNHO, FORMSEQ, FORMKIND, SYSDOCNO, REQSEQ, DOCTITLE, DOCGBN, COLDEPTNM, GUBUN, \n");			
			selectQuery.append("        SUBSTR(SDT,1,4)||'년'||SUBSTR(SDT,6,2)||'월'||SUBSTR(SDT,9,2)||'일 '||SUBSTR(SDT,12,2)||'시'||SUBSTR(SDT,15,2)||'분' SDT \n");
			selectQuery.append("FROM (SELECT (MAX(SEQNO)OVER()) CNT, SEQNO, FORMSEQ, FORMKIND, SYSDOCNO, REQSEQ, DOCTITLE, DOCGBN, COLDEPTNM, GUBUN, SDT \n");
			selectQuery.append("      FROM (SELECT ROWNUM SEQNO, FORMSEQ, FORMKIND, SYSDOCNO, REQSEQ, DOCTITLE, DOCGBN, COLDEPTNM, GUBUN, SDT \n");
			selectQuery.append("            FROM (SELECT FORMSEQ, FORMKIND, SYSDOCNO, REQSEQ, DOCTITLE, DOCGBN, COLDEPTNM, GUBUN, SDT \n");
			selectQuery.append("                  FROM (SELECT F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, 0 REQSEQ, A.DOCTITLE, B.DOCGBN, A.COLDEPTNM, B.GUBUN, A.SUBMITDATE SDT \n");		
			selectQuery.append("                        FROM DOCMST A, (SELECT '배부문서' DOCGBN, A.SYSDOCNO, DECODE(B.GUBUN,'02','검토대기','승인대기') GUBUN, '' SUBMITDT \n");
			selectQuery.append("                                        FROM DOCMST A, (SELECT SYSDOCNO, MIN(DECODE(GUBUN,'1','02','03')) GUBUN \n");
			selectQuery.append("                                                        FROM SANCCOL \n");
			selectQuery.append("                                                        WHERE SANCUSRID = '"+search.getUser_id()+"' \n");
			selectQuery.append("                                                          AND SANCYN = '0' \n");
			selectQuery.append("                                                        GROUP BY SYSDOCNO) B \n");
			selectQuery.append("                                        WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("                                          AND A.DOCSTATE = B.GUBUN \n");	
			selectQuery.append("                                          AND A.COLDEPTCD = '"+deptcd+"' \n");
			selectQuery.append("                                        UNION ALL \n");
			selectQuery.append("                                        SELECT '제출문서' ,A.SYSDOCNO, DECODE(B.GUBUN,'03','검토대기','승인대기') GUBUN, A.SUBMITDT \n");
			selectQuery.append("                                        FROM TGTDEPT A, (SELECT SYSDOCNO, TGTDEPTCD , MIN(DECODE(GUBUN,'1','03','04')) GUBUN \n");
			selectQuery.append("                                                         FROM SANCTGT \n");
			selectQuery.append("                                                         WHERE SANCUSRID = '"+search.getUser_id()+"' \n");
			selectQuery.append("                                                           AND SANCYN ='0' \n");
			selectQuery.append("                                                         GROUP BY SYSDOCNO, TGTDEPTCD) B \n");
			selectQuery.append("                                        WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("                                          AND A.TGTDEPTCD = B.TGTDEPTCD \n");
			selectQuery.append("									      AND B.TGTDEPTCD = '"+deptcd+"' \n");			
			selectQuery.append("                                          AND A.SUBMITSTATE = B.GUBUN) B, \n");
			selectQuery.append("                              (SELECT SYSDOCNO, FORMSEQ, FORMKIND \n");
			selectQuery.append("                               FROM FORMMST F \n");
			selectQuery.append("                               WHERE FORMSEQ = (SELECT MIN(FORMSEQ) FROM FORMMST WHERE SYSDOCNO = F.SYSDOCNO and to_number(formkind) <= 3)) F \n");			
			selectQuery.append("                        WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("                          AND A.SYSDOCNO = F.SYSDOCNO \n");
			selectQuery.append("                          AND A.DOCSTATE > '01' \n");			
			
			//신청서 결재를 위하여 추가 됨
			selectQuery.append("                        UNION ALL \n");
			selectQuery.append("                        SELECT 0, '0', A.REQFORMNO, A.REQSEQ, C.TITLE, '제출문서', C.COLDEPTNM, DECODE(B.GUBUN,'1','검토대기','승인대기') GUBUN, A.CRTDT \n");
			selectQuery.append("                        FROM REQMST A, (SELECT A.REQFORMNO, A.REQSEQ, A.GUBUN \n");
			selectQuery.append("                                        FROM REQSANC A, (SELECT REQFORMNO, REQSEQ, MIN(GUBUN) GUBUN \n");
			selectQuery.append("                                                         FROM REQSANC \n");
			selectQuery.append("                                                         WHERE SANCYN = '0' \n");
			selectQuery.append("                                                         GROUP BY REQFORMNO, REQSEQ) B \n");
			selectQuery.append("                                        WHERE A.REQFORMNO= B.REQFORMNO \n");
			selectQuery.append("                                          AND A.REQSEQ = B.REQSEQ \n");
			selectQuery.append("                                          AND A.GUBUN = B.GUBUN \n");
			selectQuery.append("                                          AND A.SANCUSRID = '"+search.getUser_id()+"') B, REQFORMMST C \n");
			selectQuery.append("                        WHERE A.REQFORMNO = B.REQFORMNO \n");
			selectQuery.append("                          AND A.REQSEQ = B.REQSEQ \n");
			selectQuery.append("                          AND A.REQFORMNO = C.REQFORMNO \n");	
			//신청서 결재 끝
			selectQuery.append("                        ) \n");
			
			selectQuery.append("                  WHERE DOCGBN LIKE '"+search.getDocGbn()+"' \n");
			selectQuery.append("                  ORDER BY SDT DESC) A2) A1) \n");			
			selectQuery.append("WHERE SEQNO BETWEEN "+search.getStartidx()+" AND "+search.getEndidx()+" \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());

			rs = pstmt.executeQuery();
			
			approvalList = new ArrayList();
			
			while (rs.next()) {
				ApprovalBean bean = new ApprovalBean();
				
				bean.setBunho(rs.getInt("BUNHO"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setFormkind(rs.getString("FORMKIND"));
				bean.setReqseq(rs.getInt("REQSEQ"));
				bean.setSysdocno(rs.getInt("SYSDOCNO"));
				bean.setDoctitle(rs.getString("DOCTITLE"));
				bean.setProcstatus(rs.getString("GUBUN"));
				
				if("배부문서".equals(rs.getString("DOCGBN"))){
					bean.setSancgbn("1");
				} else {
					bean.setSancgbn("2");
				}				
				
				bean.setSancgbnnm(rs.getString("DOCGBN"));
				bean.setDeptnm(rs.getString("COLDEPTNM"));
				bean.setSubmitdate(rs.getString("SDT"));
				
				approvalList.add(bean);
			}							
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return approvalList;
	}
	
	/** 
	 * 결재하기 목록 갯수 가져오기	
	 * @throws Exception 
	 */
	public int appTotCnt(SearchBean search, String deptcd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(SYSDOCNO) \n");
			selectQuery.append("FROM (SELECT A.SYSDOCNO, B.DOCGBN \n");
			selectQuery.append("      FROM DOCMST A, (SELECT '배부문서' DOCGBN, A.SYSDOCNO \n");
			selectQuery.append("				      FROM DOCMST A, (SELECT SYSDOCNO, MIN(DECODE(GUBUN,'1','02','03')) GUBUN \n");
			selectQuery.append("                                      FROM SANCCOL \n");
			selectQuery.append("                                      WHERE SANCUSRID = ? \n");
			selectQuery.append("                                        AND SANCYN = '0' \n");
			selectQuery.append("                                      GROUP BY SYSDOCNO) B \n");
			selectQuery.append("                      WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("                        AND A.DOCSTATE = B.GUBUN \n");	
			selectQuery.append("				        AND A.COLDEPTCD = ? \n");
			selectQuery.append("                      UNION ALL \n");
			selectQuery.append("                      SELECT '제출문서' ,A.SYSDOCNO \n");
			selectQuery.append("                      FROM TGTDEPT A, (SELECT SYSDOCNO, TGTDEPTCD , MIN(DECODE(GUBUN,'1','03','04')) GUBUN \n");
			selectQuery.append("                                       FROM SANCTGT \n");
			selectQuery.append("                                       WHERE SANCUSRID = ? \n");
			selectQuery.append("                                         AND SANCYN ='0' \n");
			selectQuery.append("                                       GROUP BY SYSDOCNO, TGTDEPTCD) B \n");
			selectQuery.append("                      WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("                        AND A.TGTDEPTCD = B.TGTDEPTCD \n");
			selectQuery.append("				        AND B.TGTDEPTCD = ? \n");
			selectQuery.append("                        AND A.SUBMITSTATE = B.GUBUN) B \n");
			selectQuery.append("      WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("        AND A.DOCSTATE > '01' \n");
			
			//신청서 결재를 위하여 추가 됨
			selectQuery.append("      UNION ALL \n");
			selectQuery.append("      SELECT A.REQFORMNO, '제출문서' \n");
			selectQuery.append("      FROM REQMST A, (SELECT A.REQFORMNO, A.REQSEQ, A.GUBUN \n");
			selectQuery.append("                      FROM REQSANC A, (SELECT REQFORMNO, REQSEQ, MIN(GUBUN) GUBUN \n");
			selectQuery.append("                                       FROM REQSANC \n");
			selectQuery.append("                                       WHERE SANCYN = '0' \n");
			selectQuery.append("                                       GROUP BY REQFORMNO, REQSEQ) B \n");
			selectQuery.append("                      WHERE A.REQFORMNO= B.REQFORMNO \n");
			selectQuery.append("                        AND A.REQSEQ = B.REQSEQ \n");
			selectQuery.append("                        AND A.GUBUN = B.GUBUN \n");
			selectQuery.append("                        AND A.SANCUSRID = ?) B, REQFORMMST C \n");
			selectQuery.append("      WHERE A.REQFORMNO = B.REQFORMNO \n");
			selectQuery.append("        AND A.REQSEQ = B.REQSEQ \n");
			selectQuery.append("        AND A.REQFORMNO = C.REQFORMNO) \n");
			//신청서 결재 끝
			
			selectQuery.append("WHERE DOCGBN LIKE ? \n");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, search.getUser_id());
			pstmt.setString(2, deptcd);
			pstmt.setString(3, search.getUser_id());
			pstmt.setString(4, deptcd);
			pstmt.setString(5, search.getUser_id());		
			pstmt.setString(6, search.getDocGbn());		
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
	     } finally {
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return totalCount;
	}	
		
	/**
	 * 결재완료 목록
	 * @throws Exception 
	 */
	public List appCompList(SearchBean search, String deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List approvalList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT (CNT-SEQNO+1) BUNHO, FORMSEQ, FORMKIND, SYSDOCNO, REQSEQ, DOCTITLE, DOCGBN, COLDEPTNM, CCDNAME, \n");
			selectQuery.append("        SUBSTR(SANCDT,1,4)||'년'||SUBSTR(SANCDT,6,2)||'월'||SUBSTR(SANCDT,9,2)||'일 '||SUBSTR(SANCDT,12,2)||'시'||SUBSTR(SANCDT,15,2)||'분' SANCDT \n");
			selectQuery.append("FROM (SELECT (MAX(SEQNO)OVER()) CNT, A1.* \n");
			selectQuery.append("      FROM (SELECT ROWNUM SEQNO, A2.* \n");
			selectQuery.append("            FROM (SELECT FORMSEQ, FORMKIND, SYSDOCNO, REQSEQ, DOCTITLE, DOCGBN, COLDEPTNM, CCDNAME, SANCDT \n");
			selectQuery.append("                  FROM (SELECT F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, 0 REQSEQ, A.DOCTITLE, B.DOCGBN, A.COLDEPTNM, B.CCDNAME, B.SANCDT \n");			
			selectQuery.append("                        FROM DOCMST A, (SELECT '배부문서' DOCGBN, B.SYSDOCNO, MAX(C.CCDNAME) CCDNAME, MAX(B.SANCDT) SANCDT \n");
			selectQuery.append("                                        FROM DOCMST A, SANCCOL B, (SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = '003' ) C \n");
			selectQuery.append("                                        WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("                                          AND A.DOCSTATE = C.CCDSUBCD \n");
			selectQuery.append("                                          AND B.SANCUSRID = ? \n");
			selectQuery.append("									      AND A.COLDEPTCD = ? \n");
			selectQuery.append("					                      AND A.DOCSTATE < '06'\n");			
			selectQuery.append("                                          AND B.SANCYN = '1' \n");
			selectQuery.append("                                        GROUP BY B.SYSDOCNO \n");
			selectQuery.append("                                        UNION ALL \n");
			selectQuery.append("                                        SELECT '제출문서' DOCGBN, B.SYSDOCNO, MAX(C.CCDNAME) CCDNAME, MAX(B.SANCDT) SANCDT \n");
			selectQuery.append("                                        FROM TGTDEPT A, SANCTGT B, (SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = '004') C, DOCMST D \n");
			selectQuery.append("                                        WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("                                          AND A.TGTDEPTCD = B.TGTDEPTCD \n");
			selectQuery.append("                                          AND A.SUBMITSTATE = C.CCDSUBCD \n");
			selectQuery.append("                                          AND B.SANCUSRID = ? \n");
			selectQuery.append(" 									      AND B.TGTDEPTCD = ? \n");	
			selectQuery.append("					                      AND A.SUBMITSTATE < '06' \n");
			selectQuery.append("                                          AND A.SYSDOCNO = D.SYSDOCNO \n");
			selectQuery.append("					                      AND D.DOCSTATE < '06'\n");
			selectQuery.append("                                          AND B.SANCYN ='1' \n");
			selectQuery.append("                                        GROUP BY B.SYSDOCNO) B, \n");
			selectQuery.append("                              (SELECT SYSDOCNO, FORMSEQ, FORMKIND \n");
			selectQuery.append("                               FROM FORMMST F \n");
			selectQuery.append("                               WHERE FORMSEQ = (SELECT MIN(FORMSEQ) FROM FORMMST WHERE SYSDOCNO = F.SYSDOCNO and to_number(formkind) <= 3)) F \n");
			selectQuery.append("                        WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("                        AND   A.SYSDOCNO = F.SYSDOCNO \n");
			
			//신청서 결재를 위하여 추가 됨
			selectQuery.append("                        UNION ALL \n");
			selectQuery.append("                        SELECT 0, '0', A.REQFORMNO, A.REQSEQ, C.TITLE, '제출문서', C.COLDEPTNM, D.CCDNAME, B.SANCDT \n");
			selectQuery.append("                        FROM REQMST A, (SELECT REQFORMNO, REQSEQ, SANCDT \n");
			selectQuery.append("                                        FROM REQSANC \n");
			selectQuery.append("                                        WHERE SANCYN = '1' \n");
			selectQuery.append("                                        AND SANCUSRID = ?) B, REQFORMMST C, \n");
			selectQuery.append("                            (SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = '015') D \n");
			selectQuery.append("                        WHERE A.REQFORMNO = B.REQFORMNO \n");
			selectQuery.append("                          AND A.REQSEQ = B.REQSEQ \n");
			selectQuery.append("                          AND A.REQFORMNO = C.REQFORMNO \n");
			selectQuery.append("                          AND A.STATE = D.CCDSUBCD \n");
			selectQuery.append("                          AND A.STATE < '03' \n");
			selectQuery.append("                          AND C.STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') \n");
			selectQuery.append("                          AND (C.ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR C.ENDDT IS NULL) \n");
			selectQuery.append("                        ) \n");
			//신청서 결재 끝			
		
			selectQuery.append("				  WHERE DOCGBN LIKE ? \n");
			selectQuery.append("                  ORDER BY SANCDT DESC) A2) A1) \n");			
			selectQuery.append("WHERE SEQNO BETWEEN ? AND ? \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, search.getUser_id());
			pstmt.setString(2, deptcd);
			pstmt.setString(3, search.getUser_id());
			pstmt.setString(4, deptcd);
			pstmt.setString(5, search.getUser_id());
			pstmt.setString(6, search.getDocGbn());
			pstmt.setInt(7, search.getStartidx());
			pstmt.setInt(8, search.getEndidx());
									
			rs = pstmt.executeQuery();
			
			approvalList = new ArrayList();
			
			while (rs.next()) {
				ApprovalBean bean = new ApprovalBean();
				
				bean.setBunho(rs.getInt("BUNHO"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setFormkind(rs.getString("FORMKIND"));
				bean.setSysdocno(rs.getInt("SYSDOCNO"));
				bean.setReqseq(rs.getInt("REQSEQ"));
				bean.setDoctitle(rs.getString("DOCTITLE"));
				bean.setProcstatus(rs.getString("CCDNAME"));
				
				if("배부문서".equals(rs.getString("DOCGBN"))){
					bean.setSancgbn("1");
				} else {
					bean.setSancgbn("2");
				}				
				
				bean.setSancgbnnm(rs.getString("DOCGBN"));
				bean.setDeptnm(rs.getString("COLDEPTNM"));
				bean.setSancdt(rs.getString("SANCDT"));
				
				approvalList.add(bean);
			}							
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return approvalList;
	}
	
	/** 
	 * 결재완료 목록 갯수 가져오기	
	 * @throws Exception 
	 */
	public int appCompTotCnt(SearchBean search, String deptcd) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(SYSDOCNO) \n");
			selectQuery.append("FROM (SELECT A.SYSDOCNO, B.DOCGBN \n");			
			selectQuery.append("      FROM DOCMST A, (SELECT '배부문서' DOCGBN, B.SYSDOCNO, MAX(C.CCDNAME) CCDNAME, MAX(B.SANCDT) SANCDT \n");
			selectQuery.append("                      FROM DOCMST A, SANCCOL B, (SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = '003' ) C \n");
			selectQuery.append("                      WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("                        AND A.DOCSTATE = C.CCDSUBCD \n");
			selectQuery.append("                        AND B.SANCUSRID = ? \n");
			selectQuery.append("				        AND A.COLDEPTCD = ? \n");
			selectQuery.append("  				        AND A.DOCSTATE < '06'\n");				
			selectQuery.append("                        AND B.SANCYN = '1' \n");
			selectQuery.append("                      GROUP BY B.SYSDOCNO \n");
			selectQuery.append("                      UNION ALL \n");
			selectQuery.append("                      SELECT '제출문서' DOCGBN, B.SYSDOCNO, MAX(C.CCDNAME) CCDNAME, MAX(B.SANCDT) SANCDT \n");
			selectQuery.append("                      FROM TGTDEPT A, SANCTGT B, (SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = '004') C \n");
			selectQuery.append("                      WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("                        AND A.TGTDEPTCD = B.TGTDEPTCD \n");
			selectQuery.append("                        AND A.SUBMITSTATE = C.CCDSUBCD \n");
			selectQuery.append("                        AND B.SANCUSRID = ? \n");
			selectQuery.append("				        AND B.TGTDEPTCD = ? \n");
			selectQuery.append("  				        AND A.SUBMITSTATE < '05' \n");			
			selectQuery.append("                        AND B.SANCYN ='1' \n");
			selectQuery.append("                      GROUP BY B.SYSDOCNO) B \n");
			selectQuery.append("      WHERE A.SYSDOCNO = B.SYSDOCNO \n");	
			
			//신청서 결재를 위하여 추가 됨
			selectQuery.append("      UNION ALL \n");
			selectQuery.append("      SELECT A.REQFORMNO, '제출문서' \n");
			selectQuery.append("      FROM REQMST A, (SELECT REQFORMNO, REQSEQ, SANCDT \n");
			selectQuery.append("                      FROM REQSANC \n");
			selectQuery.append("                      WHERE SANCYN = '1' \n");
			selectQuery.append("                        AND SANCUSRID = ?) B, REQFORMMST C, \n");
			selectQuery.append("          (SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = '015') D \n");
			selectQuery.append("      WHERE A.REQFORMNO = B.REQFORMNO \n");
			selectQuery.append("        AND A.REQSEQ = B.REQSEQ \n");
			selectQuery.append("        AND A.REQFORMNO = C.REQFORMNO \n");
			selectQuery.append("        AND A.STATE = D.CCDSUBCD \n");
			selectQuery.append("        AND A.STATE < '03' \n");
			selectQuery.append("        AND C.STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') \n");
			selectQuery.append("        AND (C.ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR C.ENDDT IS NULL) \n");
			selectQuery.append("     ) \n");
			//신청서 결재 끝		
			
			selectQuery.append("WHERE DOCGBN LIKE ? \n");
		
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, search.getUser_id());
			pstmt.setString(2, deptcd);
			pstmt.setString(3, search.getUser_id());
			pstmt.setString(4, deptcd);
			pstmt.setString(5, search.getUser_id());
			pstmt.setString(6, search.getDocGbn());
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
	     } finally {
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return totalCount;
	}
			
	/**
	 * [취합부서 용] 해당스텝(검토/승인)의 마지막 결제자인지 알아오기
	 * gubun : 검토(1), 승인(2)
	 * @throws Exception 
	 */
	public boolean lastColSancCheck(int sysdocno, String gubun, String sancusrid) throws Exception{
		Connection conn = null;  
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean bool = false;
		
		try{
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) ");
			selectQuery.append("FROM SANCCOL ");
			selectQuery.append("WHERE SYSDOCNO = ? ");
			selectQuery.append("  AND GUBUN = ? ");			
			selectQuery.append("  AND SANCYN = '0' ");
			selectQuery.append("  AND SANCUSRID <> ? ");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);		
			pstmt.setString(2, gubun);
			pstmt.setString(3, sancusrid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				if(rs.getInt(1) == 0){
					bool = true;
				} else {
					bool = false;
				}
			}
			
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn,pstmt,rs);
		}
		return bool;
	}
	
	/**
	 * [제출부서 용] 해당스텝(검토/승인)의 마지막 결제자인지 알아오기
	 * gubun : 검토(1), 승인(2)
	 * @throws Exception 
	 */
	public boolean lastTgtSancCheck(int sysdocno, String gubun, String deptcd, String sancusrid) throws Exception{
		Connection conn = null;  
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean bool = false;
		
		try{
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) ");
			selectQuery.append("FROM SANCTGT ");
			selectQuery.append("WHERE SYSDOCNO = ? ");
			selectQuery.append("  AND TGTDEPTCD = ? ");
			selectQuery.append("  AND GUBUN = ? ");			
			selectQuery.append("  AND SANCYN = '0' ");
			selectQuery.append("  AND SANCUSRID <> ? ");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);	
			pstmt.setString(2, deptcd);
			pstmt.setString(3, gubun);			
			pstmt.setString(4, sancusrid);		
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				if(rs.getInt(1) == 0){
					bool = true;
				} else {
					bool = false;
				}
			}
			
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn,pstmt,rs);
		}
		return bool;
	}
	
	/** 
	 * 취합부서 결재상태 체크(마지막 결제자 인지 체크하여 처리)
	 * gubun : 검토(1), 승인(2)	
	 * @throws Exception 
	 */
	public int doLastColSancCheck(Connection conn, int sysdocno, String gubun, String deptcd, String sancusrid) throws Exception  {
		
		PreparedStatement pstmt = null;
		int result =0;
				
		try {
			//마지막 결제자 일때 처리
			if(lastColSancCheck(sysdocno, gubun, sancusrid)){
				StringBuffer updateQuery2 = new StringBuffer();
				
				//문서상태를 하나 증가 시킨다.
				updateQuery2.append("UPDATE DOCMST SET DOCSTATE = LPAD(NVL(DOCSTATE,0)+1,2,'0') ");				
				updateQuery2.append("WHERE SYSDOCNO = ? ");
				
				pstmt = conn.prepareStatement(updateQuery2.toString());	
				pstmt.setInt(1, sysdocno);
				
				result += pstmt.executeUpdate();				
				
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(Exception ignored) {
						throw ignored;
					}
				}
				
				if("1".equals(gubun)){
					//검토에서 승인으로 올라갈때 처리
					StringBuffer updateQuery3 = new StringBuffer();
					
					//승인의 기안일시를 설정한다.
					updateQuery3.append("UPDATE SANCCOL SET SUBMITDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ");
					updateQuery3.append("      			    UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? ");
					updateQuery3.append("WHERE SYSDOCNO = ? ");
					updateQuery3.append("  AND GUBUN = '2' ");
					
					pstmt = conn.prepareStatement(updateQuery3.toString());	
					
					pstmt.setString(1, sancusrid);
					pstmt.setInt(2, sysdocno);
					
					result += pstmt.executeUpdate();
					
				} else if("2".equals(gubun)){
					//승인에서 배부하기로 올라갈때 처리
					StringBuffer updateQuery3 = new StringBuffer();
					
					//문서마스터에 배부일시,문서번호를 설정한다.
					updateQuery3.append("UPDATE DOCMST SET DELIVERYDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ");		
					updateQuery3.append("                  DOCNO = ? ");
					updateQuery3.append("WHERE SYSDOCNO = ? ");					
					
					pstmt = conn.prepareStatement(updateQuery3.toString());	
					pstmt.setString(1, getDocno(deptcd));
					pstmt.setInt(2, sysdocno);
					
					result += pstmt.executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.error("ERROR", e);
			try {pstmt.close();} catch(Exception e2) {};
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {};
		}
			
		return result;
	}
	
	/** 
	 * 제출부서 결재상태 체크(마지막 결제자 인지 체크하여 처리)
	 * gubun : 검토(1), 승인(2)	
	 * @throws Exception 
	 */
	public int doLastTgtSancCheck(Connection conn, int sysdocno, String gubun, String deptcd, String sancusrid ) throws Exception  {
   
		PreparedStatement pstmt = null;
		int result =0;
				
		try {
			//마지막 결제자 일때 처리
			if(lastTgtSancCheck(sysdocno, gubun, deptcd, sancusrid)){
				StringBuffer updateQuery2 = new StringBuffer();
				
				//제출부서상태를 하나 증가 시킨다.
				updateQuery2.append("UPDATE TGTDEPT SET SUBMITSTATE = LPAD(NVL(SUBMITSTATE,0)+1,2,'0'), ");
				updateQuery2.append("      			    UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? ");
				updateQuery2.append("WHERE SYSDOCNO = ? ");
				updateQuery2.append("  AND TGTDEPTCD = ? ");
				
				pstmt = conn.prepareStatement(updateQuery2.toString());	
				
				pstmt.setString(1, sancusrid);
				pstmt.setInt(2, sysdocno);
				pstmt.setString(3, deptcd);
				
				result += pstmt.executeUpdate();
				
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(Exception ignored) {
						throw ignored;
					}
				}
				
				if("1".equals(gubun)){
					//검토에서 승인으로 올라갈때 처리
					StringBuffer updateQuery3 = new StringBuffer();
					
					//승인의 기안일시를 설정한다.
					updateQuery3.append("UPDATE SANCTGT SET SUBMITDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ");
					updateQuery3.append("      			    UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? ");
					updateQuery3.append("WHERE SYSDOCNO = ? ");
					updateQuery3.append("  AND TGTDEPTCD = ? ");
					updateQuery3.append("  AND GUBUN = '2' ");
					
					pstmt = conn.prepareStatement(updateQuery3.toString());	
					
					pstmt.setString(1, sancusrid);
					pstmt.setInt(2, sysdocno);
					pstmt.setString(3, deptcd);
					
					result += pstmt.executeUpdate();
					
				} else if("2".equals(gubun)){
					//승인에서 제출완료 상태가 될때
					StringBuffer updateQuery3 = new StringBuffer();
					
					//제출일시 값을 셋팅한다.
					updateQuery3.append("UPDATE TGTDEPT SET SUBMITDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ");
					updateQuery3.append("      			    UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? ");
					updateQuery3.append("WHERE SYSDOCNO = ? ");
					updateQuery3.append("  AND TGTDEPTCD = ? ");				
					
					pstmt = conn.prepareStatement(updateQuery3.toString());	
					
					pstmt.setString(1, sancusrid);
					pstmt.setInt(2, sysdocno);
					pstmt.setString(3, deptcd);
					
					result += pstmt.executeUpdate();
					
					if (pstmt != null){
						try {
							pstmt.close();
							pstmt = null;
						} catch(Exception ignored) {
							throw ignored;
						}
					}
					
					//마지막 제출부서일 경우 문서 마스터를 마감대기로 셋팅한다.
					if(submitCount(sysdocno)==1){
						StringBuffer updateQuery4 = new StringBuffer();
						
						//문서마스터를 마감대기로 셋팅한다.
						updateQuery4.append("UPDATE DOCMST SET DOCSTATE = '05' ");						
						updateQuery4.append("WHERE SYSDOCNO = ? ");										
						
						pstmt = conn.prepareStatement(updateQuery4.toString());						
						pstmt.setInt(1, sysdocno);						
						
						result += pstmt.executeUpdate();
					}
				}
			}
		} catch (Exception e) {
			logger.error("ERROR", e);
			try {pstmt.close();} catch(Exception e2) {};
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {};
		}
			
		return result;
	}
	
	/** 
	 * 취합부서 결재하기
	 * gubun : 검토(1), 승인(2)	
	 * @throws Exception 
	 */
	public int doColSanc (int sysdocno, String gubun, String deptcd, String sancusrid ) throws Exception  {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result =0;
				
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			//결제처리
		 	StringBuffer updateQuery1 = new StringBuffer();
	       	
		 	updateQuery1.append("UPDATE SANCCOL SET SANCYN = '1', SANCDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ");
		 	updateQuery1.append("      				UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? ");
		 	updateQuery1.append("WHERE SYSDOCNO = ? ");
		 	updateQuery1.append("  AND GUBUN = ? ");
		 	updateQuery1.append("  AND SANCUSRID = ? ");
	       			    
		    pstmt = conn.prepareStatement(updateQuery1.toString());	
			
		   	pstmt.setString(1, sancusrid);
			pstmt.setInt(2, sysdocno);
			pstmt.setString(3, gubun);
			pstmt.setString(4, sancusrid);
					
			result = pstmt.executeUpdate();			
			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(Exception ignored) {
					throw ignored;
				}
			}
			
			//마지막 결제자 일때 처리
			result += doLastColSancCheck(conn, sysdocno, gubun, deptcd, sancusrid);
			
		    conn.commit();
		 } catch (Exception e) {
			 try{
		 		conn.rollback();
			 } catch(Exception ex){
				logger.error("ERROR",ex);
				ConnectionManager.close(conn,pstmt);	
				throw ex;
			 }
			 logger.error("ERROR", e);
			 try{ 
	    	 	conn.setAutoCommit(true);
 			} catch (Exception e2){
 				logger.error("ERROR",e);
 				ConnectionManager.close(conn,pstmt);	
 				throw e;
 			}
 			ConnectionManager.close(conn,pstmt);	
			 throw e;
	     } finally {	
	    	 try{ 
    	 		conn.setAutoCommit(true);
 			} catch (Exception e){
 				logger.error("ERROR",e);
 				ConnectionManager.close(conn,pstmt);	
 				throw e;
 			}
 			ConnectionManager.close(conn,pstmt);	   
	     }	     
	     return result;
	}
	
	
	/** 
	 * 제출부서 결재하기
	 * gubun : 검토(1), 승인(2)	
	 * @throws Exception 
	 */
	public int doTgtSanc (int sysdocno, String gubun, String deptcd, String sancusrid ) throws Exception  {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result =0;
				
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			//결제처리
		 	StringBuffer updateQuery1 = new StringBuffer();
	       	
		 	updateQuery1.append("UPDATE SANCTGT SET SANCYN = '1', SANCDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ");
		 	updateQuery1.append("      				UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? ");
		 	updateQuery1.append("WHERE SYSDOCNO = ? ");
		 	updateQuery1.append("  AND TGTDEPTCD = ? ");
		 	updateQuery1.append("  AND GUBUN = ? ");
		 	updateQuery1.append("  AND SANCUSRID = ? ");
	       			    
		    pstmt = conn.prepareStatement(updateQuery1.toString());	
			
		   	pstmt.setString(1, sancusrid);
			pstmt.setInt(2, sysdocno);
			pstmt.setString(3, deptcd);
			pstmt.setString(4, gubun);
			pstmt.setString(5, sancusrid);
					
			result = pstmt.executeUpdate();			
			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(Exception ignored) {
					throw ignored;
				}
			}
			
			//마지막 결제자 일때 처리
			result += doLastTgtSancCheck(conn, sysdocno, gubun, deptcd, sancusrid);
			
		    conn.commit();		
		 } catch (Exception e) {
			 try{
		 		conn.rollback();
			 } catch(Exception ex){
				logger.error("ERROR",ex);
				ConnectionManager.close(conn,pstmt);	
				throw ex;
			 }
			 logger.error("ERROR", e);
			 try{ 
	    	 		conn.setAutoCommit(true);
	 			} catch (Exception e2){
	 				logger.error("ERROR",e2);
	 				ConnectionManager.close(conn,pstmt);	 
	 				throw e;
	 			}
	 			ConnectionManager.close(conn,pstmt);	  
			 throw e;
	     } finally {	
	    	 try{ 
    	 		conn.setAutoCommit(true);
 			} catch (Exception e){
 				logger.error("ERROR",e);
 				ConnectionManager.close(conn,pstmt);	 
 				throw e;
 			}
 			ConnectionManager.close(conn,pstmt);	   
	     }	     
	     return result;
	}	
	
	/**
	 * 미제출 부서의 개수 - 조회 부서 제외
	 * @throws Exception 
	 */
	public int submitCount(int sysdocno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) ");
			selectQuery.append("  FROM TGTDEPT ");
			selectQuery.append("WHERE SYSDOCNO = ? ");	
			selectQuery.append("  AND SUBMITSTATE < '05' ");			
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
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
	 * 문서번호 생성하기
	 * @throws Exception 
	 */
	public String getDocno(String deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		int temp = 0;
		String year = String.valueOf(DateTime.getYear());
				
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT YEAR, DEPTNM, SEQ ");
			selectQuery.append("FROM GETDOCNO ");
			selectQuery.append("WHERE YEAR = ? ");	
			selectQuery.append("  AND DEPTCD = ? ");			
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, year);
			pstmt.setString(2, deptcd);
			
			rs = pstmt.executeQuery();
			
			String deptnm = "";  int seq = 0;
			if(rs.next()){
				deptnm = rs.getString("DEPTNM");
				seq = rs.getInt("SEQ") + 1;
			}
			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(Exception ignored) {
					throw ignored;
				}
			}
			
			if(seq > 0) {				
				StringBuffer updateQuery = new StringBuffer();
				
				updateQuery.append("UPDATE GETDOCNO SET SEQ = SEQ+1 ");
				updateQuery.append("WHERE YEAR = ? ");
				updateQuery.append("  AND DEPTCD = ? ");
				
				pstmt = con.prepareStatement(updateQuery.toString());
				pstmt.setString(1, year);
				pstmt.setString(2, deptcd);
				
				
				temp = pstmt.executeUpdate();
				
				if(temp>0){
					result = "[취합]"+deptnm+"-"+String.valueOf(seq);
				}
			} else {
				StringBuffer insertQuery = new StringBuffer();
				
				insertQuery.append("INSERT INTO GETDOCNO(YEAR, DEPTCD, SEQ, DEPTNM) ");
				insertQuery.append("VALUES(?, ?, ?, ?) ");
								
				pstmt = con.prepareStatement(insertQuery.toString());
				pstmt.setString(1, year);
				pstmt.setString(2, deptcd);
				pstmt.setInt(3, 1);
				pstmt.setString(4, deptNm(deptcd));
				
				temp = pstmt.executeUpdate();
				
				if(temp>0){
					result = "[취합]"+deptNm(deptcd)+"-1";
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
	 * 부서명칭 가져오기
	 * @throws Exception 
	 */
	private String deptNm(String deptid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT DEPT_NAME ");
			selectQuery.append("FROM DEPT ");
			selectQuery.append("WHERE DEPT_ID = ? ");					
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, deptid);			
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString("DEPT_NAME");
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
}
