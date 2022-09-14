package org.mescedia.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import org.mescedia.helper.CacheMessageReader;
import org.mescedia.helper.XmlFormatter;
import org.mescedia.readerCache.Xml2EdifactReaderCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mescedia.readerCache.Edifact2XmlReaderCache;
import org.smooks.Smooks;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public class CacheManager implements Processor {

    private static final Logger log = LoggerFactory.getLogger(CacheManager.class);
    private Smooks smooks = null;
    private Long startTs ;
    private String stateMsg = "" ;

    @Override
    public synchronized void process(Exchange exchange) throws Exception {

        String uuid = UUID.randomUUID().toString() ;
        exchange.getIn().setHeader("Transaction-Id", uuid);

        exchange.getIn().setHeader(Exchange.FILE_NAME, UUID.randomUUID().toString());

        boolean clear = (exchange.getIn().getHeaders().get("X-MESCEDIA-CLEAR")!=null)
                ? (exchange.getIn().getHeaders().get("X-MESCEDIA-CLEAR").toString().equals("1"))
                : false;

        boolean list = (exchange.getIn().getHeaders().get("X-MESCEDIA-LIST")!=null)
                ? (exchange.getIn().getHeaders().get("X-MESCEDIA-LIST").toString().equals("1"))
                : false;

        boolean add = (exchange.getIn().getHeaders().get("X-MESCEDIA-ADD")!=null)
                ? (exchange.getIn().getHeaders().get("X-MESCEDIA-ADD").toString().equals("1"))
                : false;

        stateMsg = "Success";
        startTs = System.currentTimeMillis();

        try {
            if (clear) {
                exchange = handleClear(exchange) ;
            }
            else if (list) {
                exchange = handleList(exchange);
            }
            else if (add) {
                exchange = handleAdd(exchange);
            }
            else {
                throw new Exception("Unknown request!");
            }
        }
        catch (Exception ex)    {
            log.error("Exception ***************************************************************"); ;
            log.error(ex.getMessage()) ;
            log.error("*************************************************************************") ;

            stateMsg = "Error - Exception";
            throw ex;
        }
        finally {
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-IN-REPORTING-INSTANCE", "CacheManager");
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-VERSION", "1.0");
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-TYPE", "MescediaCacheMessage");
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-FORMAT", "Unknown");

            String duration = String.valueOf((System.currentTimeMillis() - startTs)) ;
            exchange.getIn().setHeader("X-MESCEDIA-MESSAGE-PROCESSING-DURATION", duration);

            log.info("Message state: " +stateMsg+ "; transaction: "+uuid+"; processing duration: " + String.valueOf((System.currentTimeMillis() - startTs)) + " ms" )  ;
        }
    }

    private Exchange handleAdd(Exchange exchange) throws Exception {

        String messageIn = exchange.getIn().getBody(String.class) ;
        CacheMessageReader r = new CacheMessageReader(messageIn);
        r.read();
        initConfigReader(r) ;
        exchange.getIn().setBody("<response>ReaderCache initialized</response>");
        return handleList(exchange);
    }

    private Exchange handleList(Exchange exchange) throws Exception {

        String returnMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<cachedReaderList>" +
                    Xml2EdifactReaderCache.getInstance().list() +
                    Edifact2XmlReaderCache.getInstance().list() +
                "</cachedReaderList>";

        returnMessage = XmlFormatter.getInstance().format(returnMessage,"UTF-8");

        exchange.getIn().setBody(returnMessage);
        return exchange;
    }

    private Exchange handleClear(Exchange exchange) throws Exception {

        String returnMessage = "<response>Cache list cleared</response>" ;

        Xml2EdifactReaderCache.getInstance().clear();
        Edifact2XmlReaderCache.getInstance().clear();
        returnMessage = XmlFormatter.getInstance().format(returnMessage,"UTF-8");

        exchange.getIn().setBody(returnMessage);
        return handleList(exchange);
    }

    private void initConfigReader(CacheMessageReader r) throws Exception {

        for (Map.Entry<String, String> item  : r.getReaderConfig().entrySet()) {

            String version = item.getKey().substring(0,4).toUpperCase();
            String type = item.getKey().substring(4,10).toUpperCase();
            String format = item.getKey().substring(10);
            String message = item.getValue();

            if(format.equals("Edifact"))    {
                smooks = Edifact2XmlReaderCache.getInstance().getSmooks(version.toLowerCase(),type) ;
            }
            else if (format.equals("DfdlXmlEdifact"))   {
                smooks = Xml2EdifactReaderCache.getInstance().getSmooks(version.toLowerCase(),type) ;
            }

            StringWriter result = new StringWriter();
            smooks.filterSource(
                    new StreamSource(new ByteArrayInputStream( message.getBytes(StandardCharsets.UTF_8) )),
                    new StreamResult(result));

            log.debug(result.toString() );
        }
    }
}
