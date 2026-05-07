package com.login.Controller;

import com.login.Model.AdminMaintenanceModel;
import com.login.Utils.AdminMaintenanceFirebaseServices;

import java.util.List;

public class AdminMaintenanceController {
    public static List<AdminMaintenanceModel> getFlatData() {
        return AdminMaintenanceFirebaseServices.fetchFlatData();
    }
}
