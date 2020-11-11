/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 결재하기 bean
 * 설명:
 */
package nexti.ejms.approval.model;

public class ApprovalBean {
	private int     bunho       = 0;    //연번
	private int		formseq		= 0;	//양식번호
	private String	formkind	= "";	//양식종
	private int		sysdocno	= 0;	//시스템문서번호
	private String	doctitle	= "";	//문서제목	
	private String  procstatus  = "";   //진행상태	
	private String  sancgbn     = "";   //문서구분(1:배부문서, 2:제출문서)
	private String	sancgbnnm   = "";   //문서구분 (배부문서, 제출문서)
	private String  deptnm      = "";   //취합부서 명칭
	private String	submitdate	= "";	//기안일자
	private String  sancdt      = "";   //결재일자
	
	private int reqseq = 0;  //신청서 신청번호
		
	public int getReqseq() {
		return reqseq;
	}
	public void setReqseq(int reqseq) {
		this.reqseq = reqseq;
	}
	public String getSancgbnnm() {
		return sancgbnnm;
	}
	public void setSancgbnnm(String sancgbnnm) {
		this.sancgbnnm = sancgbnnm;
	}
	public String getSancdt() {
		return sancdt;
	}
	public void setSancdt(String sancdt) {
		this.sancdt = sancdt;
	}
	public int getBunho() {
		return bunho;
	}
	public void setBunho(int bunho) {
		this.bunho = bunho;
	}
	public String getDeptnm() {
		return deptnm;
	}
	public void setDeptnm(String deptnm) {
		this.deptnm = deptnm;
	}
	public String getDoctitle() {
		return doctitle;
	}
	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}
	public String getProcstatus() {
		return procstatus;
	}
	public void setProcstatus(String procstatus) {
		this.procstatus = procstatus;
	}
	public String getSancgbn() {
		return sancgbn;
	}
	public void setSancgbn(String sancgbn) {
		this.sancgbn = sancgbn;
	}
	public String getSubmitdate() {
		return submitdate;
	}
	public void setSubmitdate(String submitdate) {
		this.submitdate = submitdate;
	}
	public int getSysdocno() {
		return sysdocno;
	}
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
	public String getFormkind() {
		return formkind;
	}
	public void setFormkind(String formkind) {
		this.formkind = formkind;
	}
	public int getFormseq() {
		return formseq;
	}
	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}	
}
