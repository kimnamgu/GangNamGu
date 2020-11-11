/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합양식 actionform
 * 설명:
 */
package nexti.ejms.format.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class FormatForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;

	private int		seqno			= 0;	//연번
	private int		type			= 0;	//작성방법
	private int		commformseq		= 0;	//공통양식일련번호
	private int		usedsysdocno	= 0;	//사용했던양식시스템문서번호
	private int		usedformseq		= 0;	//사용했던양식일련번호
	private String	deptcd			= "";	//관련부서코드
	
	private int		sysdocno		= 0;	//시스템문서번호
	private int		formseq			= 0;	//양식일련번호
	private String	formtitle		= "";	//양식제목
	private String	formkind		= "";	//양식종류
	private String	formkindname	= "";	//양식종류이름
	private String	formcomment		= "";	//양식개요
	private String	formhtml		= "";	//양식원본
	private String	formheaderhtml	= "";	//양식구조(헤더)
	private String	formbodyhtml	= "";	//양식구조(바디)
	private String	formtailhtml	= "";	//양식구조(테일
	private int		tblcols			= 0;	//테이블 열개수
	private int		tblrows			= 0;	//테이블 행개수
	private int		ord				= 0;	//정렬순서
	private String	crtdt			= "";	//생성일자
	private String	crtusrid		= "";	//생성자코드
	private String	uptdt			= "";	//수정일자
	private String	uptusrid		= "";	//수정자코드
	private List	listform		= null;	//양식 목록
	
	private int		page			= 1;	//페이지
	private int  	treescroll		= 0;	//조직도 스크롤
	private String	searchdept		= "";	//선택부서이하 전체포함
	private String	searchtitle		= "";	//문서제목검색조건
	private String	searchcomment	= "";	//문서개요검색조건
	private String	searchuser		= "";	//작성자검색조건
	private String	searchyear		= "";	//작성년도검색조건
	
	private String	attach			= "true";	//붙임첨부유무
	private int		reqformno		= 0;	//신청서양식번호
	private int		reqseq			= 0;	//신청서내역번호
	private String	ea_id			= "";	//전자결재아이디
	private String 	orggbn			= "";	//조직구분
	
	public String getOrggbn() {
		return orggbn;
	}
	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}
	public String getEa_id() {
		return ea_id;
	}
	public void setEa_id(String ea_id) {
		this.ea_id = ea_id;
	}
	public String getSearchcomment() {
		return searchcomment;
	}
	public void setSearchcomment(String searchcomment) {
		this.searchcomment = searchcomment;
	}
	public int getReqseq() {
		return reqseq;
	}
	public void setReqseq(int reqseq) {
		this.reqseq = reqseq;
	}
	public int getReqformno() {
		return reqformno;
	}
	public void setReqformno(int reqformno) {
		this.reqformno = reqformno;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getAttach() {
		return attach;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
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

	public String getSearchyear() {
		return searchyear;
	}

	public void setSearchyear(String searchyear) {
		this.searchyear = searchyear;
	}

	public String getSearchdept() {
		return searchdept;
	}

	public void setSearchdept(String searchdept) {
		this.searchdept = searchdept;
	}

	public String getSearchtitle() {
		return searchtitle;
	}

	public void setSearchtitle(String searchtitle) {
		this.searchtitle = searchtitle;
	}

	public String getSearchuser() {
		return searchuser;
	}

	public void setSearchuser(String searchuser) {
		this.searchuser = searchuser;
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

	public int getTreescroll() {
		return treescroll;
	}

	public void setTreescroll(int treescroll) {
		this.treescroll = treescroll;
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

	public String getDeptcd() {
		return deptcd;
	}

	public void setDeptcd(String deptcd) {
		this.deptcd = deptcd;
	}
}