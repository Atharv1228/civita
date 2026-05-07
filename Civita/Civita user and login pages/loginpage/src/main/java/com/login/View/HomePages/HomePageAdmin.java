package com.login.View.HomePages;



import com.login.View.AIPage;
import com.login.View.Admin;
import com.login.View.admin_notify;
import com.login.View.emergency_contacts;
import com.login.View.sellflat;
import com.login.View.view_issues;
import com.login.View.Electricity.AdminElectricity;
import com.login.View.Maintanance.AdminMaintenanceFlatList;

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

public class HomePageAdmin {
        Scene adminHomePage1Scene,adminMaintenancePage2Scene,adminElectricityPage2Scene,adminviewissue1Scene,emergencyPage2Scene,sell2Scene,ai2Scene , adminNotificaton2Scene,adminProfile2Scene;
        Stage adminHomePagePrimaryStage;


        public void setAdminHomePage1Scene(Scene adminHomePage1Scene) {
                this.adminHomePage1Scene = adminHomePage1Scene;
        }

        public void setAdminHomePagePrimaryStage(Stage adminHomePagePrimaryStage) {
                this.adminHomePagePrimaryStage = adminHomePagePrimaryStage;
        }

     

        public VBox createAdminHomePageScene(){


// NavBar Buttons
      //homepage button:
       Button adminHomePageButton = new Button("Home");
       adminHomePageButton.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent arg0) {
          // add HomePAge on cllick logic here
        }
        
       });
        adminHomePageButton.setPrefWidth(300);
        adminHomePageButton.setPrefHeight(40);
        adminHomePageButton.setStyle(
       "-fx-text-fill: DARKSLATEGRAY;" +                
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: white;" +             
       "-fx-background-radius: 8px;" +              
       "-fx-border-radius: 8px;"  +                  
       "-fx-padding: 8 16 8 16;" );
       
//hover effect to button   
       
        adminHomePageButton.setOnMouseEntered(e -> adminHomePageButton.setStyle(
       "-fx-text-fill: DARKSLATEGRAY;" +
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: #f5f5f5;" + // Slightly grey background on hover
       "-fx-background-radius: 8;" +
       "-fx-border-radius: 8;" +
       "-fx-padding: 8 16 8 16;" +
       "-fx-cursor: hand;"));

        adminHomePageButton.setOnMouseExited(e -> adminHomePageButton.setStyle(
        "-fx-text-fill: DARKSLATEGRAY;" +
        "-fx-font-size: 15px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-color: white;" +
        "-fx-background-radius: 8;" +
        "-fx-border-radius: 8;" +
        "-fx-padding: 8 16 8 16;" +
        "-fx-cursor: hand;"));

// AI page Button
          
       Button adminAiPageButton = new Button("AIgenie");       //AIgenie – AI + Genie (interior wishes granted)
              adminAiPageButton.setOnMouseClicked(e -> {                                
          // write on press code here
          try {
                 System.out.println("ai button clicked!");
                initalizeAIPage();
                adminHomePagePrimaryStage.setScene(ai2Scene);
          } catch (Exception initalizeAIPageException) {

               initalizeAIPageException.printStackTrace();
          }
        
         });
        adminAiPageButton.setPrefWidth(300);
        adminAiPageButton.setPrefHeight(40);
        adminAiPageButton.setStyle("-fx-text-fill: DARKSLATEGRAY;" +                
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: white;" +             
       "-fx-background-radius: 8px;" +              
       "-fx-border-radius: 8px;"  +                  
       "-fx-padding: 8 16 8 16;" );
       
//hover effect to button   
       
        adminAiPageButton.setOnMouseEntered(e -> adminAiPageButton.setStyle(
       "-fx-text-fill: DARKSLATEGRAY;" +
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: #f5f5f5;" + // Slightly grey background on hover
       "-fx-background-radius: 8;" +
       "-fx-border-radius: 8;" +
       "-fx-padding: 8 16 8 16;" +
       "-fx-cursor: hand;"));

        adminAiPageButton.setOnMouseExited(e -> adminAiPageButton.setStyle(
        "-fx-text-fill: DARKSLATEGRAY;" +
        "-fx-font-size: 15px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-color: white;" +
        "-fx-background-radius: 8;" +
        "-fx-border-radius: 8;" +
        "-fx-padding: 8 16 8 16;" +
        "-fx-cursor: hand;"));


