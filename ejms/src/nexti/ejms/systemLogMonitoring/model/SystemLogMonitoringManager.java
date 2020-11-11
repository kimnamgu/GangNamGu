/**
 * 작성일: 2010.05.26
 * 작성자: 사원 신종영
 * 모듈명: System 접속 Log 모니터링 Manager
 * 설명:
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
	 * System 접속 Log 모니터링 목록
	 * 
	 * @param orggbn 	: 조직 구분
	 * @param orggbn_dt : 조직 구분 - 상세
	 * @param frDate 	: 검색조건 - 기간 FROM
	 * @param toDate 	: 검색조건 - 기간 TO
	 * @param start 	: 페이징 처리를 위한 시작 값
	 * @param end 		: 페이징 처리를 위한 종료 값
	 * 
	 * @return List : System 접속 Log 모니터링 목록
	 * @throws Exception 
	 */
	public List getSystemLogMonitoringList(String orggbn, String orggbn_dt, String rep_dept, String user_id, String frDate, String toDate, int start, int end) throws Exception {
		return getSystemLogMonitoringDAO().systemLogMonitoringList(orggbn, orggbn_dt, rep_dept, user_id, frDate, toDate, start, end);
	}
	
	/**
	 * System 접속 Log 모니터링 개수 가져오기
	 * 
	 * @param orggbn 	: 조직 구분
	 * @param orggbn_dt : 조직 구분 - 상세
	 * @param frDate 	: 검색조건 - 기간 FROM
	 * @param toDate 	: 검색조건 - 기간 TO
	 * 
	 * @return Integer : System 접속 Log 모니터링 개수
	 * @throws Exception 
	 */
	public int getSystemLogMonitoringCount(String orggbn, String orggbn_dt, String frDate, String toDate) throws Exception {
		return getSystemLogMonitoringDAO().systemLogMonitoringCount(orggbn, orggbn_dt, frDate, toDate);
	}
	
	/**
	 * System 접속 Log 모니터링 팝업화면 목록
	 * 
	 * @param ccdSubCd 	: 기관 코드
	 * @param frDate 	: 검색조건 - 기간 FROM
	 * @param toDate 	: 검색조건 - 기간 TO
	 * @param start 	: 페이징 처리를 위한 시작 값
	 * @param end 		: 페이징 처리를 위한 종료 값
	 * 
	 * @return List : System 접속 Log 모니터링 팝업화면 목록
	 * @throws Exception 
	 */
	public List getSystemLogDetailMonitoringList(String sch_gubun, String sch_usernm, String ccdSubCd, String frDate, String toDate, int start, int end) throws Exception {
		return getSystemLogMonitoringDAO().systemLogDetailMonitoringList(sch_gubun, sch_usernm, ccdSubCd, frDate, toDate, start, end);
	}
	
	/**
	 * System 접속 Log 모니터링 팝업화면 개수 가져오기
	 * 
	 * @param ccdSubCd 	: 기관 코드
	 * @param frDate 	: 검색조건 - 기간 FROM
	 * @param toDate 	: 검색조건 - 기간 TO
	 * 
	 * @return Integer : System 접속 Log 모니터링 팝업화면 개수
	 * @throws Exception 
	 */
	public int getSystemLogDetailMonitoringCount(String sch_gubun, String sch_usernm, String ccdSubCd, String frDate, String toDate) throws Exception {
		return getSystemLogMonitoringDAO().systemLogDetailMonitoringCount(sch_gubun, sch_usernm, ccdSubCd, frDate, toDate);
	}
	
	/**
	 * 조직도 기관명 가져오기
	 * 
	 * @param ccdSubCd 	: 기관 코드
	 * 
	 * @return Integer : 조직도 기관명 가져오기
	 * @throws Exception 
	 */
	public String getSystemLogCcdSubCd(String sch_gubun, String ccdSubCd) throws Exception {
		return getSystemLogMonitoringDAO().systemLogCcdSubCd(sch_gubun, ccdSubCd);
	}
}