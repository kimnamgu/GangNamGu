package nexti.ejms.util;

public class FileBean {
	private int		seq				= 0;	//마스터 일련번호	
	private int		fileseq			= 0;	//파일일련번호
	private String	filenm			= "";	//파일명
	private String	originfilenm	= "";	//원본파일명
	private int		filesize		= 0;	//파일크기
	private String	ext				= "";	//확장자
	private	int		ord				= 0;	//정렬순서
	private String	fileUrl			= "";
	private String	fileDir			= "";
	
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public int getFileseq() {
		return fileseq;
	}
	public void setFileseq(int file_seq) {
		this.fileseq = file_seq;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileDir) {
		this.fileUrl = fileDir;
	}
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	public String getFilenm() {
		return filenm;
	}	
	public String getOriginfilenm() {
		return originfilenm;
	}	
	public void setFilenm(String fileName) {
		this.filenm = fileName;
	}
	public void setOriginfilenm(String tempFileName) {
		this.originfilenm = tempFileName;
	}
	public String getExt() {
		return ext;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setExt(String fileExt) {
		this.ext = fileExt;
	}
	public void setFilesize(int fileSize) {
		this.filesize = fileSize;
	}	
}
