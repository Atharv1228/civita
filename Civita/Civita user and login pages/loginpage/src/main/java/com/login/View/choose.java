
package com.login.View;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.Parent;


public class choose { 

        Scene choose1Scene,buyFlats2Scene,rentFlats2Scene;
        
        Stage  choosePrimaryStage;

        public void setChoose1Scene(Scene choose1Scene) {
                this.choose1Scene = choose1Scene;
        }


        public void setChoosePrimaryStage(Stage choosePrimaryStage) {
                this.choosePrimaryStage = choosePrimaryStage;
        }




    // Change the return type to Parent to be more flexible
    public Parent createScene(Runnable backToOptionpage) {

    // Buy text
    Text buyTx = new Text("Buy a Flat");
    buyTx.setStyle("-fx-font-size: 24px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");

    // Rent Text
    Text rentTx = new Text("Rent a Flat");
    rentTx.setStyle("-fx-font-size: 24px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");

    // Buy vBox
    VBox buyVBox = new VBox(50, buyTx);
    buyVBox.setAlignment(Pos.CENTER);
    buyVBox.setStyle(
            "-fx-border-color: #9370DB;" +
            "-fx-border-width: 3px;" +
            "-fx-border-radius: 50px;" +
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +
            "-fx-background-radius: 50px;" +
            "-fx-padding: 20px;"
    );
    buyVBox.setPrefSize(400, 50);
    buyVBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

    buyVBox.setOnMouseClicked(e -> { 
               

          try {
                 initalizebuyFlat();
                choosePrimaryStage.setScene(buyFlats2Scene);                              
          // write on press code here
                 System.out.println("Buy Flat button clicked!");
      
          } catch (Exception initalizeBuyPageException) {

               initalizeBuyPageException.printStackTrace();
          }
        
         });

    // Buy vBox VBox click
    // buyVBox.setOnMouseClicked(e -> {
    //     ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100), buyVBox);
    //     scaleDown.setToX(0.95);
    //     scaleDown.setToY(0.95);

    //     ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), buyVBox);
    //     scaleUp.setToX(1.0);
    //     scaleUp.setToY(1.0);

    //     scaleDown.setOnFinished(event -> {
    //         scaleUp.play();
         
    //     });
    //     scaleDown.play();
    // });

    // buy vBox hover
    buyVBox.setOnMouseEntered(e -> {
        buyVBox.setStyle("-fx-border-color: #9370DB;" + "-fx-border-width: 3px;"
                + "-fx-border-radius: 50px;"
                + "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #8a44faff, #feaae8ff);"
                + "-fx-background-radius: 50px;" + "-fx-padding: 20px;");
    });

    buyVBox.setOnMouseExited(e -> {
        buyVBox.setStyle("-fx-border-color: #9370DB;" + "-fx-border-width: 3px;"
                + "-fx-border-radius: 50px;"
                + "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));"
                + "-fx-background-radius: 50px;" + "-fx-padding: 20px;");
    });

    // Rent VBox
    VBox rentVBox = new VBox(50, rentTx);
    rentVBox.setAlignment(Pos.CENTER);
    rentVBox.setStyle("-fx-border-color: #9370DB;" + "-fx-border-width: 3px;" + "-fx-border-radius: 50px;"
            + "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));"
            + "-fx-background-radius: 50px;" + "-fx-padding: 20px;");
    rentVBox.setPrefSize(400, 50);
    rentVBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        rentVBox.setOnMouseClicked(e -> { 
               

          try {
                 initalizerentFlat();
                choosePrimaryStage.setScene(rentFlats2Scene);                               
          // write on press code here
                 System.out.println("Rent Flat button clicked!");
      
          } catch (Exception initalizeBuyPageException) {

               initalizeBuyPageException.printStackTrace();
          }
        
         });
    // Rent VBox click
    // rentVBox.setOnMouseClicked(e -> {
    //     ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100), rentVBox);
    //     scaleDown.setToX(0.95);
    //     scaleDown.setToY(0.95);

    //     ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), rentVBox);
    //     scaleUp.setToX(1.0);
    //     scaleUp.setToY(1.0);

    //     scaleDown.setOnFinished(event -> {
    //         scaleUp.play();
           
    //     });
    //     scaleDown.play();
    // });

    // Rent VBox hover
    rentVBox.setOnMouseEntered(e -> {
        rentVBox.setStyle("-fx-border-color: #9370DB;" + "-fx-border-width: 3px;"
                + "-fx-border-radius: 50px;"
                + "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #8a44faff, #feaae8ff);"
                + "-fx-background-radius: 50px;" + "-fx-padding: 20px;");
    });
    rentVBox.setOnMouseExited(e -> {
        rentVBox.setStyle("-fx-border-color: #9370DB;" + "-fx-border-width: 3px;"
                + "-fx-border-radius: 50px;"
                + "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));"
                + "-fx-background-radius: 50px;" + "-fx-padding: 20px;");
    });

    // Shadow Effect
    DropShadow shadow = new DropShadow();
    shadow.setRadius(10);
    shadow.setOffsetX(4);
    shadow.setOffsetY(4);
    shadow.setColor(Color.rgb(0, 0, 0, 0.3));
    buyVBox.setEffect(shadow);
    rentVBox.setEffect(shadow);

    // Main VBox for options - remove fixed sizes
    VBox optionPageVBox = new VBox(50, buyVBox, rentVBox);
    optionPageVBox.setAlignment(Pos.CENTER);
    optionPageVBox.setStyle("-fx-background-color: LAVENDER;"); // Background color should still be here

    // Back button
    Button uploadSellBackButton = new Button("Back");
    uploadSellBackButton.setOnMouseEntered(ev -> {
        uploadSellBackButton.setScaleX(1.05);
        uploadSellBackButton.setScaleY(1.05);
    });
    uploadSellBackButton.setOnMouseExited(ev -> {
        uploadSellBackButton.setScaleX(1.0);
        uploadSellBackButton.setScaleY(1.0);
    });
    uploadSellBackButton.setStyle(
            "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
    uploadSellBackButton.setPadding(new Insets(5, 15, 5, 15));

    uploadSellBackButton.setOnAction(e -> {
        backToOptionpage.run();;
    });

    // Use a StackPane to place the back button on top of the main content
    StackPane root = new StackPane(optionPageVBox, uploadSellBackButton);
    
    // Crucial change: Bind the VBox's size to the StackPane's size
    optionPageVBox.prefWidthProperty().bind(root.widthProperty());
    optionPageVBox.prefHeightProperty().bind(root.heightProperty());
    
    StackPane.setAlignment(uploadSellBackButton, Pos.BOTTOM_LEFT);
    StackPane.setMargin(uploadSellBackButton, new Insets(30));

    // Return the root StackPane directly
    return root;
}

private void initalizebuyFlat(){

    details_buy buyFlatsObj = new details_buy();
    buyFlatsObj.setBuyFlatGuestPrimaryStage(choosePrimaryStage);
    buyFlats2Scene= new Scene(buyFlatsObj.createBuyFlatsScene(this::handleBackToChoosePage),1600,800);
    buyFlatsObj.setBuyFlat1Scene(buyFlats2Scene);
    
}


private void initalizerentFlat(){

    details_rent rentFlatsObj = new details_rent();
    rentFlatsObj.setRentPrimaryStage(choosePrimaryStage);
    rentFlats2Scene= new Scene(rentFlatsObj.createRentScene(this::handleBackToChoosePage),1600,800);
    rentFlatsObj.setRent1Scene(rentFlats2Scene);
    
}


private void handleBackToChoosePage(){

    choosePrimaryStage.setScene(choose1Scene);
}


}