/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���缱����,�Է´�������� bean
 * ����:
 */
package nexti.ejms.commapproval.model;

public class commapprovalBean {
	private String deptId;				// �μ�ID 
	private String deptName;			// �μ��� 
	private String userId;				// �����ID 
	private String userName;			// ����ڸ�
	private String upperDeptId;			// �����μ�ID 
	private String className;			// ���޸� 
	private String chrgunitcd;			//�������ڵ�	
	private String chrgunitnm;			//��������
	private int deptLevel;				// �μ����� 
	private String gubun;				// ���ΰ��俩�� 
	private String sancYn;				// ��������
	private String sancdt;              // �����Ͻ� 
	private String submitdt;			// ����Ͻ�
	private String grade_id;			// ���ID
	private String grade_name;			// ��޸�
	private String tgtcode;				// ����ڵ�
	private String tgtname;				// ����
	private String tgtgbn;				// ��󱸺�
	
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
