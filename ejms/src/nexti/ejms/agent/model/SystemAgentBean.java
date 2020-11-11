/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 bean
 * 설명:
 */
package nexti.ejms.agent.model;

import java.io.Serializable;
import java.util.List;

import nexti.ejms.agent.ControlBase;

/**
 * 업무정보를 담고 있는 bean
 * JOB(업무) 테이블의 각 칼럼에 해당하는 setter와 getter를 가진다. 
 */
public class SystemAgentBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String className = "";		//클래스명
	private String configID = "";		//agent.properties 파일에서의 ID
	private String tableID = "";		//에이전트 ID
	private String agentName = "";		//에이전트명
	private String startTime = "";		//실행시작시간
	private String interval = "";		//수행주기
	private String lastRuntime = ""; 	//마지막수행시간
	private String lastRunstat = ""; 	//마지막수행상태
	private boolean runTF = false;		//실행여부
	private boolean validTF = false;	//에이전트 사용여부
	private boolean runtimeTF = false;	//에이전트 런타임사용여부
	private ControlBase control;		//에이전트 클래스
	private List etcjoblist = null;		//시스템연동(기타)목록
	
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
