/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 수정 action
 * 설명:
 */
package nexti.ejms.sinchung.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.elecAppr.model.ElecApprBean;
import nexti.ejms.elecAppr.model.ElecApprManager;
import nexti.ejms.sinchung.form.SinchungForm;
import nexti.ejms.sinchung.model.FrmBean;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class SinchungModifyAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		SinchungForm sForm = (SinchungForm)form;		
		int reqformno = sForm.getReqformno();	
		int reqseq = sForm.getReqseq();
		
		SinchungManager smgr = SinchungManager.instance();
		
		//마스터정보 가져오기
		FrmBean frmbean = smgr.reqFormInfo(reqformno);
		BeanUtils.copyProperties(sForm, frmbean);
		sForm.setReqseq(reqseq);
		
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
		
		//항목정보가져오기
		List articleList = smgr.reqFormSubList(reqformno);
		sForm.setArticleList(articleList);	
		
		//신청 내역 정보 가져오기
		ReqMstBean rbean = smgr.reqDataInfo(reqformno, reqseq);
		sForm.setRbean(rbean);
		
		//결재선 설정
		sForm.setSancList1(smgr.approvalInfo("1", reqformno, reqseq)); //검토
		sForm.setSancList2(smgr.approvalInfo("2", reqformno, reqseq)); //승인
		
		//수정,취소, 결재 가능 여부 설정
		ReqMstBean rmBean = smgr.reqDataInfo(reqformno, reqseq);
		String docstate = "";
		if ( rmBean != null ) {
			docstate = rmBean.getState();
		}
		boolean sancfl = smgr.existSanc(reqformno, reqseq);   //결재된 건이 있는지 확인
		
		if ( "01".equals(docstate) == true ) {	//미결재(결재필요함)
			request.setAttribute("sanc", "T");                //결재선 지정 가능
			if(sancfl == true){		//결재한경우
				request.setAttribute("modify", "F");          //수정, 취소 불가
			} else {				//결재안한경우
				request.setAttribute("modify", "T");          //수정, 취소 가능
			}
		} else if ( "02".equals(docstate) == true ) {	//신청중(결재하였거나결재필요없음)
			request.setAttribute("sanc", "F");                //결재선 지정 불가
			if ( sForm.getSancneed() == null || sForm.getSancneed().equals("Y") == false ) {	//결재필요없을때
				request.setAttribute("modify", "T");          //수정, 취소 가능
			} else {	//결재필요때
				request.setAttribute("modify", "F");          //수정, 취소 불가
			}
		} else {
			request.setAttribute("sanc", "F");                //결재선 지정 불가
			request.setAttribute("modify", "F");              //수정, 취소 불가
		}
		
		//전자결재정보
		ElecApprManager eamgr = ElecApprManager.instance();
		ElecApprBean eaBean = eamgr.selectRequestSancInfo(reqformno, reqseq);
		
		if ( eaBean != null ) {
			String sancresult = Utils.nullToEmptyString(eaBean.getSancresult());
			String submitdt = Utils.nullToEmptyString(eaBean.getSubmitdt());
			String sancusrnm = Utils.nullToEmptyString(eaBean.getSancusrnm());
			if ( eaBean.getSancyn().equals("0") == true ) {
				sancusrnm = "결재 진행중";
			} else {
				sancusrnm = sancresult + " : " + submitdt + " " + sancusrnm;
			}
			sForm.setSancusrinfo(sancusrnm);
		}
		
		return mapping.findForward("modify");
	}
}