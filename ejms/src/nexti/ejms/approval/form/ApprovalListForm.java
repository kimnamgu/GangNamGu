/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ϱ� ��� actionform
 * ����:
 */
package nexti.ejms.approval.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ApprovalListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private int page       = 0 ;     	//������
	private List appList   = null;   	//�����ϱ� ���
	
	private String sancgbn     = "";    //��������(1:��ι���, 2:���⹮��)
	private int sysdocno   = 0;         //�ý��۹�����ȣ
	
	private String docgbn     = "";     //��������
	
	public String getDocgbn() {
		return docgbn;
	}

	public void setDocgbn(String docgbn) {
		this.docgbn = docgbn;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {		
		return null;
	}	

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	
	}

	public String getSancgbn() {
		return sancgbn;
	}

	public void setSancgbn(String sancgbn) {
		this.sancgbn = sancgbn;
	}

	public int getSysdocno() {
		return sysdocno;
	}

	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}

	public List getAppList() {
		return appList;
	}

	public void setAppList(List appList) {
		this.appList = appList;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}	
}