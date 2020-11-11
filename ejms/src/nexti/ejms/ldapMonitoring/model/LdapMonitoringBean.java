/**
 * 작성일: 2010.05.26
 * 작성자: 사원 신종영
 * 모듈명: LDAP 모니터링 action
 * 설명:
 */
package nexti.ejms.ldapMonitoring.model;

public class LdapMonitoringBean {

	private int    seqno;			// 연번
	private String ldap_id;			// 조직코드(주민번호)
	private String ldap_name;		// 조직명칭(이름)
	private String cmdtype;			// 명령형식
	private String last_attribute;	// 변경COLUMN
	private String applydt;			// 적용일시
	
	
	public LdapMonitoringBean() {
		this.seqno			= 0;
		this.ldap_id		= "";
		this.ldap_name		= "";
		this.cmdtype		= "";
		this.last_attribute	= "";
		this.applydt		= "";
	}

	/**
	 * @return the ldap_id
	 */
	public String getLdap_id() {
		return ldap_id;
	}


	/**
	 * @param ldap_id the ldap_id to set
	 */
	public void setLdap_id(String ldap_id) {
		this.ldap_id = ldap_id;
	}


	/**
	 * @return the ldap_name
	 */
	public String getLdap_name() {
		return ldap_name;
	}


	/**
	 * @param ldap_name the ldap_name to set
	 */
	public void setLdap_name(String ldap_name) {
		this.ldap_name = ldap_name;
	}


	/**
	 * @return the cmdtype
	 */
	public String getCmdtype() {
		return cmdtype;
	}


	/**
	 * @param cmdtype the cmdtype to set
	 */
	public void setCmdtype(String cmdtype) {
		this.cmdtype = cmdtype;
	}


	/**
	 * @return the last_attribute
	 */
	public String getLast_attribute() {
		return last_attribute;
	}


	/**
	 * @param last_attribute the last_attribute to set
	 */
	public void setLast_attribute(String last_attribute) {
		this.last_attribute = last_attribute;
	}


	/**
	 * @return the applydt
	 */
	public String getApplydt() {
		return applydt;
	}


	/**
	 * @param applydt the applydt to set
	 */
	public void setApplydt(String applydt) {
		this.applydt = applydt;
	}

	/**
	 * @return the seqno
	 */
	public int getSeqno() {
		return seqno;
	}

	/**
	 * @param seqno the seqno to set
	 */
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

}
