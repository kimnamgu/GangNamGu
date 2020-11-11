/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 추가 입력자료 bean
 * 설명:
 */
package nexti.ejms.sinchung.model;

public class ReqSubBean {
	private int reqformno    = 0;    //신청양식번호
	private int reqseq       = 0;    //신청번호
	private int formseq      = 0;    //문항번호
	private String anscont   = "";   //신청내용
	private String other     = "";   //기타의견
	
	public String getAnscont() {
		return anscont;
	}
	public void setAnscont(String anscont) {
		this.anscont = anscont;
	}
	public int getFormseq() {
		return formseq;
	}
	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public int getReqformno() {
		return reqformno;
	}
	public void setReqformno(int reqformno) {
		this.reqformno = reqformno;
	}
	public int getReqseq() {
		return reqseq;
	}
	public void setReqseq(int reqseq) {
		this.reqseq = reqseq;
	}	
}
