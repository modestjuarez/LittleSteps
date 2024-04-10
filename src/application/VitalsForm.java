package application;

import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VitalsForm extends Stage {
	//paths to the different directories needed in this file
	private static final String CHECKED_IN_DIRECTORY = System.getProperty("user.home") + "/Documents/patient_data/checked_in";
	private static final String DOCTORS_DIRECTORY = System.getProperty("user.home") + "/Documents/doctors_data";
	
	//Patient directory path that will store the vitals info for the patients directory
	private Path patientDirectoryPath;
	
	//flag to confirm if vitals file was created
	private boolean fileSavedSuccessfully = false;
    
    public VitalsForm(Path patientDirectoryPath) {
    	
    	this.patientDirectoryPath = patientDirectoryPath;
    	
        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(15, 15, 15, 15));
        
        //All vitals fields
        Label tempLabel = new Label("Temperature (°F):");
        TextField tempField = new TextField();
        tempField.setPromptText("Enter patient's temperature");

        Label pulseLabel = new Label("Pulse (bpm):");
        TextField pulseField = new TextField();
        pulseField.setPromptText("Enter patient's pulse rate");

        Label bloodPressureLabel = new Label("Blood Pressure:");
        TextField bloodPressureField = new TextField();
        bloodPressureField.setPromptText("Enter blood pressure");

        Label weightLabel = new Label("Weight (lbs):");
        TextField weightField = new TextField();
        weightField.setPromptText("Enter patient's weight");

        Label heightLabel = new Label("Height (ft):");
        TextField heightField = new TextField();
        heightField.setPromptText("Enter patient's height");

        Label bmiLabel = new Label("BMI:");
        TextField bmiField = new TextField();
        bmiField.setPromptText("Enter patient's BMI");
        
        Label doctorLabel = new Label("Doctor:");
        TextField doctorField = new TextField();
        doctorField.setPromptText("Enter the doctors name the the patient will see today");
        
        //event filter to remove spaces from text that was entered
        doctorField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        });
        
        //Save button to save info entered
        Button saveVitalsBttn = new Button("Save Vitals");
        saveVitalsBttn.setOnAction(event -> {
        	//Concatonate all the vitals into one string variable to pass to saveVitalsButton method
            String vitalsData = String.join("\n",
        	        tempField.getText(),
        	        pulseField.getText(),
        	        bloodPressureField.getText(),
        	        weightField.getText(),
        	        heightField.getText(),
        	        bmiField.getText()
        	        );
            //Save vitals data and create doctors appointment file
        	saveVitalsData(vitalsData);
        	createDoctorsApptFile(doctorField.getText());
        	
        	//move the patient to 'checked_In' directory once vitals text file is created and stored in patients directory
        	if (this.fileSavedSuccessfully) {
                movePatientDirectory();
            }
        	
        	this.close();
        });

        //add all labels and fields to VBox
        formLayout.getChildren().addAll(
                tempLabel, tempField,
                pulseLabel, pulseField,
                bloodPressureLabel, bloodPressureField,
                weightLabel, weightField,
                heightLabel, heightField,
                bmiLabel, bmiField, 
                doctorLabel, doctorField,
                saveVitalsBttn
        );

        //Allow nurse to scroll in order to fill out all fields
        ScrollPane scrollPane = new ScrollPane(formLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Scene scene = new Scene(scrollPane, 400, 600);
        this.setScene(scene);
        this.setTitle("Enter Vitals");
        this.show();
    }
    
    //method to save the vitals data text file inside the patients directory
    private void saveVitalsData(String vitalsData) {
    	
        //Code to format the way the text file will be saved: vitalsMMddyy.txt
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddyy");
        LocalDate localDate = LocalDate.now();
        String fileName = "vitals" + dtf.format(localDate) + ".txt";
        
        //create text file and store in patients directory
        try {
        	//vitalsData is a String containing the vitals to be saved
            Files.write(patientDirectoryPath.resolve(fileName), vitalsData.getBytes());//save vitals text file
            fileSavedSuccessfully = true;//change flag to true when file created
            new Alert(Alert.AlertType.ERROR, "Vitals data file saved succesfully to " + patientDirectoryPath.toString()).showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            if(fileSavedSuccessfully == false)
            	new Alert(Alert.AlertType.ERROR, "Failed to save vitals data.").showAndWait();
        }
    }
    
    //method to create a doctormmdd_hhmm.txt file in the /doctors directory. 
    //This will allow the doctor to see what appointments (s)he has that day and what patients he's seeing and when
    private void createDoctorsApptFile(String doctorsName) {
    	//make the text all lowercase
    	String readyName = doctorsName.toLowerCase();
    	
    	//create a path to the doctors directory
    	Path specificDoctorsDirPath = Paths.get(DOCTORS_DIRECTORY, readyName);
    	
    	//save file into the doctors directory and create the directory if it doesn't exist
        try {
        	//directory doesn't exist for doctor, create it here
            if (!Files.exists(specificDoctorsDirPath)) {
                Files.createDirectories(specificDoctorsDirPath);
            }
            
            //setup the way the file will be saved (doctorMMyy_HHmm.txt)
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMyy_HHmm");
            LocalDateTime now = LocalDateTime.now();
            String fileName = dtf.format(now) + ".txt";
            
            //Prepare the content to be saved: the path to the patient directory
            String contentToSave = patientDirectoryPath.toString();
            
            //Save the doctor's name into file in the doctors_data directory
            Files.write(specificDoctorsDirPath.resolve(fileName), contentToSave.getBytes());
            
            //Display success message to run once the saving function is done running
            Platform.runLater(() -> new Alert(Alert.AlertType.INFORMATION, "Doctor's appointment info saved successfully.").showAndWait());
        } catch (IOException e) {
            e.printStackTrace();
            //Display error if the file could not be saved
            Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Failed to save doctor's appointment info: " + e.getMessage()).showAndWait());
        }
    }
    
    //method to move the patients directory into the checked_in folder
    private void movePatientDirectory() {
    	//create the path that will be used to move to folder
        Path checkedInPath = Paths.get(CHECKED_IN_DIRECTORY);
        Path targetPath = checkedInPath.resolve(patientDirectoryPath.getFileName());

        try {
            //Verify that the checked_in directory exists or create it if it doesn't
            if (!Files.exists(checkedInPath)) {
                Files.createDirectories(checkedInPath);
            }

            //Check if the patients directory already exists in the checked_in directory
            //Platform.runLater() executes the message once the form closes. I used it to prevent NotOnFxApplicationThreadException error
            if (Files.exists(targetPath)) {
                Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "A directory with the same name already exists in the checked_in folder.").showAndWait());
            } else {
                Files.move(patientDirectoryPath, targetPath);
                Platform.runLater(() -> new Alert(Alert.AlertType.INFORMATION, "Patient directory moved successfully.").showAndWait());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Failed to move patient directory: " + e.getMessage()).showAndWait());
        }
    }
    
}
