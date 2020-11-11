/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ����ۼ� action
 * ����:
 */
package nexti.ejms.sinchung.action;

import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.agent.model.SystemAgentManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.sinchung.form.SinchungForm;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.sinchung.model.FrmBean;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class SinchungViewAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {			
		
		HttpSession session = request.getSession();		
				
		SinchungForm sForm = (SinchungForm)form;
		int reqformno = sForm.getReqformno();
				
		SinchungManager smgr = SinchungManager.instance();
		String sessi = session.getId().split("[!]")[0];
		
		String range = "";
		
		if(request.getAttribute("range") != null){
			range = request.getAttribute("range").toString();
		}else{
			range = sForm.getRange();
		}
		
		if(range.equals("2")){
			SystemAgentManager saManager =  SystemAgentManager.instance();
			saManager.agentControl("reqresult","002");
		}
		
		ServletContext context = getServlet().getServletContext();

		//������ ���丮 ����
		Calendar cal = Calendar.getInstance();
		String saveDir = appInfo.getRequestSampleDir() + cal.get(Calendar.YEAR) + "/";
		
		if(reqformno > 0 && "Y".equals(sForm.getViewfl())){
			//��Ͽ��� ���������� 
			smgr.deleteTempAll(sessi, context);
			smgr.copyToTemp(reqformno, sessi, context, saveDir);
		}		
		
		int examcount = sForm.getExamcount();
		
		//���������� ��������(TEMP)
		FrmBean frmbean = smgr.reqFormInfo_temp(sessi);
		BeanUtils.copyProperties(sForm, frmbean);
		
		if ( examcount < 1 ) {
			examcount = smgr.getReqSubExamcount(0, sessi);
		}
		sForm.setExamcount(examcount);
		
		//�׸�������������(TEMP)
		List articleList = smgr.reqFormSubList_temp(sessi, examcount);
		sForm.setArticleList(articleList);
		
		if(articleList != null){
			sForm.setAcnt(articleList.size());     //�׸��
		}		
		
		if(reqformno==0){
			//�ӽ����� �����϶� ó��
			String dept_code = session.getAttribute("dept_code").toString();    //�μ��ڵ�
			String dept_name = session.getAttribute("dept_name").toString();    //�μ���Ī
			String user_id = session.getAttribute("user_id").toString();	     //�����ID
			String user_name = session.getAttribute("user_name").toString();     //������̸�
			
			if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
				String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
				if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
				originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
				UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
				if ( originUserBean != null ) {
					user_id = originUserBean.getUser_id();
					user_name = originUserBean.getUser_name();
					dept_code = originUserBean.getDept_id();
					dept_name = UserManager.instance().getDeptName(user_id);
				}
			}
			
			//�����μ� ��ȭ��ȣ
			String tel = "";
			UserBean ubean = UserManager.instance().getUserInfo(user_id);
			if ( ubean != null ) {
				tel = ubean.getTel();
			}
			
			sForm.setColdeptcd(dept_code); 
			sForm.setColdeptnm(dept_name);
			sForm.setColtel(tel);
			sForm.setChrgusrid(user_id);
			sForm.setChrgusrnm(user_name);
		} else {
			if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
				String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
				if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
				originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			}
			
			//���� ��������϶� ó��
			sForm.setReqformno(reqformno);			
						
			//���� ��û �Ǽ�
			int cnt1 = smgr.reqMstCnt(reqformno, "1");    //������ ��
			int cnt2 = smgr.reqMstCnt(reqformno, "2");    //��û��
			int sumcnt = cnt1 + cnt2;
			
			request.setAttribute("cnt1", new Integer(cnt1));
			request.setAttribute("cnt2", new Integer(cnt2));
			sForm.setSumcnt(sumcnt);			
		}
		
		return mapping.findForward("view");
	}
}