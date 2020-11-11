/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 기본 입력자료 bean
 * 설명:
 */
package nexti.ejms.sinchung.model;

import java.util.List;

import org.apache.struts.upload.FormFile;

public class ReqMstBean {
	private int reqformno    = 0;    //신청양식번호
	private int reqseq       = 0;    //신청번호
	private int bunho		 = 0;    //연번
	private String title     = "";   //제목
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
	private String statenm   = "";   //진행상태 명칭	
	private String crtdt     = "";   //생성일시
	private String crtusrid  = "";   //생성자ID
	private String uptdt     = "";   //수정일시
	private String uptusrid  = "";   //수정자ID
	private String lastsanc  = "";   //최종결재자 성명
	
	private List   anscontList = null; //추가 항목 신청내용 
	
	private int		fileseq		= 0;	//첨부파일번호
	private String	filenm		= "";	//첨부파일명
	private String	originfilenm = "";	//원본파일명
	private int		filesize	= 0;	//파일크기
	private String	ext			= "";	//확장명
	private int		ord			= 0;	//정렬순서
	private String	fileToBase64Encoding = "";	//신청 첨부파일
	
	private FormFile attachFile = null;	//첨부파일
	private String attachFileYN = "";	//첨부파일삭제여부
	
	public void setAttachFileYN(String attachFileYN) {
		this.attachFileYN = attachFileYN;
	}
	public String getAttachFileYN() {
		return attachFileYN;
	}
	public FormFile getAttachFile() {
		return attachFile;
	}
	public void setAttachFile(FormFile attachFile) {
		this.attachFile = attachFile;
	}
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
	public List getAnscontList() {
		return anscontList;
	}
	public void setAnscontList(List anscontList) {
		this.anscontList = anscontList;
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
	public int getBunho() {
		return bunho;
	}
	public void setBunho(int bunho) {
		this.bunho = bunho;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public int getReqformno() {
		return reqformno;
	}
	public void setReqformno(int reqformno) {
		this.reqformno = reqformno;
	}
	public int getReqseq() {
		return reqseq;
	}
	public void setReqseq(int reqseq) {
		this.reqseq = reqseq;
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
	public String getFileToBase64Encoding() {
		return fileToBase64Encoding;
	}
	public void setFileToBase64Encoding(String fileToBinaryString) {
		this.fileToBase64Encoding = fileToBinaryString;
	}	
}
