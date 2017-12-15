package ch.martin.gossapp;

import android.app.Application;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import ch.martin.gossapp.activities.MainActivity;
import ch.martin.gossapp.classes.ConnectionRequest;
import ch.martin.gossapp.classes.Conversation;
import ch.martin.gossapp.classes.Message;
import ch.martin.gossapp.classes.ParametersPasser;
import ch.martin.gossapp.classes.User;
import ch.martin.gossapp.networking.ConversationsProvider;

/**
 * Created by martin on 15/11/17.
 */

//TODO ERROR WHEN ADDING CONTACT
//TODO ADD CONTACT TO CONVERSATION

public class MyApplication extends Application {
    private ArrayList<Conversation> conversations;
    private int currentConversation;
    private User user;
    private ConversationsProvider conversationsProvider;
    private int createdAccount = 0;

    public MyApplication(){
        this.conversations = new ArrayList<Conversation>();
        currentConversation = 0;
        user = null;

    }

    public void addConversations(ArrayList<Conversation> conversationsList){
        conversations.clear();
        conversations.addAll(conversationsList);
    }

    public void setCreatedAccount(int i){
        createdAccount = i;
    }

    public int getCreatedAccount(){
        return createdAccount;
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

    public void addMessages(int conversationID, ArrayList<Message> pack){
        if(pack != null) {
            for (Conversation conversation : conversations) {
                if (conversation.getId() == conversationID) {
                    for (Message message : pack)
                        conversation.addMessage(message);
                }
            }
        }


    }

    public void newMessage(Message message){
        try {
            conversationsProvider.newMessage(message);
            System.out.println("AAAO");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectToAccount(ConnectionRequest connection){
        conversationsProvider = new ConversationsProvider(getApplicationContext());
        try {
            conversationsProvider.connectAccount(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAccount(ConnectionRequest connectionRequest){
        conversationsProvider = new ConversationsProvider(getApplicationContext());
        try {
            conversationsProvider.createAccount(connectionRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectUser() throws IOException {
        conversationsProvider = new ConversationsProvider(getApplicationContext());
        //conversationsProvider.connectUser();
        conversationsProvider.getInformation(user);
        System.out.println("connected");
        //conversationsProvider.getConversationsID(1);
    }

    public void setCurrentUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public void newConversation(ParametersPasser<String, ArrayList<User>,Integer,Integer> params){
        conversationsProvider = new ConversationsProvider(getApplicationContext());
        try {
            conversationsProvider.createConversation(params);
            System.out.println("a");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addContact(String pseudo, String nickname){
        conversationsProvider = new ConversationsProvider(getApplicationContext());
        ParametersPasser<String, String, Integer, Integer> params = new ParametersPasser<>(pseudo, nickname, user.getID(),0);
        System.out.println(params.toString());
        try {
            conversationsProvider.addContact(params);
            System.out.println("contactAdded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
