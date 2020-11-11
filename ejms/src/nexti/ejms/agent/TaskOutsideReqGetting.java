/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 외부망 신청서 가져오기 프로세스
 * 설명:
 */
package nexti.ejms.agent;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.sinchung.model.ReqSubBean;
import nexti.ejms.util.Base64;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.HttpClientHp;
import nexti.ejms.util.Utils;

public class TaskOutsideReqGetting extends TaskBase {
	private static TaskOutsideReqGetting _instance;
	public static TaskOutsideReqGetting getInstance() {
		if(_instance == null) { _instance = new TaskOutsideReqGetting(); }
		return _instance;
	}
	private static Logger logger = Logger.getLogger(TaskOutsideReqGetting.class);
	
	private Connection DBConn = null;
	private boolean isConnect = false;

	private void DBConnection() {
		if(!isConnect) {
			AgentUtil.Disconnection(DBConn);
			DBConn = AgentUtil.getConnection();
			if(DBConn==null) isConnect = false;
			else isConnect = true;
		}
	}
	
	public void run() {
		
		if(!getIsRun()) return;
		setLastRunDateTime();
		DBConnection();
		if (!isConnect) {
			AgentUtil.AgentlogError(getLSeq());
			return;
		}
		
		try {
			/**
			 * 신청결과 가져오기 연계
			 * 1. 현재진행중인 외부망 신청양식번호 가져오기
			 * 2. httpclient통신으로 외부망의 결과가져오기 호출
			 * 3. 가져온 결과 XML PARSER
			 * 4. 신청서 내용 INSERT.
			 * 5. 내용INSERT 성공시 외부망 신청내용 삭제
			 */
			//가져온 결과에 대한 삭제처리가 됨으로 모든 결과값을 다가져오면 됨으로 주석처리됨.
			/*******************************************************************************/
			//logger.debug(">> step1: 진행중인 외부망연계 설문번호 가져오기.");
			/*******************************************************************************/
			//List reqList = SinchungManager.instance().getReqOutsideList();
			
			
			if(appInfo.isOutside() == false ){
				setLastRunStat("error 외부망 연계 사용Properties 사용안함처리 되어있습니다.");
				logger.debug(getLastRunStat());
				return;
			}
			
			if("".equals(appInfo.getOutsideurl())){
				setLastRunStat("error 외부망 연계 URL 이 등록되지 않았습니다.");
				logger.debug(getLastRunStat());
				return;
			}
			
			/*******************************************************************************/
			logger.debug(">> step2: 통신으로 외부망의 xml 가져오기.");
			/*******************************************************************************/
			String urlStr = "";
			String xmlcont = "";
			HttpClientHp hcp = null;
			urlStr = appInfo.getOutsideurl()+"/outsideReqResult.do"; 
	        hcp = new HttpClientHp(urlStr); 
	        
	        //가져온 결과에 대한 삭제처리가 됨으로 모든 결과값을 다가져오면 됨으로 주석처리됨.
	        /*hcp.setParam("reqsize", new Integer(reqList.size()).toString());
	        for(int i=0; i<reqList.size(); i++){
	        	ReqMstBean bean = (ReqMstBean)reqList.get(i);
	        	hcp.setParam("reqformno"+i, new Integer(bean.getReqformno()).toString());
	        }*/
	        
	        // setMethodType을 지정하지 않으면 default = 0, (0:get, 1:post) 
	        hcp.setMethodType(1); 
	
	        int rtnCode = hcp.execute(); // 실행하기 
	       
	        if(rtnCode == 200){
	        	xmlcont= hcp.getContent();
	        } else {
	        	setLastRunStat("외부망 통신오류로 인해 처리가 실패하였습니다.");
				logger.debug(getLastRunStat());
				return;
	        }
	        
	        /*******************************************************************************/
	        logger.debug(">> step3: xml PARSER LIST객체에 담기.");
	        /*******************************************************************************/
	        if("-1".equals(xmlcont)){
	        	setLastRunStat("서버키가 일치 하지 않아 외부망처리가 실패하였습니다.");
				logger.debug(getLastRunStat());
				return;
	        }else if("-2".equals(xmlcont)){
	        	setLastRunStat("외부망오류로 인해 처리가 실패하였습니다.");
				logger.debug(getLastRunStat());
				return;
	        }
	        
	        List ansList = xmlParser(xmlcont);
	        
	        if(ansList.size() > 0){
		        //deleteReqAns(DBConn, ansList);
		        /*******************************************************************************/
		        logger.debug(">> step4: 신청내용 저장하기.");
		        /*******************************************************************************/        
		        int cnt = insertReqMst(DBConn, ansList);
		        
		        /*******************************************************************************/
		        logger.debug(">> step5: 외부망 신청 내용 삭제하기.");
		        /*******************************************************************************/
		        if(cnt >0 ){
		        	outsideDel(ansList);
		        	
		        	try {	//전송완료처리
				        hcp = new HttpClientHp(appInfo.getOutsideurl()+"/outsideReqResult.do");
				        hcp.setParam("transmit", "comp");
				        hcp.execute();
		        	} catch ( Exception e ) {e.printStackTrace();}
		        }
		        
				DBConn.commit();
	        }
	        
			setLastRunStat("RUNNING COMPLETE!!!");
			logger.debug(getLastRunStat());
		} catch (Exception e) {
			try { DBConn.rollback(); } catch (Exception ex) { }
			AgentUtil.AgentlogError(getLSeq());
			setLastRunStat("error: "+ e);
			logger.error(getLastRunStat(), e);
		} finally {
			try { if (DBConn != null) { DBConn.close(); } } catch (Exception ex) { }
			isConnect = false;
		}
	}
	
