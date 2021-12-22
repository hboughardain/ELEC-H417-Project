package views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MessengerViewController {

    @FXML
    public TextField messageTextField;
    @FXML
    public TextArea conversationTextArea;
    @FXML
    public Button sendButton;
    @FXML
    public Button disconnectButton;
    @FXML
    public ListView<String> usersListView;

    private MessengerViewListener listener;

    public void setListener(MessengerViewListener messengerViewListener) {
        this.listener = messengerViewListener;
    }

    public void onSendButton() {
        String message = messageTextField.getText();
        if (usersListView.getSelectionModel().getSelectedItem() != null) {
            if (!"".equals(message)) {
                conversationTextArea.appendText("You\t\t" + message + "\n");
                messageTextField.clear();
                listener.onSend(usersListView.getSelectionModel().getSelectedItem(), message);
            }
        }
    }

    public void onDisconnectButton() {
        listener.onDisconnect();
    }

    public void onMessageReceived(String username, String message) {
        conversationTextArea.appendText(username + "\t" + message + "\n");
    }

    public void onUserOffline(String username) {
        usersListView.getItems().remove(username);
    }

    public void onUserOnline(String username) {
        usersListView.getItems().add(username);
    }

    public interface MessengerViewListener {
        void onSend(String username, String message);
        void onDisconnect();
    }
}
