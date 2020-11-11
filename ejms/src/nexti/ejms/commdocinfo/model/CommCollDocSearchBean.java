/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통취합문서정보 검색 bean
 * 설명:
 */
package nexti.ejms.commdocinfo.model;

public class CommCollDocSearchBean {
	private String	userid				= "";	//사용자아이디
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
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
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
	public String getSearchdept() {
		return searchdept;
	}
	public void setSearchdept(String searchdept) {
		this.searchdept = searchdept;
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
