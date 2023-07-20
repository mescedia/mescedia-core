package org.mescedia.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.google.gson.Gson;

public class MessageAnalyser implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String messageIn = exchange.getIn().getBody(String.class) ;
        org.mescedia.analyser.MessageAnalyser gmAnalyser = org.mescedia.analyser.MessageAnalyser.getInstance();
        gmAnalyser.setMessage(messageIn);
        gmAnalyser.analyse();
        exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-META-INFO", new Gson().toJson(gmAnalyser.getMetaInfo()) );
    }
}
