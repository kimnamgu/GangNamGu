/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 관리자변경 bean
 * 설명:
 */
package nexti.ejms.commsubdept.model;

public class commdeptuserBean {
	private String userId;				/*사용자ID*/
	private String userName;			/*사용자명*/
	private String deptId;				/* 부서ID */
	private String deptName;			/* 부서명 */
	private String upperDeptId;			/* 상위부서ID */
	private String grpGbn;				/* 그룹부서구분(그룹1, 부서2) */
	private String mainfl;				/* 선택단위 */
	private int deptLevel;				/* 부서레벨 */ 

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
