/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합대상지정 배포목록 세부목록 action
 * 설명:
 */
package nexti.ejms.commsubdept.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.commsubdept.model.commsubdeptManager;
import nexti.ejms.common.rootPopupAction;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class grddetailListAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		//세션정보 가져오기
		HttpSession session = request.getSession();	
		String user_id = (String)session.getAttribute("user_id");
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
			}
		}
		
		String grpId = "";
		if(request.getParameter("grpId") != null) {
			grpId = (String)request.getParameter("grpId");
		}
		
		commsubdeptManager manager = commsubdeptManager.instance();
		
		String grdDetailXML = "";
		
		if ( StringUtils.isNumeric(grpId) ) {
			grdDetailXML = manager.getGrpDetailList(grpId, user_id);
		}
		
		response.setContentType("text/xml;charset=UTF-8");
		
		StringBuffer detailListXML = new StringBuffer();
		
		StringBuffer prefixXML = new StringBuffer();
		prefixXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		prefixXML.append("\n<root>");

		
		StringBuffer suffixXML = new StringBuffer();
		suffixXML.append("\n</root>");

		detailListXML.append(prefixXML.toString());
		detailListXML.append(grdDetailXML);
		detailListXML.append(suffixXML.toString());
		
		PrintWriter out = response.getWriter();
		out.println(detailListXML.toString());
		out.flush();
		out.close();
		
		return null;

	}
}