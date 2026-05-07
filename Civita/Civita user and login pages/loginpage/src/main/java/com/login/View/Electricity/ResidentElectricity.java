package com.login.View.Electricity;

import com.login.Controller.ResidentElectricityController;
import com.login.Model.ResidentElectricityModel;
import javafx.application.Platform;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ResidentElectricity {
    Scene residentElectricity1Scene;
    Stage residentElectricityPrimaryStage;
    private boolean isPaid = false; // Flag to track payment status
    
    // Add these fields to store fetched data
    private ResidentElectricityModel currentBillData;
    private String currentFlatNo = "A 201"; // Change this to match the flat number used by admin
    
    // UI elements that need to be updated with fetched data
    private Text residentElectricityBillText;
    private Text residentMaintenanceDate;
    private Text electricityAmountText;
    private Text dueDateText;

    public void setResidentElectricity1Scene(Scene residentElectricity1Scene) {
        this.residentElectricity1Scene = residentElectricity1Scene;
    }

    public void setResidentElectricityPrimaryStage(Stage residentElectricityPrimaryStage) {
        this.residentElectricityPrimaryStage = residentElectricityPrimaryStage;
    }

    // Method to update the bill status display
    private void updateBillStatus(Text billStatusText) {
        if (isPaid) {
            billStatusText.setText("Electricity Bill Status : Paid");
            billStatusText.setStyle("-fx-font-size: 35px; -fx-fill: GREEN; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        } else {
            billStatusText.setText("Electricity Bill Status : Unpaid");
            billStatusText.setStyle("-fx-font-size: 35px; -fx-fill: RED; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        }
    }
    
    // New method to fetch and update bill data with debugging
    private void fetchAndUpdateBillData() {
        System.out.println("Starting to fetch bill data for flat: " + currentFlatNo);
        
        // First, debug what's actually in Firebase
        ResidentElectricityController.debugFirebaseData(currentFlatNo);
        
        try {
            ResidentElectricityController.getElectricityBillData(currentFlatNo)
                .thenAccept(billData -> {
                    Platform.runLater(() -> {
                        try {
                            System.out.println("=== RECEIVED BILL DATA IN UI ===");
                            System.out.println("Amount: " + billData.getElectricityBillAmount());
                            System.out.println("Date: " + billData.getElectricityBillDate());
                            System.out.println("ImagePath: " + billData.getElectricityBillImagePath());
                            
                            this.currentBillData = billData;
                            updateUIWithBillData(billData);
                        } catch (Exception e) {
                            System.err.println("Error updating UI with bill data: " + e.getMessage());
                            e.printStackTrace();
                            updateUIWithDefaultData();
                        }
                    });
                })
                .exceptionally(throwable -> {
                    Platform.runLater(() -> {
                        System.err.println("Failed to fetch bill data: " + throwable.getMessage());
                        throwable.printStackTrace();
                        updateUIWithDefaultData();
                    });
                    return null;
                });
        } catch (Exception e) {
            System.err.println("Exception in fetchAndUpdateBillData: " + e.getMessage());
            e.printStackTrace();
            Platform.runLater(() -> updateUIWithDefaultData());
        }
    }
    
    private void updateUIWithBillData(ResidentElectricityModel billData) {
        try {
            System.out.println("=== UPDATING UI WITH BILL DATA ===");
            System.out.println("Amount: " + billData.getElectricityBillAmount());
            System.out.println("Date: " + billData.getElectricityBillDate());
            
            // Update main screen texts
            if (residentMaintenanceDate != null) {
                String dateText = (billData.getElectricityBillDate() != null && 
                                  !billData.getElectricityBillDate().equals("No due date") && 
                                  !billData.getElectricityBillDate().isEmpty()) 
                    ? "Due Date : " + billData.getElectricityBillDate() 
                    : "Due Date : Not Set";
                residentMaintenanceDate.setText(dateText);
                System.out.println("Updated main date text: " + dateText);
            }
            
            // Update popup texts
            if (electricityAmountText != null) {
                String amountText = (billData.getElectricityBillAmount() != null && billData.getElectricityBillAmount() > 0) 
                    ? "Electricity Amount : ₹ " + billData.getElectricityBillAmount().intValue()
                    : "Electricity Amount : Not Set";
                electricityAmountText.setText(amountText);
                System.out.println("Updated amount text: " + amountText);
            }
            
            if (dueDateText != null) {
                String dateText = (billData.getElectricityBillDate() != null && 
                                  !billData.getElectricityBillDate().equals("No due date") && 
                                  !billData.getElectricityBillDate().isEmpty()) 
                    ? "Due Date : " + billData.getElectricityBillDate() 
                    : "Due Date : Not Set";
                dueDateText.setText(dateText);
                System.out.println("Updated popup date text: " + dateText);
            }
        } catch (Exception e) {
            System.err.println("Exception in updateUIWithBillData: " + e.getMessage());
            e.printStackTrace();
            updateUIWithDefaultData();
        }
    }
    
    private void updateUIWithDefaultData() {
        try {
            System.out.println("Updating UI with default data");
            if (residentMaintenanceDate != null) {
                residentMaintenanceDate.setText("Due Date : Not Available");
            }
            if (electricityAmountText != null) {
                electricityAmountText.setText("Electricity Amount : Not Available");
            }
            if (dueDateText != null) {
                dueDateText.setText("Due Date : Not Available");
            }
        } catch (Exception e) {
            System.err.println("Exception in updateUIWithDefaultData: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public StackPane createResidentElectricityScene(Runnable electricityHomePageResident) {
        try {
            // Initialize UI elements first
            residentElectricityBillText = new Text();
            updateBillStatus(residentElectricityBillText); // Initial status set here

            // Date text - will be updated after fetching data
            residentMaintenanceDate = new Text("Due Date : Loading..."); 
            residentMaintenanceDate.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");

            // Fetch bill data after UI initialization - with delay to ensure UI is ready
            Platform.runLater(() -> {
                try {
                    Thread.sleep(1000); // 1 second delay to ensure UI is fully initialized
                    fetchAndUpdateBillData();
                } catch (InterruptedException e) {
                    System.err.println("Sleep interrupted: " + e.getMessage());
                    fetchAndUpdateBillData();
                }
            });

            // flatDetails VBox
            VBox residentElectricityDetailsVBox = new VBox(50, residentElectricityBillText, residentMaintenanceDate);
            residentElectricityDetailsVBox.setPrefWidth(800);
            residentElectricityDetailsVBox.setPrefHeight(150);
            residentElectricityDetailsVBox.setMaxWidth(800);
            residentElectricityDetailsVBox.setMinWidth(800);
            residentElectricityDetailsVBox.setPadding(new Insets(20));
            residentElectricityDetailsVBox.setSpacing(10);
            residentElectricityDetailsVBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" + "-fx-border-color: white;" + "-fx-border-width: 2px;" + "-fx-border-radius: 15px;" + "-fx-background-radius: 15px;");
            residentElectricityDetailsVBox.setAlignment(Pos.CENTER_LEFT);

            // maintenance image at the center of image bar
            Image residentElectricityImage = new Image(getClass().getResource("/Assets/new Electricity.png").toExternalForm());
            ImageView electricityImageView = new ImageView(residentElectricityImage);
            electricityImageView.setPreserveRatio(true);
            electricityImageView.setSmooth(true);
            electricityImageView.setFitWidth(300);
            electricityImageView.setFitHeight(800);
            electricityImageView.setScaleY(1.4);

            VBox electricityImageVBox = new VBox(electricityImageView);
            electricityImageVBox.setAlignment(Pos.CENTER);
            VBox.setVgrow(electricityImageVBox, Priority.ALWAYS);
            electricityImageVBox.setStyle("-fx-background-color: transparent;");

            // Adding a back button
            Button backTOHomePageButton = new Button("Back");
            backTOHomePageButton.setStyle(
                    "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:#c5f0f2ff; -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;-fx-text-fill :black");
            backTOHomePageButton.setPadding(new Insets(5, 15, 5, 15));
            backTOHomePageButton.setOnAction(e -> {
                System.out.println("Back button clicked!");
                electricityHomePageResident.run();
            });

            // imageBar VBox
            VBox residentImageBarVBox = new VBox(electricityImageVBox, backTOHomePageButton);
            residentImageBarVBox.setPrefWidth(400);
            residentImageBarVBox.setAlignment(Pos.CENTER);
            residentImageBarVBox.setMaxHeight(Double.MAX_VALUE);
            VBox.setVgrow(electricityImageVBox, Priority.ALWAYS);
            VBox.setVgrow(residentImageBarVBox, Priority.ALWAYS);
            residentImageBarVBox.setStyle("-fx-background-color: Lavender,rgb(197, 240, 242));");
            residentImageBarVBox.setPadding(new Insets(0, 0, 5, 0));

            // flatList vbox
            VBox residentElectricityFlatListVBox = new VBox(30, residentElectricityDetailsVBox);
            residentElectricityFlatListVBox.setPadding(new Insets(40));
            residentElectricityFlatListVBox.setAlignment(Pos.TOP_CENTER);
            VBox.setVgrow(residentElectricityFlatListVBox, Priority.ALWAYS);
            residentElectricityDetailsVBox.setPrefWidth(380);
            residentElectricityDetailsVBox.setMinHeight(200);
            VBox.setVgrow(residentElectricityDetailsVBox, Priority.NEVER);

            // flatListVbox Wrap in scroll pane
            ScrollPane residentElectricityListScrollPane = new ScrollPane(residentElectricityFlatListVBox);
            residentElectricityListScrollPane.setFitToHeight(true);
            residentElectricityListScrollPane.setFitToWidth(true);
            residentElectricityListScrollPane.setMaxHeight(Double.MAX_VALUE);
            VBox.setVgrow(residentElectricityFlatListVBox, Priority.ALWAYS);
            HBox.setHgrow(residentElectricityListScrollPane, Priority.ALWAYS);
            residentElectricityListScrollPane.setStyle("-fx-background: white; -fx-background-color: white;" +
                    "-fx-border-color: #ffffffff;" +
                    "-fx-border-width: 2px;" +
                    "-fx-border-radius: 10px;" +
                    "-fx-background-radius: 10px;"
            );

            HBox residentPageHBox = new HBox(residentImageBarVBox, residentElectricityListScrollPane);
            residentPageHBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
            residentPageHBox.setMaxHeight(Double.MAX_VALUE);
            HBox.setHgrow(residentImageBarVBox, Priority.ALWAYS);
            HBox.setHgrow(residentElectricityListScrollPane, Priority.ALWAYS);

            VBox electricityFlatListRootVBox = new VBox(residentPageHBox);
            electricityFlatListRootVBox.setStyle("-fx-background-color: LAVENDER;");
            electricityFlatListRootVBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
            VBox.setVgrow(residentPageHBox, Priority.ALWAYS);

            // STACKPANE ROOT FOR OVERLAY
            StackPane residentMaintananceRootStackPane = new StackPane(electricityFlatListRootVBox);

            // 1st PopUp OVERLAY SETUP
            VBox residentElectricityPopUpVBox = new VBox(20);
            residentElectricityPopUpVBox.setPadding(new Insets(50));
            residentElectricityPopUpVBox.setStyle(" -fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" + "-fx-background-radius: 15;" + "-fx-border-radius: 15px;" + "-fx-border-color: white;" + "-fx-border-width: 2;" + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");
            residentElectricityPopUpVBox.setMaxWidth(800);
            residentElectricityPopUpVBox.setMinHeight(400);
            residentElectricityPopUpVBox.setAlignment(Pos.CENTER);

            // view electricity bill photo
            Button ViewElectricityBillPhotoButton = new Button("View Electricity Bill");
            ViewElectricityBillPhotoButton.setPrefWidth(200);
            ViewElectricityBillPhotoButton.setPrefHeight(200);
            ViewElectricityBillPhotoButton.setStyle(
                    " -fx-background-color: transparent;" +
                            "-fx-focus-color: #ffffffff;" +
                            "-fx-font-size: 18px;" +
                            "-fx-text-fill: black;" +
                            "-fx-background-radius: 10px;" +
                            "-fx-border-radius: 10px;" +
                            "-fx-border-color: #ffffffff;" +
                            "-fx-border-width: 2px;" +
                            "-fx-padding: 10 10 10 10;" +
                            "-fx-font-family: Comic Sans MS"
            );

            // electricity amount text - will be updated with fetched data
            electricityAmountText = new Text("Electricity Amount : Loading...");
            electricityAmountText.setStyle(
                    "-fx-font-weight: bold;" +
                            "-fx-font-size: 24px;" +
                            "-fx-text-fill: DARKSLATEGRAY;" +
                            "-fx-font-family: Comic Sans MS");

            // electricity amount vbox
            VBox electricityAmountVBox = new VBox(electricityAmountText);
            electricityAmountVBox.setPrefWidth(300);
            electricityAmountVBox.setPrefHeight(80);
            electricityAmountVBox.setAlignment(Pos.CENTER_LEFT);
            electricityAmountVBox.setStyle(
                    "-fx-background-radius: 10px;" +
                            "-fx-border-radius: 10px;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 2px;" +
                            "-fx-padding: 20 20 20 20;" +
                            "-fx-background-color: white;"
            );

            // due date text - will be updated with fetched data
            dueDateText = new Text("Due Date : Loading...");
            dueDateText.setStyle(
                    "-fx-font-weight: bold;" +
                            "-fx-font-size: 24px;" +
                            "-fx-text-fill: DARKSLATEGRAY;" +
                            "-fx-font-family: Comic Sans MS");

            // due date vbox
            VBox dueDateVBox = new VBox(dueDateText);
            dueDateVBox.setPrefWidth(300);
            dueDateVBox.setPrefHeight(80);
            dueDateVBox.setAlignment(Pos.CENTER_LEFT);
            dueDateVBox.setStyle(
                    "-fx-background-radius: 10px;" +
                            "-fx-border-radius: 10px;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 2px;" +
                            "-fx-padding: 20 20 20 20;" +
                            "-fx-background-color: white;"
            );

            // pay button
            Button payElectricityButton = new Button("Pay Electricity Bill");
            payElectricityButton.setPrefWidth(300);
            payElectricityButton.setPrefHeight(40);
            payElectricityButton.setStyle(
                    " -fx-background-color: green;" +
                            "-fx-focus-color: #ffffffff;" +
                            "-fx-font-size: 18px;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 10px;" +
                            "-fx-border-radius: 10px;" +
                            "-fx-border-color: green;" +
                            "-fx-border-width: 2px;" +
                            "-fx-padding: 4 10 4 10;" +
                            "-fx-font-family: Comic Sans MS");

            // cancel button
            Button cancelButton = new Button("Cancel");
            cancelButton.setPrefWidth(300);
            cancelButton.setPrefHeight(40);
            cancelButton.setStyle(
                    " -fx-background-color: red;" +
                            "-fx-focus-color: #ffffffff;" +
                            "-fx-font-size: 18px;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 10px;" +
                            "-fx-border-radius: 10px;" +
                            "-fx-border-color: red;" +
                            "-fx-border-width: 2px;" +
                            "-fx-padding: 4 10 4 10;" +
                            "-fx-font-family: Comic Sans MS"
            );

            residentElectricityPopUpVBox.getChildren().addAll(ViewElectricityBillPhotoButton, electricityAmountVBox, dueDateVBox, payElectricityButton, cancelButton);

            // overlay vbox
            VBox residentElectricityOverlayVBox = new VBox(residentElectricityPopUpVBox);
            residentElectricityOverlayVBox.setAlignment(Pos.CENTER);
            residentElectricityOverlayVBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
            residentElectricityOverlayVBox.setVisible(false);
            residentMaintananceRootStackPane.getChildren().add(residentElectricityOverlayVBox);

            // Add success image
            ImageView successImage = new ImageView(new Image(getClass().getResource("/Assets/Payment Succesful.png").toExternalForm()));
            successImage.setFitWidth(400);
            successImage.setFitHeight(800);
            successImage.setPreserveRatio(true);

            // Success message
            Text successMessage = new Text("Electricity Bill Paid Successfully!");
            successMessage.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: green; -fx-font-family: Comic Sans MS");

            // OK Button
            Button okButton = new Button("OK");
            okButton.setPrefWidth(150);
            okButton.setStyle(
                    "-fx-background-color: green;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 16px;" +
                            "-fx-background-radius: 10px;" +
                            "-fx-font-family: Comic Sans MS;"
            );

            // SECOND POPUP Payment Successful
            VBox paymentSuccessPopupVBox = new VBox(20, successImage, successMessage, okButton);
            paymentSuccessPopupVBox.setPadding(new Insets(40));
            paymentSuccessPopupVBox.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-background-radius: 15;" +
                            "-fx-border-radius: 15;" +
                            "-fx-border-color: green;" +
                            "-fx-border-width: 2;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);"
            );
            paymentSuccessPopupVBox.setAlignment(Pos.CENTER);
            paymentSuccessPopupVBox.setMaxWidth(500);
            paymentSuccessPopupVBox.setMinHeight(300);

            // Container for second popup
            VBox paymentSuccessOverlayVBox = new VBox(paymentSuccessPopupVBox);
            paymentSuccessOverlayVBox.setAlignment(Pos.CENTER);
            paymentSuccessOverlayVBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
            paymentSuccessOverlayVBox.setVisible(false);
            residentMaintananceRootStackPane.getChildren().add(paymentSuccessOverlayVBox);

            // resident maintenance detail vbox on press logic
            residentElectricityDetailsVBox.setOnMouseClicked(e -> {
                System.out.println("residentElectricityDetailsVBox button clicked!");
                // Scale animation on the clicked box
                ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100), residentElectricityDetailsVBox);
                scaleDown.setToX(0.95);
                scaleDown.setToY(0.95);
                ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), residentElectricityDetailsVBox);
                scaleUp.setToX(1.0);
                scaleUp.setToY(1.0);
                scaleDown.setOnFinished(event -> scaleUp.play());
                scaleDown.play();

                // Show overlay popup
                residentElectricityOverlayVBox.setVisible(true);
                residentElectricityOverlayVBox.setOpacity(0);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(200), residentElectricityOverlayVBox);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();

                residentElectricityPopUpVBox.setTranslateY(400); // start offscreen
                TranslateTransition slideUp = new TranslateTransition(Duration.millis(300), residentElectricityPopUpVBox);
                slideUp.setFromY(400);
                slideUp.setToY(0);
                slideUp.play();
            });

            // On press logic of Pay Electricity
            payElectricityButton.setOnAction(e -> {
                try {
                    // Update payment status in Firebase
                    ResidentElectricityController.markBillAsPaid(currentFlatNo);
                    
                    // Hide first popup
                    residentElectricityOverlayVBox.setVisible(false);
                    // Show second confirmation popup
                    paymentSuccessOverlayVBox.setVisible(true);
                    paymentSuccessOverlayVBox.setOpacity(0);
                    FadeTransition fadeInSuccess = new FadeTransition(Duration.millis(300), paymentSuccessOverlayVBox);
                    fadeInSuccess.setFromValue(0);
                    fadeInSuccess.setToValue(1);
                    fadeInSuccess.play();
                } catch (Exception ex) {
                    System.err.println("Error in pay button action: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });

            // cancel Button on press logic
            cancelButton.setOnAction(e -> {
                // Slide the bottom sheet down and then hide
                TranslateTransition slideDown = new TranslateTransition(Duration.millis(300), residentElectricityPopUpVBox);
                slideDown.setFromY(0);
                slideDown.setToY(400);
                FadeTransition fadeOut = new FadeTransition(Duration.millis(200), residentElectricityOverlayVBox);
                fadeOut.setFromValue(1);
                fadeOut.setToValue(0);
                slideDown.setOnFinished(ev -> residentElectricityOverlayVBox.setVisible(false));
                slideDown.play();
                fadeOut.play();
            });

            // Keep the original image viewing logic as requested
            ViewElectricityBillPhotoButton.setOnAction(e -> {
                Image billImage = new Image(getClass().getResource("/Assets/electricity Bill Photo 201.jpeg").toExternalForm());
                ImageView billImageView = new ImageView(billImage);
                billImageView.setFitWidth(300);
                billImageView.setPreserveRatio(true);

                // Enable zoom with scroll
                billImageView.setOnScroll(event -> {
                    double zoomFactor = 1.05;
                    if (event.getDeltaY() < 0) {
                        zoomFactor = 0.95; // Zoom out
                    }
                    billImageView.setScaleX(billImageView.getScaleX() * zoomFactor);
                    billImageView.setScaleY(billImageView.getScaleY() * zoomFactor);
                    event.consume(); // Prevent further propagation
                });

                Button closeViewBillButton = new Button("Close");
                closeViewBillButton.setStyle(
                        "-fx-background-color: red;" +
                                "-fx-text-fill: white;" +
                                "-fx-font-size: 16px;" +
                                "-fx-background-radius: 10px;" +
                                "-fx-font-family: Comic Sans MS;");

                VBox popupBox = new VBox(20, billImageView, closeViewBillButton);
                popupBox.setAlignment(Pos.CENTER);
                popupBox.setPadding(new Insets(20));
                popupBox.setStyle("-fx-background-color: white; -fx-background-radius: 10px;");

                VBox overlay = new VBox(popupBox);
                overlay.setStyle("-fx-background-color: rgba(0,0,0,0.4);");
                overlay.setAlignment(Pos.CENTER);
                overlay.setMaxWidth(800);
                overlay.setMinHeight(900);

                residentMaintananceRootStackPane.getChildren().add(overlay);
                closeViewBillButton.setOnAction(ev -> residentMaintananceRootStackPane.getChildren().remove(overlay));
            });

            // ok Button on press navigate back to the residentMaintenanceDetailsVBox logic
            okButton.setOnAction(event -> {
                FadeTransition fadeOutSuccess = new FadeTransition(Duration.millis(300), paymentSuccessOverlayVBox);
                fadeOutSuccess.setFromValue(1);
                fadeOutSuccess.setToValue(0);
                fadeOutSuccess.setOnFinished(ev -> {
                    paymentSuccessOverlayVBox.setVisible(false);
                    isPaid = true; // Set the flag to true on successful payment
                    updateBillStatus(residentElectricityBillText); // Update the displayed status
                });
                fadeOutSuccess.play();
            });

            return residentMaintananceRootStackPane;
            
        } catch (Exception e) {
            System.err.println("Exception in createResidentElectricityScene: " + e.getMessage());
            e.printStackTrace();
            
            // Return a basic StackPane with error message if something goes wrong
            Text errorText = new Text("Error loading electricity page. Please try again.");
            errorText.setStyle("-fx-font-size: 24px; -fx-fill: red; -fx-font-weight: bold;");
            VBox errorBox = new VBox(errorText);
            errorBox.setAlignment(Pos.CENTER);
            return new StackPane(errorBox);
        }
    }
}