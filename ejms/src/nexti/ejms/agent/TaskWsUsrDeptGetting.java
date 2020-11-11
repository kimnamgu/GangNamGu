/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 사용자,부서연계 프로세스
 * 설명:
 */
package nexti.ejms.agent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.organ.model.OrganizeManager;
import nexti.ejms.util.Utils;

import javax.xml.soap.SOAPMessage;

import java.util.List;

public class TaskWsUsrDeptGetting extends TaskBase {
	private static TaskWsUsrDeptGetting _instance;
	public static TaskWsUsrDeptGetting getInstance() {
		if(_instance == null) { _instance = new TaskWsUsrDeptGetting(); }
		return _instance;
	}
	private static Logger logger = Logger.getLogger(TaskWsUsrDeptGetting.class);
	
	private Connection DBConn = null;
	private Connection DBUsrDept = null;
	private boolean isConnect = false;

	private void DBConnection() {
		if(!isConnect) {
			AgentUtil.Disconnection(DBConn);
			DBConn = AgentUtil.getConnection();
			if(DBConn==null) isConnect = false;
			else isConnect = true;
		}
	}
	
	public void run() {
		if(!getIsRun()) return;
		setLastRunDateTime();
		DBConnection();
		if (!isConnect) {
			AgentUtil.AgentlogError(getLSeq());
			return;
		}
		
		PreparedStatement upstmt=null, pstmt = null, pstmt2 = null;;
		ResultSet urs=null,rs = null;
		try {
			runInfoLinkQuery(DBConn, "BEFORE");		//정보연계 전 쿼리
			
			/**
			 * 부서정보 연계
			 * 1. DEPT_TEMP_SSO를 모두 삭제한다
			 * 2. SSOV_DEPT를 DEPT_TEMP_SSO로 복사한다.
			 * 3. DEPT에 DEPT_TEMP_SSO와 DEPT을 비교한다.
			 *   - 삭제부서: USE_YN = N, CON_YN = Y
			 *   - 신규부서: 추가, 대상부서(하위부서가 없으면 MAIN_YN에 'Y' 아니면 'N') 
			 *             USE_YN, CON_YN은 모두 'Y'로 설정, 비고(부서명)으로 적용, 생성정보 적용
			 * 4. DEPT를 모두 삭제한다.
			 * 5. DEPT_TEMP_SSO를 DEPT에 복사한다.
			 * 6. DEPT 하위부서가 없으면 MAIN_YN에 'Y' 아니면 'N' 처리
			 */
			/*******************************************************************************/
			logger.debug(">> 부서step1: DEPT_TEMP_SSO를 모두 삭제한다");
			/*******************************************************************************/
			OrganizeManager.instance().executeUpdate(DBConn, "DELETE FROM DEPT_TEMP_SSO");

			/*******************************************************************************/
			logger.debug(">> 부서step2: 웹서비스를 통한 DEPT 데이터를 DEPT_TEMP_SSO로 복사한다. (DEPT_RANK가 NULL인것은 제외)");
			/*******************************************************************************/
			createDeptTemp(DBUsrDept, DBConn);
						
			/*******************************************************************************/
			logger.debug(">> 부서step3: DEPT_TEMP_SSO와 DEPT를 비교한다.");
			/*******************************************************************************/
			int deptCount = OrganizeManager.instance().chkDelDeptTemp(DBConn);
			if (deptCount > 0) {
				deptCount = OrganizeManager.instance().deleteDelDept(DBConn);
			}
			setLastRunStat("Delete Dept Count : " + deptCount);
			logger.info(getLastRunStat());

			/*******************************************************************************/
			logger.debug(">> 부서step4: DEPT를 모두 삭제한다.");
			/*******************************************************************************/
			OrganizeManager.instance().executeUpdate(DBConn, "DELETE FROM DEPT \n" +
															 "WHERE NVL(CON_YN, 'Y') = 'Y' \n");
			
			/*******************************************************************************/
			logger.debug(">> 부서step5: DEPT_TEMP_SSO를 DEPT에 복사한다.");
			/*******************************************************************************/
			deptCount = OrganizeManager.instance().executeUpdate(DBConn,
					"INSERT INTO DEPT \n" +
	                "SELECT A.DEPT_ID, A.DEPT_NAME, A.DEPT_FULLNAME, A.UPPER_DEPT_ID, A.TOP_DEPT_ID, \n" +
	                "       LEVEL, A.DEPT_RANK, A.DEPT_TEL, A.DEPT_FAX, '001', 'Y', \n" +
	                "       A.USE_YN, A.CON_YN, A.REG_DAY, 'AGENT', A.UPDATE_DAY, 'AGENT' \n" +
	                "FROM DEPT_TEMP_SSO A, DEPT B \n" +
	                "WHERE A.DEPT_ID = B.DEPT_ID(+) \n" +
	                "AND NVL(B.CON_YN, 'Y') = 'Y' \n" +
	                "CONNECT BY PRIOR A.DEPT_ID = A.UPPER_DEPT_ID \n" +
	                "START WITH A.DEPT_ID = '" + appInfo.getRootid() + "' \n");
			
			setLastRunStat("Insert & Update Dept Count : " + deptCount);
			logger.info(getLastRunStat());
			
			/*******************************************************************************/
			logger.debug(">> 부서step6: DEPT 하위부서가 없으면 MAIN_YN에 'Y' 아니면 'N' 처리");
			/*******************************************************************************/
			//OrganizeManager.instance().executeUpdate(DBConn, "DELETE FROM DEPT_TEMP_SSO");

			//각 부서별 하위 조직(부서,사용자)을 체크하여 존재할 경우 대상부서여부 끔
			//if ( !appInfo.isMultipleDelivery() ) {
			//	OrganizeManager.instance().executeUpdate(DBConn,
			//			"UPDATE DEPT \n" +
			//			"SET MAIN_YN = 'N' \n" +
			//			"WHERE DEPT_ID IN (SELECT UPPER_DEPT_ID FROM DEPT WHERE NVL(USE_YN, 'Y') = 'Y') \n");
			//}
			
			/**
			 * 사용자정보 연계
			 * 1. USR_TEMP_SSO를 모두 삭제한다.
			 * 2. SSOV_USER를 USR_TEMP_SSO에 복사한다.
			 * 3. USR을 모두 삭제한다.
			 * 4. USR_TEMP_SSO를 USR에 복사한다. 
			 * 5. USR_TEMP_SSO와 USR을 비교한다.
			 *   - 삭제사용자: 해당 사용자를 삭제한다.
			 *   - 신규사용자: 추가, 비번(1), 관리(0), 비고(사용자명)으로 적용, 생성정보 적용
			 *   - 기존사용자: 기존부서와 다를경우 담당단위코드만 null로 처리, 수정정보 적용
			 * 6. 배포목록디테일(GRPLISTDTL) 테이블에 기존부서는 명칭및 정렬순서 적용
			 * 7. 속성목록디테일(ATTLISTDTL)테이블에 LISTCD='00001'조건으로 삭제후 대상부서인것만 추가
			 */
			/*******************************************************************************/
			logger.debug(">> 사용자step1: USR_TEMP_SSO를 모두 삭제한다.");
			/*******************************************************************************/
			OrganizeManager.instance().executeUpdate(DBConn, "DELETE FROM USR_TEMP_SSO");
			
			/*******************************************************************************/
			logger.debug(">> 사용자step2: 웹서비스를 통한 USER를 USR_TEMP_SSO에 복사한다.");
			/*******************************************************************************/
			createUserTemp(DBUsrDept, DBConn);
			
			// step3 ~ step5
			OrganizeManager.instance().updateWsUserInfo(DBConn, this);
			
			//부서, 사용자 RANK 일괄변경
			if ( OrganizeManager.instance().updateOrgRank(DBConn) > 0 ) {
				/*******************************************************************************/
				logger.debug(">> 사용자step6: 배포목록디테일(GRPLISTDTL) 기존부서는 명칭및 정렬순서 적용");
				/*******************************************************************************/
				try { OrganizeManager.instance().updateGrpList(DBConn); } catch ( Exception e ) {}
				
				/*******************************************************************************/
				logger.debug(">> 사용자step7: 속성목록디테일(ATTLISTDTL)테이블에 LISTCD='00001'조건으로 삭제후 대상부서인것만 추가");
				/*******************************************************************************/
				try { OrganizeManager.instance().updateAttList(DBConn); } catch ( Exception e ) {}
			}
			
			DBConn.commit();
			setLastRunStat("RUNNING COMPLETE!!!");
			logger.info(getLastRunStat());
			
			runInfoLinkQuery(DBConn, "AFTER");		//정보연계 후 쿼리
		} catch (Exception e) {
			try { DBConn.rollback(); } catch (Exception ex) { }
			AgentUtil.AgentlogError(getLSeq());
			setLastRunStat("error: "+ e);
			logger.error(getLastRunStat(), e);
		} finally {
			try { if (rs != null) { rs.close(); } } catch (Exception ex) { }
			try { if (pstmt != null) { pstmt.close(); } } catch (Exception ex) { }
			try { if (pstmt2 != null) { pstmt2.close(); } } catch (Exception ex) { }
			try { if (DBConn != null) { DBConn.close(); } } catch (Exception ex) { }
			try { if (urs != null) { urs.close(); } } catch (Exception ex) { }
			try { if (upstmt != null) { upstmt.close(); } } catch (Exception ex) { }
			try { if (DBUsrDept != null) { DBUsrDept.close(); } } catch (Exception ex) { }
			isConnect = false;
		}
	}
	
