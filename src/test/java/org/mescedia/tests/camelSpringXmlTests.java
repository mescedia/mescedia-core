package org.mescedia.tests;

import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class camelSpringXmlTests extends CamelSpringTestSupport {

    @Override
    protected AbstractApplicationContext createApplicationContext() {

        return new ClassPathXmlApplicationContext("file:src/test/java/org/mescedia/tests/camelTestRoutes.xml");
    }

    private static final String dfdlXmlEdifactD96a = "<D96A:Interchange xmlns:D96A=\"http://www.ibm.com/dfdl/edi/un/edifact/D96A\" xmlns:srv=\"http://www.ibm.com/dfdl/edi/un/service/4.1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><UNA><CompositeSeparator>:</CompositeSeparator><FieldSeparator>+</FieldSeparator><DecimalSeparator>.</DecimalSeparator><EscapeCharacter>?</EscapeCharacter><RepeatSeparator> </RepeatSeparator><SegmentTerminator>'</SegmentTerminator></UNA><UNB><S001><E0001>UNOC</E0001><E0002>3</E0002></S001><S002><E0004>MESCEDIA-INITIALIZER</E0004><E0007>14</E0007></S002><S003><E0010>MESCEDIA-INITIALIZER</E0010><E0007>14</E0007></S003><S004><E0017>181028</E0017><E0019>1714</E0019></S004><E0020>9910577901341</E0020></UNB><D96A:Message><UNH><E0062>1</E0062><S009><E0065>DESADV</E0065><E0052>D</E0052><E0054>96A</E0054><E0051>UN</E0051><E0057>EAN006</E0057></S009></UNH><D96A:DESADV><BGM><C002><E1001>351</E1001></C002><E1004>INITIALIZER</E1004><E1225>9</E1225></BGM></D96A:DESADV><UNT><E0074>51</E0074><E0062>1</E0062></UNT></D96A:Message><UNZ><E0036>1</E0036><E0020>9910577901341</E0020></UNZ></D96A:Interchange>";
    private static final String extensionFunctionsTestResult = "<root xmlns:D96A=\"http://www.ibm.com/dfdl/edi/un/edifact/D96A\" xmlns:srv=\"http://www.ibm.com/dfdl/edi/un/service/4.1\" xmlns:java=\"http://xsltExtensions.mescedia.org\"><messageFormat value=\"DfdlXmlEdifact\"/><!--=======================--><!--input analyser result file--><!----><!--=======================--><interface type=\"inbound\" user=\"test\">AS2-TEST</interface><message type=\"DESADV\" version=\"D96A\" content-type=\"application/edifact\"/><sender type=\"14\">MESCEDIA-INITIALIZER</sender><receiver type=\"14\">MESCEDIA-INITIALIZER</receiver></root>";
    private static final String xqueryTestResult = "<root xmlns:D96A=\"http://www.ibm.com/dfdl/edi/un/edifact/D96A\" xmlns:srv=\"http://www.ibm.com/dfdl/edi/un/service/4.1\"><!--=======================--><!--input analyser result file--><!--=======================--><interface type=\"inbound\" user=\"test\">AS2-TEST</interface><message content-type=\"application/edifact\" type=\"DESADV\" version=\"D96A\"/><sender type=\"14\">MESCEDIA-INITIALIZER</sender><receiver type=\"14\">MESCEDIA-INITIALIZER</receiver></root>";
    private static final String simpleTransformerTestResult = "<root xmlns:D96A=\"http://www.ibm.com/dfdl/edi/un/edifact/D96A\" xmlns:srv=\"http://www.ibm.com/dfdl/edi/un/service/4.1\"><!--=======================--><!--input analyser result file--><!--=======================--><interface type=\"inbound\" user=\"test\">AS2-TEST</interface><message type=\"DESADV\" version=\"D96A\" content-type=\"application/edifact\"/><sender type=\"14\">MESCEDIA-INITIALIZER</sender><receiver type=\"14\">MESCEDIA-INITIALIZER</receiver></root>" ;


    @Test
    public void xsltSimpleTransformerTest() throws Exception {

        getMockEndpoint("mock:xsltSimpleTransformerTestOut").expectedBodiesReceived(simpleTransformerTestResult);
        template.sendBody("direct:xsltSimpleTransformerTestIn", dfdlXmlEdifactD96a);
        assertMockEndpointsSatisfied();
    }

    @Test
    public void xsltExtensionFunctionsTest() throws Exception {

        getMockEndpoint("mock:xsltExtensionFunctionTestOut").expectedBodiesReceived(extensionFunctionsTestResult);
        template.sendBody("direct:xsltExtensionFunctionTestIn", dfdlXmlEdifactD96a);
        assertMockEndpointsSatisfied();
    }

    @Test
    public void xquerySimpleTransformerTest() throws Exception {

        getMockEndpoint("mock:xquerySimpleTransformerTestOut").expectedBodiesReceived(xqueryTestResult);
        template.sendBody("direct:xquerySimpleTransformerTestIn", dfdlXmlEdifactD96a);
        assertMockEndpointsSatisfied();
    }

}
