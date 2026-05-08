
package com.login.View;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

import com.login.Controller.AdminProfileController;
import com.login.Controller.SigninController;

import com.login.Model.AdminProfile;
import com.login.View.AuthenticationPages.SignupPage;
import com.login.services.FirebaseInitialize;
import com.login.Utils.UserSession;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Admin {

    Scene adminProfile1Scene, signupPage3Scene;
    
    Stage adminProfilePrimaryStage;


    Map<String,Object> myMap;
    String name;
    String email;
    AdminProfile ap=new AdminProfile();

    public Admin() {
        // Use UserSession to get logged-in admin's data instead of hardcoded UID
        UserSession session = UserSession.getInstance();
        
        if (session.isLoggedIn()) {
            // Use session data directly
            this.name = session.getFullName();
            this.email = session.getEmail();
            this.loggedInAdminUid = session.getUid();
            
            ap.setFullName(name);
            ap.setEmail(email);
            
            // Set label text after data is ready
            firstNameLabel.setText(name != null ? name : "Admin");
            emailLabel.setText(email != null ? email : "admin@civita.com");
            nameHeaderLabel.setText(name != null ? name : "Admin");
            
            System.out.println("Admin loaded from session: " + name + " (" + email + ")");
        } else {
            // Fallback to fetching from Firebase if session not available
            try {
                Map<String, Object> myMap = fetchAdminData();
                this.myMap = myMap;
                
                this.name = myMap.get("fullName") != null ? myMap.get("fullName").toString() : "Admin";
                this.email = myMap.get("email") != null ? myMap.get("email").toString() : "admin@civita.com";
                
                ap.setFullName(name);
                ap.setEmail(email);
                
                firstNameLabel.setText(ap.getFullName());
                emailLabel.setText(ap.getEmail());
                nameHeaderLabel.setText(ap.getFullName());
                
                System.out.println("Admin loaded from Firebase: " + name);
            } catch (Exception e) {
                System.err.println("Error loading admin data: " + e.getMessage());
                // Set default values
                this.name = "Admin";
                this.email = "admin@civita.com";
                firstNameLabel.setText(name);
                emailLabel.setText(email);
                nameHeaderLabel.setText(name);
            }
        }
    }
    

    public void setAdminProfile1Scene(Scene adminProfile1Scene) {
        this.adminProfile1Scene = adminProfile1Scene;
    }

    public void setAdminProfilePrimaryStage(Stage adminProfilePrimaryStage) {
        this.adminProfilePrimaryStage = adminProfilePrimaryStage;
    }


    // Labels for display
    Label firstNameLabel = new Label();
    Label dobLabel = new Label();
    Label emailLabel = new Label();
    Label phoneLabel = new Label();
    Label roleLabel = new Label("Admin");

    Label nameHeaderLabel = new Label(ap.getFullName());
    Label roleHeaderLabel = new Label("Admin");
    Label locationHeaderLabel = new Label("Pune, Maharashtra");

    Label regNoLabel = new Label("MH-SOC-2020-4523");
    Label flatsLabel = new Label("3 Flats");
    Label societyRoleLabel = new Label("Secretary");

    VBox mainContent = new VBox(30);
    private AdminProfileController adminProfileController = new AdminProfileController();
    // Dynamic UID from session - no longer hardcoded
    private String loggedInAdminUid = UserSession.getInstance().getUid();

    // Sidebar button style constants
    private static final String SIDEBAR_BTN_BASE_STYLE =
            "-fx-background-color: white;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 6 12 6 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 5, 0.4, 2, 2);" +
            "-fx-border-color: #dddddd;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-border-radius: 8;" +
            "-fx-alignment: CENTER;";
    private static final String SIDEBAR_BTN_HOVER_STYLE =
            "-fx-background-color: #f0f0f0;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 6 12 6 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 7, 0.5, 2, 2);" +
            "-fx-border-color: #cccccc;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-alignment: CENTER;";

    private Stage primaryStage;
    private ImageView headerProfileImage;

    // Helper methods (extracted from your original code to keep start() cleaner, but still "callable" within start())
    private void chooseAndSetImageForAdmin() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                if (headerProfileImage != null) {
                    headerProfileImage.setImage(image);
                    headerProfileImage.setFitWidth(110);
                    headerProfileImage.setPreserveRatio(true);
                    headerProfileImage.setClip(new Circle(55, 55, 55));
                } else {
                    System.err.println("headerProfileImage ImageView is null. Please initialize it.");
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }
    }

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(20));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle(
                "-fx-background-color: linear-gradient(to right, #dbf7f5, #ffffff);" +
                "-fx-border-color: #b0b0b0;" +
                "-fx-border-style: hidden;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 4);"
        );
        Label title = new Label("Admin Dashboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        title.setTextFill(Color.DARKSLATEBLUE);
        topBar.getChildren().add(title);
        return topBar;
    }

    public Map<String,Object> fetchAdminData() {
        Map<String,Object> myMap = new HashMap<>();
        
        // First try to get data from UserSession
        UserSession session = UserSession.getInstance();
        if (session.isLoggedIn() && session.isAdmin()) {
            myMap.put("fullName", session.getFullName());
            myMap.put("email", session.getEmail());
            System.out.println("Admin data from session: " + myMap);
            return myMap;
        }
        
        // Fallback to Firebase if session not available
        try {
            Firestore db = FirebaseInitialize.getDB();
            if (db == null) {
                System.err.println("Firebase not initialized");
                myMap.put("fullName", "Admin");
                myMap.put("email", "admin@civita.com");
                return myMap;
            }
            
            String uid = loggedInAdminUid != null ? loggedInAdminUid : session.getUid();
            if (uid == null || uid.isEmpty()) {
                System.err.println("No admin UID available");
                myMap.put("fullName", "Admin");
                myMap.put("email", "admin@civita.com");
                return myMap;
            }
            
            DocumentSnapshot document = db.collection("admins").document(uid).get().get();
            
            if (document.exists()) {
                Object fullName = document.get("fullName");
                Object email = document.get("email");
                myMap.put("fullName", fullName != null ? fullName : "Admin");
                myMap.put("email", email != null ? email : "admin@civita.com");
            } else {
                // Try users collection as fallback
                document = db.collection("users").document(uid).get().get();
                if (document.exists()) {
                    Object fullName = document.get("fullName");
                    Object email = document.get("email");
                    myMap.put("fullName", fullName != null ? fullName : "Admin");
                    myMap.put("email", email != null ? email : "admin@civita.com");
                } else {
                    myMap.put("fullName", "Admin");
                    myMap.put("email", "admin@civita.com");
                }
            }
            
            System.out.println("Admin data from Firebase: " + myMap);
            
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error fetching admin data: " + e.getMessage());
            e.printStackTrace();
            myMap.put("fullName", "Admin");
            myMap.put("email", "admin@civita.com");
        }
        
        return myMap;
    }

        myMap.put("fullName", document.get("fullName"));
        myMap.put("email",document.get("email"));

        System.out.println(myMap);

        return myMap;





    }

    private Button createSidebarButton(String text) {
        Button sidebarButton = new Button(text);
        sidebarButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 13));
        sidebarButton.setTextFill(Color.web("#2c3e50"));
        sidebarButton.setPrefWidth(160);
        sidebarButton.setStyle(SIDEBAR_BTN_BASE_STYLE);

        sidebarButton.setOnMouseEntered(e -> sidebarButton.setStyle(SIDEBAR_BTN_HOVER_STYLE));
        sidebarButton.setOnMouseExited(e -> sidebarButton.setStyle(SIDEBAR_BTN_BASE_STYLE));

        return sidebarButton;
    }

    private HBox createProfileHeader() {
        DropShadow cardShadow = new DropShadow(8, Color.rgb(0, 0, 0, 0.2));

        HBox profileHeader = new HBox(25);
        profileHeader.setAlignment(Pos.CENTER_LEFT);
        profileHeader.setPadding(new Insets(25));
        profileHeader.setMaxWidth(Double.MAX_VALUE);
        profileHeader.setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#ffffff")),
                        new Stop(1, Color.web("#bed1f5ff"))),
                new CornerRadii(20), Insets.EMPTY)));
        profileHeader.setEffect(cardShadow);

        Circle bgCircle = new Circle(55);

        headerProfileImage = new ImageView(new Image(
                "https://cdn-icons-png.flaticon.com/512/847/847969.png", 110, 110, true, true));
        headerProfileImage.setClip(new Circle(55, 55, 55));

        StackPane circleStack = new StackPane(bgCircle, headerProfileImage);
        circleStack.setPrefSize(110, 110);
        StackPane.setAlignment(circleStack, Pos.CENTER_LEFT);

        VBox nameDetails = new VBox(5);
        nameHeaderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        roleHeaderLabel.setTextFill(Color.DARKGRAY);
        locationHeaderLabel.setTextFill(Color.DARKGRAY);
        nameDetails.getChildren().addAll(nameHeaderLabel, roleHeaderLabel, locationHeaderLabel);

        Region spacerProfile = new Region();
        HBox.setHgrow(spacerProfile, Priority.ALWAYS);

        Button editProfileButton = new Button("Edit");
        editProfileButton.setStyle("-fx-background-color: #1abc9c; -fx-text-fill:black; -fx-font-weight: bold;");
        editProfileButton.setOnAction(e -> openHeaderEditWindow());

        profileHeader.getChildren().addAll(circleStack, nameDetails, spacerProfile, editProfileButton);

        return profileHeader;
    }

    private VBox createCard(String title, DropShadow shadow, Runnable editHandler, Stop stop1, Stop stop2) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setEffect(shadow);
        card.setMaxWidth(800);
        card.setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stop1, stop2),
                new CornerRadii(16), Insets.EMPTY)));

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setMaxWidth(Double.MAX_VALUE);

        Label heading = new Label(title);
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        heading.setTextFill(Color.DARKSLATEGRAY);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button editBtn = new Button("Edit");
        editBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: black; -fx-font-weight: bold;");
        editBtn.setOnAction(e -> editHandler.run());

        header.getChildren().addAll(heading, spacer, editBtn);
        card.getChildren().add(header);
        return card;
    }

    private GridPane createInfoGrid(boolean isPersonal) {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));
        Font labelFont = Font.font("Arial", FontWeight.SEMI_BOLD, 14);

        if (isPersonal) {
            grid.add(styledLabel("First Name:", labelFont), 0, 0);
            grid.add(firstNameLabel, 1, 0);
            grid.add(styledLabel("Date of Birth:", labelFont), 0, 1);
            grid.add(dobLabel, 1, 1);
            grid.add(styledLabel("Email Address:", labelFont), 0, 2);
            grid.add(emailLabel, 1, 2);
            grid.add(styledLabel("Phone Number:", labelFont), 0, 3);
            grid.add(phoneLabel, 1, 3);
            grid.add(styledLabel("User Role:", labelFont), 0, 4);
            grid.add(roleLabel, 1, 4);
        } else {
            grid.add(styledLabel("Society Reg. No.:", labelFont), 0, 0);
            grid.add(regNoLabel, 1, 0);
            grid.add(styledLabel("Flats Owned:", labelFont), 0, 1);
            grid.add(flatsLabel, 1, 1);
            grid.add(styledLabel("Society Role:", labelFont), 0, 2);
            grid.add(societyRoleLabel, 1, 2);
        }
        return grid;
    }

    private Label styledLabel(String text, Font font) {
        Label lbl = new Label(text);
        lbl.setFont(font);
        lbl.setTextFill(Color.DARKBLUE);
        return lbl;
    }

    private VBox createRule(String rule) {
        VBox box = new VBox(5);
        Label ruleLabel = new Label(rule);
        ruleLabel.setFont(Font.font("Arial", 16));
        ruleLabel.setTextFill(Color.BLACK);
        box.getChildren().addAll(ruleLabel);
        return box;
    }

    // Abstract base class for edit popups
    public abstract class EditPopupBase {
        protected GridPane layout = new GridPane();
        private Stage popupStage;
        private final VBox mainBox = new VBox(10);

        public EditPopupBase(String title) {
            popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setResizable(false);
            popupStage.setTitle(title);

            layout.setPadding(new Insets(10));
            layout.setHgap(10);
            layout.setVgap(10);
            layout.setAlignment(Pos.CENTER);

            mainBox.setPadding(new Insets(12));
            mainBox.setAlignment(Pos.CENTER);
            mainBox.setStyle(
                    "-fx-background-color: linear-gradient(to bottom right, #e4f0f0ff, #aaf0efff);" +
                            "-fx-background-radius: 15px;" +
                            "-fx-border-radius: 15px;" +
                            "-fx-padding: 20px;"
            );
            mainBox.setSpacing(12);

            Button saveButton = new Button("💾 Save");
            saveButton.setStyle(
                    "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10px;"
            );
            saveButton.setPrefWidth(100);
            saveButton.setOnAction(e -> {
                onSave();
                popupStage.close();
            });

            mainBox.getChildren().addAll(layout, saveButton);
            Scene scene = new Scene(mainBox, 450, Region.USE_COMPUTED_SIZE);
            popupStage.setScene(scene);
        }

        protected abstract void buildForm();
        protected abstract void onSave();

        public void show() {
            buildForm();
            popupStage.showAndWait();
        }

        protected void addRow(int row, String labelText, Control field) {
            Label label = new Label(labelText);
            label.setFont(Font.font("Arial", 14));
            layout.add(label, 0, row);
            layout.add(field, 1, row);
        }

        protected void addTopNode(Node node) {
            mainBox.getChildren().add(0, node);
        }
    }


    // Navigation Methods (these are now defined as methods within the Admin class)
    private void showProfilePage() {
        mainContent.getChildren().clear();

        DropShadow cardShadow = new DropShadow(8, Color.rgb(0, 0, 0, 0.2));

        HBox profileHeader = createProfileHeader();

        VBox personalInfoBox = createCard("Personal Information", cardShadow, this::openPersonalEditWindow,
                new Stop(0, Color.web("#fcddf3ff")), new Stop(1, Color.web("#bbcdedff")));
        personalInfoBox.getChildren().add(createInfoGrid(true));

        VBox societyInfoBox = createCard("Society Information", cardShadow, this::openSocietyEditWindow,
                new Stop(0, Color.web("#f9f1efff")), new Stop(1, Color.web("#97a8e7ff")));
        societyInfoBox.getChildren().add(createInfoGrid(false));

        mainContent.getChildren().addAll(profileHeader, personalInfoBox, societyInfoBox);
    }

    private void openPersonalEditWindow() {
        new EditPopupBase("Edit Personal Info") {
            TextField nameField = new TextField(firstNameLabel.getText());
            TextField dobField = new TextField(dobLabel.getText());
            TextField emailField = new TextField(emailLabel.getText());
            TextField phoneField = new TextField(phoneLabel.getText());
            TextField roleField = new TextField(roleLabel.getText());

            @Override
            protected void buildForm() {
                addRow(0, "First Name:", nameField);
                addRow(1, "Date of Birth:", dobField);
                addRow(2, "Email Address:", emailField);
                addRow(3, "Phone Number:", phoneField);
                addRow(4, "User Role:", roleField);
            }

            @Override
            protected void onSave() {
                String uid = loggedInAdminUid;
                adminProfileController.updateAdminProfile(uid, nameField.getText(), dobField.getText(), emailField.getText(),
                        phoneField.getText(), roleField.getText(), null, null, null);

                firstNameLabel.setText(nameField.getText());
                dobLabel.setText(dobField.getText());
                emailLabel.setText(emailField.getText());
                phoneLabel.setText(phoneField.getText());
                roleLabel.setText(roleField.getText());
            }
        }.show();
    }

    private void openSocietyEditWindow() {
        new EditPopupBase("Edit Society Info") {
            TextField regNoField = new TextField(regNoLabel.getText());
            TextField flatsField = new TextField(flatsLabel.getText());
            TextField roleField = new TextField(societyRoleLabel.getText());

            @Override
            protected void buildForm() {
                addRow(0, "Registration Number:", regNoField);
                addRow(1, "Flats Owned:", flatsField);
                addRow(2, "Society Role:", roleField);
            }

            @Override
            protected void onSave() {
                String uid = loggedInAdminUid;
                adminProfileController.updateAdminProfile(uid, null, null, null, null, null,
                        regNoField.getText(), flatsField.getText(), roleField.getText());

                regNoLabel.setText(regNoField.getText());
                flatsLabel.setText(flatsField.getText());
                societyRoleLabel.setText(roleField.getText());
            }
        }.show();
    }

    private void openHeaderEditWindow() {
        new EditPopupBase("Edit Profile Header") {
            TextField nameField = new TextField(nameHeaderLabel.getText());
            TextField roleField = new TextField(roleHeaderLabel.getText());
            TextField locationField = new TextField(locationHeaderLabel.getText());

            @Override
            protected void buildForm() {
                Button imageBtn = new Button("🖼️ Edit Image");
                imageBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px;");
                imageBtn.setPrefWidth(140);
                HBox imageBox = new HBox(imageBtn);
                imageBox.setAlignment(Pos.CENTER);
                imageBox.setPadding(new Insets(0, 0, 10, 0));
                addTopNode(imageBox);

                imageBtn.setOnAction(event -> chooseAndSetImageForAdmin());

                addRow(0, "Name:", nameField);
                addRow(1, "Role:", roleField);
                addRow(2, "Location:", locationField);
            }

            @Override
            protected void onSave() {
                nameHeaderLabel.setText(nameField.getText());
                roleHeaderLabel.setText(roleField.getText());
                locationHeaderLabel.setText(locationField.getText());
            }
        }.show();
    }

    private void showSocietyRules() {
        mainContent.getChildren().clear();

        VBox rulesCard = new VBox(15);
        rulesCard.setPadding(new Insets(30));
        rulesCard.setMaxWidth(900);
        rulesCard.setStyle(
                "-fx-background-radius: 18; " +
                        "-fx-background-color: linear-gradient(to bottom right, #ffffff, #c1d5f8);" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 15, 0, 0, 10);"
        );

        Label title = new Label("🏢 Living Guide");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.DARKSLATEBLUE);

        rulesCard.getChildren().addAll(title,
                createRule("1. No Loud Noise after 10 PM"),
                createRule("2. No Unauthorized Construction"),
                createRule("3. Respect Parking Allotments"),
                createRule("4. Facility Booking in Advance & Use Facilities Responsibly"),
                createRule("5. Gym Schedule & Booking:"),
                createRule("   - Morning Slots: 6:00 AM - 10:00 AM   Evening Slots: 4:00 PM - 9:00 PM\""),
                createRule("   - For booking, please contact: [Rocky Gymwala: 945636541]"),
                createRule("6. Event Hall Booking:"),
                createRule("   - To book the event hall for gatherings, please contact: [Vicky:856935412]"),
                createRule("7. Pet Control Required"),
                createRule("8. Pay Maintenance on Time"),
                createRule("9. No Smoking in Public Areas"),
                createRule("10. Swimming Pool Rules & Timings:"),
                createRule("   - Timings: 7:00 AM - 11:00 AM and 4:00 PM - 8:00 PM")
        );

        mainContent.getChildren().add(rulesCard);
    }

    private void showAboutUs() {
        mainContent.getChildren().clear();

        VBox aboutCard = new VBox(25);
        aboutCard.setPadding(new Insets(35));
        aboutCard.setMaxWidth(900);
        aboutCard.setStyle(
                "-fx-background-radius: 18; " +
                        "-fx-background-color: linear-gradient(to bottom right, #ffffff, #c1d5f8);" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 15, 0, 0, 10);"
        );

        Label heading = new Label("📘 About Us");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        heading.setTextFill(Color.DARKSLATEBLUE);

        TextFlow contentFlow = new TextFlow();
        contentFlow.setPrefWidth(840);

        Font headingFont = Font.font("Arial", FontWeight.BOLD, 16);
        Font regularFont = Font.font("Arial", 16);
        Color headingColor = Color.DARKSLATEBLUE;
        Color regularColor = Color.BLACK;

        Text intro1 = new Text("CIVITA is an innovative and intelligent Society Management System crafted to simplify and digitalize the everyday operations of residential communities. Whether you're an administrator overseeing complex tasks or a resident wanting quick access to important information, CIVITA ensures a seamless and modern experience.\n\n");
        intro1.setFont(regularFont);
        intro1.setFill(regularColor);

        Text intro2 = new Text("Our platform centralizes all key aspects of society living — from managing notices, complaints, and meetings to maintaining detailed user profiles and enforcing society rules. Designed with a clean and intuitive interface, CIVITA empowers societies to shift from traditional paperwork to a structured and efficient digital environment.\n\n");
        intro2.setFont(regularFont);
        intro2.setFill(regularColor);

        Text keyFeaturesHeader = new Text("Key Features:\n");
        keyFeaturesHeader.setFont(headingFont);
        keyFeaturesHeader.setFill(headingColor);

        Text keyFeatures = new Text("• Seamless Property Transactions\n• Direct Line to Society Management\n• Real-Time Society Updates\n• Feedback & Suggestion Box\n• Hassle-Free Bill Payments\n• Safety at Your Command\n\n");
        keyFeatures.setFont(regularFont);
        keyFeatures.setFill(regularColor);

        Text visionHeader = new Text("Our Vision:\n");
        visionHeader.setFont(headingFont);
        visionHeader.setFill(headingColor);

        Text vision = new Text("To revolutionize how housing societies manage and communicate by offering an all-in-one smart solution that ensures transparency, efficiency, and convenience for all members.\n\n");
        vision.setFont(regularFont);
        vision.setFill(regularColor);

        Text missionHeader = new Text("Mission:\n");
        missionHeader.setFont(headingFont);
        missionHeader.setFill(headingColor);

        Text mission = new Text("We aim to empower every society with digital tools that save time, reduce confusion, and bring communities closer together.\n\n");
        mission.setFont(regularFont);
        mission.setFill(regularColor);

        Text developedByHeader = new Text("Developed by: ");
        developedByHeader.setFont(headingFont);
        developedByHeader.setFill(headingColor);

        Text developedBy = new Text("TEAM CIVITA [Atharv, Ashutosh, Aryan, Riya]\n\n");
        developedBy.setFont(regularFont);
        developedBy.setFill(regularColor);

        Text guidanceHeader = new Text("Developed with guidance and support from :\n");
        guidanceHeader.setFont(headingFont);
        guidanceHeader.setFill(headingColor);

        Text facilitator = new Text();
        facilitator.setFont(regularFont);
        facilitator.setFill(regularColor);
        Text facilitatorShashi = new Text("           Shashi Bagal Sir\n");
        facilitatorShashi.setFont(regularFont);
        facilitatorShashi.setFill(regularColor);

        Text facilitatorSachin = new Text("           Sachin Sir , Pramod Sir, Akshay Sir\n");
        facilitatorSachin.setFont(regularFont);
        facilitatorSachin.setFill(regularColor);

        Text facilitatorPramod = new Text("           Shiv Sir & Subodh Sir\n\n");
        facilitatorPramod.setFont(regularFont);
        facilitatorPramod.setFill(regularColor);
        Text classHeader = new Text("Class: ");
        classHeader.setFont(headingFont);
        classHeader.setFill(headingColor);

        Text className = new Text("CORE2WEB\n");
        className.setFont(regularFont);
        className.setFill(regularColor);

        Text mentorHeader = new Text("Mentor: ");
        mentorHeader.setFont(headingFont);
        mentorHeader.setFill(headingColor);

        Text mentorName = new Text("[Sumit Katkar]\n\n");
        mentorName.setFont(regularFont);
        mentorName.setFill(regularColor);

        Text futureEnhancementsHeader = new Text("Future Enhancements:\n");
        futureEnhancementsHeader.setFont(headingFont);
        futureEnhancementsHeader.setFill(headingColor);

        Text futureEnhancements = new Text("• AI for interior designing\n• Visitor Management");
        futureEnhancements.setFont(regularFont);
        futureEnhancements.setFill(regularColor);

        contentFlow.getChildren().addAll(intro1, intro2, keyFeaturesHeader, keyFeatures, visionHeader, vision, missionHeader, mission, developedByHeader, developedBy, guidanceHeader, facilitatorShashi, facilitatorSachin, facilitatorPramod, facilitator, classHeader, className, mentorHeader, mentorName, futureEnhancementsHeader, futureEnhancements);

        aboutCard.getChildren().addAll(heading, contentFlow);
        ScrollPane scrollPane = new ScrollPane(aboutCard);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        scrollPane.setPadding(new Insets(15));
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        mainContent.getChildren().add(scrollPane);
    }

    private void showFaq() {
        mainContent.getChildren().clear();

        VBox faqCard = new VBox(15);
        faqCard.setPadding(new Insets(28));
        faqCard.setMaxWidth(900);
        faqCard.setStyle(
                "-fx-background-radius: 18; " +
                        "-fx-background-color: linear-gradient(to bottom right, #ffffff, #c1d5f8);" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 15, 0, 0, 10);"
        );

        Label title = new Label("❓ FAQs/Help");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.DARKSLATEBLUE);

        faqCard.getChildren().addAll(title,
                createRule("Q: How do I report a maintenance issue in my flat or common areas?"),
                createRule("A: [ Go to the 'Raise Issue' section, provide details, and submit].\n\n"),
                createRule("Q: What should I do if I have a complaint about another resident or a society rule violation?"),
                createRule("A: [ Contact the society administrator through the app or via email/phone listed in the 'About Us' section/Complaints].\n\n"),

                createRule("Q: How are the society's maintenance charges calculated and where can I view my bills?"),
                createRule("A:   Maintenance charges are based on flat size and common expenses. You can view your bills in respective sections].\n\n"),
                createRule("Q: What are the procedures for visitor entry and parking?"),
                createRule("A:   Visitors must register at the gate. Parking for visitors is in designated areas only.\n\n"),
                createRule("Q: What should I do if I am facing a technical issue or have a suggestion for the application?"),
                createRule("A: Please contact the administrator via the 'Contact Us' information in the 'About Us' section, detailing the issue or suggestion.\n\n"),
                createRule("Q: Where can I find important society documents like meeting minutes or circulars?"),
                createRule("A:   Check the 'Documents' or 'Notices' section].")
        );
        mainContent.getChildren().add(faqCard);
    }

    private void showPrivacyPolicy() {
        mainContent.getChildren().clear();

        VBox ppCard = new VBox(15);
        ppCard.setPadding(new Insets(30));
        ppCard.setMaxWidth(900);
        ppCard.setStyle(
                "-fx-background-radius: 18; " +
                        "-fx-background-color: linear-gradient(to bottom right, #ffffff, #c1d5f8);" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 15, 0, 0, 10);"
        );

        Label title = new Label("🔒 Privacy Policy");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.DARKSLATEBLUE);

        ppCard.getChildren().addAll(title,
                createRule("We collect your basic info: Name, contact details, address, flat number, and how you use the app."),
                createRule("We use your info to run the app and help the community: This includes managing accounts, communication, and payments.."),
                createRule("For more details, please consult the full policy document."),
                createRule("You have rights to see and manage your info. Contact the admin for help."),
                createRule("We'll update this policy if things change"),
                createRule("We take steps to keep your data safe, but remember no online system is perfectly secure.")
        );
        mainContent.getChildren().add(ppCard);
    }

