package com.login.View.HomePages;



import com.login.View.AIPage;
import com.login.View.User;
import com.login.View.emergency_contacts;
import com.login.View.raise_issues;
import com.login.View.rentflat;
import com.login.View.resident_notify;
import com.login.View.Electricity.ResidentElectricity;
import com.login.View.Maintanance.Residentmaintenance;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomePageRsident {


        Scene residentHomePage1Scene,residentMaintenancePage2Scene,residentElectricityPage2Scene,emergencyPage2Scene,ai2Scene,raiseIssue2Scene,rentFlat2Scene,residentNotify2Scene,residentProfile2Scene;
        Stage residentHomePagePrimaryStage;


        public void setResidentHomePage1Scene(Scene residentHomePage1Scene) {
                this.residentHomePage1Scene = residentHomePage1Scene;
        }

        public void setResidentHomePagePrimaryStage(Stage residentHomePagePrimaryStage) {
                this.residentHomePagePrimaryStage = residentHomePagePrimaryStage;
        }



        public VBox createResidentHomePageScene(){


// NavBar Buttons
      //homepage button:
       Button homePageButton = new Button("Home");
       homePageButton.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent arg0) {
          // add HomePAge on cllick logic here
        }
        
       });
        homePageButton.setPrefWidth(300);
        homePageButton.setPrefHeight(40);
        homePageButton.setStyle(
       "-fx-text-fill: DARKSLATEGRAY;" +                
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: white;" +             
       "-fx-background-radius: 8px;" +              
       "-fx-border-radius: 8px;"  +                  
       "-fx-padding: 8 16 8 16;" );
       
//hover effect to button   
       
        homePageButton.setOnMouseEntered(e -> homePageButton.setStyle(
       "-fx-text-fill: DARKSLATEGRAY;" +
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: #f5f5f5;" + // Slightly grey background on hover
       "-fx-background-radius: 8;" +
       "-fx-border-radius: 8;" +
       "-fx-padding: 8 16 8 16;" +
       "-fx-cursor: hand;"));

        homePageButton.setOnMouseExited(e -> homePageButton.setStyle(
        "-fx-text-fill: DARKSLATEGRAY;" +
        "-fx-font-size: 15px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-color: white;" +
        "-fx-background-radius: 8;" +
        "-fx-border-radius: 8;" +
        "-fx-padding: 8 16 8 16;" +
        "-fx-cursor: hand;"));

// AI page Button
          
       Button aIPageButton = new Button("AIgenie");       //AIgenie – AI + Genie (interior wishes granted)
              aIPageButton.setOnMouseClicked(e -> {                                
          // write on press code here
          try {
                 System.out.println("ai button clicked!");
                initalizeAIPage();
                residentHomePagePrimaryStage.setScene(ai2Scene);
          } catch (Exception initalizeAIPageException) {

               initalizeAIPageException.printStackTrace();
          }
        
         });
        aIPageButton.setPrefWidth(300);
        aIPageButton.setPrefHeight(40);
        aIPageButton.setStyle("-fx-text-fill: DARKSLATEGRAY;" +                
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: white;" +             
       "-fx-background-radius: 8px;" +              
       "-fx-border-radius: 8px;"  +                  
       "-fx-padding: 8 16 8 16;" );
       
//hover effect to button   
       
        aIPageButton.setOnMouseEntered(e -> aIPageButton.setStyle(
       "-fx-text-fill: DARKSLATEGRAY;" +
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: #f5f5f5;" + // Slightly grey background on hover
       "-fx-background-radius: 8;" +
       "-fx-border-radius: 8;" +
       "-fx-padding: 8 16 8 16;" +
       "-fx-cursor: hand;"));

        aIPageButton.setOnMouseExited(e -> aIPageButton.setStyle(
        "-fx-text-fill: DARKSLATEGRAY;" +
        "-fx-font-size: 15px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-color: white;" +
        "-fx-background-radius: 8;" +
        "-fx-border-radius: 8;" +
        "-fx-padding: 8 16 8 16;" +
        "-fx-cursor: hand;"));


