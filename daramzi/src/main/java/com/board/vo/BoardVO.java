package com.board.vo;

import com.common.vo.commonVO;

public class BoardVO extends commonVO {

	private int boardIdx;
	private String id;
	private String title;
	private String content;
	private String hitCnt;
	private String ip;
	private String ins_dt;
	private String upt_dt;
	
	//로그인 확인
	private String login_id;
    
	//게시판 리스트용
	private String keyField;
	private String keyWord;
	
    private int listNum = 20;
    private int start;

    private int listNumOrg;
	private String keyFieldOrg;
	private String keyWordOrg;
    private int page = 1;
    private String pageYN = "Y";
    
    //파일업로드용
    private String file;
    private String fileName;
    private String fileRealName;
    
    //게시글 추천
    private String recommendIdx;
    private String recommendYN;
    
    private int recommendYcnt = 0;
    private int recommendNcnt = 0;
    
	
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHitCnt() {
		return hitCnt;
	}
	public void setHitCnt(String hitCnt) {
		this.hitCnt = hitCnt;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
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
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileRealName() {
		return fileRealName;
	}
	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}
	
	//게시글 추천
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
