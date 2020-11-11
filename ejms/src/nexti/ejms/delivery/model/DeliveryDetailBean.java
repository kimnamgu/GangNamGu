/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 취합양식자료 bean
 * 설명:
 */
package nexti.ejms.delivery.model;

public class DeliveryDetailBean {

	private String enddt;		//마감시한
	private String endcomment;	//마감알림말
	
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
