/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배포목록관리 bean
 * 설명:
 */
package nexti.ejms.group.model;

public class GroupBean {
	private int grplistcd	    = 0;      	//배포목록코드
	private String grplistnm    = "";     	//배포목록명칭
	private int seq          	= 0;      	//일련번호
	private String code 		= "";		//대상코드
	private String name 		= "";     	//대상명칭
	private String displayName	= "";		//대상전체명칭
	private String codegbn		= "";		//대상구분
	private int ord          	= 0;      	//정렬순서
	private String crtdt     	= "";     	//작성일자
	private String crtusrid  	= "";     	//작성자
	private String uptusrid		= "";		//수정자
	private String crtusrgbn	= "";		//작성자구분
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getCodegbn() {
		return codegbn;
	}
	public void setCodegbn(String codegbn) {
		this.codegbn = codegbn;
	}
	public String getCrtusrgbn() {
		return crtusrgbn;
	}
	public void setCrtusrgbn(String crtusrgbn) {
		this.crtusrgbn = crtusrgbn;
	}
	public String getUptusrid() {
		return uptusrid;
	}
	public void setUptusrid(String uptusrid) {
		this.uptusrid = uptusrid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String deptcd) {
		this.code = deptcd;
	}
	public String getName() {
		return name;
	}
	public void setName(String deptnm) {
		this.name = deptnm;
	}
	public int getGrplistcd() {
		return grplistcd;
	}
	public void setGrplistcd(int grplistcd) {
		this.grplistcd = grplistcd;
	}
	public String getGrplistnm() {
		return grplistnm;
	}
	public void setGrplistnm(String grplistnm) {
		this.grplistnm = grplistnm;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
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
}