	/**
	 * 신청서 신청내용 외부망에서 가져온XML PARSER해서 LIST담기
	 * @param xmlcont
	 * @return
	 * @throws Exception
	 */
	public List xmlParser(String xmlcont) throws Exception{
		List ansList = null;
		List subList = null;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setIgnoringComments(true);
        
        DocumentBuilder docParser = factory.newDocumentBuilder();
        
        StringReader sr = new StringReader(xmlcont);
        InputSource is = new InputSource(sr); 
        
        Document xmlDoc = docParser.parse(is);
        
        Element root = xmlDoc.getDocumentElement(); 
        NodeList lst = root.getElementsByTagName ("ansInfo");
        
        ansList =  new ArrayList();
        for (int i=0; i<lst.getLength (); i++){
        	ReqMstBean bean = new ReqMstBean();
        	//Node n = lst.item(i);
        	Element element = (Element)lst.item(i); 
        	bean.setReqformno(Integer.parseInt(Base64.decodeString(getData(element,"reqformno"))));
        	bean.setReqseq(Integer.parseInt(Base64.decodeString(getData(element, "reqseq"))));
        	bean.setPresentnm(Base64.decodeString(getData(element, "presentnm")));
        	bean.setPresentid(Base64.decodeString(getData(element, "presentid")));
        	bean.setPresentsn(Base64.decodeString(getData(element, "presentsn")));
        	bean.setPresentbd(Base64.decodeString(getData(element, "presentbd")));
        	bean.setPosition(Base64.decodeString(getData(element, "position")));
        	bean.setDuty(Base64.decodeString(getData(element, "duty")));
        	bean.setZip(Base64.decodeString(getData(element, "zip")));  
        	bean.setAddr1(Base64.decodeString(getData(element, "addr1")));
        	bean.setAddr2(Base64.decodeString(getData(element, "addr2")));
        	bean.setEmail(Base64.decodeString(getData(element, "email")));
        	bean.setTel(Base64.decodeString(getData(element, "tel")));
        	bean.setCel(Base64.decodeString(getData(element, "cel")));
        	bean.setFax(Base64.decodeString(getData(element, "fax")));  
        	bean.setState(Base64.decodeString(getData(element, "state")));
        	bean.setCrtdt(Base64.decodeString(getData(element, "crtdt")));
        	bean.setCrtusrid(Base64.decodeString(getData(element, "crtusrid")));
        	
        	NodeList sublst = element.getElementsByTagName("ansSub");
        	
        	subList = new ArrayList();
        	for(int j=0; j<sublst.getLength(); j++){
        		ReqSubBean subbean = new ReqSubBean();
        		
        		Element subelement = (Element)sublst.item(j);
        		subbean.setReqformno(Integer.parseInt(Base64.decodeString(getData(element,"reqformno"))));
        		subbean.setReqseq(Integer.parseInt(Base64.decodeString(getData(element, "reqseq"))));
        		subbean.setFormseq(Integer.parseInt(Base64.decodeString(getData(subelement, "formseq"))));
        		subbean.setAnscont(Base64.decodeString(getData(subelement, "anscont")));
        		subbean.setOther(Base64.decodeString(getData(subelement, "other")));
        		
        		subList.add(subbean);
        	}
        	bean.setAnscontList(subList);
        	
        	if ( !Utils.nullToEmptyString(Base64.decodeString(getData(element, "fileseq"))).equals("") ) {        	
	        	bean.setFileseq(Integer.parseInt("0" + Base64.decodeString(getData(element, "fileseq"))));
				bean.setFilenm(Base64.decodeString(getData(element, "filenm")));
				bean.setOriginfilenm(Base64.decodeString(getData(element, "originfilenm")));
				bean.setFilesize(Integer.parseInt(Base64.decodeString(getData(element, "filesize"))));
				bean.setExt(Base64.decodeString(getData(element, "ext")));
				bean.setOrd(Integer.parseInt(Base64.decodeString(getData(element, "ord"))));
				bean.setFileToBase64Encoding(getData(element, "filetobase64encoding"));				
        	}
			
        	ansList.add(bean);
        }
       
        return ansList;
	}
	