// Notification Page Button
       Button adminNotificationPageButton = new Button("Notifications");
              adminNotificationPageButton.setOnMouseClicked(e -> {                                
          // write on press code here
          try {
                 System.out.println("notification button clicked!");
                initalizeAdminNotificationPage();
                adminHomePagePrimaryStage.setScene(adminNotificaton2Scene);
          } catch (Exception adminNotificationPageException) {

               adminNotificationPageException.printStackTrace();
          }
        
         });
        adminNotificationPageButton.setPrefWidth(300);
        adminNotificationPageButton.setPrefHeight(40);
        adminNotificationPageButton.setStyle(
       "-fx-text-fill: DARKSLATEGRAY;" +                
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: white;" +             
       "-fx-background-radius: 8px;" +              
       "-fx-border-radius: 8px;"  +                  
       "-fx-padding: 8 16 8 16;" );
       
//hover effect to button   
       
        adminNotificationPageButton.setOnMouseEntered(e -> adminNotificationPageButton.setStyle(
       "-fx-text-fill: DARKSLATEGRAY;" +
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: #f5f5f5;" + // Slightly grey background on hover
       "-fx-background-radius: 8;" +
       "-fx-border-radius: 8;" +
       "-fx-padding: 8 16 8 16;" +
       "-fx-cursor: hand;"));

    adminNotificationPageButton.setOnMouseExited(e -> adminNotificationPageButton.setStyle(
        "-fx-text-fill: DARKSLATEGRAY;" +
        "-fx-font-size: 15px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-color: white;" +
        "-fx-background-radius: 8;" +
        "-fx-border-radius: 8;" +
        "-fx-padding: 8 16 8 16;" +
        "-fx-cursor: hand;"));


// Profile Page Button
       Button adminProfilePageButton = new Button("Profile");
              adminProfilePageButton.setOnMouseClicked(e -> {                                
          // write on press code here
          try {
                 System.out.println("ai button clicked!");
                initalizeAdminProfile();
                adminHomePagePrimaryStage.setScene(adminProfile2Scene);
          } catch (Exception initalizeAIPageException) {

               initalizeAIPageException.printStackTrace();
          }
        
         });
        adminProfilePageButton.setPrefWidth(300);
        adminProfilePageButton.setPrefHeight(40);
        adminProfilePageButton.setStyle(
       "-fx-text-fill: DARKSLATEGRAY;" +                
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: white;" +             
       "-fx-background-radius: 8px;" +              
       "-fx-border-radius: 8px;"  +                  
       "-fx-padding: 8 16 8 16;" );
       
//hover effect to button   
       
       adminProfilePageButton.setOnMouseEntered(e -> adminProfilePageButton.setStyle(
       "-fx-text-fill: DARKSLATEGRAY;" +
       "-fx-font-size: 15px;" +
       "-fx-font-weight: bold;" +
       "-fx-background-color: #f5f5f5;" + // Slightly grey background on hover
       "-fx-background-radius: 8;" +
       "-fx-border-radius: 8;" +
       "-fx-padding: 8 16 8 16;" +
       "-fx-cursor: hand;"));

        adminProfilePageButton.setOnMouseExited(e -> adminProfilePageButton.setStyle(
        "-fx-text-fill: DARKSLATEGRAY;" +
        "-fx-font-size: 15px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-color: white;" +
        "-fx-background-radius: 8;" +
        "-fx-border-radius: 8;" +
        "-fx-padding: 8 16 8 16;" +
        "-fx-cursor: hand;"));



//maintanance text
        Text adminMaintenanceTx = new Text("Maintenance");
        adminMaintenanceTx.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        adminMaintenanceTx.setTextAlignment(TextAlignment.CENTER);

         //Hover
         adminMaintenanceTx.setOnMouseEntered(e -> {adminMaintenanceTx.setScaleX(1.1); adminMaintenanceTx.setScaleY(1.1); });
         adminMaintenanceTx.setOnMouseExited(e -> {adminMaintenanceTx.setScaleX(1.0);adminMaintenanceTx.setScaleY(1.0);});

         


 //elctricity text
        Text adminElectricityTx = new Text("Electricity");
        adminElectricityTx.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        adminElectricityTx.setTextAlignment(TextAlignment.CENTER);

         //Hover
         adminElectricityTx.setOnMouseEntered(e -> {adminElectricityTx.setScaleX(1.1); adminElectricityTx.setScaleY(1.1); });
         adminElectricityTx.setOnMouseExited(e -> {adminElectricityTx.setScaleX(1.0);adminElectricityTx.setScaleY(1.0);});

         //tansition
         FadeTransition adminMaintananceFadeTransition = new FadeTransition(Duration.millis(1000), adminElectricityTx );
           adminMaintananceFadeTransition.setFromValue(0.0);
           adminMaintananceFadeTransition.setToValue(1.0);

