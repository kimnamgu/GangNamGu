/**
 * 작성일: 2010.05.26
 * 작성자: 사원 신종영
 * 모듈명: System 접속 Log 모니터링 action
 * 설명:
 */
package nexti.ejms.systemLogMonitoring.model;

public class SystemLogMonitoringBean {

	private String ccdSubCd			= "";		//조직코드
	private String ccdName			= "";		//조직명
	private String cnt				= "";		//접속횟수 (X100건)
	private String login_Method		= "";		//주 접속경로
	private String loginTime		= "";		//로그인일시
	
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
