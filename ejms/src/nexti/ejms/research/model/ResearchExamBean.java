/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� ���׺� ���� bean
 * ����:
 */
package nexti.ejms.research.model;

public class ResearchExamBean  {
	
	private String sessionId = "";	//����ID
	private int rchno		= 0;	//���������ȣ
	private int formseq     = 0;     //���׹�ȣ
	private int examseq     = 0;     //�����ȣ
	private int examcnt		= 0;     //�亯
	private String examcont = "";    //���⳻��
	private int		fileseq		= 0;	//÷�����Ϲ�ȣ
	private String	filenm		= "";	//÷�����ϸ�
	private String	originfilenm = "";	//�������ϸ�
	private int		filesize	= 0;	//����ũ��
	private String	ext			= "";	//Ȯ���
	private int		ord			= 0;	//���ļ���
	private String	fileToBase64Encoding = "";	//÷������
	
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