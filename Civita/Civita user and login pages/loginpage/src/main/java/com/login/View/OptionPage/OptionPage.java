package com.login.View.OptionPage;


import com.login.View.aboutt;
import com.login.View.choose;
import com.login.View.HomePages.HomePageAdmin;
import com.login.View.HomePages.HomePageRsident;
import com.login.Utils.UserSession;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        
        // Get the current user's role from session
        UserSession session = UserSession.getInstance();
        String userRole = session.getRole();
        boolean isAdmin = session.isAdmin();
        boolean isResident = session.isResident();
        
        System.out.println("OptionPage loaded for user: " + session.getFullName() + " with role: " + userRole);
        
        //Admin text 

        Text adminTx = new Text("Admin");
        adminTx.setStyle("-fx-font-size: 24px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");

        //Resident Text

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
       adminVBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);  // don't stretch


//admin vBox VBox click - ROLE BASED ACCESS CONTROL     
      adminVBox.setOnMouseClicked(e -> {
        // Only allow admin role to access admin features
        if (isAdmin) {
            try {
                System.out.println("Admin access granted for: " + session.getFullName());
                initalizeAdminHomePage();
                optionPagePrimaryStage.setScene(adminPage2Scene);
            } catch (Exception adminpageException) {
                adminpageException.printStackTrace();
            }
        } else {
            // Show access denied message for non-admin users
            showAccessDeniedAlert("Admin Access Restricted", 
                "You do not have admin privileges. Only users registered as Admin can access this section.");
            System.out.println("Admin access denied for user: " + session.getFullName() + " (Role: " + userRole + ")");
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


//resident VBox click logic - ROLE BASED ACCESS CONTROL      
       residentVBox.setOnMouseClicked(e -> {  
        // Allow both admin and resident roles to access resident features
        // Admin can view resident pages too for oversight
        if (isResident || isAdmin) {
            try {
                System.out.println("Resident access granted for: " + session.getFullName());
                initalizeResidentHomePage();
                optionPagePrimaryStage.setScene(residentHomePage2Scene);
            } catch (Exception residentHomePageexception) {
                residentHomePageexception.printStackTrace();
            }
        } else {
            showAccessDeniedAlert("Resident Access Restricted", 
                "You need to be a registered Resident or Admin to access this section.");
            System.out.println("Resident access denied for user: " + session.getFullName() + " (Role: " + userRole + ")");
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


//Guest VBox click - Guest can be accessed by anyone (for browsing available flats, etc.)      
       guestVBox.setOnMouseClicked(e -> {                                 
        // Guest mode is available to all users
        initalizeGuestOption();
        optionPagePrimaryStage.setScene(guest2Scene);
       System.out.println("Guest mode accessed by: " + session.getFullName());
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

        // Visual indicator for disabled options based on role
        if (!isAdmin) {
            // Dim the admin option for non-admin users
            adminTx.setStyle("-fx-font-size: 24px; -fx-fill: #888888; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
            Text adminLockText = new Text(" (Admin Only)");
            adminLockText.setStyle("-fx-font-size: 14px; -fx-fill: #cc0000; -fx-font-family: Comic Sans MS");
            adminVBox.getChildren().add(adminLockText);
        }
 
      //  Adding a About button

                Button aboutButton = new Button("About");
                aboutButton.setStyle(
                                " -fx-border-color: #a9d8fcff;-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242)); -fx-background-radius: 20; -fx-border-radius: 20;-fx-font-size: 24px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
                aboutButton.setPadding(new Insets(5, 15, 5, 15));
                aboutButton.setAlignment(Pos.CENTER);
                aboutButton.setPrefWidth(200);

                // Add action
                aboutButton.setOnAction(e -> {
                        System.out.println("About button clicked!");

                        //code of navigation
                        initalizeAboutPage();
                        optionPagePrimaryStage.setScene(about2Scene);
                });

                VBox heightVBox = new VBox();
                heightVBox.setPrefHeight(10);

        // Welcome text showing logged-in user
        Text welcomeText = new Text("Welcome, " + (session.getFullName() != null ? session.getFullName() : "User") + "!");
        welcomeText.setStyle("-fx-font-size: 28px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        
        Text roleText = new Text("Your Role: " + (userRole != null ? userRole.substring(0, 1).toUpperCase() + userRole.substring(1) : "Unknown"));
        roleText.setStyle("-fx-font-size: 16px; -fx-fill: #666666; -fx-font-family: Comic Sans MS");

        VBox welcomeBox = new VBox(5, welcomeText, roleText);
        welcomeBox.setAlignment(Pos.CENTER);

//
        VBox optionPageVBox = new VBox(30, welcomeBox, adminVBox,residentVBox,guestVBox,heightVBox,aboutButton);
        optionPageVBox .setAlignment(Pos.CENTER);
        optionPageVBox.setPrefWidth(50);
        optionPageVBox.setPrefHeight(200);
        optionPageVBox.setStyle("-fx-background-color: LAVENDER;");
        return optionPageVBox;


    
}

        /**
         * Show access denied alert dialog
         */
        private void showAccessDeniedAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText("Access Restricted");
            alert.setContentText(message);
            alert.showAndWait();
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
