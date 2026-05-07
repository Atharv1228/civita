package com.login.View;


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

public class WhyCivita {

    Scene whyCivita1Scene,everythingIn2Scene;

    public void setWhyCivita1Scene(Scene whyCivita1Scene) {
        this.whyCivita1Scene = whyCivita1Scene;
    }

    public void setWhyCivitaPrimaryStage(Stage whyCivitaPrimaryStage) {
        this.whyCivitaPrimaryStage = whyCivitaPrimaryStage;
    }

    Stage whyCivitaPrimaryStage;

    public VBox createWhyCivitaScene(){
        Image image = new Image("Assets\\whycivita_transparent.png");
        ImageView whyCivitaimageView = new ImageView(image);
        whyCivitaimageView.setFitWidth(1200);
        whyCivitaimageView.setPreserveRatio(true);

        //Animation
        whyCivitaimageView.setOpacity(0);

        // Fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), whyCivitaimageView);
        fadeIn.setFromValue(0);   // start fully transparent
        fadeIn.setToValue(1);     // end fully visible
        fadeIn.play(); 

        Button WhyCivitaNextButton = new Button("Next");
        WhyCivitaNextButton.setOnMouseEntered(ev -> {
            WhyCivitaNextButton.setScaleX(1.05);
            WhyCivitaNextButton.setScaleY(1.05);
        });
        WhyCivitaNextButton.setOnMouseExited(ev -> {
            WhyCivitaNextButton.setScaleX(1.0);
            WhyCivitaNextButton.setScaleY(1.0);
        });
        WhyCivitaNextButton.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
        WhyCivitaNextButton.setPadding(new Insets(5, 15, 5, 15));

        WhyCivitaNextButton.setOnAction(e -> {

            initalizeEverytingInOnepage();
            whyCivitaPrimaryStage.setScene(everythingIn2Scene);


           
        

        });

        VBox WhyCivitaNextButtonAlignmentBox =new VBox(WhyCivitaNextButton);
        WhyCivitaNextButtonAlignmentBox.setAlignment(Pos.BOTTOM_RIGHT);
        WhyCivitaNextButtonAlignmentBox.setPadding(new Insets(0, 80, 0, 0));

        VBox vbox = new VBox(20, whyCivitaimageView, WhyCivitaNextButtonAlignmentBox);
        vbox.setAlignment(Pos.CENTER);
        return vbox;


        

    }

    private void initalizeEverytingInOnepage(){
        everythingIn1 everythingObj = new everythingIn1();
        everythingObj.setEverythingIn1PrimaryStage(whyCivitaPrimaryStage);
        everythingIn2Scene=new Scene(everythingObj.createEveryThingIn1Scene(),1600,800);
        everythingObj.setEverythingIn1Scene(everythingIn2Scene);




    }

  

}
