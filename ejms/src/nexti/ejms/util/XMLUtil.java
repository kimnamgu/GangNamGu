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
     * XML ���Ͽ��� �±׿� ���� attribute �� Array Object�� ���´�.<br>
     * @param   root         xml Element 
     * @param   tagName      �Ľ��� tagName ��
     * @param   attribute      �Ľ��� attribute ��
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
     * XML ���Ͽ��� �±׿� ���� attribute �� String�� ���´�.<br>
     * @param   root         xml Element 
     * @param   tagName      �Ľ��� tagName ��
     * @param   attribute      �Ľ��� attribute ��
     * @return    returnString    �Ľ��� String 
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
     * XML ���Ͽ��� sub�±׿� ���� attribute �� String�� ���´�.<br>
     * @param   root         xml Element 
     * @param   tagName      �Ľ��� tagName ��
     * @param   subTagName   �Ľ��� subTagName ��
     * @param   attribute      �Ľ��� attribute ��
     * @return    returnString    �Ľ��� String 
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
     * XML ������ �ε� �Ͽ� Element�� �����Ѵ�. <br>
     * @param   fileName      �Ľ��� xml ����
     * @return    root          �ε�� Element 
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
     * XML ������ �ε� �Ͽ� Element�� �����Ѵ�. <br>
     * @param   fileName      �Ľ��� xml ����
     * @return    root          �ε�� Element 
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
     * XML ���Ͽ��� �±׿� ����  String�� ���´�.<br>
     * @param   root               xml Element 
     * @param   tagName            �Ľ��� tagName ��
     * @return    child.getNodeValue()    �Ľ��� String 
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
     * XML ���Ͽ��� �±׿� ����  String�� ���´�.<br>
     * @param   root               xml Element 
     * @param   tagName            �Ľ��� tagName ��
     * @return    child.getNodeValue()    �Ľ��� String 
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
     * XML ���Ͽ��� �±׿� ����  �� ArrayList Object�� ���´�.<br>
     * @param   root         xml Element 
     * @param   tagName      �Ľ��� tagName ��
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
     * XML ���Ͽ��� �ϳ��� �±׸� �����´�. <br>
     * @param   root               xml Element 
     * @param   tagName            �Ľ��� tagName ��
     * @return    Element    �Ľ��� �ϳ��� Element 
     */
    public Element getTag(Element root, String tagName) 
    {
        NodeList nList = root.getElementsByTagName(tagName);
        return (Element)nList.item(0);   
    }
    
    /**
     * XML ���Ͽ��� �±׿� ����  String�� ���´�.<br>
     * @param   root               xml Element 
     * @param   tagName            �Ľ��� tagName ��
     * @return    child.getNodeValue()    �Ľ��� String 
     */
    public NodeList getTagList(Element root, String tagName) 
    {
        return root.getElementsByTagName(tagName);
    }   
}