// Notification Page Button
       Button notificationPageButton = new Button("Notifications");
              notificationPageButton.setOnMouseClicked(e -> {                                
          // write on press code here
          try {

                initalizeResidentNotification();
                residentHomePagePrimaryStage.setScene(residentNotify2Scene);
                 System.out.println(" Resident notification button clicked!");
          
          } catch (Exception adminNotificationPageException) {

               adminNotificationPageException.printStackTrace();
          }
        
         });
        notificationPageButton.setPrefWidth(300);
        notificationPageButton.setPrefHeight(40);
        notificationPageButton.setStyle(
       "-fx-text-fill: DARKSLATEGRAY;" +                
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: white;" +             
       "-fx-background-radius: 8px;" +              
       "-fx-border-radius: 8px;"  +                  
       "-fx-padding: 8 16 8 16;" );
       
//hover effect to button   
       
        notificationPageButton.setOnMouseEntered(e -> notificationPageButton.setStyle(
       "-fx-text-fill: DARKSLATEGRAY;" +
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: #f5f5f5;" + // Slightly grey background on hover
       "-fx-background-radius: 8;" +
       "-fx-border-radius: 8;" +
       "-fx-padding: 8 16 8 16;" +
       "-fx-cursor: hand;"));

    notificationPageButton.setOnMouseExited(e -> notificationPageButton.setStyle(
        "-fx-text-fill: DARKSLATEGRAY;" +
        "-fx-font-size: 15px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-color: white;" +
        "-fx-background-radius: 8;" +
        "-fx-border-radius: 8;" +
        "-fx-padding: 8 16 8 16;" +
        "-fx-cursor: hand;"));


// Profile Page Button
       Button profilePageButton = new Button("Profile");
              profilePageButton.setOnMouseClicked(e -> { 
               

          try {
                 initalizeResidentProfilePage();
                residentHomePagePrimaryStage.setScene(residentProfile2Scene);                               
          // write on press code here
                 System.out.println("ai button clicked!");
      
          } catch (Exception initalizeAIPageException) {

               initalizeAIPageException.printStackTrace();
          }
        
         });
        profilePageButton.setPrefWidth(300);
        profilePageButton.setPrefHeight(40);
        profilePageButton.setStyle(
       "-fx-text-fill: DARKSLATEGRAY;" +                
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: white;" +             
       "-fx-background-radius: 8px;" +              
       "-fx-border-radius: 8px;"  +                  
       "-fx-padding: 8 16 8 16;" );
       
//hover effect to button   
       
       profilePageButton.setOnMouseEntered(e -> profilePageButton.setStyle(
       "-fx-text-fill: DARKSLATEGRAY;" +
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: #f5f5f5;" + // Slightly grey background on hover
       "-fx-background-radius: 8;" +
       "-fx-border-radius: 8;" +
       "-fx-padding: 8 16 8 16;" +
       "-fx-cursor: hand;"));

        profilePageButton.setOnMouseExited(e -> profilePageButton.setStyle(
        "-fx-text-fill: DARKSLATEGRAY;" +
        "-fx-font-size: 15px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-color: white;" +
        "-fx-background-radius: 8;" +
        "-fx-border-radius: 8;" +
        "-fx-padding: 8 16 8 16;" +
        "-fx-cursor: hand;"));



//maintenance text
        Text maintenanceTx = new Text("Maintenance");
        maintenanceTx.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        maintenanceTx.setTextAlignment(TextAlignment.CENTER);

         //Hover
         maintenanceTx.setOnMouseEntered(e -> {maintenanceTx.setScaleX(1.1); maintenanceTx.setScaleY(1.1); });
         maintenanceTx.setOnMouseExited(e -> {maintenanceTx.setScaleX(1.0);maintenanceTx.setScaleY(1.0);});

         


 //elctricity text
        Text electricityTx = new Text("Electricity");
        electricityTx.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        electricityTx.setTextAlignment(TextAlignment.CENTER);

         //Hover
         electricityTx.setOnMouseEntered(e -> {electricityTx.setScaleX(1.1); electricityTx.setScaleY(1.1); });
         electricityTx.setOnMouseExited(e -> {electricityTx.setScaleX(1.0);electricityTx.setScaleY(1.0);});

         //tansition
         FadeTransition maintananceFadeTransition = new FadeTransition(Duration.millis(1000), electricityTx );
           maintananceFadeTransition.setFromValue(0.0);
           maintananceFadeTransition.setToValue(1.0);

