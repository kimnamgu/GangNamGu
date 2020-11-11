/**
 * �ۼ���: 2010.05.26
 * �ۼ���: ��� ������
 * ����: System ���� Log ����͸� action
 * ����:
 */
package nexti.ejms.systemLogMonitoring.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SystemLogMonitoringForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;

	private int	   page				= 1;		//������
	private String orggbn			= "";		//����
	private String orggbn_dt		= "";		//����-����
	
	private String search_frdate	= null;
	private String search_todate	= null;

	private String ccdSubCd			= "";		//�����ڵ�
	private String ccdName			= "";		//������
	private String cnt				= "";		//����Ƚ�� (X100��)
	private String login_Method		= "";		//�� ���Ӱ��
	private String sch_gubun		= "";		//�˻� - ��ü:0, �μ� : 1
	private String sch_usernm		= "";		//�˻� - ����
		
	private List systemLogMonitoringList = null; 	//System ���� Log ����͸� ����Ʈ
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}

	public String getSch_gubun() {
		return sch_gubun;
	}

	public void setSch_gubun(String sch_gubun) {
		this.sch_gubun = sch_gubun;
	}

	public String getSch_usernm() {
		return sch_usernm;
	}

	public void setSch_usernm(String sch_usernm) {
		this.sch_usernm = sch_usernm;
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
	 * @return the ccdSubCd
	 */
	public String getCcdSubCd() {
		return ccdSubCd;
	}

	/**
	 * @param ccdSubCd the ccdSubCd to set
	 */
	public void setCcdSubCd(String ccdSubCd) {
		this.ccdSubCd = ccdSubCd;
	}

	/**
	 * @return the ccdName
	 */
	public String getCcdName() {
		return ccdName;
	}

	/**
	 * @param ccdName the ccdName to set
	 */
	public void setCcdName(String ccdName) {
		this.ccdName = ccdName;
	}

	/**
	 * @return the cnt
	 */
	public String getCnt() {
		return cnt;
	}

	/**
	 * @param cnt the cnt to set
	 */
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	/**
	 * @return the login_Method
	 */
	public String getLogin_Method() {
		return login_Method;
	}

	/**
	 * @param login_Method the login_Method to set
	 */
	public void setLogin_Method(String login_Method) {
		this.login_Method = login_Method;
	}

	/**
	 * @return the systemLogMonitoringList
	 */
	public List getSystemLogMonitoringList() {
		return systemLogMonitoringList;
	}

	/**
	 * @param systemLogMonitoringList the systemLogMonitoringList to set
	 */
	public void setSystemLogMonitoringList(List systemLogMonitoringList) {
		this.systemLogMonitoringList = systemLogMonitoringList;
	}

	/**
	 * @return the search_frdate
	 */
	public String getSearch_frdate() {
		return search_frdate;
	}

	/**
	 * @param search_frdate the search_frdate to set
	 */
	public void setSearch_frdate(String search_frdate) {
		this.search_frdate = search_frdate;
	}

	/**
	 * @return the search_todate
	 */
	public String getSearch_todate() {
		return search_todate;
	}

	/**
	 * @param search_todate the search_todate to set
	 */
	public void setSearch_todate(String search_todate) {
		this.search_todate = search_todate;
	}

	/**
	 * @return the orggbn_dt
	 */
	public String getOrggbn_dt() {
		return orggbn_dt;
	}

	/**
	 * @param orggbn_dt the orggbn_dt to set
	 */
	public void setOrggbn_dt(String orggbn_dt) {
		this.orggbn_dt = orggbn_dt;
	}
}