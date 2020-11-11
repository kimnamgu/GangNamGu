/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ڵ���� ���� action
 * ����:
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
		String wsr_id = (String)session.getAttribute("user_id");   //�α��� ����
		
		CodeForm codeForm = (CodeForm)form;
		String main_cd = codeForm.getCcd_cd();                     //���ڵ�
		String sub_cd = codeForm.getCcd_sub_cd();                  //���ڵ�
		String cd_name = codeForm.getCcd_name();                   //�ڵ��
		String sub_name= codeForm.getCcd_sub_name();               //���ڵ��
		String cd_desc = codeForm.getCcd_desc();                   //�ڵ� ����
		String gbn = codeForm.getGbn();							   //����(�����ڿ�/�ý��ۿ�)
		main_cd = new DecimalFormat("000").format(Integer.parseInt("0" + main_cd, 10));
		
/*		//�ý��ۿ� �ڵ嵵 ����/����������� �Ѵ�.
 		if(Integer.parseInt(main_cd)< 100){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","�ý��� �ڵ�� ������ �� �����ϴ�."));
			saveMessages(request,messages);
			return mapping.findForward("back");
		}
*/		
		String mode = codeForm.getMode();
		CcdManager codeManager = CcdManager.instance();
		boolean isExist = true;
		
		if("main_i".equals(mode)){
			//���ڵ� �߰�
			isExist = codeManager.existedCcd(main_cd, "...");
			if(isExist){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","�̹� �����ϴ� ���ڵ� �Դϴ�."));
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
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","����Ǿ����ϴ�."));
			saveMessages(request,messages);
			
		} else if("main_d".equals(mode)){
			//���ڵ� ����
			
			codeManager.deleteCcd(main_cd, "%");			
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","�����Ǿ����ϴ�."));
			saveMessages(request,messages);
			
		} else if("main_u".equals(mode)){
			//���ڵ� ����
			CcdBean bean = new CcdBean();
			bean.setCcdcd(main_cd);
			bean.setCcdsubcd("...");
			bean.setCcdname(cd_name);
			bean.setCcddesc(cd_desc);
		    bean.setUptusrid(wsr_id);	
		    
			codeManager.modifyCcd(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","�����Ǿ����ϴ�."));
			saveMessages(request,messages);
			
		} else if("sub_i".equals(mode)){
			//���ڵ� �߰�
			isExist = codeManager.existedCcd(main_cd, sub_cd);
			if(isExist){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","�̹� �����ϴ� ���ڵ� �Դϴ�."));
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
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","����Ǿ����ϴ�."));
			saveMessages(request,messages);
			
		} else if("sub_d".equals(mode)){
			//���ڵ� ����
			
			codeManager.deleteCcd(main_cd, sub_cd);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","�����Ǿ����ϴ�."));
			saveMessages(request,messages);
			
		} else if("sub_u".equals(mode)){
			//���ڵ� ����
			
			CcdBean bean = new CcdBean();
			bean.setCcdcd(main_cd);
			bean.setCcdsubcd(sub_cd);
			bean.setCcdname(sub_name);
			bean.setCcddesc(cd_desc);
		    bean.setUptusrid(wsr_id);	
		    
			codeManager.modifyCcd(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","�����Ǿ����ϴ�."));
			saveMessages(request,messages);			
		}
		
		request.setAttribute("gbn",gbn);
		
		return mapping.findForward("back");
	}
}

