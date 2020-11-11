/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배포목록관리 코드수정 action
 * 설명:
 */
package nexti.ejms.group.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.group.form.GroupForm;
import nexti.ejms.group.model.GroupManager;
import nexti.ejms.group.model.GroupBean;

public class GroupPopupAction extends rootPopupAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
	 	throws Exception {
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");

		GroupForm groupForm = (GroupForm)form;
		GroupManager Manager = GroupManager.instance();
		
		String mode = request.getParameter("mode");
		int grplistcd = groupForm.getGrplistcd();        //주코드	
		
		if("main_i".equals(mode)){
			return mapping.findForward("mainI");		
		}else if("main_m".equals(mode)){
			//주코드 Popup
			GroupBean bean = Manager.getGrpMstInfo(grplistcd);
			
			if(bean != null){
				groupForm.setGrplistnm(bean.getGrplistnm());
				groupForm.setOrd(bean.getOrd());
			}
			
			return mapping.findForward("mainM");
		}else if("sub_l".equals(mode)){
			String sch_grplist = request.getParameter("sch_grplist");
			
			String grpdeptXML = "";
			
			grpdeptXML = Manager.getGrpDtlXml(Integer.parseInt("0" + sch_grplist), user_id);
			
			response.setContentType("text/xml;charset=UTF-8");
			
			StringBuffer deptViewXML = new StringBuffer();
			
			StringBuffer prefixXML = new StringBuffer();
			prefixXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			prefixXML.append("\n<root>");

			
			StringBuffer suffixXML = new StringBuffer();
			suffixXML.append("\n</root>");

			deptViewXML.append(prefixXML.toString());
			deptViewXML.append(grpdeptXML);
			deptViewXML.append(suffixXML.toString());
			
			PrintWriter out = response.getWriter();
			out.println(deptViewXML.toString());
			out.flush();
			out.close();
			
			return null;
		} else {
			return mapping.findForward("subI");
		}
	}
}

