/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���չ��� bean
 * ����:
 */
package nexti.ejms.colldoc.model;

import org.apache.struts.upload.FormFile;

public class ColldocBean {
	
	private int 	seqno 		= 0; 	//����
	
	private int		formcount	= 0;	//��İ���
	private int		formseq		= 0;	//����Ϸù�ȣ
	private String	formtitle	= "";	//�������
	private String	formkind	= "";	//�������
	
	private int		sysdocno	= 0;	//�ý��۹�����ȣ
	private String	docno		= "";	//������ȣ
	private String	doctitle	= "";	//��������
	private String	docgbn		= "";	//��������
	private String	basicdate	= "";	//�ڷ��������
	private String	submitdate	= "";	//�������
	private String	basis		= "";	//���ñٰ�
	private String	summary		= "";	//���հ���
	private String	enddt		= "";	//�����Ͻ�
	private String	enddt_date	= "";	//������
	private String	enddt_hour	= "";	//������
	private String	enddt_min	= "";	//������
	private String	endcomment	= "";	//�����˸���
	private String	sancrule	= "";	//�����ڷ��������
	private String	docstate	= "";	//��������
	private String	deliverydt	= "";	//����Ͻ�
	private String	tgtdeptnm	= "";	//����μ���Ī�Ǵ±׷��Ī
	private String	coldeptcd	= "";	//���պμ��ڵ�
	private String	coldeptnm	= "";	//���պμ���
	private int		chrgunitcd	= 0;	//���մ������ڵ�
	private String	chrgunitnm	= "";	//���մ�������
	private String	chrgusrcd	= "";	//���մ�����ڵ�
	private String	chrgusrnm	= "";	//���մ���ڸ�
	private String	opendt		= "";	//��������
	private String	searchkey	= "";	//�˻�Ű����
	private String	delyn		= "";	//�ֱٸ�Ͽ�����������
	private String	openinput	= "";	//�����Է�(Ÿ�μ��ڷẸ��)
	private String	crtdt		= "";	//�����Ͻ�
	private String	crtusrid	= "";	//�������ڵ�
	private String	uptdt		= "";	//�����Ͻ�
	private String	uptusrid	= "";	//�������ڵ�
	private String	sancusrnm1	= "";	//���պμ� ������ �̸�
	private String	sancusrnm2	= "";	//���պμ� ������ �̸�  
	private String 	cnt			= "";   //����Ǽ��� ��ΰǼ�
	private String 	formkindname = "";  //���������
	private String  formcomment	= "";	//��İ���
	private String  formhtml	= "";	//��ı���
	private FormFile attachFile	= null;	//÷������
	private String attachFileYN	= "";	//÷�����ϻ���
	private int		fileseq		= 0;	//÷�����Ϲ�ȣ
	private String	filenm		= "";	//÷�����ϸ�
	private String	originfilenm = "";	//�������ϸ�
	private int		filesize	= 0;	//����ũ��
	private String	ext			= "";	//Ȯ����
	private	int		ord			= 0;	//���ļ���
	
