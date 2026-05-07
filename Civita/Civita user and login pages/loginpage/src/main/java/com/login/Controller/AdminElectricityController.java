package com.login.Controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.login.Model.AdminMaintenanceModel;
import com.login.Utils.AdminMaintenanceFirebaseServices;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminElectricityController {
    public static List<AdminMaintenanceModel> getFlatData() {
        return AdminMaintenanceFirebaseServices.fetchFlatData();


    }
     public static java.io.File chooseAndSaveImage(Stage primaryStage) {
        
   
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Electricity Bill Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        java.io.File selectedFile = fileChooser.showOpenDialog(primaryStage);
        return selectedFile;
    }
     public static void saveElectricityBill(String flatNo, Double amount, String date, String imagePath) {
         try {
        
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

   
            DatabaseReference electricityBillsRef = databaseRef.child("electricityBills").child(flatNo);

            // Create a unique key for each bill entry
            String billId = electricityBillsRef.push().getKey();

            // Prepare the data to be saved
            Map<String, Object> billData = new HashMap<>();
            billData.put("amount", amount);
            billData.put("date", date);
            billData.put("imagePath", imagePath); // Store the path if needed

            // Save the data to Firebase
            electricityBillsRef.child(billId).setValue(billData, (databaseError, databaseReference) -> {
                if (databaseError != null) {
                    System.err.println("Data could not be saved " + databaseError.getMessage());
                } else {
                    System.out.println("Electricity bill data saved successfully for Flat No: " + flatNo);
                }
            });

            // If you need to upload the image to Firebase Storage, that would be a separate step here.
            // For now, you are only storing the local file path.

        } catch (Exception e) {
            System.err.println("Error saving electricity bill: " + e.getMessage());
            e.printStackTrace();
        }
    }

 
     }

