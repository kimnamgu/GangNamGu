/**
 * 작성일: 2010.05.26
 * 작성자: 사원 신종영
 * 모듈명: LDAP 모니터링 action
 * 설명:
 */
package nexti.ejms.ldapMonitoring.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.ldapMonitoring.model.LdapMonitoringBean;
import nexti.ejms.util.commfunction;

public class LdapMonitoringDAO {
	
	private static Logger logger = Logger.getLogger(LdapMonitoringDAO.class);

	
	/**
	 * LDAP 동기화 모니터링 목록
	 * @param orggbn : 조직구분
	 * @param gbn : 구분(0:부서, 1:사용자)
	 * @param syncdate : 동기화 일시
	 * @return List : LDAP 동기화 모니터링 목록
	 * @throws Exception 
	 */
	public List ldapMonitoringList(String orggbn, String orggbn_dt, String gbn, String syncdate, String rep_dept, String user_id, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List resultList = null;
		StringBuffer selectQuery = null;
		
		try {
			int subLen = commfunction.getDeptFullNameSubstringIndex(user_id);
			selectQuery = new StringBuffer();
			if(!"".equals(syncdate)) syncdate = syncdate.replaceAll("-", "")+"%";
			
			if(gbn.equals("0")) {            
				selectQuery.append(" SELECT  SEQ SEQNO, DEPT_ID AS LDAP_ID, LDAP_NAME,  APPLYDT                                                               		\n"); 
				selectQuery.append(" FROM(                                                                                                                    		\n");
				selectQuery.append(" 	SELECT ROWNUM SEQ, B.OUCODE AS DEPT_ID,                                                                               		\n");
				selectQuery.append(" 			   NVL(TRIM(SUBSTR(DEPT_FULLNAME, "+subLen+")),DEPT_NAME) AS LDAP_NAME, 											\n");
				selectQuery.append(" 			   TO_DATE(APPLYDT,'YYYY-MM-DD HH24:MI:SS') AS APPLYDT                         										\n");
				selectQuery.append(" 	FROM                                                                                                                   		\n");
				selectQuery.append(" 		(SELECT DEPT_ID, DEPT_NAME, DEPT_FULLNAME                                                                              	\n");
				selectQuery.append(" 		FROM DEPT WHERE 1=1                                                                                                    	\n");
				if(!"".equals(orggbn) ) {
					selectQuery.append("	AND ORGGBN = '"+orggbn+"' 																								\n");
				}
				if(!"".equals(orggbn_dt) ) {
					selectQuery.append("	AND DEPT_ID = '"+orggbn_dt+"'																							\n");
				}
				selectQuery.append(" 		START WITH DEPT_ID = '"+rep_dept+"'		                                                   	                        	\n");
				selectQuery.append(" 		CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                                            	\n");   		
				selectQuery.append(" 		ORDER SIBLINGS BY DEPT_RANK)A, DEPT_TEMP_LDAP B	                                                                    	\n");
				selectQuery.append(" 	WHERE A.DEPT_ID = B.OUCODE        		                                                                                	\n");
				selectQuery.append(" 	AND APPLYDT LIKE DECODE('"+syncdate+"', '', (SELECT MAX(SUBSTR(APPLYDT, 0, 8)) FROM DEPT_TEMP_LDAP)||'%','"+syncdate+"')    \n");
				selectQuery.append(" 	AND DEPT_ID != '"+rep_dept+"'                                                                                              	\n");
				selectQuery.append(" )                                                                                                                        		\n");
				selectQuery.append(" WHERE SEQ BETWEEN ? AND ?                                                                                               		\n");
				
			} else if(gbn.equals("1")) {
				
				selectQuery.append(" SELECT  SEQ SEQNO, LDAP_ID, LDAP_NAME,  APPLYDT       		                                                        			\n"); 
				selectQuery.append(" FROM(                                                                                                                    		\n");
				selectQuery.append(" 	SELECT ROWNUM SEQ, B.OUCODE AS DEPT_ID,                                                                                		\n");
				selectQuery.append(" 			SUBSTR(RESINUMBER, 0, 6)||'-*******' LDAP_ID, DISPLAYNAME LDAP_NAME,												\n");
				selectQuery.append(" 			TO_DATE(APPLYDT,'YYYY-MM-DD HH24:MI:SS') AS APPLYDT                         										\n");
				selectQuery.append(" 	FROM                                                                                                                   		\n");
				selectQuery.append(" 		(SELECT DEPT_ID, DEPT_FULLNAME                                                                                      	\n");
				selectQuery.append(" 		FROM DEPT WHERE 1=1                                                                                                    	\n");
				if(!"".equals(orggbn) ) {
					selectQuery.append("	AND ORGGBN = '"+orggbn+"' 																								\n");
				}
				if(!"".equals(orggbn_dt) ) {
					selectQuery.append("	AND DEPT_ID = '"+orggbn_dt+"'																							\n");
				}
				selectQuery.append(" 		START WITH DEPT_ID = '"+rep_dept+"'		                                                   	                        	\n");
				selectQuery.append(" 		CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                               	                             	\n");   		
				selectQuery.append(" 		ORDER SIBLINGS BY DEPT_RANK)A, USR_TEMP_LDAP B                                        	                            	\n");
				selectQuery.append(" 	WHERE A.DEPT_ID = B.OUCODE        		                                                                                	\n");
				selectQuery.append(" 	AND APPLYDT LIKE DECODE('"+syncdate+"', '', (SELECT MAX(SUBSTR(APPLYDT, 0, 8)) FROM DEPT_TEMP_LDAP)||'%','"+syncdate+"')    \n");
				selectQuery.append(" 	AND DEPT_ID != '"+rep_dept+"'                                                                                              	\n");
				selectQuery.append(" )                                                                                                                        		\n");
				selectQuery.append(" WHERE SEQ BETWEEN ? AND ?                                                                                               		\n");
			}
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			resultList = new ArrayList();
			
			while (rs.next()) {
				LdapMonitoringBean ldapMonitoringBean = new LdapMonitoringBean();
				ldapMonitoringBean.setSeqno(rs.getInt("SEQNO"));
				ldapMonitoringBean.setLdap_id(rs.getString("LDAP_ID"));
				ldapMonitoringBean.setLdap_name(rs.getString("LDAP_NAME"));
				ldapMonitoringBean.setApplydt(rs.getString("APPLYDT"));		
				
				resultList.add(ldapMonitoringBean);
			}							
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
			
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return resultList;
	}
	
	/**
	 * LDAP 동기화 모니터링 개수 가져오기
	 * 
	 * @param orggbn : 구분
	 * @param syncdate : 동기화 일시
	 * 
	 * @return Integer : LDAP 동기화 모니터링 개수
	 * @throws Exception 
	 */
	public int ldapMonitoringCount(String orggbn, String orggbn_dt, String gbn, String syncdate, String rep_dept, String user_id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer selectQuery = null;
		int totalcount = 0;
		
		try {
			selectQuery = new StringBuffer();
			int subLen = commfunction.getDeptFullNameSubstringIndex(user_id);
			if(!"".equals(syncdate)) syncdate = syncdate.replaceAll("-", "")+"%";

			if(gbn.equals("0")) {            
				selectQuery.append(" SELECT	COUNT(*)								    		                                                        			\n"); 
				selectQuery.append(" FROM(                                                                                                                    		\n");
				selectQuery.append(" 	SELECT ROWNUM SEQ, B.OUCODE AS DEPT_ID,                                                                               		\n");
				selectQuery.append(" 			   NVL(TRIM(SUBSTR(DEPT_FULLNAME, "+subLen+")),DEPT_NAME) AS LDAP_NAME, APPLYDT 									\n");
				selectQuery.append(" 	FROM                                                                                                                   		\n");
				selectQuery.append(" 		(SELECT DEPT_ID, DEPT_NAME, DEPT_FULLNAME                                                                              	\n");
				selectQuery.append(" 		FROM DEPT WHERE 1=1                                                                                                    	\n");
				if(!"".equals(orggbn) ) {
					selectQuery.append("	AND ORGGBN = '"+orggbn+"' 																								\n");
				}
				if(!"".equals(orggbn_dt) ) {
					selectQuery.append("	AND DEPT_ID = '"+orggbn_dt+"'																							\n");
				}
				selectQuery.append(" 		START WITH DEPT_ID = '"+rep_dept+"'		                                                   	                        	\n");
				selectQuery.append(" 		CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                                            	\n");   		
				selectQuery.append(" 		ORDER SIBLINGS BY DEPT_RANK)A, DEPT_TEMP_LDAP B	                                                                    	\n");
				selectQuery.append(" 	WHERE A.DEPT_ID = B.OUCODE        		                                                                                	\n");
				selectQuery.append(" 	AND APPLYDT LIKE DECODE('"+syncdate+"', '', (SELECT MAX(SUBSTR(APPLYDT, 0, 8)) FROM DEPT_TEMP_LDAP)||'%','"+syncdate+"')    \n");
				selectQuery.append(" 	AND DEPT_ID != '"+rep_dept+"'                                                                                              	\n");
				selectQuery.append(" )                                                                                                                        		\n");
				
			} else if(gbn.equals("1")) {
				
				selectQuery.append(" SELECT	COUNT(*)								    		                                                        			\n"); 
				selectQuery.append(" FROM(                                                                                                                    		\n");
				selectQuery.append(" 	SELECT ROWNUM SEQ, B.OUCODE AS DEPT_ID,                                                                                		\n");
				selectQuery.append(" 			SUBSTR(RESINUMBER, 0, 6)||'-*******' LDAP_ID, DISPLAYNAME LDAP_NAME,  APPLYDT          				               	\n");
				selectQuery.append(" 	FROM                                                                                                                   		\n");
				selectQuery.append(" 		(SELECT DEPT_ID, DEPT_FULLNAME                                                                                      	\n");
				selectQuery.append(" 		FROM DEPT WHERE 1=1                                                                                                    	\n");
				if(!"".equals(orggbn) ) {
					selectQuery.append("	AND ORGGBN = '"+orggbn+"' 																								\n");
				}
				if(!"".equals(orggbn_dt) ) {
					selectQuery.append("	AND DEPT_ID = '"+orggbn_dt+"'																							\n");
				}
				selectQuery.append(" 		START WITH DEPT_ID = '"+rep_dept+"'		                                                   	                        	\n");
				selectQuery.append(" 		CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                               	                             	\n");   		
				selectQuery.append(" 		ORDER SIBLINGS BY DEPT_RANK)A, USR_TEMP_LDAP B                                        	                            	\n");
				selectQuery.append(" 	WHERE A.DEPT_ID = B.OUCODE        		                                                                                	\n");
				selectQuery.append(" 	AND APPLYDT LIKE DECODE('"+syncdate+"', '', (SELECT MAX(SUBSTR(APPLYDT, 0, 8)) FROM DEPT_TEMP_LDAP)||'%','"+syncdate+"')    \n");
				selectQuery.append(" 	AND DEPT_ID != '"+rep_dept+"'                                                                                              	\n");
				selectQuery.append(" )                                                                                                                        		\n");
			}
			
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() )
				totalcount = rs.getInt(1);
		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return totalcount;
	}
}
