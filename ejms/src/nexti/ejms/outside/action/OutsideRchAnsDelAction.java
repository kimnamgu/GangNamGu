/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망 설문조사 결과 전송후 삭제 action
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
import nexti.ejms.research.model.ResearchAnsBean;
import nexti.ejms.agent.AgentUtil;
import nexti.ejms.common.OutsideInfo;

public class OutsideRchAnsDelAction extends Action{
	
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
			
			List ansList = null;

			if(AgentUtil.decryptSeed(request.getParameter("listsize"))!= null){
				ansList = new ArrayList();
				String listsize = AgentUtil.decryptSeed(request.getParameter("listsize"));
				for(int i=0; i< Integer.parseInt(listsize); i++){
					ResearchAnsBean ansbean = new ResearchAnsBean();
					ansbean.setRchno(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("rchno"+i))));
					ansbean.setAnsusrseq(Integer.parseInt(AgentUtil.decryptSeed(request.getParameter("ansusrseq"+i))));
					ansList.add(ansbean);
				}
			}
			
			OutsideManager manager = OutsideManager.instance();
			
			cnt = manager.rchAnsDel(ansList);

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