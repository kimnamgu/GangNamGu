/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ϱ� �ݼ�ó�� �ݼ����� bean
 * ����:
 */
package nexti.ejms.delivery.model;

public class DeliveryRetDocViewBean {
	
	private String doctitle;	//��������
	private String coldeptnm;	//���պμ���
	private String chrgunitnm;	//���մ�������
	private String chrgusrnm;	//���մ���ڸ�
	private String coldeptcd;	//���պμ��ڵ�
	private String coldepttel;	//���մ���ںμ���ȭ��ȣ
	
	public DeliveryRetDocViewBean() {
		this.doctitle 	= "";
		this.coldeptnm 	= "";
		this.chrgunitnm = "";
		this.chrgusrnm 	= "";
		this.coldeptcd  = "";
		this.coldepttel = "";
	}

	/**
	 * @return the chrgunitnm
	 */
	public String getChrgunitnm() {
		return chrgunitnm;
	}

	/**
	 * @param chrgunitnm the chrgunitnm to set
	 */
	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}

	/**
	 * @return the chrgusrnm
	 */
	public String getChrgusrnm() {
		return chrgusrnm;
	}

	/**
	 * @param chrgusrnm the chrgusrnm to set
	 */
	public void setChrgusrnm(String chrgusrnm) {
		this.chrgusrnm = chrgusrnm;
	}

	/**
	 * @return the coldeptnm
	 */
	public String getColdeptnm() {
		return coldeptnm;
	}

	/**
	 * @param coldeptnm the coldeptnm to set
	 */
	public void setColdeptnm(String coldeptnm) {
		this.coldeptnm = coldeptnm;
	}

	/**
	 * @return the doctitle
	 */
	public String getDoctitle() {
		return doctitle;
	}

	/**
	 * @param doctitle the doctitle to set
	 */
	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}

	public String getColdeptcd() {
		return coldeptcd;
	}

	public void setColdeptcd(String coldeptcd) {
		this.coldeptcd = coldeptcd;
	}

	public String getColdepttel() {
		return coldepttel;
	}

	public void setColdepttel(String coldepttel) {
		this.coldepttel = coldepttel;
	}
}