//Raised Issue text
        Text adminViewIssueTx = new Text("View Issue");
        adminViewIssueTx.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        adminViewIssueTx.setTextAlignment(TextAlignment.CENTER);

//Hover
         adminViewIssueTx.setOnMouseEntered(e -> {adminViewIssueTx.setScaleX(1.1); adminViewIssueTx.setScaleY(1.1); });
         adminViewIssueTx.setOnMouseExited(e -> {adminViewIssueTx.setScaleX(1.0);adminViewIssueTx.setScaleY(1.0);});
         
//tansition
         FadeTransition adminViewIssueFadeTransition = new FadeTransition(Duration.millis(1000), adminViewIssueTx );
           adminViewIssueFadeTransition.setFromValue(0.0);
           adminViewIssueFadeTransition.setToValue(1.0);
           adminViewIssueFadeTransition.play();
         
//Rent Flat text
        Text adminSellFlatTx = new Text("Sell Flat");
        adminSellFlatTx.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        adminSellFlatTx.setTextAlignment(TextAlignment.CENTER);

//Hover
         adminSellFlatTx.setOnMouseEntered(e -> {adminSellFlatTx.setScaleX(1.1); adminSellFlatTx.setScaleY(1.1); });
         adminSellFlatTx.setOnMouseExited(e -> {adminSellFlatTx.setScaleX(1.0);adminSellFlatTx.setScaleY(1.0);});

 //tansition
         FadeTransition adminSellFlatTransition = new FadeTransition(Duration.millis(1000),adminSellFlatTx );
           adminSellFlatTransition.setFromValue(0.0);
           adminSellFlatTransition.setToValue(1.0);
           adminSellFlatTransition.play();


//Emergency contact text
        Text adminEmergencyContactTx = new Text("Emergency Contact");
        adminEmergencyContactTx.setStyle("-fx-font-size: 35px; -fx-fill: DARKSLATEGRAY ; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        adminEmergencyContactTx.setTextAlignment(TextAlignment.CENTER);
//Hover
         adminEmergencyContactTx.setOnMouseEntered(e -> {adminEmergencyContactTx.setScaleX(1.1); adminEmergencyContactTx.setScaleY(1.1); });
         adminEmergencyContactTx.setOnMouseExited(e -> {adminEmergencyContactTx.setScaleX(1.0);adminEmergencyContactTx.setScaleY(1.0);});


//tansition
         FadeTransition adminEmergencyContactFadeTransition = new FadeTransition(Duration.millis(1000), adminEmergencyContactTx);
           adminEmergencyContactFadeTransition.setFromValue(0.0);
           adminEmergencyContactFadeTransition.setToValue(1.0);
           adminEmergencyContactFadeTransition.play();

    

   

//Maintanance Vbox

         VBox adminMaintenanceVBox = new VBox(adminMaintenanceTx);
         adminMaintenanceVBox.setPrefWidth(400);     // Preferred width
         adminMaintenanceVBox.setPrefHeight(200);    // Preferred height
         adminMaintenanceVBox.setMaxWidth(800);      // Prevent it from stretching beyond this
         adminMaintenanceVBox.setMinWidth(400);      // Prevent it from shrinking
         adminMaintenanceVBox.setPadding(new Insets(20));     // space inside container
         adminMaintenanceVBox.setSpacing(10);                 // space between child nodes
         adminMaintenanceVBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-border-color: white;" +"-fx-border-width: 2px;" +"-fx-border-radius: 15px;" +"-fx-background-radius: 15px;");
         adminMaintenanceVBox.setAlignment(Pos.CENTER);
//maintanance vBox VBox click       
          adminMaintenanceVBox.setOnMouseClicked(e -> {                                
          // write on press code here
          try {
                 System.out.println("maintananceVBox  button clicked!");
                initalizeadminMaintenance();
                adminHomePagePrimaryStage.setScene(adminMaintenancePage2Scene);
          } catch (Exception initalizeadminMaintenancePageException) {

               initalizeadminMaintenancePageException.printStackTrace();
          }
         
//transition effect
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100),adminMaintenanceVBox);
            scaleDown.setToX(0.95);
            scaleDown.setToY(0.95);

            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), adminMaintenanceVBox);
            scaleUp.setToX(1.0);
            scaleUp.setToY(1.0);

            scaleDown.setOnFinished(event -> scaleUp.play());
            scaleDown.play();
         });
 //electricity vbox
         VBox adminElectricityVBox = new VBox(adminElectricityTx);
         adminElectricityVBox.setPrefWidth(400);     // Preferred width
         adminElectricityVBox.setPrefHeight(200);    // Preferred height
         adminElectricityVBox.setMaxWidth(800);      // Prevent it from stretching beyond this
         adminElectricityVBox.setMinWidth(400);      // Prevent it from shrinking
         adminElectricityVBox.setPadding(new Insets(20));     // space inside container
         adminElectricityVBox.setSpacing(10);                 // space between child nodes
         adminElectricityVBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-border-color: white;" +"-fx-border-width: 2px;" +"-fx-border-radius: 15px;" +"-fx-background-radius: 15px;");
           adminElectricityVBox.setAlignment(Pos.CENTER);
