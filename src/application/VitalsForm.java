package application;

import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VitalsForm extends Stage {
	private static final String CHECKED_IN_DIRECTORY = System.getProperty("user.home") + "/Documents/patient_data/checked_in";
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
            //Save vitals data
        	saveVitalsData(vitalsData);
        	
        	//move the patient to 'checked_In' directory once vitals text file is created and stored in patients directory
        	if (this.fileSavedSuccessfully) {
                movePatientDirectory();
            }
        	
        	this.close();
        });

        formLayout.getChildren().addAll(
                tempLabel, tempField,
                pulseLabel, pulseField,
                bloodPressureLabel, bloodPressureField,
                weightLabel, weightField,
                heightLabel, heightField,
                bmiLabel, bmiField,
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
    	
        //vitalsData is a String containing the vitals to be saved
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddyy");
        LocalDate localDate = LocalDate.now();
        String fileName = "vitals" + dtf.format(localDate) + ".txt";
        
        //create text file and store in patients directory
        try {
            Files.write(patientDirectoryPath.resolve(fileName), vitalsData.getBytes());//save vitals text file
            fileSavedSuccessfully = true;//change flag to true when file created
            new Alert(Alert.AlertType.ERROR, "Vitals data file saved succesfully to " + patientDirectoryPath.toString()).showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            if(fileSavedSuccessfully == false)
            	new Alert(Alert.AlertType.ERROR, "Failed to save vitals data.").showAndWait();
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