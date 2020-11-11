/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 문항 bean
 * 설명:
 */
package nexti.ejms.sinchung.model;

import java.util.List;

public class ArticleBean  {
	
	private int    reqformno = 0;     //신청양식번호
	private String sessi     = "";    //세션ID
	private int    formseq   = 0;     //문항번호
	private String formtitle = "";    //문항내용
	private String require   = "";    //필수입력여부(Y:필수, N:선택)
	private String formtype  = "1";   //입력형태       (1:단일 2:복수 3:단답 4:장문)
	private String security  = "";    //보안처리여부 (Y:보안처리, N:없음)
	private String helpexp   = "";    //추가설명
	private String examtype  = "";    //기타YN 
	private int    fileseq   = 0;     //첨부파일번호
	private String filenm	 = "";	  //첨부파일명
	private String originfilenm = ""; //원본파일명
	private int	   filesize  = 0;	  //파일크기
	private String ext		 = "";	  //확장명
	private int	   ord		 = 0;	  //정렬순서
	private String fileToBase64Encoding = ""; //첨부파일
	private int		ex_frsq		= 0;	//연동문항
	private String		ex_exsq		= "0";	//연동보기
	
	private List sampleList = null;   //보기 목록
	
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