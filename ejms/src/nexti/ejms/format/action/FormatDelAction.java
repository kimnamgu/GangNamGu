/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� ��Ļ��� action
 * ����:
 */
package nexti.ejms.format.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.form.ColldocForm;
import nexti.ejms.format.model.FormatManager;

public class FormatDelAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
				
		//Form���� �Ѿ�� �� ��������
		ColldocForm cdform = (ColldocForm)form;
		
		//1:�����ۼ�,2:�����İ�������,3:����ߴ���İ�������,4:�������ۼ�,5:��ļ���,6:�����ļ���
		int type = cdform.getType();
		int sysdocno = cdform.getSysdocno();

		ServletContext context = getServlet().getServletContext();
		
		//������ ���վ�� ����
		FormatManager fmmgr = FormatManager.instance();
		
		if(type == 1) {
			int formseq = cdform.getFormseq();
			fmmgr.delFormat(sysdocno, formseq, context);
			return mapping.findForward("colldocFormView");
			
		} else if(type == 4) {

			for(int i = 0; i < cdform.getListdelete().length; i++) {
				int formseq = Integer.parseInt(cdform.getListdelete()[i], 10);
				fmmgr.delCommFormat(formseq, context);
			}
			
			PrintWriter out = response.getWriter();
			out.write("<script language=javascript>" +
					  "  parent.form_submit();" +
					  "</script>");
			out.close();
			
			return null;
			
		}
		
		return null;
	}
}