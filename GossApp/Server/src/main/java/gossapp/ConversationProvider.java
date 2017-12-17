package gossapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gossapp.classes.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.CommonsRequestLoggingFilter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

@RestController
public class ConversationProvider {
    private final HashMap<Integer, Conversation> conversationsPerID = new HashMap<>();
    private final HashMap<Integer, ArrayList<Integer>> conversationsPerUser = new HashMap<Integer, ArrayList<Integer>>();
    private final HashMap<Integer, Information> infoPerUser = new HashMap<Integer, Information>();
    private final HashMap<Integer, User> usersByID = new HashMap<>();
    private final HashMap<String,User> userByPseudo = new HashMap<>();
    private final HashMap<String,String> passwordByPseudo = new HashMap<>();
    private final Random rand = new Random();

    @RequestMapping(path = "/pureTest")
    @ResponseBody
    public String pureTest(){
        ObjectMapper objmap = new ObjectMapper();

        try {
            return objmap.writeValueAsString(new User(1,"karl"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "-1";
        }
    }

    @RequestMapping(path="/connection")
    @ResponseBody
    public User connection(@RequestBody ConnectionRequest connection){
        if(userByPseudo.get(connection.getPseudo()) == null){
            System.out.println("No corresponding user");
            return new User(0,"No corresponding user");
        }
        else if(!passwordByPseudo.get(connection.getPseudo()).equals(connection.getPassword())){
            System.out.println("Wrong password: "+connection.getPassword()+" instead of "+passwordByPseudo.get(connection.getPseudo()));
            return new User(0, "Wrong password");
        }

        return userByPseudo.get(connection.getPseudo());
    }

    @RequestMapping(path = "/getInfo", method = RequestMethod.POST)
    @ResponseBody
    public Information getInfo(@RequestBody User user){
        return infoPerUser.get(user.getID());
    }
/*
    // Returns the ID of every conversation the user has
    @RequestMapping(path = "/requestConversations", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Integer> requestConversationList(@RequestParam(value = "userID") int userID){
        return conversationsPerUser.get(userID);
    }*/
/*
    // Returns a specific conversation
    @RequestMapping(path = "/getConversation", method = RequestMethod.POST)
    @ResponseBody
    public Conversation getConversation(@RequestParam(value = "conversationID") int conversationID)
    {
        return conversationsPerID.get(conversationID);
    }*/

    @RequestMapping(path = "/newUser", method=RequestMethod.POST)
    @ResponseBody
    public User newUser(@RequestBody User user) {
        if(usersByID.get(user.getID()) == null){
            usersByID.put(user.getID(), user);
            return user;
        }

        return null;
    }

    @RequestMapping(path = "/newAccount", method=RequestMethod.POST)
    @ResponseBody
    public User newAccount(@RequestBody ConnectionRequest connectionRequest) {
        if(userByPseudo.get(connectionRequest.getPseudo()) == null){
            int id = rand.nextInt();

            while (usersByID.keySet().contains(id)){
                id = rand.nextInt();
            }
            User newUser = new User(id, connectionRequest.getPseudo());
            userByPseudo.put(connectionRequest.getPseudo(),newUser);
            passwordByPseudo.put(connectionRequest.getPseudo(),connectionRequest.getPassword());
            usersByID.put(newUser.getID(), newUser);
            return newUser;
        }

        return null;
    }


    @RequestMapping(path = "/newMessage")
    @ResponseBody
    public Message newMessage(@RequestBody Message message) throws IOException {
        if(conversationsPerID.get(message.getConversationID()) != null){
            conversationsPerID.get(message.getConversationID()).addMessage(message);
            //System.out.println(conversationsPerID.get(message.getConversationID()).getMessages());
            //System.out.println(infoPerUser.get(message.getAuthorID()).getConversationsByID().get(message.getConversationID()).getMessages());
            return message;
        }

        return null;
    }
/*
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
    */

    // Adds a user to the specified conversation
    @RequestMapping(path = "/newConversation", method = RequestMethod.POST)
    @ResponseBody
    public Conversation newConversation(@RequestBody ParametersPasser<String,User,ArrayList<Contact>,Integer> params){
        int id = rand.nextInt();


        ArrayList<Contact> contacts = params.getC();

        while (conversationsPerID.keySet().contains(id)){
            id = rand.nextInt();
        }
        if (conversationsPerID.get(id) == null) {
            Conversation conversation = new Conversation(id, params.getA());
            conversationsPerID.put(id,conversation);
            User user = params.getB();
            addUserToConversation(new ParametersPasser<>(user.getName(),id,0,0));
            for(Contact contact: contacts){
                addUserToConversation(new ParametersPasser<>(contact.getName(),id,0,0));
            }

            return conversation;
        }
        return null;
    }

/*
    @RequestMapping(path = "/test")
    public Conversation test(){
        Conversation convtest = new Conversation(1, "convtest");

        convtest.addUser(new User(12,"martin"));
        convtest.addUser(new User(13,"nathan"));

        return convtest;
    }*/
/*
    @RequestMapping(path = "/test2", method = RequestMethod.POST)
    @ResponseBody
    public Conversation test2(@RequestBody Conversation conv){
        return conv;
    }

*/

    // Adds (creates) a new conversation to the conversations list
    @RequestMapping(path = "/addUserToConversation", method = RequestMethod.POST)
    @ResponseBody
    public Contact addUserToConversation(@RequestBody ParametersPasser<String, Integer, Integer, Integer> params){
           // @RequestParam(value="pseudo") String pseudo,
           //                     @RequestParam(value="conversationID") int conversationID){
        int userID = 0;
        String pseudo = params.getA();
        int conversationID = params.getB();


        if(userByPseudo.get(pseudo) != null) {
            userID = userByPseudo.get(pseudo).getID();
        }

        if (conversationsPerUser.get(userID) == null) {
            conversationsPerUser.put(userID, new ArrayList<Integer>());
        }
        if(infoPerUser.get(userID) == null){
            infoPerUser.put(userID, new Information());
        }
        if(!conversationsPerUser.get(userID).contains(conversationID) && usersByID.get(userID) != null && infoPerUser.get(userID) != null) {
            conversationsPerUser.get(userID).add(conversationID);
            conversationsPerID.get(conversationID).addUser(usersByID.get(userID));
            if(!infoPerUser.get(userID).getConversations().contains(conversationID)){
                infoPerUser.get(userID).addConversation(conversationsPerID.get(conversationID));
            }
            return new Contact(pseudo, pseudo);
        }

        return null;
    }

    @RequestMapping(path = "/getFreshMessages", method = RequestMethod.POST)
    @ResponseBody
    public MessagePack getFreshMessages(@RequestBody ParametersPasser<Integer,Long,Integer,Integer> params){
                                                    // @RequestParam(value="conversationID") int conversationID,
                                                    //  @RequestParam(value="from") int from){
        if(conversationsPerID.get(params.getA()) == null){
            return null;
        }
        return conversationsPerID.get(params.getA()).getFreshMessages(new Date(params.getB()));
    }

    // Parameters : Contact, UserID, ?, ?
    @RequestMapping(path = "/addContact", method = RequestMethod.POST)
    @ResponseBody
    public Contact addContact(@RequestBody ParametersPasser<String,String,Integer,Integer> params){
        String contactPseudo = params.getA();
        String contactNickName = params.getB();
        int userID = params.getC();

        if(infoPerUser.get(userID) == null){
            infoPerUser.put(userID, new Information());
        }
        if(usersByID.get(userID) != null && userByPseudo.get(contactPseudo) != null){
            Contact contact = new Contact(contactPseudo, contactNickName);
            if(!usersByID.get(userID).getContacts().contains(contact)) {
                usersByID.get(userID).addContact(contact);
                if (!infoPerUser.get(userID).getContacts().contains(contact)) {
                    infoPerUser.get(userID).addContact(contact);
                }
                System.out.println(contact);
                return contact;
            }
        }

        return null;
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


