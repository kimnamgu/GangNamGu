/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��������� Ȯ�� bean
 * ����:
 */
package nexti.ejms.user.model;

public class UserExtBean {
	
	private String user_id = "";        //����� ID
	private String passwd = "";         //��й�ȣ
	private String mgryn = "";			//�����ڿ���
	private String chrgunitcd = "";		//�������ڵ�
	private String bigo = "";           //���
	private String lstlogindt = "";		//�ֱٷα����Ͻ�
	private String crtdt     = "";      //��������
	private String crtusr    = "";      //������
	private String uptdt   = "";       	//��������
	private String uptusr  = "";       	//������
	
	public String getBigo() {
		return bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
	public String getChrgunitcd() {
		return chrgunitcd;
	}
	public void setChrgunitcd(String chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
	}
	public String getCrtdt() {
		return crtdt;
	}
	public void setCrtdt(String crtdt) {
		this.crtdt = crtdt;
	}
	public String getCrtusr() {
		return crtusr;
	}
	public void setCrtusr(String crtusr) {
		this.crtusr = crtusr;
	}
	public String getLstlogindt() {
		return lstlogindt;
	}
	public void setLstlogindt(String lstlogindt) {
		this.lstlogindt = lstlogindt;
	}
	public String getMgryn() {
		return mgryn;
	}
	public void setMgryn(String mgryn) {
		this.mgryn = mgryn;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getUptdt() {
		return uptdt;
	}
	public void setUptdt(String uptdt) {
		this.uptdt = uptdt;
	}
	public String getUptusr() {
		return uptusr;
	}
	public void setUptusr(String uptusr) {
		this.uptusr = uptusr;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
}
