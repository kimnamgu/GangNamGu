/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 조직관리 manager
 * 설명:
 */
package nexti.ejms.user.model;

import java.util.List;

public class UsrMgrManager {
	private static UsrMgrManager instance = null;
	private static UsrMgrDAO dao = null;
	
	private UsrMgrManager() {
	}
	
	public static UsrMgrManager instance() {
		if (instance==null) instance = new UsrMgrManager(); 
		return instance;
	}
	
	private UsrMgrDAO getUsrMgrDAO(){
		if (dao==null) dao = new UsrMgrDAO(); 
		return dao;
	}	

	/***************************************************** 첫번째 iframe ***************************************************************/
	//부서 리스트
	public List deptList(String gubun, String orggbn, String user_id, String dept_depth) throws Exception{
		return getUsrMgrDAO().deptList(gubun, orggbn, user_id, dept_depth);
	}

	//부서확장 테이블 전체UPDATE 
	public int updateAllExt(String user_id, String orggbn, String rep_dept){
		return getUsrMgrDAO().updateAllExt(user_id, orggbn, rep_dept); 
	}
	
	//대상여부  테이블 UPDATE 	
	public int updateMainYn(String user_id, String dept_id) {
		return getUsrMgrDAO().updateMainYn(user_id, dept_id);
		
	}

	//사용여부  테이블 UPDATE 	
	public int updateUseYn(String dept_id) {
		return getUsrMgrDAO().updateUseYn(dept_id);
		
	}

	//연계여부  테이블 UPDATE 	
	public int updateConYn(String dept_id) {
		return getUsrMgrDAO().updateConYn(dept_id);
		
	}
	
	/***************************************************** 두번째 iframe ***************************************************************/
	
	//하위 부서 목록 가져오기
	public List childDeptList(String dept_id, String orggbn) throws Exception{
		return getUsrMgrDAO().childDeptList(dept_id, orggbn);
	}

	//하위 사용자 목록 가져오기
	public List deptUserList(String dept_id) throws Exception{
		return getUsrMgrDAO().deptUserList(dept_id);
	}

	//담당자검색
	public List userList(String user_name) throws Exception{
		return getUsrMgrDAO().userList(user_name);
	}

	//사용자 삭제
	public int deleteUser(String user_id){
		return getUsrMgrDAO().deleteUser(user_id);
	}

	//사용자확장 테이블(USR_EXT) 삭제
	/*
	public int deleteExt(String user_id){
		return getUsrMgrDAO().deleteExt(user_id);
	}
	*/

	//하위부서 삭제	
	public int deleteSub(String p_dept_cd) {
		return getUsrMgrDAO().deleteSub(p_dept_cd);
	}
	
	
	/***************************************************** 세번째 iframe ***************************************************************/
	
	//하위 부서 상세보기
	public UsrMgrBean deptView(String dept_id) throws Exception{
		return getUsrMgrDAO().deptView(dept_id);
	}
	
	//부서명칭 가져오기
	public String getDeptnm(String p_dept_code){
		return getUsrMgrDAO().getDeptnm(p_dept_code);
	}
	
	//하위 사용자 상세보기
	public UsrMgrBean usrView(String user_id) throws Exception{
		return getUsrMgrDAO().usrView(user_id);
	}	
	
	//중복검사
	public boolean existedDept(String p_dept_cd) throws Exception{
		return getUsrMgrDAO().existedDept(p_dept_cd);
	}

	//하위 사용자 추가(USR)
	public int insertUser(UsrMgrBean uBean) throws Exception{
		return getUsrMgrDAO().insertUser(uBean);
	}
	
	//하위 사용자 수정(USR)
	public int modifyUser(UsrMgrBean uBean) throws Exception{
		return getUsrMgrDAO().modifyUser(uBean);
	}

	//하위 부서 추가(DEPT)
	public int insertDept(UsrMgrBean uBean) throws Exception{
		return getUsrMgrDAO().insertDept(uBean);
	}
		
	//하위 부서 수정(DEPT)
	public int modifyDept(UsrMgrBean uBean) throws Exception{
		return getUsrMgrDAO().modifyDept(uBean);
	}
	
	//사용자 비밀번호 초기화
	public int modifyUsrPass(UsrMgrBean uBean) throws Exception{
		return getUsrMgrDAO().modifyUsrPass(uBean);
	}
	
	//직급 검색
	public List roleList() throws Exception{
		return getUsrMgrDAO().roleList();
	}
	
	//USR에 AGE(연령대)랑 SEX(성별) 업데이트
	public int modifyUsrAgeSex(String user_id) throws Exception{
		return getUsrMgrDAO().modifyUsrAgeSex(user_id);
	}
	
	/**
	 * 전자결재아이디 등록
	 * @param userid
	 * @param gpkiid
	 * @return
	 * @throws Exception
	 */
	public int setEaId(String userid, String eaid) throws Exception {
		return getUsrMgrDAO().setEaId(userid, eaid);
	}
	
	/**
	 * 등록된 GPKI_ID의 사용자 ID
	 * @param gpkiid
	 * @return
	 * @throws Exception
	 */
	public String getUsrGpkiId(String gpkiid) throws Exception {
		return getUsrMgrDAO().getUsrGpkiId(gpkiid);
	}
}