//electricity vBox VBox click       
          adminElectricityVBox.setOnMouseClicked(e -> {                                
          // write on press code here
          try {
                System.out.println("Electricity   button clicked!");
                initalizeadminElectricity();
                adminHomePagePrimaryStage.setScene(adminElectricityPage2Scene);
          } catch (Exception adminElectricityPageException) {
                adminElectricityPageException.printStackTrace();
          }
          
//transition effect
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100),adminElectricityVBox);
            scaleDown.setToX(0.95);
            scaleDown.setToY(0.95);

            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), adminElectricityVBox);
            scaleUp.setToX(1.0);
            scaleUp.setToY(1.0);

            scaleDown.setOnFinished(event -> scaleUp.play());
            scaleDown.play();
         });

//view issue
         VBox adminViewIssueVBox = new VBox(adminViewIssueTx);
         adminViewIssueVBox.setPrefWidth(400);     // Preferred width
         adminViewIssueVBox.setPrefHeight(200);    // Preferred height
         adminViewIssueVBox.setMaxWidth(800);      // Prevent it from stretching beyond this
         adminViewIssueVBox.setMinWidth(400);      // Prevent it from shrinking
         adminViewIssueVBox.setPadding(new Insets(20));     // space inside container
         adminViewIssueVBox.setSpacing(10);                 // space between child nodes
         adminViewIssueVBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-border-color: white;" +"-fx-border-width: 2px;" +"-fx-border-radius: 15px;" +"-fx-background-radius: 15px;");
           adminViewIssueVBox.setAlignment(Pos.CENTER);
//ViewIssue vBox VBox click       
          adminViewIssueVBox.setOnMouseClicked(e -> {                                
          initalizeAdminViewIssue();
          adminHomePagePrimaryStage.setScene(adminviewissue1Scene);
          System.out.println("Raised Issue  button clicked!");
 //transition effect
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100),adminViewIssueVBox);
            scaleDown.setToX(0.95);
            scaleDown.setToY(0.95);

            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), adminViewIssueVBox);
            scaleUp.setToX(1.0);
            scaleUp.setToY(1.0);

            scaleDown.setOnFinished(event -> scaleUp.play());
            scaleDown.play();
         });

        
//rent Flat vbox
         VBox adminSellFlatVBox= new VBox(adminSellFlatTx );
         adminSellFlatVBox.setPrefWidth(400);     // Preferred width
         adminSellFlatVBox.setPrefHeight(200);    // Preferred height
         adminSellFlatVBox.setMaxWidth(800);      // Prevent it from stretching beyond this
         adminSellFlatVBox.setMinWidth(400);      // Prevent it from shrinking
         adminSellFlatVBox.setPadding(new Insets(20));     // space inside container
         adminSellFlatVBox.setSpacing(10);                 // space between child nodes
         adminSellFlatVBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));" +"-fx-border-color: white;" +"-fx-border-width: 2px;" +"-fx-border-radius: 15px;" +"-fx-background-radius: 15px;");
           adminSellFlatVBox.setAlignment(Pos.CENTER);
