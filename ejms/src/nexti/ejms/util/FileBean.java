package nexti.ejms.util;

public class FileBean {
	private int		seq				= 0;	//������ �Ϸù�ȣ	
	private int		fileseq			= 0;	//�����Ϸù�ȣ
	private String	filenm			= "";	//���ϸ�
	private String	originfilenm	= "";	//�������ϸ�
	private int		filesize		= 0;	//����ũ��
	private String	ext				= "";	//Ȯ����
	private	int		ord				= 0;	//���ļ���
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
