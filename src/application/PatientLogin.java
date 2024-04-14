package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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
	/******************************************************************
	 * 
	 * DIRECTORY PATH OPTIONS TO CHOOSE FROM:
	 * 
	 * First check the path in PatientFileManager. Uncomment whichever of the two Strings below to
	 * reflect the option chosen in the PatientFileManager.java file
	 * 
	 ******************************************************************/
	//Directory path to repo LittleSteps directory. Will create /LittleSteps/patient_data were files are contained
	//private static final String PATIENT_FILE_DIRECTORY = File.separator + "patient_data";
	private static final String PATIENT_FILE_DIRECTORY = System.getProperty("user.home") + File.separator + "Documents" 
		    + File.separator + "patient_data";
	

	private Runnable onBack;
	private Main main;

    public PatientLogin(Main main, Runnable onBack) {
    	this.main = main;
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
        nameInput.setPromptText("Username format: FirstName_LastNameDOB(MMDDYY)");

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        
        Button backButton = new Button("Back");
        backButton.setPrefWidth(200);
        backButton.setOnAction(e -> onBack.run());

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(200);

        loginButton.setOnAction(e -> {
        		//code to retrieve login info inside the /patient_data/firstname_lastnameDOBdigits/login.txt is below
        		String username = nameInput.getText();
        		String password = passwordInput.getText();
        		
        		//fixed code to took for patient_data directory 
        		String USERS_FILE = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "patient_data" 
        				+ File.separator + username;
        		System.out.print(USERS_FILE);
        		if (isValidUser(username, password, USERS_FILE)) {
        			
        			PatientView patientPage = new PatientView();
                    patientPage.show();
        			
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
    	Path loginFilePath = Paths.get(PATIENT_FILE_DIRECTORY, username, "login.txt");
        try {
        	//read the contents of the login.txt file into a string
            String storedPassword = Files.readString(loginFilePath).trim(); //trim whitespace
            //check if the password entered matches the password in the patients file
            if (storedPassword.equals(password)) {
                return true; //password matches the stored password
            } else {
            	new Alert(Alert.AlertType.ERROR, "The entered password is incorrect.").showAndWait();
            }
        } catch (NoSuchFileException e) {
        	new Alert(Alert.AlertType.ERROR, "Username is incorrect. Could not locate patients profile.").showAndWait();
        }catch (IOException e) {
        	new Alert(Alert.AlertType.ERROR, "An error occured while verifying entered info.").showAndWait();
        }
        return false;
    }
    
    private Map<String, String> createAccount() {
	    //Create a new stage for the pop up window
	    final Stage searchWindow = new Stage();
	    searchWindow.setTitle("Create Patient Account");
	    
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
	        	if (isValidAccount(firstNameField.getText().toLowerCase(), lastNameField.getText().toLowerCase(), dobField.getText())){
	        		//fixed path to the patients directory and the login file where the new password will be stored
	        		Path pathToLoginFile = Paths.get(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "patient_data"
	        				+ File.separator +firstNameField.getText().toLowerCase() + "_" + lastNameField.getText().toLowerCase() + dobField.getText() + File.separator + "login.txt");
	        		createPatientPassword(pathToLoginFile, password.getText());
	        		searchWindow.close();
	        	} else {
	        		new Alert(Alert.AlertType.ERROR, "User does not exist").showAndWait();
	        	}
	        }
	    });

	    searchVbox.getChildren().addAll(new Label("Enter New Patient Details:"), firstNameField, lastNameField, dobField, password, submitButton);
	    
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
    	//fixed path to patients directory
    	Path path = Paths.get(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "patient_data"
    				+ File.separator +firstName + "_" + lastName + dob);
        return Files.exists(path);
    }
    
    //fixed method used to create the patients password
    private void createPatientPassword(Path loginFilePath, String newLoginPasswd) {
    	try {
			Files.write(loginFilePath, newLoginPasswd.getBytes());//write to the login.txt file in patiens file
		} catch (IOException e) {
			e.printStackTrace();
			new Alert(Alert.AlertType.ERROR, "Could not update login password.").showAndWait();
		}
    }
    
}
