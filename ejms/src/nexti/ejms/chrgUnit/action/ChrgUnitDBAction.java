/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���������� ���� action
 * ����:
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
		String user_id = (String)session.getAttribute("user_id");   //�α��� ����
		
		ChrgUnitForm chrgForm = (ChrgUnitForm)form;
		String dept_id = chrgForm.getDept_id();                     //�μ��ڵ�
		int chrgunitcd = chrgForm.getChrgunitcd();                  //������ �ڵ�
		String chrgunitnm = chrgForm.getChrgunitnm();               //������ ��Ī	
		int ord = chrgForm.getOrd();                                //���ļ���
		String mode = chrgForm.getMode();                           //�߰�(i), ����(u), ����(d)
		
		DeptManager deptMgr = DeptManager.instance();
		boolean isExist = true;
					
		if("i".equals(mode)){
			//������ �߰�
			isExist = deptMgr.existedChrg(dept_id, chrgunitcd);
			if(isExist){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","�̹� �����ϴ� �������ڵ� �Դϴ�."));
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
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","����Ǿ����ϴ�."));
			saveMessages(request,messages);
			
		} else if("d".equals(mode)){
			//������ ����
			
			deptMgr.deleteChrgUnit(dept_id, chrgunitcd);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","�����Ǿ����ϴ�."));
			saveMessages(request,messages);
			
		} else if("u".equals(mode)){
			//������ ����
			
			ChrgUnitBean bean = new ChrgUnitBean();
			bean.setDept_id(dept_id);
			bean.setChrgunitcd(chrgunitcd);
			bean.setChrgunitnm(chrgunitnm);
			bean.setOrd(ord);		
			bean.setCrtusrid(user_id);	
		    
		    deptMgr.modifyChrgUnit(bean);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg","�����Ǿ����ϴ�."));
			saveMessages(request,messages);			
		}	
		
		return mapping.findForward("back");
	}
}