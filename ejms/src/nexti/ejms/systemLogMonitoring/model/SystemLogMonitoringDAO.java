/**
 * 작성일: 2010.05.26
 * 작성자: 사원 신종영
 * 모듈명: System 접속 Log 모니터링 DAO
 * 설명:
 */
package nexti.ejms.systemLogMonitoring.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.systemLogMonitoring.model.SystemLogMonitoringBean;
import nexti.ejms.util.commfunction;

public class SystemLogMonitoringDAO {
	
	private static Logger logger = Logger.getLogger(SystemLogMonitoringDAO.class);

	
	/**
	 * System 접속 Log 모니터링 목록
	 * 
	 * @param orggbn : 구분
	 * @param syncdate : 동기화 일시
	 * @return List : System 접속 Log 모니터링 목록
	 * @throws Exception 
	 */
	public List systemLogMonitoringList(String orggbn, String orggbn_dt, String rep_dept, String user_id, String frDate, String toDate, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List resultList = null;
		StringBuffer selectQuery = null;
		int bindPos = 0;
		
		try {
			selectQuery = new StringBuffer();
			
			String qType = "";
			if(orggbn.equals("")) 	qType = "A";	// 전체
			else 					qType = "B";	// 조직구분 1단계
			if(orggbn_dt != "") 	qType = "C";	// 조직구분 2단계
			int subLen = commfunction.getDeptFullNameSubstringIndex(user_id);
			
			if(qType.equals("A")) {
				selectQuery.append(" SELECT CCDNAME, ORGGBN AS CCDSUBCD, COUNT(*) AS CNT	                       		\n");     
				selectQuery.append(" FROM                                                                         		\n");
				selectQuery.append(" 	(	                                                                          	\n");
				selectQuery.append(" 	SELECT *                                                                    	\n");
				selectQuery.append(" 	FROM DEPT A,(SELECT  CCDSUBCD, CCDNAME  FROM  CCD  WHERE  CCDCD = '023') B  	\n");
				selectQuery.append(" 	WHERE A.ORGGBN = B.CCDSUBCD)A,  LOGINLOG B                                  	\n");
				selectQuery.append(" WHERE A.DEPT_ID = B.DEPT_ID                                                  		\n");
				selectQuery.append(" AND  LOGINTIME BETWEEN ? AND ?                             						\n");
				selectQuery.append(" GROUP BY CCDNAME, CCDSUBCD, ORGGBN                                           		\n");
				
				/*
				selectQuery.append("SELECT  (TOTAL-SEQ+1) SEQNO, CCDSUBCD, CCDNAME, CNT, LOGIN_METHOD													\n");
				selectQuery.append("  FROM (SELECT (MAX(SEQ)OVER()) TOTAL, A1.* 																		\n");
				selectQuery.append("          FROM (SELECT  ROWNUM SEQ, B.CCDSUBCD, B.CCDNAME, ROUND(A.CNT/100) CNT,                                    \n");
				selectQuery.append("						DECODE(LOGIN_METHOD, '1', '직접', '2', '행정전자서명', '3', '시도포탈', '기타') LOGIN_METHOD    	\n");
				selectQuery.append("				  FROM (SELECT  A.ORGGBN, LOGIN_METHOD, CNT_TOTAL CNT      											\n");
				selectQuery.append("					      FROM (SELECT  B.ORGGBN, A.LOGIN_METHOD, COUNT(*) AS CNT                                   \n");
				selectQuery.append("					              FROM  LOGINLOG A, DEPT B                                       					\n");
				selectQuery.append("					             WHERE  A.DEPT_ID = B.DEPT_ID                                        				\n");
				selectQuery.append("					               AND  USE_YN LIKE 'Y'                                         					\n");
				selectQuery.append("					               AND  LOGINTIME BETWEEN ? AND ?                                         			\n");
				selectQuery.append("					          GROUP BY  B.ORGGBN, A.LOGIN_METHOD) A,                                           		\n");
				selectQuery.append("					           (SELECT  ORGGBN, MAX(CNT) AS CNT, SUM(CNT) AS CNT_TOTAL                    			\n");
				selectQuery.append("					              FROM (SELECT  B.ORGGBN, A.LOGIN_METHOD, COUNT(*) AS CNT                           \n");
				selectQuery.append("					                      FROM  LOGINLOG A, DEPT B             										\n");
				selectQuery.append("					                     WHERE  A.DEPT_ID = B.DEPT_ID             									\n");
				selectQuery.append("					               		   AND  USE_YN LIKE 'Y'                             						\n");
				selectQuery.append("					               		   AND  LOGINTIME BETWEEN ? AND ?                             				\n");
				selectQuery.append("					          		  GROUP BY  B.ORGGBN, A.LOGIN_METHOD)                                          	\n");   
				selectQuery.append("					          GROUP BY  ORGGBN) B                                          							\n");      
				selectQuery.append("					     WHERE	A.ORGGBN = B.ORGGBN AND A.CNT = B.CNT) A,                       					\n");       
				selectQuery.append("					   (SELECT  CCDSUBCD, CCDNAME  FROM  CCD  WHERE  CCDCD = '023') B                                                     				\n");
				selectQuery.append("			     WHERE  A.ORGGBN = B.CCDSUBCD                                                     					\n");
				selectQuery.append("			  ORDER BY  B.CCDSUBCD                                                       							\n");
				selectQuery.append("   			    ) A1                                                                                 				\n");  
				selectQuery.append("   		)                                                                                							\n"); 
				selectQuery.append(" WHERE  SEQ BETWEEN ? AND ?                                                                                  		\n");
				*/
			} else if(qType.equals("B")) {
				selectQuery.append(" SELECT SEQ AS SEQNO, CCDSUBCD, CCDNAME, CNT                                                        		\n");
				selectQuery.append(" FROM(                                                                                              		\n");
				selectQuery.append(" SELECT ROWNUM AS SEQ, CCDSUBCD, CCDNAME, CNT                                                       		\n");
				selectQuery.append(" FROM(                                                                                              		\n");
				selectQuery.append(" 	SELECT CCDSUBCD, CCDNAME,  COUNT(*) AS CNT                                                        		\n");
				selectQuery.append(" 	FROM(                                                                                             		\n");
				selectQuery.append(" 	SELECT NVL(TRIM(SUBSTR(DEPT_FULLNAME, "+subLen+")),DEPT_NAME) AS CCDNAME, CCDSUBCD 			    		\n");
				selectQuery.append(" 	FROM                                                                                              		\n");
				selectQuery.append(" 		(SELECT DEPT_ID, DEPT_ID AS CCDSUBCD, DEPT_NAME, DEPT_FULLNAME      						    	\n");
				selectQuery.append(" 		FROM DEPT A,(SELECT  CCDSUBCD, CCDNAME  FROM  CCD  WHERE  CCDCD = '023') B                      	\n");
				selectQuery.append(" 		WHERE A.ORGGBN = B.CCDSUBCD                                                                     	\n");
				if(!"".equals(orggbn) ) {
				selectQuery.append("	AND ORGGBN = '"+orggbn+"' 																				\n");
				}              	
				selectQuery.append(" 		START WITH DEPT_ID = '"+rep_dept+"'                                                        	      	\n");
				selectQuery.append(" 		CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                        	\n");                       		
				selectQuery.append(" 		ORDER SIBLINGS BY DEPT_RANK)A, LOGINLOG B	                                                      	\n");
				selectQuery.append(" 	WHERE A.DEPT_ID = B.DEPT_ID        		                                                            	\n");
				selectQuery.append(" 	AND LOGINTIME BETWEEN ? AND ?							                                           		\n");
				selectQuery.append(" 	AND A.DEPT_ID != '"+rep_dept+"'                                                                    		\n");
				selectQuery.append(" 	)                                                                                                 		\n");
				selectQuery.append(" GROUP BY CCDNAME, CCDSUBCD                                                                         		\n");
				selectQuery.append(" ))                                                                                                 		\n");
				selectQuery.append(" WHERE SEQ BETWEEN ? AND ?	                                                                         		\n");
				
				/*
				selectQuery.append("SELECT  (TOTAL-SEQ+1) SEQNO, CCDSUBCD, CCDNAME, CNT, LOGIN_METHOD													\n");
				selectQuery.append("  FROM (SELECT (MAX(SEQ)OVER()) TOTAL, A1.* 																		\n");
				selectQuery.append("          FROM (SELECT  ROWNUM SEQ, A.DEPT_ID CCDSUBCD, B.DEPT_NAME CCDNAME, ROUND(A.CNT/100) CNT, 					\n");
				selectQuery.append("          				DECODE(LOGIN_METHOD, '1', '직접', '2', '행정전자서명', '3', '시도포탈', '기타') LOGIN_METHOD		\n");
				selectQuery.append("          		  FROM (SELECT  A.DEPT_ID, A.ORGGBN, LOGIN_METHOD, B.CNTTOTAL CNT									\n");
				selectQuery.append("          				  FROM (SELECT  A.DEPT_ID, B.ORGGBN, A.LOGIN_METHOD, COUNT(*) AS CNT						\n");
				selectQuery.append("          						  FROM  LOGINLOG A, DEPT B															\n");
				selectQuery.append("          						 WHERE  A.DEPT_ID = B.DEPT_ID														\n");
				selectQuery.append("          						   AND  USE_YN LIKE 'Y'     														\n");
				selectQuery.append("          						   AND  LOGINTIME BETWEEN ? AND ?													\n");
				selectQuery.append("          						   AND  ORGGBN = ? 		 															\n");
				selectQuery.append("          					  GROUP BY  A.DEPT_ID, B.ORGGBN, A.LOGIN_METHOD) A,										\n");
				selectQuery.append("          					   (SELECT  DEPT_ID, ORGGBN, MAX(CNT) AS CNT, SUM(CNT) CNTTOTAL							\n");
				selectQuery.append("          						  FROM (SELECT  A.DEPT_ID, B.ORGGBN, A.LOGIN_METHOD, COUNT(*) AS CNT				\n");
				selectQuery.append("          								  FROM  LOGINLOG A, DEPT B													\n");
				selectQuery.append("          								 WHERE  A.DEPT_ID = B.DEPT_ID												\n");
				selectQuery.append("          								   AND  USE_YN LIKE 'Y'  													\n");
				selectQuery.append("          								   AND  LOGINTIME BETWEEN ? AND ?											\n");
				selectQuery.append("          								   AND  ORGGBN = ?	 														\n");
				selectQuery.append("          							  GROUP BY  A.DEPT_ID, B.ORGGBN, A.LOGIN_METHOD)								\n");
				selectQuery.append("          				   	  GROUP BY  DEPT_ID, ORGGBN) B															\n");
				selectQuery.append("           				 WHERE	A.DEPT_ID = B.DEPT_ID AND  A.ORGGBN = B.ORGGBN  AND  A.CNT = B.CNT) A,				\n");
				selectQuery.append("          			   (SELECT  DEPT_ID,																			\n");
				selectQuery.append("          						REPLACE(DEPT_FULLNAME, (SELECT DEPT_NAME FROM DEPT WHERE DEPT_ID = '" + rootid + "')||' ', '')  DEPT_NAME	\n");
				selectQuery.append("          				  FROM  DEPT																				\n");
				selectQuery.append("          				 WHERE  USE_YN LIKE 'Y'																		\n");
				selectQuery.append("          				   AND  ORGGBN = ?																			\n");
				selectQuery.append("      				START WITH  DEPT_ID = '6280000'    																\n");
				selectQuery.append("       		  CONNECT BY PRIOR  DEPT_ID = UPPER_DEPT_ID      														\n");
				selectQuery.append("          	 ORDER SIBLINGS BY  DEPT_RANK) B																		\n");
				selectQuery.append("       		 WHERE	A.DEPT_ID = B.DEPT_ID   																		\n");
				selectQuery.append("      		) A1    																								\n");
				selectQuery.append("		)          																									\n");
				selectQuery.append(" WHERE  SEQ BETWEEN ? AND ?                                                                                  		\n");
				*/
			} else if(qType.equals("C")) {
				selectQuery.append(" SELECT SEQ AS SEQNO, CCDSUBCD, CCDNAME, CNT                                                        		\n");
				selectQuery.append(" FROM(                                                                                              		\n");
				selectQuery.append(" SELECT ROWNUM AS SEQ, CCDSUBCD, CCDNAME, CNT                                                       		\n");
				selectQuery.append(" FROM(                                                                                              		\n");
				selectQuery.append(" 	SELECT CCDSUBCD, CCDNAME,  COUNT(*) AS CNT                                                        		\n");
				selectQuery.append(" 	FROM(                                                                                             		\n");
				selectQuery.append(" 	SELECT NVL(TRIM(SUBSTR(DEPT_FULLNAME, "+subLen+")),DEPT_NAME) AS CCDNAME, CCDSUBCD 			    		\n");
				selectQuery.append(" 	FROM                                                                                              		\n");
				selectQuery.append(" 		(SELECT DEPT_ID, DEPT_ID AS CCDSUBCD, DEPT_NAME, DEPT_FULLNAME                                    	\n");
				selectQuery.append(" 		FROM DEPT A,(SELECT  CCDSUBCD, CCDNAME  FROM  CCD  WHERE  CCDCD = '023') B                      	\n");
				selectQuery.append(" 		WHERE A.ORGGBN = B.CCDSUBCD                                                                     	\n");
				if(!"".equals(orggbn) ) {
				selectQuery.append("		AND ORGGBN = '"+orggbn+"' 																			\n");
				}              	
				if(!"".equals(orggbn_dt) ) {
					selectQuery.append("	AND DEPT_ID = '"+orggbn_dt+"'																		\n");
				}
				selectQuery.append(" 		START WITH DEPT_ID = '"+rep_dept+"'                                                        	      	\n");
				selectQuery.append(" 		CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                        	\n");                       		
				selectQuery.append(" 		ORDER SIBLINGS BY DEPT_RANK)A, LOGINLOG B	                                                      	\n");
				selectQuery.append(" 	WHERE A.DEPT_ID = B.DEPT_ID        		                                                            	\n");
				selectQuery.append(" 	AND LOGINTIME BETWEEN ? AND ?							                                           		\n");
				selectQuery.append(" 	AND A.DEPT_ID != '"+rep_dept+"'                                                                    		\n");
				selectQuery.append(" 	)                                                                                                 		\n");
				selectQuery.append(" GROUP BY CCDNAME, CCDSUBCD                                                                         		\n");
				selectQuery.append(" ))                                                                                                 		\n");
				selectQuery.append(" WHERE SEQ BETWEEN ? AND ?	                                                                         		\n");
				
				/*
				selectQuery.append("SELECT  (TOTAL-SEQ+1) SEQNO, CCDSUBCD, CCDNAME, CNT, LOGIN_METHOD													\n");
				selectQuery.append("  FROM (SELECT (MAX(SEQ)OVER()) TOTAL, A1.* 																		\n");
				selectQuery.append("          FROM (SELECT  ROWNUM SEQ, A.DEPT_ID CCDSUBCD, B.DEPT_NAME CCDNAME, ROUND(A.CNT/100) CNT, 					\n");
				selectQuery.append("          				DECODE(LOGIN_METHOD, '1', '직접', '2', '행정전자서명', '3', '시도포탈', '기타') LOGIN_METHOD		\n");
				selectQuery.append("          		  FROM (SELECT  A.DEPT_ID, A.ORGGBN, LOGIN_METHOD, B.CNTTOTAL CNT									\n");
				selectQuery.append("          				  FROM (SELECT  A.DEPT_ID, B.ORGGBN, A.LOGIN_METHOD, COUNT(*) AS CNT						\n");
				selectQuery.append("          						  FROM  LOGINLOG A, DEPT B															\n");
				selectQuery.append("          						 WHERE  A.DEPT_ID = B.DEPT_ID														\n");
				selectQuery.append("          						   AND  USE_YN LIKE 'Y'     														\n");
				selectQuery.append("          						   AND  LOGINTIME BETWEEN ? AND ?													\n");
				selectQuery.append("          						   AND  ORGGBN = ? 		 															\n");
				selectQuery.append("          						   AND  A.DEPT_ID = ? 		 														\n");
				selectQuery.append("          					  GROUP BY  A.DEPT_ID, B.ORGGBN, A.LOGIN_METHOD) A,										\n");
				selectQuery.append("          					   (SELECT  DEPT_ID, ORGGBN, MAX(CNT) AS CNT, SUM(CNT) CNTTOTAL							\n");
				selectQuery.append("          						  FROM (SELECT  A.DEPT_ID, B.ORGGBN, A.LOGIN_METHOD, COUNT(*) AS CNT				\n");
				selectQuery.append("          								  FROM  LOGINLOG A, DEPT B													\n");
				selectQuery.append("          								 WHERE  A.DEPT_ID = B.DEPT_ID												\n");
				selectQuery.append("          								   AND  USE_YN LIKE 'Y'  													\n");
				selectQuery.append("          								   AND  LOGINTIME BETWEEN ? AND ?											\n");
				selectQuery.append("          								   AND  ORGGBN = ?	 														\n");
				selectQuery.append("          						   		   AND  A.DEPT_ID = ? 		 												\n");
				selectQuery.append("          							  GROUP BY  A.DEPT_ID, B.ORGGBN, A.LOGIN_METHOD)								\n");
				selectQuery.append("          				   	  GROUP BY  DEPT_ID, ORGGBN) B															\n");
				selectQuery.append("           				 WHERE	A.DEPT_ID = B.DEPT_ID AND  A.ORGGBN = B.ORGGBN  AND  A.CNT = B.CNT) A,				\n");
				selectQuery.append("          			   (SELECT  DEPT_ID,																			\n");
				selectQuery.append("          						REPLACE(DEPT_FULLNAME, (SELECT DEPT_NAME FROM DEPT WHERE DEPT_ID = '" + root_id + "')||' ', '')  DEPT_NAME	\n");
				selectQuery.append("          				  FROM  DEPT																				\n");
				selectQuery.append("          				 WHERE  USE_YN LIKE 'Y'																		\n");
				selectQuery.append("          				   AND  ORGGBN = ?																			\n");
				selectQuery.append("          				   AND  DEPT_ID = ?																			\n");
				selectQuery.append("      				START WITH  DEPT_ID = '6280000'    																\n");
				selectQuery.append("       		  CONNECT BY PRIOR  DEPT_ID = UPPER_DEPT_ID      														\n");
				selectQuery.append("          	 ORDER SIBLINGS BY  DEPT_RANK) B																		\n");
				selectQuery.append("       		 WHERE	A.DEPT_ID = B.DEPT_ID   																		\n");
				selectQuery.append("      		) A1    																								\n");
				selectQuery.append("		)          																									\n");
				selectQuery.append(" WHERE  SEQ BETWEEN ? AND ?                                                                                  		\n");
				*/
			}

			//System.out.println("selectQuery.toString() " + selectQuery.toString());
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());

			if(!"".equals(frDate)) frDate = frDate+'%';
			if(!"".equals(toDate)) toDate = toDate+'%';
			if(qType.equals("A")) {
				pstmt.setString(++bindPos, frDate);
				pstmt.setString(++bindPos, toDate);
			} else if(qType.equals("B")) {
				pstmt.setString(++bindPos, frDate);
				pstmt.setString(++bindPos, toDate);
				pstmt.setInt(++bindPos, start);
				pstmt.setInt(++bindPos, end);
			} else if(qType.equals("C")) {
				pstmt.setString(++bindPos, frDate);
				pstmt.setString(++bindPos, toDate);;
				pstmt.setInt(++bindPos, start);
				pstmt.setInt(++bindPos, end);
			}
			
			rs = pstmt.executeQuery();
			resultList = new ArrayList();
			while (rs.next()) {
				SystemLogMonitoringBean systemLogMonitoringBean = new SystemLogMonitoringBean();
				
				systemLogMonitoringBean.setCcdSubCd(rs.getString("CCDSUBCD"));
				systemLogMonitoringBean.setCcdName(rs.getString("CCDNAME"));
				systemLogMonitoringBean.setCnt(rs.getString("CNT"));
				
				resultList.add(systemLogMonitoringBean);
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
	 * System 접속 Log 모니터링 개수 가져오기
	 * 
	 * @param orggbn : 구분
	 * @param syncdate : 동기화 일시
	 * 
	 * @return Integer : System 접속 Log 모니터링 개수
	 * @throws Exception 
	 */
	public int systemLogMonitoringCount(String orggbn, String orggbn_dt, String frDate, String toDate) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int bindPos = 0;
		int totalcount = 0;
		
		try {
			String query = "";
			
			String qType = "";
			if(orggbn.equals("")) 	qType = "A";	// 전체
			else 					qType = "B";	// 조직구분 1단계
			if(orggbn_dt != "") 	qType = "C";	// 조직구분 2단계
			
			if(qType.equals("A")) {
				query = "	SELECT  COUNT(*) CNT        						" +
				 		"	  FROM (SELECT  COUNT(USER_ID)           			" +
				 		"	          FROM  LOGINLOG A, DEPT B          		" +
				 		"	         WHERE  A.REP_DEPT = B.DEPT_ID" +
				 		"              AND	LOGINTIME BETWEEN ? AND ?   		" +
				 		"	      GROUP BY  ORGGBN)								";
			} else if(qType.equals("B")) {
				query = "	SELECT  COUNT(*) CNT        						" +
				 		"	  FROM (SELECT  COUNT(USER_ID)             			" +
						"	  		  FROM  LOGINLOG A,              			" +
						"		   		   (SELECT  DEPT_ID, DEPT_NAME          " +
						"		      		  FROM  DEPT                        " +
						"	         		 WHERE  USE_YN LIKE 'Y'             " +
						"			   		   AND  ORGGBN = ?                  " +    
						"	      		  ORDER BY  DEPT_DEPTH, DEPT_RANK) B    " +
						"	 		 WHERE  A.DEPT_ID = B.DEPT_ID              	" +
						"	   		   AND  LOGINTIME BETWEEN ? AND ?           " +
						"  		  GROUP BY  A.DEPT_ID)                         	";
			} else if(qType.equals("C")) {
				query = "	SELECT  COUNT(*) CNT        						" +
				 		"	  FROM (SELECT  COUNT(USER_ID)	              		" +
						"	  		  FROM  LOGINLOG A,              			" +
						"		   		   (SELECT  DEPT_ID, DEPT_NAME          " +
						"		      		  FROM  DEPT                        " +
						"	         		 WHERE  USE_YN LIKE 'Y'             " +
						"			   		   AND  ORGGBN = ?                  " +
						"			   		   AND  DEPT_ID = ?                 " +
						"	      		  ORDER BY  DEPT_DEPTH, DEPT_RANK) B    " +
						"	 		 WHERE  A.DEPT_ID = B.DEPT_ID              	" +
						"	   		   AND  LOGINTIME BETWEEN ? AND ?           " +
						"  		  GROUP BY  A.DEPT_ID)                          ";
			}
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(query);
			if(!"".equals(frDate)) frDate = frDate+'%';
			if(!"".equals(toDate)) toDate = toDate+'%';
			
			if(qType.equals("B")) {
				pstmt.setString(++bindPos, orggbn);
			} else if(qType.equals("C")) {
				pstmt.setString(++bindPos, orggbn);
				pstmt.setString(++bindPos, orggbn_dt);
			}
			pstmt.setString(++bindPos, frDate);
			pstmt.setString(++bindPos, toDate);
			
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
	
	/**
	 * System 접속 Log 모니터링 팝업화면 목록
	 * 
	 * @param orggbn : 구분
	 * @param syncdate : 동기화 일시
	 * 
	 * @return List : System 접속 Log 모니터링 팝업화면 목록
	 * @throws Exception 
	 */
	public List systemLogDetailMonitoringList(String sch_gubun, String sch_usernm, String ccdSubCd, String frDate, String toDate, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List resultList = null;
		StringBuffer selectQuery = null;
		int bindPos = 0;
		
		try {
			selectQuery = new StringBuffer();
			/*
			selectQuery.append(" SELECT  (TOTAL-SEQ+1) SEQNO, CCDSUBCD, CCDNAME, LOGINTIME					    \n");
			selectQuery.append(" FROM(                                                                     		\n");
			selectQuery.append(" 	SELECT (MAX(SEQ)OVER()) TOTAL, A1.*							                \n");
			selectQuery.append(" 	FROM(                                                                    	\n");
			selectQuery.append(" 		SELECT	ROWNUM SEQ, A2.* 							                    \n");
			selectQuery.append("     FROM (                                                                		\n");
			selectQuery.append(" 	    SELECT A.USER_ID AS CCDSUBCD,  B.USER_NAME  AS CCDNAME, A.LOGINTIME  	\n");		
			selectQuery.append(" 	 	  FROM  LOGINLOG A, USR B , DEPT C                                     	\n");
			selectQuery.append(" 	    WHERE  UPPER(A.USER_ID) = UPPER(B.USER_ID)                           	\n");
			selectQuery.append(" 	    AND A.DEPT_ID = C.DEPT_ID                                            	\n");
			selectQuery.append(" 	    AND  C.ORGGBN = ?               	                                   	\n");
			selectQuery.append(" 	    AND  LOGINTIME BETWEEN BETWEEN ? AND ?                               	\n");
			selectQuery.append(" 	    ORDER BY  B.USER_NAME, A.LOGINTIME DESC                              	\n");
			selectQuery.append(" 	    ) A2					                                                \n");
			selectQuery.append(" 		) A1               								                        \n");
			selectQuery.append(" 	)                    										                \n");
			selectQuery.append(" WHERE	SEQ BETWEEN ? AND ?                                                		\n");
			 */
			if(sch_gubun.equals("0")){
				selectQuery.append(" SELECT  (TOTAL-SEQ+1) SEQNO, CCDSUBCD, CCDNAME, LOGINTIME				   		\n");
				selectQuery.append(" FROM(                                                                    		\n");
				selectQuery.append(" 	SELECT (MAX(SEQ)OVER()) TOTAL, A1.*							            	\n");
				selectQuery.append(" 	FROM(                                                                   	\n");
				selectQuery.append(" 		SELECT	ROWNUM SEQ, A2.* 							                	\n");
				selectQuery.append(" 	    FROM (                                                              	\n");
				selectQuery.append(" 	    	SELECT A.USER_ID AS CCDSUBCD,  A.LOGINTIME,                         \n"); 		
				selectQuery.append(" 	    	       NVL(B.USER_NAME, A.USER_ID) || '(' || SUBSTR(B.USER_SN, 0, 6) || ')' AS CCDNAME \n"); 		
				selectQuery.append(" 	 	  	FROM  LOGINLOG A, USR B , DEPT C                                  	\n");
				selectQuery.append(" 	    	WHERE  UPPER(A.USER_ID) = UPPER(B.USER_ID(+))                       \n");
				selectQuery.append(" 	    	AND A.DEPT_ID = C.DEPT_ID                                           \n");
				selectQuery.append(" 	    	AND  C.ORGGBN =  ?                  	                            \n");
				if(!sch_usernm.equals(""))
				selectQuery.append(" 			AND  NVL(B.USER_NAME, A.USER_ID) LIKE '%"+sch_usernm+"%'			\n");
				selectQuery.append(" 	    	AND  LOGINTIME BETWEEN ? AND ?                       		        \n");
				selectQuery.append(" 	    	ORDER BY  NVL(B.USER_NAME, A.USER_ID), A.LOGINTIME DESC             \n");
				selectQuery.append(" 	    ) A2					                                            	\n");
				selectQuery.append(" 	) A1               								                    		\n");
				selectQuery.append(" )                    										            		\n");
				selectQuery.append(" WHERE	SEQ BETWEEN ? AND ?                                               		\n");
			
			}else if(sch_gubun.equals("1")){
				selectQuery.append(" SELECT  (TOTAL-SEQ+1) SEQNO, CCDSUBCD, CCDNAME, LOGINTIME				   		\n");
				selectQuery.append(" FROM(                                                                    		\n");
				selectQuery.append(" 	SELECT (MAX(SEQ)OVER()) TOTAL, A1.*							            	\n");
				selectQuery.append(" 	FROM(                                                                   	\n");
				selectQuery.append(" 		SELECT	ROWNUM SEQ, A2.* 							                	\n");
				selectQuery.append(" 	  	FROM (                                                                	\n");
				selectQuery.append(" 	    	SELECT A.USER_ID AS CCDSUBCD,  A.LOGINTIME,                         \n"); 		
				selectQuery.append(" 	    	       NVL(B.USER_NAME, A.USER_ID) || '(' || SUBSTR(B.USER_SN, 0, 6) || ')' AS CCDNAME \n"); 		
				selectQuery.append(" 	 		FROM  LOGINLOG A, USR B			                                 	\n");
				selectQuery.append(" 	    	WHERE  UPPER(A.USER_ID) = UPPER(B.USER_ID(+))                       \n");
				selectQuery.append(" 	    	AND  A.DEPT_ID =  ?                  	                            \n");
				if(!sch_usernm.equals(""))
				selectQuery.append(" 			AND  NVL(B.USER_NAME, A.USER_ID) LIKE '%"+sch_usernm+"%'			\n");
				selectQuery.append(" 	    	AND  LOGINTIME BETWEEN ? AND ?                        			    \n");
				selectQuery.append(" 	    	ORDER BY  NVL(B.USER_NAME, A.USER_ID), A.LOGINTIME DESC             \n");
				selectQuery.append(" 	    ) A2					                                            	\n");
				selectQuery.append(" 	) A1               								                    		\n");
				selectQuery.append(" )                    										            		\n");
				selectQuery.append(" WHERE	SEQ BETWEEN ? AND ?                                               		\n");
			}
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());

			if(!"".equals(frDate)) frDate = frDate+'%';
			if(!"".equals(toDate)) toDate = toDate+'%';
			pstmt.setString(++bindPos, ccdSubCd);
			pstmt.setString(++bindPos, frDate);
			pstmt.setString(++bindPos, toDate);
			pstmt.setInt(++bindPos, start);
			pstmt.setInt(++bindPos, end);
			
			rs = pstmt.executeQuery();
			
			resultList = new ArrayList();
			
			while (rs.next()) {
				SystemLogMonitoringBean systemLogDetailMonitoringBean = new SystemLogMonitoringBean();
				
				systemLogDetailMonitoringBean.setCcdSubCd(rs.getString("CCDSUBCD"));
				systemLogDetailMonitoringBean.setCcdName(rs.getString("CCDNAME"));
				systemLogDetailMonitoringBean.setLoginTime(rs.getString("LOGINTIME"));
				
				resultList.add(systemLogDetailMonitoringBean);
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
	 * System 접속 Log 모니터링 팝업화면 개수 가져오기
	 * 
	 * @param orggbn : 구분
	 * @param syncdate : 동기화 일시
	 * 
	 * @return Integer : System 접속 Log 모니터링 개수
	 * @throws Exception 
	 */
	public int systemLogDetailMonitoringCount(String sch_gubun, String sch_usernm, String ccdSubCd, String frDate, String toDate) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int bindPos = 0;
		int totalcount = 0;
		StringBuffer selectQuery = null; 
		
		try {
			selectQuery = new StringBuffer();
			if(sch_gubun.equals("0")){
				selectQuery.append(" SELECT  COUNT(A.USER_ID) AS CNT				\n");
				selectQuery.append(" FROM  LOGINLOG A, USR B , DEPT C           	\n");
				selectQuery.append(" WHERE  UPPER(A.USER_ID) = UPPER(B.USER_ID(+)) 	\n");
				selectQuery.append(" AND A.DEPT_ID = C.DEPT_ID                  	\n");
				selectQuery.append(" AND  C.ORGGBN =  ?                  	     	\n");
				if(!sch_usernm.equals(""))
				selectQuery.append(" AND  NVL(B.USER_NAME, A.USER_ID) LIKE '%"+sch_usernm+"%' \n");
				selectQuery.append(" AND  LOGINTIME BETWEEN ? AND ?             	\n");
				selectQuery.append(" ORDER BY  NVL(B.USER_NAME, A.USER_ID), A.LOGINTIME DESC \n");
			
			}else if(sch_gubun.equals("1")){
				selectQuery.append(" SELECT  COUNT(A.USER_ID) AS CNT				\n");
				selectQuery.append(" FROM  LOGINLOG A, USR B			         	\n");
				selectQuery.append(" WHERE  UPPER(A.USER_ID) = UPPER(B.USER_ID(+)) 	\n");
				selectQuery.append(" AND  A.DEPT_ID =  ?                  	     	\n");
				if(!sch_usernm.equals(""))
				selectQuery.append(" AND  NVL(B.USER_NAME, A.USER_ID) LIKE '%"+sch_usernm+"%' \n");
				selectQuery.append(" AND  LOGINTIME BETWEEN ? AND ?             	\n");
				selectQuery.append(" ORDER BY  NVL(B.USER_NAME, A.USER_ID), A.LOGINTIME DESC \n");
			}
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			if(!"".equals(frDate)) frDate = frDate+'%';
			if(!"".equals(toDate)) toDate = toDate+'%';   
			pstmt.setString(++bindPos, ccdSubCd);
			pstmt.setString(++bindPos, frDate);
			pstmt.setString(++bindPos, toDate);
			
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
	
	/**
	 * 조직도 기관명 가져오기
	 * 
	 * @param 	ccdSubCd 	: 기관 코드
	 * @param 	sch_gubun	: 검색 - 전체:0, 부서 : 1
	 * @return 	Integer : 조직도 기관명 가져오기
	 * @throws	 Exception 
	 */
	public String systemLogCcdSubCd(String sch_gubun, String ccdSubCd) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dept_name = "";
		StringBuffer selectQuery = null;
		try {
    		String rootid = appInfo.getRootid();
			selectQuery = new StringBuffer();
			
			if(sch_gubun.equals("0")){
				selectQuery.append(" SELECT CCDNAME AS DEPT_NAME			\n");
				selectQuery.append(" FROM  CCD                              \n");
				selectQuery.append(" WHERE  CCDCD = '023' AND  CCDSUBCD = ? \n");
			}else if(sch_gubun.equals("1")){
				selectQuery.append(" SELECT  REPLACE(DEPT_FULLNAME, (SELECT DEPT_NAME FROM DEPT WHERE DEPT_ID = '" + rootid + "')||' ', '')  DEPT_NAME  \n"); 	
				selectQuery.append(" FROM  DEPT			                                                                                          		\n");
				selectQuery.append(" WHERE  DEPT_ID = ?		                                                                                          	\n");
			}
			
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, ccdSubCd);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() )
				dept_name = rs.getString(1);
		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return dept_name;
	}
}
