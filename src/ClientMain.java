import controllers.LogInController;
import controllers.MessengerController;
import controllers.RegisterController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import models.Client;

import java.io.IOException;

public class ClientMain extends Application implements LogInController.LogInListener, RegisterController.RegisterListener, MessengerController.MessengerListener {

    private LogInController logInController;
    private RegisterController registerController;
    private MessengerController messengerController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        logInController = new LogInController(this, stage);
        registerController = new RegisterController(this, stage);
        messengerController = new MessengerController(this, stage);
        Client.getInstance().start(messengerController);
        try {
            logInController.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError(LogInController.LOAD_LOGIN_PAGE_ERROR);
        }
    }

    public static void showError(String type) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred when " + type + ".");
        alert.setContentText("It's not your fault but the developer's one. The program will now terminate.");
        alert.showAndWait();
        Platform.exit();
    }

    @Override
    public void onLogInAsked(String username) {
        try {
            messengerController.show();
        } catch (IOException e) {
            showError(RegisterController.LOAD_REGISTER_PAGE_ERROR);
        }
    }

    @Override
    public void onRegisterLinkAsked() {
        try {
            registerController.show();
        } catch (IOException e) {
            showError(RegisterController.LOAD_REGISTER_PAGE_ERROR);
        }
    }

    @Override
    public void onRegisterAsked() {
        try {
            logInController.show();
        } catch (IOException e) {
            showError(LogInController.LOAD_LOGIN_PAGE_ERROR);
        }
    }

    @Override
    public void onBackToLogInAsked() {
        try {
            logInController.show();
        } catch (IOException e) {
            showError(LogInController.LOAD_LOGIN_PAGE_ERROR);
        }
    }

    @Override
    public void onDisconnectButton() {
        try {
            logInController.show();
        } catch (IOException e) {
            showError(LogInController.LOAD_LOGIN_PAGE_ERROR);
        }
    }
}
