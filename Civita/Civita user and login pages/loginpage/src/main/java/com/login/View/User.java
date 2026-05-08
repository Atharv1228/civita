package com.login.View;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.login.Controller.UserController;
import com.login.Controller.SigninController;

import com.login.Model.UserProfile;
import com.login.View.AuthenticationPages.SignupPage;
import com.login.services.FirebaseInitialize;
import com.login.Utils.UserSession;

import javafx.application.Platform;
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

public class User {

    Scene userProfile1Scene,signup3Scene;
    Stage userProfilePrimaryStage;

    public void setUserProfile1Scene(Scene userProfile1Scene) {
        this.userProfile1Scene = userProfile1Scene;
    }

    public void setUserProfilePrimaryStage(Stage userProfilePrimaryStage) {
        this.userProfilePrimaryStage = userProfilePrimaryStage;
    }

    // Firebase user ID - now dynamically fetched from session
    private String USER_UID = UserSession.getInstance().getUid();

    Map<String, Object> myMap;
    String name;
    String email;
    String flatNo;
    UserProfile ap = new UserProfile();

    // User Personal Labels - will be updated with Firebase data
    Label NameLabel = new Label("Loading...");
    Label dobLabel = new Label("12 July 1988");
    Label emailLabel = new Label("Loading...");
    Label phoneLabel = new Label("+91 98765 43210");
    Label roleLabel = new Label("Resident");

    // User Header Labels
    Label nameHeaderLabel = new Label("Loading...");
    Label roleHeaderLabel = new Label("Resident");
    Label locationHeaderLabel = new Label("Pune, Maharashtra");

    // Society Info Labels
    Label flatNumberLabel = new Label("Loading...");
    Label familyTypeLabel = new Label("Family");
    Label numMembersLabel = new Label("4");
    Label parkingSlotLabel = new Label("P-17");

    VBox mainContent = new VBox(30);
    private UserController userController;
    private Stage primaryStage;
    private ImageView headerProfileImage;

    // Sidebar button style constants (Admin UI style)
    private static final String SIDEBAR_BTN_BASE_STYLE =
            "-fx-background-color: white;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 6 12 6 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 5, 0.4, 2, 2);" +
            "-fx-border-color: #dddddd;" +
            "-fx-border-width: 1;" +
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

    public User() {
        // Use UserSession to get logged-in user's data instead of hardcoded UID
        UserSession session = UserSession.getInstance();
        
        if (session.isLoggedIn()) {
            // Use session data directly
            this.name = session.getFullName();
            this.email = session.getEmail();
            this.flatNo = session.getFlatNo();
            this.USER_UID = session.getUid();
            
            ap.setFullName(name);
            ap.setEmail(email);
            
            // Set label text after data is ready
            NameLabel.setText(name != null ? name : "User");
            emailLabel.setText(email != null ? email : "user@civita.com");
            nameHeaderLabel.setText(name != null ? name : "User");
            flatNumberLabel.setText(flatNo != null ? flatNo : "Not specified");
            
            System.out.println("User loaded from session: " + name + " (" + email + ")");
        } else {
            // Initialize with loading state and fetch asynchronously
            this.name = "Loading...";
            this.email = "Loading...";
            this.flatNo = "Loading...";
            
            ap.setFullName(name);
            ap.setEmail(email);
            
            // Fetch data from Firebase asynchronously
            fetchUserDataAsync();
        }
    }

    /**
     * Asynchronously fetch user data from Firebase and update UI
     */
    private void fetchUserDataAsync() {
        // Run Firebase fetch in background thread
        CompletableFuture.supplyAsync(() -> {
            return fetchuserData();
        }).thenAccept(fetchedData -> {
            // Update UI on JavaFX Application Thread
            Platform.runLater(() -> {
                updateUIWithFetchedData(fetchedData);
            });
        }).exceptionally(throwable -> {
            // Handle errors
            Platform.runLater(() -> {
                System.err.println("Error fetching user data: " + throwable.getMessage());
                throwable.printStackTrace();
                setDefaultValues();
            });
            return null;
        });
    }

