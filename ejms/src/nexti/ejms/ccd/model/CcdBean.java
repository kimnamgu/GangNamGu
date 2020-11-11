/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 코드관리 bean
 * 설명:
 */
package nexti.ejms.ccd.model;

public class CcdBean {
	private String ccdcd     = "";
	private String ccdsubcd  = "";
	private String ccdname 	 = "";
	private String ccddesc   = "";
	private String bigo      = "";
	private String crtdt     = "";
	private String crtusrid  = "";
	private String uptdt     = "";
	private String uptusrid  = "";
	
	public String getBigo() {
		return bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
	public String getCcdcd() {
		return ccdcd;
	}
	public void setCcdcd(String ccdcd) {
		this.ccdcd = ccdcd;
	}
	public String getCcddesc() {
		return ccddesc;
	}
	public void setCcddesc(String ccddesc) {
		this.ccddesc = ccddesc;
	}
	public String getCcdname() {
		return ccdname;
	}
	public void setCcdname(String ccdname) {
		this.ccdname = ccdname;
	}
	public String getCcdsubcd() {
		return ccdsubcd;
	}
	public void setCcdsubcd(String ccdsubcd) {
		this.ccdsubcd = ccdsubcd;
	}
	public String getCrtdt() {
		return crtdt;
	}
	public void setCrtdt(String crtdt) {
		this.crtdt = crtdt;
	}
	public String getCrtusrid() {
		return crtusrid;
	}
	public void setCrtusrid(String crtusrid) {
		this.crtusrid = crtusrid;
	}
	public String getUptdt() {
		return uptdt;
	}
	public void setUptdt(String uptdt) {
		this.uptdt = uptdt;
	}
	public String getUptusrid() {
		return uptusrid;
	}
	public void setUptusrid(String uptusrid) {
		this.uptusrid = uptusrid;
	}	
}
