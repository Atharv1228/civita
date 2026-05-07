
package com.login.View;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import com.login.services.FirebaseInitialize;

public class details_rent  {

    Scene rent1Scene;
    
    Stage rentPrimaryStage;
    public void setRentPrimaryStage(Stage rentPrimaryStage) {
        this.rentPrimaryStage = rentPrimaryStage;
    }

    public void setRent1Scene(Scene rent1Scene) {
        this.rent1Scene = rent1Scene;
    }

   


    

    public static class Flat {
        String flatNo, flatType, amenities, compatibleFor, price;
        List<String> imageUrls;

        public Flat(String flatNo, String flatType, String amenities, String compatibleFor, String price,
                    List<String> imageUrls) {
            this.flatNo = flatNo;
            this.flatType = flatType;
            this.amenities = amenities;
            this.compatibleFor = compatibleFor;
            this.price = price;
            this.imageUrls = imageUrls;
        }
    }


    public VBox createRentScene(Runnable rentBackToChoosePage){


        // Adding a back button

                Button backButton = new Button("Back");
                backButton.setStyle(
                                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
                backButton.setPadding(new Insets(5, 15, 5, 15));

                // (Optional) Add action
                backButton.setOnAction(e -> {
                    rentBackToChoosePage.run();
                        System.out.println("Back button clicked!");

                        // code of navigation
                });
        // Initialize Firebase
        // if (FirebaseApp.getApps().isEmpty()) {
        //     FileInputStream serviceAccount = new FileInputStream(
        //             "demo\\src\\main\\resources\\civita-alpha-firebase-adminsdk-fbsvc-0ca705c544.json");

        //     FirebaseOptions options = FirebaseOptions.builder()
        //             .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        //             .build();

        //     FirebaseApp.initializeApp(options);
        // }

        // Default fallback images (same as details_buy)
        List<String> defaultImageUrls = Arrays.asList(
                "https://imgs.search.brave.com/Wq5GWyZ8NOGvVkYf3hMRHCR-0fOtN_RSq1Yk4Y5hKto/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9pLnBp/bmltZy5jb20vb3Jp/Z2luYWxzLzRhLzE1/L2YxLzRhMTVmMTVm/OGIxODAzZWZlYzli/NThjNDU5ZTFiZjU3/LmpwZw",
                "https://imgs.search.brave.com/3VLlXv2pxzP6rvYYXYa_xzzOeRZevxqLPmhLy-cCu1A/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9raXRr/aXRjaGVucy5jb20u/YXUvd3AtY29udGVu/dC91cGxvYWRzLzIw/MjMvMDQvMDExLmpw/Zw",
                "https://imgs.search.brave.com/9UxAzVTxU9jgGU7cbR3SKvmk7lYSwlBQwtx0SeUWk3k/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tZWRp/YS5vbnRoZW1hcmtl/dC5jb20vcHJvcGVy/dGllcy8xNzU0MTYw/OC8xNTU3ODQ0NzMy/L2ltYWdlLTMtNDgw/eDMyMC5qcGc",
                "https://imgs.search.brave.com/MpOQTtk7ELR5WGSpP8nfIe-duTP8Xzl8Pk07p9LbyP8/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tZWRp/YS5ob3VzZWFuZGdh/cmRlbi5jby51ay9w/aG90b3MvNjQyYzJm/Y2YxMzA4YTQ3OTdh/NGI0ZmQ0L21hc3Rl/ci93XzMyMCxjX2xp/bWl0LzIxMDcwOF9M/YXVyZW5fV2Vpc3Nf/VW5pb25fU3RyZWV0/ODQ5Ni1wcm9kdWN0/aW9uX2RpZ2l0YWwu/anBn"
        );

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #E6E6FA;");

        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER);
        Text title = new Text("Flats available for Rent");
        title.setStyle(
                "-fx-font-size: 36px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        titleBox.getChildren().add(title);

        VBox allFlatsContainer = new VBox(30);
        allFlatsContainer.setAlignment(Pos.TOP_CENTER);

        List<Flat> flats = new ArrayList<>();

        Firestore db = FirebaseInitialize.getDB();
        ApiFuture<QuerySnapshot> future = db.collection("rentFlats").get();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                List<?> rawList = (List<?>) doc.get("imageUrls");
                List<String> imageUrls = new ArrayList<>();
                if (rawList != null) {
                    for (Object item : rawList) {
                        if (item instanceof String) {
                            imageUrls.add((String) item);
                        }
                    }
                }

                Flat flat = new Flat(
                        doc.getString("flatNo"),
                        doc.getString("flatType"),
                        doc.getString("amenities"),
                        doc.getString("compatibleFor"),
                        doc.getString("price"),
                        imageUrls.isEmpty() ? defaultImageUrls : imageUrls // use default if none
                );

                flats.add(flat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Flat flat : flats) {
            allFlatsContainer.getChildren().add(createFlatCard(flat));
        }

        ScrollPane verticalScroll = new ScrollPane(allFlatsContainer);
        verticalScroll.setFitToWidth(true);
        verticalScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        verticalScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        verticalScroll.setPadding(Insets.EMPTY);
        verticalScroll.setPrefViewportHeight(700);
        verticalScroll.setStyle("-fx-background-color: transparent; -fx-background-insets: 0;");

        root.getChildren().addAll(backButton,titleBox, verticalScroll);
        return root;

    }

    private VBox createFlatCard(Flat flat) {
        VBox flatBox = new VBox(12);
        flatBox.setAlignment(Pos.CENTER);
        flatBox.setPadding(new Insets(10, 20, 10, 20));

        HBox imageGallery = new HBox(15);
        imageGallery.setPadding(new Insets(10));
        for (String url : flat.imageUrls) {
            ImageView img = new ImageView(new Image(url + "?w=250&h=160&fit=crop"));
            img.setFitWidth(200);
            img.setFitHeight(150);
            img.setPreserveRatio(false);
            StackPane imgContainer = new StackPane(img);
            imgContainer.setPadding(new Insets(5));
            imageGallery.getChildren().add(imgContainer);
        }

        ScrollPane imageScroll = new ScrollPane(imageGallery);
        imageScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        imageScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        imageScroll.setPannable(true);
        imageScroll.setPrefHeight(180);
        imageScroll.setStyle("-fx-background-color: transparent; -fx-background-insets: 0;");

        VBox detailCard = new VBox(8);
        detailCard.setPadding(new Insets(15));
        detailCard.setAlignment(Pos.TOP_LEFT);
        detailCard.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #FFFFFF, #C5F0F2);"
                + "-fx-background-radius: 15;");
        detailCard.setPrefWidth(650);

        detailCard.getChildren().addAll(
                createField("Flat No:", flat.flatNo),
                createField("Flat Type:", flat.flatType),
                createField("Amenities:", flat.amenities),
                createField("Compatible For:", flat.compatibleFor),
                createField("Rent Price:", flat.price));

        flatBox.getChildren().addAll(imageScroll, detailCard);
        return flatBox;
    }

    private HBox createField(String label, String valueText) {
        Text labelText = new Text(label + " ");
        labelText.setStyle("-fx-font-size: 20px; -fx-fill: DARKSLATEGRAY; -fx-font-family: Comic Sans MS");

        Text value = new Text(valueText);
        value.setStyle("-fx-font-size: 20px; -fx-fill: DARKSLATEGRAY; -fx-font-family: Comic Sans MS");

        HBox hBox = new HBox(6, labelText, value);
        hBox.setAlignment(Pos.CENTER_LEFT);
        return hBox;
    }

}