    /**
     * Update UI components with fetched data
     */
    private void updateUIWithFetchedData(Map<String, Object> fetchedData) {
        if (fetchedData != null && 
            fetchedData.get("fullName") != null && 
            fetchedData.get("email") != null) {
            
            // Update instance variables
            this.name = fetchedData.get("fullName").toString();
            this.email = fetchedData.get("email").toString();
            
            // Update flatNo if available
            if (fetchedData.get("flatNo") != null) {
                this.flatNo = fetchedData.get("flatNo").toString();
            } else {
                this.flatNo = "Not specified";
            }
            
            // Update UserProfile object
            ap.setFullName(name);
            ap.setEmail(email);
            
            // Update UI labels - ensuring nameHeaderLabel gets the same data as NameLabel
            NameLabel.setText(name);
            emailLabel.setText(email);
            nameHeaderLabel.setText(name); // Same data as NameLabel
            flatNumberLabel.setText(flatNo);
            
            System.out.println(" Successfully loaded user data:");
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Flat No: " + flatNo);
            
        } else {
            System.out.println("⚠️ Warning: Could not fetch user data, using default values");
            setDefaultValues();
        }
    }

    /**
     * Set default values when data fetch fails
     */
    private void setDefaultValues() {
        this.name = "User";
        this.email = "user@example.com";
        this.flatNo = "Not specified";
        
        ap.setFullName(name);
        ap.setEmail(email);
        
        NameLabel.setText(name);
        emailLabel.setText(email);
        nameHeaderLabel.setText(name); // Same data as NameLabel
        flatNumberLabel.setText(flatNo);
    }

    /**
     * Fetch user data from Firebase Firestore
     */
    public Map<String, Object> fetchuserData() {
        Map<String, Object> myMap = new HashMap<>();
        
        // First try to get data from UserSession
        UserSession session = UserSession.getInstance();
        if (session.isLoggedIn()) {
            myMap.put("fullName", session.getFullName());
            myMap.put("email", session.getEmail());
            myMap.put("flatNo", session.getFlatNo());
            System.out.println("User data from session: " + myMap);
            return myMap;
        }
        
        // Fallback to Firebase if session not available
        try {
            System.out.println("Fetching user data from Firebase...");
            
            Firestore db = FirebaseInitialize.getDB();
            if (db == null) {
                System.err.println("Firebase database is not initialized");
                myMap.put("fullName", "User");
                myMap.put("email", "user@civita.com");
                myMap.put("flatNo", "Unknown");
                return myMap;
            }
            
            String uid = USER_UID != null ? USER_UID : session.getUid();
            if (uid == null || uid.isEmpty()) {
                System.err.println("No user UID available");
                myMap.put("fullName", "User");
                myMap.put("email", "user@civita.com");
                myMap.put("flatNo", "Unknown");
                return myMap;
            }
            
            // Fetch from users collection with the specific UID
            DocumentSnapshot document = db.collection("users")
                                        .document(uid)
                                        .get()
                                        .get();
            
            if (document.exists()) {
                System.out.println("Document found in 'users' collection");
                
                Object fullNameObj = document.get("fullName");
                Object emailObj = document.get("email");
                Object flatNoObj = document.get("flatNo");
                
                myMap.put("fullName", fullNameObj != null ? fullNameObj : "User");
                myMap.put("email", emailObj != null ? emailObj : "user@civita.com");
                myMap.put("flatNo", flatNoObj != null ? flatNoObj : "Unknown");
                
            } else {
                // Try admins collection as fallback
                DocumentSnapshot adminDocument = db.collection("admins")
                                                  .document(uid)
                                                  .get()
                                                  .get();
                
                if (adminDocument.exists()) {
                    System.out.println("Document found in 'admins' collection");
                    
                    Object fullNameObj = adminDocument.get("fullName");
                    Object emailObj = adminDocument.get("email");
                    Object flatNoObj = adminDocument.get("flatNo");
                    
                    myMap.put("fullName", fullNameObj != null ? fullNameObj : "User");
                    myMap.put("email", emailObj != null ? emailObj : "user@civita.com");
                    myMap.put("flatNo", flatNoObj != null ? flatNoObj : "Unknown");
                } else {
                    System.out.println("Document not found in database");
                    myMap.put("fullName", "User");
                    myMap.put("email", "user@civita.com");
                    myMap.put("flatNo", "Unknown");
                }
            }
            
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error fetching user data: " + e.getMessage());
            e.printStackTrace();
            myMap.put("fullName", "User");
            myMap.put("email", "user@civita.com");
            myMap.put("flatNo", "Unknown");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            myMap.put("fullName", "User");
            myMap.put("email", "user@civita.com");
            myMap.put("flatNo", "Unknown");
        }
        
        System.out.println("Final fetched data: " + myMap);
        return myMap;
    }

