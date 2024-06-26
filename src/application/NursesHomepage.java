package application;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NursesHomepage extends Stage{
	//Directory path in repo where patient files/directories are contained
	private static final String LITTLESTEPS_LOGO = "Logo/logoName.jpeg";
	//var holds the name of the file of the signed in nurse
	private String nurseUsername;
	
	//Homepage that will pop up once the nurse signs in
	public NursesHomepage(String nurseUsername) {
		
		this.nurseUsername = nurseUsername;
		
		//logo HBox for the company logo
        HBox logo = new HBox();
        logo.setPadding(new Insets(15, 12, 15, 12));
        logo.setSpacing(10);
        logo.setAlignment(Pos.CENTER_LEFT);
        logo.setStyle("-fx-background-color: white;");//background color for logo background

        //Load the logo image, throw an error if image file not found
        File logoFile = new File(LITTLESTEPS_LOGO);
        if (!logoFile.exists()) {
        	Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Logo file not found at " + LITTLESTEPS_LOGO);
            errorAlert.showAndWait();
        } else {
            Image logoImage = new Image(logoFile.toURI().toString());
            ImageView logoView = new ImageView(logoImage);
            logoView.setFitHeight(50); //Set logo height
            logoView.setPreserveRatio(true);
            logo.getChildren().add(logoView); //Add the logoView to the logo hbox
        }
        
        //create an array of all the nurse files in the Nurse directory
        String nurse = "Nurse " + nurseUsername.substring(5, nurseUsername.length());//Adds space after the word Nurse
		
        //Set up the top menu bar of the homepage
        HBox menuBar = new HBox();
        menuBar.setPadding(new Insets(15, 12, 15, 12));
        menuBar.setSpacing(10);
        menuBar.setStyle("-fx-background-color: #3c3c3c;");//grey color for the menu bar background color

        //Welcome message for the specific nurse
        Text welcomeText = new Text("Welcome " + nurse + "!");
        welcomeText.setStyle("-fx-font-size: 16px; -fx-fill: white;");//text color and size welcome section

        //Spacer to push buttons to the right of the menu bar
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        //Buttons for the top menu bar
        Button messagesButton = new Button("Messages");
        //Button profileButton = new Button("Profile");
        Button logoutButton = new Button("Log Out");

        //Placeholder for message button action
        messagesButton.setOnAction(event -> {
        	/*
        	 * 
    		 * *********************************************************
    		 * 		      Code from Kaden Madson goes here       	   *
    		 * *********************************************************
    		 * 
    		 *
             */
        });
        
        //Placeholder for logout button action
        logoutButton.setOnAction(event -> {	
        	this.close();//closes the stage for the Nurse's homepage
        });

        //Add all buttons to menu bar
        menuBar.getChildren().addAll(welcomeText, spacer, messagesButton, logoutButton);
        
        Button checkInPatientBttn = new Button("Check-In Patient for Appointment");
        checkInPatientBttn.setOnAction(event -> {
        	//save filled map with patient into that was entered into the pop up search form
            Map<String, String> patientInfo = showPatientSearchForm();

            //create the directory name in the format firstname_lastnameDOBdigit 
            PatientFileManager manager = new PatientFileManager();
            LocalDate dob = LocalDate.parse(patientInfo.get("dob"), DateTimeFormatter.ofPattern("MMddyy"));
            //get the path to the patient directory
            Path patientDirPath = manager.findPatientDirectory(patientInfo.get("firstName"), patientInfo.get("lastName"), dob);
            
            if(patientDirPath != null && Files.exists(patientDirPath)) {
            	//pop up with the vitals form to fill into the patients directory
            	//it will also move the directory into a seperate checked_in directory once the vitals text file is created
            	new VitalsForm(patientDirPath).showAndWait();//wait until form is closed to move forward
            } else { //show error
            	new Alert(Alert.AlertType.ERROR, "Patient was not found that matched the entered info.").showAndWait();
            }
        });//end of action
        
        //Button to add new patient
        Button addNewPatientBttn = new Button("Add New Patient");
        addNewPatientBttn.setOnAction(event -> {
        	//Will show a form for the nurse to fill in with patient info
        	new AddNewPatientForm();
        });
        
        //Main layout
        VBox mainLayout = new VBox();
        mainLayout.setAlignment(Pos.TOP_CENTER);
        
        //HBox for the check-in button to center it
        HBox checkInBttnBox = new HBox();
        checkInBttnBox.setAlignment(Pos.CENTER);
        checkInBttnBox.getChildren().add(checkInPatientBttn);
        checkInBttnBox.setPadding(new Insets(10, 0, 10, 0));

        //HBox for the add patient button to center it
        HBox addPatientBttnBox = new HBox();
        addPatientBttnBox.setAlignment(Pos.CENTER);
        addPatientBttnBox.getChildren().add(addNewPatientBttn);
        addPatientBttnBox.setPadding(new Insets(0, 0, 10, 0));
        
        //Add the logo, menu bar, and the two new HBoxes to the main VBox
        mainLayout.getChildren().addAll(logo, menuBar, checkInBttnBox, addPatientBttnBox);

        //Set the scene for homepage
        Scene scene = new Scene(mainLayout, 800, 600);
        this.setScene(scene);
        this.setTitle("Little Steps Pediatrics");
		
	}
	
	//method that will show a pop up window in which to search up a patients directory
	private Map<String, String> showPatientSearchForm() {
	    //Create a new stage for the pop up window
	    final Stage searchWindow = new Stage();
	    searchWindow.setTitle("Search Patient");
	    
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
	    
	    //Button for submission to search for file
	    Button submitButton = new Button("Submit");
	    submitButton.setOnAction(e -> {
	        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || dobField.getText().isEmpty()) {
	            new Alert(Alert.AlertType.ERROR, "All fields must be filled.").showAndWait();
	        } else {
	        	searchWindow.close(); //Close the dialog on successful submission
	        }
	    });

	    searchVbox.getChildren().addAll(new Label("Enter Patient Details:"), firstNameField, lastNameField, dobField, submitButton);
	    
	    Scene dialogScene = new Scene(searchVbox, 300, 250);
	    searchWindow.setScene(dialogScene);
	    searchWindow.showAndWait();

	    //Return patient info as a map
	    Map<String, String> patientInfo = new HashMap<>();
	    patientInfo.put("firstName", firstNameField.getText());
	    patientInfo.put("lastName", lastNameField.getText());
	    patientInfo.put("dob", dobField.getText());
	    return patientInfo;
	}//end of showPatientSearchForm
}//end of NurseHomepage



