/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ܺθ� ��û�� �ۼ� action
 * ����:
 */
package nexti.ejms.outside.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.sinchung.form.SinchungForm;
import nexti.ejms.sinchung.model.FrmBean;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.util.Utils;
import nexti.ejms.outside.model.OutsideManager;

public class OutsideReqViewAction extends Action {
	
	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		try {
			//System.out.println("OutsideReqViewAction start");
			String uid = Utils.nullToEmptyString((String)request.getParameter("uid"));
			String resident = Utils.nullToEmptyString((String)request.getParameter("resident"));
			String committee = Utils.nullToEmptyString((String)request.getParameter("committee"));
			String msg;
			
			//Form ���� ��������
			SinchungForm sForm = (SinchungForm)form;
			int reqformno = sForm.getReqformno();
			
			OutsideManager manager = OutsideManager.instance();
			FrmBean frmbean = manager.reqFormInfo(reqformno);
			if ( frmbean == null ) {
			  //������û ��û�� �󼼸޽��� ���(JHkim, 14.01.07)
                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>�ش� ��û���� ���ų� �������� ��û���� �ƴմϴ�.";
                    
                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                    saveMessages(request,messages);
        
                    return mapping.findForward("hidden");
                }else{
    				response.setContentType("text/html;charset=euc-kr");
    				PrintWriter out = response.getWriter();
    				out.write("<script language=javascript>alert('�������� ��û���� �����ϴ�.');window.close();</script>");
    				out.close();
    				return null;
                }
			}
			
			//��û���� üũ
			String rangedetail = frmbean.getRangedetail();
			if ( ("22".equals(rangedetail) && "".equals(uid))
				|| ("23".equals(rangedetail) && !"y".equals(resident))
				|| ("24".equals(rangedetail) && !"y".equals(committee)) ) {
			    //������û ��û�� �󼼸޽��� ���(JHkim, 14.01.06)
                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    ActionMessages messages = new ActionMessages();
                    if ( "22".equals(rangedetail) && "".equals(uid) ){
                        msg = "�ش� ��û���� Ȩ������ ȸ���� ��쿡�� ������ �����մϴ�." +
                            "<br>Ȩ������ �α��� �� �ٽ� ������ �ּ���.";
                    }else if ( "23".equals(rangedetail) && !"y".equals(resident) ){
                        msg = "�ش� ��û���� �ּ����� �������� ��쿡�� ������ �����մϴ�." +
                            "<br>�α��� �� MY���� ���������������� �ּ����� Ȯ���� �ּ���.";
                    }else if ( "24".equals(rangedetail) && !"y".equals(committee) ){
                        msg = "�ش� ��û���� ������ �ֹ���ġ������ ������ �����մϴ�.";
                    }else{
                        msg = "��û�� ������ ������ �߻��Ͽ����ϴ�. �̿뿡 �������� �˼��մϴ�." +
                            "<br>���� : ���������� ���ȣ 02-3423-5317";
                    }
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                    saveMessages(request,messages);
        
                    return mapping.findForward("hidden");
                }else{
    				response.setContentType("text/html;charset=euc-kr");
    				PrintWriter out = response.getWriter();
    				out.write("<script language=javascript>alert('��û�� ���� ����� �ƴմϴ�.');window.close();</script>");
    				out.close();
    				return null;
                }
			}
			
			//��û�� �Ⱓüũ ��������
			int cnt = manager.reqState(reqformno, uid);
			
			if ( cnt != 0 && "1".equals(manager.reqFormInfo(reqformno).getDuplicheck()) ) {
				Cookie[] cookies = request.getCookies();
				if ( cookies != null ) {
					for ( int i = 0; i < cookies.length; i++ ) {
						Cookie c = cookies[i];
						String name = c.getName();
						String value = c.getValue();
						if ( name.equals("cookie_ejms_outside_req") && value.equals("outside_req"+reqformno) ) cnt = -1;
					}		
				}
			}
			
			if ( cnt == 0 ) {
			    //������û ��û�� �󼼸޽��� ���(JHkim, 14.01.06)
                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>��û�� ������ ����Ǿ����ϴ�.";
                    
                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                    saveMessages(request,messages);
        
                    return mapping.findForward("hidden");
                }else{
    		    	response.setContentType("text/html;charset=euc-kr");
    				PrintWriter out = response.getWriter();
    				out.write("<script language=javascript>alert('��û�� ������  ����Ǿ����ϴ�.');window.close();</script>");
    				out.close();
    				return null;
                }
			} else if ( cnt < 0 ) {
	            //������û ��û�� �󼼸޽��� ���(JHkim, 14.01.06)
                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) == -1 ){
                    msg = "<br>�̹� �����Ͻ� ��û���Դϴ�.";
                	//msg = "<img src=\"/images/request_comp2.gif\" alt=\"�����Ͻ� ��û���Դϴ�\">";
                }else{
                   /* msg = "<img src=\"/images/request_comp2.gif\" alt=\"�����Ͻ� ��û���Դϴ�\">";*/
                    msg = "<br>�̹� �����Ͻ� ��û���Դϴ�.";
                }
				//System.out.println("msg : "+msg);
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
				saveMessages(request,messages);
	
				//System.out.println("OutsideReqViewAction end");
				return mapping.findForward("hidden");
			}
			
			BeanUtils.copyProperties(sForm, frmbean);		
			
			//�׸�(�������)��������
			List articleList = manager.reqFormSubList(reqformno);
			sForm.setArticleList(articleList);
	
			sForm.setRbean(new ReqMstBean());
			
			sForm.setCrtusrid(uid);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		//System.out.println("OutsideReqViewAction end");
		return mapping.findForward("view");
	}
}