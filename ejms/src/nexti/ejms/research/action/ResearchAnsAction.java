/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ���� ���� action
 * ����:
 */
package nexti.ejms.research.action;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.research.model.ResearchAnsBean;
import nexti.ejms.research.model.ResearchManager;

public class ResearchAnsAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		int result = 0;
		List ansList = null;
		String msg = "";
		HttpSession session = request.getSession();
		String usrid = (String)session.getAttribute("user_id");
		String usrnm = (String)session.getAttribute("user_name");
		
		ResearchManager manager = ResearchManager.instance();
		String formtype = "";
		int rchno =  Integer.parseInt(request.getParameter("rchno").toString());
		int listcnt = Integer.parseInt(request.getParameter("listcnt").toString());
		
		ansList = new ArrayList();
		for(int i=0; i<listcnt; i++){
			ResearchAnsBean bean = new ResearchAnsBean();
			int elistcnt = 0;
			String anscont = "";
			String other = "";
			bean.setFormseq(Integer.parseInt(request.getParameter("formseq"+i)));
			formtype = request.getParameter("formtype"+i).toString();
			
			if(formtype.equals("01")||formtype.equals("02")){
				elistcnt = Integer.parseInt(request.getParameter("elistcnt"+i));
				for(int j=0; j<elistcnt ; j++ ){
					if(formtype.equals("01")){
						if(request.getParameter("anscont"+i)!= null){
							anscont = request.getParameter("anscont"+i);
							other = request.getParameter("other"+i);
						}
					}else{
						if(request.getParameter(("anscont"+i)+"_"+j)!=null){
							if("".equals(anscont)){
								anscont = request.getParameter(("anscont"+i)+"_"+j);
							}else{
								anscont = anscont + "," + request.getParameter(("anscont"+i)+"_"+j);
							}
						}
						other = request.getParameter("other"+i);
					}
					bean.setAnscont(anscont);
					bean.setOther(other);
				}
			}else{
				bean.setAnscont(request.getParameter("anscont"+i+"0304"));
			}

			ansList.add(bean);
		}
		
		//����� ���պμ�����, ����μ�, ����׷� �ӽ� ���̺� ������ ���󹮼� �����ͷ� ��� 
		result = manager.rchAnsSave(ansList, rchno, usrid, usrnm);
		
		if ( result > 0 ) {
			
			if (rchno == 4712 ) //더 강남 관련 설문(응답완료시 감사문구 변경)
				msg = "<img src=\"/images/research_comp_tgn.gif\" alt=\"�亯�� ��� �Ǿ���ϴ�\">";
			else
				msg = "<img src=\"/images/research_comp.gif\" alt=\"�亯�� ��� �Ǿ���ϴ�\">";
			
			Cookie cookie = new Cookie("cookie_ejms_rch", URLEncoder.encode("rch"+rchno,"EUC-KR"));
			cookie.setMaxAge(60*60*24*30);    //30�ϵ��� ��Ű�� ��� �ֵ��� ����
			response.addCookie(cookie);
			
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
			saveMessages(request,messages);
		}
		
		request.setAttribute("result", new Integer(result));
		
		request.removeAttribute("hidden");

		return mapping.findForward("hidden");
	}	
}