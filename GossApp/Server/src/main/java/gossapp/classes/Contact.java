package gossapp.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contact extends User implements Comparable{
    private String nickname;

    public Contact(@JsonProperty("id")int id, @JsonProperty("name") String name, @JsonProperty("nickname") String nickname){
        super(id,name);
        this.nickname = nickname;
    }

    public void modifyNickName(String nickname){
        this.nickname = nickname;
    }

    public String getNickName(){
        return this.nickname;
    }

    public int getId(){
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        Contact c = (Contact) obj;
        return this.id == c.getId();
    }

    @Override
    public int compareTo(Object o) {
        Contact c = (Contact) o;
        return this.nickname.compareTo(c.getNickName());
    }

    @Override
    public String toString(){
        return this.nickname;
    }
}
