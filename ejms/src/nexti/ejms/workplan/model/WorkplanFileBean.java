/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서첨부파일 bean
 * 설명:
 */
package nexti.ejms.workplan.model;

public class WorkplanFileBean{

	private int		planno		= 0;	//계획번호
	private int		resultno	= 0;	//실적번호
	private int		fileno		= 0;	//첨부파일번호
	private String	filenm		= "";	//첨부파일명
	private String	orgfilenm = "";	//원본파일명
	private int		filesize	= 0;	//파일크기
	private String	ext			= "";	//확장자
	private	int		ord			= 0;	//정렬순서
	
	public int getResultno() {
		return resultno;
	}
	public void setResultno(int resultno) {
		this.resultno = resultno;
	}
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
	public int getFileno() {
		return fileno;
	}
	public void setFileno(int fileno) {
		this.fileno = fileno;
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
	public String getOrgfilenm() {
		return orgfilenm;
	}
	public void setOrgfilenm(String orgfilenm) {
		this.orgfilenm = orgfilenm;
	}
	public int getPlanno() {
		return planno;
	}
	public void setPlanno(int planno) {
		this.planno = planno;
	}
}
