/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력하기 행추가형 입력자료 저장 action
 * 설명:
 */
package nexti.ejms.formatLine.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.formatLine.model.FormatLineManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class InputLineDataSaveAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		
		String user_id = session.getAttribute("user_id").toString();
		String dept_code = session.getAttribute("dept_code").toString();
		
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
		
		//시스템문서번호와 양식일련번호 얻어오기 - submit시 Parameter로 넘어옴
		int sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		int formseq = Integer.parseInt(request.getParameter("formseq"));
		
		int chrgunitcd = 0;
		
		//사용자 담당단위 코드 가져오기
		UserManager usrManager = UserManager.instance();
		chrgunitcd = usrManager.getUsrChrgunitcd(user_id);
		
		//저장할 칼럼수 얻어오기 - submit시 Parameter로 넘어옴
		int tblcols = Integer.parseInt(request.getParameter("tblcols"));
		String mode = request.getParameter("mode");
		
		String colname = "";
		String coldata = "";
		List colList = null;
		
		//행추가형 데이터 저장
		FormatLineManager manager = FormatLineManager.instance();
		int retCode = 0;
		String errMsg = "";
		String[] gubun = null;
		
		colList = new ArrayList();
		
		if(mode.equals("insert")){
			for(int j = 0; j < tblcols; j++) {
				if(j < 26) {
					colname = (char)('A' + j) + "";
				} else {
					colname = (char)('A' + j - 26) + "" + (char)('A' + j - 26);
				}
				
				coldata = request.getParameter(colname);
				
				colList.add(colname + ":" + coldata);
			}
			
			try {
				retCode = manager.insertFormatLineData(sysdocno, formseq, colList, tblcols, dept_code, user_id, chrgunitcd, mode);
			} catch (Exception e) {
				gubun = e.getMessage().split(":");
				if("Infinity".equals(gubun[1])) {
					errMsg = gubun[0] + "행 : " + "0으로 나눌 수는 없습니다.";
				} else if("NaN".equals(gubun[1])) {
					errMsg = gubun[0] + "행 : " + "입력값이 비어있습니다.수식계산을 위해서 숫자값을 입력하여주십시오.";
				}
			}	
			if(retCode > 0) {
				errMsg = "데이터 저장 완료";
			}
		}else if(mode.equals("update")){
			int seq = Integer.parseInt(request.getParameter("seq")) - Integer.parseInt(request.getParameter("strIdx"));
			String rowseq[] = request.getParameterValues("rowseq");
			for(int j = 0; j < tblcols; j++) {
				if(j < 26) {
					colname = (char)('A' + j) + "";
				} else {
					colname = (char)('A' + j - 26) + "" + (char)('A' + j - 26);
				}
				
				String coldatas[] = request.getParameterValues(colname);
				
				colList.add(colname + ":" + coldatas[(seq)]);
			}
			
			try {
				retCode = manager.updateFormatLineData(sysdocno, formseq, colList, tblcols, dept_code, user_id, chrgunitcd, Integer.parseInt(rowseq[(seq-1)]));
			} catch (Exception e) {
				gubun = e.getMessage().split(":");
				if("Infinity".equals(gubun[1])) {
					errMsg = gubun[0] + "행 : " + "0으로 나눌 수는 없습니다.";
				} else if("NaN".equals(gubun[1])) {
					errMsg = gubun[0] + "행 : " + "입력값이 비어있습니다.수식계산을 위해서 숫자값을 입력하여주십시오.";
				}
			}	
			if(retCode > 0) {
				errMsg = "데이터 수정 완료";
			}
		}else{
			int seq = Integer.parseInt(request.getParameter("seq")) - Integer.parseInt(request.getParameter("strIdx"));
			String rowseq[] = request.getParameterValues("rowseq");
			
			retCode = manager.deleteFormatLineData(sysdocno, formseq, dept_code, user_id, Integer.parseInt(rowseq[(seq-1)]));
			if(retCode > 0) {
				errMsg = "데이터 삭제 완료";
			}
		}

		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", errMsg));
		saveMessages(request,messages);
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		request.setAttribute("formseq", new Integer(formseq));
		request.setAttribute("retpage", new String("inputformatline"));
		
		return mapping.findForward("hiddenframe");
	}
}