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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene2Controller {

    @FXML private Label welcomeLabel, notificationLabel;
    @FXML private Button addEventButton;
    @FXML private ListView<String> listView;
    @FXML private TextField titleField, categoryField, descriptionField,
            dateField, timeField, locationField, capacityField;

    private int editingIndex = -1;

    @FXML
    public void initialize() {
        listView.setItems(EventStore.getEventDisplays());
    }

    public void displayName(String username) {
        welcomeLabel.setText("Welcome, " + username);
    }

    @FXML
    private void handleAddEvent(ActionEvent event) {
        String title       = titleField.getText().trim();
        String category    = categoryField.getText().trim();
        String description = descriptionField.getText().trim();
        String date        = dateField.getText().trim();
        String time        = timeField.getText().trim();
        String location    = locationField.getText().trim();
        String capacityStr = capacityField.getText().trim();

        if (title.isEmpty() || category.isEmpty() || description.isEmpty() ||
                date.isEmpty() || time.isEmpty() || location.isEmpty() || capacityStr.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }

        int capacity;
        try {
            capacity = Integer.parseInt(capacityStr);
            if (capacity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showError("Capacity must be a positive number.");
            return;
        }

        Event newEvent = new Event(title, category, description, date, time, location, capacity);

        if (editingIndex >= 0) {
            EventStore.updateEvent(editingIndex, newEvent);
            editingIndex = -1;
            addEventButton.setText("Add Event");
            showSuccess("Event updated successfully.");
        } else {
            EventStore.addEvent(newEvent);
            showSuccess("Event added successfully.");
        }
        clearAllFields();
    }

    @FXML
    private void handleDeleteEvent(ActionEvent event) {
        int index = listView.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            showError("Select an event to delete.");
            return;
        }
        EventStore.removeEvent(index);
        if (editingIndex == index) {
            editingIndex = -1;
            addEventButton.setText("Add Event");
            clearAllFields();
        }
        showSuccess("Event deleted.");
    }

    @FXML
    private void handleEditEvent(ActionEvent event) {
        int index = listView.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            showError("Select an event to edit.");
            return;
        }
        Event e = EventStore.getEvent(index);
        titleField.setText(e.getTitle());
        categoryField.setText(e.getCategory());
        descriptionField.setText(e.getDescription());
        dateField.setText(e.getDate());
        timeField.setText(e.getTime());
        locationField.setText(e.getLocation());
        capacityField.setText(String.valueOf(e.getCapacity()));
        editingIndex = index;
        addEventButton.setText("Update Event");
        notificationLabel.setStyle("-fx-text-fill: #d97706; -fx-font-size: 12px;");
        notificationLabel.setText("Editing — modify the fields and click Update Event.");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("firstscene.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void clearAllFields() {
        titleField.clear(); categoryField.clear(); descriptionField.clear();
        dateField.clear();  timeField.clear();     locationField.clear();
        capacityField.clear();
    }

    private void showError(String msg) {
        notificationLabel.setStyle("-fx-text-fill: #dc2626; -fx-font-size: 12px;");
        notificationLabel.setText(msg);
    }

    private void showSuccess(String msg) {
        notificationLabel.setStyle("-fx-text-fill: #16a34a; -fx-font-size: 12px;");
        notificationLabel.setText(msg);
    }
}
