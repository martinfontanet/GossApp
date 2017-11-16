package ch.martin.gossapp.classes;

import java.util.ArrayList;

public class Conversation {
    private final int id;
    private final ArrayList<User> users;
    private String name;
    private final ArrayList<Message> messages = new ArrayList<>();



    public Conversation(int id, String name) {
        this.id = id;
        this.users = new ArrayList<>();
        this.name = name;
    }

    public void addUser(User user){
        if(!users.contains(user)) {
            this.users.add(user);
        }
    }

    public int getId() {
        return id;
    }

    public String getNameByID(int id){
        for(User user: users){
            if(user.getID()==id){
                return user.getName();
            }
        }

        return "";
    }

    public void addMessage(Message message){
        messages.add(message);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", users=" + users +
                ", name='" + name + '\'' +
                ", messages=" + messages +
                '}';
    }

    public String getName() {
        return name;
    }

    public ArrayList<Message> getFreshMessages(int index) {
        ArrayList<Message> newMessages = new ArrayList<>();

        for(int i=index; i<messages.size(); ++i){
            newMessages.add(messages.get(i));
        }
        return newMessages;
    }

    public ArrayList<Message> getMessages(){
        return this.messages;
    }
}