    // Helper method for choosing and setting image (Admin UI style)
    private void chooseAndSetImageForUser() {
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
        
        Label title = new Label("User Dashboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        title.setTextFill(Color.DARKSLATEBLUE);
        topBar.getChildren().add(title);
        return topBar;
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
            grid.add(styledLabel("Full Name:", labelFont), 0, 0);
            grid.add(NameLabel, 1, 0);
            grid.add(styledLabel("Date of Birth:", labelFont), 0, 1);
            grid.add(dobLabel, 1, 1);
            grid.add(styledLabel("Email Address:", labelFont), 0, 2);
            grid.add(emailLabel, 1, 2);
            grid.add(styledLabel("Phone Number:", labelFont), 0, 3);
            grid.add(phoneLabel, 1, 3);
            grid.add(styledLabel("User Role:", labelFont), 0, 4);
            grid.add(roleLabel, 1, 4);
        } else {
            grid.add(styledLabel("Flat Number:", labelFont), 0, 0);
            grid.add(flatNumberLabel, 1, 0);
            grid.add(styledLabel("Family Type:", labelFont), 0, 1);
            grid.add(familyTypeLabel, 1, 1);
            grid.add(styledLabel("Family Members:", labelFont), 0, 2);
            grid.add(numMembersLabel, 1, 2);
            grid.add(styledLabel("Parking Slot:", labelFont), 0, 3);
            grid.add(parkingSlotLabel, 1, 3);
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

    // Abstract base class for edit popups (Admin UI style)
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

    // Navigation Methods
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
            TextField nameField = new TextField(NameLabel.getText());
            TextField dobField = new TextField(dobLabel.getText());
            TextField emailField = new TextField(emailLabel.getText());
            TextField phoneField = new TextField(phoneLabel.getText());
            TextField roleField = new TextField(roleLabel.getText());

            @Override
            protected void buildForm() {
                addRow(0, "Full Name:", nameField);
                addRow(1, "Date of Birth:", dobField);
                addRow(2, "Email Address:", emailField);
                addRow(3, "Phone Number:", phoneField);
                addRow(4, "User Role:", roleField);
            }

            @Override
            protected void onSave() {
                NameLabel.setText(nameField.getText());
                dobLabel.setText(dobField.getText());
                emailLabel.setText(emailField.getText());
                phoneLabel.setText(phoneField.getText());
                roleLabel.setText(roleField.getText());

                // Update header as well - ensuring nameHeaderLabel gets the same data as NameLabel
                nameHeaderLabel.setText(nameField.getText());

                // Update database if needed
                if (userController != null) {
                    userController.updateUserProfile(USER_UID, nameField.getText(), dobField.getText(),
                            emailField.getText(), phoneField.getText(), roleField.getText(), null, null, null);
                }
            }
        }.show();
    }

