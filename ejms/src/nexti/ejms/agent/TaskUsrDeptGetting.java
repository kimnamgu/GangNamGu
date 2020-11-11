/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ý��ۿ�����Ʈ �����,�μ����� ���μ���
 * ����:
 */
package nexti.ejms.agent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.organ.model.OrganizeManager;
import nexti.ejms.util.Utils;

public class TaskUsrDeptGetting extends TaskBase {
	private static TaskUsrDeptGetting _instance;
	public static TaskUsrDeptGetting getInstance() {
		if(_instance == null) { _instance = new TaskUsrDeptGetting(); }
		return _instance;
	}
	private static Logger logger = Logger.getLogger(TaskUsrDeptGetting.class);
	
	private Connection DBConn = null;
	private Connection DBUsrDept = null;
	private boolean isUsrDeptConnect = false;
	private boolean isConnect = false;

	private void DBConnection() {
		if(!isConnect) {
			AgentUtil.Disconnection(DBConn);
			DBConn = AgentUtil.getConnection();
			if(DBConn==null) isConnect = false;
			else isConnect = true;
		}
		if(!isUsrDeptConnect) {
			try { if (DBUsrDept != null) { DBUsrDept.close(); } } catch (Exception ex) { }
			try {
				Class.forName(appInfo.getUsrdeptDbtype());
				DBUsrDept = DriverManager.getConnection(appInfo.getUsrdeptDbip(), appInfo.getUsrdeptDbuser(), appInfo.getUsrdeptDbpass());
				isUsrDeptConnect = true;
			} catch (Exception e) {
				isUsrDeptConnect = false;
				setLastRunStat("UsrDept database connection error:" + e);
				logger.error(getLastRunStat());				
			}
		}
	}
	
