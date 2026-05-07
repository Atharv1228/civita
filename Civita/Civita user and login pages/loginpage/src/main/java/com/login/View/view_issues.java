
package com.login.View;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.login.Controller.view_issuesController; // Import the controller

public class view_issues  {

      Scene adminViewIssueScene;
    Stage adminViewIssuePrimaryStage;

    public void setAdminViewIssueScene(Scene adminViewIssueScene) {
        this.adminViewIssueScene = adminViewIssueScene;
    }

    public void setAdminViewIssuePrimaryStage(Stage adminViewIssuePrimaryStage) {
        this.adminViewIssuePrimaryStage = adminViewIssuePrimaryStage;
    }

    private VBox issueDescriptionOverlay;
    private VBox issuesPopUp;
    private TextArea issueDescriptionField;
    private VBox issuesVBox;
    private view_issuesController controller;

     public StackPane createAdminViewIssueScene(Runnable backToAdminHomePAge){

 

        //FirebaseInitializer.initialize(); // Initialize Firebase when the application starts

        Label view_issues_title = new Label("View Issues");
        view_issues_title.setTextFill(javafx.scene.paint.Color.DARKSLATEGRAY);
        view_issues_title.setStyle("-fx-font-size: 48px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        VBox labelVBox = new VBox(10, view_issues_title);
        labelVBox.setAlignment(Pos.TOP_CENTER);

        VBox vbImage = new VBox(20);
        vbImage.setAlignment(Pos.TOP_CENTER);
        vbImage.setPadding(new Insets(20));

        Image image = new Image("Assets/view issue2.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(350);
        imageView.setPreserveRatio(true);
        vbImage.getChildren().add(imageView);

        issueDescriptionField = new TextArea();
        issueDescriptionField.setPromptText("Issue's Description");
        issueDescriptionField.setPrefWidth(300);
        issueDescriptionField.setPrefHeight(250);
        issueDescriptionField.setEditable(false);
        issueDescriptionField.setWrapText(true);
        issueDescriptionField.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-text-fill: DARKSLATEGRAY;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-border-color: #E6E6FA;" +
                        "-fx-border-width: 2px;" +
                        "-fx-padding: 10 10 10 10;" +
                        "-fx-font-family: Comic Sans MS");

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(300);
        cancelButton.setPrefHeight(40);
        cancelButton.setStyle(
                " -fx-background-color: #ff2323ff;" +
                        "-fx-focus-color: #ffffffff;" +
                        "-fx-font-size: 18px;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-border-color: #ff2323ff;" +
                        "-fx-border-width: 2px;" +
                        "-fx-padding: 4 10 4 10;" +
                        "-fx-font-family: Comic Sans MS");

        cancelButton.setOnAction(e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), issueDescriptionOverlay);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> {
                issueDescriptionOverlay.setVisible(false);
            });
            fadeOut.play();

            TranslateTransition slideDown = new TranslateTransition(Duration.millis(300), issuesPopUp);
            slideDown.setToY(issuesPopUp.getHeight());
            slideDown.play();
        });

        issuesPopUp = new VBox(20, issueDescriptionField, cancelButton);
        issuesPopUp.setPadding(new Insets(50));
        issuesPopUp.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));"
                        + "-fx-background-radius: 15;" + "-fx-border-radius: 15px;"
                        + "-fx-border-color:rgb(117, 117, 117);"
                        + "-fx-border-width: 2;"
                        + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        issuesPopUp.setMaxWidth(800);
        issuesPopUp.setMaxHeight(400);
        issuesPopUp.setAlignment(Pos.CENTER);

        issueDescriptionOverlay = new VBox();
        issueDescriptionOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");
        issueDescriptionOverlay.setVisible(false);
        issueDescriptionOverlay.setOpacity(0);
        issueDescriptionOverlay.setPrefSize(1920, 1080);
        issueDescriptionOverlay.getChildren().add(issuesPopUp);
        issueDescriptionOverlay.setAlignment(Pos.CENTER);

        issuesVBox = new VBox(10);
        issuesVBox.setPrefWidth(850);
        issuesVBox.setAlignment(Pos.TOP_LEFT);
        issuesVBox.setPadding(new Insets(10));
        issuesVBox.setStyle("-fx-border-color: gray;-fx-border-radius: 30; -fx-background-radius: 30;");

        controller = new view_issuesController(issuesVBox, issueDescriptionField, issueDescriptionOverlay,
                issuesPopUp);
        controller.loadIssuesIntoVBox(); // Initial call to load existing issues

        ScrollPane issuesVBoxscrollPane = new ScrollPane(issuesVBox);
        issuesVBoxscrollPane.setFitToWidth(true);
        issuesVBoxscrollPane.setPrefViewportHeight(400);
        issuesVBoxscrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        HBox contentBox = new HBox(40, vbImage, issuesVBoxscrollPane);
        contentBox.setAlignment(Pos.TOP_LEFT);

        VBox outerVBox = new VBox(20);
        outerVBox.setPrefSize(500, 550);
        outerVBox.setPadding(new Insets(30));
        outerVBox.setAlignment(Pos.CENTER_LEFT);
        outerVBox.setPadding(new Insets(20));
        outerVBox.getChildren().add(contentBox);
        outerVBox.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));"
                        + "-fx-border-color: white;" + "-fx-border-radius: 30;"
                        + "-fx-background-radius: 30;" + "-fx-alignment: center;");

        Button viewIssuebackButton = new Button("Back");
        viewIssuebackButton.setOnMouseEntered(ev -> {
            viewIssuebackButton.setScaleX(1.05);
            viewIssuebackButton.setScaleY(1.05);
        });
        viewIssuebackButton.setOnMouseExited(ev -> {
            viewIssuebackButton.setScaleX(1.0);
            viewIssuebackButton.setScaleY(1.0);
        });
        viewIssuebackButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
        viewIssuebackButton.setPadding(new Insets(5, 15, 5, 15));

        viewIssuebackButton.setOnAction(e -> {
            System.out.println("Back button clicked!");
            backToAdminHomePAge.run();
        
        });

        HBox backBox = new HBox(viewIssuebackButton);
        backBox.setAlignment(Pos.BOTTOM_CENTER);

        VBox mainBox = new VBox(20, labelVBox, outerVBox, backBox);
        mainBox.setPadding(new Insets(40, 80, 40, 80));
        mainBox.setAlignment(Pos.TOP_CENTER);

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: lavender;");

        root.getChildren().addAll(mainBox, issueDescriptionOverlay);
        return root;

       
    }

}