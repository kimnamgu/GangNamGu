/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 부서정보 manager
 * 설명:
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
	 * 하위부서 목록가져오기
	 * @param deptid
	 * @param includeTopDept
	 * @return
	 */
	public List getSubDeptList(String deptid, boolean includeTopDept) throws Exception {
		return getDeptDAO().getSubDeptList(deptid, includeTopDept);
	}
	
	/**
	 * 부서 정보가져오기
	 */
	public DeptBean getDeptInfo(String dept_id)throws Exception {
		DeptBean dept = null;
		
		dept = getDeptDAO().getDeptInfo(dept_id);		
		return dept;
	}	
	
	/**
	 * 담당단위 정보 가져오기
	 */
	public List chrgUnitList(String dept_id)throws Exception {
		List result = null;
		
		result = getDeptDAO().chrgUnitList(dept_id);
		
		return result;
	}
	
	/**
	 * 배부담당 정보 가져오기
	 */
	public List deliveryUserList(String dept_id)throws Exception {
		List result = null;
		
		result = getDeptDAO().deliveryUserList(dept_id);
		
		return result;
	}
	
	/**
	 * 개인별 담당단위정보 가져오기
	 * @param String user_id
	 * @return ChrgUnitBean 담당단위정
	 */
	public ChrgUnitBean chrgUnitInfo(String user_id) throws Exception {
		ChrgUnitBean result = null;
		
		result = getDeptDAO().chrgUnitInfo(user_id);
		
		return result;
	}
	
	/**
	 * 담당단위정보 가져오기	
	 */
	public ChrgUnitBean chrgUnitInfo(String dept_id, int chrgunitcd) throws Exception {
		ChrgUnitBean result = null;
		
		result = getDeptDAO().chrgUnitInfo(dept_id, chrgunitcd);
		
		return result;
	}
	
	/**
	 * 담당단위코드 가져오기
	 * @param String user_id
	 * @return int 담당단위코드
	 */
	public int getChrgunitcd(String user_id) throws Exception {
		
		int result = 0;
		
		result = getDeptDAO().getChrgunitcd(user_id);
		
		return result;
	}
	
	/**
	 * 개인별 담당단위명칭 가져오기
	 * @param String dept_code
	 * @param int chrg_code
	 * @return String 담당단위명칭
	 */
	public String getChrgunitnm(String dept_code, int chrg_code) throws Exception {
		
		String result = "";
		
		result = getDeptDAO().getChrgunitnm(dept_code, chrg_code);
		
		return result;
	}
	
	/** 
	 * 대상부서 목록
	 */
	public List tgtDeptList(String orggbn, String user_id, String dept_depth) throws Exception {
		List tgtList = null;
		
		tgtList = getDeptDAO().tgtDeptList(orggbn, user_id, dept_depth);
		
		return tgtList;
	}
	
	/** 
	 * 담당단위 코드 존재유무 
	 * return true : 존재함
	 * return false : 존재안함
	 */
	public boolean existedChrg(String dept_id) throws Exception {
		return getDeptDAO().existedChrg(dept_id);
	}
	
	/** 
	 * 담당단위 코드 존재유무 
	 * return true : 존재함
	 * return false : 존재안함
	 */
	public boolean existedChrg(String dept_id, int chrgunitcd) throws Exception {
		return getDeptDAO().existedChrg(dept_id, chrgunitcd);
	}

	/** 
	 * 담당단위 코드 존재유무 
	 * return true : 존재함
	 * return false : 존재안함
	 */
	public int getExistedChrg(String dept_id) throws Exception {
		return getDeptDAO().getExistedChrg(dept_id);
	}
	
	/** 
	 * 담당단위 추가	
	 */
	public int insertChrgUnit (ChrgUnitBean bean) throws Exception {
		return getDeptDAO().insertChrgUnit(bean);
	}

	/** 
	 * 담당단위 수정	
	 */
	public int modifyChrgUnit (ChrgUnitBean bean) throws Exception {
		return getDeptDAO().modifyChrgUnit(bean);
	}

	/** 
	 * 담당단위 삭제	
	 */
	public int deleteChrgUnit (String dept_id, int chrgunitcd) throws Exception {
		return getDeptDAO().deleteChrgUnit(dept_id, chrgunitcd);
	}
	
	/** 
	 * 배부담당 지정	
	 */
	public int updateDeliveryUser(String userid, String delivery_yn) throws Exception {
		return getDeptDAO().updateDeliveryUser(userid, delivery_yn);
	}
	
	/** 
	 * DEPT테이블에 신규부서 추가	 
	 */
	public int insertDept(Connection conn, DeptBean bean) throws Exception {
		return getDeptDAO().insertDept(conn, bean);
	}
	
	/** 
	 * DEPT테이블에 폐지부서 삭제
	 */
	public int deleteDept(Connection conn, String dept_id) throws Exception {
		return getDeptDAO().deleteDept(conn, dept_id);
	}
}