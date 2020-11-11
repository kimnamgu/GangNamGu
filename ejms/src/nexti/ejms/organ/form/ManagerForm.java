/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 관리자변경 actionform
 * 설명:
 */
package nexti.ejms.organ.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ManagerForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private String 	user_id      = "";     //관리자 ID
	private String 	user_id1     = "";     //화면에 다시 뿌려지는 것을 막는다.
	private String 	gbn          = "";     //삭제/등록구분(D:삭제)
	private List 	mgrList      = null;   //관리자 목록
	private String 	orggbn       = "";	   //조직구분
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}

	public String getOrggbn() {
		return orggbn;
	}

	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}

	public String getUser_id1() {
		return user_id1;
	}

	public void setUser_id1(String user_id1) {
		this.user_id1 = user_id1;
	}

	public String getGbn() {
		return gbn;
	}

	public void setGbn(String gbn) {
		this.gbn = gbn;
	}	

	public List getMgrList() {
		return mgrList;
	}

	public void setMgrList(List mgrList) {
		this.mgrList = mgrList;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}	
}