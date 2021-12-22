package views;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterViewController {
    @FXML
    private Label errorMessageLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;

    private RegisterViewListener listener;

    public void setListener(RegisterViewListener registerViewListener) {
        this.listener = registerViewListener;
    }

    public void onRegisterButton() throws Exception {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String email = emailTextField.getText();
        listener.onRegisterButton(firstname, lastname, username, email, password);
    }

    public void onBackButton() {
        listener.onBackButton();
    }

    public void setErrorMessage(String errorMessage){
        errorMessageLabel.setText(errorMessage);
        errorMessageLabel.setVisible(true);
    }

    public interface RegisterViewListener {
        void onRegisterButton(String firstname, String lastname, String username, String email, String password) throws Exception;
        void onBackButton();
    }
}

