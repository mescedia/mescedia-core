package org.mescedia.analyser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class MessageFormatAnalyser extends ContentAnalyserBase {

    private List<MessageFormatAnalyserRule> ruleList = new ArrayList<MessageFormatAnalyserRule>(); ;
    private static final Logger log = LoggerFactory.getLogger(MessageFormatAnalyser.class);

    public MessageFormatAnalyser() {

    }

    public void addRule(MessageFormatAnalyserRule _rule) {

        this.ruleList.add(_rule);
    }

    public List<MessageFormatAnalyserRule> getRuleList() {

        return this.ruleList;
    }

    public void setRuleList( List<MessageFormatAnalyserRule> _list) {

        this.ruleList = _list;
    }

    public boolean analyse()   {

        for (MessageFormatAnalyserRule r : this.ruleList )  {

            if (r.match(this.messageExtract)) {
                log.debug("rule match : "  );
                log.debug("  -> "  + r.getType() );
                log.debug("  -> "  + r.getValue() );
                return true;
            }
        }
        return false;
    }
}
