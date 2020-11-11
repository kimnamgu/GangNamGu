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
	 * �õ�LDAP ����ȭ ���̺��� �������뿡 ���� ����Ʈ�� �����´�.
	 */
	public List getSidoLdapList(Connection DBSido) throws Exception {
		List sidoLdapList = null;
		
		sidoLdapList =  getLdapDAO().getSidoLdapList(DBSido);
		
		return sidoLdapList;
	}

	/**
	 *	����� LDAP����ȭ������ ����������� �μ����� ���̺� ���� - [��]LDAP���������(�����), [��]�μ������ӽ�(�����)    
	 */
	public int changeSidoLDAP1(List sidoLdapList) throws Exception {
		int result = 0;
		
		result = getLdapDAO().changeSidoLDAP1(sidoLdapList) ;
		
		return result;
	}

	/**
	 *	����� LDAP����ȭ������ ����������� �μ����� ���̺� ���� - [��]���������, [��]�μ�����
	 */
	public int changeSidoLDAP2() throws Exception {
		int result = 0;
		
		result = getLdapDAO().changeSidoLDAP2() ;
		
		return result;
	}
	
	/**
	 * LDAP����ȭ�������� ���̺� ����ȭ �̷� ����
	 */
	public int sidoUpdate(Connection DBSido, SidoLdapBean bean) throws Exception {
		int result = 0;
		
		result = getLdapDAO().sidoUpdate(DBSido, bean) ;
		
		return result;
	}	
}
