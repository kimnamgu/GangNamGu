/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����������չ��� bean
 * ����:
 */
package nexti.ejms.colldoc.model;

public class CollprocBean {
	
	private int 	sysdocno		= 0;
	private String  radiochk		= "";   //��������( ����1, ����� 2, ��������� 3, ����/�Է��ڿ��԰��� 4)
	private String	closedate		= "";	//������ 
	private String	searchkey		= "";	//�˻���
	private String  formcomment 	= "";   //��İ���
	private String  formhederhtml	= "";   //�����屸��
	private String  formbodyhtml	= "";   //��Ĺٵ���
	private String  formtailhtml	= "";	//������ϱ���
	private String  enddt			= "";   //��������
	private String  deliverydt		= "";   //��νð� 
	private String  endcomment  	= "";   //�����˸���
	private String  docstate 		= "";   //��������
	private String  docstatenm		= "";	//�������¸�
	private String  chrgusrcd		= "";	//���մ�����ڵ�


	public String getChrgusrcd() {
		return chrgusrcd;
	}


	public void setChrgusrcd(String chrgusrcd) {
		this.chrgusrcd = chrgusrcd;
	}


	public String getDocstatenm() {
		return docstatenm;
	}


	public void setDocstatenm(String docstatenm) {
		this.docstatenm = docstatenm;
	}


	public String getDocstate() {
		return docstate;
	}


	public void setDocstate(String docstate) {
		this.docstate = docstate;
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


	public String getFormcomment() {
		return formcomment;
	}


	public void setFormcomment(String formcomment) {
		this.formcomment = formcomment;
	}

	public String getClosedate() {
		return closedate;
	}


	public void setClosedate(String closedate) {
		this.closedate = closedate;
	}


	public String getRadiochk() {
		return radiochk;
	}


	public void setRadiochk(String radiochk) {
		this.radiochk = radiochk;
	}


	public String getSearchkey() {
		return searchkey;
	}


	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}


	public int getSysdocno() {
		return sysdocno;
	}


	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}


	public String getFormbodyhtml() {
		return formbodyhtml;
	}


	public void setFormbodyhtml(String formbodyhtml) {
		this.formbodyhtml = formbodyhtml;
	}


	public String getFormhederhtml() {
		return formhederhtml;
	}


	public void setFormhederhtml(String formhederhtml) {
		this.formhederhtml = formhederhtml;
	}


	public String getFormtailhtml() {
		return formtailhtml;
	}


	public void setFormtailhtml(String formtailhtml) {
		this.formtailhtml = formtailhtml;
	}


	public String getDeliverydt() {
		return deliverydt;
	}


	public void setDeliverydt(String deliverydt) {
		this.deliverydt = deliverydt;
	}

	
}