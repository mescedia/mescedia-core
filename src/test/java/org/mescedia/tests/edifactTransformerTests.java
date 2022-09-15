package org.mescedia.tests;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mescedia.helper.DbDataProvider;

public class edifactTransformerTests extends  CamelTestSupport  {

    private static final String dfdlXmlEdifact = "<D03B:Interchange xmlns:D03B=\"http://www.ibm.com/dfdl/edi/un/edifact/D03B\" xmlns:srv=\"http://www.ibm.com/dfdl/edi/un/service/4.1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><UNB><S001><E0001>UNOA</E0001><E0002>4</E0002></S001><S002><E0004>MESCEDIA-INITIALIZER</E0004><E0007>1</E0007></S002><S003><E0010>MESCEDIA-INITIALIZER</E0010><E0007>1</E0007></S003><S004><E0017>20051107</E0017><E0019>1159</E0019></S004><E0020>6002123</E0020></UNB><D03B:Message><UNH><E0062>1222222</E0062><S009><E0065>ORDERS</E0065><E0052>D</E0052><E0054>03B</E0054><E0051>UN</E0051><E0057>EAN008</E0057></S009></UNH><D03B:ORDERS><BGM><C002><E1001>220</E1001></C002><C106><E1004>MESCEDIA-655</E1004></C106><E1225>9</E1225></BGM><DTM><C507><E2005>137</E2005><E2380>20051107</E2380><E2379>102</E2379></C507></DTM><SegGrp-2><NAD><E3035>BY</E3035><C082><E3039>5432101234567</E3039><E3055>9</E3055></C082></NAD></SegGrp-2><SegGrp-2><NAD><E3035>SU</E3035><C082><E3039>4321012345678</E3039><E3055>9</E3055></C082></NAD><SegGrp-5><CTA><E3139>AA</E3139></CTA><COM><C076><E3148>s11</E3148><E3155>AA</E3155></C076><C076><E3148>s21</E3148><E3155>AA</E3155></C076><C076><E3148>s31</E3148><E3155>AA</E3155></C076></COM></SegGrp-5></SegGrp-2><SegGrp-28><LIN><E1082>1</E1082><E1229>1</E1229><C212><E7140>0007456104</E7140><E7143>IB</E7143></C212></LIN><QTY><C186><E6063>1</E6063><E6060>25</E6060></C186></QTY><FTX><E4451>AFM</E4451><E4453>1</E4453><C107><E4441></E4441></C107><C108><E4440>Sort of Things</E4440></C108></FTX></SegGrp-28><SegGrp-28><LIN><E1082>2</E1082><E1229>1</E1229><C212><E7140>0074569099</E7140><E7143>IB</E7143></C212></LIN><QTY><C186><E6063>1</E6063><E6060>25</E6060></C186></QTY><FTX><E4451>AFM</E4451><E4453>1</E4453><C107><E4441></E4441></C107><C108><E4440>The Mobbit</E4440></C108></FTX></SegGrp-28><SegGrp-28><LIN><E1082>3</E1082><E1229>1</E1229><C212><E7140>007004656</E7140><E7143>IB</E7143></C212></LIN><QTY><C186><E6063>1</E6063><E6060>16</E6060></C186></QTY><FTX><E4451>AFM</E4451><E4453>1</E4453><C107><E4441></E4441></C107><C108><E4440>The Gilmarillion</E4440></C108></FTX></SegGrp-28><SegGrp-28><LIN><E1082>4</E1082><E1229>1</E1229><C212><E7140>00076006777</E7140><E7143>IB</E7143></C212></LIN><QTY><C186><E6063>1</E6063><E6060>10</E6060></C186></QTY><FTX><E4451>AFM</E4451><E4453>1</E4453><C107><E4441></E4441></C107><C108><E4440>The Sons of the Desert</E4440></C108></FTX></SegGrp-28><UNS><E0081>S</E0081></UNS><CNT><C270><E6069>2</E6069><E6066>4</E6066></C270></CNT></D03B:ORDERS><UNT><E0074>22</E0074><E0062>1222222</E0062></UNT></D03B:Message><UNZ><E0036>1</E0036><E0020>6002123</E0020></UNZ></D03B:Interchange>";

