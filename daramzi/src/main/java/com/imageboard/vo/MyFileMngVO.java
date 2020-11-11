package com.imageboard.vo;

import com.common.vo.commonVO;

public class MyFileMngVO extends commonVO {

	private String fileIdx;
	private String fileSeq;
	private String fileName;
	private String fileExtension;
	private String fileRealName;
	private String fileSize;
	private String boardIdx;
	private String id;
	private String upperFolder;
	
	private String folderPath;
	private String del_yn;
	private String open_yn;
	private String down_yn;
	private String fileDescription;
	private String ins_dt;
	private String upt_dt;
	
	//기본정보
	private int page;
	private int rows;
	private String sidx;
	private String sord;
	
	private int start;
	
	
	//셀변경
	private String cellName;
	private String cellValue;
	
	
	public String getFileIdx() {
		return fileIdx;
	}
	public void setFileIdx(String fileIdx) {
		this.fileIdx = fileIdx;
	}
	public String getFileSeq() {
		return fileSeq;
	}
	public void setFileSeq(String fileSeq) {
		this.fileSeq = fileSeq;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getFileRealName() {
		return fileRealName;
	}
	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	
	public String getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(String boardIdx) {
		this.boardIdx = boardIdx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
	public String getUpperFolder() {
		return upperFolder;
	}
	public void setUpperFolder(String upperFolder) {
		this.upperFolder = upperFolder;
	}
	public String getFolderPath() {
		return folderPath;
	}
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	public String getOpen_yn() {
		return open_yn;
	}
	public void setOpen_yn(String open_yn) {
		this.open_yn = open_yn;
	}
	public String getDown_yn() {
		return down_yn;
	}
	public void setDown_yn(String down_yn) {
		this.down_yn = down_yn;
	}
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	public String getIns_dt() {
		return ins_dt;
	}
	public void setIns_dt(String ins_dt) {
		this.ins_dt = ins_dt;
	}
	public String getUpt_dt() {
		return upt_dt;
	}
	public void setUpt_dt(String upt_dt) {
		this.upt_dt = upt_dt;
	}
	
	//기본정보
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public int getStart() {
		int start = this.getRows() * (this.getPage()-1);
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
	//셀 변경
	public String getCellName() {
		return cellName;
	}
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	public String getCellValue() {
		return cellValue;
	}
	public void setCellValue(String cellValue) {
		this.cellValue = cellValue;
	}
	
	
	
	
	
}
