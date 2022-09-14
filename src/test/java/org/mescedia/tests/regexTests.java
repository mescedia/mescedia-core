package org.mescedia.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class regexTests {

    private static final Logger log = LoggerFactory.getLogger(regexTests.class);

    private static final String dfdlXmlEdifact = "<D96A:Interchange xmlns:D96A=\"http://www.ibm.com/dfdl/edi/un/edifact/D96A\" xmlns:srv=\"http://www.ibm.com/dfdl/edi/un/service/4.1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><UNA><CompositeSeparator>:</CompositeSeparator><FieldSeparator>+</FieldSeparator><DecimalSeparator>.</DecimalSeparator><EscapeCharacter>?</EscapeCharacter><RepeatSeparator> </RepeatSeparator><SegmentTerminator>'</SegmentTerminator></UNA><UNB><S001><E0001>UNOC</E0001><E0002>3</E0002></S001><S002><E0004>MESCEDIA-INITIALIZER</E0004><E0007>14</E0007></S002><S003><E0010>MESCEDIA-INITIALIZER</E0010><E0007>14</E0007></S003><S004><E0017>181028</E0017><E0019>1714</E0019></S004><E0020>9910577901341</E0020></UNB><D96A:Message><UNH><E0062>1</E0062><S009><E0065>DESADV</E0065><E0052>D</E0052><E0054>96A</E0054><E0051>UN</E0051><E0057>EAN006</E0057></S009></UNH><D96A:DESADV><BGM><C002><E1001>351</E1001></C002><E1004>INITIALIZER</E1004><E1225>9</E1225></BGM></D96A:DESADV><UNT><E0074>51</E0074><E0062>1</E0062></UNT></D96A:Message><UNZ><E0036>1</E0036><E0020>9910577901341</E0020></UNZ></D96A:Interchange>";

    @BeforeAll
    static void setup()  {
    }

    @BeforeEach
    void init() {}

    @Test
    void groupRegexTest1()   {

        String patt1 = "<E0052>(.+?)</E0052>.*?<E0054>.+?</E0054>" ;
        assertTrue(getMatch(dfdlXmlEdifact, patt1).equals("D"));
    }

    @Test
    void groupRegexTest2()   {

        String patt2 = "<E0052>(.+?)</E0052>.*?<E0054>(.+?)</E0054>" ;
        assertTrue(getMatch(dfdlXmlEdifact, patt2).equals("D96A"));
    }

    private String getMatch(String _msgExtract, String patt )  {

        String retVal = "";
        Matcher matcher = Pattern.compile(patt,Pattern.DOTALL).matcher(_msgExtract);

        if (matcher.find()) {
            for (int j = 1; j <= matcher.groupCount(); j++) {
                retVal += matcher.group(j);
            }
        }
        return retVal;
    }
}
