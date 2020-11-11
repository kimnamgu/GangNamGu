/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ��û���� actionform
 * ����:
 */
package nexti.ejms.sinchung.form;

import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class DataForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int reqformno    = 0;    //��û��Ĺ�ȣ
	private int reqseq		 = 0;    //��û��ȣ
	private String sessi     = "";   //����ID	
	private String presentnm = "";   //��û��
	private String presentid = "";   //��û��ID
	private String presentsn = "";   //�ֹι�ȣ
	private String presentbd = "";   //�������
	private String position  = "";   //�Ҽ�
	private String duty      = "";   //����
	private String zip       = "";   //�����ȣ
	private String addr1     = "";   //�ּ�1
	private String addr2     = "";   //�ּ�2
	private String email     = "";   //email
	private String tel       = "";   //��ȭ��ȣ
	private String cel       = "";   //�޴���ȭ��ȣ
	private String fax       = "";   //fax��ȣ
	private String state     = "";   //�������
	private String statenm   = "";   //������¸�Ī
	private String crtdt     = "";   //�����Ͻ�
	private String crtusrid  = "";   //������ID
	private String uptdt     = "";   //�����Ͻ�
	private String uptusrid  = "";   //������ID
	private String lastsanc  = "";   //���������� ����
	
	private List   anscontList = null;            //�߰� �׸� ��û���� 	
	private String mode      = "";                //����(s), ����(u)
	private int[] formseq;                        //���׹�ȣ
	private String[] radioans = new String[20];   //���� ��ư �Է°�	
	private String[] chkans;                      //üũBOX �Է°�	
	private String[] txtans;                      //�ܴ��� �Է°�	
	private String[] areaans;                     //�幮�� �Է°�	
	private String[] other;                       //��Ÿ�ǰ� �Է°�	
	
	private String title     = "";      //����
	private String basictype = "";      //�Է±⺻����
	private String coldeptnm = "";      //������� �μ���Ī
	private String chrgusrnm = "";      //���������
	private String depttel   = "";      //�����μ� ��ȭ��ȣ
	private List articleList = null;    //�׸��� ����	
	private List sancList1 	 = null;  	//������ ���
	private List sancList2 	 = null;  	//������ ��� 
	private String sancusrinfo	= "";	//�������系��
	
	private FormFile attachFile = null;	//÷������
	private String attachFileYN = "";	//÷�����ϻ�������
	private int		fileseq		= 0;	//÷�����Ϲ�ȣ
	private String	filenm		= "";	//÷�����ϸ�
	private String	originfilenm = "";	//�������ϸ�
	private int		filesize	= 0;	//����ũ��
	private String	ext			= "";	//Ȯ���
	private int		ord			= 0;	//���ļ���
	
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
	public String getAttachFileYN() {
		return attachFileYN;
	}
	public void setAttachFileYN(String attachFileYN) {
		this.attachFileYN = attachFileYN;
	}
	public FormFile getAttachFile() {
		return attachFile;
	}
	public void setAttachFile(FormFile attachFile) {
		this.attachFile = attachFile;
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
	public String getChrgusrnm() {
		return chrgusrnm;
	}
	public void setChrgusrnm(String chrgusrnm) {
		this.chrgusrnm = chrgusrnm;
	}
	public String getColdeptnm() {
		return coldeptnm;
	}
	public void setColdeptnm(String coldeptnm) {
		this.coldeptnm = coldeptnm;
	}
	public String getDepttel() {
		return depttel;
	}
	public void setDepttel(String depttel) {
		this.depttel = depttel;
	}
	public String getLastsanc() {
		return lastsanc;
	}
	public void setLastsanc(String lastsanc) {
		this.lastsanc = lastsanc;
	}
	public String getStatenm() {
		return statenm;
	}
	public void setStatenm(String statenm) {
		this.statenm = statenm;
	}
	public List getArticleList() {
		return articleList;
	}
	public void setArticleList(List articleList) {
		this.articleList = articleList;
	}
	public String getBasictype() {
		return basictype;
	}
	public void setBasictype(String basictype) {
		this.basictype = basictype;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int[] getFormseq() {
		return formseq;
	}
	public void setFormseq(int[] formseq) {
		this.formseq = formseq;
	}
	public List getAnscontList() {
		return anscontList;
	}
	public void setAnscontList(List anscontList) {
		this.anscontList = anscontList;
	}
	public int getReqseq() {
		return reqseq;
	}
	public void setReqseq(int reqseq) {
		this.reqseq = reqseq;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String[] getAreaans() {
		return areaans;
	}
	public void setAreaans(String[] areaans) {
		this.areaans = areaans;
	}
	public String[] getChkans() {
		return chkans;
	}
	public void setChkans(String[] chkans) {
		this.chkans = chkans;
	}
	public String[] getOther() {
		return other;
	}
	public void setOther(String[] other) {
		this.other = other;
	}
	public String[] getRadioans() {
		return radioans;
	}
	public void setRadioans(String[] radioans) {
		this.radioans = radioans;
	}
	public String[] getTxtans() {
		return txtans;
	}
	public void setTxtans(String[] txtans) {
		this.txtans = txtans;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getCel() {
		return cel;
	}
	public void setCel(String cel) {
		this.cel = cel;
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
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPresentid() {
		return presentid;
	}
	public void setPresentid(String presentid) {
		this.presentid = presentid;
	}
	public String getPresentnm() {
		return presentnm;
	}
	public void setPresentnm(String presentnm) {
		this.presentnm = presentnm;
	}
	public String getPresentsn() {
		return presentsn;
	}
	public void setPresentsn(String presentsn) {
		this.presentsn = presentsn;
	}
	public String getPresentbd() {
		return presentbd;
	}
	public void setPresentbd(String presentbd) {
		this.presentbd = presentbd;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public int getReqformno() {
		return reqformno;
	}
	public void setReqformno(int reqformno) {
		this.reqformno = reqformno;
	}
	public String getSessi() {
		return sessi;
	}
	public void setSessi(String sessi) {
		this.sessi = sessi;
	}
	public String getSancusrinfo() {
		return sancusrinfo;
	}
	public void setSancusrinfo(String sancusrinfo) {
		this.sancusrinfo = sancusrinfo;
	}	
}