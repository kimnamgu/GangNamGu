/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ������ϰ��� actionform
 * ����:
 */
package nexti.ejms.group.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GroupForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private List mainlist = null;
	private List sublist = null;
	
	private String mode      	= "";     //mode
	private String gbn       	= "";     //���� (s:�ý���, a:������)
	private int grplistcd		= 0;     //����ڵ�
	private String grplistnm    = "";     //��ϸ�Ī
	private int ord          	= 0;      //���ļ���
	private int seq          	= 0;      //�Ϸù�ȣ
	private String code 		= "";     //����̸�
	private String name		= "";     //����Ī
	private String codegbn		= "";	  //��󱸺�
	private int posi         	= 0;      //���ڵ� ��ũ�� ��ġ
	private int cposi        	= 0;      //���ڵ� ��ũ�� ��ġ
	private String sch_grplist  = "";	  //��ȸ���ǹ�����ϸ�����
	private String sch_orggbn	= "";	  //����ڱ���( 001:�����ú�û, 002:���ӱ��, 003:�����, 004:����ȸ, 005:��/��/��, 006: ��Ÿ)
	
	public String getSch_orggbn() {
		return sch_orggbn;
	}

	public void setSch_orggbn(String sch_orggbn) {
		this.sch_orggbn = sch_orggbn;
	}

	public String getCodegbn() {
		return codegbn;
	}

	public void setCodegbn(String codegbn) {
		this.codegbn = codegbn;
	}

	public String getSch_grplist() {
		return sch_grplist;
	}

	public void setSch_grplist(String sch_grplist) {
		this.sch_grplist = sch_grplist;
	}

	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}
	public List getSublist() {
		return sublist;
	}

	public void setSublist(List sublist) {
		this.sublist = sublist;
	}

	public List getMainlist() {
		return mainlist;
	}

	public void setMainlist(List mainlist) {
		this.mainlist = mainlist;
	}
	public String getGbn() {
		return gbn;
	}
	public void setGbn(String gbn) {
		this.gbn = gbn;
	}

	public int getCposi() {
		return cposi;
	}

	public void setCposi(int cposi) {
		this.cposi = cposi;
	}

	public int getPosi() {
		return posi;
	}

	public void setPosi(int posi) {
		this.posi = posi;
	}	

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String deptcd) {
		this.code = deptcd;
	}

	public String getName() {
		return name;
	}

	public void setName(String deptnm) {
		this.name = deptnm;
	}

	public int getGrplistcd() {
		return grplistcd;
	}

	public void setGrplistcd(int grplistcd) {
		this.grplistcd = grplistcd;
	}

	public String getGrplistnm() {
		return grplistnm;
	}

	public void setGrplistnm(String grplistnm) {
		this.grplistnm = grplistnm;
	}		
}

