package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

    public PatientLogin(Runnable onBack) {
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
            if (isValid(username, password)) {
            	
                login.setText("Succesful login!"); 
            } else {
      
            	login.setText("Unsuccesful login.");
            }
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

    private boolean isValid(String username, String password) {
        return username.equals("Staff") && password.equals("password123");
    }
}