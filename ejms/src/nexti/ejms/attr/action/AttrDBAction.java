/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Ӽ���ϰ��� ó�� action
 * ����:
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
		String user_id = (String)session.getAttribute("user_id");  //�α��� ����
		
		AttrForm attrForm = (AttrForm)form;
		String listcd     = attrForm.getListcd();                  //��� �ڵ�
		int seq           = attrForm.getSeq();                     //�Ϸù�ȣ
		String listnm     = attrForm.getListnm();                  //�����͸�
		String listdtlnm  = attrForm.getListdtlnm();               //�󼼸�
		String attr_desc  = attrForm.getAttr_desc();               //�󼼼���
		String gbn = attrForm.getGbn();							   //����(�����ڿ�/�ý��ۿ�)
		listcd = new DecimalFormat("00000").format(Integer.parseInt("0" + listcd, 10));
		
/*		//�ý��ۿ� �ڵ嵵 ����/����������� �Ѵ�.
 		if(Integer.parseInt(main_cd)< 100){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","�ý��� �ڵ�� ������ �� �����ϴ�."));
			saveMessages(request,messages);
			return mapping.findForward("back");
		}
*/		
		String mode = attrForm.getMode();
		AttrManager attrManager = AttrManager.instance();
		boolean isExist = true;
		
		if("main_i".equals(mode)){
			//�Ӽ� �߰�
			isExist = attrManager.existedMst(listcd);
			if(isExist){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","�̹� �����ϴ� �Ӽ���� �ڵ� �Դϴ�."));
				saveMessages(request,messages);
				return mapping.findForward("back");
			}
			
			AttrBean bean = new AttrBean();
			bean.setListcd(listcd);
			bean.setListnm(listnm);		
		    bean.setCrtusrid(user_id);
		    bean.setCrtusrgbn("0");
			attrManager.insertAttrMst(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","����Ǿ����ϴ�."));
			saveMessages(request,messages);
			
		} else if("main_d".equals(mode)){
			//�Ӽ� ����
			
			attrManager.deleteAttrMst(listcd);			
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","�����Ǿ����ϴ�."));
			//saveMessages(request,messages);
			
		} else if("main_u".equals(mode)){
			//�Ӽ� ����
			AttrBean bean = new AttrBean();
			bean.setListcd(listcd);
			bean.setListnm(listnm);		
		    bean.setCrtusrid(user_id);	
		    
			attrManager.modifyAttrMst(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","�����Ǿ����ϴ�."));
			saveMessages(request,messages);
			
		} else if("sub_i".equals(mode)){
			//�Ӽ��� �߰�
			isExist = attrManager.existedDtl(listcd, seq);
			if(isExist){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","�̹� �����ϴ� ���ڵ� �Դϴ�."));
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
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","����Ǿ����ϴ�."));
			saveMessages(request,messages);
			
		} else if("sub_d".equals(mode)){
			//�Ӽ��� ����
			
			attrManager.deleteAttrDtl(listcd, seq);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","�����Ǿ����ϴ�."));
			//saveMessages(request,messages);
			
		} else if("sub_u".equals(mode)){
			//�Ӽ��� ����
			
			AttrBean bean = new AttrBean();
			bean.setListcd(listcd);
			bean.setSeq(seq);
			bean.setListdtlnm(listdtlnm);
			bean.setAttr_desc(attr_desc);
		    bean.setCrtusrid(user_id);
		    
			attrManager.modifyAttrDtl(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","�����Ǿ����ϴ�."));
			saveMessages(request,messages);			
		}
		
		request.setAttribute("gbn",gbn);
		
		return mapping.findForward("back");
	}
}

