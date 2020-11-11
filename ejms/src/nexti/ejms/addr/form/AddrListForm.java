/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ּ�ã�� actionform
 * ����:
 */
package nexti.ejms.addr.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class AddrListForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
		
	private String sch_addr 	= "";   	//�˻�����
	private String zipcode		= "";		//�����ȣ
	private String addr			= "";		//�ּ�
	
	private String gbn = "";
	private String prov = "";
	private String city = "";
	private String street = "";
		
	private List addrList 		= null; 	//��û������Ʈ

	public List getAddrList() {
		return addrList;
	}

	public void setAddrList(List addrList) {
		this.addrList = addrList;
	}

	public String getSch_addr() {
		return sch_addr;
	}

	public void setSch_addr(String sch_addr) {
		this.sch_addr = sch_addr;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getGbn() {
		return gbn;
	}

	public void setGbn(String gbn) {
		this.gbn = gbn;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
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
}