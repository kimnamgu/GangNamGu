package nexti.ejms.agent;


import java.io.StringReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
//import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import com.gpki.gpkiapi.GpkiApi;
import com.gpki.gpkiapi.cert.X509Certificate;
import com.gpki.gpkiapi.cms.EnvelopedData;
import com.gpki.gpkiapi.crypto.PrivateKey;
import com.gpki.gpkiapi.storage.Disk;
import com.gpki.gpkiapi.util.Base64;
import com.gpki.gpkiapi.util.Ldap;

import nexti.ejms.common.appInfo;


/**
 * �������� ������ ���� UTIL
 * - �׽�Ʈ ������ �Žð�(����)�� ��⵿�ǹǷ� �������κ��� �� 15~20�а� ���� �̿��� ���ѵ�(�����߻�)
 * - ��ȣȭ�� ����Ÿ�� base64�� encode�� �ؾ� �ϸ�, ��ȣȭ�Ҷ����� base64�� decode�� ���� �� ������ �ؾ� ��
 *
 * @author <a href="mailto:robin@srpost.co.kr">Kim, nan-gyu</a>
 * @version $Id: MgrBean.java 1128 2010-03-10 13:04:13Z robin $
 */
public class TaskWsUsrDeptUtil {
    
    private static Logger logger = Logger.getLogger(TaskWsUsrDeptUtil.class);
    
    // GPKI ������ �� ���̼��� ���� ���
    private static final String GPKI_PATH = appInfo.getWsSaeallgpkilic();
    private static final String GPKI_ID = appInfo.getWsSaeallgpkiid();
    // GPKI LDAP ���� URL
    private static final String GPKI_LDAP_SERVER = appInfo.getWsSaeallgpkildapserver();
    private static final String SAEALL_GPKI_PATH = appInfo.getWsSaeallgpkipath();
    //���� ���߼��� GPKI ID
    private static final String SAEALL_GPKI_ID = appInfo.getWsSaeallsaeallgpkiid();
    //���� GPKI ����Ű
    private static final String SAEALL_GPKI_CER_KEY = SAEALL_GPKI_ID + "_env.cer";
    
    private static final String GPKI_PWD = appInfo.getWsSaeallgpkipwd();
    //GPKI ENV CER ���ϸ�
    private static final String GPKI_ENV_CER = GPKI_ID + "_env.cer";
    //GPKI ENV KEY ���ϸ�
    private static final String GPKI_ENV_KEY = GPKI_ID + "_env.key";
    
    private static final String SAEALL_CER_KEY_PATH = SAEALL_GPKI_PATH + SAEALL_GPKI_CER_KEY;
    private static final String GPKI_ENV_CER_PATH = GPKI_PATH + GPKI_ENV_CER;
    private static final String GPKI_ENV_KEY_PATH = GPKI_PATH + GPKI_ENV_KEY;
    
    private static final String SRC_ORG_CD = appInfo.getWsSaeallsrcorgcd();
    private static final String TGT_ORG_CD = appInfo.getWsSaealltgtorgcd();
    
    private static final String CALL_URL = appInfo.getWsSaeallcallurl(); 
    
    /**
     * ������ ��û
     * 
     * @param queryId
     * @param messkey
     * @return 
     */
    public static SOAPMessage sendRequestMessage(
            String ifId, String queryId, String messageKey, boolean isEncrypt) throws Exception {
    	
        String empty = "";
        
        return sendRequestMessage(ifId, queryId, messageKey, empty, isEncrypt);
    }
    
    /**
     * ������ ��û
     * 
     * @param queryId
     * @param messkey
     * @return 
     */
    public static SOAPMessage sendRequestMessage(
            String ifId, String queryId, String messageKey, String param, boolean isEncrypt) throws Exception {
        
        //SOAPMessage ���� 
        String soapMessage = buildSOAPMessage(ifId, queryId, messageKey, param, isEncrypt);
        
        //Debugging
        //messageDebug(ifId, queryId, messageKey, soapMessage);
        
        //SOAPMessage ��ü ����
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage reqSoapMessage = messageFactory.createMessage();  
        
        reqSoapMessage.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
        reqSoapMessage.setProperty(SOAPMessage.WRITE_XML_DECLARATION, "true");
        
        SOAPPart soapPart = reqSoapMessage.getSOAPPart();
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
        SOAPBody soapBody = soapEnvelope.getBody();
        
        //DOM��ü�� �̿��Ͽ� XML ������ �Ľ�
        Document doc = getDocument(soapMessage);
        soapBody.addDocument(doc);
        
        //Debugging
        //reqSoapMessage.writeTo(System.out);
        
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        
        //��û SOAP �޽����� ������, ���� SOAP �޽����� �޴´�.
        SOAPMessage resSOAPMessage = soapConnection.call(reqSoapMessage, new URL(CALL_URL + ifId));
        
        //Debugging
        //resSOAPMessage.writeTo(System.out);
        
        return resSOAPMessage;
    }
    
    /**
     * ��������� ��ȯ
     * 
     * @param resSOAPMessage, messageKey, isDecrypt
     * @return List
     */
    public static List parseData(
            SOAPMessage resSOAPMessage, String messageKey, boolean isEncrypt) throws Exception {
        
        List resultList = new ArrayList();
        
        SOAPPart soapPart = resSOAPMessage.getSOAPPart();
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
        SOAPBody soapBody = soapEnvelope.getBody();
        SOAPElement soapElement = (SOAPElement)soapBody.getChildElements().next();
        
        if ( soapElement.getTagName().equals("soapenv:Fault") || 
                soapElement.getTagName().equals("env:Fault") ) {
            
            logger.error("ERROR !!");
            return resultList;
        }
        
        String resultCode = getNodeData(soapElement.getElementsByTagName("RESULTCODE").item(0));
        String resMessageKey = getNodeData(soapElement.getElementsByTagName("MSGKEY").item(0));
        
        if ( !("200".equals(resultCode) && messageKey.equals(resMessageKey)) ) {
            
            logger.error("resultCode => " + resultCode);
            logger.error("resMessageKey => " + resMessageKey);
            logger.error("messageKey => " + messageKey);
            return resultList;
        }
        
        NodeList nodeList = soapElement.getElementsByTagName("DATA");
        Node resultData = nodeList.item(0);
        
        if ( resultData == null || resultData.getFirstChild() == null ){
            
            logger.info("NO DATA !!");
            return resultList;
        }
        
        String resultMessage = resultData.getFirstChild().toString();
        
        String decryptMessage = "";
        
        //��ȣȭ
        if ( isEncrypt ) {
            
            decryptMessage = decryptData(resultMessage);
            
            logger.info("-----[decryptData]-----");
            //logger.info(decryptMessage);
        }
        else {
            decryptMessage = resultMessage;
        }
        
        //logger.info(decryptMessage);
        
        Document doc = getDocument(decryptMessage);
        //logger.info("[document.hasChildNodes] : " + doc.hasChildNodes());
        
        Element elem = doc.getDocumentElement();
        NodeList dataList = elem.getElementsByTagName("list");
        int dataLength = dataList != null ? dataList.getLength() : 0;
        
        for ( int i = 0; i < dataLength; i++ ) {
            
            Node itemNode = dataList.item(i);
            
            if ( itemNode != null ) {
                
                int itemLength = itemNode.getChildNodes().getLength();
                Node child = itemNode.getFirstChild();
                
                Map itemMap = new HashMap(itemLength);
                
                while ( child != null ) {
                    
                    if ( child.getFirstChild() != null ) {
                        itemMap.put(child.getNodeName(), child.getFirstChild().getNodeValue());
                    }
                    
                    child = child.getNextSibling();
                }
                
                //logger.info(itemMap);
                resultList.add(itemMap);
            }
        }
        
        return resultList;
    }
    
    /**
     * ��� ������ ��������
     * 
     * @param node
     * @return String
     * @throws Exception
     */
    private static String getNodeData(Node node) throws Exception {
        
        String nodeData = "";
        
        if ( node.getNodeType() == Node.ATTRIBUTE_NODE ) 
            return node.getNodeValue(); 
        
        Node child = node.getFirstChild();
        
        if ( child != null ) {
            Node sibling = child.getNextSibling();
            
            if ( sibling != null ) {
                StringBuffer sb = new StringBuffer();
                getNodeData(node, sb);
                nodeData = sb.toString();
                sb = null;
            }
            else { 
                if ( child.getNodeType() == Node.TEXT_NODE ) 
                    nodeData = child.getNodeValue();
                else 
                    nodeData = getNodeData(child);
            }
        }
        
        return nodeData;
    }
    
