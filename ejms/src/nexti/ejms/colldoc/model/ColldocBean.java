/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서 bean
 * 설명:
 */
package nexti.ejms.colldoc.model;

import org.apache.struts.upload.FormFile;

public class ColldocBean {
	
	private int 	seqno 		= 0; 	//연번
	
	private int		formcount	= 0;	//양식개수
	private int		formseq		= 0;	//양식일련번호
	private String	formtitle	= "";	//양식제목
	private String	formkind	= "";	//양식유형
	
	private int		sysdocno	= 0;	//시스템문서번호
	private String	docno		= "";	//문서번호
	private String	doctitle	= "";	//문서제목
	private String	docgbn		= "";	//문서종류
	private String	basicdate	= "";	//자료기준일자
	private String	submitdate	= "";	//기안일자
	private String	basis		= "";	//관련근거
	private String	summary		= "";	//취합개요
	private String	enddt		= "";	//마감일시
	private String	enddt_date	= "";	//마감일
	private String	enddt_hour	= "";	//마감시
	private String	enddt_min	= "";	//마감분
	private String	endcomment	= "";	//마감알림말
	private String	sancrule	= "";	//제출자료전결규정
	private String	docstate	= "";	//문서상태
	private String	deliverydt	= "";	//배부일시
	private String	tgtdeptnm	= "";	//제출부서명칭또는그룹명칭
	private String	coldeptcd	= "";	//취합부서코드
	private String	coldeptnm	= "";	//취합부서명
	private int		chrgunitcd	= 0;	//취합담당단위코드
	private String	chrgunitnm	= "";	//취합담당단위명
	private String	chrgusrcd	= "";	//취합담당자코드
	private String	chrgusrnm	= "";	//취합담당자명
	private String	opendt		= "";	//공개일자
	private String	searchkey	= "";	//검색키워드
	private String	delyn		= "";	//최근목록에서삭제여부
	private String	openinput	= "";	//공개입력(타부서자료보기)
	private String	crtdt		= "";	//생성일시
	private String	crtusrid	= "";	//생성자코드
	private String	uptdt		= "";	//수정일시
	private String	uptusrid	= "";	//수정자코드
	private String	sancusrnm1	= "";	//취합부서 검토자 이름
	private String	sancusrnm2	= "";	//취합부서 승인자 이름  
	private String 	cnt			= "";   //제출건수및 배부건수
	private String 	formkindname = "";  //양식종류명
	private String  formcomment	= "";	//양식개요
	private String  formhtml	= "";	//양식구조
	private FormFile attachFile	= null;	//첨부파일
	private String attachFileYN	= "";	//첨부파일삭제
	private int		fileseq		= 0;	//첨부파일번호
	private String	filenm		= "";	//첨부파일명
	private String	originfilenm = "";	//원본파일명
	private int		filesize	= 0;	//파일크기
	private String	ext			= "";	//확장자
	private	int		ord			= 0;	//정렬순서
	
	public String getOpeninput() {
		return openinput;
	}
	public void setOpeninput(String openinput) {
		this.openinput = openinput;
	}
	public FormFile getAttachFile() {
		return attachFile;
	}
	public void setAttachFile(FormFile attachFile) {
		this.attachFile = attachFile;
	}
	public String getAttachFileYN() {
		return attachFileYN;
	}
	public void setAttachFileYN(String attachFileYN) {
		this.attachFileYN = attachFileYN;
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
	public String getFormcomment() {
		return formcomment;
	}
	public void setFormcomment(String formcomment) {
		this.formcomment = formcomment;
	}
	public String getFormhtml() {
		return formhtml;
	}
	public void setFormhtml(String formhtml) {
		this.formhtml = formhtml;
	}
	public String getFormkindname() {
		return formkindname;
	}
	public void setFormkindname(String formkindname) {
		this.formkindname = formkindname;
	}
	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
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
	public String getOpendt() {
		return opendt;
	}
	public void setOpendt(String opendt) {
		this.opendt = opendt;
	}
	public String getSancrule() {
		return sancrule;
	}
	public void setSancrule(String sancrule) {
		this.sancrule = sancrule;
	}
	public String getSearchkey() {
		return searchkey;
	}
	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
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
	public int getFormcount() {
		return formcount;
	}
	public void setFormcount(int formcount) {
		this.formcount = formcount;
	}
	public String getFormkind() {
		return formkind;
	}
	public void setFormkind(String formkind) {
		this.formkind = formkind;
	}
	public String getFormtitle() {
		return formtitle;
	}
	public void setFormtitle(String formtitle) {
		this.formtitle = formtitle;
	}
	public int getFormseq() {
		return formseq;
	}
	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}
	public String getChrgusrcd() {
		return chrgusrcd;
	}
	public void setChrgusrcd(String chrgusrcd) {
		this.chrgusrcd = chrgusrcd;
	}
}
