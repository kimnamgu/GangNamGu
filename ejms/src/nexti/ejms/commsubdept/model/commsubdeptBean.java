/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합대상지정 bean
 * 설명:
 */
package nexti.ejms.commsubdept.model;

public class commsubdeptBean {
	private String code;				/* 대상ID */
	private String name;				/* 대상명 */
	private String displayName;			/* 화면표시-대상명 */
	private String codegbn;				/* 대상구분 */
	private String upperDeptId;			/* 상위부서ID */
	private String grpGbn;				/* 그룹부서구분(그룹1, 부서2) */
	private String mainyn;				/* 선택단위 */
	private int deptLevel;				/* 부서레벨 */
	private String crtusrid;			/* 작성자 ID */
	private String crtusrgbn;			/* 작성자구분  */

	public commsubdeptBean(){
		this.code = "";
		this.name = "";
		this.displayName = "";
		this.codegbn = "";
		this.upperDeptId = "";
		this.deptLevel = 0;
		this.crtusrid = "";
		this.crtusrgbn = "";
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getCodegbn() {
		return codegbn;
	}

	public void setCodegbn(String codegbn) {
		this.codegbn = codegbn;
	}

	public String getCrtusrgbn() {
		return crtusrgbn;
	}

	public void setCrtusrgbn(String crtusrgbn) {
		this.crtusrgbn = crtusrgbn;
	}

	public String getCrtusrid() {
		return crtusrid;
	}

	public void setCrtusrid(String crtusrid) {
		this.crtusrid = crtusrid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String deptId) {
		this.code = deptId;
	}

	public String getName() {
		return name;
	}

	public void setName(String deptName) {
		this.name = deptName;
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

	public String getMainyn() {
		return mainyn;
	}

	public void setMainyn(String mainyn) {
		this.mainyn = mainyn;
	}
}
