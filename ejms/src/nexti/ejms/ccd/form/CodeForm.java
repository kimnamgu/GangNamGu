/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ڵ���� actionform
 * ����:
 */
package nexti.ejms.ccd.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CodeForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private List mainlist = null;
	private List sublist = null;
	
	private String mode = "";         //mode
	private String gbn = "";          //���� (s:�ý���, a:������)
	private String ccd_cd ="";        //���ڵ�
	private String ccd_sub_cd ="";    //���ڵ�
	private String ccd_name = "";     //�ڵ��
	private String ccd_sub_name = ""; //���ڵ��(���ڵ�� ���ڵ���� ���� ���ɶ� ���)
	private String ccd_desc = "";     //�ڵ弳��
	private int posi = 0;             //���ڵ� ��ũ�� ��ġ
	private int cposi = 0;            //���ڵ� ��ũ�� ��ġ

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

	public String getCcd_cd() {
		return ccd_cd;
	}

	public void setCcd_cd(String ccd_cd) {
		this.ccd_cd = ccd_cd;
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

	public String getCcd_desc() {
		return ccd_desc;
	}

	public void setCcd_desc(String ccd_desc) {
		this.ccd_desc = ccd_desc;
	}

	public String getCcd_name() {
		return ccd_name;
	}

	public void setCcd_name(String ccd_name) {
		this.ccd_name = ccd_name;
	}

	public String getCcd_sub_cd() {
		return ccd_sub_cd;
	}

	public void setCcd_sub_cd(String ccd_sub_cd) {
		this.ccd_sub_cd = ccd_sub_cd;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getCcd_sub_name() {
		return ccd_sub_name;
	}

	public void setCcd_sub_name(String ccd_sub_name) {
		this.ccd_sub_name = ccd_sub_name;
	}			
}

