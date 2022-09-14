package org.mescedia.analyser;

public abstract class ContentAnalyserBase {

    protected String messageExtract = null;

    public ContentAnalyserBase() {}

    public String getMessageExtract() {

        return this.messageExtract + "...";
    }

    public void setMessageExtract(String msg) {

        this.messageExtract = msg;
    }
}
