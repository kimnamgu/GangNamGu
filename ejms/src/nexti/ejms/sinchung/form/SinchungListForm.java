/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 목록 actionform
 * 설명:
 */
package nexti.ejms.sinchung.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class SinchungListForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private String search_title = "";   //검색제목
	private String syear      = "";     //년도
	private String procGbn    = "1";    //미처리 여부(0:미처리 , 1:전체)
	private int page          = 1 ;     //페이지
		
	private List sinchungList = null; 	//신청서리스트
	
	private int treescroll	  = 0;      //부서Tree의 스크롤 유지를 위해..
	private String deptcd     = "";     //부서코드
	private String deptnm	  = "";     //부서명칭

	private String  sch_deptcd		= "";			//부서조회조건
	private String 	sch_old_deptcd	= "";			//부서명조회조건
	private String 	sch_deptnm		= "";			//부서명조회조건
	private String  sch_old_userid	= "";			//담당자조회조건
	private String  sch_userid		= "";			//담당자조회조건
	private String 	sch_usernm		= "";			//담당자조회조건
	private String 	initentry		= "first";		//초기진입여부
	
	private String  orggbn			= "";
	
	public String getOrggbn() {
		return orggbn;
	}

	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
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

	public String getSearch_title() {
		return search_title;
	}

	public void setSearch_title(String search_title) {
		this.search_title = search_title;
	}

	public String getSyear() {
		return syear;
	}

	public void setSyear(String syear) {
		this.syear = syear;			
	}

	public String getDeptnm() {
		return deptnm;
	}

	public void setDeptnm(String deptnm) {
		this.deptnm = deptnm;
	}

	public String getDeptcd() {
		return deptcd;
	}

	public void setDeptcd(String deptcd) {
		this.deptcd = deptcd;
	}

	public int getTreescroll() {
		return treescroll;
	}

	public void setTreescroll(int treescroll) {
		this.treescroll = treescroll;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getProcGbn() {
		return procGbn;
	}

	public void setProcGbn(String procGbn) {
		this.procGbn = procGbn;
	}

	public List getSinchungList() {
		return sinchungList;
	}

	public void setSinchungList(List sinchungList) {
		this.sinchungList = sinchungList;
	}	
}