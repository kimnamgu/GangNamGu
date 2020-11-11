/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망 설문조사 결과 전송 action
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

public class OutsideRchResultAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response)  {
		try{
	//		가져온 결과에 대한 삭제처리가 됨으로 모든 결과값을 다가져오면 됨으로 주석처리됨.
			/*int rchsize = 0;
			int x = 0;
			String rchno = "";
			
			if(request.getParameter("rchsize") != null) {
				rchsize = Integer.parseInt(request.getParameter("rchsize"));
			}
			
			for(int i=0; i<rchsize; i++){
				x++;
				if(x !=1){
					rchno = rchno + ",";
				}
				rchno = rchno + request.getParameter("rchno"+i);
	 
			}*/
			
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
			
			OutsideManager manager = OutsideManager.instance();
			
			if ( "comp".equals((String)request.getParameter("transmit")) ) {
				manager.rchResultTransmitComplete();
				return null;
			}
			
			String resultXML = "";
	
			//resultXML = manager.getRchResult(rchno);
			
			resultXML = manager.getRchResult();
			
			response.setContentType("text/xml;charset=UTF-8");
			
			StringBuffer ansListXML = new StringBuffer();
			
			StringBuffer prefixXML = new StringBuffer();
			prefixXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			prefixXML.append("\n<root>");
	
			System.out.println("prefixXML :"+prefixXML);
			StringBuffer suffixXML = new StringBuffer();
			suffixXML.append("\n</root>");
	
			ansListXML.append(prefixXML.toString());
			ansListXML.append(resultXML);
			ansListXML.append(suffixXML.toString());
			System.out.println("ansListXML :"+ansListXML);
			PrintWriter out = response.getWriter();
			out.println(nexti.ejms.agent.AgentUtil.encryptSeed(ansListXML.toString()));
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