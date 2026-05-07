package com.login.Utils;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.login.Model.AdminMaintenanceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AdminElectricityFirebaseServices {

    public static List<AdminMaintenanceModel> fetchFlatData() {
        List<AdminMaintenanceModel> flatList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();

        try {
            ApiFuture<QuerySnapshot> future = db.collection("users").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                String flatNo = doc.getString("flatNo");
                String fullName = doc.getString("fullName");

                if (flatNo != null && fullName != null) {
                    flatList.add(new AdminMaintenanceModel());
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return flatList;
    }
}
