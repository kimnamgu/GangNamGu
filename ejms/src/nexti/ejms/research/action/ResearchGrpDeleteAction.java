/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��������׷� ���� action
 * ����:
 */
package nexti.ejms.research.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.ResearchManager;
import nexti.ejms.util.HttpClientHp;

public class ResearchGrpDeleteAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		int result = 0;
		String urlStr = "";
		
		HttpClientHp hcp = null;
		
		ResearchManager manager = ResearchManager.instance();
		
		//Form ��������
		ResearchForm rchForm = (ResearchForm)form;
		
		String msg = "";
		
		if("2".equals(rchForm.getRange())){
    		
	        urlStr = appInfo.getOutsideurl()+"/outsideRchGrpDel.do"; 
	        hcp = new HttpClientHp(urlStr); 
	        // �Ѱ��� �Ķ���� ���� 
	        hcp.setParam("rchgrpno", new Integer(rchForm.getMdrchgrpno()).toString()); 
	        // setMethodType�� �������� ������ default = 0, (0:get, 1:post, 2:multipart) 
	        hcp.setMethodType(1); 
	
	        int rtnCode = hcp.execute(); // �����ϱ� 
	       
	        if(rtnCode == 200){
	        	
	        	if("-1".equals(hcp.getContent())){
	        		msg = "��û Ŭ���̾�ƮIP �Ǵ� ����Ű�� ��ġ ���� �ʾ� �ܺθ�ó���� �����Ͽ����ϴ�.";
	        	}else if("-2".equals(hcp.getContent())){
	        		msg = "�ܺθ������� ���� ó���� �����Ͽ����ϴ�.";
	        	}else{ 
	        		result = manager.ResearchGrpDelete(rchForm.getMdrchgrpno());
	        		
	        		if(result > 0) {
	        			msg = "�������� �ڷᰡ  �����Ǿ����ϴ�.";
	        		} else {
	        			msg = "������ ������ �����ϴ�.";
	        		}
	        	}
	        	
	        }else{
	        	msg = "�ܺθ� ��ſ����� ���� ó���� �����Ͽ����ϴ�.";	
	        }
		}else{ 
    		result = manager.ResearchGrpDelete(rchForm.getMdrchgrpno());
    		
    		if(result > 0) {
    			msg = "�������� �ڷᰡ  �����Ǿ����ϴ�.";
    		} else {
    			msg = "������ ������ �����ϴ�.";
    		}
		}
		
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
		saveMessages(request,messages);
		
		return mapping.findForward("list");
	}	
}