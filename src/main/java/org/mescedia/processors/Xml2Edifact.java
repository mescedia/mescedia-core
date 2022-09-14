package org.mescedia.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.daffodil.japi.DataProcessor;
import org.mescedia.analyser.MessageAnalyser;
import org.mescedia.readerCache.Xml2EdifactReaderCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smooks.FilterSettings;
import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;
import org.smooks.api.SmooksException;
import org.smooks.api.resource.config.ResourceConfig;
import org.smooks.cartridges.dfdl.unparser.DfdlUnparser;
import org.smooks.cartridges.edifact.EdifactDataProcessorFactory;
import org.smooks.engine.DefaultApplicationContextBuilder;
import org.smooks.engine.injector.Scope;
import org.smooks.engine.lifecycle.PostConstructLifecyclePhase;
import org.smooks.engine.lookup.LifecycleManagerLookup;
import org.smooks.engine.resource.config.DefaultResourceConfig;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

public class Xml2Edifact implements Processor {
    private static Smooks smooks = null;
    private ExecutionContext execContext = null;
    private static final Logger log = LoggerFactory.getLogger(Xml2Edifact.class);
    private Long startTs ;
    private String stateMsg = "" ;

    @Override
    public synchronized void process(Exchange exchange) throws Exception {

        String uuid = UUID.randomUUID().toString() ;
        exchange.getIn().setHeader("Transaction-Id", uuid);

        String mVersion = null, mType = null, mFormat = null, sender=null, receiver=null;

        startTs = System.currentTimeMillis();
        stateMsg = "Success";

        boolean debug = (exchange.getIn().getHeaders().get("X-MESCEDIA-DEBUG")!=null)
                ? (exchange.getIn().getHeaders().get("X-MESCEDIA-DEBUG").toString().equals("1"))
                : false;

        String xmlEdifact = exchange.getIn().getBody(String.class) ;

        MessageAnalyser gmAnalyser = MessageAnalyser.getInstance();
        gmAnalyser.setMessage(xmlEdifact);
        gmAnalyser.analyse();

        mFormat = gmAnalyser.getMessageFormat();

        if (!mFormat.equals("DfdlXmlEdifact"))
            throw new Exception("Invalid message format: " + mFormat + "; expected: DfdlXmlEdifact");

        try {

            mVersion = gmAnalyser.getMessageVersion().toLowerCase();
            mType = gmAnalyser.getMessageType().toUpperCase();
            sender = gmAnalyser.getSender();
            receiver = gmAnalyser.getReceiver();

            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-IN-REPORTING-INSTANCE", "Xml2Edifact");
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-IN-VERSION", mVersion.toUpperCase());
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-IN-TYPE", mType);
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-IN-FORMAT", mFormat);
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-IN-SENDER", sender);
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-IN-RECEIVER", receiver);

            log.info("Initialising reader " + mVersion + "/" + mType + " (this might take a while) ... ");

            if (!debug) {
                smooks = Xml2EdifactReaderCache.getInstance().getSmooks(mVersion, mType);
            } else {
                // debugging information goes to stdout
                smooks = new Smooks();
                EdifactDataProcessorFactory edifactDataProcessorFactory = new EdifactDataProcessorFactory();
                edifactDataProcessorFactory.setResourceConfig(new DefaultResourceConfig());
                edifactDataProcessorFactory.setApplicationContext(new DefaultApplicationContextBuilder().build());
                edifactDataProcessorFactory.getResourceConfig().setParameter("cacheOnDisk", "false"); // ?
                edifactDataProcessorFactory.getResourceConfig().setParameter("debugging", "true"); // ?

                edifactDataProcessorFactory.getResourceConfig().setParameter(
                        "schemaURI", "/" + mVersion + "/EDIFACT-Messages.dfdl.xsd");

                // if not stated all messages from specific version are cached !!!
                edifactDataProcessorFactory.getResourceConfig().setParameter("messageType", mType);

                DataProcessor dataProcessor = edifactDataProcessorFactory.doCreateDataProcessor(new HashMap<>());
                DfdlUnparser dfdlUnparser = new org.smooks.cartridges.dfdl.unparser.DfdlUnparser(dataProcessor);
                //
                ResourceConfig resourceConfig = new DefaultResourceConfig();
                resourceConfig.setParameter("schemaURI", "");

                smooks.getApplicationContext().getRegistry().lookup(new LifecycleManagerLookup()).applyPhase(dfdlUnparser,
                        new PostConstructLifecyclePhase(new Scope(smooks.getApplicationContext().getRegistry(),
                                resourceConfig, dfdlUnparser)));

                smooks.addVisitor(dfdlUnparser, "*");
                smooks.setFilterSettings(FilterSettings.newSaxNgSettings().setDefaultSerializationOn(false));
            }

            execContext = smooks.createExecutionContext();
            execContext.setContentEncoding("UTF-8");

            StringWriter result = new StringWriter();
            smooks.filterSource(
                    execContext,
                    new StreamSource(new ByteArrayInputStream(xmlEdifact.getBytes(StandardCharsets.UTF_8))),
                    new StreamResult(result));

            exchange.getIn().setBody(result.toString().replace("\n", "").replace("\r", ""));
        }
        catch (SmooksException se)  {

            log.error("SmooksException *********************************************************"); ;
            log.error(se.getMessage()) ;
            log.error(se.getCause().toString()) ;
            log.error("*************************************************************************") ;
            stateMsg = "Error - SmooksException";
            throw se;
        }
        catch (Exception ex) {

            log.error("Exception ***************************************************************"); ;
            log.error(ex.getMessage()) ;
            log.error("*************************************************************************") ;
            stateMsg = "Error - Exception";
            throw ex;
        }
        finally {

            String duration = String.valueOf((System.currentTimeMillis() - startTs)) ;
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-PROCESSING-DURATION", duration);

            log.info("Message state: " +stateMsg+ "; transaction: "+uuid+"; processing duration: " + duration + " ms")  ;
        }
    }
}
