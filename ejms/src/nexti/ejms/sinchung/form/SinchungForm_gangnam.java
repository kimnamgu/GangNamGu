/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� actionform
 * ����:
 */
package nexti.ejms.sinchung.form;

import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import nexti.ejms.sinchung.model.ReqMstBean;

public class SinchungForm_gangnam extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int reqformno    = 0;     //��û��Ĺ�ȣ
	private int reqseq       = 0;     //��û��ȣ
	private String sessi     = "";    //����ID
	private String title     = "";    //����
	private String strdt     = "";    //������
	private String enddt     = "";    //������
	private String coldeptcd = "";    //���μ��ڵ�
	private String coldeptnm = "";    //���μ���
	private String coltel    = "";    //�μ���ȭ��ȣ
	private String chrgusrid = "";    //������ڵ�
	private String chrgusrnm = "";    //����ڸ�	
	private String summary   = "";    //��û�� ����
	private String range  	 = "2";   //��û����(����1,�����2)
	private String duplicheck = "1";  //�ߺ���ûüũ(����0,��Ű1,���̵�2)
	private String rangedetail = "21"; //������������
	private String imgpreview = "2";  //÷���̹���ǥ��(ǥ��1,�����2)
	private int    limitcount = 0;    //��ǥ��û��
	private String sancneed  = "N";    //�����ʿ俩��(Y:�����ʿ�, N:������ʿ�)
	private String basictype = "";    //�Է±⺻��������
	private String[] btype;           //�Է±⺻����(�迭)
	private String headcont  = "";    //�Ӹ���
	private String tailcont  = "";    //������
	private String crtdt     = "";    //�����Ͻ�
	private String crtusrid  = "";    //�������ڵ�
	private String uptdt     = "";    //�����Ͻ�
	private String uptusrid  = "";    //�������ڵ�	
	private String mode      = "";    //(make:�׸񸸵��, del:����, add:�߰�, comp:�Ϸ�, prev:�̸�����)
	private String usedFL    = "";    //������Ŀ��� �������� ����(Y)	
	private String closefl   = "";    //��������
	private String viewfl    = "";    //List���� ���Կ���(Y:List���� ����)
	
	private int    posscroll = 0;     //��ũ�ѹ� ��ġ
	private int    sumcnt    = 0;     //���������� �Ǽ�
	private int    delseq    = 0;     //�����׸��ȣ
	
	private int acnt         = 0;                 //���� �Ѱ���
	private int	examcount    = 0;    			  //�����
	private List articleList = null;              //���׸��
	private int[] formseq;                     	  //���׹�ȣ
	private int[] changeFormseq;				  //���׹�ȣ��� : ���׼���������
	private String[] formtitle;                   //��������
	private String[] require;                     //�ʼ��Է¿��� (Y:�ʼ�, N:����)
	private String[] formtype = new String[20];   //�Է�����(1:���� 2:���� 3:�ܴ� 4:�幮)
	private String[] security;                    //����ó������(Y:����ó��, N:����)
	private String[] examcont;                    //��������
	private String[] examtype;	                  //��Ÿüũ����(Y:����, N:����)
	private String[] helpexp;		              //�߰�����
	private FormFile[] formtitleFile	= new FormFile[20];		//��������÷��
	private FormFile[] examcontFile		= new FormFile[1000];	//��������÷��
	private String[] formtitleFileYN	= new String[20];		//��������÷�ο���
	private String[] examcontFileYN		= new String[1000];		//��������÷�ο���
		
	private ReqMstBean rbean = null;    //��û����
	private List sancList1 	 = null;  	//������ ���
	private List sancList2 	 = null;  	//������ ��� 
	private String sancusrinfo	= "";	//�������系��
	
	public String getDuplicheck() {
		return duplicheck;
	}
	public void setDuplicheck(String duplicheck) {
		this.duplicheck = duplicheck;
	}
	public int getLimitcount() {
		return limitcount;
	}
	public void setLimitcount(int limitcount) {
		this.limitcount = limitcount;
	}
	public String getRangedetail() {
		return rangedetail;
	}
	public void setRangedetail(String rangedetail) {
		this.rangedetail = rangedetail;
	}
	public String getImgpreview() {
		return imgpreview;
	}
	public void setImgpreview(String imgpreview) {
		this.imgpreview = imgpreview;
	}
	public int[] getChangeFormseq() {
		return changeFormseq;
	}
	public void setChangeFormseq(int[] changeFormseq) {
		this.changeFormseq = changeFormseq;
	}
	public String getSancusrinfo() {
		return sancusrinfo;
	}
	public void setSancusrinfo(String sancusrinfo) {
		this.sancusrinfo = sancusrinfo;
	}
	public FormFile[] getExamcontFile() {
		return examcontFile;
	}
	public void setExamcontFile(FormFile[] examcontFile) {
		this.examcontFile = examcontFile;
	}
	public String[] getExamcontFileYN() {
		return examcontFileYN;
	}
	public void setExamcontFileYN(String[] examcontFileYN) {
		this.examcontFileYN = examcontFileYN;
	}
	public FormFile[] getFormtitleFile() {
		return formtitleFile;
	}
	public void setFormtitleFile(FormFile[] formtitleFile) {
		this.formtitleFile = formtitleFile;
	}
	public String[] getFormtitleFileYN() {
		return formtitleFileYN;
	}
	public void setFormtitleFileYN(String[] formtitleFileYN) {
		this.formtitleFileYN = formtitleFileYN;
	}
	public String getViewfl() {
		return viewfl;
	}
	public void setViewfl(String viewfl) {
		this.viewfl = viewfl;
	}
	public String getSancneed() {
		return sancneed;
	}
	public void setSancneed(String sancneed) {
		this.sancneed = sancneed;
	}
	public int getSumcnt() {
		return sumcnt;
	}
	public void setSumcnt(int sumcnt) {
		this.sumcnt = sumcnt;
	}
	public int getPosscroll() {
		return posscroll;
	}
	public void setPosscroll(int posscroll) {
		this.posscroll = posscroll;
	}
	public String getClosefl() {
		return closefl;
	}
	public void setClosefl(String closefl) {
		this.closefl = closefl;
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
	public ReqMstBean getRbean() {
		return rbean;
	}
	public void setRbean(ReqMstBean rbean) {
		this.rbean = rbean;
	}
	public int getReqseq() {
		return reqseq;
	}
	public void setReqseq(int reqseq) {
		this.reqseq = reqseq;
	}
	public String getUsedFL() {
		return usedFL;
	}
	public void setUsedFL(String usedFL) {
		this.usedFL = usedFL;
	}
	public int getDelseq() {
		return delseq;
	}
	public void setDelseq(int delseq) {
		this.delseq = delseq;
	}
	public int[] getFormseq() {
		return formseq;
	}
	public void setFormseq(int[] formseq) {
		this.formseq = formseq;
	}
	public String getSessi() {
		return sessi;
	}
	public void setSessi(String sessi) {
		this.sessi = sessi;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public int getAcnt() {
		return acnt;
	}
	public void setAcnt(int acnt) {
		this.acnt = acnt;
	}
	public String getColtel() {
		return coltel;
	}
	public void setColtel(String coltel) {
		this.coltel = coltel;
	}
	public String[] getBtype() {
		return btype;
	}
	public void setBtype(String[] btype) {
		this.btype = btype;
	}
	public String[] getFormtitle() {
		return formtitle;
	}
	public void setFormtitle(String[] formtitle) {
		this.formtitle = formtitle;
	}
	public String[] getHelpexp() {
		return helpexp;
	}
	public void setHelpexp(String[] helpexp) {
		this.helpexp = helpexp;
	}
	public String[] getRequire() {
		return require;
	}
	public void setRequire(String[] require) {
		this.require = require;
	}
	public String[] getSecurity() {
		return security;
	}
	public void setSecurity(String[] security) {
		this.security = security;
	}
	public String[] getExamcont() {
		return examcont;
	}
	public void setExamcont(String[] examcont) {
		this.examcont = examcont;
	}
	public String[] getExamtype() {
		return examtype;
	}
	public void setExamtype(String[] examtype) {
		this.examtype = examtype;
	}
	public String[] getFormtype() {
		return formtype;
	}
	public void setFormtype(String[] formtype) {
		this.formtype = formtype;
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
	public String getChrgusrid() {
		return chrgusrid;
	}
	public void setChrgusrid(String chrgusrid) {
		this.chrgusrid = chrgusrid;
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
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getHeadcont() {
		return headcont;
	}
	public void setHeadcont(String headcont) {
		this.headcont = headcont;
	}	
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public int getReqformno() {
		return reqformno;
	}
	public void setReqformno(int reqformno) {
		this.reqformno = reqformno;
	}
	public String getStrdt() {
		return strdt;
	}
	public void setStrdt(String strdt) {
		this.strdt = strdt;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getTailcont() {
		return tailcont;
	}
	public void setTailcont(String tailcont) {
		this.tailcont = tailcont;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public int getExamcount() {
		return examcount;
	}
	public void setExamcount(int examcount) {
		this.examcount = examcount;
	}
}