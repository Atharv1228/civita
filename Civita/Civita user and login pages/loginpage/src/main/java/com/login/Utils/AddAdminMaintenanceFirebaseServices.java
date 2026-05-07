package com.login.Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.api.core.ApiFuture;
import com.login.Model.AddAdminMaintenanceModel;

public class AddAdminMaintenanceFirebaseServices {

    public static void addMaintenance(AddAdminMaintenanceModel model) {
        try {
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("MaintenanceData")
                    .child(model.getFlatNo()) // store under flat number
                    .push(); // create unique ID

            ApiFuture<Void> future = ref.setValueAsync(model);

            // Block until completion for debug purposes
            future.get(); // waits for completion
            System.out.println(" Data added to Firebase for Flat: " + model.getFlatNo());

        } catch (Exception e) {
            System.out.println(" Failed to add data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
