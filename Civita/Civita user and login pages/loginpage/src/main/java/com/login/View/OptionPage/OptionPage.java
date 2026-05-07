package com.login.View.OptionPage;


import com.login.View.aboutt;
import com.login.View.choose;
import com.login.View.HomePages.HomePageAdmin;
import com.login.View.HomePages.HomePageRsident;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OptionPage  {

        Scene optionPage1Scene,adminPage2Scene,residentHomePage2Scene,guest2Scene,about2Scene;
        Stage optionPagePrimaryStage;

        
        public void setOptionPage1Scene(Scene optionPage1Scene) {
                this.optionPage1Scene = optionPage1Scene;
        }
        public void setOptionPagePrimaryStage(Stage optionPagePrimaryStage) {
                this.optionPagePrimaryStage = optionPagePrimaryStage;
        }
      
        public VBox createOptionScene(){


        
        //Admin text 

        Text adminTx = new Text("Admin");
        adminTx.setStyle("-fx-font-size: 24px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");

        //Recident Text

         Text residentTx = new Text("Resident");
        residentTx.setStyle("-fx-font-size: 24px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");

        //Guest Text

         Text guestTx = new Text("Guest");
        guestTx.setStyle("-fx-font-size: 24px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");

// admin vBox

        VBox adminVBox = new VBox(50,adminTx);
        adminVBox.setAlignment(Pos.CENTER);
        adminVBox.setStyle( 
        "-fx-border-color: #a9d8fcff;" +                                         // border color
        "-fx-border-width: 3px;" +                                              // thickness
        "-fx-border-radius: 50px;" +                                           // rounded corners
        "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));"+         // gradient background
        "-fx-background-radius: 50px;" +                                     // make background match border
        "-fx-padding: 20px;"                                               // internal spacing
        
        );
       adminVBox.setPrefSize(400, 50);                                     // preferred size
       adminVBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);  // don’t stretch


//admin vBox VBox click       
      adminVBox.setOnMouseClicked(e -> {

        try {
                System.out.println("adminvbox clicked");
                initalizeAdminHomePage();
                optionPagePrimaryStage.setScene(adminPage2Scene);
        } catch (Exception adminpageException) {
                adminpageException.printStackTrace();
        }

   
    });


//admin vBox hover
        adminVBox.setOnMouseEntered(e -> {
        adminVBox.setStyle("-fx-border-color: #446bfaff;" + "-fx-border-width: 3px;" + "-fx-border-radius: 50px;" +"-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,#446bfaff,rgb(197, 240, 242));" +"-fx-background-radius: 50px;" +"-fx-padding: 20px;");
});

        adminVBox.setOnMouseExited(e -> {
        adminVBox.setStyle("-fx-border-color: #a9d8fcff;" + "-fx-border-width: 3px;" + "-fx-border-radius: 50px;" +"-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-background-radius: 50px;" +"-fx-padding: 20px;");
});

    
//Resident VBox
        VBox residentVBox = new VBox(50,residentTx);
        residentVBox.setAlignment(Pos.CENTER);
        residentVBox.setStyle("-fx-border-color: #a9d8fcff;" + "-fx-border-width: 3px;" + "-fx-border-radius: 50px;" +"-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-background-radius: 50px;" +"-fx-padding: 20px;");
       residentVBox.setPrefSize(400, 50);      
       residentVBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE); 


//resident VBox click logic      
       residentVBox.setOnMouseClicked(e -> {  


        try {
                initalizeResidentHomePage();
                optionPagePrimaryStage.setScene(residentHomePage2Scene);
        } catch (Exception residentHomePageexception) {
                residentHomePageexception.printStackTrace();
        }


        
    });

 
//resident VBox hover
        residentVBox.setOnMouseEntered(e -> {
        residentVBox.setStyle("-fx-border-color: #446bfaff;" + "-fx-border-width: 3px;" + "-fx-border-radius: 50px;" +"-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,#446bfaff,rgb(197, 240, 242));" +"-fx-background-radius: 50px;" +"-fx-padding: 20px;");});
        residentVBox.setOnMouseExited(e -> {
        residentVBox.setStyle("-fx-border-color: #a9d8fcff;" + "-fx-border-width: 3px;" + "-fx-border-radius: 50px;" +"-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-background-radius: 50px;" +"-fx-padding: 20px;");});



// Guest VBox
        VBox guestVBox = new VBox(50,guestTx);
          guestVBox.setAlignment(Pos.CENTER);
        guestVBox.setStyle("-fx-border-color: #a9d8fcff;" + "-fx-border-width: 3px;" + "-fx-border-radius: 50px;" +"-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-background-radius: 50px;" +"-fx-padding: 20px;");
       guestVBox.setPrefSize(400, 50);      
       guestVBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE); 


