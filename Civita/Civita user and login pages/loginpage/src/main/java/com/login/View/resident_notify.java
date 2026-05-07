
package com.login.View;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class resident_notify  {

    Scene residentNotify1Scene;
    public void setResidentNotify1Scene(Scene residentNotify1Scene) {
        this.residentNotify1Scene = residentNotify1Scene;
    }


    public void setResidentNotifyPrimaryStage(Stage residentNotifyPrimaryStage) {
        this.residentNotifyPrimaryStage = residentNotifyPrimaryStage;
    }


    Stage residentNotifyPrimaryStage;
   

   public StackPane createResidentNotifyScene(Runnable notifyBackToResidentHomePage){
        VBox root = new VBox(30);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: LAVENDER;");

        Label title = new Label(" Society Alerts & Notices");
        title.setStyle(
                "-fx-font-size: 48px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        title.setAlignment(Pos.CENTER);

        VBox notificationList = new VBox(20);
        notificationList.setPadding(new Insets(10));
        notificationList.setStyle("-fx-background-color: LAVENDER;");

        //  FETCH FROM FIRESTORE 
        try {
             Firestore db = FirestoreClient.getFirestore();
           List<QueryDocumentSnapshot> documents = db.collection("notifications")
            .orderBy("timestamp", com.google.cloud.firestore.Query.Direction.DESCENDING) // This is the key change
            .get()
            .get()
            .getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                String message = doc.getString("message");
                String timestamp = doc.getString("timestamp");

                if (message != null && timestamp != null) {
                    HBox card = new HBox(30);
                    card.setPadding(new Insets(20));
                    card.setAlignment(Pos.CENTER_LEFT);
                    card.setStyle(
                            "-fx-background-color: white;" +
                                    "-fx-background-radius: 12;" +
                                    "-fx-border-radius: 12;" +
                                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 8, 0, 4, 4);");

                    Label descLabel = new Label(message);
                    descLabel.setStyle(
                            "-fx-font-size: 20px; -fx-text-fill: DARKSLATEGRAY;  -fx-font-family: Comic Sans MS");
                    descLabel.setWrapText(true);
                    descLabel.setMaxWidth(820);

                    Label timeLabel = new Label(timestamp);
                    timeLabel.setStyle(
                            "-fx-font-size: 15px; -fx-text-fill: DARKSLATEGRAY;  -fx-font-family: Comic Sans MS");

                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    card.getChildren().addAll(descLabel, spacer, timeLabel);
                    notificationList.getChildren().add(card);
                }
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        Button refreshButton = new Button("🔄 Refresh");
        refreshButton.setStyle(
                "-fx-font-size: 18px; -fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242)); -fx-font-family: Comic Sans MS;");
        refreshButton.setOnAction(e -> {
            notificationList.getChildren().clear(); // clear old list
            residentNotifyPrimaryStage.close(); // restart stage (lazy refresh)
           // start(new Stage());
        });

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(notificationList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(700);
        scrollPane.setStyle(
                "-fx-background: transparent;" +
                        "-fx-background-color: transparent;" +
                        "-fx-control-inner-background: transparent;");

        notificationList.setStyle("-fx-background-color: LAVENDER;");
        notificationList.setFillWidth(true);

        // Add the refresh button to a HBox for proper alignment
        HBox topButtons = new HBox(refreshButton);
        topButtons.setAlignment(Pos.CENTER);

        root.getChildren().addAll(title, topButtons, scrollPane);

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
            notifyBackToResidentHomePage.run();
        });

        // Use a StackPane to place the back button on top of the main content
        StackPane stackPane = new StackPane(root, uploadSellBackButton);
        StackPane.setAlignment(uploadSellBackButton, Pos.BOTTOM_LEFT);
        StackPane.setMargin(uploadSellBackButton, new Insets(30));
        return stackPane;

   
    }

}