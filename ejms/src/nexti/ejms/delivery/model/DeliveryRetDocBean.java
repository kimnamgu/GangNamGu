/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ϱ� �ݼ�ó�� bean
 * ����:
 */
package nexti.ejms.delivery.model;

public class DeliveryRetDocBean {
	private int sysdocno;			//�ý��۹�����ȣ
	private String tgtdeptcd;		//����μ��ڵ�
	private String submitstate;		//�������
	private String returncomment;	//�ݼۻ���
	
	public DeliveryRetDocBean() {
		this.sysdocno 		= 0;
		this.tgtdeptcd 		= "";
		this.submitstate 	= "";
		this.returncomment 	= "";
	}

	/**
	 * @return the returncomment
	 */
	public String getReturncomment() {
		return returncomment;
	}

	/**
	 * @param returncomment the returncomment to set
	 */
	public void setReturncomment(String returncomment) {
		this.returncomment = returncomment;
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

	/**
	 * @return the tgtdeptcd
	 */
	public String getTgtdeptcd() {
		return tgtdeptcd;
	}

	/**
	 * @param tgtdeptcd the tgtdeptcd to set
	 */
	public void setTgtdeptcd(String tgtdeptcd) {
		this.tgtdeptcd = tgtdeptcd;
	}
}
