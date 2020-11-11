/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���մ������ bean
 * ����:
 */
package nexti.ejms.commsubdept.model;

public class commsubdeptBean {
	private String code;				/* ���ID */
	private String name;				/* ���� */
	private String displayName;			/* ȭ��ǥ��-���� */
	private String codegbn;				/* ��󱸺� */
	private String upperDeptId;			/* �����μ�ID */
	private String grpGbn;				/* �׷�μ�����(�׷�1, �μ�2) */
	private String mainyn;				/* ���ô��� */
	private int deptLevel;				/* �μ����� */
	private String crtusrid;			/* �ۼ��� ID */
	private String crtusrgbn;			/* �ۼ��ڱ���  */

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
