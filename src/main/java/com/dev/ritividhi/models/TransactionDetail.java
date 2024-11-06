package com.dev.ritividhi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "transactionDetails")
public class TransactionDetail {
    
    @Id
    private String id; 

    private String orderId; 
    
    private boolean success;
    private String code;
    private String message;
    
    private String merchantId;
    private String merchantTransactionId;
    private String transactionId;
    private double amount;
    private String state;
    private String responseCode;
    private String paymentMode;

    //private PaymentInstrument paymentInstrument;
    
//    @Data
//    public static class PaymentInstrument {
//        private String type; // e.g., UPI, Card, etc.
//    }
}
