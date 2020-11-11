package nexti.ejms.commsubdept.form;

import org.apache.struts.action.ActionForm;

public class CommSubdeptForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	
	private int		sysdocno        = 0;
	private String 	processchk		= "";
	
	private int		rchno			= 0;
	private int		rchgrpno			= 0;
	
	private String  sch_orggbn		= "";	//����ڱ���( 001:�����ú�û, 002:���ӱ��, 003:�����, 004:����ȸ, 005:��/��/��, 006: ��Ÿ)
	private String  grp_orggbn		= "";	//����ڱ���( 001:�����ú�û, 002:���ӱ��, 003:�����, 004:����ȸ, 005:��/��/��, 006: ��Ÿ)
	
	public int getRchgrpno() {
		return rchgrpno;
	}
	public void setRchgrpno(int rchgrpno) {
		this.rchgrpno = rchgrpno;
	}
	public int getRchno() {
		return rchno;
	}
	public void setRchno(int rchno) {
		this.rchno = rchno;
	}
	public String getProcesschk() {
		return processchk;
	}
	public void setProcesschk(String processchk) {
		this.processchk = processchk;
	}
	public int getSysdocno() {
		return sysdocno;
	}
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
	public String getGrp_orggbn() {
		return grp_orggbn;
	}
	public void setGrp_orggbn(String grp_orggbn) {
		this.grp_orggbn = grp_orggbn;
	}
	public String getSch_orggbn() {
		return sch_orggbn;
	}
	public void setSch_orggbn(String sch_orggbn) {
		this.sch_orggbn = sch_orggbn;
	}

	
	
}
