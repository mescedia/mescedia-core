package org.mescedia.analyser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageContentAnalyserRule extends MessageRuleBase {

    private static final Logger log = LoggerFactory.getLogger(MessageContentAnalyserRule.class);

    private String item = null ;

    public MessageContentAnalyserRule()  {}

    public String getItem() {

        return item;
    }

    public void setItem(String item) {

        this.item = item;
    }

    public String getMatch(String _msgExtract)  {

        String retVal = "";
        Matcher matcher = Pattern.compile(this.getValue(),Pattern.DOTALL).matcher(_msgExtract);

        if (matcher.find()) {
            for (int j = 1; j <= matcher.groupCount(); j++) {
                retVal += matcher.group(j);
            }
        }
        return retVal;
    }
}
