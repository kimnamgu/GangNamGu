/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ϱ� bean
 * ����:
 */
package nexti.ejms.approval.model;

public class ApprovalBean {
	private int     bunho       = 0;    //����
	private int		formseq		= 0;	//��Ĺ�ȣ
	private String	formkind	= "";	//�����
	private int		sysdocno	= 0;	//�ý��۹�����ȣ
	private String	doctitle	= "";	//��������	
	private String  procstatus  = "";   //�������	
	private String  sancgbn     = "";   //��������(1:��ι���, 2:���⹮��)
	private String	sancgbnnm   = "";   //�������� (��ι���, ���⹮��)
	private String  deptnm      = "";   //���պμ� ��Ī
	private String	submitdate	= "";	//�������
	private String  sancdt      = "";   //��������
	
	private int reqseq = 0;  //��û�� ��û��ȣ
		
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
