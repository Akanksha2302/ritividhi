package com.dev.ritividhi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dev.ritividhi.models.PaymentRequest;
import com.dev.ritividhi.models.TransactionDetail;
import com.dev.ritividhi.repository.TransactionDetailRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Service
public class PaymentService {
	
	
	@Autowired
	private TransactionDetailRepository transactionDetailRepository;
	
	
    private final String MERCHANT_ID = "M22FUH33ACWJT";
    private final String SALT_KEY = "c07178ff-0a9f-4fc1-b619-d0ec2e58676e";
    private final String SALT_INDEX = "1";  
    private final String CALLBACK_URL = "https://app.ritividhi.com/ritividhi/callback";
    private final String REDIRECT_URL = "https://www.ritividhi.com";

    private final String PHONEPE_PAY_API_URL = "https://api.phonepe.com/apis/hermes/pg/v1/pay";
    
 

    
 // Method to generate SHA-256 hash
    public static String generateSHA256Hash(String data) throws NoSuchAlgorithmException {
        // Get SHA-256 MessageDigest instance
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Compute the hash
        byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));

        // Convert byte array to hex string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    

    // Initiate Payment using PhonePe API

    public String initiatePayment(PaymentRequest request) throws Exception {
        // Create the transaction ID
        String merchantTransactionId = UUID.randomUUID().toString().substring(0, 34); 
        
        System.out.println("merchantTransactionId---"+ merchantTransactionId);
        long transactionAmount = request.getAmount() * 100;
        // UserId
        String merchantUserId = request.getMerchantUserId();      
        
        String orderId = request.getOrderId();

        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setMerchantTransactionId(merchantTransactionId); 
        transactionDetail.setOrderId(orderId); 


        transactionDetailRepository.save(transactionDetail);

        String rawRequestBody = String.format(
        	    "{\"merchantId\":\"%s\",\"merchantTransactionId\":\"%s\",\"merchantUserId\":\"%s\",\"amount\":%d,\"redirectUrl\":\"%s\",\"redirectMode\":\"REDIRECT\",\"callbackUrl\":\"%s\",\"paymentInstrument\":{\"type\":\"PAY_PAGE\"}}",
        	    MERCHANT_ID, merchantTransactionId, merchantUserId, transactionAmount, REDIRECT_URL, CALLBACK_URL
        	);
        
        // Encode the request body in Base64
        String encodedRequestBody = Base64.getEncoder().encodeToString(rawRequestBody.getBytes());
        
        System.out.println("encodedRequestBody: " + encodedRequestBody);

        // Create the final request body with the "request" field
        String finalRequestBody = String.format("{\"request\":\"%s\"}", encodedRequestBody);

        
        //String xVerify = signRequest(encodedRequestBody) + "###" + SALT_INDEX;
        String xVerify =  generateSHA256Hash(encodedRequestBody + "/pg/v1/pay" + SALT_KEY) + "###" + SALT_INDEX;

        System.out.println("X-VERIFY: " + xVerify);
        // Prepare HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-VERIFY", xVerify);

        // Create HTTP entity with headers and final base64-encoded body
        HttpEntity<String> entity = new HttpEntity<>(finalRequestBody, headers);

        // Use RestTemplate to send the request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(PHONEPE_PAY_API_URL, HttpMethod.POST, entity, String.class);

        // Return the response 
        return response.getBody();  
    }
    

}

