package org.mescedia.analyser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class MessageFormatAnalyserRule extends MessageRuleBase {

    private static final Logger log = LoggerFactory.getLogger(MessageFormatAnalyserRule.class);

    public MessageFormatAnalyserRule() { }

    public boolean match(String _msgExtract) {

        boolean retVal = false;
        switch(this.getType().toLowerCase())  {
            case "indexof":
                retVal = ( _msgExtract.indexOf(getValue()) > -1 );
                break;
            case "regex":
                retVal = ( Pattern.compile(this.getValue(),Pattern.DOTALL).matcher(_msgExtract).find() );
                break;
        }
        return retVal;
    }
}
