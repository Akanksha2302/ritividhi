package com.dev.ritividhi.controllers;

import com.dev.ritividhi.services.TransactionDetailService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ritividhi")
public class PaymentCallbackController {

    @Autowired
    private TransactionDetailService transactionDetailService;

    @PostMapping("/callback")
    public ResponseEntity<String> handlePaymentCallback(@RequestBody Map<String, Object> callbackResponse) {
    	transactionDetailService.processPaymentCallback(callbackResponse);
        return ResponseEntity.ok("Payment callback processed successfully");
    }

    @GetMapping("/paymentStatus/{orderId}")
    public ResponseEntity<String> getPaymentStatus(@PathVariable String orderId) {
        try {
            String paymentStatus = transactionDetailService.getPaymentStatus(orderId);
            return ResponseEntity.ok(paymentStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Return 404 with error message
        }
    }
}
