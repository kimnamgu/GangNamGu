/**
 * �ۼ���: 2010.05.26
 * �ۼ���: ��� ������
 * ����: System ���� Log ����͸� Manager
 * ����:
 */
package nexti.ejms.systemLogMonitoring.model;

import java.util.List;

//import org.apache.log4j.Logger;

import nexti.ejms.systemLogMonitoring.model.SystemLogMonitoringManager;

public class SystemLogMonitoringManager {
	
	private static SystemLogMonitoringManager instance = null;
	private static SystemLogMonitoringDAO dao = null;
	//private static Logger logger = Logger.getLogger(SystemLogMonitoringManager.class);
	
	private SystemLogMonitoringManager() {
		
	}
	
	public static SystemLogMonitoringManager instance() {
		
		if(instance == null)
			instance = new SystemLogMonitoringManager();
		return instance;
	}

	private SystemLogMonitoringDAO getSystemLogMonitoringDAO() {
		
		if(dao == null)
			dao = new SystemLogMonitoringDAO(); 
		return dao;
	}
	
	/**
	 * System ���� Log ����͸� ���
	 * 
	 * @param orggbn 	: ���� ����
	 * @param orggbn_dt : ���� ���� - ��
	 * @param frDate 	: �˻����� - �Ⱓ FROM
	 * @param toDate 	: �˻����� - �Ⱓ TO
	 * @param start 	: ����¡ ó���� ���� ���� ��
	 * @param end 		: ����¡ ó���� ���� ���� ��
	 * 
	 * @return List : System ���� Log ����͸� ���
	 * @throws Exception 
	 */
	public List getSystemLogMonitoringList(String orggbn, String orggbn_dt, String rep_dept, String user_id, String frDate, String toDate, int start, int end) throws Exception {
		return getSystemLogMonitoringDAO().systemLogMonitoringList(orggbn, orggbn_dt, rep_dept, user_id, frDate, toDate, start, end);
	}
	
	/**
	 * System ���� Log ����͸� ���� ��������
	 * 
	 * @param orggbn 	: ���� ����
	 * @param orggbn_dt : ���� ���� - ��
	 * @param frDate 	: �˻����� - �Ⱓ FROM
	 * @param toDate 	: �˻����� - �Ⱓ TO
	 * 
	 * @return Integer : System ���� Log ����͸� ����
	 * @throws Exception 
	 */
	public int getSystemLogMonitoringCount(String orggbn, String orggbn_dt, String frDate, String toDate) throws Exception {
		return getSystemLogMonitoringDAO().systemLogMonitoringCount(orggbn, orggbn_dt, frDate, toDate);
	}
	
	/**
	 * System ���� Log ����͸� �˾�ȭ�� ���
	 * 
	 * @param ccdSubCd 	: ��� �ڵ�
	 * @param frDate 	: �˻����� - �Ⱓ FROM
	 * @param toDate 	: �˻����� - �Ⱓ TO
	 * @param start 	: ����¡ ó���� ���� ���� ��
	 * @param end 		: ����¡ ó���� ���� ���� ��
	 * 
	 * @return List : System ���� Log ����͸� �˾�ȭ�� ���
	 * @throws Exception 
	 */
	public List getSystemLogDetailMonitoringList(String sch_gubun, String sch_usernm, String ccdSubCd, String frDate, String toDate, int start, int end) throws Exception {
		return getSystemLogMonitoringDAO().systemLogDetailMonitoringList(sch_gubun, sch_usernm, ccdSubCd, frDate, toDate, start, end);
	}
	
	/**
	 * System ���� Log ����͸� �˾�ȭ�� ���� ��������
	 * 
	 * @param ccdSubCd 	: ��� �ڵ�
	 * @param frDate 	: �˻����� - �Ⱓ FROM
	 * @param toDate 	: �˻����� - �Ⱓ TO
	 * 
	 * @return Integer : System ���� Log ����͸� �˾�ȭ�� ����
	 * @throws Exception 
	 */
	public int getSystemLogDetailMonitoringCount(String sch_gubun, String sch_usernm, String ccdSubCd, String frDate, String toDate) throws Exception {
		return getSystemLogMonitoringDAO().systemLogDetailMonitoringCount(sch_gubun, sch_usernm, ccdSubCd, frDate, toDate);
	}
	
	/**
	 * ������ ����� ��������
	 * 
	 * @param ccdSubCd 	: ��� �ڵ�
	 * 
	 * @return Integer : ������ ����� ��������
	 * @throws Exception 
	 */
	public String getSystemLogCcdSubCd(String sch_gubun, String ccdSubCd) throws Exception {
		return getSystemLogMonitoringDAO().systemLogCcdSubCd(sch_gubun, ccdSubCd);
	}
}