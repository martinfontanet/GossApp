package gossapp.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by martin on 20/11/17.
 */

public class ConnectionRequest implements Comparable {
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

    @Override
    public int compareTo(Object o) {
        ConnectionRequest e = (ConnectionRequest) o;

        return pseudo.compareTo(e.getPseudo());
    }

    @Override
    public boolean equals(Object obj) {
        return pseudo.equals(((ConnectionRequest) obj).getPseudo());
    }

    public boolean checkPassword(String pass){
        return password.equals(pass);
    }

}