public BorderPane createAdminProfileScene(Runnable adminProfileReturnBackToTheHomepage){
         BorderPane root = new BorderPane();
        try {
           
            root.setStyle("-fx-background-color:LAVENDER;");

            // Top Bar
            HBox topBar = createTopBar();
            root.setTop(topBar);

            HBox body = new HBox();

            // Sidebar
            VBox sidebar = new VBox(20);
            sidebar.setPadding(new Insets(30, 20, 30, 20));
            sidebar.setAlignment(Pos.TOP_CENTER);
            sidebar.setPrefWidth(250);
            sidebar.setStyle("-fx-background-color: linear-gradient(to bottom right, #dccef3ff, #99ecf0ff);-fx-background-radius: 0 20 20 0;");

            Label menuTitle = new Label("Menu");
            menuTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
            menuTitle.setTextFill(Color.BLACK);

            Button profileBtn = createSidebarButton("👤 My Profile");
            Button rulesBtn = createSidebarButton("📜Community Living");
            Button faqBtn = createSidebarButton("✅ FAQs/Help");
            Button ppBtn = createSidebarButton(" 🔒 Privacy Policy");
            Button aboutUsBtn = createSidebarButton("📘 About Us");
            Button logoutBtn = createSidebarButton("📤 Logout");

            Button profileBackButton = new Button("Back");
            profileBackButton.setStyle(
                    "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
            profileBackButton.setPadding(new Insets(5, 15, 5, 15));

            profileBackButton.setOnAction(e -> {

                adminProfileReturnBackToTheHomepage.run();
                System.out.println("Back button clicked!");
                // Add your navigation code here, e.g., to go back to a previous scene
            });
            VBox profileBackButtonAlignmentBox = new VBox(profileBackButton);
            profileBackButtonAlignmentBox.setAlignment(Pos.BOTTOM_CENTER);

            VBox heightVBox = new VBox();
            heightVBox.setPrefHeight(250);

            sidebar.getChildren().addAll(menuTitle, profileBtn, rulesBtn, faqBtn, ppBtn, aboutUsBtn, logoutBtn, heightVBox, profileBackButton);

            // Set button actions
            profileBtn.setOnAction(e -> showProfilePage());
            rulesBtn.setOnAction(e -> showSocietyRules());
            aboutUsBtn.setOnAction(e -> showAboutUs());
            faqBtn.setOnAction(e -> showFaq());
            ppBtn.setOnAction(e -> showPrivacyPolicy());

            logoutBtn.setOnAction(e -> {
                // Clear the user session on logout
                SigninController.logoutUser();
                System.out.println("Admin logged out");
                
                initalizeSignupPage();
                adminProfilePrimaryStage.setScene(signupPage3Scene);
            });


            // Main content initial view: profile page
            mainContent.setPadding(new Insets(40));
            mainContent.setPrefWidth(1000);
            mainContent.setAlignment(Pos.TOP_CENTER);
            showProfilePage();
            fetchAdminData();

            body.getChildren().addAll(sidebar, mainContent);
            root.setCenter(body);

         
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    private void initalizeSignupPage(){

        SignupPage signupPage2Obj= new SignupPage();
        signupPage2Obj.setSignupPagePrimaryStage(adminProfilePrimaryStage);
        signupPage3Scene = new Scene(signupPage2Obj.createSignupPageScene(),1600,800);
        signupPage2Obj.setSignupPage1Scene(signupPage3Scene);



    }

}
