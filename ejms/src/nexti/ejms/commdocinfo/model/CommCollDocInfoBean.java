/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통취합문서정보 bean
 * 설명:
 */
package nexti.ejms.commdocinfo.model;

import org.apache.struts.upload.FormFile;

public class CommCollDocInfoBean {
	private int seqno;				//순번
	private int formseq;			//
	private String formkind;		//
	private int sysdocno;			//시스템문서번호
	private String doctitle;		//문서제목
	private String basicdate;		//자료기준일
	private String basicdate1;		//자료기준일(yyyy년 mm월 dd일)
	private String deliverydt;		//접수일(yyyy년 mm월 dd일)
	private String submitdt;		//기안일
	private String docno;			//취합문서번호
	private String sancusrinfo;		//최종결재자정보
	private String coldeptcd;		//취합부서코드
	private String coldeptnm;		//취합부서명
	private int    chrgunitcd;      //취합담당단위코드
	private String chrgunitnm;		//취합담당단위명
	private String chrgusrcd;		//취합담당자코드
	private String chrgusrnm;		//취합담당자명
	private String coldepttel;		//취합담당부서전화번호
	private String basis;			//관련근거
	private String summary;			//취합개요
	private String enddt;			//마감시한
	private String enddt_date;      //마감일시(yyyy-mm-dd)
	private String enddt_hour;      //마감시한(hh24)
	private String enddt_min;      	//마감분(mi)
	private String endcomment;		//마감알림말
	private String targetdeptnm;	//취합대상부서명
	private String openinput;		//공개입력(타부서자료보기)
	private String sancrulecd;		//제출시요청사항코드
	private String sancrule;		//제출시요청사항
	private String basicdt;			//자료기준일
	private String opendt;			//공개일자
	private String opennm;			//공개명
	private String docstate;		//문서상태
	private String gubun;			//취합자료1,  설문구분2
	private FormFile attachFile;	//첨부파일
	private String attachFileYN;	//첨부파일삭제
	private int    fileseq;			//첨부파일번호
	private String filenm;			//첨부파일명
	private String originfilenm;	//원본파일명
	private int    filesize;		//파일크기
	private String ext;				//확장자
	private	int    ord;				//정렬순서

	public CommCollDocInfoBean() {
		this.seqno			= 0;
		this.formseq		= 0;
		this.formkind		= "";
		this.sysdocno		= 0;
		this.doctitle		= "";
		this.basicdate		= "";
		this.basicdate1		= "";
		this.deliverydt		= "";
		this.docno			= "";
		this.sancusrinfo	= "";
		this.coldeptcd		= "";
		this.coldeptnm		= "";
		this.chrgunitcd		= 0;
		this.chrgunitnm		= "";
		this.chrgusrcd		= "";
		this.chrgusrnm		= "";
		this.coldepttel		= "";
		this.basis			= "";
		this.summary		= "";
		this.enddt			= "";
		this.enddt_date     = "";
		this.enddt_hour		= "";
		this.enddt_min		= "";
		this.endcomment		= "";
		this.targetdeptnm	= "";
		this.openinput		= "";
		this.sancrulecd		= "";
		this.sancrule		= "";
		this.basicdt		= "";
		this.opendt			= "";
		this.opennm			= "";
		this.docstate		= "";
		this.gubun			= "";
		this.attachFile		= null;
		this.attachFileYN	= "";
		this.fileseq		= 0;
		this.filenm			= "";
		this.originfilenm 	= "";
		this.filesize		= 0;
		this.ext			= "";
		this.ord			= 0;
	}

	public String getOpeninput() {
		return openinput;
	}

	public void setOpeninput(String openinput) {
		this.openinput = openinput;
	}

	public String getChrgusrcd() {
		return chrgusrcd;
	}

