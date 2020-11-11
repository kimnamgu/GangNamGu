package nexti.ejms.elecAppr.exchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import nexti.ejms.common.appInfo;
import nexti.ejms.util.Utils;

/**
 * 전자결재 연계 모듈  
 * 버전 : 1.0
 * 작성일 : 2007-03-08
 * 작성내용 : 2006년 2월 개정판 표준 연계 API를 근거로 작성되었습니다.
 */
public class Exchange {
	
	private String tableName; 					//테이블명
	private ArrayList primaryKeyList; 			//식별자 리스트
	private ArrayList attachFileList;			//첨부파일 리스트
	private String senderSystemName; 			//전송서버 이름
	private String senderServerId; 				//전송서버 아이디 
	private String senderId; 					//전송유저 아이디 
	private String senderName;					//전송유저명
	private String senderDeptName;				//전송유저 부서명
	private String receiverServerId; 			//수신서버 아이디 
	private String receiverId; 					//수신유저 아이디
	private String receiverName;				//수신유저명
	private String receiverDeptName; 			//수신유저부서명 
	private String createDateTag; 				//작성일자 
	private String createDateFileName; 			//작성일자 
	private String sendTempDir; 				//임시 전송디렉토리 
	private String title; 						//제목 
	private String content; 					//내용 
	private int adminNo; 						//관리번호 
	private String mailAddress; 				//메일 주소 
	private String orgName; 					//기관명 
	private int primaryKey; 					
	private String usecaseGbn; 					//업무구분 
	private String resultUrl; 
	private ExchangeFileVO bodyXmlFile;
	private ExchangeFileVO bodyXslFile;
	
