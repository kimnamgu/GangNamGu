/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���չ��� actionform
 * ����:
 */
package nexti.ejms.colldoc.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class ColldocForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int		seqno		= 0;		//���� 
	private String	searchvalue	= "";		//�˻���
	private int		page		= 1;		//������
	private List	listcolldoc	= null;		//���չ�������Ʈ
	private String 	selyear		= "";		//��������
	private String 	initentry	= "first";	//�ʱ����Կ���
	private String[] listdelete	= null;		//�������� �ý��۹�����ȣ
	
	private int		type		= 0;	//�ۼ�����
	private int		formcount	= 0;	//��İ���
	private int		formseq		= 0;	//����Ϸù�ȣ
	private List	listcolldocform	= null;	//���վ�ĸ���Ʈ
	
	private int		mode		= 1;	//�����ۼ�(1), ������������(2), ���������(3)
	private String	user_id		= "";	//����� ���̵�
	private String	user_name	= "";	//����� �̸�
	private String	dept_code	= "";	//����� �μ��ڵ�
	private String	dept_name	= "";	//����� �μ���
	private String	d_tel		= "";	//����� �μ���ȭ��ȣ
		
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
	private FormFile attachFile	= null;	//÷������
	private String attachFileYN	= "";	//÷�����ϻ���
	private int		fileseq		= 0;	//÷�����Ϲ�ȣ
	private String	filenm		= "";	//÷�����ϸ�
	private String	originfilenm = "";	//�������ϸ�

	private String 	sch_old_deptcd	= "";			//�μ�����ȸ����
	private String  sch_deptcd		= "";			//�μ�����ȸ����
	private String 	sch_deptnm		= "";			//�μ�����ȸ����
	private String  sch_old_userid	= "";			//�������ȸ����
	private String  sch_userid		= "";			//�������ȸ����
	private String 	sch_usernm		= "";			//�������ȸ����
	
	public String getOpeninput() {
		return openinput;
	}
	public void setOpeninput(String openinput) {
		this.openinput = openinput;
	}
	public String getSch_deptcd() {
		return sch_deptcd;
	}
	public void setSch_deptcd(String sch_deptcd) {
		this.sch_deptcd = sch_deptcd;
	}

	public String getSch_deptnm() {
		return sch_deptnm;
	}

	public void setSch_deptnm(String sch_deptnm) {
		this.sch_deptnm = sch_deptnm;
	}

	public String getSch_old_deptcd() {
		return sch_old_deptcd;
	}

	public void setSch_old_deptcd(String sch_old_deptcd) {
		this.sch_old_deptcd = sch_old_deptcd;
	}

	public String getSch_old_userid() {
		return sch_old_userid;
	}

	public void setSch_old_userid(String sch_old_userid) {
		this.sch_old_userid = sch_old_userid;
	}

	public String getSch_userid() {
		return sch_userid;
	}

	public void setSch_userid(String sch_userid) {
		this.sch_userid = sch_userid;
	}

	public String getSch_usernm() {
		return sch_usernm;
	}

	public void setSch_usernm(String sch_usernm) {
		this.sch_usernm = sch_usernm;
	}

	public String getAttachFileYN() {
		return attachFileYN;
	}

	public void setAttachFileYN(String attachFileYN) {
		this.attachFileYN = attachFileYN;
	}

	public int getFileseq() {
		return fileseq;
	}

	public void setFileseq(int fileseq) {
		this.fileseq = fileseq;
	}

	public String getFilenm() {
		return filenm;
	}

	public void setFilenm(String filenm) {
		this.filenm = filenm;
	}

	public String getOriginfilenm() {
		return originfilenm;
	}

	public void setOriginfilenm(String originfilenm) {
		this.originfilenm = originfilenm;
	}

	public FormFile getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(FormFile attachFile) {
		this.attachFile = attachFile;
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

	public List getListcolldoc() {
		return listcolldoc;
	}

	public void setListcolldoc(List listcolldoc) {
		this.listcolldoc = listcolldoc;
	}

	public String[] getListdelete() {
		return listdelete;
	}

	public void setListdelete(String[] listdelete) {
		this.listdelete = listdelete;
	}

	public String getOpendt() {
		return opendt;
	}

	public void setOpendt(String opendt) {
		this.opendt = opendt;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSancrule() {
		return sancrule;
	}

	public void setSancrule(String sancrule) {
		this.sancrule = sancrule;
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

	public String getSearchkey() {
		return searchkey;
	}

	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}

	public String getSearchvalue() {
		return searchvalue;
	}

	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
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

	public String getD_tel() {
		return d_tel;
	}

	public void setD_tel(String d_tel) {
		this.d_tel = d_tel;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getFormcount() {
		return formcount;
	}

	public void setFormcount(int formcount) {
		this.formcount = formcount;
	}

	public List getListcolldocform() {
		return listcolldocform;
	}

	public void setListcolldocform(List listcolldocform) {
		this.listcolldocform = listcolldocform;
	}

	public int getFormseq() {
		return formseq;
	}

	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}

	public String getInitentry() {
		return initentry;
	}

	public void setInitentry(String initentry) {
		this.initentry = initentry;
	}

	public String getSelyear() {
		return selyear;
	}

	public void setSelyear(String selyear) {
		this.selyear = selyear;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getChrgusrcd() {
		return chrgusrcd;
	}

	public void setChrgusrcd(String chrgusrcd) {
		this.chrgusrcd = chrgusrcd;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {return null;}
	public void reset(ActionMapping mapping, HttpServletRequest request) {}
}