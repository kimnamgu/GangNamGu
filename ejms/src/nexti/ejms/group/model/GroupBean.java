/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ������ϰ��� bean
 * ����:
 */
package nexti.ejms.group.model;

public class GroupBean {
	private int grplistcd	    = 0;      	//��������ڵ�
	private String grplistnm    = "";     	//������ϸ�Ī
	private int seq          	= 0;      	//�Ϸù�ȣ
	private String code 		= "";		//����ڵ�
	private String name 		= "";     	//����Ī
	private String displayName	= "";		//�����ü��Ī
	private String codegbn		= "";		//��󱸺�
	private int ord          	= 0;      	//���ļ���
	private String crtdt     	= "";     	//�ۼ�����
	private String crtusrid  	= "";     	//�ۼ���
	private String uptusrid		= "";		//������
	private String crtusrgbn	= "";		//�ۼ��ڱ���
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getCodegbn() {
		return codegbn;
	}
	public void setCodegbn(String codegbn) {
		this.codegbn = codegbn;
	}
	public String getCrtusrgbn() {
		return crtusrgbn;
	}
	public void setCrtusrgbn(String crtusrgbn) {
		this.crtusrgbn = crtusrgbn;
	}
	public String getUptusrid() {
		return uptusrid;
	}
	public void setUptusrid(String uptusrid) {
		this.uptusrid = uptusrid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String deptcd) {
		this.code = deptcd;
	}
	public String getName() {
		return name;
	}
	public void setName(String deptnm) {
		this.name = deptnm;
	}
	public int getGrplistcd() {
		return grplistcd;
	}
	public void setGrplistcd(int grplistcd) {
		this.grplistcd = grplistcd;
	}
	public String getGrplistnm() {
		return grplistnm;
	}
	public void setGrplistnm(String grplistnm) {
		this.grplistnm = grplistnm;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
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
}
