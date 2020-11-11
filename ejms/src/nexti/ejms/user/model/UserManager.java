/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��������� manager
 * ����:
 */
package nexti.ejms.user.model;

import java.sql.Connection;

public class UserManager {	
	private static UserManager instance = null;
	private static UserDAO dao = null;
	
	private UserManager() {
	}
	
	public static UserManager instance() {
		if (instance==null) instance = new UserManager(); 
		return instance;
	}
	
	private UserDAO getUserDAO(){
		if (dao==null) dao = new UserDAO(); 
		return dao;
	}
	
	/**
	 * �α��� ó��
	 * return userid
	 * @throws Exception 
	 */
	public String login(String userId, String password) throws Exception{		
		String result = "";
		System.out.println("login id :"+userId);
		System.out.println("login ps :"+password);
		result = getUserDAO().matchIdPwd(userId,password);		
		
		return result;
	}

	/**
	 * �α��� ó��(�ֹι�ȣ�� ���� �α���)
	 * return userid
	 * @throws Exception 
	 */
	public String login(String user_sn) throws Exception{		
		String result = "";
		
		result = getUserDAO().matchUserSn(user_sn);		
		
		return result;
	}

	/**
	 * ��ǥ�μ� �ڵ�(��û�� ��õ������, �������� ��õ������ �ٷ� �� STEP �Ʒ� ���)
	 */
	public String selectRepDept(String userId, String orggbn) throws Exception{	
		return getUserDAO().selectRepDept(userId, orggbn);		
	}
	
	
	/**
	 * �ֱٷα������� ����
	 */
	public int updateLoginfo(String userid) throws Exception {
		return getUserDAO().updateLoginfo(userid);
	}

	/**
	 * �ֱٷα������� ���� - [��]���ӷαװ��� == LOGINLOG
	 */
	public void insertLoginLog(String userid, String sessi, String method, String ip, String dept_id, String rep_dept) throws Exception {
		getUserDAO().insertLoginLog(userid, sessi, method, ip, dept_id, rep_dept);
		return;
	}
	
	/**
	 * �������� �α��ν� ����,���ɴ� ����
	 */
	public int updateResearchLoginfo(String userid, String sex, int age) throws Exception {
		return getUserDAO().updateResearchLoginfo(userid, sex, age);
	}
	
	/**
	 * ����� ������������
	 * @throws Exception 
	 */
	public String getDeptName(String user_id) throws Exception{
		return getUserDAO().getDeptName(user_id);
	}

	/**
	 * ����� ������������
	 * @throws Exception 
	 */
	public UserBean getUserInfo(String user_id) throws Exception{
		UserBean user = null;
		
		user = getUserDAO().getUserInfo(user_id);		
		return user;
	}
	
	/**
	 * ����� ���� üũ
	 * @throws Exception 
	 */
	public boolean existedUser(String userId) throws Exception {
		System.out.println("use :"+userId);
		return getUserDAO().existedUser(userId);
	}
	
	/**
	 * ����� ���� üũ : �ֹι�ȣ
	 * @throws Exception 
	 */
	public boolean existedUserSN(String usersn) throws Exception {
		return getUserDAO().existedUserSN(usersn);
	}
	
	/**
	 * ����� �������ڵ� ����
	 * @param String user_id
	 * @param int chrg_code
	 * @return int ���ళ��
	 * @throws Exception 
	 */
	public int updateChrgunitcd(String user_id, int chrg_code) throws Exception{
		
		int result = 0;
		
		result = getUserDAO().updateChrgunitcd(user_id, chrg_code);
		
		return result;
	}
	
	/**
	 * �α����� ����� �������ڵ� ��������(INPUTUSR ���̺�)
	 * 
	 * @param usrid : �����ID
	 * 
	 * @return int : �������ڵ� 
	 * @throws Exception 
	 */
	public int getUsrChrgunitcd(String usrid) throws Exception {
		return getUserDAO().getUsrChrgunitcd(usrid);
	}
	
	
	/** 
	 * 
	 */
	public UserBean getUserInfoForRegno(String user_sn) throws Exception { 
		
		return getUserDAO().getUserInfoForRegno(user_sn);
	}
	
	/** 
	 * �ű� ���̵� ���� ��ȣ
	 */
	public String getNextIdSeq(Connection con, String resiNumberLen) throws Exception{ 
		
		return getUserDAO().getNextIdSeq(con, resiNumberLen);
	}
	
	/** 
	 * �ű� ���̵� ���� ��ȣ
	 */
	public String getNextId(String gbn) throws Exception{ 
		return getUserDAO().getNextId(gbn);
	}
}