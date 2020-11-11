/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직도 manager
 * 설명:
 */
package nexti.ejms.formation.model;

import java.util.List;

//import org.apache.log4j.Logger;

import nexti.ejms.common.appInfo;

public class FormationManager {
	//private static Logger logger = Logger.getLogger(FormationManager.class);
	
	private static FormationManager instance = null;
	private static FormationDAO dao = null;
	
	private FormationManager() {}
	private FormationDAO getFormationDAO() {
		if ( dao == null ) dao = new FormationDAO(); 
		return dao;
	}
	
	public static FormationManager instance() {
		if ( instance == null ) instance = new FormationManager();
		return instance;
	}
	
	/**
	 * 조직도 불러오기
	 * @param type
	 * @param orggbn
	 * @param orgid
	 * @return
	 * @throws Exception
	 */
	public String getFormation(String userid, String type, String orggbn, String orgid, String selectedItem, String searchKey, String searchValue) throws Exception {
		StringBuffer result = null;
		List formationList = null;
		boolean forceFolding = false;
		boolean forceFolderIcon = false;
		
		if ( orgid.equals("") ) {
			orgid = appInfo.getRootid();
		}
		
		if ( type.equals("DEPTUSER") ) {		//부서+사용자 : 취합대상부서지정, 입력담당자지정
			forceFolding = true;
			formationList = getFormationDAO().getFormationDeptUser(orggbn, orgid, searchKey, searchValue, userid);
		} else if ( type.equals("DEPT") ) {		//부서 : 신청하기, 관리자지정
			forceFolding = forceFolderIcon = true;
			formationList = getFormationDAO().getFormationDept(orggbn, orgid, userid);
		} else if ( type.equals("GROUP") ) {		//그룹 : 배포목록
			String delImg = "/images/common/btn_icon_del.gif";
			String onclickHandler = "saveDistribute";
			orgid = "GROUP";
			formationList = getFormationDAO().getFormationGroup(orggbn, userid, delImg, onclickHandler);
		} else if ( type.equals("DEPT_EXCLUDE_005") ) {		//공통양식관리 : 자치시/구/군(005)는 나오지 않게
			forceFolding = forceFolderIcon = true;
			formationList = getFormationDAO().getFormationDeptExclude005(orggbn, orgid, userid);
		}

		result = new StringBuffer();
		result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		result.append("<result rootId=\"" + orgid + "\" forceFolding=\"" + forceFolding + "\" " +
						"forceFolderIcon=\"" + forceFolderIcon + "\"  selectedItem=\"" + selectedItem + "\">\n");
		
		for ( int i = 0; formationList != null && i < formationList.size(); i++ ) {
			FormationBean fbean = (FormationBean)formationList.get(i);
			result.append("<formation>\n");
			result.append("\t<level>" + fbean.getLevel() + "</level>\n");
			result.append("\t<grpgbn>" + fbean.getGrpgbn() + "</grpgbn>\n");
			result.append("\t<main_yn>" + fbean.getMain_yn() + "</main_yn>\n");
			result.append("\t<upper_dept_id>" + fbean.getUpper_dept_id() + "</upper_dept_id>\n");
			result.append("\t<upper_dept_name>" + fbean.getUpper_dept_name() + "</upper_dept_name>\n");
			result.append("\t<upper_dept_fullname>" + fbean.getUpper_dept_fullname() + "</upper_dept_fullname>\n");
			result.append("\t<dept_id>" + fbean.getDept_id() + "</dept_id>\n");
			result.append("\t<dept_name>" + fbean.getDept_name() + "</dept_name>\n");
			result.append("\t<dept_fullname>" + fbean.getDept_fullname() + "</dept_fullname>\n");
			result.append("</formation>\n");
		}
		result.append("</result>\n");
		
		return result.toString();
	}
}