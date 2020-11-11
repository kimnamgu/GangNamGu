/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서작성 문서저장 action
 * 설명:
 */
package nexti.ejms.colldoc.action;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.form.ColldocForm;
import nexti.ejms.colldoc.model.ColldocBean;
import nexti.ejms.colldoc.model.ColldocFileBean;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.formatBook.model.DataBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class ColldocSaveAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//사용자 ID
		String user_name = (String)session.getAttribute("user_name");	//사용자 성명;
		String dept_code = (String)session.getAttribute("dept_code");	//사용자 부서코드;
		String dept_name = (String)session.getAttribute("dept_name");	//사용자 부서명;
		String sessi = session.getId().split("[!]")[0];
		
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
		
		String formapprep = (String)request.getParameter("formapprep");
		
		//Form 가져오기
		ColldocForm cdform = (ColldocForm)form;
		
		ColldocManager cdmgr = ColldocManager.instance();
		DeptManager dmgr = DeptManager.instance();
		
		ColldocBean cdbean = null;
		
		int sysdocno = cdform.getSysdocno();
		int chrg_code = 0;
		String chrg_name = null;
		
		if(formapprep == null) {
			chrg_code = cdform.getChrgunitcd();
			chrg_name = dmgr.getChrgunitnm(dept_code, chrg_code);
			
			String enddate = cdform.getEnddt_date();
			if(enddate.equals("") == false) {
				enddate = enddate + " " +
						  cdform.getEnddt_hour() + ":" +
						  cdform.getEnddt_min() + ":00";
			}
			
			cdbean = new ColldocBean();
			
			cdbean.setDoctitle(cdform.getDoctitle());		//문서제목
			cdbean.setBasicdate(cdform.getBasicdate());		//자료기준일
			cdbean.setColdeptcd(dept_code);					//취합부서코드
			cdbean.setColdeptnm(dept_name);					//취합부서명
			cdbean.setChrgunitcd(chrg_code);				//취합담당단위코드
			cdbean.setChrgunitnm(chrg_name);				//취합담당단위명
			cdbean.setChrgusrcd(user_id);					//취합담당자코드
			cdbean.setChrgusrnm(user_name);					//취합담당자명
			cdbean.setBasis(cdform.getBasis());				//관련근거
			cdbean.setSummary(cdform.getSummary());			//취합개요
			cdbean.setEnddt(enddate);						//마감일시
			cdbean.setEndcomment(cdform.getEndcomment());	//마감알림말
			cdbean.setTgtdeptnm(cdform.getTgtdeptnm());		//제출부서명,그룹명
			cdbean.setSancrule(cdform.getSancrule());		//제출자료전결
			
			cdbean.setSysdocno(sysdocno);					//시스템문서번호(hidden)
			cdbean.setDocgbn("01");							//문서구분
			cdbean.setDocstate("01");						//문서상태
			cdbean.setDelyn("0");							//목록에서삭제여부
			cdbean.setUptusrid(user_id);					//작성자ID
			
			if ( !"Y".equals(Utils.nullToEmptyString(cdform.getOpeninput())) ) cdform.setOpeninput("N");
			cdbean.setOpeninput(cdform.getOpeninput());		//공개입력
		} else if(formapprep.equals("1") == true) {
			cdbean = cdmgr.getColldoc(sysdocno);
			
			chrg_code = cdbean.getChrgunitcd();
			chrg_name = dmgr.getChrgunitnm(dept_code, chrg_code);
		}
		
		ServletContext context = getServlet().getServletContext();

		//저장할 디렉토리 지정
		Calendar cal = Calendar.getInstance();
		String saveDir = appInfo.getColldocSampleDir() + cal.get(Calendar.YEAR) + "/";
		
		cdbean.setAttachFile(cdform.getAttachFile());
		cdbean.setAttachFileYN(cdform.getAttachFileYN());
		
		String formatfileDir = context.getRealPath("");
		
		//1:새로저장, 2:새문서로 저장, 3:저장, 4:저장하고사본생성, 5:임시저장, 6:저장
		//mode=5는 새로작성 중인 문서를 임시저장(목록에서 안보임)하기 위해 사용
		//mode=6은 수정 중인 문서를 새문서로저장 가능 하도록 하기 위해 사용(결재용)
		int mode = cdform.getMode();

		if(mode == 2 || mode == 4) {
			//취합문서 파일 유무 체크
			List existFile = cdmgr.getExistColldocFile(formatfileDir, sysdocno);
			if(existFile != null && cdform.getAttachFile().getFileName().equals("") == true && cdform.getAttachFileYN().equals("N") == false ) {
				StringBuffer msg = new StringBuffer();
				msg.append("취합문서에 첨부된 파일이 없습니다.\\n첨부파일을 수정하시거나 관리자에게 문의바랍니다.\\n");
					
				for(int i = 0; i < existFile.size(); i++) {
					ColldocFileBean dbbean = (ColldocFileBean)existFile.get(i);
					msg.append("\\n" + " ⇒ " + dbbean.getOriginfilenm());
				}
				
				out.write("<script language=javascript>location='/colldocInfoView.do?mode=" + 3 + "&sysdocno=" + sysdocno + "';alert(\"" + msg + "\");</script>");
				out.close();

				return null;
			}
			
			//제본자료형 양식파일 유무 체크
			FormatBookManager fbmgr = FormatBookManager.instance();
			existFile = fbmgr.getExistBookFile(formatfileDir, sysdocno, 0);
			if(existFile != null) {
				StringBuffer msg = new StringBuffer();
				msg.append("아래 양식에 첨부된 파일이 없습니다.\\n양식을 수정하시거나 관리자에게 문의바랍니다.\\n");
					
				for(int i = 0; i < existFile.size(); i++) {
					DataBookBean dbbean = (DataBookBean)existFile.get(i);
					msg.append("\\n" + dbbean.getFormseq() + ". " + dbbean.getFormtitle() + " ⇒ " + dbbean.getOriginfilenm());
				}
				
				out.write("<script language=javascript>location='/colldocFormView.do?mode=" + 3 + "&sysdocno=" + sysdocno + "';alert(\"" + msg + "\");</script>");
				out.close();

				return null;
			}
		}

		sysdocno = cdmgr.saveColldoc(cdbean, formatfileDir, sessi, mode, context, saveDir);
		
		UserManager umgr = UserManager.instance();
		umgr.updateChrgunitcd(user_id, chrg_code);
		
		if(mode == 1) {
			mode = 2;
		} else if(mode == 2) {
			mode = 3;
		} else if(mode == 3) {
			cdmgr.setDelyn(sysdocno, 0);
		} else if(mode == 4) {
			cdmgr.setDelyn(cdbean.getSysdocno(), 0);	//원본문서 보이게
		} else if(mode == 5) {
			mode = 3;
			cdmgr.setDelyn(sysdocno, 1);				//임시문서 안보이
		} else if(mode == 6) {
			mode = 2;
		}
		
		String reqAction = (String)request.getParameter("action");
		String action = null;
		
		if(reqAction.equals("INFOVIEW") == true) {			//저장 후 문서정보
			
			action = "document.location.href = " +
					 "'/colldocInfoView.do?mode=" + mode + "&sysdocno=" + sysdocno + "&msg=SAVE';";
			
		} else if(reqAction.equals("FORMVIEW") == true) {		//저장 후 양식정보
			
			action = "document.location.href = " +
					 "'/colldocFormView.do?mode=" + mode + "&sysdocno=" + sysdocno + "';";
			
		} else if(reqAction.equals("TEMPFORMVIEW") == true) {	//임시저장 후 양식정보
			
			action = "document.location.href = " +
			 		 "'/colldocFormView.do?mode=" + mode + "&sysdocno=" + sysdocno + "';";
			
		} else if(reqAction.equals("APPREP") == true) {			//저장 후 결재상신
			
			//취합문서 결재상신 상태로 지정
			String docstate = "04";		//검토대기(02), 승인대기(03), 취합진행(04)		
			if(cdform.getSancusrnm2().trim().equals("") == false) {	//승인자
				docstate = "03";
			}
			if(cdform.getSancusrnm1().trim().equals("") == false) {	//검토자
				docstate = "02";
			}
			
			action = "document.location.href = " +
			 		 "'/colldocAppRep.do?sysdocno=" + sysdocno + "&docstate=" + docstate +"';";
			
		}
		
		out.write("<script language=javascript>" + action + "</script>");
		out.close();

		return null;
	}	
}