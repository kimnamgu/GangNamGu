/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����Ϸ� ��� actionform
 * ����:
 */
package nexti.ejms.approval.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AppCompListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String docgbn     = "";     //��������
	private int page          = 0;     	//������
	private List appCompList  = null;   //����Ϸ� ���
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {	
		
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	
	}	
	
	public String getDocgbn() {
		return docgbn;
	}

	public void setDocgbn(String docgbn) {
		this.docgbn = docgbn;
	}

	public List getAppCompList() {
		return appCompList;
	}

	public void setAppCompList(List appCompList) {
		this.appCompList = appCompList;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}	
}