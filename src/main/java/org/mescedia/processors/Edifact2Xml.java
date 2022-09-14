package org.mescedia.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.daffodil.japi.ValidationMode;
import org.mescedia.analyser.MessageAnalyser;
import org.mescedia.helper.XmlFormatter;
import org.mescedia.readerCache.Edifact2XmlReaderCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;
import org.smooks.api.SmooksException;
import org.smooks.cartridges.edifact.EdifactReaderConfigurator;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
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

        boolean debug = (exchange.getIn().getHeaders().get("X-MESCEDIA-DEBUG")!=null)
                ? (exchange.getIn().getHeaders().get("X-MESCEDIA-DEBUG").toString().equals("1"))
                : false;
        boolean format = (exchange.getIn().getHeaders().get("X-MESCEDIA-FORMAT")!=null)
                ? (exchange.getIn().getHeaders().get("X-MESCEDIA-FORMAT").toString().equals("1"))
                : false;

        String xmlEdifact = null;
        String mVersion = null, mType = null, mFormat = null, sender=null, receiver=null;
        String messageIn = exchange.getIn().getBody(String.class) ;

        final StringWriter result = new StringWriter();

        MessageAnalyser gmAnalyser = MessageAnalyser.getInstance();
        gmAnalyser.setMessage(messageIn);
        gmAnalyser.analyse();

        mFormat = gmAnalyser.getMessageFormat();

        if (!mFormat.equals("Edifact"))
            throw new Exception("Invalid message format: " + mFormat + "; expected: Edifact");

        try {

            mVersion = gmAnalyser.getMessageVersion().toLowerCase();
            mType = gmAnalyser.getMessageType().toUpperCase();
            sender = gmAnalyser.getSender();
            receiver = gmAnalyser.getReceiver();

            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-IN-REPORTING-INSTANCE", "Edifact2Xml");
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-IN-VERSION", mVersion.toUpperCase());
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-IN-TYPE", mType);
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-IN-FORMAT", mFormat);
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-IN-SENDER", sender);
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-IN-RECEIVER", receiver);

            log.info("Initialising reader " + mVersion.toUpperCase() + "/" + mType +" (this might take a while) ... ");

            // ========================================
            // debugging information goes to stdout !!!
            // ========================================
            if (!debug) {
                smooks = Edifact2XmlReaderCache.getInstance().getSmooks(mVersion, mType);

                execContext = smooks.createExecutionContext();
                execContext.setContentEncoding("UTF-8");

                smooks.filterSource(
                        execContext,
                        new StreamSource(new ByteArrayInputStream( messageIn.getBytes(StandardCharsets.UTF_8) )),
                        new StreamResult(result)
                );

                xmlEdifact = result.toString();

                if (format) {
                    xmlEdifact = XmlFormatter.getInstance().format(xmlEdifact, "UTF-8");
                }

                if (xmlEdifact.indexOf(mVersion.toUpperCase() + ":BadMessage") > -1) {

                    throw new Exception("Error in Message-Structure or Segment-Layout ...") ;
                }

                exchange.getIn().setBody(xmlEdifact);
            } else {
                smooks = new Smooks();

                EdifactReaderConfigurator readerConfigurator = new EdifactReaderConfigurator(
                        "/" + mVersion + "/EDIFACT-Messages.dfdl.xsd")
                        .setMessageTypes(Arrays.asList(mType) );

                readerConfigurator.setCacheOnDisk(false);
                readerConfigurator.setDebugging(true);
                readerConfigurator.setValidationMode( ValidationMode.Off);
                smooks.setReaderConfig(readerConfigurator) ;

                execContext = smooks.createExecutionContext();
                execContext.setContentEncoding("UTF-8");

                smooks.filterSource(
                        execContext,
                        new StreamSource(new ByteArrayInputStream( messageIn.getBytes(StandardCharsets.UTF_8) )),
                        new StreamResult(result)
                );

                xmlEdifact = result.toString();

                if (format) {
                    xmlEdifact = XmlFormatter.getInstance().format(xmlEdifact, "UTF-8");
                }

                if (xmlEdifact.indexOf(mVersion.toUpperCase() + ":BadMessage") > -1) {
                    throw new Exception("Error in message-structure or segment-layout ...") ;
                }
                exchange.getIn().setBody(xmlEdifact);
            }
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
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-PROCESSING-DURATION", duration);

            smooks.close();
            log.info("Message state: " +stateMsg+ "; transaction: "+uuid+"; processing duration: " + duration + " ms" )  ;
        }
    }
}
