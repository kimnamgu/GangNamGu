/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문대상지정 추가 action
 * 설명:
 */
package nexti.ejms.commsubdept.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.commsubdept.model.commsubdeptManager;
import nexti.ejms.commsubdept.model.commsubdeptBean;
import nexti.ejms.common.rootPopupAction;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class commrchdeptInsertAction extends rootPopupAction{

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
	
		request.setCharacterEncoding("UTF-8");
		
		String cmd = request.getParameter("cmd");
		int	rchno = Integer.parseInt(request.getParameter("rchno").toString());
		int	rchgrpno = Integer.parseInt(request.getParameter("rchgrpno").toString());
		String idList = request.getParameter("idList");
		String grpgbnList = request.getParameter("grpgbnList");
		String nameList = request.getParameter("nameList");
		
		//세션정보 가져오기
		HttpSession session = request.getSession();	
		String sessionId = session.getId().split("[!]")[0];
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
		
		int ret = 0;
		int x = 0;
		int y = 0;
		ArrayList deptList = new ArrayList();
		
		String[] deptId = null;
		String[] deptName = null;
		String[] grpGbn = null;
		
		String grpCd = "";
		String subCd = "";

		commsubdeptManager manager = commsubdeptManager.instance();
		
		if (!"".equals(idList)){
			deptId = idList.split(";");
		}
		
		if (!"".equals(grpgbnList)){
			grpGbn = grpgbnList.split(";");
		}	
		
		if (!"".equals(nameList)){
			deptName = nameList.split(";");
		}
		
		commsubdeptBean bean = null;
		
		if (deptId != null){
			for (int i = 0; i < deptId.length; i++){
				bean = new commsubdeptBean();
				
				if(grpGbn[i].equals("1")){
					x++;
					if(x !=1){
						grpCd = grpCd + ",";
					}
					grpCd = grpCd + "'" + deptId[i] + "'";

				}else{
					y++;
					if(y !=1){
						subCd = subCd + ",";
					}					
					subCd = subCd + "'" + deptId[i] + "'";
				}
				
				bean.setCode(deptId[i]);
				bean.setName(deptName[i]);
				bean.setGrpGbn(grpGbn[i]);	
				deptList.add(bean);
				bean = null;
			}
		}
		
		if ("INSERT".equals(cmd)){
			if ( Utils.nullToEmptyString(request.getHeader("referer")).indexOf("rchgrpno") != -1 ) {
				ret = manager.commrchGrpdeptInsert(rchgrpno, deptList, sessionId, user_id, grpCd, subCd);
			} else {
				ret = manager.commrchdeptInsert(rchno, deptList, sessionId, user_id, grpCd, subCd);
			}
		}

		String subdeptList = "";
		if ( ret > 0 ) {
			if ( Utils.nullToEmptyString(request.getHeader("referer")).indexOf("rchgrpno") != -1 ) {
				subdeptList = manager.commrchGrpdeptList(rchno, sessionId, user_id);
			} else {
				subdeptList = manager.commrchdeptList(rchno, sessionId, user_id);
			}
		}
		
		StringBuffer resultXML = new StringBuffer();
		resultXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		resultXML.append("\n<result>");
		resultXML.append("\n<cmd>" + cmd + "</cmd>");
		resultXML.append("\n<retCode>" + ret + "</retCode>");
		resultXML.append("\n<subdeptList>" + subdeptList + "</subdeptList>");
		resultXML.append("\n</result>");
		
		response.setContentType("text/xml;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(resultXML.toString());
		out.flush();
		out.close();
		
		return null;
	}
}