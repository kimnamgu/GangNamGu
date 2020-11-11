/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 결재선지정,입력담당자지정 bean
 * 설명:
 */
package nexti.ejms.commapproval.model;

public class commapprovalBean {
	private String deptId;				// 부서ID 
	private String deptName;			// 부서명 
	private String userId;				// 사용자ID 
	private String userName;			// 사용자명
	private String upperDeptId;			// 상위부서ID 
	private String className;			// 직급명 
	private String chrgunitcd;			//담당단위코드	
	private String chrgunitnm;			//담당단위명
	private int deptLevel;				// 부서레벨 
	private String gubun;				// 승인검토여부 
	private String sancYn;				// 결제여부
	private String sancdt;              // 결재일시 
	private String submitdt;			// 기안일시
	private String grade_id;			// 계급ID
	private String grade_name;			// 계급명
	private String tgtcode;				// 대상코드
	private String tgtname;				// 대상명
	private String tgtgbn;				// 대상구분
	
	public String getTgtcode() {
		return tgtcode;
	}

	public void setTgtcode(String tgtcode) {
		this.tgtcode = tgtcode;
	}

	public String getTgtname() {
		return tgtname;
	}

	public void setTgtname(String tgtname) {
		this.tgtname = tgtname;
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

	public String getSancYn() {
		return sancYn;
	}

	public void setSancYn(String sancYn) {
		this.sancYn = sancYn;
	}

	public commapprovalBean(){
		this.deptId = "";
		this.deptName = "";
		this.userId = "";
		this.userName = "";
		this.upperDeptId = "";
		this.deptLevel = 0;
		this.sancdt = "";
		this.submitdt = "";
		this.grade_id = "";
		this.grade_name = "";
		this.tgtcode = "";
		this.tgtname = "";
		this.tgtgbn = "";
	}
	
	public String getSubmitdt() {
		return submitdt;
	}

	public void setSubmitdt(String submitdt) {
		this.submitdt = submitdt;
	}

	public String getSancdt() {
		return sancdt;
	}

	public void setSancdt(String sancdt) {
		this.sancdt = sancdt;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUpperDeptId() {
		return upperDeptId;
	}

	public void setUpperDeptId(String upperDeptId) {
		this.upperDeptId = upperDeptId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(int deptLevel) {
		this.deptLevel = deptLevel;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getChrgunitcd() {
		return chrgunitcd;
	}

	public void setChrgunitcd(String chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
	}

	public String getChrgunitnm() {
		return chrgunitnm;
	}

	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}

	public String getTgtgbn() {
		return tgtgbn;
	}

	public void setTgtgbn(String tgtgbn) {
		this.tgtgbn = tgtgbn;
	}
}
