/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ڷ��̰� bean
 * ����:
 */
package nexti.ejms.dataTransfer.model;

public class DataTransferBean {
	
	private int		historyno	= 0;	//�̷¹�ȣ
	private int		seq			= 0;	//ó����ȣ
	private	String	tablename	= "";	//�������̺�
	private	String	columnname	= "";	//�����÷�
	private	String	oldvalue	= "";	//��������
	private	String	cause		= "";	//�̰�����
	private	String	crtdt		= "";	//�̰��Ͻ�
	private	String	crtusrid	= "";	//�̰���ID
	
	private String[] gbn		= null;	//�����ڷᱸ��
	private String[] gbnid		= null;	//���浥����
	private String[] tgtuserid	= null;	//�̰��������ID
	
	private int		no			= 0;	//�ڷ��ȣ
	private String	type		= "";	//�ڷ�����
	private String	title		= "";	//����
	private String	deptid		= "";	//�μ�ID
	private String	deptnm		= "";	//�μ���
	private String	userid		= "";	//�����ID
	private String	usernm		= "";	//����ڸ�
	private String	existdeptid = "";	//�μ����翩��
	private String	existuserid = "";	//��������翩��
	private String	uptdt	= "";		//������������
	
	private String	uptusrid	= "";	//����������ID
	private int		chrgunitid	= 0;	//������ID
	private String	chrgunitnm	= "";	//��������
	private	String	depttel		= "";	//�μ���ȭ��ȣ
	
	public String[] getGbn() {
		return gbn;
	}
	public void setGbn(String[] gbn) {
		this.gbn = gbn;
	}
	public String[] getGbnid() {
		return gbnid;
	}
	public void setGbnid(String[] gbnid) {
		this.gbnid = gbnid;
	}
	public String[] getTgtuserid() {
		return tgtuserid;
	}
	public void setTgtuserid(String[] tgtuserid) {
		this.tgtuserid = tgtuserid;
	}
	public int getChrgunitid() {
		return chrgunitid;
	}
	public void setChrgunitid(int chrgunitid) {
		this.chrgunitid = chrgunitid;
	}
	public String getChrgunitnm() {
		return chrgunitnm;
	}
	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}
	public String getDepttel() {
		return depttel;
	}
	public void setDepttel(String depttel) {
		this.depttel = depttel;
	}
	public String getUptusrid() {
		return uptusrid;
	}
	public void setUptusrid(String uptusrid) {
		this.uptusrid = uptusrid;
	}
	public String getExistdeptid() {
		return existdeptid;
	}
	public void setExistdeptid(String existdeptid) {
		this.existdeptid = existdeptid;
	}
	public String getExistuserid() {
		return existuserid;
	}
	public void setExistuserid(String existuserid) {
		this.existuserid = existuserid;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDeptnm() {
		return deptnm;
	}
	public void setDeptnm(String deptnm) {
		this.deptnm = deptnm;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUptdt() {
		return uptdt;
	}
	public void setUptdt(String uptdt) {
		this.uptdt = uptdt;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsernm() {
		return usernm;
	}
	public void setUsernm(String usernm) {
		this.usernm = usernm;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
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
	public int getHistoryno() {
		return historyno;
	}
	public void setHistoryno(int historyno) {
		this.historyno = historyno;
	}
	public String getOldvalue() {
		return oldvalue;
	}
	public void setOldvalue(String oldvalue) {
		this.oldvalue = oldvalue;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
}
