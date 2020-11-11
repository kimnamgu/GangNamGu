/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 결재하기 목록 actionform
 * 설명:
 */
package nexti.ejms.approval.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ApprovalListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private int page       = 0 ;     	//페이지
	private List appList   = null;   	//결재하기 목록
	
	private String sancgbn     = "";    //문서구분(1:배부문서, 2:제출문서)
	private int sysdocno   = 0;         //시스템문서번호
	
	private String docgbn     = "";     //문서구분
	
	public String getDocgbn() {
		return docgbn;
	}

	public void setDocgbn(String docgbn) {
		this.docgbn = docgbn;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {		
		return null;
	}	

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	
	}

	public String getSancgbn() {
		return sancgbn;
	}

	public void setSancgbn(String sancgbn) {
		this.sancgbn = sancgbn;
	}

	public int getSysdocno() {
		return sysdocno;
	}

	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}

	public List getAppList() {
		return appList;
	}

	public void setAppList(List appList) {
		this.appList = appList;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}	
}