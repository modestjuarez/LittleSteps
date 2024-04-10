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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MedicalRecordsPage extends VBox {		
	DoctorView doctorViewStage;
	
	HBox upperMedicalRecordsPageHBox;
	FileInputStream inputStream;
	Image logoImage;
	ImageView logoImageView;
	Label patientMedicalRecordsLabel;
	TextArea patientMedicalRecordsTextArea;
	
	HBox mainMedicalRecordsPageHBox;
	
	VBox leftMedicalRecordsPageVBox;
	Label previousHealthProblemsLabel;
	TextArea previousHealthProblemsTextArea;
	
	VBox centerMedicalRecordsPageVBox;
	VBox centerUpperMedicalRecordsPageVBox;
	VBox centerLowerMedicalRecordsPageVBox;
	Label previousMedicationsLabel;
	TextArea previousMedicationsTextArea;	
	Label notesAndRecommendationsLabel;
	TextArea notesAndRecommendationsTextArea;
	
	VBox rightMedicalRecordsPageVBox;
	Label immunizationHistoryLabel;
	TextArea immunizationHistoryTextArea;
	
	VBox patientInformationButtonVBox;
	Button patientInformationButton;
	
	public MedicalRecordsPage(DoctorView stage, String doctorTextFile) {
		doctorViewStage = stage;
		
		/* START OF UPPER HBOX UI */
		
		try {
			inputStream = new FileInputStream(System.getProperty("user.home") + "/Documents/Logo/logoName.jpeg");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} 
		
		logoImage = new Image(inputStream);
		
		logoImageView = new ImageView(logoImage);
		logoImageView.setFitHeight(100); 
		logoImageView.setFitWidth(100); 
		logoImageView.setPreserveRatio(true);  

		patientMedicalRecordsLabel = new Label("Patient Medical Records");
		patientMedicalRecordsLabel.setTextFill(Color.BLACK);
		patientMedicalRecordsLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 18));
		patientMedicalRecordsLabel.setPadding(new Insets(0, 50, 0, 150)); 
		
		patientMedicalRecordsTextArea = new TextArea();
		patientMedicalRecordsTextArea.setMaxHeight(100);
		patientMedicalRecordsTextArea.setMaxWidth(200);
		patientMedicalRecordsTextArea.setEditable(false);
		patientMedicalRecordsTextArea.setMouseTransparent(true);
		patientMedicalRecordsTextArea.setFocusTraversable(false);
		
		upperMedicalRecordsPageHBox = new HBox();
		
		upperMedicalRecordsPageHBox.getChildren().add(logoImageView);
		
		upperMedicalRecordsPageHBox.getChildren().add(patientMedicalRecordsLabel);
		patientMedicalRecordsLabel.setAlignment(Pos.CENTER);
		
		upperMedicalRecordsPageHBox.getChildren().add(patientMedicalRecordsTextArea);
		
		upperMedicalRecordsPageHBox.setPadding(new Insets(0, 0, 40, 0));
		// upperMedicalRecordsPageHBox.setSpacing(100);
		
		/* END OF UPPER HBOX UI */

	/* START OF MAIN HBOX UI */

		/* START OF LEFT VBOX UI */
		
		previousHealthProblemsLabel = new Label("Previous Health Problems");
		previousHealthProblemsLabel.setTextFill(Color.BLACK);
		previousHealthProblemsLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 14));
		
		previousHealthProblemsTextArea = new TextArea();
		previousHealthProblemsTextArea.setMaxHeight(100);
		previousHealthProblemsTextArea.setMaxWidth(200);
		previousHealthProblemsTextArea.setEditable(false);
		previousHealthProblemsTextArea.setMouseTransparent(true);
		previousHealthProblemsTextArea.setFocusTraversable(false);
		
		leftMedicalRecordsPageVBox = new VBox();
		
		leftMedicalRecordsPageVBox.getChildren().add(previousHealthProblemsLabel);
		previousHealthProblemsLabel.setAlignment(Pos.CENTER);
		
		leftMedicalRecordsPageVBox.getChildren().add(previousHealthProblemsTextArea);
		
		leftMedicalRecordsPageVBox.setSpacing(10);
		
		/* END OF LEFT VBOX UI */
		
		/* START OF CENTER VBOX UI */
		
		previousMedicationsLabel = new Label("Previous Medications");
		previousMedicationsLabel.setTextFill(Color.BLACK);
		previousMedicationsLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 14));
		
		previousMedicationsTextArea = new TextArea();
		previousMedicationsTextArea.setMaxHeight(100);
		previousMedicationsTextArea.setMaxWidth(200);
		previousMedicationsTextArea.setEditable(false);
		previousMedicationsTextArea.setMouseTransparent(true);
		previousMedicationsTextArea.setFocusTraversable(false);
		
		notesAndRecommendationsLabel = new Label("Notes and Recommendations");
		notesAndRecommendationsLabel.setTextFill(Color.BLACK);
		notesAndRecommendationsLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 14));
		
		notesAndRecommendationsTextArea = new TextArea();
		notesAndRecommendationsTextArea.setMaxHeight(100);
		notesAndRecommendationsTextArea.setMaxWidth(200);
		notesAndRecommendationsTextArea.setEditable(false);
		notesAndRecommendationsTextArea.setMouseTransparent(true);
		notesAndRecommendationsTextArea.setFocusTraversable(false);
		
		centerUpperMedicalRecordsPageVBox = new VBox();
		
		centerUpperMedicalRecordsPageVBox.getChildren().add(previousMedicationsLabel);
		previousMedicationsLabel.setAlignment(Pos.CENTER);
		
		centerUpperMedicalRecordsPageVBox.getChildren().add(previousMedicationsTextArea);	
		
		centerUpperMedicalRecordsPageVBox.setPadding(new Insets(0, 0, 40, 0));
		
		centerUpperMedicalRecordsPageVBox.setSpacing(10);

		centerLowerMedicalRecordsPageVBox = new VBox();

		centerLowerMedicalRecordsPageVBox.getChildren().add(notesAndRecommendationsLabel);
		notesAndRecommendationsLabel.setAlignment(Pos.CENTER);
		
		centerLowerMedicalRecordsPageVBox.getChildren().add(notesAndRecommendationsTextArea);
		
		centerLowerMedicalRecordsPageVBox.setPadding(new Insets(40, 0, 0, 0));
		
		centerLowerMedicalRecordsPageVBox.setSpacing(10);
		
		centerMedicalRecordsPageVBox = new VBox();
		
		centerMedicalRecordsPageVBox.getChildren().add(centerUpperMedicalRecordsPageVBox);
		centerUpperMedicalRecordsPageVBox.setAlignment(Pos.CENTER);
				
		centerMedicalRecordsPageVBox.getChildren().add(centerLowerMedicalRecordsPageVBox);
		centerLowerMedicalRecordsPageVBox.setAlignment(Pos.CENTER);
		
		centerMedicalRecordsPageVBox.setPadding(new Insets(0, 40, 0, 40));
				
		/* END OF CENTER VBOX UI */
		
		/* START OF RIGHT VBOX UI */
		
		immunizationHistoryLabel = new Label("Immunization History");
		immunizationHistoryLabel.setTextFill(Color.BLACK);
		immunizationHistoryLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 14));
		
		immunizationHistoryTextArea = new TextArea();
		immunizationHistoryTextArea.setMaxHeight(100);
		immunizationHistoryTextArea.setMaxWidth(200);
		immunizationHistoryTextArea.setEditable(false);
		immunizationHistoryTextArea.setMouseTransparent(true);
		immunizationHistoryTextArea.setFocusTraversable(false);
		
		rightMedicalRecordsPageVBox = new VBox();
		
		rightMedicalRecordsPageVBox.getChildren().add(immunizationHistoryLabel);
		immunizationHistoryLabel.setAlignment(Pos.CENTER);
		
		rightMedicalRecordsPageVBox.getChildren().add(immunizationHistoryTextArea);
		
		rightMedicalRecordsPageVBox.setSpacing(10);

		/* END OF RIGHT VBOX UI */
		
		mainMedicalRecordsPageHBox = new HBox();
		
		mainMedicalRecordsPageHBox.getChildren().add(leftMedicalRecordsPageVBox);
		leftMedicalRecordsPageVBox.setAlignment(Pos.CENTER);
		
		mainMedicalRecordsPageHBox.getChildren().add(centerMedicalRecordsPageVBox);
		centerMedicalRecordsPageVBox.setAlignment(Pos.CENTER);
		
		mainMedicalRecordsPageHBox.getChildren().add(rightMedicalRecordsPageVBox);
		rightMedicalRecordsPageVBox.setAlignment(Pos.CENTER);
		
		mainMedicalRecordsPageHBox.setPadding(new Insets(0, 0, 40, 0));

		
	/* END OF MAIN HBOX UI */

		/* START OF BUTTON VBOX UI */
		
		patientInformationButton = new Button("Patient Information");
		patientInformationButton.setFont(Font.font("Verdana", FontWeight.MEDIUM, 14));
		patientInformationButton.setMaxHeight(30); 
		patientInformationButton.setMaxWidth(200);
		patientInformationButton.setStyle("-fx-text-fill: white; -fx-background-color: cornflowerblue;");
		
		patientInformationButtonVBox = new VBox();
		
		patientInformationButtonVBox.getChildren().add(patientInformationButton);
		patientInformationButton.setAlignment(Pos.CENTER);
		
		/* START OF BUTTON VBOX UI */

		this.getChildren().add(upperMedicalRecordsPageHBox);
		upperMedicalRecordsPageHBox.setAlignment(Pos.CENTER);
		
		this.getChildren().add(mainMedicalRecordsPageHBox);
		mainMedicalRecordsPageHBox.setAlignment(Pos.CENTER);
		
		this.getChildren().add(patientInformationButtonVBox);
		patientInformationButtonVBox.setAlignment(Pos.CENTER);

		patientInformationButton.setOnAction(new EventHandler<>() {
	        public void handle(ActionEvent event)
	        {
	        	stage.goToDoctorHomePage();
	        }
	    });
	}
}
