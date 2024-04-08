package application;
	
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class LittleStepsMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//Login button for the nurse
			Button nurseBttn = new Button("Nurse Login");
			// Set button to size
	        final double BUTTON_WIDTH = 200;	
	        nurseBttn.setPrefWidth(BUTTON_WIDTH);
			
			//Action once button pressed
			nurseBttn.setOnAction(event -> {
	        	NursesHomepage homepage = new NursesHomepage();
	        	homepage.show();
	        });
			
			/*
			 * 
			 * method to save login info into login.txt file
			 * private void saveLogin(String username, String password) {}
			 * 
			 * */
			
			//Layout setup with size between buttons in Vbox
	        VBox mainBttnMenu = new VBox(20);
	        mainBttnMenu.getChildren().addAll(nurseBttn);
	        mainBttnMenu.setAlignment(Pos.CENTER);
			
			BorderPane root = new BorderPane();
			root.setCenter(mainBttnMenu);
			Scene scene = new Scene(root,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
