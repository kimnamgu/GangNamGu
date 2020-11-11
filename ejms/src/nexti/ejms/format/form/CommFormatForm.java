/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통양식 actionform
 * 설명:
 */
package nexti.ejms.format.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CommFormatForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private int sysdocno      = 0;   //문서번호
	private String sancgbn    = "";  //문서구분(1:배부문서, 2:제출문서) 
	private String enddt      = "";  //마감시한
	private String endcomment = "";  //마감알림말
	private int appCnt        = 0;   //결재해야할 건수
	 
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {		
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}

	public int getAppCnt() {
		return appCnt;
	}

	public void setAppCnt(int appCnt) {
		this.appCnt = appCnt;
	}

	public String getEndcomment() {
		return endcomment;
	}

	public void setEndcomment(String endcomment) {
		this.endcomment = endcomment;
	}

	public String getEnddt() {
		return enddt;
	}

	public void setEnddt(String enddt) {
		this.enddt = enddt;
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
}