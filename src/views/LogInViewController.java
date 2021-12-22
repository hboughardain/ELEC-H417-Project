package views;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LogInViewController {

    @FXML
    private Label errorMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    private LogInViewListener listener;

    public void setListener(LogInViewListener listener) {
        this.listener = listener;
    }

    public void onLogInButton() throws Exception {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        listener.onLogInButton(username, password);
    }

    public void onRegisterLink() {
        listener.onRegisterLink();
    }

    public void setErrorMessage(String errorMessage) {
        errorMessageLabel.setText(errorMessage);
        errorMessageLabel.setVisible(true);
    }

    public interface LogInViewListener {
        void onLogInButton(String username, String password) throws Exception;
        void onRegisterLink();
    }
}
