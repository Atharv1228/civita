# Razorpay Payment Configuration Guide

## Overview
This application uses Razorpay for payment processing. Follow the steps below to configure it properly.

## Prerequisites
- Razorpay account (https://razorpay.com)
- Razorpay API Key ID and Key Secret

## Configuration Steps

### 1. Get Your Razorpay Credentials
1. Log in to your Razorpay Dashboard
2. Navigate to Settings → API Keys
3. Copy your **Key ID** and **Key Secret**

### 2. Set Environment Variables

#### For Development (Local Testing)
Create a `.env` file in the project root or set system environment variables:

```bash
# Linux/Mac
export RAZORPAY_KEY_ID=your_key_id_here
export RAZORPAY_KEY_SECRET=your_key_secret_here
```

```bash
# Windows (Command Prompt)
set RAZORPAY_KEY_ID=your_key_id_here
set RAZORPAY_KEY_SECRET=your_key_secret_here
```

#### For Production (Deployment)
Set environment variables in your deployment platform:
- Vercel: Settings → Environment Variables
- Docker: In Dockerfile or docker-compose.yml
- Other platforms: Follow their documentation

### 3. Firebase Configuration
Ensure the Firebase service account key file is placed at:
```
src/main/resources/civita-alpha-firebase-adminsdk-fbsvc-0ca705c544.json
```

### 4. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn javafx:run
```

## Troubleshooting

### Razorpay Not Initialized
**Error**: "Razorpay is not properly configured"
- Check environment variables are set correctly
- Verify API Key ID and Key Secret are valid
- Check logs for detailed error messages

### Payment Order Creation Failed
**Error**: "Failed to create payment order"
- Verify internet connection
- Check Razorpay API credentials
- Ensure amount is positive and in paise
- Review Razorpay API response in logs

### Firebase Connection Issues
**Error**: "Error initializing Firebase"
- Verify service account JSON file exists and is valid
- Check file path matches configuration
- Ensure Google Cloud credentials are correct
- Check Firebase project is active

## Payment Flow

1. User clicks "Pay Electricity Bill"
2. Application validates bill data
3. Razorpay order is created via API
4. Payment popup/redirect initiated
5. User completes payment on Razorpay
6. Payment status updated in Firebase
7. Success confirmation displayed

## Testing

### Test Payments
Use Razorpay test mode credentials for testing:
- Test Card: 4111 1111 1111 1111
- Expiry: Any future date
- CVV: Any 3 digits

### Check Logs
Monitor console logs for:
- `[RazorpayPaymentService]` - Payment service logs
- `[ResidentElectricity]` - UI interaction logs
- `[FirebaseInitializer]` - Database initialization logs

## Security Notes

⚠️ **Important**:
- Never hardcode API keys in source code
- Always use environment variables
- Keep Key Secret confidential
- Use HTTPS in production
- Implement proper signature verification for production

## Support

For issues with:
- **Razorpay Integration**: https://razorpay.com/support
- **Firebase**: https://firebase.google.com/support
- **Application**: Contact development team

## Additional Resources

- Razorpay Documentation: https://razorpay.com/docs/
- Firebase Admin SDK: https://firebase.google.com/docs/database
- Razorpay Java SDK: https://github.com/razorpay/razorpay-java
