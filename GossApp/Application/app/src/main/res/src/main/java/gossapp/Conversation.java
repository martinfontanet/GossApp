package main.java.gossapp;

import java.util.ArrayList;

public class Conversation {
    private final int id;
    private final ArrayList<User> users;
    private String name;


    public Conversation(int id, ArrayList<User> users, String name) {
        this.id = id;
        this.users = users;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }
}
