package gossapp.classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;

public class Information {
    private final ArrayList<Conversation> conversations ;
    private final ArrayList<Contact> contacts;
    private final HashMap<Integer, Conversation> conversationsByID = new HashMap<>();

    public Information() {
        this.conversations = new ArrayList<>();
        this.contacts = new ArrayList<>();
    }

    public Information(@JsonProperty("conversations") ArrayList<Conversation> conversations,@JsonProperty("contacts") ArrayList<Contact> contacts) {
        this.conversations = conversations;
        this.contacts = contacts;

        for(Conversation conversation: conversations){
            conversationsByID.put(conversation.getId(),conversation);
        }
    }

    public void addConversation(Conversation conversation){
        conversations.add(conversation);
        conversationsByID.put(conversation.getId(),conversation);
    }

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    public ArrayList<Conversation> getConversations() {
        return conversations;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public HashMap<Integer, Conversation> getConversationsByID() {
        return conversationsByID;
    }
}
