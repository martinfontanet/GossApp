package gossapp.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Conversation {
    private final int id;
    private final ArrayList<User> users;
    private String name;
    private final ArrayList<Message> messages = new ArrayList<>();

    public class MessagePack{
        private final ArrayList<Message> pack;

        public MessagePack(@JsonProperty("date") Date date) {
            pack = new ArrayList<>();
            for(Message message: messages){
                if(message.getDateAndTime().compareTo(date) >= 0) {
                    pack.add(message);
                }
            }
        }

        public ArrayList<Message> getPack(){
            return pack;
        }
    }

    public Conversation(@JsonProperty("id") int id,@JsonProperty("name") String name) {
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
        if(!messages.contains(message)) {
            messages.add(message);
        }
        Collections.sort(messages);
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

    public MessagePack getFreshMessages(Date date) {
        return new MessagePack(date);
    }

    public ArrayList<Message> getMessages(){
        return this.messages;
    }
}
