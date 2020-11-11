/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ���� action
 * ����:
 */
package nexti.ejms.sinchung.action;

import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.sinchung.form.SinchungForm;
import nexti.ejms.sinchung.model.SinchungDAO;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.HttpClientHp;
import nexti.ejms.util.Utils;

public class SinchungSaveAction extends rootAction {
	
	private static Logger logger = Logger.getLogger(SinchungSaveAction.class);
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
		//logger.info("<======== ���� ========>");
		//����������������
		HttpSession session = request.getSession();
		String sessi = session.getId().split("[!]")[0];
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
		
		int count = 0;
		String msg = "";
		String urlStr = "";
		HttpClientHp hcp = null;
		//logger.info("form.toString("+form.toString()+")");
		SinchungForm sForm = (SinchungForm)form;
		SinchungManager smgr = SinchungManager.instance();
		sForm.setSessi(sessi);
		sForm.setCrtusrid(user_id);
		sForm.setUptusrid(user_id);
		logger.info("���� : "+sForm.getTitle());
		//logger.info("���� : "+sForm.getSummary());
		//�����μ� ��ȭ��ȣ
		String tel = "";
		UserBean ubean = UserManager.instance().getUserInfo(sForm.getChrgusrid());
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		sForm.setColtel(tel);
		
		ServletContext context = getServlet().getServletContext();

		//������ ���丮 ����
		Calendar cal = Calendar.getInstance();
		String saveDir = appInfo.getRequestSampleDir() + cal.get(Calendar.YEAR) + "/";
		//saveDir = "D:/gangnamgu/workspace/gangnamgu/WebContent"+saveDir;
		//logger.info("saveDir : "+saveDir); 
		///logger.info("getRange : "+sForm.getRange()); 
		if("2".equals(sForm.getRange())){

	        urlStr = appInfo.getOutsideurl()+"/outsideCnt.do"; 
	        hcp = new HttpClientHp(urlStr); 
	        // �Ѱ��� �Ķ���� ���� 
	        hcp.setParam("rno", new Integer(sForm.getReqformno()).toString()); 
	        hcp.setParam("mode", "request");
	        // setMethodType�� �������� ������ default = 0, (0:get, 1:post, 2:multipart) 
	        hcp.setMethodType(1); 
	
	        int rtnCode = hcp.execute(); // �����ϱ� 
	       
	        if(rtnCode == 200){
	        	count = Integer.parseInt(hcp.getContent());
	        	
	        	int cnt1 = smgr.reqMstCnt(sForm.getReqformno(), "1");    //������ ��
	    		int cnt2 = smgr.reqMstCnt(sForm.getReqformno(), "2");    //��û��
	    		int sumcnt = cnt1 + cnt2;
	    		
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
	            }else if(count == 0){
	    			int result = smgr.makeReqForm(sForm, context, saveDir);		
	    		
	    			//result = -1 :����, 0: ���� , 1 �̻� :reqformno��
	    			if ( "temp".equals(sForm.getMode()) ) {
	    				sForm.setReqformno(result);
	    				sForm.setViewfl(sForm.getViewfl());
	    			} else if ( result > 0 ) {
	    				//�Ϸ� ��ư�� �����Ͽ������� ����ȴ�.	
	    				msg = "����Ǿ����ϴ�.";
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
	    		} else{
	    			if(sumcnt >0){
	    				int result = smgr.makeReqForm(sForm, context, saveDir);		
	    				
	    				//result = -1 :����, 0: ���� , 1 �̻� :reqformno��
	    				if ( "temp".equals(sForm.getMode()) ) {
	    					sForm.setReqformno(result);
		    				sForm.setViewfl(sForm.getViewfl());
		    			} else if ( result > 0 ) {
	    					//�Ϸ� ��ư�� �����Ͽ������� ����ȴ�.	
	    					msg = "����Ǿ����ϴ�.";
	    					
	    					ActionMessages messages = new ActionMessages();
	    					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
	    					saveMessages(request,messages);
	    				
	    					return mapping.findForward("list");
	    				}
	    			}else{
	    				msg = "��û�� ��û�Ǽ��� �־� ������� �ʾҽ��ϴ�.";	
	    				
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
		} else {
			
			int result = smgr.makeReqForm(sForm, context, saveDir);		
			
			//result = -1 :����, 0: ���� , 1 �̻� :reqformno��
			if ( "temp".equals(sForm.getMode()) ) {
				sForm.setReqformno(result);
				sForm.setViewfl(sForm.getViewfl());
			} else if ( result > 0 ) {				
				//�Ϸ� ��ư�� �����Ͽ������� ����ȴ�.	
				msg = "����Ǿ����ϴ�.";
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
				saveMessages(request,messages);
			
				return mapping.findForward("list");
			}
		}
		
		//�̸�����ÿ� ó��..
		if("prev".equals(sForm.getMode())){
			PrintWriter out = response.getWriter();
			out.write("<script language='javascript'>parent.showPopup('/preview.do?reqformno="+sForm.getReqformno()+"',705,650,0,0);</script>");
			out.close();
			return null;
		}
				
		return mapping.findForward("view");
	}
}