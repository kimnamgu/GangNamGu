/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 결재하기 검색조건 bean
 * 설명:
 */
package nexti.ejms.approval.model;

public class SearchBean {
	private String docGbn      	= "";   //문서구분
	private String user_id      = "";  	//결재자 ID	
	private String deptcd		= "";	//부서코드
	private int startidx       	= 0;    //시작위치
	private int endidx         	= 0;    //마지막위치	
		
	public String getDocGbn() {
		return docGbn;
	}
	public String getDeptcd() {
		return deptcd;
	}
	public void setDeptcd(String deptcd) {
		this.deptcd = deptcd;
	}
	public void setDocGbn(String docGbn) {
		this.docGbn = docGbn;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getEndidx() {
		return endidx;
	}
	public void setEndidx(int endidx) {
		this.endidx = endidx;
	}
	public int getStartidx() {
		return startidx;
	}
	public void setStartidx(int startidx) {
		this.startidx = startidx;
	}		
}
