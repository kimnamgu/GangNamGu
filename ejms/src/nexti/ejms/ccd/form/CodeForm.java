/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 코드관리 actionform
 * 설명:
 */
package nexti.ejms.ccd.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CodeForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private List mainlist = null;
	private List sublist = null;
	
	private String mode = "";         //mode
	private String gbn = "";          //구분 (s:시스템, a:관리자)
	private String ccd_cd ="";        //주코드
	private String ccd_sub_cd ="";    //부코드
	private String ccd_name = "";     //코드명
	private String ccd_sub_name = ""; //부코드명(주코드와 부코드명이 같이 사용될때 사용)
	private String ccd_desc = "";     //코드설명
	private int posi = 0;             //주코드 스크롤 위치
	private int cposi = 0;            //부코드 스크롤 위치

	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}
	public List getSublist() {
		return sublist;
	}

	public void setSublist(List sublist) {
		this.sublist = sublist;
	}

	public List getMainlist() {
		return mainlist;
	}

	public void setMainlist(List mainlist) {
		this.mainlist = mainlist;
	}
	public String getGbn() {
		return gbn;
	}
	public void setGbn(String gbn) {
		this.gbn = gbn;
	}

	public String getCcd_cd() {
		return ccd_cd;
	}

	public void setCcd_cd(String ccd_cd) {
		this.ccd_cd = ccd_cd;
	}

	public int getCposi() {
		return cposi;
	}

	public void setCposi(int cposi) {
		this.cposi = cposi;
	}

	public int getPosi() {
		return posi;
	}

	public void setPosi(int posi) {
		this.posi = posi;
	}

	public String getCcd_desc() {
		return ccd_desc;
	}

	public void setCcd_desc(String ccd_desc) {
		this.ccd_desc = ccd_desc;
	}

	public String getCcd_name() {
		return ccd_name;
	}

	public void setCcd_name(String ccd_name) {
		this.ccd_name = ccd_name;
	}

	public String getCcd_sub_cd() {
		return ccd_sub_cd;
	}

	public void setCcd_sub_cd(String ccd_sub_cd) {
		this.ccd_sub_cd = ccd_sub_cd;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getCcd_sub_name() {
		return ccd_sub_name;
	}

	public void setCcd_sub_name(String ccd_sub_name) {
		this.ccd_sub_name = ccd_sub_name;
	}			
}

