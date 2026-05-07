
package com.login.services;

import com.google.api.core.ApiFuture;

import com.google.cloud.firestore.*;

import com.login.Model.Flat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseService_rentFlat {
    public static Firestore db = FirebaseInitialize.getDB();

    // static {
    //     try {
    //         FileInputStream serviceAccount = new FileInputStream("src\\main\\resources\\civita-alpha-firebase-adminsdk-fbsvc-0ca705c544.json");

    //         FirebaseOptions options = FirebaseOptions.builder()
    //             .setCredentials(GoogleCredentials.fromStream(serviceAccount))
    //             .build();

    //         if (FirebaseApp.getApps().isEmpty()) {
    //             FirebaseApp.initializeApp(options);
    //         }

    //         db = FirestoreClient.getFirestore();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    public static void addRentFlat(Flat flat) {
        db.collection("rentFlats").add(flatToMap(flat));
    }

    public static List<Flat> getRentFlats() {
        List<Flat> flats = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> future = db.collection("rentFlats").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                Map<String, Object> data = doc.getData();
                flats.add(mapToFlat(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flats;
    }

    private static Map<String, Object> flatToMap(Flat flat) {
        return Map.of(
            "flatNo", flat.getFlatNo(),
            "flatType", flat.getFlatType(),
            "amenities", flat.getAmenities(),
            "compatibleFor", flat.getCompatibleFor(),
            "price", flat.getPrice()
        );
    }

    private static Flat mapToFlat(Map<String, Object> data) {
        return new Flat(
            (String) data.get("flatNo"),
            (String) data.get("flatType"),
            (String) data.get("amenities"),
            (String) data.get("compatibleFor"),
            (String) data.get("price")
        );
    }
}


