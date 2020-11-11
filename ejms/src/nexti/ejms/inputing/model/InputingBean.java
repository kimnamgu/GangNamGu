/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է��ϱ� bean
 * ����:
 */
package nexti.ejms.inputing.model;

public class InputingBean {

	private int seqno;				//����
	private int formseq;			//��Ĺ�ȣ
	private String formkind;		//�������
	private int sysdocno;			//�ý��۹�����ȣ
	private String doctitle;		//��������
	private String deliverydt;		//������¥
	private String coldeptnm;    	//���պμ���Ī
	private String chrgusrnm;    	//���մ���ڸ�
	private String enddt;        	//�����Ͻ�
	private String remaintime;   	//�����ð�
	private String inputusrid;		//�Է´����ID
	
	private int gubun;				//�������
	
	public InputingBean() {
		this.seqno			= 0;
		this.sysdocno		= 0;
		this.doctitle		= "";
		this.deliverydt		= "";
		this.coldeptnm		= "";
		this.chrgusrnm		= "";
		this.enddt			= "";
		this.remaintime		= "";
		this.inputusrid		= "";
		this.gubun			= 0;
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

	public String getDeliverydt() {
		return deliverydt;
	}

	public void setDeliverydt(String deliverydt) {
		this.deliverydt = deliverydt;
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

	public int getGubun() {
		return gubun;
	}

	public void setGubun(int gubun) {
		this.gubun = gubun;
	}
}
