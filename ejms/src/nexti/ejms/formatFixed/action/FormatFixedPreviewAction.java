/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� ��������� �̸����� action
 * ����:
 */
package nexti.ejms.formatFixed.action;

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
import nexti.ejms.formatFixed.model.FormatFixedBean;
import nexti.ejms.formatFixed.model.FormatFixedManager;

public class FormatFixedPreviewAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		FormatManager fmgr = FormatManager.instance();
		FormatFixedManager ffmgr = FormatFixedManager.instance();
		
		//���߰����� ����������� JSP�� �����ϹǷ� ���߰����� ActionForm ���	
		FormatLineForm flform = (FormatLineForm)form;
		
		//1:�����ۼ�,2:�����İ�������,3:����ߴ���İ�������,4:�������ۼ�,5:��ļ���,6:�����ļ���
		//int type = flform.getType();
		int sysdocno = flform.getSysdocno();
		int formseq = flform.getFormseq();
		
		try {
			InputDeptSearchBoxBean idsbbean = new InputDeptSearchBoxBean();
			//��������� ��� ���� ��������
			FormatFixedBean ffbean = ffmgr.getFormatFixedDataView(sysdocno, formseq, idsbbean, "���������", true, true);
			ffbean.setSysdocno(sysdocno);
			ffbean.setFormseq(formseq);			
			ffbean.setSch_deptcd1("");
			ffbean.setSch_deptcd2("");
			ffbean.setSch_deptcd3("");
			ffbean.setSch_deptcd4("");
			ffbean.setSch_deptcd5("");
			ffbean.setSch_deptcd6("");
			ffbean.setSch_chrgunitcd("");
			ffbean.setSch_inputusrid("");
			ffbean.setTotalState(true);
			ffbean.setTotalShowStringState(true);
			ffbean.setSubtotalState(true);
			ffbean.setSubtotalShowStringState(true);
			ffbean.setIncludeNotSubmitData(true);
			ffbean.setListform(ffmgr.getFormFixedDataList(ffbean));
			
			BeanUtils.copyProperties(flform, ffbean);
			//���������ڷ� ��� ����� formbodyhtml_ext�� ��Ƽ� �ѱ�
			flform.setFormbodyhtml_ext(ffmgr.getFormatFormView(sysdocno, formseq, "�μ�").getFormheaderhtml());
			
			ffmgr.formatFixedPreviewEnd(sysdocno, formseq);	//�̸����� �ӽõ����� ����
			
			flform.setSysdocno(Integer.parseInt((String)request.getParameter("oldSysdocno"), 10));
			flform.setFormseq(Integer.parseInt((String)request.getParameter("oldFormseq"), 10));
			flform.setFormkind("02");
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

		return mapping.findForward("formatFixedPreview");
	}
}