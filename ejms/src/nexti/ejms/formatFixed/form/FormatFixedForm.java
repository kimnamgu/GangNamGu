/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 고정양식형 actionform
 * 설명:
 */
package nexti.ejms.formatFixed.form;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class FormatFixedForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private List	formdatalist	= null;
	private int 	datacnt			= 0;    //자료입력건수

	private int		seqno			= 0;	//연번
	private int		type			= 0;	//작성방법
	
	private int		page			= 0;	//페이지
	private String	deptcd			= "";	//부서코드
	private String	makedt			= "";	//작성년도
	private String	title			= "";	//제목
	private String	makeusr			= "";	//작성자이름
	
	private int		sysdocno		= 0;	//시스템문서번호
	private int		formseq			= 0;	//양식일련번호
	private int		commformseq		= 0;	//공통양식일련번호
	private int		usedsysdocno	= 0;	//사용했던양식시스템문서번호
	private int		usedformseq		= 0;	//사용했던양식일련번호
	private String	formtitle		= "";	//양식제목
	private String	formkind		= "";	//양식종류
	private String	formkindname	= "";	//양식종류이름
	private String	formcomment		= "";	//양식개요
	private String	formhtml		= "";	//양식원본
	private String	formheaderhtml	= "";	//양식구조(헤더)
	private String	formbodyhtml	= "";	//양식구조(바디)
	private String	formbodyhtml_ext= "";	//양식구조(바디) => 양식작성시 화면 표시용
	private String	formtailhtml	= "";	//양식구조(테일
	private int		tblcols			= 0;	//테이블 열개수
	private int		tblrows			= 0;	//테이블 행개수
	private int		ord				= 0;	//정렬순서
	private String	crtdt			= "";	//생성일자
	private String	crtusrid		= "";	//생성자코드
	private String	uptdt			= "";	//수정일자
	private String	uptusrid		= "";	//수정자코드
	private List	listform		= null;	//양식 목록
	private String	attoption1		= "";	//속성목록OPTION태그(내목록)
	private String	attoption2		= "";	//속성목록OPTION태그(공통목록)
	private String	openinput		= "";	//공개입력(타부서자료보기)
	
	private String  sch_tgtdeptcd   = "";   //조회조건부서코드
	
	private String		sch_deptcd1					= "";	//부서코드1
	private String		sch_deptcd2					= "";	//부서코드2
	private String		sch_deptcd3					= "";	//부서코드3
	private String		sch_deptcd4					= "";	//부서코드4
	private String		sch_deptcd5					= "";	//부서코드5
	private String		sch_deptcd6					= "";	//부서코드6
	private String		sch_chrgunitcd				= "";	//담당단위코드
	private String		sch_inputusrid				= "";	//입력자코드
	private Collection	sch_deptcd1_collection		= null;	//부서목록1
	private Collection	sch_deptcd2_collection		= null;	//부서목록2
	private Collection	sch_deptcd3_collection		= null;	//부서목록3
	private Collection	sch_deptcd4_collection		= null;	//부서목록4
	private Collection	sch_deptcd5_collection		= null;	//부서목록5
	private Collection	sch_deptcd6_collection		= null;	//부서목록6
	private Collection	sch_chrgunitcd_collection	= null;	//담당단위목록
	private Collection	sch_inputusrid_collection	= null;	//입력자목록
	
	private boolean	totalState						= false; //총계보기
	private boolean subtotalState					= false; //소계보기
	private boolean	totalShowStringState			= false; //총계보기
	private boolean subtotalShowStringState			= false; //소계보기
	
	private boolean includeNotSubmitData			= false; //미제출자료 포함
	
	public String getOpeninput() {
		return openinput;
	}
	public void setOpeninput(String openinput) {
		this.openinput = openinput;
	}
	public boolean isIncludeNotSubmitData() {
		return includeNotSubmitData;
	}
	public void setIncludeNotSubmitData(boolean includeNotSubmitData) {
		this.includeNotSubmitData = includeNotSubmitData;
	}
	public String getSch_chrgunitcd() {
		return sch_chrgunitcd;
	}
	public void setSch_chrgunitcd(String sch_chrgunitcd) {
		this.sch_chrgunitcd = sch_chrgunitcd;
	}
	public Collection getSch_chrgunitcd_collection() {
		return sch_chrgunitcd_collection;
	}
	public void setSch_chrgunitcd_collection(Collection sch_chrgunitcd_collection) {
		this.sch_chrgunitcd_collection = sch_chrgunitcd_collection;
	}
	public String getSch_deptcd1() {
		return sch_deptcd1;
	}
	public void setSch_deptcd1(String sch_deptcd1) {
		this.sch_deptcd1 = sch_deptcd1;
	}
	public Collection getSch_deptcd1_collection() {
		return sch_deptcd1_collection;
	}
	public void setSch_deptcd1_collection(Collection sch_deptcd1_collection) {
		this.sch_deptcd1_collection = sch_deptcd1_collection;
	}
	public String getSch_deptcd2() {
		return sch_deptcd2;
	}
	public void setSch_deptcd2(String sch_deptcd2) {
		this.sch_deptcd2 = sch_deptcd2;
	}
	public Collection getSch_deptcd2_collection() {
		return sch_deptcd2_collection;
	}
	public void setSch_deptcd2_collection(Collection sch_deptcd2_collection) {
		this.sch_deptcd2_collection = sch_deptcd2_collection;
	}
	public String getSch_deptcd3() {
		return sch_deptcd3;
	}
	public void setSch_deptcd3(String sch_deptcd3) {
		this.sch_deptcd3 = sch_deptcd3;
	}
	public Collection getSch_deptcd3_collection() {
		return sch_deptcd3_collection;
	}
	public void setSch_deptcd3_collection(Collection sch_deptcd3_collection) {
		this.sch_deptcd3_collection = sch_deptcd3_collection;
	}
	public String getSch_deptcd4() {
		return sch_deptcd4;
	}
	public void setSch_deptcd4(String sch_deptcd4) {
		this.sch_deptcd4 = sch_deptcd4;
	}
	public Collection getSch_deptcd4_collection() {
		return sch_deptcd4_collection;
	}
	public void setSch_deptcd4_collection(Collection sch_deptcd4_collection) {
		this.sch_deptcd4_collection = sch_deptcd4_collection;
	}
	public String getSch_deptcd5() {
		return sch_deptcd5;
	}
	public void setSch_deptcd5(String sch_deptcd5) {
		this.sch_deptcd5 = sch_deptcd5;
	}
	public Collection getSch_deptcd5_collection() {
		return sch_deptcd5_collection;
	}
	public void setSch_deptcd5_collection(Collection sch_deptcd5_collection) {
		this.sch_deptcd5_collection = sch_deptcd5_collection;
	}
	public String getSch_deptcd6() {
		return sch_deptcd6;
	}
	public void setSch_deptcd6(String sch_deptcd6) {
		this.sch_deptcd6 = sch_deptcd6;
	}
	public Collection getSch_deptcd6_collection() {
		return sch_deptcd6_collection;
	}
	public void setSch_deptcd6_collection(Collection sch_deptcd6_collection) {
		this.sch_deptcd6_collection = sch_deptcd6_collection;
	}
	public Collection getSch_inputusrid_collection() {
		return sch_inputusrid_collection;
	}
	public void setSch_inputusrid_collection(Collection sch_inputusrid_collection) {
		this.sch_inputusrid_collection = sch_inputusrid_collection;
	}
	public String getSch_inputusrid() {
		return sch_inputusrid;
	}
	public void setSch_inputusrid(String sch_inputusrid) {
		this.sch_inputusrid = sch_inputusrid;
	}	
	public boolean isSubtotalShowStringState() {
		return subtotalShowStringState;
	}
	public void setSubtotalShowStringState(boolean subtotalShowStringState) {
		this.subtotalShowStringState = subtotalShowStringState;
	}
	public boolean isTotalShowStringState() {
		return totalShowStringState;
	}
	public void setTotalShowStringState(boolean totalShowStringState) {
		this.totalShowStringState = totalShowStringState;
	}
	public String getAttoption2() {
		return attoption2;
	}
	public void setAttoption2(String attoption2) {
		this.attoption2 = attoption2;
	}
	public boolean isSubtotalState() {
		return subtotalState;
	}
	public void setSubtotalState(boolean subtotalState) {
		this.subtotalState = subtotalState;
	}
	public boolean isTotalState() {
		return totalState;
	}
	public void setTotalState(boolean totalState) {
		this.totalState = totalState;
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
	public int getDatacnt() {
		return datacnt;
	}
	public void setDatacnt(int datacnt) {
		this.datacnt = datacnt;
	}
	public String getDeptcd() {
		return deptcd;
	}
	public void setDeptcd(String deptcd) {
		this.deptcd = deptcd;
	}
	public String getFormbodyhtml() {
		return formbodyhtml;
	}
	public void setFormbodyhtml(String formbodyhtml) {
		this.formbodyhtml = formbodyhtml;
	}
	public String getFormcomment() {
		return formcomment;
	}
	public void setFormcomment(String formcomment) {
		this.formcomment = formcomment;
	}
	public List getFormdatalist() {
		return formdatalist;
	}
	public void setFormdatalist(List formdatalist) {
		this.formdatalist = formdatalist;
	}
	public String getFormheaderhtml() {
		return formheaderhtml;
	}
	public void setFormheaderhtml(String formheaderhtml) {
		this.formheaderhtml = formheaderhtml;
	}
	public String getFormhtml() {
		return formhtml;
	}
	public void setFormhtml(String formhtml) {
		this.formhtml = formhtml;
	}
	public String getFormkind() {
		return formkind;
	}
	public void setFormkind(String formkind) {
		this.formkind = formkind;
	}
	public String getFormkindname() {
		return formkindname;
	}
	public void setFormkindname(String formkindname) {
		this.formkindname = formkindname;
	}
	public int getFormseq() {
		return formseq;
	}
	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}
	public String getFormtailhtml() {
		return formtailhtml;
	}
	public void setFormtailhtml(String formtailhtml) {
		this.formtailhtml = formtailhtml;
	}
	public String getFormtitle() {
		return formtitle;
	}
	public void setFormtitle(String formtitle) {
		this.formtitle = formtitle;
	}
	public List getListform() {
		return listform;
	}
	public void setListform(List listform) {
		this.listform = listform;
	}
	public String getMakedt() {
		return makedt;
	}
	public void setMakedt(String makedt) {
		this.makedt = makedt;
	}
	public String getMakeusr() {
		return makeusr;
	}
	public void setMakeusr(String makeusr) {
		this.makeusr = makeusr;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSeqno() {
		return seqno;
	}
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}
	public int getSysdocno() {
		return sysdocno;
	}
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
	public int getTblcols() {
		return tblcols;
	}
	public void setTblcols(int tblcols) {
		this.tblcols = tblcols;
	}
	public int getTblrows() {
		return tblrows;
	}
	public void setTblrows(int tblrows) {
		this.tblrows = tblrows;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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

	public String getFormbodyhtml_ext() {
		return formbodyhtml_ext;
	}

	public void setFormbodyhtml_ext(String formbodyhtml_ext) {
		this.formbodyhtml_ext = formbodyhtml_ext;
	}
	
	public String getSch_tgtdeptcd() {
		return sch_tgtdeptcd;
	}

	public void setSch_tgtdeptcd(String sch_tgtdeptcd) {
		this.sch_tgtdeptcd = sch_tgtdeptcd;
	}

	public int getCommformseq() {
		return commformseq;
	}

	public void setCommformseq(int commformseq) {
		this.commformseq = commformseq;
	}

	public int getUsedformseq() {
		return usedformseq;
	}

	public void setUsedformseq(int usedformseq) {
		this.usedformseq = usedformseq;
	}

	public int getUsedsysdocno() {
		return usedsysdocno;
	}

	public void setUsedsysdocno(int usedsysdocno) {
		this.usedsysdocno = usedsysdocno;
	}

	public String getAttoption1() {
		return attoption1;
	}

	public void setAttoption1(String attoption) {
		this.attoption1 = attoption;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {return null;}
	public void reset(ActionMapping mapping, HttpServletRequest request) {}
}