package nexti.ejms.exhibit.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nexti.ejms.colldoc.form.CollprocForm;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.colldoc.model.CollprocBean;
import nexti.ejms.common.rootAction;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExhibitCloseAction extends rootAction {
	
	public ActionForward doService(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//세션정보 가져오기
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
		
		//Form에서 넘어온 값 가져오기
		CollprocForm collprocForm = (CollprocForm)form;
		
		CollprocBean Bean = new CollprocBean();
		
		Bean.setSysdocno(collprocForm.getSysdocno());
		Bean.setRadiochk(collprocForm.getRadiochk());
		Bean.setSearchkey(collprocForm.getSearchkey());
		Bean.setClosedate(collprocForm.getClosedate());
		
		//취합마감하기 상세 - 마감처리
		ColldocManager manager = ColldocManager.instance();
		manager.collprocClose(Bean, dept_code, user_id);

		return mapping.findForward("list");
	}
}