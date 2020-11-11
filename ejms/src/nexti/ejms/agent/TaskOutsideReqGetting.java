/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ý��ۿ�����Ʈ �ܺθ� ��û�� �������� ���μ���
 * ����:
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
			 * ��û��� �������� ����
			 * 1. ������������ �ܺθ� ��û��Ĺ�ȣ ��������
			 * 2. httpclient������� �ܺθ��� ����������� ȣ��
			 * 3. ������ ��� XML PARSER
			 * 4. ��û�� ���� INSERT.
			 * 5. ����INSERT ������ �ܺθ� ��û���� ����
			 */
			//������ ����� ���� ����ó���� ������ ��� ������� �ٰ������� ������ �ּ�ó����.
			/*******************************************************************************/
			//logger.debug(">> step1: �������� �ܺθ����� ������ȣ ��������.");
			/*******************************************************************************/
			//List reqList = SinchungManager.instance().getReqOutsideList();
			
			
			if(appInfo.isOutside() == false ){
				setLastRunStat("error �ܺθ� ���� ���Properties ������ó�� �Ǿ��ֽ��ϴ�.");
				logger.debug(getLastRunStat());
				return;
			}
			
			if("".equals(appInfo.getOutsideurl())){
				setLastRunStat("error �ܺθ� ���� URL �� ��ϵ��� �ʾҽ��ϴ�.");
				logger.debug(getLastRunStat());
				return;
			}
			
			/*******************************************************************************/
			logger.debug(">> step2: ������� �ܺθ��� xml ��������.");
			/*******************************************************************************/
			String urlStr = "";
			String xmlcont = "";
			HttpClientHp hcp = null;
			urlStr = appInfo.getOutsideurl()+"/outsideReqResult.do"; 
	        hcp = new HttpClientHp(urlStr); 
	        
	        //������ ����� ���� ����ó���� ������ ��� ������� �ٰ������� ������ �ּ�ó����.
	        /*hcp.setParam("reqsize", new Integer(reqList.size()).toString());
	        for(int i=0; i<reqList.size(); i++){
	        	ReqMstBean bean = (ReqMstBean)reqList.get(i);
	        	hcp.setParam("reqformno"+i, new Integer(bean.getReqformno()).toString());
	        }*/
	        
	        // setMethodType�� �������� ������ default = 0, (0:get, 1:post) 
	        hcp.setMethodType(1); 
	
	        int rtnCode = hcp.execute(); // �����ϱ� 
	       
	        if(rtnCode == 200){
	        	xmlcont= hcp.getContent();
	        } else {
	        	setLastRunStat("�ܺθ� ��ſ����� ���� ó���� �����Ͽ����ϴ�.");
				logger.debug(getLastRunStat());
				return;
	        }
	        
	        /*******************************************************************************/
	        logger.debug(">> step3: xml PARSER LIST��ü�� ���.");
	        /*******************************************************************************/
	        if("-1".equals(xmlcont)){
	        	setLastRunStat("����Ű�� ��ġ ���� �ʾ� �ܺθ�ó���� �����Ͽ����ϴ�.");
				logger.debug(getLastRunStat());
				return;
	        }else if("-2".equals(xmlcont)){
	        	setLastRunStat("�ܺθ������� ���� ó���� �����Ͽ����ϴ�.");
				logger.debug(getLastRunStat());
				return;
	        }
	        
	        List ansList = xmlParser(xmlcont);
	        
	        if(ansList.size() > 0){
		        //deleteReqAns(DBConn, ansList);
		        /*******************************************************************************/
		        logger.debug(">> step4: ��û���� �����ϱ�.");
		        /*******************************************************************************/        
		        int cnt = insertReqMst(DBConn, ansList);
		        
		        /*******************************************************************************/
		        logger.debug(">> step5: �ܺθ� ��û ���� �����ϱ�.");
		        /*******************************************************************************/
		        if(cnt >0 ){
		        	outsideDel(ansList);
		        	
		        	try {	//���ۿϷ�ó��
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
	 * ��û�� ��û���� �ܺθ����� ������XML PARSER�ؼ� LIST���
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
	 * XML PARSER�� �׸� ����������
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
	 * ��û�� ��û���� ���� ���������� ����(������������ ������)
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
	 * ��û�� ��û���� ���� �߰��ϱ�
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
						//������ ���丮 ����
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
	 * ��û��ȣ�� �亯���� ���� �����ϱ�
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
	 * ��û����÷������ �߰�
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
	 * ��û�� ��û���� ������ �ܺ� ��û�� ��û���� �����ϱ�
	 * @param ansList
	 * @throws Exception
	 */
	private void outsideDel(List ansList) throws Exception {
		/* ��û������ ��û�� ������ �ϰ� ����
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
		
		//setMethodType�� �������� ������ default = 0, (0:get, 1:post) 
        hcp.setMethodType(1); 

        int rtnCode = hcp.execute(); // �����ϱ� 
        
        if(rtnCode != 200){
        	setLastRunStat("�ܺθ� ��ſ����� ���� ó���� �����Ͽ����ϴ�.");
			logger.debug(getLastRunStat());
        }
		*/
	}		
}
