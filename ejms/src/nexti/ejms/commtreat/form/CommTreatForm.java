/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통처리현황 actionform
 * 설명:
 */
package nexti.ejms.commtreat.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CommTreatForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private int sysdocno        	= 0;        //시스템문서번호
	private String appntusrnm 		= "";		//입력담당자명
	private String sancusrnm1 		= "";		//검토자명
	private String sancusrnm2 		= "";		//승인자명
	private String tcnt 			= "";		//제출대상
	private String scnt 			= "";		//제출완료
	private String fcnt 			= "";		//미제출
	private String docstate 		= "";		//문서상태
	private String docstatenm		= "";		//문서상태명
	private String enddt			= "";		//마감일자
	
	private String inputdeptcd		= "";		//입력부서명
	private int chrgunitcd			= 0;		//담당단위코드
	private String submitstate		= "";		//진행상태
	private String deliverydt		= "";		//배부일시
	
	private String inputstate		= "";		//입력완료구분
	private String inputcomp		= "";		//입력완료일시
	
	private String sancgbn          = "";       //문서구분(1:배부문서, 2:제출문서) 
	private List inputuser1 		= null; 	//입력담당자 목록
	private List inputuser2 		= null; 	//미입력담당자 목록
	private List sancList1 			= null;  	//검토자 목록
	private List sancList2 			= null;  	//승인자 목록 
	private String submitdt         = "";       //기안일자
	private int appCnt				= 0;        //결재해야할 건수

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}
		
	public int getAppCnt() {
		return appCnt;
	}

	public void setAppCnt(int appCnt) {
		this.appCnt = appCnt;
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

	public String getSubmitdt() {
		return submitdt;
	}

	public void setSubmitdt(String submitdt) {
		this.submitdt = submitdt;
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

	public String getSancgbn() {
		return sancgbn;
	}

	public void setSancgbn(String sancgbn) {
		this.sancgbn = sancgbn;
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

	public String getFcnt() {
		return fcnt;
	}

	public void setFcnt(String fcnt) {
		this.fcnt = fcnt;
	}

	public String getScnt() {
		return scnt;
	}

	public void setScnt(String scnt) {
		this.scnt = scnt;
	}

	public String getTcnt() {
		return tcnt;
	}

	public void setTcnt(String tcnt) {
		this.tcnt = tcnt;
	}
	
	public String getAppntusrnm() {
		return appntusrnm;
	}

	public void setAppntusrnm(String appntusrnm) {
		this.appntusrnm = appntusrnm;
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
	 * @return the submitstate
	 */
	public String getSubmitstate() {
		return submitstate;
	}

	/**
	 * @param submitstate the submitstate to set
	 */
	public void setSubmitstate(String submitstate) {
		this.submitstate = submitstate;
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

	/**
	 * @return the chrgunitcd
	 */
	public int getChrgunitcd() {
		return chrgunitcd;
	}

	/**
	 * @param chrgunitcd the chrgunitcd to set
	 */
	public void setChrgunitcd(int chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
	}

	/**
	 * @return the inputdeptcd
	 */
	public String getInputdeptcd() {
		return inputdeptcd;
	}

	/**
	 * @param inputdeptcd the inputdeptcd to set
	 */
	public void setInputdeptcd(String inputdeptcd) {
		this.inputdeptcd = inputdeptcd;
	}

	/**
	 * @return the inputcomp
	 */
	public String getInputcomp() {
		return inputcomp;
	}

	/**
	 * @param inputcomp the inputcomp to set
	 */
	public void setInputcomp(String inputcomp) {
		this.inputcomp = inputcomp;
	}

	/**
	 * @return the inputstate
	 */
	public String getInputstate() {
		return inputstate;
	}

	/**
	 * @param inputstate the inputstate to set
	 */
	public void setInputstate(String inputstate) {
		this.inputstate = inputstate;
	}
}