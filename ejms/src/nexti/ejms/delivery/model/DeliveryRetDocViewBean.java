/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 반송처리 반송정보 bean
 * 설명:
 */
package nexti.ejms.delivery.model;

public class DeliveryRetDocViewBean {
	
	private String doctitle;	//문서제목
	private String coldeptnm;	//취합부서명
	private String chrgunitnm;	//취합담당단위명
	private String chrgusrnm;	//취합담당자명
	private String coldeptcd;	//취합부서코드
	private String coldepttel;	//취합담당자부서전화번호
	
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
