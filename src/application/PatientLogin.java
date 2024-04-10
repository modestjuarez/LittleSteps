package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class PatientLogin extends Stage {

	private Runnable onBack;
	private Main main;

    public PatientLogin(Main main, Runnable onBack) {
    	this.main = main;
        this.onBack = onBack;
    }
    
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Staff Login");

        BorderPane everything = new BorderPane();
        Image logoImage = new Image("file:///C:/Users/cadem/OneDrive/Desktop/School/2024/CSE%20360/littlePic.png");
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
        Label info = new Label("Please Login Below:");

        Label login = new Label(); 
        
        Hyperlink createAccount = new Hyperlink("Create Account");

        // Create the bottom pane with red background
        Pane bottomPane = new Pane();
        bottomPane.setStyle("-fx-background-color: rgba(0, 120, 220, 0.8);");
        bottomPane.setMinHeight(75);
        
        Label mainLabel = new Label("Little Steps Pediatrics");
        mainLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: rgba(0, 120, 220, 0.8);");
        
        TextField nameInput = new TextField();
        nameInput.setPromptText("Username");

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        
        Button backButton = new Button("Back");
        backButton.setPrefWidth(200);
        backButton.setOnAction(e -> onBack.run());

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(200);

        loginButton.setOnAction(e -> {
        
        		String username = nameInput.getText();
        		String password = passwordInput.getText();
        		String USERS_FILE = "C:\\Users\\cadem\\OneDrive\\Desktop\\School\\2024\\CSE 360\\patients\\" + username + ".txt";
        		if (isValidUser(username, password, USERS_FILE)) {
        			main.showNurseHomepage();
        		} else {
      
        			System.out.println("Unsuccesful login.");
        		}

        });
        
        createAccount.setOnAction(e -> {
        	System.out.print("Hi!");
        	Map<String, String> patientInfo = createAccount();
        });
     
        vbox.getChildren().add(logoImageView);
        vboxMain.getChildren().add(info);
        vboxMain.getChildren().add(nameInput);
        vboxMain.getChildren().add(passwordInput);
        vboxMain.getChildren().addAll(loginButton, backButton, createAccount);
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
    
    private Map<String, String> createAccount() {
	    //Create a new stage for the pop up window
	    final Stage searchWindow = new Stage();
	    searchWindow.setTitle("Create Account");
	    
	    //VBox for layout
	    VBox searchVbox = new VBox(20);
	    searchVbox.setAlignment(Pos.CENTER);
	    searchVbox.setPadding(new Insets(10));

	    //TextFields for input
	    TextField firstNameField = new TextField();
	    firstNameField.setPromptText("First Name");
	    TextField lastNameField = new TextField();
	    lastNameField.setPromptText("Last Name");
	    TextField dobField = new TextField();
	    dobField.setPromptText("DOB (MMddyy)");
	    TextField password = new TextField();
	    password.setPromptText("Password");
	    
	    //Button for submission to search for file
	    Button submitButton = new Button("Submit");
	    submitButton.setOnAction(e -> {
	        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || dobField.getText().isEmpty() || password.getText().isEmpty()) {
	            new Alert(Alert.AlertType.ERROR, "All fields must be filled.").showAndWait();
	        } else {
	        	if (isValidAccount(firstNameField.getText(), lastNameField.getText(), dobField.getText()))
	        			{
	        				createPatientDirectory(firstNameField.getText(), lastNameField.getText(), dobField.getText(), password.getText());
	        				searchWindow.close();
	        			}
	        	else {
	        		new Alert(Alert.AlertType.ERROR, "User does not exist").showAndWait();
	        	}
	        }
	    });

	    searchVbox.getChildren().addAll(new Label("Enter Patient Details:"), firstNameField, lastNameField, dobField, password, submitButton);
	    
	    Scene dialogScene = new Scene(searchVbox, 300, 300);
	    searchWindow.setScene(dialogScene);
	    searchWindow.showAndWait();

	    //Return patient info as a map
	    Map<String, String> patientInfo = new HashMap<>();
	    patientInfo.put("firstName", firstNameField.getText());
	    patientInfo.put("lastName", lastNameField.getText());
	    patientInfo.put("dob", dobField.getText());
	    patientInfo.put("password", password.getText());
	    return patientInfo;
	}
    
    private boolean isValidAccount(String firstName, String lastName, String dob)
    {
    	Path path = Paths.get("C:\\Users\\cadem\\OneDrive\\Documents\\patient_data\\" + firstName + "_" + lastName + dob + "\\contactInfo.txt");
        return Files.exists(path);
    }
    
    public void createPatientDirectory(String firstName, String lastName, String dob, String password){
    	String PATIENT_FILE_DIRECTORY = "C:\\\\Users\\\\cadem\\\\OneDrive\\\\Desktop\\\\School\\\\2024\\\\CSE 360\\\\patients\\\\";
        String textFile = String.format("%s_%s%s.txt", firstName.toLowerCase(), lastName.toLowerCase(), dob);
        File patientFile = new File(PATIENT_FILE_DIRECTORY);
        String userinfo = firstName.toLowerCase() + "_" + lastName.toLowerCase() + dob + " " + password;
        
     
        //create each file and write the information provided by the nurse
        try {
        	Files.write(Paths.get(patientFile.getPath(), textFile), userinfo.getBytes());
            //emtpy login file that will be filled in once the user creates their login account
            
        } catch (IOException e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Could not create the patient profile directory.");
            errorAlert.showAndWait();
        }
    }
}
