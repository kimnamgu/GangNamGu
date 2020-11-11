/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���������� actionform
 * ����:
 */
package nexti.ejms.chrgUnit.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ChrgUnitForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private List deptList = null;     //�μ����
	private List chrgList = null;     //������ ���
	
	private String mode = "";         //mode
	private String dept_id = "";      //�μ��ڵ�
	private String dept_name = "";    //�μ���Ī
	private String user_id   = "";    //����ھ��̵�
	private String user_name = "";	  //����ڸ�
	private int chrgunitcd = 0;       //������ �ڵ�
	private String chrgunitnm = "";   //������ ��Ī
	private int ord  = 0;             //���ļ���

	private int posi = 0;             //���ڵ� ��ũ�� ��ġ
	private int cposi = 0;            //���ڵ� ��ũ�� ��ġ
	private String orggbn = "";		  // ���� ( 1:�����ú�û, 2:���ӱ��, 3:�����, 4:����ȸ, 5:��/��/��, 6: ��Ÿ)

	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public List getChrgList() {
		return chrgList;
	}

	public void setChrgList(List chrgList) {
		this.chrgList = chrgList;
	}

	public List getDeptList() {
		return deptList;
	}

	public void setDeptList(List deptList) {
		this.deptList = deptList;
	}	

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
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

	public int getChrgunitcd() {
		return chrgunitcd;
	}

	public void setChrgunitcd(int chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
	}

	public String getChrgunitnm() {
		return chrgunitnm;
	}

	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}

	public void setPosi(int posi) {
		this.posi = posi;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getOrggbn() {
		return orggbn;
	}

	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}
}

