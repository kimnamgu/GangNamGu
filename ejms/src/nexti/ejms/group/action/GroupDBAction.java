/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배포목록관리 저장 action
 * 설명:
 */
package nexti.ejms.group.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.group.form.GroupForm;
import nexti.ejms.group.model.GroupManager;
import nexti.ejms.group.model.GroupBean;

public class GroupDBAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {		
		
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		String usr_id = (String)session.getAttribute("user_id");   //로그인 유저
		
		GroupForm groupForm = (GroupForm)form;
		int grplistcd = groupForm.getGrplistcd();        //주코드
		String grplistnm = groupForm.getGrplistnm();        //코드명
		int ord = groupForm.getOrd();                   	//코드순서
	
		String mode = groupForm.getMode();
		GroupManager Manager = GroupManager.instance();
		boolean isExist = true;
		
		if("main_i".equals(mode)){
			//주코드 추가
			isExist = Manager.existedGrp(grplistcd);
			if(isExist){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","이미 존재하는 주코드 입니다."));
				saveMessages(request,messages);
				return mapping.findForward("back");
			}
			
			GroupBean bean = new GroupBean();
			bean.setGrplistcd(grplistcd);
			bean.setGrplistnm(grplistnm);
			bean.setOrd(ord);
		    bean.setCrtusrid(usr_id);
		    bean.setCrtusrgbn("0");
			Manager.insertGrpMst(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","저장되었습니다."));
			saveMessages(request,messages);
			
		} else if("main_d".equals(mode)){
			//주코드 삭제
			
			Manager.deleteGrpMst(grplistcd);			
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","삭제되었습니다."));
			saveMessages(request,messages);
			
		} else if("main_u".equals(mode)){
			//주코드 수정
			GroupBean bean = new GroupBean();
			bean.setGrplistcd(grplistcd);
			bean.setGrplistnm(grplistnm);
			bean.setOrd(ord);
		    bean.setUptusrid(usr_id);	
		    
			Manager.modifyGrpMst(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","수정되었습니다."));
			saveMessages(request,messages);
			
		} else if("sub_i".equals(mode)){
			String idList = request.getParameter("idList");
			String grpgbnList = request.getParameter("grpgbnList");
			String nameList = request.getParameter("nameList");
			String sch_grplist = request.getParameter("sch_grplist");
			String cmd = request.getParameter("cmd");
			
			ArrayList deptList = new ArrayList();
			
			String[] deptCd = null;
			String[] deptGbn = null;
			String[] deptName = null;
			
			if (!"".equals(idList)){
				deptCd = idList.split(";");
			}
			
			if (!"".equals(grpgbnList)){
				deptGbn = grpgbnList.split(";");
			}
			
			if (!"".equals(nameList)){
				deptName = nameList.split(";");
			}
			
			GroupBean bean = null;
			if (deptCd != null){
				for (int i = 0; i < deptCd.length; i++){
					bean = new GroupBean();

					bean.setCode(deptCd[i]);
					bean.setName(deptName[i]);
					bean.setCodegbn(("0".equals(deptGbn[i]) || "2".equals(deptGbn[i])) ? "0" : "1");
					deptList.add(bean);
					bean = null;
				}
			}			
			 
			int ret = Manager.insertGrpDtl(Integer.parseInt("0" + sch_grplist), deptList);
			
			StringBuffer resultXML = new StringBuffer();
			
			resultXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			resultXML.append("\n<result>");
			resultXML.append("\n<cmd>" + cmd + "</cmd>");
			resultXML.append("\n<retCode>" + ret + "</retCode>");
			resultXML.append("\n</result>");
			
			response.setContentType("text/xml;charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println(resultXML.toString());
			out.flush();
			out.close();
			
			return null;
			
		} else if("sub_d".equals(mode)){
			int seq = groupForm.getSeq();
			//부코드 삭제
			
			Manager.deleteGrpDtl(grplistcd, seq);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","삭제되었습니다."));
			saveMessages(request,messages);
			
		} 
		
		return mapping.findForward("back");
	}	
}

