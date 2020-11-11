/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 결재하기 bean
 * 설명:
 */
package nexti.ejms.elecAppr.model;

public class ElecApprBean {
	private int		sysdocno	= 0;	//시스템문서번호
	private String	tgtdeptcd	= "";	//제출부서코드
	private String	inputusrid	= "";	//입력담당자ID
	private int		seq			= 0;	//일련번호
	private String	gubun		= "";	//검토/승인구분(검토1, 승인2)
	private String	sancresult	= "";	//결재결과
	private String	sancusrid	= "";	//결재자ID
	private String	sancusrnm	= "";	//결재자성명
	private String	sancyn		= "";	//결재여부(완료1, 안함0)
	private String	sancdt		= "";	//결재일시
	private String	submitdt	= "";	//기안일시
	private String	crtdt		= "";	//생성일시
	private String	crtusrid	= "";	//생성자코드
	private String	uptdt		= "";	//수정일시
	private String	uptusrid	= "";	//수정자코드
	
	private int		reqformno	= 0;	//신청양식번
	private	int		reqseq		= 0;	//일련번호
	
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
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getSancdt() {
		return sancdt;
	}
	public void setSancdt(String sancdt) {
		this.sancdt = sancdt;
	}
	public String getSancusrid() {
		return sancusrid;
	}
	public void setSancusrid(String sancusrid) {
		this.sancusrid = sancusrid;
	}
	public String getSancusrnm() {
		return sancusrnm;
	}
	public void setSancusrnm(String sancusrnm) {
		this.sancusrnm = sancusrnm;
	}
	public String getSancyn() {
		return sancyn;
	}
	public void setSancyn(String sancyn) {
		this.sancyn = sancyn;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getSubmitdt() {
		return submitdt;
	}
	public void setSubmitdt(String submitdt) {
		this.submitdt = submitdt;
	}
	public int getSysdocno() {
		return sysdocno;
	}
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
	public String getInputusrid() {
		return inputusrid;
	}
	public void setInputusrid(String tgtdeptcd) {
		this.inputusrid = tgtdeptcd;
	}
	public String getUptdt() {
		return uptdt;
	}
	public void setUptdt(String uptdt) {
		this.uptdt = uptdt;
	}
	public String getUptusrid() {
		return uptusrid;
	}
	public void setUptusrid(String uptusrid) {
		this.uptusrid = uptusrid;
	}
	public String getTgtdeptcd() {
		return tgtdeptcd;
	}
	public void setTgtdeptcd(String tgtdeptcd) {
		this.tgtdeptcd = tgtdeptcd;
	}
	public String getSancresult() {
		return sancresult;
	}
	public void setSancresult(String sancresult) {
		this.sancresult = sancresult;
	}
}