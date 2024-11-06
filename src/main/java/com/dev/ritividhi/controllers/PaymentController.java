package com.dev.ritividhi.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.ritividhi.models.PaymentRequest;
import com.dev.ritividhi.services.PaymentService;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ritividhi/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<String> initiatePayment(@RequestBody PaymentRequest request) {
        try {
            String url = paymentService.initiatePayment(request);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error initiating payment: " + e.getMessage());
        }
    }


}
