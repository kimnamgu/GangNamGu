/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 신청내용 actionform
 * 설명:
 */
package nexti.ejms.sinchung.form;

import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class DataForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int reqformno    = 0;    //신청양식번호
	private int reqseq		 = 0;    //신청번호
	private String sessi     = "";   //세션ID	
	private String presentnm = "";   //신청자
	private String presentid = "";   //신청자ID
	private String presentsn = "";   //주민번호
	private String presentbd = "";   //생년월일
	private String position  = "";   //소속
	private String duty      = "";   //직급
	private String zip       = "";   //우편번호
	private String addr1     = "";   //주소1
	private String addr2     = "";   //주소2
	private String email     = "";   //email
	private String tel       = "";   //전화번호
	private String cel       = "";   //휴대전화번호
	private String fax       = "";   //fax번호
	private String state     = "";   //진행상태
	private String statenm   = "";   //진행상태명칭
	private String crtdt     = "";   //생성일시
	private String crtusrid  = "";   //생성자ID
	private String uptdt     = "";   //수정일시
	private String uptusrid  = "";   //수정자ID
	private String lastsanc  = "";   //최종결재자 성명
	
	private List   anscontList = null;            //추가 항목 신청내용 	
	private String mode      = "";                //저장(s), 수정(u)
	private int[] formseq;                        //문항번호
	private String[] radioans = new String[20];   //라디오 버튼 입력값	
	private String[] chkans;                      //체크BOX 입력값	
	private String[] txtans;                      //단답형 입력값	
	private String[] areaans;                     //장문형 입력값	
	private String[] other;                       //기타의견 입력값	
	
	private String title     = "";      //제목
	private String basictype = "";      //입력기본정보
	private String coldeptnm = "";      //접수담당 부서명칭
	private String chrgusrnm = "";      //접수담당자
	private String depttel   = "";      //접수부서 전화번호
	private List articleList = null;    //항목양식 정보	
	private List sancList1 	 = null;  	//검토자 목록
	private List sancList2 	 = null;  	//승인자 목록 
	private String sancusrinfo	= "";	//최종결재내역
	
	private FormFile attachFile = null;	//첨부파일
	private String attachFileYN = "";	//첨부파일삭제여부
	private int		fileseq		= 0;	//첨부파일번호
	private String	filenm		= "";	//첨부파일명
	private String	originfilenm = "";	//원본파일명
	private int		filesize	= 0;	//파일크기
	private String	ext			= "";	//확장명
	private int		ord			= 0;	//정렬순서
	
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