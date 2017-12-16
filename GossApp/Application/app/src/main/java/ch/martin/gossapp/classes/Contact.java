package ch.martin.gossapp.classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Contact implements Comparable{
    private final String name;
    private String nickname;

    @JsonCreator
    public Contact(@JsonProperty("name") String name, @JsonProperty("nickname") String nickname){
        this.name = name;
        this.nickname = nickname;
    }



    public void modifyNickname(String nickname){
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public String getNickname(){
        return this.nickname;
    }

    //public int getId(){
       // return id;
    //}

    @Override
    public boolean equals(Object obj) {
        Contact c = (Contact) obj;
        return this.name.equals(c.getName());
    }

    @Override
    public int compareTo(Object o) {
        Contact c = (Contact) o;
        return this.nickname.compareTo(c.getNickname());
    }

    @Override
    public String toString(){
        return this.nickname;
    }
}
