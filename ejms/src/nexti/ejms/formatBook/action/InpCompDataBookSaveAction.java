/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է¿Ϸ� �����ڷ��� �Է��ڷ� ���� action
 * ����:
 */
package nexti.ejms.formatBook.action;

import java.io.File;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.formatBook.form.DataBookForm;
import nexti.ejms.formatBook.model.DataBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.FileManager;
import nexti.ejms.util.Utils;

public class InpCompDataBookSaveAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		//�⺻ ���� ����
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
		String user_id = session.getAttribute("user_id").toString();
		String dept_code = session.getAttribute("dept_code").toString();
		
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
		DataBookForm dataBookForm = (DataBookForm)form;
		
		int sysdocno = 0;
		int formseq = 0;
		
		int chrgunitcd = 0;
		
		//����� ������ �ڵ� ��������
		UserManager usrManager = UserManager.instance();
		chrgunitcd = usrManager.getUsrChrgunitcd(user_id);
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("formseq") != null) {
			formseq = Integer.parseInt(request.getParameter("formseq"));
		}
		
		//������ ���丮 ����
		Calendar cal = Calendar.getInstance();
		String saveDir = appInfo.getBookFrmDataDir() + cal.get(Calendar.YEAR) + "/";
		
		File saveFolder = new File(context.getRealPath(saveDir));
		
		if(!saveFolder.exists()) {
			saveFolder.mkdirs();
		}
		
		//���� ���� FileBean
		FileBean fileBean = new FileBean();
		
		fileBean = FileManager.doFileUpload(dataBookForm.getInputFile(), context, saveDir);
		
		DataBookBean dataBean = new DataBookBean();
		
		//Form �����͸� Bean���� ����
		BeanUtils.copyProperties(dataBean, dataBookForm);
		dataBean.setSysdocno(sysdocno);
		dataBean.setFormseq(formseq);
		dataBean.setTgtdeptcd(dept_code);
		dataBean.setInputusrid(user_id);
		dataBean.setChrgunitcd(chrgunitcd);
		
		FormatBookManager manager = FormatBookManager.instance();
		int retCode = manager.InsertDataBookFrm(dataBean, fileBean, "update");
		
		String msg = "";
		String retpage = "";
		if(retCode > 0) {
			msg = "������ ���� �Ϸ�";
			retpage = "booksavesuccess";
		} else {
			msg = "������ ���� ����";
			retpage = "booksavefail";
		}
		
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
		saveMessages(request,messages);
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		request.setAttribute("formseq", new Integer(formseq));
		request.setAttribute("retpage", retpage);
		
		return mapping.findForward("hiddenframe");
	}
}