/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문대상지정 조직도 action
 * 설명:
 */
package nexti.ejms.commsubdept.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.commsubdept.model.commsubdeptManager;
import nexti.ejms.common.rootPopupAction;

public class researchDeptFormationListAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		commsubdeptManager manager = commsubdeptManager.instance();
		
		String searchType = (String)request.getParameter("searchType");
		if ( searchType == null || searchType.trim().length() == 0 ) searchType = "0";
		
		String searchValue = (String)request.getParameter("searchValue");
		if ( searchValue == null || searchValue.trim().length() == 0 ) searchValue = "";
		
		String formationTree = manager.getResearchDeptFormationList(searchType, searchValue);

	    response.setContentType("text/xml;charset=UTF-8");
	    
	    PrintWriter out = response.getWriter(); 
    	out.write(formationTree);
	    out.flush();
	    out.close();
		
		return null;
	}
}