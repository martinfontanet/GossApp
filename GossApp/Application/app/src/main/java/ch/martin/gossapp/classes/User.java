package ch.martin.gossapp.classes;

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

    public User(String name, int id){
        this.name = name;
        this.id = id;
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
