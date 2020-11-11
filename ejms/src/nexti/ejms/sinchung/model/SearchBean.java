/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 검색조건 bean
 * 설명:
 */
package nexti.ejms.sinchung.model;

public class SearchBean {
	private int reqformno    = 0;    //신청 양식번호
	private String deptid    = "";   //부서코드
	private String userid    = "";   //사용자코드
	private String title     = "";   //제목
	private String presentnm = ""; 	 //신청자
	private String presentid = ""; 	 //신청자ID
	private String syear     = "";   //작성년도
	private String procFL    = "";   //처리여부(0:미처리, 1:전체)
	private String gbn       = "";   //구분
	private int start_idx    = 0;    //조회 시작위치
	private int end_idx      = 0;    //조회 끝 위치
	private String	strdt	 = "";	//조회 시작일
	private String	enddt	 = "";	//조회 종료일
	private boolean unlimited	= true;	//제한없음
	
	public String getPresentid() {
		return presentid;
	}
	public void setPresentid(String presentid) {
		this.presentid = presentid;
	}
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getStrdt() {
		return strdt;
	}
	public void setStrdt(String strdt) {
		this.strdt = strdt;
	}
	public boolean isUnlimited() {
		return unlimited;
	}
	public void setUnlimited(boolean unlimited) {
		this.unlimited = unlimited;
	}
	public int getReqformno() {
		return reqformno;
	}
	public void setReqformno(int reqformno) {
		this.reqformno = reqformno;
	}
	public String getPresentnm() {
		return presentnm;
	}
	public void setPresentnm(String presentnm) {
		this.presentnm = presentnm;
	}
	public String getSyear() {
		return syear;
	}
	public void setSyear(String syear) {
		this.syear = syear;
	}
	public String getGbn() {
		return gbn;
	}
	public void setGbn(String gbn) {
		this.gbn = gbn;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getEnd_idx() {
		return end_idx;
	}
	public void setEnd_idx(int end_idx) {
		this.end_idx = end_idx;
	}
	public String getProcFL() {
		return procFL;
	}
	public void setProcFL(String procFL) {
		this.procFL = procFL;
	}
	public int getStart_idx() {
		return start_idx;
	}
	public void setStart_idx(int start_idx) {
		this.start_idx = start_idx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
}
