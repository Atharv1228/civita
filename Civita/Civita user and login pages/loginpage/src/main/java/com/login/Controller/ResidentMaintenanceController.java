package com.login.Controller;

import com.login.Model.ResidentMaintenanceModel;
import com.login.Utils.ResidentMaintenanceFirebaseServices;

import java.util.List;

public class ResidentMaintenanceController {

    /**
     * Fetches maintenance data for a specific user.
     * @param userUid The Firebase User ID of the logged-in resident.
     * @return A list of ResidentMaintenanceModel objects.
     */
    public static List<ResidentMaintenanceModel> getResidentMaintenanceData(String userUid) {
        if (userUid == null || userUid.isEmpty()) {
            System.err.println("User UID is null or empty. Cannot fetch maintenance data.");
            return List.of(); // Return an empty list
        }
        return ResidentMaintenanceFirebaseServices.fetchUserMaintenanceData("50iKugZzqmVWeByI9kKXcFh4hl42");
    }

    /**
     * Updates the payment status of a specific maintenance record.
     * @param userUid The Firebase User ID of the resident.
     * @param maintenanceId The document ID of the maintenance record to update.
     * @param newStatus The new status ("paid" or "unpaid").
     * @return true if the update was successful, false otherwise.
     */
    public static boolean updateMaintenanceStatus(String userUid, String maintenanceId, String newStatus) {
        if (userUid == null || userUid.isEmpty() || maintenanceId == null || maintenanceId.isEmpty() || newStatus == null || newStatus.isEmpty()) {
            System.err.println("Invalid parameters for updating maintenance status.");
            return false;
        }
        return ResidentMaintenanceFirebaseServices.updateMaintenanceStatus("50iKugZzqmVWeByI9kKXcFh4hl42", maintenanceId, newStatus);
    }

    /**
     * Adds a new maintenance record for a specific user's flat.
     * This method would typically be called by the Admin side to create new maintenance records.
     * It's included here for completeness of the resident's perspective if it were to initiate creation.
     * For your current setup, the Admin adds maintenance.
     * @param userUid The Firebase User ID of the resident.
     * @param flatNo The flat number associated with the maintenance.
     * @param amount The maintenance amount.
     * @param date The due date.
     * @param status The initial status (e.g., "unpaid").
     * @return true if the record was added successfully, false otherwise.
     */
    public static boolean addMaintenanceForUser(String userUid, String flatNo, String amount, String date, String status) {
        if (userUid == null || userUid.isEmpty() || flatNo == null || flatNo.isEmpty() || amount == null || amount.isEmpty() || date == null || date.isEmpty() || status == null || status.isEmpty()) {
            System.err.println("Invalid parameters for adding maintenance data.");
            return false;
        }
        return ResidentMaintenanceFirebaseServices.addMaintenanceRecord("50iKugZzqmVWeByI9kKXcFh4hl42", flatNo, amount, date, status);
    }
}
