/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ϱ� ���վ���ڷ� bean
 * ����:
 */
package nexti.ejms.delivery.model;

public class DeliveryDetailBean {

	private String enddt;		//��������
	private String endcomment;	//�����˸���
	
	public DeliveryDetailBean() {
		this.enddt		= "";
		this.endcomment	= "";
	}

	/**
	 * @return the endcomment
	 */
	public String getEndcomment() {
		return endcomment;
	}

	/**
	 * @param endcomment the endcomment to set
	 */
	public void setEndcomment(String endcomment) {
		this.endcomment = endcomment;
	}

	/**
	 * @return the enddt
	 */
	public String getEnddt() {
		return enddt;
	}

	/**
	 * @param enddt the enddt to set
	 */
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
}
