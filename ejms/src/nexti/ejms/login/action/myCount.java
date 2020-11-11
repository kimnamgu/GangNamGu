package nexti.ejms.login.action;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.delivery.model.DeliveryManager;
import nexti.ejms.inputing.model.InputingManager;
import nexti.ejms.research.model.ResearchManager;
import nexti.ejms.sinchung.model.SearchBean;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class myCount extends Action
{
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      HttpSession session = request.getSession();
      String userid = (String)session.getAttribute("user_id");
      String deptcd = (String)session.getAttribute("dept_code");
      String isSysMgr = (String)session.getAttribute("isSysMgr");
      String type = (String)session.getAttribute("type");

      session.removeAttribute("sso");
      session.removeAttribute("act");
      session.removeAttribute("type");

      ColldocManager collMgr = ColldocManager.instance();
      int collprocCount = collMgr.procCount("2", userid, deptcd);
      request.setAttribute("collprocCount", new Integer(collprocCount));

      String delivery_yn = "";
      UserBean ubean = UserManager.instance().getUserInfo(userid);
      if (ubean != null) {
        delivery_yn = Utils.nullToEmptyString(ubean.getDelivery_yn());
      }
      int deliCount = 0;
      if (("Y".equals(delivery_yn)) || ("001".equals(isSysMgr))) {
        DeliveryManager deliMgr = DeliveryManager.instance();
        deliCount = deliMgr.deliTotCnt(deptcd);
      }
      request.setAttribute("deliCount", new Integer(deliCount));

      InputingManager inMgr = InputingManager.instance();
      int inputCount = inMgr.inputingTotCnt(userid, deptcd, 1);
      request.setAttribute("inputCount", new Integer(inputCount));

      ResearchManager rMgr = ResearchManager.instance();
      String getUserGradeId = rMgr.getUserGradeId(userid);
      String gradeName = rMgr.getGradeName(userid);
	  int rchCount = rMgr.getRchParticiTotCnt(userid, deptcd,getUserGradeId, gradeName, "", "");
      request.setAttribute("rchCount", new Integer(rchCount));

      SinchungManager sMgr = SinchungManager.instance();
      SearchBean search = new SearchBean();
      search.setDeptid(appInfo.getRootid());
      search.setStart_idx(1);
      search.setEnd_idx(10000);
      search.setUnlimited(false);
      search.setPresentid(userid);
      int reqCount = sMgr.doSinchungTotCnt(search);
      request.setAttribute("reqCount", new Integer(reqCount));

      int reqProcWaitCount = sMgr.getStateTotalCount(userid, deptcd, "01");
      reqProcWaitCount += sMgr.getStateTotalCount(userid, deptcd, "02");
      request.setAttribute("reqProcWaitCount", new Integer(reqProcWaitCount));

      if (("동작3190000".indexOf(appInfo.getRootid()) != -1) && 
        ("001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))))) {
        reqProcWaitCount = sMgr.getStateTotalCount("%", "%", "01");
        reqProcWaitCount += sMgr.getStateTotalCount("%", "%", "02");
        request.setAttribute("reqProcWaitCount", new Integer(reqProcWaitCount));
      }

      if (("".equals(Utils.nullToEmptyString(userid))) || ("".equals(Utils.nullToEmptyString(deptcd)))) {
        collprocCount = deliCount = inputCount = rchCount = reqCount = 0;
      }

      if ("부산6260000".indexOf(appInfo.getRootid()) != -1) {
        return new ActionForward("/client/jsp/ejms_portlet.jsp");
      }

      if ("COUNT".equals(type)) {
        StringBuffer resultXML = new StringBuffer();
        resultXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        resultXML.append("<result>\n");
        resultXML.append("\t<uid>" + userid + "</uid>\n");
        resultXML.append("\t<collprocCount>" + collprocCount + "</collprocCount>\n");
        resultXML.append("\t<deliCount>" + deliCount + "</deliCount>\n");
        resultXML.append("\t<inputCount>" + inputCount + "</inputCount>\n");
        resultXML.append("\t<rchCount>" + rchCount + "</rchCount>\n");
        resultXML.append("\t<reqCount>" + reqCount + "</reqCount>\n");
        resultXML.append("\t<reqProcWaitCount>" + reqProcWaitCount + "</reqProcWaitCount>\n");
        resultXML.append("</result>\n");
        response.setContentType("text/xml;charset=UTF-8");
        response.getWriter().print(resultXML);
        return null;
      }
      return mapping.findForward("mycount");
    } catch (Exception e) {
    }
    return null;
  }
}