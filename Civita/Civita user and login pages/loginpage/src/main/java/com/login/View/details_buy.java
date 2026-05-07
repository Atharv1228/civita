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

public class details_buy  {

    Scene buyFlat1Scene;

    Stage buyFlatGuestPrimaryStage;

    public void setBuyFlat1Scene(Scene buyFlat1Scene) {
        this.buyFlat1Scene = buyFlat1Scene;
    }

    public void setBuyFlatGuestPrimaryStage(Stage buyFlatGuestPrimaryStage) {
        this.buyFlatGuestPrimaryStage = buyFlatGuestPrimaryStage;
    }

   

    public VBox createBuyFlatsScene(Runnable buyBackToChoosePage){


        // Adding a back button

                Button backButton = new Button("Back");
                backButton.setStyle(
                                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color:rgb(251, 222, 251); -fx-border-color: black; -fx-background-radius: 20; -fx-border-radius: 20;");
                backButton.setPadding(new Insets(5, 15, 5, 15));

                // (Optional) Add action
                backButton.setOnAction(e -> {
                    buyBackToChoosePage.run();
                        System.out.println("Back button clicked!");

                        // code of navigation
                });
        //Initializing Firebase ...

        // if (FirebaseApp.getApps().isEmpty()) {
        //     FileInputStream serviceAccount = new FileInputStream("demo\\src\\main\\resources\\civita-alpha-firebase-adminsdk-fbsvc-0ca705c544.json");

        //     FirebaseOptions options = FirebaseOptions.builder()
        //             .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        //             .build();

        //     FirebaseApp.initializeApp(options);
        // }

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #E6E6FA;");

        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER);
        Text title = new Text("Flats available to purchase");
        title.setStyle(
                "-fx-font-size: 36px; -fx-fill: DARKSLATEGRAY; -fx-font-weight: bold; -fx-font-family: Comic Sans MS");
        titleBox.getChildren().add(title);

        VBox allFlatsContainer = new VBox(30);
        allFlatsContainer.setAlignment(Pos.TOP_CENTER);

        List<String> commonImageUrls = Arrays.asList(
                "https://imgs.search.brave.com/152hCzfIj8XIh1DeatiIDckgmVHDy3lQVQj92QBv2pM/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9wZXJp/bGludGVyaW9ycy5j/b20vd3AtY29udGVu/dC91cGxvYWRzL2hv/dXNlLWludGVyaW9y/LWhhbGwtdHYtc2hv/d2Nhc2UtZGVzaWdu/LWlkZWFzLS5qcGc",
                "https://imgs.search.brave.com/JO70ZEj8IfHBm0t2vAPaa62T2Taw2ugP6oMBF5258EU/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9tZWRp/YS5hcmNoaXRlY3R1/cmFsZGlnZXN0LmNv/bS9waG90b3MvNjg1/YzYwZDcyZWFkNDdm/YzU4OGVkNDEwLzE6/MS9wYXNzL3VuZGVm/aW5lZA",
                "https://imgs.search.brave.com/7LkSW0EcuRjqsN3RZXerDJ6SzJBUrp5hbuF9OFIcGCU/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9jZG4u/Y3JlYXRlLnZpc3Rh/LmNvbS9hcGkvbWVk/aWEvc21hbGwvMTg1/NjUwMjA0L3N0b2Nr/LXBob3RvLWludGVy/aW9yLWNvenktYmVk/cm9vbS1jbG9zZXQt/YmVkLW1vZGVybi1k/ZXNpZ24",
                "https://imgs.search.brave.com/NdiFGd2-5WmH5CroHin-5GqagOgl3892_2gofhp1A-w/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9jZG4u/ZGVjb2lzdC5jb20v/d3AtY29udGVudC91/cGxvYWRzLzIwMTgv/MDgvVGlueS1iYXRo/cm9vbS1vZi10aGUt/c21hbGwtYXR0aWMt/YXBhcnRtZW50LXdp/dGgtU2NhbmRpbmF2/aWFuLXN0eWxlLmpw/Zw");
                

        List<Flat> flats = new ArrayList<>();

        Firestore db = FirebaseInitialize.getDB();
        ApiFuture<QuerySnapshot> future = db.collection("flats").get();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                Flat flat = new Flat(
                        doc.getString("flatNo"),
                        doc.getString("flatType"),
                        doc.getString("amenities"),
                        doc.getString("compatibleFor"),
                        doc.getString("price"),
                        commonImageUrls // use default dummy images
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
        detailCard.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #FFFFFF, #C5F0F2);"
                        + "-fx-background-radius: 15;");
        detailCard.setPrefWidth(650);

        detailCard.getChildren().addAll(
                createField("Flat No:", flat.flatNo),
                createField("Flat Type:", flat.flatType),
                createField("Amenities:", flat.amenities),
                createField("Compatible For:", flat.compatibleFor),
                createField("Flat Price:", flat.price));

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
}
