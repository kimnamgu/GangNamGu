/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� bean
 * ����:
 */
package nexti.ejms.research.model;

import java.util.List;

import org.apache.struts.upload.FormFile;

public class ResearchBean2  {
	
	private int		seqno		= 0;		//���� 
	
	private int 	rchno 			= 0;	//������ȣ
	private int 	rchgrpno		= 0;	//�����׷��ȣ
	private int     mdrchno			= 0;    //�����ñ���������ȣ
	private int     mdrchgrpno		= 0;    //�����ñ��������׷��ȣ
	private String  sessionId		= "";   //���Ǿ��̵�
	private String  title			= "";   //��������
	private String	strdt			= "";	//������ 
	private String  strymd			= "";   //���۳����
	private String	enddt			= "";	//������
	private String  endymd			= "";   //��������
	private String  coldeptcd 		= "";   //��������ںμ��ڵ�
	private String  coldeptnm		= "";   //��������ںμ���
	private String  coldepttel		= "";   //�����������ȭ
	private String  chrgusrid		= "";   //���������ID
	private String  chrgusrnm		= "";	//��������ڸ�
	private String  summary			= "";   //��������
	private String  exhibit			= "";	//�����������(����1,�����2)
	private String  opentype		= "";	//������������(����1,�����2)
	private String  range  			= "";   //����������(����1,���ͳ�2)
	private String	imgpreview		= "";	//÷���̹���ǥ��(ǥ��1,�����2)
	private String  rangedetail		= "";	//������������
	private String	duplicheck		= "";	//�ߺ��亯üũ(����0,��Ű1,���̵�2)
	private int		limitcount		= 0;	//��ǥ�����
	private String	groupyn			= "";	//�����׷쿩��
	private String  tgtdeptnm 		= "";   //���μ���
	private String  rchname		 	= "";   //�����Ѽ��������
	private String  rchnolist	 	= "";   //�����Ѽ��������ȣ	
	private String  othertgtnm 		= "";   //��Ÿ���
	private String  headcont		= "";	//�Ӹ���
	private String  tailcont		= "";   //������
	private String  telno			= "";   //��ȭ��ȣ
	private String  partici			= "";   //������������
	private int		formcount		= 0;    //���׼�
	private int		examcount		= 0;    //�����
	private int 	anscount		= 0;    //�Ѵ亯�ڼ�
	private int     tday			= 0;    //�������ʰ� �ϼ�
	private String	crtdt			= "";	//�ۼ�����
	private String	status			= "";	//����
	
	private List	listrch			= null;	//����Ʈ
	private List	subFileList		= null;	//����÷�����ϸ���Ʈ
	private List	examFileList	= null;	//����÷�����ϸ���Ʈ
	
	private String  sch_title		= "";   		//������ȸ����
	private String  sch_deptcd		= "";			//�μ���ȸ����
	private String 	sch_userid		= "";			//�������ȸ����
	private String  sch_sex			= "";           //������ȸ����
	private String  sch_age			= "";           //������ȸ����
	private String[]  sch_exam;						//������ȸ����
	private int		page			= 1;			//������
	private String 	selyear			= "";			//��������
	private String 	initentry		= "first";		//�ʱ����Կ���
	
	private String crtusrid			= "";
	private String crtusrnm			= "";
	private int ansusrseq			= 0;			//�亯�ڿ���
	