    /**
     * ��� ������ ��������
     * 
     * @param node, sb
     * @throws Exception
     */
    private static void getNodeData(Node node, StringBuffer sb) throws Exception {
        
        Node child = node.getFirstChild();
        
        while ( child != null ) {
            
            if ( child.getNodeType() == Node.TEXT_NODE ) {
                sb.append(child.getNodeValue());
            } 
            else {
                getNodeData(child, sb);
            }
            
            child = child.getNextSibling();
        }
    }
    
    /**
     * �޽��������ĺ�Ű ����
     * �޽����ĺ�Ű�� �� ��û�ø��� �����ϸ�, ������ yyyyMMddHHmmssSSS + ����8�ڸ�(�� 25�ڸ� ���ڷ� ����)
     * 
     * @return String
     * @throws Exception
     */
    public static String getMessageKey() throws Exception {
        
        String DATE_FORMAT = "yyyyMMddHHmmssSSS";
        SimpleDateFormat sdf = new SimpleDateFormat( DATE_FORMAT );
        Calendar cal = Calendar.getInstance();
        
        Random oRandom = new Random();
        
        long rndValue = 0;
        
        while (true) {
            rndValue = Math.abs( oRandom.nextInt()*1000000 );
            
            if ( String.valueOf(rndValue).length() == 8 )
                break;
        }
        
        return sdf.format( cal.getTime() ) + rndValue;
    }
    
    /**
     * ��û SOAPMessage ����
     * ��ȣȭ ����� <message></message>�±׸� ������ ��ü�� ��ȣȭ �ؾ���
     * 
     * @param ifId, queryId, messageKey, param, isEncrypt
     * @return String
     * @throws Exception
     */
    private static String buildSOAPMessage(
            String ifId, String queryId, String messageKey, String param, boolean isEncrypt) throws Exception {
        
        String xmlDoc = "";
        
        if ( ifId.equals("") || queryId.equals("") || messageKey.equals("") ) {
            return xmlDoc;
        }
        
        StringBuffer bufferDoc = new StringBuffer();
        StringBuffer bufferMsg = new StringBuffer();
        
        bufferMsg.append("<message>");
        bufferMsg.append("<body>");
        bufferMsg.append("<query_id>" + queryId + "</query_id>");
        
        if ( param.equals("") ) {
            bufferMsg.append("<dataList>");
            bufferMsg.append("<data>" + param + "</data>");
            bufferMsg.append("</dataList>");
        }
        
        bufferMsg.append("</body>");
        bufferMsg.append("</message>");
        
        String msg = "";
        
        if ( isEncrypt ) {
            byte[] byInpMsg = encryptData(bufferMsg.toString());
            msg = new Base64().encode(byInpMsg);
        }
        else {
            msg = bufferMsg.toString();
        }
        bufferMsg = null;
        
        bufferDoc.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        bufferDoc.append("<DOCUMENT>");
        bufferDoc.append("<IFID>" + ifId + "</IFID>");
        bufferDoc.append("<SRCORGCD>" + SRC_ORG_CD + "</SRCORGCD>");
        bufferDoc.append("<TGTORGCD>" + TGT_ORG_CD + "</TGTORGCD>");
        bufferDoc.append("<RESULTCODE>000</RESULTCODE>");
        bufferDoc.append("<MSGKEY>" + messageKey + "</MSGKEY>");
        bufferDoc.append("<DATA>");
        bufferDoc.append(msg);
        bufferDoc.append("</DATA>");
        bufferDoc.append("</DOCUMENT>");
        
        xmlDoc = bufferDoc.toString();
        
        bufferDoc = null;
        
        return xmlDoc;
    }
    
    /**
     * ��û�� XML�� DOM��ü�� �̿��Ͽ� XML ������ �Ľ�
     * 
     * @param ifId, queryId, messageKey, param, isEncrypt
     * @return Document
     * @throws Exception
     */
    private static Document getDocument(String param) throws Exception {
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        
        InputSource inputSource = new InputSource(new StringReader(param));
        
        return documentBuilder.parse(inputSource);
    }
    
    /**
     * �������� Ű��ȣ ��ȣȭ(GPKI ID�� �̿��Ͽ� GPKI LDAP�������� ����Ű��  ���� �� ��ȣȭ )
     * 
     * @param inputMsg
     * @return byte[]
     * @throws Exception
     */
    private static byte[] encryptData(String inputMsg) throws Exception {
        
        X509Certificate recCert;
        byte[] outData = null;
        
        GpkiApi.init(GPKI_PATH);
        
        
        Ldap ldap = new Ldap();
        ldap.setLdap(GPKI_LDAP_SERVER, 389);
        
        ldap.searchCN(Ldap.DATA_TYPE_KM_CERT, SAEALL_GPKI_ID);
        
        byte[] snLdap = ldap.getData();
        Disk.write(SAEALL_CER_KEY_PATH, snLdap);
        
        
        recCert = Disk.readCert(SAEALL_CER_KEY_PATH);
        
        //SEED 3DES DES NEAT ARIA NES NEAT/CBC
        //���� ȯ�濡�� ���ĪŰ ��ȣ �޽����� ����/ó��
        EnvelopedData envData = new EnvelopedData("NEAT");
        
        //��ȣ �޽��� �������� �������� �߰��Ѵ�.
        envData.addRecipient( recCert );
        outData = envData.generate( inputMsg.getBytes() );
        
        return outData;
    }
    
    /**
     * �ñ��� Ű��ȣ�� ��ȣȭ(����Ű(���Ű)�� ����Ͽ� �޽����� �ص�)
     * 
     * @param inputMsg
     * @return String
     * @throws Exception
     */
    private static String decryptData(String inputMsg) throws Exception {
        
        X509Certificate recCert;
        PrivateKey recPriKey;
        
        byte[] outMsg = null;
        String message = "";
        
        //�ñ��� ����Ű
        recCert = Disk.readCert(GPKI_ENV_CER_PATH);
        //�ñ��� ����Ű(����Ű)
        recPriKey = Disk.readPriKey(GPKI_ENV_KEY_PATH, GPKI_PWD);
        
        //���� ȯ�濡�� ���ĪŰ ��ȣ �޽����� ����/ó��
        EnvelopedData envData = new EnvelopedData("NEAT");
        byte[] byteContent = new Base64().decode( inputMsg );
        
        //��ȣȭ�� �޽����� ��ȣȭ�Ͽ� ���� �޽����� ȹ���Ѵ�.
        outMsg = envData.process(byteContent, recCert, recPriKey);
        
        
        message = new String(outMsg);
        
        return message;
    }
    
    /**
     * ������ ��û ���� DEBUG
     * 
     * @param ifId, queryId, messageKey, soapMessage
     */
//    private static void messageDebug(
//            String ifId, String queryId, String messageKey, String soapMessage) {
//        
//        logger.info("gpkiPath : " + GPKI_PATH);
//        logger.info("gpkiLdapServer : " + GPKI_LDAP_SERVER);
//        logger.info("saeallGpkiPath : " + SAEALL_GPKI_PATH);
//        logger.info("saeallGpkiId : " + SAEALL_GPKI_ID);
//        logger.info("saeallGpkiCerKey : " + SAEALL_GPKI_CER_KEY);
//        logger.info("gpkiPwd : " + GPKI_PWD);
//        logger.info("gpkiEnvCer : " + GPKI_ENV_CER);
//        logger.info("gpkiEnvKey : " + GPKI_ENV_KEY);
//        logger.info("saeallCerKeyPath : " + SAALL_CER_KEY_PATH);
//        logger.info("gpkiEnvCerPath : " + GPKI_ENV_CER_PATH);
//        logger.info("gpkiEnvKeyPath : " + GPKI_ENV_KEY_PATH);
//        logger.info("ifId : " + ifId);
//        logger.info("srcOrgCd : " + SRC_ORG_CD);
//        logger.info("tgtOrgCd : " + TGT_ORG_CD);
//        logger.info("callUrl : " + CALL_URL);
//        
//        logger.info("queryId : " + queryId);
//        logger.info("messageKey : " + messageKey);
//        
//        logger.info("soapMessage : " + soapMessage);
//        
//    }
    
