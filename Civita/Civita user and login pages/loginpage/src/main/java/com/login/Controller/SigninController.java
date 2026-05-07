package com.login.Controller;

import com.login.Model.SigninModel;
import com.login.Utils.SigninFirebaseServices;
import org.json.JSONObject;

public class SigninController {
    public static String loginUser(SigninModel user) {
        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            return "Please fill in all fields.";
        }

        JSONObject response = SigninFirebaseServices.authenticateUser(user.getEmail(), user.getPassword());

        if (response.getString("status").equals("success")) {
            return "success";
        } else {
            return response.getString("message");
        }
    }
}
