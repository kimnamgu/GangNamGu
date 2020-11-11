/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��������,��û�� ���� �� ����üũ action
 * ����:
 */
package nexti.ejms.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nexti.ejms.dept.model.DeptBean;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.organ.model.OrganizeManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.DateTime;
import nexti.ejms.util.Utils;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class rootDirectAction extends Action {
	//����ȭ���� �θ� Ŭ������ ����
	private static Logger logger = Logger.getLogger(rootDirectAction.class);
	
	public ActionForward execute(
		ActionMapping mapping, 
		ActionForm form,
		HttpServletRequest request, 
		HttpServletResponse response)
	 	throws Exception {	

		HttpSession session = request.getSession();	
		ActionForward forward = null;
		
		if( ((session == null) || (session.getAttribute("user_id") == null) )
				&& request.getParameter("userid") == null){
			logger.debug("Session�� ����Ǿ����ϴ�.");
			return mapping.findForward("loginRchReqYN");
		} else {
			//���������� �Ϸ�(����)�ǰ� ���� ����� ������ ���� �Ѵ�.
			if ( session.getAttribute("user_id") == null && request.getParameter("userid") != null ) {
				String ip = Utils.nullToEmptyString(request.getRemoteAddr());
				String retUrl = Utils.nullToEmptyString(request.getRequestURI());
				String userid = Utils.nullToEmptyString(request.getParameter("userid"));
				doLogin(session, ip, retUrl, userid);
			}
			
			String[] roles = mapping.getRoleNames();
			String user_id = (String)session.getAttribute("user_id");			
			
			for(int i=0;i<roles.length;i++){
				if(roles[i].equals("admin")){
					if(!"001".equals(session.getAttribute("isSysMgr"))){
						//�ý��� �����ڰ� �ƴϸ� forwarding
						logger.info("�ý��� ������ Page�� ������ ���� ����ڰ� ���ٽõ� , �����ID["+user_id+"]");
						return mapping.findForward("global_main");
					}
				} 
			}
			
			try{
				forward=doService(mapping, form, request, response);
			}catch(Exception e){
				request.setAttribute("exception",e); 
				request.getRequestDispatcher("/common/popuperror.jsp").forward(request, response); 
			}
			return forward;
		}
	}
	
	private void doLogin(HttpSession session, String ip, String retUrl, String userid) throws Exception{
		UserManager mgrusr = UserManager.instance();
		DeptManager mgrdept = DeptManager.instance();

		UserBean user = mgrusr.getUserInfo(userid);
		
		if ( user != null ) {
			DeptBean dept = mgrdept.getDeptInfo(user.getDept_id());
			session.setAttribute("listener", new NewHttpSessionBindingListener());
			
			session.setAttribute("isSysMgr",OrganizeManager.instance().isSysMgr(userid));     	 // �ý��۰����� ���� (001)
			session.setAttribute("user_id",user.getUser_id());               // ����� ID
			session.setAttribute("user_name",user.getUser_name());           // ����� ����		
			session.setAttribute("dept_code",user.getDept_id());             // ����� �μ��ڵ�		
			session.setAttribute("dept_name",user.getDept_name());           // ����� �μ���
			
			if(Utils.isNull(dept.getDept_tel())){
				session.setAttribute("d_tel", "����");
			} else {
				session.setAttribute("d_tel", dept.getDept_tel());           // �μ���ǥ��ȭ
			}																
			
			session.setAttribute("chrg_code", user.getChrgunitcd());              // �������ڵ�
			session.setAttribute("chrg_name", user.getChrgunitnm());              // ������ ��
			session.setAttribute("c_tel",user.getTel());                     // ����� �Ϲ���ȭ��ȣ
			session.setAttribute("h_tel",user.getMobile());                  // ����� �޴���ȭ��ȣ
			session.setAttribute("logindate",DateTime.getCurrentTime());   	 // ����� �α��νð� (yyyy/MM/dd HH:mm:ss)
			session.setAttribute("loginip",ip);         					 // ����� �α���IP (127.0.0.1)
			
			if (retUrl.indexOf("research") != -1) {
				mgrusr.updateResearchLoginfo(userid, "M", 20);	//�α�����������(�α���ȭ�� ��ġ�� �����Ƿ� �⺻�� ����)
			}
		}
	}
	
	abstract public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response)
			throws Exception; 
}
