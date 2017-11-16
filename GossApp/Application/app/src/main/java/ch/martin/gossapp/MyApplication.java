package ch.martin.gossapp;

import android.app.Application;
import java.util.ArrayList;

import ch.martin.gossapp.classes.Conversation;
import ch.martin.gossapp.classes.User;

/**
 * Created by martin on 15/11/17.
 */


public class MyApplication extends Application {
    private ArrayList<Conversation> conversations;
    private int currentConversation;
    private int userID;

    public MyApplication(){
        this.conversations = getConversations();
        currentConversation = 0;
        userID = 0;
    }

    public ArrayList<Conversation> getConversations(){  
        Conversation conversation1 = new Conversation(1, "Conversation 1");
        conversation1.addUser(new User("martin",1));
        conversation1.addUser(new User("nathan",2));

        Conversation conversation2 = new Conversation(2, "Conversation 2");
        conversation1.addUser(new User("martin",1));
        conversation1.addUser(new User("nathan",2));
        conversation1.addUser(new User("tristan",3));


        ArrayList<Conversation> conversations = new ArrayList<>();
        conversations.add(conversation1);
        conversations.add(conversation2);

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

    public void setCurrentUser(int id){
        this.userID = id;
    }
    public int getUserID(){
        return userID;
    }
}