//rentFlat vBox VBox click       
          adminSellFlatVBox.setOnMouseClicked(e -> {                                
          // write on press code here
          initalizeSellFlatPage();
          adminHomePagePrimaryStage.setScene(sell2Scene);
          System.out.println("Rent Flat  button clicked!");
            //transition effect
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100),adminSellFlatVBox);
            scaleDown.setToX(0.95);
            scaleDown.setToY(0.95);

            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), adminSellFlatVBox);
            scaleUp.setToX(1.0);
            scaleUp.setToY(1.0);

            scaleDown.setOnFinished(event -> scaleUp.play());
            scaleDown.play();
         });

         
//emergency issue VBox
         VBox adminEmergencyVBox = new VBox(  adminEmergencyContactTx );
         adminEmergencyVBox.setPrefWidth(400);     // Preferred width
         adminEmergencyVBox.setPrefHeight(200);    // Preferred height
         adminEmergencyVBox.setMaxWidth(800);      // Prevent it from stretching beyond this
         adminEmergencyVBox.setMinWidth(400);      // Prevent it from shrinking
         adminEmergencyVBox.setPadding(new Insets(20));     // space inside container
         adminEmergencyVBox.setSpacing(10);                 // space between child nodes
         adminEmergencyVBox.setStyle( "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));"+"-fx-border-color: white;" +"-fx-border-width: 2px;" +"-fx-border-radius: 15px;" +"-fx-background-radius: 15px;");
           adminEmergencyVBox.setAlignment(Pos.CENTER);
            
          adminEmergencyVBox.setOnMouseClicked(e -> {                                
          InitalizeEmergencyPage();
          adminHomePagePrimaryStage.setScene(emergencyPage2Scene);
          System.out.println("emergency  button clicked!");
//transition effect
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100),adminEmergencyVBox);
            scaleDown.setToX(0.95);
            scaleDown.setToY(0.95);

            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), adminEmergencyVBox);
            scaleUp.setToX(1.0);
            scaleUp.setToY(1.0);

            scaleDown.setOnFinished(event -> scaleUp.play());
            scaleDown.play();
         });

// Logo at bottom of navbar
         Image adminLogoImage = new Image(getClass().getResource("/Assets/Civita Logo Official.png").toExternalForm());
         ImageView adminLogoImageView = new ImageView(adminLogoImage);
         adminLogoImageView.setPreserveRatio(true);
         adminLogoImageView.setSmooth(true);
         adminLogoImageView.setFitWidth(280); // Adjust width to fit nicely in the navbar
         adminLogoImageView.setFitHeight(200);

         VBox adminLogoBox = new VBox(adminLogoImageView);
         adminLogoBox.setAlignment(Pos.BOTTOM_CENTER);
         adminLogoBox.setPadding(new Insets(20, 0, 10, 0)); // top, right, bottom, left
         adminLogoBox.setStyle("-fx-background-color: transparent;");

// Push logoBox to bottom using spacer
         VBox adminSpacer = new VBox();
         VBox.setVgrow(adminSpacer, Priority.ALWAYS);
          
//navbar VBox

         VBox adminNavBarVBox = new VBox(35,adminHomePageButton,adminAiPageButton,adminNotificationPageButton,adminProfilePageButton,adminSpacer,adminLogoBox);
         adminNavBarVBox.setPrefWidth(300);
         adminNavBarVBox.setMaxHeight(800);
         adminNavBarVBox.setPadding(new Insets(20));
        
         adminNavBarVBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%,rgb(255, 255, 255),rgb(197, 240, 242));");



// feature vbox

        VBox adminFeaturesVBox = new VBox(adminMaintenanceVBox,adminElectricityVBox,adminViewIssueVBox,adminSellFlatVBox,adminEmergencyVBox);
        
         adminFeaturesVBox.setAlignment(Pos.CENTER);
         adminFeaturesVBox.setPadding(new Insets(20));
         adminFeaturesVBox.setSpacing(20);
         HBox.setHgrow(adminFeaturesVBox, Priority.ALWAYS); // allow it to expand
// featuresVbox Wrap in scroll pane
          ScrollPane adminFeatureListScrollPane = new ScrollPane(adminFeaturesVBox);
         adminFeatureListScrollPane.setFitToWidth(true);
         adminFeatureListScrollPane.setPannable(true);
         adminFeatureListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
         adminFeatureListScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
         adminFeatureListScrollPane.setStyle(
         "-fx-border-color: #ffffffff;" +       // Border color 
         "-fx-border-width: 2px;" +           //  Border thickness
         "-fx-border-radius: 10px;" +         //  Rounded corners
         "-fx-background-radius: 10px;"       // Match background radius
         );
         

