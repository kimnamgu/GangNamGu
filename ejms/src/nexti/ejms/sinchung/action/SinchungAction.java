/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ��û���� �ۼ� action
 * ����:
 */
package nexti.ejms.sinchung.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootDirectAction;
import nexti.ejms.sinchung.form.SinchungForm;
import nexti.ejms.sinchung.model.FrmBean;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.sinchung.model.SearchBean;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;

public class SinchungAction extends rootDirectAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
			
		//���ǰ� ��������
		HttpSession session = request.getSession();	
		String userid = session.getAttribute("user_id").toString();
		
		//Form ���� ��������
		SinchungForm sForm = (SinchungForm)form;		
		int reqformno = sForm.getReqformno();		
		
		SinchungManager smgr = SinchungManager.instance();
		
		//��û�� �Ⱓüũ ��������
		int cnt = smgr.reqState(reqformno, userid);
		
		if ( cnt != 0 && "1".equals(smgr.reqFormInfo(reqformno).getDuplicheck()) ) {
			Cookie[] cookies = request.getCookies();
			if ( cookies != null ) {
				for ( int i = 0; i < cookies.length; i++ ) {
					Cookie c = cookies[i];
					String name = c.getName();
					String value = c.getValue();
					if ( name.equals("cookie_ejms_req") && value.equals("req"+reqformno) ) cnt = -1;
				}		
			}
		}
		
		if ( cnt == 0 ) {
	    	response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.write("<script language=javascript>alert('��û�� ������  ����Ǿ����ϴ�.');window.close();</script>");
			out.close();
			return null;
		} else if ( cnt < 0 ) {	
			String msg = "<img src=\"/images/request_comp2.gif\" alt=\"�����Ͻ� ���������Դϴ�\">";
			
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
			saveMessages(request,messages);

			return mapping.findForward("hidden");
		}
		
		//������(�������) ��������
		FrmBean frmbean = smgr.reqFormInfo(reqformno);
		BeanUtils.copyProperties(sForm, frmbean);		
		
		//�����μ� ��ȭ��ȣ
		String tel = frmbean.getColtel();
		UserBean ubean = UserManager.instance().getUserInfo(sForm.getChrgusrid());
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		sForm.setColtel(tel);
		
		if ( "����3740000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
            sForm.setColtel(sForm.getColtel().substring(4));
        }
		
		UserManager umgr = UserManager.instance();
		
		//�׸�(�������)��������
		List articleList = smgr.reqFormSubList(reqformno);
		sForm.setArticleList(articleList);	
		
		//�⺻������ ��������
		ReqMstBean rbean = new ReqMstBean();	
	
		if ( umgr != null ) {
			rbean.setPresentnm(umgr.getUserInfo(userid).getUser_name());	//����� ����
			rbean.setPresentid(userid);										//����� ID
			rbean.setPresentsn(umgr.getUserInfo(userid).getUser_sn());		//�ֹε�Ϲ�ȣ
			rbean.setPosition(UserManager.instance().getDeptName(userid));	//�Ҽ�
			rbean.setDuty(umgr.getUserInfo(userid).getClass_name());		//����
			rbean.setEmail(umgr.getUserInfo(userid).getEmail());			//email
			rbean.setTel(umgr.getUserInfo(userid).getTel());				//��ȭ��ȣ
			rbean.setCel(umgr.getUserInfo(userid).getMobile());				//�޴���ȭ��ȣ
			rbean.setFax(umgr.getUserInfo(userid).getDept_fax());			//�ѽ���ȣ
			rbean.setReqformno(reqformno);
		}
		
		sForm.setRbean(rbean);
		
		SearchBean search = new SearchBean();
		search.setReqformno(reqformno);
		search.setStrdt("0001-01-01");
		search.setEnddt("9999-12-31");
		search.setProcFL("99");
		request.setAttribute("totalCount",new Integer(smgr.reqDataTotCnt(search) + 1));
		
		return mapping.findForward("write");
	}
}