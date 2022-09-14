package org.mescedia.helper;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.mescedia.analyser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.HashMap;

public class CacheMessageReader {

    private static final Logger log = LoggerFactory.getLogger(CacheMessageReader.class);
    private ContentAnalyserInterface contentAnalyser = null;
    private HashMap<String,  String> readerConfig = new HashMap<String, String>() ;
    private String configMessage = "";
    private Document doc = null;
    public CacheMessageReader(String _msg)  {
        this.configMessage = _msg ;
    }
    public void read() throws IOException, ConfigurationException, XPathExpressionException {

        readConfigMessages();
    }

    public HashMap<String, String>  getReaderConfig()   {

        return this.readerConfig ;
    }

    private void  readConfigMessages() throws XPathExpressionException {

        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(this.configMessage.getBytes("UTF-8")));
            doc.getDocumentElement().normalize();

            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/configuration//message";
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
                    doc, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node mNode = nodeList.item(i);

                if (mNode.getNodeName().equals("message")) {

                    String messageIn = mNode.getTextContent().trim();

                    MessageAnalyser gmAnalyser = MessageAnalyser.getInstance();
                    gmAnalyser.setMessage(messageIn);
                    gmAnalyser.analyse();

                    String mFormat = gmAnalyser.getMessageFormat();
                    String mVersion = gmAnalyser.getMessageVersion().toLowerCase();
                    String mType = gmAnalyser.getMessageType().toUpperCase();

                    readerConfig.put(mVersion + mType + mFormat, messageIn) ;
                }
            }
        } catch (ParserConfigurationException pe) {
            throw new RuntimeException(pe);
        } catch (SAXException se) {
            throw new RuntimeException(se);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