//Raised Issue text
        Text raisedIssueTx = new Text("Raise an issue");
        raisedIssueTx.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        raisedIssueTx.setTextAlignment(TextAlignment.CENTER);

//Hover
         raisedIssueTx.setOnMouseEntered(e -> {raisedIssueTx.setScaleX(1.1); raisedIssueTx.setScaleY(1.1); });
         raisedIssueTx.setOnMouseExited(e -> {raisedIssueTx.setScaleX(1.0);raisedIssueTx.setScaleY(1.0);});
         
//tansition
         FadeTransition raisedIssueFadeTransition = new FadeTransition(Duration.millis(1000), raisedIssueTx );
           raisedIssueFadeTransition.setFromValue(0.0);
           raisedIssueFadeTransition.setToValue(1.0);
           raisedIssueFadeTransition.play();
         
//Rent Flat text
        Text rentFlatTx = new Text("Rent Flat");
        rentFlatTx.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        rentFlatTx.setTextAlignment(TextAlignment.CENTER);

//Hover
         rentFlatTx.setOnMouseEntered(e -> {rentFlatTx.setScaleX(1.1); rentFlatTx.setScaleY(1.1); });
         rentFlatTx.setOnMouseExited(e -> {rentFlatTx.setScaleX(1.0);rentFlatTx.setScaleY(1.0);});

 //tansition
         FadeTransition rentFlatTransition = new FadeTransition(Duration.millis(1000),rentFlatTx );
           rentFlatTransition.setFromValue(0.0);
           rentFlatTransition.setToValue(1.0);
           rentFlatTransition.play();


//Emergency contact text
        Text emergencyContactTx = new Text("Emergency Contact");
        emergencyContactTx.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        emergencyContactTx.setTextAlignment(TextAlignment.CENTER);
//Hover
         emergencyContactTx.setOnMouseEntered(e -> {emergencyContactTx.setScaleX(1.1); emergencyContactTx.setScaleY(1.1); });
         emergencyContactTx.setOnMouseExited(e -> {emergencyContactTx.setScaleX(1.0);emergencyContactTx.setScaleY(1.0);});


//tansition
         FadeTransition emergencyContactFadeTransition = new FadeTransition(Duration.millis(1000), emergencyContactTx);
           emergencyContactFadeTransition.setFromValue(0.0);
           emergencyContactFadeTransition.setToValue(1.0);
           emergencyContactFadeTransition.play();

    

   

//Maintenance Vbox

         VBox maintenanceVBox = new VBox(maintenanceTx);
         maintenanceVBox.setPrefWidth(400);     // Preferred width
         maintenanceVBox.setPrefHeight(200);    // Preferred height
         maintenanceVBox.setMaxWidth(800);      // Prevent it from stretching beyond this
         maintenanceVBox.setMinWidth(400);      // Prevent it from shrinking
         maintenanceVBox.setPadding(new Insets(20));     // space inside container
         maintenanceVBox.setSpacing(10);                 // space between child nodes
         maintenanceVBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-border-color: white;" +"-fx-border-width: 2px;" +"-fx-border-radius: 15px;" +"-fx-background-radius: 15px;");
         maintenanceVBox.setAlignment(Pos.CENTER);
