/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 actionform
 * 설명:
 */
package nexti.ejms.sinchung.form;

import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import nexti.ejms.sinchung.model.ReqMstBean;

public class SinchungForm_gangnam extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int reqformno    = 0;     //신청양식번호
	private int reqseq       = 0;     //신청번호
	private String sessi     = "";    //세션ID
	private String title     = "";    //제목
	private String strdt     = "";    //시작일
	private String enddt     = "";    //종료일
	private String coldeptcd = "";    //담당부서코드
	private String coldeptnm = "";    //담당부서명
	private String coltel    = "";    //부서전화번호
	private String chrgusrid = "";    //담당자코드
	private String chrgusrnm = "";    //담당자명	
	private String summary   = "";    //신청서 개요
	private String range  	 = "2";   //신청범위(공개1,비공개2)
	private String duplicheck = "1";  //중복신청체크(없음0,쿠키1,아이디2)
	private String rangedetail = "21"; //설문대상범위상세
	private String imgpreview = "2";  //첨부이미지표시(표시1,숨기기2)
	private int    limitcount = 0;    //목표신청수
	private String sancneed  = "N";    //결재필요여부(Y:결재필요, N:결재불필요)
	private String basictype = "";    //입력기본정보선택
	private String[] btype;           //입력기본정보(배열)
	private String headcont  = "";    //머리말
	private String tailcont  = "";    //꼬리말
	private String crtdt     = "";    //생성일시
	private String crtusrid  = "";    //생성자코드
	private String uptdt     = "";    //수정일시
	private String uptusrid  = "";    //수정자코드	
	private String mode      = "";    //(make:항목만들기, del:삭제, add:추가, comp:완료, prev:미리보기)
	private String usedFL    = "";    //기존양식에서 가져오기 여부(Y)	
	private String closefl   = "";    //마감여부
	private String viewfl    = "";    //List에서 진입여부(Y:List에서 진입)
	
	private int    posscroll = 0;     //스크롤바 위치
	private int    sumcnt    = 0;     //접수데이터 건수
	private int    delseq    = 0;     //삭제항목번호
	
	private int acnt         = 0;                 //문항 총갯수
	private int	examcount    = 0;    			  //보기수
	private List articleList = null;              //문항목록
	private int[] formseq;                     	  //문항번호
	private int[] changeFormseq;				  //문항번호목록 : 문항순서조정용
	private String[] formtitle;                   //문항제목
	private String[] require;                     //필수입력여부 (Y:필수, N:없음)
	private String[] formtype = new String[20];   //입력형태(1:단일 2:복수 3:단답 4:장문)
	private String[] security;                    //보안처리여부(Y:보안처리, N:없음)
	private String[] examcont;                    //보기제목
	private String[] examtype;	                  //기타체크여부(Y:선택, N:없음)
	private String[] helpexp;		              //추가설명
	private FormFile[] formtitleFile	= new FormFile[20];		//문항제목첨부
	private FormFile[] examcontFile		= new FormFile[1000];	//보기제목첨부
	private String[] formtitleFileYN	= new String[20];		//문항제목첨부여부
	private String[] examcontFileYN		= new String[1000];		//보기제목첨부여부
		
	private ReqMstBean rbean = null;    //신청내역
	private List sancList1 	 = null;  	//검토자 목록
	private List sancList2 	 = null;  	//승인자 목록 
	private String sancusrinfo	= "";	//최종결재내역
	
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