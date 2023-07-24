package org.mescedia.processors;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mescedia.analyser.MessageMetaInfo;
import org.mescedia.helper.DbDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

//import org.json.*;

public class MessageProcessorRuleLoader implements Processor {

    private static final Logger log = LoggerFactory.getLogger(MessageProcessorRuleLoader.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        String metaInfo = exchange.getIn().getHeaders().get("X-MESCEDIA-MESSAGE-META-INFO").toString() ;
        if ( metaInfo == null || metaInfo.equals("") )    {
            log.error("Message-Header X-MESCEDIA-MESSAGE-META-INFO not set !");
            return;
        }


        String interfaceIn = null;
        if (exchange.getIn().getHeaders().get("X-MESCEDIA-INTERFACE-IN") != null) {
            interfaceIn = exchange.getIn().getHeaders().get("X-MESCEDIA-INTERFACE-IN").toString() ;
        } else {
            interfaceIn = exchange.getIn().getBody(File.class).getParent() + "/";
        }


        Gson gson = new Gson();
        MessageMetaInfo mmi = gson.fromJson(metaInfo, MessageMetaInfo.class);

        log.debug("messageFormat:    " +  mmi.messageFormat);
        log.debug("messageType:      " +  mmi.messageType);
        log.debug("messageVersion:   " +  mmi.messageVersion);
        log.debug("senderId:         " +  mmi.senderId);
        log.debug("receiverId:       " +  mmi.receiverId);
        log.debug("interfaceIn:      " +  interfaceIn);

        String[] destSetValues = DbDataProvider.getInstance().getDestinationProcessSetId(mmi, interfaceIn);

        String destination = destSetValues[0].trim() ;
        String processSetId = destSetValues[1].trim() ;
        String contentType = destSetValues[2].trim();
        String routingSlip = DbDataProvider.getInstance().getRoutingSlip(processSetId);

        log.debug("RoutingSlip: " + routingSlip + "; Destination: " + destination + "; processStepId: " + processSetId);

        exchange.getIn().setHeader("X-MESCEDIA-RoutingSlip", routingSlip);
        exchange.getIn().setHeader("X-MESCEDIA-RoutingSlipDestination", destination);
        exchange.getIn().setHeader("X-MESCEDIA-DestinationContentType", contentType);
    }
}
