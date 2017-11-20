package ch.martin.gossapp.networking;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
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
import java.util.HashMap;
import java.util.Map;

import ch.martin.gossapp.MyApplication;
import ch.martin.gossapp.R;
import ch.martin.gossapp.activities.MainActivity;
import ch.martin.gossapp.classes.ConnectionRequest;
import ch.martin.gossapp.classes.Conversation;
import ch.martin.gossapp.classes.Information;
import ch.martin.gossapp.classes.User;

public class ConversationsProvider {

    //private static final String URL_getConversationsID = ServerAccess.BASE_URL + "/requestConversations?userID=%d";
    //private static final String URL_getConversation = ServerAccess.BASE_URL + "/getConversation?conversationID=%d";
    private static final String URL_getInfo = ServerAccess.BASE_URL + "/getInfo";
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
        }

        @Override
        public void onError() {
            //mainAct.renameTitle("Connection error.");
        }

    }, Information.class);

    try {
        serverAccess.makeRequest(query);
    } catch (ServerAccess.ServerAccessException e) {

        throw new IOException();
    }
}
/*
    public String getConversationsByIDURL(int userID) {
        return String.format(URL_getConversationsID, userID);
    }

    public String getConversationURL(int conversationID) {

        return String.format(URL_getConversation, conversationID);

    }
    */
}