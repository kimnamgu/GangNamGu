/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 사용자정보 확장 bean
 * 설명:
 */
package nexti.ejms.user.model;

public class UserExtBean {
	
	private String user_id = "";        //사용자 ID
	private String passwd = "";         //비밀번호
	private String mgryn = "";			//관리자여부
	private String chrgunitcd = "";		//담당단위코드
	private String bigo = "";           //비고
	private String lstlogindt = "";		//최근로그인일시
	private String crtdt     = "";      //생성일자
	private String crtusr    = "";      //생성자
	private String uptdt   = "";       	//수정일자
	private String uptusr  = "";       	//수정자
	
	public String getBigo() {
		return bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
	public String getChrgunitcd() {
		return chrgunitcd;
	}
	public void setChrgunitcd(String chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
	}
	public String getCrtdt() {
		return crtdt;
	}
	public void setCrtdt(String crtdt) {
		this.crtdt = crtdt;
	}
	public String getCrtusr() {
		return crtusr;
	}
	public void setCrtusr(String crtusr) {
		this.crtusr = crtusr;
	}
	public String getLstlogindt() {
		return lstlogindt;
	}
	public void setLstlogindt(String lstlogindt) {
		this.lstlogindt = lstlogindt;
	}
	public String getMgryn() {
		return mgryn;
	}
	public void setMgryn(String mgryn) {
		this.mgryn = mgryn;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getUptdt() {
		return uptdt;
	}
	public void setUptdt(String uptdt) {
		this.uptdt = uptdt;
	}
	public String getUptusr() {
		return uptusr;
	}
	public void setUptusr(String uptusr) {
		this.uptusr = uptusr;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
}
