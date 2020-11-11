/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 외부망 설문조사가져오기 프로세스
 * 설명:
 */
package nexti.ejms.agent;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import nexti.ejms.common.appInfo;
import nexti.ejms.research.model.ResearchAnsBean;
import nexti.ejms.util.Base64;
import nexti.ejms.util.HttpClientHp;

public class TaskOutsideRchGetting extends TaskBase {
	private static TaskOutsideRchGetting _instance;
	public static TaskOutsideRchGetting getInstance() {
		if(_instance == null) { _instance = new TaskOutsideRchGetting(); }
		return _instance;
	}
	private static Logger logger = Logger.getLogger(TaskOutsideRchGetting.class);
	
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
			 * 설문결과 및 신청결과 가져오기 연계
			 * 1. 현재진행중인 외부망 설문번호 가져오기
			 * 2. httpclient통신으로 외부망의 결과가져오기 호출
			 * 3. 가져온 결과 XML PARSER
			 * 4. 설문결과 INSERT.
			 * 5. 설문결과 INSERT성공시 외부망 설문결과 자료 삭제
			 */
			//가져온 결과에 대한 삭제처리가 됨으로 모든 결과값을 다가져오면 됨으로 주석처리됨.
			/*******************************************************************************/
			//logger.debug(">> step1: 진행중인 외부망연계 설문번호 가져오기.");
			/*******************************************************************************/
			//List rchList = ResearchManager.instance().getRchOutsideList();
			
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
			urlStr = appInfo.getOutsideurl()+"/outsideRchResult.do"; 
	        hcp = new HttpClientHp(urlStr);
	        
	        //가져온 결과에 대한 삭제처리가 됨으로 모든 결과값을 다가져오면 됨으로 주석처리됨.
	        //hcp.setParam("rchsize", new Integer(rchList.size()).toString());
	        //for(int i=0; i<rchList.size(); i++){
	        //	ResearchAnsBean bean = (ResearchAnsBean)rchList.get(i);
	        //	hcp.setParam("rchno"+i, new Integer(bean.getRchno()).toString());
	        //}
	        
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
 
