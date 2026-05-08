package com.login.View.Electricity;

import java.io.File;
import java.io.FileInputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.login.Controller.AdminElectricityController;
import com.login.Controller.AdminMaintenanceController;
import com.login.Model.AdminMaintenanceModel;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminElectricity {

    Scene adminElectricity1Scene;
    Stage adminElectricityPrimaryStage;

    // UI elements for the electricity bill popup
    private VBox electricityPopUpVBox;
    private VBox electricityOverlay;
    private TextField electricityBillAmountField;
    private DatePicker datePicker;
    private Button addElectricityBillPhotoButton;
    private Button addElectricityButton;
    private Button cancelButton;

    // image upload popup
    private VBox imageUploadPopup;
    private VBox imageOverlay;
    private ImageView uploadedImageView;
    private Button chooseImageButton;
    private Button doneButton;

    // success message popup
    private VBox successmessagePopup;

    // To store the currently selected flat and the chosen image file
    private AdminMaintenanceModel selectedFlat;
    private File uploadedImageFile;

    public void setAdminElectricity1Scene(Scene adminElectricity1Scene) {
        this.adminElectricity1Scene = adminElectricity1Scene;
    }

    public void setAdminElectricityPrimaryStage(Stage adminElectricityPrimaryStage) {
        this.adminElectricityPrimaryStage = adminElectricityPrimaryStage;
    }

    public StackPane createAdminElectricityScene(Runnable electricityHomePageAdmin) {

        // Firebase is now initialized centrally via FirebaseInitialize
        // No need for manual initialization here

        // --- Flat Details Display (Potentially for selected flat, or just a header) ---
        // For now, this flatDetailsVBox is static. If you want it to show details of the clicked flat,
        // you'd need to update its content dynamically from the generateFlatCard method.
        Text flatNoText = new Text("Flat No : N/A"); // Default or placeholder
        flatNoText.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: 'Comic Sans MS'");
        flatNoText.setTextAlignment(TextAlignment.CENTER);

        Text flatOwnerNameText = new Text("User Name : N/A"); // Default or placeholder
        flatOwnerNameText.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: 'Comic Sans MS'");
        flatOwnerNameText.setTextAlignment(TextAlignment.CENTER);

        VBox flatDetailsVBox = new VBox(50, flatNoText, flatOwnerNameText);
        flatDetailsVBox.setPrefWidth(800);
        flatDetailsVBox.setPrefHeight(150);
        flatDetailsVBox.setMaxWidth(800);
        flatDetailsVBox.setMinWidth(800);
        flatDetailsVBox.setPadding(new Insets(20));
        flatDetailsVBox.setSpacing(10);
        flatDetailsVBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));"
                + "-fx-border-color: white;" + "-fx-border-width: 2px;" + "-fx-border-radius: 15px;"
                + "-fx-background-radius: 15px;");
        flatDetailsVBox.setAlignment(Pos.CENTER_LEFT);
        VBox.setVgrow(flatDetailsVBox, Priority.NEVER); // Don't let it grow vertically unnecessarily

        // --- Image Bar ---
        Image electricityImage = new Image(getClass().getResource("/Assets/new Electricity.png").toExternalForm());
        ImageView electricityImageView = new ImageView(electricityImage);
        electricityImageView.setPreserveRatio(true);
        electricityImageView.setSmooth(true);
        electricityImageView.setFitWidth(300);
        electricityImageView.setFitHeight(800);
       // electricityImageView.setScaleX(1.80);
        electricityImageView.setScaleY(1.40);

        VBox electricityImageVBox = new VBox(electricityImageView);
        electricityImageVBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(electricityImageVBox, Priority.ALWAYS);
        electricityImageVBox.setStyle("-fx-background-color: transparent;");

        Button backTOHomePageButton = new Button("Back");
        backTOHomePageButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:#c5f0f2ff; -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;-fx-text-fill :black");
        backTOHomePageButton.setPadding(new Insets(5, 15, 5, 15));
        backTOHomePageButton.setOnAction(e -> {
            System.out.println("Back button clicked!");
            electricityHomePageAdmin.run();
        });

        VBox imageBarVBox = new VBox(electricityImageVBox, backTOHomePageButton);
        imageBarVBox.setPadding(new Insets(0, 0, 0, 0));
       // imageBarVBox.setPrefWidth(150);
        imageBarVBox.setMaxHeight(Double.MAX_VALUE);
        imageBarVBox.setAlignment(Pos.CENTER);
        imageBarVBox.setPrefWidth(400);
        imageBarVBox.setMaxHeight(800);
        VBox.setVgrow(electricityImageVBox, Priority.ALWAYS);
        VBox.setVgrow(imageBarVBox, Priority.ALWAYS);
        imageBarVBox.setStyle("-fx-background-color: transperent");

        // --- Flat List VBox ---
        VBox flatListVBox = new VBox(12);
        flatListVBox.setPadding(new Insets(10));
        flatListVBox.setStyle("-fx-background-color: white;");
        flatListVBox.setAlignment(Pos.CENTER);
        flatListVBox.setPadding(new Insets(40, 40, 40, 40));
        flatListVBox.setSpacing(20);
        HBox.setHgrow(flatListVBox, Priority.ALWAYS);

        // Populate flatListVBox with cards
        List<AdminMaintenanceModel> flats = AdminMaintenanceController.getFlatData();
        for (AdminMaintenanceModel flat : flats) {
            VBox card = generateFlatCard(flat);
            card.setSpacing(20);
            card.setPadding(new Insets(15));
            card.setPrefWidth(400);
            card.setPrefHeight(200);
            card.setMaxWidth(800);
            card.setMinWidth(400);
            card.setPadding(new Insets(20));
            card.setSpacing(10);
            card.setStyle( "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));"+"-fx-border-color: white;" +"-fx-border-width: 2px;" +"-fx-border-radius: 15px;" +"-fx-background-radius: 15px;");
            card.setAlignment(Pos.CENTER); // Pass flat data
            flatListVBox.getChildren().add(card);
        }

        ScrollPane flatListScrollPane = new ScrollPane(flatListVBox);
        flatListScrollPane.setPadding(new Insets(0, 50, 0, 0));
        flatListScrollPane.setFitToWidth(true);
        flatListScrollPane.setPannable(true);
        flatListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        flatListScrollPane.setPrefWidth(800);
        flatListScrollPane.setStyle(
                "-fx-border-color: #ffffffff;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-background-radius: 10px;"
        );

        HBox adminElectricityPageHBox = new HBox(imageBarVBox, flatListScrollPane);
        adminElectricityPageHBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        adminElectricityPageHBox.setMaxHeight(Double.MAX_VALUE);
        HBox.setHgrow(imageBarVBox, Priority.ALWAYS);
        HBox.setHgrow(flatListScrollPane, Priority.ALWAYS);

        VBox electricityBillFlatListRootVBox = new VBox(adminElectricityPageHBox);
        electricityBillFlatListRootVBox.setStyle("-fx-background-color: LAVENDER;");
        electricityBillFlatListRootVBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        VBox.setVgrow(adminElectricityPageHBox, Priority.ALWAYS);

        // --- STACKPANE ROOT FOR OVERLAY ---
        StackPane electricityRootStackPane = new StackPane(electricityBillFlatListRootVBox);

        // --- Electricity Bill Popup (Initialization) ---
        initializeElectricityPopup();
        electricityRootStackPane.getChildren().add(electricityOverlay);

        // --- Image Upload Popup (Initialization) ---
        initializeImageUploadPopup();
        electricityRootStackPane.getChildren().add(imageOverlay);

        // --- Success Message Popup (Initialization) ---
        initializeSuccessPopup();
        electricityRootStackPane.getChildren().add(successmessagePopup);

        // --- Event Handlers for Popups ---
        setupPopupEventHandlers();

        return electricityRootStackPane;
    }

    // --- Helper method to create individual flat cards ---
    private VBox generateFlatCard(AdminMaintenanceModel flat) {
        Label flatNoLabel = new Label("Flat No: " + flat.getFlatNo());
        flatNoLabel.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        flatNoLabel.setAlignment(Pos.CENTER_LEFT);
        Label ownerNameLabel = new Label("Owner: " + flat.getOwnerName());
        ownerNameLabel.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        ownerNameLabel.setAlignment(Pos.CENTER_LEFT);

        VBox card = new VBox(flatNoLabel, ownerNameLabel);
        card.setSpacing(20);
        card.setPadding(new Insets(15));
        card.setPrefWidth(400);
        card.setPrefHeight(200);
        card.setMaxWidth(800);
        card.setMinWidth(400);
        card.setPadding(new Insets(20));
        card.setSpacing(10);
        card.setStyle( "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));"+"-fx-border-color: white;" +"-fx-border-width: 2px;" +"-fx-border-radius: 15px;" +"-fx-background-radius: 15px;");
        card.setAlignment(Pos.CENTER_LEFT);
        card.setOnMouseClicked((MouseEvent e) -> {
            System.out.println("Clicked: Flat " + flat.getFlatNo());
            selectedFlat = flat; // Store the clicked flat
            // Now, show the electricity bill popup when a flat card is clicked
            showElectricityPopup(flat); // Pass the flat data to the popup
        });

        // Add hover effect 
        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(220, 245, 247),rgb(170, 210, 212));" +
                "-fx-border-color: white;" + "-fx-border-width: 2px;" + "-fx-border-radius: 15px;" +
                "-fx-background-radius: 15px;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +
                "-fx-border-color: white;" + "-fx-border-width: 2px;" + "-fx-border-radius: 15px;" +
                "-fx-background-radius: 15px;"));

        return card;
    }

    // --- Methods to initialize popup UI elements ---
    private void initializeElectricityPopup() {
        electricityPopUpVBox = new VBox(20);
        electricityPopUpVBox.setPadding(new Insets(50));
        electricityPopUpVBox.setStyle(" -fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),#c5f0f2ff);"
                + "-fx-background-radius: 15;" + "-fx-border-radius: 15px;" + "-fx-border-color: white;"
                + "-fx-border-width: 2;" + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        electricityPopUpVBox.setMaxWidth(800);
        electricityPopUpVBox.setMinHeight(400);
        electricityPopUpVBox.setAlignment(Pos.CENTER);

        addElectricityBillPhotoButton = new Button("Add Electricity Bill\n        Photo");
        addElectricityBillPhotoButton.setPrefWidth(200);
        addElectricityBillPhotoButton.setPrefHeight(200);
        addElectricityBillPhotoButton.setStyle(
                " -fx-background-color: transparent;"+
                        "-fx-focus-color: #ffffffff;"+
                        "-fx-font-size: 18px;" +         // Increase text size
                        "-fx-text-fill: black;" +         // Text color
                        "-fx-background-radius: 10px;" + // Rounded corners
                        "-fx-border-radius: 10px;" +     // Rounded border
                        "-fx-border-color: #ffffffff;" +       // Border color
                        "-fx-border-width: 2px;" +     // Border thickness
                        "-fx-padding: 10 10 10 10;" +     // Padding inside the TextField
                        "-fx-font-family: Comic Sans MS"
        );

        electricityBillAmountField = new TextField();
        electricityBillAmountField.setPromptText("Enter Electricity Bill Amount");
        electricityBillAmountField.setPrefWidth(300);
        electricityBillAmountField.setPrefHeight(80);
        electricityBillAmountField.setAlignment(Pos.CENTER);
        electricityBillAmountField.setStyle(
                "-fx-font-size: 18px;" +         // Increase text size
                        "-fx-text-fill: DARKSLATEGRAY;" +         // Text color
                        "-fx-background-radius: 10px;" + // Rounded corners
                        "-fx-border-radius: 10px;" +     // Rounded border
                        "-fx-border-color: #E6E6FA;" +       // Border color
                        "-fx-border-width: 2px;" +     // Border thickness
                        "-fx-padding: 4 10 4 10;" +     // Padding inside the TextField
                        "-fx-font-family: Comic Sans MS");

        datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");
        datePicker.setPrefWidth(300);
        datePicker.setPrefHeight(80);
        datePicker.setStyle(
                "-fx-background-color: white;" +
                        "-fx-control-inner-background: white;" +     // Inner box color
                        "-fx-focus-color: transparent;" +             // Remove blue border
                        "-fx-faint-focus-color: transparent;"+
                        "-fx-font-size: 18px;" +         // Increase text size
                        "-fx-text-fill: DARKSLATEGRAY;" +         // Text color
                        "-fx-background-radius: 10px;" + // Rounded corners
                        "-fx-border-radius: 10px;" +     // Rounded border
                        "-fx-border-color: white;" +       // Border color
                        "-fx-border-width: 2px;" +     // Border thickness
                        "-fx-padding: 4 10 4 10;" +     // Padding inside the TextField
                        "-fx-font-family: Comic Sans MS"
        );

        addElectricityButton = new Button("Add Electricity Bill");
        addElectricityButton.setPrefWidth(300);
        addElectricityButton.setPrefHeight(40);
        addElectricityButton.setStyle(
                " -fx-background-color: #28c039ff;"+
                        "-fx-focus-color: #ffffffff;"+
                        "-fx-font-size: 18px;" +         // Increase text size
                        "-fx-text-fill: white;" +         // Text color
                        "-fx-background-radius: 10px;" + // Rounded corners
                        "-fx-border-radius: 10px;" +     // Rounded border
                        "-fx-border-color: #28c039ff;" +       // Border color
                        "-fx-border-width: 2px;" +     // Border thickness
                        "-fx-padding: 4 10 4 10;" +     // Padding inside the TextField
                        "-fx-font-family: Comic Sans MS"
        );

        cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(300);
        cancelButton.setPrefHeight(40);
        cancelButton.setStyle(
                " -fx-background-color: #ff2323ff;"+
                        "-fx-focus-color: #ffffffff;"+
                        "-fx-font-size: 18px;" +         // Increase text size
                        "-fx-text-fill: white;" +         // Text color
                        "-fx-background-radius: 10px;" + // Rounded corners
                        "-fx-border-radius: 10px;" +     // Rounded border
                        "-fx-border-color: #ff2323ff;" +       // Border color
                        "-fx-border-width: 2px;" +     // Border thickness
                        "-fx-padding: 4 10 4 10;" +     // Padding inside the TextField
                        "-fx-font-family: Comic Sans MS"
        );

        electricityPopUpVBox.getChildren().addAll(addElectricityBillPhotoButton, electricityBillAmountField, datePicker, addElectricityButton, cancelButton);

        electricityOverlay = new VBox(electricityPopUpVBox);
        electricityOverlay.setAlignment(Pos.CENTER);
        electricityOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        electricityOverlay.setVisible(false);
    }

    private void initializeImageUploadPopup() {
        imageUploadPopup = new VBox(20);
        imageUploadPopup.setPadding(new Insets(30));
        imageUploadPopup.setAlignment(Pos.CENTER);
        imageUploadPopup.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +
                "-fx-background-radius: 15;" + "-fx-border-radius: 15px;" + "-fx-border-color: #ccc;" +
                "-fx-border-width: 2;" + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        imageUploadPopup.setMaxWidth(600);
        imageUploadPopup.setMinHeight(400);

        uploadedImageView = new ImageView();
        uploadedImageView.setFitWidth(300);
        uploadedImageView.setPreserveRatio(true);

        chooseImageButton = new Button("Choose Image");
        chooseImageButton.setStyle("-fx-font-size: 16px; -fx-font-family: 'Comic Sans MS';");

        doneButton = new Button("Done");
        doneButton.setStyle("-fx-background-color: #28c039ff; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-family: 'Comic Sans MS';");
        VBox.setMargin(doneButton, new Insets(10, 0, 0, 0));

        imageUploadPopup.getChildren().addAll(chooseImageButton, uploadedImageView, doneButton);

        imageOverlay = new VBox(imageUploadPopup);
        imageOverlay.setAlignment(Pos.CENTER);
        imageOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        imageOverlay.setVisible(false);
    }

    private void initializeSuccessPopup() {
        Text successMessageText = new Text("Electricity Bill Added Successfully!");
        successMessageText.setStyle("-fx-font-size: 24px; -fx-fill: green; -fx-font-weight: bold; -fx-font-family: 'Comic Sans MS';");

        successmessagePopup = new VBox(successMessageText);
        successmessagePopup.setAlignment(Pos.CENTER);
        successmessagePopup.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +
                        "-fx-background-radius: 15;" + "-fx-border-radius: 15px;" + // Corrected border-radius syntax
                        "-fx-border-color: white;" + "-fx-border-width: 2;" + "-fx-padding: 20;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);"
        );
        successmessagePopup.setVisible(false);
        successmessagePopup.setOpacity(0);
        successmessagePopup.setMaxWidth(300);
        successmessagePopup.setMaxHeight(200);
    }

    // --- Helper methods for showing/hiding popups with animations ---
    private void showElectricityPopup(AdminMaintenanceModel flat) {
        // Clear previous inputs when showing the popup for a new flat
        electricityBillAmountField.clear();
        datePicker.setValue(null);
        uploadedImageView.setImage(null);
        uploadedImageFile = null; // Reset the uploaded image file

        System.out.println("Showing electricity popup for Flat: " + flat.getFlatNo());

        electricityOverlay.setVisible(true);
        electricityOverlay.setOpacity(0);
        electricityPopUpVBox.setTranslateY(400);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), electricityOverlay);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        TranslateTransition slideUp = new TranslateTransition(Duration.millis(300), electricityPopUpVBox);
        slideUp.setFromY(400);
        slideUp.setToY(0);
        slideUp.play();
    }

    private void hideElectricityPopup() {
        TranslateTransition slideDown = new TranslateTransition(Duration.millis(300), electricityPopUpVBox);
        slideDown.setFromY(0);
        slideDown.setToY(400);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), electricityOverlay);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        slideDown.setOnFinished(ev -> electricityOverlay.setVisible(false));
        slideDown.play();
        fadeOut.play();
    }

    private void showImageUploadPopup() {
        electricityOverlay.setVisible(false); // Hide previous popup
        imageOverlay.setVisible(true);
        imageOverlay.setOpacity(0);
        imageUploadPopup.setTranslateY(400);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), imageOverlay);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        TranslateTransition slideUp = new TranslateTransition(Duration.millis(300), imageUploadPopup);
        slideUp.setFromY(400);
        slideUp.setToY(0);
        slideUp.play();
    }

    private void hideImageUploadPopup() {
        TranslateTransition slideDown = new TranslateTransition(Duration.millis(300), imageUploadPopup);
        slideDown.setFromY(0);
        slideDown.setToY(400);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), imageOverlay);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        slideDown.setOnFinished(ev -> imageOverlay.setVisible(false));
        slideDown.play();
        fadeOut.play();
    }

    private void showSuccessMessagePopup() {
        successmessagePopup.setVisible(true);
        successmessagePopup.setOpacity(0);
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), successmessagePopup);
        scaleIn.setFromX(0.8);
        scaleIn.setFromY(0.8);
        scaleIn.setToX(1.0);
        scaleIn.setToY(1.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), successmessagePopup);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        scaleIn.play();
        fadeIn.play();

        PauseTransition wait = new PauseTransition(Duration.seconds(2));
        wait.setOnFinished(ev -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), successmessagePopup);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(evt -> successmessagePopup.setVisible(false));
            fadeOut.play();
        });
        wait.play();
    }

    // --- Setup all event handlers for popup buttons ---
    private void setupPopupEventHandlers() {
        cancelButton.setOnAction(e -> hideElectricityPopup());

        addElectricityBillPhotoButton.setOnAction(e -> {
            showImageUploadPopup();
            // Call file chooser logic here
            File chosenFile = AdminElectricityController.chooseAndSaveImage(adminElectricityPrimaryStage);
            if (chosenFile != null) {
                uploadedImageFile = chosenFile; // Store the chosen file
                Image selectedImage = new Image(chosenFile.toURI().toString());
                uploadedImageView.setImage(selectedImage);
            }
        });

        doneButton.setOnAction(e -> {
            hideImageUploadPopup();
            showElectricityPopup(selectedFlat); // Re-show electricity bill popup with current flat
        });

        addElectricityButton.setOnAction(e -> {
            if (selectedFlat == null) {
                System.err.println("No flat selected to add electricity bill to.");
                return;
            }

            try {
                // Get values from input fields
                Double amount = Double.parseDouble(electricityBillAmountField.getText());
                String date = null;
                if (datePicker.getValue() != null) {
                    date = datePicker.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);
                }
                String imagePath = (uploadedImageFile != null) ? uploadedImageFile.getAbsolutePath() : null;

                // Call controller to save data to Firebase and local storage
                AdminElectricityController.saveElectricityBill(selectedFlat.getFlatNo(), amount, date, imagePath);

                hideElectricityPopup(); // Hide the form popup
                showSuccessMessagePopup(); // Show success message

            } catch (NumberFormatException ex) {
                System.err.println("Invalid amount entered: " + electricityBillAmountField.getText());
                // Optionally show an error message to the user
            }
        });
    }
}
