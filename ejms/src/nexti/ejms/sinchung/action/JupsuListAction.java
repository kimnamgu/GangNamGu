/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 접수내역 목록 action
 * 설명:
 */
package nexti.ejms.sinchung.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.sinchung.form.DataListForm;
import nexti.ejms.sinchung.model.SearchBean;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.sinchung.model.FrmBean;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.DateTime;
import nexti.ejms.util.commfunction;

public class JupsuListAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		HttpSession session = request.getSession();;
		String user_id = (String)session.getAttribute("user_id");
		
		DataListForm dataForm = (DataListForm)form;
		int reqformno = dataForm.getReqformno();    //신청서 양식번호
		int cPage = dataForm.getPage();             //현재페이지
		String gbn = dataForm.getGbn();				//정렬순서
		String presentnm = dataForm.getPresentnm(); //신청자
		String procGbn = dataForm.getProcGbn();     //미처리건 (0:미처리 , 1:전체)
		
		SinchungManager smgr = SinchungManager.instance();
		
		//마스터 정보 복사
		FrmBean fbean = smgr.reqFormInfo(reqformno);
		
		if ( !dataForm.getStrdt().equals("") && !dataForm.getStrdt().equals(fbean.getStrdt()) ) {
			fbean.setStrdt(dataForm.getStrdt());
		}
		if ( !dataForm.getEnddt().equals("") && !dataForm.getEnddt().equals(fbean.getEnddt()) ) {
			fbean.setEnddt(dataForm.getEnddt());
		} else {
			fbean.setEnddt(DateTime.getCurrentDate());
		}
		
		BeanUtils.copyProperties(dataForm, fbean);
		
		//접수부서 전화번호
		String tel = fbean.getColtel();
		UserBean ubean = UserManager.instance().getUserInfo(fbean.getChrgusrid());
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		dataForm.setColtel(tel);
		
		//데이터 범위 결정
		int pageSize = 10;		//한번에 표시한 리스트의 갯수
		int start = commfunction.startIndex(cPage, pageSize);
		int end = commfunction.endIndex(cPage, pageSize);
		
		//조회조건 셋팅
		SearchBean search = new SearchBean();
		search.setUserid(user_id);
		search.setReqformno(reqformno);
		search.setGbn(gbn);
		search.setPresentnm(presentnm);
		search.setStrdt(fbean.getStrdt());
		search.setEnddt(fbean.getEnddt());
		search.setProcFL(procGbn);
		search.setStart_idx(start);
		search.setEnd_idx(end);
		
		//목록 가져오기
		List dataList = smgr.reqDataList(search);
		dataForm.setDataList(dataList);		
		
		//페이지 및 조건 셋팅		
		HashMap searchCondition = new HashMap();
		searchCondition.put("reqformno", String.valueOf(reqformno));
		searchCondition.put("presentnm",presentnm);	
		searchCondition.put("procGbn", procGbn);			
		request.setAttribute("search",searchCondition);
		
		//화면으로 가져갈 값 설정
		int totalCount = smgr.reqDataTotCnt(search);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);
		int tbunho = totalCount - ((cPage - 1) * pageSize) ;
		request.setAttribute("totalPage",new Integer(totalPage));		//총페이지수
		request.setAttribute("totalCount", new Integer(totalCount));    //총 데이터 건수
		request.setAttribute("currpage", new Integer(cPage));           //현재페이지		
		request.setAttribute("tbunho", new Integer(tbunho));            //한페이지당 데이터건수
		
		return mapping.findForward("list");
	}
}