	public int getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}
	public Exchange(ExchangeVO exchangeVO) throws Exception{
		this.bodyXmlFile=exchangeVO.getBodyXmlFile();
		this.bodyXslFile=exchangeVO.getBodyXslFile();
		this.tableName=null; 
		this.primaryKeyList=null;
		this.primaryKey=0; 
		this.usecaseGbn=exchangeVO.getUsecaseGbn();
		this.resultUrl=exchangeVO.getResultUrl();
		this.adminNo=exchangeVO.getAdminNo();
		this.title=exchangeVO.getTitle();
		this.content=exchangeVO.getBody();
		this.senderId=exchangeVO.getSenderId();
		this.receiverId=exchangeVO.getReceiverId();
		this.attachFileList=exchangeVO.getAtchFileList();
		this.createDateTag=Utils.getToday("yyyy-MM-dd HH:mm:ss"); //태그에서 사용할 일자 
		this.createDateFileName=Utils.getToday("yyyyMMddHHmmss"); //파일명에서 사용할 일자 
		this.loadResource();
		
//		this.bodyXmlFile=exchangeVO.getBodyXmlFile();
//		this.bodyXslFile=exchangeVO.getBodyXslFile();
//		this.tableName=null; 
//		this.primaryKeyList=null;
//		this.primaryKey=0; 
//		this.usecaseGbn=exchangeVO.getUsecaseGbn();
//		this.resultUrl=exchangeVO.getResultUrl();
//		this.adminNo=this.getAdminNo(); 
//		this.title=exchangeVO.getTitle();
//		this.content=exchangeVO.getBody();
//		this.senderId=ExchangeMgr.getInstance().selectEmpCode(exchangeVO.getSenderId());
//		this.receiverId=ExchangeMgr.getInstance().selectEmpCode(exchangeVO.getReceiverId());
//		this.attachFileList=exchangeVO.getAtchFileList();
//		this.createDateTag=Utils.getToday("yyyy-MM-dd HH:mm:ss"); //태그에서 사용할 일자 
//		this.createDateFileName=Utils.getToday("yyyyMMddHHmmss"); //파일명에서 사용할 일자 
//		this.loadResource();
	}//Con

	/**
	 * 최종 전송한다.
	 */
	public int send(){
		/**************************************
		 * 1. 연계 API에 맞도록 디렉토리를 작성한다.
		 **************************************/
		File dir = new File(sendTempDir);
		dir.mkdirs();
		/**************************************
		 * 2. 실제 전송 ( exchange.xml -> header.inf -> 첨부파일 -> eof.inf
		 **************************************/
		this.makeExchangeXml();
		this.makeHeaderInf();
		this.makeAttachFile();  
		this.makeXML_XSLFile(this.bodyXmlFile,"xml");
		this.makeXML_XSLFile(this.bodyXslFile,"xsl");
		this.makeEofInf();
		
		/**************************************
		 * 3.일련번호 리턴
		 **************************************/
		return this.adminNo; 
	}//send() 
	
	
	/**
	 * 첨부파일을 작성한다. 
	 */
	private void makeAttachFile(){
		if(attachFileList!=null){
			for(int i=0; i<attachFileList.size(); i++){
				ExchangeFileVO file=(ExchangeFileVO)attachFileList.get(i); 
				 try {
					  File f=new File(file.getSaveDir()+File.separator+file.getPhyclFileName()+"."+file.getFileExt());
		              FileInputStream fis  = new FileInputStream(f);
		              FileOutputStream fos = new FileOutputStream(sendTempDir+"/attach_"+file.getLogclFileName());
		              byte [] barray=new byte[4096];
		              int c=0;
		              while((c=fis.read(barray))!=-1){
		            	  fos.write(barray,0,c);
		            	  fos.flush();
		              }//while
		              fis.close();
		              fos.close();
		              
		              try {
		            	  Runtime.getRuntime().exec("chmod 664 " + sendTempDir+"/attach_"+file.getLogclFileName());
		              } catch (Exception e) {}
		           } catch (Exception e) {
		               e.printStackTrace();
		           }
			}//for
		}//if
	}//makeAttachFile()
	
	/**
	 * 첨부파일을 작성한다. 
	 */
	private void makeXML_XSLFile(ExchangeFileVO file, String type){
		if(file==null)
			return;
		
		try {
			File f=new File(file.getSaveDir()+File.separator+file.getPhyclFileName()+"."+file.getFileExt());			
			FileInputStream fis  = new FileInputStream(f);
//			FileOutputStream fos = new FileOutputStream(sendTempDir+"/attach"+type+"_"+file.getPhyclFileName()+"."+file.getFileExt());
			System.out.println("hello=-----------");
			FileOutputStream fos = new FileOutputStream(sendTempDir+"/attach"+type+"_"+file.getLogclFileName());
			byte [] barray=new byte[4096];
			int c=0;
			while((c=fis.read(barray))!=-1){
				fos.write(barray,0,c);
				fos.flush();
			}//while
			fis.close();
			fos.close();
			try {
				Runtime.getRuntime().exec("chmod 664 " + sendTempDir+"/attach_"+file.getLogclFileName());
			} catch (Exception e) {}
		} catch (Exception e) {
			e.printStackTrace();
		}//catch
	}//makeAttachFile()
	
	/**
	 * 고유 키값을 가져온다.  
	 */
