/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공지사항 actionform
 * 설명:
 */
package nexti.ejms.notice.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class NoticeForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private int seqno           = 0;      //연번
	private int seq             = 0;      //공지사항글번호	
	private String title        = "";     //제목
	private String content      = "";     //내용
	private int visitno         = 0;      //조회수
	private String crtdt        = "";     //작성일자
	private String crtusrnm     = "";     //작성자성명
	private FormFile[] fileList = new FormFile[3];    //첨부파일
	
	private int page            = 1 ;     //페이지
	private String gubun        = "";     //검색구분 (1:제목, 2:내용)
	private String searchval    = "";     //검색내용
	private List noticeList     = null;   //목록
	private List flist          = null;   //파일목록 리스트
	private int		fileseq		= 0;	//첨부파일번호
	
	private String gbn          = "";     //작동구분(D:삭제)

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		return null;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}

	public String getGbn() {
		return gbn;
	}

	public void setGbn(String gbn) {
		this.gbn = gbn;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCrtdt() {
		return crtdt;
	}

	public void setCrtdt(String crtdt) {
		this.crtdt = crtdt;
	}

	public String getCrtusrnm() {
		return crtusrnm;
	}

	public void setCrtusrnm(String crtusrnm) {
		this.crtusrnm = crtusrnm;
	}

	public FormFile[] getFileList() {
		return fileList;
	}

	public void setFileList(FormFile[] fileList) {
		this.fileList = fileList;
	}

	public int getSeqno() {
		return seqno;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getVisitno() {
		return visitno;
	}

	public void setVisitno(int visitno) {
		this.visitno = visitno;
	}

	public List getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List noticeList) {
		this.noticeList = noticeList;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getSearchval() {
		return searchval;
	}

	public void setSearchval(String searchval) {
		this.searchval = searchval;
	}

	public List getFlist() {
		return flist;
	}

	public void setFlist(List flist) {
		this.flist = flist;
	}

	public int getFileseq() {
		return fileseq;
	}

	public void setFileseq(int fileseq) {
		this.fileseq = fileseq;
	}		
}