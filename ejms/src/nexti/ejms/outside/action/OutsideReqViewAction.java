/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망 신청서 작성 action
 * 설명:
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
			
			//Form 정보 가져오기
			SinchungForm sForm = (SinchungForm)form;
			int reqformno = sForm.getReqformno();
			
			OutsideManager manager = OutsideManager.instance();
			FrmBean frmbean = manager.reqFormInfo(reqformno);
			if ( frmbean == null ) {
			  //강남구청 신청서 상세메시지 출력(JHkim, 14.01.07)
                if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>해당 신청서가 없거나 접수중인 신청서가 아닙니다.";
                    
                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                    saveMessages(request,messages);
        
                    return mapping.findForward("hidden");
                }else{
    				response.setContentType("text/html;charset=euc-kr");
    				PrintWriter out = response.getWriter();
    				out.write("<script language=javascript>alert('접수중인 신청서가 없습니다.');window.close();</script>");
    				out.close();
    				return null;
                }
			}
			
			//신청범위 체크
			String rangedetail = frmbean.getRangedetail();
			if ( ("22".equals(rangedetail) && "".equals(uid))
				|| ("23".equals(rangedetail) && !"y".equals(resident))
				|| ("24".equals(rangedetail) && !"y".equals(committee)) ) {
			    //강남구청 신청서 상세메시지 출력(JHkim, 14.01.06)
                if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    ActionMessages messages = new ActionMessages();
                    if ( "22".equals(rangedetail) && "".equals(uid) ){
                        msg = "해당 신청서는 홈페이지 회원인 경우에만 참여가 가능합니다." +
                            "<br>홈페이지 로그인 후 다시 참여해 주세요.";
                    }else if ( "23".equals(rangedetail) && !"y".equals(resident) ){
                        msg = "해당 신청서는 주소지가 강남구인 경우에만 참여가 가능합니다." +
                            "<br>로그인 후 MY강남 개인정보수정에서 주소지를 확인해 주세요.";
                    }else if ( "24".equals(rangedetail) && !"y".equals(committee) ){
                        msg = "해당 신청서는 강남구 주민자치위원만 참여가 가능합니다.";
                    }else{
                        msg = "신청서 접수에 문제가 발생하였습니다. 이용에 불편드려서 죄송합니다." +
                            "<br>문의 : 전산정보과 배순호 02-3423-5317";
                    }
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                    saveMessages(request,messages);
        
                    return mapping.findForward("hidden");
                }else{
    				response.setContentType("text/html;charset=euc-kr");
    				PrintWriter out = response.getWriter();
    				out.write("<script language=javascript>alert('신청서 접수 대상이 아닙니다.');window.close();</script>");
    				out.close();
    				return null;
                }
			}
			
			//신청서 기간체크 가져오기
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
			    //강남구청 신청서 상세메시지 출력(JHkim, 14.01.06)
                if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>신청서 접수가 종료되었습니다.";
                    
                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                    saveMessages(request,messages);
        
                    return mapping.findForward("hidden");
                }else{
    		    	response.setContentType("text/html;charset=euc-kr");
    				PrintWriter out = response.getWriter();
    				out.write("<script language=javascript>alert('신청서 접수가  종료되었습니다.');window.close();</script>");
    				out.close();
    				return null;
                }
			} else if ( cnt < 0 ) {
	            //강남구청 신청서 상세메시지 출력(JHkim, 14.01.06)
                if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) == -1 ){
                    msg = "<br>이미 접수하신 신청서입니다.";
                	//msg = "<img src=\"/images/request_comp2.gif\" alt=\"접수하신 신청서입니다\">";
                }else{
                   /* msg = "<img src=\"/images/request_comp2.gif\" alt=\"접수하신 신청서입니다\">";*/
                    msg = "<br>이미 접수하신 신청서입니다.";
                }
				//System.out.println("msg : "+msg);
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
				saveMessages(request,messages);
	
				//System.out.println("OutsideReqViewAction end");
				return mapping.findForward("hidden");
			}
			
			BeanUtils.copyProperties(sForm, frmbean);		
			
			//항목(양식정보)가져오기
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