	private FormFile[] formtitleFile	= new FormFile[50];	//��������÷��
	private FormFile[] examcontFile		= new FormFile[2500];	//��������÷��
	private String[] formtitle;                   	//��������
	private String[] examcont;                    	//��������
	private String[] formtitleFileYN	= null;		//��������÷�ο���
	private String[] examcontFileYN		= null;		//��������÷�ο���
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCrtdt() {
		return crtdt;
	}
	public void setCrtdt(String crtdt) {
		this.crtdt = crtdt;
	}
	public String getRchnolist() {
		return rchnolist;
	}
	public void setRchnolist(String rchnolist) {
		this.rchnolist = rchnolist;
	}
	public int getMdrchgrpno() {
		return mdrchgrpno;
	}
	public void setMdrchgrpno(int mdrchgrpno) {
		this.mdrchgrpno = mdrchgrpno;
	}
	public int getRchgrpno() {
		return rchgrpno;
	}
	public void setRchgrpno(int rchgrpno) {
		this.rchgrpno = rchgrpno;
	}
	public String getGroupyn() {
		return groupyn;
	}
	public void setGroupyn(String groupyn) {
		this.groupyn = groupyn;
	}
	public String getRchname() {
		return rchname;
	}
	public void setRchname(String rchname) {
		this.rchname = rchname;
	}
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
	public String getOthertgtnm() {
		return othertgtnm;
	}
	public void setOthertgtnm(String othertgtnm) {
		this.othertgtnm = othertgtnm;
	}
	public String getExhibit() {
		return exhibit;
	}
	public void setExhibit(String exhibit) {
		this.exhibit = exhibit;
	}
	public String getSch_userid() {
		return sch_userid;
	}
	public void setSch_userid(String sch_userid) {
		this.sch_userid = sch_userid;
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
	public String[] getFormtitle() {
		return formtitle;
	}
	public void setFormtitle(String[] formtitle) {
		this.formtitle = formtitle;
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
	public int getAnsusrseq() {
		return ansusrseq;
	}
	public void setAnsusrseq(int ansusrseq) {
		this.ansusrseq = ansusrseq;
	}
	public String getCrtusrid() {
		return crtusrid;
	}
	public void setCrtusrid(String crtusrid) {
		this.crtusrid = crtusrid;
	}
	public String getCrtusrnm() {
		return crtusrnm;
	}
	public void setCrtusrnm(String crtusrnm) {
		this.crtusrnm = crtusrnm;
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
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getOpentype() {
		return opentype;
	}
	public void setOpentype(String opentype) {
		this.opentype = opentype;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public int getRchno() {
		return rchno;
	}
	public void setRchno(int rchno) {
		this.rchno = rchno;
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
	public String getTgtdeptnm() {
		return tgtdeptnm;
	}
	public void setTgtdeptnm(String tgtdeptnm) {
		this.tgtdeptnm = tgtdeptnm;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSeqno() {
		return seqno;
	}
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}
	public int getAnscount() {
		return anscount;
	}
	public void setAnscount(int anscount) {
		this.anscount = anscount;
	}
	public List getListrch() {
		return listrch;
	}
	public void setListrch(List listrch) {
		this.listrch = listrch;
	}
	public String getHeadcont() {
		return headcont;
	}
	public void setHeadcont(String headcont) {
		this.headcont = headcont;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public int getFormcount() {
		return formcount;
	}
	public void setFormcount(int formcount) {
		this.formcount = formcount;
	}
	public String getEndymd() {
		return endymd;
	}
	public void setEndymd(String endymd) {
		this.endymd = endymd;
	}
	public String getStrymd() {
		return strymd;
	}
	public void setStrymd(String strymd) {
		this.strymd = strymd;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getInitentry() {
		return initentry;
	}
	public void setInitentry(String initentry) {
		this.initentry = initentry;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getSch_deptcd() {
		return sch_deptcd;
	}
	public void setSch_deptcd(String sch_deptcd) {
		this.sch_deptcd = sch_deptcd;
	}
	public String getSch_title() {
		return sch_title;
	}
	public void setSch_title(String sch_title) {
		this.sch_title = sch_title;
	}
	public String getSelyear() {
		return selyear;
	}
	public void setSelyear(String selyear) {
		this.selyear = selyear;
	}
	public String getPartici() {
		return partici;
	}
	public void setPartici(String partici) {
		this.partici = partici;
	}
	public String getSch_age() {
		return sch_age;
	}
	public void setSch_age(String sch_age) {
		this.sch_age = sch_age;
	}
	public String[] getSch_exam() {
		return sch_exam;
	}
	public void setSch_exam(String[] sch_exam) {
		this.sch_exam = sch_exam;
	}
	public String getSch_sex() {
		return sch_sex;
	}
	public void setSch_sex(String sch_sex) {
		this.sch_sex = sch_sex;
	}
	public int getMdrchno() {
		return mdrchno;
	}
	public void setMdrchno(int mdrchno) {
		this.mdrchno = mdrchno;
	}
	public int getTday() {
		return tday;
	}
	public void setTday(int tday) {
		this.tday = tday;
	}
	public String getColdepttel() {
		return coldepttel;
	}
	public void setColdepttel(String coldepttel) {
		this.coldepttel = coldepttel;
	}
	public int getExamcount() {
		return examcount;
	}
	public void setExamcount(int examcount) {
		this.examcount = examcount;
	}

}