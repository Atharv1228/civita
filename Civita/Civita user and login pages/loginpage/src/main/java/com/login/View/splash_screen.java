package com.login.View;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class splash_screen extends Application {

    Scene splash1Scene,whyCivita2Scene;
    Stage splashPrimaryStage;


    @Override
    public void start(Stage splashScreenStage) throws Exception {

      splashPrimaryStage=splashScreenStage;


        // Logo setup
        ImageView logo = new ImageView(new Image("Assets\\Civita Logo Official.png"));
        logo.setFitWidth(200); // Start small
        logo.setPreserveRatio(true);

        VBox logoBox = new VBox(logo);
        logoBox.setAlignment(Pos.CENTER);

        // Adding image to upcoming vbox

        Image welcomeImage = new Image("Assets\\welcoming_you.png");
        ImageView welcomeImageView = new ImageView(welcomeImage);
        welcomeImageView.setFitWidth(500); // Adjust as needed
        welcomeImageView.setPreserveRatio(true);
        ProgressIndicator loadingSpinner = new ProgressIndicator();
        loadingSpinner.setPrefSize(50, 50);

        VBox welcomeBox = new VBox(welcomeImageView,loadingSpinner);
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.setPadding(new Insets(30));
        welcomeBox.setTranslateY(400); // Off-screen (bottom)
        welcomeBox.setMinWidth(900); 
        welcomeBox.setPrefHeight(100);

        welcomeBox.setStyle(
                "-fx-background-color: rgb(254, 255, 255, 0.2); -fx-background-radius: 15; -fx-border-color: black; -fx-border-radius: 15");

        welcomeBox.setMaxWidth(300);
        welcomeBox.setTranslateX(200); // Push right

        welcomeBox.setOpacity(0); // Fully transparent
        welcomeBox.setTranslateY(100); 

        // Root layout
        StackPane root = new StackPane(logoBox, welcomeBox);
        root.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(0, 0, 0),rgb(0, 2, 97));");
        //root.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242)); -fx-border-color: black;-fx-border-radius: 30; -fx-background-radius: 30; -fx-alignment: center;");

        Scene splashScene = new Scene(root, 1000, 700);
        splashScreenStage.setScene(splashScene);        //(from 0% 0% to 100% 100%,rgb(37, 37, 37),rgb(237, 112, 112));");
        splashScreenStage.setTitle("Civita");
        splashScreenStage.setMaximized(true);
        splashScreenStage.show();
        splashScene=splash1Scene;
        // splashScreenStage=splashPrimaryStage;

        // 1. Scale up logo
        ScaleTransition scaleLogo = new ScaleTransition(Duration.seconds(1.5), logo);
        scaleLogo.setFromX(1);
        scaleLogo.setFromY(1);
        scaleLogo.setToX(5.0);
        scaleLogo.setToY(5.0);

        //  Move logo to left
        TranslateTransition moveLogo = new TranslateTransition(Duration.seconds(1), logoBox);
        moveLogo.setToX(-520);

        //  Slide in welcome box from bottom
        TranslateTransition welcomeIn = new TranslateTransition(Duration.seconds(1), welcomeBox);
        welcomeIn.setToY(0);

        // Sequential animation: scale → move → show welcome
        SequentialTransition sequence = new SequentialTransition(scaleLogo, moveLogo, welcomeIn);
        sequence.play();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), welcomeBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        TranslateTransition slideUp = new TranslateTransition(Duration.seconds(1), welcomeBox);
        slideUp.setFromY(100); // Should match initial translateY
        slideUp.setToY(60);

        ParallelTransition showWelcomeBox = new ParallelTransition(fadeIn, slideUp);

        // Trigger this AFTER logo scaling and shifting animation finishes
        sequence.setOnFinished(e -> {
            showWelcomeBox.play();

            // After welcome box animation, wait for 5 seconds
            PauseTransition wait = new PauseTransition(Duration.seconds(5));
            wait.setOnFinished(ev -> {

             initalizeWhyCivitaPage(); //  whatever method shows the main screen
             splashScreenStage.setScene(whyCivita2Scene);
            });
            wait.play();
        });

        // Optional: switch to main scene after delay
        PauseTransition delay = new PauseTransition(Duration.seconds(6));
        delay.setOnFinished(e ->initalizeWhyCivitaPage());
        delay.play();
    }

    // private void showMainScene(Stage stage) {

    //     //add code of logIn page(Atharv's Section)

    //     Label mainText = new Label("Welcome to Civita App!");
    //     mainText.setStyle("-fx-font-size: 36px;");
    //     VBox mainLayout = new VBox(mainText);
    //     mainLayout.setAlignment(Pos.CENTER);

    //     Scene mainScene = new Scene(mainLayout, 800, 600);
    //     stage.setScene(mainScene);
    //     stage.setMaximized(true);
    // }

    private void initalizeWhyCivitaPage(){

        WhyCivita whyCivitaObj = new WhyCivita();
        whyCivitaObj.setWhyCivitaPrimaryStage(splashPrimaryStage);
        whyCivita2Scene= new Scene(whyCivitaObj.createWhyCivitaScene(),1600,800);
        whyCivitaObj.setWhyCivita1Scene(whyCivita2Scene);





    }
}
