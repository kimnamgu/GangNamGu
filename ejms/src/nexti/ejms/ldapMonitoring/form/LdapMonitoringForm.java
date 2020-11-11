/**
 * 작성일: 2010.05.26
 * 작성자: 사원 신종영
 * 모듈명: LDAP 모니터링 action
 * 설명:
 */
package nexti.ejms.ldapMonitoring.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LdapMonitoringForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;

	private int	  	page			= 1;		//페이지
	private String 	syncdate		= "";		//동기화일시
	private String 	orggbn			= "";		//구분
	private String 	orggbn_dt		= "";		//구분-세부
	private String	gbn				= "0";		//구분

	private int	   seqNo			= 0;		//번호
	private String ldap_id			= "";		//조직코드(주민번호)
	private String ldap_name		= "";		//조직명칭(이름)
	private String cmdtype			= "";		//명령형식
	private String last_attribute	= "";		//변경COLUMN
	private String applydt			= "";		//적용일시
		
	private List ldapMonitoringList = null; 	//LDAP 동기화 모니터링 리스트
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}

	public String getGbn() {
		return gbn;
	}

	public void setGbn(String gbn) {
		this.gbn = gbn;
	}

	public String getOrggbn_dt() {
		return orggbn_dt;
	}

	public void setOrggbn_dt(String orggbn_dt) {
		this.orggbn_dt = orggbn_dt;
	}

	/**
	 * @return the orggbn
	 */
	public String getOrggbn() {
		return orggbn;
	}

	/**
	 * @param orggbn the orggbn to set
	 */
	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}

	/**
	 * @return the syncdate
	 */
	public String getSyncdate() {
		return syncdate;
	}

	/**
	 * @param syncdate the syncdate to set
	 */
	public void setSyncdate(String syncdate) {
		this.syncdate = syncdate;
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
	 * @return the ldapMonitoringList
	 */
	public List getLdapMonitoringList() {
		return ldapMonitoringList;
	}

	/**
	 * @param ldapMonitoringList the ldapMonitoringList to set
	 */
	public void setLdapMonitoringList(List ldapMonitoringList) {
		this.ldapMonitoringList = ldapMonitoringList;
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
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the seqNo
	 */
	public int getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo the seqNo to set
	 */
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	
}