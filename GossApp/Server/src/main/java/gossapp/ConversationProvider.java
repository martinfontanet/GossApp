package gossapp;

import gossapp.classes.Conversation;
import gossapp.classes.Message;
import gossapp.classes.User;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.CommonsRequestLoggingFilter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ConversationProvider {
    private final HashMap<Integer, Conversation> conversationsPerID = new HashMap<>();
    private final HashMap<Integer, ArrayList<Integer>> conversationsPerUser = new HashMap<Integer, ArrayList<Integer>>();
    private final HashMap<Integer, User> usersByID = new HashMap<>();

    // Returns the ID of every conversation the user has
    @RequestMapping(path = "/requestConversations", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Integer> requestConversationList(@RequestParam(value = "userID") int userID){
        return conversationsPerUser.get(userID);
    }

    // Returns a specific conversation
    @RequestMapping(path = "/getConversation", method = RequestMethod.POST)
    @ResponseBody
    public Conversation getConversation(@RequestParam(value = "conversationID") int conversationID)
    {
        return conversationsPerID.get(conversationID);
    }

    @RequestMapping(path = "/newUser")
    @ResponseBody
    public User newUser(@RequestBody User user) {
        if(usersByID.get(user.getID()) == null){
            usersByID.put(user.getID(), user);
            return user;
        }

        return null;
    }


    @RequestMapping(path = "/newMessage")
    @ResponseBody
    public Message newMessage(@RequestBody Message message) throws IOException {
        if(conversationsPerID.get(message.getConversationID()) != null){
            conversationsPerID.get(message.getConversationID()).addMessage(message);
            return message;
        }

        return null;
    }

    // Adds a user to the specified conversation
    @RequestMapping(path = "/newConversation", method = RequestMethod.POST)
    @ResponseBody
    public Conversation newConversation(@RequestBody Conversation conversation){
        if (conversationsPerID.get(conversation.getId()) == null) {
            conversationsPerID.put(conversation.getId(),conversation);
            return conversation;
        }
        return null;
    }

    @RequestMapping(path = "/test")
    public Conversation test(){
        Conversation convtest = new Conversation(1, "convtest");

        convtest.addUser(new User(12,"martin"));
        convtest.addUser(new User(13,"nathan"));

        return convtest;
    }

    @RequestMapping(path = "/test2", method = RequestMethod.POST)
    @ResponseBody
    public Conversation test2(@RequestBody Conversation conv){
        return conv;
    }


    // Adds (creates) a new conversation to the conversations list
    @RequestMapping(path = "/addUserToConversation", method = RequestMethod.POST)
    @ResponseBody
    public boolean addUserToConversation(@RequestParam(value="userID") int userID,
                                @RequestParam(value="conversationID") int conversationID){
        if (conversationsPerUser.get(userID) == null) {
            conversationsPerUser.put(userID, new ArrayList<Integer>());
        }
        if(!conversationsPerUser.get(userID).contains(conversationID) && usersByID.get(userID) != null) {
            conversationsPerUser.get(userID).add(conversationID);
            conversationsPerID.get(conversationID).addUser(usersByID.get(userID));
            return true;
        }

        return false;
    }



    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
        crlf.setIncludeClientInfo(true);
        crlf.setIncludeQueryString(true);
        crlf.setIncludePayload(true);
        return crlf;
    }



}


