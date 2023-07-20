package org.mescedia.tests;

import org.junit.jupiter.api.Test;
import org.mescedia.readerCache.Edifact2XmlReaderCache;
import org.mescedia.readerCache.Xml2EdifactReaderCache;
import org.smooks.Smooks;
import org.xml.sax.SAXException;

import java.io.IOException;

public class readerCacheInit {

    @Test
    public void initSmooksReaderCache() throws IOException, SAXException {

        // xml2edifact
        Edifact2XmlReaderCache.getInstance().getSmooks("D96A", "PRICAT").createExecutionContext();
        Edifact2XmlReaderCache.getInstance().getSmooks("D96A", "ORDERS").createExecutionContext();
        Edifact2XmlReaderCache.getInstance().getSmooks("D96A", "INVOIC").createExecutionContext();
        Edifact2XmlReaderCache.getInstance().getSmooks("D96A", "DESADV").createExecutionContext();
        Edifact2XmlReaderCache.getInstance().getSmooks("D96A", "SLSRPT").createExecutionContext();
        Edifact2XmlReaderCache.getInstance().getSmooks("D96A", "INVRPT").createExecutionContext();


        //xml2edifact
        Xml2EdifactReaderCache.getInstance().getSmooks("D01B", "ORDERS").createExecutionContext();
        Xml2EdifactReaderCache.getInstance().getSmooks("D01B", "ORDRSP").createExecutionContext();
        Xml2EdifactReaderCache.getInstance().getSmooks("D01B", "INVOIC").createExecutionContext();
        Xml2EdifactReaderCache.getInstance().getSmooks("D01B", "DESADV").createExecutionContext();

    }

}
