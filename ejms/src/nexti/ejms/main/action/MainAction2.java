/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ȭ�� action
 * ����:
 */
package nexti.ejms.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.common.form.MainForm;
import nexti.ejms.commdocinfo.model.CommCollDocSearchBean;
import nexti.ejms.commdocinfo.model.CommCollDocInfoManager;
import nexti.ejms.inputing.model.InputingManager;
import nexti.ejms.notice.model.NoticeManager;
import nexti.ejms.notice.model.SearchBean;
import nexti.ejms.delivery.model.DeliveryManager;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.research.model.ResearchManager;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class MainAction2 extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {

		//�������� ��������
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("user_id");
		String deptcd = (String)session.getAttribute("dept_code");
		String isSysMgr = (String)session.getAttribute("isSysMgr");
		
		MainForm mForm = (MainForm)form;
		
		//�������� ��������
		NoticeManager ntManager = NoticeManager.instance();
		SearchBean search = new SearchBean();
		search.setStartidx(1);
		search.setEndidx(5);
		List ntlist = ntManager.noticeList(search);
		mForm.setNoticeList(ntlist);
		
		//�ֱ����չ��� ��������
		CommCollDocSearchBean collSearch = new CommCollDocSearchBean();
		CommCollDocInfoManager manager = CommCollDocInfoManager.instance();
		collSearch.setSearchdept(appInfo.getRootid());
		collSearch.setUserid(userid);
		mForm.setRecentList(manager.getExhibitList(collSearch, 1, 5));
		
		//�����ϱ��� ��������
		ResearchManager rchManager = ResearchManager.instance();			
		List rchList = rchManager.getRchParticiList(userid, deptcd, "", "", 1, 5);
		mForm.setRchList(rchList);

		//��û��� ��������
		SinchungManager sManager = SinchungManager.instance();			
		List sinchungList = sManager.mainShowList(userid, appInfo.getRootid());
		mForm.setSinchungList(sinchungList);
		
		//��δ��
		String delivery_yn = Utils.nullToEmptyString(UserManager.instance().getUserInfo(userid).getDelivery_yn());
		int deliCount = 0;
		if ( "Y".equals(delivery_yn) || "001".equals(isSysMgr) ) {
			DeliveryManager deliMgr = DeliveryManager.instance();
			deliCount = deliMgr.deliTotCnt(deptcd);
		}
		request.setAttribute("deliCount", new Integer(deliCount));
		
		//�Է´�� �Ǽ� ��������
		InputingManager inMgr = InputingManager.instance();
		int inputCount = inMgr.inputingTotCnt(userid,deptcd, 1);
		request.setAttribute("inputCount", new Integer(inputCount));
		
		//��������
		ColldocManager collMgr = ColldocManager.instance();
		int collCount = collMgr.procCount("2", userid, deptcd);
		request.setAttribute("collCount", new Integer(collCount));
		
		//�������
		int endCount = collMgr.procCount("3", userid, deptcd);
		request.setAttribute("endCount", new Integer(endCount));
		
		//�������Ǽ� ��������
		int rchCount = rchManager.getResearchMyTotCnt(userid, deptcd, "", "main");
		request.setAttribute("rchCount", new Integer(rchCount));
		
		//��û��� �Ǽ���������
		int jupCnt = sManager.jupsuCnt(userid);          //�������� ��İǼ�
		int notProcCnt = sManager.notProcCnt(userid);    //��ó���� �����Ǽ�
		request.setAttribute("jupCnt", new Integer(jupCnt));
		request.setAttribute("notProcCnt", new Integer(notProcCnt));
		
		//��û��� �Ǽ� ��������
		SinchungManager sMgr = SinchungManager.instance();
		nexti.ejms.sinchung.model.SearchBean sinchungsearch = new nexti.ejms.sinchung.model.SearchBean();
		sinchungsearch.setDeptid(appInfo.getRootid());		
		sinchungsearch.setStart_idx(1);
		sinchungsearch.setEnd_idx(10000);
		sinchungsearch.setUnlimited(false);
		sinchungsearch.setPresentid(userid);
		int reqCount = sMgr.doSinchungTotCnt(sinchungsearch);
		request.setAttribute("reqCount", new Integer(reqCount));
		
		//���չ��� ������Ȳ
		int apprCol = inMgr.inputingTotCnt(userid,deptcd, 2);
		request.setAttribute("apprCol", new Integer(apprCol));
		
		int apprReq = sManager.apprProcCount(userid);
		request.setAttribute("apprReq", new Integer(apprReq));

		return mapping.findForward("main");
	}
}