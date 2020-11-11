/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ý��ۿ�����Ʈ �����,�μ����� ���μ���
 * ����:
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
			runInfoLinkQuery(DBConn, "BEFORE");		//�������� �� ����
			
			/**
			 * �μ����� ����
			 * 1. DEPT_TEMP_SSO�� ��� �����Ѵ�
			 * 2. SSOV_DEPT�� DEPT_TEMP_SSO�� �����Ѵ�.
			 * 3. DEPT�� DEPT_TEMP_SSO�� DEPT�� ���Ѵ�.
			 *   - �����μ�: USE_YN = N, CON_YN = Y
			 *   - �űԺμ�: �߰�, ���μ�(�����μ��� ������ MAIN_YN�� 'Y' �ƴϸ� 'N') 
			 *             USE_YN, CON_YN�� ��� 'Y'�� ����, ���(�μ���)���� ����, �������� ����
			 * 4. DEPT�� ��� �����Ѵ�.
			 * 5. DEPT_TEMP_SSO�� DEPT�� �����Ѵ�.
			 * 6. DEPT �����μ��� ������ MAIN_YN�� 'Y' �ƴϸ� 'N' ó��
			 */
			/*******************************************************************************/
			logger.debug(">> �μ�step1: DEPT_TEMP_SSO�� ��� �����Ѵ�");
			/*******************************************************************************/
			OrganizeManager.instance().executeUpdate(DBConn, "DELETE FROM DEPT_TEMP_SSO");

			/*******************************************************************************/
			logger.debug(">> �μ�step2: �����񽺸� ���� DEPT �����͸� DEPT_TEMP_SSO�� �����Ѵ�. (DEPT_RANK�� NULL�ΰ��� ����)");
			/*******************************************************************************/
			createDeptTemp(DBUsrDept, DBConn);
						
			/*******************************************************************************/
			logger.debug(">> �μ�step3: DEPT_TEMP_SSO�� DEPT�� ���Ѵ�.");
			/*******************************************************************************/
			int deptCount = OrganizeManager.instance().chkDelDeptTemp(DBConn);
			if (deptCount > 0) {
				deptCount = OrganizeManager.instance().deleteDelDept(DBConn);
			}
			setLastRunStat("Delete Dept Count : " + deptCount);
			logger.info(getLastRunStat());

			/*******************************************************************************/
			logger.debug(">> �μ�step4: DEPT�� ��� �����Ѵ�.");
			/*******************************************************************************/
			OrganizeManager.instance().executeUpdate(DBConn, "DELETE FROM DEPT \n" +
															 "WHERE NVL(CON_YN, 'Y') = 'Y' \n");
			
			/*******************************************************************************/
			logger.debug(">> �μ�step5: DEPT_TEMP_SSO�� DEPT�� �����Ѵ�.");
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
			logger.debug(">> �μ�step6: DEPT �����μ��� ������ MAIN_YN�� 'Y' �ƴϸ� 'N' ó��");
			/*******************************************************************************/
			//OrganizeManager.instance().executeUpdate(DBConn, "DELETE FROM DEPT_TEMP_SSO");

			//�� �μ��� ���� ����(�μ�,�����)�� üũ�Ͽ� ������ ��� ���μ����� ��
			//if ( !appInfo.isMultipleDelivery() ) {
			//	OrganizeManager.instance().executeUpdate(DBConn,
			//			"UPDATE DEPT \n" +
			//			"SET MAIN_YN = 'N' \n" +
			//			"WHERE DEPT_ID IN (SELECT UPPER_DEPT_ID FROM DEPT WHERE NVL(USE_YN, 'Y') = 'Y') \n");
			//}
			
			/**
			 * ��������� ����
			 * 1. USR_TEMP_SSO�� ��� �����Ѵ�.
			 * 2. SSOV_USER�� USR_TEMP_SSO�� �����Ѵ�.
			 * 3. USR�� ��� �����Ѵ�.
			 * 4. USR_TEMP_SSO�� USR�� �����Ѵ�. 
			 * 5. USR_TEMP_SSO�� USR�� ���Ѵ�.
			 *   - ���������: �ش� ����ڸ� �����Ѵ�.
			 *   - �űԻ����: �߰�, ���(1), ����(0), ���(����ڸ�)���� ����, �������� ����
			 *   - ���������: �����μ��� �ٸ���� �������ڵ常 null�� ó��, �������� ����
			 * 6. ������ϵ�����(GRPLISTDTL) ���̺� �����μ��� ��Ī�� ���ļ��� ����
			 * 7. �Ӽ���ϵ�����(ATTLISTDTL)���̺� LISTCD='00001'�������� ������ ���μ��ΰ͸� �߰�
			 */
			/*******************************************************************************/
			logger.debug(">> �����step1: USR_TEMP_SSO�� ��� �����Ѵ�.");
			/*******************************************************************************/
			OrganizeManager.instance().executeUpdate(DBConn, "DELETE FROM USR_TEMP_SSO");
			
			/*******************************************************************************/
			logger.debug(">> �����step2: �����񽺸� ���� USER�� USR_TEMP_SSO�� �����Ѵ�.");
			/*******************************************************************************/
			createUserTemp(DBUsrDept, DBConn);
			
			// step3 ~ step5
			OrganizeManager.instance().updateWsUserInfo(DBConn, this);
			
			//�μ�, ����� RANK �ϰ�����
			if ( OrganizeManager.instance().updateOrgRank(DBConn) > 0 ) {
				/*******************************************************************************/
				logger.debug(">> �����step6: ������ϵ�����(GRPLISTDTL) �����μ��� ��Ī�� ���ļ��� ����");
				/*******************************************************************************/
				try { OrganizeManager.instance().updateGrpList(DBConn); } catch ( Exception e ) {}
				
				/*******************************************************************************/
				logger.debug(">> �����step7: �Ӽ���ϵ�����(ATTLISTDTL)���̺� LISTCD='00001'�������� ������ ���μ��ΰ͸� �߰�");
				/*******************************************************************************/
				try { OrganizeManager.instance().updateAttList(DBConn); } catch ( Exception e ) {}
			}
			
			DBConn.commit();
			setLastRunStat("RUNNING COMPLETE!!!");
			logger.info(getLastRunStat());
			
			runInfoLinkQuery(DBConn, "AFTER");		//�������� �� ����
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
	 * ���úμ�(SSOV_DEPT)�� DEPT_TEMP_SSO�� ����
	 * @param DBConn
	 * @throws Exception
	 */
	private void createDeptTemp(Connection DBUsrDept, Connection DBConn) throws Exception {
		String sql = null;
		PreparedStatement upstmt=null, pstmt = null;
		ResultSet urs=null;
		
		
        try {
        	
        	//������ ���� �׽�Ʈ
            String ifIdOrg = appInfo.getWsSaeallifid();
            String queryIdDept = appInfo.getWsSaealldeptqueryid();
            String messageKeyOrg =  TaskWsUsrDeptUtil.getMessageKey();
            boolean isEncrypt = appInfo.isWsSaeallEncrypt();
        	
        	//�μ�����
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
	 * ���û����(SSOV_DEPT)�� USR_TEMP_SSO�� ����
	 * @param DBConn
	 * @throws Exception
	 */
	private void createUserTemp(Connection DBUsrDept, Connection DBConn) throws Exception {
		String sql = null;
		PreparedStatement upstmt=null, pstmt = null;
		ResultSet urs=null;
		
		try {
			
			//������ ���� �׽�Ʈ
		    String ifIdOrg = appInfo.getWsSaeallifid();
		    String queryIdUser = appInfo.getWsSaeallusrqueryid();
		    String messageKeyOrg =  TaskWsUsrDeptUtil.getMessageKey();
		    boolean isEncrypt = appInfo.isWsSaeallEncrypt();
			
			//���������
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
				//�λ굿���� �Ʒ� �ּ������ϰ� �������ؾ���
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
				//����������,��õ��,�λ��û(���� ldap)�� �������ѹ��� �α��ξƾƵ� ���
				pstmt.setString(pIndex++, (String)userData.get("logon_id"));
				//�λ굿���� �Ʒ� �ּ������ϰ� �������ؾ���
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
	
	
	//���� �޽��� ������ ���� �ĺ��ڸ� YYYYMMDDHHMMSSsss+������ 8�ڸ� �������� �Է�(�� 25�ڸ�)
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
