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

    // private String messageFormat, messageType, messageVersion, senderId, receiverId ;
    private MessageMetaInfo metaInfo = null ;

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

        return this.metaInfo.getSenderId();
    }

    public String getReceiver()   {

        return this.metaInfo.getReceiverId();
    }

    public String getMessageType()   {

        return this.metaInfo.getMessageType();
    }

    public String getMessageVersion()   {

        return this.metaInfo.getMessageVersion();
    }

    public String getMessageFormat()   {

        return this.metaInfo.getMessageFormat();
    }

    public MessageMetaInfo getMetaInfo()    {

        return this.metaInfo;
    }

    public void analyse() throws Exception {

        this.metaInfo = new MessageMetaInfo();

        this.metaInfo.setMessageFormat("Unknown");

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

                this.metaInfo.setMessageFormat(configFormat.getName());

                log.debug("--------------------------------------------------------");
                log.debug(mfAnalyser.getMessageExtract() );
                log.info("Analysed message format: " + this.metaInfo.getMessageFormat());

                mcAnalyser = new MessageContentAnalyser();
                mcAnalyser.setRuleList(configFormat.getContentAnalyser().getRuleList());
                mcAnalyser.setMessageExtract(messageExtract); // extract

                if (mcAnalyser.analyse()) {

                    this.metaInfo.setSenderId( mcAnalyser.getSender() ) ;
                    this.metaInfo.setReceiverId(mcAnalyser.getReceiver() );
                    this.metaInfo.setMessageType(mcAnalyser.getMessageType());
                    this.metaInfo.setMessageVersion(mcAnalyser.getVersion());

                    log.debug("found GenericMessageContentAnalyser - sender: " + this.metaInfo.getSenderId());
                    log.debug("found GenericMessageContentAnalyser - receiver: " + this.metaInfo.getReceiverId());
                    log.debug("found GenericMessageContentAnalyser - messageType: " + this.metaInfo.getMessageType());
                    log.debug("found GenericMessageContentAnalyser - messageVersion: " + this.metaInfo.getMessageVersion());
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
