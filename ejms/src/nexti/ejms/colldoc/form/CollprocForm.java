/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 진행중인취합문서 actionform
 * 설명:
 */
package nexti.ejms.colldoc.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CollprocForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int 	sysdocno 		= 0;
	private String  radiochk		= "";   //공개여부( 공개1, 비공개 2, 비공개기한 3, 취합/입력자에게공개 4)
	private String	closedate		= "";	//마감일 
	private String	searchkey		= "";	//검색어
	private String  formcomment 	= "";   //양식개요
	private String  formhederhtml	= "";   //양식헤드구조
	private String  formbodyhtml	= "";   //양식바디구조
	private String  formtailhtml	= "";	//양식테일구조
	private String  enddt			= "";   //마감시한
	private String  deliverydt		= "";	//배부시간 
	private String  endcomment  	= "";   //마감알림말
	private String  docstate 		= "";   //문서상태
	private String  docstatenm		= "";	//문서상태명
	private String  chrgusrcd		= "";	//취합담당자코드

	public String getChrgusrcd() {
		return chrgusrcd;
	}

	public void setChrgusrcd(String chrgusrcd) {
		this.chrgusrcd = chrgusrcd;
	}

	public String getDocstatenm() {
		return docstatenm;
	}
	
	public void setDocstatenm(String docstatenm) {
		this.docstatenm = docstatenm;
	}


	public String getDocstate() {
		return docstate;
	}


	public void setDocstate(String docstate) {
		this.docstate = docstate;
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


	public String getFormcomment() {
		return formcomment;
	}


	public void setFormcomment(String formcomment) {
		this.formcomment = formcomment;
	}

	public int getSysdocno() {
		return sysdocno;
	}


	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}

	public String getClosedate() {
		return closedate;
	}


	public void setClosedate(String closedate) {
		this.closedate = closedate;
	}


	public String getRadiochk() {
		return radiochk;
	}


	public void setRadiochk(String radiochk) {
		this.radiochk = radiochk;
	}


	public String getSearchkey() {
		return searchkey;
	}


	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}


	public String getFormbodyhtml() {
		return formbodyhtml;
	}


	public void setFormbodyhtml(String formbodyhtml) {
		this.formbodyhtml = formbodyhtml;
	}


	public String getFormhederhtml() {
		return formhederhtml;
	}


	public void setFormhederhtml(String formhederhtml) {
		this.formhederhtml = formhederhtml;
	}


	public String getFormtailhtml() {
		return formtailhtml;
	}


	public void setFormtailhtml(String formtailhtml) {
		this.formtailhtml = formtailhtml;
	}


	public String getDeliverydt() {
		return deliverydt;
	}


	public void setDeliverydt(String deliverydt) {
		this.deliverydt = deliverydt;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {return null;}
	public void reset(ActionMapping mapping, HttpServletRequest request) {}
}