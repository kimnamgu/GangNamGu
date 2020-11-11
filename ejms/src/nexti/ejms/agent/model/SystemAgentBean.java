/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ý��ۿ�����Ʈ bean
 * ����:
 */
package nexti.ejms.agent.model;

import java.io.Serializable;
import java.util.List;

import nexti.ejms.agent.ControlBase;

/**
 * ���������� ��� �ִ� bean
 * JOB(����) ���̺��� �� Į���� �ش��ϴ� setter�� getter�� ������. 
 */
public class SystemAgentBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String className = "";		//Ŭ������
	private String configID = "";		//agent.properties ���Ͽ����� ID
	private String tableID = "";		//������Ʈ ID
	private String agentName = "";		//������Ʈ��
	private String startTime = "";		//������۽ð�
	private String interval = "";		//�����ֱ�
	private String lastRuntime = ""; 	//����������ð�
	private String lastRunstat = ""; 	//�������������
	private boolean runTF = false;		//���࿩��
	private boolean validTF = false;	//������Ʈ ��뿩��
	private boolean runtimeTF = false;	//������Ʈ ��Ÿ�ӻ�뿩��
	private ControlBase control;		//������Ʈ Ŭ����
	private List etcjoblist = null;		//�ý��ۿ���(��Ÿ)���
	
	public List getEtcjoblist() {
		return etcjoblist;
	}
	public void setEtcjoblist(List etcJobInfoList) {
		etcjoblist = etcJobInfoList;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getLastRunstat() {
		if (control!=null) lastRunstat = control.getLastRunStat();
		return lastRunstat;
	}
	public void setLastRunstat(String lastRunstat) {
		this.lastRunstat = lastRunstat;
	}
	public ControlBase getControl() {
		return control;
	}
	public void setControl(ControlBase control) {
		this.control = control;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getClassName() {
		if (control!=null) className = control.getClass().getName();
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getConfigID() {
		return configID;
	}
	public void setConfigID(String configID) {
		this.configID = configID;
	}
	public String getLastRuntime() {
		if (control!=null) lastRuntime = control.getLastRunDateTime();
		return lastRuntime;
	}
	public void setLastRuntime(String lastRuntime) {
		this.lastRuntime = lastRuntime;
	}
	public boolean isRunTF() {
		if (control!=null) runTF = control.getIsRun();
		return runTF;
	}
	public void setRunTF(boolean runTF) {
		this.runTF = runTF;
	}
	public String getStartTime() {
		if (control!=null) startTime = control.getStartDateTime();
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getTableID() {
		return tableID;
	}
	public void setTableID(String tableID) {
		this.tableID = tableID;
	}
	public boolean isValidTF() {
		return validTF;
	}
	public void setValidTF(boolean validTF) {
		this.validTF = validTF;
	}
	public boolean isRuntimeTF() {
		return runtimeTF;
	}
	public void setRuntimeTF(boolean runtimeTF) {
		this.runtimeTF = runtimeTF;
	}
}
