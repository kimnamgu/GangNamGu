/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ں��� actionform
 * ����:
 */
package nexti.ejms.organ.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ManagerForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private String 	user_id      = "";     //������ ID
	private String 	user_id1     = "";     //ȭ�鿡 �ٽ� �ѷ����� ���� ���´�.
	private String 	gbn          = "";     //����/��ϱ���(D:����)
	private List 	mgrList      = null;   //������ ���
	private String 	orggbn       = "";	   //��������
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}

	public String getOrggbn() {
		return orggbn;
	}

	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}

	public String getUser_id1() {
		return user_id1;
	}

	public void setUser_id1(String user_id1) {
		this.user_id1 = user_id1;
	}

	public String getGbn() {
		return gbn;
	}

	public void setGbn(String gbn) {
		this.gbn = gbn;
	}	

	public List getMgrList() {
		return mgrList;
	}

	public void setMgrList(List mgrList) {
		this.mgrList = mgrList;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}	
}