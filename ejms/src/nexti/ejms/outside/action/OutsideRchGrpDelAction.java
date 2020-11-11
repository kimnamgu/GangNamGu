/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망 설문조사그룹 삭제 action
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

import nexti.ejms.common.OutsideInfo;
import nexti.ejms.outside.model.OutsideManager;

public class OutsideRchGrpDelAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			
			int rchgrpno = 0;
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
			
			if(nexti.ejms.agent.AgentUtil.decryptSeed(request.getParameter("rchgrpno")) != null) {
				rchgrpno = Integer.parseInt(nexti.ejms.agent.AgentUtil.decryptSeed(request.getParameter("rchgrpno")));
			}
			
			//설문조사 마스터  가져오기
			OutsideManager manager = OutsideManager.instance();
			cnt = manager.ResearchGrpDlete(rchgrpno);
			
			PrintWriter out = response.getWriter();
			out.println(nexti.ejms.agent.AgentUtil.encryptSeed(new Integer(cnt).toString()));
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
