/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �μ����� manager
 * ����:
 */
package nexti.ejms.dept.model;

import java.sql.Connection;
import java.util.List;

public class DeptManager {
	private static DeptManager instance = null;
	private static DeptDAO dao = null;
	
	public static DeptManager instance() {
		if (instance==null) instance = new DeptManager(); 
		return instance;
	}
	
	private DeptDAO getDeptDAO(){
		if (dao==null) dao = new DeptDAO(); 
		return dao;
	}
	
	private DeptManager() {
	}
	
	/**
	 * �����μ� ��ϰ�������
	 * @param deptid
	 * @param includeTopDept
	 * @return
	 */
	public List getSubDeptList(String deptid, boolean includeTopDept) throws Exception {
		return getDeptDAO().getSubDeptList(deptid, includeTopDept);
	}
	
	/**
	 * �μ� ������������
	 */
	public DeptBean getDeptInfo(String dept_id)throws Exception {
		DeptBean dept = null;
		
		dept = getDeptDAO().getDeptInfo(dept_id);		
		return dept;
	}	
	
	/**
	 * ������ ���� ��������
	 */
	public List chrgUnitList(String dept_id)throws Exception {
		List result = null;
		
		result = getDeptDAO().chrgUnitList(dept_id);
		
		return result;
	}
	
	/**
	 * ��δ�� ���� ��������
	 */
	public List deliveryUserList(String dept_id)throws Exception {
		List result = null;
		
		result = getDeptDAO().deliveryUserList(dept_id);
		
		return result;
	}
	
	/**
	 * ���κ� ���������� ��������
	 * @param String user_id
	 * @return ChrgUnitBean ��������
	 */
	public ChrgUnitBean chrgUnitInfo(String user_id) throws Exception {
		ChrgUnitBean result = null;
		
		result = getDeptDAO().chrgUnitInfo(user_id);
		
		return result;
	}
	
	/**
	 * ���������� ��������	
	 */
	public ChrgUnitBean chrgUnitInfo(String dept_id, int chrgunitcd) throws Exception {
		ChrgUnitBean result = null;
		
		result = getDeptDAO().chrgUnitInfo(dept_id, chrgunitcd);
		
		return result;
	}
	
	/**
	 * �������ڵ� ��������
	 * @param String user_id
	 * @return int �������ڵ�
	 */
	public int getChrgunitcd(String user_id) throws Exception {
		
		int result = 0;
		
		result = getDeptDAO().getChrgunitcd(user_id);
		
		return result;
	}
	
	/**
	 * ���κ� ��������Ī ��������
	 * @param String dept_code
	 * @param int chrg_code
	 * @return String ��������Ī
	 */
	public String getChrgunitnm(String dept_code, int chrg_code) throws Exception {
		
		String result = "";
		
		result = getDeptDAO().getChrgunitnm(dept_code, chrg_code);
		
		return result;
	}
	
	/** 
	 * ���μ� ���
	 */
	public List tgtDeptList(String orggbn, String user_id, String dept_depth) throws Exception {
		List tgtList = null;
		
		tgtList = getDeptDAO().tgtDeptList(orggbn, user_id, dept_depth);
		
		return tgtList;
	}
	
	/** 
	 * ������ �ڵ� �������� 
	 * return true : ������
	 * return false : �������
	 */
	public boolean existedChrg(String dept_id) throws Exception {
		return getDeptDAO().existedChrg(dept_id);
	}
	
	/** 
	 * ������ �ڵ� �������� 
	 * return true : ������
	 * return false : �������
	 */
	public boolean existedChrg(String dept_id, int chrgunitcd) throws Exception {
		return getDeptDAO().existedChrg(dept_id, chrgunitcd);
	}

	/** 
	 * ������ �ڵ� �������� 
	 * return true : ������
	 * return false : �������
	 */
	public int getExistedChrg(String dept_id) throws Exception {
		return getDeptDAO().getExistedChrg(dept_id);
	}
	
	/** 
	 * ������ �߰�	
	 */
	public int insertChrgUnit (ChrgUnitBean bean) throws Exception {
		return getDeptDAO().insertChrgUnit(bean);
	}

	/** 
	 * ������ ����	
	 */
	public int modifyChrgUnit (ChrgUnitBean bean) throws Exception {
		return getDeptDAO().modifyChrgUnit(bean);
	}

	/** 
	 * ������ ����	
	 */
	public int deleteChrgUnit (String dept_id, int chrgunitcd) throws Exception {
		return getDeptDAO().deleteChrgUnit(dept_id, chrgunitcd);
	}
	
	/** 
	 * ��δ�� ����	
	 */
	public int updateDeliveryUser(String userid, String delivery_yn) throws Exception {
		return getDeptDAO().updateDeliveryUser(userid, delivery_yn);
	}
	
	/** 
	 * DEPT���̺� �űԺμ� �߰�	 
	 */
	public int insertDept(Connection conn, DeptBean bean) throws Exception {
		return getDeptDAO().insertDept(conn, bean);
	}
	
	/** 
	 * DEPT���̺� �����μ� ����
	 */
	public int deleteDept(Connection conn, String dept_id) throws Exception {
		return getDeptDAO().deleteDept(conn, dept_id);
	}
}