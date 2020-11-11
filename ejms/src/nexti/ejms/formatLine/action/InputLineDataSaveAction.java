/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է��ϱ� ���߰��� �Է��ڷ� ���� action
 * ����:
 */
package nexti.ejms.formatLine.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.formatLine.model.FormatLineManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class InputLineDataSaveAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		//�������� ��������
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
		
		//�ý��۹�����ȣ�� ����Ϸù�ȣ ������ - submit�� Parameter�� �Ѿ��
		int sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		int formseq = Integer.parseInt(request.getParameter("formseq"));
		
		int chrgunitcd = 0;
		
		//����� ������ �ڵ� ��������
		UserManager usrManager = UserManager.instance();
		chrgunitcd = usrManager.getUsrChrgunitcd(user_id);
		
		//������ Į���� ������ - submit�� Parameter�� �Ѿ��
		int tblcols = Integer.parseInt(request.getParameter("tblcols"));
		String mode = request.getParameter("mode");
		
		String colname = "";
		String coldata = "";
		List colList = null;
		
		//���߰��� ������ ����
		FormatLineManager manager = FormatLineManager.instance();
		int retCode = 0;
		String errMsg = "";
		String[] gubun = null;
		
		colList = new ArrayList();
		
		if(mode.equals("insert")){
			for(int j = 0; j < tblcols; j++) {
				if(j < 26) {
					colname = (char)('A' + j) + "";
				} else {
					colname = (char)('A' + j - 26) + "" + (char)('A' + j - 26);
				}
				
				coldata = request.getParameter(colname);
				
				colList.add(colname + ":" + coldata);
			}
			
			try {
				retCode = manager.insertFormatLineData(sysdocno, formseq, colList, tblcols, dept_code, user_id, chrgunitcd, mode);
			} catch (Exception e) {
				gubun = e.getMessage().split(":");
				if("Infinity".equals(gubun[1])) {
					errMsg = gubun[0] + "�� : " + "0���� ���� ���� �����ϴ�.";
				} else if("NaN".equals(gubun[1])) {
					errMsg = gubun[0] + "�� : " + "�Է°��� ����ֽ��ϴ�.���İ���� ���ؼ� ���ڰ��� �Է��Ͽ��ֽʽÿ�.";
				}
			}	
			if(retCode > 0) {
				errMsg = "������ ���� �Ϸ�";
			}
		}else if(mode.equals("update")){
			int seq = Integer.parseInt(request.getParameter("seq")) - Integer.parseInt(request.getParameter("strIdx"));
			String rowseq[] = request.getParameterValues("rowseq");
			for(int j = 0; j < tblcols; j++) {
				if(j < 26) {
					colname = (char)('A' + j) + "";
				} else {
					colname = (char)('A' + j - 26) + "" + (char)('A' + j - 26);
				}
				
				String coldatas[] = request.getParameterValues(colname);
				
				colList.add(colname + ":" + coldatas[(seq)]);
			}
			
			try {
				retCode = manager.updateFormatLineData(sysdocno, formseq, colList, tblcols, dept_code, user_id, chrgunitcd, Integer.parseInt(rowseq[(seq-1)]));
			} catch (Exception e) {
				gubun = e.getMessage().split(":");
				if("Infinity".equals(gubun[1])) {
					errMsg = gubun[0] + "�� : " + "0���� ���� ���� �����ϴ�.";
				} else if("NaN".equals(gubun[1])) {
					errMsg = gubun[0] + "�� : " + "�Է°��� ����ֽ��ϴ�.���İ���� ���ؼ� ���ڰ��� �Է��Ͽ��ֽʽÿ�.";
				}
			}	
			if(retCode > 0) {
				errMsg = "������ ���� �Ϸ�";
			}
		}else{
			int seq = Integer.parseInt(request.getParameter("seq")) - Integer.parseInt(request.getParameter("strIdx"));
			String rowseq[] = request.getParameterValues("rowseq");
			
			retCode = manager.deleteFormatLineData(sysdocno, formseq, dept_code, user_id, Integer.parseInt(rowseq[(seq-1)]));
			if(retCode > 0) {
				errMsg = "������ ���� �Ϸ�";
			}
		}

		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", errMsg));
		saveMessages(request,messages);
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		request.setAttribute("formseq", new Integer(formseq));
		request.setAttribute("retpage", new String("inputformatline"));
		
		return mapping.findForward("hiddenframe");
	}
}