/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 bean
 * 설명:
 */
package nexti.ejms.sinchung.model;

import java.util.List;

import org.apache.struts.upload.FormFile;

public class FrmBean  {
	
	private int reqformno    = 0;     //신청양식번호
	private String sessi	 = "";    //세션번호	 
	private String title     = "";    //제목
	private String strdt     = "";    //시작일
	private String enddt     = "";    //종료일
	private String coldeptcd = "";    //담당부서코드
	private String coldeptnm = "";    //담당부서명
	private String coltel 	 = "";    //담당부서전
	private String chrgusrid = "";    //담당자코드
	private String chrgusrnm = "";    //담당자명
	private String summary   = "";    //신청서 개요
	private String range	 = "";    //신청범위(내부1,인터넷2)
	private String rangedetail = "";  //설문대상범위상세
	private String imgpreview = "";	  //첨부이미지표시(표시1,숨기기2)
	private String duplicheck = "";   //중복신청체크(없음0,쿠키1,아이디2)
	private int    limitcount = 0;    //목표신청수
	private String sancneed  = "";    //결재필요여부(Y:결재필요, N:결재불필요)
	private String basictype = "";    //입력기본정보선택
	private String headcont  = "";    //머리말
	private String tailcont  = "";    //꼬리말
	private String crtdt     = "";    //생성일시
	private String crtusrid  = "";    //생성자코드
	private String uptdt     = "";    //수정일시
	private String uptusrid  = "";    //수정자코드
	private String closefl   = "";    //마감여부
	
	private int    bunho     = 0;     //목록일련번호
	private int    notproc   = 0;     //미처리 건수
	private int    acnt      = 0;     //문항 총갯수
	private int	   examcount = 0;	  //보기수
	private int    tday		 = 0;     //마감초과일
	
	private List   articleList = null; //문항목록
	private List	subFileList		= null;	//문항첨부파일리스트
	private List	examFileList	= null;	//보기첨부파일리스트
	
	private FormFile[] formtitleFile	= new FormFile[100];	//문항제목첨부
	private FormFile[] examcontFile		= new FormFile[2500];	//보기제목첨부
	private String[] formtitle;                   	//문항제목
	private String[] examcont;                    	//보기제목
	private String[] formtitleFileYN	= new String[100];		//문항제목첨부여부
	private String[] examcontFileYN		= new String[2500];		//보기제목첨부여부
	
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