	/**
	 * XML PARSER된 항목별 값가져오기
	 * @param element
	 * @param tagName
	 * @return
	 */
	public static String getData(Element element, String tagName){ 
		NodeList list = element.getElementsByTagName(tagName); 
		Element cElement = (Element)list.item(0); 

		if(cElement.getFirstChild()!=null){ 
			if(!"null".equals(cElement.getFirstChild().getNodeValue())){
				return cElement.getFirstChild().getNodeValue(); 
			}else{
				return "";
			}
			
		}else{ 
			return "";  
		} 
	}  	
	
	/**
	 * 신청서 신청내용 내부 기존데이터 삭제(로직변경으로 사용않함)
	 * @param DBConn
	 * @param ansList
	 * @throws Exception
	 */
	/*private void deleteReqAns(Connection DBConn, List ansList) throws Exception {
		PreparedStatement pstmt = null;
		StringBuffer deleteQuery = new StringBuffer();
		
		String reqformno = "";
		int prereqformno = 0;
		int x = 0;
		
		try {
			for(int i=0; i<ansList.size(); i++){
				ReqMstBean bean = (ReqMstBean)ansList.get(i);
				
				if(prereqformno != bean.getReqformno()){
					x++;
					if(x !=1){
						reqformno = reqformno +",";
					}
					reqformno = reqformno + bean.getReqformno();
				}
				
				prereqformno = bean.getReqformno();
			}
			deleteQuery.append("\n DELETE FROM REQMST 			");
			deleteQuery.append("\n  WHERE REQFORMNO IN (" + reqformno +	") " );
			
			pstmt = DBConn.prepareStatement(deleteQuery.toString());
			pstmt.executeUpdate();
		
		}finally{
			try { pstmt.close(); } catch (Exception e) { }
		}
	}*/
	
