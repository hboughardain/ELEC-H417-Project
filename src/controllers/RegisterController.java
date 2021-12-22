package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Client;
import views.RegisterViewController;

import java.io.IOException;

public class RegisterController implements RegisterViewController.RegisterViewListener {

    private final RegisterListener listener;
    private final Stage stage;
    private RegisterViewController registerViewController;

    public static final String LOAD_REGISTER_PAGE_ERROR = "the register window had to be displayed";

    public RegisterController(RegisterListener listener, Stage stage) {
        this.stage = stage;
        this.listener = listener;
    }

    public void show() throws IOException {
        FXMLLoader loader = new FXMLLoader(RegisterViewController.class.getResource("RegisterView.fxml"));
        loader.load();
        registerViewController = loader.getController();
        registerViewController.setListener(this);
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void hide() {
        stage.hide();
    }

    @Override
    public void onRegisterButton(String firstname, String lastname, String username, String email, String password) {
        if (Client.getInstance().register(firstname, lastname, username, email, password)) {
            listener.onRegisterAsked();
        } else {
            registerViewController.setErrorMessage("Problem creating an account");
        }
    }

    @Override
    public void onBackButton() {
        listener.onBackToLogInAsked();
    }

    public interface RegisterListener {
        void onRegisterAsked();
        void onBackToLogInAsked();
    }
}
