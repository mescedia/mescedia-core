package org.mescedia.analyser;

public class MessageRuleBase {

    private String type = null;
    private String value = null;

    public MessageRuleBase() { }

    public String getType() {

        return type;
    }

    public String getValue() {

        return value;
    }

    public void setType(String type) {

        this.type = type;
    }

    public void setValue(String value) {

        this.value = value;
    }
}