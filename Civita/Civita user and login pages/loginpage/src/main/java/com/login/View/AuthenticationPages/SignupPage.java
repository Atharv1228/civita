package com.login.View.AuthenticationPages;

import java.util.Objects;
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
import com.login.Model.SignupModel;
import com.login.Controller.SignupController;
import javafx.scene.control.Alert;

public class SignupPage {
    Scene signupPage1Scene, signinPage1Scene, about2Scene;
    Stage SignupPagePrimaryStage;
    
    // Password visibility fields
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;
    
    public void setSignupPage1Scene(Scene signupPage1Scene) {
        this.signupPage1Scene = signupPage1Scene;
    }
    
    public void setSignupPagePrimaryStage(Stage signupPagePrimaryStage) {
        this.SignupPagePrimaryStage = signupPagePrimaryStage;
    }
    
    public VBox createSignupPageScene() {
        // Label                
        Label createAccontLabel = new Label("Create a New Account");
        createAccontLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white; ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        
        // Text
        Text userNametText = new Text("Full Name");
        userNametText.setStyle("-fx-font-size: 24px; -fx-fill: WHITE; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        Text emailAddresstText = new Text("Email Address");
        emailAddresstText.setStyle("-fx-font-size: 24px; -fx-fill: WHITE; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        
        Text flatNoText = new Text("Flat No");
        flatNoText.setStyle("-fx-font-size: 24px; -fx-fill: WHITE; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        Text passwordtText = new Text("Password");
        passwordtText.setStyle("-fx-font-size: 24px; -fx-fill: WHITE; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        
        Text confirmPasswordtText = new Text("Confirm Password");
        confirmPasswordtText.setStyle("-fx-font-size: 24px; -fx-fill: WHITE; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        Text alreadyAccountText = new Text("Already have an account?");
        alreadyAccountText.setStyle("-fx-font-size: 20px; -fx-fill: WHITE;  -fx-font-family: Comic Sans MS");
        Text logInText = new Text("Log in");
        logInText.setStyle("-fx-font-size: 24px; -fx-fill: WHITE; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        
        ////// login text logic call
        logInText.setOnMouseClicked(e -> {
            try {
                initalizeSigninPage();
                SignupPagePrimaryStage.setScene(signinPage1Scene);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        // Textfield
        TextField userNameTextField = new TextField();
        userNameTextField.setStyle("-fx-prompt-text-fill: derive(#ffffffff, 0%);" +
                "-fx-background-color: transparent;" +     // No fill
                "-fx-border-color: transparent transparent #ffffffff transparent;" + // Only bottom border
                "-fx-border-width: 0 0 1 0;" +             // Bottom border thickness
                "-fx-text-fill: #ffffffff;" +                // Text color          
                "-fx-font-size: 18px;"                // Hint color
        );
        userNameTextField.setPromptText("Enter your Username");
        
        TextField emailAddressTextField = new TextField();
        emailAddressTextField.setStyle("-fx-prompt-text-fill: derive(#ffffffff, 10%);" + "-fx-background-color: transparent;" + "-fx-border-color: transparent transparent #ffffffff transparent;" + "-fx-border-width: 0 0 1 0;" + "-fx-text-fill: #ffffffff;" + "-fx-font-size: 18px;");
        emailAddressTextField.setPromptText("Enter your Email ");
        
        TextField flatNoTextField = new TextField();
        flatNoTextField.setStyle("-fx-prompt-text-fill: derive(#ffffffff, 10%);" + "-fx-background-color: transparent;" + "-fx-border-color: transparent transparent #ffffffff transparent;" + "-fx-border-width: 0 0 1 0;" + "-fx-text-fill: #ffffffff;" + "-fx-font-size: 18px;");
        flatNoTextField.setPromptText("Enter your Flat No (Optional for Admin)");
        
        // FIXED: Password fields with eye toggle functionality
        PasswordField passwordField = new PasswordField();
        TextField passwordTextField = new TextField();
        passwordField.setStyle("-fx-prompt-text-fill: derive(#ffffffff, 10%);" + "-fx-background-color: transparent;" + "-fx-border-color: transparent transparent #ffffffff transparent;" + "-fx-border-width: 0 0 1 0;" + "-fx-text-fill: #ffffffff;" + "-fx-font-size: 18px;");
        passwordTextField.setStyle("-fx-prompt-text-fill: derive(#ffffffff, 10%);" + "-fx-background-color: transparent;" + "-fx-border-color: transparent transparent #ffffffff transparent;" + "-fx-border-width: 0 0 1 0;" + "-fx-text-fill: #ffffffff;" + "-fx-font-size: 18px;");
        passwordField.setPromptText("Enter your Password");
        passwordTextField.setPromptText("Enter your Password");
        passwordTextField.setVisible(false);
        
        // Eye button for password
        Button passwordEyeButton = new Button("👁");
        passwordEyeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 30px; -fx-border-color: transparent;");
        passwordEyeButton.setOnAction(e -> togglePasswordVisibility(passwordField, passwordTextField, passwordEyeButton));
        
        // Password container
        StackPane passwordContainer = new StackPane();
        passwordContainer.getChildren().addAll(passwordField, passwordTextField);
        StackPane.setAlignment(passwordEyeButton, Pos.CENTER_RIGHT);
        passwordContainer.getChildren().add(passwordEyeButton);
        
        // Confirm Password fields with eye toggle
        PasswordField confirmPasswordField = new PasswordField();
        TextField confirmPasswordTextFieldVisible = new TextField();
        confirmPasswordField.setStyle("-fx-prompt-text-fill: derive(#ffffffff, 10%);" + "-fx-background-color: transparent;" + "-fx-border-color: transparent transparent #ffffffff transparent;" + "-fx-border-width: 0 0 1 0;" + "-fx-text-fill: #ffffffff;" + "-fx-font-size: 18px;");
        confirmPasswordTextFieldVisible.setStyle("-fx-prompt-text-fill: derive(#ffffffff, 10%);" + "-fx-background-color: transparent;" + "-fx-border-color: transparent transparent #ffffffff transparent;" + "-fx-border-width: 0 0 1 0;" + "-fx-text-fill: #ffffffff;" + "-fx-font-size: 18px;");
        confirmPasswordField.setPromptText("Enter your Password again");
        confirmPasswordTextFieldVisible.setPromptText("Enter your Password again");
        confirmPasswordTextFieldVisible.setVisible(false);
        
        // Eye button for confirm password
        Button confirmPasswordEyeButton = new Button("👁");
        confirmPasswordEyeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 30px; -fx-border-color: transparent;");
        confirmPasswordEyeButton.setOnAction(e -> toggleConfirmPasswordVisibility(confirmPasswordField, confirmPasswordTextFieldVisible, confirmPasswordEyeButton));
        
        // Confirm Password container
        StackPane confirmPasswordContainer = new StackPane();
        confirmPasswordContainer.getChildren().addAll(confirmPasswordField, confirmPasswordTextFieldVisible);
        StackPane.setAlignment(confirmPasswordEyeButton, Pos.CENTER_RIGHT);
        confirmPasswordContainer.getChildren().add(confirmPasswordEyeButton);
        
        // Sign UP Button                
        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle(
                "-fx-text-fill: green;" +          // text color
                        "-fx-font-size: 24px;" +           // font size
                        "-fx-font-weight: bold;" +         // font weight
                        "-fx-background-radius: 10px;" +   // optional: rounded corners
                        "-fx-focus-color: #c1bebeff;"  // Default blue focus color
        );
        signUpButton.setPrefWidth(350);
        signUpButton.setPrefHeight(20);
        
        /// FIXED: signup button ON press - only navigate on success
        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // Get input values
                String name = userNameTextField.getText();
                String email = emailAddressTextField.getText();
                String flatNo = flatNoTextField.getText();
                String password = getCurrentPassword(passwordField, passwordTextField);
                String confirmPassword = getCurrentConfirmPassword(confirmPasswordField, confirmPasswordTextFieldVisible);
                
                // Validate input fields
                if (name == null || name.trim().isEmpty()) {
                    showAlert("Error", "Please enter your full name");
                    return;
                }
                
                if (email == null || email.trim().isEmpty()) {
                    showAlert("Error", "Please enter your email address");
                    return;
                }
                
                if (password == null || password.trim().isEmpty()) {
                    showAlert("Error", "Please enter your password");
                    return;
                }
                
                if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
                    showAlert("Error", "Please confirm your password");
                    return;
                }
                
                if (!password.equals(confirmPassword)) {
                    showAlert("Error", "Passwords do not match");
                    return;
                }
                
                // FIXED: If flatNo is empty, treat as admin (no validation needed)
                if (flatNo == null || flatNo.trim().isEmpty()) {
                    flatNo = "ADMIN"; // Set a default value for admin
                    System.out.println("Empty flat number detected - treating as admin login");
                }
                
                // Create user model and attempt registration
                SignupModel user = new SignupModel(name, email, flatNo, password, confirmPassword);
                String result = SignupController.registerUser(user);
                
                // FIXED: Check if registration was successful before navigating
                if (result.toLowerCase().contains("success") || result.toLowerCase().contains("registered")) {
                    // Show success message
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registration Successful");
                    alert.setHeaderText(null);
                    alert.setContentText(result);
                    alert.showAndWait();
                    
                    // Navigate to signin page only on success
                    try {
                        initalizeSigninPage();
                        SignupPagePrimaryStage.setScene(signinPage1Scene);
                        System.out.println("Registration successful, navigating to signin page");
                    } catch (Exception ex) {
                        System.err.println("Error navigating to signin page: " + ex.getMessage());
                        showAlert("Error", "Navigation error occurred");
                        ex.printStackTrace();
                    }
                } else {
                    // Show error message and stay on current page
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Registration Failed");
                    alert.setHeaderText(null);
                    alert.setContentText(result);
                    alert.showAndWait();
                    System.out.println("Registration failed: " + result);
                }
            }
        });
        
        // Height VBox             
        VBox height1VBox = new VBox(10);
        height1VBox.setPrefHeight(100);
        VBox height2VBox = new VBox(10);
        height2VBox.setPrefHeight(100);
        
        // HBox
        HBox signUpHBox = new HBox(10, alreadyAccountText, logInText);
        signUpHBox.setPadding(new Insets(20, 0, 20, 0));
        signUpHBox.setAlignment(Pos.CENTER);
        
        // doodle image VBOX
        VBox doodleImageVBox = new VBox(30);
        doodleImageVBox.setPadding(new Insets(0, 40, 0, 0));
        Image doodleImage = new Image(getClass().getResource("/Assets/doodleimage.png").toExternalForm());
        ImageView doodleImageView = new ImageView(doodleImage);
        doodleImageView.setPreserveRatio(true);
        doodleImageView.setSmooth(true);
        
        doodleImageVBox.getChildren().addAll(doodleImageView);
        doodleImageView.setFitWidth(700);
        doodleImageView.setFitHeight(800);
        doodleImageView.setScaleY(1.9);
        doodleImageView.setScaleX(1.9);
        doodleImageVBox.setAlignment(Pos.CENTER);
        
        //Spacer       
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // FIXED: Signup VBox with password containers
        VBox signUpVBox = new VBox(10, createAccontLabel, height1VBox, userNametText, userNameTextField, emailAddresstText, emailAddressTextField, flatNoText, flatNoTextField, passwordtText, passwordContainer, confirmPasswordtText, confirmPasswordContainer, height2VBox, signUpButton, signUpHBox);
        signUpVBox.setStyle("-fx-background-color: rgba(57, 203, 105, 0.2); -fx-border-radius: 20;-fx-background-radius: 20;");
        signUpVBox.setPrefSize(400, 500);
        signUpVBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        signUpVBox.setPadding(new Insets(20));
        
        //HBox for image and signup
        HBox imageHBox = new HBox(doodleImageVBox, spacer, signUpVBox);
        imageHBox.setPadding(new Insets(50));
        imageHBox.setAlignment(Pos.CENTER_LEFT);
        
        // Outer VBox                 
        VBox signUpBackgruondVBox = new VBox(imageHBox);
        signUpBackgruondVBox.setAlignment(Pos.CENTER_LEFT);
        signUpBackgruondVBox.setPrefWidth(50);
        signUpBackgruondVBox.setPrefHeight(200);
        signUpBackgruondVBox.setPadding(new Insets(50, 100, 50, 0));
        
        // Image background    
        Image image = new Image(
                Objects.requireNonNull(getClass().getResource("/Assets/background gradient.png")).toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(200, 200, true, true, false, true));
        signUpBackgruondVBox.setBackground(new Background(backgroundImage));
        
        return signUpBackgruondVBox;
    }
    
    // FIXED: Password visibility toggle methods - corrected logic
    private void togglePasswordVisibility(PasswordField passwordField, TextField passwordTextField, Button eyeButton) {
        if (isPasswordVisible) {
            // Currently showing password, now hide it
            // Copy text from visible field to hidden field
            passwordField.setText(passwordTextField.getText());
            // Show password field, hide text field
            passwordTextField.setVisible(false);
            passwordField.setVisible(true);
            // Update button to show eye (indicating password is hidden)
            eyeButton.setText("👁");
            isPasswordVisible = false;
        } else {
            // Currently hiding password, now show it
            // Copy text from hidden field to visible field
            passwordTextField.setText(passwordField.getText());
            // Hide password field, show text field
            passwordField.setVisible(false);
            passwordTextField.setVisible(true);
            // Update button to show cross (indicating password is visible)
            eyeButton.setText("⊘");
            isPasswordVisible = true;
        }
    }
    
    private void toggleConfirmPasswordVisibility(PasswordField confirmPasswordField, TextField confirmPasswordTextField, Button eyeButton) {
        if (isConfirmPasswordVisible) {
            // Currently showing password, now hide it
            // Copy text from visible field to hidden field
            confirmPasswordField.setText(confirmPasswordTextField.getText());
            // Show password field, hide text field
            confirmPasswordTextField.setVisible(false);
            confirmPasswordField.setVisible(true);
            // Update button to show eye (indicating password is hidden)
            eyeButton.setText("👁");
            isConfirmPasswordVisible = false;
        } else {
            // Currently hiding password, now show it
            // Copy text from hidden field to visible field
            confirmPasswordTextField.setText(confirmPasswordField.getText());
            // Hide password field, show text field
            confirmPasswordField.setVisible(false);
            confirmPasswordTextField.setVisible(true);
            // Update button to show cross (indicating password is visible)
            eyeButton.setText("⊘");
            isConfirmPasswordVisible = true;
        }
    }
    
    // Helper methods to get current password values
    private String getCurrentPassword(PasswordField passwordField, TextField passwordTextField) {
        return isPasswordVisible ? passwordTextField.getText() : passwordField.getText();
    }
    
    private String getCurrentConfirmPassword(PasswordField confirmPasswordField, TextField confirmPasswordTextField) {
        return isConfirmPasswordVisible ? confirmPasswordTextField.getText() : confirmPasswordField.getText();
    }
    
    // Helper method for showing alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void initalizeSigninPage() {
        SigninPage signinPageObj = new SigninPage();
        signinPageObj.setSigninPage2Stage(SignupPagePrimaryStage);
        signinPage1Scene = new Scene(signinPageObj.createSigninScene(), 1600, 800);
        signinPageObj.setSigninPage2Scene(signinPage1Scene);
    }
}