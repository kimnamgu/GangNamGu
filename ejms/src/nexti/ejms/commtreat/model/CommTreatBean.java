/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통처리현황 bean
 * 설명:
 */
package nexti.ejms.commtreat.model;

import java.util.List;

public class CommTreatBean {
	private int		level			= 0;
	private int		grpgbn			= 0;	
	private int		sysdocno		= 0;	//시스템문서번호
	private String	sessionid		= "";	//세션아이디
	private String	tgtdeptcd		= "";	//제출부서코드
	private String	tgtdeptnm		= "";	//제출부서명칭
	private String	tgtdeptfullnm	= "";	//제출부서전체명칭
	private String	submitstate		= "";	//제출상태
	private String	submitstatenm	= "";	//제출상태명
	private String	modifyyn		= "";	//수정가능여부(가능1,안함0)
	private String	returncomment	= "";	//반송사유
	private String	inusrsenddt		= "";	//입력담당지정일시
	private String	appntusrnm		= "";	//입력담당지정자성명
	private String	submitdt		= "";	//제출일시
	private String	predeptcd		= "";	//이전제출부서코드
	private String	inputusrid		= "";	//입력담당자ID
	private String	inputusrnm		= "";	//입력담당자성명
	private int		chrgunitcd		= 0;	//담당단위코드
	private String	chrgunitnm		= "";	//담당단위명
	private String	inputstate		= "";	//입력상태
	private String	inputstatenm	= "";	//입력상태명
	private String	inputcomp		= "";	//입력완료처리일시

	private String	sancusrnm1		= "";	//검토자명
	private String	sancusrnm2		= "";	//승인자명
	private String	tcnt			= "";	//제출대상
	private String	scnt			= "";	//제출완료
	private String	fcnt			= "";	//미제출
	private String	docstate		= "";	//문서상태
	private String	docstatenm		= "";	//문서상태
	private String	enddt			= "";	//마감일자
	private String	inputdeptcd		= "";	//입력부서명
	private String	deliverydt		= "";	//배부일시
	
	private List	inputuser1		= null;	//입력담당자 목록
	private List	inputuser2		= null;	//미입력담당자 목록
	private List	sancList1		= null;	//검토자 목록
	private List	sancList2		= null;	//승인자 목록
	
	private String inputusersn		= "";	//입력담당자주민번호
	
	public String getInputusersn() {
		return inputusersn;
	}
	public void setInputusersn(String inputusersn) {
		this.inputusersn = inputusersn;
	}
	public int getGrpgbn() {
		return grpgbn;
	}
	public void setGrpgbn(int grpgbn) {
		this.grpgbn = grpgbn;
	}
	public String getTgtdeptfullnm() {
		return tgtdeptfullnm;
	}
	public void setTgtdeptfullnm(String tgtdeptfullnm) {
		this.tgtdeptfullnm = tgtdeptfullnm;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getInputstatenm() {
		return inputstatenm;
	}
	public void setInputstatenm(String inputstatenm) {
		this.inputstatenm = inputstatenm;
	}
	public String getSubmitstatenm() {
		return submitstatenm;
	}
	public void setSubmitstatenm(String submitstatenm) {
		this.submitstatenm = submitstatenm;
	}
	public String getAppntusrnm() {
		return appntusrnm;
	}
	public void setAppntusrnm(String appntusrnm) {
		this.appntusrnm = appntusrnm;
	}
	public int getChrgunitcd() {
		return chrgunitcd;
	}
	public void setChrgunitcd(int chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
	}
	public String getChrgunitnm() {
		return chrgunitnm;
	}
	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}
	public String getDeliverydt() {
		return deliverydt;
	}
	public void setDeliverydt(String deliverydt) {
		this.deliverydt = deliverydt;
	}
	public String getDocstate() {
		return docstate;
	}
	public void setDocstate(String docstate) {
		this.docstate = docstate;
	}
	public String getDocstatenm() {
		return docstatenm;
	}
	public void setDocstatenm(String docstatenm) {
		this.docstatenm = docstatenm;
	}
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
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
	public String getInputusrid() {
		return inputusrid;
	}
	public void setInputusrid(String inputusrid) {
		this.inputusrid = inputusrid;
	}
	public String getInputusrnm() {
		return inputusrnm;
	}
	public void setInputusrnm(String inputusrnm) {
		this.inputusrnm = inputusrnm;
	}
	public String getInusrsenddt() {
		return inusrsenddt;
	}
	public void setInusrsenddt(String inusrsenddt) {
		this.inusrsenddt = inusrsenddt;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getModifyyn() {
		return modifyyn;
	}
	public void setModifyyn(String modifyyn) {
		this.modifyyn = modifyyn;
	}
	public String getPredeptcd() {
		return predeptcd;
	}
	public void setPredeptcd(String predeptcd) {
		this.predeptcd = predeptcd;
	}
	public String getReturncomment() {
		return returncomment;
	}
	public void setReturncomment(String returncomment) {
		this.returncomment = returncomment;
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
	public String getSubmitdt() {
		return submitdt;
	}
	public void setSubmitdt(String submitdt) {
		this.submitdt = submitdt;
	}
	public String getSubmitstate() {
		return submitstate;
	}
	public void setSubmitstate(String submitstate) {
		this.submitstate = submitstate;
	}
	public int getSysdocno() {
		return sysdocno;
	}
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
	public String getTcnt() {
		return tcnt;
	}
	public void setTcnt(String tcnt) {
		this.tcnt = tcnt;
	}
	public String getTgtdeptcd() {
		return tgtdeptcd;
	}
	public void setTgtdeptcd(String tgtdeptcd) {
		this.tgtdeptcd = tgtdeptcd;
	}
	public String getTgtdeptnm() {
		return tgtdeptnm;
	}
	public void setTgtdeptnm(String tgtdeptnm) {
		this.tgtdeptnm = tgtdeptnm;
	}
}