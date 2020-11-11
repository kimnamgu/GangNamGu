/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� ���� action
 * ����:
 */
package nexti.ejms.research.action;

import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.common.appInfo;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.research.model.ResearchManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.HttpClientHp;

public class ResearchSaveAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		System.out.println("ResearchSaveAction start");
		int result = 0;
		int count = 0;
		String urlStr = "";
		String msg = "";
		
		HttpClientHp hcp = null;
		
		ResearchManager manager = ResearchManager.instance();
		
		//Form ��������
		ResearchForm rchForm = (ResearchForm)form;
		System.out.println("head cont ===> " + rchForm.getHeadcont());
		//�����μ� ��ȭ��ȣ
		String tel = "";
		UserBean ubean = UserManager.instance().getUserInfo(rchForm.getChrgusrid());
		
		System.out.println("ubean ===> " + ubean);
		
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		rchForm.setColdepttel(tel);

		ServletContext context = getServlet().getServletContext();

		//������ ���丮 ����
		Calendar cal = Calendar.getInstance();
		String saveDir = appInfo.getResearchSampleDir() + cal.get(Calendar.YEAR) + "/";
		
		System.out.println("Range ==> "+rchForm.getRange());
		
		if("2".equals(rchForm.getRange())){
			int anscount = 0;
			ResearchBean rbean = manager.getRchMst(rchForm.getMdrchno(), rchForm.getSessionId());
			if(rbean != null) {
				anscount = rbean.getAnscount();
			}
			
	        urlStr = appInfo.getOutsideurl()+"/outsideCnt.do"; 
	        System.out.println("urlStr : "+urlStr);
	        hcp = new HttpClientHp(urlStr); 
	        // �Ѱ��� �Ķ���� ���� 
	        hcp.setParam("rno", new Integer(rchForm.getMdrchno()).toString()); 
	        hcp.setParam("mode", "research");
	        // setMethodType�� �������� ������ default = 0, (0:get, 1:post, 2:multipart) 
	        hcp.setMethodType(1); 
	
	        int rtnCode = hcp.execute(); // �����ϱ� 
	       System.out.println("rtnCode : "+rtnCode);
	       
	        if(rtnCode == 200){
	        	count = Integer.parseInt(hcp.getContent());
	        	  System.out.println("count : "+count);
	        	if(count == -1){
	    			msg = "��û Ŭ���̾�Ʈ IP �Ǵ� ����Ű�� ��ġ ���� �ʾ� �ܺθ�ó���� �����Ͽ����ϴ�.";
	    			
					ActionMessages messages = new ActionMessages();
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
					saveMessages(request,messages);
	        	}else if(count == -2){
	        		msg = "�ܺθ������� ���� ó���� �����Ͽ����ϴ�.";
	    			
					ActionMessages messages = new ActionMessages();
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
					saveMessages(request,messages);
	            }else if(count ==0){
	            	//����� ���պμ�����, ����μ�, ����׷� �ӽ� ���̺� ������ ���󹮼� �����ͷ� ���� 
	    			result = manager.ResearchSave(rchForm, context, saveDir);
	    			  System.out.println("getMode : "+rchForm.getMode());
	    			  
	    			if ( "temp".equals(rchForm.getMode()) ) {
	    				request.setAttribute("rchno", new Integer(0));
	    				request.setAttribute("range", rchForm.getRange());
	    				rchForm.setSessionId(rchForm.getSessionId());
	    				rchForm.setRchno(0);
	    				request.setAttribute("mdrchno", new Integer(result));
	    				rchForm.setMdrchno(result);
	    			} else if ( result > 0 ) {
	    				msg = "����Ǿ����ϴ�.";			
	    				rchForm.setRchno(result); 
	    				request.setAttribute("rchno", new Integer(result));
	    				
	    				ActionMessages messages = new ActionMessages();
	    				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
	    				saveMessages(request,messages);
	    				
	    				return mapping.findForward("list");
	    			} else if(result < 0) {
	    				msg = "�ܺθ������� ���� ó���� �����Ͽ����ϴ�.";
		    			
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
						saveMessages(request,messages);
	    			}
	            }else{
	            	if(anscount > 0){
	            		//����� ���պμ�����, ����μ�, ����׷� �ӽ� ���̺� ������ ���󹮼� �����ͷ� ���� 
		    			result = manager.ResearchSave(rchForm, context, saveDir);
		    			
		    			if ( "temp".equals(rchForm.getMode()) ) {
		    				request.setAttribute("rchno", new Integer(0));
		    				request.setAttribute("range", rchForm.getRange());
		    				rchForm.setSessionId(rchForm.getSessionId());
		    				rchForm.setRchno(0);
		    				request.setAttribute("mdrchno", new Integer(result));
		    				rchForm.setMdrchno(result);
		    			} else if ( result > 0 ) {
		    				msg = "����Ǿ����ϴ�.";
		    				rchForm.setRchno(result);
		    				request.setAttribute("rchno", new Integer(result));
		    				
		    				ActionMessages messages = new ActionMessages();
		    				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
		    				saveMessages(request,messages);
		    				
		    				return mapping.findForward("list");
		    			}
	            	}else{
	            		msg = "�������� �����Ǽ��� �־� ������� �ʾҽ��ϴ�.";	
	            		
	    				ActionMessages messages = new ActionMessages();
	    				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
	    				saveMessages(request,messages);
	            	}
	            }

	        } else {
				msg = "�ܺθ� ��ſ����� ���� ó���� �����Ͽ����ϴ�.";
    			
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
				saveMessages(request,messages);
	        }

		}else{
			//����� ���պμ�����, ����μ�, ����׷� �ӽ� ���̺� ������ ���󹮼� �����ͷ� ���� 
			result = manager.ResearchSave(rchForm, context, saveDir);
			
			if ( "temp".equals(rchForm.getMode()) ) {
				request.setAttribute("rchno", new Integer(0));
				request.setAttribute("range", rchForm.getRange());
				rchForm.setSessionId(rchForm.getSessionId());
				rchForm.setRchno(0);
				request.setAttribute("mdrchno", new Integer(result));
				rchForm.setMdrchno(result);
			} else if ( result > 0 ) {
				msg = "����Ǿ����ϴ�.";			
				rchForm.setRchno(result); 
				request.setAttribute("rchno", new Integer(result));
				
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
				saveMessages(request,messages);
				
				return mapping.findForward("list");
			}
		}

		//�̸�����ÿ� ó��..
		if("prev".equals(rchForm.getMode())){
			PrintWriter out = response.getWriter();
			out.write("<script language='javascript'>parent.showPopup('/researchPreview.do?rchno="+rchForm.getRchno()+"',700,750,0,0);</script>");
			out.close();
			return null;
		}
		
		return mapping.findForward("view");
	}	
}