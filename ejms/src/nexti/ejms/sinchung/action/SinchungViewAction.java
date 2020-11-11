/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 양식작성 action
 * 설명:
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

		//저장할 디렉토리 지정
		Calendar cal = Calendar.getInstance();
		String saveDir = appInfo.getRequestSampleDir() + cal.get(Calendar.YEAR) + "/";
		
		if(reqformno > 0 && "Y".equals(sForm.getViewfl())){
			//목록에서 진입했을때 
			smgr.deleteTempAll(sessi, context);
			smgr.copyToTemp(reqformno, sessi, context, saveDir);
		}		
		
		int examcount = sForm.getExamcount();
		
		//마스터정보 가져오기(TEMP)
		FrmBean frmbean = smgr.reqFormInfo_temp(sessi);
		BeanUtils.copyProperties(sForm, frmbean);
		
		if ( examcount < 1 ) {
			examcount = smgr.getReqSubExamcount(0, sessi);
		}
		sForm.setExamcount(examcount);
		
		//항목정보가져오기(TEMP)
		List articleList = smgr.reqFormSubList_temp(sessi, examcount);
		sForm.setArticleList(articleList);
		
		if(articleList != null){
			sForm.setAcnt(articleList.size());     //항목수
		}		
		
		if(reqformno==0){
			//임시저장 상태일때 처리
			String dept_code = session.getAttribute("dept_code").toString();    //부서코드
			String dept_name = session.getAttribute("dept_name").toString();    //부서명칭
			String user_id = session.getAttribute("user_id").toString();	     //사용자ID
			String user_name = session.getAttribute("user_name").toString();     //사용자이름
			
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
			
			//접수부서 전화번호
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
			
			//정식 저장상태일때 처리
			sForm.setReqformno(reqformno);			
						
			//현재 신청 건수
			int cnt1 = smgr.reqMstCnt(reqformno, "1");    //결재대기 건
			int cnt2 = smgr.reqMstCnt(reqformno, "2");    //신청건
			int sumcnt = cnt1 + cnt2;
			
			request.setAttribute("cnt1", new Integer(cnt1));
			request.setAttribute("cnt2", new Integer(cnt2));
			sForm.setSumcnt(sumcnt);			
		}
		
		return mapping.findForward("view");
	}
}