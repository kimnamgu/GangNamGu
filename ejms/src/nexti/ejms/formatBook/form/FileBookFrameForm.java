/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 첨부파일 actionform
 * 설명:
 */
package nexti.ejms.formatBook.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class FileBookFrameForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int		type		= 0;	//작성형태
	private	int		sysdocno	= 0;	//시스템문서번호
	private	int		formseq		= 0;	//양식일련번호
	private	int		fileseq		= 0;	//첨부파일일련번호
	private	String	filenm		= "";	//첨부파일명
	private	String	originfilenm = "";	//원본파일명
	private	int		filesize	= 0;	//파일크기
	private	String	ext			= "";	//확장자
	private	int		ord			= 0;	//정렬순서
	private List	listfilebook = null;//양식첨부파일목록
	private String	deptcd			= "";	//관련부서코드

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