	/**
	 * 신청서 신청내용 내부 추가하기
	 * @param DBConn
	 * @param ansList
	 * @return
	 * @throws Exception
	 */
	private int insertReqMst(Connection DBConn, List ansList) throws Exception {
		PreparedStatement pstmt = null;
		StringBuffer insertQuery = new StringBuffer();
		
		int bindPos = 0;
		int result = 0;
		
		insertQuery.append("\n INSERT INTO");
		insertQuery.append("\n REQMST(REQFORMNO, REQSEQ, PRESENTNM, PRESENTID, PRESENTSN, PRESENTBD, ");
		insertQuery.append("\n		  POSITION, DUTY, ZIP, ADDR1, ADDR2, EMAIL, ");
		insertQuery.append("\n		  TEL, CEL, FAX, STATE, CRTDT, CRTUSRID ) ");
		insertQuery.append("\n SELECT ?, ?, ?, ?, ?, ?,");
		insertQuery.append("\n        ?, ?, ?, ?, ?, ?, ");
		insertQuery.append("\n        ?, ?, ?, ?, ?, ? FROM DUAL ");
		insertQuery.append("\n WHERE NOT EXISTS (SELECT * FROM REQMST WHERE REQFORMNO=? AND REQSEQ=?) ");
		
		try {
			
			pstmt = DBConn.prepareStatement(insertQuery.toString());
			for(int i=0; i<ansList.size(); i++){
				pstmt.clearParameters();
				bindPos=1;
				ReqMstBean bean = (ReqMstBean)ansList.get(i);

				pstmt.setInt(bindPos++, bean.getReqformno());
				pstmt.setInt(bindPos++, bean.getReqseq());
				pstmt.setString(bindPos++, bean.getPresentnm());
				pstmt.setString(bindPos++, bean.getPresentid());
				pstmt.setString(bindPos++, bean.getPresentsn());
				pstmt.setString(bindPos++, bean.getPresentbd());
				pstmt.setString(bindPos++, bean.getPosition());
				pstmt.setString(bindPos++, bean.getDuty());
				pstmt.setString(bindPos++, bean.getZip());
				pstmt.setString(bindPos++, bean.getAddr1());
				pstmt.setString(bindPos++, bean.getAddr2());
				pstmt.setString(bindPos++, bean.getEmail());
				pstmt.setString(bindPos++, bean.getTel());	
				pstmt.setString(bindPos++, bean.getCel());
				pstmt.setString(bindPos++, bean.getFax());
				pstmt.setString(bindPos++, bean.getState());					
				pstmt.setString(bindPos++, bean.getCrtdt());
				pstmt.setString(bindPos++, bean.getCrtusrid());
				pstmt.setInt(bindPos++, bean.getReqformno());
				pstmt.setInt(bindPos++, bean.getReqseq());
				
				insertReqSub(DBConn, bean.getAnscontList());
				
				FileBean fileBean = new FileBean();
				fileBean.setFileseq(bean.getFileseq());
				fileBean.setFilenm(bean.getFilenm());
				fileBean.setOriginfilenm(bean.getOriginfilenm());
				fileBean.setFilesize(bean.getFilesize());
				fileBean.setExt(bean.getExt());
				fileBean.setOrd(bean.getOrd());
				int filecnt = addReqAnsFile(DBConn, bean.getReqformno(), bean.getReqseq(), fileBean);
				
				if ( filecnt > 0 && !"".equals(Utils.nullToEmptyString(bean.getFilenm())) ) {
					try {
						//저장할 디렉토리 지정
						Calendar cal = Calendar.getInstance();
						String saveDir = appInfo.getRequestDataDir() + cal.get(Calendar.YEAR) + "/";
						File saveFolder = new File(appInfo.getRootRealPath() + saveDir);
						if(!saveFolder.exists()) saveFolder.mkdirs();
						
						String file = appInfo.getRootRealPath() + bean.getFilenm();
						String fileData = Utils.nullToEmptyString(bean.getFileToBase64Encoding());
						byte[] fileByte = fileData.getBytes("UTF-8");
						fileByte = Base64.decode(fileByte, 0, fileByte.length);
						
						byte[] buffer = new byte[4096];
						ByteArrayInputStream bais = null;
						BufferedOutputStream bos = null;
						try {
							bais = new ByteArrayInputStream(fileByte);
							bos = new BufferedOutputStream(new FileOutputStream(file));
							int cnt;
							while ( (cnt = bais.read(buffer, 0, buffer.length) ) != -1 ) {
								bos.write(buffer, 0, cnt);
							}
						} catch ( Exception e ) {
							e.printStackTrace();
						} finally {
							try { bais.close(); } catch ( Exception e ) {}
							try { bos.close(); } catch ( Exception e ) {}
						}
					} catch ( Exception e ) {
						e.printStackTrace();
					}
				}
				
				result += pstmt.executeUpdate();
			}		
		
		}finally{
			try { pstmt.close(); } catch (Exception e) { }
		}
		
		return result;
	}		
	
