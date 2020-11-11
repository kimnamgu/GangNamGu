/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 담당단위관리 저장 action
 * 설명:
 */
package nexti.ejms.chrgUnit.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.chrgUnit.form.ChrgUnitForm;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.dept.model.ChrgUnitBean;

public class ChrgUnitDBAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {		
		
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");   //로그인 유저
		
		ChrgUnitForm chrgForm = (ChrgUnitForm)form;
		String dept_id = chrgForm.getDept_id();                     //부서코드
		int chrgunitcd = chrgForm.getChrgunitcd();                  //담당단위 코드
		String chrgunitnm = chrgForm.getChrgunitnm();               //담당단위 명칭	
		int ord = chrgForm.getOrd();                                //정렬순서
		String mode = chrgForm.getMode();                           //추가(i), 수정(u), 삭제(d)
		
		DeptManager deptMgr = DeptManager.instance();
		boolean isExist = true;
					
		if("i".equals(mode)){
			//담당단위 추가
			isExist = deptMgr.existedChrg(dept_id, chrgunitcd);
			if(isExist){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","이미 존재하는 담당단위코드 입니다."));
				saveMessages(request,messages);
				return mapping.findForward("back");
			}
			
			ChrgUnitBean bean = new ChrgUnitBean();
			bean.setDept_id(dept_id);
			bean.setChrgunitcd(chrgunitcd);
			bean.setChrgunitnm(chrgunitnm);
			bean.setOrd(ord);		
			bean.setCrtusrid(user_id);
			
			deptMgr.insertChrgUnit(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","저장되었습니다."));
			saveMessages(request,messages);
			
		} else if("d".equals(mode)){
			//담당단위 삭제
			
			deptMgr.deleteChrgUnit(dept_id, chrgunitcd);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","삭제되었습니다."));
			saveMessages(request,messages);
			
		} else if("u".equals(mode)){
			//담당단위 수정
			
			ChrgUnitBean bean = new ChrgUnitBean();
			bean.setDept_id(dept_id);
			bean.setChrgunitcd(chrgunitcd);
			bean.setChrgunitnm(chrgunitnm);
			bean.setOrd(ord);		
			bean.setCrtusrid(user_id);	
		    
		    deptMgr.modifyChrgUnit(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","수정되었습니다."));
			saveMessages(request,messages);			
		}	
		
		return mapping.findForward("back");
	}
}