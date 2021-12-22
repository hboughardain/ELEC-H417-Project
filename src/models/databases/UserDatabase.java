package models.databases;

import models.User;
import java.util.ArrayList;

public class UserDatabase extends Database<User> {
    private static UserDatabase instance;

    public static UserDatabase getInstance(){
        if (instance == null){
            instance = new UserDatabase();
        }
        return instance;
    }

    private UserDatabase(){
        path = "database/UserDB.serial";
        data = new ArrayList<>();
    }

    public void replace(User user, User temporaryUser) {
        for (User databaseUser : data) {
            if (databaseUser.getUsername().equals(user.getUsername())) {
                data.set(data.indexOf(databaseUser), temporaryUser);
                break;
            }
        }
    }

    public boolean checkModification(User user, User temporaryUser){
        return ((checkExistingEmail(temporaryUser.getEmailAddress()) &&
                temporaryUser.getEmailAddress().equals(user.getEmailAddress())
                || !checkExistingEmail(temporaryUser.getEmailAddress())));
    }

    public boolean checkExistingUser(User user){
        for (User databaseUser : data){
            if (databaseUser.getUsername().equals(user.getUsername()) ||
                    databaseUser.getEmailAddress().equals(user.getEmailAddress())){
                return true;
            }
        }
        return false;
    }

    public boolean checkExistingEmail(String email){
        for (User databaseUser : data){
            if (databaseUser.getEmailAddress().equals(email)){
                return true;
            }
        }
        return false;
    }

    public boolean logIn(String username, String password){
        for (User databaseUser : data){
            if (databaseUser.getUsername().equals(username) && databaseUser.getPassword().equals(password)
                    && !databaseUser.isConnected()){
                databaseUser.setConnected(true);
                return true;
            }
        }
        return false;
    }

    public void logOut(User user){
        for (User databaseUser : data){
            if (databaseUser.getUsername().equals(user.getUsername())){
                databaseUser.setConnected(false);
            }
        }
    }

    public User getUser(String username){
        for (User user : data){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
}
