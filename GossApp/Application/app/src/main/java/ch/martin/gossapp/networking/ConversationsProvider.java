package ch.martin.gossapp.networking;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


import com.android.volley.Request;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import ch.martin.gossapp.MyApplication;
import ch.martin.gossapp.R;
import ch.martin.gossapp.activities.MainActivity;
import ch.martin.gossapp.classes.ConnectionRequest;
import ch.martin.gossapp.classes.Contact;
import ch.martin.gossapp.classes.Conversation;
import ch.martin.gossapp.classes.Information;
import ch.martin.gossapp.classes.Message;
import ch.martin.gossapp.classes.MessagePack;
import ch.martin.gossapp.classes.ParametersPasser;
import ch.martin.gossapp.classes.User;

public class ConversationsProvider {

    //private static final String URL_getConversationsID = ServerAccess.BASE_URL + "/requestConversations?userID=%d";
    //private static final String URL_getConversation = ServerAccess.BASE_URL + "/getConversation?conversationID=%d";
    private static final String URL_freshMessages = ServerAccess.BASE_URL + "/getFreshMessages";
    private static final String URL_getInfo = ServerAccess.BASE_URL + "/getInfo";
    private static final String URL_newMessage = ServerAccess.BASE_URL + "/newMessage";
    private static final String URL_connection = ServerAccess.BASE_URL + "/connection";
    private static final String URL_createAccount = ServerAccess.BASE_URL + "/newAccount";
    private static final String URL_newConversation = ServerAccess.BASE_URL + "/newConversation";
    private static final String URL_addContact = ServerAccess.BASE_URL + "/addContact";
    private static final String URL_addUserToConversation = ServerAccess.BASE_URL + "/addUserToConversation";



    private static final String URL_test = ServerAccess.BASE_URL + "/pureTest";


    private final Context context;
    public ConversationsProvider(Context context) {
        this.context = context;

    }

    public void connectUser() throws IOException {
        ServerAccess<ConnectionRequest, User> serverAccess = new ServerAccess<>(context, Request.Method.GET, URL_test, new ServerAccess.OnResultHandler<User>() {
            @Override
            public void onSuccess(User response) {
                ((MyApplication) context).setCurrentUser(response);
            }

            @Override
            public void onError() {
                //mainAct.renameTitle("Connection error.");
            }

        }, User.class);

        ConnectionRequest query = new ConnectionRequest("bernard","password");
        try {
            serverAccess.makeRequest(query);
        } catch (ServerAccess.ServerAccessException e) {

            throw new IOException();
        }
    }
/*
    public void getConversationsID(int id) throws IOException {
        ServerAccess<ConnectionRequest, ArrayList> serverAccess = new ServerAccess<>(context, Request.Method.POST, getConversationURL(id), new ServerAccess.OnResultHandler< ArrayList>() {
            @Override
            public void onSuccess( ArrayList response) {
                for(int i=0; i<response.size(); ++i){
                    System.out.println(response.get(i));
                    try {
                        getConversation((Integer) response.get(i));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError() {
                System.out.println("rho");
                //mainAct.renameTitle("Connection error.");
            }

        },  ArrayList.class);

        ConnectionRequest query = new ConnectionRequest("bernard","password");
        try {
            serverAccess.makeRequest(query);
        } catch (ServerAccess.ServerAccessException e) {

            throw new IOException();
        }
    }

    public void getConversation(int id) throws IOException {
        ServerAccess<ConnectionRequest, Conversation> serverAccess = new ServerAccess<>(context, Request.Method.POST, getConversationURL(id), new ServerAccess.OnResultHandler<Conversation>() {
            @Override
            public void onSuccess(Conversation response) {
                ((MyApplication) context).addConversation(response);
            }

            @Override
            public void onError() {
                //mainAct.renameTitle("Connection error.");
            }

        }, Conversation.class);

        ConnectionRequest query = new ConnectionRequest("bernard","password");
        try {
            serverAccess.makeRequest(query);
        } catch (ServerAccess.ServerAccessException e) {

            throw new IOException();
        }
    }
*/
public void getInformation(User query) throws IOException {
    ServerAccess<User, Information> serverAccess = new ServerAccess<>(context, Request.Method.POST, URL_getInfo, new ServerAccess.OnResultHandler<Information>() {
        @Override
        public void onSuccess(Information response) {
            ((MyApplication) context).addConversations(response.getConversations());
            ((MyApplication) context).addContacts(response.getContacts());
        }

        @Override
        public void onError() {
            ((MyApplication) context).addConversations(new ArrayList<Conversation>());
            //mainAct.renameTitle("Connection error.");
        }

    }, Information.class);

    try {
        serverAccess.makeRequest(query);
    } catch (ServerAccess.ServerAccessException e) {

        throw new IOException();
    }
}

    public void getFreshMessages(final Conversation conversation, Date time) throws IOException {
        final ParametersPasser<Integer,Long,Integer,Integer> query = new ParametersPasser<>(conversation.getId(),time.getTime(),0,0);
        ServerAccess<ParametersPasser<Integer,Long,Integer,Integer>, MessagePack> serverAccess = new ServerAccess<>(context, Request.Method.POST, URL_freshMessages, new ServerAccess.OnResultHandler<MessagePack>() {
            @Override
            public void onSuccess(MessagePack response) {
                ((MyApplication) context).addMessages(conversation.getId(),(ArrayList<Message>) response.getPack());
            }

            @Override
            public void onError() {
                //System.out.println("DID NOT WORK");
                ((MyApplication) context).addMessages(conversation.getId(), null);
                //mainAct.renameTitle("Connection error.");
            }

        }, MessagePack.class);

        try {
            serverAccess.makeRequest(query);
        } catch (ServerAccess.ServerAccessException e) {

            throw new IOException();
        }
    }

