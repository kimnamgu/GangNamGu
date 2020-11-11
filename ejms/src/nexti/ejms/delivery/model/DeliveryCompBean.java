/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부완료 bean
 * 설명:
 */
package nexti.ejms.delivery.model;

public class DeliveryCompBean {

	private int seqno;      		//연번
	private int formseq;			//양식번호
	private String formkind;		//양식종류
	private int sysdocno;   		//시스템문서번호
	private String doctitle;     	//제목
	private String submitstate;		//진행상태
	private String deliverydt;     	//배부일자
	private String coldeptnm;     	//취합부서명칭
	private String chrgusrnm;     	//취합담당자명
	private String enddt;     		//마감일시
	private String remaintime;     	//남은시간
	private String inputperdeli;	//입력/배부
	private String dept_member_id;	//부서구성원(마지막으로 로그인한 서열이 가장 낮은 사람 1명)
	
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
