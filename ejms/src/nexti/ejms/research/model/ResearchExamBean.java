/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 문항별 보기 bean
 * 설명:
 */
package nexti.ejms.research.model;

public class ResearchExamBean  {
	
	private String sessionId = "";	//세션ID
	private int rchno		= 0;	//설문조사번호
	private int formseq     = 0;     //문항번호
	private int examseq     = 0;     //보기번호
	private int examcnt		= 0;     //답변
	private String examcont = "";    //보기내용
	private int		fileseq		= 0;	//첨부파일번호
	private String	filenm		= "";	//첨부파일명
	private String	originfilenm = "";	//원본파일명
	private int		filesize	= 0;	//파일크기
	private String	ext			= "";	//확장명
	private int		ord			= 0;	//정렬순서
	private String	fileToBase64Encoding = "";	//첨부파일
	
	public String getFileToBase64Encoding() {
		return fileToBase64Encoding;
	}
	public void setFileToBase64Encoding(String fileToBase64Encoding) {
		this.fileToBase64Encoding = fileToBase64Encoding;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public int getRchno() {
		return rchno;
	}
	public void setRchno(int rchno) {
		this.rchno = rchno;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getFilenm() {
		return filenm;
	}
	public void setFilenm(String filenm) {
		this.filenm = filenm;
	}
	public int getFileseq() {
		return fileseq;
	}
	public void setFileseq(int fileseq) {
		this.fileseq = fileseq;
	}
	public String getOriginfilenm() {
		return originfilenm;
	}
	public void setOriginfilenm(String originfilenm) {
		this.originfilenm = originfilenm;
	}
	public String getExamcont() {
		return examcont;
	}
	public void setExamcont(String examcont) {
		this.examcont = examcont;
	}
	public int getExamseq() {
		return examseq;
	}
	public void setExamseq(int examseq) {
		this.examseq = examseq;
	}
	public int getFormseq() {
		return formseq;
	}
	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}
	public int getExamcnt() {
		return examcnt;
	}
	public void setExamcnt(int examcnt) {
		this.examcnt = examcnt;
	}
}