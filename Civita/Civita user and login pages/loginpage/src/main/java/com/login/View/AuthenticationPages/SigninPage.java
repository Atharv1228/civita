package com.login.View.AuthenticationPages;

import java.util.Objects;
import com.login.Model.SigninModel;
import com.login.View.OptionPage.OptionPage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SigninPage {
   Scene signinPage2Scene,optionPage2Scene;
   Stage signinPage2Stage;
   
   // Password visibility field
   private boolean isPasswordVisible = false;
   
   public void setSigninPage2Stage(Stage stage) {
    this.signinPage2Stage = stage;
   }

   public void setSigninPage2Scene(Scene scene) {
    this.signinPage2Scene = scene;
   }
    
   public VBox createSigninScene() {
         // Label                 
         Label sigininAccontLabel = new Label("Sign In into Account");
         sigininAccontLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white; ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
                
        // Text
         Text userNametText = new Text("Full Name");
         userNametText.setStyle("-fx-font-size: 24px; -fx-fill: WHITE; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
         Text emailAddresstText = new Text("Email Address");
         emailAddresstText.setStyle("-fx-font-size: 24px; -fx-fill: WHITE; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
                 
         Text passwordtText = new Text("Password");
         passwordtText.setStyle("-fx-font-size: 24px; -fx-fill: WHITE; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
          
        // Textfield
         TextField userNameTextField = new TextField();
         userNameTextField.setStyle("-fx-prompt-text-fill: derive(#ffffffff, 10%);"+"-fx-background-color: transparent;" +"-fx-border-color: transparent transparent #ffffffff transparent;" +"-fx-border-width: 0 0 1 0;" +"-fx-text-fill: #ffffffff;" +"-fx-font-size: 18px;" );
         userNameTextField.setPromptText("Enter your Username");
                  
         TextField emailAddressTextField = new TextField();
         emailAddressTextField.setStyle( "-fx-prompt-text-fill: derive(#ffffffff, 10%);"+"-fx-background-color: transparent;" + "-fx-border-color: transparent transparent #ffffffff transparent;" + "-fx-border-width: 0 0 1 0;" + "-fx-text-fill: #ffffffff;" + "-fx-font-size: 18px; " );
         emailAddressTextField.setPromptText("Enter your Email ");
         
         // UPDATED: Password fields with eye toggle functionality
         PasswordField passwordField = new PasswordField();
         TextField passwordTextField = new TextField();
         passwordField.setStyle( "-fx-prompt-text-fill: derive(#ffffffff, 10%);"+"-fx-background-color: transparent;" + "-fx-border-color: transparent transparent #ffffffff transparent;" + "-fx-border-width: 0 0 1 0;" + "-fx-text-fill: #ffffffff;-fx-font-size: 18px; "  );
         passwordTextField.setStyle( "-fx-prompt-text-fill: derive(#ffffffff, 10%);"+"-fx-background-color: transparent;" + "-fx-border-color: transparent transparent #ffffffff transparent;" + "-fx-border-width: 0 0 1 0;" + "-fx-text-fill: #ffffffff;-fx-font-size: 18px; "  );
         passwordField.setPromptText("Enter your Password");
         passwordTextField.setPromptText("Enter your Password");
         passwordTextField.setVisible(false);
         
         // ADDED: Eye button for password with 30px size
         Button passwordEyeButton = new Button("👁");
         passwordEyeButton.setStyle(
             "-fx-background-color: transparent;" +
             "-fx-text-fill: white;" +
             "-fx-font-size: 30px;" +
             "-fx-border-color: transparent;" +
             "-fx-cursor: hand;" +
             "-fx-padding: 2 5 2 5;"
         );
         passwordEyeButton.setOnAction(e -> togglePasswordVisibility(passwordField, passwordTextField, passwordEyeButton));
         
         // ADDED: Password container
         StackPane passwordContainer = new StackPane();
         passwordContainer.getChildren().addAll(passwordField, passwordTextField);
         StackPane.setAlignment(passwordEyeButton, Pos.CENTER_RIGHT);
         passwordContainer.getChildren().add(passwordEyeButton);
                 
        // Button                 
         Button signInButton = new Button("Sign In");
         signInButton.setStyle(
         "-fx-text-fill: green;" +          // text color
         "-fx-font-size: 24px;" +           // font size
         "-fx-font-weight: bold;" +         // font weight
         "-fx-background-radius: 10px;"+   // optional: rounded corners
         "-fx-focus-color: #c1bebeff;"  // Default blue focus color
         );
         signInButton.setPrefWidth(350);
         signInButton.setPrefHeight(20);
         
         // FIXED: Removed duplicate navigation code
         signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // Get input values
                String email = emailAddressTextField.getText();
                String password = getCurrentPassword(passwordField, passwordTextField);
                
                // Validate input fields
                if (email == null || email.trim().isEmpty()) {
                    showErrorPopup("Please enter your email address");
                    return;
                }
                
                if (password == null || password.trim().isEmpty()) {
                    showErrorPopup("Please enter your password");
                    return;
                }
                
                // Create user model and attempt login
                SigninModel user = new SigninModel(email, password);
                String result = com.login.Controller.SigninController.loginUser(user);
                
                // FIXED: Only navigate if login is successful
                if (result.equals("success")) {
                    try {
                        initalizeOptionPage();
                        signinPage2Stage.setScene(optionPage2Scene);
                        System.out.println("Login successful, navigating to option page");
                    } catch (Exception e) {
                        System.err.println("Error navigating to option page: " + e.getMessage());
                        showErrorPopup("Navigation error occurred");
                    }
                } else {
                    // Show error popup and stay on current page
                    showErrorPopup(result);
                    System.out.println("Login failed: " + result);
                }
                
                // REMOVED: The duplicate navigation code that was causing the issue
                // initalizeOptionPage();
                // signinPage2Stage.setScene(optionPage2Scene);
            }
         });

        // Height VBox              
         VBox height1VBox = new VBox(10);
         height1VBox.setPrefHeight(100);
         VBox height2VBox = new VBox(10);
         height2VBox.setPrefHeight(100);
                   
        // doodle image VBOX
         VBox doodleImageVBox = new VBox();
         doodleImageVBox.setPadding(new Insets(0, 40, 0, 0));           
         doodleImageVBox.setMaxHeight(400);
         doodleImageVBox.setMaxWidth(800);
        doodleImageVBox.setPrefWidth(50);
        doodleImageVBox.setPrefHeight(600);
        doodleImageVBox.setAlignment(Pos.CENTER);
         
         Image doodleImage = new Image(getClass().getResource("/Assets/doodleimage.png").toExternalForm());
         ImageView doodleImageView = new ImageView(doodleImage);
         doodleImageView.setPreserveRatio(true);
         doodleImageView.setSmooth(true);
            
         doodleImageVBox.getChildren().add(doodleImageView);
      // Let the image always occupy ~40 % of the stage width
      //   doodleImageView.fitWidthProperty().bind(signinStage.widthProperty().multiply(0.65));
      doodleImageView.setFitWidth(700);
      doodleImageView.setFitHeight(800);
      doodleImageView.setScaleY(1.9);
      doodleImageView.setScaleX(1.9);
             
        //Spacer          
          Region spacer = new Region();                   // flexible gap
          HBox.setHgrow(spacer, Priority.ALWAYS);
         
        // UPDATED: SignIn VBox with password container
          VBox signInVBox = new VBox(10,sigininAccontLabel,height1VBox,userNametText,userNameTextField,emailAddresstText,emailAddressTextField,passwordtText,passwordContainer,height2VBox,signInButton);
          signInVBox.setStyle("-fx-background-color: rgba(57, 203, 105, 0.2); -fx-border-radius: 20;-fx-background-radius: 20;");
          //signInVBox.setPrefSize(400, 500);                                     // preferred size
          //signInVBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);  // don't stretch
         signInVBox.setStyle("-fx-background-color: rgba(57, 203, 105, 0.2); -fx-border-radius: 20;-fx-background-radius: 20;");
         signInVBox.setPrefSize(400, 500);                                     // preferred size
         signInVBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);  // don't stretch
         signInVBox.setPadding(new Insets(20));
                   
        //HBox for image and signup
         HBox imageHBox= new  HBox(doodleImageVBox,spacer,signInVBox);
         imageHBox.setPadding(new Insets(50));          
         imageHBox.setAlignment(Pos.CENTER_LEFT);
         HBox.setHgrow(signInVBox, Priority.ALWAYS);
         
        // Outer VBox                  
         VBox signInBackgruondVBox = new VBox(imageHBox);
         signInBackgruondVBox.setAlignment(Pos.CENTER_LEFT);
         signInBackgruondVBox.setPrefWidth(50);
         signInBackgruondVBox.setPrefHeight(200);
         signInBackgruondVBox.setPadding(new Insets(50, 100, 50, 0));
        
        // Image background          
          Image image = new Image(
          Objects.requireNonNull(getClass().getResource("/Assets/background gradient.png")).toExternalForm());
          BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize(200, 200, true, true, false, true));
          signInBackgruondVBox.setBackground(new Background(backgroundImage));
                                     
         return signInBackgruondVBox;
                    
        //  Scene signupPageScene =new Scene(signInBackgruondVBox);               
        //  signinStage.setScene(signupPageScene);
        // signinStage.show();
        // signinStage.setMaximized(true);
   }
   
   // ADDED: Password visibility toggle method
   private void togglePasswordVisibility(PasswordField passwordField, TextField passwordTextField, Button eyeButton) {
       if (isPasswordVisible) {
           // Hide password - sync text and show password field
           passwordField.setText(passwordTextField.getText());
           passwordTextField.setVisible(false);
           passwordField.setVisible(true);
           eyeButton.setText("👁");  // Show eye - password hidden
           isPasswordVisible = false;
       } else {
           // Show password - sync text and show text field
           passwordTextField.setText(passwordField.getText());
           passwordField.setVisible(false);
           passwordTextField.setVisible(true);
           eyeButton.setText("⊘");  // Cross symbol - password visible
           isPasswordVisible = true;
       }
   }
   
   // ADDED: Helper method to get current password value
   private String getCurrentPassword(PasswordField passwordField, TextField passwordTextField) {
       return isPasswordVisible ? passwordTextField.getText() : passwordField.getText();
   }
                       
   private void initalizeOptionPage(){
         OptionPage optionPageObj= new OptionPage();
         optionPageObj.setOptionPagePrimaryStage(signinPage2Stage);
         optionPage2Scene= new Scene(optionPageObj.createOptionScene(),1600,800);
         optionPageObj.setOptionPage1Scene(optionPage2Scene);
   }
         
   private void showErrorPopup(String message) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
    alert.setTitle("Login Failed");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
   }
}