//	private int getAdminNo(){
//		Connection con=null; 
//		PreparedStatement pstmt=null; 
//		ResultSet rs=null;
//		String query="SELECT EXCHANGE_SEQ.NEXTVAL FROM DUAL";
//		int seq=0; 
//		try{
//			con=ConnectionManager.getConnection();			
//			pstmt=con.prepareStatement(query); 
//			rs=pstmt.executeQuery(); 
//			
//			rs.next();
//			seq=rs.getInt(1);
//		}catch(Exception e){
//			e.printStackTrace(); 
//		}finally{
//			if(rs!=null)
//				try{
//					rs.close();
//				}catch(Exception e){}
//			if(pstmt!=null)
//				try{
//					pstmt.close(); 
//				}catch(Exception e){}
//			if(con!=null)
//				try{
//					con.close(); 
//				}catch(Exception e){}
//		}//finally
//		
//		return seq; 
//	}//getAdminNo
	
	
	/**
	 * 리소스 번들을 로딩한다. 
	 */
	private void loadResource() throws Exception{
		ResourceBundle resource=ResourceBundle.getBundle(ExchangeCmd.RESOURCE_FILE_NAME);
		this.senderSystemName=appInfo.getAppName();
		this.senderServerId=resource.getString("sender_server_id"); 
		this.receiverServerId=resource.getString("receiver_server_id");
		this.sendTempDir=resource.getString("exchange_send_root_dir")+receiverId+senderServerId+receiverServerId+createDateFileName+"00001";
		this.mailAddress=resource.getString("main_address");
		this.orgName="EJMS";
	}//loadResource()
	
	/**
	 * exchange xml 을 작성한다. 
	 */
	public void makeExchangeXml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>\n");
        sb.append("<!DOCTYPE EXCHANGE SYSTEM \"exchange.dtd\">\n");
        sb.append("<EXCHANGE>\n");
        sb.append("<HEADER>\n");
        sb.append("   <COMMON>\n");
        sb.append("      <SENDER>\n");
        sb.append("         <SERVERID>"+senderServerId+"</SERVERID>\n");
        sb.append("         <USERID>"+senderId+"</USERID>\n");
        sb.append("      </SENDER>\n");
        sb.append("      <RECEIVER>\n");
        sb.append("         <SERVERID>"+receiverServerId+"</SERVERID>\n");
        sb.append("         <USERID>"+receiverId+"</USERID>\n");
        sb.append("      </RECEIVER>\n");
        sb.append("      <TITLE><![CDATA[\n");
        sb.append(title);
        sb.append("]]></TITLE>\n");
        sb.append("      <CREATED_DATE>"+createDateTag+"</CREATED_DATE>\n");
        sb.append("      <ATTACHNUM>"+(attachFileList != null ? attachFileList.size() : 0 )+"</ATTACHNUM>\n");
        sb.append("      <ADMINISTRATIVE_NUM>"+adminNo+"</ADMINISTRATIVE_NUM>\n");
        sb.append("   </COMMON>\n");
        sb.append("   <DIRECTION>\n");
        sb.append("      <TO_DOCUMENT_SYSTEM notification=\"all\">\n");
        sb.append("         <MODIFICATION_FLAG>\n");
        sb.append("            <MODIFIABLE modifyflag=\"yes\"/>\n");
        sb.append("         </MODIFICATION_FLAG>\n");
        sb.append("      </TO_DOCUMENT_SYSTEM>\n");
        sb.append("   </DIRECTION>\n");
