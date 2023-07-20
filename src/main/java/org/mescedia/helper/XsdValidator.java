package org.mescedia.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class XsdValidator {

    private static final Logger log = LoggerFactory.getLogger(XsdValidator.class);

    public static void validate(String xsdPath, String xmlStr) throws SAXException, IOException {

        SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new File(xsdPath));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new ByteArrayInputStream(xmlStr.getBytes("UTF-8"))));


//        try {
//            SchemaFactory factory =
//                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//            Schema schema = factory.newSchema(new File(xsdPath));
//            Validator validator = schema.newValidator();
//            validator.validate(new StreamSource(new ByteArrayInputStream(xmlStr.getBytes("UTF-8"))));
//        } catch (IOException e) {
//            log.error(e.getMessage());
//            return false;
//        } catch (SAXException e1) {
//            log.error(e1.getMessage());
//            return false;
//        }
//        return true;
    }
}
