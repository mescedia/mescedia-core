package org.mescedia.readerCache;

import org.apache.daffodil.japi.DataProcessor;
import org.smooks.FilterSettings;
import org.smooks.Smooks;
import org.smooks.api.resource.config.ResourceConfig;
import org.smooks.cartridges.dfdl.unparser.DfdlUnparser;
import org.smooks.cartridges.edifact.EdifactDataProcessorFactory;
import org.smooks.engine.DefaultApplicationContextBuilder;
import org.smooks.engine.injector.Scope;
import org.smooks.engine.lifecycle.PostConstructLifecyclePhase;
import org.smooks.engine.lookup.LifecycleManagerLookup;
import org.smooks.engine.resource.config.DefaultResourceConfig;
import java.util.HashMap;

public class Xml2EdifactReaderItem {

    private String messageVersion = "";
    private String messageType = "";

    private Smooks smooks = null;
    private long lastUsed = 0;

    public Xml2EdifactReaderItem(String _version, String _messageType)  {

        this.messageVersion = _version.toLowerCase();
        this.messageType = _messageType.toUpperCase();

        this.smooks = new Smooks();
        EdifactDataProcessorFactory edifactDataProcessorFactory = new EdifactDataProcessorFactory();
        edifactDataProcessorFactory.setResourceConfig(new DefaultResourceConfig());
        edifactDataProcessorFactory.setApplicationContext(new DefaultApplicationContextBuilder().build());
        edifactDataProcessorFactory.getResourceConfig().setParameter("cacheOnDisk", "false"); // ?

        edifactDataProcessorFactory.getResourceConfig().setParameter(
                "schemaURI", "/" + this.messageVersion + "/EDIFACT-Messages.dfdl.xsd");

        edifactDataProcessorFactory.getResourceConfig().setParameter("messageType", this.messageType);

        DataProcessor dataProcessor = edifactDataProcessorFactory.doCreateDataProcessor(new HashMap<>());
        DfdlUnparser dfdlUnparser = new org.smooks.cartridges.dfdl.unparser.DfdlUnparser(dataProcessor);
        //
        ResourceConfig resourceConfig = new DefaultResourceConfig();
        resourceConfig.setParameter("schemaURI", "");

        smooks.getApplicationContext().getRegistry().lookup(new LifecycleManagerLookup()).applyPhase(dfdlUnparser, new PostConstructLifecyclePhase(new Scope(smooks.getApplicationContext().getRegistry(), resourceConfig, dfdlUnparser)));
        smooks.addVisitor(dfdlUnparser, "*");
        smooks.setFilterSettings(FilterSettings.newSaxNgSettings().setDefaultSerializationOn(false));
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
