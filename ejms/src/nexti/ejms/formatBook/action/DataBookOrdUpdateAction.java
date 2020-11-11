/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합완료 제본자료형 제본순서 저장 action
 * 설명:
 */
package nexti.ejms.formatBook.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.formatBook.form.DataBookForm;
import nexti.ejms.formatBook.model.DataBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;

public class DataBookOrdUpdateAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
				
		//Form에서 넘어온 값 가져오기 
		DataBookForm dataBookForm = (DataBookForm)form;
		List rowList = new ArrayList();

		String ord_gubun = request.getParameter("ord_gubun");

		if(request.getParameter("app") != null){
			String modify = new String(request.getParameter("app"));
			StringTokenizer stModify = new StringTokenizer(modify, ",");
			String[] sModify = new String[stModify.countTokens()];
			int i = 0;
			while(stModify.hasMoreTokens())
			{
			    sModify[i++] = stModify.nextToken();                    
			}
		
			for(int j = 0; j < sModify.length; j++)
			{
			    if(!sModify[j].equals(""))
			    {
					String[] appList = sModify[j].split(";");
					DataBookBean Bean = new DataBookBean();
					
					Bean.setSysdocno(Integer.parseInt(appList[0]));
					Bean.setFormseq(Integer.parseInt(appList[1]));
					Bean.setFileseq(Integer.parseInt(appList[2]));
					Bean.setOrd(appList[3]);
					Bean.setTgtdeptcd(appList[4]);
					Bean.setInputusrid(appList[5]);
					
					rowList.add(Bean);
		        }
		    }
		}	
		
		FormatBookManager manager = FormatBookManager.instance();
		int retCode = manager.DataBookOrdUpdate(ord_gubun, rowList);
		
		request.setAttribute("savecheck", new Integer(retCode));
		request.setAttribute("sysdocno", new Integer(dataBookForm.getSysdocno()));
		request.setAttribute("formseq", new Integer(dataBookForm.getFormseq()));
		
		return mapping.findForward("hiddenframe");
	}
}