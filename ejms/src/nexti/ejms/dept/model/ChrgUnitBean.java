/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 담당단위조직 bean
 * 설명:
 */
package nexti.ejms.dept.model;

public class ChrgUnitBean {
	private String dept_id    = "";   //부서코드
	private String user_id	  = "";	  //사용자아이디
	private String user_name  = "";	  //사용자명
	private int chrgunitcd    = 0;    //담당단위 코드
    private String chrgunitnm = "";   //담당단위 명칭
    private int ord           = 0;    //정렬순서
    private String crtdt      = "";   //생성일자
    private String crtusrid   = "";   //생성자코드
    private String uptdt      = "";   //수정일자
    private String uptusrid   = "";   //수정자코드
    
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
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
	public String getCrtdt() {
		return crtdt;
	}
	public void setCrtdt(String crtdt) {
		this.crtdt = crtdt;
	}
	public String getCrtusrid() {
		return crtusrid;
	}
	public void setCrtusrid(String crtusrid) {
		this.crtusrid = crtusrid;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public String getUptdt() {
		return uptdt;
	}
	public void setUptdt(String uptdt) {
		this.uptdt = uptdt;
	}
	public String getUptusrid() {
		return uptusrid;
	}
	public void setUptusrid(String uptusrid) {
		this.uptusrid = uptusrid;
	}    
}
