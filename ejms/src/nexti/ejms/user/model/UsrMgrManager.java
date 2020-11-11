/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �������� manager
 * ����:
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

	/***************************************************** ù��° iframe ***************************************************************/
	//�μ� ����Ʈ
	public List deptList(String gubun, String orggbn, String user_id, String dept_depth) throws Exception{
		return getUsrMgrDAO().deptList(gubun, orggbn, user_id, dept_depth);
	}

	//�μ�Ȯ�� ���̺� ��üUPDATE 
	public int updateAllExt(String user_id, String orggbn, String rep_dept){
		return getUsrMgrDAO().updateAllExt(user_id, orggbn, rep_dept); 
	}
	
	//��󿩺�  ���̺� UPDATE 	
	public int updateMainYn(String user_id, String dept_id) {
		return getUsrMgrDAO().updateMainYn(user_id, dept_id);
		
	}

	//��뿩��  ���̺� UPDATE 	
	public int updateUseYn(String dept_id) {
		return getUsrMgrDAO().updateUseYn(dept_id);
		
	}

	//���迩��  ���̺� UPDATE 	
	public int updateConYn(String dept_id) {
		return getUsrMgrDAO().updateConYn(dept_id);
		
	}
	
	/***************************************************** �ι�° iframe ***************************************************************/
	
	//���� �μ� ��� ��������
	public List childDeptList(String dept_id, String orggbn) throws Exception{
		return getUsrMgrDAO().childDeptList(dept_id, orggbn);
	}

	//���� ����� ��� ��������
	public List deptUserList(String dept_id) throws Exception{
		return getUsrMgrDAO().deptUserList(dept_id);
	}

	//����ڰ˻�
	public List userList(String user_name) throws Exception{
		return getUsrMgrDAO().userList(user_name);
	}

	//����� ����
	public int deleteUser(String user_id){
		return getUsrMgrDAO().deleteUser(user_id);
	}

	//�����Ȯ�� ���̺�(USR_EXT) ����
	/*
	public int deleteExt(String user_id){
		return getUsrMgrDAO().deleteExt(user_id);
	}
	*/

	//�����μ� ����	
	public int deleteSub(String p_dept_cd) {
		return getUsrMgrDAO().deleteSub(p_dept_cd);
	}
	
	
	/***************************************************** ����° iframe ***************************************************************/
	
	//���� �μ� �󼼺���
	public UsrMgrBean deptView(String dept_id) throws Exception{
		return getUsrMgrDAO().deptView(dept_id);
	}
	
	//�μ���Ī ��������
	public String getDeptnm(String p_dept_code){
		return getUsrMgrDAO().getDeptnm(p_dept_code);
	}
	
	//���� ����� �󼼺���
	public UsrMgrBean usrView(String user_id) throws Exception{
		return getUsrMgrDAO().usrView(user_id);
	}	
	
	//�ߺ��˻�
	public boolean existedDept(String p_dept_cd) throws Exception{
		return getUsrMgrDAO().existedDept(p_dept_cd);
	}

	//���� ����� �߰�(USR)
	public int insertUser(UsrMgrBean uBean) throws Exception{
		return getUsrMgrDAO().insertUser(uBean);
	}
	
	//���� ����� ����(USR)
	public int modifyUser(UsrMgrBean uBean) throws Exception{
		return getUsrMgrDAO().modifyUser(uBean);
	}

	//���� �μ� �߰�(DEPT)
	public int insertDept(UsrMgrBean uBean) throws Exception{
		return getUsrMgrDAO().insertDept(uBean);
	}
		
	//���� �μ� ����(DEPT)
	public int modifyDept(UsrMgrBean uBean) throws Exception{
		return getUsrMgrDAO().modifyDept(uBean);
	}
	
	//����� ��й�ȣ �ʱ�ȭ
	public int modifyUsrPass(UsrMgrBean uBean) throws Exception{
		return getUsrMgrDAO().modifyUsrPass(uBean);
	}
	
	//���� �˻�
	public List roleList() throws Exception{
		return getUsrMgrDAO().roleList();
	}
	
	//USR�� AGE(���ɴ�)�� SEX(����) ������Ʈ
	public int modifyUsrAgeSex(String user_id) throws Exception{
		return getUsrMgrDAO().modifyUsrAgeSex(user_id);
	}
	
	/**
	 * ���ڰ�����̵� ���
	 * @param userid
	 * @param gpkiid
	 * @return
	 * @throws Exception
	 */
	public int setEaId(String userid, String eaid) throws Exception {
		return getUsrMgrDAO().setEaId(userid, eaid);
	}
	
	/**
	 * ��ϵ� GPKI_ID�� ����� ID
	 * @param gpkiid
	 * @return
	 * @throws Exception
	 */
	public String getUsrGpkiId(String gpkiid) throws Exception {
		return getUsrMgrDAO().getUsrGpkiId(gpkiid);
	}
}
