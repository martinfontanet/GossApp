package ch.martin.gossapp.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contact {
    private String name;

    public Contact(@JsonProperty("name") String name){
        this.name = name;
    }

    public void modifyName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
