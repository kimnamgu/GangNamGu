/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서 actionform
 * 설명:
 */
package nexti.ejms.colldoc.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class ColldocForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int		seqno		= 0;		//연번 
	private String	searchvalue	= "";		//검색어
	private int		page		= 1;		//페이지
	private List	listcolldoc	= null;		//취합문서리스트
	private String 	selyear		= "";		//연도선택
	private String 	initentry	= "first";	//초기진입여부
	private String[] listdelete	= null;		//삭제문서 시스템문서번호
	
	private int		type		= 0;	//작성형태
	private int		formcount	= 0;	//양식개수
	private int		formseq		= 0;	//양식일련번호
	private List	listcolldocform	= null;	//취합양식리스트
	
	private int		mode		= 1;	//새로작성(1), 수정새로저장(2), 수정덮어쓰기(3)
	private String	user_id		= "";	//사용자 아이디
	private String	user_name	= "";	//사용자 이름
	private String	dept_code	= "";	//사용자 부서코드
	private String	dept_name	= "";	//사용자 부서명
	private String	d_tel		= "";	//사용자 부서전화번호
		
	private int		sysdocno	= 0;	//시스템문서번호
	private String	docno		= "";	//문서번호
	private String	doctitle	= "";	//문서제목
	private String	docgbn		= "";	//문서종류
	private String	basicdate	= "";	//자료기준일자
	private String	submitdate	= "";	//기안일자
	private String	basis		= "";	//관련근거
	private String	summary		= "";	//취합개요
	private String	enddt		= "";	//마감일시
	private String	enddt_date	= "";	//마감일
	private String	enddt_hour	= "";	//마감시
	private String	enddt_min	= "";	//마감분
	private String	endcomment	= "";	//마감알림말
	private String	sancrule	= "";	//제출자료전결규정
	private String	docstate	= "";	//문서상태
	private String	deliverydt	= "";	//배부일시
	private String	tgtdeptnm	= "";	//제출부서명칭또는그룹명칭
	private String	coldeptcd	= "";	//취합부서코드
	private String	coldeptnm	= "";	//취합부서명
	private int		chrgunitcd	= 0;	//취합담당단위코드
	private String	chrgunitnm	= "";	//취합담당단위명
	private String	chrgusrcd	= "";	//취합담당자코드
	private String	chrgusrnm	= "";	//취합담당자명
	private String	opendt		= "";	//공개일자
	private String	searchkey	= "";	//검색키워드
	private String	delyn		= "";	//최근목록에서삭제여부
	private String	openinput	= "";	//공개입력(타부서자료보기)
	private String	crtdt		= "";	//생성일시
	private String	crtusrid	= "";	//생성자코드
	private String	uptdt		= "";	//수정일시
	private String	uptusrid	= "";	//수정자코드
	private String	sancusrnm1	= "";	//취합부서 검토자 이름
	private String	sancusrnm2	= "";	//취합부서 승인자 이름
	private FormFile attachFile	= null;	//첨부파일
	private String attachFileYN	= "";	//첨부파일삭제
	private int		fileseq		= 0;	//첨부파일번호
	private String	filenm		= "";	//첨부파일명
	private String	originfilenm = "";	//원본파일명

	private String 	sch_old_deptcd	= "";			//부서명조회조건
	private String  sch_deptcd		= "";			//부서명조회조건
	private String 	sch_deptnm		= "";			//부서명조회조건
	private String  sch_old_userid	= "";			//담당자조회조건
	private String  sch_userid		= "";			//담당자조회조건
	private String 	sch_usernm		= "";			//담당자조회조건
	
	public String getOpeninput() {
		return openinput;
	}
	public void setOpeninput(String openinput) {
		this.openinput = openinput;
	}
	public String getSch_deptcd() {
		return sch_deptcd;
	}
	public void setSch_deptcd(String sch_deptcd) {
		this.sch_deptcd = sch_deptcd;
	}

	public String getSch_deptnm() {
		return sch_deptnm;
	}

	public void setSch_deptnm(String sch_deptnm) {
		this.sch_deptnm = sch_deptnm;
	}

	public String getSch_old_deptcd() {
		return sch_old_deptcd;
	}

	public void setSch_old_deptcd(String sch_old_deptcd) {
		this.sch_old_deptcd = sch_old_deptcd;
	}

	public String getSch_old_userid() {
		return sch_old_userid;
	}

	public void setSch_old_userid(String sch_old_userid) {
		this.sch_old_userid = sch_old_userid;
	}

	public String getSch_userid() {
		return sch_userid;
	}

	public void setSch_userid(String sch_userid) {
		this.sch_userid = sch_userid;
	}

	public String getSch_usernm() {
		return sch_usernm;
	}

	public void setSch_usernm(String sch_usernm) {
		this.sch_usernm = sch_usernm;
	}

	public String getAttachFileYN() {
		return attachFileYN;
	}

	public void setAttachFileYN(String attachFileYN) {
		this.attachFileYN = attachFileYN;
	}

	public int getFileseq() {
		return fileseq;
	}

	public void setFileseq(int fileseq) {
		this.fileseq = fileseq;
	}

	public String getFilenm() {
		return filenm;
	}

	public void setFilenm(String filenm) {
		this.filenm = filenm;
	}

	public String getOriginfilenm() {
		return originfilenm;
	}

	public void setOriginfilenm(String originfilenm) {
		this.originfilenm = originfilenm;
	}

	public FormFile getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(FormFile attachFile) {
		this.attachFile = attachFile;
	}

	public String getBasicdate() {
		return basicdate;
	}

	public void setBasicdate(String basicdate) {
		this.basicdate = basicdate;
	}

	public String getBasis() {
		return basis;
	}

	public void setBasis(String basis) {
		this.basis = basis;
	}

	public String getChrgunitnm() {
		return chrgunitnm;
	}

	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}

	public String getChrgusrnm() {
		return chrgusrnm;
	}

	public void setChrgusrnm(String chrgusrnm) {
		this.chrgusrnm = chrgusrnm;
	}

	public String getColdeptcd() {
		return coldeptcd;
	}

	public void setColdeptcd(String coldeptcd) {
		this.coldeptcd = coldeptcd;
	}

	public String getColdeptnm() {
		return coldeptnm;
	}

	public void setColdeptnm(String coldeptnm) {
		this.coldeptnm = coldeptnm;
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

	public String getDeliverydt() {
		return deliverydt;
	}

	public void setDeliverydt(String deliverydt) {
		this.deliverydt = deliverydt;
	}

	public String getDelyn() {
		return delyn;
	}

	public void setDelyn(String delyn) {
		this.delyn = delyn;
	}

	public String getDocgbn() {
		return docgbn;
	}

	public void setDocgbn(String docgbn) {
		this.docgbn = docgbn;
	}

	public String getDocno() {
		return docno;
	}

	public void setDocno(String docno) {
		this.docno = docno;
	}

	public String getDocstate() {
		return docstate;
	}

	public void setDocstate(String docstate) {
		this.docstate = docstate;
	}

	public String getDoctitle() {
		return doctitle;
	}

	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}

	public String getEndcomment() {
		return endcomment;
	}

	public void setEndcomment(String endcomment) {
		this.endcomment = endcomment;
	}

	public String getEnddt() {
		return enddt;
	}

	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}

	public List getListcolldoc() {
		return listcolldoc;
	}

	public void setListcolldoc(List listcolldoc) {
		this.listcolldoc = listcolldoc;
	}

	public String[] getListdelete() {
		return listdelete;
	}

	public void setListdelete(String[] listdelete) {
		this.listdelete = listdelete;
	}

	public String getOpendt() {
		return opendt;
	}

	public void setOpendt(String opendt) {
		this.opendt = opendt;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSancrule() {
		return sancrule;
	}

	public void setSancrule(String sancrule) {
		this.sancrule = sancrule;
	}

	public String getSancusrnm1() {
		return sancusrnm1;
	}

	public void setSancusrnm1(String sancusrnm1) {
		this.sancusrnm1 = sancusrnm1;
	}

	public String getSancusrnm2() {
		return sancusrnm2;
	}

	public void setSancusrnm2(String sancusrnm2) {
		this.sancusrnm2 = sancusrnm2;
	}

	public String getSearchkey() {
		return searchkey;
	}

	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}

	public String getSearchvalue() {
		return searchvalue;
	}

	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
	}

	public String getSubmitdate() {
		return submitdate;
	}

	public void setSubmitdate(String submitdate) {
		this.submitdate = submitdate;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getSysdocno() {
		return sysdocno;
	}

	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}

	public String getTgtdeptnm() {
		return tgtdeptnm;
	}

	public void setTgtdeptnm(String tgtdeptnm) {
		this.tgtdeptnm = tgtdeptnm;
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

	public int getSeqno() {
		return seqno;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	public String getEnddt_date() {
		return enddt_date;
	}

	public void setEnddt_date(String enddt_date) {
		this.enddt_date = enddt_date;
	}

	public String getEnddt_hour() {
		return enddt_hour;
	}

	public void setEnddt_hour(String enddt_hour) {
		this.enddt_hour = enddt_hour;
	}

	public String getEnddt_min() {
		return enddt_min;
	}

	public void setEnddt_min(String enddt_min) {
		this.enddt_min = enddt_min;
	}

	public int getChrgunitcd() {
		return chrgunitcd;
	}

	public void setChrgunitcd(int chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
	}

	public String getD_tel() {
		return d_tel;
	}

	public void setD_tel(String d_tel) {
		this.d_tel = d_tel;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getFormcount() {
		return formcount;
	}

	public void setFormcount(int formcount) {
		this.formcount = formcount;
	}

	public List getListcolldocform() {
		return listcolldocform;
	}

	public void setListcolldocform(List listcolldocform) {
		this.listcolldocform = listcolldocform;
	}

	public int getFormseq() {
		return formseq;
	}

	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}

	public String getInitentry() {
		return initentry;
	}

	public void setInitentry(String initentry) {
		this.initentry = initentry;
	}

	public String getSelyear() {
		return selyear;
	}

	public void setSelyear(String selyear) {
		this.selyear = selyear;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getChrgusrcd() {
		return chrgusrcd;
	}

	public void setChrgusrcd(String chrgusrcd) {
		this.chrgusrcd = chrgusrcd;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {return null;}
	public void reset(ActionMapping mapping, HttpServletRequest request) {}
}