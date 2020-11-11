/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 주소찾기 bean
 * 설명:
 */
package nexti.ejms.addr.model;

public class AddrBean {
	/*지번************************************/
	private int     bunho       = 0;    //연번	
	private String	zipcode		= "";	//우편번호
	private String	addr		= "";	//주소	
	private String  bungi		= "";   //번지
	/*도로명************************************/
	private String province = "";
	private String city = "";
	private String street = "";
	private String bdnm = "";
	private String bdno = "";
	private String dong = "";
	/*신청서************************************/
	private int reqseq = 0;  //신청서 신청번호
		
	/*지번************************************/
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getBunho() {
		return bunho;
	}
	public void setBunho(int bunho) {
		this.bunho = bunho;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getBungi() {
		return bungi;
	}
	public void setBungi(String bungi) {
		this.bungi = bungi;
	}
	/*도로명************************************/
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getBdnm() {
		return bdnm;
	}
	public void setBdnm(String bdnm) {
		this.bdnm = bdnm;
	}
	public String getBdno() {
		return bdno;
	}
	public void setBdno(String bdno) {
		this.bdno = bdno;
	}
	
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	/*신청서************************************/
	public int getReqseq() {
		return reqseq;
	}
	public void setReqseq(int reqseq) {
		this.reqseq = reqseq;
	}
}
