package Homework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DoctorHomePage extends VBox {
	DoctorView doctorViewStage;
	
	VBox patientInformationLabelVBox;
	Label patientInformationEntryLabel;
	TextField patientInformationEntryTextField;
	Button patientInformationEntryButton;
	Label patientInformationLabel;
	Label patientInformationErrorLabel;
	
	HBox mainDoctorHomePageHBox;
	
	VBox leftDoctorHomePageVBox;
	Label patientFindingsLabel;
	TextArea patientFindingsEntryTextArea;
	Button patientFindingsAddButton;
	TextArea patientFindingsDisplayTextArea;
	Button patientFindingsSaveButton;
	
	VBox centerDoctorHomePageVBox;
	FileInputStream inputStream;
	Image logoImage;
	ImageView logoImageView;
	Button patientMedicalRecordsButton;
	
	VBox rightDoctorHomePageVBox;
	Label patientMedicationsLabel;
	TextArea patientMedicationsEntryTextArea;
	Button patientMedicationsAddButton;
	TextArea patientMedicationsDisplayTextArea;
	Button patientMedicationsSaveButton;
		
	public DoctorHomePage(DoctorView stage, String doctorTextFile) {
		doctorViewStage = stage;
		
		/* START OF LABEL VBOX UI */
		
		patientInformationEntryLabel = new Label("Enter the patient's full name below");
		patientInformationEntryLabel.setTextFill(Color.BLACK);
		patientInformationEntryLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 18));
		
		patientInformationEntryTextField = new TextField();
		patientInformationEntryTextField.setMaxWidth(300);
		patientInformationEntryTextField.setFont(Font.font("Verdana", 14));

		patientInformationEntryButton = new Button("Search for patient's information");
		patientInformationEntryButton.setFont(Font.font("Verdana", FontWeight.MEDIUM, 14));
		patientInformationEntryButton.setMaxHeight(30); 
		patientInformationEntryButton.setMaxWidth(250);
		patientInformationEntryButton.setStyle("-fx-text-fill: white; -fx-background-color: cornflowerblue;");
		
		patientInformationLabel = new Label("Patient Name Information");
		patientInformationLabel.setTextFill(Color.BLACK);
		patientInformationLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 24));
		
		patientInformationErrorLabel = new Label("Error: Patient's information does not exist");
		patientInformationErrorLabel.setTextFill(Color.RED);
		patientInformationErrorLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		
		patientInformationLabelVBox = new VBox();
		
		patientInformationLabelVBox.getChildren().add(patientInformationEntryLabel);
		patientInformationLabel.setAlignment(Pos.CENTER);
		
		patientInformationLabelVBox.getChildren().add(patientInformationEntryTextField);
		patientInformationEntryTextField.setAlignment(Pos.CENTER);
		
		patientInformationLabelVBox.getChildren().add(patientInformationEntryButton);
		patientInformationEntryButton.setAlignment(Pos.CENTER);
		
		// patientInformationLabelVBox.getChildren().add(patientInformationErrorLabel);
		// patientInformationErrorLabel.setAlignment(Pos.CENTER);
		
		patientInformationLabelVBox.setPadding(new Insets(0, 0, 20, 0));
		
		patientInformationLabelVBox.setSpacing(15);

		/* END OF LABEL VBOX UI */
		
	/* START OF MAIN HBOX UI */

		/* START OF LEFT VBOX UI */

		patientFindingsLabel = new Label("Patient Findings");
		patientFindingsLabel.setTextFill(Color.BLACK);
		patientFindingsLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 18));
				
		patientFindingsEntryTextArea = new TextArea();
		patientFindingsEntryTextArea.setMaxHeight(100);
		patientFindingsEntryTextArea.setMaxWidth(200);
		patientFindingsEntryTextArea.setFont(Font.font("Verdana", 14));
		
		patientFindingsAddButton = new Button("Add Patient Findings");
		patientFindingsAddButton.setFont(Font.font("Verdana", FontWeight.MEDIUM, 14));
		patientFindingsAddButton.setMaxHeight(30); 
		patientFindingsAddButton.setMaxWidth(175);
		patientFindingsAddButton.setStyle("-fx-text-fill: white; -fx-background-color: cornflowerblue;");
		
		patientFindingsDisplayTextArea = new TextArea();
		patientFindingsDisplayTextArea.setMaxHeight(100);
		patientFindingsDisplayTextArea.setMaxWidth(200);
		patientFindingsDisplayTextArea.setFont(Font.font("Verdana", 14));
		patientFindingsDisplayTextArea.setEditable(false);
		patientFindingsDisplayTextArea.setMouseTransparent(true);
		patientFindingsDisplayTextArea.setFocusTraversable(false);
		
		patientFindingsSaveButton = new Button("Save Patient Findings");
		patientFindingsSaveButton.setFont(Font.font("Verdana", FontWeight.MEDIUM, 14));
		patientFindingsSaveButton.setMaxHeight(30); 
		patientFindingsSaveButton.setMaxWidth(175);
	
		leftDoctorHomePageVBox = new VBox();
		
		leftDoctorHomePageVBox.getChildren().add(patientFindingsLabel);
		patientFindingsLabel.setAlignment(Pos.CENTER);
		
		leftDoctorHomePageVBox.getChildren().add(patientFindingsEntryTextArea);
		
		leftDoctorHomePageVBox.getChildren().add(patientFindingsAddButton);
		patientFindingsAddButton.setAlignment(Pos.CENTER);
		
		leftDoctorHomePageVBox.getChildren().add(patientFindingsDisplayTextArea);
		
		leftDoctorHomePageVBox.getChildren().add(patientFindingsSaveButton);
		patientFindingsSaveButton.setAlignment(Pos.CENTER);
		
		leftDoctorHomePageVBox.setPadding(new Insets(20, 20, 20, 20));
		leftDoctorHomePageVBox.setSpacing(20);
	
		/* END OF LEFT VBOX UI */
		
		/* START OF CENTER VBOX UI */
				
		try {
			inputStream = new FileInputStream(System.getProperty("user.home") + "/Documents/Logo/logoName.jpeg");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} 
		
		logoImage = new Image(inputStream);
		
		logoImageView = new ImageView(logoImage);
		logoImageView.setFitHeight(150); 
		logoImageView.setFitWidth(150); 
		logoImageView.setPreserveRatio(true);  
		
		patientMedicalRecordsButton = new Button("Patient Medical Records");
		patientMedicalRecordsButton.setFont(Font.font("Verdana", FontWeight.MEDIUM, 14));
		patientMedicalRecordsButton.setMaxHeight(30); 
		patientMedicalRecordsButton.setMaxWidth(200);
		patientMedicalRecordsButton.setStyle("-fx-text-fill: white; -fx-background-color: cornflowerblue;");
		
		centerDoctorHomePageVBox = new VBox();

		centerDoctorHomePageVBox.getChildren().add(logoImageView);

		centerDoctorHomePageVBox.getChildren().add(patientMedicalRecordsButton);
		patientMedicalRecordsButton.setAlignment(Pos.CENTER);
		
		centerDoctorHomePageVBox.setPadding(new Insets(20, 20, 20, 20)); 

		centerDoctorHomePageVBox.setSpacing(30);

		/* END OF CENTER VBOX UI */
		
		/* START OF RIGHT VBOX UI */

		patientMedicationsLabel = new Label("Patient Medications");
		patientMedicationsLabel.setTextFill(Color.BLACK);
		patientMedicationsLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 18));
		
		patientMedicationsEntryTextArea = new TextArea();
		patientMedicationsEntryTextArea.setMaxHeight(100);
		patientMedicationsEntryTextArea.setMaxWidth(200);
		patientMedicationsEntryTextArea.setFont(Font.font("Verdana", 14));
		
		patientMedicationsAddButton = new Button("Add Patient Medications");
		patientMedicationsAddButton.setFont(Font.font("Verdana", FontWeight.MEDIUM, 14));
		patientMedicationsAddButton.setMaxHeight(30); 
		patientMedicationsAddButton.setMaxWidth(200);
		patientMedicationsAddButton.setStyle("-fx-text-fill: white; -fx-background-color: cornflowerblue;");
		
		patientMedicationsDisplayTextArea = new TextArea();
		patientMedicationsDisplayTextArea.setMaxHeight(100);
		patientMedicationsDisplayTextArea.setMaxWidth(200);
		patientMedicationsDisplayTextArea.setFont(Font.font("Verdana", 14));
		patientMedicationsDisplayTextArea.setEditable(false);
		patientMedicationsDisplayTextArea.setMouseTransparent(true);
		patientMedicationsDisplayTextArea.setFocusTraversable(false);
		
		patientMedicationsSaveButton = new Button("Save Patient Medications");
		patientMedicationsSaveButton.setFont(Font.font("Verdana", FontWeight.MEDIUM, 14));
		patientMedicationsSaveButton.setMaxHeight(30); 
		patientMedicationsSaveButton.setMaxWidth(200);
		
		rightDoctorHomePageVBox = new VBox();
		
		rightDoctorHomePageVBox.getChildren().add(patientMedicationsLabel);
		patientMedicationsLabel.setAlignment(Pos.CENTER);
		
		rightDoctorHomePageVBox.getChildren().add(patientMedicationsEntryTextArea);
		
		rightDoctorHomePageVBox.getChildren().add(patientMedicationsAddButton);
		patientMedicationsAddButton.setAlignment(Pos.CENTER);
		
		rightDoctorHomePageVBox.getChildren().add(patientMedicationsDisplayTextArea);
		
		rightDoctorHomePageVBox.getChildren().add(patientMedicationsSaveButton);
		patientMedicationsSaveButton.setAlignment(Pos.CENTER);

		rightDoctorHomePageVBox.setPadding(new Insets(20, 20, 20, 20)); 
		
		rightDoctorHomePageVBox.setSpacing(20);

		/* END OF RIGHT VBOX UI */

		mainDoctorHomePageHBox = new HBox();
		
		mainDoctorHomePageHBox.getChildren().add(leftDoctorHomePageVBox);
		leftDoctorHomePageVBox.setAlignment(Pos.CENTER);
		
		mainDoctorHomePageHBox.getChildren().add(centerDoctorHomePageVBox);
		centerDoctorHomePageVBox.setAlignment(Pos.CENTER);
		
		mainDoctorHomePageHBox.getChildren().add(rightDoctorHomePageVBox);
		rightDoctorHomePageVBox.setAlignment(Pos.CENTER);
		
	/* END OF MAIN HBOX UI */

		this.getChildren().add(patientInformationLabelVBox);
		patientInformationLabelVBox.setAlignment(Pos.CENTER);

		this.getChildren().add(mainDoctorHomePageHBox);
		mainDoctorHomePageHBox.setAlignment(Pos.CENTER);
		
		patientMedicalRecordsButton.setOnAction(new EventHandler<>() {
	        public void handle(ActionEvent event)
	        {
	        	stage.goToPatientMedicalRecordsPage();
	        }
	    });
	}
}
