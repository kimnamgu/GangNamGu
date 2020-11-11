/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 manager
 * 설명:
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
	 * 에이전트 리스트 
	 * ClassName,ConfigID,TableID,AgentName,StartTime,LastRuntime,Isrun,IsValid
	 */
	public List agentList() {
		List saList = new ArrayList();
		SystemAgentBean saBean = null;
		//String lastrunstat = null;	
		
		//스케줄러
		saBean = new SystemAgentBean();
		saBean.setControl(ControlScheduleTimer.getInstance());
		saBean.setValidTF(appInfo.isScheduleTimer());
		saBean.setInterval(String.valueOf(appInfo.getScheduleInterval())+"초");
		saBean.setAgentName("Agent 스케줄러");
		saBean.setConfigID("schedule");
		saBean.setTableID("schedule");
		saBean.setRuntimeTF(false);		//자체Timer로 구동하면 false, 아니면 true
		if ( saBean.isValidTF() ) saList.add(saBean);
		
		//마감시한처리
		saBean = new SystemAgentBean();
		saBean.setControl(ControlDeadlineTimer.getInstance());
		saBean.setValidTF(appInfo.isDeadlineTimer());
		saBean.setInterval(String.valueOf(appInfo.getDeadlineInterval())+"초");
		saBean.setAgentName("취합마감시한 처리");
		saBean.setConfigID("deadline");
		saBean.setTableID("deadline");
		saBean.setRuntimeTF(false);		//자체Timer로 구동하면 false, 아니면 true
		if ( saBean.isValidTF() ) saList.add(saBean);
		
		//설문조사 결과가져오기
		saBean = new SystemAgentBean();
		saBean.setControl(ControlOutsideRchGetting.getInstance());
		saBean.setValidTF(appInfo.isRchresultTimer());
		saBean.setInterval(String.valueOf(appInfo.getRchresultInterval())+"초");
		saBean.setAgentName("외부설문결과 가져오기");
		saBean.setConfigID("rchresult");
		saBean.setTableID("rchresult");
		saBean.setRuntimeTF(false);		//자체Timer로 구동하면 false, 아니면 true
		if ( saBean.isValidTF() ) saList.add(saBean);
		
		//신청서 결과가져오기
		saBean = new SystemAgentBean();
		saBean.setControl(ControlOutsideReqGetting.getInstance());
		saBean.setValidTF(appInfo.isReqresultTimer());
		saBean.setInterval(String.valueOf(appInfo.getReqresultInterval())+"초");
		saBean.setAgentName("외부신청결과 가져오기");
		saBean.setConfigID("reqresult");
		saBean.setTableID("reqresult");
		saBean.setRuntimeTF(false);		//자체Timer로 구동하면 false, 아니면 true
		if ( saBean.isValidTF() ) saList.add(saBean);
		
		//새올행정 조직도 연계
		saBean = new SystemAgentBean();
		saBean.setControl(ControlUsrDeptGetting.getInstance());
		saBean.setValidTF(appInfo.isUsrdept());
		saBean.setAgentName("조직정보연계");
		saBean.setConfigID("usrdept");
		saBean.setTableID("usrdept");
		saBean.setRuntimeTF(true);		//자체Timer로 구동하면 false, 아니면 true
		if ( saBean.isValidTF() ) saList.add(saBean);

		//시도LDAP 조직도 연계
		saBean = new SystemAgentBean();
		saBean.setControl(ControlSidoLdapGetting.getInstance());
		saBean.setValidTF(appInfo.isSidoldap());
		saBean.setAgentName("시도LDAP 조직정보연계");
		saBean.setConfigID("sidoldap");
		saBean.setTableID("sidoldap");
		saBean.setRuntimeTF(true);		//자체Timer로 구동하면 false, 아니면 true
		if ( saBean.isValidTF() ) saList.add(saBean);
		
		//세올 웹서비스 조직도 연계
		saBean = new SystemAgentBean();
		saBean.setControl(ControlWsUsrDeptGetting.getInstance());
		saBean.setValidTF(appInfo.isWsSaeall());
		saBean.setAgentName("조직정보연계(웹서비스)");
		saBean.setConfigID("wsSaeall");
		saBean.setTableID("wsSaeall");
		saBean.setRuntimeTF(true);		//자체Timer로 구동하면 false, 아니면 true
		if ( saBean.isValidTF() ) saList.add(saBean);
		
		return saList;
	}

	/**
	 * 에이전트 관리 - 에이젼트 시작 및 일시중지
	 * p_id : 프로그램ID(XXXX)
	 * p_ctr : 시작(001), 중지(000), 즉시실행(002) 
	 */
	public void agentControl(String p_id, String p_ctr){
		if (Utils.isNull(p_id)) return;
		List list = agentList();
		for (int i=0; i<list.size(); i++) {
			SystemAgentBean saBean = (SystemAgentBean)list.get(i);
			if (!p_id.equalsIgnoreCase(saBean.getTableID())) continue;
			if ("001".equals(p_ctr)) {	//시작
				saBean.getControl().start();
			}
			else if ("002".equals(p_ctr)) {	//즉시실행
				saBean.getControl().run();
			} 
			else {	//일시중지
				saBean.getControl().stop();
			}
		}
	}
	
	/* agent명 가져오기
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
	
	/** agent runtime 등록	 */
	public int insertSystemAgentRuntime (SystemAgentRuntimeBean bean) {
		
		return getSystemAgentDAO().insertSystemAgentRuntime(bean);
	}

	/** agent runtime 삭제	 */
	public int deleteSystemAgentRuntime (String p_id, int p_seq) {
		
		return getSystemAgentDAO().deleteSystemAgentRuntime(p_id, p_seq);
	}

	/** agent runtime 수정	 */
	public int modifySystemAgentRuntime (SystemAgentRuntimeBean bean) {
		
		return getSystemAgentDAO().modifySystemAgentRuntime(bean);
	}

	/** agent runtime detail 상세정보	 */
	public SystemAgentRuntimeBean agentRuntimeDtlInfo (String p_id, int p_seq) {
		
		return getSystemAgentDAO().agentRuntimeDtlInfo(p_id, p_seq);
	}		
}
