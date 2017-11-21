package ch.martin.gossapp;

import android.app.Application;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import ch.martin.gossapp.activities.MainActivity;
import ch.martin.gossapp.classes.Conversation;
import ch.martin.gossapp.classes.Message;
import ch.martin.gossapp.classes.User;
import ch.martin.gossapp.networking.ConversationsProvider;

/**
 * Created by martin on 15/11/17.
 */


public class MyApplication extends Application {
    private ArrayList<Conversation> conversations;
    private int currentConversation;
    private User user;
    private ConversationsProvider conversationsProvider;

    public MyApplication(){
        this.conversations = new ArrayList<Conversation>();
        currentConversation = 0;
        user = new User(-1, "");

    }

    public void addConversations(ArrayList<Conversation> conversationsList){
        conversations.clear();
        conversations.addAll(conversationsList);
    }


    public ArrayList<Conversation> getConversations(){
        /*Conversation conversation1 = new Conversation(1, "Conversation 1");
        conversation1.addUser(new User(1,"martin"));
        conversation1.addUser(new User(2, "nathan"));

        Conversation conversation2 = new Conversation(2, "Conversation 2");
        conversation2.addUser(new User(1,"martin"));
        conversation2.addUser(new User(2,"nathan"));
        conversation2.addUser(new User(3,"tristan"));


        ArrayList<Conversation> conversations = new ArrayList<>();
        conversations.add(conversation1);
        conversations.add(conversation2);*/

        try {
            conversationsProvider.getInformation(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return conversations;
    }

    public void setCurrentConversation(int id){
        currentConversation = id;
    }

    public Conversation getCurrentConversation(){
        for(Conversation conv: conversations){
            if(conv.getId()==currentConversation){
                return conv;
            }
        }

        return null;
    }

    public void refreshMessages(Conversation conversation, Date from){

        try {
            conversationsProvider.getFreshMessages(conversation, from);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addMessages(int conversationID, Conversation.MessagePack pack){
        if(pack != null) {
            for (Conversation conversation : conversations) {
                if (conversation.getId() == conversationID) {
                    for (Message message : pack.getPack())
                        conversation.addMessage(message);
                    System.out.println("ADDDDDDIIIIING");
                }
            }
            System.out.println(pack.getPack().size());
        }


    }

    public void connectUser() throws IOException {
        conversationsProvider = new ConversationsProvider(getApplicationContext());
        conversationsProvider.connectUser();
        conversationsProvider.getInformation(user);
        //conversationsProvider.getConversationsID(1);
    }

    public void setCurrentUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }
}
