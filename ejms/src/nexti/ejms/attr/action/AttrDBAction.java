/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 속성목록관리 처리 action
 * 설명:
 */
package nexti.ejms.attr.action;

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
import nexti.ejms.attr.form.AttrForm;
import nexti.ejms.attr.model.AttrManager;
import nexti.ejms.attr.model.AttrBean;

public class AttrDBAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {		
		
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");  //로그인 유저
		
		AttrForm attrForm = (AttrForm)form;
		String listcd     = attrForm.getListcd();                  //목록 코드
		int seq           = attrForm.getSeq();                     //일련번호
		String listnm     = attrForm.getListnm();                  //마스터명
		String listdtlnm  = attrForm.getListdtlnm();               //상세명
		String attr_desc  = attrForm.getAttr_desc();               //상세설명
		String gbn = attrForm.getGbn();							   //구분(관리자용/시스템용)
		listcd = new DecimalFormat("00000").format(Integer.parseInt("0" + listcd, 10));
		
/*		//시스템용 코드도 수정/삭제가능토록 한다.
 		if(Integer.parseInt(main_cd)< 100){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","시스템 코드는 수정할 수 없습니다."));
			saveMessages(request,messages);
			return mapping.findForward("back");
		}
*/		
		String mode = attrForm.getMode();
		AttrManager attrManager = AttrManager.instance();
		boolean isExist = true;
		
		if("main_i".equals(mode)){
			//속성 추가
			isExist = attrManager.existedMst(listcd);
			if(isExist){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","이미 존재하는 속성목록 코드 입니다."));
				saveMessages(request,messages);
				return mapping.findForward("back");
			}
			
			AttrBean bean = new AttrBean();
			bean.setListcd(listcd);
			bean.setListnm(listnm);		
		    bean.setCrtusrid(user_id);
		    bean.setCrtusrgbn("0");
			attrManager.insertAttrMst(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","저장되었습니다."));
			saveMessages(request,messages);
			
		} else if("main_d".equals(mode)){
			//속성 삭제
			
			attrManager.deleteAttrMst(listcd);			
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","삭제되었습니다."));
			//saveMessages(request,messages);
			
		} else if("main_u".equals(mode)){
			//속성 수정
			AttrBean bean = new AttrBean();
			bean.setListcd(listcd);
			bean.setListnm(listnm);		
		    bean.setCrtusrid(user_id);	
		    
			attrManager.modifyAttrMst(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","수정되었습니다."));
			saveMessages(request,messages);
			
		} else if("sub_i".equals(mode)){
			//속성상세 추가
			isExist = attrManager.existedDtl(listcd, seq);
			if(isExist){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","이미 존재하는 부코드 입니다."));
				saveMessages(request,messages);
				return mapping.findForward("back");
			}
			
			AttrBean bean = new AttrBean();
			bean.setListcd(listcd);
			bean.setSeq(seq);
			bean.setListdtlnm(listdtlnm);
			bean.setAttr_desc(attr_desc);
		    bean.setCrtusrid(user_id);			 
			attrManager.insertAttrDtl(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","저장되었습니다."));
			saveMessages(request,messages);
			
		} else if("sub_d".equals(mode)){
			//속성상세 삭제
			
			attrManager.deleteAttrDtl(listcd, seq);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","삭제되었습니다."));
			//saveMessages(request,messages);
			
		} else if("sub_u".equals(mode)){
			//속성상세 수정
			
			AttrBean bean = new AttrBean();
			bean.setListcd(listcd);
			bean.setSeq(seq);
			bean.setListdtlnm(listdtlnm);
			bean.setAttr_desc(attr_desc);
		    bean.setCrtusrid(user_id);
		    
			attrManager.modifyAttrDtl(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","수정되었습니다."));
			saveMessages(request,messages);			
		}
		
		request.setAttribute("gbn",gbn);
		
		return mapping.findForward("back");
	}
}

