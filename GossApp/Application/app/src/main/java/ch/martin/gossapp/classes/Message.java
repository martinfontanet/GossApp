package ch.martin.gossapp.classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class Message implements Comparable{
    private final int authorID;
    private final int conversationID;
    private String text;
    private final Date dateAndTime;

    public Message(@JsonProperty("authorID") int authorID,
                   @JsonProperty("conversationID") int conversationID,
                   @JsonProperty("text") String text,
                   @JsonProperty("date") int date){
        this.authorID = authorID;
        this.conversationID = conversationID;
        this.text = text;
        this.dateAndTime = new Date(date);
    }

    public void modifyText(String text){
        this.text = text;
    }

    public int getConversationID() {
        return conversationID;
    }

    public String getText() {
        return text;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public int getAuthorID() {

        return authorID;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Message) obj).getAuthorID() == authorID && ((Message) obj).getConversationID() == conversationID &&
                ((Message) obj).getText() == text && ((Message) obj).getDateAndTime() == dateAndTime;
    }

    @Override
    public int compareTo(Object o) {
        Message e = (Message) o;
        return getDateAndTime().compareTo(e.getDateAndTime());
    }

    @Override
    public String toString(){
        return text;
    }
}
