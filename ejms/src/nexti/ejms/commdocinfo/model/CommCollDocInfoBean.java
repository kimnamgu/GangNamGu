/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������չ������� bean
 * ����:
 */
package nexti.ejms.commdocinfo.model;

import org.apache.struts.upload.FormFile;

public class CommCollDocInfoBean {
	private int seqno;				//����
	private int formseq;			//
	private String formkind;		//
	private int sysdocno;			//�ý��۹�����ȣ
	private String doctitle;		//��������
	private String basicdate;		//�ڷ������
	private String basicdate1;		//�ڷ������(yyyy�� mm�� dd��)
	private String deliverydt;		//������(yyyy�� mm�� dd��)
	private String submitdt;		//�����
	private String docno;			//���չ�����ȣ
	private String sancusrinfo;		//��������������
	private String coldeptcd;		//���պμ��ڵ�
	private String coldeptnm;		//���պμ���
	private int    chrgunitcd;      //���մ������ڵ�
	private String chrgunitnm;		//���մ�������
	private String chrgusrcd;		//���մ�����ڵ�
	private String chrgusrnm;		//���մ���ڸ�
	private String coldepttel;		//���մ��μ���ȭ��ȣ
	private String basis;			//���ñٰ�
	private String summary;			//���հ���
	private String enddt;			//��������
	private String enddt_date;      //�����Ͻ�(yyyy-mm-dd)
	private String enddt_hour;      //��������(hh24)
	private String enddt_min;      	//������(mi)
	private String endcomment;		//�����˸���
	private String targetdeptnm;	//���մ��μ���
	private String openinput;		//�����Է�(Ÿ�μ��ڷẸ��)
	private String sancrulecd;		//����ÿ�û�����ڵ�
	private String sancrule;		//����ÿ�û����
	private String basicdt;			//�ڷ������
	private String opendt;			//��������
	private String opennm;			//������
	private String docstate;		//��������
	private String gubun;			//�����ڷ�1,  ��������2
	private FormFile attachFile;	//÷������
	private String attachFileYN;	//÷�����ϻ���
	private int    fileseq;			//÷�����Ϲ�ȣ
	private String filenm;			//÷�����ϸ�
	private String originfilenm;	//�������ϸ�
	private int    filesize;		//����ũ��
	private String ext;				//Ȯ����
	private	int    ord;				//���ļ���

	public CommCollDocInfoBean() {
		this.seqno			= 0;
		this.formseq		= 0;
		this.formkind		= "";
		this.sysdocno		= 0;
		this.doctitle		= "";
		this.basicdate		= "";
		this.basicdate1		= "";
		this.deliverydt		= "";
		this.docno			= "";
		this.sancusrinfo	= "";
		this.coldeptcd		= "";
		this.coldeptnm		= "";
		this.chrgunitcd		= 0;
		this.chrgunitnm		= "";
		this.chrgusrcd		= "";
		this.chrgusrnm		= "";
		this.coldepttel		= "";
		this.basis			= "";
		this.summary		= "";
		this.enddt			= "";
		this.enddt_date     = "";
		this.enddt_hour		= "";
		this.enddt_min		= "";
		this.endcomment		= "";
		this.targetdeptnm	= "";
		this.openinput		= "";
		this.sancrulecd		= "";
		this.sancrule		= "";
		this.basicdt		= "";
		this.opendt			= "";
		this.opennm			= "";
		this.docstate		= "";
		this.gubun			= "";
		this.attachFile		= null;
		this.attachFileYN	= "";
		this.fileseq		= 0;
		this.filenm			= "";
		this.originfilenm 	= "";
		this.filesize		= 0;
		this.ext			= "";
		this.ord			= 0;
	}

	public String getOpeninput() {
		return openinput;
	}

	public void setOpeninput(String openinput) {
		this.openinput = openinput;
	}

	public String getChrgusrcd() {
		return chrgusrcd;
	}

