/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������չ������� actionform
 * ����:
 */
package nexti.ejms.commdocinfo.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class CommCollDocInfoForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private int	   sysdocno			= 0;		//�ý��۹�����ȣ
	private int		formseq			= 0;		//��Ĺ�ȣ
	private String	formkind		= "";		//�������
	private String doctitle			= "";		//��������
	private String basicdate		= "";		//�ڷ������
	private String basicdate1		= "";		//�ڷ������(yyyy�� mm�� dd��)
	private String deliverydt		= "";		//������
	private String submitdt			= "";       //�����
	private String docno			= "";		//���չ�����ȣ
	private String sancusrinfo		= "";		//��������������
	private String coldeptcd		= "";		//���պμ��ڵ�
	private String coldeptnm		= "";		//���պμ���
	private int chrgunitcd			= 0;       	//���մ������ڵ�
	private String chrgunitnm		= "";		//���մ�������
	private String chrgusrcd		= "";		//���մ�����ڵ�
	private String chrgusrnm		= "";		//���մ���ڸ�
	private String coldepttel		= "";		//���մ��μ���ȭ��ȣ
	private String basis			= "";		//���ñٰ�
	private String summary			= "";		//���հ���
	private String enddt			= "";		//��������
	private String enddt_date		= "";       //�����Ͻ�(yyyy-mm-dd)
	private String enddt_hour		= "";       //��������(hh24)
	private String enddt_min		= "";      	//������(mi)
	private String endcomment		= "";		//�����˸���
	private String targetdeptnm		= "";		//���մ��μ���
	private String sancrulecd		= "";       //����ÿ�û�����ڵ�
	private String sancrule			= "";		//����ÿ�û����
	private String basicdt			= "";		//�ڷ������
	private String docstate			= "";		//��������
	private String opendt			= "";		//��������
	private String opennm			= "";		//������
	private String openinput		= "";		//�����Է�(Ÿ�μ��ڷẸ��)
	
	private String sancgbn          = "";       //��������(1:��ι���, 2:���⹮��) 
	private int    appCnt			= 0;        //�����ؾ��� �Ǽ�
	
	//commTreateForm ���� ����
	private String appntusrnm 		= "";		//�μ����Է´���ڸ�
	private String appntdeptnm 		= "";		//�μ������պμ���
	private String sancusrnm1 		= "";		//�����ڸ�
	private String sancusrnm2 		= "";		//�����ڸ�
	private String tcnt 			= "";		//������
	private String scnt 			= "";		//����Ϸ�
	private String fcnt 			= "";		//������
	private String docstatenm		= "";		//�������¸�
	
	private String inputdeptnm		= "";		//�Էºμ���
	private String inputdeptcd		= "";		//�Էºμ��ڵ�
	private String submitstate		= "";		//�������
	
	private String inputstate		= "";		//�Է¿Ϸᱸ��
	private String inputcomp		= "";		//�Է¿Ϸ��Ͻ�
	 
	private String inputusrnm		= "";		//�Է´���ڸ�
	private List inputuser1 		= null; 	//�Է´���� ���
	private List inputuser2 		= null; 	//���Է´���� ���
	private List sancList1 			= null;  	//������ ���
	private List sancList2 			= null;  	//������ ���
	//commTreateForm ���� ��

	private FormFile attachFile	= null;	//÷������
	private String attachFileYN	= "";	//÷�����ϻ���
	private int		fileseq		= 0;	//÷�����Ϲ�ȣ
	private String	filenm		= "";	//÷�����ϸ�
	private String	originfilenm = "";	//�������ϸ�
	
	private String	tgtdeptnm	= "";	//����μ���Ī�Ǵ±׷��Ī

	public String getOpeninput() {
		return openinput;
	}

	public void setOpeninput(String openinput) {
		this.openinput = openinput;
	}

	public String getInputusrnm() {
		return inputusrnm;
	}

	public void setInputusrnm(String inputusrnm) {
		this.inputusrnm = inputusrnm;
	}

	public String getInputdeptnm() {
		return inputdeptnm;
	}

	public void setInputdeptnm(String inputdeptnm) {
		this.inputdeptnm = inputdeptnm;
	}

	public String getChrgusrcd() {
		return chrgusrcd;
	}

	public void setChrgusrcd(String chrgusrcd) {
		this.chrgusrcd = chrgusrcd;
	}

	public String getAppntdeptnm() {
		return appntdeptnm;
	}

	public void setAppntdeptnm(String appntdeptnm) {
		this.appntdeptnm = appntdeptnm;
	}

	public String getTgtdeptnm() {
		return tgtdeptnm;
	}

	public void setTgtdeptnm(String tgtdeptnm) {
		this.tgtdeptnm = tgtdeptnm;
	}

	public String getAttachFileYN() {
		return attachFileYN;
	}

	public void setAttachFileYN(String attachFileYN) {
		this.attachFileYN = attachFileYN;
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

	public FormFile getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(FormFile attachFile) {
		this.attachFile = attachFile;
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

	public String getOriginfilenm() {
		return originfilenm;
	}

	public void setOriginfilenm(String originfilenm) {
		this.originfilenm = originfilenm;
	}

	public int getAppCnt() {
		return appCnt;
	}

	public void setAppCnt(int appCnt) {
		this.appCnt = appCnt;
	}

	public String getOpendt() {
		return opendt;
	}

	public void setOpendt(String opendt) {
		this.opendt = opendt;
	}

	public String getOpennm() {
		return opennm;
	}

	public void setOpennm(String opennm) {
		this.opennm = opennm;
	}

	public String getSancgbn() {
		return sancgbn;
	}

	public void setSancgbn(String sancgbn) {
		this.sancgbn = sancgbn;
	}

	public String getDocstate() {
		return docstate;
	}

	public void setDocstate(String docstate) {
		this.docstate = docstate;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
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

	public String getAppntusrnm() {
		return appntusrnm;
	}

	public void setAppntusrnm(String appntusrnm) {
		this.appntusrnm = appntusrnm;
	}

	public String getDocstatenm() {
		return docstatenm;
	}

	public void setDocstatenm(String docstatenm) {
		this.docstatenm = docstatenm;
	}

	public String getFcnt() {
		return fcnt;
	}

	public void setFcnt(String fcnt) {
		this.fcnt = fcnt;
	}

	public String getInputcomp() {
		return inputcomp;
	}

	public void setInputcomp(String inputcomp) {
		this.inputcomp = inputcomp;
	}

	public String getInputdeptcd() {
		return inputdeptcd;
	}

	public void setInputdeptcd(String inputdeptcd) {
		this.inputdeptcd = inputdeptcd;
	}

	public String getInputstate() {
		return inputstate;
	}

	public void setInputstate(String inputstate) {
		this.inputstate = inputstate;
	}

	public List getInputuser1() {
		return inputuser1;
	}

	public void setInputuser1(List inputuser1) {
		this.inputuser1 = inputuser1;
	}

	public List getInputuser2() {
		return inputuser2;
	}

	public void setInputuser2(List inputuser2) {
		this.inputuser2 = inputuser2;
	}

	public List getSancList1() {
		return sancList1;
	}

	public void setSancList1(List sancList1) {
		this.sancList1 = sancList1;
	}

	public List getSancList2() {
		return sancList2;
	}

	public void setSancList2(List sancList2) {
		this.sancList2 = sancList2;
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

	public String getScnt() {
		return scnt;
	}

	public void setScnt(String scnt) {
		this.scnt = scnt;
	}

	public String getSubmitstate() {
		return submitstate;
	}

	public void setSubmitstate(String submitstate) {
		this.submitstate = submitstate;
	}

	public String getTcnt() {
		return tcnt;
	}

	public void setTcnt(String tcnt) {
		this.tcnt = tcnt;
	}
}