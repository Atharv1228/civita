package com.login.Controller;

import com.login.Utils.RazorpayPaymentService;
import com.login.Utils.UserSession;
import com.razorpay.Order;

/**
 * Controller class for handling payment operations.
 * Integrates with RazorpayPaymentService for processing payments.
 */
public class PaymentController {
    
    /**
     * Initiates a maintenance payment using Razorpay.
     * @param amount The payment amount in rupees
     * @param flatNo The flat number
     * @param onSuccess Callback for successful payment initiation
     * @param onFailure Callback for failed payment initiation
     * @return The Razorpay order ID if successful, null otherwise
     */
    public static String initiateMaintenancePayment(double amount, String flatNo, 
            Runnable onSuccess, Runnable onFailure) {
        try {
            UserSession session = UserSession.getInstance();
            String userUid = session.getUserUid();
            String email = session.getEmail();
            String name = session.getDisplayName() != null ? session.getDisplayName() : "Resident";
            
            System.out.println("PaymentController: Initiating maintenance payment - Amount: Rs. " + amount + ", Flat: " + flatNo);
            
            // Create Razorpay order
            Order order = RazorpayPaymentService.createMaintenancePaymentOrder(amount, flatNo, userUid);
            
            if (order != null) {
                String orderId = order.get("id");
                System.out.println("PaymentController: Maintenance order created - " + orderId);
                
                // Open payment page
                boolean opened = RazorpayPaymentService.openPaymentPage(
                    orderId, 
                    amount, 
                    name, 
                    email, 
                    "Maintenance Payment for Flat " + flatNo
                );
                
                if (opened && onSuccess != null) {
                    onSuccess.run();
                }
                
                return orderId;
            } else {
                System.err.println("PaymentController: Failed to create maintenance order");
                if (onFailure != null) {
                    onFailure.run();
                }
                return null;
            }
        } catch (Exception e) {
            System.err.println("PaymentController: Error initiating maintenance payment - " + e.getMessage());
            e.printStackTrace();
            if (onFailure != null) {
                onFailure.run();
            }
            return null;
        }
    }
    
    /**
     * Initiates an electricity payment using Razorpay.
     * @param amount The payment amount in rupees
     * @param flatNo The flat number
     * @param onSuccess Callback for successful payment initiation
     * @param onFailure Callback for failed payment initiation
     * @return The Razorpay order ID if successful, null otherwise
     */
    public static String initiateElectricityPayment(double amount, String flatNo,
            Runnable onSuccess, Runnable onFailure) {
        try {
            UserSession session = UserSession.getInstance();
            String userUid = session.getUserUid();
            String email = session.getEmail();
            String name = session.getDisplayName() != null ? session.getDisplayName() : "Resident";
            
            System.out.println("PaymentController: Initiating electricity payment - Amount: Rs. " + amount + ", Flat: " + flatNo);
            
            // Create Razorpay order
            Order order = RazorpayPaymentService.createElectricityPaymentOrder(amount, flatNo, userUid);
            
            if (order != null) {
                String orderId = order.get("id");
                System.out.println("PaymentController: Electricity order created - " + orderId);
                
                // Open payment page
                boolean opened = RazorpayPaymentService.openPaymentPage(
                    orderId, 
                    amount, 
                    name, 
                    email, 
                    "Electricity Bill Payment for Flat " + flatNo
                );
                
                if (opened && onSuccess != null) {
                    onSuccess.run();
                }
                
                return orderId;
            } else {
                System.err.println("PaymentController: Failed to create electricity order");
                if (onFailure != null) {
                    onFailure.run();
                }
                return null;
            }
        } catch (Exception e) {
            System.err.println("PaymentController: Error initiating electricity payment - " + e.getMessage());
            e.printStackTrace();
            if (onFailure != null) {
                onFailure.run();
            }
            return null;
        }
    }
    
    /**
     * Verifies a payment after completion.
     * @param orderId The Razorpay order ID
     * @param paymentId The Razorpay payment ID
     * @param signature The signature from Razorpay
     * @return true if payment is verified, false otherwise
     */
    public static boolean verifyPayment(String orderId, String paymentId, String signature) {
        return RazorpayPaymentService.verifyPaymentSignature(orderId, paymentId, signature);
    }
    
    /**
     * Callback interface for payment results.
     */
    public interface PaymentResultCallback {
        void onPaymentSuccess(String paymentId, String orderId);
        void onPaymentFailure(String errorMessage);
    }
}
