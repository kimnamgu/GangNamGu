/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망 설문조사 응답 action
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

import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.util.Utils;
import nexti.ejms.outside.model.OutsideManager;

public class OutsideRchViewAction extends Action{

    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
    	//System.out.println("설문 : ActionForward ");
        try {
            String uid = Utils.nullToEmptyString((String)request.getParameter("uid"));
            String resident = Utils.nullToEmptyString((String)request.getParameter("resident"));
            String committee = Utils.nullToEmptyString((String)request.getParameter("committee"));
            String msg;

            //Form에서 넘어온 값 가져오기
            ResearchForm rchForm = (ResearchForm)form;

            int rchno = 0;

            if(request.getParameter("rchno") != null) {
                rchno = Integer.parseInt(request.getParameter("rchno"));
            }

            //설문조사 마스터  가져오기
            OutsideManager manager = OutsideManager.instance();
            ResearchBean bean = manager.getRchMst(rchno, "");
            if ( bean == null ) {
                //강남구청 설문조사 상세메시지 출력(JHkim, 14.01.06)
                if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>해당 설문이 없거나 진행중인 설문이 아닙니다.";

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

            //설문범위 체크
            String rangedetail = bean.getRangedetail();
            if ( ("22".equals(rangedetail) && "".equals(uid))
                || ("23".equals(rangedetail) && !"y".equals(resident))
                || ("24".equals(rangedetail) && !"y".equals(committee)) ) {
                //강남구청 설문조사 상세메시지 출력(JHkim, 14.01.06)
                if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    ActionMessages messages = new ActionMessages();
                    if ( "22".equals(rangedetail) && "".equals(uid) ){
                        msg = "해당 설문은 홈페이지 회원인 경우에만 참여가 가능합니다." +
                            "<br>홈페이지 로그인 후 다시 참여해 주세요.";
                    }else if ( "23".equals(rangedetail) && !"y".equals(resident) ){
                        msg = "해당 설문은 주소지가 강남구인 경우에만 참여가 가능합니다." +
                            "<br>로그인 후 MY강남 개인정보수정에서 주소지를 확인해 주세요.";
                    }else if ( "24".equals(rangedetail) && !"y".equals(committee) ){
                        msg = "해당 설문은 강남구 주민자치위원만 참여가 가능합니다.";
                    }else{
                        msg = "설문 참여에 문제가 발생하였습니다. 이용에 불편드려서 죄송합니다." +
                            "<br>문의 : 전산정보과 배순호 02-3423-5317";
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

            int grpDuplicheck = -1;
            int grpLimitcount = -1;
            if ( "y".equals(request.getParameter("grp")) ) {
                if ( request.getParameter("rchgrpno") != null ) {
                    ResearchBean grpBean = manager.getRchGrpMst(Integer.parseInt(request.getParameter("rchgrpno")), "");
                    grpDuplicheck = Integer.parseInt(grpBean.getDuplicheck());
                    grpLimitcount = grpBean.getLimitcount();
                }
            }

            int cnt = manager.rchState(rchno, uid, grpDuplicheck, grpLimitcount);

            if ( (cnt != 0 && "1".equals(manager.getRchMst(rchno, "").getDuplicheck()))
                    || grpDuplicheck == 1 ) {
                Cookie[] cookies = request.getCookies();
                if ( cookies != null ) {
                    for ( int i = 0; i < cookies.length; i++ ) {
                        Cookie c = cookies[i];
                        String name = c.getName();
                        String value = c.getValue();
                        if ( name.equals("cookie_ejms_outside_rch") && value.equals("outside_rch"+rchno) ) cnt = -2;
                    }
                }
            }

            if ( cnt == 0 ) {
                //강남구청 설문조사 상세메시지 출력(JHkim, 14.01.06)
                if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>설문조사 기간이 종료되었습니다.";

                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                    saveMessages(request,messages);

                    return mapping.findForward("hidden");
                }else{
                    response.setContentType("text/html;charset=euc-kr");
                    PrintWriter out = response.getWriter();
                    out.write("<script language=javascript>alert('설문이 종료되었습니다.');window.close();</script>");
                    out.close();
                    return null;
                }
            } else if ( cnt == -1 ){
                //강남구청 설문조사 상세메시지 출력(JHkim, 14.01.06)
                if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>설문조사 기간이 아닙니다.";

                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                    saveMessages(request,messages);

                    return mapping.findForward("hidden");
                }else{
                    response.setContentType("text/html;charset=euc-kr");
                    PrintWriter out = response.getWriter();
                    out.write("<script language=javascript>alert('지금은 설문기간이 아닙니다.');window.close();</script>");
                    out.close();
                    return null;
                }
            } else if ( cnt < 0 ) {
                //강남구청 설문조사 상세메시지 출력(JHkim, 14.01.06)
                if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>이미 참여하신 설문입니다.";
                }else{
                    //msg = "<center><br><br><br><br><br><img src=\"/images/research_comp2.gif\" alt=\"답변하신 설문조사입니다\">";
                	//msg = "<img src=\"/images/research_comp2.gif\" alt=\"답변하신 설문조사입니다\">";
                	 msg = "<br>답변하신 설문조사입니다.";
                }

                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                saveMessages(request,messages);

                return mapping.findForward("hidden");
            }

            //설문조사 문항및 보기 가져오기
            List rchSubList = manager.getRchSubList(rchno, "", "research");

            bean.setListrch(rchSubList);

            BeanUtils.copyProperties(rchForm,bean);

            rchForm.setCrtusrid(uid);
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return mapping.findForward("view");
    }
}