/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���������� bean
 * ����:
 */
package nexti.ejms.dept.model;

public class ChrgUnitBean {
	private String dept_id    = "";   //�μ��ڵ�
	private String user_id	  = "";	  //����ھ��̵�
	private String user_name  = "";	  //����ڸ�
	private int chrgunitcd    = 0;    //������ �ڵ�
    private String chrgunitnm = "";   //������ ��Ī
    private int ord           = 0;    //���ļ���
    private String crtdt      = "";   //��������
    private String crtusrid   = "";   //�������ڵ�
    private String uptdt      = "";   //��������
    private String uptusrid   = "";   //�������ڵ�
    
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getChrgunitcd() {
		return chrgunitcd;
	}
	public void setChrgunitcd(int chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
	}
	public String getChrgunitnm() {
		return chrgunitnm;
	}
	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}
	public String getCrtdt() {
		return crtdt;
	}
	public void setCrtdt(String crtdt) {
		this.crtdt = crtdt;
	}
	public String getCrtusrid() {
		return crtusrid;
	}
	public void setCrtusrid(String crtusrid) {
		this.crtusrid = crtusrid;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public String getUptdt() {
		return uptdt;
	}
	public void setUptdt(String uptdt) {
		this.uptdt = uptdt;
	}
	public String getUptusrid() {
		return uptusrid;
	}
	public void setUptusrid(String uptusrid) {
		this.uptusrid = uptusrid;
	}    
}
