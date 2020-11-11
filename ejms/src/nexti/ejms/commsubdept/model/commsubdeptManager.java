/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합대상지정 manager
 * 설명:
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
	 * 프로그램 관리 DAO 객체를 받아온다.
	 * @return CommsubdeptDAO - 프로그램 관리 DAO 객체
	 */
	private commsubdeptDAO getCommsubdeptDAO(){
		if (dao==null) dao = new commsubdeptDAO(); 
		return dao;
	}
	
	/**
	 * XML String으로 가져오기
	 * @param form
	 * @return ArrayList - 목록
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