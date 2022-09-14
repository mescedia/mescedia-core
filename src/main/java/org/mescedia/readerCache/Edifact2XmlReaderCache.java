package org.mescedia.readerCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smooks.Smooks;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Edifact2XmlReaderCache {

    private static final Logger log = LoggerFactory.getLogger(Edifact2XmlReaderCache.class);
    private static Edifact2XmlReaderCache instance = null;
    private static int maxEntries = 10 ;
    private List<Edifact2XmlReaderItem> readerConfigurationList = new ArrayList<Edifact2XmlReaderItem>(); ;

    public static synchronized Edifact2XmlReaderCache getInstance()	{

        if(instance == null)
            instance = new Edifact2XmlReaderCache();

        return instance ;
    }

    public Smooks getSmooks(String _version, String _message) throws IOException, SAXException {

        _version= _version.toLowerCase();
        _message = _message.toUpperCase();

        for (Edifact2XmlReaderItem item : this.readerConfigurationList)	{
            if (item.getMessageType().equals(_message)
                    && item.getMessageVersion().equals(_version )) 	{
                log.info("Retrieve edifact-reader from cache: " + _version + ":" + _message );
                return item.getSmooks() ;
            }
        }
        return (this.addItem( _version, _message)).getSmooks() ;
    }

    private Edifact2XmlReaderItem addItem(String _version, String _message) throws IOException, SAXException {

        if(this.contains(_version,_message))    {
            log.info("Edifact-reader already cached: " + _version +"/" + _message );
        }

        log.info("Create new edifact-reader: " + _version + "/" +_message );

        if (this.readerConfigurationList.size() + 1 > maxEntries ) {

            int c = 0, index = 0;
            Long llastUsed = Long.parseUnsignedLong("99999999999999"); ;

            for (Edifact2XmlReaderItem item : this.readerConfigurationList)	{
                if (item.getLastUsed() < llastUsed ) {
                    llastUsed = item.getLastUsed();
                    index = c;
                }
                c++;
            }

            String rmv = this.readerConfigurationList.get(index).getMessageVersion() ;
            String rmt = this.readerConfigurationList.get(index).getMessageType() ;
            this.readerConfigurationList.remove(index); // remove the oldest

            log.info("Removed edifact-reader (lastUsed: " + llastUsed.toString() + ") from cache: " + rmv +"/" + rmt + " - maxEntries: " + maxEntries);
        }

        Edifact2XmlReaderItem rc = new Edifact2XmlReaderItem( _version,_message);
        this.readerConfigurationList.add(rc) ;
        return rc;
    }

    private boolean contains(String _version, String _message) throws IOException, SAXException {
        for (Edifact2XmlReaderItem item : this.readerConfigurationList)	{
            if (item.getMessageType().equals(_message)
                    && item.getMessageVersion().equals(_version )) 	{
                return true;
            }
        }
        return false;
    }

    public String list()  {
        String xml = "<processor type=\"edifact2xml\">" ;
        for (Edifact2XmlReaderItem item : this.readerConfigurationList)	{
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
