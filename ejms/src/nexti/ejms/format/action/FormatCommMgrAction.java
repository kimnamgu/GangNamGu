/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통양식관리 action
 * 설명:
 */
package nexti.ejms.format.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;

public class FormatCommMgrAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		return mapping.findForward("formatCommMgr");
	}
}