package com.login.Controller;

import com.login.Model.ResidentMaintenanceModel;
import com.login.Utils.ResidentMaintenanceFirebaseServices;
import com.login.Utils.UserSession;

import java.util.List;

public class ResidentMaintenanceController {

    /**
     * Fetches maintenance data for the currently logged-in user.
     * Uses UserSession to get the current user's UID.
     * @return A list of ResidentMaintenanceModel objects.
     */
    public static List<ResidentMaintenanceModel> getResidentMaintenanceData() {
        String userUid = UserSession.getInstance().getUserUid();
        if (userUid == null || userUid.isEmpty()) {
            System.err.println("ResidentMaintenanceController: User UID is null or empty. User may not be logged in.");
            return List.of(); // Return an empty list
        }
        System.out.println("ResidentMaintenanceController: Fetching maintenance data for user UID: " + userUid);
        return ResidentMaintenanceFirebaseServices.fetchUserMaintenanceData(userUid);
    }

    /**
     * Fetches maintenance data for a specific user.
     * @param userUid The Firebase User ID of the logged-in resident.
     * @return A list of ResidentMaintenanceModel objects.
     */
    public static List<ResidentMaintenanceModel> getResidentMaintenanceData(String userUid) {
        if (userUid == null || userUid.isEmpty()) {
            System.err.println("ResidentMaintenanceController: User UID is null or empty. Cannot fetch maintenance data.");
            return List.of(); // Return an empty list
        }
        System.out.println("ResidentMaintenanceController: Fetching maintenance data for user UID: " + userUid);
        return ResidentMaintenanceFirebaseServices.fetchUserMaintenanceData(userUid);
    }

    /**
     * Updates the payment status of a specific maintenance record for the current user.
     * Uses UserSession to get the current user's UID.
     * @param maintenanceId The document ID of the maintenance record to update.
     * @param newStatus The new status ("paid" or "unpaid").
     * @return true if the update was successful, false otherwise.
     */
    public static boolean updateMaintenanceStatus(String maintenanceId, String newStatus) {
        String userUid = UserSession.getInstance().getUserUid();
        if (userUid == null || userUid.isEmpty() || maintenanceId == null || maintenanceId.isEmpty() || newStatus == null || newStatus.isEmpty()) {
            System.err.println("ResidentMaintenanceController: Invalid parameters for updating maintenance status.");
            return false;
        }
        System.out.println("ResidentMaintenanceController: Updating maintenance status for user UID: " + userUid + ", maintenanceId: " + maintenanceId);
        return ResidentMaintenanceFirebaseServices.updateMaintenanceStatus(userUid, maintenanceId, newStatus);
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
            System.err.println("ResidentMaintenanceController: Invalid parameters for updating maintenance status.");
            return false;
        }
        System.out.println("ResidentMaintenanceController: Updating maintenance status for user UID: " + userUid);
        return ResidentMaintenanceFirebaseServices.updateMaintenanceStatus(userUid, maintenanceId, newStatus);
    }

    /**
     * Adds a new maintenance record for the current user's flat.
     * Uses UserSession to get the current user's UID.
     * @param flatNo The flat number associated with the maintenance.
     * @param amount The maintenance amount.
     * @param date The due date.
     * @param status The initial status (e.g., "unpaid").
     * @return true if the record was added successfully, false otherwise.
     */
    public static boolean addMaintenanceForCurrentUser(String flatNo, String amount, String date, String status) {
        String userUid = UserSession.getInstance().getUserUid();
        if (userUid == null || userUid.isEmpty() || flatNo == null || flatNo.isEmpty() || amount == null || amount.isEmpty() || date == null || date.isEmpty() || status == null || status.isEmpty()) {
            System.err.println("ResidentMaintenanceController: Invalid parameters for adding maintenance data.");
            return false;
        }
        return ResidentMaintenanceFirebaseServices.addMaintenanceRecord(userUid, flatNo, amount, date, status);
    }

    /**
     * Adds a new maintenance record for a specific user's flat.
     * @param userUid The Firebase User ID of the resident.
     * @param flatNo The flat number associated with the maintenance.
     * @param amount The maintenance amount.
     * @param date The due date.
     * @param status The initial status (e.g., "unpaid").
     * @return true if the record was added successfully, false otherwise.
     */
    public static boolean addMaintenanceForUser(String userUid, String flatNo, String amount, String date, String status) {
        if (userUid == null || userUid.isEmpty() || flatNo == null || flatNo.isEmpty() || amount == null || amount.isEmpty() || date == null || date.isEmpty() || status == null || status.isEmpty()) {
            System.err.println("ResidentMaintenanceController: Invalid parameters for adding maintenance data.");
            return false;
        }
        return ResidentMaintenanceFirebaseServices.addMaintenanceRecord(userUid, flatNo, amount, date, status);
    }
    
    /**
     * Gets the current logged-in user's UID.
     * @return The user UID or null if not logged in.
     */
    public static String getCurrentUserUid() {
        return UserSession.getInstance().getUserUid();
    }
}
