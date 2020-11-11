/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통처리현황 진행상태 actionform
 * 설명:
 */
package nexti.ejms.commtreat.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class CommTreatDeptStatusForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int 	sysdocno 	= 0;			//시스템문서번호
	private String	viewmode	= "all";		//대상전체(all),완료(comp),미제출(nocomp)
	private String	mode		= "view";		//조회(view),수정(modify)
	private String 	modifydept	= "";			//수정적용부서
	private String 	modifyvalue	= "0";			//수정가능YN
	private String  docstate	= "";			//문서상태
	
	private String	submitdepttotcount	= "0";	//제출대상수
	private String	submitdeptcompcount = "0";	//제출완료수
	private String	submitdeptnocompcount = "0";	//미제출수
	
	private List	deptlist;			//제출부서리스트

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