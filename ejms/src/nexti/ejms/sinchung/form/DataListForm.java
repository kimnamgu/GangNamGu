/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ��û���� ��� actionform
 * ����:
 */
package nexti.ejms.sinchung.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class DataListForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int reqformno    = 0;        //��û��Ĺ�ȣ	
	private String presentnm = "";       //��û��
	private String procGbn   = "1";    	 //��ó�� ����(0:��ó�� , 1:��ü)
	
	private String title     = "";       //����
	private String coldeptnm = "";       //���μ���
	private String coltel    = "";       //�μ���ȭ��ȣ	
	private String chrgusrnm = "";       //����ڸ�
	private String strdt     = "";       //������
	private String enddt     = "";       //������
	private String gbn       = "1";      //��û����(0:��û��, 1:��ü), ��ó�� ����(0:��ó�� , 1:��ü)
	private int page         = 1 ;       //������
		
	private List dataList = null;        //��û ����Ʈ

	public String getPresentnm() {
		return presentnm;
	}

	public void setPresentnm(String presentnm) {
		this.presentnm = presentnm;
	}

	public String getProcGbn() {
		return procGbn;
	}

	public void setProcGbn(String procGbn) {
		this.procGbn = procGbn;
	}

	public int getReqformno() {
		return reqformno;
	}

	public void setReqformno(int reqformno) {
		this.reqformno = reqformno;
	}

	public String getChrgusrnm() {
		return chrgusrnm;
	}

	public void setChrgusrnm(String chrgusrnm) {
		this.chrgusrnm = chrgusrnm;
	}

	public String getColdeptnm() {
		return coldeptnm;
	}

	public void setColdeptnm(String coldeptnm) {
		this.coldeptnm = coldeptnm;
	}

	public String getColtel() {
		return coltel;
	}

	public void setColtel(String coltel) {
		this.coltel = coltel;
	}

	public String getEnddt() {
		return enddt;
	}

	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}

	public String getStrdt() {
		return strdt;
	}

	public void setStrdt(String strdt) {
		this.strdt = strdt;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public String getGbn() {
		return gbn;
	}

	public void setGbn(String gbn) {
		this.gbn = gbn;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}