	private void runInfoLinkQuery(Connection DBConn, String type) {
		PreparedStatement pstmt = null;
		
		try {
			if ( appInfo.isInfoLinkQuery() != true ) return;
			
			DBConn.commit();
			
			String[] query = null;
			if ( "BEFORE".equals(type) ) {
				query = appInfo.getInfoLinkQuery_BeforeQuery();
			} else if ( "AFTER".equals(type) ) {
				query = appInfo.getInfoLinkQuery_AfterQuery();
			} 
			
			for ( int i = 0; query != null && i < query.length; i++ ) {
				pstmt = DBConn.prepareStatement(query[i]);
				pstmt.executeUpdate();
			}
			
			DBConn.commit();
			logger.info("InfoLinkQuery" + ":" + type + " RUNNING COMPLETE!!!");
		} catch ( Exception e ) {
			try { DBConn.rollback(); } catch (Exception ex) {}
			logger.error("ERROR : runInfoLinkQuery()" + ":" + type, e);
		} finally {
			ConnectionManager.close(pstmt);
		}
	}

	/**
	 * 새올부서(SSOV_DEPT)를 DEPT_TEMP_SSO로 복사
	 * @param DBConn
	 * @throws Exception
	 */
	private void createDeptTemp(Connection DBUsrDept, Connection DBConn) throws Exception {
		String sql = null;
		PreparedStatement upstmt=null, pstmt = null;
		ResultSet urs=null;
		
		
        try {
        	
        	//조직도 연계 테스트
            String ifIdOrg = appInfo.getWsSaeallifid();
            String queryIdDept = appInfo.getWsSaealldeptqueryid();
            String messageKeyOrg =  TaskWsUsrDeptUtil.getMessageKey();
            boolean isEncrypt = appInfo.isWsSaeallEncrypt();
        	
        	//부서정보
            SOAPMessage resSOAPMessageDept = 
            	TaskWsUsrDeptUtil.sendRequestMessage(ifIdOrg, queryIdDept, messageKeyOrg, isEncrypt);
            
            List resultListDept = 
            	TaskWsUsrDeptUtil.parseData(resSOAPMessageDept, messageKeyOrg, isEncrypt);
            
            //List resultListDept = 
            //	TaskWsUsrDeptUtil.parseDataTestDept();
            
            int resultListDeptSize = resultListDept.size();
            
			sql = "INSERT INTO DEPT_TEMP_SSO (DEPT_ID, BASE_SYS, DEPT_NAME, DEPT_FULLNAME, UPPER_DEPT_ID, \n" +
			      "       TOP_DEPT_ID, ORG_DIV_ID, DEPT_ORDER, DEPT_RANK, DEPT_TEL, \n" +
			      "       DEPT_FAX, DEPT_SE,  APPLY_DAY,  REG_DAY, UPDATE_DAY, \n" +
			      "		  USE_YN, CON_YN) \n" +
			      "VALUES (?,?,?,?,?, ?,?,?,?,?, \n" +
			      "        ?,?,?,?,?, ?,?) \n";
			
			pstmt = DBConn.prepareStatement(sql);
			int pIndex=0;
			
			logger.info("resultListDeptSize = "+resultListDeptSize);
			
			for(int i = 0; i < resultListDeptSize; i++){
				Map deptData = (HashMap)resultListDept.get(i);
				
				
				
				
				pstmt.clearParameters();
				pIndex=1;
				/*
				pstmt.setString(pIndex++, (String)deptData.get("dep_code"));
				pstmt.setString(pIndex++, "");
				pstmt.setString(pIndex++, (String)deptData.get("dep_code_nm"));
				pstmt.setString(pIndex++, (String)deptData.get("dep_full_nm"));
				pstmt.setString(pIndex++, (String)deptData.get("upr_dept_code"));
				pstmt.setString(pIndex++, (String)deptData.get("sprm_dept_code"));
				pstmt.setString(pIndex++, (String)deptData.get("org_no"));
				pstmt.setString(pIndex++, (String)deptData.get("dept_seq"));	
				String deptrank = Utils.nullToEmptyString((String)deptData.get("dept_rank")).trim();
				if ( deptrank.trim().equals("") ) {
					pstmt.setString(pIndex++, "99999");
				} else {
					pstmt.setString(pIndex++, deptrank);
				}
				pstmt.setString(pIndex++, (String)deptData.get("rep_tel_no"));
				pstmt.setString(pIndex++, (String)deptData.get("rep_fax_no"));
				pstmt.setString(pIndex++, (String)deptData.get("dept_se"));
				pstmt.setString(pIndex++, (String)deptData.get("esb_ymd"));
				pstmt.setString(pIndex++, "");
				pstmt.setString(pIndex++, (String)deptData.get("mod_dt"));				
				pstmt.setString(pIndex++, "Y");
				pstmt.setString(pIndex++, "Y");
				*/
				
				pstmt.setString(pIndex++, (String)deptData.get("dept_id"));
				pstmt.setString(pIndex++, (String)deptData.get("base_sys"));
				pstmt.setString(pIndex++, (String)deptData.get("dept_name"));
				pstmt.setString(pIndex++, (String)deptData.get("dept_fullnm"));
				pstmt.setString(pIndex++, (String)deptData.get("upper_dept_id"));
				pstmt.setString(pIndex++, (String)deptData.get("top_dept_id"));
				pstmt.setString(pIndex++, (String)deptData.get("org_div_id"));
				pstmt.setString(pIndex++, (String)deptData.get("dept_order"));	
				String deptrank = Utils.nullToEmptyString((String)deptData.get("dept_rank")).trim();
				if ( deptrank.trim().equals("") ) {
					pstmt.setString(pIndex++, "99999");
				} else {
					pstmt.setString(pIndex++, deptrank);
				}
				pstmt.setString(pIndex++, (String)deptData.get("dept_tel"));
				pstmt.setString(pIndex++, (String)deptData.get("dept_fax"));
				pstmt.setString(pIndex++, (String)deptData.get("dept_se"));
				pstmt.setString(pIndex++, (String)deptData.get("apply_day"));
				pstmt.setString(pIndex++, (String)deptData.get("reg_day"));
				pstmt.setString(pIndex++, (String)deptData.get("update_day"));				
				pstmt.setString(pIndex++, "Y");
				pstmt.setString(pIndex++, "Y");
				pstmt.executeUpdate();
				
			}
		} finally {
			try { urs.close(); } catch (Exception e) { }
			try { upstmt.close(); } catch (Exception e) { }
			try { pstmt.close(); } catch (Exception e) { }
		}
	}

