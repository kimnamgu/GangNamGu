/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� ���� bean
 * ����:
 */
package nexti.ejms.research.model;

import java.util.List;

public class ResearchAnsBean  {
	
	private int rchno 		= 0;    //������ȣ
	private int ansusrseq	= 0;    //�亯�ڹ�ȣ
	private int formseq     = 0;    //���׹�ȣ
	private String anscont 	= "";   //���⳻��
	private String other 	= "";	//��ŸYN
	private String crtusrid = "";	//�Է���
	private String crtusrnm = "";	//�Է��ڸ�
	private String crtdt 	= "";   //�Է½ð�
	
	private List ansList = null;   	//���� ���
	
	public String getCrtdt() {
		return crtdt;
	}
	public void setCrtdt(String crtdt) {
		this.crtdt = crtdt;
	}
	public List getAnsList() {
		return ansList;
	}
	public void setAnsList(List ansList) {
		this.ansList = ansList;
	}
	public String getCrtusrid() {
		return crtusrid;
	}
	public void setCrtusrid(String crtusrid) {
		this.crtusrid = crtusrid;
	}
	public String getCrtusrnm() {
		return crtusrnm;
	}
	public void setCrtusrnm(String crtusrnm) {
		this.crtusrnm = crtusrnm;
	}
	public String getAnscont() {
		return anscont;
	}
	public void setAnscont(String anscont) {
		this.anscont = anscont;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public int getFormseq() {
		return formseq;
	}
	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}
	public int getRchno() {
		return rchno;
	}
	public void setRchno(int rchno) {
		this.rchno = rchno;
	}
	public int getAnsusrseq() {
		return ansusrseq;
	}
	public void setAnsusrseq(int ansusrseq) {
		this.ansusrseq = ansusrseq;
	}
}