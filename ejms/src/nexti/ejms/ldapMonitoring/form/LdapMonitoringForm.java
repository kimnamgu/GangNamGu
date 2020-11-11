/**
 * �ۼ���: 2010.05.26
 * �ۼ���: ��� ������
 * ����: LDAP ����͸� action
 * ����:
 */
package nexti.ejms.ldapMonitoring.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LdapMonitoringForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;

	private int	  	page			= 1;		//������
	private String 	syncdate		= "";		//����ȭ�Ͻ�
	private String 	orggbn			= "";		//����
	private String 	orggbn_dt		= "";		//����-����
	private String	gbn				= "0";		//����

	private int	   seqNo			= 0;		//��ȣ
	private String ldap_id			= "";		//�����ڵ�(�ֹι�ȣ)
	private String ldap_name		= "";		//������Ī(�̸�)
	private String cmdtype			= "";		//�������
	private String last_attribute	= "";		//����COLUMN
	private String applydt			= "";		//�����Ͻ�
		
	private List ldapMonitoringList = null; 	//LDAP ����ȭ ����͸� ����Ʈ
	
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