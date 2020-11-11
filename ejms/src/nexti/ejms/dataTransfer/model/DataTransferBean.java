/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 자료이관 bean
 * 설명:
 */
package nexti.ejms.dataTransfer.model;

public class DataTransferBean {
	
	private int		historyno	= 0;	//이력번호
	private int		seq			= 0;	//처리번호
	private	String	tablename	= "";	//관련테이블
	private	String	columnname	= "";	//관련컬럼
	private	String	oldvalue	= "";	//변경전값
	private	String	cause		= "";	//이관사유
	private	String	crtdt		= "";	//이관일시
	private	String	crtusrid	= "";	//이관자ID
	
	private String[] gbn		= null;	//변경자료구분
	private String[] gbnid		= null;	//변경데이터
	private String[] tgtuserid	= null;	//이관대상사용자ID
	
	private int		no			= 0;	//자료번호
	private String	type		= "";	//자료종류
	private String	title		= "";	//제목
	private String	deptid		= "";	//부서ID
	private String	deptnm		= "";	//부서명
	private String	userid		= "";	//사용자ID
	private String	usernm		= "";	//사용자명
	private String	existdeptid = "";	//부서존재여부
	private String	existuserid = "";	//사용자존재여부
	private String	uptdt	= "";		//최종수정일자
	
	private String	uptusrid	= "";	//최종수정자ID
	private int		chrgunitid	= 0;	//담당단위ID
	private String	chrgunitnm	= "";	//담당단위명
	private	String	depttel		= "";	//부서전화번호
	
	public String[] getGbn() {
		return gbn;
	}
	public void setGbn(String[] gbn) {
		this.gbn = gbn;
	}
	public String[] getGbnid() {
		return gbnid;
	}
	public void setGbnid(String[] gbnid) {
		this.gbnid = gbnid;
	}
	public String[] getTgtuserid() {
		return tgtuserid;
	}
	public void setTgtuserid(String[] tgtuserid) {
		this.tgtuserid = tgtuserid;
	}
	public int getChrgunitid() {
		return chrgunitid;
	}
	public void setChrgunitid(int chrgunitid) {
		this.chrgunitid = chrgunitid;
	}
	public String getChrgunitnm() {
		return chrgunitnm;
	}
	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}
	public String getDepttel() {
		return depttel;
	}
	public void setDepttel(String depttel) {
		this.depttel = depttel;
	}
	public String getUptusrid() {
		return uptusrid;
	}
	public void setUptusrid(String uptusrid) {
		this.uptusrid = uptusrid;
	}
	public String getExistdeptid() {
		return existdeptid;
	}
	public void setExistdeptid(String existdeptid) {
		this.existdeptid = existdeptid;
	}
	public String getExistuserid() {
		return existuserid;
	}
	public void setExistuserid(String existuserid) {
		this.existuserid = existuserid;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDeptnm() {
		return deptnm;
	}
	public void setDeptnm(String deptnm) {
		this.deptnm = deptnm;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUptdt() {
		return uptdt;
	}
	public void setUptdt(String uptdt) {
		this.uptdt = uptdt;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsernm() {
		return usernm;
	}
	public void setUsernm(String usernm) {
		this.usernm = usernm;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
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
	public int getHistoryno() {
		return historyno;
	}
	public void setHistoryno(int historyno) {
		this.historyno = historyno;
	}
	public String getOldvalue() {
		return oldvalue;
	}
	public void setOldvalue(String oldvalue) {
		this.oldvalue = oldvalue;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
}
