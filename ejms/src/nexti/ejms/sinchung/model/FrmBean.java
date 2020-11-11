/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� bean
 * ����:
 */
package nexti.ejms.sinchung.model;

import java.util.List;

import org.apache.struts.upload.FormFile;

public class FrmBean  {
	
	private int reqformno    = 0;     //��û��Ĺ�ȣ
	private String sessi	 = "";    //���ǹ�ȣ	 
	private String title     = "";    //����
	private String strdt     = "";    //������
	private String enddt     = "";    //������
	private String coldeptcd = "";    //���μ��ڵ�
	private String coldeptnm = "";    //���μ���
	private String coltel 	 = "";    //���μ���
	private String chrgusrid = "";    //������ڵ�
	private String chrgusrnm = "";    //����ڸ�
	private String summary   = "";    //��û�� ����
	private String range	 = "";    //��û����(����1,���ͳ�2)
	private String rangedetail = "";  //������������
	private String imgpreview = "";	  //÷���̹���ǥ��(ǥ��1,�����2)
	private String duplicheck = "";   //�ߺ���ûüũ(����0,��Ű1,���̵�2)
	private int    limitcount = 0;    //��ǥ��û��
	private String sancneed  = "";    //�����ʿ俩��(Y:�����ʿ�, N:������ʿ�)
	private String basictype = "";    //�Է±⺻��������
	private String headcont  = "";    //�Ӹ���
	private String tailcont  = "";    //������
	private String crtdt     = "";    //�����Ͻ�
	private String crtusrid  = "";    //�������ڵ�
	private String uptdt     = "";    //�����Ͻ�
	private String uptusrid  = "";    //�������ڵ�
	private String closefl   = "";    //��������
	
	private int    bunho     = 0;     //����Ϸù�ȣ
	private int    notproc   = 0;     //��ó�� �Ǽ�
	private int    acnt      = 0;     //���� �Ѱ���
	private int	   examcount = 0;	  //�����
	private int    tday		 = 0;     //�����ʰ���
	
	private List   articleList = null; //���׸��
	private List	subFileList		= null;	//����÷�����ϸ���Ʈ
	private List	examFileList	= null;	//����÷�����ϸ���Ʈ
	
	private FormFile[] formtitleFile	= new FormFile[100];	//��������÷��
	private FormFile[] examcontFile		= new FormFile[2500];	//��������÷��
	private String[] formtitle;                   	//��������
	private String[] examcont;                    	//��������
	private String[] formtitleFileYN	= new String[100];		//��������÷�ο���
	private String[] examcontFileYN		= new String[2500];		//��������÷�ο���
	
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
	public List getExamFileList() {
		return examFileList;
	}
	public void setExamFileList(List examFileList) {
		this.examFileList = examFileList;
	}
	public List getSubFileList() {
		return subFileList;
	}
	public void setSubFileList(List subFileList) {
		this.subFileList = subFileList;
	}
	public String[] getExamcont() {
		return examcont;
	}
	public void setExamcont(String[] examcont) {
		this.examcont = examcont;
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
	public String[] getFormtitle() {
		return formtitle;
	}
	public void setFormtitle(String[] formtitle) {
		this.formtitle = formtitle;
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
	public int getTday() {
		return tday;
	}
	public void setTday(int tday) {
		this.tday = tday;
	}
	public String getSancneed() {
		return sancneed;
	}
	public void setSancneed(String sancneed) {
		this.sancneed = sancneed;
	}
	public String getClosefl() {
		return closefl;
	}
	public void setClosefl(String closefl) {
		this.closefl = closefl;
	}
	public int getAcnt() {
		return acnt;
	}
	public void setAcnt(int acnt) {
		this.acnt = acnt;
	}
	public String getSessi() {
		return sessi;
	}
	public void setSessi(String sessi) {
		this.sessi = sessi;
	}
	public List getArticleList() {
		return articleList;
	}
	public void setArticleList(List articleList) {
		this.articleList = articleList;
	}
	public int getBunho() {
		return bunho;
	}
	public void setBunho(int bunho) {
		this.bunho = bunho;
	}
	public int getNotproc() {
		return notproc;
	}
	public void setNotproc(int notproc) {
		this.notproc = notproc;
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
	public String getColtel() {
		return coltel;
	}
	public void setColtel(String coltel) {
		this.coltel = coltel;
	}
	public int getExamcount() {
		return examcount;
	}
	public void setExamcount(int examcount) {
		this.examcount = examcount;
	}
}