//maintanance vBox VBox click       
          maintenanceVBox.setOnMouseClicked(e -> {                                
          // write on press code here
          try {
                 initalizeResidentMaintenancePage();
          residentHomePagePrimaryStage.setScene(residentMaintenancePage2Scene);
                
          } catch (Exception residentMaintenance) {

                residentMaintenance.printStackTrace();
          }
         
          System.out.println("maintananceVBox  button clicked!");
//transition effect
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100),maintenanceVBox);
            scaleDown.setToX(0.95);
            scaleDown.setToY(0.95);

            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), maintenanceVBox);
            scaleUp.setToX(1.0);
            scaleUp.setToY(1.0);

            scaleDown.setOnFinished(event -> scaleUp.play());
            scaleDown.play();
         });
 //electricity vbox
         VBox electricityVBox = new VBox(electricityTx);
         electricityVBox.setPrefWidth(400);     // Preferred width
         electricityVBox.setPrefHeight(200);    // Preferred height
         electricityVBox.setMaxWidth(800);      // Prevent it from stretching beyond this
         electricityVBox.setMinWidth(400);      // Prevent it from shrinking
         electricityVBox.setPadding(new Insets(20));     // space inside container
         electricityVBox.setSpacing(10);                 // space between child nodes
         electricityVBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-border-color: white;" +"-fx-border-width: 2px;" +"-fx-border-radius: 15px;" +"-fx-background-radius: 15px;");
           electricityVBox.setAlignment(Pos.CENTER);
//electricity vBox VBox click       
          electricityVBox.setOnMouseClicked(e -> {                                
          // write on press code here
          try {
                initalizeResidentElectricityPage();
                residentHomePagePrimaryStage.setScene(residentElectricityPage2Scene);
          } catch (Exception residentElctricityException) {
                residentElctricityException.printStackTrace();
                
          }
          System.out.println("Electricity   button clicked!");
//transition effect
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100),electricityVBox);
            scaleDown.setToX(0.95);
            scaleDown.setToY(0.95);

            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), electricityVBox);
            scaleUp.setToX(1.0);
            scaleUp.setToY(1.0);

            scaleDown.setOnFinished(event -> scaleUp.play());
            scaleDown.play();
         });

//raised issue
         VBox raisedIssueVBox = new VBox(raisedIssueTx);
         raisedIssueVBox.setPrefWidth(400);     // Preferred width
         raisedIssueVBox.setPrefHeight(200);    // Preferred height
         raisedIssueVBox.setMaxWidth(800);      // Prevent it from stretching beyond this
         raisedIssueVBox.setMinWidth(400);      // Prevent it from shrinking
         raisedIssueVBox.setPadding(new Insets(20));     // space inside container
         raisedIssueVBox.setSpacing(10);                 // space between child nodes
         raisedIssueVBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-border-color: white;" +"-fx-border-width: 2px;" +"-fx-border-radius: 15px;" +"-fx-background-radius: 15px;");
           raisedIssueVBox.setAlignment(Pos.CENTER);
//raisedIssue vBox VBox click       
          raisedIssueVBox.setOnMouseClicked(e -> {                                
          initalizeRaiseIssuePage();
          residentHomePagePrimaryStage.setScene(raiseIssue2Scene);
          
          System.out.println("Raised Issue  button clicked!");
 //transition effect
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100),raisedIssueVBox);
            scaleDown.setToX(0.95);
            scaleDown.setToY(0.95);

            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), raisedIssueVBox);
            scaleUp.setToX(1.0);
            scaleUp.setToY(1.0);

            scaleDown.setOnFinished(event -> scaleUp.play());
            scaleDown.play();
         });

        
//rent Flat vbox
         VBox rentFlatVBox= new VBox(rentFlatTx );
         rentFlatVBox.setPrefWidth(400);     // Preferred width
         rentFlatVBox.setPrefHeight(200);    // Preferred height
         rentFlatVBox.setMaxWidth(800);      // Prevent it from stretching beyond this
         rentFlatVBox.setMinWidth(400);      // Prevent it from shrinking
         rentFlatVBox.setPadding(new Insets(20));     // space inside container
         rentFlatVBox.setSpacing(10);                 // space between child nodes
         rentFlatVBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-border-color: white;" +"-fx-border-width: 2px;" +"-fx-border-radius: 15px;" +"-fx-background-radius: 15px;");
           rentFlatVBox.setAlignment(Pos.CENTER);
//rentFlat vBox VBox click       
          rentFlatVBox.setOnMouseClicked(e -> {                                
          // write on press code here
          initalizeRentPage();
          residentHomePagePrimaryStage.setScene(rentFlat2Scene);
          System.out.println("Rent Flat  button clicked!");
            //transition effect
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100),rentFlatVBox);
            scaleDown.setToX(0.95);
            scaleDown.setToY(0.95);

            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), rentFlatVBox);
            scaleUp.setToX(1.0);
            scaleUp.setToY(1.0);

            scaleDown.setOnFinished(event -> scaleUp.play());
            scaleDown.play();
         });

         
