/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ������ actionform
 * ����:
 */
package nexti.ejms.format.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CommFormatForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private int sysdocno      = 0;   //������ȣ
	private String sancgbn    = "";  //��������(1:��ι���, 2:���⹮��) 
	private String enddt      = "";  //��������
	private String endcomment = "";  //�����˸���
	private int appCnt        = 0;   //�����ؾ��� �Ǽ�
	 
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {		
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}

	public int getAppCnt() {
		return appCnt;
	}

	public void setAppCnt(int appCnt) {
		this.appCnt = appCnt;
	}

	public String getEndcomment() {
		return endcomment;
	}

	public void setEndcomment(String endcomment) {
		this.endcomment = endcomment;
	}

	public String getEnddt() {
		return enddt;
	}

	public void setEnddt(String enddt) {
		this.enddt = enddt;
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
}