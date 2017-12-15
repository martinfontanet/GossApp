package gossapp.classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class User {
    protected final int id;
    protected String name;
    private final ArrayList<Contact> contacts = new ArrayList<>();
    private final ArrayList<Integer> conversationsID = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public User(@JsonProperty("id") int id,@JsonProperty("name") String name){
        this.name = name;
        this.id = id;
    }

    public User(){
        this.name = "undefined";
        this.id = -1;
    }

    public void addContact(Contact contact){
        this.contacts.add(contact);
    }

    public void addContacts(ArrayList<Contact> contactList){
        for(Contact contact: contactList){
            if(!contacts.contains(contact)){
                contacts.add(contact);
            }
        }
    }

    public void addConversationID(int conversationID){
        this.conversationsID.add(conversationID);
    }

    public int getID(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }
}
