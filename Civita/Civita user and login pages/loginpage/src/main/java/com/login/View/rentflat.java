
package com.login.View;

import com.login.Model.Flat;
import com.login.services.FirebaseService_rentFlat;
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

public class rentflat{
    // File chooser related variables
    public VBox addImagesPopUp;
    public VBox addImageOverlay;
    public Label uploadStatusLabel;
    public List<File> selectedImages = new ArrayList<>();
    public FlowPane imagePreviewPane;
    
    Scene rentFlat1Scene;
    Stage rentFlatPrimaryStage;              

    public void setRentFlat1Scene(Scene rentFlat1Scene) {
        this.rentFlat1Scene = rentFlat1Scene;
    }

    public void setRentFlatPrimaryStage(Stage rentFlatPrimaryStage) {
        this.rentFlatPrimaryStage = rentFlatPrimaryStage;
    } 

    public StackPane createRentFlatScene(Runnable rentBackToResidentHomePage){
        // Title
        Label rentFlatlabel = new Label("Rent Flat");
        rentFlatlabel.setFont(Font.font(40));
        rentFlatlabel.setTextFill(javafx.scene.paint.Color.DARKSLATEGRAY);
        rentFlatlabel.setStyle("-fx-font-size: 48px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        VBox rentFlatlabelBox = new VBox(10, rentFlatlabel);
        rentFlatlabelBox.setAlignment(Pos.CENTER);

        //FlatType Text & TextField
        Text rentflatType=new Text("Flat type (1/2 BHK) :");
        rentflatType.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        rentflatType.setFill(javafx.scene.paint.Color.rgb(0,0,0,0.7));
        rentflatType.setFont(Font.font(15));
        TextField rentflatTypeTextField=new TextField();
        rentflatTypeTextField.setPrefWidth(700);
        rentflatTypeTextField.setPrefHeight(30);
        rentflatTypeTextField.setFocusTraversable(false);
        rentflatTypeTextField.setPromptText("Enter Flat type...");
                
        VBox rentflatTypeVBox=new VBox(10,rentflatType,rentflatTypeTextField);
        rentflatTypeVBox.setPadding(new Insets(10));
        rentflatTypeVBox.setAlignment(Pos.CENTER_LEFT);
        rentflatTypeVBox.setMaxWidth(700);
        VBox setrentFlatTypeAlignmentVBox =new VBox(rentflatTypeVBox);
        setrentFlatTypeAlignmentVBox.setAlignment(Pos.CENTER);

        //Flat Rent Text & TextField
        Text rentflatPrice =new Text("Flat rent :");
        rentflatPrice.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        rentflatPrice.setFill(javafx.scene.paint.Color.rgb(0,0,0,0.7));
        rentflatPrice.setFont(Font.font(15));
        TextField rentflatPriceTextField=new TextField();
        rentflatPriceTextField.setPrefWidth(700);
        rentflatPriceTextField.setPrefHeight(40);
        rentflatPriceTextField.setFocusTraversable(false);
        rentflatPriceTextField.setPromptText("Flat rent...");
        VBox rentflatPriceVBox=new VBox(10,rentflatPrice,rentflatPriceTextField);
        rentflatPriceVBox.setAlignment(Pos.CENTER_LEFT);
        rentflatPriceVBox.setPadding(new Insets(10));
        rentflatPriceVBox.setMaxWidth(700);
        VBox setrentflatPriceAlignmentVBox=new VBox(rentflatPriceVBox);
        setrentflatPriceAlignmentVBox.setAlignment(Pos.CENTER);

        //Flat number Text & TextField
        Text rentflatNo = new Text("Flat number :");
        rentflatNo.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        rentflatNo.setFill(javafx.scene.paint.Color.rgb(0,0,0,0.7));
        rentflatNo.setFont(Font.font(15));
        TextField rentflatNoTextField = new TextField();
        rentflatNoTextField.setPrefWidth(700);
        rentflatNoTextField.setPrefHeight(40);
        rentflatNoTextField.setFocusTraversable(false);
        rentflatNoTextField.setPromptText("Enter Flat number...");
        VBox rentflatvb = new VBox(10,rentflatNo,rentflatNoTextField);
        rentflatvb.setPadding(new Insets(10));
        rentflatvb.setAlignment(Pos.CENTER_LEFT);
        rentflatvb.setMaxWidth(700);
        VBox setrentAlignmentBox = new VBox(rentflatvb);
        setrentAlignmentBox.setAlignment(Pos.CENTER);

        //Flat compatibility Text & TextField
        Text rentflatCompatibility = new Text("Compatible for : ");
        rentflatCompatibility.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        rentflatCompatibility.setFill(javafx.scene.paint.Color.rgb(0,0,0,0.7));
        rentflatCompatibility.setFont(Font.font(15));
        TextField rentflatCompatibilityTextField = new TextField();
        rentflatCompatibilityTextField.setPrefWidth(700);
        rentflatCompatibilityTextField.setPrefHeight(40);
        rentflatCompatibilityTextField.setFocusTraversable(false);
        rentflatCompatibilityTextField.setPromptText("Family/Bachelors...");
        VBox rentflatCompatibilitytvb = new VBox(10, rentflatCompatibility, rentflatCompatibilityTextField);
        rentflatCompatibilitytvb.setPadding(new Insets(10));
        rentflatCompatibilitytvb.setAlignment(Pos.CENTER_LEFT);
        rentflatCompatibilitytvb.setMaxWidth(700);
        VBox setrentCompatibilityAlignmentBox = new VBox(rentflatCompatibilitytvb);
        setrentCompatibilityAlignmentBox.setAlignment(Pos.CENTER);

        //Aminities Text & TextField
        Text rentaminitiesText = new Text("Amenities :");
        rentaminitiesText.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        rentaminitiesText.setFill(javafx.scene.paint.Color.rgb(0,0,0,0.7));
        rentaminitiesText.setFont(Font.font(15));
        TextField rentaminitiesTextField = new TextField();
        rentaminitiesTextField .setPrefWidth(700);
        rentaminitiesTextField .setPrefHeight(40);
        rentaminitiesTextField .setFocusTraversable(false);
        rentaminitiesTextField .setPromptText("Aminities available...");
        VBox rentaminitiesVBox = new VBox(10, rentaminitiesText, rentaminitiesTextField);
        rentaminitiesVBox.setPadding(new Insets(10));
        rentaminitiesVBox.setAlignment(Pos.CENTER_LEFT);
        rentaminitiesVBox.setMaxWidth(700);
        VBox rentaminitiesAlignmentVBox = new VBox(rentaminitiesVBox);
        rentaminitiesAlignmentVBox.setAlignment(Pos.CENTER);

        // Creating a filechooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Images");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        Button rentFlatAddImageButton = new Button("Add Images");
        rentFlatAddImageButton.setOnMouseEntered(ev -> {
                rentFlatAddImageButton.setScaleX(1.05);
                rentFlatAddImageButton.setScaleY(1.05);
            });           
        rentFlatAddImageButton.setOnMouseExited(ev -> {
                rentFlatAddImageButton.setScaleX(1.0);
                rentFlatAddImageButton.setScaleY(1.0);
            });
        rentFlatAddImageButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
        rentFlatAddImageButton.setPadding(new Insets(5, 15, 5, 15));
        // Modified action to show popup
        rentFlatAddImageButton.setOnAction(e -> {
            System.out.println("Add Images button clicked!");
            addImageOverlay.setVisible(true);
            addImageOverlay.setOpacity(1.0);
            uploadStatusLabel.setVisible(false);
        });

        // "Submission successfull !" message when the submit button is pressed
        Label submissionSuccessLabel = new Label(""); // Initialize as empty
        submissionSuccessLabel.setStyle(
                "-fx-text-fill: green; -fx-font-size: 16px; -fx-font-weight: bold;");
        submissionSuccessLabel.setVisible(false); // by setting it to false, it is initially hidden

        //Creating submit Button
        Button rentFlatSubmitButton = new Button("Submit");
        rentFlatSubmitButton.setOnMouseEntered(ev -> {
                rentFlatSubmitButton.setScaleX(1.05);
                rentFlatSubmitButton.setScaleY(1.05);
            });           
        rentFlatSubmitButton.setOnMouseExited(ev -> {
                rentFlatSubmitButton.setScaleX(1.0);
                rentFlatSubmitButton.setScaleY(1.0);
            });
        rentFlatSubmitButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
        rentFlatSubmitButton.setPadding(new Insets(5, 15, 5, 15));
        // Modified action with success message
        rentFlatSubmitButton.setOnAction(e -> {
            System.out.println("Submit button clicked!");
            // Create Flat object
            Flat flat = new Flat(
                rentflatNoTextField.getText(),
                rentflatTypeTextField.getText(),
                rentaminitiesTextField.getText(),
                rentflatCompatibilityTextField.getText(),
                rentflatPriceTextField.getText(),
                List.of(
                    "https://images.unsplash.com/photo-1600585154340-be6161a56a0c",
                    "https://images.unsplash.com/photo-1580587771525-78b9dba3b914"
                )
            );
            // Send to Firebase
            FirebaseService_rentFlat.addRentFlat(flat);
            
            // Show success message with same styling and animation as sellflat
            submissionSuccessLabel.setText("Submitted successfully !"); // Set the success message
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
            
            System.out.println("Flat submitted to Firebase successfully!");
        });

        //Back button of rentflat.java
        Button rentflatBackButton = new Button("Back");
        rentflatBackButton.setOnMouseEntered(ev -> {
            rentflatBackButton.setScaleX(1.05);
            rentflatBackButton.setScaleY(1.05);
        });
        rentflatBackButton.setOnMouseExited(ev -> {
            rentflatBackButton.setScaleX(1.0);
            rentflatBackButton.setScaleY(1.0);
        });
        rentflatBackButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
        rentflatBackButton.setPadding(new Insets(5, 15, 5, 15));
        rentflatBackButton.setOnAction(e -> {
            System.out.println("Back button clicked!");
            //Code of navigation
            rentBackToResidentHomePage.run();
        });

        VBox rentFlatimageandSubmitBtnVBox =new VBox(15,rentFlatAddImageButton,rentFlatSubmitButton,rentflatBackButton,submissionSuccessLabel);
        rentFlatimageandSubmitBtnVBox.setAlignment(Pos.CENTER);

        // Pop up components for image selection
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
            List<File> files = fileChooser.showOpenMultipleDialog(rentFlatPrimaryStage);
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

        //Left sided image i.e Rent a Flat !
        Image rentflatimage = new Image("Assets\\rent flat.jpg");
        ImageView imageView = new ImageView(rentflatimage);
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(rentFlatPrimaryStage.heightProperty());
        VBox rentimageBox = new VBox(imageView);
        rentimageBox.setAlignment(Pos.CENTER_LEFT);

        VBox mainBox = new VBox(10,rentFlatlabelBox,setrentAlignmentBox,setrentFlatTypeAlignmentVBox,rentaminitiesAlignmentVBox,setrentCompatibilityAlignmentBox,setrentflatPriceAlignmentVBox,rentFlatimageandSubmitBtnVBox);  //check sequence of added VBoxes
        mainBox.setPadding(new Insets(10));
        mainBox.setStyle("-fx-background-color:LAVENDER;");
        mainBox.setAlignment(Pos.TOP_CENTER);

        HBox contentBox = new HBox(150, rentimageBox, mainBox);
        contentBox.setStyle("-fx-background-color: lavender;");

        // Add overlay on top of content using StackPane
        StackPane root = new StackPane(contentBox, addImageOverlay);
        
        return root;           
    }
}