//Guest VBox click       
       guestVBox.setOnMouseClicked(e -> {                                 
        // write on press code here
        initalizeGuestOption();
        optionPagePrimaryStage.setScene(guest2Scene);
       System.out.println("guestVBox button clicked!");
       });
//Guest VBox hover
        guestVBox.setOnMouseEntered(e -> {
        guestVBox.setStyle("-fx-border-color: #446bfaff;" + "-fx-border-width: 3px;" + "-fx-border-radius: 50px;" +"-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,#446bfaff,rgb(197, 240, 242));" +"-fx-background-radius: 50px;" +"-fx-padding: 20px;");});
        guestVBox.setOnMouseExited(e -> {
        guestVBox.setStyle("-fx-border-color: #a9d8fcff;" + "-fx-border-width: 3px;" + "-fx-border-radius: 50px;" +"-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-background-radius: 50px;" +"-fx-padding: 20px;");});
//Shadow Effect
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(4);
        shadow.setOffsetY(4);
        shadow.setColor(Color.rgb(0, 0, 0, 0.3));
        adminVBox.setEffect(shadow);
        residentVBox.setEffect(shadow);
        guestVBox.setEffect(shadow);

 
      //  Adding a About button

                Button aboutButton = new Button("About");
                aboutButton.setStyle(
                                " -fx-border-color: #a9d8fcff;-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242)); -fx-background-radius: 20; -fx-border-radius: 20;-fx-font-size: 24px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
                aboutButton.setPadding(new Insets(5, 15, 5, 15));
                aboutButton.setAlignment(Pos.CENTER);
                aboutButton.setPrefWidth(200);

                // Add action
                aboutButton.setOnAction(e -> {
                        System.out.println("Back button clicked!");

                        //code of navigation
                        initalizeAboutPage();
                        optionPagePrimaryStage.setScene(about2Scene);
                });

                //  aboutButton.setOnMouseEntered(e -> {
                //  aboutButton.setStyle("-fx-border-color: #446bfaff;" + "-fx-border-width: 3px;" + "-fx-border-radius: 50px;" +"-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,#446bfaff,rgb(197, 240, 242));" +"-fx-background-radius: 50px;" +"-fx-padding: 20px;");});
                //  aboutButton.setOnMouseExited(e -> {
                //  aboutButton.setStyle("-fx-border-color: #a9d8fcff;" + "-fx-border-width: 3px;" + "-fx-border-radius: 50px;" +"-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-background-radius: 50px;" +"-fx-padding: 20px;");});

                VBox heightVBox = new VBox();
                heightVBox.setPrefHeight(10);

       

//
        VBox optionPageVBox = new VBox(50,adminVBox,residentVBox,guestVBox,heightVBox,aboutButton);
        optionPageVBox .setAlignment(Pos.CENTER);
        optionPageVBox.setPrefWidth(50);
        optionPageVBox.setPrefHeight(200);
        optionPageVBox.setStyle("-fx-background-color: LAVENDER;");
        return optionPageVBox;


    
}

        private void initalizeAdminHomePage(){

        HomePageAdmin homePageAdminObj= new HomePageAdmin();
        homePageAdminObj.setAdminHomePagePrimaryStage(optionPagePrimaryStage);
        adminPage2Scene = new Scene(homePageAdminObj.createAdminHomePageScene(),1600,800);
        homePageAdminObj.setAdminHomePage1Scene(adminPage2Scene);




}

        private void initalizeResidentHomePage(){

                HomePageRsident homePageRsidentObj = new HomePageRsident();
                homePageRsidentObj.setResidentHomePagePrimaryStage(optionPagePrimaryStage);
                residentHomePage2Scene = new Scene(homePageRsidentObj.createResidentHomePageScene(),1600,800);
                homePageRsidentObj.setResidentHomePage1Scene(residentHomePage2Scene);

        }

        private void initalizeGuestOption(){

                choose chooseObj = new choose();
                chooseObj.setChoosePrimaryStage(optionPagePrimaryStage);
                guest2Scene= new Scene(chooseObj.createScene(this::handleBackToOptionPageButton),1600,800);
                chooseObj.setChoose1Scene(guest2Scene);



        }

            private void handleBackToOptionPageButton(){

                optionPagePrimaryStage.setScene(optionPage1Scene);


    }

            private void initalizeAboutPage(){
     
        aboutt aboutpageObj =new aboutt();
        aboutpageObj.setAboutPrimaryStage(optionPagePrimaryStage);
        about2Scene= new Scene(aboutpageObj.createAboutScene(),1600,800);
        aboutpageObj.setAbout1Scene(about2Scene);
        

    }

}



  