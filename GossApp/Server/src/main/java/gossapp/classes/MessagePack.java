package gossapp.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

/**
 * Created by martin on 06/12/17.
 */

public class MessagePack {
    private final ArrayList<Message> pack;

    public MessagePack(@JsonProperty("pack") ArrayList<Message> pack) {
        this.pack = new ArrayList<>(pack);
    }

    public void fillPack(Date from, TreeSet<Message> messages){
        for(Message message: messages){
            if(message.getDateAndTime().compareTo(from) >= 0) {
                pack.add(message);
            }
        }
    }

    public ArrayList<Message> getPack(){
        return pack;
    }
}

