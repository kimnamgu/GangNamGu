/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����İ��� action
 * ����:
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