//        sb.append("   <ADDENDA>\n");
//        sb.append("      <ADDENDUM name=\"APV_DOC_NO\" comment=\"\">"+adminNo+"</ADDENDUM>\n");
//        if(StringUtils.isNotEmpty(this.usecaseGbn)){
//	        sb.append("      <ADDENDUM name=\"USECASE_GBN\" comment=\"USECASE_GBN\">"+this.usecaseGbn+"</ADDENDUM>\n");
//        }
//        if(StringUtils.isNotEmpty(this.resultUrl)){
//	        sb.append("      <ADDENDUM name=\"RESULT_URL\" comment=\"RESULT_URL\">"+this.resultUrl+"</ADDENDUM>\n");
//        }
//        sb.append("   </ADDENDA>\n");
        sb.append("</HEADER>\n");
        sb.append("<BODY><![CDATA[\n");
        sb.append(content);
        sb.append("]]>\n");
        sb.append("</BODY>\n");
        sb.append("<ATTACHMENTS>\n");
        sb.append("   <ADMINISTRATIVE_DB>\n");
        if(this.bodyXmlFile!=null && this.bodyXslFile!=null){
        	/*sb.append("	  <XMLFILE filename=\""+bodyXmlFile.getPhyclFileName()+"\">xml파일</XMLFILE>");
        	sb.append("	  <XSLFILE filename=\""+bodyXslFile.getPhyclFileName()+"\">xsl파일</XSLFILE>"); */       	
        	sb.append("	  <XMLFILE filename=\"sample.xml\">xml파일</XMLFILE>\n");
        	sb.append("	  <XSLFILE filename=\"sample.xsl\">xsl파일</XSLFILE>\n");
        }
        sb.append("   </ADMINISTRATIVE_DB>\n");

        // 첨부파일 수 만큼 출력
        for (int i = 0 ; i < ( attachFileList!= null?attachFileList.size(): 0 )  ; i++) {
        	ExchangeFileVO file=(ExchangeFileVO)attachFileList.get(i); 
            sb.append("   <ATTACHMENT filename=\""+file.getLogclFileName()+"\"></ATTACHMENT>\n");
        }
        sb.append("</ATTACHMENTS>\n");
        sb.append("</EXCHANGE>");
        String xmlStr = sb.toString();

        try {
            // 문자열을 바이트 배열로 변환
            byte [] barray = xmlStr.getBytes();
            FileOutputStream fo = new FileOutputStream(sendTempDir+"/exchange.xml");
            for(int i=0; i<barray.length; i++) {
              fo.write(barray[i]);
            }
            fo.flush(); 
            fo.close();
            try {
	            Runtime.getRuntime().exec("chmod 775 " + sendTempDir);
	            Runtime.getRuntime().exec("chmod 664 " + sendTempDir+"/exchange.xml");
            } catch (Exception e) {}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * header.inf 를 작성한다. 
	 *
	 */
    public void makeHeaderInf() {
        StringBuffer sb = new StringBuffer();
        sb.append("\ntype=send");
        sb.append("\ndate="+createDateTag);
        sb.append("\nsender="+senderServerId);
        sb.append("\nreceiver="+receiverServerId);
        sb.append("\nsender_userid="+senderId);
        sb.append("\nreceiver_userid="+receiverId);
        sb.append("\nsender_email= ");
        sb.append("\nsender_orgname="+orgName);
        sb.append("\nsender_systemname="+senderSystemName);
        sb.append("\nadministrative_num="+adminNo);
        
        String xmlStr = sb.toString();

        try {
            // 문자열을 바이트 배열로 변환
            byte [] barray = xmlStr.getBytes();

            FileOutputStream fo = new FileOutputStream(sendTempDir+"/header.inf");
            for(int i=0; i<barray.length; i++) {
              fo.write(barray[i]);
            }
            fo.flush(); 
            fo.close();
            try {
            	Runtime.getRuntime().exec("chmod 664 " + sendTempDir+"/header.inf");
            } catch (Exception e) {}
        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }
    
    /**
     * eof.inf를 작성한다. 
     */
    public void makeEofInf() {
        try {
           File eofinf = new File(sendTempDir+"/eof.inf");
           eofinf.createNewFile();
           try {
        	   Runtime.getRuntime().exec("chmod 664 " + sendTempDir+"/eof.inf");
           } catch (Exception e) {}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
    
    public ArrayList getAttachFileList() {
		return attachFileList;
	}

	public void setAttachFileList(ArrayList attachFileList) {
		this.attachFileList = attachFileList;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateDateFileName() {
		return createDateFileName;
	}

	public void setCreateDateFileName(String createDateFileName) {
		this.createDateFileName = createDateFileName;
	}

	public String getCreateDateTag() {
		return createDateTag;
	}

	public void setCreateDateTag(String createDateTag) {
		this.createDateTag = createDateTag;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setAdminNo(int adminNo) {
		this.adminNo = adminNo;
	}

	public ArrayList getPrimaryKeyList() {
		return primaryKeyList;
	}

	public void setPrimaryKeyList(ArrayList primaryKeyList) {
		this.primaryKeyList = primaryKeyList;
	}

	public String getReceiverServerId() {
		return receiverServerId;
	}

	public void setReceiverServerId(String receiverServerId) {
		this.receiverServerId = receiverServerId;
	}

	public String getSenderServerId() {
		return senderServerId;
	}

	public void setSenderServerId(String senderServerId) {
		this.senderServerId = senderServerId;
	}

	public String getSendTempDir() {
		return sendTempDir;
	}

	public void setSendTempDir(String sendTempDir) {
		this.sendTempDir = sendTempDir;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setTableInfo(String tableName,String primaryKeyName, int primaryKey){
		this.tableName=tableName;
		this.primaryKey=primaryKey; 
	}

	public String getUsecaseGbn() {
		return usecaseGbn;
	}

	public void setUsecaseGbn(String usecaseGbn) {
		this.usecaseGbn = usecaseGbn;
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

	public String getResultUrl() {
		return resultUrl;
	}

	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}
}//EOC
