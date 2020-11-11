/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����������չ��� ���ո��� action
 * ����:
 */
package nexti.ejms.colldoc.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.colldoc.form.CollprocForm;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.colldoc.model.CollprocBean;
import nexti.ejms.messanger.MessangerRelay;
import nexti.ejms.messanger.MessangerRelayBean;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class CollprocCloseAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		//�������� ��������
		HttpSession session = request.getSession();
		String dept_code = "";
		String user_id = "";
		dept_code = session.getAttribute("dept_code").toString();
		user_id = session.getAttribute("user_id").toString();
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
				dept_code = originUserBean.getDept_id();
			}
		}
		
		//Form���� �Ѿ�� �� ��������
		CollprocForm collprocForm = (CollprocForm)form;
		
		CollprocBean Bean = new CollprocBean();
		
		Bean.setSysdocno(collprocForm.getSysdocno());
		Bean.setRadiochk(collprocForm.getRadiochk());
		Bean.setSearchkey(collprocForm.getSearchkey());
		Bean.setClosedate(collprocForm.getClosedate());
		
		//���ո����ϱ� �� - ����ó��
		ColldocManager manager = ColldocManager.instance();
		int result = manager.collprocClose(Bean, dept_code, user_id);
		
		if ( result > 0 ) {
			MessangerRelayBean mrBean = new MessangerRelayBean();
			mrBean.setRelayMode(MessangerRelay.COLLECT_COMP);
			mrBean.setSysdocno(Bean.getSysdocno());
			new MessangerRelay(mrBean).start();
		}

		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();		
		out.write("<script> \n");
		out.write("if ( window.dialogArguments.location.href.indexOf('/collcomp') == -1 ) { \n");
		out.write("  window.dialogArguments.location.href='/collprocList.do?docstate=0'; \n");
		out.write("} else {");
		out.write("  alert('�������ΰ� ����Ǿ����ϴ�'); \n");
		out.write("  window.dialogArguments.location.href=window.dialogArguments.location.href;");
		out.write("} \n");
		out.write("window.close(); \n");
		out.write("</script> \n");
		
		out.close();

		return null;
	}
}