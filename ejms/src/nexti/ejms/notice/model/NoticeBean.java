/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� bean
 * ����:
 */
package nexti.ejms.notice.model;

public class NoticeBean {
	private int bunho        = 0;     //����
	private int seq          = 0;     //�������ױ۹�ȣ	
	private String title     = "";    //����
	private String content   = "";    //����
	private int visitno      = 0;     //��ȸ��
	private String crtdt     = "";    //�ۼ�����
	private String crtusrnm  = "";    //�ۼ��ڼ���
	private String newfl     = "";    //���� ����(1:����)
	private int		fileseq		= 0;	//÷�����Ϲ�ȣ
	private String	filenm		= "";	//÷�����ϸ�
	private String	originfilenm = "";	//�������ϸ�
	private int		filesize	= 0;	//����ũ��
	private String	ext			= "";	//Ȯ���
	private int		ord			= 0;	//���ļ���
	
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
