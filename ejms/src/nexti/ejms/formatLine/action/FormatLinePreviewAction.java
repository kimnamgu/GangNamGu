/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� ���߰��� �̸����� action
 * ����:
 */
package nexti.ejms.formatLine.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.formatLine.form.FormatLineForm;
import nexti.ejms.formatLine.model.FormatLineBean;
import nexti.ejms.formatLine.model.FormatLineManager;

public class FormatLinePreviewAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		FormatManager fmgr = FormatManager.instance();
		FormatLineManager flmgr = FormatLineManager.instance();
		
		FormatLineForm flform = (FormatLineForm)form;
		
		//1:�����ۼ�,2:�����İ�������,3:����ߴ���İ�������,4:�������ۼ�,5:��ļ���,6:�����ļ���
		//int type = flform.getType();
		int sysdocno = flform.getSysdocno();
		int formseq = flform.getFormseq();
		
		try {
			InputDeptSearchBoxBean idsbbean = new InputDeptSearchBoxBean();
			//���߰��� ��� ���� ��������
			FormatLineBean flbean = flmgr.getFormatLineDataView(sysdocno, formseq, idsbbean, true, true);
			
			//�Է¾�Ŀ��� �߰� ��ư(�÷�)����
			StringBuffer formbodyhtml = new StringBuffer(flbean.getFormbodyhtml());
			int findIndex1 = formbodyhtml.length();
			int findIndex2 = 0;
			while((findIndex2 = formbodyhtml.lastIndexOf("</tr>", findIndex1)) != -1) {
				findIndex1 = formbodyhtml.lastIndexOf("<td", findIndex2);
				int isEmpty = formbodyhtml.lastIndexOf("border:1 solid", findIndex2);
				if(isEmpty < findIndex1) {
					formbodyhtml.delete(findIndex1, findIndex2);
				}
			}
			flbean.setFormbodyhtml(formbodyhtml.toString());
			
			flbean.setListform(flmgr.getFormDataList(sysdocno, formseq, idsbbean, true, true, true));
			
			BeanUtils.copyProperties(flform, flbean);
			//���������ڷ� ��� ����� formbodyhtml_ext�� ��Ƽ� �ѱ�
			flform.setFormbodyhtml_ext(flmgr.getFormatFormView(sysdocno, formseq).getFormheaderhtml());
			
			flmgr.formatLinePreviewEnd(sysdocno, formseq);	//�̸����� �ӽõ����� ����
			
			flform.setSysdocno(Integer.parseInt((String)request.getParameter("oldSysdocno"), 10));
			flform.setFormseq(Integer.parseInt((String)request.getParameter("oldFormseq"), 10));
			flform.setFormkind("01");
			flform.setFormkindname(fmgr.getFormkindName(flform.getFormkind()));
		} catch (Exception e) {
			response.setContentType("text/html;charset=euc-kr");
			response.getWriter().write("<script>" +
									   "window.dialogArguments.parent.processingHide();" +
									   "alert('����� �ùٸ��� �ʽ��ϴ�.\\n�����ܰ�� ���ư� �ٽ� �ۼ��� �ֽñ� �ٶ��ϴ�.');" +
									   "window.close();" +
									   "</script>");
			response.getWriter().flush();
			response.flushBuffer();
			return null;
		}

		return mapping.findForward("formatLinePreview");
	}
}