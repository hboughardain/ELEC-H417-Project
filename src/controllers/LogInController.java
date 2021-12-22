package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Client;
import views.LogInViewController;

import java.io.IOException;
import java.util.Objects;

public class LogInController implements LogInViewController.LogInViewListener {

    private final LogInListener listener;
    private final Stage stage;
    private LogInViewController logInViewController;

    public static final String LOAD_LOGIN_PAGE_ERROR = "the login window had to be displayed";

    public LogInController(LogInListener listener, Stage stage){
        this.stage = stage;
        this.listener = listener;
    }

    public void show() throws IOException {
        FXMLLoader loader = new FXMLLoader(LogInViewController.class.getResource("LogInView.fxml"));
        loader.load();
        logInViewController = loader.getController();
        logInViewController.setListener(this);
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.setTitle("Mingle");
        stage.getIcons().add(new Image("file:media/logo.png"));
        stage.show();
    }

    public void hide() {
        stage.hide();
    }

    @Override
    public void onLogInButton(String username, String password) {
        try {
            if (Client.getInstance().login(username, password)) {
                listener.onLogInAsked(username);
            } else {
                logInViewController.setErrorMessage("Problem logging in");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRegisterLink() {
        listener.onRegisterLinkAsked();
    }

    public interface LogInListener {
        void onLogInAsked(String username);
        void onRegisterLinkAsked();
    }
}
