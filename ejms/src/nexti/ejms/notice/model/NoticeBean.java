/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공지사항 bean
 * 설명:
 */
package nexti.ejms.notice.model;

public class NoticeBean {
	private int bunho        = 0;     //연번
	private int seq          = 0;     //공지사항글번호	
	private String title     = "";    //제목
	private String content   = "";    //내용
	private int visitno      = 0;     //조회수
	private String crtdt     = "";    //작성일자
	private String crtusrnm  = "";    //작성자성명
	private String newfl     = "";    //새글 여부(1:새글)
	private int		fileseq		= 0;	//첨부파일번호
	private String	filenm		= "";	//첨부파일명
	private String	originfilenm = "";	//원본파일명
	private int		filesize	= 0;	//파일크기
	private String	ext			= "";	//확장명
	private int		ord			= 0;	//정렬순서
	
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
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
	public String getOriginfilenm() {
		return originfilenm;
	}
	public void setOriginfilenm(String originfilenm) {
		this.originfilenm = originfilenm;
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
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getBunho() {
		return bunho;
	}
	public void setBunho(int bunho) {
		this.bunho = bunho;
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
	public String getNewfl() {
		return newfl;
	}
	public void setNewfl(String newfl) {
		this.newfl = newfl;
	}		
}