// navbar and feature Hbox

        HBox homePageAdminHBox = new HBox(adminNavBarVBox,adminFeatureListScrollPane);
        HBox.setHgrow(adminFeatureListScrollPane, Priority.ALWAYS);
        VBox homePageAdminRootVBox = new VBox(homePageAdminHBox);
        homePageAdminRootVBox .setAlignment(Pos.CENTER);
        homePageAdminRootVBox.setPrefWidth(50);
        homePageAdminRootVBox.setPrefHeight(200);
        homePageAdminRootVBox.setStyle("-fx-background-color: LAVENDER;");

        return homePageAdminRootVBox;



        //Scene homePageScene = new Scene(homePageAdminRootVBox);
        //adminHomeStage.setScene(homePageScene);
       // adminHomeStage.show();
       // adminHomeStage.setMaximized(true);
    }
//maintenance navigation

    private void initalizeadminMaintenance(){
    AdminMaintenanceFlatList adminMaintenancePageobj = new AdminMaintenanceFlatList();
    adminMaintenancePageobj.setAdminMaintanancePrimaryStage(adminHomePagePrimaryStage);
    adminMaintenancePage2Scene=new Scene(adminMaintenancePageobj.createAdminMaintenanceScene(this::handleBackAdminHomePageButton),1600,800);
    adminMaintenancePageobj.setAdminMaintenance1Scene(adminMaintenancePage2Scene);
    
    }

//electricity navigation
private void initalizeadminElectricity(){
        AdminElectricity adminElectricityObj = new AdminElectricity();
        adminElectricityObj.setAdminElectricityPrimaryStage(adminHomePagePrimaryStage);
        adminElectricityPage2Scene=new Scene(adminElectricityObj.createAdminElectricityScene(this::handleBackAdminHomePageButton),1600,800);
        adminElectricityObj.setAdminElectricity1Scene(adminElectricityPage2Scene);
}

    private void handleBackAdminHomePageButton(){

        adminHomePagePrimaryStage.setScene(adminHomePage1Scene);
    }

private void initalizeAdminViewIssue(){
        view_issues viewIssueObj = new view_issues();
        viewIssueObj.setAdminViewIssuePrimaryStage(adminHomePagePrimaryStage);
        adminviewissue1Scene= new Scene(viewIssueObj.createAdminViewIssueScene(this::handleBackAdminHomePageButton),1600,800);
        viewIssueObj.setAdminViewIssueScene(adminviewissue1Scene);

}

private void InitalizeEmergencyPage(){
        emergency_contacts emergencyPageObj= new emergency_contacts();
        emergencyPageObj.setEmergencyPrimaryStage(adminHomePagePrimaryStage);
        emergencyPage2Scene = new Scene(emergencyPageObj.createAdminEmergencyScene(this::handleBackAdminHomePageButton),1600,800);
        emergencyPageObj.setEmergency2Scene(emergencyPage2Scene);
}


private void initalizeSellFlatPage(){

        sellflat sellflatObj = new sellflat();
        sellflatObj.setSellPrimaryStage(adminHomePagePrimaryStage);
        sell2Scene = new Scene(sellflatObj.createSellFlatScene(this::handleBackAdminHomePageButton),1600,800);
        sellflatObj.setSell1Scene(sell2Scene);
}

private void initalizeAIPage(){

        AIPage aiPageObj = new AIPage();
        aiPageObj.setaIPrimaryStage(adminHomePagePrimaryStage);
        ai2Scene= new Scene(aiPageObj.createAIScene(this::handleBackAdminHomePageButton),1600,800);
        aiPageObj.setaI1Scene(ai2Scene);
}

private void initalizeAdminNotificationPage(){
        admin_notify adminNotificationObj = new admin_notify();
        adminNotificationObj.setAdminNotificationPrimaryStage(adminHomePagePrimaryStage);
        adminNotificaton2Scene = new Scene(adminNotificationObj.createAdminNotificationScene(this:: handleBackAdminHomePageButton),1600,800);
        adminNotificationObj.setAdminNotification1Scene(adminNotificaton2Scene);


}

private void initalizeAdminProfile(){

        Admin adminObj = new Admin();
        adminObj.setAdminProfilePrimaryStage(adminHomePagePrimaryStage);
        adminProfile2Scene= new Scene(adminObj.createAdminProfileScene(this::handleBackAdminHomePageButton),1600,800);
        adminObj.setAdminProfile1Scene(adminProfile2Scene);


}

}





