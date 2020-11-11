/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력하기 bean
 * 설명:
 */
package nexti.ejms.inputing.model;

public class InputingBean {

	private int seqno;				//연번
	private int formseq;			//양식번호
	private String formkind;		//양식종류
	private int sysdocno;			//시스템문서번호
	private String doctitle;		//문서제목
	private String deliverydt;		//접수날짜
	private String coldeptnm;    	//취합부서명칭
	private String chrgusrnm;    	//취합담당자명
	private String enddt;        	//마감일시
	private String remaintime;   	//남은시간
	private String inputusrid;		//입력담당자ID
	
	private int gubun;				//진행상태
	
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
