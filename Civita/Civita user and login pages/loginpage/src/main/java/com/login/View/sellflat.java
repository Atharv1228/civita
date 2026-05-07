package com.login.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.login.Model.Flat;
import com.login.services.FirebaseService;


public class sellflat  {

    public VBox addImagesPopUp;
    public VBox addImageOverlay;
    public Label uploadStatusLabel;
    public List<File> selectedImages = new ArrayList<>();
    public FlowPane imagePreviewPane;

    Scene sell1Scene;
    public void setSell1Scene(Scene sell1Scene) {
        this.sell1Scene = sell1Scene;
    }

    Stage sellPrimaryStage;


    public void setSellPrimaryStage(Stage sellPrimaryStage) {
        this.sellPrimaryStage = sellPrimaryStage;
    }



    public StackPane createSellFlatScene(Runnable  sellFlatBackToAdminHomePage){

        // Title

        Label sellFlatlabel = new Label("Sell Flat");
        sellFlatlabel.setFont(Font.font(40));
        sellFlatlabel.setTextFill(javafx.scene.paint.Color.DARKSLATEGRAY);
        sellFlatlabel.setStyle("-fx-font-size: 48px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        VBox sellFlatlabelBox = new VBox(10, sellFlatlabel);
        sellFlatlabelBox.setAlignment(Pos.CENTER);

        // FlatType Text & TextField

        Text sellflatType = new Text("Flat type (1/2 BHK) :");
        sellflatType.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        sellflatType.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0.7));
        sellflatType.setFont(Font.font(15));
        TextField sellflatTypeTextField = new TextField();
        sellflatTypeTextField.setPrefWidth(700);
        sellflatTypeTextField.setPrefHeight(30);
        sellflatTypeTextField.setFocusTraversable(false);
        sellflatTypeTextField.setPromptText("Enter Flat type...");

        VBox sellflatTypeVBox = new VBox(10, sellflatType, sellflatTypeTextField);
        sellflatTypeVBox.setPadding(new Insets(10));
        sellflatTypeVBox.setAlignment(Pos.CENTER_LEFT);
        sellflatTypeVBox.setMaxWidth(700);

        VBox setsellFlatTypeAlignmentVBox = new VBox(sellflatTypeVBox);
        setsellFlatTypeAlignmentVBox.setAlignment(Pos.CENTER);

        // Flat Price Text & TextField

