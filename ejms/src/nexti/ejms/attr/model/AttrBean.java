/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Ӽ���ϰ��� bean
 * ����:
 */
package nexti.ejms.attr.model;

import java.util.List;

public class AttrBean {
	private String	listcd		= "";	//����ڵ�
	private String	listnm		= "";	//��ϸ�Ī
	private String	crtdt		= "";	//�ۼ�����
	private String	crtusrid	= "";	//�ۼ���
	private String	crtusrgbn	= "";	//�ۼ��ڱ���
	private int		seq			= 0;	//�Ϸù�ȣ
	private String	listdtlnm	= "";	//��ϳ���
	private String 	attr_desc	= "";	//�Ӽ��󼼼���
	private List	listdtlList	= null;	//�Ӽ���ϸ���Ʈ
	
	public List getListdtlList() {
		return listdtlList;
	}
	public void setListdtlList(List listdtlnmList) {
		this.listdtlList = listdtlnmList;
	}
	public String getCrtusrgbn() {
		return crtusrgbn;
	}
	public void setCrtusrgbn(String crtusrgbn) {
		this.crtusrgbn = crtusrgbn;
	}
	public String getAttr_desc() {
		return attr_desc;
	}
	public void setAttr_desc(String attr_desc) {
		this.attr_desc = attr_desc;
	}
	public String getListcd() {
		return listcd;
	}
	public void setListcd(String listcd) {
		this.listcd = listcd;
	}
	public String getListdtlnm() {
		return listdtlnm;
	}
	public void setListdtlnm(String listdtlnm) {
		this.listdtlnm = listdtlnm;
	}
	public String getListnm() {
		return listnm;
	}
	public void setListnm(String listnm) {
		this.listnm = listnm;
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
