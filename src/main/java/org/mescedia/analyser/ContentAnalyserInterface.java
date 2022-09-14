package org.mescedia.analyser;

public interface ContentAnalyserInterface {

    public  String getSender() throws Exception;
    public  String getReceiver() throws Exception;
    public  String getMessageType() throws Exception;
    public  String getVersion() throws Exception;
}