	public void setChrgusrcd(String chrgusrcd) {
		this.chrgusrcd = chrgusrcd;
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

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	
	public String getOpennm() {
		return opennm;
	}

	public void setOpennm(String opennm) {
		this.opennm = opennm;
	}

	public String getOpendt() {
		return opendt;
	}

	public void setOpendt(String opendt) {
		this.opendt = opendt;
	}

	public int getSeqno() {
		return seqno;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	/**
	 * @return the basis
	 */
	public String getBasis() {
		return basis;
	}

	/**
	 * @param basis the basis to set
	 */
	public void setBasis(String basis) {
		this.basis = basis;
	}

	/**
	 * @return the chrgunitnm
	 */
	public String getChrgunitnm() {
		return chrgunitnm;
	}

	/**
	 * @param chrgunitnm the chrgunitnm to set
	 */
	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}

	/**
	 * @return the chrgusrnm
	 */
	public String getChrgusrnm() {
		return chrgusrnm;
	}

	/**
	 * @param chrgusrnm the chrgusrnm to set
	 */
	public void setChrgusrnm(String chrgusrnm) {
		this.chrgusrnm = chrgusrnm;
	}

	/**
	 * @return the coldeptnm
	 */
	public String getColdeptnm() {
		return coldeptnm;
	}

	/**
	 * @param coldeptnm the coldeptnm to set
	 */
	public void setColdeptnm(String coldeptnm) {
		this.coldeptnm = coldeptnm;
	}

	/**
	 * @return the deliverydt
	 */
	public String getDeliverydt() {
		return deliverydt;
	}

	/**
	 * @param deliverydt the deliverydt to set
	 */
	public void setDeliverydt(String deliverydt) {
		this.deliverydt = deliverydt;
	}

	/**
	 * @return the docno
	 */
	public String getDocno() {
		return docno;
	}

	/**
	 * @param docno the docno to set
	 */
	public void setDocno(String docno) {
		this.docno = docno;
	}

	/**
	 * @return the doctitle
	 */
	public String getDoctitle() {
		return doctitle;
	}

	/**
	 * @param doctitle the doctitle to set
	 */
	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}

	/**
	 * @return the endcomment
	 */
	public String getEndcomment() {
		return endcomment;
	}

	/**
	 * @param endcomment the endcomment to set
	 */
	public void setEndcomment(String endcomment) {
		this.endcomment = endcomment;
	}

	/**
	 * @return the enddt
	 */
	public String getEnddt() {
		return enddt;
	}

	/**
	 * @param enddt the enddt to set
	 */
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}

	/**
	 * @return the sancrule
	 */
	public String getSancrule() {
		return sancrule;
	}

	/**
	 * @param sancrule the sancrule to set
	 */
	public void setSancrule(String sancrule) {
		this.sancrule = sancrule;
	}

	/**
	 * @return the sancusrinfo
	 */
	public String getSancusrinfo() {
		return sancusrinfo;
	}

	/**
	 * @param sancusrinfo the sancusrinfo to set
	 */
	public void setSancusrinfo(String sancusrinfo) {
		this.sancusrinfo = sancusrinfo;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the targetdeptnm
	 */
	public String getTargetdeptnm() {
		return targetdeptnm;
	}

	/**
	 * @param targetdeptnm the targetdeptnm to set
	 */
	public void setTargetdeptnm(String targetdeptnm) {
		this.targetdeptnm = targetdeptnm;
	}

	/**
	 * @return the basicdt
	 */
	public String getBasicdt() {
		return basicdt;
	}

	/**
	 * @param basicdt the basicdt to set
	 */
	public void setBasicdt(String basicdt) {
		this.basicdt = basicdt;
	}

	public String getSubmitdt() {
		return submitdt;
	}

	public void setSubmitdt(String submitdt) {
		this.submitdt = submitdt;
	}

	public String getColdeptcd() {
		return coldeptcd;
	}

	public void setColdeptcd(String coldeptcd) {
		this.coldeptcd = coldeptcd;
	}

	public String getBasicdate() {
		return basicdate;
	}

	public void setBasicdate(String basicdate) {
		this.basicdate = basicdate;
	}

	public String getBasicdate1() {
		return basicdate1;
	}

	public void setBasicdate1(String basicdate1) {
		this.basicdate1 = basicdate1;
	}

	public int getChrgunitcd() {
		return chrgunitcd;
	}

	public void setChrgunitcd(int chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
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

	public int getSysdocno() {
		return sysdocno;
	}

	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}

	public String getDocstate() {
		return docstate;
	}

	public void setDocstate(String docstate) {
		this.docstate = docstate;
	}

	public String getSancrulecd() {
		return sancrulecd;
	}

	public void setSancrulecd(String sancrulecd) {
		this.sancrulecd = sancrulecd;
	}

	/**
	 * @return the coldepttel
	 */
	public String getColdepttel() {
		return coldepttel;
	}

	/**
	 * @param coldepttel the coldepttel to set
	 */
	public void setColdepttel(String coldepttel) {
		this.coldepttel = coldepttel;
	}

	public String getFormkind() {
		return formkind;
	}

	public void setFormkind(String formkind) {
		this.formkind = formkind;
	}

	public int getFormseq() {
		return formseq;
	}

	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}
}
