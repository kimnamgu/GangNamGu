package nexti.ejms.agent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import nexti.ejms.agent.model.SidoLdapBean;
import nexti.ejms.agent.model.SidoLdapManager;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.organ.model.OrganizeManager;

import org.apache.log4j.Logger;

public class TaskSidoLdapGetting extends TaskBase {
	private static TaskSidoLdapGetting _instance;
	public static TaskSidoLdapGetting getInstance() {
		if(_instance == null) { _instance = new TaskSidoLdapGetting(); }
		return _instance;
	}
	private static Logger logger = Logger.getLogger(TaskSidoLdapGetting.class);
	
	private Connection DBConn = null;
	private Connection DBSido = null;
	private boolean isSidoLdapConnect = false;
	private boolean isConnect = false;

	private void DBConnection() {
		if(!isConnect) {
			AgentUtil.Disconnection(DBConn);
			DBConn = AgentUtil.getConnection();
			if(DBConn==null) isConnect = false;
			else isConnect = true;
		}
		if(!isSidoLdapConnect) {
			try { if (DBSido != null) { DBSido.close(); } } catch (Exception ex) { }
			try {
				Class.forName(appInfo.getSidoldapDbtype());
				DBSido = DriverManager.getConnection(appInfo.getSidoldapDbip(), appInfo.getSidoldapDbuser(), appInfo.getSidoldapDbpass());
				isSidoLdapConnect = true;
			} catch (Exception e) {
				isSidoLdapConnect = false;
				setLastRunStat("SidoLdap database connection error:" + e);
				logger.error(getLastRunStat());				
			}
		}
	}
	
