/**
 * 작성일: 2010.05.26
 * 작성자: 사원 신종영
 * 모듈명: LDAP 모니터링 action
 * 설명:
 */
package nexti.ejms.ldapMonitoring.model;

import java.util.List;

//import org.apache.log4j.Logger;

import nexti.ejms.ldapMonitoring.model.LdapMonitoringManager;

public class LdapMonitoringManager {
	
	private static LdapMonitoringManager instance = null;
	private static LdapMonitoringDAO dao = null;
	//private static Logger logger = Logger.getLogger(LdapMonitoringManager.class);
	
	private LdapMonitoringManager() {
		
	}
	
	public static LdapMonitoringManager instance() {
		
		if(instance == null)
			instance = new LdapMonitoringManager();
		return instance;
	}

	private LdapMonitoringDAO getLdapMonitoringDAO() {
		
		if(dao == null)
			dao = new LdapMonitoringDAO(); 
		return dao;
	}
	
	/**
	 * LDAP 동기화 모니터링 목록
	 * 
	 * @param orggbn : 구분
	 * @param syncdate : 동기화 일시
	 * 
	 * @return List : LDAP 동기화 모니터링 목록
	 * @throws Exception 
	 */
	public List getLdapMonitoringList(String orggbn, String orggbn_dt, String gbn, String syncdate, String rep_dept, String user_id, int start, int end) throws Exception {
		return getLdapMonitoringDAO().ldapMonitoringList(orggbn, orggbn_dt, gbn, syncdate, rep_dept, user_id, start, end);
	}
	
	/**
	 * LDAP 동기화 모니터링 개수 가져오기
	 * 
	 * @param orggbn : 구분
	 * @param syncdate : 동기화 일시
	 * 
	 * @return Integer : LDAP 동기화 모니터링 개수
	 * @throws Exception 
	 */
	public int getLdapMonitoringCount(String orggbn, String orggbn_dt, String gbn, String syncdate, String rep_dept, String user_id) throws Exception {
		return getLdapMonitoringDAO().ldapMonitoringCount(orggbn, orggbn_dt, gbn, syncdate, rep_dept, user_id);
	}
}