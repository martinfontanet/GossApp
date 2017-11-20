package ch.martin.gossapp.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by martin on 20/11/17.
 */

public class ConnectionRequest {
    private final String pseudo;
    private final String password;

    public ConnectionRequest(@JsonProperty("pseudo") String pseudo, @JsonProperty("password") String password) {
        this.pseudo = pseudo;
        this.password = password;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "ConnectionRequest{" +
                "pseudo='" + pseudo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
