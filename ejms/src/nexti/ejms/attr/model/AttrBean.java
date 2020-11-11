/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 속성목록관리 bean
 * 설명:
 */
package nexti.ejms.attr.model;

import java.util.List;

public class AttrBean {
	private String	listcd		= "";	//목록코드
	private String	listnm		= "";	//목록명칭
	private String	crtdt		= "";	//작성일자
	private String	crtusrid	= "";	//작성자
	private String	crtusrgbn	= "";	//작성자구분
	private int		seq			= 0;	//일련번호
	private String	listdtlnm	= "";	//목록내용
	private String 	attr_desc	= "";	//속성상세설명
	private List	listdtlList	= null;	//속성목록리스트
	
	public List getListdtlList() {
		return listdtlList;
	}
	public void setListdtlList(List listdtlnmList) {
		this.listdtlList = listdtlnmList;
	}
	public String getCrtusrgbn() {
		return crtusrgbn;
	}
	public void setCrtusrgbn(String crtusrgbn) {
		this.crtusrgbn = crtusrgbn;
	}
	public String getAttr_desc() {
		return attr_desc;
	}
	public void setAttr_desc(String attr_desc) {
		this.attr_desc = attr_desc;
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
	public String getCrtdt() {
		return crtdt;
	}
	public void setCrtdt(String crtdt) {
		this.crtdt = crtdt;
	}
	public String getCrtusrid() {
		return crtusrid;
	}
	public void setCrtusrid(String crtusrid) {
		this.crtusrid = crtusrid;
	}
}
