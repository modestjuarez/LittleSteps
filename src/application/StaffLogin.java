package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class StaffLogin extends Stage {

	private Runnable onBack;
	private String userType;

    public StaffLogin(Runnable onBack) {
        this.onBack = onBack;
    }
	
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Staff Login");

        BorderPane everything = new BorderPane();
        
        //changed code to get logo jpg file from the Logo folder in repo directory
        File logoFile = new File("Logo/logoName.jpeg");
        Image logoImage = new Image(logoFile.toURI().toString());
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(130); // Set the width to 200 pixels
        logoImageView.setPreserveRatio(true); // Preserve the aspect ratio
        
        VBox vboxBottom = new VBox();
        vboxBottom.setAlignment(Pos.TOP_CENTER);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(10));
        
        VBox vboxMain = new VBox();
        vboxMain.setAlignment(Pos.TOP_CENTER);
        vboxMain.setPadding(new Insets(30,40,5,40));
        vboxMain.setSpacing(5);
        Label info = new Label("Please select your role from the menu below");

        // Create the bottom pane with red background
        Pane bottomPane = new Pane();
        bottomPane.setStyle("-fx-background-color: rgba(128, 0, 0, 1);");
        bottomPane.setMinHeight(75);
        
        Label mainLabel = new Label("Little Steps Pediatrics");
        mainLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: rgba(0, 120, 220, 0.8);");
        
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Doctor",
                        "Nurse"
                );
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setOnAction(event -> {
             userType = comboBox.getValue();
        });
        
        TextField nameInput = new TextField();
        nameInput.setPromptText("Username");

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        
        Button backButton = new Button("Back");
        //Button.setStyle("-fx-background-color: rgba(100, 220, 255, 1); -fx-text-fill: black; -fx-border-color: black;");
        backButton.setOnAction(e -> onBack.run());
        backButton.setPrefWidth(200);

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(200);
        //loginButton.setStyle("-fx-background-color: rgba(100, 220, 255, 1); -fx-text-fill: black; -fx-border-color: black;");

        loginButton.setOnAction(e -> {
        	if (comboBox.getValue() == null) {
                showAlert("No Selection", "Please select an option from the Menu.");
        	}
        	else
        	{
        		String username = nameInput.getText();
        		String password = passwordInput.getText();
        		//Changed code to took for staffMembers directory in this LitteSteps directory/repo
        		String USERS_FILE = "staffMembers" + File.separator + userType + File.separator + username + ".txt";

        		
        		if (isValidUser(username, password, USERS_FILE)) {
            	
        			System.out.println("Succesful login!");
        			
        			//added code to pop up the nurse homepage when nurse succesfully logs in
        			if(userType == "Nurse") {
        				new NursesHomepage(username).show();
        			}else if (userType == "Doctor"){
        				//pop up the doctors page when doctor succesfully logs in
        				DoctorView docsHomepg = new DoctorView();
        				docsHomepg.show();
        			}
        				
        		} else {
      
        			System.out.println("Unsuccesful login.");
        		}
        	}
        });
        
        
     
        vbox.getChildren().add(logoImageView);
        vboxMain.getChildren().add(info);
        vboxMain.getChildren().add(comboBox);
        vboxMain.getChildren().add(nameInput);
        vboxMain.getChildren().add(passwordInput);
        vboxMain.getChildren().addAll(loginButton, backButton);
        vboxBottom.getChildren().addAll(bottomPane);
        everything.setTop(vbox);
        everything.setCenter(vboxMain);
        everything.setBottom(vboxBottom);
        everything.setStyle("-fx-background-color: white;");
    
        BorderPane.setAlignment(vboxMain, Pos.TOP_CENTER);
        
      
        
        Scene scene = new Scene(everything, 500, 300);
       
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean isValidUser(String username, String password, String USERS_FILE) {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
                else if (parts[0].equals(username) && !parts[1].equals(password))
                {
                	showAlert("Incorrect Password", "Please enter correct password.");
                }
            }
        } catch (IOException e) {
        	showAlert("No File", "Make sure Username is entered correctly.");
        }
        return false;
    }
}
