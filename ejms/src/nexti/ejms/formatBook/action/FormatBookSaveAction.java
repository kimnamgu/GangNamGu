/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� �����ڷ��� ���� action
 * ����:
 */
package nexti.ejms.formatBook.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.formatBook.form.FormatBookForm;
import nexti.ejms.formatBook.model.FormatBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;


public class FormatBookSaveAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response)
	throws Exception {
		
		//�������� ��������
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
			}
		}
		
		FormatBookManager fbmgr = FormatBookManager.instance();
		FormatBookForm fbform = (FormatBookForm)form;
		
		//1:�����ۼ�,2:�����İ�������,3:����ߴ���İ�������,4:�������ۼ�,5:��ļ���,6:�����ļ���
		int type = fbform.getType();
		int sysdocno = fbform.getSysdocno();
		int formseq = fbform.getFormseq();
		
		FormatBean fbean = new FormatBean();
		
		//������� ���̺� �߰�
		fbean.setSysdocno(sysdocno);
		fbean.setFormseq(formseq);
		fbean.setFormtitle(fbform.getFormtitle());
		fbean.setFormkind(fbform.getFormkind());
		fbean.setFormcomment(fbform.getFormcomment());
		fbean.setCrtusrid(user_id);
		fbean.setUptusrid(user_id);
		fbean.setDeptcd(fbform.getDeptcd());
		
		FormatBookBean ffbean = new FormatBookBean();
		
		//��ļӼ� ���̺� �߰�
		ffbean.setSysdocno(sysdocno);
		ffbean.setFormseq(formseq);
		ffbean.setListcategorynm1(fbform.getListcategorynm1());
		
		if(type == 1) {
				
			fbmgr.addFormatBook(ffbean, fbean);
			
		} else if(type == 4) {
			
			fbmgr.addCommFormatBook(ffbean, fbean);	

		} else if(type == 5) {
				
			fbmgr.modifyFormatBook(ffbean, fbean);
			
		} else if(type == 6) {
			
			fbmgr.modifyCommFormatBook(ffbean, fbean);	

		}
		
		PrintWriter out = response.getWriter();
		out.write("<script language=javascript>" +
				  "  var opener = window.dialogArguments;" +
				  "  opener.document.forms[0].submit();" +
				  "  window.close();" +
				  "</script>");
		out.close();
		
		return null;
	}
}