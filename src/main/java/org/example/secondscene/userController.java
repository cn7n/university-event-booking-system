package org.example.secondscene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class userController {

    @FXML private Label userLabel, statusLabel;
    @FXML private Button logoutButton;
    @FXML private ListView<String> eventListView;
    @FXML private ListView<String> registrationListView;

    @FXML
    public void initialize() {
        eventListView.setItems(EventStore.getEventDisplays());
        registrationListView.setItems(EventStore.getRegistrations());
    }

    public void displayName(String username) {
        userLabel.setText("Welcome, " + username);
    }

    @FXML
    public void handleRegister(ActionEvent event) {
        int index = eventListView.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            showStatus("Select an event to register for.", false);
            return;
        }
        if (EventStore.isRegistered(index)) {
            showStatus("You are already registered for this event.", false);
            return;
        }
        EventStore.register(index);
        showStatus("Successfully registered!", true);
    }

    @FXML
    public void handleUnregister(ActionEvent event) {
        int index = registrationListView.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            showStatus("Select a registration to cancel.", false);
            return;
        }
        EventStore.unregister(index);
        showStatus("Registration cancelled.", true);
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("firstscene.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showStatus(String msg, boolean success) {
        statusLabel.setStyle(success
                ? "-fx-text-fill: #16a34a; -fx-font-size: 12px;"
                : "-fx-text-fill: #dc2626; -fx-font-size: 12px;");
        statusLabel.setText(msg);
    }
}
