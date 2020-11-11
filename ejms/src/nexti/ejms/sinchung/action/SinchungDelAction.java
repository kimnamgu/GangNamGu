/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ���� action
 * ����:
 */
package nexti.ejms.sinchung.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.sinchung.form.SinchungForm;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.HttpClientHp;
import nexti.ejms.util.Utils;

public class SinchungDelAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
		
		//����������������
		HttpSession session = request.getSession();		
		String user_id = session.getAttribute("user_id").toString();
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
			}
		}
		
		String urlStr = "";
		HttpClientHp hcp = null;
		
		ServletContext context = getServlet().getServletContext();
		
		SinchungForm sform = (SinchungForm)form;
		
		SinchungManager smgr = SinchungManager.instance();
		
		int reqformno = Integer.parseInt(request.getParameter("reqformno"));
		
		String msg = "";
				
		if("2".equals(sform.getRange())){
    		
	        urlStr = appInfo.getOutsideurl()+"/outsideReqDel.do"; 
	        hcp = new HttpClientHp(urlStr); 
	        // �Ѱ��� �Ķ���� ���� 
	        hcp.setParam("reqformno", new Integer(reqformno).toString()); 
	        // setMethodType�� �������� ������ default = 0, (0:get, 1:post, 2:multipart) 
	        hcp.setMethodType(1); 
	
	        int rtnCode = hcp.execute(); // �����ϱ� 
	       
	        if(rtnCode == 200){
	        	if("-1".equals(hcp.getContent())){
	        		msg = "��û Ŭ���̾�ƮIP �Ǵ� ����Ű�� ��ġ ���� �ʾ� �ܺθ�ó���� �����Ͽ����ϴ�.";	
	        	}else if("-2".equals(hcp.getContent())){
	        		msg = "�ܺθ������� ���� ó���� �����Ͽ����ϴ�.";
	        	}else{
	        		int result = smgr.deleteAll(reqformno, user_id, context);
	    			
	    			if(result > 0) {
	    				msg = "��û������ �ڷᰡ  �����Ǿ����ϴ�.";
	    			} else {
	    				msg = "������ ������ �����ϴ�.";
	    			}
	        	}
	        }else{
	        	msg = "�ܺθ� ��ſ����� ���� ó���� �����Ͽ����ϴ�.";	
	        }
		}else{
			int result = smgr.deleteAll(reqformno, user_id, context);
			
			if(result > 0) {
				msg = "��û������ �ڷᰡ  �����Ǿ����ϴ�.";
			} else {
				msg = "������ ������ �����ϴ�.";
			}
		}

//���� ���� ������...
//		int result = smgr.deleteAll(reqformno, userid);
//		
//		String msg = "";
//		
//		if(result > 0){
//			if("2".equals(sform.getRange())){
//				
//				urlStr = appInfo.getOutsideurl()+"/outsideReqDel.do"; 
//				hcp = new HttpClientHp(urlStr); 
//				// �Ѱ��� �Ķ���� ���� 
//				hcp.setParam("reqformno", new Integer(reqformno).toString()); 
//				// setMethodType�� �������� ������ default = 0, (0:get, 1:post, 2:multipart) 
//				hcp.setMethodType(1); 
//				
//				int rtnCode = hcp.execute(); // �����ϱ� 
//				
//				if(rtnCode == 200){
//					if("-1".equals(hcp.getContent())){
//						msg = "��û Ŭ���̾�ƮIP �Ǵ� ����Ű�� ��ġ ���� �ʾ� �ܺθ�ó���� �����Ͽ����ϴ�.";	
//					}else if("-2".equals(hcp.getContent())){
//						msg = "�ܺθ������� ���� ó���� �����Ͽ����ϴ�.";
//					}else{
//						msg = "��û������ �ڷᰡ  �����Ǿ����ϴ�.";
//					}
//				}else{
//					msg = "�ܺθ� ��ſ����� ���� ó���� �����Ͽ����ϴ�.";	
//				}
//			}else{
//				msg = "��û������ �ڷᰡ  �����Ǿ����ϴ�.";	
//			}
//		} else {
//			msg = "������ ������ �����ϴ�.";
//		}
		
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
		saveMessages(request,messages);
		
		return mapping.findForward("list");
	}
}