        Text sellflatPrice = new Text("Flat price :");
        sellflatPrice.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        sellflatPrice.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0.7));
        sellflatPrice.setFont(Font.font(15));
        TextField sellflatPriceTextField = new TextField();
        sellflatPriceTextField.setPrefWidth(700);
        sellflatPriceTextField.setPrefHeight(40);
        sellflatPriceTextField.setFocusTraversable(false);
        sellflatPriceTextField.setPromptText("Flat price...");

        VBox sellflatPriceVBox = new VBox(10, sellflatPrice, sellflatPriceTextField);
        sellflatPriceVBox.setAlignment(Pos.CENTER_LEFT);
        sellflatPriceVBox.setPadding(new Insets(10));
        sellflatPriceVBox.setMaxWidth(700);

        VBox setsellflatPriceAlignmentVBox = new VBox(sellflatPriceVBox);
        setsellflatPriceAlignmentVBox.setAlignment(Pos.CENTER);

        // Flat number Text & TextField

        Text sellflatNo = new Text("Flat number :");
        sellflatNo.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        sellflatNo.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0.7));
        sellflatNo.setFont(Font.font(15));

        TextField sellflatNoTextField = new TextField();
        sellflatNoTextField.setPrefWidth(700);
        sellflatNoTextField.setPrefHeight(40);
        sellflatNoTextField.setFocusTraversable(false);
        sellflatNoTextField.setPromptText("Enter Flat number...");

        VBox sellflatvb = new VBox(10, sellflatNo, sellflatNoTextField);
        sellflatvb.setPadding(new Insets(10));
        sellflatvb.setAlignment(Pos.CENTER_LEFT);
        sellflatvb.setMaxWidth(700);

        VBox setsellAlignmentBox = new VBox(sellflatvb);
        setsellAlignmentBox.setAlignment(Pos.CENTER);

        // Flat compatibility Text & TextField

        Text sellflatCompatibility = new Text("Compatible for : ");
        sellflatCompatibility.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        sellflatCompatibility.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0.7));
        sellflatCompatibility.setFont(Font.font(15));

        TextField sellflatCompatibilityTextField = new TextField();
        sellflatCompatibilityTextField.setPrefWidth(700);
        sellflatCompatibilityTextField.setPrefHeight(40);
        sellflatCompatibilityTextField.setFocusTraversable(false);
        sellflatCompatibilityTextField.setPromptText("Family/Bachelors...");

        VBox sellflatCompatibilitytvb = new VBox(10, sellflatCompatibility, sellflatCompatibilityTextField);
        sellflatCompatibilitytvb.setPadding(new Insets(10));
        sellflatCompatibilitytvb.setAlignment(Pos.CENTER_LEFT);
        sellflatCompatibilitytvb.setMaxWidth(700);

        VBox setsellCompatibilityAlignmentBox = new VBox(sellflatCompatibilitytvb);
        setsellCompatibilityAlignmentBox.setAlignment(Pos.CENTER);

        // Aminites Text & TextField

        Text sellaminitiesText = new Text("Amenities :");
        sellaminitiesText.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        sellaminitiesText.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0.7));
        sellaminitiesText.setFont(Font.font(15));

        TextField sellaminitiesTextField = new TextField();
        sellaminitiesTextField.setPrefWidth(700);
        sellaminitiesTextField.setPrefHeight(40);
        sellaminitiesTextField.setFocusTraversable(false);
        sellaminitiesTextField.setPromptText("Aminities available...");

        VBox sellaminitiesVBox = new VBox(10, sellaminitiesText, sellaminitiesTextField);
        sellaminitiesVBox.setPadding(new Insets(10));
        sellaminitiesVBox.setAlignment(Pos.CENTER_LEFT);
        sellaminitiesVBox.setMaxWidth(700);

        VBox aminitiesAlignmentVBox = new VBox(sellaminitiesVBox);
        aminitiesAlignmentVBox.setAlignment(Pos.CENTER);

        Button addImageButton1 = new Button("Add Images");
        addImageButton1.setOnMouseEntered(ev -> {
            addImageButton1.setScaleX(1.05);
            addImageButton1.setScaleY(1.05);
        });
        addImageButton1.setOnMouseExited(ev -> {
            addImageButton1.setScaleX(1.0);
            addImageButton1.setScaleY(1.0);
        });
        addImageButton1.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
        addImageButton1.setPadding(new Insets(5, 15, 5, 15));

        // (Optional) Add action
        addImageButton1.setOnAction(e -> {

            System.out.println("Add Images button clicked!");
            addImageOverlay.setVisible(true);
            addImageOverlay.setOpacity(1.0);
            uploadStatusLabel.setVisible(false);

            // code of navigation
        });

        // "Submission successfull !" message when the submit button i spressed
        // i.e(sellFlatSubmissionButton)

        Label submissionSuccessLabel = new Label(""); // Initialize as empty
        submissionSuccessLabel.setStyle(
                "-fx-text-fill: green; -fx-font-size: 16px; -fx-font-weight: bold;");
        submissionSuccessLabel.setVisible(false); // by setting it to false, it is initially hidden

        // Creating submit Button

        Button sellFlatSubmitButton = new Button("Submit");
        sellFlatSubmitButton.setOnMouseEntered(ev -> {
            sellFlatSubmitButton.setScaleX(1.05);
            sellFlatSubmitButton.setScaleY(1.05);
        });
        sellFlatSubmitButton.setOnMouseExited(ev -> {
            sellFlatSubmitButton.setScaleX(1.0);
            sellFlatSubmitButton.setScaleY(1.0);
        });
        sellFlatSubmitButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
        sellFlatSubmitButton.setPadding(new Insets(5, 15, 5, 15));

        // what happens when the sellFlatSubmitButton is pressed...

        sellFlatSubmitButton.setOnAction(e -> {

            String flatNo = sellflatNoTextField.getText();
            String flatType = sellflatTypeTextField.getText();
            String amenities = sellaminitiesTextField.getText();
            String compatibleFor = sellflatCompatibilityTextField.getText();
            String price = sellflatPriceTextField.getText();

            Flat flat = new Flat(flatNo, flatType, amenities, compatibleFor, price);

            FirebaseService.addFlat(flat);

            System.out.println("Submit button clicked!");
            submissionSuccessLabel.setText("Submitted successfully !"); // first we set the label null and when the
                                                                        // button is pressed the value of null is
                                                                        // changed to "Submitted successfully !"
            submissionSuccessLabel.setStyle(
                    "-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS;-fx-text-fill: green;");
            submissionSuccessLabel.setVisible(true);

            // Create a FadeTransition to fade out the message
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), submissionSuccessLabel);
            fadeOut.setFromValue(1.0); // Start fully opaque
            fadeOut.setToValue(0.0); // End fully transparent
            fadeOut.setOnFinished(event -> {
                submissionSuccessLabel.setVisible(false); // Hide the label after fading
                submissionSuccessLabel.setOpacity(1.0); // Reset opacity for next time
            });
            fadeOut.play(); // Start the animation
        });

        //Main back button

         Button sellflatBackButton = new Button("Back");
        sellflatBackButton.setOnMouseEntered(ev -> {
            sellflatBackButton.setScaleX(1.05);
            sellflatBackButton.setScaleY(1.05);
        });
        sellflatBackButton.setOnMouseExited(ev -> {
            sellflatBackButton.setScaleX(1.0);
            sellflatBackButton.setScaleY(1.0);
        });
        sellflatBackButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
        sellflatBackButton.setPadding(new Insets(5, 15, 5, 15));

        sellflatBackButton.setOnAction(e -> {
            System.out.println("Back button clicked!");

            //Code of navigation
            sellFlatBackToAdminHomePage.run();

        });
        
        VBox imageandSubmitBtnVBox = new VBox(15, addImageButton1, sellFlatSubmitButton, sellflatBackButton, submissionSuccessLabel);
        imageandSubmitBtnVBox.setAlignment(Pos.CENTER);

        // Creating a filechooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Images");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        // Pop up on clicking add images
        // Creating A button (add images), and an upload button on a popup
        // Add image button inside the pop up

        Button addImageButton2 = new Button("Add Images");
        addImageButton2.setPrefWidth(300);
        addImageButton2.setPrefHeight(300);
        addImageButton2.setOnMouseEntered(ev -> {
            addImageButton2.setScaleX(1.05);
            addImageButton2.setScaleY(1.05);
        });
        addImageButton2.setOnMouseExited(ev -> {
            addImageButton2.setScaleX(1.0);
            addImageButton2.setScaleY(1.0);
        });

        addImageButton2.setOnAction(e -> {
            List<File> files = fileChooser.showOpenMultipleDialog(sellPrimaryStage);
            if (files != null) {
                if (selectedImages.size() >= 5) {
                    System.out.println("You can only add up to 5 images.");
                }

                for (File file : files) {
                    if (selectedImages.size() < 5) {
                        selectedImages.add(file);
                    } else {
                        break;
                    }
                }

                // Clear and reload preview
                imagePreviewPane.getChildren().clear();
                for (File imgFile : selectedImages) {
                    Image image = new Image(imgFile.toURI().toString(), 100, 100, true, true); // thumbnail
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    imagePreviewPane.getChildren().add(imageView);
                }
            }

        });
        addImageButton2.setStyle(
                " -fx-background-color:rgb(229, 227, 227);" +
                        "-fx-focus-color: #ffffffff;" +
                        "-fx-font-size: 18px;" + // Increase text size
                        "-fx-text-fill: darkslategray;" + // Text color
                        "-fx-background-radius: 10px;" + // Rounded corners
                        "-fx-border-radius: 10px;" + // Rounded border
                        "-fx-border-color:rgb(35, 237, 255);" + // Border color
                        "-fx-border-width: 2px;" + // Border thickness
                        "-fx-padding: 4 10 4 10;" + // Padding inside the TextField
                        "-fx-font-family: Comic Sans MS");

        

        // Label under the add images box ...

        Label imageLimitLabel = new Label("You can add upto 5 images");
        imageLimitLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 14px; -fx-font-family: Arial;");

        // Label which appears after clicking the upload button in popup

        uploadStatusLabel = new Label("Uploaded successfully !");
        uploadStatusLabel.setStyle(
                "-fx-text-fill: green; -fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: Comic Sans MS;");
        uploadStatusLabel.setVisible(false); // initially hidden

        imagePreviewPane = new FlowPane();
        imagePreviewPane.setHgap(10);
        imagePreviewPane.setVgap(10);
        imagePreviewPane.setPadding(new Insets(10));
        imagePreviewPane.setPrefWrapLength(600); // width before wrapping

        // Upload image inside the pop-up...

        Button uploadButton = new Button("Upload");
        uploadButton.setPrefWidth(300);
        uploadButton.setPrefHeight(40);
        uploadButton.setOnMouseEntered(ev -> {
            uploadButton.setScaleX(1.05);
            uploadButton.setScaleY(1.05);
        });
        uploadButton.setOnMouseExited(ev -> {
            uploadButton.setScaleX(1.0);
            uploadButton.setScaleY(1.0);
        });

        uploadButton.setOnAction(e -> {
            System.out.println("Upload button clicked!");
            uploadStatusLabel.setVisible(true);
        });

        uploadButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");

        // Back button inside the pop-up ...

        Button uploadBackButton = new Button("Back");
        uploadBackButton.setOnMouseEntered(ev -> {
            uploadBackButton.setScaleX(1.05);
            uploadBackButton.setScaleY(1.05);
        });
        uploadBackButton.setOnMouseExited(ev -> {
            uploadBackButton.setScaleX(1.0);
            uploadBackButton.setScaleY(1.0);
        });
        uploadBackButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
        uploadBackButton.setPadding(new Insets(5, 15, 5, 15));

        uploadBackButton.setOnAction(e -> {
            System.out.println("Back button clicked!");
            addImageOverlay.setVisible(false);
            uploadStatusLabel.setVisible(false);

            // Clear image preview thumbnails
            imagePreviewPane.getChildren().clear();

            // Clear the selected image list
            selectedImages.clear();

            // Clear the "Uploaded successfully!" message
            uploadStatusLabel.setText("Uploaded successfully");

        });

        VBox imageUploadBox = new VBox(10, addImageButton2, imageLimitLabel, imagePreviewPane, uploadButton,
                uploadBackButton,
                uploadStatusLabel);
        imageUploadBox.setAlignment(Pos.CENTER);

        // pop up
        addImagesPopUp = new VBox(20, imageUploadBox);
        addImagesPopUp.setPadding(new Insets(50));
        addImagesPopUp.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));"
                        +
                        "-fx-background-radius: 15;" +
                        "-fx-border-radius: 15px;" +
                        "-fx-border-color: rgb(117, 117, 117);" +
                        "-fx-border-width: 2;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        addImagesPopUp.setMaxWidth(800);
        addImagesPopUp.setMaxHeight(400);
        addImagesPopUp.setAlignment(Pos.CENTER);

        // Overlay
        addImageOverlay = new VBox();
        addImageOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");
        addImageOverlay.setVisible(false);
        addImageOverlay.setOpacity(0);
        addImageOverlay.setPrefSize(1920, 1080); // Full screen overlay
        addImageOverlay.getChildren().add(addImagesPopUp);
        addImageOverlay.setVisible(false); // start hidden
        addImageOverlay.setAlignment(Pos.CENTER);

        // Left sided image i.e Selling a Flat !

        Image sellflatimage = new Image("Assets\\flat_sell.jpg");
        ImageView imageView = new ImageView(sellflatimage);
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(sellPrimaryStage.heightProperty());

        VBox sellimageBox = new VBox(imageView);
        sellimageBox.setAlignment(Pos.CENTER_LEFT);

        VBox mainBox = new VBox(10, sellFlatlabelBox, setsellAlignmentBox, setsellFlatTypeAlignmentVBox,
                aminitiesAlignmentVBox, setsellCompatibilityAlignmentBox, setsellflatPriceAlignmentVBox,
                imageandSubmitBtnVBox); // check sequence of added VBoxes
        mainBox.setPadding(new Insets(10));
        mainBox.setStyle("-fx-background-color:LAVENDER;");
        mainBox.setAlignment(Pos.TOP_CENTER);

        HBox contentBox = new HBox(150, sellimageBox, mainBox);
        contentBox.setStyle("-fx-background-color: lavender;");

        // Add overlay on top of content using StackPane
        StackPane root = new StackPane(contentBox, addImageOverlay);
        
        return root;

      

    }

    

}
