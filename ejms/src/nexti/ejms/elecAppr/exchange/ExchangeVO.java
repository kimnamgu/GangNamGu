package nexti.ejms.elecAppr.exchange;

import java.util.ArrayList;

public class ExchangeVO {
	private int adminNo;		//관리번호
	private int exchangeSeq;	//전자결재 일련번호
	private int templateSeq; 	//템플릿 일련번호 
	private String title;		//제목
	private String body; 		//본문 
	private String senderId;	//결재자 아이디
	private String senderName;	//결재자명
	private String senderDeptName;	//결재자부서명
	private String receiverId; //수신자 아이디
	private String receiverName;	//수신자 명
	private String receiverDeptName; 	//수신자 부서명 
	private String sancStatus;	//결재상태
	private String sancStatusCode;	//결재상태코드
	private String sancEndYn;	//결재종료여부
	private String curSancUserId;	//현재 결재자 아이디
	private String curSancUserName;	//현재 결재자 명
	private String curSancUserPosition;	//현재 결재자 직위
	private String curSancUserDeptName;	//현재 결재자 부서명
	private String curSancUserOrgName;	//현재 결재자 조직명
	private ArrayList sancUserList; //결재자 명단 목록
	private String usecaseGbn;
	private String usecaseGbnTxt;
	private String regId;
	private String regName;
	private String regDeptName;
	private String regTs;		//결재요청일자
	private String useGbn; 
	private String useGbnTxt; 
	private ArrayList atchFileList; //첨부파일 목록 
	private int atchFileCnt;
	private String totalFileInfo; 
	private String sendUrl; //전자결재 문서를 전송하기 위해 사용자가 업무별로 작성한 Exchange Send 경로 (프로그램 아이디)
	private String resultUrl; 
	private String adminChangeYn;
	private String adminChangeTs;
	private String adminName; 
	private String usecaseId;
	private ExchangeFileVO bodyXmlFile;
	private ExchangeFileVO bodyXslFile;
	
	private String userId; 		//결재자 아이디
	private String name;		//결재자명
	private String position;	//결재자 직위
	private String dept;		//결재자 부서명
	private String org;			//결재자 소속
	private int sancLevel;		//결재자 순서
	private String sancDt;		//결재일자
	private String sancType;	//결재타입 (기안,검토,전결,대결 등)
	private String sancResult; 	//결재결과
	private String curSancYn; 	//현재 결재 진행여부 
	

	public String getCurSancYn() {
		return curSancYn;
	}

