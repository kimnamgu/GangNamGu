/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직도 데이터 로딩 action
 * 설명:
 */
package nexti.ejms.formation.action;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nexti.ejms.common.rootAction;
import nexti.ejms.formation.model.FormationManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FormationAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String user_id = Utils.nullToEmptyString((String)session.getAttribute("user_id"));  
		String type = Utils.nullToEmptyString(request.getParameter("type"));
		String orggbn = Utils.nullToEmptyString(request.getParameter("orggbn"));
		String orgid = Utils.nullToEmptyString(request.getParameter("orgid"));
		String selectedItem = Utils.nullToEmptyString(request.getParameter("selectedItem"));
		String searchKey = Utils.nullToEmptyString(request.getParameter("searchKey"));
		String searchValue = Utils.nullToEmptyString(request.getParameter("searchValue"));
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
			}
		}
		
		FormationManager fmgr = FormationManager.instance();

		String resultXML = fmgr.getFormation(user_id, type, orggbn, orgid, selectedItem, searchKey, URLDecoder.decode(searchValue, "UTF-8"));
		
		response.setContentType("text/xml;charset=UTF-8");
		response.getWriter().print(resultXML);
		
		return null;
	}
}