/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 actionform
 * 설명:
 */
package nexti.ejms.delivery.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DeliveryForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private int seqno         	= 0;      	//연번
	private int sysdocno 		= 0;   		//시스템문서번호
	private String doctitle   	= "";     	//제목
	private String deliverydt 	= "";     	//접수일자
	private String coldeptnm  	= "";     	//취합부서명칭
	private String chrgusrnm  	= "";     	//취합담당자명
	private String enddt      	= "";     	//마감일시
	private String remaintime 	= "";     	//남은시간
	
	private int page          	= 0 ;     	//페이지
	private List deliList     	= null;   	//배포 목록

	private String 	initentry		= "first";		//초기진입여부
	private String 	sch_old_userid	= "";			//담당자조회조건
	private String 	sch_usernm		= "";			//담당자조회조건
	private String 	sch_old_deptcd	= "";			//부서명조회조건
	private String  sch_deptcd		= "";			//부서조회조건
	private String 	sch_deptnm		= "";			//부서명조회조건

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}

	public String getSch_usernm() {
		return sch_usernm;
	}

	public void setSch_usernm(String sch_usernm) {
		this.sch_usernm = sch_usernm;
	}

	public String getSch_old_userid() {
		return sch_old_userid;
	}

	public void setSch_old_userid(String sch_old_userid) {
		this.sch_old_userid = sch_old_userid;
	}

	public String getInitentry() {
		return initentry;
	}

	public void setInitentry(String initentry) {
		this.initentry = initentry;
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

	public String getDeliverydt() {
		return deliverydt;
	}

	public void setDeliverydt(String deliverydt) {
		this.deliverydt = deliverydt;
	}

	public String getEnddt() {
		return enddt;
	}

	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}

	public String getRemaintime() {
		return remaintime;
	}

	public void setRemaintime(String remaintime) {
		this.remaintime = remaintime;
	}

	public int getSeqno() {
		return seqno;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	public String getDoctitle() {
		return doctitle;
	}

	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List getDeliList() {
		return deliList;
	}

	public void setDeliList(List deliList) {
		this.deliList = deliList;
	}
	
	public int getSysdocno() {
		return sysdocno;
	}
	
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
}