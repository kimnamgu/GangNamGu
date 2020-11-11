/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� actionform
 * ����:
 */
package nexti.ejms.research.form;

import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ResearchForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int		seqno				= 0;					//���� 
	
	private int 	rchno 				= 0;					//������ȣ
	private int 	rchgrpno			= 0;					//�����׷��ȣ
	private int     mdrchno				= 0;					//�����ÿ�������ȣ
	private int     mdrchgrpno			= 0;					//�����ÿ������׷��ȣ
	private String  sessionId			= "";					//���Ǿ��̵�
	private String  title				= "";   				//��������
	private String	strdt				= "";					//������ 
	private String	enddt				= "";					//������
	private String	strymd				= "";					//���۳���� 
	private String	endymd				= "";					//��������
	private String  coldeptcd 			= "";   				//��������ںμ��ڵ�
	private String  coldeptnm			= "";   				//��������ںμ���
	private String  coldepttel			= "";					//��������ںμ���ȭ
	private String  chrgusrid			= "";   				//���������ID
	private String  chrgusrnm			= "";					//��������ڸ�
	private String  summary				= "";   				//��������
	
	private String limit1					= "";					//������� ����
	private String limit2					= "";					//������Ÿ��� ����
	private String limit1chk					= "";					//������� ����
	private String limit2chk					= "";					//������Ÿ��� ����
	private String rchtarget1			="";	//�����������
	private String rchtarget2			="";	//������Ÿ�������
	
	private String  exhibit				= "2";					//�����������(����1,�����2)
	//����
//	private String  exhibit				= "1";					//�����������(����1,�����2)

	private String  range  				= "1";   				//����������(����1,���ͳ�2)
	//����
//	private String  range  				= "2";   				//����������(����1,���ͳ�2)

	private String	duplicheck			= "2";					//�ߺ��亯üũ(����0,��Ű1,���̵�2)
	//����
//	private String	duplicheck			= "1";					//�ߺ��亯üũ(����0,��Ű1,���̵�2)
	
	private String  opentype			= "1";					//������������(����1,�����2)
	private String  rangedetail			= "21";   				//������������
	private String	imgpreview			= "2";					//÷���̹���ǥ��(ǥ��1,�����2)
	private int		limitcount			= 0;					//��ǥ�����
	private String	groupyn				= "N";					//�����׷쿩��
	private String  tgtdeptnm 			= "";   				//���μ���
	private String  rchname		 		= "";   				//�����Ѽ��������
	private String  rchnolist	 		= ""; 					//�����Ѽ��������ȣ
	private String  othertgtnm 			= "";   				//��Ÿ���
	private String  headcont			= "";					//�Ӹ���
	private String  tailcont			= "";   				//������
	private String  telno				= "";   				//��ȭ��ȣ
	private int		formcount			= 0;    				//���׼�
	private int		examcount			= 0;    				//�����
	private int 	anscount			= 0;    				//�Ѵ亯�ڼ�
	private String	crtusrid			= "";					//�ۼ���

	private String 	sch_orggbn			= "";					//����ڱ���
	private String  sch_title			= "";   				//������ȸ����
	private String  sch_deptcd			= "";					//�μ���ȸ����
	private String[] sch_exam;									//�亯��ȸ����
	private String  sch_sex				= "";					//������ȸ����
	private String  sch_age				= "";					//������ȸ��
	private String 	sch_old_deptcd		= "";					//�μ�����ȸ����
	private String 	sch_deptnm			= "";					//�μ�����ȸ����
	private String  sch_old_userid		= "";					//�������ȸ����
	private String  sch_userid			= "";					//�������ȸ����
	private String 	sch_usernm			= "";					//�������ȸ����
	private int		page				= 1;					//������
	private List	listrch				= null;					//����Ʈ
	private String 	selyear				= "";					//��������
	private String 	initentry			= "first";				//�ʱ����Կ���
	private int 	treescroll			= 0;
	private int 	posscroll 			= 0;
	
	private String  mode				= "";					//Ÿ�Ա���
	private int		delseq				= 0;       				//�������׹�ȣ
	private String 	orggbn 				= "";					//��������
	
	private List 	 formList 			= null;       			//���׸��
	private int[] 	 formseq;									//���׹�ȣ
	private int[] 	 changeFormseq;								//���׹�ȣ��� : ���׼���������
	private String[] formgrp;					  				//��������
	private String[] formtitle;                   				//��������
	private String[] formtype 			= new String[100];   	//�Է�����(1:���� 2:���� 3:�ܴ� 4:�幮)
	private String[] security;                    				//����ó������(Y:����ó��, N:����)
	private int[]	 examseq;
	private String[] examcont;                    				//��������
	private String[] examtype;	                  				//��Ÿüũ����(Y:����, N:����)
	private FormFile[] formtitleFile	= new FormFile[100];		//��������÷��
	private FormFile[] examcontFile		= new FormFile[2500];	//��������÷��
	private String[] formtitleFileYN	= new String[100];		//��������÷�ο���
	private String[] examcontFileYN		= new String[2500];		//��������÷�ο���
	
	private String[] anscont;									//�亯����
	private String[] other				= new String[100];		//��Ÿ�亯����
	
	private List	individualResult 	= null;					//���μ����������Ʈ
	private String[] require; 										//2017.02.09 �ʼ����� �߰�
	private String[] frsqs; 											//
	private String[] exsqs; 											//
	
	public String getCrtusrid() {
		return crtusrid;
	}
	public void setCrtusrid(String crtusrid) {
		this.crtusrid = crtusrid;
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
	public int[] getChangeFormseq() {
		return changeFormseq;
	}
	public void setChangeFormseq(int[] changeFormseq) {
		this.changeFormseq = changeFormseq;
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
	public void setExhibit(String exhbit) {
		this.exhibit = exhbit;
	}
	public String getSch_orggbn() {
		return sch_orggbn;
	}
	public void setSch_orggbn(String sch_orggbn) {
		this.sch_orggbn = sch_orggbn;
	}
	public String getOrggbn() {
		return orggbn;
	}
	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
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
	public String getSch_deptnm() {
		return sch_deptnm;
	}
	public void setSch_deptnm(String sch_deptnm) {
		this.sch_deptnm = sch_deptnm;
	}
	public String getSch_usernm() {
		return sch_usernm;
	}
	public void setSch_usernm(String sch_usernm) {
		this.sch_usernm = sch_usernm;
	}
	public FormFile[] getExamcontFile() {
		return examcontFile;
	}
	public void setExamcontFile(FormFile[] examcontFile) {
		this.examcontFile = examcontFile;
	}
	public FormFile[] getFormtitleFile() {
		return formtitleFile;
	}
	public void setFormtitleFile(FormFile[] formtitleFile) {
		this.formtitleFile = formtitleFile;
	}
	public String[] getExamcontFileYN() {
		return examcontFileYN;
	}
	public void setExamcontFileYN(String[] examcontFileYN) {
		this.examcontFileYN = examcontFileYN;
	}
	public String[] getFormtitleFileYN() {
		return formtitleFileYN;
	}
	public void setFormtitleFileYN(String[] formtitleFileYN) {
		this.formtitleFileYN = formtitleFileYN;
	}
	public List getListrch() {
		return listrch;
	}
	public void setListrch(List listrch) {
		this.listrch = listrch;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
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
	
	public String getLimit1() {
		return limit1;
	}
	public void setLimit1(String limit1) {
		this.limit1 = limit1;
	}
	public String getLimit2() {
		return limit2;
	}
	public void setLimit2(String limit2) {
		this.limit2 = limit2;
	}
	
	public String getLimit1chk() {
		return limit1chk;
	}
	public void setLimit1chk(String limit1chk) {
		this.limit1chk = limit1chk;
	}
	public String getLimit2chk() {
		return limit2chk;
	}
	public void setLimit2chk(String limit2chk) {
		this.limit2chk = limit2chk;
	}
	
	public String getRchtarget1() {
		return rchtarget1;
	}
	public void setRchtarget1(String rchtarget1) {
		this.rchtarget1 = rchtarget1;
	}
	public String getRchtarget2() {
		return rchtarget2;
	}
	public void setRchtarget2(String rchtarget2) {
		this.rchtarget2 = rchtarget2;
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
	public String getSch_title() {
		return sch_title;
	}
	public void setSch_title(String sch_title) {
		this.sch_title = sch_title;
	}
	public int getSeqno() {
		return seqno;
	}
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}
	public String getHeadcont() {
		return headcont;
	}
	public void setHeadcont(String headcont) {
		this.headcont = headcont;
	}
	public int getAnscount() {
		return anscount;
	}
	public void setAnscount(int anscount) {
		this.anscount = anscount;
	}
	public int[] getExamseq() {
		return examseq;
	}
	public void setExamseq(int[] examseq) {
		this.examseq = examseq;
	}
	public int[] getFormseq() {
		return formseq;
	}
	public void setFormseq(int[] formseq) {
		this.formseq = formseq;
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
	public String[] getFormgrp() {
		return formgrp;
	}
	public void setFormgrp(String[] formgrp) {
		this.formgrp = formgrp;
	}
	public List getFormList() {
		return formList;
	}
	public void setFormList(List formList) {
		this.formList = formList;
	}
	public String[] getFormtitle() {
		return formtitle;
	}
	public void setFormtitle(String[] formtitle) {
		this.formtitle = formtitle;
	}
	public String[] getFormtype() {
		return formtype;
	}
	public void setFormtype(String[] formtype) {
		this.formtype = formtype;
	}
	public String[] getSecurity() {
		return security;
	}
	public void setSecurity(String[] security) {
		this.security = security;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
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
	public int getDelseq() {
		return delseq;
	}
	public void setDelseq(int delseq) {
		this.delseq = delseq;
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
	public String getSch_deptcd() {
		return sch_deptcd;
	}
	public void setSch_deptcd(String sch_deptcd) {
		this.sch_deptcd = sch_deptcd;
	}
	public int getTreescroll() {
		return treescroll;
	}
	public void setTreescroll(int treescroll) {
		this.treescroll = treescroll;
	}
	public String[] getAnscont() {
		return anscont;
	}
	public void setAnscont(String[] anscont) {
		this.anscont = anscont;
	}
	public String[] getOther() {
		return other;
	}
	public void setOther(String[] other) {
		this.other = other;
	}
	public String[] getSch_exam() {
		return sch_exam;
	}
	public void setSch_exam(String[] sch_exam) {
		this.sch_exam = sch_exam;
	}
	public String getSch_age() {
		return sch_age;
	}
	public void setSch_age(String sch_age) {
		this.sch_age = sch_age;
	}
	public String getSch_sex() {
		return sch_sex;
	}
	public void setSch_sex(String sch_sex) {
		this.sch_sex = sch_sex;
	}
	public int getPosscroll() {
		return posscroll;
	}
	public void setPosscroll(int posscroll) {
		this.posscroll = posscroll;
	}
	public int getMdrchno() {
		return mdrchno;
	}
	public void setMdrchno(int mdrchno) {
		this.mdrchno = mdrchno;
	}
	public String getColdepttel() {
		return coldepttel;
	}
	public void setColdepttel(String coldepttel) {
		this.coldepttel = coldepttel;
	}
	public List getIndividualResult() {
		return individualResult;
	}
	public void setIndividualResult(List individualResult) {
		this.individualResult = individualResult;
	}
	public int getExamcount() {
		return examcount;
	}
	public void setExamcount(int examcount) {
		this.examcount = examcount;
	}
	public String[] getRequire() {
		return require;
	}
	public void setRequire(String[] require) {
		this.require = require;
	}
	public String[] getFrsqs() {
		return frsqs;
	}
	public void setFrsqs(String[] frsqs) {
		this.frsqs = frsqs;
	}
	public String[] getExsqs() {
		return exsqs;
	}
	public void setExsqs(String[] exsqs) {
		this.exsqs = exsqs;
	}
}