//emergency issue VBox
         VBox emergencyVBox = new VBox(  emergencyContactTx );
         emergencyVBox.setPrefWidth(400);     // Preferred width
         emergencyVBox.setPrefHeight(200);    // Preferred height
         emergencyVBox.setMaxWidth(800);      // Prevent it from stretching beyond this
         emergencyVBox.setMinWidth(400);      // Prevent it from shrinking
         emergencyVBox.setPadding(new Insets(20));     // space inside container
         emergencyVBox.setSpacing(10);                 // space between child nodes
         emergencyVBox.setStyle( "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));"+"-fx-border-color: white;" +"-fx-border-width: 2px;" +"-fx-border-radius: 15px;" +"-fx-background-radius: 15px;");
           emergencyVBox.setAlignment(Pos.CENTER);
            
          emergencyVBox.setOnMouseClicked(e -> {                                
          InitalizeEmergencyPage();
           residentHomePagePrimaryStage.setScene(emergencyPage2Scene);
          System.out.println("emergency  button clicked!");
//transition effect
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100),emergencyVBox);
            scaleDown.setToX(0.95);
            scaleDown.setToY(0.95);

            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), emergencyVBox);
            scaleUp.setToX(1.0);
            scaleUp.setToY(1.0);

            scaleDown.setOnFinished(event -> scaleUp.play());
            scaleDown.play();
         });

// Logo at bottom of navbar
         Image logoImage = new Image(getClass().getResource("/Assets/Civita Logo Official.png").toExternalForm());
         ImageView logoImageView = new ImageView(logoImage);
         logoImageView.setPreserveRatio(true);
         logoImageView.setSmooth(true);
         logoImageView.setFitWidth(280); // Adjust width to fit nicely in the navbar
         logoImageView.setFitHeight(200);

         VBox logoBox = new VBox(logoImageView);
         logoBox.setAlignment(Pos.BOTTOM_CENTER);
         logoBox.setPadding(new Insets(20, 0, 10, 0)); // top, right, bottom, left
         logoBox.setStyle("-fx-background-color: transparent;");

// Push logoBox to bottom using spacer
         VBox spacer = new VBox();
         VBox.setVgrow(spacer, Priority.ALWAYS);
          
//navbar VBox

         VBox navBarVBox = new VBox(35,homePageButton,aIPageButton,notificationPageButton,profilePageButton,spacer,logoBox);
         navBarVBox.setPrefWidth(300);
         navBarVBox.setMaxHeight(800);
         navBarVBox.setPadding(new Insets(20));
        
         navBarVBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));");



// feature vbox

        VBox featuresVBox = new VBox(maintenanceVBox,electricityVBox,raisedIssueVBox,rentFlatVBox,emergencyVBox);
        
         featuresVBox.setAlignment(Pos.CENTER);
         featuresVBox.setPadding(new Insets(20));
         featuresVBox.setSpacing(20);
         HBox.setHgrow(featuresVBox, Priority.ALWAYS); // allow it to expand
// featuresVbox Wrap in scroll pane
         ScrollPane featureListScrollPane = new ScrollPane(featuresVBox);
         featureListScrollPane.setFitToWidth(true);
         featureListScrollPane.setPannable(true);
         featureListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
         featureListScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
         featureListScrollPane.setStyle(
         "-fx-border-color: #ffffffff;" +       // Border color 
         "-fx-border-width: 2px;" +           //  Border thickness
         "-fx-border-radius: 10px;" +         //  Rounded corners
         "-fx-background-radius: 10px;"       // Match background radius
         );
         

