/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 자료이관 actionform
 * 설명:
 */
package nexti.ejms.dataTransfer.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DataTransferForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int		historyno	= 0;	//이력번호
	private int		seq			= 0;	//처리번호
	private	String	tablename	= "";	//관련테이블
	private	String	columnname	= "";	//관련컬럼
	private	String	oldvalue	= "";	//변경전값
	private	String	cause		= "";	//이관사유
	private	String	crtdt		= "";	//이관일시
	private	String	crtusrid	= "";	//이관자ID
	
	private String 	sch_orggbn	= "";	//검색조건:사용자 구분 (001:광역시본청, 002:직속기관, 003:사업소, 004:시의회, 005:시/구/군, 006: 기타)
	private String	sch_deptid	= "";	//검색조건:부서
	private String	sch_userid	= "";	//검색조건:사용자
	private String	orgdept		= "";	//이관자료부서(셀렉트박스)
	private String	orguser		= "";	//이관자료부서(셀렉트박스)
	private String	tgtdept		= "";	//이관대상부서(셀렉트박스)
	private String	tgtuser		= "";	//이관대상부서(셀렉트박스)
	private List	datalist	= null;	//이관자료목록
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
	
	public String getSch_orggbn() {
		return sch_orggbn;
	}
	public void setSch_orggbn(String sch_orggbn) {
		this.sch_orggbn = sch_orggbn;
	}
	public String getOrgdept() {
		return orgdept;
	}
	public void setOrgdept(String orgdept) {
		this.orgdept = orgdept;
	}
	public String getOrguser() {
		return orguser;
	}
	public void setOrguser(String orguser) {
		this.orguser = orguser;
	}
	public String getTgtuser() {
		return tgtuser;
	}
	public void setTgtuser(String tgtuser) {
		this.tgtuser = tgtuser;
	}
	public String getTgtdept() {
		return tgtdept;
	}
	public void setTgtdept(String tgtdeptid) {
		this.tgtdept = tgtdeptid;
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
	public String[] getGbn() {
		return gbn;
	}
	public void setGbn(String[] type) {
		this.gbn = type;
	}
	public List getDatalist() {
		return datalist;
	}
	public void setDatalist(List dtlist) {
		this.datalist = dtlist;
	}
	public String getSch_deptid() {
		return sch_deptid;
	}
	public void setSch_deptid(String sch_deptid) {
		this.sch_deptid = sch_deptid;
	}
	public String getSch_userid() {
		return sch_userid;
	}
	public void setSch_userid(String sch_userid) {
		this.sch_userid = sch_userid;
	}
	public String[] getTgtuserid() {
		return tgtuserid;
	}
	public void setTgtuserid(String[] tgtuserid) {
		this.tgtuserid = tgtuserid;
	}
	public String[] getGbnid() {
		return gbnid;
	}
	public void setGbnid(String[] value) {
		this.gbnid = value;
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
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {return null;}
	public void reset(ActionMapping mapping, HttpServletRequest request) {}
}