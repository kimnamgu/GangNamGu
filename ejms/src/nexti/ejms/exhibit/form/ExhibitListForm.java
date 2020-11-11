/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 자료공유 목록 actionform
 * 설명:
 */
package nexti.ejms.exhibit.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ExhibitListForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;

	private int 	page				= 1 ;	//페이지
	private String	check_detail		= "";	//상세조건
	private int  	treescroll			= 0;	//조직도 스크롤
	
	private String	searchdept			= "";	//선택부서이하 전체포함
	private String 	searchdoctitle		= "";	//문서제목
	private String 	searchformtitle		= "";	//양식제목
	private String 	searchkey			= "";	//검색어
	private String 	searchcrtdtfr		= "";	//문서작성일from
	private String 	searchcrtdtto		= "";	//문서작성일to
	private String 	searchbasicdatefr	= "";	//자료기준일from
	private String 	searchbasicdateto	= "";	//자료기준일to
	private String 	searchchrgusrnm		= "";	//취합담당자
	private String 	searchinputusrnm	= "";	//입력담당자
	
	private List	doclist;					//문서목록
	private String 	orggbn				= "";	//조직구분
	
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

	public String getOrggbn() {
		return orggbn;
	}

	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}

	public int getTreescroll() {
		return treescroll;
	}

	public void setTreescroll(int treescroll) {
		this.treescroll = treescroll;
	}

	public List getDoclist() {
		return doclist;
	}

	public void setDoclist(List doclist) {
		this.doclist = doclist;
	}

	public String getSearchdept() {
		return searchdept;
	}

	public void setSearchdept(String searchdept) {
		this.searchdept = searchdept;
	}

	public String getCheck_detail() {
		return check_detail;
	}

	public void setCheck_detail(String check_detail) {
		this.check_detail = check_detail;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSearchbasicdatefr() {
		return searchbasicdatefr;
	}

	public void setSearchbasicdatefr(String searchbasicdatefr) {
		this.searchbasicdatefr = searchbasicdatefr;
	}

	public String getSearchbasicdateto() {
		return searchbasicdateto;
	}

	public void setSearchbasicdateto(String searchbasicdateto) {
		this.searchbasicdateto = searchbasicdateto;
	}

	public String getSearchchrgusrnm() {
		return searchchrgusrnm;
	}

	public void setSearchchrgusrnm(String searchchrgusrnm) {
		this.searchchrgusrnm = searchchrgusrnm;
	}

	public String getSearchcrtdtfr() {
		return searchcrtdtfr;
	}

	public void setSearchcrtdtfr(String searchcrtdtfr) {
		this.searchcrtdtfr = searchcrtdtfr;
	}

	public String getSearchcrtdtto() {
		return searchcrtdtto;
	}

	public void setSearchcrtdtto(String searchcrtdtto) {
		this.searchcrtdtto = searchcrtdtto;
	}

	public String getSearchdoctitle() {
		return searchdoctitle;
	}

	public void setSearchdoctitle(String searchdoctitle) {
		this.searchdoctitle = searchdoctitle;
	}

	public String getSearchformtitle() {
		return searchformtitle;
	}

	public void setSearchformtitle(String searchformtitle) {
		this.searchformtitle = searchformtitle;
	}

	public String getSearchinputusrnm() {
		return searchinputusrnm;
	}

	public void setSearchinputusrnm(String searchinputusrnm) {
		this.searchinputusrnm = searchinputusrnm;
	}

	public String getSearchkey() {
		return searchkey;
	}

	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}
	
}