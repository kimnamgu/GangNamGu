package nexti.ejms.agent.model;

import java.sql.Connection;
import java.util.List;

public class SidoLdapManager {
	private static SidoLdapManager instance = null;
	private static SidoLdapDAO dao = null;
	
	private SidoLdapManager() {
	}
	
	public static SidoLdapManager instance() {
		if (instance==null) instance = new SidoLdapManager(); 
		return instance;
	}
	
	private SidoLdapDAO getLdapDAO(){
		if (dao==null) dao = new SidoLdapDAO(); 
		return dao;
	}	

	/**
	 * 시도LDAP 동기화 테이블의 수정내용에 대한 리스트를 가져온다.
	 */
	public List getSidoLdapList(Connection DBSido) throws Exception {
		List sidoLdapList = null;
		
		sidoLdapList =  getLdapDAO().getSidoLdapList(DBSido);
		
		return sidoLdapList;
	}

	/**
	 *	변경된 LDAP동기화정보를 사용자정보와 부서정보 테이블에 갱신 - [관]LDAP사용자정보(연계용), [관]부서정보임시(연계용)    
	 */
	public int changeSidoLDAP1(List sidoLdapList) throws Exception {
		int result = 0;
		
		result = getLdapDAO().changeSidoLDAP1(sidoLdapList) ;
		
		return result;
	}

	/**
	 *	변경된 LDAP동기화정보를 사용자정보와 부서정보 테이블에 갱신 - [관]사용자정보, [관]부서정보
	 */
	public int changeSidoLDAP2() throws Exception {
		int result = 0;
		
		result = getLdapDAO().changeSidoLDAP2() ;
		
		return result;
	}
	
	/**
	 * LDAP동기화최종정보 테이블에 동기화 이력 갱신
	 */
	public int sidoUpdate(Connection DBSido, SidoLdapBean bean) throws Exception {
		int result = 0;
		
		result = getLdapDAO().sidoUpdate(DBSido, bean) ;
		
		return result;
	}	
}
