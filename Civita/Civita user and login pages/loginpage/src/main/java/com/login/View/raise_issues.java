
//raise_issues Firebase integrated code

package com.login.View;

import javafx.animation.FadeTransition;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.login.Model.Issue;
import com.login.services.IssueService; 
import java.time.LocalDate;

public class raise_issues {

    Scene residentRaiseIssuScene;
    public void setResidentRaiseIssuScene(Scene residentRaiseIssuScene) {
        this.residentRaiseIssuScene = residentRaiseIssuScene;
    }
    public void setRaiseIssuePrimaryStage(Stage raiseIssuePrimaryStage) {
        this.raiseIssuePrimaryStage = raiseIssuePrimaryStage;
    }
    Stage raiseIssuePrimaryStage;

    private Stage currentStage;
    private Label validationMessageLabel;
    private IssueService issueService; // Instance of IssueService

     public StackPane createResidentRaiseIssueScene(Runnable reurnBackToResidentHomePage){

        this.currentStage = raiseIssuePrimaryStage;
        this.issueService = new IssueService(); // Initialize IssueService

        Label raise_issues_title = new Label("Raise Issues");
        raise_issues_title.setTextFill(javafx.scene.paint.Color.DARKSLATEGRAY);
        raise_issues_title.setStyle("-fx-font-size: 48px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        VBox labelVBox = new VBox(10, raise_issues_title);
        labelVBox.setAlignment(Pos.TOP_CENTER);

        VBox raiseIssues_vbImage = new VBox(20);
        raiseIssues_vbImage.setAlignment(Pos.TOP_CENTER);
        raiseIssues_vbImage.setPadding(new Insets(20));

        Image raiseIssues_image = new Image("/Assets/report_issues.png");
        ImageView imageView = new ImageView(raiseIssues_image);
        imageView.setFitWidth(350);
        imageView.setPreserveRatio(true);
        raiseIssues_vbImage.getChildren().add(imageView);

        Label describeProblemLabel = new Label("Describe your issue :");
        describeProblemLabel.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");

        TextArea describeIssuesTextArea = new TextArea();
        describeIssuesTextArea.setMaxWidth(950);
        describeIssuesTextArea.setPrefHeight(350);
        describeIssuesTextArea.setFocusTraversable(false);
        describeIssuesTextArea.setPromptText(" Feel free to share your problems ...");
        describeIssuesTextArea.setStyle("-fx-font-size: 22px;");
        describeIssuesTextArea.setWrapText(true);

        VBox confirmationPopup = new VBox();
        confirmationPopup.setAlignment(Pos.CENTER);
        confirmationPopup.setPadding(new Insets(20));
        confirmationPopup.setSpacing(10);
        confirmationPopup.setStyle(
                "-fx-background-color:linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));"
                        + "-fx-border-color: white; "
                        + "-fx-border-radius: 15; -fx-background-radius: 15;");
        confirmationPopup.setMaxWidth(400);
        confirmationPopup.setMaxHeight(200);
        confirmationPopup.setPrefSize(300, 150);
        confirmationPopup.setVisible(false);

        Label confirmationLabel = new Label("Issue Submitted !!");
        confirmationLabel.setStyle(
                "-fx-font-size: 24px;-fx-text-fill: green;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");

        confirmationPopup.getChildren().add(confirmationLabel);

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");
        datePicker.setPrefWidth(300);
        datePicker.setPrefHeight(80);
        datePicker.setStyle(
                "-fx-background-color: white;" +
                        "-fx-control-inner-background: white;" +
                        "-fx-focus-color: transparent;" +
                        "-fx-faint-focus-color: transparent;" +
                        "-fx-font-size: 18px;" +
                        "-fx-text-fill: DARKSLATEGRAY;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2px;" +
                        "-fx-padding: 4 10 4 10;" +
                        "-fx-font-family: Comic Sans MS");

        validationMessageLabel = new Label("");         //initially this will be empty .. its value will be updated later
        validationMessageLabel.setTextFill(javafx.scene.paint.Color.RED);
        validationMessageLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        validationMessageLabel.setVisible(false);

        Button submitButton = new Button("Submit Issue");
        submitButton.setOnMouseEntered(ev -> {
            submitButton.setScaleX(1.05);
            submitButton.setScaleY(1.05);
        });
        submitButton.setOnMouseExited(ev -> {
            submitButton.setScaleX(1.0);
            submitButton.setScaleY(1.0);
        });
        submitButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
        submitButton.setPadding(new Insets(5, 15, 5, 15));

        submitButton.setOnAction(e -> {
            String issueText = describeIssuesTextArea.getText().trim();
            LocalDate selectedDate = datePicker.getValue();

            if (issueText.isEmpty() || selectedDate == null) {
                validationMessageLabel.setText("Please describe your issue and select a date.");
                validationMessageLabel.setVisible(true);
                confirmationPopup.setVisible(false);

                FadeTransition validationFade = new FadeTransition(Duration.seconds(3),
                        validationMessageLabel);
                validationFade.setFromValue(1.0);
                validationFade.setToValue(0.0);
                validationFade.setOnFinished(event -> validationMessageLabel.setVisible(false));
                validationFade.play();
                return;
            }

            validationMessageLabel.setVisible(false);
            validationMessageLabel.setText("");

            // Create Issue object and add to Firestore
            Issue newIssue = new Issue(issueText, selectedDate);
            issueService.addIssue(newIssue);
            System.out.println("RAISE_ISSUES: Issue submitted and added to service. Clearing form.");

            confirmationPopup.setVisible(true);
            StackPane.setAlignment(confirmationPopup, Pos.CENTER);
            confirmationPopup.toFront();

            FadeTransition fade = new FadeTransition(Duration.seconds(2), confirmationPopup);
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(ev -> {
                confirmationPopup.setVisible(false);
                confirmationPopup.setOpacity(1.0);

                describeIssuesTextArea.clear();
                datePicker.setValue(null);

                // No need to close this stage here and immediately open view_issues.
                // The real-time listener in view_issues will handle the update.
                // currentStage.close(); // Keep this stage open if desired, or handle navigation
                // externally.
            });
            fade.play();
        });

        VBox submitButtonAlignmentBox = new VBox(submitButton, validationMessageLabel);
        submitButtonAlignmentBox.setAlignment(Pos.CENTER);

        VBox raiseIssueVBox = new VBox(20, describeProblemLabel, describeIssuesTextArea, datePicker,
                submitButtonAlignmentBox);
        raiseIssueVBox.setPrefWidth(850);
        raiseIssueVBox.setAlignment(Pos.TOP_LEFT);
        raiseIssueVBox.setPadding(new Insets(10));
        raiseIssueVBox.setStyle(
                "-fx-background-color:rgba(237, 237, 237, 0.9); -fx-border-color: white;-fx-border-radius: 30; -fx-background-radius: 30;");

        HBox contentBox = new HBox(40, raiseIssues_vbImage, raiseIssueVBox);
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

        Button raiseIssuebackButton = new Button("Back");
        raiseIssuebackButton.setOnMouseEntered(ev -> {
            raiseIssuebackButton.setScaleX(1.05);
            raiseIssuebackButton.setScaleY(1.05);
        });
        raiseIssuebackButton.setOnMouseExited(ev -> {
            raiseIssuebackButton.setScaleX(1.0);
            raiseIssuebackButton.setScaleY(1.0);
        });

        raiseIssuebackButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
        raiseIssuebackButton.setPadding(new Insets(5, 15, 5, 15));

        raiseIssuebackButton.setOnAction(e -> {
            System.out.println("Back button clicked! Navigating back to main menu or closing.");
           reurnBackToResidentHomePage.run();
            
        });

        HBox backBox = new HBox(raiseIssuebackButton);
        backBox.setAlignment(Pos.BOTTOM_CENTER);

        VBox mainBox = new VBox(20, labelVBox, outerVBox, backBox);
        mainBox.setPadding(new Insets(40, 80, 40, 80));
        mainBox.setAlignment(Pos.TOP_CENTER);

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: lavender;");
        root.getChildren().add(mainBox);
        root.getChildren().add(confirmationPopup);
        return root;

       
    }
}