    private void openSocietyEditWindow() {
        new EditPopupBase("Edit Society Info") {
            TextField flatNumberField = new TextField(flatNumberLabel.getText());
            TextField familyTypeField = new TextField(familyTypeLabel.getText());
            TextField numMembersField = new TextField(numMembersLabel.getText());
            TextField parkingSlotField = new TextField(parkingSlotLabel.getText());

            @Override
            protected void buildForm() {
                addRow(0, "Flat Number:", flatNumberField);
                addRow(1, "Family Type:", familyTypeField);
                addRow(2, "Number of Members:", numMembersField);
                addRow(3, "Parking Slot:", parkingSlotField);
            }

            @Override
            protected void onSave() {
                flatNumberLabel.setText(flatNumberField.getText());
                familyTypeLabel.setText(familyTypeField.getText());
                numMembersLabel.setText(numMembersField.getText());
                parkingSlotLabel.setText(parkingSlotField.getText());

                // Update database if needed
                if (userController != null) {
                    userController.updateUserProfile(USER_UID, null, null, null, null, null,
                            familyTypeField.getText(), numMembersField.getText(), parkingSlotField.getText());
                }
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

                imageBtn.setOnAction(event -> chooseAndSetImageForUser());

                addRow(0, "Name:", nameField);
                addRow(1, "Role:", roleField);
                addRow(2, "Location:", locationField);
            }

            @Override
            protected void onSave() {
                nameHeaderLabel.setText(nameField.getText());
                roleHeaderLabel.setText(roleField.getText());
                locationHeaderLabel.setText(locationField.getText());

                // Also update the main name label - ensuring NameLabel gets the same data as nameHeaderLabel
                NameLabel.setText(nameField.getText());
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

        Label title = new Label("🏢 Community Living Guidelines");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.DARKSLATEBLUE);

        rulesCard.getChildren().addAll(title,
                createRule("🔇 Maintain quiet hours after 10:00 PM"),
                createRule("🏗️ Obtain approval before any construction work"),
                createRule("🚗 Respect designated parking spaces"),
                createRule("🏋️ Book facilities in advance and use responsibly"),
                createRule("⏰ Gym Hours: 6:00 AM - 10:00 AM, 4:00 PM - 9:00 PM"),
                createRule("📞 Gym Booking: Contact Rocky (945636541)"),
                createRule("🎉 Event Hall: Contact Vicky (856935412) for bookings"),
                createRule("🐕 Keep pets under control at all times"),
                createRule("💰 Pay maintenance fees on time"),
                createRule("🚭 No smoking in common areas"),
                createRule("🏊 Pool Hours: 7:00 AM - 11:00 AM, 4:00 PM - 8:00 PM")
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

        Label heading = new Label("📘 About CIVITA");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        heading.setTextFill(Color.DARKSLATEBLUE);

        TextFlow contentFlow = new TextFlow();
        contentFlow.setPrefWidth(840);

        Font headingFont = Font.font("Arial", FontWeight.BOLD, 16);
        Font regularFont = Font.font("Arial", 16);
        Color headingColor = Color.DARKSLATEBLUE;
        Color regularColor = Color.BLACK;

        Text intro1 = new Text("CIVITA is an innovative Society Management System designed to digitalize residential community operations.\n\n");
        intro1.setFont(regularFont);
        intro1.setFill(regularColor);

        Text intro2 = new Text("Our platform centralizes all aspects of society living — from managing notices and complaints to maintaining user profiles and enforcing community rules.\n\n");
        intro2.setFont(regularFont);
        intro2.setFill(regularColor);

        Text keyFeaturesHeader = new Text("Key Features:\n");
        keyFeaturesHeader.setFont(headingFont);
        keyFeaturesHeader.setFill(headingColor);

        Text keyFeatures = new Text("• Seamless Property Transactions\n• Direct Communication with Management\n• Real-Time Updates\n• Feedback System\n• Digital Bill Payments\n• Emergency Response\n\n");
        keyFeatures.setFont(regularFont);
        keyFeatures.setFill(regularColor);

        Text visionHeader = new Text("Vision:\n");
        visionHeader.setFont(headingFont);
        visionHeader.setFill(headingColor);

        Text vision = new Text("To revolutionize housing society management with smart, transparent, and efficient digital solutions.\n\n");
        vision.setFont(regularFont);
        vision.setFill(regularColor);

        Text missionHeader = new Text("Mission:\n");
        missionHeader.setFont(headingFont);
        missionHeader.setFill(headingColor);

        Text mission = new Text("Empowering communities with digital tools that save time and bring residents closer together.\n\n");
        mission.setFont(regularFont);
        mission.setFill(regularColor);

        Text developedByHeader = new Text("Developed by: ");
        developedByHeader.setFont(headingFont);
        developedByHeader.setFill(headingColor);

        Text developedBy = new Text("Team CIVITA [Atharv, Ashutosh, Aryan, Riya]\n\n");
        developedBy.setFont(regularFont);
        developedBy.setFill(regularColor);

        Text guidanceHeader = new Text("Guidance: ");
        guidanceHeader.setFont(headingFont);
        guidanceHeader.setFill(headingColor);

        Text guidance = new Text("Shashi Bagal Sir, Sachin Sir, Pramod Sir, Akshay Sir, Shiv Sir & Subodh Sir\n\n");
        guidance.setFont(regularFont);
        guidance.setFill(regularColor);

        Text classHeader = new Text("Class: ");
        classHeader.setFont(headingFont);
        classHeader.setFill(headingColor);

        Text className = new Text("CORE2WEB\nMentor: Sumit Katkar");
        className.setFont(regularFont);
        className.setFill(regularColor);

        contentFlow.getChildren().addAll(intro1, intro2, keyFeaturesHeader, keyFeatures, visionHeader, vision, missionHeader, mission, developedByHeader, developedBy, guidanceHeader, guidance, classHeader, className);

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

        Label title = new Label("❓ Frequently Asked Questions");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.DARKSLATEBLUE);

        faqCard.getChildren().addAll(title,
                createRule("Q: How do I report a maintenance issue?"),
                createRule("A: Go to the 'Raise Issue' section, provide details, and submit your request.\n"),
                createRule("Q: How do I contact the society administrator?"),
                createRule("A: Use the contact information provided in the 'About Us' section.\n"),
                createRule("Q: Where can I view my maintenance bills?"),
                createRule("A: Check the billing section in your dashboard for all payment details.\n"),
                createRule("Q: What are the visitor entry procedures?"),
                createRule("A: All visitors must register at the main gate. Parking is available in designated areas only.\n"),
                createRule("Q: How do I report technical issues?"),
                createRule("A: Contact the administrator through the app or use the feedback system.\n"),
                createRule("Q: Where can I find society documents?"),
                createRule("A: Important documents and notices are available in the 'Documents' section.")
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
                createRule("Data Collection: We collect basic information including name, contact details, address, and flat number."),
                createRule("Data Usage: Information is used to manage your account, facilitate communication, and process payments."),
                createRule("Data Security: We implement security measures to protect your personal information."),
                createRule("User Rights: You have the right to access, modify, and delete your personal information."),
                createRule("Policy Updates: This policy may be updated periodically. Users will be notified of significant changes."),
                createRule("Contact: For privacy concerns, please contact the administrator through the provided channels.")
        );

        mainContent.getChildren().add(ppCard);
    }

    public BorderPane createUserProfileScene(Runnable residentProfileReturnBackToHomePage) {
        this.primaryStage = userProfilePrimaryStage;
        this.userController = new UserController();

        BorderPane root = new BorderPane();
        try {
            root.setStyle("-fx-background-color:LAVENDER;");

            // Top Bar (Admin UI style)
            HBox topBar = createTopBar();
            root.setTop(topBar);

            HBox body = new HBox();

            // Sidebar (Admin UI style)
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
                residentProfileReturnBackToHomePage.run();
                System.out.println("Back button clicked!");
            });

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
                System.out.println("User logged out");
                
                initalizeSignupPage3();
                userProfilePrimaryStage.setScene(signup3Scene);
            });

            // Main content initial view: profile page
            mainContent.setPadding(new Insets(40));
            mainContent.setPrefWidth(1000);
            mainContent.setAlignment(Pos.TOP_CENTER);
            showProfilePage();

            body.getChildren().addAll(sidebar, mainContent);
            root.setCenter(body);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    public CompletableFuture<UserProfile> fetchUserProfile(String uid) {
        CompletableFuture<UserProfile> future = new CompletableFuture<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserProfile user = snapshot.getValue(UserProfile.class);
                    future.complete(user);
                } else {
                    future.complete(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        return future;
    }

    // private void initalizeAboutPage(){
     
    //     aboutt aboutpageObj =new aboutt();
    //     aboutpageObj.setAboutPrimaryStage(userProfilePrimaryStage);
    //     about2Scene= new Scene(aboutpageObj.createAboutScene(),1600,800);
    //     aboutpageObj.setAbout1Scene(about2Scene);
        

    // }


    private void initalizeSignupPage3(){
          
        SignupPage signupPage3Obj = new SignupPage();
        signupPage3Obj.setSignupPagePrimaryStage(userProfilePrimaryStage);
        signup3Scene= new Scene(signupPage3Obj.createSignupPageScene(),1600,800);
        signupPage3Obj.setSignupPage1Scene(signup3Scene);



        }

    }
