/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ÷������ actionform
 * ����:
 */
package nexti.ejms.formatBook.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class FileBookFrameForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int		type		= 0;	//�ۼ�����
	private	int		sysdocno	= 0;	//�ý��۹�����ȣ
	private	int		formseq		= 0;	//����Ϸù�ȣ
	private	int		fileseq		= 0;	//÷�������Ϸù�ȣ
	private	String	filenm		= "";	//÷�����ϸ�
	private	String	originfilenm = "";	//�������ϸ�
	private	int		filesize	= 0;	//����ũ��
	private	String	ext			= "";	//Ȯ����
	private	int		ord			= 0;	//���ļ���
	private List	listfilebook = null;//���÷�����ϸ��
	private String	deptcd			= "";	//���úμ��ڵ�

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
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

	public int getFormseq() {
		return formseq;
	}

	public void setFormseq(int formseq) {
		this.formseq = formseq;
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

	public int getSysdocno() {
		return sysdocno;
	}

	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}

	public List getListfilebook() {
		return listfilebook;
	}

	public void setListfilebook(List listfilebook) {
		this.listfilebook = listfilebook;
	}

	public String getDeptcd() {
		return deptcd;
	}

	public void setDeptcd(String deptcd) {
		this.deptcd = deptcd;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}