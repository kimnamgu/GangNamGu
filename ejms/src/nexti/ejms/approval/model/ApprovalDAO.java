/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ϱ� dao
 * ����:
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
	 * ���պμ� ����Ϸ� ���� üũ
	 * gubun(1) : ����, gubun(2) : ����
	 * @param sysdocno �ý��۹�����ȣ
	 * @param gubun ����
	 * @return ����Ϸ�sysdocno,tgtdeptcd,sancusrid(1��)
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
	 * ����μ� ����Ϸ� ���� üũ
	 * gubun(1) : ����, gubun(2) : ����
	 * @param sysdocno �ý��۹�����ȣ
	 * @param tgtdeptcd ����μ��ڵ�
	 * @param gubun ����
	 * @return ����Ϸ�sysdocno,tgtdeptcd,sancusrid(1��)
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
	 * �����ϱ� ���
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
			selectQuery.append("        SUBSTR(SDT,1,4)||'��'||SUBSTR(SDT,6,2)||'��'||SUBSTR(SDT,9,2)||'�� '||SUBSTR(SDT,12,2)||'��'||SUBSTR(SDT,15,2)||'��' SDT \n");
			selectQuery.append("FROM (SELECT (MAX(SEQNO)OVER()) CNT, SEQNO, FORMSEQ, FORMKIND, SYSDOCNO, REQSEQ, DOCTITLE, DOCGBN, COLDEPTNM, GUBUN, SDT \n");
			selectQuery.append("      FROM (SELECT ROWNUM SEQNO, FORMSEQ, FORMKIND, SYSDOCNO, REQSEQ, DOCTITLE, DOCGBN, COLDEPTNM, GUBUN, SDT \n");
			selectQuery.append("            FROM (SELECT FORMSEQ, FORMKIND, SYSDOCNO, REQSEQ, DOCTITLE, DOCGBN, COLDEPTNM, GUBUN, SDT \n");
			selectQuery.append("                  FROM (SELECT F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, 0 REQSEQ, A.DOCTITLE, B.DOCGBN, A.COLDEPTNM, B.GUBUN, A.SUBMITDATE SDT \n");		
			selectQuery.append("                        FROM DOCMST A, (SELECT '��ι���' DOCGBN, A.SYSDOCNO, DECODE(B.GUBUN,'02','������','���δ��') GUBUN, '' SUBMITDT \n");
			selectQuery.append("                                        FROM DOCMST A, (SELECT SYSDOCNO, MIN(DECODE(GUBUN,'1','02','03')) GUBUN \n");
			selectQuery.append("                                                        FROM SANCCOL \n");
			selectQuery.append("                                                        WHERE SANCUSRID = '"+search.getUser_id()+"' \n");
			selectQuery.append("                                                          AND SANCYN = '0' \n");
			selectQuery.append("                                                        GROUP BY SYSDOCNO) B \n");
			selectQuery.append("                                        WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("                                          AND A.DOCSTATE = B.GUBUN \n");	
			selectQuery.append("                                          AND A.COLDEPTCD = '"+deptcd+"' \n");
			selectQuery.append("                                        UNION ALL \n");
			selectQuery.append("                                        SELECT '���⹮��' ,A.SYSDOCNO, DECODE(B.GUBUN,'03','������','���δ��') GUBUN, A.SUBMITDT \n");
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
			
			//��û�� ���縦 ���Ͽ� �߰� ��
			selectQuery.append("                        UNION ALL \n");
			selectQuery.append("                        SELECT 0, '0', A.REQFORMNO, A.REQSEQ, C.TITLE, '���⹮��', C.COLDEPTNM, DECODE(B.GUBUN,'1','������','���δ��') GUBUN, A.CRTDT \n");
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
			//��û�� ���� ��
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
				
				if("��ι���".equals(rs.getString("DOCGBN"))){
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
	 * �����ϱ� ��� ���� ��������	
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
			selectQuery.append("      FROM DOCMST A, (SELECT '��ι���' DOCGBN, A.SYSDOCNO \n");
			selectQuery.append("				      FROM DOCMST A, (SELECT SYSDOCNO, MIN(DECODE(GUBUN,'1','02','03')) GUBUN \n");
			selectQuery.append("                                      FROM SANCCOL \n");
			selectQuery.append("                                      WHERE SANCUSRID = ? \n");
			selectQuery.append("                                        AND SANCYN = '0' \n");
			selectQuery.append("                                      GROUP BY SYSDOCNO) B \n");
			selectQuery.append("                      WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("                        AND A.DOCSTATE = B.GUBUN \n");	
			selectQuery.append("				        AND A.COLDEPTCD = ? \n");
			selectQuery.append("                      UNION ALL \n");
			selectQuery.append("                      SELECT '���⹮��' ,A.SYSDOCNO \n");
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
			
			//��û�� ���縦 ���Ͽ� �߰� ��
			selectQuery.append("      UNION ALL \n");
			selectQuery.append("      SELECT A.REQFORMNO, '���⹮��' \n");
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
			//��û�� ���� ��
			
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
	 * ����Ϸ� ���
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
			selectQuery.append("        SUBSTR(SANCDT,1,4)||'��'||SUBSTR(SANCDT,6,2)||'��'||SUBSTR(SANCDT,9,2)||'�� '||SUBSTR(SANCDT,12,2)||'��'||SUBSTR(SANCDT,15,2)||'��' SANCDT \n");
			selectQuery.append("FROM (SELECT (MAX(SEQNO)OVER()) CNT, A1.* \n");
			selectQuery.append("      FROM (SELECT ROWNUM SEQNO, A2.* \n");
			selectQuery.append("            FROM (SELECT FORMSEQ, FORMKIND, SYSDOCNO, REQSEQ, DOCTITLE, DOCGBN, COLDEPTNM, CCDNAME, SANCDT \n");
			selectQuery.append("                  FROM (SELECT F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, 0 REQSEQ, A.DOCTITLE, B.DOCGBN, A.COLDEPTNM, B.CCDNAME, B.SANCDT \n");			
			selectQuery.append("                        FROM DOCMST A, (SELECT '��ι���' DOCGBN, B.SYSDOCNO, MAX(C.CCDNAME) CCDNAME, MAX(B.SANCDT) SANCDT \n");
			selectQuery.append("                                        FROM DOCMST A, SANCCOL B, (SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = '003' ) C \n");
			selectQuery.append("                                        WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("                                          AND A.DOCSTATE = C.CCDSUBCD \n");
			selectQuery.append("                                          AND B.SANCUSRID = ? \n");
			selectQuery.append("									      AND A.COLDEPTCD = ? \n");
			selectQuery.append("					                      AND A.DOCSTATE < '06'\n");			
			selectQuery.append("                                          AND B.SANCYN = '1' \n");
			selectQuery.append("                                        GROUP BY B.SYSDOCNO \n");
			selectQuery.append("                                        UNION ALL \n");
			selectQuery.append("                                        SELECT '���⹮��' DOCGBN, B.SYSDOCNO, MAX(C.CCDNAME) CCDNAME, MAX(B.SANCDT) SANCDT \n");
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
			
			//��û�� ���縦 ���Ͽ� �߰� ��
			selectQuery.append("                        UNION ALL \n");
			selectQuery.append("                        SELECT 0, '0', A.REQFORMNO, A.REQSEQ, C.TITLE, '���⹮��', C.COLDEPTNM, D.CCDNAME, B.SANCDT \n");
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
			//��û�� ���� ��			
		
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
				
				if("��ι���".equals(rs.getString("DOCGBN"))){
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
	 * ����Ϸ� ��� ���� ��������	
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
			selectQuery.append("      FROM DOCMST A, (SELECT '��ι���' DOCGBN, B.SYSDOCNO, MAX(C.CCDNAME) CCDNAME, MAX(B.SANCDT) SANCDT \n");
			selectQuery.append("                      FROM DOCMST A, SANCCOL B, (SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = '003' ) C \n");
			selectQuery.append("                      WHERE A.SYSDOCNO = B.SYSDOCNO \n");
			selectQuery.append("                        AND A.DOCSTATE = C.CCDSUBCD \n");
			selectQuery.append("                        AND B.SANCUSRID = ? \n");
			selectQuery.append("				        AND A.COLDEPTCD = ? \n");
			selectQuery.append("  				        AND A.DOCSTATE < '06'\n");				
			selectQuery.append("                        AND B.SANCYN = '1' \n");
			selectQuery.append("                      GROUP BY B.SYSDOCNO \n");
			selectQuery.append("                      UNION ALL \n");
			selectQuery.append("                      SELECT '���⹮��' DOCGBN, B.SYSDOCNO, MAX(C.CCDNAME) CCDNAME, MAX(B.SANCDT) SANCDT \n");
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
			
			//��û�� ���縦 ���Ͽ� �߰� ��
			selectQuery.append("      UNION ALL \n");
			selectQuery.append("      SELECT A.REQFORMNO, '���⹮��' \n");
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
			//��û�� ���� ��		
			
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
	 * [���պμ� ��] �ش罺��(����/����)�� ������ ���������� �˾ƿ���
	 * gubun : ����(1), ����(2)
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
	 * [����μ� ��] �ش罺��(����/����)�� ������ ���������� �˾ƿ���
	 * gubun : ����(1), ����(2)
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
	 * ���պμ� ������� üũ(������ ������ ���� üũ�Ͽ� ó��)
	 * gubun : ����(1), ����(2)	
	 * @throws Exception 
	 */
	public int doLastColSancCheck(Connection conn, int sysdocno, String gubun, String deptcd, String sancusrid) throws Exception  {
		
		PreparedStatement pstmt = null;
		int result =0;
				
		try {
			//������ ������ �϶� ó��
			if(lastColSancCheck(sysdocno, gubun, sancusrid)){
				StringBuffer updateQuery2 = new StringBuffer();
				
				//�������¸� �ϳ� ���� ��Ų��.
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
					//���信�� �������� �ö󰥶� ó��
					StringBuffer updateQuery3 = new StringBuffer();
					
					//������ ����Ͻø� �����Ѵ�.
					updateQuery3.append("UPDATE SANCCOL SET SUBMITDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ");
					updateQuery3.append("      			    UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? ");
					updateQuery3.append("WHERE SYSDOCNO = ? ");
					updateQuery3.append("  AND GUBUN = '2' ");
					
					pstmt = conn.prepareStatement(updateQuery3.toString());	
					
					pstmt.setString(1, sancusrid);
					pstmt.setInt(2, sysdocno);
					
					result += pstmt.executeUpdate();
					
				} else if("2".equals(gubun)){
					//���ο��� ����ϱ�� �ö󰥶� ó��
					StringBuffer updateQuery3 = new StringBuffer();
					
					//���������Ϳ� ����Ͻ�,������ȣ�� �����Ѵ�.
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
	 * ����μ� ������� üũ(������ ������ ���� üũ�Ͽ� ó��)
	 * gubun : ����(1), ����(2)	
	 * @throws Exception 
	 */
	public int doLastTgtSancCheck(Connection conn, int sysdocno, String gubun, String deptcd, String sancusrid ) throws Exception  {
   
		PreparedStatement pstmt = null;
		int result =0;
				
		try {
			//������ ������ �϶� ó��
			if(lastTgtSancCheck(sysdocno, gubun, deptcd, sancusrid)){
				StringBuffer updateQuery2 = new StringBuffer();
				
				//����μ����¸� �ϳ� ���� ��Ų��.
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
					//���信�� �������� �ö󰥶� ó��
					StringBuffer updateQuery3 = new StringBuffer();
					
					//������ ����Ͻø� �����Ѵ�.
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
					//���ο��� ����Ϸ� ���°� �ɶ�
					StringBuffer updateQuery3 = new StringBuffer();
					
					//�����Ͻ� ���� �����Ѵ�.
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
					
					//������ ����μ��� ��� ���� �����͸� �������� �����Ѵ�.
					if(submitCount(sysdocno)==1){
						StringBuffer updateQuery4 = new StringBuffer();
						
						//���������͸� �������� �����Ѵ�.
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
	 * ���պμ� �����ϱ�
	 * gubun : ����(1), ����(2)	
	 * @throws Exception 
	 */
	public int doColSanc (int sysdocno, String gubun, String deptcd, String sancusrid ) throws Exception  {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result =0;
				
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			//����ó��
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
			
			//������ ������ �϶� ó��
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
	 * ����μ� �����ϱ�
	 * gubun : ����(1), ����(2)	
	 * @throws Exception 
	 */
	public int doTgtSanc (int sysdocno, String gubun, String deptcd, String sancusrid ) throws Exception  {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result =0;
				
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			//����ó��
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
			
			//������ ������ �϶� ó��
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
	 * ������ �μ��� ���� - ��ȸ �μ� ����
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
	 * ������ȣ �����ϱ�
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
					result = "[����]"+deptnm+"-"+String.valueOf(seq);
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
					result = "[����]"+deptNm(deptcd)+"-1";
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
	 * �μ���Ī ��������
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
