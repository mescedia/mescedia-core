package org.mescedia.analyser;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageMetaInfo {

    @JsonProperty("messageFormat")
    public String messageFormat;
    @JsonProperty("messageType")
    public String messageType;
    @JsonProperty("messageVersion")
    public String messageVersion;
    @JsonProperty("senderId")
    public String senderId;
    @JsonProperty("receiverId")
    public String receiverId ;

    public MessageMetaInfo() {
    }

    public String getMessageFormat() {
        return this.messageFormat;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public String getMessageVersion() {
        return this.messageVersion;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public String getReceiverId() {
        return this.receiverId;
    }

    public void setMessageFormat(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setMessageVersion(String messageVersion) {
        this.messageVersion = messageVersion;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

}