// navbar and feature Hbox

        HBox homePageHBox = new HBox(navBarVBox,featureListScrollPane);
        HBox.setHgrow(featureListScrollPane, Priority.ALWAYS);
        VBox homePageRootVBox = new VBox(homePageHBox);
        homePageRootVBox .setAlignment(Pos.CENTER);
        homePageRootVBox.setPrefWidth(50);
        homePageRootVBox.setPrefHeight(200);
        homePageRootVBox.setStyle("-fx-background-color: LAVENDER;");
      //  Scene homePageScene = new Scene(homePageRootVBox);
      //  homStage.setScene(homePageScene);
       // homStage.show();
        //homStage.setMaximized(true);
        return homePageRootVBox;
    

}

//maintenance navigation - now uses UserSession for user UID
private void initalizeResidentMaintenancePage(){
Residentmaintenance residentmaintenanceObj = new Residentmaintenance();
residentmaintenanceObj.setResidentMaintenancePrimaryStage(residentHomePagePrimaryStage);
// No need to pass userUid - Residentmaintenance uses UserSession automatically
residentMaintenancePage2Scene=new Scene(residentmaintenanceObj.createResidentMaintenanceScene(this::handleBackResidentHomePageButton),1600,800);
residentmaintenanceObj.setResidentMaintenance1Scene(residentMaintenancePage2Scene);

}

//electricity Navigation

private void initalizeResidentElectricityPage(){
ResidentElectricity residentElectricityObj = new ResidentElectricity();
residentElectricityObj.setResidentElectricityPrimaryStage(residentHomePagePrimaryStage);
residentElectricityPage2Scene=new Scene(residentElectricityObj.createResidentElectricityScene(this::handleBackResidentHomePageButton),1600,800);
residentElectricityObj.setResidentElectricity1Scene(residentElectricityPage2Scene);


}

    private void handleBackResidentHomePageButton(){

        residentHomePagePrimaryStage.setScene(residentHomePage1Scene);
    }

    private void InitalizeEmergencyPage(){
        emergency_contacts emergencyPageObj= new emergency_contacts();
        emergencyPageObj.setEmergencyPrimaryStage(residentHomePagePrimaryStage);
        emergencyPage2Scene = new Scene(emergencyPageObj.createAdminEmergencyScene(this::handleBackResidentHomePageButton),1600,800);
        emergencyPageObj.setEmergency2Scene(emergencyPage2Scene);
}

private void initalizeAIPage(){

        AIPage aiPageObj = new AIPage();
        aiPageObj.setaIPrimaryStage(residentHomePagePrimaryStage);
        ai2Scene= new Scene(aiPageObj.createAIScene(this::handleBackResidentHomePageButton),1600,800);
        aiPageObj.setaI1Scene(ai2Scene);
}

private void initalizeRaiseIssuePage(){
raise_issues raiseIssueObj = new raise_issues();
raiseIssueObj.setRaiseIssuePrimaryStage(residentHomePagePrimaryStage);
raiseIssue2Scene= new Scene(raiseIssueObj.createResidentRaiseIssueScene(this::handleBackResidentHomePageButton),1600,800);
raiseIssueObj.setResidentRaiseIssuScene(raiseIssue2Scene);


}

private void  initalizeRentPage(){

        rentflat rentflatObj = new rentflat();
        rentflatObj.setRentFlatPrimaryStage(residentHomePagePrimaryStage);
        rentFlat2Scene = new Scene(rentflatObj.createRentFlatScene(this::handleBackResidentHomePageButton),1600,800);
        rentflatObj.setRentFlat1Scene(rentFlat2Scene);



}

private void initalizeResidentNotification(){

        resident_notify residentNotificationObj = new resident_notify();
        residentNotificationObj.setResidentNotifyPrimaryStage(residentHomePagePrimaryStage);
        residentNotify2Scene= new Scene(residentNotificationObj.createResidentNotifyScene(this::handleBackResidentHomePageButton),1600,800);
        residentNotificationObj.setResidentNotify1Scene(residentNotify2Scene);



}

private void initalizeResidentProfilePage(){

        User userProfileObj = new User();
        userProfileObj.setUserProfilePrimaryStage(residentHomePagePrimaryStage);
        residentProfile2Scene= new Scene(userProfileObj.createUserProfileScene(this::handleBackResidentHomePageButton),1600,800);
        userProfileObj.setUserProfile1Scene(residentProfile2Scene);

}



}
