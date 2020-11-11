/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 로그인 actionform
 * 설명:
 */
package nexti.ejms.common.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LoginForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String userId   = "";
	private String password = "";
	private String usernm 	= "";
	private String usersn1  = "";
	private String usersn2  = "";
	private String retUrl	= "";
	private String setfl    = null;
	private int    userage	= 20;	//연령
	private String usersex	= "M";	//성별(남자:M, 여자:F)
	private String cert		= "";
	private String type		= "";
	
	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}
	
	public int getUserage() {
		return userage;
	}
	public void setUserage(int userage) {
		this.userage = userage;
	}
	public String getUsersex() {
		return usersex;
	}
	public void setUsersex(String usersex) {
		this.usersex = usersex;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSetfl() {
		return setfl;
	}

	public void setSetfl(String setfl) {
		this.setfl = setfl;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsernm() {
		return usernm;
	}

	public void setUsernm(String usernm) {
		this.usernm = usernm;
	}

	public String getUsersn1() {
		return usersn1;
	}

	public void setUsersn1(String usersn1) {
		this.usersn1 = usersn1;
	}

	public String getUsersn2() {
		return usersn2;
	}

	public void setUsersn2(String usersn2) {
		this.usersn2 = usersn2;
	}

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}	
}