/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ϱ� dao
 * ����:
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
	 * ����ϱ� ���
	 * 
	 * @param deptcd : �μ��ڵ�
	 * @param start : ��� ���� �ε���
	 * @param end : ��� ������ �ε���
	 * 
	 * @return List : ��� ��� ����Ʈ
	 * @throws Exception 
	 */
	public List deliveryList(String deptcd, String isSysMgr, String sch_deptcd, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List deliList = null;
		int bindPos = 0;
		
		String sysdate = "SYSDATE";
		if ( "�λ�6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			sysdate = "(SYSDATE-30/(24*60))";
		}
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT (CNT-SEQ+1) BUNHO, SEQ, FORMSEQ, FORMKIND, SYSDOCNO, DOCTITLE, DELIVERYDT, COLDEPTNM, CHRGUSRNM, ENDDT, RDAY, RTIME, USER_ID                                          \n");
			sql.append("  FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.*                                                                                                                                    \n");
			sql.append("          FROM (SELECT ROWNUM SEQ, A2.*                                                                                                                                      \n");
			sql.append("                  FROM (SELECT * FROM (SELECT DISTINCT F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, A.DOCTITLE, A.COLDEPTNM, A.CHRGUSRNM, C.USER_ID,                                   \n");
			sql.append("                               DECODE(A.DELIVERYDT, '', '', SUBSTR(A.DELIVERYDT,1,4)||'�� '||SUBSTR(A.DELIVERYDT,6,2)||'�� '||SUBSTR(A.DELIVERYDT,9,2)||'��') DELIVERYDT,      \n");
			sql.append("                               SUBSTR(A.ENDDT,1,4)||'��'||SUBSTR(A.ENDDT,6,2)||'��'||SUBSTR(A.ENDDT,9,2)||'�� '||SUBSTR(A.ENDDT,12,2)||'��'||SUBSTR(A.ENDDT,15,2)||'��' ENDDT, \n");
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
					deli.setRemaintime("�������� �ʰ�");
				} else if(rs.getInt("RDAY") == 0){
					if(rs.getInt("RTIME")<=0){
						deli.setRemaintime("�������� �ʰ�");
					}else if(rs.getInt("RTIME")==1){
						deli.setRemaintime(rs.getString("RTIME")+"�ð��̸�");
					}else{
						deli.setRemaintime(rs.getString("RTIME")+"�ð�");
					}
				} else{
					deli.setRemaintime(rs.getString("RDAY")+"�� "+rs.getString("RTIME")+"�ð�");
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
	 * ����ϱ� ��� �Ѱ��� ��������
	 * 
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return int : ����ϱ� ��� �Ѱ���
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
	 * ����ϱ� ��� �Ѱ��� ��������
	 * 
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return int : ����ϱ� ��� �Ѱ���
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
	 * ����ϱ� �� - �ݼ� �˾�â ������ ����
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * 
	 * @return DeliveryRetDocViewBean : �ݼ� �˾�â �����͸� ��� �ִ� Bean
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
	 * ���ó�� - �ݼ�
	 * 
	 * @param conn : Connection ��ü
	 * @param RetDocBean : �ݼ� ����/���� ���¸� ��� �ִ� Bean
	 * @param userid : �����ID
	 * 
	 * @return int : ó����� 
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
	 * ���ó���� �������� ����
	 * 
	 * @param conn : Connection ��ü
	 * @param sysdocno : �ý��� ������ȣ
	 * @param docstate : ������ ��������
	 * @param usrid : �����ID
	 * 
	 * @return int : ó����� 
	 * @throws Exception : SQL ���� ����
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
	 * �����Լ� - ������ ����μ����� �ƴ��� ���� üũ
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return true : ������ �μ� O, false : ������ �μ� X	
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
	 * ����ϱ� �� - ���
	 * 
	 * @param submitstate : ������ ����μ� ����
	 * @param appusrnm : �Է´�������� ����
	 * @param usrid : �����ID
	 * @param sysdocno : �ý��� ������ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return int : ó�����
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
	 * ��οϷ� ���
	 * 
	 * @param deptcd : �μ��ڵ�
	 * @param start : ��� ���� �ε���
	 * @param end : ��� ������ �ε���
	 * 
	 * @return List : ��οϷ� ��� ����Ʈ
	 * @throws Exception 
	 */
	public List deliveryCompList(String deptcd, String isSysMgr, String sch_deptcd, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List deliCompList = null;
		
		String sysdate = "SYSDATE";
		if ( "�λ�6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			sysdate = "(SYSDATE-30/(24*60))";
		}
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT (CNT-SEQ+1) BUNHO, SEQ, FORMSEQ, FORMKIND, SYSDOCNO, DOCTITLE, SUBMITSTATE, DELIVERYDT,                                                                                 \n");
			sql.append("       COLDEPTNM, CHRGUSRNM, ENDDT, RDAY, RTIME, INPUTCNT, DELICNT, USER_ID                                                                                                    \n");
			sql.append("  FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.*                                                                                                                                      \n");
			sql.append("          FROM (SELECT ROWNUM SEQ, A2.*                                                                                                                                        \n");
			sql.append("                  FROM (SELECT F.FORMSEQ, F.FORMKIND, A.SYSDOCNO, A.DOCTITLE, B.SUBMITSTATE, A.COLDEPTNM, A.CHRGUSRNM,                                                         \n");
			sql.append("                               DECODE(A.DELIVERYDT, '', '', SUBSTR(A.DELIVERYDT,1,4)||'�� '||SUBSTR(A.DELIVERYDT,6,2)||'�� '||SUBSTR(A.DELIVERYDT,9,2)||'��') DELIVERYDT,        \n");
			sql.append("                               SUBSTR(A.ENDDT,1,4)||'��'||SUBSTR(A.ENDDT,6,2)||'��'||SUBSTR(A.ENDDT,9,2)||'�� '||SUBSTR(A.ENDDT,12,2)||'��'||SUBSTR(A.ENDDT,15,2)||'��' ENDDT,   \n");
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
//�����μ� ��ι������� ���� ����			
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
//�����μ� ��ι������� ���� ��
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
					deliComp.setSubmitstate("�Է�����");
				} else if(rs.getString("SUBMITSTATE").equals("03")) {
					deliComp.setSubmitstate("������");
				} else if(rs.getString("SUBMITSTATE").equals("04")) {
					deliComp.setSubmitstate("���δ��");
				}
				
				deliComp.setDeliverydt(rs.getString("DELIVERYDT"));
				deliComp.setColdeptnm(rs.getString("COLDEPTNM"));
				deliComp.setChrgusrnm(rs.getString("CHRGUSRNM"));
				deliComp.setEnddt(rs.getString("ENDDT"));
				
				if(rs.getInt("RDAY")< 0){
					deliComp.setRemaintime("�������� �ʰ�");
				} else if(rs.getInt("RDAY") == 0){
					if(rs.getInt("RTIME")<=0){
						deliComp.setRemaintime("�������� �ʰ�");
					}else if(rs.getInt("RTIME")== 1){
						deliComp.setRemaintime(rs.getString("RTIME")+"�ð��̸�");
					}else{
						deliComp.setRemaintime(rs.getString("RTIME")+"�ð�");
					}
				} else{
					deliComp.setRemaintime(rs.getString("RDAY")+"�� "+rs.getString("RTIME")+"�ð�");
				}
				
				deliComp.setInputperdeli(rs.getInt("INPUTCNT") + "/" + rs.getInt("DELICNT") + "��");
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
	 * ��οϷ� ��� ���� ��������
	 * @param deptcd : �μ��ڵ�
	 * @return int : ��οϷ� ��� �Ѱ��� 
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
//�����μ� ��ι������� ���� ����
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
//�����μ� ��ι������� ���� ��
			
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
	 * ��οϷ� ��� �Ѱ��� ��������
	 * 
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return int : ��οϷ� ��� �Ѱ��� 
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
	 * ����ϱ� - �Է´���� ���� ����
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return true, false : true - ����� ���� O, false - ����� ���� X
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
	 * ����ϱ� - ���缱 ������ ���� ����
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return true, false : true - ���缱 ���� O, false - ���缱 ���� X
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
	 * ����ϱ� - ���������̳� ����
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * 
	 * @return true, false : true - ���������̳� O, false - �������� �ʰ� X
	 * @throws Exception 
	 */
	public boolean IsAssignEndDt(int sysdocno) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean check = true;
		
		String sysdate = "SYSDATE";
		if ( "�λ�6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
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
	 * ����ϱ� �� - ��������/�����˸��� ��������
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * 
	 * @return DeliveryDetailBean : ��������/�����˸����� ��� �ִ� Bean
	 * @throws Exception 
	 */
	public DeliveryDetailBean viewDeliveryDetail(int sysdocno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		DeliveryDetailBean detailBean = new DeliveryDetailBean();
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT SUBSTR(ENDDT,1,4)||'��'||SUBSTR(ENDDT,6,2)||'��'||SUBSTR(ENDDT,9,2)||'�� '||SUBSTR(ENDDT,12,2)||'��'||SUBSTR(ENDDT,15,2)||'��' ENDDT \n");
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
	 * ����ϱ� - �������ΰ�� �˻� ���ǿ� �ش��ϴ� �� �������� 
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
	 * ����ϱ� - �������ΰ�� �˻� ���ǿ� �ش��ϴ� �� �������� 
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
