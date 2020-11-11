/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 결재내역 보기 action
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

import nexti.ejms.common.rootAction;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.sinchung.form.DataForm;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;

public class SancViewAction extends rootAction {
	
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
		String coldeptnm = smgr.reqFormInfo(reqformno).getColdeptnm();
		String chrgusrid = smgr.reqFormInfo(reqformno).getChrgusrid();
		String chrgusrnm = smgr.reqFormInfo(reqformno).getChrgusrnm();
		dForm.setTitle(title);            //문서제목
		dForm.setBasictype(basictype);    //입력기본정보
		dForm.setColdeptnm(coldeptnm);    //접수부서명칭
		dForm.setChrgusrnm(chrgusrnm);    //접수담당자 성명
		
		//접수부서 전화번호
		String tel = smgr.reqFormInfo(reqformno).getColtel();
		UserBean ubean = UserManager.instance().getUserInfo(chrgusrid);
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		dForm.setDepttel(tel);
		
		//항목 양식 데이터 가져오기
		List articleList = smgr.reqFormSubList(reqformno);
		dForm.setArticleList(articleList);
		
		//결재선 설정
		dForm.setSancList1(smgr.approvalInfo("1", reqformno, reqseq)); //검토
		dForm.setSancList2(smgr.approvalInfo("2", reqformno, reqseq)); //승인
		
		//결재하기(1), 결재완료(2)
		request.setAttribute("gbn", request.getParameter("gbn"));     
		
		return mapping.findForward("view");
	}
}