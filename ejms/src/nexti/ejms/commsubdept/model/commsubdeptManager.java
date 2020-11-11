/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���մ������ manager
 * ����:
 */
package nexti.ejms.commsubdept.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nexti.ejms.commsubdept.model.commsubdeptDAO;

public class commsubdeptManager {

	private static commsubdeptManager instance = null;
	private static commsubdeptDAO dao = null;
	
	public static commsubdeptManager instance() {
		if (instance==null) instance = new commsubdeptManager(); 
		return instance;
	}
	
	/**
	 * ���α׷� ���� DAO ��ü�� �޾ƿ´�.
	 * @return CommsubdeptDAO - ���α׷� ���� DAO ��ü
	 */
	private commsubdeptDAO getCommsubdeptDAO(){
		if (dao==null) dao = new commsubdeptDAO(); 
		return dao;
	}
	
	/**
	 * XML String���� ��������
	 * @param form
	 * @return ArrayList - ���
	 * @throws SQLException
	 */
	public String getTargetDeptFormationList(String searchType, String searchValue) throws Exception{

		return getCommsubdeptDAO().getTargetDeptFormationList(searchType, searchValue);
	}
	
	public String getResearchDeptFormationList(String searchType, String searchValue) throws Exception{
		
		return getCommsubdeptDAO().getResearchDeptFormationList(searchType, searchValue);
	}
	
	public String getFormationList() throws Exception{
		
		return getCommsubdeptDAO().getFormationList();
	}
	
	public String getDistributeList(String user_id, String delImg, String onclickHandler) throws Exception{

		return getCommsubdeptDAO().getDistributeList(user_id, delImg, onclickHandler);
	}
	
	public String getDeptViewXML(List xml) throws Exception {
		
		String result = null;
		
		result = getCommsubdeptDAO().getDeptViewXML(xml);
		
		return result;
	}
	
	public String getCommsubdeptView(int sysdocno, String predeptcd, String sessionId, String userid) throws Exception {
		
		String result = null;
		
		List deptList = getCommsubdeptDAO().getCommsubdeptList(sysdocno, predeptcd, sessionId, userid);
		
		result = getDeptViewXML(deptList);
		
		return result;
	}
	
	public String getCommsubdeptList(int sysdocno, String predeptcd, String sessionId, String userid) throws Exception {
		
		StringBuffer result = new StringBuffer();
		
		List deptList = getCommsubdeptDAO().getCommsubdeptList(sysdocno, predeptcd, sessionId, userid);
		for ( int i = 0; i < deptList.size(); i++ ) {
			commsubdeptBean bean = (commsubdeptBean)deptList.get(i);
			
			if ( result.toString().equals("") == false ) result.append(", "); 
			result.append(bean.getDisplayName());
		}
		
		return result.toString();
	}
	
	public String selectDeptXml(String deptId) throws Exception {

		return getCommsubdeptDAO().selectDeptXml(deptId);
	}
	
	public String getGrpDetailList(String grpId, String userid) throws Exception {

		return getCommsubdeptDAO().getGrpDetailList(grpId, userid);
	}
	
	public int commsubdeptInsert(int sysdocno, String docState, ArrayList deptList, String sessionId, String userid, String deptid, String grpCd, String subCd) throws Exception{
		return getCommsubdeptDAO().commsubdeptInsert(sysdocno, docState, deptList, sessionId, userid, deptid, grpCd, subCd);
	}
	
	public String getProcessChk(int sysdocno, String deptcd, String usrid, String gbn, String type)throws Exception{
		String result = "";
		
		result = getCommsubdeptDAO().getProcessChk(sysdocno, deptcd, usrid, gbn, type);
		
		return result;
	}

	public String getUserList(String deptCode, String userName) throws Exception {

		return getCommsubdeptDAO().getUserList(deptCode, userName);
	}	
	
	public String getCommrchdeptView(int rchno, String sessionId, String userid) throws Exception {
		
		String result = null;
		List deptList = getCommsubdeptDAO().commrchdeptList(rchno, sessionId, userid);
		
		result = getDeptViewXML(deptList);
		
		return result;
	}
	
	public String getCommrchGrpdeptView(int rchgrpno, String sessionId, String userid) throws Exception {
		
		String result = null;
		List deptList = getCommsubdeptDAO().commrchGrpdeptList(rchgrpno, sessionId, userid);
		
		result = getDeptViewXML(deptList);
		
		return result;
	}
	
	public int commrchdeptInsert(int rchno, ArrayList deptList, String sessionId, String usrid, String grpCd, String subCd) throws Exception{
		return getCommsubdeptDAO().commrchdeptInsert(rchno, deptList, sessionId, usrid, grpCd, subCd);
	}
	
	public int commrchGrpdeptInsert(int rchgrpno, ArrayList deptList, String sessionId, String usrid, String grpCd, String subCd) throws Exception{
		return getCommsubdeptDAO().commrchGrpdeptInsert(rchgrpno, deptList, sessionId, usrid, grpCd, subCd);
	}
	
	public String commrchdeptList(int rchno, String sessionId, String userid) throws Exception {
		
		StringBuffer result = new StringBuffer();
		
		List deptList = getCommsubdeptDAO().commrchdeptList(rchno, sessionId, userid);
		for ( int i = 0; i < deptList.size(); i++ ) {
			commsubdeptBean bean = (commsubdeptBean)deptList.get(i);
			
			if ( result.toString().equals("") == false ) result.append(", "); 
			result.append(bean.getDisplayName());
		}
		
		return result.toString();
	}
	
	public String commrchGrpdeptList(int rchgrpno, String sessionId, String userid) throws Exception {
		
		StringBuffer result = new StringBuffer();
		
		List deptList = getCommsubdeptDAO().commrchGrpdeptList(rchgrpno, sessionId, userid);
		for ( int i = 0; i < deptList.size(); i++ ) {
			commsubdeptBean bean = (commsubdeptBean)deptList.get(i);
			
			if ( result.toString().equals("") == false ) result.append(", "); 
			result.append(bean.getDisplayName());
		}
		
		return result.toString();
	}
}