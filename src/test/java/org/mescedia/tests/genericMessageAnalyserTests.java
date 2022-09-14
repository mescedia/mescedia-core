package org.mescedia.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mescedia.analyser.MessageAnalyser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class genericMessageAnalyserTests {

    private static final Logger log = LoggerFactory.getLogger(genericMessageAnalyserTests.class);

    private static final String dfdlXmlEdifact = "<D96A:Interchange xmlns:D96A=\"http://www.ibm.com/dfdl/edi/un/edifact/D96A\" xmlns:srv=\"http://www.ibm.com/dfdl/edi/un/service/4.1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><UNA><CompositeSeparator>:</CompositeSeparator><FieldSeparator>+</FieldSeparator><DecimalSeparator>.</DecimalSeparator><EscapeCharacter>?</EscapeCharacter><RepeatSeparator> </RepeatSeparator><SegmentTerminator>'</SegmentTerminator></UNA><UNB><S001><E0001>UNOC</E0001><E0002>3</E0002></S001><S002><E0004>MESCEDIA-INITIALIZER</E0004><E0007>14</E0007></S002><S003><E0010>MESCEDIA-INITIALIZER</E0010><E0007>14</E0007></S003><S004><E0017>181028</E0017><E0019>1714</E0019></S004><E0020>9910577901341</E0020></UNB><D96A:Message><UNH><E0062>1</E0062><S009><E0065>DESADV</E0065><E0052>D</E0052><E0054>96A</E0054><E0051>UN</E0051><E0057>EAN006</E0057></S009></UNH><D96A:DESADV><BGM><C002><E1001>351</E1001></C002><E1004>INITIALIZER</E1004><E1225>9</E1225></BGM></D96A:DESADV><UNT><E0074>51</E0074><E0062>1</E0062></UNT></D96A:Message><UNZ><E0036>1</E0036><E0020>9910577901341</E0020></UNZ></D96A:Interchange>";
    private static final String edifact = "UNA:+.? 'UNB+UNOC:3+MESCEDIA-INITIALIZER:14+MESCEDIA-INITIALIZER:14+181028:1714+9910577901341'UNH+1+DESADV:D:93A:UN:EAN006'BGM+351+INITIALIZER+9'UNT+51+1'UNZ+1+9910577901341'";
    private static final String xmlIdoc = "<DESADV01><IDOC BEGIN=\"1\"><EDI_DC40 SEGMENT=\"1\"><TABNAM>EDI_DC40</TABNAM><MANDT>100</MANDT><DOCNUM>0000000011111111</DOCNUM><DOCREL>700</DOCREL><STATUS>30</STATUS><DIRECT>1</DIRECT><OUTMOD>2</OUTMOD><IDOCTYP>DESADV01</IDOCTYP><MESTYP>DESADV</MESTYP><SNDPOR>SAPP01</SNDPOR><SNDPRT>LS</SNDPRT><SNDPRN>134568790</SNDPRN><SNDLAD>5465465465464</SNDLAD><RCVPOR>MESCEDIA</RCVPOR><RCVPRT>KU</RCVPRT><RCVPFC>SP</RCVPFC><RCVPRN>123456487904</RCVPRN><RCVLAD>111123231321</RCVLAD><CREDAT>20220912</CREDAT><CRETIM>064159</CRETIM><SERIAL>20220912064229</SERIAL></EDI_DC40></IDOC></DESADV01>";

    @BeforeAll
    static void setup()  {}

    @BeforeEach
    void init() {}

    @Test
    void genericMessageFormatAnalyserTestEdifact() throws Exception {

        MessageAnalyser gmAnalyser = MessageAnalyser.getInstance();
        gmAnalyser.setMessage(edifact);
        gmAnalyser.analyse();
        assertTrue(gmAnalyser.getMessageFormat().equals("Edifact")) ;
    }

    @Test
    void genericMessageFormatAnalyserTestDfdlXmlEdifact() throws Exception {

        MessageAnalyser gmAnalyser = MessageAnalyser.getInstance();
        gmAnalyser.setMessage(dfdlXmlEdifact);
        gmAnalyser.analyse();
        assertTrue(gmAnalyser.getMessageFormat().equals("DfdlXmlEdifact")) ;
    }

    @Test
    void genericMessageFormatAnalyserTestXmlSapIdoc() throws Exception {

         MessageAnalyser gmAnalyser = MessageAnalyser.getInstance();
         gmAnalyser.setMessage(xmlIdoc);
         gmAnalyser.analyse();
         assertTrue(gmAnalyser.getMessageFormat().equals("SapIdocXml")) ;
    }
}
