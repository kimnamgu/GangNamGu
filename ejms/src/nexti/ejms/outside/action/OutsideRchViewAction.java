/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ܺθ� �������� ���� action
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
    	//System.out.println("���� : ActionForward ");
        try {
            String uid = Utils.nullToEmptyString((String)request.getParameter("uid"));
            String resident = Utils.nullToEmptyString((String)request.getParameter("resident"));
            String committee = Utils.nullToEmptyString((String)request.getParameter("committee"));
            String msg;

            //Form���� �Ѿ�� �� ��������
            ResearchForm rchForm = (ResearchForm)form;

            int rchno = 0;

            if(request.getParameter("rchno") != null) {
                rchno = Integer.parseInt(request.getParameter("rchno"));
            }

            //�������� ������  ��������
            OutsideManager manager = OutsideManager.instance();
            ResearchBean bean = manager.getRchMst(rchno, "");
            if ( bean == null ) {
                //������û �������� �󼼸޽��� ���(JHkim, 14.01.06)
                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>�ش� ������ ���ų� �������� ������ �ƴմϴ�.";

                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                    saveMessages(request,messages);

                    return mapping.findForward("hidden");
                }else{
                    response.setContentType("text/html;charset=euc-kr");
                    PrintWriter out = response.getWriter();
                    out.write("<script language=javascript>alert('�������� �������簡 �����ϴ�.');window.close();</script>");
                    out.close();
                    return null;
                }
            }

            //�������� üũ
            String rangedetail = bean.getRangedetail();
            if ( ("22".equals(rangedetail) && "".equals(uid))
                || ("23".equals(rangedetail) && !"y".equals(resident))
                || ("24".equals(rangedetail) && !"y".equals(committee)) ) {
                //������û �������� �󼼸޽��� ���(JHkim, 14.01.06)
                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    ActionMessages messages = new ActionMessages();
                    if ( "22".equals(rangedetail) && "".equals(uid) ){
                        msg = "�ش� ������ Ȩ������ ȸ���� ��쿡�� ������ �����մϴ�." +
                            "<br>Ȩ������ �α��� �� �ٽ� ������ �ּ���.";
                    }else if ( "23".equals(rangedetail) && !"y".equals(resident) ){
                        msg = "�ش� ������ �ּ����� �������� ��쿡�� ������ �����մϴ�." +
                            "<br>�α��� �� MY���� ���������������� �ּ����� Ȯ���� �ּ���.";
                    }else if ( "24".equals(rangedetail) && !"y".equals(committee) ){
                        msg = "�ش� ������ ������ �ֹ���ġ������ ������ �����մϴ�.";
                    }else{
                        msg = "���� ������ ������ �߻��Ͽ����ϴ�. �̿뿡 �������� �˼��մϴ�." +
                            "<br>���� : ���������� ���ȣ 02-3423-5317";
                    }
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                    saveMessages(request,messages);

                    return mapping.findForward("hidden");
                }else{
                    response.setContentType("text/html;charset=euc-kr");
                    PrintWriter out = response.getWriter();
                    out.write("<script language=javascript>alert('���� ����� �ƴմϴ�.');window.close();</script>");
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
                //������û �������� �󼼸޽��� ���(JHkim, 14.01.06)
                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>�������� �Ⱓ�� ����Ǿ����ϴ�.";

                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                    saveMessages(request,messages);

                    return mapping.findForward("hidden");
                }else{
                    response.setContentType("text/html;charset=euc-kr");
                    PrintWriter out = response.getWriter();
                    out.write("<script language=javascript>alert('������ ����Ǿ����ϴ�.');window.close();</script>");
                    out.close();
                    return null;
                }
            } else if ( cnt == -1 ){
                //������û �������� �󼼸޽��� ���(JHkim, 14.01.06)
                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>�������� �Ⱓ�� �ƴմϴ�.";

                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                    saveMessages(request,messages);

                    return mapping.findForward("hidden");
                }else{
                    response.setContentType("text/html;charset=euc-kr");
                    PrintWriter out = response.getWriter();
                    out.write("<script language=javascript>alert('������ �����Ⱓ�� �ƴմϴ�.');window.close();</script>");
                    out.close();
                    return null;
                }
            } else if ( cnt < 0 ) {
                //������û �������� �󼼸޽��� ���(JHkim, 14.01.06)
                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>�̹� �����Ͻ� �����Դϴ�.";
                }else{
                    //msg = "<center><br><br><br><br><br><img src=\"/images/research_comp2.gif\" alt=\"�亯�Ͻ� ���������Դϴ�\">";
                	//msg = "<img src=\"/images/research_comp2.gif\" alt=\"�亯�Ͻ� ���������Դϴ�\">";
                	 msg = "<br>�亯�Ͻ� ���������Դϴ�.";
                }

                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
                saveMessages(request,messages);

                return mapping.findForward("hidden");
            }

            //�������� ���׹� ���� ��������
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