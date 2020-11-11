/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망 설문조사그룹 저장 action
 * 설명:
 */
package nexti.ejms.outside.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.outside.model.OutsideManager;
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.agent.AgentUtil;
import nexti.ejms.common.OutsideInfo;

public class OutsideRchGrpSaveAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
			
			ResearchBean Bean = new ResearchBean();
			
			if(AgentUtil.decryptSeed(request.getParameter("rchgrpno"))!= null){
				Bean.setRchgrpno(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("rchgrpno"))));
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
			Bean.setRchnolist(AgentUtil.decryptSeed(request.getParameter("rchnolist")));
			Bean.setHeadcont(AgentUtil.decryptSeed(request.getParameter("headcont")));
			Bean.setTailcont(AgentUtil.decryptSeed(request.getParameter("tailcont")));
			Bean.setAnscount(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("anscount"))));
			
			OutsideManager manager = OutsideManager.instance();
			
			cnt = manager.ResearchGrpSave(Bean);

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
		return null;	
	}
}