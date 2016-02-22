import org.json.simple.JSONObject;

import java.sql.Timestamp;

public class Message {

    private Integer id;
    private String messageText;
    private String author;
    private Timestamp timeStamp;


    public void setId(Integer id) {
        this.id = id;
    }

    void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    void setAuthor(String author) {
        this.author = author;
    }

    void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    Integer getId() {
        return this.id;
    }

    String getMessageText() {
        return this.messageText;
    }

    String getAuthor() {
        return this.author;
    }

    Timestamp getTimeStamp() {
        return this.timeStamp;
    }

    public Message() {
    }

    public Message(String author, String text) {

        this.author = author;
        this.messageText = text;

    }

    public Message(Integer id, String author, String messageText, Timestamp timeStamp) {
        this.id = id;
        this.author = author;
        this.messageText = messageText;
        this.timeStamp = timeStamp;
    }

    /*  public static JSONObject messageToJson(Message m) throws ParseException {

          JSONParser parser = new JSONParser();
          return (JSONObject) parser.parse(m.toString());

      }
  */
    public static Message jsonToMessage(JSONObject jobj) {

        Message m = new Message();
        m.id = Integer.valueOf((String) jobj.get("id"));
        m.author = (String) jobj.get("author");
        m.messageText = (String) jobj.get("message");
        m.timeStamp = Timestamp.valueOf((String) jobj.get("date"));
        return m;

    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("{\"id\":\"").append(id).append("\", \"author\":\"").append(author)
                .append("\", \"message\":\"").append(messageText).append("\", \"date\":\"").append(timeStamp.toString()).append("\"}");
        return sb.toString();
    }

}
