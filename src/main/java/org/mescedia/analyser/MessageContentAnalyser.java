package org.mescedia.analyser;

import java.util.ArrayList;
import java.util.List;

public class MessageContentAnalyser extends ContentAnalyserBase implements  ContentAnalyserInterface {

    private String sender, receiver, messageType, messageVersion  = null;
    private List<MessageContentAnalyserRule> ruleList = new ArrayList<MessageContentAnalyserRule>(); ;

    public MessageContentAnalyser() {}

    public void addRule(MessageContentAnalyserRule _rule)   {

        this.ruleList.add(_rule) ;
    }

    @Override
    public String getSender() throws Exception {

        return this.sender;
    }

    @Override
    public String getReceiver() throws Exception {

        return this.receiver;
    }

    @Override
    public String getMessageType() throws Exception {

        return this.messageType;
    }

    @Override
    public String getVersion() throws Exception {

        return this.messageVersion;
    }

    public List<MessageContentAnalyserRule> getRuleList()   {

        return this.ruleList;
    }

    public void setRuleList(List<MessageContentAnalyserRule> _list) {

        this.ruleList = _list;
    }

    public boolean analyse()   {

        for (MessageContentAnalyserRule r : this.ruleList )  {
            switch (r.getType().toLowerCase()) {
                case "regex":
                    switch (r.getItem().toLowerCase()) {
                        case "senderid":
                            this.sender = r.getMatch(this.messageExtract);
                            break;
                        case "receiverid":
                            this.receiver = r.getMatch(this.messageExtract);
                            break;
                        case "messageversion":
                            this.messageVersion = r.getMatch(this.messageExtract);
                            break;
                        case "messagetype":
                            this.messageType = r.getMatch(this.messageExtract);
                            break;
                    }
                    break;
            }
        }
        return (this.sender != null && this.receiver != null && this.messageType != null && this.messageVersion != null ) ;
    }
}