    /**
     * ��� ������ �����ϱ�
     * 
     * @param node, param
     * @throws Exception
     */
//    private static void setNodeData(Node node, String param) throws Exception {
//        
//        if ( node.getNodeType() == Node.ATTRIBUTE_NODE ) {
//            
//            if ( param == null ) 
//                param = "";
//            
//            node.setNodeValue(param); 
//        }
//        else {
//            Node child;
//            
//            while ( (child = node.getFirstChild()) != null ) {
//                node.removeChild(child);
//            }
//            
//            if ( param.equals("") ) {
//                Text textNode = node.getOwnerDocument().createTextNode(param);
//                node.appendChild(textNode);
//            }
//        }
//    }
    
//    private void charsetInfo(String pstr) {
//        
//        try {
//            String[] charsetArr = new String[5];
//            charsetArr[0] = "utf-8";
//            charsetArr[1] = "euc-kr";
//            charsetArr[2] = "ISO-8859-1";
//            charsetArr[3] = "KSC5601";
//            charsetArr[4] = "8859_1";
//            
//            logger.info("\n\n\n");
//            
//            for ( int i = 0; i < charsetArr.length; i++ ) {
//                
//                for ( int j = 0; j < charsetArr.length; j++ ) {
//                    
//                    String charset1 = charsetArr[i];
//                    String charset2 = charsetArr[j];
//                    String str=new String(pstr.getBytes(charset1),charset2);
//                    
//                    logger.info("from "+charset1+" to "+charset2);
//                    logger.info(str);
//                }
//            }
//           
//            logger.info("\n\n\n");
//            
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    /**
     * ��������� ��ȯ �׽�Ʈ
     * 
     * @param resSOAPMessage, messageKey, isDecrypt
     * @return List
     */
    public static List parseDataTestDept() throws Exception {
        
        List resultList = new ArrayList();
        
        String decryptMessage = 
                "<message><body><res_cnt>125</res_cnt><list><dept_seq>3</dept_seq><esb_ymd>20100514</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xC8FC;&#xBBFC;&#xC0DD;&#xD65C;&#xC9C0;&#xC6D0;&#xC2E4;</dep_full_nm><rep_tel_no>0555802451</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20910</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xC8FC;&#xBBFC;&#xC0DD;&#xD65C;&#xC9C0;&#xC6D0;&#xC2E4;</dep_code_nm><dep_code>54000630000</dep_code><rep_fax_no>055 580 2459</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>20100514</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xBBFC;&#xC6D0;&#xBD09;&#xC0AC;&#xACFC;</dep_full_nm><rep_tel_no>0555802231</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20930</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xBBFC;&#xC6D0;&#xBD09;&#xC0AC;&#xACFC;</dep_code_nm><dep_code>54000640000</dep_code><rep_fax_no>055 580 2238</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19960901</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xAE30;&#xD68D;&#xAC10;&#xC0AC;&#xC2E4;</dep_full_nm><rep_tel_no>0555802011</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20905</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xAE30;&#xD68D;&#xAC10;&#xC0AC;&#xC2E4;</dep_code_nm><dep_code>54000010000</dep_code><rep_fax_no>055-580-2019</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19620101</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xC7AC;&#xBB34;&#xACFC;</dep_full_nm><rep_tel_no>05525802160</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20925</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xC7AC;&#xBB34;&#xACFC;</dep_code_nm><dep_code>54000050000</dep_code><rep_fax_no>0552-580-2169</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20100620</esb_ymd><upr_dept_code>30000000000</upr_dept_code><mod_dt>20100620135000</mod_dt><dep_full_nm>&#xC11C;&#xC6B8;&#xC2DC; &#xC885;&#xB85C;&#xAD6C; &#xC0AC;&#xC9C1;&#xB3D9;</dep_full_nm><rep_tel_no/><sprm_dept_code>30000000000</sprm_dept_code><dept_rank/><dept_se>5</dept_se><dep_se>5</dep_se><org_no>3000000</org_no><del_ymd/><dep_code_nm>&#xC0AC;&#xC9C1;&#xB3D9;</dep_code_nm><dep_code>30000420000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>19830215</esb_ymd><upr_dept_code>43300210000</upr_dept_code><mod_dt>20111024061439</mod_dt><dep_full_nm>&#xAC15;&#xC6D0;&#xB3C4; &#xC778;&#xC81C;&#xAD70; &#xC778;&#xC81C;&#xC74D; &#xADC0;&#xB454;&#xCD9C;&#xC7A5;&#xC18C;</dep_full_nm><rep_tel_no>033-460-2371</rep_tel_no><sprm_dept_code>43300000000</sprm_dept_code><dept_rank>14570</dept_rank><dept_se>7</dept_se><dep_se>7</dep_se><org_no>4330000</org_no><del_ymd/><dep_code_nm>&#xADC0;&#xB454;&#xCD9C;&#xC7A5;&#xC18C;</dep_code_nm><dep_code>43300330000</dep_code><rep_fax_no>460 2547</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19901206</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xC9C0;&#xC5ED;&#xACBD;&#xC81C;&#xACFC;</dep_full_nm><rep_tel_no>05525802310</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20950</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xC9C0;&#xC5ED;&#xACBD;&#xC81C;&#xACFC;</dep_code_nm><dep_code>54000090000</dep_code><rep_fax_no>0552580 2319</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19621130</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xAC74;&#xC124;&#xACFC;</dep_full_nm><rep_tel_no>05525802330</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20955</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xAC74;&#xC124;&#xACFC;</dep_code_nm><dep_code>54000100000</dep_code><rep_fax_no>0552580 2339</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19900914</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xB3C4;&#xC2DC;&#xACFC;</dep_full_nm><rep_tel_no>05525802350</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20960</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xB3C4;&#xC2DC;&#xACFC;</dep_code_nm><dep_code>54000110000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>19910411</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xC758;&#xD68C;&#xC0AC;&#xBB34;&#xACFC;</dep_full_nm><rep_tel_no>0555802520</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20980</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xC758;&#xD68C;&#xC0AC;&#xBB34;&#xACFC;</dep_code_nm><dep_code>54000120000</dep_code><rep_fax_no>055 580 2529</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19990115</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xB18D;&#xC5C5;&#xAE30;&#xC220;&#xC13C;&#xD130;</dep_full_nm><rep_tel_no>05525802280</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21000</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xB18D;&#xC5C5;&#xAE30;&#xC220;&#xC13C;&#xD130;</dep_code_nm><dep_code>54000210000</dep_code><rep_fax_no>0552580 2289</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19880423</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xAC00;&#xC57C;&#xC74D;</dep_full_nm><rep_tel_no>05525802600</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21030</dept_rank><dept_se>3</dept_se><dep_se>3</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xAC00;&#xC57C;&#xC74D;</dep_code_nm><dep_code>54000250000</dep_code><rep_fax_no>0552-580-2609</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19880423</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xD568;&#xC548;&#xBA74;</dep_full_nm><rep_tel_no>05525802611</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21035</dept_rank><dept_se>4</dept_se><dep_se>4</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xD568;&#xC548;&#xBA74;</dep_code_nm><dep_code>54000260000</dep_code><rep_fax_no>0552-580-2619</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19880423</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xAD70;&#xBD81;&#xBA74;</dep_full_nm><rep_tel_no>05525802621</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21040</dept_rank><dept_se>4</dept_se><dep_se>4</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xAD70;&#xBD81;&#xBA74;</dep_code_nm><dep_code>54000270000</dep_code><rep_fax_no>0552-580-2629</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19880423</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xBC95;&#xC218;&#xBA74;</dep_full_nm><rep_tel_no>05525802631</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21045</dept_rank><dept_se>4</dept_se><dep_se>4</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xBC95;&#xC218;&#xBA74;</dep_code_nm><dep_code>54000280000</dep_code><rep_fax_no>0552-580-2639</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19880423</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xB300;&#xC0B0;&#xBA74;</dep_full_nm><rep_tel_no>05525802641</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21050</dept_rank><dept_se>4</dept_se><dep_se>4</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xB300;&#xC0B0;&#xBA74;</dep_code_nm><dep_code>54000290000</dep_code><rep_fax_no>0552-580-2649</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19880423</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xCE60;&#xC11C;&#xBA74;</dep_full_nm><rep_tel_no>05525802651</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21055</dept_rank><dept_se>4</dept_se><dep_se>4</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xCE60;&#xC11C;&#xBA74;</dep_code_nm><dep_code>54000300000</dep_code><rep_fax_no>0552-580-2659</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19880423</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xCE60;&#xBD81;&#xBA74;</dep_full_nm><rep_tel_no>05505803651</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21060</dept_rank><dept_se>4</dept_se><dep_se>4</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xCE60;&#xBD81;&#xBA74;</dep_code_nm><dep_code>54000310000</dep_code><rep_fax_no>0552-580-2669</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19880423</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xCE60;&#xC6D0;&#xBA74;</dep_full_nm><rep_tel_no>05525802671</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21065</dept_rank><dept_se>4</dept_se><dep_se>4</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xCE60;&#xC6D0;&#xBA74;</dep_code_nm><dep_code>54000320000</dep_code><rep_fax_no>0552-580-2679</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19880423</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xC0B0;&#xC778;&#xBA74;</dep_full_nm><rep_tel_no>05525802681</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21070</dept_rank><dept_se>4</dept_se><dep_se>4</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xC0B0;&#xC778;&#xBA74;</dep_code_nm><dep_code>54000330000</dep_code><rep_fax_no>0552-580-2689</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19880423</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xC5EC;&#xD56D;&#xBA74;</dep_full_nm><rep_tel_no>05525802691</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21075</dept_rank><dept_se>4</dept_se><dep_se>4</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xC5EC;&#xD56D;&#xBA74;</dep_code_nm><dep_code>54000340000</dep_code><rep_fax_no>0552-580-2699</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19070904</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xAD70;&#xC218;</dep_full_nm><rep_tel_no>055-580-2001</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20895</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xAD70;&#xC218;</dep_code_nm><dep_code>54000350000</dep_code><rep_fax_no>055-583-6388</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19750115</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xBD80;&#xAD70;&#xC218;</dep_full_nm><rep_tel_no>0555802005</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20900</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xBD80;&#xAD70;&#xC218;</dep_code_nm><dep_code>54000360000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>20000426</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xBB38;&#xD654;&#xAD00;&#xAD11;&#xACFC;</dep_full_nm><rep_tel_no>114</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20940</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xBB38;&#xD654;&#xAD00;&#xAD11;&#xACFC;</dep_code_nm><dep_code>54000390000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20010728</esb_ymd><upr_dept_code>54000210000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xB18D;&#xC5C5;&#xAE30;&#xC220;&#xC13C;&#xD130; &#xB18D;&#xC5C5;&#xC9C0;&#xC6D0;&#xACFC;</dep_full_nm><rep_tel_no>3300</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21010</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xB18D;&#xC5C5;&#xC9C0;&#xC6D0;&#xACFC;</dep_code_nm><dep_code>54000440000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>20030101</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xBCF4;&#xAC74;&#xC18C;</dep_full_nm><rep_tel_no/><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20990</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xBCF4;&#xAC74;&#xC18C;</dep_code_nm><dep_code>54000460000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>20021230</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xBB38;&#xD654;&#xCCB4;&#xC721;&#xC2DC;&#xC124;&#xC0AC;&#xC5C5;&#xC18C;</dep_full_nm><rep_tel_no>114</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21020</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xBB38;&#xD654;&#xCCB4;&#xC721;&#xC2DC;&#xC124;&#xC0AC;&#xC5C5;&#xC18C;</dep_code_nm><dep_code>54000470000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>20040722</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xD658;&#xACBD;&#xBCF4;&#xD638;&#xACFC;</dep_full_nm><rep_tel_no>0555802222</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20945</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xD658;&#xACBD;&#xBCF4;&#xD638;&#xACFC;</dep_code_nm><dep_code>54000500000</dep_code><rep_fax_no>055 580 2117</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>20040722</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xD589;&#xC815;&#xACFC;</dep_full_nm><rep_tel_no>0555802222</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20920</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xD589;&#xC815;&#xACFC;</dep_code_nm><dep_code>54000510000</dep_code><rep_fax_no>055 580 2119</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>20040722</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xC0C1;&#xD558;&#xC218;&#xB3C4;&#xC0AC;&#xC5C5;&#xC18C;</dep_full_nm><rep_tel_no>0555803701</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21025</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xC0C1;&#xD558;&#xC218;&#xB3C4;&#xC0AC;&#xC5C5;&#xC18C;</dep_code_nm><dep_code>54000530000</dep_code><rep_fax_no>055 580 2119</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>20050502</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xC7AC;&#xB09C;&#xC548;&#xC804;&#xAD00;&#xB9AC;&#xACFC;</dep_full_nm><rep_tel_no>0555802773</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20970</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xC7AC;&#xB09C;&#xC548;&#xC804;&#xAD00;&#xB9AC;&#xACFC;</dep_code_nm><dep_code>54000540000</dep_code><rep_fax_no>055 580 2119</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>20060701</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xC8FC;&#xBBFC;&#xBCF5;&#xC9C0;&#xACFC;</dep_full_nm><rep_tel_no>114</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20935</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xC8FC;&#xBBFC;&#xBCF5;&#xC9C0;&#xACFC;</dep_code_nm><dep_code>54000580000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20060701</esb_ymd><upr_dept_code>54000210000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xB18D;&#xC5C5;&#xAE30;&#xC220;&#xC13C;&#xD130; &#xC18C;&#xB4DD;&#xAC1C;&#xBC1C;&#xACFC;</dep_full_nm><rep_tel_no>114</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21015</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xC18C;&#xB4DD;&#xAC1C;&#xBC1C;&#xACFC;</dep_code_nm><dep_code>54000600000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>20080212</esb_ymd><upr_dept_code>54000000000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xC815;&#xCC45;&#xC804;&#xB7B5;&#xC0AC;&#xC5C5;&#xB2E8;</dep_full_nm><rep_tel_no>114</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>20975</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xC815;&#xCC45;&#xC804;&#xB7B5;&#xC0AC;&#xC5C5;&#xB2E8;</dep_code_nm><dep_code>54000610000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20080704</esb_ymd><upr_dept_code>54000210000</upr_dept_code><mod_dt>20110602100240</mod_dt><dep_full_nm>&#xACBD;&#xC0C1;&#xB0A8;&#xB3C4; &#xD568;&#xC548;&#xAD70; &#xB18D;&#xC5C5;&#xAE30;&#xC220;&#xC13C;&#xD130; &#xB18D;&#xC5C5;&#xC0B0;&#xB9BC;&#xACFC;</dep_full_nm><rep_tel_no>114</rep_tel_no><sprm_dept_code>54000000000</sprm_dept_code><dept_rank>21005</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>5400000</org_no><del_ymd/><dep_code_nm>&#xB18D;&#xC5C5;&#xC0B0;&#xB9BC;&#xACFC;</dep_code_nm><dep_code>54000620000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46402920000</upr_dept_code><mod_dt>20090210070013</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xAD50;&#xD1B5;&#xAD6D; &#xB3C4;&#xB85C;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2448</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10185</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd>20080225</del_ymd><dep_code_nm>&#xB3C4;&#xB85C;&#xACFC;</dep_code_nm><dep_code>46402960000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20050801</esb_ymd><upr_dept_code>46400970000</upr_dept_code><mod_dt>20090210070014</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xBCF5;&#xC9C0;&#xD658;&#xACBD;&#xAD6D; &#xCCAD;&#xC18C;&#xD589;&#xC815;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2843</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10085</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd>20080225</del_ymd><dep_code_nm>&#xCCAD;&#xC18C;&#xD589;&#xC815;&#xACFC;</dep_code_nm><dep_code>46401870000</dep_code><rep_fax_no>063 240 0413</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20050801</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20090210070014</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xBB38;&#xD654;&#xACBD;&#xC81C;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10475</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd>20080630</del_ymd><dep_code_nm>&#xBB38;&#xD654;&#xACBD;&#xC81C;&#xACFC;</dep_code_nm><dep_code>46600540000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20090210070014</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xAC00;&#xB85C;&#xAD50;&#xD1B5;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10495</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd>20080630</del_ymd><dep_code_nm>&#xAC00;&#xB85C;&#xAD50;&#xD1B5;&#xACFC;</dep_code_nm><dep_code>46600670000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46402760000</upr_dept_code><mod_dt>20090210070014</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xACBD;&#xC81C;&#xAD6D; &#xAE30;&#xC5C5;&#xC9C4;&#xD765;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2928</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10100</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd>20080225</del_ymd><dep_code_nm>&#xAE30;&#xC5C5;&#xC9C4;&#xD765;&#xACFC;</dep_code_nm><dep_code>46402780000</dep_code><rep_fax_no>063 281 2614</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46402810000</upr_dept_code><mod_dt>20090210070014</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC804;&#xD1B5;&#xBB38;&#xD654;&#xAD6D; &#xD55C;&#xBE0C;&#xB79C;&#xB4DC;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2556</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10125</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD55C;&#xBE0C;&#xB79C;&#xB4DC;&#xACFC;</dep_code_nm><dep_code>46402830000</dep_code><rep_fax_no>063 281 0492</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46402860000</upr_dept_code><mod_dt>20090210070013</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB3C4;&#xC2DC;&#xAD6D; &#xC8FC;&#xD0DD;&#xD589;&#xC815;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2445</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10150</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd>20080225</del_ymd><dep_code_nm>&#xC8FC;&#xD0DD;&#xD589;&#xC815;&#xACFC;</dep_code_nm><dep_code>46402880000</dep_code><rep_fax_no>063 281 2616</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20070123</esb_ymd><upr_dept_code>46400970000</upr_dept_code><mod_dt>20090210070014</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xBCF5;&#xC9C0;&#xD658;&#xACBD;&#xAD6D; &#xC2DC;&#xBBFC;&#xC0DD;&#xD65C;&#xBCF5;&#xC9C0;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10062</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd>20080225</del_ymd><dep_code_nm>&#xC2DC;&#xBBFC;&#xC0DD;&#xD65C;&#xBCF5;&#xC9C0;&#xACFC;</dep_code_nm><dep_code>46403020000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20070123</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20070905070018</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xC2DC;&#xBBFC;&#xC0DD;&#xD65C;&#xBCF5;&#xC9C0;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10463</dept_rank><dept_se/><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd>20070825</del_ymd><dep_code_nm>&#xC2DC;&#xBBFC;&#xC0DD;&#xD65C;&#xBCF5;&#xC9C0;&#xACFC;</dep_code_nm><dep_code>46403040000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20090210070014</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xAC00;&#xB85C;&#xAD50;&#xD1B5;&#xACFC;</dep_full_nm><rep_tel_no>063 220 5451</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10345</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd>20080630</del_ymd><dep_code_nm>&#xAC00;&#xB85C;&#xAD50;&#xD1B5;&#xACFC;</dep_code_nm><dep_code>46500750000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46402680000</upr_dept_code><mod_dt>20090210070014</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xAE30;&#xD68D;&#xAD6D; &#xAE30;&#xD68D;&#xC608;&#xC0B0;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2211</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10035</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd>20080225</del_ymd><dep_code_nm>&#xAE30;&#xD68D;&#xC608;&#xC0B0;&#xACFC;</dep_code_nm><dep_code>46402690000</dep_code><rep_fax_no>063 281 2602</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46402680000</upr_dept_code><mod_dt>20090210070014</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xAE30;&#xD68D;&#xAD6D; &#xD589;&#xC815;&#xC9C0;&#xC6D0;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2231</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10040</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd>20080225</del_ymd><dep_code_nm>&#xD589;&#xC815;&#xC9C0;&#xC6D0;&#xACFC;</dep_code_nm><dep_code>46402700000</dep_code><rep_fax_no>063 281 2628</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46402680000</upr_dept_code><mod_dt>20090210070014</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xAE30;&#xD68D;&#xAD6D; &#xD589;&#xC815;&#xD601;&#xC2E0;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2090</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10045</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd>20080225</del_ymd><dep_code_nm>&#xD589;&#xC815;&#xD601;&#xC2E0;&#xACFC;</dep_code_nm><dep_code>46402710000</dep_code><rep_fax_no>063 281 2623</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>20080630</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt/><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xC644;&#xC0B0;&#xBCF4;&#xAC74;&#xC18C;  &#xBCF4;&#xAC74;&#xD589;&#xC815;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10301</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xBCF4;&#xAC74;&#xD589;&#xC815;&#xACFC;</dep_code_nm><dep_code>46500770000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>20080630</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt/><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xB355;&#xC9C4;&#xBCF4;&#xAC74;&#xC18C; &#xBCF4;&#xAC74;&#xD589;&#xC815;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10301</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xBCF4;&#xAC74;&#xD589;&#xC815;&#xACFC;</dep_code_nm><dep_code>46600690000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20090101</esb_ymd><upr_dept_code>30000790000</upr_dept_code><mod_dt>20080109060957</mod_dt><dep_full_nm>&#xC11C;&#xC6B8;&#xD2B9;&#xBCC4;&#xC2DC; &#xC885;&#xB85C;&#xAD6C; &#xB3C4;&#xC2DC;&#xAD00;&#xB9AC;&#xAD6D; &#xB3C4;&#xC2DC;&#xACC4;&#xD68D;&#xACFC;</dep_full_nm><rep_tel_no>02-731-0385</rep_tel_no><sprm_dept_code>30000000000</sprm_dept_code><dept_rank>6140</dept_rank><dept_se>6</dept_se><dep_se>6</dep_se><org_no>3000000</org_no><del_ymd/><dep_code_nm>&#xB3C4;&#xC2DC;&#xACC4;&#xD68D;&#xACFC;</dep_code_nm><dep_code>30000810000</dep_code><rep_fax_no>02-733-0060</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20090101</esb_ymd><upr_dept_code>46402760000</upr_dept_code><mod_dt>20100430070013</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xACBD;&#xC81C;&#xAD6D; &#xC601;&#xC0C1;&#xC815;&#xBCF4;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2849</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10110</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC601;&#xC0C1;&#xC815;&#xBCF4;&#xACFC;</dep_code_nm><dep_code>46402800000</dep_code><rep_fax_no>063 281 2607</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20090101</esb_ymd><upr_dept_code>46402760000</upr_dept_code><mod_dt>20100430070015</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xACBD;&#xC81C;&#xAD6D; &#xC0DD;&#xD65C;&#xACBD;&#xC81C;&#xACFC;</dep_full_nm><rep_tel_no>063 281 5078</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10105</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC0DD;&#xD65C;&#xACBD;&#xC81C;&#xACFC;</dep_code_nm><dep_code>46402790000</dep_code><rep_fax_no>063 281 2622</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20090101</esb_ymd><upr_dept_code>46402920000</upr_dept_code><mod_dt>20100430070010</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xAD50;&#xD1B5;&#xAD6D; &#xC7AC;&#xB09C;&#xC548;&#xC804;&#xAD00;&#xB9AC;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2081</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10190</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC7AC;&#xB09C;&#xC548;&#xC804;&#xAD00;&#xB9AC;&#xACFC;</dep_code_nm><dep_code>46402970000</dep_code><rep_fax_no>063 281 2619</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20090101</esb_ymd><upr_dept_code>46402680000</upr_dept_code><mod_dt>20100430070016</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xAE30;&#xD68D;&#xAD6D; &#xC7AC;&#xBB34;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2281</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10050</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC7AC;&#xBB34;&#xACFC;</dep_code_nm><dep_code>46402720000</dep_code><rep_fax_no>063 281 2604</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20090101</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070017</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xBB38;&#xD654;&#xACBD;&#xC81C;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10325</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xBB38;&#xD654;&#xACBD;&#xC81C;&#xACFC;</dep_code_nm><dep_code>46500590000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20090101</esb_ymd><upr_dept_code>46402920000</upr_dept_code><mod_dt>20100430070011</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xAD50;&#xD1B5;&#xAD6D; &#xAD50;&#xD1B5;&#xD589;&#xC815;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2079</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10175</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xAD50;&#xD1B5;&#xD589;&#xC815;&#xACFC;</dep_code_nm><dep_code>46402930000</dep_code><rep_fax_no>063 281 2618</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20090101</esb_ymd><upr_dept_code>32101000000</upr_dept_code><mod_dt>20080424061001</mod_dt><dep_full_nm>&#xC11C;&#xC6B8;&#xD2B9;&#xBCC4;&#xC2DC; &#xC11C;&#xCD08;&#xAD6C; &#xD589;&#xC815;&#xC9C0;&#xC6D0;&#xAD6D; &#xC624;-&#xCF00;&#xC774;&#xBBFC;&#xC6D0;&#xC13C;&#xD130;</dep_full_nm><rep_tel_no>123-4567</rep_tel_no><sprm_dept_code>32100000000</sprm_dept_code><dept_rank>12138</dept_rank><dept_se>1</dept_se><dep_se>6</dep_se><org_no>3210000</org_no><del_ymd/><dep_code_nm>&#xC624;-&#xCF00;&#xC774;&#xBBFC;&#xC6D0;&#xC13C;&#xD130;</dep_code_nm><dep_code>32101210000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20090101</esb_ymd><upr_dept_code>46400970000</upr_dept_code><mod_dt>20100430070015</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xBCF5;&#xC9C0;&#xD658;&#xACBD;&#xAD6D; &#xC5EC;&#xC131;&#xC815;&#xCC45;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2344</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10075</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC5EC;&#xC131;&#xC815;&#xCC45;&#xACFC;</dep_code_nm><dep_code>46402750000</dep_code><rep_fax_no>063 281 0411</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>19701214</esb_ymd><upr_dept_code>46400000000</upr_dept_code><mod_dt>20081111070045</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xBCF5;&#xC9C0;&#xD658;&#xACBD;&#xAD6D;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10060</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xBCF5;&#xC9C0;&#xD658;&#xACBD;&#xAD6D;</dep_code_nm><dep_code>46400970000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46400000000</upr_dept_code><mod_dt>20100430070015</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xACBD;&#xC81C;&#xAD6D;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10090</dept_rank><dept_se>1</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xACBD;&#xC81C;&#xAD6D;</dep_code_nm><dep_code>46402760000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20090101</esb_ymd><upr_dept_code>46402860000</upr_dept_code><mod_dt>20100430070017</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB3C4;&#xC2DC;&#xAD6D; &#xB3C4;&#xC2EC;&#xC9C4;&#xD765;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2494</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10160</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xB3C4;&#xC2EC;&#xC9C4;&#xD765;&#xACFC;</dep_code_nm><dep_code>46403010000</dep_code><rep_fax_no>063 281 2609</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20090318</esb_ymd><upr_dept_code>46400970000</upr_dept_code><mod_dt>20100430070014</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xBCF5;&#xC9C0;&#xD658;&#xACBD;&#xAD6D; &#xD658;&#xACBD;&#xC704;&#xC0DD;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2326</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10080</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD658;&#xACBD;&#xC704;&#xC0DD;&#xACFC;</dep_code_nm><dep_code>46401860000</dep_code><rep_fax_no>063 240 0412</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070014</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xD589;&#xC815;&#xC9C0;&#xC6D0;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10305</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD589;&#xC815;&#xC9C0;&#xC6D0;&#xACFC;</dep_code_nm><dep_code>46500730000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20070123</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070013</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xC2DC;&#xBBFC;&#xC0DD;&#xD65C;&#xBCF5;&#xC9C0;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10465</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC2DC;&#xBBFC;&#xC0DD;&#xD65C;&#xBCF5;&#xC9C0;&#xACFC;</dep_code_nm><dep_code>46600680000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46400000000</upr_dept_code><mod_dt>20100430070017</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xD64D;&#xBCF4;&#xB2F4;&#xB2F9;&#xAD00;</dep_full_nm><rep_tel_no>063 281 2221</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10020</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD64D;&#xBCF4;&#xB2F4;&#xB2F9;&#xAD00;</dep_code_nm><dep_code>46402670000</dep_code><rep_fax_no>063 287 4244</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>20060816</esb_ymd><upr_dept_code>46400000000</upr_dept_code><mod_dt>20100430070013</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC804;&#xD1B5;&#xBB38;&#xD654;&#xAD6D;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10115</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC804;&#xD1B5;&#xBB38;&#xD654;&#xAD6D;</dep_code_nm><dep_code>46402810000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46402810000</upr_dept_code><mod_dt>20100430070013</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC804;&#xD1B5;&#xBB38;&#xD654;&#xAD6D; &#xBB38;&#xD654;&#xAD00;&#xAD11;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2541</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10130</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xBB38;&#xD654;&#xAD00;&#xAD11;&#xACFC;</dep_code_nm><dep_code>46402840000</dep_code><rep_fax_no>063 281 0493</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46402810000</upr_dept_code><mod_dt>20100430070013</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC804;&#xD1B5;&#xBB38;&#xD654;&#xAD6D; &#xCCB4;&#xC721;&#xC9C0;&#xC6D0;&#xACFC;</dep_full_nm><rep_tel_no>063 281 2472</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10135</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xCCB4;&#xC721;&#xC9C0;&#xC6D0;&#xACFC;</dep_code_nm><dep_code>46402850000</dep_code><rep_fax_no>063 281 0494</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46400000000</upr_dept_code><mod_dt>20100430070010</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB18D;&#xC5C5;&#xAE30;&#xC220;&#xC13C;&#xD130;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10215</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xB18D;&#xC5C5;&#xAE30;&#xC220;&#xC13C;&#xD130;</dep_code_nm><dep_code>46402990000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>01--    </esb_ymd><upr_dept_code>46400000000</upr_dept_code><mod_dt>20100430070016</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10300</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC644;&#xC0B0;&#xAD6C;</dep_code_nm><dep_code>46500000000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>19970310</esb_ymd><upr_dept_code>46400000000</upr_dept_code><mod_dt>20100430070016</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB3D9;&#xBB3C;&#xC6D0;</dep_full_nm><rep_tel_no>114</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10260</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xB3D9;&#xBB3C;&#xC6D0;</dep_code_nm><dep_code>46401120000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>19490815</esb_ymd><upr_dept_code>46400000000</upr_dept_code><mod_dt>20100430070016</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC2DC;&#xC7A5;</dep_full_nm><rep_tel_no>063 281 2004</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10010</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC2DC;&#xC7A5;</dep_code_nm><dep_code>46401130000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>19991101</esb_ymd><upr_dept_code>46400000000</upr_dept_code><mod_dt>20100430070012</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xBCF4;&#xAC74;&#xC18C;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10200</dept_rank><dept_se>0</dept_se><dep_se>0</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xBCF4;&#xAC74;&#xC18C;</dep_code_nm><dep_code>46401310000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20010105</esb_ymd><upr_dept_code>46401310000</upr_dept_code><mod_dt>20100430070010</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xBCF4;&#xAC74;&#xC18C; &#xAC74;&#xAC15;&#xC99D;&#xC9C4;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10210</dept_rank><dept_se>0</dept_se><dep_se>0</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xAC74;&#xAC15;&#xC99D;&#xC9C4;&#xACFC;</dep_code_nm><dep_code>46401430000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>20021231</esb_ymd><upr_dept_code>46400000000</upr_dept_code><mod_dt>20100430070008</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xAC10;&#xC0AC;&#xB2F4;&#xB2F9;&#xAD00;</dep_full_nm><rep_tel_no>281-2117</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10025</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xAC10;&#xC0AC;&#xB2F4;&#xB2F9;&#xAD00;</dep_code_nm><dep_code>46401570000</dep_code><rep_fax_no>063 281 2605</rep_fax_no></list><list><dept_seq>3</dept_seq><esb_ymd>      --</esb_ymd><upr_dept_code>46400000000</upr_dept_code><mod_dt>20100430070013</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10450</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xB355;&#xC9C4;&#xAD6C;</dep_code_nm><dep_code>46600000000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20030328</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070012</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xC138;&#xBB34;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10470</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC138;&#xBB34;&#xACFC;</dep_code_nm><dep_code>46600030000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>19890501</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070009</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xC778;&#xD6C4;1&#xB3D9;</dep_full_nm><rep_tel_no>0652270 6604</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10505</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC778;&#xD6C4;1&#xB3D9;</dep_code_nm><dep_code>46600210000</dep_code><rep_fax_no>0652270 6724</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19890501</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070009</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xC778;&#xD6C4;2&#xB3D9;</dep_full_nm><rep_tel_no>0652270 6605</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10510</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC778;&#xD6C4;2&#xB3D9;</dep_code_nm><dep_code>46600220000</dep_code><rep_fax_no>0652270 6725</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19890501</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070009</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xB355;&#xC9C4;&#xB3D9;</dep_full_nm><rep_tel_no>0652270 6607</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10520</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xB355;&#xC9C4;&#xB3D9;</dep_code_nm><dep_code>46600240000</dep_code><rep_fax_no>0652270 6727</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19890501</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070009</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xAE08;&#xC554;1&#xB3D9;</dep_full_nm><rep_tel_no>0652270 6608</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10525</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xAE08;&#xC554;1&#xB3D9;</dep_code_nm><dep_code>46600250000</dep_code><rep_fax_no>0652270 6728</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19890501</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070009</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xAE08;&#xC554;2&#xB3D9;</dep_full_nm><rep_tel_no>0652270 6609</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10530</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xAE08;&#xC554;2&#xB3D9;</dep_code_nm><dep_code>46600260000</dep_code><rep_fax_no>0652270 6729</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19960901</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070009</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xC6B0;&#xC544;1&#xB3D9;</dep_full_nm><rep_tel_no>0652270 6611</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10540</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC6B0;&#xC544;1&#xB3D9;</dep_code_nm><dep_code>46600280000</dep_code><rep_fax_no>0652270 6731</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19960901</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070009</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xC6B0;&#xC544;2&#xB3D9;</dep_full_nm><rep_tel_no>0652270 6612</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10545</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC6B0;&#xC544;2&#xB3D9;</dep_code_nm><dep_code>46600290000</dep_code><rep_fax_no>0652270 6732</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19890501</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070008</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xD638;&#xC131;&#xB3D9;</dep_full_nm><rep_tel_no>0652270 6613</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10550</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD638;&#xC131;&#xB3D9;</dep_code_nm><dep_code>46600300000</dep_code><rep_fax_no>0652270 6733</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20050801</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070015</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xAC74;&#xCD95;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10485</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xAC74;&#xCD95;&#xACFC;</dep_code_nm><dep_code>46600550000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20050801</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070015</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xC9C4;&#xBD81;&#xB3D9;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10500</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC9C4;&#xBD81;&#xB3D9;</dep_code_nm><dep_code>46600580000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20030328</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070016</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xC138;&#xBB34;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10320</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC138;&#xBB34;&#xACFC;</dep_code_nm><dep_code>46500040000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>19980801</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070011</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xB3D9;&#xC11C;&#xD559;&#xB3D9;</dep_full_nm><rep_tel_no>063 220 5610</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10370</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xB3D9;&#xC11C;&#xD559;&#xB3D9;</dep_code_nm><dep_code>46500260000</dep_code><rep_fax_no>063 220 5710</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19960901</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070011</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xC911;&#xD654;&#xC0B0;1&#xB3D9;</dep_full_nm><rep_tel_no>063 220 5612</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10380</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC911;&#xD654;&#xC0B0;1&#xB3D9;</dep_code_nm><dep_code>46500270000</dep_code><rep_fax_no>063 220 5712</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19960226</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070010</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xD3C9;&#xD654;1&#xB3D9;</dep_full_nm><rep_tel_no>063 220 5614</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10390</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD3C9;&#xD654;1&#xB3D9;</dep_code_nm><dep_code>46500290000</dep_code><rep_fax_no>063 220 5714</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19960226</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070010</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xD3C9;&#xD654;2&#xB3D9;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10395</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD3C9;&#xD654;2&#xB3D9;</dep_code_nm><dep_code>46500300000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>19940808</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070010</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xC0BC;&#xCC9C;2&#xB3D9;</dep_full_nm><rep_tel_no>063 220 5618</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10410</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC0BC;&#xCC9C;2&#xB3D9;</dep_code_nm><dep_code>46500310000</dep_code><rep_fax_no>063 220 5718</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19960901</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070010</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xC0BC;&#xCC9C;3&#xB3D9;</dep_full_nm><rep_tel_no>063 220 5619</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10415</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC0BC;&#xCC9C;3&#xB3D9;</dep_code_nm><dep_code>46500320000</dep_code><rep_fax_no>063 220 5719</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19920416</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070010</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xD6A8;&#xC790;3&#xB3D9;</dep_full_nm><rep_tel_no>063 220 5622</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10430</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD6A8;&#xC790;3&#xB3D9;</dep_code_nm><dep_code>46500330000</dep_code><rep_fax_no>063 220 5722</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19960901</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070010</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xD6A8;&#xC790;4&#xB3D9;</dep_full_nm><rep_tel_no>063 220 5623</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10435</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD6A8;&#xC790;4&#xB3D9;</dep_code_nm><dep_code>46500340000</dep_code><rep_fax_no>063 220 5723</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19890501</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070008</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xC11C;&#xC11C;&#xD559;&#xB3D9;</dep_full_nm><rep_tel_no>063 220 5611</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10375</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC11C;&#xC11C;&#xD559;&#xB3D9;</dep_code_nm><dep_code>46500440000</dep_code><rep_fax_no>063 220 5711</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19890501</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070008</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xC11C;&#xC2E0;&#xB3D9;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10400</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC11C;&#xC2E0;&#xB3D9;</dep_code_nm><dep_code>46500450000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>19940808</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070008</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xC0BC;&#xCC9C;1&#xB3D9;</dep_full_nm><rep_tel_no>063 220 5617</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10405</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC0BC;&#xCC9C;1&#xB3D9;</dep_code_nm><dep_code>46500460000</dep_code><rep_fax_no>063 220 5717</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19900530</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070008</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xD6A8;&#xC790;2&#xB3D9;</dep_full_nm><rep_tel_no>063 220 5621</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10425</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD6A8;&#xC790;2&#xB3D9;</dep_code_nm><dep_code>46500480000</dep_code><rep_fax_no>063 220 5721</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20030105</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070006</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xD658;&#xACBD;&#xCCAD;&#xC18C;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10330</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD658;&#xACBD;&#xCCAD;&#xC18C;&#xACFC;</dep_code_nm><dep_code>46500530000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20050801</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070006</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xBBFC;&#xC6D0;&#xBD09;&#xC0AC;&#xC2E4;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10310</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xBBFC;&#xC6D0;&#xBD09;&#xC0AC;&#xC2E4;</dep_code_nm><dep_code>46500570000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20050801</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070016</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xAC74;&#xC124;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10340</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xAC74;&#xC124;&#xACFC;</dep_code_nm><dep_code>46500610000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20050801</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070016</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xC911;&#xC559;&#xB3D9;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10350</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC911;&#xC559;&#xB3D9;</dep_code_nm><dep_code>46500630000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20050801</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070016</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xB178;&#xC1A1;&#xB3D9;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10360</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xB178;&#xC1A1;&#xB3D9;</dep_code_nm><dep_code>46500650000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20050801</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070016</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xC644;&#xC0B0;&#xB3D9;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10365</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC644;&#xC0B0;&#xB3D9;</dep_code_nm><dep_code>46500660000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>19920412</esb_ymd><upr_dept_code>46400000000</upr_dept_code><mod_dt>20100430070007</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xCC28;&#xB7C9;&#xB4F1;&#xB85D;&#xC0AC;&#xC5C5;&#xC18C;</dep_full_nm><rep_tel_no>114</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10265</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xCC28;&#xB7C9;&#xB4F1;&#xB85D;&#xC0AC;&#xC5C5;&#xC18C;</dep_code_nm><dep_code>46400610000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>19910326</esb_ymd><upr_dept_code>46400000000</upr_dept_code><mod_dt>20100430070007</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC758;&#xD68C;&#xC0AC;&#xBB34;&#xAD6D;</dep_full_nm><rep_tel_no>1111111111</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10270</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC758;&#xD68C;&#xC0AC;&#xBB34;&#xAD6D;</dep_code_nm><dep_code>46400650000</dep_code><rep_fax_no/></list><list><dept_seq>3</dept_seq><esb_ymd>19500508</esb_ymd><upr_dept_code>46400000000</upr_dept_code><mod_dt>20100430070015</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xBD80;&#xC2DC;&#xC7A5;</dep_full_nm><rep_tel_no>063 281 2011</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10015</dept_rank><dept_se>1</dept_se><dep_se>1</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xBD80;&#xC2DC;&#xC7A5;</dep_code_nm><dep_code>46400830000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20030105</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070017</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xD658;&#xACBD;&#xCCAD;&#xC18C;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10480</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD658;&#xACBD;&#xCCAD;&#xC18C;&#xACFC;</dep_code_nm><dep_code>46600480000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20050801</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070016</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xBBFC;&#xC6D0;&#xBD09;&#xC0AC;&#xC2E4;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10460</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xBBFC;&#xC6D0;&#xBD09;&#xC0AC;&#xC2E4;</dep_code_nm><dep_code>46600520000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20050801</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070015</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xAC74;&#xC124;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10490</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xAC74;&#xC124;&#xACFC;</dep_code_nm><dep_code>46600560000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20060809</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070013</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xD589;&#xC815;&#xC9C0;&#xC6D0;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10455</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD589;&#xC815;&#xC9C0;&#xC6D0;&#xACFC;</dep_code_nm><dep_code>46600650000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>19960901</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070011</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xC911;&#xD654;&#xC0B0;2&#xB3D9;</dep_full_nm><rep_tel_no>063 220 5613</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10385</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC911;&#xD654;&#xC0B0;2&#xB3D9;</dep_code_nm><dep_code>46500280000</dep_code><rep_fax_no>063 220 5713</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19900530</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070008</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xD6A8;&#xC790;1&#xB3D9;</dep_full_nm><rep_tel_no>063 220 5620</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10420</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD6A8;&#xC790;1&#xB3D9;</dep_code_nm><dep_code>46500470000</dep_code><rep_fax_no>063 220 5720</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20050801</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070017</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xAC74;&#xCD95;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10335</dept_rank><dept_se>2</dept_se><dep_se>2</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xAC74;&#xCD95;&#xACFC;</dep_code_nm><dep_code>46500600000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>20050801</esb_ymd><upr_dept_code>46500000000</upr_dept_code><mod_dt>20100430070016</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xC644;&#xC0B0;&#xAD6C; &#xD48D;&#xB0A8;&#xB3D9;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10355</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD48D;&#xB0A8;&#xB3D9;</dep_code_nm><dep_code>46500640000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>19890501</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070009</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xC778;&#xD6C4;3&#xB3D9;</dep_full_nm><rep_tel_no>0652270 6606</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10515</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC778;&#xD6C4;3&#xB3D9;</dep_code_nm><dep_code>46600230000</dep_code><rep_fax_no>0652270 6726</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19890501</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070009</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xD314;&#xBCF5;&#xB3D9;</dep_full_nm><rep_tel_no>0652270 6610</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10535</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xD314;&#xBCF5;&#xB3D9;</dep_code_nm><dep_code>46600270000</dep_code><rep_fax_no>0652270 6730</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>20010105</esb_ymd><upr_dept_code>46401310000</upr_dept_code><mod_dt>20100430070013</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xBCF4;&#xAC74;&#xC18C; &#xBCF4;&#xAC74;&#xD589;&#xC815;&#xACFC;</dep_full_nm><rep_tel_no/><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10205</dept_rank><dept_se>0</dept_se><dep_se>0</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xBCF4;&#xAC74;&#xD589;&#xC815;&#xACFC;</dep_code_nm><dep_code>46401250000</dep_code><rep_fax_no/></list><list><dept_seq>4</dept_seq><esb_ymd>19960901</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070008</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xC1A1;&#xCC9C;1&#xB3D9;</dep_full_nm><rep_tel_no>0652270 6614</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10555</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC1A1;&#xCC9C;1&#xB3D9;</dep_code_nm><dep_code>46600330000</dep_code><rep_fax_no>0652270 6734</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19980101</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070007</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xC1A1;&#xCC9C;2&#xB3D9;</dep_full_nm><rep_tel_no>0652270 6615</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10560</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC1A1;&#xCC9C;2&#xB3D9;</dep_code_nm><dep_code>46600340000</dep_code><rep_fax_no>0652270 6735</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19890501</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070007</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xC870;&#xCD0C;&#xB3D9;</dep_full_nm><rep_tel_no>0652270 6616</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10565</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xC870;&#xCD0C;&#xB3D9;</dep_code_nm><dep_code>46600350000</dep_code><rep_fax_no>0652270 6736</rep_fax_no></list><list><dept_seq>4</dept_seq><esb_ymd>19890501</esb_ymd><upr_dept_code>46600000000</upr_dept_code><mod_dt>20100430070007</mod_dt><dep_full_nm>&#xC804;&#xB77C;&#xBD81;&#xB3C4; &#xC804;&#xC8FC;&#xC2DC; &#xB355;&#xC9C4;&#xAD6C; &#xB3D9;&#xC0B0;&#xB3D9;</dep_full_nm><rep_tel_no>0652270 6617</rep_tel_no><sprm_dept_code>46400000000</sprm_dept_code><dept_rank>10570</dept_rank><dept_se>5</dept_se><dep_se>5</dep_se><org_no>4640000</org_no><del_ymd/><dep_code_nm>&#xB3D9;&#xC0B0;&#xB3D9;</dep_code_nm><dep_code>46600360000</dep_code><rep_fax_no>0652270 6737</rep_fax_no></list><res_code>Y</res_code><err_msg/></body></message>";
        
        //logger.info(decryptMessage);
        
        Document doc = getDocument(decryptMessage);
        //logger.info("[document.hasChildNodes] : " + doc.hasChildNodes());
        
        Element elem = doc.getDocumentElement();
        NodeList dataList = elem.getElementsByTagName("list");
        int dataLength = dataList != null ? dataList.getLength() : 0;
        
        for ( int i = 0; i < dataLength; i++ ) {
            
            Node itemNode = dataList.item(i);
            
            if ( itemNode != null ) {
                
                int itemLength = itemNode.getChildNodes().getLength();
                Node child = itemNode.getFirstChild();
                
                Map itemMap = new HashMap(itemLength);
                
                while ( child != null ) {
                    
                    if ( child.getFirstChild() != null ) {
                        itemMap.put(child.getNodeName(), child.getFirstChild().getNodeValue());
                    }
                    
                    child = child.getNextSibling();
                }
                
                logger.info(itemMap);
                resultList.add(itemMap);
            }
        }
        
        return resultList;
    }
    