    private static final String edifactD96A ="UNA:+.? 'UNB+UNOC:3+MESCEDIA-INITIALIZER:14+MESCEDIA-INITIALIZER:14+181028:1714+9910577901341'UNH+1+DESADV:D:96A:UN:EAN006'BGM+351+INITIALIZER+9'UNT+51+1'UNZ+1+9910577901341'";
    private static final String dfdlXmlEdifactD96a = "<D96A:Interchange xmlns:D96A=\"http://www.ibm.com/dfdl/edi/un/edifact/D96A\" xmlns:srv=\"http://www.ibm.com/dfdl/edi/un/service/4.1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><UNA><CompositeSeparator>:</CompositeSeparator><FieldSeparator>+</FieldSeparator><DecimalSeparator>.</DecimalSeparator><EscapeCharacter>?</EscapeCharacter><RepeatSeparator> </RepeatSeparator><SegmentTerminator>'</SegmentTerminator></UNA><UNB><S001><E0001>UNOC</E0001><E0002>3</E0002></S001><S002><E0004>MESCEDIA-INITIALIZER</E0004><E0007>14</E0007></S002><S003><E0010>MESCEDIA-INITIALIZER</E0010><E0007>14</E0007></S003><S004><E0017>181028</E0017><E0019>1714</E0019></S004><E0020>9910577901341</E0020></UNB><D96A:Message><UNH><E0062>1</E0062><S009><E0065>DESADV</E0065><E0052>D</E0052><E0054>96A</E0054><E0051>UN</E0051><E0057>EAN006</E0057></S009></UNH><D96A:DESADV><BGM><C002><E1001>351</E1001></C002><E1004>INITIALIZER</E1004><E1225>9</E1225></BGM></D96A:DESADV><UNT><E0074>51</E0074><E0062>1</E0062></UNT></D96A:Message><UNZ><E0036>1</E0036><E0020>9910577901341</E0020></UNZ></D96A:Interchange>";
    private static final String simpleXsltResult = "<root xmlns:srv=\"http://www.ibm.com/dfdl/edi/un/service/4.1\" xmlns:D96A=\"http://www.ibm.com/dfdl/edi/un/edifact/D96A\"><!--=======================--><!--input analyser result file--><!--=======================--><interface type=\"inbound\" user=\"test\">AS2-TEST</interface><message type=\"DESADV\" version=\"D96A\" content-type=\"application/edifact\"/><sender type=\"14\">MESCEDIA-INITIALIZER</sender><receiver type=\"14\">MESCEDIA-INITIALIZER</receiver></root>";
    private static final String edifact = "UNB+UNOA:4+MESCEDIA-INITIALIZER:1+MESCEDIA-INITIALIZER:1+20051107:1159+6002123'UNH+1222222+ORDERS:D:03B:UN:EAN008'BGM+220+MESCEDIA-655+9'DTM+137:20051107:102'NAD+BY+5432101234567::9'NAD+SU+4321012345678::9'CTA+AA'COM+s11:AA*s21:AA*s31:AA'LIN+1+1+0007456104:IB'QTY+1:25'FTX+AFM+1++Sort of Things'LIN+2+1+0074569099:IB'QTY+1:25'FTX+AFM+1++The Mobbit'LIN+3+1+007004656:IB'QTY+1:16'FTX+AFM+1++The Gilmarillion'LIN+4+1+00076006777:IB'QTY+1:10'FTX+AFM+1++The Sons of the Desert'UNS+S'CNT+2:4'UNT+22+1222222'UNZ+1+6002123'";

    @BeforeAll
    static void setup() throws Exception {

        DbDataProvider.getInstance().readMessageFormats();
    }

    @Test
    public void edifact2XmlTest() throws Exception {

        getMockEndpoint("mock:result").expectedBodiesReceived(dfdlXmlEdifact);
        template.sendBody("direct:edifactIn", edifact);
        assertMockEndpointsSatisfied();
    }

    @Test
    public void xml2EdifactTest() throws Exception {

        getMockEndpoint("mock:result").expectedBodiesReceived(edifact);
        template.sendBody("direct:dfdlXmlIn", dfdlXmlEdifact);
        assertMockEndpointsSatisfied();
    }

    @Test
    public void simpleXsltTransformerTest() throws Exception {

        getMockEndpoint("mock:result").expectedBodiesReceived(simpleXsltResult);
        template.sendBody("direct:dfdlXmlIn2XmlOut", dfdlXmlEdifactD96a);
        assertMockEndpointsSatisfied();
    }

    @Test
    public void simpleRoutingSlipTest() throws Exception {

        getMockEndpoint("mock:result").expectedBodiesReceived(simpleXsltResult);
        template.sendBody("direct:edifactD96A2XmlOut", edifactD96A);
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RoutesBuilder createRouteBuilder() {


        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                context.getRegistry().bind("edifact2xml", org.mescedia.processors.Edifact2Xml.class);
                context.getRegistry().bind("xml2edifact", org.mescedia.processors.Xml2Edifact.class);

                from("direct:edifactIn")
                            .log("input message:")
                            .log("${body}")
                        .bean("edifact2xml","process")
                            .log("output message:")
                            .log("${body}")
                        .to("mock:result");

                from("direct:dfdlXmlIn")
                            .log("input message:")
                            .log("${body}")
                        .bean("xml2edifact","process")
                            .log("output message:")
                            .log("${body}")
                        .to("mock:result");

                from("direct:dfdlXmlIn2XmlOut")
                            .log("input message:")
                            .log("${body}")
                        .to("xslt:file:data/mapping/d96a.header.xslt")
                            .log("output message:")
                            .log("${body}")
                        .to("mock:result");

                from("direct:edifactD96A2XmlOut")
                        .setHeader("processing-pipeline").simple("bean:edifact2xml?method=process;xslt:file:data/mapping/d96a.header.xslt")
                            .log("input message:")
                            .log("${body}")
                        .log("processing pipeline: ${header.processing-pipeline}")
                        .routingSlip(header("processing-pipeline")).uriDelimiter(";").ignoreInvalidEndpoints(false)
                            .log("output message:")
                            .log("${body}")
                        .to("mock:result");

            }
        };
    }
}