	        if(ansList.size() >= 1){
		        /*******************************************************************************/
		        logger.debug(">> step4: ANSDATA저장.");
		        /*******************************************************************************/
		        //deleteRchAns(DBConn, ansList);
		        
		        int cnt = insertRchAns(DBConn, ansList);
		        
		        /*******************************************************************************/
		        logger.debug(">> step5: 외부 ANSDATA삭제.");
		        /*******************************************************************************/
		        if(cnt >0 ){
		        	outsideDel(ansList);
		        	
		        	try {	//전송완료처리
				        hcp = new HttpClientHp(appInfo.getOutsideurl()+"/outsideRchResult.do");
				        hcp.setParam("transmit", "comp");
				        hcp.execute();
		        	} catch ( Exception e ) {}
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
	 * 설문조사 결과 외부망에서 xml로 가져와 PARSER하여 LIST 에 담기
	 * @param xmlcont
	 * @return
	 * @throws Exception
	 */
	public List xmlParser(String xmlcont) throws Exception{
		List ansList  = null;

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
        	ResearchAnsBean bean = new ResearchAnsBean();
        	//Node n = lst.item(i);
        	Element element = (Element)lst.item(i); 
        	bean.setRchno(Integer.parseInt(Base64.decodeString(getData(element,"rchno"))));
        	bean.setAnsusrseq(Integer.parseInt(Base64.decodeString(getData(element, "ansusrseq"))));
        	bean.setFormseq(Integer.parseInt(Base64.decodeString(getData(element, "formseq"))));
        	bean.setAnscont(Base64.decodeString(getData(element, "anscont")));
        	bean.setOther(Base64.decodeString(getData(element, "other")));
        	bean.setCrtdt(Base64.decodeString(getData(element, "crtdt")));
        	bean.setCrtusrid(Base64.decodeString(getData(element, "crtusrid")));
        	bean.setCrtusrnm(Base64.decodeString(getData(element, "crtusrnm")));
        	ansList.add(bean);
        }

        return ansList;
	}
	
	/**
	 * 설문조사 결과XML파서하여 각 항목별 값가져오기
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
	 * 설문자료 결과 내용 내부자료 삭제(기존로직변경으로 사용안함)
	 * @param DBConn
	 * @param ansList
	 * @throws Exception
	 */
	/*private void deleteRchAns(Connection DBConn, List ansList) throws Exception {
		PreparedStatement pstmt = null;
		StringBuffer deleteQuery = new StringBuffer();
		
		String rchno = "";
		int prerchno = 0;
		int x = 0;
		
		try {
			for(int i=0; i<ansList.size(); i++){
				ResearchAnsBean bean = (ResearchAnsBean)ansList.get(i);
				
				if(prerchno != bean.getRchno()){
					x++;
					if(x !=1){
						rchno = rchno +",";
					}
					rchno = rchno + bean.getRchno();
				}
				
				prerchno = bean.getRchno();
			}
			deleteQuery.append("\n DELETE FROM RCHANS 			");
			deleteQuery.append("\n  WHERE RCHNO IN (" + rchno +	") " );
			
			pstmt = DBConn.prepareStatement(deleteQuery.toString());
			pstmt.executeUpdate();
		}finally{
			try { pstmt.close(); } catch (Exception e) { }
		}
	}*/
	
	/**
	 * 설문조사 결과 내용 내부 추가하기
	 * @param DBConn
	 * @param ansList
	 * @return
	 * @throws Exception
	 */
	private int insertRchAns(Connection DBConn, List ansList) throws Exception {
		PreparedStatement pstmt = null;
		StringBuffer insertQuery = new StringBuffer();
		
		int bindPos = 0;
		int result = 0;
		
		insertQuery.append("\n INSERT INTO");
		insertQuery.append("\n RCHANS(RCHNO, ANSUSRSEQ, FORMSEQ, ANSCONT, OTHER, CRTDT, CRTUSRID, CRTUSRNM)");
		insertQuery.append("\n SELECT ?, ?, ?, ?, ?, ?, ?, ? FROM DUAL");
		insertQuery.append("\n WHERE NOT EXISTS (SELECT * FROM RCHANS WHERE RCHNO=? AND ANSUSRSEQ=? AND FORMSEQ=?) ");
		
		try {
			
			pstmt = DBConn.prepareStatement(insertQuery.toString());
			for(int i=0; i<ansList.size(); i++){
				pstmt.clearParameters();
				bindPos=1;
				ResearchAnsBean bean = (ResearchAnsBean)ansList.get(i);
				
				pstmt.setInt(bindPos++, bean.getRchno());
				pstmt.setInt(bindPos++, bean.getAnsusrseq());
				pstmt.setInt(bindPos++, bean.getFormseq());
				pstmt.setString(bindPos++, bean.getAnscont());
				pstmt.setString(bindPos++, bean.getOther());
				pstmt.setString(bindPos++, bean.getCrtdt());
				pstmt.setString(bindPos++, bean.getCrtusrid());
				pstmt.setString(bindPos++, bean.getCrtusrnm());
				pstmt.setInt(bindPos++, bean.getRchno());
				pstmt.setInt(bindPos++, bean.getAnsusrseq());
				pstmt.setInt(bindPos++, bean.getFormseq());
				
				result += pstmt.executeUpdate();
			}	
			
			updateMst(DBConn, ansList);
		}finally{
			try { pstmt.close(); } catch (Exception e) { }
		}
		
		return result;
	}
	
	/**
	 * 설문조사 결과 추가시 내부 설문마스터 신청자수 업데이트
	 * @param DBConn
	 * @param rchno
	 * @param cnt
	 * @throws Exception
	 */
	private int updateMst(Connection DBConn, List ansList) throws Exception {
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		StringBuffer selectQuery = new StringBuffer();
		StringBuffer updateQuery = new StringBuffer();
		int count = 0;
		int result = 0;
		
		int prerchno = 0;
		
		selectQuery.append("\n SELECT COUNT(DISTINCT ANSUSRSEQ) FROM RCHANS WHERE RCHNO = ? ");
		updateQuery.append("\n UPDATE RCHMST SET ANSCOUNT = ? WHERE RCHNO = ? "); 
		
		try{
			for(int i=0; i<ansList.size(); i++){
				ResearchAnsBean bean = (ResearchAnsBean)ansList.get(i);
				
				if(prerchno != bean.getRchno()){
					pstmt2 = DBConn.prepareStatement(selectQuery.toString());
					pstmt2.setInt(1, bean.getRchno());
					
					rs1 = pstmt2.executeQuery();
					
					if ( rs1.next() ){
						count = rs1.getInt(1);
					}
					pstmt2.executeUpdate();			
					if (pstmt2 != null){
						try {
							rs1.close();
							pstmt2.close();
							pstmt2 = null;
						} catch(SQLException ignored){ }
					}
					
					pstmt2 = DBConn.prepareStatement(updateQuery.toString());
					pstmt2.setInt(1, count);
					pstmt2.setInt(2, bean.getRchno());
					
					result += pstmt2.executeUpdate();
					
					pstmt2.executeUpdate();			
					if (pstmt2 != null){
						try {
							pstmt2.close();
							pstmt2 = null;
						} catch(SQLException ignored){ }
					}
					
				}
				
				prerchno = bean.getRchno();
				
			}
			
				
		}finally{
			try { pstmt2.close(); } catch (Exception e) { }
		}
		
		return result;
	}	
	
	/**
	 * 설문조사 결과 외부 자료 삭제하
	 * @param ansList
	 * @throws Exception
	 */
	private void outsideDel(List ansList) throws Exception {
		/* 답변은 설문조사 삭제시 일괄 삭제
		String urlStr = "";
		HttpClientHp hcp = null;
		urlStr = appInfo.getOutsideurl()+"/outsideRchAnsDel.do"; 
        hcp = new HttpClientHp(urlStr); 

        hcp.setParam("listsize", new Integer(ansList.size()).toString());
		for(int i=0; i<ansList.size(); i++){
			ResearchAnsBean bean = (ResearchAnsBean)ansList.get(i);
			
			hcp.setParam("rchno"+i, new Integer(bean.getRchno()).toString());
			hcp.setParam("ansusrseq"+i, new Integer(bean.getAnsusrseq()).toString());
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
