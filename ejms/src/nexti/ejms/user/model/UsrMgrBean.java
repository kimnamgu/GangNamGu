/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 조직관리 bean
 * 설명:
 */
package nexti.ejms.user.model;

public class UsrMgrBean {
	//dept		
	private String grp_gbn;				
	private String main_yn;				
	private int dept_level;		
	
	private String dept_id;				//조직코드
	private String dept_name;			//조직명
	private String dept_fullname;		//전체조직명
	private String upper_dept_id;		//차상위조직코드
	private String upper_dept_name;		//차상위조직명
	private String dept_rank;			//조직서열
	private String dept_tel;			//대표전화
	private String use_yn;				//사용여부(사용1, 안함0)            
	private String con_yn;				//연계여부(사용1, 안함0)
	private String bigo;				//비고
	private String main_yn_one;         //대성부서여부(대상부서1,나머지0)   
	private String use_yn_one;          //사용여부
	private String con_yn_one; 			//연계여부
	private String uptusr;				//수정코드
	private String orggbn;				//조직구분
	
	//usr
	private String user_id;				//사용자아이디
	private String user_name;			//사용자명
	private String tel;					//전화번호
	private String mobile; 				//핸드폰번호
	private String user_sn;				//주민등록번호
	private String email;				//이메일
	private String passwd;				//비밀번호
	private String class_id;   			//직급코드
	private String class_name;   		//직급명칭
	private String position_id;     	//직위코드
	private String position_name;   	//직위명
	private String grade_id;  			//계급코드   
	private String grade_name;  		//계급명칭
	private String usr_rank;			//서열
	
	private String sex;					//성별
	private String age;					//연령대
	private String ea_id;				//전자결재아이디
	private String gpki_id;				//GPKI인증서ID
	
	public String getPosition_id() {
		return position_id;
	}
	public void setPosition_id(String position_id) {
		this.position_id = position_id;
	}
	public String getPosition_name() {
		return position_name;
	}
	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}
	public String getUsr_rank() {
		return usr_rank;
	}
	public void setUsr_rank(String usr_rank) {
		this.usr_rank = usr_rank;
	}
	public String getOrggbn() {
		return orggbn;
	}
	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUptusr() {
		return uptusr;
	}
	public void setUptusr(String uptusr) {
		this.uptusr = uptusr;
	}
	public String getEa_id() {
		return ea_id;
	}
	public void setEa_id(String ea_id) {
		this.ea_id = ea_id;
	}
	public String getBigo() {
		return bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
	public String getDept_fullname() {
		return dept_fullname;
	}
	public void setDept_fullname(String dept_fullname) {
		this.dept_fullname = dept_fullname;
	}
	public String getDept_tel() {
		return dept_tel;
	}
	public void setDept_tel(String dept_tel) {
		this.dept_tel = dept_tel;
	}
	public String getUpper_dept_name() {
		return upper_dept_name;
	}
	public void setUpper_dept_name(String upper_dept_name) {
		this.upper_dept_name = upper_dept_name;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
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
	public String getCon_yn() {
		return con_yn;
	}
	public void setCon_yn(String con_yn) {
		this.con_yn = con_yn;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public int getDept_level() {
		return dept_level;
	}
	public void setDept_level(int dept_level) {
		this.dept_level = dept_level;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getGrp_gbn() {
		return grp_gbn;
	}
	public void setGrp_gbn(String grp_gbn) {
		this.grp_gbn = grp_gbn;
	}
	public String getMain_yn() {
		return main_yn;
	}
	public void setMain_yn(String main_yn) {
		this.main_yn = main_yn;
	}
	public String getUpper_dept_id() {
		return upper_dept_id;
	}
	public void setUpper_dept_id(String upper_dept_id) {
		this.upper_dept_id = upper_dept_id;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(String grade_id) {
		this.grade_id = grade_id;
	}
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getUser_sn() {
		return user_sn;
	}
	public void setUser_sn(String user_sn) {
		this.user_sn = user_sn;
	}
	public String getDept_rank() {
		return dept_rank;
	}
	public void setDept_rank(String dept_rank) {
		this.dept_rank = dept_rank;
	}
	public String getCon_yn_one() {
		return con_yn_one;
	}
	public void setCon_yn_one(String con_yn_one) {
		this.con_yn_one = con_yn_one;
	}
	public String getMain_yn_one() {
		return main_yn_one;
	}
	public void setMain_yn_one(String main_yn_one) {
		this.main_yn_one = main_yn_one;
	}
	public String getUse_yn_one() {
		return use_yn_one;
	}
	public void setUse_yn_one(String use_yn_one) {
		this.use_yn_one = use_yn_one;
	}
	public String getGpki_id() {
		return gpki_id;
	}
	public void setGpki_id(String gpki_id) {
		this.gpki_id = gpki_id;
	}

}
