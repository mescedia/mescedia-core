package org.mescedia.helper;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class XmlFormatter {

    private String xml;
    private static XmlFormatter instance = null;
    private Document doc = null;
    private static final String XSLT_FILENAME = "errorReportFormatter.xslt";

    private XmlFormatter()    {    }

    public static XmlFormatter getInstance()   {
        if (instance == null)
            instance = new XmlFormatter();

        return instance ;
    }

    public synchronized String format(String xml, String encoding) throws IOException, ParserConfigurationException, SAXException, TransformerException {

        doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().
                parse(new ByteArrayInputStream(xml.getBytes(encoding)));

        doc.getDocumentElement().normalize();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        StreamResult result = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
        return result.getWriter().toString();
    }

    // use with caution: xslt Formatter may ruin xml namespace content
    public synchronized String xsltFormat(String xml, String encoding)  {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try (InputStream is = new ByteArrayInputStream(xml.getBytes(encoding)) ) {

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(
                    new StreamSource( this.getClass().getClassLoader()
                            .getResourceAsStream(XSLT_FILENAME ) ));

            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
            StreamResult result = new StreamResult(new StringWriter());
            transformer.transform(new DOMSource(doc), result);
            return result.getWriter().toString();

        } catch (IOException | ParserConfigurationException |
                 SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return xml ;
    }
}