	public void run() {
		if(!getIsRun()) return;
		setLastRunDateTime();
		DBConnection();
		
		if (!isSidoLdapConnect) {
			AgentUtil.AgentlogError(getLSeq());
			return;
		}
		if (!isConnect) {
			AgentUtil.AgentlogError(getLSeq());
			return;
		}

		List sidoLdapList = null;
		int changeSidoLDAP1 = 0;
		int changeSidoLDAP2 = 0;
		int sidoUpdate = 0;
		
		try {
			runInfoLinkQuery(DBConn, "BEFORE");		//�������� �� ����
			
			/**
			 * 1. �õ�LDAP ����ȭ �����������̺��� ����ȭ ������ ��������.
			 *    1) ����ȭ�����������̺��� �������� �ݿ��� ������ ���ڿ� ������ �����´�.
			 *    2) LOG_SEQ �ִ���� üũ
			 *    3) ����ȭ������ �������������� ���泻�뿡 ���� �Ͻ� ���� ����ڱ��� ��������� �����´�.
			 * 2. ����� LDAP����ȭ������ ����������� �μ����� ���̺� ���� - [��]LDAP���������(�����), [��]�μ������ӽ�(�����)   
			 * 	  1) ����ڱ��� 0:����� 1:���� -- INSERT, UPDATE
			 * 2. ����� LDAP����ȭ������ ����������� �μ����� ���̺� ����- [��]���������, [��]�μ�����)    
			 * 	  1) ����ڱ��� 0:����� 1:���� -- INSERT, UPDATE
			 * 4. LDAP����ȭ�������� ���̺� ���� ������׹ݿ� - �Ͻ� �� ���� ������Ʈ
			 * 	  1) �������� ����
			 */
			
			OrganizeManager.instance().executeUpdate(DBConn,
					"INSERT INTO USR_TEMP_LDAP(RESINUMBER) \n" +
					"SELECT USER_SN FROM USR WHERE USER_SN IS NOT NULL \n" +
					"MINUS SELECT RESINUMBER FROM USR_TEMP_LDAP \n");
			
			OrganizeManager.instance().executeUpdate(DBConn,
					"INSERT INTO DEPT_TEMP_LDAP(OUCODE) \n" +
					"SELECT DEPT_ID FROM DEPT \n" +
					"MINUS SELECT OUCODE FROM DEPT_TEMP_LDAP \n");
			
			DBConn.commit();

			SidoLdapManager manager = SidoLdapManager.instance();
			
			logger.debug("step~1: sidoLdapList");
			//LDA����ȭ�������� ���̺��� ���� �̷��� üũ�Ͽ� ����� ������ ������
			// [1]
			sidoLdapList = manager.getSidoLdapList(DBSido);

			if ( sidoLdapList.size() > 0 ) {
				setLastRunStat("��ü�������� : �����/�μ�(" + sidoLdapList.size() + "), ����������...");
				logger.info(getLastRunStat());
				
				logger.debug("step~2: changeSidoLDAP1");
				//����� LDAP����ȭ������ ����������� �μ����� ���̺� ���� - [��]LDAP���������(�����), [��]�μ������ӽ�(�����)
				// [2]
				changeSidoLDAP1 = manager.changeSidoLDAP1(sidoLdapList);
				logger.info("������������ "+changeSidoLDAP1);
				//����������� �μ����� ���̺� -> ����������� �μ����� ���̺� ���� - [��]���������, [��]�μ�����)    
				if ( changeSidoLDAP1 > 0 ) {
					// [3]
					changeSidoLDAP2 = manager.changeSidoLDAP2();
				}
				logger.info("������������ a");
				//LDAP����ȭ�������� ���̺� ����ȭ �̷� ����
				// [4]
				sidoUpdate = manager.sidoUpdate(DBSido, (SidoLdapBean)sidoLdapList.get(sidoLdapList.size()-1));
				logger.info("������������ b");
				if ( changeSidoLDAP2 > 0 && sidoUpdate > 0 ) {
					setLastRunStat("COMPLETE(�̷�O/����O UPDATE:" + sidoLdapList.size() + "[" + changeSidoLDAP1 + "])");
				} else if ( changeSidoLDAP2 > 0 && sidoUpdate == 0 ) {
					setLastRunStat("COMPLETE(�̷�X/����O UPDATE:" + sidoLdapList.size() + "[" + changeSidoLDAP1 + "])");
				} else if ( changeSidoLDAP2 == 0 && sidoUpdate > 0 ) {
					setLastRunStat("COMPLETE(�̷�O/����X UPDATE:" + sidoLdapList.size() + "[" + changeSidoLDAP1 + "])");
				} else if ( changeSidoLDAP2 == 0 && sidoUpdate == 0 ) {
					setLastRunStat("COMPLETE(�̷�X/����X UPDATE:" + sidoLdapList.size() + "[" + changeSidoLDAP1 + "])");
				} else {
					setLastRunStat("COMPLETE(LDAP NO DATA:" + sidoLdapList.size() + "[" + changeSidoLDAP1 + "])");
				}
			} else {
				setLastRunStat("COMPLETE(LDAP NO DATA)");	//ldap ���� ����
			}
			logger.info(getLastRunStat());
			//�μ�, ����� RANK �ϰ�����
			if ( OrganizeManager.instance().updateOrgRank(DBConn) > 0 ) {
				//������ϵ�����(GRPLISTDTL) �����μ��� ��Ī�� ����
				try { OrganizeManager.instance().updateGrpList(DBConn); } catch ( Exception e ) {}
				logger.info("��ũ���� 1");
				//�Ӽ���ϵ�����(ATTLISTDTL)���̺� LISTCD='00001'�������� ������ ���μ��ΰ͸� �߰�
				try { OrganizeManager.instance().updateAttList(DBConn); } catch ( Exception e ) {}
				logger.info("��ũ���� 2");
			}
			
			DBConn.commit();
			DBSido.commit();
			logger.info(getLastRunStat());
			logger.info("��������");
			runInfoLinkQuery(DBConn, "AFTER");		//�������� �� ����
		} catch (Exception e) {
			try { DBConn.rollback(); } catch (Exception ex) { }
			AgentUtil.AgentlogError(getLSeq());
			setLastRunStat("error: "+ e);
			logger.error(getLastRunStat(), e);
		} finally {
			try { if (DBConn != null) { DBConn.close(); } } catch (Exception ex) { }
			try { if (DBSido != null) { DBSido.close(); } } catch (Exception ex) { }
			isConnect = false;
			isSidoLdapConnect = false;
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
}