/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��οϷ� bean
 * ����:
 */
package nexti.ejms.delivery.model;

public class DeliveryCompBean {

	private int seqno;      		//����
	private int formseq;			//��Ĺ�ȣ
	private String formkind;		//�������
	private int sysdocno;   		//�ý��۹�����ȣ
	private String doctitle;     	//����
	private String submitstate;		//�������
	private String deliverydt;     	//�������
	private String coldeptnm;     	//���պμ���Ī
	private String chrgusrnm;     	//���մ���ڸ�
	private String enddt;     		//�����Ͻ�
	private String remaintime;     	//�����ð�
	private String inputperdeli;	//�Է�/���
	private String dept_member_id;	//�μ�������(���������� �α����� ������ ���� ���� ��� 1��)
	
	public DeliveryCompBean() {
		this.seqno 			= 0;
		this.sysdocno 		= 0;
		this.doctitle 		= "";
		this.submitstate	= "";
		this.deliverydt 	= "";
		this.coldeptnm 		= "";
		this.chrgusrnm 		= "";
		this.enddt 			= "";
		this.remaintime 	= "";
		this.inputperdeli 	= "";
		this.dept_member_id	= "";
	}

	public String getDept_member_id() {
		return dept_member_id;
	}

	public void setDept_member_id(String dept_member_id) {
		this.dept_member_id = dept_member_id;
	}

	/**
	 * @return the chrgusrnm
	 */
	public String getChrgusrnm() {
		return chrgusrnm;
	}

	/**
	 * @param chrgusrnm the chrgusrnm to set
	 */
	public void setChrgusrnm(String chrgusrnm) {
		this.chrgusrnm = chrgusrnm;
	}

	/**
	 * @return the coldeptnm
	 */
	public String getColdeptnm() {
		return coldeptnm;
	}

	/**
	 * @param coldeptnm the coldeptnm to set
	 */
	public void setColdeptnm(String coldeptnm) {
		this.coldeptnm = coldeptnm;
	}

	/**
	 * @return the deliverydt
	 */
	public String getDeliverydt() {
		return deliverydt;
	}

	/**
	 * @param deliverydt the deliverydt to set
	 */
	public void setDeliverydt(String deliverydt) {
		this.deliverydt = deliverydt;
	}

	/**
	 * @return the submitstate
	 */
	public String getSubmitstate() {
		return submitstate;
	}

	/**
	 * @param submitstate the submitstate to set
	 */
	public void setSubmitstate(String submitstate) {
		this.submitstate = submitstate;
	}

	/**
	 * @return the doctitle
	 */
	public String getDoctitle() {
		return doctitle;
	}

	/**
	 * @param doctitle the doctitle to set
	 */
	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}

	/**
	 * @return the enddt
	 */
	public String getEnddt() {
		return enddt;
	}

	/**
	 * @param enddt the enddt to set
	 */
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}

	/**
	 * @return the inputperdeli
	 */
	public String getInputperdeli() {
		return inputperdeli;
	}

	/**
	 * @param inputperdeli the inputperdeli to set
	 */
	public void setInputperdeli(String inputperdeli) {
		this.inputperdeli = inputperdeli;
	}

	/**
	 * @return the remaintime
	 */
	public String getRemaintime() {
		return remaintime;
	}

	/**
	 * @param remaintime the remaintime to set
	 */
	public void setRemaintime(String remaintime) {
		this.remaintime = remaintime;
	}

	/**
	 * @return the seqno
	 */
	public int getSeqno() {
		return seqno;
	}

	/**
	 * @param seqno the seqno to set
	 */
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	/**
	 * @return the sysdocno
	 */
	public int getSysdocno() {
		return sysdocno;
	}

	/**
	 * @param sysdocno the sysdocno to set
	 */
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
