/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��������׷� �̸����� action
 * ����:
 */
package nexti.ejms.outside.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.outside.model.OutsideManager;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class OutsideRchGrpViewAction extends Action {
	
	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		 try {
			String uid = Utils.nullToEmptyString((String)request.getParameter("uid"));
			String resident = Utils.nullToEmptyString((String)request.getParameter("resident"));
			String committee = Utils.nullToEmptyString((String)request.getParameter("committee"));
			String msg;
			
			//Form���� �Ѿ�� �� ��������
			ResearchForm rchForm = (ResearchForm)form;
			
			//�������� ��������
			HttpSession session = request.getSession();
			String sessionId = session.getId().split("[!]")[0];
			
			int rchgrpno = 0;
			String mode = "preview";
			
			if(request.getParameter("rchgrpno") != null) {
				rchgrpno = Integer.parseInt(request.getParameter("rchgrpno"));
			}
			
			if(request.getParameter("mode") != null) {
				mode = request.getParameter("mode");
			}
			
			//�������� ������  ��������
			OutsideManager manager = OutsideManager.instance();
			ResearchBean bean = manager.getRchGrpMst(rchgrpno, sessionId);
			if ( bean == null ) {
                //������û �������� �󼼸޽��� ���(JHkim, 14.01.06)
                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<li>�ش� ������ ���ų� �������� ������ �ƴմϴ�.</li>";
                    
                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                    saveMessages(request,messages);
        
                    return mapping.findForward("hidden");
                }else{
    				response.setContentType("text/html;charset=euc-kr");
    				PrintWriter out = response.getWriter();
    				out.write("<script language=javascript>alert('�������� �������簡 �����ϴ�.');window.close();</script>");
    				out.close();
    				return null;
                }
			}
	
			if ( "research".equals(mode) ) {
				//�������� üũ
				String rangedetail = bean.getRangedetail();
				if ( ("22".equals(rangedetail) && "".equals(uid))
					|| ("23".equals(rangedetail) && !"y".equals(resident))
					|| ("24".equals(rangedetail) && !"y".equals(committee)) ) {
				  //������û �������� �󼼸޽��� ���(JHkim, 14.01.06)
	                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
	                    ActionMessages messages = new ActionMessages();
	                    if ( "22".equals(rangedetail) && "".equals(uid) ){
	                        msg = "<li>�ش� ������ Ȩ������ ȸ���� ��쿡�� ������ �����մϴ�.</li>" +
	                            "<li>Ȩ������ �α��� �� �ٽ� ������ �ּ���.</li>";
	                    }else if ( "23".equals(rangedetail) && !"y".equals(resident) ){
	                        msg = "<li>�ش� ������ �ּ����� �������� ��쿡�� ������ �����մϴ�.</li>" +
	                            "<li>�α��� �� MY���� ���������������� �ּ����� Ȯ���� �ּ���.</li>";
	                    }else if ( "24".equals(rangedetail) && !"y".equals(committee) ){
	                        msg = "<li>�ش� ������ ������ �ֹ���ġ������ ������ �����մϴ�.</li>";
	                    }else{
	                        msg = "<li>���� ������ ������ �߻��Ͽ����ϴ�. �̿뿡 �������� �˼��մϴ�.</li>" +
	                            "<li>���� : ���������� ���ȣ 02-3423-5317</li>";
	                    }
	                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
	                    saveMessages(request,messages);
	        
	                    return mapping.findForward("hidden");
	                }else{
    					response.setContentType("text/html;charset=euc-kr");
    					PrintWriter out = response.getWriter();
    					out.write("<script language=javascript>alert('���� ����� �ƴմϴ�.');window.close();</script>");
    					out.close();
    					return null;
	                }
				}
				
				int cnt = manager.rchGrpState(rchgrpno);
				
				if ( cnt == 0 ) {
	                //������û �������� �󼼸޽��� ���(JHkim, 14.01.06)
	                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
	                    msg = "<li>�������簡 ����Ǿ��ų� ��������� �ƴմϴ�.</li>";
	                    
	                    ActionMessages messages = new ActionMessages();
	                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
	                    saveMessages(request,messages);
	        
	                    return mapping.findForward("hidden");
	                }else{
    			    	response.setContentType("text/html;charset=euc-kr");
    					PrintWriter out = response.getWriter();
    					out.write("<script language=javascript>alert('�������簡 ����Ǿ��ų� ��������� �ƴմϴ�.');window.close();</script>");
    					out.close();
    					return null;
	                }
				}
			}
			
			BeanUtils.copyProperties(rchForm,bean);
			
			String tel = "";
			UserBean ubean = UserManager.instance().getUserInfo(rchForm.getChrgusrid());
			if ( ubean != null ) {
				tel = ubean.getTel();
			}
			rchForm.setColdepttel(tel);
			
			rchForm.setMode(mode);
	
			rchForm.setCrtusrid(uid);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return mapping.findForward("view");
	}
}