/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 반송처리 bean
 * 설명:
 */
package nexti.ejms.delivery.model;

public class DeliveryRetDocBean {
	private int sysdocno;			//시스템문서번호
	private String tgtdeptcd;		//제출부서코드
	private String submitstate;		//제출상태
	private String returncomment;	//반송사유
	
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
