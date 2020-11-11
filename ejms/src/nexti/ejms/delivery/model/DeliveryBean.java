/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 bean
 * 설명:
 */
package nexti.ejms.delivery.model;

public class DeliveryBean {
	private int seqno 			= 0;            //연번
	private int formseq			= 0;			//양식번호
	private String formkind		= "";			//양식종류
	private int sysdocno 		= 0;         	//시스템문서번호		
	private String doctitle 	= "";     		//제목
	private String deliverydt 	= "";   		//접수일자
	private String coldeptnm 	= "";    		//취합부서명칭
	private String chrgusrnm 	= "";    		//취합담당자명
	private String enddt 		= "";        	//마감일시
	private String remaintime 	= "";   		//남은시간
	private String dept_member_id = "";			//부서구성원(마지막으로 로그인한 서열이 가장 낮은 사람 1명)
	
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
