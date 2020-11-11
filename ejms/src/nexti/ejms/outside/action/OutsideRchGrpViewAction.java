/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사그룹 미리보기 action
 * 설명:
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
			
			//Form에서 넘어온 값 가져오기
			ResearchForm rchForm = (ResearchForm)form;
			
			//세션정보 가져오기
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
			
			//설문조사 마스터  가져오기
			OutsideManager manager = OutsideManager.instance();
			ResearchBean bean = manager.getRchGrpMst(rchgrpno, sessionId);
			if ( bean == null ) {
                //강남구청 설문조사 상세메시지 출력(JHkim, 14.01.06)
                if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<li>해당 설문이 없거나 진행중인 설문이 아닙니다.</li>";
                    
                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                    saveMessages(request,messages);
        
                    return mapping.findForward("hidden");
                }else{
    				response.setContentType("text/html;charset=euc-kr");
    				PrintWriter out = response.getWriter();
    				out.write("<script language=javascript>alert('진행중인 설문조사가 없습니다.');window.close();</script>");
    				out.close();
    				return null;
                }
			}
	
			if ( "research".equals(mode) ) {
				//설문범위 체크
				String rangedetail = bean.getRangedetail();
				if ( ("22".equals(rangedetail) && "".equals(uid))
					|| ("23".equals(rangedetail) && !"y".equals(resident))
					|| ("24".equals(rangedetail) && !"y".equals(committee)) ) {
				  //강남구청 설문조사 상세메시지 출력(JHkim, 14.01.06)
	                if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
	                    ActionMessages messages = new ActionMessages();
	                    if ( "22".equals(rangedetail) && "".equals(uid) ){
	                        msg = "<li>해당 설문은 홈페이지 회원인 경우에만 참여가 가능합니다.</li>" +
	                            "<li>홈페이지 로그인 후 다시 참여해 주세요.</li>";
	                    }else if ( "23".equals(rangedetail) && !"y".equals(resident) ){
	                        msg = "<li>해당 설문은 주소지가 강남구인 경우에만 참여가 가능합니다.</li>" +
	                            "<li>로그인 후 MY강남 개인정보수정에서 주소지를 확인해 주세요.</li>";
	                    }else if ( "24".equals(rangedetail) && !"y".equals(committee) ){
	                        msg = "<li>해당 설문은 강남구 주민자치위원만 참여가 가능합니다.</li>";
	                    }else{
	                        msg = "<li>설문 참여에 문제가 발생하였습니다. 이용에 불편드려서 죄송합니다.</li>" +
	                            "<li>문의 : 전산정보과 배순호 02-3423-5317</li>";
	                    }
	                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
	                    saveMessages(request,messages);
	        
	                    return mapping.findForward("hidden");
	                }else{
    					response.setContentType("text/html;charset=euc-kr");
    					PrintWriter out = response.getWriter();
    					out.write("<script language=javascript>alert('설문 대상이 아닙니다.');window.close();</script>");
    					out.close();
    					return null;
	                }
				}
				
				int cnt = manager.rchGrpState(rchgrpno);
				
				if ( cnt == 0 ) {
	                //강남구청 설문조사 상세메시지 출력(JHkim, 14.01.06)
	                if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
	                    msg = "<li>설문조사가 종료되었거나 설문대상이 아닙니다.</li>";
	                    
	                    ActionMessages messages = new ActionMessages();
	                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
	                    saveMessages(request,messages);
	        
	                    return mapping.findForward("hidden");
	                }else{
    			    	response.setContentType("text/html;charset=euc-kr");
    					PrintWriter out = response.getWriter();
    					out.write("<script language=javascript>alert('설문조사가 종료되었거나 설문대상이 아닙니다.');window.close();</script>");
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