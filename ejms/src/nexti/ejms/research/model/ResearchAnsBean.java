/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 응답 bean
 * 설명:
 */
package nexti.ejms.research.model;

import java.util.List;

public class ResearchAnsBean  {
	
	private int rchno 		= 0;    //설문번호
	private int ansusrseq	= 0;    //답변자번호
	private int formseq     = 0;    //문항번호
	private String anscont 	= "";   //보기내용
	private String other 	= "";	//기타YN
	private String crtusrid = "";	//입력자
	private String crtusrnm = "";	//입력자명
	private String crtdt 	= "";   //입력시간
	
	private List ansList = null;   	//보기 목록
	
	public String getCrtdt() {
		return crtdt;
	}
	public void setCrtdt(String crtdt) {
		this.crtdt = crtdt;
	}
	public List getAnsList() {
		return ansList;
	}
	public void setAnsList(List ansList) {
		this.ansList = ansList;
	}
	public String getCrtusrid() {
		return crtusrid;
	}
	public void setCrtusrid(String crtusrid) {
		this.crtusrid = crtusrid;
	}
	public String getCrtusrnm() {
		return crtusrnm;
	}
	public void setCrtusrnm(String crtusrnm) {
		this.crtusrnm = crtusrnm;
	}
	public String getAnscont() {
		return anscont;
	}
	public void setAnscont(String anscont) {
		this.anscont = anscont;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public int getFormseq() {
		return formseq;
	}
	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}
	public int getRchno() {
		return rchno;
	}
	public void setRchno(int rchno) {
		this.rchno = rchno;
	}
	public int getAnsusrseq() {
		return ansusrseq;
	}
	public void setAnsusrseq(int ansusrseq) {
		this.ansusrseq = ansusrseq;
	}
}