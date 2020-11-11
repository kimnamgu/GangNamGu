/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 신청내역 작성 action
 * 설명:
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
			
		//세션값 가져오기
		HttpSession session = request.getSession();	
		String userid = session.getAttribute("user_id").toString();
		
		//Form 정보 가져오기
		SinchungForm sForm = (SinchungForm)form;		
		int reqformno = sForm.getReqformno();		
		
		SinchungManager smgr = SinchungManager.instance();
		
		//신청서 기간체크 가져오기
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
			out.write("<script language=javascript>alert('신청서 접수가  종료되었습니다.');window.close();</script>");
			out.close();
			return null;
		} else if ( cnt < 0 ) {	
			String msg = "<img src=\"/images/request_comp2.gif\" alt=\"접수하신 설문조사입니다\">";
			
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
			saveMessages(request,messages);

			return mapping.findForward("hidden");
		}
		
		//마스터(양식정보) 가져오기
		FrmBean frmbean = smgr.reqFormInfo(reqformno);
		BeanUtils.copyProperties(sForm, frmbean);		
		
		//접수부서 전화번호
		String tel = frmbean.getColtel();
		UserBean ubean = UserManager.instance().getUserInfo(sForm.getChrgusrid());
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		sForm.setColtel(tel);
		
		if ( "수원3740000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
            sForm.setColtel(sForm.getColtel().substring(4));
        }
		
		UserManager umgr = UserManager.instance();
		
		//항목(양식정보)가져오기
		List articleList = smgr.reqFormSubList(reqformno);
		sForm.setArticleList(articleList);	
		
		//기본데이터 가져오기
		ReqMstBean rbean = new ReqMstBean();	
	
		if ( umgr != null ) {
			rbean.setPresentnm(umgr.getUserInfo(userid).getUser_name());	//사용자 성명
			rbean.setPresentid(userid);										//사용자 ID
			rbean.setPresentsn(umgr.getUserInfo(userid).getUser_sn());		//주민등록번호
			rbean.setPosition(UserManager.instance().getDeptName(userid));	//소속
			rbean.setDuty(umgr.getUserInfo(userid).getClass_name());		//직급
			rbean.setEmail(umgr.getUserInfo(userid).getEmail());			//email
			rbean.setTel(umgr.getUserInfo(userid).getTel());				//전화번호
			rbean.setCel(umgr.getUserInfo(userid).getMobile());				//휴대전화번호
			rbean.setFax(umgr.getUserInfo(userid).getDept_fax());			//팩스번호
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