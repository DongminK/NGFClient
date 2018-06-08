package insoft.client;

import insoft.openmanager.message.Message;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XPathXMLParser {

	public static Message parse(String messageName, String response, String pattern) {
        Message rspMsg = new Message(messageName);

        ArrayList<Node> findNodeList = new ArrayList<Node>();
        ArrayList<Node> childFindNodeList = new ArrayList<Node>();

        findNodeList = parser(response, "//*/" + pattern);

        Message inMsg = null;
        Vector<Message> vNode = new Vector<Message>();

        int index = 1;

        for(Node node : findNodeList) {
            inMsg = new Message("NODE");

            childFindNodeList = parser(response, "//*/" + pattern + "[" + (index++) + "]/*");
            inMsg.setInteger("child_count", childFindNodeList.size());

            inMsg.setString("name", node.getNodeName().trim());

            if(node.getTextContent() != null)
                inMsg.setString("value", node.getTextContent().trim());
            else
                inMsg.setString("value", "");

            NamedNodeMap attrMap = node.getAttributes();

            Vector<Message> vAttr = new Vector<Message>();
            for(int i = 0, size = attrMap.getLength(); i < size; i++) {
                Node attrNode = attrMap.item(i);
                Message attrMsg = new Message("ATTRIBUTE");

                attrMsg.setString("name", attrNode.getNodeName().trim());

                if(attrNode.getNodeValue() != null)
                    attrMsg.setString("value", attrNode.getTextContent().trim());
                else
                    attrMsg.setString("value", "");

                vAttr.add(attrMsg);
            }

            inMsg.setVector("attrs", vAttr);
            vNode.add(inMsg);
        }
        rspMsg.setVector("nodes", vNode);

        return rspMsg;
    }


    private static ArrayList<Node> parser(String xml, String pattern) {
        ArrayList<Node> findNodeList = new ArrayList<Node>();

        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            ErrorHandler errorHandler = new ErrorHandler() {
                @Override
                public void warning(SAXParseException paramSAXParseException) throws SAXException {}


                @Override
                public void fatalError(SAXParseException paramSAXParseException) throws SAXException {}


                @Override
                public void error(SAXParseException paramSAXParseException) throws SAXException {}
            };

            docBuilder.setErrorHandler(errorHandler);
            Document doc = docBuilder.parse(is);

            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList)xpath.evaluate(pattern, doc, XPathConstants.NODESET);

            for(int i = 0, size = nodeList.getLength(); i < size; i++) {
                findNodeList.add(nodeList.item(i));
            }

        }
        catch(Exception e) {
            // TODO Auto-generated catch block
        	e.printStackTrace();
        }

        return findNodeList;
    }
    
}
