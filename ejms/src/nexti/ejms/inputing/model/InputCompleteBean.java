/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է¿Ϸ� bean
 * ����:
 */
package nexti.ejms.inputing.model;

public class InputCompleteBean {

	private int seqno;				//����
	private int formseq;			//��Ĺ�ȣ
	private String formkind;		//�������
	private int sysdocno;			//�ý��۹�����ȣ
	private String doctitle;		//��������
	private String submistate;		//�������
	private String inusrenddt;		//�Է¿Ϸ��Ͻ�
	private String inputendreason;	//�Է¿Ϸᱸ��
	private String coldeptnm;    	//���պμ���Ī
	private String chrgusrnm;    	//���մ���ڸ�
	private String deliverydt;     	//�������
	private String inputusrid;		//�Է´����ID
	
	public InputCompleteBean() {
		this.seqno			= 0;
		this.sysdocno		= 0;
		this.doctitle		= "";
		this.submistate		= "";
		this.inusrenddt		= "";
		this.inputendreason = "";
		this.coldeptnm		= "";
		this.chrgusrnm		= "";
		this.deliverydt		= "";
		this.inputusrid		= "";
	}

	public String getInputusrid() {
		return inputusrid;
	}

	public void setInputusrid(String inputusrid) {
		this.inputusrid = inputusrid;
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
	 * @return the inputendreason
	 */
	public String getInputendreason() {
		return inputendreason;
	}

	/**
	 * @param inputendreason the inputendreason to set
	 */
	public void setInputendreason(String inputendreason) {
		this.inputendreason = inputendreason;
	}

	/**
	 * @return the inusrenddt
	 */
	public String getInusrenddt() {
		return inusrenddt;
	}

	/**
	 * @param inusrenddt the inusrenddt to set
	 */
	public void setInusrenddt(String inusrenddt) {
		this.inusrenddt = inusrenddt;
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
	 * @return the submistate
	 */
	public String getSubmistate() {
		return submistate;
	}

	/**
	 * @param submistate the submistate to set
	 */
	public void setSubmistate(String submistate) {
		this.submistate = submistate;
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
