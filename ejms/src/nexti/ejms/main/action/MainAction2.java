/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 메인화면 action
 * 설명:
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

		//세션정보 가져오기
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("user_id");
		String deptcd = (String)session.getAttribute("dept_code");
		String isSysMgr = (String)session.getAttribute("isSysMgr");
		
		MainForm mForm = (MainForm)form;
		
		//공지사항 가져오기
		NoticeManager ntManager = NoticeManager.instance();
		SearchBean search = new SearchBean();
		search.setStartidx(1);
		search.setEndidx(5);
		List ntlist = ntManager.noticeList(search);
		mForm.setNoticeList(ntlist);
		
		//최근취합문서 가져오기
		CommCollDocSearchBean collSearch = new CommCollDocSearchBean();
		CommCollDocInfoManager manager = CommCollDocInfoManager.instance();
		collSearch.setSearchdept(appInfo.getRootid());
		collSearch.setUserid(userid);
		mForm.setRecentList(manager.getExhibitList(collSearch, 1, 5));
		
		//설문하기목록 가져오기
		ResearchManager rchManager = ResearchManager.instance();			
		List rchList = rchManager.getRchParticiList(userid, deptcd, "", "", 1, 5);
		mForm.setRchList(rchList);

		//신청목록 가져오기
		SinchungManager sManager = SinchungManager.instance();			
		List sinchungList = sManager.mainShowList(userid, appInfo.getRootid());
		mForm.setSinchungList(sinchungList);
		
		//배부대기
		String delivery_yn = Utils.nullToEmptyString(UserManager.instance().getUserInfo(userid).getDelivery_yn());
		int deliCount = 0;
		if ( "Y".equals(delivery_yn) || "001".equals(isSysMgr) ) {
			DeliveryManager deliMgr = DeliveryManager.instance();
			deliCount = deliMgr.deliTotCnt(deptcd);
		}
		request.setAttribute("deliCount", new Integer(deliCount));
		
		//입력대기 건수 가져오기
		InputingManager inMgr = InputingManager.instance();
		int inputCount = inMgr.inputingTotCnt(userid,deptcd, 1);
		request.setAttribute("inputCount", new Integer(inputCount));
		
		//취합진행
		ColldocManager collMgr = ColldocManager.instance();
		int collCount = collMgr.procCount("2", userid, deptcd);
		request.setAttribute("collCount", new Integer(collCount));
		
		//마감대기
		int endCount = collMgr.procCount("3", userid, deptcd);
		request.setAttribute("endCount", new Integer(endCount));
		
		//내설문건수 가져오기
		int rchCount = rchManager.getResearchMyTotCnt(userid, deptcd, "", "main");
		request.setAttribute("rchCount", new Integer(rchCount));
		
		//신청양식 건수가져오기
		int jupCnt = sManager.jupsuCnt(userid);          //접수중인 양식건수
		int notProcCnt = sManager.notProcCnt(userid);    //미처리된 접수건수
		request.setAttribute("jupCnt", new Integer(jupCnt));
		request.setAttribute("notProcCnt", new Integer(notProcCnt));
		
		//신청대기 건수 가져오기
		SinchungManager sMgr = SinchungManager.instance();
		nexti.ejms.sinchung.model.SearchBean sinchungsearch = new nexti.ejms.sinchung.model.SearchBean();
		sinchungsearch.setDeptid(appInfo.getRootid());		
		sinchungsearch.setStart_idx(1);
		sinchungsearch.setEnd_idx(10000);
		sinchungsearch.setUnlimited(false);
		sinchungsearch.setPresentid(userid);
		int reqCount = sMgr.doSinchungTotCnt(sinchungsearch);
		request.setAttribute("reqCount", new Integer(reqCount));
		
		//취합문서 결재현황
		int apprCol = inMgr.inputingTotCnt(userid,deptcd, 2);
		request.setAttribute("apprCol", new Integer(apprCol));
		
		int apprReq = sManager.apprProcCount(userid);
		request.setAttribute("apprReq", new Integer(apprReq));

		return mapping.findForward("main");
	}
}