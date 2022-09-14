package org.mescedia.analyser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageFormat {

    private int id ;
    private String formatName ;
    private int extractStartIndex;
    private int extractEndIndex;
    private String extractEndIndexString;
    private MessageFormatAnalyser mfAnalyser = null;
    private MessageContentAnalyser mcAnalyser = null;

    private static final Logger log = LoggerFactory.getLogger(MessageFormat.class);

    public MessageFormat()   { }

    public void setId(int id) {
        this.id = id;
    }

    public void setExtractStartIndex(int extractStartIndex) {
        this.extractStartIndex = extractStartIndex;
    }

    public void setExtractEndIndex(int extractEndIndex) {
        this.extractEndIndex = extractEndIndex;
    }

    public void setExtractEndIndexString(String extractStartIndexString) {

        this.extractEndIndexString = extractStartIndexString;
    }

    public int getId() {

        return id;
    }

    public int getExtractStartIndex() {

        return extractStartIndex;
    }

    public int getExtractEndIndex() {

        return extractEndIndex;
    }

    public String getExtractEndIndexString() {

        return extractEndIndexString;
    }

    public void setName(String _name) {

        this.formatName= _name ;
    }

    public String getName() {

        return this.formatName ;
    }

    public void setContentAnalyser(MessageContentAnalyser _analyser)   {

        this.mcAnalyser = _analyser ;
    }

    public MessageContentAnalyser getContentAnalyser()   {

        return this.mcAnalyser ;
    }

    public void setFormatAnalyser(MessageFormatAnalyser _analyser)   {

        this.mfAnalyser = _analyser ;
    }

    public MessageFormatAnalyser getFormatAnalyser()   {

        return this.mfAnalyser ;
    }
}
