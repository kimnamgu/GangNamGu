/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 부서정보 bean
 * 설명:
 */
package nexti.ejms.dept.model;

public class DeptBean {
	private String dept_id        = "";  
	private String dept_name      = "";  
	private String dept_fullname  = "";  
	private String upper_dept_id  = "";  
	private String top_dept_id    = "";  
	private String dept_depth     = "";  
	private String dept_rank      = "";  
	private String dept_tel       = "";  
	private String dept_fax       = "";  
	private String orggbn         = "";  
	private String main_yn        = "";  
	private String use_yn         = "";  
	private String con_yn         = "";  
	private String crtdt          = "";  
	private String crtusrid       = "";  
	private String uptdt          = "";  
	private String uptusrid       = "";
	
	public String getCon_yn() {
		return con_yn;
	}
	public void setCon_yn(String con_yn) {
		this.con_yn = con_yn;
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
	public String getDept_depth() {
		return dept_depth;
	}
	public void setDept_depth(String dept_depth) {
		this.dept_depth = dept_depth;
	}
	public String getDept_fax() {
		return dept_fax;
	}
	public void setDept_fax(String dept_fax) {
		this.dept_fax = dept_fax;
	}
	public String getDept_fullname() {
		return dept_fullname;
	}
	public void setDept_fullname(String dept_fullname) {
		this.dept_fullname = dept_fullname;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getDept_rank() {
		return dept_rank;
	}
	public void setDept_rank(String dept_rank) {
		this.dept_rank = dept_rank;
	}
	public String getDept_tel() {
		return dept_tel;
	}
	public void setDept_tel(String dept_tel) {
		this.dept_tel = dept_tel;
	}
	public String getMain_yn() {
		return main_yn;
	}
	public void setMain_yn(String main_yn) {
		this.main_yn = main_yn;
	}
	public String getOrggbn() {
		return orggbn;
	}
	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}
	public String getTop_dept_id() {
		return top_dept_id;
	}
	public void setTop_dept_id(String top_dept_id) {
		this.top_dept_id = top_dept_id;
	}
	public String getUpper_dept_id() {
		return upper_dept_id;
	}
	public void setUpper_dept_id(String upper_dept_id) {
		this.upper_dept_id = upper_dept_id;
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
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}  
	
}
