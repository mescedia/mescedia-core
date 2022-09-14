package org.mescedia.analyser;

import org.mescedia.helper.ApplicationConfig;
import org.mescedia.helper.ApplicationConfigReader;
import org.mescedia.helper.DbDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageAnalyser {

    private static MessageAnalyser instance = null;
    private String message = null;
    private MessageFormatAnalyser mfAnalyser = null;
    private MessageContentAnalyser mcAnalyser = null;

    private static final Logger log = LoggerFactory.getLogger(MessageAnalyser.class);

    private String messageFormat, messageType, messageVersion, senderId, receiverId ;

    private MessageAnalyser()   {}

    public static MessageAnalyser getInstance() throws Exception {

        if(instance == null) {
            instance = new MessageAnalyser();

            log.info("Reading application configuration ...");

            DbDataProvider.getInstance().readMessageFormats();

            // lets populate ApplicationConfig
            ApplicationConfigReader appConfigReader = new ApplicationConfigReader();
            appConfigReader.read();
        }

        return instance ;
    }

    public void setMessage(String _msg) {

        this.message = _msg;
    }

    public String getSender()   {

        return this.senderId;
    }

    public String getReceiver()   {

        return this.receiverId;
    }

    public String getMessageType()   {

        return this.messageType;
    }

    public String getMessageVersion()   {

        return this.messageVersion;
    }

    public String getMessageFormat()   {

        return this.messageFormat;
    }

    public void analyse() throws Exception {

        this.messageFormat = "Unknown";

        ApplicationConfig appConfig =  ApplicationConfig.getInstance();

        for (MessageFormat configFormat : appConfig.getMessageFormatList())	{

            log.debug("Checking if message format is " + configFormat.getName() + " ...");

            mfAnalyser = new MessageFormatAnalyser();
            mfAnalyser.setRuleList(configFormat.getFormatAnalyser().getRuleList());

            String messageExtract = this.getMessageExtract(
                    configFormat.getExtractStartIndex(),
                    configFormat.getExtractEndIndex(),
                    configFormat.getExtractEndIndexString()) ;

            mfAnalyser.setMessageExtract(messageExtract);

            if (mfAnalyser.analyse())   {

                this.messageFormat = configFormat.getName();

                log.debug("--------------------------------------------------------");
                log.debug(mfAnalyser.getMessageExtract() );
                log.info("Analysed message format: " + this.messageFormat);

                mcAnalyser = new MessageContentAnalyser();
                mcAnalyser.setRuleList(configFormat.getContentAnalyser().getRuleList());
                mcAnalyser.setMessageExtract(messageExtract); // extract

                if (mcAnalyser.analyse()) {

                    this.senderId = mcAnalyser.getSender() ;
                    this.receiverId = mcAnalyser.getReceiver();
                    this.messageType = mcAnalyser.getMessageType();
                    this.messageVersion = mcAnalyser.getVersion();

                    log.debug("found GenericMessageContentAnalyser - sender: " + this.senderId);
                    log.debug("found GenericMessageContentAnalyser - receiver: " + this.receiverId);
                    log.debug("found GenericMessageContentAnalyser - messageType: " + this.messageType);
                    log.debug("found GenericMessageContentAnalyser - messageVersion: " + this.messageVersion);
                }
                break;
            }
        }
    }

    private String getMessageExtract(int startIndex, int endIndex, String endIndexStr) {

        if (endIndex > 0 && endIndex > this.message.length())
            endIndex = this.message.length();

        if(endIndex > 0 )
            return this.message.substring(startIndex,endIndex);

        if (this.message.indexOf(endIndexStr) > 0)
            return this.message.substring(startIndex,this.message.indexOf(endIndexStr));

        return "";
    }

}
