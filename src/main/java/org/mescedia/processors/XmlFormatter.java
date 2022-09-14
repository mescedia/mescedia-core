package org.mescedia.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFormatter implements Processor {

    private static final Logger log = LoggerFactory.getLogger(XmlFormatter.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        String xmlIn =  exchange.getIn().getBody().toString();
        String xmlOut = "";

        if (exchange.getIn().getHeaders().get("X-MESCEDIA-XML-FORMATTER-TYPE").toString().equals("XSLT"))
            xmlOut = org.mescedia.helper.XmlFormatter.getInstance().xsltFormat(xmlIn, "UTF-8");
        else
            xmlOut =  org.mescedia.helper.XmlFormatter.getInstance().format(xmlIn, "UTF-8");

        log.info("Formatting Xml -> bytes in: " + xmlIn.length() + "; bytes out: " + xmlOut.length() );

        exchange.getIn().setBody(xmlOut);

    }
}
