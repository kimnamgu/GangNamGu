/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직도 bean
 * 설명:
 */
package nexti.ejms.formation.model;

public class FormationBean {
	private String	level			= "";
	private String	grpgbn			= "";
	private String	main_yn			= "";
	private String	upper_dept_id	= "";
	private String	upper_dept_name	= "";
	private String	upper_dept_fullname	= "";
	private String	dept_id			= "";
	private String	dept_name		= "";
	private String	dept_fullname	= "";
	private String	user_id			= "";
	private String	user_name		= "";
	
	public String getDept_fullname() {
		return dept_fullname;
	}
	public void setDept_fullname(String dept_fullname) {
		this.dept_fullname = dept_fullname;
	}
	public String getUpper_dept_fullname() {
		return upper_dept_fullname;
	}
	public void setUpper_dept_fullname(String upper_dept_fullname) {
		this.upper_dept_fullname = upper_dept_fullname;
	}
	public String getUpper_dept_name() {
		return upper_dept_name;
	}
	public void setUpper_dept_name(String upper_dept_name) {
		this.upper_dept_name = upper_dept_name;
	}
	public String getMain_yn() {
		return main_yn;
	}
	public void setMain_yn(String main_yn) {
		this.main_yn = main_yn;
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
	public String getGrpgbn() {
		return grpgbn;
	}
	public void setGrpgbn(String grpgbn) {
		this.grpgbn = grpgbn;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getUpper_dept_id() {
		return upper_dept_id;
	}
	public void setUpper_dept_id(String upper_dept_id) {
		this.upper_dept_id = upper_dept_id;
	}
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
	
	
}
