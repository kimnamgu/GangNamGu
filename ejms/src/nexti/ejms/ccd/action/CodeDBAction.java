/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 코드관리 저장 action
 * 설명:
 */
package nexti.ejms.ccd.action;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.ccd.form.CodeForm;
import nexti.ejms.ccd.model.CcdManager;
import nexti.ejms.ccd.model.CcdBean;

public class CodeDBAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {		
		
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		String wsr_id = (String)session.getAttribute("user_id");   //로그인 유저
		
		CodeForm codeForm = (CodeForm)form;
		String main_cd = codeForm.getCcd_cd();                     //주코드
		String sub_cd = codeForm.getCcd_sub_cd();                  //부코드
		String cd_name = codeForm.getCcd_name();                   //코드명
		String sub_name= codeForm.getCcd_sub_name();               //부코드명
		String cd_desc = codeForm.getCcd_desc();                   //코드 설명
		String gbn = codeForm.getGbn();							   //구분(관리자용/시스템용)
		main_cd = new DecimalFormat("000").format(Integer.parseInt("0" + main_cd, 10));
		
/*		//시스템용 코드도 수정/삭제가능토록 한다.
 		if(Integer.parseInt(main_cd)< 100){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","시스템 코드는 수정할 수 없습니다."));
			saveMessages(request,messages);
			return mapping.findForward("back");
		}
*/		
		String mode = codeForm.getMode();
		CcdManager codeManager = CcdManager.instance();
		boolean isExist = true;
		
		if("main_i".equals(mode)){
			//주코드 추가
			isExist = codeManager.existedCcd(main_cd, "...");
			if(isExist){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","이미 존재하는 주코드 입니다."));
				saveMessages(request,messages);
				return mapping.findForward("back");
			}
			
			CcdBean bean = new CcdBean();
			bean.setCcdcd(main_cd);
			bean.setCcdsubcd("...");
			bean.setCcdname(cd_name);
			bean.setCcddesc(cd_desc);
		    bean.setCrtusrid(wsr_id);			 
			codeManager.insertCcd(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","저장되었습니다."));
			saveMessages(request,messages);
			
		} else if("main_d".equals(mode)){
			//주코드 삭제
			
			codeManager.deleteCcd(main_cd, "%");			
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","삭제되었습니다."));
			saveMessages(request,messages);
			
		} else if("main_u".equals(mode)){
			//주코드 수정
			CcdBean bean = new CcdBean();
			bean.setCcdcd(main_cd);
			bean.setCcdsubcd("...");
			bean.setCcdname(cd_name);
			bean.setCcddesc(cd_desc);
		    bean.setUptusrid(wsr_id);	
		    
			codeManager.modifyCcd(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","수정되었습니다."));
			saveMessages(request,messages);
			
		} else if("sub_i".equals(mode)){
			//부코드 추가
			isExist = codeManager.existedCcd(main_cd, sub_cd);
			if(isExist){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","이미 존재하는 부코드 입니다."));
				saveMessages(request,messages);
				return mapping.findForward("back");
			}
			
			CcdBean bean = new CcdBean();
			bean.setCcdcd(main_cd);
			bean.setCcdsubcd(sub_cd);
			bean.setCcdname(cd_name);
			bean.setCcddesc(cd_desc);
		    bean.setCrtusrid(wsr_id);			 
			codeManager.insertCcd(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","저장되었습니다."));
			saveMessages(request,messages);
			
		} else if("sub_d".equals(mode)){
			//부코드 삭제
			
			codeManager.deleteCcd(main_cd, sub_cd);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","삭제되었습니다."));
			saveMessages(request,messages);
			
		} else if("sub_u".equals(mode)){
			//부코드 수정
			
			CcdBean bean = new CcdBean();
			bean.setCcdcd(main_cd);
			bean.setCcdsubcd(sub_cd);
			bean.setCcdname(sub_name);
			bean.setCcddesc(cd_desc);
		    bean.setUptusrid(wsr_id);	
		    
			codeManager.modifyCcd(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","수정되었습니다."));
			saveMessages(request,messages);			
		}
		
		request.setAttribute("gbn",gbn);
		
		return mapping.findForward("back");
	}
}

