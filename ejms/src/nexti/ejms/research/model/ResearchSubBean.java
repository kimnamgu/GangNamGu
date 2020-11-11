/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 문항 bean
 * 설명:
 */
package nexti.ejms.research.model;

import java.util.List;

public class ResearchSubBean  {
	
	private String sessionId	= "";	//세션ID
	private int	rchno			= 0;	//설문조사번호
	private int formseq      	= 0;	//문항번호
	private String formgrp		= "";	//문항주제 
	private String formtitle 	= "";	//문항내용
	private String require   	= "";	//필수입력여부
	private String formtype  	= "";	//입력형태
	private String security  	= "";	//보안처리여부
	private String examtype		= "";   //답변기타구분
	private String sch_exam		= "";   //답변유형조회조건
	private int		fileseq		= 0;	//첨부파일번호
	private String	filenm		= "";	//첨부파일명
	private String	originfilenm = "";	//원본파일명
	private int		filesize	= 0;	//파일크기
	private String	ext			= "";	//확장명
	private int		ord			= 0;	//정렬순서
	private int		ex_frsq		= 0;	//연동문항
	private String		ex_exsq		= "0";	//연동보기
	private String	fileToBase64Encoding = "";	//첨부파일
	
	private List examList = null;	//보기 목록
	private List otherList = null;	//기타목록

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

	public List getOtherList() {
		return otherList;
	}

	public void setOtherList(List otherList) {
		this.otherList = otherList;
	}

	public List getExamList() {
		return examList;
	}

	public void setExamList(List examList) {
		this.examList = examList;
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

	public String getFormgrp() {
		return formgrp;
	}

	public void setFormgrp(String formgrp) {
		this.formgrp = formgrp;
	}

	public String getExamtype() {
		return examtype;
	}

	public void setExamtype(String examtype) {
		this.examtype = examtype;
	}

	public String getSch_exam() {
		return sch_exam;
	}

	public void setSch_exam(String sch_exam) {
		this.sch_exam = sch_exam;
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