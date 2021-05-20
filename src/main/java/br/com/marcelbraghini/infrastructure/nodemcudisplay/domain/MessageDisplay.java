package br.com.marcelbraghini.infrastructure.nodemcudisplay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDisplay {

    private String headerText;

    private String bodyText;

    private String key;

    public MessageDisplay(String headerText, String bodyText, String key) {
        this.headerText = headerText;
        this.bodyText = bodyText;
        this.key = key;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "MessageDisplay{" +
                "headerText='" + headerText + '\'' +
                ", bodyText='" + bodyText + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
