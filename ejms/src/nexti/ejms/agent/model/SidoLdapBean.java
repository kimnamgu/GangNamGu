package nexti.ejms.agent.model;

public class SidoLdapBean {
	private int		seq					= 0;
	private String 	logdate 			= "";	//일시(YYYY-MM-DD)
	private int    	logseq 				= 0;	//순서
	private String 	usergb 				= "";	//사용자구분(0:사용자, 1:부서)
	private String 	dn 					= "";	//ldap에표현된dn
	private String 	code 				= "";	//코드(사용자:UID, 조직:OUCODE)
	private String 	type 				= "";	//명령형식(I:insert, U:update, D:delete)
		
	//USR_TEMP_LDAP
	private String  resinumber        	= "";	//주민번호
	private String  displayname       	= "";	//성명
	private String  oucode            	= "";	//부서코드
	private String  ou                	= "";	//부서명
	private String  orgfullname       	= "";	//전체부서명
	private String  parentoucode      	= "";	//차상위부서코드
	private String  positioncode      	= "";	//직급코드
	private String  position          	= "";	//직급명
	private String  titlecode         	= "";	//직위코드
	private String  title             	= "";	//직위명
	private String  titleorposition   	= "";	//직책명
	private String  usrorder          	= "";	//서열
	private String  homephone         	= "";	//전화번호
	private String  mail              	= "";	//이메일
	private String  mobile            	= "";	//휴대폰번호
	private String  status            	= "";	//사용자상태
	private String  cmdtype           	= "";	//명령형식
	private String  last_attribute    	= "";	//최종 변경 ATTRIBURE
	private String  apply_yn          	= "";	//반영여부
	private String  applydt           	= "";	//반영일자
	private String  crtdt             	= "";	//등록일자
	private String  uptdt             	= "";	//수정일자
	private String  user_id           	= "";  	//사용자ID
		
	//DEPT_TEMP_LDAP
	private String  topoucode         	= "";	//최상위조직코드
	private String  oulevel           	= "";	//조직차수
	private String  ouorder           	= "";	//조직서열
	private String  telephonenumber   	= "";	//대표전화
	private String  oufax             	= "";	//팩스번호
	private String  issidoonly        	= "";	//임시조직여
	private String  virparentoucode   	= "";	//가상코드
	private String	gender				= "";	//성별

	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getApply_yn() {
		return apply_yn;
	}
	public void setApply_yn(String apply_yn) {
		this.apply_yn = apply_yn;
	}
	public String getApplydt() {
		return applydt;
	}
	public void setApplydt(String applydt) {
		this.applydt = applydt;
	}
	public String getCmdtype() {
		return cmdtype;
	}
	public void setCmdtype(String cmdtype) {
		this.cmdtype = cmdtype;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCrtdt() {
		return crtdt;
	}
	public void setCrtdt(String crtdt) {
		this.crtdt = crtdt;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public String getHomephone() {
		return homephone;
	}
	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}
	public String getIssidoonly() {
		return issidoonly;
	}
	public void setIssidoonly(String issidoonly) {
		this.issidoonly = issidoonly;
	}
	public String getLast_attribute() {
		return last_attribute;
	}
	public void setLast_attribute(String last_attribute) {
		this.last_attribute = last_attribute;
	}
	public String getLogdate() {
		return logdate;
	}
	public void setLogdate(String logdate) {
		this.logdate = logdate;
	}
	public int getLogseq() {
		return logseq;
	}
	public void setLogseq(int logseq) {
		this.logseq = logseq;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOrgfullname() {
		return orgfullname;
	}
	public void setOrgfullname(String orgfullname) {
		this.orgfullname = orgfullname;
	}
	public String getOu() {
		return ou;
	}
	public void setOu(String ou) {
		this.ou = ou;
	}
	public String getOucode() {
		return oucode;
	}
	public void setOucode(String oucode) {
		this.oucode = oucode;
	}
	public String getOufax() {
		return oufax;
	}
	public void setOufax(String oufax) {
		this.oufax = oufax;
	}
	public String getOulevel() {
		return oulevel;
	}
	public void setOulevel(String oulevel) {
		this.oulevel = oulevel;
	}
	public String getOuorder() {
		return ouorder;
	}
	public void setOuorder(String ouorder) {
		this.ouorder = ouorder;
	}
	public String getParentoucode() {
		return parentoucode;
	}
	public void setParentoucode(String parentoucode) {
		this.parentoucode = parentoucode;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPositioncode() {
		return positioncode;
	}
	public void setPositioncode(String positioncode) {
		this.positioncode = positioncode;
	}
	public String getResinumber() {
		return resinumber;
	}
	public void setResinumber(String resinumber) {
		this.resinumber = resinumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTelephonenumber() {
		return telephonenumber;
	}
	public void setTelephonenumber(String telephonenumber) {
		this.telephonenumber = telephonenumber;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitlecode() {
		return titlecode;
	}
	public void setTitlecode(String titlecode) {
		this.titlecode = titlecode;
	}
	public String getTitleorposition() {
		return titleorposition;
	}
	public void setTitleorposition(String titleorposition) {
		this.titleorposition = titleorposition;
	}
	public String getTopoucode() {
		return topoucode;
	}
	public void setTopoucode(String topoucode) {
		this.topoucode = topoucode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUptdt() {
		return uptdt;
	}
	public void setUptdt(String uptdt) {
		this.uptdt = uptdt;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUsergb() {
		return usergb;
	}
	public void setUsergb(String usergb) {
		this.usergb = usergb;
	}
	public String getUsrorder() {
		return usrorder;
	}
	public void setUsrorder(String usrorder) {
		this.usrorder = usrorder;
	}
	public String getVirparentoucode() {
		return virparentoucode;
	}
	public void setVirparentoucode(String virparentoucode) {
		this.virparentoucode = virparentoucode;
	}
	
	
}
