/**
 * �ۼ���: 2010.05.26
 * �ۼ���: ��� ������
 * ����: System ���� Log ����͸� action
 * ����:
 */
package nexti.ejms.systemLogMonitoring.model;

public class SystemLogMonitoringBean {

	private String ccdSubCd			= "";		//�����ڵ�
	private String ccdName			= "";		//������
	private String cnt				= "";		//����Ƚ�� (X100��)
	private String login_Method		= "";		//�� ���Ӱ��
	private String loginTime		= "";		//�α����Ͻ�
	
	public SystemLogMonitoringBean() {
		this.ccdSubCd		= "";
		this.ccdName		= "";
		this.cnt			= "";
		this.login_Method	= "";
		this.loginTime		= "";
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
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
}
