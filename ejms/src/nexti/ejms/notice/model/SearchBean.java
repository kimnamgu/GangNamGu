/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공지사항 검색조건 bean
 * 설명:
 */
package nexti.ejms.notice.model;

public class SearchBean {
	private String gubun       = "";   //구분   
	private String  searchval  = "";   //검색내용
	private int startidx       = 0;    //시작위치
	private int endidx         = 0;    //마지막위치	
	
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public int getEndidx() {
		return endidx;
	}
	public void setEndidx(int endidx) {
		this.endidx = endidx;
	}
	public String getSearchval() {
		return searchval;
	}
	public void setSearchval(String searchval) {
		this.searchval = searchval;
	}
	public int getStartidx() {
		return startidx;
	}
	public void setStartidx(int startidx) {
		this.startidx = startidx;
	}		
}
