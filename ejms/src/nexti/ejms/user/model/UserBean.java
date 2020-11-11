/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 사용자정보 bean
 * 설명:
 */
package nexti.ejms.user.model;

public class UserBean {
	private String	user_id			= "";	//사용자ID
	private String	user_sn			= "";	//주민번호
	private String	user_name		= "";	//이름
	private String	dept_id			= "";	//부서코드
	private String	dept_name		= "";	//부서명
	private String	dept_fullname	= "";	//전체부서명
	private String	class_id		= "";	//직급코드
	private String	class_name		= "";	//직급명
	private String	position_id		= "";	//직위코드
	private String	position_name	= "";	//직위명
	private String	grade_id		= "";	//계급코드
	private String	grade_name		= "";	//계급명
	private int		usr_rank		= 0;	//서열
	private String	email			= "";	//이메일
	private String	tel				= "";	//전화번호
	private String	mobile			= "";	//핸드폰번호
	private String	use_yn			= "";	//사용여부
	private String	con_yn			= "";	//연계여부
	private String	passwd			= "";	//비밀번호
	private String	mgryn			= "";	//관리자여부
	private String	delivery_yn		= "";	//배부여부
	private String	chrgunitcd		= "";	//담당단위조직코드
	private String	ea_id			= "";	//전자결재 ID
	private String	gpki_id			= "";	//GPKI ID
	private String	sex				= "M";	//성별
	private int		age				= 20;	//연령대
	private String	lstlogindt		= "";	//최근로그인일시
	private String	crtdt			= "";	//등록일자
	private String	crtusrid		= "";	//등록자
	private String	uptdt			= "";	//수정일자
	private String	uptusrid		= "";	//수정자
	
	private String	chrgunitnm		= "";	//담당단위조직명
	private String	dept_fax		= "";	//부서팩스
	
//	private String user_stat       = "";   //사용자근무상태코드
//	private String user_stat_name  = "";   //사용자근무상태명칭
//	private String regularity      = "";   //채용구분
//	private String org_id          = "";   //기관코드
//	private String org_name        = "";   //기관명칭
//	private String fax			   = "";   //팩스번호
//	private String join_day        = "";   //입사일자
//	private String retire_day      = "";   //퇴직일자
//	private String add_info1       = "";   //추가정보1
//	private String add_info2       = "";   //추가정보2
//	private String add_info3       = "";   //추가정보3
//	private String add_info4       = "";   //추가정보4
//	private String add_info5       = "";   //추가정보5
//	private String add_info6       = "";   //추가정보6
//	private String add_info7       = "";   //추가정보7
//	private String base_sys		   = "";   //기준시스템구분
//	private String reg_day         = "";   //등록일자
//	private String update_day      = "";   //수정일자
//	private String rduty_name      = "";   //지자체직위명
//	
//	private String chrcd           = "";   //담당단위 조직코드
//	private String chrnm           = "";   //담당단위 조직명칭
//
//	private int 	user_age	= 20;	//연령
//	private String	user_sex	= "M";	//성별(남자:M, 여자:F)
	
	public String getDept_fax() {
		return dept_fax;
	}
	public void setDept_fax(String dept_fax) {
		this.dept_fax = dept_fax;
	}
	public String getChrgunitnm() {
		return chrgunitnm;
	}
	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getChrgunitcd() {
		return chrgunitcd;
	}
	public void setChrgunitcd(String chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
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
	public String getDelivery_yn() {
		return delivery_yn;
	}
	public void setDelivery_yn(String delivery_yn) {
		this.delivery_yn = delivery_yn;
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
	public String getEa_id() {
		return ea_id;
	}
	public void setEa_id(String ea_id) {
		this.ea_id = ea_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGpki_id() {
		return gpki_id;
	}
	public void setGpki_id(String gpki_id) {
		this.gpki_id = gpki_id;
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
	public String getLstlogindt() {
		return lstlogindt;
	}
	public void setLstlogindt(String lstlogindt) {
		this.lstlogindt = lstlogindt;
	}
	public String getMgryn() {
		return mgryn;
	}
	public void setMgryn(String mgryn) {
		this.mgryn = mgryn;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public String getUser_sn() {
		return user_sn;
	}
	public void setUser_sn(String user_sn) {
		this.user_sn = user_sn;
	}
	public int getUsr_rank() {
		return usr_rank;
	}
	public void setUsr_rank(int usr_rank) {
		this.usr_rank = usr_rank;
	}
}