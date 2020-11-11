/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 고정양식형 보기 action
 * 설명:
 */
package nexti.ejms.formatFixed.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.formatFixed.form.FormatFixedForm;
import nexti.ejms.formatFixed.model.FormatFixedBean;
import nexti.ejms.formatFixed.model.FormatFixedManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class DeliveryFormatFixedViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form에서 넘어온 값 가져오기 
		FormatFixedForm formatFixedForm = (FormatFixedForm)form;
		
		//세션정보 가져오기
		HttpSession session = request.getSession();

		String dept_name = session.getAttribute("dept_name").toString();
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				dept_name = UserManager.instance().getDeptName(originUserBean.getUser_id());
			}
		}
		
		int sysdocno = 0;
		int formseq = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("formseq") != null) {
			formseq = Integer.parseInt(request.getParameter("formseq"));
		}
		
		//고정양식형 양식 구조 가져오기
		FormatFixedManager manager = FormatFixedManager.instance();
		FormatFixedBean fixedBean = manager.getFormatFormView(sysdocno, formseq, dept_name);
		
		BeanUtils.copyProperties(formatFixedForm, fixedBean);
		
		return mapping.findForward("detailview");
	}
}