/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합대상지정 추가 action
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

import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.commsubdept.model.commsubdeptManager;
import nexti.ejms.commsubdept.model.commsubdeptBean;
import nexti.ejms.common.rootPopupAction;
import nexti.ejms.messanger.MessangerRelay;
import nexti.ejms.messanger.MessangerRelayBean;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class commsubdeptInsertAction extends rootPopupAction{

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
	
		request.setCharacterEncoding("UTF-8");
		
		String cmd = request.getParameter("cmd");
		int	sysdocno =0;
		sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		String idList = request.getParameter("idList");
		String grpgbnList = request.getParameter("grpgbnList");
		String nameList = request.getParameter("nameList");
		
		//세션정보 가져오기
		HttpSession session = request.getSession();	
		String sessionId = session.getId().split("[!]")[0];
		String user_id = (String)session.getAttribute("user_id");
		String dept_code = (String)session.getAttribute("dept_code");
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
				dept_code = originUserBean.getDept_id();
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
		
		String docState = "0";
				
		if ("INSERT".equals(cmd)){
			if ( sysdocno != 0 ) {
				docState = ColldocManager.instance().getColldoc(sysdocno).getDocstate();
			}
			ret = manager.commsubdeptInsert(sysdocno, docState, deptList, sessionId, user_id, dept_code, grpCd, subCd);
		}
		
		String subdeptList = "";
		if ( ret > 0 ) {
			subdeptList = manager.getCommsubdeptList(sysdocno, dept_code, sessionId, user_id);
			
			if ( "04".equals(docState) ) {
				MessangerRelayBean mrBean = new MessangerRelayBean();
				mrBean.setRelayMode(MessangerRelay.COLLECT_START);
				mrBean.setSysdocno(sysdocno);
				new MessangerRelay(mrBean).start();
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