    /**
     * ��������� ��ȯ �׽�Ʈ
     * 
     * @param resSOAPMessage, messageKey, isDecrypt
     * @return List
     */
    public static List parseDataTestUser() throws Exception {
        
        List resultList = new ArrayList();
        
        String decryptMessage = 
        
        //logger.info(decryptMessage);
        
        Document doc = getDocument(decryptMessage);
        //logger.info("[document.hasChildNodes] : " + doc.hasChildNodes());
        
        Element elem = doc.getDocumentElement();
        NodeList dataList = elem.getElementsByTagName("list");
        int dataLength = dataList != null ? dataList.getLength() : 0;
        
        for ( int i = 0; i < dataLength; i++ ) {
            
            Node itemNode = dataList.item(i);
            
            if ( itemNode != null ) {
                
                int itemLength = itemNode.getChildNodes().getLength();
                Node child = itemNode.getFirstChild();
                
                Map itemMap = new HashMap(itemLength);
                
                while ( child != null ) {
                    
                    if ( child.getFirstChild() != null ) {
                        itemMap.put(child.getNodeName(), child.getFirstChild().getNodeValue());
                    }
                    
                    child = child.getNextSibling();
                }
                
                logger.info(itemMap);
                resultList.add(itemMap);
            }
        }
        
        return resultList;
    }
    
}