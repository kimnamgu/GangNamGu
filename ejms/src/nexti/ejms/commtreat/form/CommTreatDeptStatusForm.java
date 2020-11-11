/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ó����Ȳ ������� actionform
 * ����:
 */
package nexti.ejms.commtreat.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class CommTreatDeptStatusForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int 	sysdocno 	= 0;			//�ý��۹�����ȣ
	private String	viewmode	= "all";		//�����ü(all),�Ϸ�(comp),������(nocomp)
	private String	mode		= "view";		//��ȸ(view),����(modify)
	private String 	modifydept	= "";			//��������μ�
	private String 	modifyvalue	= "0";			//��������YN
	private String  docstate	= "";			//��������
	
	private String	submitdepttotcount	= "0";	//�������
	private String	submitdeptcompcount = "0";	//����Ϸ��
	private String	submitdeptnocompcount = "0";	//�������
	
	private List	deptlist;			//����μ�����Ʈ

	public String getDocstate() {
		return docstate;
	}

	public void setDocstate(String docstate) {
		this.docstate = docstate;
	}

	public List getDeptlist() {
		return deptlist;
	}

	public void setDeptlist(List deptlist) {
		this.deptlist = deptlist;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getModifydept() {
		return modifydept;
	}

	public void setModifydept(String modifydept) {
		this.modifydept = modifydept;
	}

	
	public String getModifyvalue() {
		return modifyvalue;
	}

	public void setModifyvalue(String modifyvalue) {
		if (modifyvalue==null || "".equals(modifyvalue)) {
			modifyvalue = "0";
		}
		this.modifyvalue = modifyvalue;
	}

	public String getSubmitdeptcompcount() {
		return submitdeptcompcount;
	}

	public void setSubmitdeptcompcount(String submitdeptcompcount) {
		this.submitdeptcompcount = submitdeptcompcount;
	}

	public String getSubmitdeptnocompcount() {
		return submitdeptnocompcount;
	}

	public void setSubmitdeptnocompcount(String submitdeptnocompcount) {
		this.submitdeptnocompcount = submitdeptnocompcount;
	}

	public String getSubmitdepttotcount() {
		return submitdepttotcount;
	}

	public void setSubmitdepttotcount(String submitdepttotcount) {
		this.submitdepttotcount = submitdepttotcount;
	}

	public int getSysdocno() {
		return sysdocno;
	}

	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}

	public String getViewmode() {
		return viewmode;
	}

	public void setViewmode(String viewmode) {
		this.viewmode = viewmode;
	}
}