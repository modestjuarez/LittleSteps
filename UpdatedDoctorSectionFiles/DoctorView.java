package application;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DoctorView extends Stage {
	StackPane doctorViewRoot;
	DoctorHomePage doctorHomePage;
	MedicalRecordsPage patientMedicalRecordsPage;
	
	public DoctorView() {
		doctorHomePage = new DoctorHomePage(this);
	
		doctorViewRoot = new StackPane();	
		doctorViewRoot.getChildren().add(doctorHomePage);
		doctorHomePage.setAlignment(Pos.CENTER);
			
		doctorViewRoot.setStyle("-fx-background-color: white;");
		
		this.setScene(new Scene(doctorViewRoot, 800, 600));		
	}
	
	public void goToDoctorHomePage() {
		doctorViewRoot.getChildren().clear();
		
		doctorViewRoot.getChildren().add(doctorHomePage);
		doctorHomePage.setAlignment(Pos.CENTER);
		
		doctorViewRoot.setStyle("-fx-background-color: white;");
	}
	
	public void goToPatientMedicalRecordsPage(File patientDir) {
		doctorViewRoot.getChildren().clear();
		
		patientMedicalRecordsPage = new MedicalRecordsPage(this, patientDir);
		
		doctorViewRoot.getChildren().add(patientMedicalRecordsPage);
		patientMedicalRecordsPage.setAlignment(Pos.CENTER);
		
		doctorViewRoot.setStyle("-fx-background-color: white;");
	}
}