	public void setChrgusrcd(String chrgusrcd) {
		this.chrgusrcd = chrgusrcd;
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

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	
	public String getOpennm() {
		return opennm;
	}

	public void setOpennm(String opennm) {
		this.opennm = opennm;
	}

	public String getOpendt() {
		return opendt;
	}

	public void setOpendt(String opendt) {
		this.opendt = opendt;
	}

	public int getSeqno() {
		return seqno;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	/**
	 * @return the basis
	 */
	public String getBasis() {
		return basis;
	}

	/**
	 * @param basis the basis to set
	 */
	public void setBasis(String basis) {
		this.basis = basis;
	}

	/**
	 * @return the chrgunitnm
	 */
	public String getChrgunitnm() {
		return chrgunitnm;
	}

	/**
	 * @param chrgunitnm the chrgunitnm to set
	 */
	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}

	/**
	 * @return the chrgusrnm
	 */
	public String getChrgusrnm() {
		return chrgusrnm;
	}

	/**
	 * @param chrgusrnm the chrgusrnm to set
	 */
	public void setChrgusrnm(String chrgusrnm) {
		this.chrgusrnm = chrgusrnm;
	}

	/**
	 * @return the coldeptnm
	 */
	public String getColdeptnm() {
		return coldeptnm;
	}

	/**
	 * @param coldeptnm the coldeptnm to set
	 */
	public void setColdeptnm(String coldeptnm) {
		this.coldeptnm = coldeptnm;
	}

	/**
	 * @return the deliverydt
	 */
	public String getDeliverydt() {
		return deliverydt;
	}

	/**
	 * @param deliverydt the deliverydt to set
	 */
	public void setDeliverydt(String deliverydt) {
		this.deliverydt = deliverydt;
	}

	/**
	 * @return the docno
	 */
	public String getDocno() {
		return docno;
	}

	/**
	 * @param docno the docno to set
	 */
	public void setDocno(String docno) {
		this.docno = docno;
	}

	/**
	 * @return the doctitle
	 */
	public String getDoctitle() {
		return doctitle;
	}

	/**
	 * @param doctitle the doctitle to set
	 */
	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}

	/**
	 * @return the endcomment
	 */
	public String getEndcomment() {
		return endcomment;
	}

	/**
	 * @param endcomment the endcomment to set
	 */
	public void setEndcomment(String endcomment) {
		this.endcomment = endcomment;
	}

	/**
	 * @return the enddt
	 */
	public String getEnddt() {
		return enddt;
	}

	/**
	 * @param enddt the enddt to set
	 */
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}

	/**
	 * @return the sancrule
	 */
	public String getSancrule() {
		return sancrule;
	}

	/**
	 * @param sancrule the sancrule to set
	 */
	public void setSancrule(String sancrule) {
		this.sancrule = sancrule;
	}

	/**
	 * @return the sancusrinfo
	 */
	public String getSancusrinfo() {
		return sancusrinfo;
	}

	/**
	 * @param sancusrinfo the sancusrinfo to set
	 */
	public void setSancusrinfo(String sancusrinfo) {
		this.sancusrinfo = sancusrinfo;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the targetdeptnm
	 */
	public String getTargetdeptnm() {
		return targetdeptnm;
	}

	/**
	 * @param targetdeptnm the targetdeptnm to set
	 */
	public void setTargetdeptnm(String targetdeptnm) {
		this.targetdeptnm = targetdeptnm;
	}

	/**
	 * @return the basicdt
	 */
	public String getBasicdt() {
		return basicdt;
	}

	/**
	 * @param basicdt the basicdt to set
	 */
	public void setBasicdt(String basicdt) {
		this.basicdt = basicdt;
	}

	public String getSubmitdt() {
		return submitdt;
	}

	public void setSubmitdt(String submitdt) {
		this.submitdt = submitdt;
	}

	public String getColdeptcd() {
		return coldeptcd;
	}

	public void setColdeptcd(String coldeptcd) {
		this.coldeptcd = coldeptcd;
	}

	public String getBasicdate() {
		return basicdate;
	}

	public void setBasicdate(String basicdate) {
		this.basicdate = basicdate;
	}

	public String getBasicdate1() {
		return basicdate1;
	}

	public void setBasicdate1(String basicdate1) {
		this.basicdate1 = basicdate1;
	}

	public int getChrgunitcd() {
		return chrgunitcd;
	}

	public void setChrgunitcd(int chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
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

	public int getSysdocno() {
		return sysdocno;
	}

	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}

	public String getDocstate() {
		return docstate;
	}

	public void setDocstate(String docstate) {
		this.docstate = docstate;
	}

	public String getSancrulecd() {
		return sancrulecd;
	}

	public void setSancrulecd(String sancrulecd) {
		this.sancrulecd = sancrulecd;
	}

	/**
	 * @return the coldepttel
	 */
	public String getColdepttel() {
		return coldepttel;
	}

	/**
	 * @param coldepttel the coldepttel to set
	 */
	public void setColdepttel(String coldepttel) {
		this.coldepttel = coldepttel;
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
