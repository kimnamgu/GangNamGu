/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 속성목록관리 actionform
 * 설명:
 */
package nexti.ejms.attr.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AttrForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private List mainlist = null;
	private List sublist = null;
	
	private String mode      = "";     //mode
	private String gbn       = "";     //구분 (s:시스템, a:관리자)
	private String listcd    = "";     //목록코드
	private String listnm    = "";     //목록명칭
	private int seq          = 0;      //일련번호
	private String listdtlnm = "";     //목록내용
	private String attr_desc = "";     //속성상세설명
	private int posi         = 0;      //주코드 스크롤 위치
	private int cposi        = 0;      //부코드 스크롤 위치

	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}
	
	public String getAttr_desc() {
		return attr_desc;
	}

	public void setAttr_desc(String attr_desc) {
		this.attr_desc = attr_desc;
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getListcd() {
		return listcd;
	}

	public void setListcd(String listcd) {
		this.listcd = listcd;
	}

	public String getListdtlnm() {
		return listdtlnm;
	}

	public void setListdtlnm(String listdtlnm) {
		this.listdtlnm = listdtlnm;
	}

	public String getListnm() {
		return listnm;
	}

	public void setListnm(String listnm) {
		this.listnm = listnm;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}		
}

