package br.com.marcelbraghini.entities;

import io.quarkus.mongodb.panache.MongoEntity;
import org.bson.types.ObjectId;

@MongoEntity(collection="messageDisplay")
public class MessageDisplay {

    private ObjectId id;

    private String headerText;

    private String bodyText;

    private String key;

    private boolean delivered;

    public MessageDisplay(final String headerText, final String bodyText, final String key) {
        this.headerText = headerText;
        this.bodyText = bodyText;
        this.key = key;
        this.delivered = false;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public void delivered() {
        this.delivered = true;
    }

    @Override
    public String toString() {
        return "MessageDisplay{" +
                "headerText='" + headerText + '\'' +
                ", bodyText='" + bodyText + '\'' +
                ", key='" + key + '\'' +
                ", delivered=" + delivered +
                '}';
    }
}
