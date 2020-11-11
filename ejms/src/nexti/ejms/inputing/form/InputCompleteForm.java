/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력완료 actionform
 * 설명:
 */
package nexti.ejms.inputing.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class InputCompleteForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int seqno				= 0;			//연번
	private int sysdocno			= 0;			//시스템문서번호
	private String doctitle			= "";			//문서제목
	private String submistate		= "";			//진행상태
	private String inusrenddt		= "";			//입력완료일시
	private String inputendreason	= "";			//입력완료구분
	private String coldeptnm		= "";   		//취합부서명칭
	private String chrgusrnm		= "";   		//취합담당자명
	private String deliverydt		= "";   		//배부일자
	private String inputusrid		= "";			//입력담당자ID
	
	private String searchvalue		= "";			//검색어
	private String selyear			= "";			//연도선택
	private String initentry		= "first";		//초기진입여부
	private int page          		= 0 ;  			//페이지
	private List completeList     	= null;			//입력 목록

	private String 	sch_old_deptcd	= "";			//부서명조회조건
	private String  sch_deptcd		= "";			//부서명조회조건
	private String 	sch_deptnm		= "";			//부서명조회조건
	private String  sch_old_userid	= "";			//담당자조회조건
	private String  sch_userid		= "";			//담당자조회조건
	private String 	sch_usernm		= "";			//담당자조회조건

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

	public String getInputusrid() {
		return inputusrid;
	}

	public void setInputusrid(String inputusrid) {
		this.inputusrid = inputusrid;
	}

	public String getSch_deptcd() {
		return sch_deptcd;
	}

	public void setSch_deptcd(String sch_deptcd) {
		this.sch_deptcd = sch_deptcd;
	}

	public String getSch_deptnm() {
		return sch_deptnm;
	}

	public void setSch_deptnm(String sch_deptnm) {
		this.sch_deptnm = sch_deptnm;
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

	public String getSch_usernm() {
		return sch_usernm;
	}

	public void setSch_usernm(String sch_usernm) {
		this.sch_usernm = sch_usernm;
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
	 * @return the completeList
	 */
	public List getCompleteList() {
		return completeList;
	}

	/**
	 * @param completeList the completeList to set
	 */
	public void setCompleteList(List completeList) {
		this.completeList = completeList;
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
	 * @return the inputendreason
	 */
	public String getInputendreason() {
		return inputendreason;
	}

	/**
	 * @param inputendreason the inputendreason to set
	 */
	public void setInputendreason(String inputendreason) {
		this.inputendreason = inputendreason;
	}

	/**
	 * @return the inusrenddt
	 */
	public String getInusrenddt() {
		return inusrenddt;
	}

	/**
	 * @param inusrenddt the inusrenddt to set
	 */
	public void setInusrenddt(String inusrenddt) {
		this.inusrenddt = inusrenddt;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the seqno
	 */
	public int getSeqno() {
		return seqno;
	}

	/**
	 * @param seqno the seqno to set
	 */
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	/**
	 * @return the submistate
	 */
	public String getSubmistate() {
		return submistate;
	}

	/**
	 * @param submistate the submistate to set
	 */
	public void setSubmistate(String submistate) {
		this.submistate = submistate;
	}

	/**
	 * @return the sysdocno
	 */
	public int getSysdocno() {
		return sysdocno;
	}

	/**
	 * @param sysdocno the sysdocno to set
	 */
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}

	/**
	 * @return the searchvalue
	 */
	public String getSearchvalue() {
		return searchvalue;
	}

	/**
	 * @param searchvalue the searchvalue to set
	 */
	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
	}

	/**
	 * @return the selyear
	 */
	public String getSelyear() {
		return selyear;
	}

	/**
	 * @param selyear the selyear to set
	 */
	public void setSelyear(String selyear) {
		this.selyear = selyear;
	}

	/**
	 * @return the initentry
	 */
	public String getInitentry() {
		return initentry;
	}

	/**
	 * @param initentry the initentry to set
	 */
	public void setInitentry(String initentry) {
		this.initentry = initentry;
	}
}