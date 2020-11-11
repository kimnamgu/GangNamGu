/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 접수내역 엑셀다운로드 action
 * 설명:
 */
package nexti.ejms.sinchung.action;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.sinchung.form.DataForm;
import nexti.ejms.sinchung.form.DataListForm;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.sinchung.model.SearchBean;
import nexti.ejms.sinchung.model.SinchungManager;

public class JupsuXlsAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 		
				
		DataListForm dataListForm = (DataListForm)form;
		int reqformno = dataListForm.getReqformno();    //신청서 양식번호	
		String presentnm = dataListForm.getPresentnm(); //신청자
		String procGbn = dataListForm.getProcGbn();     //미처리건 (0:미처리 , 1:전체)
				
		//조회조건 셋팅		
		SearchBean search = new SearchBean();	
		search.setReqformno(reqformno);
		search.setPresentnm(presentnm);
		search.setStrdt(dataListForm.getStrdt());
		search.setEnddt(dataListForm.getEnddt());
		search.setProcFL(procGbn);
		search.setStart_idx(1);
		search.setEnd_idx(99999);
		
		//목록 가져오기
		SinchungManager smgr = SinchungManager.instance();	
		List dataList = smgr.reqDataList(search);
		dataListForm.setDataList(dataList);
		
		ArrayList arrList = new ArrayList();
		
		for(int i = 0; i < dataList.size(); i++) {
			DataForm dataForm = new DataForm();
			
			int reqseq = ((ReqMstBean)dataList.get(i)).getReqseq();
			
			//내용 복사
			ReqMstBean mstBean = smgr.reqDataInfo(reqformno, reqseq);
			BeanUtils.copyProperties(dataForm, mstBean);
			
			//마스터 정보가져오기
			String title = smgr.reqFormInfo(reqformno).getTitle();
			String basictype = smgr.reqFormInfo(reqformno).getBasictype();
			dataForm.setTitle(title);
			dataForm.setBasictype(basictype);
			
			//항목 양식 데이터 가져오기
			List articleList = smgr.reqFormSubList(reqformno);
			dataForm.setArticleList(articleList);
			
			arrList.add(dataForm);
		}
				
		//신청서명
		request.setAttribute("reqTitle", smgr.reqFormInfo(reqformno).getTitle());
		//신청내역목록
		request.setAttribute("dataFormList", arrList);
				
		return mapping.findForward("excel");
	}
}