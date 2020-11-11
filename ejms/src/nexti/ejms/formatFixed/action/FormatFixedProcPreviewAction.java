/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 고정양식형 미리보기용 데이터 세팅 action
 * 설명:
 */
package nexti.ejms.formatFixed.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootFrameAction;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.formatFixed.form.FormatFixedForm;
import nexti.ejms.formatFixed.model.FormatFixedBean;
import nexti.ejms.formatFixed.model.FormatFixedManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class FormatFixedProcPreviewAction extends rootFrameAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		String dept_name = (String)session.getAttribute("dept_name");
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
				dept_name = UserManager.instance().getDeptName(user_id);
			}
		}
		
		FormatFixedManager ffmgr = FormatFixedManager.instance();
		
		FormatFixedForm ffform = (FormatFixedForm)form;
		
		//1:새로작성,2:공통양식가져오기,3:사용했던양식가져오기,4:공통양식작성,5:양식수정,6:공통양식수정
		int type = ffform.getType();
		int sysdocno = -1 * ffform.getSysdocno();	//미리보기 -시스템문서번호
		int formseq = -1;	//미리보기 -1
		int oldSysdocno = ffform.getSysdocno();
		int oldFormseq = ffform.getFormseq();
		int usedsysdocno = ffform.getUsedsysdocno();
		int usedformseq = ffform.getUsedformseq();
		int commformseq = ffform.getCommformseq();
		
		int saveCheck = 0;
		if(request.getParameter("saveCheck") != null) {
			saveCheck = Integer.parseInt((String)request.getParameter("saveCheck"), 10);
		}
		
		//부서형태 가져오기 : 팀 or 과 or 동
		String depttype = dept_name.substring(dept_name.length() - 1);
		
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		
		try {
			if(type == 1 || type == 4 || type == 5 || type == 6) {
				
				String[] cellatt = request.getParameterValues("cellatt");

				FormatFixedBean htmlbean = ffmgr.FormatFixedHtmlSeparator(ffform.getFormhtml());
				
				String formhtml = htmlbean.getFormhtml();
				String formheaderhtml = htmlbean.getFormheaderhtml();
				String formbodyhtml = htmlbean.getFormbodyhtml();
				String formtailhtml = htmlbean.getFormtailhtml();
				int tblcols = htmlbean.getTblcols();
				int tblrows = htmlbean.getTblrows();
				String formtitle = ffform.getFormtitle();
				String formkind = ffform.getFormkind();
				String formcomment = ffform.getFormcomment();
	
				//양식정보 테이블에 추가
				FormatBean fbean = new FormatBean();
	
				fbean.setSysdocno(sysdocno);
				fbean.setFormseq(formseq);
				fbean.setFormtitle(formtitle);
				fbean.setFormkind(formkind);
				fbean.setFormcomment(formcomment);
				fbean.setFormhtml(formhtml);
				fbean.setFormheaderhtml(formheaderhtml);
				fbean.setFormbodyhtml(formbodyhtml);
				fbean.setFormtailhtml(formtailhtml);
				fbean.setTblcols(tblcols);
				fbean.setTblrows(tblrows);
				fbean.setCrtusrid(user_id);
				fbean.setUptusrid(user_id);
				
				//양식속성 테이블에 추가
				FormatFixedBean ffbean = new FormatFixedBean();
				
				ffbean.setSysdocno(sysdocno);
				ffbean.setFormseq(formseq);
				ffbean.setTblcols(tblcols);
				ffbean.setCellatt(cellatt);
				ffbean.setUptusrid(user_id);
	
				ffmgr.formatFixedPreview(ffbean, fbean, depttype);
				
			} else if(type == 2) {
			
				//양식정보 테이블에 추가
				FormatBean fbean = new FormatBean();

				fbean.setSysdocno(sysdocno);
				fbean.setFormseq(formseq);
				fbean.setCommformseq(commformseq);
				fbean.setFormkind("02");
				fbean.setUptusrid(user_id);
				
				ffmgr.commFormatFixedPreview(fbean, depttype);
				
			} else if(type == 3) {
				
				//양식정보 테이블에 추가
				FormatBean fbean = new FormatBean();

				fbean.setSysdocno(sysdocno);
				fbean.setFormseq(formseq);
				fbean.setUsedsysdocno(usedsysdocno);
				fbean.setUsedformseq(usedformseq);
				fbean.setFormkind("02");
				fbean.setUptusrid(user_id);
				
				ffmgr.usedFormatFixedPreview(fbean, depttype);
				
			}
			
			if(saveCheck == 1) {
				
				out.write("<script language=javascript>" +
						  "  parent.click_comp('/formatFixedSave.do')" +
						  "</script>");
				
			} else {
				
				out.write("<script language=javascript>" +
						  "  parent.click_popup('/formatFixedPreview.do" +
						  "?type=" + type + "&sysdocno=" + sysdocno + "&formseq=" + formseq +
						  "&oldSysdocno=" + oldSysdocno + "&oldFormseq=" + oldFormseq +
						  "&usedsysdocno=" + usedsysdocno + "&usedformseq=" + usedformseq + 
						  "&commformseq=" + commformseq + 
						  "', 780, 550);" +
						  "</script>");
				
			}
		} catch(Exception e) {
			ffmgr.formatFixedPreviewEnd(sysdocno, formseq);
			
			if(e.getMessage() != null && e.getMessage().split("[:]")[0].equals("1") == true) {
				out.write("<script language=javascript>" +
						  "parent.processingHide();" +
						  "alert('수식 지정이 잘못 되었습니다')" +
						  "</script>");
			} else {
				out.write("<script language=javascript>" +
						  "parent.parent.processingHide();" +
						  "alert('내부 Server에 에러가 발생한것 같습니다.\\n" +
						  "잠시후 다시 시도 하시거나 관리자에게 연락을 주십시오. 바로 해결해 드리겠습니다.\\n" +
						  "에러가 발생한 페이지는 최선을 다하여 바로 수정조치 하겠습니다. 감사합니다.')" +
						  "</script>");
			}
		}
		out.close();
		
		return null;
	}
}