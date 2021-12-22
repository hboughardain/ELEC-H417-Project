package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Client;
import views.LogInViewController;
import views.MessengerViewController;

import java.io.IOException;

public class MessengerController implements MessengerViewController.MessengerViewListener, Client.MessageListener, Client.UserStatusListener {

    private final MessengerListener listener;
    private final Stage stage;
    private MessengerViewController messengerViewController;

    public MessengerController(MessengerListener listener, Stage stage) {
        this.stage = stage;
        this.listener = listener;
    }

    public void show() throws IOException {
        FXMLLoader loader = new FXMLLoader(LogInViewController.class.getResource("MessengerView.fxml"));
        loader.load();
        messengerViewController = loader.getController();
        messengerViewController.setListener(this);
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void hide() {
        stage.hide();
    }

    @Override
    public void onSend(String username, String message) {
        try {
            Client.getInstance().message(username, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisconnect() {
        try {
            Client.getInstance().disconnect();
            Client.getInstance().start(this);
            listener.onDisconnectButton();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(String username, String message) {
        messengerViewController.onMessageReceived(username, message);
    }

    @Override
    public void online(String username) {
        messengerViewController.onUserOnline(username);
    }

    @Override
    public void offline(String username) {
        messengerViewController.onUserOffline(username);
    }

    public interface MessengerListener {
        void onDisconnectButton();
    }
}
