package org.mescedia.processors;

import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
//import org.apache.camel.console.DevConsole;
//import org.apache.daffodil.japi.ValidationMode;
import org.mescedia.analyser.MessageAnalyser;
import org.mescedia.analyser.MessageMetaInfo;
//import org.mescedia.helper.XmlFormatter;
import org.mescedia.readerCache.Edifact2XmlReaderCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;
import org.smooks.api.SmooksException;
import org.smooks.cartridges.edifact.EdifactReaderConfigurator;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

public class Edifact2Xml  implements Processor {

    private static final Logger log = LoggerFactory.getLogger(Edifact2Xml.class);
    private static Smooks smooks = null;
    private ExecutionContext execContext = null;
    private Long startTs ;
    private String stateMsg = "" ;

    @Override
    public synchronized void process(Exchange exchange) throws Exception {

        String uuid = UUID.randomUUID().toString() ;
        exchange.getIn().setHeader("Transaction-Id", uuid);

        startTs = System.currentTimeMillis();
        stateMsg = "Success";

        String xmlEdifact = null;
        String messageIn = exchange.getIn().getBody(String.class) ;

        final StringWriter result = new StringWriter();

        // check for meta-info
        String metaInfo = exchange.getIn().getHeaders().get("X-MESCEDIA-MESSAGE-META-INFO").toString() ;
        if ( metaInfo == null || metaInfo.equals("") )    {
            throw new Exception("Message-Header X-MESCEDIA-MESSAGE-META-INFO not set!");
        }

        Gson gson = new Gson();
        MessageMetaInfo mmi = gson.fromJson(metaInfo, MessageMetaInfo.class);

        if(!mmi.messageFormat.equals("Edifact"))    {
            throw new Exception("Invalid message-format. Expected UN/Edifact !");
        }

        try {
            smooks = Edifact2XmlReaderCache.getInstance().getSmooks(mmi.messageVersion, mmi.messageType);
            execContext = smooks.createExecutionContext();
            execContext.setContentEncoding("UTF-8");
            smooks.filterSource(
                    execContext,
                    new StreamSource(new ByteArrayInputStream( messageIn.getBytes(StandardCharsets.UTF_8) )),
                    new StreamResult(result)
            );
            xmlEdifact = result.toString();
            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/xml");
            exchange.getIn().setBody(xmlEdifact);
        }
        catch (SmooksException se)  {
            log.error("SmooksException *********************************************************"); ;
            log.error(se.getMessage()) ;
            log.error(se.getCause().toString()) ;
            log.error("*************************************************************************") ;
            stateMsg = "Error - SmooksException";
            throw se;
        }
        catch (Exception ex)  {
            log.error("Exception ***************************************************************");
            log.error(ex.getMessage()) ;
            log.error("*************************************************************************");
            stateMsg = "Error - Exception";
            throw ex;
        }
        finally {
            String duration = String.valueOf((System.currentTimeMillis() - startTs));
            log.info("Message state: " +stateMsg+ "; transaction: "+uuid+"; processing duration: " + duration + " ms" )  ;
            smooks.close();
        }
    }
}
