package com.login.View;


import javafx.animation.FadeTransition;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class aboutt  {

    Scene about1Scene;
    public void setAbout1Scene(Scene about1Scene) {
        this.about1Scene = about1Scene;
    }

    public void setAboutPrimaryStage(Stage aboutPrimaryStage) {
        this.aboutPrimaryStage = aboutPrimaryStage;
    }

    Stage aboutPrimaryStage;

  public  VBox createAboutScene(){

        Image image = new Image("Assets\\abouttt.png");
        ImageView abouttimageView = new ImageView(image);
        abouttimageView.setFitWidth(1650);
        abouttimageView.setFitHeight(850);
        

        //Animation
        abouttimageView.setOpacity(0);

        // Fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), abouttimageView);
        fadeIn.setFromValue(0);   // start fully transparent
        fadeIn.setToValue(1);     // end fully visible
        fadeIn.play(); 


        VBox vbox = new VBox(20, abouttimageView);
        vbox.setAlignment(Pos.CENTER);
        return vbox;

        

    }

}
