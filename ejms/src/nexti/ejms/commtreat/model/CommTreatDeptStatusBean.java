/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통처리현황 제출상태 bean
 * 설명:
 */
package nexti.ejms.commtreat.model;

public class CommTreatDeptStatusBean {
	
	private String  tgtdeptcd		= "";   //부서코드
	private String  tgtdeptnm		= "";   //부서명
	private String	tgtdepttel		= "";	//부서전화번호
	private String	submityn		= "";	//제출여부
	private String	submitdt		= "";	//제출일시
	private String	submitstate		= "";	//진행상태코드
	private String	submitstatenm	= "";	//진행상태명
	private String	returncomment	= "";	//반송사유
	private String	modifyyn		= "0";	//수정권한
	
	public String getTgtdepttel() {
		return tgtdepttel;
	}
	public void setTgtdepttel(String tgtdepttel) {
		this.tgtdepttel = tgtdepttel;
	}
	public String getModifyyn() {
		return modifyyn;
	}
	public void setModifyyn(String modifyyn) {
		if (modifyyn==null || "".equals(modifyyn)) {
			modifyyn = "0";
		}
		this.modifyyn = modifyyn;
	}
	public String getReturncomment() {
		return returncomment;
	}
	public void setReturncomment(String returncomment) {
		this.returncomment = returncomment;
	}
	public String getSubmitdt() {
		return submitdt;
	}
	public void setSubmitdt(String submitdt) {
		this.submitdt = submitdt;
	}
	public String getSubmitstate() {
		return submitstate;
	}
	public void setSubmitstate(String submitstate) {
		this.submitstate = submitstate;
	}
	public String getSubmitstatenm() {
		return submitstatenm;
	}
	public void setSubmitstatenm(String submitstatenm) {
		this.submitstatenm = submitstatenm;
	}
	public String getSubmityn() {
		return submityn;
	}
	public void setSubmityn(String submityn) {
		this.submityn = submityn;
	}
	public String getTgtdeptcd() {
		return tgtdeptcd;
	}
	public void setTgtdeptcd(String tgtdeptcd) {
		this.tgtdeptcd = tgtdeptcd;
	}
	public String getTgtdeptnm() {
		return tgtdeptnm;
	}
	public void setTgtdeptnm(String tgtdeptnm) {
		this.tgtdeptnm = tgtdeptnm;
	}

}