	/**
	 * 신청번호별 답변내용 내부 저장하기
	 * @param DBConn
	 * @param reqseq
	 * @param subList
	 * @throws Exception
	 */
	private void insertReqSub(Connection DBConn, List subList) throws Exception {
		PreparedStatement pstmt1 = null;
		StringBuffer insertQuery = new StringBuffer();
		
		int bindPos = 0;
		
		insertQuery.append("\n INSERT INTO");
		insertQuery.append("\n REQSUB(REQFORMNO, REQSEQ, FORMSEQ, ANSCONT, OTHER) ");
		insertQuery.append("\n SELECT ?, ?, ?, ?, ? FROM DUAL");	
		insertQuery.append("\n WHERE NOT EXISTS (SELECT * FROM REQSUB WHERE REQFORMNO=? AND REQSEQ=? AND FORMSEQ=?) ");
		
		try {
			
			pstmt1 = DBConn.prepareStatement(insertQuery.toString());
			for(int i=0; i<subList.size(); i++){
				pstmt1.clearParameters();
				bindPos=1;
				ReqSubBean bean = (ReqSubBean)subList.get(i);
				
				pstmt1.setInt(bindPos++, bean.getReqformno());
				pstmt1.setInt(bindPos++, bean.getReqseq());
				pstmt1.setInt(bindPos++, bean.getFormseq());
				pstmt1.setString(bindPos++, bean.getAnscont());
				pstmt1.setString(bindPos++, bean.getOther());
				pstmt1.setInt(bindPos++, bean.getReqformno());
				pstmt1.setInt(bindPos++, bean.getReqseq());
				pstmt1.setInt(bindPos++, bean.getFormseq());
				
				pstmt1.executeUpdate();
			}		
		
		}finally{
			try { pstmt1.close(); } catch (Exception e) { }
		}
	}
	
	/**
	 * 신청내역첨부파일 추가
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param fileBean
	 * @return
	 * @throws Exception
	 */
	public int addReqAnsFile(Connection DBConn, int reqformno, int reqseq, FileBean fileBean) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
							
			sql.append("INSERT INTO \n");
			sql.append("REQMSTFILE(REQFORMNO, REQSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
			sql.append("SELECT ?, ?, ?, ?, ?, ?, ?, ? FROM DUAL \n");
			sql.append("WHERE NOT EXISTS (SELECT * FROM REQMSTFILE WHERE REQFORMNO=? AND REQSEQ=? AND FILESEQ=?) \n");
			
			pstmt = DBConn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			pstmt.setInt(3, fileBean.getFileseq());
			pstmt.setString(4, fileBean.getFilenm());
			pstmt.setString(5, fileBean.getOriginfilenm());
			pstmt.setInt(6, fileBean.getFilesize());
			pstmt.setString(7, fileBean.getExt());
			pstmt.setInt(8, fileBean.getFileseq());
			pstmt.setInt(9, reqformno);
			pstmt.setInt(10, reqseq);
			pstmt.setInt(11, fileBean.getFileseq());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 신청서 신청내용 성공시 외부 신청서 신청내용 삭제하기
	 * @param ansList
	 * @throws Exception
	 */
	private void outsideDel(List ansList) throws Exception {
		/* 신청내역은 신청서 삭제시 일괄 삭제
		String urlStr = "";
		HttpClientHp hcp = null;
		urlStr = appInfo.getOutsideurl()+"/outsideReqAnsDel.do"; 
        hcp = new HttpClientHp(urlStr); 

        hcp.setParam("listsize", new Integer(ansList.size()).toString());
		for(int i=0; i<ansList.size(); i++){
			ReqMstBean bean = (ReqMstBean)ansList.get(i);
			
			hcp.setParam("reqformno"+i, new Integer(bean.getReqformno()).toString());
			hcp.setParam("reqseq"+i, new Integer(bean.getReqseq()).toString());
		}
		
		//setMethodType을 지정하지 않으면 default = 0, (0:get, 1:post) 
        hcp.setMethodType(1); 

        int rtnCode = hcp.execute(); // 실행하기 
        
        if(rtnCode != 200){
        	setLastRunStat("외부망 통신오류로 인해 처리가 실패하였습니다.");
			logger.debug(getLastRunStat());
        }
		*/
	}		
}
