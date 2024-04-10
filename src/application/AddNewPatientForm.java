package application;

import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import java.time.LocalDate;

public class AddNewPatientForm extends Stage {
    
    public AddNewPatientForm() {
    	//form setup for the patient info form
        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(15, 15, 15, 15));
        

        //Form fields
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Enter patients first name");
        
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Enter patients last name");
        
        Label dobLabel = new Label("Date of Birth:");
        DatePicker dobPicker = new DatePicker();
        dobPicker.setPromptText("Select patients date of birth (must be at least 12 years old)");
        
        Label emailLabel = new Label("Parent's Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter the parent or legal guardians email");
        
        Label healthIssuesLabel = new Label("Health Issues:");
        TextArea healthIssuesArea = new TextArea();
        healthIssuesArea.setPromptText("Enter patients health issues");
        
        Label medicationsLabel = new Label("Previously Prescribed Medications:");
        TextArea medicationsArea = new TextArea();
        medicationsArea.setPromptText("Enter patients prescribed medications");
        
        Label pharmacyLabel = new Label("Preferred Pharmacy:");
        TextField pharmacyField = new TextField();
        pharmacyField.setPromptText("Enter patients preferred pharmacy");
        
        //Allows nurse to select date and time of appointment
        Label appointmentLabel = new Label("Appointment Date and Time:");
        DatePicker appointmentDatePicker = new DatePicker();
        appointmentDatePicker.setPromptText("Select appointment date");
        ComboBox<String> timePicker = new ComboBox<>();
        timePicker.setPromptText("Select appointment time");
        timePicker.getItems().addAll(
        		"08:00 AM", "09:00 AM", 
        		"10:00 AM", "11:00 AM", 
        		"12:00 PM", "01:00 PM", 
        		"02:00 PM", "03:00 PM", 
        		"04:00 PM", "05:00 PM");
        
        Label notesLabel = new Label("Notes:");
        TextArea notesArea = new TextArea();
        notesArea.setPromptText("Enter any pertinent information");
        
        Label immunizationLabel = new Label("History of Immunization:");
        TextArea immunizationArea = new TextArea();
        immunizationArea.setPromptText("Enter the patients immunization history");

        //Save the patients information and create the patient directory then close the form after confirmation message
        Button saveInfoButton = new Button("Save");
        saveInfoButton.setOnAction(event -> {
        	//Collect input information from the form
        	String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            LocalDate dob = dobPicker.getValue();
            String email = emailField.getText();
            String healthIssues = healthIssuesArea.getText();
            String medications = medicationsArea.getText();
            String pharmacy = pharmacyField.getText();
            LocalDate appointmentDate = appointmentDatePicker.getValue();
            String appointmentTime = timePicker.getSelectionModel().getSelectedItem(); // For ComboBox timePicker
            String notes = notesArea.getText();
            String immunizationHistory = immunizationArea.getText();

            //check that required contact information fields are filled
            if (firstName.isEmpty() || lastName.isEmpty() || dob == null || email.isEmpty()) {
                //Show error message if there are empty  contact info fields
                new Alert(Alert.AlertType.ERROR, "Please fill in all required fields.").showAndWait();
            } else {
            	try {
                    //Construct appointment string to pass to the patientFileManager 
                    String appointment = (appointmentDate != null ? appointmentDate.toString() : "") +
                                         " " + (appointmentTime != null ? appointmentTime : "");

                    //Create instance and pass the collected data from the form
                    PatientFileManager newPatient = new PatientFileManager();
                    newPatient.createPatientDirectory(firstName, lastName, dob, email, healthIssues, medications, pharmacy, appointment, notes, immunizationHistory);

                    //If no exceptions thrown, show a success message
                    //Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Patient profile created successfully.");
                    //successAlert.setHeaderText("Success");
                    //successAlert.showAndWait();

                    //Close the form
                    this.close();
                } catch (Exception e) {
                    //If there's an exception, show an error message
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error encountered when creating patient profile: " + e.getMessage());
                    errorAlert.setHeaderText("Error");
                    errorAlert.showAndWait();
                }
            }
        });//end of action event for save button

        // Add all fields and the button to the form layout
        formLayout.getChildren().addAll(
                firstNameLabel, firstNameField, 
                lastNameLabel, lastNameField,
                dobLabel, dobPicker,
                emailLabel, emailField,
                healthIssuesLabel, healthIssuesArea,
                medicationsLabel, medicationsArea,
                pharmacyLabel, pharmacyField,
                appointmentLabel, appointmentDatePicker, timePicker,
                notesLabel, notesArea,
                immunizationLabel, immunizationArea,
                saveInfoButton
            );
        
        //Put formLayout in a ScrollPane to allow nurse to scroll through form
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(formLayout); // Set the VBox as the content of the scroll pane
        scrollPane.setFitToWidth(true); // Ensures the content width is bounded by the width of the ScrollPane
        //Show vertical scrollbar as needed
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        
        //Set scene and show stage
        Scene scene = new Scene(scrollPane, 600, 600); //(width, height)
        this.setScene(scene);
        this.setTitle("Add New Patient");
        this.show();
    }

}