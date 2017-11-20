package gossapp.classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class User {
    private final int id;
    private String name;
    private final ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @JsonCreator
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

    public int getID(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
}
