package com.login.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class emergency_contacts {

        Scene emergency2Scene;
        Stage emergencyPrimaryStage;

        public void setEmergency2Scene(Scene emergency2Scene) {
                this.emergency2Scene = emergency2Scene;
        }

        public void setEmergencyPrimaryStage(Stage emergencyPrimaryStage) {
                this.emergencyPrimaryStage = emergencyPrimaryStage;
        }

        public VBox createAdminEmergencyScene(Runnable emergencyBackToHomePage){



        // Created Title "Emergency contacts"

        Label emergencylabel = new Label("Emergency Contacts");
        emergencylabel.setFont(Font.font(40));
        emergencylabel.setTextFill(javafx.scene.paint.Color.DARKSLATEGRAY);
        emergencylabel.setStyle("-fx-font-size: 48px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        emergencylabel.setAlignment(Pos.CENTER);

        // VBox for centering label

        VBox emergencylabelBox = new VBox(10, emergencylabel);
        emergencylabelBox.setAlignment(Pos.TOP_CENTER);

        // Creating a title "Fire for box 1

        Text fireText = new Text("Fire :");
        fireText.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");

        // Creating a box for fire

        VBox emergencyContactsBox1 = new VBox(10);
        emergencyContactsBox1.setPrefSize(320, 210);
        emergencyContactsBox1.setMaxSize(320, 210);
        emergencyContactsBox1.setMinSize(320, 210);
        emergencyContactsBox1.setStyle(
                "-fx-background-color:linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242)); -fx-border-color: black;-fx-border-radius: 30; -fx-background-radius: 30; -fx-alignment: center;");

        // Adding image in the fire box

        Image fireImage = new Image("Assets\\fire.png");
        ImageView fireImageView = new ImageView(fireImage);
        fireImageView.setFitHeight(330); // Adjust size as needed
        fireImageView.setFitHeight(220);
        fireImageView.setPreserveRatio(true);
        fireImageView.setSmooth(true);
        emergencyContactsBox1.getChildren().add(fireImageView); // Add image to box
        emergencyContactsBox1.setAlignment(Pos.CENTER);

        // aligning the "fire" text and fire box one below another

        VBox emergencyContactsMainBox1 = new VBox(10, fireText, emergencyContactsBox1);

        // Creating a title "Nearby police station" for box 2

        Text policeStationText = new Text("Nearby police stations :");
        policeStationText.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");

        // Creating a box for nearby police stations

        VBox emergencyContactsBox2 = new VBox(10);
        emergencyContactsBox2.setPrefSize(320, 210);
        emergencyContactsBox2.setMaxSize(320, 210);
        emergencyContactsBox2.setMinSize(320, 210);
        emergencyContactsBox2.setStyle(
                "-fx-background-color:linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242)); -fx-border-color: black;-fx-border-radius: 30; -fx-background-radius: 30; -fx-alignment: center;");

        // Adding image in the police station control box

        Image policeStationImage = new Image("Assets\\police.png");
        ImageView policeStationImageView = new ImageView(policeStationImage);
        policeStationImageView.setFitHeight(330); // Adjust size as needed
        policeStationImageView.setFitHeight(220);
        policeStationImageView.setPreserveRatio(true);
        policeStationImageView.setSmooth(true);
        emergencyContactsBox2.getChildren().add(policeStationImageView); // Add image to box
        emergencyContactsBox2.setAlignment(Pos.CENTER);

        // aligning the "Nearby police stations" text and police station box one below
        // another

        VBox emergencyContactsMainBox2 = new VBox(10, policeStationText, emergencyContactsBox2);

        // Creating a title "antiPoison" for box 3

        Text antiPoisonText = new Text("Anti poison :");
        antiPoisonText.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");

        // Creating a box for antiPoison

        VBox emergencyContactsBox3 = new VBox(10);
        emergencyContactsBox3.setPrefSize(320, 210);
        emergencyContactsBox3.setMaxSize(320, 210);
        emergencyContactsBox3.setMinSize(330, 210);
        emergencyContactsBox3.setStyle(
                "-fx-background-color:linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242)); -fx-border-color: black;-fx-border-radius: 30; -fx-background-radius: 30; -fx-alignment: center;");

        // Adding image in the antiPoison box

        Image antiPoisonImage = new Image("Assets\\anti_poison.png");
        ImageView antipoisonImageView = new ImageView(antiPoisonImage);
        antipoisonImageView.setFitHeight(330); // Adjust size as needed
        antipoisonImageView.setFitHeight(220);
        antipoisonImageView.setPreserveRatio(true);
        antipoisonImageView.setSmooth(true);
        emergencyContactsBox3.getChildren().add(antipoisonImageView); // Add image to box
        emergencyContactsBox3.setAlignment(Pos.CENTER);

        // aligning the "antiPoison" text and antiPoison box one below another

        VBox emergencyContactsMainBox3 = new VBox(10, antiPoisonText, emergencyContactsBox3);

        // Creating a title "cyberCrime" for box 4

        Text cyberCrimeText = new Text("Cyber crime :");
        cyberCrimeText.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");

        // Creating a box for Cyber crime

        VBox emergencyContactsBox4 = new VBox(10);
        emergencyContactsBox4.setPrefSize(320, 210);
        emergencyContactsBox4.setMaxSize(320, 210);
        emergencyContactsBox4.setMinSize(330, 210);
        emergencyContactsBox4.setStyle(
                "-fx-background-color:linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242)); -fx-border-color: black;-fx-border-radius: 30; -fx-background-radius: 30; -fx-alignment: center;");

        // Adding image in the Cyber crimer box

        Image cyberCrimeImage = new Image("Assets\\cyber_crime.png");
        ImageView cyberCrimeImageView = new ImageView(cyberCrimeImage);
        cyberCrimeImageView.setFitHeight(330); // Adjust size as needed
        cyberCrimeImageView.setFitHeight(220);
        cyberCrimeImageView.setPreserveRatio(true);
        cyberCrimeImageView.setSmooth(true);
        emergencyContactsBox4.getChildren().add(cyberCrimeImageView); // Add image to box
        emergencyContactsBox4.setAlignment(Pos.CENTER);

        // aligning the "Cyber crime" text and Cyber crime one below another

        VBox emergencyContactsMainBox4 = new VBox(10, cyberCrimeText, emergencyContactsBox4);

        // Creating a title "ambulance" for box 5

        Text ambulanceText = new Text("Ambulance :");
        ambulanceText.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");

        // Creating a box for Nearby hospitals

        VBox emergencyContactsBox5 = new VBox(10);
        emergencyContactsBox5.setPrefSize(320, 210);
        emergencyContactsBox5.setMaxSize(320, 210);
        emergencyContactsBox5.setMinSize(330, 210);
        emergencyContactsBox5.setStyle(
                "-fx-background-color:linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242)); -fx-border-color: black;-fx-border-radius: 30; -fx-background-radius: 30; -fx-alignment: center;");

        // Adding image in the hospital box

        Image ambulanceImage = new Image("Assets\\ambulance.png");
        ImageView ambulanceImageView = new ImageView(ambulanceImage);
        ambulanceImageView.setFitHeight(330); // Adjust size as needed
        ambulanceImageView.setFitHeight(220);
        ambulanceImageView.setPreserveRatio(true);
        ambulanceImageView.setSmooth(true);
        emergencyContactsBox5.getChildren().add(ambulanceImageView); // Add image to box
        emergencyContactsBox5.setAlignment(Pos.CENTER);

        // aligning the "Nearby hospitals" text and Nearby hospitals box one below
        // another

        VBox emergencyContactsMainBox5 = new VBox(10, ambulanceText, emergencyContactsBox5);

        // Creating a title "womenEmpowerment" for box 6

        Text womenEmpowermentText = new Text("Women helpline :");
        womenEmpowermentText.setStyle("-fx-font-size: 24px;-fx-font-weight: bold; -fx-font-family: Comic Sans MS");

        // Creating a box for womenEmpowerment

        VBox emergencyContactsBox6 = new VBox(10);
        emergencyContactsBox6.setPrefSize(320, 210);
        emergencyContactsBox6.setMaxSize(320, 210);
        emergencyContactsBox6.setMinSize(330, 210);
        emergencyContactsBox6.setStyle(
                "-fx-background-color:linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242)); -fx-border-color: black;-fx-border-radius: 30; -fx-background-radius: 30; -fx-alignment: center;");

        // Adding image in the womenEmpowerment box

        Image womenEmpowermentImage = new Image("Assets\\women_helpline.png");
        ImageView womenEmpowermentImageView = new ImageView(womenEmpowermentImage);
        womenEmpowermentImageView.setFitHeight(330); // Adjust size as needed
        womenEmpowermentImageView.setFitHeight(220);
        womenEmpowermentImageView.setPreserveRatio(true);
        womenEmpowermentImageView.setSmooth(true);
        emergencyContactsBox6.getChildren().add(womenEmpowermentImageView); // Add image to box
        emergencyContactsBox6.setAlignment(Pos.CENTER);

        // aligning the "women Empowerment" text and womenEmpowerment box one below
        // another

        VBox emergencyContactsMainBox6 = new VBox(10, womenEmpowermentText, emergencyContactsBox6);

        // HBox Row1 to add boxes horizontally

        HBox emergency_mainHBoxRow1 = new HBox(150, emergencyContactsMainBox1, emergencyContactsMainBox2,
                emergencyContactsMainBox3);
        emergency_mainHBoxRow1.setAlignment(Pos.CENTER);
        emergency_mainHBoxRow1.setPadding(new Insets(10));

        // HBox Row2 to add boxes horizontally

        HBox emergency_mainHBoxRow2 = new HBox(150, emergencyContactsMainBox4, emergencyContactsMainBox5,
                emergencyContactsMainBox6);
        emergency_mainHBoxRow2.setAlignment(Pos.CENTER);
        emergency_mainHBoxRow2.setPadding(new Insets(10));

        // Adding a back button

        Button emergencybackButton = new Button("Back");
        emergencybackButton.setOnMouseEntered(ev -> {
            emergencybackButton.setScaleX(1.05);
            emergencybackButton.setScaleY(1.05);
        });
        emergencybackButton.setOnMouseExited(ev -> {
            emergencybackButton.setScaleX(1.0);
            emergencybackButton.setScaleY(1.0);
        });

        emergencybackButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
        emergencybackButton.setPadding(new Insets(5, 15, 5, 15));

        // Add action
        emergencybackButton.setOnAction(e -> {

            System.out.println("Back button clicked!");
            emergencyBackToHomePage.run();

        });

        // Put it in a HBox to align left
        HBox emergency_backBox = new HBox(emergencybackButton);
        emergency_backBox.setAlignment(Pos.BOTTOM_CENTER);

        // Scene vala VBox

        VBox emergency_mainVBox = new VBox(30, emergencylabelBox, emergency_mainHBoxRow1, emergency_mainHBoxRow2,
                emergency_backBox);
        emergency_mainVBox.setStyle("-fx-background-color: lavender;");
        emergency_mainVBox.setAlignment(Pos.TOP_CENTER);
        emergency_mainVBox.setPadding(new Insets(10));
        return emergency_mainVBox;

        
     

    }
}


