package org.mescedia.readerCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smooks.Smooks;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Xml2EdifactReaderCache {

    private static final Logger log = LoggerFactory.getLogger(Xml2EdifactReaderCache.class);
    private static Xml2EdifactReaderCache instance = null;
    private static int maxEntries = 10 ; //todo: make this configurable
    private List<Xml2EdifactReaderItem> readerConfigurationList = new ArrayList<Xml2EdifactReaderItem>(); ;

    public static synchronized Xml2EdifactReaderCache getInstance()	{

        if(instance == null)
            instance = new Xml2EdifactReaderCache();

        return instance ;
    }

    public Smooks getSmooks(String _version, String _message) throws IOException, SAXException {

        _version= _version.toLowerCase();
        _message = _message.toUpperCase();

        for (Xml2EdifactReaderItem item : this.readerConfigurationList)	{
            if (item.getMessageType().equals(_message)
                    && item.getMessageVersion().equals(_version )) 	{
                log.info("Retrieve xml2edifact-reader from cache: " + _version + ":" + _message );
                return item.getSmooks() ;
            }
        }
        return (this.addItem( _version, _message)).getSmooks() ;
    }

    private Xml2EdifactReaderItem addItem(String _version, String _message) throws IOException, SAXException {

        if(this.contains(_version,_message))    {
            log.info("Xml2edifact-reader already cached: " + _version +"/" + _message );
        }

        if (this.readerConfigurationList.size() + 1 > maxEntries ) {

            int c = 0, index = 0;
            Long llastUsed = Long.parseUnsignedLong("99999999999999"); ;

            for (Xml2EdifactReaderItem item : this.readerConfigurationList)	{
                if (item.getLastUsed() < llastUsed ) {
                    llastUsed = item.getLastUsed();
                    index = c;
                }
                c++;
            }

            String rmv = this.readerConfigurationList.get(index).getMessageVersion() ;
            String rmt = this.readerConfigurationList.get(index).getMessageType() ;
            this.readerConfigurationList.remove(index); // remove oldest item ...

            log.info("Removed xml2edifact-reader (lastUsed: " + llastUsed.toString() + ") from cache: " + rmv +"/" + rmt + " - maxEntries: " + maxEntries);
        }

        log.info("Create new xml2edifact-reader: " + _version + "/" +_message );

        Xml2EdifactReaderItem rc = new Xml2EdifactReaderItem( _version,_message);
        this.readerConfigurationList.add(rc) ;
        return rc;
    }

    private boolean contains(String _version, String _message) throws IOException, SAXException {
        for (Xml2EdifactReaderItem item : this.readerConfigurationList)	{
            if (item.getMessageType().equals(_message)
                    && item.getMessageVersion().equals(_version )) 	{
                return true;
            }
        }
        return false;
    }

    public String list()  {

        String xml = "<processor type=\"xml2edifact\">" ;
        for (Xml2EdifactReaderItem item : this.readerConfigurationList)	{
            xml += "<reader messageVersion=\"" +item.getMessageVersion() + "\" messageType=\""+item.getMessageType()+"\"/>";
            log.debug("    -> reader cached: " + item.getMessageVersion() + ":" + item.getMessageType() );
        }
        xml += "</processor>" ;
        return xml;
    }

    public void clear()  {

        this.readerConfigurationList.clear();
    }
}
