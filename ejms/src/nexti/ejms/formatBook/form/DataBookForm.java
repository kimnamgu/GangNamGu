/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 제본자료형 입력자료 actionform
 * 설명:
 */
package nexti.ejms.formatBook.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class DataBookForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private int seqno 			= 0; 	//연번
	
	private int sysdocno 		= 0; 	//시스템문서번호
	private int formseq 		= 0; 	//양식일련번호
	private int fileseq			= 0;	//파일일련번호
	private String tgtdeptcd 	= "";	//제출부서코드
	private String tgtdeptnm	= "";   //제출부서명
	private String inputusrid	= "";	//입력담당자ID
	private String inputusrnm   = "";   //입력담당자명
	private int chrgunitcd		= 0;	//담당단위코드
	private String categorynm	= "";	//카테고리명칭
	private String formtitle	= "";	//제목
	private String filenm		= "";	//첨부파일명
	private String originfilenm	= "";	//원본파일명
	private int filesize		= 0;	//파일크기
	private String ext			= "";	//파일확장자
	private String ord			= "";	//정렬순서
	private String rowid		= "";   //행번호
	
	private FormFile inputFile	= null;	//제본형 첨부파일
	
	private List dataList		= null;	//제본형 데이터 리스트

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}

	/**
	 * @return the categorynm
	 */
	public String getCategorynm() {
		return categorynm;
	}

	/**
	 * @param categorynm the categorynm to set
	 */
	public void setCategorynm(String categorynm) {
		this.categorynm = categorynm;
	}

	/**
	 * @return the chrgunitcd
	 */
	public int getChrgunitcd() {
		return chrgunitcd;
	}

	/**
	 * @param chrgunitcd the chrgunitcd to set
	 */
	public void setChrgunitcd(int chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
	}

	/**
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}

	/**
	 * @param ext the ext to set
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}

	/**
	 * @return the filenm
	 */
	public String getFilenm() {
		return filenm;
	}

	/**
	 * @param filenm the filenm to set
	 */
	public void setFilenm(String filenm) {
		this.filenm = filenm;
	}

	/**
	 * @return the fileseq
	 */
	public int getFileseq() {
		return fileseq;
	}

	/**
	 * @param fileseq the fileseq to set
	 */
	public void setFileseq(int fileseq) {
		this.fileseq = fileseq;
	}

	/**
	 * @return the filesize
	 */
	public int getFilesize() {
		return filesize;
	}

	/**
	 * @param filesize the filesize to set
	 */
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}

	/**
	 * @return the formseq
	 */
	public int getFormseq() {
		return formseq;
	}

	/**
	 * @param formseq the formseq to set
	 */
	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}

	/**
	 * @return the formtitle
	 */
	public String getFormtitle() {
		return formtitle;
	}

	/**
	 * @param formtitle the formtitle to set
	 */
	public void setFormtitle(String formtitle) {
		this.formtitle = formtitle;
	}

	/**
	 * @return the inputusrid
	 */
	public String getInputusrid() {
		return inputusrid;
	}

	/**
	 * @param inputusrid the inputusrid to set
	 */
	public void setInputusrid(String inputusrid) {
		this.inputusrid = inputusrid;
	}

	/**
	 * @return the ord
	 */
	public String getOrd() {
		return ord;
	}

	/**
	 * @param ord the ord to set
	 */
	public void setOrd(String ord) {
		this.ord = ord;
	}

	/**
	 * @return the originfilenm
	 */
	public String getOriginfilenm() {
		return originfilenm;
	}

	/**
	 * @param originfilenm the originfilenm to set
	 */
	public void setOriginfilenm(String originfilenm) {
		this.originfilenm = originfilenm;
	}

	/**
	 * @return the seqno
	 */
	public int getSeqno() {
		return seqno;
	}

	/**
	 * @param seqno the seqno to set
	 */
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	/**
	 * @return the sysdocno
	 */
	public int getSysdocno() {
		return sysdocno;
	}

	/**
	 * @param sysdocno the sysdocno to set
	 */
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}

	/**
	 * @return the tgtdeptcd
	 */
	public String getTgtdeptcd() {
		return tgtdeptcd;
	}

	/**
	 * @param tgtdeptcd the tgtdeptcd to set
	 */
	public void setTgtdeptcd(String tgtdeptcd) {
		this.tgtdeptcd = tgtdeptcd;
	}

	/**
	 * @return the dataList
	 */
	public List getDataList() {
		return dataList;
	}

	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	/**
	 * @return the inputFile
	 */
	public FormFile getInputFile() {
		return inputFile;
	}

	/**
	 * @param inputFile the inputFile to set
	 */
	public void setInputFile(FormFile inputFile) {
		this.inputFile = inputFile;
	}

	public String getInputusrnm() {
		return inputusrnm;
	}

	public void setInputusrnm(String inputusrnm) {
		this.inputusrnm = inputusrnm;
	}

	public String getTgtdeptnm() {
		return tgtdeptnm;
	}

	public void setTgtdeptnm(String tgtdeptnm) {
		this.tgtdeptnm = tgtdeptnm;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}	
}