    public void newMessage(final Message message) throws IOException{
    System.out.println(message.getAuthorID()+" "+message.getConversationID()+" "+message.getDateAndTime()+" "+message);
        ServerAccess<Message, Message> serverAccess = new ServerAccess<>(context, Request.Method.POST, URL_newMessage, new ServerAccess.OnResultHandler<Message>() {
            @Override
            public void onSuccess(Message response) {
                if(!message.equals(response)){
                    //ERROR
                    System.out.println("MESSAGE NOT SENT");
                }
            }

            @Override
            public void onError() {
                //mainAct.renameTitle("Connection error.");
            }

        }, Message.class);

        try {
            serverAccess.makeRequest(message);
        } catch (ServerAccess.ServerAccessException e) {

            throw new IOException();
        }
    }

    public void connectAccount(final ConnectionRequest connection) throws IOException{
    System.out.println(connection);
        ServerAccess<ConnectionRequest, User> serverAccess = new ServerAccess<>(context, Request.Method.POST, URL_connection, new ServerAccess.OnResultHandler<User>() {
            @Override
            public void onSuccess(User response) {
                ((MyApplication) context).setCurrentUser(response);
                System.out.println(response);
                try {
                    ((MyApplication) context).connectUser();
                } catch (IOException e) {
                    System.out.println("AJAA");
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {
                System.out.println("OO");
                //mainAct.renameTitle("Connection error.");
            }

        }, User.class);

        try {
            serverAccess.makeRequest(connection);
        } catch (ServerAccess.ServerAccessException e) {
            System.out.println("serveraccess");
            throw new IOException();
        }
    }

    public void createAccount(final ConnectionRequest connection) throws IOException{
        System.out.println(connection);
        ServerAccess<ConnectionRequest, User> serverAccess = new ServerAccess<>(context, Request.Method.POST, URL_createAccount, new ServerAccess.OnResultHandler<User>() {
            @Override
            public void onSuccess(User response) {
                if(response!=null) {
                    ((MyApplication) context).setCreatedAccount(1);
                    System.out.println("NEW AC");
                    try {
                        connectAccount(connection);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println("DEJA");
                    ((MyApplication) context).setCreatedAccount(-1);
                }

            }

            @Override
            public void onError() {
                System.out.println("UU");
                ((MyApplication) context).setCreatedAccount(-1);
                //mainAct.renameTitle("Connection error.");
            }

        }, User.class);

        try {
            serverAccess.makeRequest(connection);
        } catch (ServerAccess.ServerAccessException e) {
            System.out.println("serveraccess");
            ((MyApplication) context).setCreatedAccount(-2);
            throw new IOException();
        }
    }
/*
    public String getFreshMessagesURL(int conversationID, long date) {
        return String.format(URL_freshMessages, conversationID, date);
    }

    public String getConversationsByIDURL(int userID) {
        return String.format(URL_getConversationsID, userID);
    }

    public String getConversationURL(int conversationID) {

        return String.format(URL_getConversation, conversationID);

    }
    */

    public void addContact(ParametersPasser<String, String, Integer, Integer> query) throws IOException{
        ServerAccess<ParametersPasser<String, String, Integer, Integer>, Contact> serverAccess = new ServerAccess<>(context, Request.Method.POST, URL_addContact, new ServerAccess.OnResultHandler<Contact>() {
            @Override
            public void onSuccess(Contact response) {
                System.out.println("OKOK CONTACT");
            }

            @Override
            public void onError() {
                System.out.println("ERROR CONTACT");
            }

        }, Contact.class);

        try {
            serverAccess.makeRequest(query);
        } catch (ServerAccess.ServerAccessException e) {

            throw new IOException();
        }
    }

    public void createConversation(ParametersPasser<String, User, ArrayList<Contact>, Integer> query) throws IOException {
        ServerAccess<ParametersPasser<String, User, ArrayList<Contact>, Integer>, Conversation> serverAccess = new ServerAccess<>(context, Request.Method.POST, URL_newConversation, new ServerAccess.OnResultHandler<Conversation>() {
            @Override
            public void onSuccess(Conversation response) {
                System.out.println(response);
            }

            @Override
            public void onError() {
                System.out.println("ERROR");
                //mainAct.renameTitle("Connection error.");
            }

        }, Conversation.class);

        try {
            serverAccess.makeRequest(query);
        } catch (ServerAccess.ServerAccessException e) {

            throw new IOException();
        }
    }

    public void addUserToConversation(ParametersPasser<String, Integer, Integer, Integer> query) throws IOException{
        ServerAccess<ParametersPasser<String, Integer, Integer, Integer>, Contact> serverAccess = new ServerAccess<>(context, Request.Method.POST, URL_addUserToConversation, new ServerAccess.OnResultHandler<Contact>() {
            @Override
            public void onSuccess(Contact response) {
                System.out.println(response.getName() + "ADDED TO THE CONVERSATION");
            }

            @Override
            public void onError() {
                System.out.println("ERROR addConv");
            }

        }, Contact.class);

        try {
            serverAccess.makeRequest(query);
        } catch (ServerAccess.ServerAccessException e) {

            throw new IOException();
        }
    }
}

