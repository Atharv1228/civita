
//view_issues controller code , firebase Integrated

package com.login.Controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import com.login.Model.Issue;
import com.login.services.IssueService;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class view_issuesController {

    private VBox issuesVBox;
    private TextArea issueDescriptionField;
    private VBox issueDescriptionOverlay;
    private VBox issuesPopUp;
    private IssueService issueService; // Instance of IssueService

    public view_issuesController(VBox issuesVBox, TextArea issueDescriptionField, VBox issueDescriptionOverlay,
            VBox issuesPopUp) {
        this.issuesVBox = issuesVBox;
        this.issueDescriptionField = issueDescriptionField;
        this.issueDescriptionOverlay = issueDescriptionOverlay;
        this.issuesPopUp = issuesPopUp;
        this.issueService = new IssueService(); // Initialize IssueService
        setupRealtimeListener(); // Set up the real-time listener
    }

    // This method will be called when Firestore data changes
    private void updateIssuesDisplay(List<Issue> issues) {
        issuesVBox.getChildren().clear(); // Clear existing issues
        if (issues.isEmpty()) {
            Label noIssuesLabel = new Label("It seems a bit empty here !!");
            noIssuesLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: gray; -fx-font-family: Comic Sans MS;");
            issuesVBox.getChildren().add(noIssuesLabel);
            issuesVBox.setAlignment(Pos.CENTER);
        } else {
            issuesVBox.setAlignment(Pos.TOP_LEFT); // Reset alignment if issues are present
            for (Issue issue : issues) {
                issuesVBox.getChildren().add(createIssueDisplayBox(issue));
            }
        }
    }

    private void setupRealtimeListener() {
        issueService.listenForIssues(this::updateIssuesDisplay);
        System.out.println("VIEW_ISSUES_CONTROLLER: Real-time listener set up.");
    }

    public void loadIssuesIntoVBox() {
        
        System.out.println("VIEW_ISSUES_CONTROLLER: loadIssuesIntoVBox called.");
    }

    private VBox createIssueDisplayBox(Issue issue) {
        Label issueDescriptionLabel = new Label(issue.getDescription());
        issueDescriptionLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        issueDescriptionLabel.setWrapText(true);
        issueDescriptionLabel.setMaxWidth(600); // Limit width to ensure wrapping

        // Attempt to parse date from string for display if needed
        String formattedDate = "N/A";
        if (issue.getDate() != null && !issue.getDate().isEmpty()) {
            try {
                LocalDate date = LocalDate.parse(issue.getDate());
                formattedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException e) {
                System.err.println("Error parsing date: " + issue.getDate() + " - " + e.getMessage());
                // Keep "N/A" or show original string if parsing fails
                formattedDate = issue.getDate();
            }
        }
        Label issueDateLabel = new Label("Date: " + formattedDate);
        issueDateLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666666;");

        Button viewButton = new Button("View");
        viewButton.setStyle("-fx-font-size: 14px; -fx-background-color: #6495ED; -fx-text-fill: white; -fx-background-radius: 5;");
        viewButton.setOnAction(e -> showIssueDetails(issue));

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-font-size: 14px; -fx-background-color: #DC143C; -fx-text-fill: white; -fx-background-radius: 5;");
        deleteButton.setOnAction(e -> {
            issueService.deleteIssue(issue.getId()); // Delete from Firestore
            // The real-time listener will automatically update the UI after deletion.
            System.out.println("Deleted issue with ID: " + issue.getId());
        });

        HBox buttonsBox = new HBox(10, viewButton, deleteButton);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        VBox issueBox = new VBox(5, issueDescriptionLabel, issueDateLabel, buttonsBox);
        issueBox.setPadding(new Insets(10));
        issueBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: #CCCCCC; -fx-border-radius: 10; -fx-border-width: 1; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        issueBox.setMaxWidth(700);
        VBox.setMargin(issueBox, new Insets(5, 0, 5, 0)); // Add some vertical margin

        return issueBox;
    }

    private void showIssueDetails(Issue issue) {
        issueDescriptionField.setText("Date: " + issue.getDate() + "\n\nDescription:\n" + issue.getDescription());
        issueDescriptionOverlay.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), issueDescriptionOverlay);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        // Optional: Slide up the popup for a nice effect
        issuesPopUp.setTranslateY(issuesPopUp.getHeight()); // Start below its final position
        FadeTransition popupFadeIn = new FadeTransition(Duration.millis(300), issuesPopUp);
        popupFadeIn.setFromValue(0);
        popupFadeIn.setToValue(1);

        // Slide up animation
        issuesPopUp.setTranslateY(0); 
        ;
    }
}