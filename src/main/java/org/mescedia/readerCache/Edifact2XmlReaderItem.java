package org.mescedia.readerCache;

import org.apache.daffodil.japi.ValidationMode;
import org.smooks.Smooks;
import org.smooks.cartridges.edifact.EdifactReaderConfigurator;

import java.util.Arrays;

public class Edifact2XmlReaderItem {

    private String messageType, messageVersion = "";
    private Smooks smooks = null;
    private long lastUsed = 0;

    public Edifact2XmlReaderItem(String _version, String _messageType) {
        this.messageVersion = _version.toLowerCase();
        this.messageType = _messageType.toUpperCase();
        this.smooks = new Smooks();

        EdifactReaderConfigurator rc = new EdifactReaderConfigurator(
                "/" + this.messageVersion + "/EDIFACT-Messages.dfdl.xsd")
                .setMessageTypes(Arrays.asList(this.messageType));

        rc.setCacheOnDisk(false); // ?

        rc.setDebugging(false);
        rc.setValidationMode( ValidationMode.Off);

        this.smooks.setReaderConfig(rc) ;
    }

    public String getMessageVersion()   {
        return this.messageVersion;
    }

    public String getMessageType()   {
        return this.messageType;
    }

    public Smooks getSmooks()   {

        this.lastUsed = System.currentTimeMillis();
        return this.smooks;
    }

    public long getLastUsed()   {

        return this.lastUsed;
    }
}
