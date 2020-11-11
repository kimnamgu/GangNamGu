/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ܺθ� ��������,��û�� �Ǽ� ��ȸ action
 * ����:
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
import nexti.ejms.common.OutsideInfo;

public class OutsideCntAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			int rno = 0;
			int cnt = 0;
			String mode = "";
			String serverkey = "";
			String clientip = request.getRemoteAddr();
			
			if(nexti.ejms.agent.AgentUtil.decryptSeed(request.getParameter("serverkey")) != null){
				serverkey = nexti.ejms.agent.AgentUtil.decryptSeed(request.getParameter("serverkey"));
			}

			if( OutsideUtil.outsidIpChk(clientip)==false || !serverkey.equals(OutsideInfo.getServerkey())){

				cnt = -1;

				PrintWriter out = response.getWriter();
				out.print(nexti.ejms.agent.AgentUtil.encryptSeed(new Integer(cnt).toString()));
				out.flush();
				out.close();
				return null;
			}
			
			if(nexti.ejms.agent.AgentUtil.decryptSeed(request.getParameter("rno")) != null){
				rno = Integer.parseInt(nexti.ejms.agent.AgentUtil.decryptSeed(request.getParameter("rno")));
			}
			
			if(nexti.ejms.agent.AgentUtil.decryptSeed(request.getParameter("mode")) != null){
				mode = nexti.ejms.agent.AgentUtil.decryptSeed(request.getParameter("mode"));
			}	
			
			OutsideManager manager = OutsideManager.instance();
			
			if ( mode.equals("research") ) {
				cnt= manager.rchMstCnt(rno);
			} if ( mode.equals("researchGrp") ) {
				cnt= 0;
			} else {
				//���� ��û �Ǽ�
				int cnt1 = manager.reqMstCnt(rno, "1");    //������ ��
				int cnt2 = manager.reqMstCnt(rno, "2");    //��û��
				cnt = cnt1 + cnt2;
			}
			
			PrintWriter out = response.getWriter();
			out.print(nexti.ejms.agent.AgentUtil.encryptSeed(new Integer(cnt).toString()));
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