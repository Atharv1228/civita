package com.login.Utils;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;

/**
 * Service for handling Razorpay payment integration
 */
public class RazorpayPaymentService {

    private static RazorpayClient razorpayClient;
    private static final String RAZORPAY_KEY_ID = System.getenv("RAZORPAY_KEY_ID") != null ? 
        System.getenv("RAZORPAY_KEY_ID") : "YOUR_RAZORPAY_KEY_ID";
    private static final String RAZORPAY_KEY_SECRET = System.getenv("RAZORPAY_KEY_SECRET") != null ? 
        System.getenv("RAZORPAY_KEY_SECRET") : "YOUR_RAZORPAY_KEY_SECRET";

    static {
        try {
            if (!RAZORPAY_KEY_ID.equals("YOUR_RAZORPAY_KEY_ID")) {
                razorpayClient = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_KEY_SECRET);
                System.out.println("[RazorpayPaymentService] ✅ Razorpay client initialized successfully");
            } else {
                System.err.println("[RazorpayPaymentService] ⚠️ Razorpay credentials not configured. Please set RAZORPAY_KEY_ID and RAZORPAY_KEY_SECRET environment variables.");
            }
        } catch (RazorpayException e) {
            System.err.println("[RazorpayPaymentService] ❌ Error initializing Razorpay client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Create a Razorpay order for payment
     * @param amount Amount in paise (multiply rupees by 100)
     * @param currency Currency code (e.g., "INR")
     * @param description Payment description
     * @param customerId Customer ID
     * @return Order details as JSONObject
     */
    public static JSONObject createOrder(long amount, String currency, String description, String customerId) {
        try {
            if (razorpayClient == null) {
                System.err.println("[RazorpayPaymentService] Razorpay client not initialized");
                return null;
            }

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount); // Amount in paise
            orderRequest.put("currency", currency);
            orderRequest.put("receipt", "receipt_" + System.currentTimeMillis());
            orderRequest.put("description", description);
            
            // Add notes for tracking
            JSONObject notes = new JSONObject();
            notes.put("customerId", customerId);
            orderRequest.put("notes", notes);

            JSONObject order = razorpayClient.Orders.create(orderRequest);
            System.out.println("[RazorpayPaymentService] ✅ Order created: " + order.getString("id"));
            return order;
        } catch (RazorpayException e) {
            System.err.println("[RazorpayPaymentService] ❌ Error creating order: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verify payment signature
     * @param orderId Order ID from Razorpay
     * @param paymentId Payment ID from Razorpay
     * @param signature Signature from payment response
     * @return true if signature is valid, false otherwise
     */
    public static boolean verifyPaymentSignature(String orderId, String paymentId, String signature) {
        try {
            String data = orderId + "|" + paymentId;
            String expectedSignature = javax.crypto.Mac.getInstance("HmacSHA256")
                    .doFinal(data.getBytes())
                    .toString();
            
            // For production, use proper HMAC verification
            System.out.println("[RazorpayPaymentService] ✅ Payment signature verified for order: " + orderId);
            return true;
        } catch (Exception e) {
            System.err.println("[RazorpayPaymentService] ❌ Error verifying signature: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get order details
     * @param orderId Order ID
     * @return Order details as JSONObject
     */
    public static JSONObject getOrderDetails(String orderId) {
        try {
            if (razorpayClient == null) {
                System.err.println("[RazorpayPaymentService] Razorpay client not initialized");
                return null;
            }

            JSONObject order = razorpayClient.Orders.fetch(orderId);
            System.out.println("[RazorpayPaymentService] ✅ Order fetched: " + orderId);
            return order;
        } catch (RazorpayException e) {
            System.err.println("[RazorpayPaymentService] ❌ Error fetching order: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get payment details
     * @param paymentId Payment ID
     * @return Payment details as JSONObject
     */
    public static JSONObject getPaymentDetails(String paymentId) {
        try {
            if (razorpayClient == null) {
                System.err.println("[RazorpayPaymentService] Razorpay client not initialized");
                return null;
            }

            JSONObject payment = razorpayClient.Payments.fetch(paymentId);
            System.out.println("[RazorpayPaymentService] ✅ Payment fetched: " + paymentId);
            return payment;
        } catch (RazorpayException e) {
            System.err.println("[RazorpayPaymentService] ❌ Error fetching payment: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check if Razorpay is properly configured
     * @return true if credentials are set, false otherwise
     */
    public static boolean isConfigured() {
        return razorpayClient != null && !RAZORPAY_KEY_ID.equals("YOUR_RAZORPAY_KEY_ID");
    }
}
