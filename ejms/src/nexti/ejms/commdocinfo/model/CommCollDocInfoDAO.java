/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������չ������� dao
 * ����:
 */
package nexti.ejms.commdocinfo.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.util.Utils;

public class CommCollDocInfoDAO {
	
	private static Logger logger = Logger.getLogger(CommCollDocInfoDAO.class);
	
	/**
	 * ���չ������� ������ ����
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * 
	 * @return CommCollDocInfoBean
	 * @throws Exception 
	 */
	public CommCollDocInfoBean viewCommCollDocInfo(int sysdocno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		CommCollDocInfoBean collDocInfoBean = new CommCollDocInfoBean();
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT DM.DOCTITLE \n");
			selectQuery.append("	 , TO_CHAR(TO_DATE(DM.BASICDATE,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD') BASICDATE \n");  
			selectQuery.append("     , SUBSTR(DM.BASICDATE,1,4)||'�� '||SUBSTR(DM.BASICDATE,6,2)||'�� '||SUBSTR(DM.BASICDATE,9,2)||'��' BASICDATE1 \n");  
			selectQuery.append("     , DECODE(DM.DELIVERYDT, '', '', SUBSTR(DM.DELIVERYDT,1,4)||'�� '||SUBSTR(DM.DELIVERYDT,6,2)||'�� '||SUBSTR(DM.DELIVERYDT,9,2)||'��') DELIVERYDT \n");
			selectQuery.append("	 , SUBSTR(DM.SUBMITDATE,1,4)||'�� '||SUBSTR(DM.SUBMITDATE,6,2)||'�� '||SUBSTR(DM.SUBMITDATE,9,2)||'��' SUBMITDT \n");
			selectQuery.append("     , DM.DOCNO \n");
			selectQuery.append("     , (SELECT U.CLASS_NAME || ' ' || U.USER_NAME \n");
			selectQuery.append("          FROM SANCCOL S, USR U \n");
			selectQuery.append("         WHERE S.SANCUSRID = U.USER_ID \n");
			selectQuery.append("           AND S.SYSDOCNO = DM.SYSDOCNO \n");
			selectQuery.append("           AND S.GUBUN = 2 \n");
			selectQuery.append("           AND S.SANCDT = (SELECT MAX(SANCDT) FROM SANCCOL WHERE SYSDOCNO = S.SYSDOCNO AND GUBUN = 2)) SANCUSRINFO \n");
			selectQuery.append("     , SUBSTR(DM.BASICDATE,1,4)||'�� '||SUBSTR(DM.BASICDATE,6,2)||'�� '||SUBSTR(DM.BASICDATE,9,2)||'��' BASICDATE \n");
			selectQuery.append("     , DM.DOCSTATE, DM.COLDEPTCD, DM.COLDEPTNM, DM.CHRGUNITCD, DM.CHRGUNITNM, DM.CHRGUSRCD, DM.CHRGUSRNM \n");
			selectQuery.append("     , (SELECT DEPT_TEL FROM DEPT WHERE DEPT_ID = DM.COLDEPTCD) DEPT_TEL \n");
			selectQuery.append("     , DM.BASIS, DM.SUMMARY, DM.OPENDT \n");
			selectQuery.append("     , SUBSTR(DM.ENDDT,1,4)||'��'||SUBSTR(DM.ENDDT,6,2)||'��'||SUBSTR(DM.ENDDT,9,2)||'�� '||SUBSTR(DM.ENDDT,12,2)||'��'||SUBSTR(DM.ENDDT,15,2)||'��' ENDDT \n");
			selectQuery.append("	 , TO_CHAR(TO_DATE(DM.ENDDT,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD') ENDDT_DATE \n");
			selectQuery.append("	 , TO_CHAR(TO_DATE(DM.ENDDT,'YYYY-MM-DD HH24:MI:SS'),'HH24') ENDDT_HOUR \n");
			selectQuery.append("	 , TO_CHAR(TO_DATE(ENDDT, 'YYYY-MM-DD HH24:MI:SS'), 'MI') ENDDT_MIN \n");
			selectQuery.append("     , DM.ENDCOMMENT \n");
			selectQuery.append("     , DM.TGTDEPTNM TARGETDEPTNM \n");
			selectQuery.append("     , DM.SANCRULE SANCRULECD \n");
			selectQuery.append("     , (SELECT CCDNAME FROM CCD WHERE CCDCD = '006' AND CCDSUBCD = DM.SANCRULE) SANCRULE \n");
			selectQuery.append("     , OPENINPUT \n");
			selectQuery.append("     , FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
			selectQuery.append("  FROM DOCMST DM, DOCMSTFILE DMF \n");
			selectQuery.append(" WHERE DM.SYSDOCNO = DMF.SYSDOCNO(+) ");
			selectQuery.append(" AND DM.SYSDOCNO = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				collDocInfoBean.setSysdocno(sysdocno);
				collDocInfoBean.setDoctitle(rs.getString("DOCTITLE"));
				collDocInfoBean.setBasicdate(rs.getString("BASICDATE"));
				collDocInfoBean.setBasicdate1(rs.getString("BASICDATE1"));
				collDocInfoBean.setDeliverydt(rs.getString("DELIVERYDT"));
				collDocInfoBean.setSubmitdt(rs.getString("SUBMITDT"));
				collDocInfoBean.setDocno(rs.getString("DOCNO"));
				collDocInfoBean.setSancusrinfo(rs.getString("SANCUSRINFO"));
				collDocInfoBean.setBasicdt(rs.getString("BASICDATE"));
				collDocInfoBean.setDocstate(rs.getString("DOCSTATE"));
				collDocInfoBean.setColdeptcd(rs.getString("COLDEPTCD"));
				collDocInfoBean.setColdeptnm(rs.getString("COLDEPTNM"));
				collDocInfoBean.setChrgunitcd(rs.getInt("CHRGUNITCD"));
				collDocInfoBean.setChrgunitnm(rs.getString("CHRGUNITNM"));
				collDocInfoBean.setChrgusrcd(rs.getString("CHRGUSRCD"));
				collDocInfoBean.setChrgusrnm(rs.getString("CHRGUSRNM"));
				collDocInfoBean.setColdepttel(rs.getString("DEPT_TEL"));
				collDocInfoBean.setBasis(rs.getString("BASIS"));
				collDocInfoBean.setSummary(rs.getString("SUMMARY"));
				collDocInfoBean.setOpendt(rs.getString("OPENDT"));
				collDocInfoBean.setEnddt(rs.getString("ENDDT"));
				collDocInfoBean.setEnddt_date(rs.getString("ENDDT_DATE"));
				collDocInfoBean.setEnddt_hour(rs.getString("ENDDT_HOUR"));
				collDocInfoBean.setEnddt_min(rs.getString("ENDDT_MIN"));
				collDocInfoBean.setEndcomment(rs.getString("ENDCOMMENT"));
				collDocInfoBean.setTargetdeptnm(rs.getString("TARGETDEPTNM"));
				collDocInfoBean.setSancrulecd(rs.getString("SANCRULECD"));
				collDocInfoBean.setSancrule(rs.getString("SANCRULE"));
				collDocInfoBean.setOpeninput(rs.getString("OPENINPUT"));
				collDocInfoBean.setFileseq(rs.getInt("FILESEQ"));
				collDocInfoBean.setFilenm(rs.getString("FILENM"));
				collDocInfoBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				collDocInfoBean.setFilesize(rs.getInt("FILESIZE"));
				collDocInfoBean.setExt(rs.getString("EXT"));
				collDocInfoBean.setOrd(rs.getInt("ORD"));
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return collDocInfoBean;
	}
	
	/**
	 * ���չ��� �󼼳��� ���� ����
	 * @param ColldocBean cdbean
	 * @param int savemode
	 * @return int ���ý��۹�����ȣ
	 * @throws Exception 
	 */
	public int saveCommCollDocInfo(CommCollDocInfoBean cdbean, int sysdocno, String sessionId) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
	    int cnt = 0;
	    int bindPos = 0;
	    
		String summary = null;
		if(cdbean.getSummary() != null) {
			summary = cdbean.getSummary().replaceAll("'", "''");
		} else {
			summary = "";
		}
	    
	    StringBuffer updateQuery = new StringBuffer();
	    
	    updateQuery.append("\n  UPDATE DOCMST				");
	    updateQuery.append("\n     SET DOCTITLE 	= ?		");
	    updateQuery.append("\n         ,BASICDATE 	= ?		");
	    updateQuery.append("\n         ,TGTDEPTNM 	= ?		");
	    updateQuery.append("\n         ,COLDEPTCD 	= ?		"); 
	    updateQuery.append("\n     	   ,COLDEPTNM 	= ?		");
	    updateQuery.append("\n     	   ,CHRGUNITCD 	= ?		");
	    updateQuery.append("\n         ,CHRGUNITNM 	= ?		");
	    updateQuery.append("\n         ,CHRGUSRNM	= ?		");
	    updateQuery.append("\n         ,BASIS 		= ?		");
	    updateQuery.append("\n         ,SUMMARY 	= '" + summary + "' ");
	    updateQuery.append("\n         ,ENDDT		= ?		"); 
	    updateQuery.append("\n     	   ,ENDCOMMENT	= ?		");
	    updateQuery.append("\n     	   ,SANCRULE    = ?		");
	    updateQuery.append("\n     	   ,OPENINPUT   = ?		");
	    updateQuery.append("\n         ,UPTDT		= TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS') ");
	    updateQuery.append("\n         ,UPTUSRID	= ?		");  
	    updateQuery.append("\n   WHERE SYSDOCNO  	= ?		"); 
		
		try {			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(updateQuery.toString());
			
			pstmt.setString(++bindPos, cdbean.getDoctitle());	//��������
			pstmt.setString(++bindPos, cdbean.getBasicdate());	//�ڷ������
			pstmt.setString(++bindPos, cdbean.getTargetdeptnm());	//���մ��μ���			
			pstmt.setString(++bindPos, cdbean.getColdeptcd());	//���պμ��ڵ�
			pstmt.setString(++bindPos, cdbean.getColdeptnm());	//���պμ���
			pstmt.setInt(++bindPos, cdbean.getChrgunitcd());	//���մ������ڵ�
			pstmt.setString(++bindPos, cdbean.getChrgunitnm());	//���մ�������
			pstmt.setString(++bindPos, cdbean.getChrgusrnm());	//���մ���ڸ�
			pstmt.setString(++bindPos, cdbean.getBasis());		//���ñٰ�
			//pstmt.setString(++bindPos, cdbean.getSummary());	//���հ���		
			pstmt.setString(++bindPos, cdbean.getEnddt());		//�����Ͻ�
			pstmt.setString(++bindPos, cdbean.getEndcomment());	//�����˸���
			pstmt.setString(++bindPos, cdbean.getSancrulecd());	//�����ڷ�����
			pstmt.setString(++bindPos, cdbean.getOpeninput());//�����Է�
			pstmt.setString(++bindPos, sessionId);				//������
			pstmt.setInt(++bindPos, sysdocno);					//�ý��۹�����ȣ 
	
			if(pstmt.executeUpdate()>0){
				cnt++;
			}
			
		 } catch (Exception e) {
			 logger.error("ERROR",e);
			 ConnectionManager.close(conn,pstmt);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn, pstmt);
	     }
	     
		return cnt;
	}
	/**
	 * ���� ���
	 * @param CommCollDocSearchBean searchBean
	 * @param int start
	 * @param int end
	 * @return List ���չ������(ArrayList)
	 * @throws Exception 
	 */
	public List getExhibitList(CommCollDocSearchBean searchBean, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT (CNT-SEQ+1) BUNHO, X.* \n" +
							   "FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n" +
							   "      FROM (SELECT ROWNUM SEQ, A2.*  \n" +
							   "            FROM (SELECT F.FORMSEQ, F.FORMKIND, D.SYSDOCNO, D.DOCTITLE, D.COLDEPTNM, D.CHRGUNITNM, D.CHRGUSRNM, SUBSTR(D.ENDDT,1,10) ENDDT, '1' AS GUBUN, D.CRTDT, D.UPTDT, NVL(DE.DEPT_RANK, 99999) \n" +
							   "                  FROM DOCMST D, DEPT DE,   \n" +
							   "                       (SELECT SYSDOCNO, FORMSEQ, FORMKIND \n" +
							   "                        FROM FORMMST F \n" +
							   "                        WHERE FORMSEQ = (SELECT MIN(FORMSEQ) FROM FORMMST WHERE SYSDOCNO = F.SYSDOCNO and to_number(formkind) <= 3)) F \n" +
							   "                  WHERE D.SYSDOCNO = F.SYSDOCNO \n" +
							   "                  AND D.COLDEPTCD = DE.DEPT_ID(+)" +
							   "                  AND D.SYSDOCNO IN (-- ���պμ�,��������,�������,�˻�Ű����,�����ۼ���,�ڷ������,���մ����,�Է´���� \n" +
							   "                              SELECT DISTINCT A.SYSDOCNO \n" +
							   "                              FROM DOCMST A \n" +
							   "                              WHERE A.DOCSTATE = '06' \n" +								           
		   					   "                                AND CASE A.OPENDT WHEN '1900-01-04' THEN (SELECT COUNT(*) FROM DOCMST D, INPUTUSR I \n" +
		   					   "                                                                        WHERE D.SYSDOCNO = I.SYSDOCNO AND D.SYSDOCNO = A.SYSDOCNO \n" +
		   					   "                                                                        AND (CHRGUSRCD = '"+searchBean.getUserid()+"' \n" +
		   					   "                                                                             OR INPUTUSRID = '"+searchBean.getUserid()+"')) \n" +
		   					   "                                                                  ELSE SYSDATE - TO_DATE(A.OPENDT, 'YYYY\"-\"MM\"-\"DD') END > 0 \n");
			//���պμ� ����
			if (Utils.isNotNull(searchBean.getSearchdept())) {
			selectQuery.append("                                AND A.COLDEPTCD IN (SELECT T1.DEPT_ID \n" +
							   "                                                    FROM DEPT T1 \n" +
							   "                                                    START WITH T1.DEPT_ID = '"+searchBean.getSearchdept()+"' \n" +
							   "                                                    CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
			}
			//�������� ����
			if (Utils.isNotNull(searchBean.getSearchdoctitle())) {
			selectQuery.append("                                AND A.DOCTITLE LIKE '%"+searchBean.getSearchdoctitle().replaceAll("'", "''")+"%' \n");
			}
			//������� ����
			if (Utils.isNotNull(searchBean.getSearchformtitle())) {
			selectQuery.append("                                AND SYSDOCNO IN (SELECT SYSDOCNO \n" +
							   "                                                 FROM FORMMST \n" +
					   		   "                                                 WHERE FORMTITLE LIKE '%"+searchBean.getSearchformtitle().replaceAll("'", "''")+"%') \n");
			}
			//�˻��� ����
			if (Utils.isNotNull(searchBean.getSearchkey())) {
			selectQuery.append("                                AND A.SEARCHKEY LIKE '%"+searchBean.getSearchkey().replaceAll("'", "''")+"%' \n");
			}
			//�����ۼ��� ����
			if (Utils.isNotNull(searchBean.getSearchcrtdtfr()) && Utils.isNotNull(searchBean.getSearchcrtdtto())) {
			selectQuery.append("                                AND TO_DATE(SUBSTR(A.CRTDT, 1, 10), 'YYYY\"-\"MM\"-\"DD') BETWEEN TO_DATE('"+searchBean.getSearchcrtdtfr()+"', 'YYYY\"-\"MM\"-\"DD')" +
							   "                                                                               AND TO_DATE('"+searchBean.getSearchcrtdtto()+"', 'YYYY\"-\"MM\"-\"DD') \n");
			}
			//�ڷ������ ����
			if (Utils.isNotNull(searchBean.getSearchbasicdatefr()) && Utils.isNotNull(searchBean.getSearchbasicdateto())) {
			selectQuery.append("                                AND TO_DATE(A.BASICDATE, 'YYYY\"-\"MM\"-\"DD') BETWEEN TO_DATE('"+searchBean.getSearchbasicdatefr()+"', 'YYYY\"-\"MM\"-\"DD')" +
					   		   "                                                                                   AND TO_DATE('"+searchBean.getSearchbasicdateto()+"', 'YYYY\"-\"MM\"-\"DD') \n");
			}
			//���մ���� ����
			if (Utils.isNotNull(searchBean.getSearchchrgusrnm())) {
			selectQuery.append("                                AND A.CHRGUSRNM LIKE '%"+searchBean.getSearchchrgusrnm().replaceAll("'", "''")+"%' \n");
			}
			//�Է´���� ����
			if (Utils.isNotNull(searchBean.getSearchinputusrnm())) {
			selectQuery.append("                                AND SYSDOCNO IN (SELECT DISTINCT SYSDOCNO \n" +
							   "                                                 FROM INPUTUSR \n" +
			   		   		   "                                                 WHERE INPUTUSRNM LIKE '%"+searchBean.getSearchinputusrnm().replaceAll("'", "''")+"%') \n");
			}
			//�������� ��� �߰�
			selectQuery.append("                  ) UNION ALL \n" +
					           "                  SELECT 0, R.RANGE, R.RCHNO, R.TITLE, R.COLDEPTNM, (SELECT CHRGUNITNM FROM CHRGUNIT WHERE DEPT_ID = R.COLDEPTCD \n" +
							   "                                                                     AND CHRGUNITCD = (SELECT CHRGUNITCD FROM USR WHERE USER_ID = R.CHRGUSRID)), \n" +
							   "                         R.CHRGUSRNM, SUBSTR(R.ENDDT,1,10) ENDDT, '2' AS GUBUN, R.CRTDT, R.UPTDT, NVL(DE.DEPT_RANK, 99999) \n" +
							   "                  FROM RCHMST R, DEPT DE \n" +
							   "                  WHERE R.COLDEPTCD = DE.DEPT_ID(+)" +
							   "                  AND R.RCHNO IN (-- ���պμ�,��������,�����ۼ���,���մ���� \n" +
							   "                              SELECT DISTINCT R.RCHNO \n" +
							   "                              FROM RCHMST R \n" +
							   "                              WHERE R.EXHIBIT = '1' \n" +								           
							   "                                AND R.GROUPYN = 'N' \n" +								           
		   					   "                                AND TO_DATE(R.ENDDT, 'YYYY\"-\"MM\"-\"DD') < SYSDATE - 1 \n");
			//���պμ� ����
			if (Utils.isNotNull(searchBean.getSearchdept())) {
			selectQuery.append("                                AND R.COLDEPTCD IN (SELECT T1.DEPT_ID \n" +
							   "                                                    FROM DEPT T1 \n" +
							   "                                                    START WITH T1.DEPT_ID = '"+searchBean.getSearchdept()+"' \n" +
							   "                                                    CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
			}
			//�������� ����
			if (Utils.isNotNull(searchBean.getSearchdoctitle())) {
			selectQuery.append("                                AND R.TITLE LIKE '%"+searchBean.getSearchdoctitle().replaceAll("'", "''")+"%' \n");
			}
			//������� ����
			if (Utils.isNotNull(searchBean.getSearchformtitle())) {
			selectQuery.append("                                AND R.RCHNO = 0 \n");
			}
			//�˻��� ����
			if (Utils.isNotNull(searchBean.getSearchkey())) {
			selectQuery.append("                                AND R.RCHNO = 0 \n");
			}
			//�����ۼ��� ����
			if (Utils.isNotNull(searchBean.getSearchcrtdtfr()) && Utils.isNotNull(searchBean.getSearchcrtdtto())) {
			selectQuery.append("                                AND TO_DATE(SUBSTR(R.CRTDT, 1, 10), 'YYYY\"-\"MM\"-\"DD') BETWEEN TO_DATE('"+searchBean.getSearchcrtdtfr()+"', 'YYYY\"-\"MM\"-\"DD')" +
							   "                                                                               AND TO_DATE('"+searchBean.getSearchcrtdtto()+"', 'YYYY\"-\"MM\"-\"DD') \n");
			}
			//�ڷ������ ����
			if (Utils.isNotNull(searchBean.getSearchbasicdatefr()) && Utils.isNotNull(searchBean.getSearchbasicdateto())) {
			selectQuery.append("                                AND R.RCHNO = 0 \n");
			}
			//���մ���� ����
			if (Utils.isNotNull(searchBean.getSearchchrgusrnm())) {
			selectQuery.append("                                AND R.CHRGUSRNM LIKE '%"+searchBean.getSearchchrgusrnm().replaceAll("'", "''")+"%' \n");
			}
			//�Է´���� ����
			if (Utils.isNotNull(searchBean.getSearchinputusrnm())) {
			selectQuery.append("                                AND R.RCHNO = 0 \n");
			}
			//��������׷� ��� �߰�
			selectQuery.append("                  ) UNION ALL \n" +
					           "                  SELECT 0, R.RANGE, R.RCHGRPNO, R.TITLE, R.COLDEPTNM, (SELECT CHRGUNITNM FROM CHRGUNIT WHERE DEPT_ID = R.COLDEPTCD \n" +
							   "                                                                        AND CHRGUNITCD = (SELECT CHRGUNITCD FROM USR WHERE USER_ID = R.CHRGUSRID)), \n" +
							   "                         R.CHRGUSRNM, SUBSTR(R.ENDDT,1,10) ENDDT, '3' AS GUBUN, R.CRTDT, R.UPTDT, NVL(DE.DEPT_RANK, 99999) \n" +
							   "                  FROM RCHGRPMST R, DEPT DE \n" +
							   "                  WHERE R.COLDEPTCD = DE.DEPT_ID(+)" +
							   "                  AND R.RCHGRPNO IN (-- ���պμ�,��������,�����ۼ���,���մ���� \n" +
							   "                              SELECT DISTINCT R.RCHGRPNO \n" +
							   "                              FROM RCHGRPMST R \n" +
							   "                              WHERE R.EXHIBIT = '1' \n" +								           
							   "                                AND R.GROUPYN = 'N' \n" +								           
		   					   "                                AND TO_DATE(R.ENDDT, 'YYYY\"-\"MM\"-\"DD') < SYSDATE - 1 \n");
			//���պμ� ����
			if (Utils.isNotNull(searchBean.getSearchdept())) {
			selectQuery.append("                                AND R.COLDEPTCD IN (SELECT T1.DEPT_ID \n" +
							   "                                                    FROM DEPT T1 \n" +
							   "                                                    START WITH T1.DEPT_ID = '"+searchBean.getSearchdept()+"' \n" +
							   "                                                    CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
			}
			//�������� ����
			if (Utils.isNotNull(searchBean.getSearchdoctitle())) {
			selectQuery.append("                                AND R.TITLE LIKE '%"+searchBean.getSearchdoctitle().replaceAll("'", "''")+"%' \n");
			}
			//������� ����
			if (Utils.isNotNull(searchBean.getSearchformtitle())) {
			selectQuery.append("                                AND R.RCHGRPNO = 0 \n");
			}
			//�˻��� ����
			if (Utils.isNotNull(searchBean.getSearchkey())) {
			selectQuery.append("                                AND R.RCHGRPNO = 0 \n");
			}
			//�����ۼ��� ����
			if (Utils.isNotNull(searchBean.getSearchcrtdtfr()) && Utils.isNotNull(searchBean.getSearchcrtdtto())) {
			selectQuery.append("                                AND TO_DATE(SUBSTR(R.CRTDT, 1, 10), 'YYYY\"-\"MM\"-\"DD') BETWEEN TO_DATE('"+searchBean.getSearchcrtdtfr()+"', 'YYYY\"-\"MM\"-\"DD')" +
							   "                                                                               AND TO_DATE('"+searchBean.getSearchcrtdtto()+"', 'YYYY\"-\"MM\"-\"DD') \n");
			}
			//�ڷ������ ����
			if (Utils.isNotNull(searchBean.getSearchbasicdatefr()) && Utils.isNotNull(searchBean.getSearchbasicdateto())) {
			selectQuery.append("                                AND R.RCHGRPNO = 0 \n");
			}
			//���մ���� ����
			if (Utils.isNotNull(searchBean.getSearchchrgusrnm())) {
			selectQuery.append("                                AND R.CHRGUSRNM LIKE '%"+searchBean.getSearchchrgusrnm().replaceAll("'", "''")+"%' \n");
			}
			//�Է´���� ����
			if (Utils.isNotNull(searchBean.getSearchinputusrnm())) {
			selectQuery.append("                                AND R.RCHGRPNO = 0 \n");
			}
			selectQuery.append("				  ) ORDER BY 12, 9, 4, CRTDT DESC, UPTDT DESC) A2) A1) X \n" +
							   "WHERE SEQ BETWEEN ? AND ? \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
									
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while (rs.next()) {
				CommCollDocInfoBean Bean = new CommCollDocInfoBean();
				
				Bean.setSeqno(rs.getInt("BUNHO"));
				Bean.setFormseq(rs.getInt("FORMSEQ"));
				Bean.setFormkind(rs.getString("FORMKIND"));
				Bean.setSysdocno(rs.getInt("SYSDOCNO"));
				Bean.setDoctitle(rs.getString("DOCTITLE"));		
				Bean.setEnddt_date(rs.getString("ENDDT"));
				Bean.setColdeptnm(rs.getString("COLDEPTNM"));
				Bean.setChrgunitnm(rs.getString("CHRGUNITNM"));
				Bean.setChrgusrnm(rs.getString("CHRGUSRNM"));
				Bean.setGubun(rs.getString("GUBUN"));
				
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
	 * ���� ��� ���� ��������
	 * @param String deptcd
	 * @return int ���չ�������
	 * @throws Exception 
	 */
	public int getExhibitTotCnt(CommCollDocSearchBean searchBean) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*)CNT \n" +
							   "FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n" +
							   "      FROM (SELECT ROWNUM SEQ, A2.*  \n" +
							   "            FROM (SELECT F.FORMSEQ, F.FORMKIND, D.SYSDOCNO, D.DOCTITLE, D.CHRGUNITNM, D.CHRGUSRNM, SUBSTR(D.ENDDT,1,10) ENDDT, '1' AS GUBUN, D.CRTDT \n" +
							   "                  FROM DOCMST D,   \n" +
							   "                 (SELECT SYSDOCNO, FORMSEQ, FORMKIND \n" +
							   "                  FROM FORMMST F \n" +
							   "                  WHERE FORMSEQ = (SELECT MIN(FORMSEQ) FROM FORMMST WHERE SYSDOCNO = F.SYSDOCNO and to_number(formkind) <= 3)) F \n" +
							   "                  WHERE D.SYSDOCNO = F.SYSDOCNO \n" + 
							   "                  AND D.SYSDOCNO IN (-- ���պμ�,��������,�������,�˻�Ű����,�����ۼ���,�ڷ������,���մ����,�Է´���� \n" +
							   "                              SELECT DISTINCT A.SYSDOCNO \n" +
							   "                              FROM DOCMST A \n" +
							   "                              WHERE A.DOCSTATE = '06' \n" +								           
							   "                                AND CASE A.OPENDT WHEN '1900-01-04' THEN (SELECT COUNT(*) FROM DOCMST D, INPUTUSR I \n" +
		   					   "                                                                        WHERE D.SYSDOCNO = I.SYSDOCNO AND D.SYSDOCNO = A.SYSDOCNO \n" +
		   					   "                                                                        AND (CHRGUSRCD = '"+searchBean.getUserid()+"' \n" +
		   					   "                                                                             OR INPUTUSRID = '"+searchBean.getUserid()+"')) \n" +
		   					   "                                                                  ELSE SYSDATE - TO_DATE(A.OPENDT, 'YYYY\"-\"MM\"-\"DD') END > 0 \n");
			//���պμ� ����
			if (Utils.isNotNull(searchBean.getSearchdept())) {
			selectQuery.append("                                AND A.COLDEPTCD IN (SELECT T1.DEPT_ID \n" +
							   "                                                    FROM DEPT T1 \n" +
							   "                                                    START WITH T1.DEPT_ID = '"+searchBean.getSearchdept()+"' \n" +
							   "                                                    CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
			}
			//�������� ����
			if (Utils.isNotNull(searchBean.getSearchdoctitle())) {
			selectQuery.append("                                AND A.DOCTITLE LIKE '%"+searchBean.getSearchdoctitle().replaceAll("'", "''")+"%' \n");
			}
			//������� ����
			if (Utils.isNotNull(searchBean.getSearchformtitle())) {
			selectQuery.append("                                AND SYSDOCNO IN (SELECT SYSDOCNO \n" +
							   "                                                 FROM FORMMST \n" +
					   		   "                                                 WHERE FORMTITLE LIKE '%"+searchBean.getSearchformtitle().replaceAll("'", "''")+"%') \n");
			}
			//�˻��� ����
			if (Utils.isNotNull(searchBean.getSearchkey())) {
			selectQuery.append("                                AND A.SEARCHKEY LIKE '%"+searchBean.getSearchkey().replaceAll("'", "''")+"%' \n");
			}
			//�����ۼ��� ����
			if (Utils.isNotNull(searchBean.getSearchcrtdtfr()) && Utils.isNotNull(searchBean.getSearchcrtdtto())) {
			selectQuery.append("                                AND TO_DATE(SUBSTR(A.CRTDT, 1, 10), 'YYYY\"-\"MM\"-\"DD') BETWEEN TO_DATE('"+searchBean.getSearchcrtdtfr()+"', 'YYYY\"-\"MM\"-\"DD')" +
							   "                                                                               AND TO_DATE('"+searchBean.getSearchcrtdtto()+"', 'YYYY\"-\"MM\"-\"DD') \n");
			}
			//�ڷ������ ����
			if (Utils.isNotNull(searchBean.getSearchbasicdatefr()) && Utils.isNotNull(searchBean.getSearchbasicdateto())) {
			selectQuery.append("                                AND TO_DATE(A.BASICDATE, 'YYYY\"-\"MM\"-\"DD') BETWEEN TO_DATE('"+searchBean.getSearchbasicdatefr()+"', 'YYYY\"-\"MM\"-\"DD')" +
					   		   "                                                                                   AND TO_DATE('"+searchBean.getSearchbasicdateto()+"', 'YYYY\"-\"MM\"-\"DD') \n");
			}
			//���մ���� ����
			if (Utils.isNotNull(searchBean.getSearchchrgusrnm())) {
			selectQuery.append("                                AND A.CHRGUSRNM LIKE '%"+searchBean.getSearchchrgusrnm().replaceAll("'", "''")+"%' \n");
			}
			//�Է´���� ����
			if (Utils.isNotNull(searchBean.getSearchinputusrnm())) {
			selectQuery.append("                                AND SYSDOCNO IN (SELECT DISTINCT SYSDOCNO \n" +
							   "                                                 FROM INPUTUSR \n" +
			   		   		   "                                                 WHERE INPUTUSRNM LIKE '%"+searchBean.getSearchinputusrnm().replaceAll("'", "''")+"%') \n");
			}
			//�������� ��� �߰�
			selectQuery.append("                  ) UNION ALL \n" +
							   "                  SELECT 0, R.RANGE, R.RCHNO, R.TITLE, (SELECT CHRGUNITNM FROM CHRGUNIT WHERE DEPT_ID = R.COLDEPTCD \n" +
							   "                                                      AND CHRGUNITCD = (SELECT CHRGUNITCD FROM USR WHERE USER_ID = R.CHRGUSRID)), \n" +
							   "                         R.CHRGUSRNM, SUBSTR(R.ENDDT,1,10) ENDDT, '2' AS GUBUN, R.CRTDT \n" +
							   "                  FROM RCHMST R \n" +
							   "                  WHERE R.RCHNO IN (-- ���պμ�,��������,�����ۼ���,���մ���� \n" +
							   "                              SELECT DISTINCT R.RCHNO \n" +
							   "                              FROM RCHMST R \n" +
							   "                              WHERE R.EXHIBIT = '1' \n" +
							   "                                AND R.GROUPYN = 'N' \n" +
							   "                                AND TO_DATE(R.ENDDT, 'YYYY\"-\"MM\"-\"DD') < SYSDATE - 1 \n");
			//���պμ� ����
			if (Utils.isNotNull(searchBean.getSearchdept())) {
			selectQuery.append("                                AND R.COLDEPTCD IN (SELECT T1.DEPT_ID \n" +
							   "                                                    FROM DEPT T1 \n" +
							   "                                                    START WITH T1.DEPT_ID = '"+searchBean.getSearchdept()+"' \n" +
							   "                                                    CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
			}
			//�������� ����
			if (Utils.isNotNull(searchBean.getSearchdoctitle())) {
			selectQuery.append("                                AND R.TITLE LIKE '%"+searchBean.getSearchdoctitle().replaceAll("'", "''")+"%' \n");
			}
			//������� ����
			if (Utils.isNotNull(searchBean.getSearchformtitle())) {
			selectQuery.append("                                AND R.RCHNO = 0 \n");
			}
			//�˻��� ����
			if (Utils.isNotNull(searchBean.getSearchkey())) {
			selectQuery.append("                                AND R.RCHNO = 0 \n");
			}
			//�����ۼ��� ����
			if (Utils.isNotNull(searchBean.getSearchcrtdtfr()) && Utils.isNotNull(searchBean.getSearchcrtdtto())) {
			selectQuery.append("                                AND TO_DATE(SUBSTR(R.CRTDT, 1, 10), 'YYYY\"-\"MM\"-\"DD') BETWEEN TO_DATE('"+searchBean.getSearchcrtdtfr()+"', 'YYYY\"-\"MM\"-\"DD')" +
							   "                                                                               AND TO_DATE('"+searchBean.getSearchcrtdtto()+"', 'YYYY\"-\"MM\"-\"DD') \n");
			}
			//�ڷ������ ����
			if (Utils.isNotNull(searchBean.getSearchbasicdatefr()) && Utils.isNotNull(searchBean.getSearchbasicdateto())) {
			selectQuery.append("                                AND R.RCHNO = 0 \n");
			}
			//���մ���� ����
			if (Utils.isNotNull(searchBean.getSearchchrgusrnm())) {
			selectQuery.append("                                AND R.CHRGUSRNM LIKE '%"+searchBean.getSearchchrgusrnm().replaceAll("'", "''")+"%' \n");
			}
			//�Է´���� ����
			if (Utils.isNotNull(searchBean.getSearchinputusrnm())) {
			selectQuery.append("                                AND R.RCHNO = 0 \n");
			}
			//��������׷� ��� �߰�
			selectQuery.append("                  ) UNION ALL \n" +
					           "                  SELECT 0, R.RANGE, R.RCHGRPNO, R.TITLE, (SELECT CHRGUNITNM FROM CHRGUNIT WHERE DEPT_ID = R.COLDEPTCD \n" +
							   "                                                      AND CHRGUNITCD = (SELECT CHRGUNITCD FROM USR WHERE USER_ID = R.CHRGUSRID)), \n" +
							   "                         R.CHRGUSRNM, SUBSTR(R.ENDDT,1,10) ENDDT, '3' AS GUBUN, R.CRTDT \n" +
							   "                  FROM RCHGRPMST R \n" +
							   "                  WHERE R.RCHGRPNO IN (-- ���պμ�,��������,�����ۼ���,���մ���� \n" +
							   "                              SELECT DISTINCT R.RCHGRPNO \n" +
							   "                              FROM RCHGRPMST R \n" +
							   "                              WHERE R.EXHIBIT = '1' \n" +								           
							   "                                AND R.GROUPYN = 'N' \n" +								           
		   					   "                                AND TO_DATE(R.ENDDT, 'YYYY\"-\"MM\"-\"DD') < SYSDATE - 1 \n");
			//���պμ� ����
			if (Utils.isNotNull(searchBean.getSearchdept())) {
			selectQuery.append("                                AND R.COLDEPTCD IN (SELECT T1.DEPT_ID \n" +
							   "                                                    FROM DEPT T1 \n" +
							   "                                                    START WITH T1.DEPT_ID = '"+searchBean.getSearchdept()+"' \n" +
							   "                                                    CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
			}
			//�������� ����
			if (Utils.isNotNull(searchBean.getSearchdoctitle())) {
			selectQuery.append("                                AND R.TITLE LIKE '%"+searchBean.getSearchdoctitle().replaceAll("'", "''")+"%' \n");
			}
			//������� ����
			if (Utils.isNotNull(searchBean.getSearchformtitle())) {
			selectQuery.append("                                AND R.RCHGRPNO = 0 \n");
			}
			//�˻��� ����
			if (Utils.isNotNull(searchBean.getSearchkey())) {
			selectQuery.append("                                AND R.RCHGRPNO = 0 \n");
			}
			//�����ۼ��� ����
			if (Utils.isNotNull(searchBean.getSearchcrtdtfr()) && Utils.isNotNull(searchBean.getSearchcrtdtto())) {
			selectQuery.append("                                AND TO_DATE(SUBSTR(R.CRTDT, 1, 10), 'YYYY\"-\"MM\"-\"DD') BETWEEN TO_DATE('"+searchBean.getSearchcrtdtfr()+"', 'YYYY\"-\"MM\"-\"DD')" +
							   "                                                                               AND TO_DATE('"+searchBean.getSearchcrtdtto()+"', 'YYYY\"-\"MM\"-\"DD') \n");
			}
			//�ڷ������ ����
			if (Utils.isNotNull(searchBean.getSearchbasicdatefr()) && Utils.isNotNull(searchBean.getSearchbasicdateto())) {
			selectQuery.append("                                AND R.RCHGRPNO = 0 \n");
			}
			//���մ���� ����
			if (Utils.isNotNull(searchBean.getSearchchrgusrnm())) {
			selectQuery.append("                                AND R.CHRGUSRNM LIKE '%"+searchBean.getSearchchrgusrnm().replaceAll("'", "''")+"%' \n");
			}
			//�Է´���� ����
			if (Utils.isNotNull(searchBean.getSearchinputusrnm())) {
			selectQuery.append("                                AND R.RCHGRPNO = 0 \n");
			}
			selectQuery.append("				  ) ORDER BY CRTDT DESC ) A2) A1) X \n");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
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
}
