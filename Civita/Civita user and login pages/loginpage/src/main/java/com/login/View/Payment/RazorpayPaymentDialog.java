package com.login.View.Payment;

import com.login.Controller.PaymentController;
import com.login.Utils.UserSession;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * A dialog for processing Razorpay payments within the JavaFX application.
 * Provides a user interface for maintenance and electricity bill payments.
 */
public class RazorpayPaymentDialog {

    public enum PaymentType {
        MAINTENANCE,
        ELECTRICITY
    }

    private Stage dialogStage;
    private boolean paymentInitiated = false;
    private String orderId;
    private PaymentResultCallback callback;

    /**
     * Shows the payment dialog.
     * @param parentStage The parent stage
     * @param paymentType Type of payment (MAINTENANCE or ELECTRICITY)
     * @param amount Amount in rupees
     * @param flatNo Flat number
     * @param callback Callback for payment result
     */
    public void show(Stage parentStage, PaymentType paymentType, double amount, String flatNo, PaymentResultCallback callback) {
        this.callback = callback;
        
        dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.initStyle(StageStyle.DECORATED);
        dialogStage.setTitle(paymentType == PaymentType.MAINTENANCE ? "Pay Maintenance" : "Pay Electricity Bill");
        dialogStage.setResizable(false);

        VBox content = createDialogContent(paymentType, amount, flatNo);
        Scene scene = new Scene(content, 450, 400);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    private VBox createDialogContent(PaymentType paymentType, double amount, String flatNo) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, white, #c5f0f2);");

        // Header
        Text header = new Text(paymentType == PaymentType.MAINTENANCE ? "Maintenance Payment" : "Electricity Payment");
        header.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: #2c3e50; -fx-font-family: 'Comic Sans MS';");

        // Payment details card
        VBox detailsCard = new VBox(15);
        detailsCard.setPadding(new Insets(20));
        detailsCard.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 15px;" +
            "-fx-border-radius: 15px;" +
            "-fx-border-color: #e0e0e0;" +
            "-fx-border-width: 1px;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);"
        );

        // Flat number
        HBox flatRow = createDetailRow("Flat Number:", flatNo);
        
        // Amount
        HBox amountRow = createDetailRow("Amount:", "Rs. " + String.format("%.2f", amount));
        amountRow.lookup(".value-label").setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");

        // User email
        String email = UserSession.getInstance().getEmail();
        HBox emailRow = createDetailRow("Email:", email != null ? email : "Not available");

        // Payment method
        HBox methodRow = createDetailRow("Payment via:", "Razorpay");

        detailsCard.getChildren().addAll(flatRow, amountRow, emailRow, methodRow);

        // Buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button payButton = new Button("Pay Now");
        payButton.setPrefWidth(150);
        payButton.setPrefHeight(45);
        payButton.setStyle(
            "-fx-background-color: #27ae60;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10px;" +
            "-fx-cursor: hand;" +
            "-fx-font-family: 'Comic Sans MS';"
        );
        payButton.setOnMouseEntered(e -> payButton.setStyle(
            "-fx-background-color: #2ecc71;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10px;" +
            "-fx-cursor: hand;" +
            "-fx-font-family: 'Comic Sans MS';"
        ));
        payButton.setOnMouseExited(e -> payButton.setStyle(
            "-fx-background-color: #27ae60;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10px;" +
            "-fx-cursor: hand;" +
            "-fx-font-family: 'Comic Sans MS';"
        ));

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(150);
        cancelButton.setPrefHeight(45);
        cancelButton.setStyle(
            "-fx-background-color: #e74c3c;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10px;" +
            "-fx-cursor: hand;" +
            "-fx-font-family: 'Comic Sans MS';"
        );
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle(
            "-fx-background-color: #c0392b;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10px;" +
            "-fx-cursor: hand;" +
            "-fx-font-family: 'Comic Sans MS';"
        ));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle(
            "-fx-background-color: #e74c3c;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10px;" +
            "-fx-cursor: hand;" +
            "-fx-font-family: 'Comic Sans MS';"
        ));

        // Pay button action
        payButton.setOnAction(e -> {
            payButton.setDisable(true);
            payButton.setText("Processing...");
            
            // Process payment in background thread
            new Thread(() -> {
                try {
                    if (paymentType == PaymentType.MAINTENANCE) {
                        orderId = PaymentController.initiateMaintenancePayment(amount, flatNo, null, null);
                    } else {
                        orderId = PaymentController.initiateElectricityPayment(amount, flatNo, null, null);
                    }
                    
                    Platform.runLater(() -> {
                        if (orderId != null) {
                            paymentInitiated = true;
                            showPaymentInstructions(amount);
                        } else {
                            showError("Failed to create payment order. Please try again.");
                            payButton.setDisable(false);
                            payButton.setText("Pay Now");
                        }
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        showError("Error: " + ex.getMessage());
                        payButton.setDisable(false);
                        payButton.setText("Pay Now");
                    });
                }
            }).start();
        });

        // Cancel button action
        cancelButton.setOnAction(e -> {
            if (callback != null) {
                callback.onPaymentCancelled();
            }
            dialogStage.close();
        });

        buttonBox.getChildren().addAll(payButton, cancelButton);

        // Secure payment note
        Text secureNote = new Text("Secured by Razorpay");
        secureNote.setStyle("-fx-font-size: 12px; -fx-fill: #7f8c8d;");

        root.getChildren().addAll(header, detailsCard, buttonBox, secureNote);
        return root;
    }

    private HBox createDetailRow(String label, String value) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Label labelText = new Label(label);
        labelText.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d; -fx-font-family: 'Comic Sans MS';");
        labelText.setMinWidth(100);

        Label valueText = new Label(value);
        valueText.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-family: 'Comic Sans MS';");
        valueText.getStyleClass().add("value-label");

        row.getChildren().addAll(labelText, valueText);
        return row;
    }

    private void showPaymentInstructions(double amount) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Initiated");
        alert.setHeaderText("Complete Payment in Browser");
        alert.setContentText(
            "A payment page has been opened in your browser.\n\n" +
            "Amount: Rs. " + String.format("%.2f", amount) + "\n" +
            "Order ID: " + orderId + "\n\n" +
            "Please complete the payment in the browser.\n" +
            "Once done, click 'I've Paid' to confirm."
        );

        ButtonType paidButton = new ButtonType("I've Paid", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(paidButton, cancelButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == paidButton) {
                if (callback != null) {
                    callback.onPaymentSuccess(orderId);
                }
                dialogStage.close();
            } else {
                if (callback != null) {
                    callback.onPaymentCancelled();
                }
                dialogStage.close();
            }
        });
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Payment Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Callback interface for payment results.
     */
    public interface PaymentResultCallback {
        void onPaymentSuccess(String orderId);
        void onPaymentCancelled();
    }

    /**
     * Static method to show maintenance payment dialog.
     * @param parentStage Parent stage
     * @param amount Amount in rupees
     * @param flatNo Flat number
     * @param callback Payment result callback
     */
    public static void showMaintenancePayment(Stage parentStage, double amount, String flatNo, PaymentResultCallback callback) {
        RazorpayPaymentDialog dialog = new RazorpayPaymentDialog();
        dialog.show(parentStage, PaymentType.MAINTENANCE, amount, flatNo, callback);
    }

    /**
     * Static method to show electricity payment dialog.
     * @param parentStage Parent stage
     * @param amount Amount in rupees
     * @param flatNo Flat number
     * @param callback Payment result callback
     */
    public static void showElectricityPayment(Stage parentStage, double amount, String flatNo, PaymentResultCallback callback) {
        RazorpayPaymentDialog dialog = new RazorpayPaymentDialog();
        dialog.show(parentStage, PaymentType.ELECTRICITY, amount, flatNo, callback);
    }
}
