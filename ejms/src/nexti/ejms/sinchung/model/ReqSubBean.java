/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� �߰� �Է��ڷ� bean
 * ����:
 */
package nexti.ejms.sinchung.model;

public class ReqSubBean {
	private int reqformno    = 0;    //��û��Ĺ�ȣ
	private int reqseq       = 0;    //��û��ȣ
	private int formseq      = 0;    //���׹�ȣ
	private String anscont   = "";   //��û����
	private String other     = "";   //��Ÿ�ǰ�
	
	public String getAnscont() {
		return anscont;
	}
	public void setAnscont(String anscont) {
		this.anscont = anscont;
	}
	public int getFormseq() {
		return formseq;
	}
	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public int getReqformno() {
		return reqformno;
	}
	public void setReqformno(int reqformno) {
		this.reqformno = reqformno;
	}
	public int getReqseq() {
		return reqseq;
	}
	public void setReqseq(int reqseq) {
		this.reqseq = reqseq;
	}	
}