	public void run() {
		if(!getIsRun()) return;
		setLastRunDateTime();
		DBConnection();
		if (!isUsrDeptConnect) {
			AgentUtil.AgentlogError(getLSeq());
			return;
		}
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
			logger.debug(">> �μ�step2: SSOV_DEPT�� DEPT_TEMP_SSO�� �����Ѵ�. (DEPT_RANK�� NULL�ΰ��� ����)");
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
			logger.debug(">> �����step2: SSOV_USER�� USR_TEMP_SSO�� �����Ѵ�.");
			/*******************************************************************************/
			createUserTemp(DBUsrDept, DBConn);
			
			// step3 ~ step5
			OrganizeManager.instance().updateUserInfo(DBConn, this);
			
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
			isUsrDeptConnect = false;
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
			//USE_YN �÷��� �ִ��� �ľ�
			boolean existUSEYN = true;
			try {
				sql = "SELECT USE_YN FROM " + appInfo.getUsrdeptDeptTable() + " WHERE ROWNUM = 0";
				upstmt = DBUsrDept.prepareStatement(sql);
				upstmt.executeQuery();
			} catch ( Exception e ) {
				existUSEYN = false;
			} finally {
				try { upstmt.close(); } catch (Exception e) {}
			}
			
			sql = "SELECT * \n" +
				  "FROM " + appInfo.getUsrdeptDeptTable() + " \n" +
				  "WHERE DEPT_ID NOT IN (SELECT DEPT_ID \n" +
				  "                      FROM " + appInfo.getUsrdeptDeptTable() + " \n" +
				  "                      GROUP BY DEPT_ID \n" +
				  "                      HAVING COUNT(DEPT_ID) > 1) \n" +
				  "AND (UPPER_DEPT_ID IN (SELECT DEPT_ID FROM " + appInfo.getUsrdeptDeptTable() + ") \n" +
				  "     OR DEPT_ID = '" + appInfo.getRootid() + "') \n";
			upstmt = DBUsrDept.prepareStatement(sql);
			urs = upstmt.executeQuery();
			
			sql = "INSERT INTO DEPT_TEMP_SSO (DEPT_ID, BASE_SYS, DEPT_NAME, DEPT_FULLNAME, UPPER_DEPT_ID, \n" +
			      "       TOP_DEPT_ID, ORG_DIV_ID, DEPT_ORDER, DEPT_RANK, DEPT_TEL, \n" +
			      "       DEPT_FAX, DEPT_SE,  APPLY_DAY,  REG_DAY, UPDATE_DAY, \n" +
			      "		  USE_YN, CON_YN) \n" +
			      "VALUES (?,?,?,?,?, ?,?,?,?,?, \n" +
			      "        ?,?,?,?,?, ?,?) \n";
			pstmt = DBConn.prepareStatement(sql);
			int pIndex=0;
			while(urs.next()) {
				pstmt.clearParameters();
				pIndex=1;
				pstmt.setString(pIndex++, urs.getString("DEPT_ID"));
				pstmt.setString(pIndex++, urs.getString("BASE_SYS"));
				pstmt.setString(pIndex++, urs.getString("DEPT_NAME"));
				pstmt.setString(pIndex++, urs.getString("DEPT_FULLNAME"));
				pstmt.setString(pIndex++, urs.getString("UPPER_DEPT_ID"));
				pstmt.setString(pIndex++, urs.getString("TOP_DEPT_ID"));
				pstmt.setString(pIndex++, urs.getString("ORG_DIV_ID"));
				pstmt.setString(pIndex++, urs.getString("DEPT_ORDER"));	
				String deptrank = Utils.nullToEmptyString(urs.getString("DEPT_RANK")).trim();
				if ( deptrank.trim().equals("") ) {
					pstmt.setString(pIndex++, "99999");
				} else {
					pstmt.setString(pIndex++, deptrank);
				}
				pstmt.setString(pIndex++, urs.getString("DEPT_TEL"));
				pstmt.setString(pIndex++, urs.getString("DEPT_FAX"));
				pstmt.setString(pIndex++, urs.getString("DEPT_SE"));
				pstmt.setString(pIndex++, urs.getString("APPLY_DAY"));
				pstmt.setString(pIndex++, urs.getString("REG_DAY"));
				pstmt.setString(pIndex++, urs.getString("UPDATE_DAY"));				
				if ( existUSEYN == true ) {
					if ( urs.getString("USE_YN").equals("0") || urs.getString("USE_YN").equals("N") ) {
						pstmt.setString(pIndex++, "N");
					} else {
						pstmt.setString(pIndex++, "Y");
					}
				} else {
					pstmt.setString(pIndex++, "Y");
				}
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
			//USE_YN �÷��� �ִ��� �ľ�
			boolean existUSEYN = true;
			try {
				sql = "SELECT USE_YN FROM " + appInfo.getUsrdeptUserTable() + " WHERE ROWNUM = 0";
				upstmt = DBUsrDept.prepareStatement(sql);
				upstmt.executeQuery();
			} catch ( Exception e ) {
				existUSEYN = false;
			} finally {
				try { upstmt.close(); } catch (Exception e) { }
			}
			
			sql = "SELECT \n" +
//db��ȣȭ �����
				  " FC_CMM_DEC( USER_SN, 'ICA', '1', 'T' ) USER_SN \n" +
//db��ȣȭ �������
//				  " USER_SN \n" +				  
		    	  " ,USER_ID,USER_NAME,USER_STAT,USER_STAT_NAME,REGULARITY,ORG_ID,ORG_NAME,DEPT_ID,DEPT_NAME,DEPT_FULLNAME \n" +
	    	      " ,CLASS_ID,CLASS_NAME,POSITION_ID,POSITION_NAME,GRADE_ID,GRADE_NAME,EMAIL,TEL,MOBILE,JOIN_DAY,RETIRE_DAY \n" +
	    	      " ,ADD_INFO1,ADD_INFO2,ADD_INFO3,ADD_INFO4,ADD_INFO5,ADD_INFO6,ADD_INFO7,REG_DAY,UPDATE_DAY,USE_YN \n" +
				  " FROM " + appInfo.getUsrdeptUserTable() + " \n" +
				  " WHERE USER_ID NOT IN (SELECT USER_ID \n" +
				  "                      FROM " + appInfo.getUsrdeptUserTable() + " \n" +
				  "                      GROUP BY USER_ID \n" +
				  "                      HAVING COUNT(USER_ID) > 1) \n";
//			logger.info("ntis query : " + sql);
			upstmt = DBUsrDept.prepareStatement(sql);
			urs = upstmt.executeQuery();
			
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
			
			while(urs.next()) {
				pstmt.clearParameters();
				pIndex=1;
				pstmt.setString(pIndex++, urs.getString("USER_ID"));
				pstmt.setString(pIndex++, urs.getString("USER_NAME"));
				//pstmt.setString(pIndex++, "");
				pstmt.setString(pIndex++, Utils.nullToEmptyString(urs.getString("USER_SN")));
				//���������ֽ�,��õ��,�λ��û(���� ldap) USER_SN �� USER_ID�� ���
				//pstmt.setString(pIndex++, urs.getString("USER_ID"));
				//�λ굿���� �Ʒ� �ּ������ϰ� �������ؾ���
				//pstmt.setString(pIndex++, com.api.crypt.dec(Utils.nullToEmptyString(urs.getString("USER_SN"))));
				pstmt.setString(pIndex++, urs.getString("USER_STAT"));
				pstmt.setString(pIndex++, urs.getString("USER_STAT_NAME"));
				pstmt.setString(pIndex++, urs.getString("REGULARITY"));
				pstmt.setString(pIndex++, urs.getString("ORG_ID"));
				pstmt.setString(pIndex++, urs.getString("ORG_NAME"));
				pstmt.setString(pIndex++, urs.getString("DEPT_ID"));
				pstmt.setString(pIndex++, urs.getString("DEPT_NAME"));
				pstmt.setString(pIndex++, urs.getString("DEPT_FULLNAME"));
				pstmt.setString(pIndex++, urs.getString("CLASS_ID"));
				pstmt.setString(pIndex++, urs.getString("CLASS_NAME"));
				pstmt.setString(pIndex++, urs.getString("POSITION_ID"));
				pstmt.setString(pIndex++, urs.getString("POSITION_NAME"));
				pstmt.setString(pIndex++, Utils.nullToEmptyString(urs.getString("GRADE_ID")).trim());
				pstmt.setString(pIndex++, urs.getString("GRADE_NAME"));
				pstmt.setString(pIndex++, urs.getString("EMAIL"));
				pstmt.setString(pIndex++, urs.getString("TEL"));
				pstmt.setString(pIndex++, urs.getString("MOBILE"));
				pstmt.setString(pIndex++, urs.getString("JOIN_DAY"));
				pstmt.setString(pIndex++, urs.getString("RETIRE_DAY"));
				pstmt.setString(pIndex++, urs.getString("ADD_INFO1"));
				pstmt.setString(pIndex++, urs.getString("ADD_INFO2"));
				pstmt.setString(pIndex++, urs.getString("ADD_INFO3"));
				pstmt.setString(pIndex++, urs.getString("ADD_INFO4"));
				pstmt.setString(pIndex++, urs.getString("ADD_INFO5"));
				pstmt.setString(pIndex++, urs.getString("ADD_INFO6"));
				pstmt.setString(pIndex++, urs.getString("ADD_INFO7"));
				pstmt.setString(pIndex++, "");	//BASE_SYS
				pstmt.setString(pIndex++, urs.getString("REG_DAY"));
				pstmt.setString(pIndex++, urs.getString("UPDATE_DAY"));
				if ( existUSEYN == true ) {					
					if ( urs.getString("USE_YN").equals("0") || urs.getString("USE_YN").equals("N") ) {
						pstmt.setString(pIndex++, "N");
					} else {
						pstmt.setString(pIndex++, "Y");
					}
				} else {
					pstmt.setString(pIndex++, "Y");
				}
				if ( appInfo.getUsrdeptUserstat().indexOf(Utils.nullToEmptyString(urs.getString("USER_STAT"))) == -1
						|| appInfo.getUsrdeptRegularity().indexOf(Utils.nullToEmptyString(urs.getString("REGULARITY"))) == -1 ) {
					pstmt.setString(pIndex - 1, "N");
				}
				pstmt.setString(pIndex++, "Y");
				pstmt.setString(pIndex++, "");	//RDUTY_NAME
				pstmt.executeUpdate();
			}
		} finally {
			try { urs.close(); } catch (Exception e) { }
			try { upstmt.close(); } catch (Exception e) { }
			try { pstmt.close(); } catch (Exception e) { }
		}
	}
}
