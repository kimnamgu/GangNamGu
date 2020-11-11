/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 결재선지정 추가 action
 * 설명:
 */
package nexti.ejms.commapproval.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.approval.model.ApprovalManager;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.commapproval.model.commapprovalManager;
import nexti.ejms.commapproval.model.commapprovalBean;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.common.rootPopupAction;
import nexti.ejms.delivery.model.DeliveryManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class designateInsertAction extends rootPopupAction{

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
	
		request.setCharacterEncoding("UTF-8");
		
		String cmd = request.getParameter("cmd");
		String type = request.getParameter("type");
		int isMaking = Integer.parseInt(request.getParameter("isMaking"));
		int	sysdocno =0;
		sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		String idList = request.getParameter("idList");
		String nameList = request.getParameter("nameList");
		String gubunList = request.getParameter("gubunList");
		
		//세션정보 가져오기
		HttpSession session = request.getSession();		
		String sessionId = session.getId().split("[!]")[0];
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
		
		boolean result = false;
		ArrayList userList = new ArrayList();
		
		String[] userId = null;
		String[] userName = null;
		String[] gubun = null;

		commapprovalManager manager = commapprovalManager.instance();
		
		if (!"".equals(idList)){
			userId = idList.split(":");
		}
		
		if (!"".equals(nameList)){
			userName = nameList.split(":");
		}
		
		if (!"".equals(gubunList)){
			gubun = gubunList.split(":");
		}
		
		commapprovalBean bean = null;
		
		if (userId != null){
			for (int i = 0; i < userId.length; i++){
				bean = new commapprovalBean();

				bean.setUserId(userId[i]);
				bean.setUserName(userName[i]);
				bean.setGubun(gubun[i]);
				
				userList.add(bean);
				bean = null;
			}
		}
		
		if ("INSERT".equals(cmd)){
			result = manager.designateInsert(sysdocno, userList, sessionId, dept_code, user_id, type);
		}

		String msg = "";
		if(result == true && isMaking != 1) {
			ApprovalManager aprvmgr = ApprovalManager.instance();
			CommTreatManager ctmgr = CommTreatManager.instance();
			if(type.equals("1") == true) { //취합부서
				ColldocManager cdmgr = ColldocManager.instance();
				String docstate = cdmgr.getDocState(sysdocno).getDocstate();		
				String[] sancInfo = null;	//시스템문서번호, 사용자코드
				if(docstate.equals("02") == true) {	//검토대기
					sancInfo = aprvmgr.isColSancComplete(sysdocno, "1");
					if(sancInfo == null) {	//검토자가 없을 때
						sancInfo = aprvmgr.isColSancComplete(sysdocno, "2");
						if(sancInfo == null) {	//승인자가 없을 때
							cdmgr.appovalColldoc(user_id, dept_code, sysdocno, "04");
							msg = "검토자와 승인자가 지정되지 않아\n담당자 전결로 취합진행 처리되었습니다.";
						} else if(sancInfo != null && sancInfo[0].equals("-1") == false) {	//승인 완료
							aprvmgr.doLastColSancCheck(Integer.parseInt(sancInfo[0], 10), "2", dept_code, sancInfo[1]);
							msg = "승인자 모두 결재를 하여\n취합진행 처리되었습니다.";
						} else {	//승인 미완료
							ctmgr.updateDocState(sysdocno, user_id, "03");
							msg = "검토자가 지정되지 않아\n승인대기 처리되었습니다.";
						}
					} else if(sancInfo != null && sancInfo[0].equals("-1") == false) {	//검토 완료
						aprvmgr.doLastColSancCheck(Integer.parseInt(sancInfo[0], 10), "1", dept_code, sancInfo[1]);
						msg = "검토자가 모두 결재를 하여\n승인대기 처리되었습니다.";
					}
				} else if(docstate.equals("03") == true) {	//승인대기
					sancInfo = aprvmgr.isColSancComplete(sysdocno, "2");
					if(sancInfo == null) {	//승인자가 없을 때
						cdmgr.appovalColldoc(user_id, dept_code, sysdocno, "04");
						msg = "검토자와 승인자가 지정되지 않아\n담당자 전결로 취합진행 처리되었습니다.";
					} else if(sancInfo != null && sancInfo[0].equals("-1") == false) {	//승인 완료
						aprvmgr.doLastColSancCheck(Integer.parseInt(sancInfo[0], 10), "2", dept_code, sancInfo[1]);
						msg = "승인자 모두 결재를 하여\n취합진행 처리되었습니다.";
					}
				}
			} else if(type.equals("1") == false) { //제출부서
				String submitState = ctmgr.getTgtdeptState(sysdocno, dept_code);		
				String[] sancInfo = null;	//시스템문서번호, 제출부서코드, 사용자코드
				if(submitState.equals("03") == true) {	//검토대기
					sancInfo = aprvmgr.isTgtSancComplete(sysdocno, dept_code, "1");
					if(sancInfo == null) {	//검토자가 없을 때
						sancInfo = aprvmgr.isTgtSancComplete(sysdocno, dept_code, "2");
						if(sancInfo == null) {	//승인자가 없을 때
							ctmgr.updateSubmitState(sysdocno, dept_code, user_id, "05");
							msg = "검토자와 승인자가 지정되지 않아\n담당자 전결로 제출완료 처리되었습니다.";
						} else if(sancInfo != null && sancInfo[0].equals("-1") == false) {	//승인 완료
							aprvmgr.doLastTgtSancCheck(Integer.parseInt(sancInfo[0], 10), "2", sancInfo[1], sancInfo[2]);
							msg = "승인자 모두 결재를 하여\n제출완료 처리되었습니다.";
						} else {	//승인 미완료
							ctmgr.updateSubmitState(sysdocno, dept_code, user_id, "04");
							msg = "검토자가 지정되지 않아\n승인대기 처리되었습니다.";
						}
					} else if(sancInfo != null && sancInfo[0].equals("-1") == false) {	//검토 완료
						aprvmgr.doLastTgtSancCheck(Integer.parseInt(sancInfo[0], 10), "1", sancInfo[1], sancInfo[2]);
						msg = "검토자가 모두 결재를 하여\n승인대기 처리되었습니다.";
					}
				} else if(submitState.equals("04") == true) {	//승인대기
					DeliveryManager dvmgr = DeliveryManager.instance();
					sancInfo = aprvmgr.isTgtSancComplete(sysdocno, dept_code, "2");
					if(sancInfo == null) {	//승인자가 없을 때
						ctmgr.updateSubmitState(sysdocno, dept_code, user_id, "05");
						msg = "검토자와 승인자가 지정되지 않아\n담당자 전결로 제출완료 처리되었습니다.";
						if(dvmgr.IsLastDeliveryDept(sysdocno, dept_code) == true) {	//마지막 제출부서이면
							ctmgr.updateDocState(sysdocno, user_id, "05");
						}
					} else if(sancInfo != null && sancInfo[0].equals("-1") == false) {	//승인 완료
						aprvmgr.doLastTgtSancCheck(Integer.parseInt(sancInfo[0], 10), "2", sancInfo[1], sancInfo[2]);
						msg = "승인자 모두 결재를 하여\n제출완료 처리되었습니다.";
					}
				}
			}			
		}
		
		StringBuffer resultXML = new StringBuffer();
		
		resultXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		resultXML.append("\n<result>");
		resultXML.append("\n<cmd>" + cmd + "</cmd>");
		resultXML.append("\n<retCode>" + result + "</retCode>");
		resultXML.append("\n<msg>" + msg + "</msg>");
		resultXML.append("\n</result>");
		
		response.setContentType("text/xml;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(resultXML.toString());
		out.flush();
		out.close();
		
		return null;
	}
}