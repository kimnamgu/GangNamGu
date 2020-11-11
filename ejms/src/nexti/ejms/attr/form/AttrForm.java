/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Ӽ���ϰ��� actionform
 * ����:
 */
package nexti.ejms.attr.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AttrForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private List mainlist = null;
	private List sublist = null;
	
	private String mode      = "";     //mode
	private String gbn       = "";     //���� (s:�ý���, a:������)
	private String listcd    = "";     //����ڵ�
	private String listnm    = "";     //��ϸ�Ī
	private int seq          = 0;      //�Ϸù�ȣ
	private String listdtlnm = "";     //��ϳ���
	private String attr_desc = "";     //�Ӽ��󼼼���
	private int posi         = 0;      //���ڵ� ��ũ�� ��ġ
	private int cposi        = 0;      //���ڵ� ��ũ�� ��ġ

	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}
	
	public String getAttr_desc() {
		return attr_desc;
	}

	public void setAttr_desc(String attr_desc) {
		this.attr_desc = attr_desc;
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

	public String getListcd() {
		return listcd;
	}

	public void setListcd(String listcd) {
		this.listcd = listcd;
	}

	public String getListdtlnm() {
		return listdtlnm;
	}

	public void setListdtlnm(String listdtlnm) {
		this.listdtlnm = listdtlnm;
	}

	public String getListnm() {
		return listnm;
	}

	public void setListnm(String listnm) {
		this.listnm = listnm;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}		
}

