package com.login.View.Maintanance;

import java.io.FileInputStream;
import java.time.LocalDate; // Import LocalDate
import java.time.format.DateTimeFormatter; // Import DateTimeFormatter
import java.util.List;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.login.Controller.AdminMaintenanceController;
import com.login.Model.AdminMaintenanceModel;
import com.login.Utils.AdminMaintenanceFirebaseServices; // Import the Firebase service

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert; // Import Alert for feedback
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminMaintenanceFlatList  {

        Scene adminMaintenance1Scene;
        Stage adminMaintenancePrimaryStage;
        
        public void setAdminMaintenance1Scene(Scene adminMaintenance1Scene) {
                this.adminMaintenance1Scene = adminMaintenance1Scene;
        }

        public void setAdminMaintanancePrimaryStage(Stage adminMaintenancePrimaryStage) {
                this.adminMaintenancePrimaryStage = adminMaintenancePrimaryStage;
        }

        public StackPane createAdminMaintenanceScene(Runnable maintenanceHomePageAdmin){


         try {
         if (FirebaseApp.getApps().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream("src\\main\\resources\\civita-alpha-firebase-adminsdk-fbsvc-0ca705c544.json");
                FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).setDatabaseUrl("https://civita-alpha-default-rtdb.firebaseio.com/").build();
                FirebaseApp.initializeApp(options);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

         Image maintananceImage = new Image(getClass().getResource("/Assets/new Maintenance.png").toExternalForm());
         ImageView maintenanceImageView = new ImageView(maintananceImage);
         maintenanceImageView.setPreserveRatio(true);
         maintenanceImageView.setSmooth(true);
         maintenanceImageView.setFitWidth(400);
         maintenanceImageView.setFitHeight(800);
        // maintenanceImageView.setScaleX(1.2);
         maintenanceImageView.setScaleY(1.2);

         VBox maitenanceImageVBox = new VBox(maintenanceImageView);
         maitenanceImageVBox.setPrefHeight(800);
         maitenanceImageVBox.setAlignment(Pos.CENTER);
         maitenanceImageVBox.setPadding(new Insets(20, 0, 10, 0));
         maitenanceImageVBox.setStyle("-fx-background-color: transparent;");
         VBox.setVgrow(maitenanceImageVBox, Priority.ALWAYS);
         maitenanceImageVBox.setAlignment(Pos.CENTER);

                Button backTOHomePageButton = new Button("Back");
                backTOHomePageButton.setStyle(
                                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:c5f0f2ff; -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;-fx-text-fill :black");
                backTOHomePageButton.setPadding(new Insets(5, 15, 5, 15));

                backTOHomePageButton.setOnAction(e -> {
                        System.out.println("Back button clicked!");
                        maintenanceHomePageAdmin.run();
                });
                
         VBox imageBarVBox = new VBox(maitenanceImageVBox,backTOHomePageButton );
         imageBarVBox.setAlignment(Pos.CENTER);
         imageBarVBox.setPrefWidth(400);
         imageBarVBox.setMaxHeight(800);
         imageBarVBox.setPadding(new Insets(0, 0, 5, 0));
         imageBarVBox.setStyle("-fx-background-color: Lavender");

        // The maintenancePopUp needs to be instantiated after the scene is set up
        // to pass the selected flat data effectively.
        // We'll create it within the generateFlatCard's click handler or pass a reference.
        // For simplicity, let's keep it here but pass the selected flat to `createMaintenancePopup` when opened.
        VBox maintenancePopUp = createMaintenancePopup(null); // Pass null initially, will be populated on click
        maintenancePopUp.setVisible(false);
        maintenancePopUp.setOpacity(0);
        maintenancePopUp.setPadding(new Insets(50));
        maintenancePopUp.setStyle("  -fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-background-radius: 15;"+"-fx-border-radius: 15px;" +"-fx-border-color: white;" + "-fx-border-width: 2;" +"-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        maintenancePopUp.setMaxWidth(800);
        maintenancePopUp.setMinHeight(400);
        maintenancePopUp.setAlignment(Pos.CENTER);
     
        VBox flatListVBox = new VBox(15);
        flatListVBox.setPadding(new Insets(10));
        flatListVBox.setStyle("-fx-background-color: white;");
        flatListVBox.setAlignment(Pos.CENTER);
        flatListVBox.setPadding(new Insets(40, 40, 40, 40));
        flatListVBox.setSpacing(20);
        HBox.setHgrow(flatListVBox, Priority.ALWAYS);

        // Fetch flats from Firebase
        List<AdminMaintenanceModel> flats = AdminMaintenanceController.getFlatData();
        for (AdminMaintenanceModel flat : flats) {
            // Pass the maintenancePopUp instance to generateFlatCard
            VBox card = generateFlatCard(flat, adminMaintenancePrimaryStage, maintenancePopUp);
            flatListVBox.getChildren().add(card);
        }

         ScrollPane flatListScrollPane = new ScrollPane(flatListVBox);
         flatListScrollPane.setPadding(new Insets(0, 0, 0, 0));
         flatListScrollPane.setFitToWidth(true);
         flatListScrollPane.setPannable(true);
         flatListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
         flatListScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
         flatListScrollPane.setStyle("-fx-background: LAVENDER;"+
         "-fx-border-color: #ffffffff;" +
         "-fx-border-width: 2px;" +
         "-fx-border-radius: 10px;" +
         "-fx-background-radius: 10px;"
         );
        HBox adminMaintenancePageHBox = new HBox(imageBarVBox ,flatListScrollPane);
        HBox.setHgrow(flatListScrollPane, Priority.ALWAYS);

        VBox maintenanceFlatListRootVBox = new VBox(adminMaintenancePageHBox);
        maintenanceFlatListRootVBox .setAlignment(Pos.CENTER);
        maintenanceFlatListRootVBox.setPrefWidth(50);
        maintenanceFlatListRootVBox.setPrefHeight(200);
        maintenanceFlatListRootVBox.setStyle("-fx-background-color: LAVENDER;");

        // STACKPANE ROOT FOR OVERLAY
        StackPane maintananceRootStackPane = new StackPane(maintenanceFlatListRootVBox, maintenancePopUp);

        return maintananceRootStackPane;
    }

    // Pass the selected flat model to generateFlatCard so it can be used when the popup is shown
    private static VBox generateFlatCard(AdminMaintenanceModel flat, Stage stage, VBox maintenancePopUp) {

        Label flatNoLabel = new Label("Flat No: " + flat.getFlatNo());
        flatNoLabel.setStyle("-fx-font-size: 35px; -fx-text-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        Label ownerNameLabel = new Label("Owner: " + flat.getOwnerName());
         ownerNameLabel.setStyle("-fx-font-size: 35px; -fx-text-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");

        VBox card = new VBox(15,flatNoLabel, ownerNameLabel);
        card.setSpacing(20);
        card.setPadding(new Insets(15));
        card.setPrefWidth(400);
        card.setPrefHeight(200);
        card.setMaxWidth(800);
        card.setMinWidth(400);
        card.setPadding(new Insets(20));
        card.setSpacing(10);
        card.setStyle( "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));"+"-fx-border-color: white;" +"-fx-border-width: 2px;" +"-fx-border-radius: 15px;" +"-fx-background-radius: 15px;");
        card.setAlignment(Pos.CENTER);

         // Add hover effect
        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(220, 245, 247),rgb(170, 210, 212));" +
                "-fx-border-color: white;" + "-fx-border-width: 2px;" + "-fx-border-radius: 15px;" +
                "-fx-background-radius: 15px;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +
                "-fx-border-color: white;" + "-fx-border-width: 2px;" + "-fx-border-radius: 15px;" +
                "-fx-background-radius: 15px;"));


        //on press card logic
        card.setOnMouseClicked((MouseEvent e) -> {
            // Before animation starts, clear previous values and populate with current flat's data
            TextField maintenanceAmountField = (TextField) maintenancePopUp.getChildren().get(0);
            DatePicker datePicker = (DatePicker) maintenancePopUp.getChildren().get(1);
            Button addButton = (Button) maintenancePopUp.getChildren().get(2);
            Button cancelButton = (Button) maintenancePopUp.getChildren().get(3);


            maintenanceAmountField.clear();
            datePicker.setValue(null);

            // Populate fields if flat has existing maintenance data
            if (flat.getAmount() != null && !flat.getAmount().isEmpty()) {
                maintenanceAmountField.setText(flat.getAmount());
            }
            if (flat.getDate() != null && !flat.getDate().isEmpty()) {
                try {
                    datePicker.setValue(LocalDate.parse(flat.getDate(), DateTimeFormatter.ISO_LOCAL_DATE));
                } catch (Exception ex) {
                    System.err.println("Error parsing date: " + flat.getDate() + " - " + ex.getMessage());
                    // Handle invalid date format if necessary
                }
            }
            
            // Set the action for the "Add Maintenance" button within the click handler
            // to ensure it uses the 'flat' object that was clicked.
            addButton.setOnAction(event -> {
                String amount = maintenanceAmountField.getText();
                LocalDate selectedDate = datePicker.getValue();
                String date = (selectedDate != null) ? selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE) : null;

                if (amount != null && !amount.trim().isEmpty() && date != null) {
                    // Call the Firebase service to update the data
                    boolean success = AdminMaintenanceFirebaseServices.updateFlatMaintenance(flat.getFlatNo(), amount, date);

                    if (success) {
                        System.out.println("Maintenance data saved for Flat No: " + flat.getFlatNo());
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Maintenance data saved successfully!");
                        // Optionally, update the local model object and card UI
                        flat.setAmount(amount);
                        flat.setDate(date);
                        // You might need to re-render the card or refresh the list if needed
                    } else {
                        System.err.println("Failed to save maintenance data for Flat No: " + flat.getFlatNo());
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to save maintenance data.");
                    }
                    // Close the popup after attempting to save
                    cancelButton.fire(); // Simulate a click on the cancel button to close the popup
                } else {
                    showAlert(Alert.AlertType.WARNING, "Missing Information", "Please enter amount and select a date.");
                }
            });


            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100),maintenancePopUp);
            scaleDown.setToX(0.95);
            scaleDown.setToY(0.95);

            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100),maintenancePopUp);
            scaleUp.setToX(1.0);
            scaleUp.setToY(1.0);
            scaleDown.setOnFinished(event -> scaleUp.play());
            scaleDown.play();

            maintenancePopUp.setVisible(true);
            maintenancePopUp.setOpacity(0);
            maintenancePopUp.setTranslateY(400);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), maintenancePopUp);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

            TranslateTransition slideUp = new TranslateTransition(Duration.millis(300), maintenancePopUp);
            slideUp.setFromY(400);
            slideUp.setToY(0);
            slideUp.play();

            System.out.println("Clicked: Flat " + flat.getFlatNo());
        });

        return card;
    }

    // Modified createMaintenancePopup to accept and store the selected flat for future use
    private VBox createMaintenancePopup(AdminMaintenanceModel selectedFlat) {
        VBox maintenancePopUp = new VBox(20);
        maintenancePopUp.setPadding(new Insets(50));
        maintenancePopUp.setStyle(
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15px;" +
            "-fx-border-color: white;" +
            "-fx-border-width: 2;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);"
        );
        maintenancePopUp.setMaxWidth(600);
        maintenancePopUp.setMinHeight(80);
        maintenancePopUp.setPrefHeight(100);
        maintenancePopUp.setAlignment(Pos.CENTER);

        TextField maintenanceAmountField = new TextField();
        maintenanceAmountField.setPrefWidth(300);
        maintenanceAmountField.setPrefHeight(80);
        maintenanceAmountField.setAlignment(Pos.CENTER);
        maintenanceAmountField.setPromptText("Enter Amount");
         maintenanceAmountField.setStyle(
         "-fx-font-size: 18px;" +         // Increase text size
         "-fx-text-fill: DARKSLATEGRAY;" +         // Text color
         "-fx-background-radius: 10px;" + // Rounded corners
         "-fx-border-radius: 10px;" +     // Rounded border
         "-fx-border-color: #E6E6FA;" +      // Border color
         "-fx-border-width: 2px;" +     // Border thickness
         "-fx-padding: 4 10 4 10;" +      // Padding inside the TextField
         "-fx-font-family: Comic Sans MS");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");
         datePicker.setPromptText("Select Date");
        datePicker.setPrefWidth(300);
        datePicker.setPrefHeight(80);
        datePicker.setStyle( 
         "-fx-background-color: white;" +
         "-fx-control-inner-background: white;" +    // Inner box color
         "-fx-focus-color: transparent;" +           // Remove blue border
         "-fx-faint-focus-color: transparent;"+
         "-fx-font-size: 18px;" +         // Increase text size
         "-fx-text-fill: DARKSLATEGRAY;" +         // Text color
         "-fx-background-radius: 10px;" + // Rounded corners
         "-fx-border-radius: 10px;" +     // Rounded border
         "-fx-border-color: white;" +      // Border color
         "-fx-border-width: 2px;" +     // Border thickness
         "-fx-padding: 4 10 4 10;" +      // Padding inside the TextField
         "-fx-font-family: Comic Sans MS"
         );

        Button addButton = new Button("Add Maintenance");
        addButton.setPrefWidth(300);
        addButton.setPrefHeight(40);
        addButton.setStyle(       
         " -fx-background-color: #28c039ff;"+
         "-fx-focus-color: #ffffffff;"+
         "-fx-font-size: 18px;" +         // Increase text size
         "-fx-text-fill: white;" +         // Text color
         "-fx-background-radius: 10px;" + // Rounded corners
         "-fx-border-radius: 10px;" +     // Rounded border
         "-fx-border-color: #28c039ff;" +      // Border color
         "-fx-border-width: 2px;" +     // Border thickness
         "-fx-padding: 4 10 4 10;" +      // Padding inside the TextField
         "-fx-font-family: Comic Sans MS"
         );
        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(300);
        cancelButton.setPrefHeight(40);
        cancelButton.setStyle(       
         " -fx-background-color: #ff2323ff;"+
         "-fx-focus-color: #ffffffff;"+
         "-fx-font-size: 18px;" +         // Increase text size
         "-fx-text-fill: white;" +         // Text color
         "-fx-background-radius: 10px;" + // Rounded corners
         "-fx-border-radius: 10px;" +     // Rounded border
         "-fx-border-color: #ff2323ff;" +      // Border color
         "-fx-border-width: 2px;" +     // Border thickness
         "-fx-padding: 4 10 4 10;" +      // Padding inside the TextField
         "-fx-font-family: Comic Sans MS"
         );

        cancelButton.setOnAction(e -> {
            TranslateTransition slideDown = new TranslateTransition(Duration.millis(300), maintenancePopUp);
            slideDown.setFromY(0);
            slideDown.setToY(400);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), maintenancePopUp);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            slideDown.setOnFinished(ev -> maintenancePopUp.setVisible(false));

            slideDown.play();
            fadeOut.play();
        });

        maintenancePopUp.getChildren().addAll(maintenanceAmountField, datePicker, addButton, cancelButton);
        return maintenancePopUp;
    }

    // Helper method to show alerts
    private static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}