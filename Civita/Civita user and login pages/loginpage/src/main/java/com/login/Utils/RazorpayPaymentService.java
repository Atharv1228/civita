package com.login.Utils;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;

import java.awt.Desktop;
import java.net.URI;

/**
 * Service class for handling Razorpay payment integration.
 * Provides methods to create payment orders and process payments for
 * maintenance and electricity bills.
 */
public class RazorpayPaymentService {
    
    // Razorpay API Keys - Replace with your actual keys
    // For testing, use test keys from Razorpay Dashboard
    private static final String RAZORPAY_KEY_ID = "rzp_test_YOUR_KEY_ID";
    private static final String RAZORPAY_KEY_SECRET = "YOUR_KEY_SECRET";
    
    private static RazorpayClient razorpayClient;
    
    /**
     * Initializes the Razorpay client.
     * Call this method once at application startup.
     */
    public static void initialize() {
        try {
            razorpayClient = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_KEY_SECRET);
            System.out.println("RazorpayPaymentService: Initialized successfully");
        } catch (RazorpayException e) {
            System.err.println("RazorpayPaymentService: Failed to initialize - " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Initializes the Razorpay client with custom keys.
     * @param keyId Razorpay Key ID
     * @param keySecret Razorpay Key Secret
     */
    public static void initialize(String keyId, String keySecret) {
        try {
            razorpayClient = new RazorpayClient(keyId, keySecret);
            System.out.println("RazorpayPaymentService: Initialized with custom keys");
        } catch (RazorpayException e) {
            System.err.println("RazorpayPaymentService: Failed to initialize - " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Creates a Razorpay order for payment.
     * @param amount Amount in paise (e.g., 50000 for Rs. 500)
     * @param currency Currency code (e.g., "INR")
     * @param receiptId Unique receipt ID for tracking
     * @param description Description of the payment
     * @return The created Order object or null if failed
     */
    public static Order createOrder(int amount, String currency, String receiptId, String description) {
        try {
            if (razorpayClient == null) {
                initialize();
            }
            
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount); // Amount in paise
            orderRequest.put("currency", currency);
            orderRequest.put("receipt", receiptId);
            orderRequest.put("notes", new JSONObject().put("description", description));
            
            Order order = razorpayClient.orders.create(orderRequest);
            System.out.println("RazorpayPaymentService: Order created - " + order.get("id"));
            return order;
            
        } catch (RazorpayException e) {
            System.err.println("RazorpayPaymentService: Failed to create order - " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Creates a maintenance payment order.
     * @param amount Amount in rupees
     * @param flatNo Flat number for receipt ID
     * @param userUid User UID for tracking
     * @return The created Order object or null if failed
     */
    public static Order createMaintenancePaymentOrder(double amount, String flatNo, String userUid) {
        int amountInPaise = (int) (amount * 100); // Convert to paise
        String receiptId = "MAINT_" + flatNo.replace(" ", "_") + "_" + System.currentTimeMillis();
        String description = "Maintenance Payment for Flat " + flatNo;
        
        System.out.println("RazorpayPaymentService: Creating maintenance order - Amount: Rs. " + amount + ", Flat: " + flatNo);
        return createOrder(amountInPaise, "INR", receiptId, description);
    }
    
    /**
     * Creates an electricity payment order.
     * @param amount Amount in rupees
     * @param flatNo Flat number for receipt ID
     * @param userUid User UID for tracking
     * @return The created Order object or null if failed
     */
    public static Order createElectricityPaymentOrder(double amount, String flatNo, String userUid) {
        int amountInPaise = (int) (amount * 100); // Convert to paise
        String receiptId = "ELEC_" + flatNo.replace(" ", "_") + "_" + System.currentTimeMillis();
        String description = "Electricity Bill Payment for Flat " + flatNo;
        
        System.out.println("RazorpayPaymentService: Creating electricity order - Amount: Rs. " + amount + ", Flat: " + flatNo);
        return createOrder(amountInPaise, "INR", receiptId, description);
    }
    
    /**
     * Opens the Razorpay checkout page in the default browser.
     * This is a simplified approach - in production, you'd want a proper web server.
     * @param orderId The Razorpay order ID
     * @param amount Amount in rupees (for display)
     * @param name Customer name
     * @param email Customer email
     * @param description Payment description
     * @return true if browser opened successfully, false otherwise
     */
    public static boolean openPaymentPage(String orderId, double amount, String name, String email, String description) {
        try {
            // Create a simple HTML payment page URL
            // In production, this would be your server endpoint that serves the checkout page
            String paymentUrl = generatePaymentPageUrl(orderId, amount, name, email, description);
            
            // Open in default browser
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(paymentUrl));
                System.out.println("RazorpayPaymentService: Opened payment page in browser");
                return true;
            } else {
                System.err.println("RazorpayPaymentService: Desktop browsing not supported");
                return false;
            }
        } catch (Exception e) {
            System.err.println("RazorpayPaymentService: Failed to open payment page - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Generates a payment page URL.
     * In a real application, this would point to your server that hosts the checkout page.
     * For now, we'll create a data URL with the checkout form.
     */
    private static String generatePaymentPageUrl(String orderId, double amount, String name, String email, String description) {
        // This is a simplified URL - in production, you'd redirect to your payment server
        // The actual Razorpay checkout needs to be loaded via JavaScript on a web page
        return "https://razorpay.com/docs/payment-gateway/quick-integration/#test-integration";
    }
    
    /**
     * Payment result callback interface.
     */
    public interface PaymentCallback {
        void onSuccess(String paymentId, String orderId, String signature);
        void onFailure(String errorCode, String errorDescription);
    }
    
    /**
     * Verifies a payment signature.
     * @param orderId The Razorpay order ID
     * @param paymentId The Razorpay payment ID
     * @param signature The signature received from Razorpay
     * @return true if signature is valid, false otherwise
     */
    public static boolean verifyPaymentSignature(String orderId, String paymentId, String signature) {
        try {
            JSONObject attributes = new JSONObject();
            attributes.put("razorpay_order_id", orderId);
            attributes.put("razorpay_payment_id", paymentId);
            attributes.put("razorpay_signature", signature);
            
            boolean isValid = com.razorpay.Utils.verifyPaymentSignature(attributes, RAZORPAY_KEY_SECRET);
            System.out.println("RazorpayPaymentService: Payment signature verification - " + (isValid ? "Valid" : "Invalid"));
            return isValid;
            
        } catch (RazorpayException e) {
            System.err.println("RazorpayPaymentService: Signature verification failed - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Gets the Razorpay Key ID for frontend use.
     * @return The Razorpay Key ID
     */
    public static String getKeyId() {
        return RAZORPAY_KEY_ID;
    }
}