	public String getOpeninput() {
		return openinput;
	}
	public void setOpeninput(String openinput) {
		this.openinput = openinput;
	}
	public FormFile getAttachFile() {
		return attachFile;
	}
	public void setAttachFile(FormFile attachFile) {
		this.attachFile = attachFile;
	}
	public String getAttachFileYN() {
		return attachFileYN;
	}
	public void setAttachFileYN(String attachFileYN) {
		this.attachFileYN = attachFileYN;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getFilenm() {
		return filenm;
	}
	public void setFilenm(String filenm) {
		this.filenm = filenm;
	}
	public int getFileseq() {
		return fileseq;
	}
	public void setFileseq(int fileseq) {
		this.fileseq = fileseq;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public String getOriginfilenm() {
		return originfilenm;
	}
	public void setOriginfilenm(String originfilenm) {
		this.originfilenm = originfilenm;
	}
	public String getFormcomment() {
		return formcomment;
	}
	public void setFormcomment(String formcomment) {
		this.formcomment = formcomment;
	}
	public String getFormhtml() {
		return formhtml;
	}
	public void setFormhtml(String formhtml) {
		this.formhtml = formhtml;
	}
	public String getFormkindname() {
		return formkindname;
	}
	public void setFormkindname(String formkindname) {
		this.formkindname = formkindname;
	}
	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	public String getBasicdate() {
		return basicdate;
	}
	public void setBasicdate(String basicdate) {
		this.basicdate = basicdate;
	}
	public String getBasis() {
		return basis;
	}
	public void setBasis(String basis) {
		this.basis = basis;
	}
	public String getChrgunitnm() {
		return chrgunitnm;
	}
	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}
	public String getChrgusrnm() {
		return chrgusrnm;
	}
	public void setChrgusrnm(String chrgusrnm) {
		this.chrgusrnm = chrgusrnm;
	}
	public String getColdeptcd() {
		return coldeptcd;
	}
	public void setColdeptcd(String coldeptcd) {
		this.coldeptcd = coldeptcd;
	}
	public String getColdeptnm() {
		return coldeptnm;
	}
	public void setColdeptnm(String coldeptnm) {
		this.coldeptnm = coldeptnm;
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
	public String getDeliverydt() {
		return deliverydt;
	}
	public void setDeliverydt(String deliverydt) {
		this.deliverydt = deliverydt;
	}
	public String getDelyn() {
		return delyn;
	}
	public void setDelyn(String delyn) {
		this.delyn = delyn;
	}
	public String getDocgbn() {
		return docgbn;
	}
	public void setDocgbn(String docgbn) {
		this.docgbn = docgbn;
	}
	public String getDocno() {
		return docno;
	}
	public void setDocno(String docno) {
		this.docno = docno;
	}
	public String getDocstate() {
		return docstate;
	}
	public void setDocstate(String docstate) {
		this.docstate = docstate;
	}
	public String getDoctitle() {
		return doctitle;
	}
	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
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
	public String getOpendt() {
		return opendt;
	}
	public void setOpendt(String opendt) {
		this.opendt = opendt;
	}
	public String getSancrule() {
		return sancrule;
	}
	public void setSancrule(String sancrule) {
		this.sancrule = sancrule;
	}
	public String getSearchkey() {
		return searchkey;
	}
	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}
	public String getSubmitdate() {
		return submitdate;
	}
	public void setSubmitdate(String submitdate) {
		this.submitdate = submitdate;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getSysdocno() {
		return sysdocno;
	}
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
	public String getTgtdeptnm() {
		return tgtdeptnm;
	}
	public void setTgtdeptnm(String tgtdeptnm) {
		this.tgtdeptnm = tgtdeptnm;
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
	public String getSancusrnm1() {
		return sancusrnm1;
	}
	public void setSancusrnm1(String sancusrnm1) {
		this.sancusrnm1 = sancusrnm1;
	}
	public String getSancusrnm2() {
		return sancusrnm2;
	}
	public void setSancusrnm2(String sancusrnm2) {
		this.sancusrnm2 = sancusrnm2;
	}
	public int getSeqno() {
		return seqno;
	}
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}
	public String getEnddt_date() {
		return enddt_date;
	}
	public void setEnddt_date(String enddt_date) {
		this.enddt_date = enddt_date;
	}
	public String getEnddt_hour() {
		return enddt_hour;
	}
	public void setEnddt_hour(String enddt_hour) {
		this.enddt_hour = enddt_hour;
	}
	public String getEnddt_min() {
		return enddt_min;
	}
	public void setEnddt_min(String enddt_min) {
		this.enddt_min = enddt_min;
	}
	public int getChrgunitcd() {
		return chrgunitcd;
	}
	public void setChrgunitcd(int chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
	}
	public int getFormcount() {
		return formcount;
	}
	public void setFormcount(int formcount) {
		this.formcount = formcount;
	}
	public String getFormkind() {
		return formkind;
	}
	public void setFormkind(String formkind) {
		this.formkind = formkind;
	}
	public String getFormtitle() {
		return formtitle;
	}
	public void setFormtitle(String formtitle) {
		this.formtitle = formtitle;
	}
	public int getFormseq() {
		return formseq;
	}
	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}
	public String getChrgusrcd() {
		return chrgusrcd;
	}
	public void setChrgusrcd(String chrgusrcd) {
		this.chrgusrcd = chrgusrcd;
	}
}
