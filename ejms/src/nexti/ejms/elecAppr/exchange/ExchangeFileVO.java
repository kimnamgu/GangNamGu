package nexti.ejms.elecAppr.exchange;

import java.io.File;

public class ExchangeFileVO {
	
	private int atchSeq = 0;
	private String saveDir;
	private String fileStatus;
	private String logclFileName;
	private String phyclFileName;
	private int fileSize = 0;
	private String fileExt;
	private String logclFileName1;
	private String phyclFileName1;
	private int fileSize1 = 0;
	private String fileExt1;
	private String fileGbn;
	private String fileContent; 
	private File file; 
	private int exchangeFileSeq;
	private int exchangeSeq; 
	
	public ExchangeFileVO(){
		this.saveDir = "";
		this.fileStatus = "";
		this.logclFileName = "";
		this.phyclFileName = "";
		this.fileExt = "";
		this.logclFileName1 = "";
		this.phyclFileName1 = "";
		this.fileExt1 = "";
		this.fileGbn = "";
		this.exchangeFileSeq=0;
		this.exchangeSeq=0; 
		this.file=null; 
		this.fileContent=null; 
	}
	
	public int getAtchSeq() {
		return atchSeq;
	}

	public void setAtchSeq(int atchSeq) {
		this.atchSeq = atchSeq;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getFileExt1() {
		return fileExt1;
	}

	public void setFileExt1(String fileExt1) {
		this.fileExt1 = fileExt1;
	}

	public String getFileGbn() {
		return fileGbn;
	}

	public void setFileGbn(String fileGbn) {
		this.fileGbn = fileGbn;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public int getFileSize1() {
		return fileSize1;
	}

	public void setFileSize1(int fileSize1) {
		this.fileSize1 = fileSize1;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getLogclFileName() {
		return logclFileName;
	}

	public void setLogclFileName(String logclFileName) {
		this.logclFileName = logclFileName;
	}

	public String getLogclFileName1() {
		return logclFileName1;
	}

	public void setLogclFileName1(String logclFileName1) {
		this.logclFileName1 = logclFileName1;
	}

	public String getPhyclFileName() {
		return phyclFileName;
	}

	public void setPhyclFileName(String phyclFileName) {
		this.phyclFileName = phyclFileName;
	}

	public String getPhyclFileName1() {
		return phyclFileName1;
	}

	public void setPhyclFileName1(String phyclFileName1) {
		this.phyclFileName1 = phyclFileName1;
	}

	public String getSaveDir() {
		return saveDir;
	}

	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}

	public ExchangeFileVO(String fileName, String fileContent){
		this.fileContent=fileContent; 
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getExchangeFileSeq() {
		return exchangeFileSeq;
	}

	public void setExchangeFileSeq(int exchangeFileSeq) {
		this.exchangeFileSeq = exchangeFileSeq;
	}

	public int getExchangeSeq() {
		return exchangeSeq;
	}

	public void setExchangeSeq(int exchangeSeq) {
		this.exchangeSeq = exchangeSeq;
	}

}//EOC
