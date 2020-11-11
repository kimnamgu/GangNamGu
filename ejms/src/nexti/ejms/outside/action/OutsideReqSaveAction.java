/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망 신청서 저장 action
 * 설명:
 */
package nexti.ejms.outside.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.agent.AgentUtil;
import nexti.ejms.common.OutsideInfo;
import nexti.ejms.outside.model.OutsideManager;
import nexti.ejms.sinchung.model.ArticleBean;
import nexti.ejms.sinchung.model.FrmBean;
import nexti.ejms.sinchung.model.SampleBean;

public class OutsideReqSaveAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("OutsideReqSaveAction start");
		try{
			int cnt = 0;
			String serverkey = "";
			String clientip = request.getRemoteAddr();
			
			if(nexti.ejms.agent.AgentUtil.decryptSeed(request.getParameter("serverkey")) != null){
				serverkey = nexti.ejms.agent.AgentUtil.decryptSeed(request.getParameter("serverkey"));
			}

			if(OutsideUtil.outsidIpChk(clientip)== false || !serverkey.equals(OutsideInfo.getServerkey())){

				cnt = -1;

				PrintWriter out = response.getWriter();
				out.print(nexti.ejms.agent.AgentUtil.encryptSeed(new Integer(cnt).toString()));
				out.flush();
				out.close();
				return null;
			}
			
			List subList = null;
			List examList = null;
			
			FrmBean Bean = new FrmBean();
			
			if(AgentUtil.decryptSeed(request.getParameter("reqformno"))!= null){
				Bean.setReqformno(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("reqformno"))));
			}
			Bean.setTitle(AgentUtil.decryptSeed(request.getParameter("title")));
			Bean.setStrdt(AgentUtil.decryptSeed(request.getParameter("strdt")));
			Bean.setStrdt(AgentUtil.decryptSeed(request.getParameter("strdt")));
			Bean.setEnddt(AgentUtil.decryptSeed(request.getParameter("enddt")));
			Bean.setColdeptcd(AgentUtil.decryptSeed(request.getParameter("coldeptcd")));
			Bean.setColdeptnm(AgentUtil.decryptSeed(request.getParameter("coldeptnm")));
			Bean.setColtel(AgentUtil.decryptSeed(request.getParameter("coltel")));
			Bean.setChrgusrid(AgentUtil.decryptSeed(request.getParameter("chrgusrid")));
			Bean.setChrgusrnm(AgentUtil.decryptSeed(request.getParameter("chrgusrnm")));
			Bean.setSummary(AgentUtil.decryptSeed(request.getParameter("summary")));
			Bean.setRange(AgentUtil.decryptSeed(request.getParameter("range")));
			Bean.setRangedetail(AgentUtil.decryptSeed(request.getParameter("rangedetail")));
			Bean.setImgpreview(AgentUtil.decryptSeed(request.getParameter("imgpreview")));
			Bean.setDuplicheck(AgentUtil.decryptSeed(request.getParameter("duplicheck")));
			Bean.setLimitcount(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("limitcount"))));
			Bean.setSancneed(AgentUtil.decryptSeed(request.getParameter("sancneed")));
			Bean.setBasictype(AgentUtil.decryptSeed(request.getParameter("basictype")));
			Bean.setHeadcont(AgentUtil.decryptSeed(request.getParameter("headcont")));
			Bean.setTailcont(AgentUtil.decryptSeed(request.getParameter("tailcont")));
			
			if(AgentUtil.decryptSeed(request.getParameter("subsize"))!= null){
				subList = new ArrayList();
				for(int i=0; i<Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("subsize"))); i++){
					ArticleBean subbean = new ArticleBean();
					subbean.setReqformno(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("reqformno"))));
					subbean.setFormseq(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("formseq"+i))));
					subbean.setFormtitle(AgentUtil.decryptSeed(request.getParameter("formtitle"+i)));
					subbean.setRequire(AgentUtil.decryptSeed(request.getParameter("require"+i)));
					subbean.setFormtype(AgentUtil.decryptSeed(request.getParameter("formtype"+i)));
					subbean.setSecurity(AgentUtil.decryptSeed(request.getParameter("security"+i)));
					subbean.setHelpexp(AgentUtil.decryptSeed(request.getParameter("helpexp"+i)));
					subbean.setExamtype(AgentUtil.decryptSeed(request.getParameter("examtype"+i)));
					subbean.setEx_frsq(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("ex_frsq"+i))));	//동적 문항 추가(연계된 문항에 보기와 연계) 2018.2.28 
					subbean.setEx_exsq(AgentUtil.decryptSeed(request.getParameter("ex_exsq"+i)));//동적 문항 추가(연계된 문항에 보기와 연계) 2018.2.28 
					
					if(AgentUtil.decryptSeed(request.getParameter("examsize"+i))!= null){
						examList = new ArrayList();
						for(int j=0; j<Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("examsize"+i))); j++){
							SampleBean exambean = new SampleBean();
							exambean.setReqformno(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("reqformno"))));
							exambean.setFormseq(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("formseq"+i))));
							exambean.setExamseq(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("examseq"+i+""+j))));
							exambean.setExamcont(AgentUtil.decryptSeed(request.getParameter("examcont"+i+""+j)));
							examList.add(exambean);
						}
						subbean.setSampleList(examList);
					}
					
					subList.add(subbean);
				}
				Bean.setArticleList(subList);
				//강남이 아닌경우만 아래 타도록 강남은 받을때 reqformno 값으로 데이터 업데이트 
				{
					List subFileList = null;
					int subFileCount = Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("subfilecount")));
					for ( int i = 0; i < subFileCount; i++ ) {
						ArticleBean rsBean = new ArticleBean();
						rsBean.setReqformno(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("subfilereqformno"+i))));
						rsBean.setFormseq(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("subfileformseq"+i))));
						rsBean.setFileseq(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("subfilefileseq"+i))));
						rsBean.setFilenm(AgentUtil.decryptSeed(request.getParameter("subfilefilenm"+i)));
						rsBean.setOriginfilenm(AgentUtil.decryptSeed(request.getParameter("subfileoriginfilenm"+i)));
						rsBean.setFilesize(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("subfilefilesize"+i))));
						rsBean.setExt(AgentUtil.decryptSeed(request.getParameter("subfileext"+i)));
						rsBean.setOrd(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("subfileord"+i))));
						rsBean.setFileToBase64Encoding(AgentUtil.decryptSeed(request.getParameter("subfilebase64encoding"+i)));
						if ( subFileList == null ) subFileList = new ArrayList();
						subFileList.add(rsBean);
					}
					Bean.setSubFileList(subFileList);
					
					List examFileList = null;
					int examFileCount = Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("examfilecount")));
					for ( int i = 0; i < examFileCount; i++ ) {
						SampleBean reBean = new SampleBean();
						reBean.setReqformno(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("examfilereqformno"+i))));
						reBean.setFormseq(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("examfileformseq"+i))));
						reBean.setExamseq(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("examfileexamseq"+i))));
						reBean.setFileseq(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("examfilefileseq"+i))));
						reBean.setFilenm(AgentUtil.decryptSeed(request.getParameter("examfilefilenm"+i)));
						reBean.setOriginfilenm(AgentUtil.decryptSeed(request.getParameter("examfileoriginfilenm"+i)));
						reBean.setFilesize(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("examfilefilesize"+i))));
						reBean.setExt(AgentUtil.decryptSeed(request.getParameter("examfileext"+i)));
						reBean.setOrd(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("examfileord"+i))));
						reBean.setFileToBase64Encoding(AgentUtil.decryptSeed(request.getParameter("examfilebase64encoding"+i)));
						if ( examFileList == null ) examFileList = new ArrayList();
						examFileList.add(reBean);
					}
					Bean.setExamFileList(examFileList);
				}
			}
			
			OutsideManager manager = OutsideManager.instance();
			
			cnt = manager.RequestSave(Bean, getServlet().getServletContext());

			PrintWriter out = response.getWriter();
			out.println(AgentUtil.encryptSeed(new Integer(cnt).toString()));
			out.flush();
			out.close();
			System.out.println("OutsideReqSaveAction end");
		}catch(Exception e){
			e.printStackTrace();
			int cnt = -2;
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(nexti.ejms.agent.AgentUtil.encryptSeed(new Integer(cnt).toString()));
				out.flush();
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;	
	}
}