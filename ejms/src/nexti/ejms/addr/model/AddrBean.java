/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ּ�ã�� bean
 * ����:
 */
package nexti.ejms.addr.model;

public class AddrBean {
	/*����************************************/
	private int     bunho       = 0;    //����	
	private String	zipcode		= "";	//�����ȣ
	private String	addr		= "";	//�ּ�	
	private String  bungi		= "";   //����
	/*���θ�************************************/
	private String province = "";
	private String city = "";
	private String street = "";
	private String bdnm = "";
	private String bdno = "";
	private String dong = "";
	/*��û��************************************/
	private int reqseq = 0;  //��û�� ��û��ȣ
		
	/*����************************************/
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
	/*���θ�************************************/
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
	/*��û��************************************/
	public int getReqseq() {
		return reqseq;
	}
	public void setReqseq(int reqseq) {
		this.reqseq = reqseq;
	}
}
