/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ���� bean
 * ����:
 */
package nexti.ejms.sinchung.model;

import java.util.List;

public class ArticleBean  {
	
	private int    reqformno = 0;     //��û��Ĺ�ȣ
	private String sessi     = "";    //����ID
	private int    formseq   = 0;     //���׹�ȣ
	private String formtitle = "";    //���׳���
	private String require   = "";    //�ʼ��Է¿���(Y:�ʼ�, N:����)
	private String formtype  = "1";   //�Է�����       (1:���� 2:���� 3:�ܴ� 4:�幮)
	private String security  = "";    //����ó������ (Y:����ó��, N:����)
	private String helpexp   = "";    //�߰�����
	private String examtype  = "";    //��ŸYN 
	private int    fileseq   = 0;     //÷�����Ϲ�ȣ
	private String filenm	 = "";	  //÷�����ϸ�
	private String originfilenm = ""; //�������ϸ�
	private int	   filesize  = 0;	  //����ũ��
	private String ext		 = "";	  //Ȯ���
	private int	   ord		 = 0;	  //���ļ���
	private String fileToBase64Encoding = ""; //÷������
	private int		ex_frsq		= 0;	//��������
	private String		ex_exsq		= "0";	//��������
	
	private List sampleList = null;   //���� ���
	
	public String getExamtype() {
		return examtype;
	}
	public void setExamtype(String examtype) {
		this.examtype = examtype;
	}
	public String getSessi() {
		return sessi;
	}
	public void setSessi(String sessi) {
		this.sessi = sessi;
	}
	public String getHelpexp() {
		return helpexp;
	}
	public void setHelpexp(String helpexp) {
		this.helpexp = helpexp;
	}
	public List getSampleList() {
		return sampleList;
	}
	public void setSampleList(List sampleList) {
		this.sampleList = sampleList;
	}
	public int getFormseq() {
		return formseq;
	}
	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}
	public String getFormtitle() {
		return formtitle;
	}
	public void setFormtitle(String formtitle) {
		this.formtitle = formtitle;
	}
	public String getFormtype() {
		return formtype;
	}
	public void setFormtype(String formtype) {
		this.formtype = formtype;
	}
	public int getReqformno() {
		return reqformno;
	}
	public void setReqformno(int reqformno) {
		this.reqformno = reqformno;
	}
	public String getRequire() {
		return require;
	}
	public void setRequire(String require) {
		this.require = require;
	}
	public String getSecurity() {
		return security;
	}
	public void setSecurity(String security) {
		this.security = security;
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
	public String getFileToBase64Encoding() {
		return fileToBase64Encoding;
	}
	public void setFileToBase64Encoding(String fileToBase64Encoding) {
		this.fileToBase64Encoding = fileToBase64Encoding;
	}
	public int getEx_frsq() {
		return ex_frsq;
	}
	public void setEx_frsq(int ex_frsq) {
		this.ex_frsq = ex_frsq;
	}
	public String getEx_exsq() {
		return ex_exsq;
	}
	public void setEx_exsq(String ex_exsq) {
		this.ex_exsq = ex_exsq;
	}	
}