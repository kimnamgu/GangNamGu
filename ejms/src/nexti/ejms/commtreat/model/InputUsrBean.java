/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է´���� ������ ���� bean
 * ����:
 */
package nexti.ejms.commtreat.model;

public class InputUsrBean {
	private String chrgunitnm  = "";    //������ ��Ī
	private String inputusrnm  = "";    //�Է´���ڸ�
	private String chrusrnm    = "";    //������ : �Է´���ڸ�,...
	
	public String getChrusrnm() {
		return chrusrnm;
	}
	public void setChrusrnm(String chrusrnm) {
		this.chrusrnm = chrusrnm;
	}
	public String getChrgunitnm() {
		return chrgunitnm;
	}	
	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}
	public String getInputusrnm() {
		return inputusrnm;
	}
	public void setInputusrnm(String inputusrnm) {
		this.inputusrnm = inputusrnm;
	}	
}
