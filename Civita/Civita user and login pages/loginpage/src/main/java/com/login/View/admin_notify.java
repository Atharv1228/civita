
package com.login.View;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import com.login.services.FirebaseInitialize;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class admin_notify {

    Scene adminNotification1Scene;
    public void setAdminNotification1Scene(Scene adminNotification1Scene) {
        this.adminNotification1Scene = adminNotification1Scene;
    }

    public void setAdminNotificationPrimaryStage(Stage adminNotificationPrimaryStage) {
        this.adminNotificationPrimaryStage = adminNotificationPrimaryStage;
    }

    Stage adminNotificationPrimaryStage;

    private final VBox notificationList = new VBox(15);
    private final Firestore db = FirebaseInitialize.getDB();

      public StackPane createAdminNotificationScene(Runnable adminNotificationBacktoAdminHomePage){

    
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:LAVENDER;");

        Label title = new Label("Admin Notification");
        title.setStyle(
                "-fx-font-size: 48px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(30, 0, 10, 0));
        root.setTop(title);

        ScrollPane scrollPane = new ScrollPane(notificationList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(0, 20, 0, 20));
        scrollPane.setPrefWidth(800);
        notificationList.setPadding(new Insets(20, 0, 20, 0));
        notificationList.setAlignment(Pos.TOP_CENTER);

        scrollPane.setStyle(
                "-fx-background: transparent;" +
                        "-fx-background-color: transparent;" +
                        "-fx-background-insets: 10;" +
                        "-fx-background-radius: 25;" +
                        "-fx-border-radius: 25;" +
                        "-fx-padding: 10;");
        root.setCenter(scrollPane);

        HBox bottomBox = new HBox();
        bottomBox.setPadding(new Insets(20, 0, 40, 0));
        bottomBox.setAlignment(Pos.CENTER);

        Button postButton = new Button("Post New Notification");
        postButton.setStyle(
                "-fx-font-family: Comic Sans MS;" +
                        "-fx-font-size: 24px;" +
                        "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +
                        "-fx-text-fill: DARKSLATEGRAY;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 8 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0.3, 2, 3);" +
                        "-fx-cursor: hand;");

        postButton.setOnMouseEntered(e -> {
            postButton.setScaleX(1.08);
            postButton.setScaleY(1.08);
        });
        postButton.setOnMouseExited(e -> {
            postButton.setScaleX(1.0);
            postButton.setScaleY(1.0);
        });

        postButton.setOnAction(e -> openPostDialog());
        bottomBox.getChildren().add(postButton);
        root.setBottom(bottomBox);

        // Back button
        Button uploadSellBackButton = new Button("Back");
        uploadSellBackButton.setOnMouseEntered(ev -> {
            
            uploadSellBackButton.setScaleX(1.05);
            uploadSellBackButton.setScaleY(1.05);
        });
        uploadSellBackButton.setOnMouseExited(ev -> {
            uploadSellBackButton.setScaleX(1.0);
            uploadSellBackButton.setScaleY(1.0);
        });
        uploadSellBackButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
        uploadSellBackButton.setPadding(new Insets(5, 15, 5, 15));

        uploadSellBackButton.setOnAction(e -> {
            System.out.println("Sell Flat Back button clicked!");
            adminNotificationBacktoAdminHomePage.run();
        });
        
        // Use a StackPane to place the back button on top of the main content
        StackPane stackPane = new StackPane(root, uploadSellBackButton);
        StackPane.setAlignment(uploadSellBackButton, Pos.BOTTOM_LEFT);
        StackPane.setMargin(uploadSellBackButton, new Insets(30));
        return stackPane;


    }

    private void addNotification(String message, String timestamp, String docId) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(15));
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0.3, 1, 2);");
        card.setMaxWidth(1000);
        card.setMinWidth(1000);

        HBox messageRow = new HBox(10);
        messageRow.setAlignment(Pos.TOP_LEFT);

        Label newLabel = new Label("NEW");
        newLabel.setStyle(
                "-fx-font-size: 10px;" +
                        "-fx-font-family: Comic Sans MS;" +
                        "-fx-background-color: lavender;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 3 8;" +
                        "-fx-text-fill: darkslategray;");

        Label msgText = new Label(message);
        msgText.setWrapText(true);
        msgText.setMaxWidth(600);
        msgText.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-family: Comic Sans MS;" +
                        "-fx-text-fill: darkslategray;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button deleteButton = new Button("🗑️");
        deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-font-size: 14px; -fx-font-family: Comic Sans MS; -fx-text-fill: DARKSLATEGREY;");
        deleteButton.setOnAction(e -> {
            notificationList.getChildren().remove(card);
            if (docId != null) {
                db.collection("notifications").document(docId).delete();
            }
        });

        messageRow.getChildren().addAll(newLabel, msgText, spacer, deleteButton);

        Label timeLabel = new Label("Posted: " + timestamp);
        timeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: DARKSLATEGREY; -fx-font-family: Comic Sans MS");
        timeLabel.setTextFill(Color.web("#7A7A7A"));

        card.getChildren().addAll(messageRow, timeLabel);
        notificationList.getChildren().add(card);
    }

    private void openPostDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("New Notification");
        dialog.setResizable(false);

        VBox form = new VBox(15);
        form.setPadding(new Insets(30));
        form.setAlignment(Pos.CENTER);
        form.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));");

        Label prompt = new Label("Enter Notification:");
        prompt.setStyle("-fx-font-size: 24px; -fx-text-fill: DARKSLATEGREY; -fx-font-family: Comic Sans MS");

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPromptText("e.g. Water supply will be unavailable from 10AM to 2PM.");
        textArea.setPrefRowCount(4);
        textArea.setMaxWidth(400);

        Button submit = new Button("Post");
        submit.setStyle(
                "-fx-font-family: Comic Sans MS;" +
                        "-fx-background-color: DARKSLATEGREY;" +
                        "-fx-text-fill: PINK;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 8 20;");
        submit.setOnAction(e -> {
            String text = textArea.getText().trim();
            if (!text.isEmpty()) {
                String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("d MMM yyyy, hh:mm a"));
                Map<String, Object> data = new HashMap<>();
                data.put("message", text);
                data.put("timestamp", time);

                new Thread(() -> {
                    try {
                        DocumentReference ref = db.collection("notifications").add(data).get();
                        javafx.application.Platform.runLater(() -> addNotification(text, time, ref.getId()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }).start();

                dialog.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Message cannot be empty.");
                alert.showAndWait();
            }
        });

        form.getChildren().addAll(prompt, textArea, submit);
        Scene scene = new Scene(form, 500, 250);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void loadNotificationsFromFirestore() {
        try {
            ApiFuture<QuerySnapshot> future = db.collection("notifications")
                    .orderBy("timestamp", Query.Direction.DESCENDING).get();
            List<QueryDocumentSnapshot> docs = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : docs) {
                String message = doc.getString("message");
                String timestamp = doc.getString("timestamp");
                addNotification(message, timestamp, doc.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
