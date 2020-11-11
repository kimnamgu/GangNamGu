/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 결재완료 목록 actionform
 * 설명:
 */
package nexti.ejms.approval.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AppCompListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String docgbn     = "";     //문서구분
	private int page          = 0;     	//페이지
	private List appCompList  = null;   //결재완료 목록
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {	
		
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	
	}	
	
	public String getDocgbn() {
		return docgbn;
	}

	public void setDocgbn(String docgbn) {
		this.docgbn = docgbn;
	}

	public List getAppCompList() {
		return appCompList;
	}

	public void setAppCompList(List appCompList) {
		this.appCompList = appCompList;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}	
}