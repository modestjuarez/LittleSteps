package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PatientView extends Stage {

    private VBox root;

    public PatientView() {
        setTitle("Patient Portal");

        BorderPane everything = new BorderPane();

        root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        // Option Label
        Label optionLabel = new Label("Welcome, Patient Name!");
        optionLabel.setFont(Font.font(18));
        root.getChildren().add(optionLabel);

        // Contact Information Section
        VBox contactInfoSection = new VBox(10);
        contactInfoSection.setAlignment(Pos.CENTER_LEFT);
        Label contactInfoLabel = new Label("Contact Information:");
        contactInfoLabel.setFont(Font.font(16));
        TextArea contactInfoTextArea = new TextArea();
        contactInfoTextArea.setPrefRowCount(3);
        contactInfoTextArea.setEditable(false);
        contactInfoSection.getChildren().addAll(contactInfoLabel, contactInfoTextArea);
        root.getChildren().add(contactInfoSection);

        // Visit Summaries Section
        VBox visitSummariesSection = new VBox(10);
        visitSummariesSection.setAlignment(Pos.CENTER_LEFT);
        Label visitSummariesLabel = new Label("Visit Summaries:");
        visitSummariesLabel.setFont(Font.font(16));
        TextArea visitSummariesTextArea = new TextArea();
        visitSummariesTextArea.setPrefRowCount(6);
        visitSummariesTextArea.setEditable(false);
        visitSummariesSection.getChildren().addAll(visitSummariesLabel, visitSummariesTextArea);
        root.getChildren().add(visitSummariesSection);

        // Messages Section
        VBox messagesSection = new VBox(10);
        messagesSection.setAlignment(Pos.CENTER_LEFT);
        Label messagesLabel = new Label("Messages from Doctor/Nurse:");
        messagesLabel.setFont(Font.font(16));
        TextArea messagesTextArea = new TextArea();
        messagesTextArea.setPrefRowCount(6);
        messagesTextArea.setEditable(false); // To be replaced with patient data
        messagesSection.getChildren().addAll(messagesLabel, messagesTextArea);
        root.getChildren().add(messagesSection);

        // Message Section
        VBox messageSection = new VBox(10);
        messageSection.setAlignment(Pos.CENTER_LEFT);
        Label messageLabel = new Label("Send Message to Doctor/Nurse:");
        messageLabel.setFont(Font.font(16));
        TextArea messageTextArea = new TextArea();
        messageTextArea.setPrefRowCount(6);
        Button sendMessageButton = new Button("Send Message");
        messageSection.getChildren().addAll(messageLabel, messageTextArea, sendMessageButton);
        root.getChildren().add(messageSection);

        // Spacer
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        root.getChildren().add(spacer);

        // Logout Button
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> logout());
        root.getChildren().add(logoutButton);

        everything.setCenter(root);
        everything.setStyle("-fx-background-color: white;");

        Scene scene = new Scene(everything, 800, 600);
        scene.setFill(Color.WHITE);
        setScene(scene);
    }

    private void logout() {
        // Implement logout functionality here
        System.out.println("Logged out.");
    }


}
