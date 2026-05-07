package com.login.View.Maintanance;


import com.login.Controller.ResidentMaintenanceController;
import com.login.Model.ResidentMaintenanceModel; 

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert; 
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.List;

public class Residentmaintenance {

    Scene residentMaintenance1Scene;
    Stage residentMaintenancePrimaryStage;

    // UI elements for the popups that need to be accessed from event handlers
    private VBox residentMaintenancePopUpOverlay;
    private VBox paymentSuccessOverlay;
    private Label popupMaintenanceAmountLabel;
    private Label popupDueDateLabel;
    private Button payMaintenanceButtonInPopup;
    private Button cancelButtonInPopup;
    private Button okButtonInSuccessPopup;


    public void setResidentMaintenance1Scene(Scene residentMaintenance1Scene) {
        this.residentMaintenance1Scene = residentMaintenance1Scene;
    }

    public void setResidentMaintenancePrimaryStage(Stage residentMaintenancePrimaryStage) {
        this.residentMaintenancePrimaryStage = residentMaintenancePrimaryStage;
    }

    /**
     * Creates the resident maintenance scene, fetching data based on flat number.
     * @param maintenanceHomePageResident A runnable to navigate back to the home page.
     * @param userUid The Firebase User ID (UID) of the currently logged-in resident.
     * @return A StackPane containing the maintenance view.
     */
    public StackPane createResidentMaintenanceScene(Runnable maintenanceHomePageResident, String userUid) {
       
  

        // Maintenance text (not dynamic, static header)
        Text residentMaintenanceHeader = new Text("Your Maintenance Records");
        residentMaintenanceHeader.setStyle("-fx-font-size: 40px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        residentMaintenanceHeader.setTextAlignment(TextAlignment.CENTER);

        // Dynamic VBox to hold maintenance cards
        VBox maintenanceCardsContainer = new VBox(20); // Spacing between cards
        maintenanceCardsContainer.setPadding(new Insets(20, 40, 40, 40));
        maintenanceCardsContainer.setAlignment(Pos.TOP_CENTER);
        maintenanceCardsContainer.setStyle("-fx-background-color: white;");
        HBox.setHgrow(maintenanceCardsContainer, Priority.ALWAYS);

        // **HERE IS THE KEY CHANGE:** Pass the actual userUid to the controller
        // This will fetch the single document for this UID.
        List<ResidentMaintenanceModel> maintenanceRecords = ResidentMaintenanceController.getResidentMaintenanceData("50iKugZzqmVWeByI9kKXcFh4hl42");

        if (maintenanceRecords.isEmpty()) {
            Label noRecordsLabel = new Label("No maintenance records found for your flat.");
            noRecordsLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: gray;");
            maintenanceCardsContainer.getChildren().add(noRecordsLabel);
        } else {
            // Since your structure has maintenance details *within* the user's document,
            // there will typically be only ONE ResidentMaintenanceModel in the list.
            for (ResidentMaintenanceModel record : maintenanceRecords) {
                // Generate a card for each maintenance record
                // Pass the userUid to card creation. The record.getId() will also be the userUid.
                VBox card = createMaintenanceCard(record, userUid);
                maintenanceCardsContainer.getChildren().add(card);
            }
        }

        // Wrap maintenance cards in a ScrollPane
        ScrollPane residentMaintenanceListScrollPane = new ScrollPane(maintenanceCardsContainer);
        residentMaintenanceListScrollPane.setFitToWidth(true);
        residentMaintenanceListScrollPane.setPannable(true);
        residentMaintenanceListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        residentMaintenanceListScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        residentMaintenanceListScrollPane.setStyle("-fx-background: white; -fx-background-color: white;"+
                "-fx-border-color: #ffffffff;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;"
        );

        // Left Image Bar (as in your existing code)
        Image maintananceImage = new Image(getClass().getResource("/Assets/new Maintenance.png").toExternalForm());
        ImageView residentMaintenanceImageView = new ImageView(maintananceImage);
        residentMaintenanceImageView.setPreserveRatio(true);
        residentMaintenanceImageView.setSmooth(true);
        residentMaintenanceImageView.setFitWidth(400);
        residentMaintenanceImageView.setFitHeight(800);
       // residentMaintenanceImageView.setScaleX(1.20);
        residentMaintenanceImageView.setScaleY(1.2);

        VBox maintenanceImageVBox = new VBox(residentMaintenanceImageView);
        maintenanceImageVBox.setPrefHeight(800);
        maintenanceImageVBox.setAlignment(Pos.CENTER);
        maintenanceImageVBox.setPadding(new Insets(20, 0, 10, 0));
        maintenanceImageVBox.setStyle("-fx-background-color: transparent;");
        VBox.setVgrow(maintenanceImageVBox, Priority.ALWAYS);
        maintenanceImageVBox.setAlignment(Pos.CENTER);

        Button backTOHomePageButton = new Button("Back");
        backTOHomePageButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:c5f0f2ff; -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;-fx-text-fill :black");
        backTOHomePageButton.setPadding(new Insets(5, 15, 5, 15));
        backTOHomePageButton.setOnAction(e -> {
            System.out.println("Back button clicked!");
            maintenanceHomePageResident.run();
        });

        VBox residentImageBarVBox = new VBox(maintenanceImageVBox, backTOHomePageButton);
        residentImageBarVBox.setPrefWidth(400);
        residentImageBarVBox.setMaxHeight(800);
        residentImageBarVBox.setPadding(new Insets(0, 0, 5, 0));
        residentImageBarVBox.setAlignment(Pos.CENTER);
        residentImageBarVBox.setStyle("-fx-background-color: Lavender");

        HBox residentPageHBox = new HBox(residentImageBarVBox, residentMaintenanceListScrollPane);
        HBox.setHgrow(residentMaintenanceListScrollPane, Priority.ALWAYS);

        VBox maintenanceRootVBox = new VBox(residentPageHBox);
        maintenanceRootVBox.setAlignment(Pos.CENTER);
        maintenanceRootVBox.setPrefWidth(50);
        maintenanceRootVBox.setPrefHeight(200);
        maintenanceRootVBox.setStyle("-fx-background-color: LAVENDER;");

        // Initialize the popups and add to the stack pane
        residentMaintenancePopUpOverlay = createMaintenanceDetailsPopup();
        paymentSuccessOverlay = createPaymentSuccessPopup();

        StackPane residentMaintananceRootStackPane = new StackPane(maintenanceRootVBox, residentMaintenancePopUpOverlay, paymentSuccessOverlay);
        residentMaintenancePopUpOverlay.setVisible(false); // Hide initially
        paymentSuccessOverlay.setVisible(false);    // Hide initially

        return residentMaintananceRootStackPane;
    }

    /**
     * Creates a single maintenance card to display status and due date.
     * @param record The ResidentMaintenanceModel for this card.
     * @param userUid The Firebase User ID (UID) of the currently logged-in user.
     * @return A VBox representing the card.
     */
    private VBox createMaintenanceCard(ResidentMaintenanceModel record, String userUid) {
        Label statusLabel = new Label("Maintenance Status: " + record.getStatus());
        statusLabel.setStyle("-fx-font-size: 30px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        if ("paid".equalsIgnoreCase(record.getStatus())) {
            statusLabel.setStyle(statusLabel.getStyle() + "; -fx-text-fill: green;");
        } else {
            statusLabel.setStyle(statusLabel.getStyle() + "; -fx-text-fill: red;");
        }

        Label dueDateLabel = new Label("Due Date: " + record.getDate());
        dueDateLabel.setStyle("-fx-font-size: 30px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");

        VBox card = new VBox(20, statusLabel, dueDateLabel);
        card.setPadding(new Insets(20));
        card.setPrefWidth(400);
        card.setPrefHeight(200);
        card.setMaxWidth(800);
        card.setMinWidth(400);
        card.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 15px;" +
                "-fx-background-radius: 15px;"
        );
        card.setAlignment(Pos.CENTER_LEFT);

        // On card click, show the detailed popup
        card.setOnMouseClicked(e -> {
            // Update the labels in the popup with this card's data
            popupMaintenanceAmountLabel.setText("Maintenance Amount: " + record.getAmount());
            popupDueDateLabel.setText("Due Date: " + record.getDate());

            // Enable/Disable pay button based on status
            if ("paid".equalsIgnoreCase(record.getStatus())) {
                payMaintenanceButtonInPopup.setDisable(true);
                payMaintenanceButtonInPopup.setText("PAID");
                payMaintenanceButtonInPopup.setStyle(payMaintenanceButtonInPopup.getStyle() + "; -fx-background-color: gray; -fx-border-color: gray;");
            } else {
                payMaintenanceButtonInPopup.setDisable(false);
                payMaintenanceButtonInPopup.setText("Pay Maintenance");
                 payMaintenanceButtonInPopup.setStyle(
                    " -fx-background-color: #28c039ff;"+
                    "-fx-focus-color: #ffffffff;"+
                    "-fx-font-size: 18px;" +
                    "-fx-text-fill: white;" +
                    "-fx-background-radius: 10px;" +
                    "-fx-border-radius: 10px;" +
                    "-fx-border-color: #28c039ff;" +
                    "-fx-border-width: 2px;" +
                    "-fx-padding: 4 10 4 10;" +
                    "-fx-font-family: Comic Sans MS");
            }

            // Set action for Pay Maintenance button for *this specific record*
            payMaintenanceButtonInPopup.setOnAction(event -> {
                boolean success = ResidentMaintenanceController.updateMaintenanceStatus(
                    "50iKugZzqmVWeByI9kKXcFh4hl42", // **PASS THE ACTUAL USER UID HERE**
                    record.getId(), // This will also be the userUid, but passing it for consistency.
                    "paid"
                );

                if (success) {
                    System.out.println("Maintenance paid successfully for User UID: " + userUid);
                    record.setStatus("paid"); // Update local model
                    statusLabel.setText("Maintenance Status: Paid"); // Update card UI
                    statusLabel.setStyle(statusLabel.getStyle() + "; -fx-text-fill: green;"); // Change color
                    payMaintenanceButtonInPopup.setDisable(true);
                    payMaintenanceButtonInPopup.setText("PAID");
                    payMaintenanceButtonInPopup.setStyle(payMaintenanceButtonInPopup.getStyle() + "; -fx-background-color: gray; -fx-border-color: gray;");

                    // Show success popup
                    residentMaintenancePopUpOverlay.setVisible(false); // Hide details popup
                    paymentSuccessOverlay.setVisible(true);
                    paymentSuccessOverlay.setOpacity(0);
                    FadeTransition fadeInSuccess = new FadeTransition(Duration.millis(300), paymentSuccessOverlay);
                    fadeInSuccess.setFromValue(0);
                    fadeInSuccess.setToValue(1);
                    fadeInSuccess.play();

                } else {
                    showAlert(Alert.AlertType.ERROR, "Payment Failed", "Could not process payment. Please try again.");
                }
            });


            // Animations for showing popup (as in your original code)
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100), card);
            scaleDown.setToX(0.95);
            scaleDown.setToY(0.95);
            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), card);
            scaleUp.setToX(1.0);
            scaleUp.setToY(1.0);
            scaleDown.setOnFinished(event -> scaleUp.play());
            scaleDown.play();

            residentMaintenancePopUpOverlay.setVisible(true); // Show the overlay
            residentMaintenancePopUpOverlay.setOpacity(0);

            // Access the actual popup content inside the overlay to animate
            VBox actualPopupContent = (VBox) residentMaintenancePopUpOverlay.getChildren().get(0);
            actualPopupContent.setTranslateY(400); // Start below screen

            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), residentMaintenancePopUpOverlay);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

            // Animate bottom sheet upward
            TranslateTransition slideUp = new TranslateTransition(Duration.millis(300), actualPopupContent);
            slideUp.setFromY(400); // match initial Y
            slideUp.setToY(0);    // slide into visible area
            slideUp.play();
        });
        return card;
    }

    /**
     * Creates the detailed maintenance popup.
     * @return A VBox representing the popup overlay.
     */
    private VBox createMaintenanceDetailsPopup() {
        VBox popupContent = new VBox(20);
        popupContent.setPadding(new Insets(50));
        popupContent.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +
                "-fx-background-radius: 15;" +
                "-fx-border-radius: 15px;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);"
        );
        popupContent.setMaxWidth(800);
        popupContent.setMinHeight(400);
        popupContent.setAlignment(Pos.CENTER);

        // Maintenance amount label (dynamic)
        popupMaintenanceAmountLabel = new Label("Maintenance Amount: ");
        popupMaintenanceAmountLabel.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-font-size: 24px;" +
                "-fx-text-fill: DARKSLATEGRAY;" +
                "-fx-font-family: Comic Sans MS"
        );
        VBox maintenanceAmountVBox = new VBox(popupMaintenanceAmountLabel);
        maintenanceAmountVBox.setPrefWidth(300);
        maintenanceAmountVBox.setPrefHeight(80);
        maintenanceAmountVBox.setAlignment(Pos.CENTER_LEFT);
        maintenanceAmountVBox.setStyle(
                "-fx-background-radius: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2px;" +
                "-fx-padding: 20;" +
                "-fx-background-color: white;"
        );

        // Due date label (dynamic)
        popupDueDateLabel = new Label("Due Date: ");
        popupDueDateLabel.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-font-size: 24px;" +
                "-fx-text-fill: DARKSLATEGRAY;" +
                "-fx-font-family: Comic Sans MS"
        );
        VBox dueDateVBox = new VBox(popupDueDateLabel);
        dueDateVBox.setPrefWidth(300);
        dueDateVBox.setPrefHeight(80);
        dueDateVBox.setAlignment(Pos.CENTER_LEFT);
        dueDateVBox.setStyle(
                "-fx-background-radius: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2px;" +
                "-fx-padding: 20;" +
                "-fx-background-color: white;"
        );

        payMaintenanceButtonInPopup = new Button("Pay Maintenance"); // Store reference
        payMaintenanceButtonInPopup.setPrefWidth(300);
        payMaintenanceButtonInPopup.setPrefHeight(40);
        payMaintenanceButtonInPopup.setStyle(
                " -fx-background-color: #28c039ff;" +
                "-fx-focus-color: #ffffffff;" +
                "-fx-font-size: 18px;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-border-color: #28c039ff;" +
                "-fx-border-width: 2px;" +
                "-fx-padding: 4 10 4 10;" +
                "-fx-font-family: Comic Sans MS");

        cancelButtonInPopup = new Button("Cancel"); // Store reference
        cancelButtonInPopup.setPrefWidth(300);
        cancelButtonInPopup.setPrefHeight(40);
        cancelButtonInPopup.setStyle(
                " -fx-background-color: #ff2323ff;" +
                "-fx-focus-color: #ffffffff;" +
                "-fx-font-size: 18px;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-border-color: #ff2323ff;" +
                "-fx-border-width: 2px;" +
                "-fx-padding: 4 10 4 10;" +
                "-fx-font-family: Comic Sans MS"
        );

        cancelButtonInPopup.setOnAction(e -> {
            TranslateTransition slideDown = new TranslateTransition(Duration.millis(300), popupContent);
            slideDown.setFromY(0);
            slideDown.setToY(400);
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), residentMaintenancePopUpOverlay); // Fade out the overlay
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            slideDown.setOnFinished(ev -> residentMaintenancePopUpOverlay.setVisible(false));
            slideDown.play();
            fadeOut.play();
        });

        popupContent.getChildren().addAll(maintenanceAmountVBox, dueDateVBox, payMaintenanceButtonInPopup, cancelButtonInPopup);

        // Wrap popup content in an overlay VBox to handle background dimming
        VBox overlay = new VBox(popupContent);
        overlay.setAlignment(Pos.CENTER);
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        overlay.setVisible(false); // Initially hidden
        return overlay;
    }

    /**
     * Creates the "Payment Successful" popup.
     * @return A VBox representing the success popup overlay.
     */
    private VBox createPaymentSuccessPopup() {
        ImageView successImage = new ImageView(new Image(getClass().getResource("/Assets/Payment Succesful.png").toExternalForm()));
        successImage.setFitWidth(800);
        successImage.setFitHeight(400);
        successImage.setPreserveRatio(true);

        Text successMessage = new Text("Maintenance Paid Successfully!");
        successMessage.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: green; -fx-font-family: Comic Sans MS");

        okButtonInSuccessPopup = new Button("OK"); // Store reference
        okButtonInSuccessPopup.setPrefWidth(150);
        okButtonInSuccessPopup.setStyle(
                "-fx-background-color: #28c039;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 16px;" +
                "-fx-background-radius: 10px;" +
                "-fx-font-family: Comic Sans MS;"
        );

        VBox paymentSuccessPopupContent = new VBox(20, successImage, successMessage, okButtonInSuccessPopup);
        paymentSuccessPopupContent.setPadding(new Insets(40));
        paymentSuccessPopupContent.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 15;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: green;" +
                "-fx-border-width: 2;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);"
        );
        paymentSuccessPopupContent.setAlignment(Pos.CENTER);
        paymentSuccessPopupContent.setMaxWidth(500);
        paymentSuccessPopupContent.setMinHeight(300);

        VBox overlay = new VBox(paymentSuccessPopupContent);
        overlay.setAlignment(Pos.CENTER);
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        overlay.setVisible(false); // Initially hidden

        // Action for OK button (to close this success popup)
        okButtonInSuccessPopup.setOnAction(event -> {
            FadeTransition fadeOutSuccess = new FadeTransition(Duration.millis(300), overlay);
            fadeOutSuccess.setFromValue(1);
            fadeOutSuccess.setToValue(0);
            fadeOutSuccess.setOnFinished(ev -> overlay.setVisible(false));
            fadeOutSuccess.play();
        });

        return overlay;
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