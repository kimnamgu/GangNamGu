/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 행추가형 미리보기용 데이터 세팅 action
 * 설명:
 */
package nexti.ejms.formatLine.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootFrameAction;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.formatLine.form.FormatLineForm;
import nexti.ejms.formatLine.model.FormatLineBean;
import nexti.ejms.formatLine.model.FormatLineManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class FormatLineProcPreviewAction extends rootFrameAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		String dept_code = (String)session.getAttribute("dept_code");
		
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
		
		FormatLineManager flmgr = FormatLineManager.instance();
		
		FormatLineForm flform = (FormatLineForm)form;
		
		//1:새로작성,2:공통양식가져오기,3:사용했던양식가져오기,4:공통양식작성,5:양식수정,6:공통양식수정
		int type = flform.getType();
		int sysdocno = -1 * flform.getSysdocno();	//미리보기 -시스템문서번호
		int formseq = -1;	//미리보기 -1
		int oldSysdocno = flform.getSysdocno();
		int oldFormseq = flform.getFormseq();
		int usedsysdocno = flform.getUsedsysdocno();
		int usedformseq = flform.getUsedformseq();
		int commformseq = flform.getCommformseq();
		
		int saveCheck = 0;
		if(request.getParameter("saveCheck") != null) {
			saveCheck = Integer.parseInt((String)request.getParameter("saveCheck"), 10);
		}
		
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		
		try {
			if(type == 1 || type == 4 || type == 5 || type == 6) {
				
				String[] cellatt = request.getParameterValues("cellatt");

				FormatLineBean htmlbean = flmgr.FormatLineHtmlSeparator(flform.getFormhtml());
				
				String formhtml = htmlbean.getFormhtml();
				String formheaderhtml = htmlbean.getFormheaderhtml();
				String formbodyhtml = htmlbean.getFormbodyhtml();
				String formtailhtml = htmlbean.getFormtailhtml();
				int tblcols = htmlbean.getTblcols();
				int tblrows = htmlbean.getTblrows();
				String formtitle = flform.getFormtitle();
				String formkind = flform.getFormkind();
				String formcomment = flform.getFormcomment();
	
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
				fbean.setDeptcd(dept_code);
				
				//양식속성 테이블에 추가
				FormatLineBean flbean = new FormatLineBean();
				
				flbean.setSysdocno(sysdocno);
				flbean.setFormseq(formseq);
				flbean.setTblcols(tblcols);
				flbean.setCellatt(cellatt);
				flbean.setUptusrid(user_id);
	
				flmgr.formatLinePreview(flbean, fbean);
				
			} else if(type == 2) {
			
				//양식정보 테이블에 추가
				FormatBean fbean = new FormatBean();

				fbean.setSysdocno(sysdocno);
				fbean.setFormseq(formseq);
				fbean.setCommformseq(commformseq);
				fbean.setFormkind("01");
				fbean.setUptusrid(user_id);
				fbean.setDeptcd(dept_code);
				
				flmgr.commFormatLinePreview(fbean);
				
			} else if(type == 3) {
				
				//양식정보 테이블에 추가
				FormatBean fbean = new FormatBean();

				fbean.setSysdocno(sysdocno);
				fbean.setFormseq(formseq);
				fbean.setUsedsysdocno(usedsysdocno);
				fbean.setUsedformseq(usedformseq);
				fbean.setFormkind("01");
				fbean.setUptusrid(user_id);
				fbean.setDeptcd(dept_code);
				
				flmgr.usedFormatLinePreview(fbean);
				
			}
			
			if(saveCheck == 1) {
				
				out.write("<script language=javascript>" +
						  "  parent.click_comp('/formatLineSave.do')" +
						  "</script>");
				
			} else {
				
				out.write("<script language=javascript>" +
						  "  parent.click_popup('/formatLinePreview.do" +
						  "?type=" + type + "&sysdocno=" + sysdocno + "&formseq=" + formseq +
						  "&oldSysdocno=" + oldSysdocno + "&oldFormseq=" + oldFormseq +
						  "&usedsysdocno=" + usedsysdocno + "&usedformseq=" + usedformseq + 
						  "&commformseq=" + commformseq + 
						  "', 780, 550);" +
						  "</script>");
				
			}
		} catch(Exception e) {
			flmgr.formatLinePreviewEnd(sysdocno, formseq);
			
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
						  "에러가 발생한 페이지는 최선을 다하여 바로 수정조치 하겠습니다. 감사합니다.');" +
						  "</script>");
				//throw e;
			}
		}
		out.close();
		
		return null;
	}
}