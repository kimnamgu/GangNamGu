/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망 설문조사 저장 action
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

import nexti.ejms.outside.model.OutsideManager;
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.research.model.ResearchExamBean;
import nexti.ejms.research.model.ResearchSubBean;
import nexti.ejms.agent.AgentUtil;
import nexti.ejms.common.OutsideInfo;

public class OutsideRchSaveAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			//System.out.println("OutsideRchSaveAction start");
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
			
			ResearchBean Bean = new ResearchBean();
			
			if(AgentUtil.decryptSeed(request.getParameter("rchno"))!= null){
				Bean.setRchno(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("rchno"))));
			}
			Bean.setTitle(AgentUtil.decryptSeed(request.getParameter("title")));
			Bean.setStrdt(AgentUtil.decryptSeed(request.getParameter("strdt")));
			Bean.setStrdt(AgentUtil.decryptSeed(request.getParameter("strdt")));
			Bean.setEnddt(AgentUtil.decryptSeed(request.getParameter("enddt")));
			Bean.setColdeptcd(AgentUtil.decryptSeed(request.getParameter("coldeptcd")));
			Bean.setColdeptnm(AgentUtil.decryptSeed(request.getParameter("coldeptnm")));
			Bean.setColdepttel(AgentUtil.decryptSeed(request.getParameter("coldepttel")));
			Bean.setChrgusrid(AgentUtil.decryptSeed(request.getParameter("chrgusrid")));
			Bean.setChrgusrnm(AgentUtil.decryptSeed(request.getParameter("chrgusrnm")));
			Bean.setSummary(AgentUtil.decryptSeed(request.getParameter("summary")));
			Bean.setExhibit(AgentUtil.decryptSeed(request.getParameter("exhibit")));
			Bean.setOpentype(AgentUtil.decryptSeed(request.getParameter("opentype")));
			Bean.setRange(AgentUtil.decryptSeed(request.getParameter("range")));
			Bean.setRangedetail(AgentUtil.decryptSeed(request.getParameter("rangedetail")));
			Bean.setImgpreview(AgentUtil.decryptSeed(request.getParameter("imgpreview")));
			Bean.setDuplicheck(AgentUtil.decryptSeed(request.getParameter("duplicheck")));
			Bean.setLimitcount(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("limitcount"))));
			Bean.setGroupyn(AgentUtil.decryptSeed(request.getParameter("groupyn")));
			Bean.setHeadcont(AgentUtil.decryptSeed(request.getParameter("headcont")));
			Bean.setTailcont(AgentUtil.decryptSeed(request.getParameter("tailcont")));
			Bean.setAnscount(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("anscount"))));
			
			if(request.getParameter("subsize")!= null){
				subList = new ArrayList();
				for(int i=0; i<Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("subsize"))); i++){
					ResearchSubBean subbean = new ResearchSubBean();
					subbean.setFormseq(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("formseq"+i))));
					subbean.setFormgrp(AgentUtil.decryptSeed(request.getParameter("formgrp"+i)));
					subbean.setFormtitle(AgentUtil.decryptSeed(request.getParameter("formtitle"+i)));
					subbean.setFormtype(AgentUtil.decryptSeed(request.getParameter("formtype"+i)));
					subbean.setSecurity(AgentUtil.decryptSeed(request.getParameter("security"+i)));
					subbean.setExamtype(AgentUtil.decryptSeed(request.getParameter("examtype"+i)));
					subbean.setRequire(AgentUtil.decryptSeed(request.getParameter("require"+i)));			//2017.02.09 필수응답 추가
					subbean.setEx_frsq(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("ex_frsq"+i))));			//2017.02.09 필수응답 추가
					subbean.setEx_exsq(AgentUtil.decryptSeed(request.getParameter("ex_exsq"+i)));			//2017.02.09 필수응답 추가
					
					if(request.getParameter("examsize"+i)!= null){
						examList = new ArrayList();
						String parami="";
				        if(i<10){
				        	parami = "0"+i;
				        }else{
				        	parami = i+"";
				        }
						for(int j=0; j<Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("examsize"+i))); j++){
							ResearchExamBean exambean = new ResearchExamBean();
					          String paramNum=parami;
					          if(j<10){
					        	  paramNum += "0"+j;
					          }else{
					        	  paramNum += j+"";
					          }
							int examseq = Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("examseq"+paramNum)));
							String examcont = AgentUtil.decryptSeed(request.getParameter("examcont"+paramNum));
							exambean.setExamseq(examseq);
							exambean.setExamcont(examcont);
							examList.add(exambean);
						}
						subbean.setExamList(examList);
					}
					
					subList.add(subbean);
				}
				Bean.setListrch(subList);
				
				//강남이 아닌경우만 아래 타도록 강남은 받을때 rchno 값으로 데이터 업데이트
				{
					List subFileList = null;
					int subFileCount = Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("subfilecount")));
					for ( int i = 0; i < subFileCount; i++ ) {
						ResearchSubBean rsBean = new ResearchSubBean();
						rsBean.setRchno(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("subfilerchno"+i))));
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
						ResearchExamBean reBean = new ResearchExamBean();
						reBean.setRchno(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("examfilerchno"+i))));
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
			
			cnt = manager.ResearchSave(Bean, getServlet().getServletContext());

			PrintWriter out = response.getWriter();
			out.println(AgentUtil.encryptSeed(new Integer(cnt).toString()));
			out.flush();
			out.close();
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
		//System.out.println("OutsideRchSaveAction end");
		return null;	
	}
}