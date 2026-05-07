package com.login.View;

import com.login.View.AuthenticationPages.SignupPage;

import javafx.animation.FadeTransition;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class everythingIn1 {

    Scene everythingIn1Scene,signupPage2Scene;
    Stage everythingIn1PrimaryStage;


    public void setEverythingIn1Scene(Scene everythingIn1Scene) {
        this.everythingIn1Scene = everythingIn1Scene;
    }

    public void setEverythingIn1PrimaryStage(Stage everythingIn1PrimaryStage) {
        this.everythingIn1PrimaryStage = everythingIn1PrimaryStage;
    }


    public VBox createEveryThingIn1Scene(){
        Image image = new Image("Assets\\eveInn1.png");
        ImageView everythingIn1imageView = new ImageView(image);
        everythingIn1imageView.setFitWidth(1200);
        everythingIn1imageView.setPreserveRatio(true);

        everythingIn1imageView.setOpacity(0);

        // Fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), everythingIn1imageView);
        fadeIn.setFromValue(0);   // start fully transparent
        fadeIn.setToValue(1);     // end fully visible
        fadeIn.play();

        Button everythingIn1NextButton = new Button("Next");
        everythingIn1NextButton.setOnMouseEntered(ev -> {
            everythingIn1NextButton.setScaleX(1.05);
            everythingIn1NextButton.setScaleY(1.05);
        });
        everythingIn1NextButton.setOnMouseExited(ev -> {
            everythingIn1NextButton.setScaleX(1.0);
            everythingIn1NextButton.setScaleY(1.0);
        });
        everythingIn1NextButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
        everythingIn1NextButton.setPadding(new Insets(5, 15, 5, 15));

        everythingIn1NextButton.setOnAction(e -> {
            System.out.println("Back button clicked!");

        ////////////Code of navigation//////////////////
        
        initalizeSignupPage();
        everythingIn1PrimaryStage.setScene(signupPage2Scene);

        });

        VBox everythingIn1NextButtonAlignmentBox =new VBox(20,everythingIn1NextButton);
        everythingIn1NextButtonAlignmentBox.setAlignment(Pos.BOTTOM_RIGHT);
        everythingIn1NextButtonAlignmentBox.setPadding(new Insets(0, 80, 0, 0));

        VBox vbox = new VBox(20, everythingIn1imageView, everythingIn1NextButtonAlignmentBox);
        vbox.setAlignment(Pos.CENTER);
        return vbox;

    }


private void initalizeSignupPage(){

    SignupPage signupPageObj=new SignupPage();
    signupPageObj.setSignupPagePrimaryStage(everythingIn1PrimaryStage);
    signupPage2Scene=new Scene(signupPageObj.createSignupPageScene(),1600,800);
    signupPageObj.setSignupPage1Scene(signupPage2Scene);

}
    

   
}
