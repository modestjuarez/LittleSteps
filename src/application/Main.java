package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private VBox root;
    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Login");
        
        BorderPane everything = new BorderPane();

        root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        
        //changed code to get logo jpg file from the Logo folder in repo directory
        File logoFile = new File("Logo/logoFull.jpeg");
        Image logoImage = new Image(logoFile.toURI().toString());
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(200); // Set the width to 200 pixels
        logoImageView.setPreserveRatio(true);

        root.getChildren().add(logoImageView);

        Region spacer1 = new Region();
        VBox.setVgrow(spacer1, Priority.ALWAYS);
        root.getChildren().add(spacer1);

        
        Label optionLabel = new Label("Please select one option:");
        root.getChildren().add(optionLabel);
        
        Region spacer2 = new Region();
        VBox.setVgrow(spacer2, Priority.ALWAYS);
        root.getChildren().add(spacer2);

        HBox buttonBox = new HBox(50);
        buttonBox.setAlignment(Pos.CENTER);

        Button staffButton = new Button("Staff Login");
        staffButton.setOnAction(e -> showStaffLogin());
        staffButton.setStyle("-fx-background-color: rgba(255,200,80,1); -fx-font-size: 16px; -fx-border-color: black;");
        HBox.setHgrow(staffButton, Priority.ALWAYS);
        Button patientButton = new Button("Patient Login");
        patientButton.setStyle("-fx-background-color: rgba(255,150,150,1); -fx-font-size: 16px; -fx-border-color: black;");
        
        patientButton.setOnAction(e -> showPatientLogin());
        HBox.setHgrow(patientButton, Priority.ALWAYS);
        
        buttonBox.getChildren().addAll(staffButton,  patientButton);
        root.getChildren().add(buttonBox);
        everything.setCenter(root);

        everything.setStyle("-fx-background-color: white;");

        Scene scene = new Scene(everything, 600, 400);
        scene.setFill(Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showStaffLogin() {
        root.getChildren().clear();
        StaffLogin staffLogin = new StaffLogin(this::showMainPage);
        staffLogin.start(primaryStage);
    }
    
    private void showMainPage() {
        root.getChildren().clear();
        start(primaryStage);
    }

    private void showPatientLogin() {
        root.getChildren().clear();
        PatientLogin patientLogin = new PatientLogin(this::showMainPage);
        patientLogin.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}