package ch.martin.gossapp.classes;

public class Message {
    private final int authorID;
    private final int conversationID;
    private String text;
    private int dateAndTime;

    public Message(int authorID, int conversationID, String text){
        this.authorID = authorID;
        this.conversationID = conversationID;
        this.text = text;
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

    public int getDateAndTime() {
        return dateAndTime;
    }

    public int getAuthorID() {

        return authorID;
    }

    public String toString(){
        return text;
    }
}