	public void setCurSancYn(String curSancYn) {
		this.curSancYn = curSancYn;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSancDt() {
		return sancDt;
	}

	public void setSancDt(String sancDt) {
		this.sancDt = sancDt;
	}

	public int getSancLevel() {
		return sancLevel;
	}

	public void setSancLevel(int sancLevel) {
		this.sancLevel = sancLevel;
	}

	public String getSancResult() {
		return sancResult;
	}

	public void setSancResult(String sancResult) {
		this.sancResult = sancResult;
	}

	public String getSancType() {
		return sancType;
	}

	public void setSancType(String sancType) {
		this.sancType = sancType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ExchangeFileVO getBodyXmlFile() {
		return bodyXmlFile;
	}

	public void setBodyXmlFile(ExchangeFileVO bodyXmlFile) {
		this.bodyXmlFile = bodyXmlFile;
	}

	public ExchangeFileVO getBodyXslFile() {
		return bodyXslFile;
	}

	public void setBodyXslFile(ExchangeFileVO bodyXslFile) {
		this.bodyXslFile = bodyXslFile;
	}

	public String getAdminChangeTs() {
		return adminChangeTs;
	}

	public void setAdminChangeTs(String adminChangeTs) {
		this.adminChangeTs = adminChangeTs;
	}

	public String getAdminChangeYn() {
		return adminChangeYn;
	}

	public void setAdminChangeYn(String adminChangeYn) {
		this.adminChangeYn = adminChangeYn;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getResultUrl() {
		return resultUrl;
	}

	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}

	public String getTotalFileInfo() {
		return totalFileInfo;
	}

	public void setTotalFileInfo(String totalFileInfo) {
		this.totalFileInfo = totalFileInfo;
	}

	public String getUseGbnTxt() {
		return useGbnTxt;
	}

	public void setUseGbnTxt(String useGbnTxt) {
		this.useGbnTxt = useGbnTxt;
	}

	public ExchangeVO(){
		this.bodyXmlFile=null;
		this.bodyXslFile=null;
		this.usecaseId=null;
		this.adminChangeYn=null;
		this.adminChangeTs=null;
		this.adminName=null; 
		this.sendUrl=null; 
		this.resultUrl=null;
		this.atchFileCnt=0; 
		this.totalFileInfo=null; 
		this.useGbnTxt=null;
		this.useGbn=null; 
		this.regId=null;
		this.regName=null;
		this.regDeptName=null; 
		this.templateSeq=0; 
		this.usecaseGbn=null;
		this.usecaseGbnTxt=null; 
		this.exchangeSeq=0;
		this.title=null;
		this.body=null;
		this.senderId=null;
		this.senderName=null;
		this.senderDeptName=null;
		this.receiverId=null;
		this.receiverName=null;
		this.receiverDeptName=null; 
		this.regTs=null;
		this.sancStatus=null;
		this.sancStatusCode=null;
		this.sancEndYn=null;
		this.curSancUserId=null;
		this.curSancUserName=null;
		this.curSancUserPosition=null;
		this.curSancUserDeptName=null;
		this.curSancUserOrgName=null;
		this.sancUserList=null; 
		this.atchFileList=null; 
	}

	public String getCurSancUserOrgName() {
		return curSancUserOrgName;
	}

	public void setCurSancUserOrgName(String curSancUserOrgName) {
		this.curSancUserOrgName = curSancUserOrgName;
	}

	public String getCurSancUserDeptName() {
		return curSancUserDeptName;
	}

	public void setCurSancUserDeptName(String curSancUserDeptName) {
		this.curSancUserDeptName = curSancUserDeptName;
	}

	public String getCurSancUserId() {
		return curSancUserId;
	}

	public void setCurSancUserId(String curSancUserId) {
		this.curSancUserId = curSancUserId;
	}

	public String getCurSancUserName() {
		return curSancUserName;
	}

	public void setCurSancUserName(String curSancUserName) {
		this.curSancUserName = curSancUserName;
	}

	public String getCurSancUserPosition() {
		return curSancUserPosition;
	}

	public void setCurSancUserPosition(String curSancUserPosition) {
		this.curSancUserPosition = curSancUserPosition;
	}

	public int getExchangeSeq() {
		return exchangeSeq;
	}

	public void setExchangeSeq(int exchangeSeq) {
		this.exchangeSeq = exchangeSeq;
	}

	public String getRegTs() {
		return regTs;
	}

	public void setRegTs(String regTs) {
		this.regTs = regTs;
	}

	public String getSancEndYn() {
		return sancEndYn;
	}

	public void setSancEndYn(String sancEndYn) {
		this.sancEndYn = sancEndYn;
	}

	public String getSancStatus() {
		return sancStatus;
	}

	public void setSancStatus(String sancStatus) {
		this.sancStatus = sancStatus;
	}

	public String getSancStatusCode() {
		return sancStatusCode;
	}

	public void setSancStatusCode(String sancStatusCode) {
		this.sancStatusCode = sancStatusCode;
	}

	public ArrayList getSancUserList() {
		return sancUserList;
	}

	public void setSancUserList(ArrayList sancUserList) {
		this.sancUserList = sancUserList;
	}

	public String getSenderDeptName() {
		return senderDeptName;
	}

	public void setSenderDeptName(String senderDeptName) {
		this.senderDeptName = senderDeptName;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsecaseGbn() {
		return usecaseGbn;
	}

	public void setUsecaseGbn(String usecaseGbn) {
		this.usecaseGbn = usecaseGbn;
	}

	public String getUsecaseGbnTxt() {
		return usecaseGbnTxt;
	}

	public void setUsecaseGbnTxt(String usecaseGbnTxt) {
		this.usecaseGbnTxt = usecaseGbnTxt;
	}

	public int getTemplateSeq() {
		return templateSeq;
	}

	public void setTemplateSeq(int templateSeq) {
		this.templateSeq = templateSeq;
	}

	public String getRegDeptName() {
		return regDeptName;
	}

	public void setRegDeptName(String regDeptName) {
		this.regDeptName = regDeptName;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getUseGbn() {
		return useGbn;
	}

	public void setUseGbn(String useGbn) {
		this.useGbn = useGbn;
	}

	public ArrayList getAtchFileList() {
		return atchFileList;
	}

	public void setAtchFileList(ArrayList atchFileList) {
		this.atchFileList = atchFileList;
	}

	public String getReceiverDeptName() {
		return receiverDeptName;
	}

	public void setReceiverDeptName(String receiverDeptName) {
		this.receiverDeptName = receiverDeptName;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public int getAtchFileCnt() {
		return atchFileCnt;
	}

	public void setAtchFileCnt(int atchFileCnt) {
		this.atchFileCnt = atchFileCnt;
	}

	public String getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

	public String getUsecaseId() {
		return usecaseId;
	}

	public void setUsecaseId(String usecaseId) {
		this.usecaseId = usecaseId;
	}

	public int getAdminNo() {
		return adminNo;
	}

	public void setAdminNo(int adminNo) {
		this.adminNo = adminNo;
	}

	

}//EOC
