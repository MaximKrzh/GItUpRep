package by.bsu.up.chat.common.models;

import java.io.Serializable;

public class Message implements Serializable {

    private String id;
    private String author;
    private String timestamp;
    private String text;
    private Boolean edited;
    private Boolean deleted;

    public Message () {};
    public  Message (String id,String author,
                     String timestamp , String text,
                     Boolean edited , Boolean deleted) {
        this.id = id;
        this.author = author ;
        this.timestamp = timestamp;
        this.text = text ;
        this.edited = edited;
        this.deleted = deleted;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", timestamp='" + timestamp + '\''+
                ", text='" + text + '\'' +
                ", edited='" + edited + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';
    }

    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
