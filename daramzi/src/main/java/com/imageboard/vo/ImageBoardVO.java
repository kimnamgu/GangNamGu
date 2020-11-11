package com.imageboard.vo;

import java.util.List;

import com.common.vo.commonVO;

public class ImageBoardVO extends commonVO {

	private String userIdx;
    private int seq;
    private String title;
    private String description;
    private String content;
    private int count;
    private String ip;
    private int pos;
    private int depth;
    
	//게시판 리스트용
	private String keyField;
	private String keyWord;
	
    private int listNum = 20;
    private int start;

    private int listNumOrg;
	private String keyFieldOrg;
	private String keyWordOrg;
    private int page = 1;
    private String pageYN;
    
    
    //파일업로드용
    private String fileIdx;
    private int fileSeq;
    private String fileName;
    private String fileExtension;
    private String fileRealName;
    private long fileSize;
    private String categoryCode;
    

    private int boardIdx;
    private String id;
    private String folderPath;
    private String del_yn;
    private String open_yn;
    private String down_yn;
    private String fileDescription;
    private String ins_dt;
    private String upt_dt;
    
    // 게시판 표시 스타일
    private String viewStyle;
    private String viewStyleOrg;
    
    //선택파일
    private List<String> idxArr;
    
    //폴더명
    private String folderName;
    
    //체크 스타일 선택
    private String sendStyle;
    
    
    //파일 추천
    private String recommendIdx;
    private String recommendYN;
    
    private int recommendYcnt = 0;
    private int recommendNcnt = 0;
    
    //getter setter 시작
    public String getUserIdx() {
    	return userIdx;
    }
    public void setUserIdx(String userIdx) {
    	this.userIdx = userIdx;
    }
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	//게시판 리스트용 getter, setter
	public String getKeyField() {
		return keyField;
	}
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public int getStart() {
		int start = this.getListNum() * (this.getPage()-1);
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getListNumOrg() {
		return listNumOrg;
	}
	public void setListNumOrg(int listNumOrg) {
		this.listNumOrg = listNumOrg;
	}
	public String getKeyFieldOrg() {
		return keyFieldOrg;
	}
	public void setKeyFieldOrg(String keyFieldOrg) {
		this.keyFieldOrg = keyFieldOrg;
	}
	public String getKeyWordOrg() {
		return keyWordOrg;
	}
	public void setKeyWordOrg(String keyWordOrg) {
		this.keyWordOrg = keyWordOrg;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getPageYN() {
		return pageYN;
	}
	public void setPageYN(String pageYN) {
		this.pageYN = pageYN;
	}

	//파일업로드
    public String getFileIdx() {
		return fileIdx;
	}
	public void setFileIdx(String fileIdx) {
		this.fileIdx = fileIdx;
	}
	public int getFileSeq() {
		return fileSeq;
	}
	public void setFileSeq(int fileSeq) {
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
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
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
	
	// 게시판 표시 스타일
	public String getViewStyle() {
		return viewStyle;
	}
	public void setViewStyle(String viewStyle) {
		this.viewStyle = viewStyle;
	}
	public String getViewStyleOrg() {
		return viewStyleOrg;
	}
	public void setViewStyleOrg(String viewStyleOrg) {
		this.viewStyleOrg = viewStyleOrg;
	}
	
	//선택파일
	public List<String> getIdxArr() {
		return idxArr;
	}
	public void setIdxArr(List<String> idxArr) {
		this.idxArr = idxArr;
	}
	
	//폴더명
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
    //체크 스타일 선택
	public String getSendStyle() {
		return sendStyle;
	}
	public void setSendStyle(String sendStyle) {
		this.sendStyle = sendStyle;
	}
	//id 정보 전달
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	//파일 추천
	public String getRecommendIdx() {
		return recommendIdx;
	}
	public void setRecommendIdx(String recommendIdx) {
		this.recommendIdx = recommendIdx;
	}
	public String getRecommendYN() {
		return recommendYN;
	}
	public void setRecommendYN(String recommendYN) {
		this.recommendYN = recommendYN;
	}
	public int getRecommendYcnt() {
		return recommendYcnt;
	}
	public void setRecommendYcnt(int recommendYcnt) {
		this.recommendYcnt = recommendYcnt;
	}
	public int getRecommendNcnt() {
		return recommendNcnt;
	}
	public void setRecommendNcnt(int recommendNcnt) {
		this.recommendNcnt = recommendNcnt;
	}
}
