package org.mescedia.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mescedia.helper.XsdValidator;
import org.mescedia.readerCache.Edifact2XmlReaderCache;
import org.mescedia.readerCache.Xml2EdifactReaderCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.UUID;

public class MessageModelLoader implements Processor {

    private static final Logger log = LoggerFactory.getLogger(MessageModelLoader.class);
    private static final String xsdPath = "target/classes/xsd/messageModels.xsd" ;

    private Long startTs ;
    private String stateMsg = "" ;

    @Override
    public void process(Exchange exchange) throws IOException, SAXException {

        String uuid = UUID.randomUUID().toString() ;
        exchange.getIn().setHeader("Transaction-Id", uuid);

        startTs = System.currentTimeMillis();
        stateMsg = "Success";

        String messageIn = exchange.getIn().getBody(String.class);
        XsdValidator.validate(xsdPath, messageIn);

        log.debug("XSD validation successfull!");

        /* input message example
        <?xml version="1.0" encoding="UTF-8"?>
        <messageModels>
          <processor type="xml2edifact">
            <reader messageType="ORDERS" messageVersion="d01b" />
            <reader messageType="ORDRSP" messageVersion="d01b" />
            <reader messageType="INVOIC" messageVersion="d01b" />
            <reader messageType="DESADV" messageVersion="d01b" />
          </processor>
          <processor type="edifact2xml">
            <reader messageType="PRICAT" messageVersion="d96a" />
            <reader messageType="ORDERS" messageVersion="d96a" />
            <reader messageType="INVOIC" messageVersion="d96a" />
            <reader messageType="DESADV" messageVersion="d96a" />
            <reader messageType="SLSRPT" messageVersion="d96a" />
            <reader messageType="INVRPT" messageVersion="d96a" />
          </processor>
        </messageModels>
        */

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse( new InputSource( new StringReader( messageIn ) ) );
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("processor");
            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    String processorType = element.getAttribute("type");

                    NodeList readerList = element.getElementsByTagName("reader");
                    for (int j = 0; j < readerList.getLength(); j++) {

                        String msgType = readerList.item(j).getAttributes().getNamedItem("messageType").getTextContent().toUpperCase();
                        String msgVersion = readerList.item(j).getAttributes().getNamedItem("messageVersion").getTextContent().toUpperCase();

                        if (processorType.equals("edifact2xml")) {
                            log.debug("Loading messageModel: edifact2xml -> " + msgType + "." + msgVersion);
                            Edifact2XmlReaderCache.getInstance().getSmooks(msgVersion, msgType).createExecutionContext();
                        }
                        else if (processorType.equals("xml2edifact")) {
                            log.debug("Loading messageModel: xml2edifact -> " + msgType + "." + msgVersion);
                            Xml2EdifactReaderCache.getInstance().getSmooks(msgVersion, msgType).createExecutionContext();
                        }
                    }
                }
            }

            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "200");
            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/xml");
            exchange.getIn().setBody("<?xml version=\"1.0\" encoding=\"UTF-8\"?><status>OK</status>");

        } catch (SAXException | IOException | ParserConfigurationException e) {
            stateMsg = "Error";
            log.error(e.getMessage());
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "400");
            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/xml");
            exchange.getIn().setBody("<?xml version=\"1.0\" encoding=\"UTF-8\"?><status>ERROR</status>");
        }

        String duration = String.valueOf((System.currentTimeMillis() - startTs));
        log.info("Message state: " +stateMsg+ "; transaction: "+uuid+"; processing duration: " + duration + " ms" )  ;
    }
}
