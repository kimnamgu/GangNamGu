/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ϱ� bean
 * ����:
 */
package nexti.ejms.elecAppr.model;

public class ElecApprBean {
	private int		sysdocno	= 0;	//�ý��۹�����ȣ
	private String	tgtdeptcd	= "";	//����μ��ڵ�
	private String	inputusrid	= "";	//�Է´����ID
	private int		seq			= 0;	//�Ϸù�ȣ
	private String	gubun		= "";	//����/���α���(����1, ����2)
	private String	sancresult	= "";	//������
	private String	sancusrid	= "";	//������ID
	private String	sancusrnm	= "";	//�����ڼ���
	private String	sancyn		= "";	//���翩��(�Ϸ�1, ����0)
	private String	sancdt		= "";	//�����Ͻ�
	private String	submitdt	= "";	//����Ͻ�
	private String	crtdt		= "";	//�����Ͻ�
	private String	crtusrid	= "";	//�������ڵ�
	private String	uptdt		= "";	//�����Ͻ�
	private String	uptusrid	= "";	//�������ڵ�
	
	private int		reqformno	= 0;	//��û��Ĺ�
	private	int		reqseq		= 0;	//�Ϸù�ȣ
	
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