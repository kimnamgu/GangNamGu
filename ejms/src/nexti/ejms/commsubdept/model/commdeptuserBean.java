/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ں��� bean
 * ����:
 */
package nexti.ejms.commsubdept.model;

public class commdeptuserBean {
	private String userId;				/*�����ID*/
	private String userName;			/*����ڸ�*/
	private String deptId;				/* �μ�ID */
	private String deptName;			/* �μ��� */
	private String upperDeptId;			/* �����μ�ID */
	private String grpGbn;				/* �׷�μ�����(�׷�1, �μ�2) */
	private String mainfl;				/* ���ô��� */
	private int deptLevel;				/* �μ����� */ 

	public commdeptuserBean(){
		this.userId = "";
		this.userName = "";
		this.deptId = "";
		this.deptName = "";
		this.upperDeptId = "";
		this.deptLevel = 0;
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

	public int getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(int deptLevel) {
		this.deptLevel = deptLevel;
	}

	public String getGrpGbn() {
		return grpGbn;
	}

	public void setGrpGbn(String grpGbn) {
		this.grpGbn = grpGbn;
	}

	public String getMainfl() {
		return mainfl;
	}

	public void setMainfl(String mainfl) {
		this.mainfl = mainfl;
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
}
