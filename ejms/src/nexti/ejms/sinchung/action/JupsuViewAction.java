/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 접수내역 보기 action
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
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.sinchung.form.DataForm;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.util.Utils;

public class JupsuViewAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {			
				
		DataForm dForm = (DataForm)form;
		int reqformno = dForm.getReqformno();
		int reqseq = dForm.getReqseq();
		
		//내용 복사
		SinchungManager smgr = SinchungManager.instance();
		ReqMstBean mstBean = smgr.reqDataInfo(reqformno, reqseq);		
		BeanUtils.copyProperties(dForm, mstBean);
		
		//마스터 정보가져오기
		String title = smgr.reqFormInfo(reqformno).getTitle();
		String basictype = smgr.reqFormInfo(reqformno).getBasictype();
		dForm.setTitle(title);
		dForm.setBasictype(basictype);
		
		//항목 양식 데이터 가져오기
		List articleList = smgr.reqFormSubList(reqformno);
		dForm.setArticleList(articleList);
		
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
			dForm.setSancusrinfo(sancusrnm);
		}
		
		return mapping.findForward("view");
	}
}