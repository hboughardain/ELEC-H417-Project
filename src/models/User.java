package models;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = -3233133803033315L;
    private final String firstname;
    private final String lastname;
    private String username;
    private final String emailAddress;
    private String password;
    private boolean isConnected = false;

    public User(String firstname, String lastname, String username, String emailAddress, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
    }

    public User(User user){
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.emailAddress = user.emailAddress;
        this.username = user.username;
        this.password = user.password;
        this.isConnected = user.isConnected;
    }

    public static boolean checkSyntax(String firstname, String lastname, String username, String emailAddress, String password){
        if ((firstname == null || lastname == null || username == null || password == null)){
            return false;
        } else if (Objects.equals(firstname, "") || Objects.equals(lastname, "") || Objects.equals(username, "")
                || Objects.equals(password, "")){
            return false;
        } else {
            return emailAddress.contains("@");
        }
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isConnected(){
        return isConnected;
    }

    public void setConnected(boolean bool){
        this.isConnected = bool;
    }

    public String toString(){
        return firstname + " " + lastname + " " + username + " " + emailAddress + " " + password + " " + isConnected;
    }
}
