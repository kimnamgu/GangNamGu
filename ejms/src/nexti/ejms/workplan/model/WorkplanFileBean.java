/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���չ���÷������ bean
 * ����:
 */
package nexti.ejms.workplan.model;

public class WorkplanFileBean{

	private int		planno		= 0;	//��ȹ��ȣ
	private int		resultno	= 0;	//������ȣ
	private int		fileno		= 0;	//÷�����Ϲ�ȣ
	private String	filenm		= "";	//÷�����ϸ�
	private String	orgfilenm = "";	//�������ϸ�
	private int		filesize	= 0;	//����ũ��
	private String	ext			= "";	//Ȯ����
	private	int		ord			= 0;	//���ļ���
	
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
