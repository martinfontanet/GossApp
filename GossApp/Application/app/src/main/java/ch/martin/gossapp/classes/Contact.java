package ch.martin.gossapp.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contact extends User{
    private String name;

    public Contact(@JsonProperty("id")int id, @JsonProperty("name") String name){
        super(id,name);
    }

    public void modifyName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return id;
    }
}
