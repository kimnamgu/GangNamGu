/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ý��ۿ�����Ʈ manager
 * ����:
 */
package nexti.ejms.agent.model;

import java.util.ArrayList;
import java.util.List;

import nexti.ejms.agent.ControlDeadlineTimer;
import nexti.ejms.agent.ControlOutsideRchGetting;
import nexti.ejms.agent.ControlOutsideReqGetting;
import nexti.ejms.agent.ControlScheduleTimer;
import nexti.ejms.agent.ControlSidoLdapGetting;
import nexti.ejms.agent.ControlUsrDeptGetting;
import nexti.ejms.agent.ControlWsUsrDeptGetting;


import nexti.ejms.common.appInfo;
import nexti.ejms.util.Utils;

public class SystemAgentManager {
	private SystemAgentManager() {
	}

	public static SystemAgentManager instance() {
		return (new SystemAgentManager());
	}
	
	private SystemAgentDAO getSystemAgentDAO() {
		return new SystemAgentDAO();
	}	
	
	/**
	 * ������Ʈ ����Ʈ 
	 * ClassName,ConfigID,TableID,AgentName,StartTime,LastRuntime,Isrun,IsValid
	 */
	public List agentList() {
		List saList = new ArrayList();
		SystemAgentBean saBean = null;
		//String lastrunstat = null;	
		
		//�����ٷ�
		saBean = new SystemAgentBean();
		saBean.setControl(ControlScheduleTimer.getInstance());
		saBean.setValidTF(appInfo.isScheduleTimer());
		saBean.setInterval(String.valueOf(appInfo.getScheduleInterval())+"��");
		saBean.setAgentName("Agent �����ٷ�");
		saBean.setConfigID("schedule");
		saBean.setTableID("schedule");
		saBean.setRuntimeTF(false);		//��üTimer�� �����ϸ� false, �ƴϸ� true
		if ( saBean.isValidTF() ) saList.add(saBean);
		
		//��������ó��
		saBean = new SystemAgentBean();
		saBean.setControl(ControlDeadlineTimer.getInstance());
		saBean.setValidTF(appInfo.isDeadlineTimer());
		saBean.setInterval(String.valueOf(appInfo.getDeadlineInterval())+"��");
		saBean.setAgentName("���ո������� ó��");
		saBean.setConfigID("deadline");
		saBean.setTableID("deadline");
		saBean.setRuntimeTF(false);		//��üTimer�� �����ϸ� false, �ƴϸ� true
		if ( saBean.isValidTF() ) saList.add(saBean);
		
		//�������� �����������
		saBean = new SystemAgentBean();
		saBean.setControl(ControlOutsideRchGetting.getInstance());
		saBean.setValidTF(appInfo.isRchresultTimer());
		saBean.setInterval(String.valueOf(appInfo.getRchresultInterval())+"��");
		saBean.setAgentName("�ܺμ������ ��������");
		saBean.setConfigID("rchresult");
		saBean.setTableID("rchresult");
		saBean.setRuntimeTF(false);		//��üTimer�� �����ϸ� false, �ƴϸ� true
		if ( saBean.isValidTF() ) saList.add(saBean);
		
		//��û�� �����������
		saBean = new SystemAgentBean();
		saBean.setControl(ControlOutsideReqGetting.getInstance());
		saBean.setValidTF(appInfo.isReqresultTimer());
		saBean.setInterval(String.valueOf(appInfo.getReqresultInterval())+"��");
		saBean.setAgentName("�ܺν�û��� ��������");
		saBean.setConfigID("reqresult");
		saBean.setTableID("reqresult");
		saBean.setRuntimeTF(false);		//��üTimer�� �����ϸ� false, �ƴϸ� true
		if ( saBean.isValidTF() ) saList.add(saBean);
		
		//�������� ������ ����
		saBean = new SystemAgentBean();
		saBean.setControl(ControlUsrDeptGetting.getInstance());
		saBean.setValidTF(appInfo.isUsrdept());
		saBean.setAgentName("������������");
		saBean.setConfigID("usrdept");
		saBean.setTableID("usrdept");
		saBean.setRuntimeTF(true);		//��üTimer�� �����ϸ� false, �ƴϸ� true
		if ( saBean.isValidTF() ) saList.add(saBean);

		//�õ�LDAP ������ ����
		saBean = new SystemAgentBean();
		saBean.setControl(ControlSidoLdapGetting.getInstance());
		saBean.setValidTF(appInfo.isSidoldap());
		saBean.setAgentName("�õ�LDAP ������������");
		saBean.setConfigID("sidoldap");
		saBean.setTableID("sidoldap");
		saBean.setRuntimeTF(true);		//��üTimer�� �����ϸ� false, �ƴϸ� true
		if ( saBean.isValidTF() ) saList.add(saBean);
		
		//���� ������ ������ ����
		saBean = new SystemAgentBean();
		saBean.setControl(ControlWsUsrDeptGetting.getInstance());
		saBean.setValidTF(appInfo.isWsSaeall());
		saBean.setAgentName("������������(������)");
		saBean.setConfigID("wsSaeall");
		saBean.setTableID("wsSaeall");
		saBean.setRuntimeTF(true);		//��üTimer�� �����ϸ� false, �ƴϸ� true
		if ( saBean.isValidTF() ) saList.add(saBean);
		
		return saList;
	}

	/**
	 * ������Ʈ ���� - ������Ʈ ���� �� �Ͻ�����
	 * p_id : ���α׷�ID(XXXX)
	 * p_ctr : ����(001), ����(000), ��ý���(002) 
	 */
	public void agentControl(String p_id, String p_ctr){
		if (Utils.isNull(p_id)) return;
		List list = agentList();
		for (int i=0; i<list.size(); i++) {
			SystemAgentBean saBean = (SystemAgentBean)list.get(i);
			if (!p_id.equalsIgnoreCase(saBean.getTableID())) continue;
			if ("001".equals(p_ctr)) {	//����
				saBean.getControl().start();
			}
			else if ("002".equals(p_ctr)) {	//��ý���
				saBean.getControl().run();
			} 
			else {	//�Ͻ�����
				saBean.getControl().stop();
			}
		}
	}
	
	/* agent�� ��������
	 */
	public String getAgentName(String p_id){
		if (Utils.isNull(p_id)) return "";
		List list = agentList();
		for (int i=0; i<list.size(); i++) {
			SystemAgentBean saBean = (SystemAgentBean)list.get(i);
			if (!p_id.equalsIgnoreCase(saBean.getTableID())) continue;
			return saBean.getAgentName();
		}
		return "";
	}

	/* agentruntime list
	 */
	public List agentRuntimeList(String p_id){
		return getSystemAgentDAO().agentRuntimeList(p_id);
	}
	
	/** agent runtime ���	 */
	public int insertSystemAgentRuntime (SystemAgentRuntimeBean bean) {
		
		return getSystemAgentDAO().insertSystemAgentRuntime(bean);
	}

	/** agent runtime ����	 */
	public int deleteSystemAgentRuntime (String p_id, int p_seq) {
		
		return getSystemAgentDAO().deleteSystemAgentRuntime(p_id, p_seq);
	}

	/** agent runtime ����	 */
	public int modifySystemAgentRuntime (SystemAgentRuntimeBean bean) {
		
		return getSystemAgentDAO().modifySystemAgentRuntime(bean);
	}

	/** agent runtime detail ������	 */
	public SystemAgentRuntimeBean agentRuntimeDtlInfo (String p_id, int p_seq) {
		
		return getSystemAgentDAO().agentRuntimeDtlInfo(p_id, p_seq);
	}		
}
