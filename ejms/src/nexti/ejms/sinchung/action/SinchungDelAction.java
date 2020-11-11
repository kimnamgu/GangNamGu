/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 삭제 action
 * 설명:
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
		
		//세션정보가져오기
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
	        // 넘겨줄 파라미터 세팅 
	        hcp.setParam("reqformno", new Integer(reqformno).toString()); 
	        // setMethodType을 지정하지 않으면 default = 0, (0:get, 1:post, 2:multipart) 
	        hcp.setMethodType(1); 
	
	        int rtnCode = hcp.execute(); // 실행하기 
	       
	        if(rtnCode == 200){
	        	if("-1".equals(hcp.getContent())){
	        		msg = "요청 클라이언트IP 또는 서버키가 일치 하지 않아 외부망처리가 실패하였습니다.";	
	        	}else if("-2".equals(hcp.getContent())){
	        		msg = "외부망오류로 인해 처리가 실패하였습니다.";
	        	}else{
	        		int result = smgr.deleteAll(reqformno, user_id, context);
	    			
	    			if(result > 0) {
	    				msg = "신청서관련 자료가  삭제되었습니다.";
	    			} else {
	    				msg = "삭제된 내용이 없습니다.";
	    			}
	        	}
	        }else{
	        	msg = "외부망 통신오류로 인해 처리가 실패하였습니다.";	
	        }
		}else{
			int result = smgr.deleteAll(reqformno, user_id, context);
			
			if(result > 0) {
				msg = "신청서관련 자료가  삭제되었습니다.";
			} else {
				msg = "삭제된 내용이 없습니다.";
			}
		}

//삭제 로직 변경전...
//		int result = smgr.deleteAll(reqformno, userid);
//		
//		String msg = "";
//		
//		if(result > 0){
//			if("2".equals(sform.getRange())){
//				
//				urlStr = appInfo.getOutsideurl()+"/outsideReqDel.do"; 
//				hcp = new HttpClientHp(urlStr); 
//				// 넘겨줄 파라미터 세팅 
//				hcp.setParam("reqformno", new Integer(reqformno).toString()); 
//				// setMethodType을 지정하지 않으면 default = 0, (0:get, 1:post, 2:multipart) 
//				hcp.setMethodType(1); 
//				
//				int rtnCode = hcp.execute(); // 실행하기 
//				
//				if(rtnCode == 200){
//					if("-1".equals(hcp.getContent())){
//						msg = "요청 클라이언트IP 또는 서버키가 일치 하지 않아 외부망처리가 실패하였습니다.";	
//					}else if("-2".equals(hcp.getContent())){
//						msg = "외부망오류로 인해 처리가 실패하였습니다.";
//					}else{
//						msg = "신청서관련 자료가  삭제되었습니다.";
//					}
//				}else{
//					msg = "외부망 통신오류로 인해 처리가 실패하였습니다.";	
//				}
//			}else{
//				msg = "신청서관련 자료가  삭제되었습니다.";	
//			}
//		} else {
//			msg = "삭제된 내용이 없습니다.";
//		}
		
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
		saveMessages(request,messages);
		
		return mapping.findForward("list");
	}
}