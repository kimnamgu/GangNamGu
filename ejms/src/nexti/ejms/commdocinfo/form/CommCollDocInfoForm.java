/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통취합문서정보 actionform
 * 설명:
 */
package nexti.ejms.commdocinfo.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class CommCollDocInfoForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private int	   sysdocno			= 0;		//시스템문서번호
	private int		formseq			= 0;		//양식번호
	private String	formkind		= "";		//양식종류
	private String doctitle			= "";		//문서제목
	private String basicdate		= "";		//자료기준일
	private String basicdate1		= "";		//자료기준일(yyyy년 mm월 dd일)
	private String deliverydt		= "";		//접수일
	private String submitdt			= "";       //기안일
	private String docno			= "";		//취합문서번호
	private String sancusrinfo		= "";		//최종결재자정보
	private String coldeptcd		= "";		//취합부서코드
	private String coldeptnm		= "";		//취합부서명
	private int chrgunitcd			= 0;       	//취합담당단위코드
	private String chrgunitnm		= "";		//취합담당단위명
	private String chrgusrcd		= "";		//취합담당자코드
	private String chrgusrnm		= "";		//취합담당자명
	private String coldepttel		= "";		//취합담당부서전화번호
	private String basis			= "";		//관련근거
	private String summary			= "";		//취합개요
	private String enddt			= "";		//마감시한
	private String enddt_date		= "";       //마감일시(yyyy-mm-dd)
	private String enddt_hour		= "";       //마감시한(hh24)
	private String enddt_min		= "";      	//마감분(mi)
	private String endcomment		= "";		//마감알림말
	private String targetdeptnm		= "";		//취합대상부서명
	private String sancrulecd		= "";       //제출시요청사항코드
	private String sancrule			= "";		//제출시요청사항
	private String basicdt			= "";		//자료기준일
	private String docstate			= "";		//문서상태
	private String opendt			= "";		//공개일자
	private String opennm			= "";		//공개명
	private String openinput		= "";		//공개입력(타부서자료보기)
	
	private String sancgbn          = "";       //문서구분(1:배부문서, 2:제출문서) 
	private int    appCnt			= 0;        //결재해야할 건수
	
	//commTreateForm 내용 시작
	private String appntusrnm 		= "";		//부서내입력담당자명
	private String appntdeptnm 		= "";		//부서외취합부서명
	private String sancusrnm1 		= "";		//검토자명
	private String sancusrnm2 		= "";		//승인자명
	private String tcnt 			= "";		//제출대상
	private String scnt 			= "";		//제출완료
	private String fcnt 			= "";		//미제출
	private String docstatenm		= "";		//문서상태명
	
	private String inputdeptnm		= "";		//입력부서명
	private String inputdeptcd		= "";		//입력부서코드
	private String submitstate		= "";		//진행상태
	
	private String inputstate		= "";		//입력완료구분
	private String inputcomp		= "";		//입력완료일시
	 
	private String inputusrnm		= "";		//입력담당자명
	private List inputuser1 		= null; 	//입력담당자 목록
	private List inputuser2 		= null; 	//미입력담당자 목록
	private List sancList1 			= null;  	//검토자 목록
	private List sancList2 			= null;  	//승인자 목록
	//commTreateForm 내용 끝

	private FormFile attachFile	= null;	//첨부파일
	private String attachFileYN	= "";	//첨부파일삭제
	private int		fileseq		= 0;	//첨부파일번호
	private String	filenm		= "";	//첨부파일명
	private String	originfilenm = "";	//원본파일명
	
	private String	tgtdeptnm	= "";	//제출부서명칭또는그룹명칭

	public String getOpeninput() {
		return openinput;
	}

	public void setOpeninput(String openinput) {
		this.openinput = openinput;
	}

	public String getInputusrnm() {
		return inputusrnm;
	}

	public void setInputusrnm(String inputusrnm) {
		this.inputusrnm = inputusrnm;
	}

	public String getInputdeptnm() {
		return inputdeptnm;
	}

	public void setInputdeptnm(String inputdeptnm) {
		this.inputdeptnm = inputdeptnm;
	}

	public String getChrgusrcd() {
		return chrgusrcd;
	}

	public void setChrgusrcd(String chrgusrcd) {
		this.chrgusrcd = chrgusrcd;
	}

	public String getAppntdeptnm() {
		return appntdeptnm;
	}

	public void setAppntdeptnm(String appntdeptnm) {
		this.appntdeptnm = appntdeptnm;
	}

	public String getTgtdeptnm() {
		return tgtdeptnm;
	}

	public void setTgtdeptnm(String tgtdeptnm) {
		this.tgtdeptnm = tgtdeptnm;
	}

	public String getAttachFileYN() {
		return attachFileYN;
	}

	public void setAttachFileYN(String attachFileYN) {
		this.attachFileYN = attachFileYN;
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

	public FormFile getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(FormFile attachFile) {
		this.attachFile = attachFile;
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

	public String getOriginfilenm() {
		return originfilenm;
	}

	public void setOriginfilenm(String originfilenm) {
		this.originfilenm = originfilenm;
	}

	public int getAppCnt() {
		return appCnt;
	}

	public void setAppCnt(int appCnt) {
		this.appCnt = appCnt;
	}

	public String getOpendt() {
		return opendt;
	}

	public void setOpendt(String opendt) {
		this.opendt = opendt;
	}

	public String getOpennm() {
		return opennm;
	}

	public void setOpennm(String opennm) {
		this.opennm = opennm;
	}

	public String getSancgbn() {
		return sancgbn;
	}

	public void setSancgbn(String sancgbn) {
		this.sancgbn = sancgbn;
	}

	public String getDocstate() {
		return docstate;
	}

	public void setDocstate(String docstate) {
		this.docstate = docstate;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
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

	public String getAppntusrnm() {
		return appntusrnm;
	}

	public void setAppntusrnm(String appntusrnm) {
		this.appntusrnm = appntusrnm;
	}

	public String getDocstatenm() {
		return docstatenm;
	}

	public void setDocstatenm(String docstatenm) {
		this.docstatenm = docstatenm;
	}

	public String getFcnt() {
		return fcnt;
	}

	public void setFcnt(String fcnt) {
		this.fcnt = fcnt;
	}

	public String getInputcomp() {
		return inputcomp;
	}

	public void setInputcomp(String inputcomp) {
		this.inputcomp = inputcomp;
	}

	public String getInputdeptcd() {
		return inputdeptcd;
	}

	public void setInputdeptcd(String inputdeptcd) {
		this.inputdeptcd = inputdeptcd;
	}

	public String getInputstate() {
		return inputstate;
	}

	public void setInputstate(String inputstate) {
		this.inputstate = inputstate;
	}

	public List getInputuser1() {
		return inputuser1;
	}

	public void setInputuser1(List inputuser1) {
		this.inputuser1 = inputuser1;
	}

	public List getInputuser2() {
		return inputuser2;
	}

	public void setInputuser2(List inputuser2) {
		this.inputuser2 = inputuser2;
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

	public String getScnt() {
		return scnt;
	}

	public void setScnt(String scnt) {
		this.scnt = scnt;
	}

	public String getSubmitstate() {
		return submitstate;
	}

	public void setSubmitstate(String submitstate) {
		this.submitstate = submitstate;
	}

	public String getTcnt() {
		return tcnt;
	}

	public void setTcnt(String tcnt) {
		this.tcnt = tcnt;
	}
}