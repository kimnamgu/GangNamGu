/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사그룹 삭제 action
 * 설명:
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
		
		//Form 가져오기
		ResearchForm rchForm = (ResearchForm)form;
		
		String msg = "";
		
		if("2".equals(rchForm.getRange())){
    		
	        urlStr = appInfo.getOutsideurl()+"/outsideRchGrpDel.do"; 
	        hcp = new HttpClientHp(urlStr); 
	        // 넘겨줄 파라미터 세팅 
	        hcp.setParam("rchgrpno", new Integer(rchForm.getMdrchgrpno()).toString()); 
	        // setMethodType을 지정하지 않으면 default = 0, (0:get, 1:post, 2:multipart) 
	        hcp.setMethodType(1); 
	
	        int rtnCode = hcp.execute(); // 실행하기 
	       
	        if(rtnCode == 200){
	        	
	        	if("-1".equals(hcp.getContent())){
	        		msg = "요청 클라이언트IP 또는 서버키가 일치 하지 않아 외부망처리가 실패하였습니다.";
	        	}else if("-2".equals(hcp.getContent())){
	        		msg = "외부망오류로 인해 처리가 실패하였습니다.";
	        	}else{ 
	        		result = manager.ResearchGrpDelete(rchForm.getMdrchgrpno());
	        		
	        		if(result > 0) {
	        			msg = "설문관련 자료가  삭제되었습니다.";
	        		} else {
	        			msg = "삭제된 내용이 없습니다.";
	        		}
	        	}
	        	
	        }else{
	        	msg = "외부망 통신오류로 인해 처리가 실패하였습니다.";	
	        }
		}else{ 
    		result = manager.ResearchGrpDelete(rchForm.getMdrchgrpno());
    		
    		if(result > 0) {
    			msg = "설문관련 자료가  삭제되었습니다.";
    		} else {
    			msg = "삭제된 내용이 없습니다.";
    		}
		}
		
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
		saveMessages(request,messages);
		
		return mapping.findForward("list");
	}	
}