/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ϱ� bean
 * ����:
 */
package nexti.ejms.delivery.model;

public class DeliveryBean {
	private int seqno 			= 0;            //����
	private int formseq			= 0;			//��Ĺ�ȣ
	private String formkind		= "";			//�������
	private int sysdocno 		= 0;         	//�ý��۹�����ȣ		
	private String doctitle 	= "";     		//����
	private String deliverydt 	= "";   		//��������
	private String coldeptnm 	= "";    		//���պμ���Ī
	private String chrgusrnm 	= "";    		//���մ���ڸ�
	private String enddt 		= "";        	//�����Ͻ�
	private String remaintime 	= "";   		//�����ð�
	private String dept_member_id = "";			//�μ�������(���������� �α����� ������ ���� ���� ��� 1��)
	
	public String getDept_member_id() {
		return dept_member_id;
	}
	public void setDept_member_id(String dept_member_id) {
		this.dept_member_id = dept_member_id;
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
	public String getDeliverydt() {
		return deliverydt;
	}
	public void setDeliverydt(String deliverydt) {
		this.deliverydt = deliverydt;
	}
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getRemaintime() {
		return remaintime;
	}
	public void setRemaintime(String remaintime) {
		this.remaintime = remaintime;
	}
	public int getSeqno() {
		return seqno;
	}
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}
	public String getDoctitle() {
		return doctitle;
	}
	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}
	public int getSysdocno() {
		return sysdocno;
	}
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
	public String getFormkind() {
		return formkind;
	}
	public void setFormkind(String formkind) {
		this.formkind = formkind;
	}
	public int getFormseq() {
		return formseq;
	}
	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}
}