	/**
	 * 새올사용자(SSOV_DEPT)를 USR_TEMP_SSO로 복사
	 * @param DBConn
	 * @throws Exception
	 */
	private void createUserTemp(Connection DBUsrDept, Connection DBConn) throws Exception {
		String sql = null;
		PreparedStatement upstmt=null, pstmt = null;
		ResultSet urs=null;
		
		try {
			
			//조직도 연계 테스트
		    String ifIdOrg = appInfo.getWsSaeallifid();
		    String queryIdUser = appInfo.getWsSaeallusrqueryid();
		    String messageKeyOrg =  TaskWsUsrDeptUtil.getMessageKey();
		    boolean isEncrypt = appInfo.isWsSaeallEncrypt();
			
			//사용자정보
	        SOAPMessage resSOAPMessageUser = 
	        	TaskWsUsrDeptUtil.sendRequestMessage(ifIdOrg, queryIdUser, messageKeyOrg, isEncrypt);
	        
	        List resultListUser = 
	        	TaskWsUsrDeptUtil.parseData(resSOAPMessageUser, messageKeyOrg, isEncrypt);
	        
	        //List resultListUser = 
	        //	TaskWsUsrDeptUtil.parseDataTestUser();
	        
	        int resultListUserSize = resultListUser.size();
	        
			sql = "INSERT INTO USR_TEMP_SSO (USER_ID, USER_NAME, USER_SN, USER_STAT, USER_STAT_NAME, \n" +
				  "                      	 REGULARITY, ORG_ID, ORG_NAME, DEPT_ID, DEPT_NAME, \n" +
				  "                      	 DEPT_FULLNAME, CLASS_ID, CLASS_NAME, POSITION_ID, POSITION_NAME, \n" +
				  "                      	 GRADE_ID, GRADE_NAME, EMAIL, TEL, MOBILE, \n" +
				  "                      	 JOIN_DAY, RETIRE_DAY, ADD_INFO1, ADD_INFO2, ADD_INFO3, \n" +
				  "                      	 ADD_INFO4, ADD_INFO5, ADD_INFO6, ADD_INFO7, BASE_SYS, \n" +
				  "                      	 REG_DAY, UPDATE_DAY, USE_YN, CON_YN, RDUTY_NAME) \n" +
			      "VALUES (?,?,?,?,?, ?,?,?,?,?, \n" +
			      "        ?,?,?,?,?, ?,?,?,?,?, \n" +
			      "        ?,?,?,?,?, ?,?,?,?,?, \n" +
			      "        ?,?,?,?,?) \n";
			pstmt = DBConn.prepareStatement(sql);
			int pIndex=0;
			
			//logger.info("resultListUserSize = "+resultListUserSize);
			
			
			for(int i = 0; i < resultListUserSize; i++){
				Map userData = (HashMap)resultListUser.get(i);
				
				pstmt.clearParameters();
				pIndex=1;
				/*
				pstmt.setString(pIndex++, (String)userData.get("logon_id"));
				pstmt.setString(pIndex++, (String)userData.get("usr_nm"));
				pstmt.setString(pIndex++, "");
				//부산동구는 아래 주석제거하고 컴파일해야함
				//pstmt.setString(pIndex++, com.api.crypt.dec(Utils.nullToEmptyString(urs.getString("USER_SN"))));
				pstmt.setString(pIndex++, (String)userData.get("usr_work_state_code"));
				pstmt.setString(pIndex++, (String)userData.get("usr_work_state_code_nm"));
				pstmt.setString(pIndex++, (String)userData.get("engm_se"));
				pstmt.setString(pIndex++, (String)userData.get("org_no"));
				pstmt.setString(pIndex++, (String)userData.get("org_code_nm"));
				pstmt.setString(pIndex++, (String)userData.get("real_use_dep_code"));
				pstmt.setString(pIndex++, (String)userData.get("real_use_dep_nm"));
				pstmt.setString(pIndex++, (String)userData.get("real_use_dep_full_nm"));
				pstmt.setString(pIndex++, (String)userData.get("clss_no"));
				pstmt.setString(pIndex++, (String)userData.get("clss_nm"));
				pstmt.setString(pIndex++, (String)userData.get("posit_code"));
				pstmt.setString(pIndex++, (String)userData.get("posit_nm"));
				pstmt.setString(pIndex++, Utils.nullToEmptyString((String)userData.get("grade_no")).trim());
				pstmt.setString(pIndex++, (String)userData.get("grade_nm"));
				pstmt.setString(pIndex++, (String)userData.get("email_addr"));
				pstmt.setString(pIndex++, (String)userData.get("telno"));
				pstmt.setString(pIndex++, (String)userData.get("hpno"));
				pstmt.setString(pIndex++, (String)userData.get("etcom_ymd"));
				pstmt.setString(pIndex++, (String)userData.get("rtrmnt_ymd"));
				pstmt.setString(pIndex++, (String)userData.get("adi_info1"));
				pstmt.setString(pIndex++, (String)userData.get("adi_info2"));
				pstmt.setString(pIndex++, (String)userData.get("adi_info3"));
				pstmt.setString(pIndex++, (String)userData.get("adi_info4"));
				pstmt.setString(pIndex++, (String)userData.get("adi_info5"));
				pstmt.setString(pIndex++, (String)userData.get("adi_info6"));
				pstmt.setString(pIndex++, (String)userData.get("adi_info7"));
				pstmt.setString(pIndex++, "");	//BASE_SYS
				pstmt.setString(pIndex++, (String)userData.get("cre_dt"));
				pstmt.setString(pIndex++, (String)userData.get("mod_dt"));
				pstmt.setString(pIndex++, "Y");
				pstmt.setString(pIndex++, "Y");
				pstmt.setString(pIndex++, "");	//RDUTY_NAME
				*/
				
				pstmt.setString(pIndex++, (String)userData.get("logon_id"));
				pstmt.setString(pIndex++, (String)userData.get("user_name"));
				//pstmt.setString(pIndex++, "");
				//강원도원주,인천시,부산시청(현재 ldap)는 시퀀스넘버로 로그인아아디 사용
				pstmt.setString(pIndex++, (String)userData.get("logon_id"));
				//부산동구는 아래 주석제거하고 컴파일해야함
				//pstmt.setString(pIndex++, com.api.crypt.dec(Utils.nullToEmptyString(urs.getString("USER_SN"))));
				pstmt.setString(pIndex++, (String)userData.get("user_stat"));
				pstmt.setString(pIndex++, (String)userData.get("user_stat_name"));
				pstmt.setString(pIndex++, (String)userData.get("regularity"));
				pstmt.setString(pIndex++, (String)userData.get("org_id"));
				pstmt.setString(pIndex++, (String)userData.get("org_name"));
				pstmt.setString(pIndex++, (String)userData.get("dept_id"));
				pstmt.setString(pIndex++, (String)userData.get("dept_name"));
				pstmt.setString(pIndex++, (String)userData.get("dept_fullname"));
				pstmt.setString(pIndex++, (String)userData.get("class_id"));
				pstmt.setString(pIndex++, (String)userData.get("class_name"));
				pstmt.setString(pIndex++, (String)userData.get("position_id"));
				pstmt.setString(pIndex++, (String)userData.get("position_name"));
				pstmt.setString(pIndex++, Utils.nullToEmptyString((String)userData.get("grade_id")).trim());
				pstmt.setString(pIndex++, (String)userData.get("grade_name"));
				pstmt.setString(pIndex++, (String)userData.get("email"));
				pstmt.setString(pIndex++, (String)userData.get("tel"));
				pstmt.setString(pIndex++, (String)userData.get("mobile"));
				pstmt.setString(pIndex++, (String)userData.get("join_day"));
				pstmt.setString(pIndex++, (String)userData.get("retire_day"));
				pstmt.setString(pIndex++, (String)userData.get("add_info1"));
				pstmt.setString(pIndex++, (String)userData.get("add_info2"));
				pstmt.setString(pIndex++, (String)userData.get("add_info3"));
				pstmt.setString(pIndex++, (String)userData.get("add_info4"));
				pstmt.setString(pIndex++, (String)userData.get("add_info5"));
				pstmt.setString(pIndex++, (String)userData.get("add_info6"));
				pstmt.setString(pIndex++, (String)userData.get("add_info7"));
				pstmt.setString(pIndex++, (String)userData.get("base_sys"));	//BASE_SYS
				pstmt.setString(pIndex++, (String)userData.get("reg_day"));
				pstmt.setString(pIndex++, (String)userData.get("update_day"));
				pstmt.setString(pIndex++, "Y");
				pstmt.setString(pIndex++, "Y");
				pstmt.setString(pIndex++, (String)userData.get("rduty_name"));	//RDUTY_NAME
				pstmt.executeUpdate();
			}

		} finally {
			try { urs.close(); } catch (Exception e) { }
			try { upstmt.close(); } catch (Exception e) { }
			try { pstmt.close(); } catch (Exception e) { }
		}
	}
	
	
	//연계 메시지 구분을 위한 식별자를 YYYYMMDDHHMMSSsss+랜덤값 8자리 형식으로 입력(총 25자리)
	public static String getMessageKey(){
		String DATE_FORMAT = "yyyyMMddHHmmssSSS";
		SimpleDateFormat sdf = new SimpleDateFormat( DATE_FORMAT );
		Calendar cal = Calendar.getInstance();

		Random oRandom = new Random();

		long rndValue = 0;

		while(true){
			rndValue = Math.abs( oRandom.nextInt()*1000000 );
			if(String.valueOf(rndValue).length()==8)
				break;
		}

		return sdf.format( cal.getTime() )+rndValue;
	}
}
