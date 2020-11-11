package nexti.ejms.util;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLUtil {
	 /**
     * XML 파일에서 태그에 따른 attribute 값 Array Object를 얻어온다.<br>
     * @param   root         xml Element 
     * @param   tagName      파싱할 tagName 값
     * @param   attribute      파싱할 attribute 값
     * @return    aList       Array List 
     */
    public  ArrayList getSubTagAttributes(Element root, String tagName,String attribute) 
    {
        ArrayList aList = new ArrayList();
        NodeList nList = root.getElementsByTagName(tagName);
        for(int i=0;i<nList.getLength();i++) {
            Node node = nList.item(i);
            if(node!=null) {
                Node  child = nList.item(i);
                if (   (child != null) && 
                        (child.getNodeName() != null) && 
                        child.getNodeName().equals(tagName) ) {
                    if (child instanceof Element) {
                        aList.add(((Element)child).getAttribute(attribute));
                    }
                }
            }          
        }
        return aList;   
    }
    
    /**
     * XML 파일에서 태그에 따른 attribute 값 String을 얻어온다.<br>
     * @param   root         xml Element 
     * @param   tagName      파싱할 tagName 값
     * @param   attribute      파싱할 attribute 값
     * @return    returnString    파싱한 String 
     */
    public  String getSubTagAttribute(   Element root, 
            String tagName, 
            String attribute   ) {
        String returnString = "";
        NodeList aList = root.getElementsByTagName(tagName);
        for (int loop = 0; loop < aList.getLength(); loop++) {
            Node node = aList.item(loop);
            if (node != null) 
            {
                Node  child = aList.item(loop);
                if (   (child != null) && 
                        (child.getNodeName() != null) && 
                        child.getNodeName().equals(tagName) ) {
                    if (child instanceof Element) {
                        return ((Element)child).getAttribute(attribute);
                    }
                }
            }
        }
        return returnString;
    }
    
    /**
     * XML 파일에서 sub태그에 따른 attribute 값 String을 얻어온다.<br>
     * @param   root         xml Element 
     * @param   tagName      파싱할 tagName 값
     * @param   subTagName   파싱할 subTagName 값
     * @param   attribute      파싱할 attribute 값
     * @return    returnString    파싱한 String 
     */
    public  String getSubTagAttribute(   Element root, 
            String tagName, 
            String subTagName, 
            String attribute   ) {
        String returnString = "";
        NodeList list = root.getElementsByTagName(tagName);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) 
            {
                NodeList  children = node.getChildNodes();
                for (int innerLoop=0;innerLoop<children.getLength();innerLoop++) 
                {
                    Node  child = children.item(innerLoop);
                    if (   (child != null) && 
                            (child.getNodeName() != null) && 
                            child.getNodeName().equals(subTagName) ) {
                        if (child instanceof Element) {
                            return ((Element)child).getAttribute(attribute);
                        }
                    }
                } // end inner loop
            }
        }
        return returnString;
    }
    
    /**
     * XML 파일을 로드 하여 Element를 생성한다. <br>
     * @param   fileName      파싱할 xml 파일
     * @return    root          로드된 Element 
     */
    public  Element loadXmlDocument(String fileName) 
    {
        try {
            DocumentBuilderFactory factory = 
                DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));
            
            Element root = doc.getDocumentElement();
            root.normalize();
            
            return root;
        }catch (SAXParseException err) {
            System.out.println("loadXmlDocument(String fileName)-XmlUtil Parsing error" + ", line " +
                    err.getLineNumber () + ", uri " + err.getSystemId ());
            System.out.println("loadXmlDocument(String fileName)-XmlUtil error: " + err.getMessage ());
        } catch (SAXException e) {
            System.out.println("loadXmlDocument(String fileName)-XmlUtil error: " + e);
        } catch (java.net.MalformedURLException mfx) {
            System.out.println("loadXmlDocument(String fileName)-XmlUtil error: " + mfx);
        } catch (java.io.IOException e) {
            System.out.println("loadXmlDocument(String fileName)-XmlUtil error: " + e);
        } catch (Exception pce) {
            pce.printStackTrace();
            System.out.println("loadXmlDocument(String fileName)-XmlUtil error: " + pce);
        }
        return null;
    }
    
    /**
     * XML 파일을 로드 하여 Element를 생성한다. <br>
     * @param   fileName      파싱할 xml 파일
     * @return    root          로드된 Element 
     */
    public Element loadXmlDocument(File xmlFile) 
    {
        try {
            DocumentBuilderFactory factory = 
                DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            
            Element root = doc.getDocumentElement();
            root.normalize();
            
            return root;
        }catch (SAXParseException err) {
            System.out.println("loadXmlDocument(File xmlFile)-XmlUtil Parsing error" + ", line " +
                    err.getLineNumber () + ", uri " + err.getSystemId ());
            System.out.println("loadXmlDocument(File xmlFile)-XmlUtil error: " + err.getMessage ());
        } catch (SAXException e) {
            System.out.println("loadXmlDocument(File xmlFile)-XmlUtil error: " + e);
        } catch (java.net.MalformedURLException mfx) {
            System.out.println("loadXmlDocument(File xmlFile)-XmlUtil error: " + mfx);
        } catch (java.io.IOException e) {
            System.out.println("loadXmlDocument(File xmlFile)-XmlUtil error: " + e);
        } catch (Exception pce) {
            pce.printStackTrace();
            System.out.println("loadXmlDocument(File xmlFile)-XmlUtil error: " + pce);
        }
        return null;
    }   
    /**
     * XML 파일에서 태그에 따른  String을 얻어온다.<br>
     * @param   root               xml Element 
     * @param   tagName            파싱할 tagName 값
     * @return    child.getNodeValue()    파싱한 String 
     */
    public String getTagValue(Element root, String tagName) 
    {
        NodeList nList = root.getElementsByTagName(tagName);
        for(int i=0;i<nList.getLength();i++) {
            Node node = nList.item(i);
            if(node!=null) {
                Node child = node.getFirstChild();
                if( (child!=null) && (child.getNodeValue()!=null) ) {
                    return child.getNodeValue();   
                }
            }          
        }
        return "";   
    }
    
    /**
     * XML 파일에서 태그에 따른  String을 얻어온다.<br>
     * @param   root               xml Element 
     * @param   tagName            파싱할 tagName 값
     * @return    child.getNodeValue()    파싱한 String 
     */
    public String getTagValueByName(Element root, String tagName, String attName, String attValue) 
    {
        NodeList nList = root.getElementsByTagName(tagName);
        for(int i=0;i<nList.getLength();i++) {
            Node node = nList.item(i);
            if(node!=null && node instanceof Element) {
            	String att=((Element)node).getAttribute(attName);
            	if(att!=null && att.equals(attValue))
            		return node.getFirstChild().getNodeValue();
            }          
        }
        return "";   
    }
    
    /**
     * XML 파일에서 태그에 따른  값 ArrayList Object를 얻어온다.<br>
     * @param   root         xml Element 
     * @param   tagName      파싱할 tagName 값
     * @return    list          ArrayList Object 
     */
    public ArrayList getTagValues(Element root, String tagName) 
    {
        ArrayList list = new ArrayList();
        NodeList nList = root.getElementsByTagName(tagName);
        for(int i=0;i<nList.getLength();i++) {
            Node node = nList.item(i);
            if(node!=null) {
                Node child = node.getFirstChild();
                if( (child!=null) && (child.getNodeValue()!=null) ) {
                    list.add(child.getNodeValue());   
                }
            }          
        }
        return list;
    }
    
    /**
     * XML 파일에서 하나의 태그를 가져온다. <br>
     * @param   root               xml Element 
     * @param   tagName            파싱할 tagName 값
     * @return    Element    파싱한 하나의 Element 
     */
    public Element getTag(Element root, String tagName) 
    {
        NodeList nList = root.getElementsByTagName(tagName);
        return (Element)nList.item(0);   
    }
    
    /**
     * XML 파일에서 태그에 따른  String을 얻어온다.<br>
     * @param   root               xml Element 
     * @param   tagName            파싱할 tagName 값
     * @return    child.getNodeValue()    파싱한 String 
     */
    public NodeList getTagList(Element root, String tagName) 
    {
        return root.getElementsByTagName(tagName);
    }   
}