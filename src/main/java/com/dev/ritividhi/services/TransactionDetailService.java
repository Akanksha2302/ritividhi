package com.dev.ritividhi.services;

import com.dev.ritividhi.models.Order;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.dev.ritividhi.models.TransactionDetail;
import com.dev.ritividhi.repository.TransactionDetailRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;

@Service
public class TransactionDetailService {

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;
    
    @Autowired
    private OrderService orderService;

    @SuppressWarnings("unchecked")
	public void processPaymentCallback(Map<String, Object> callbackResponse) {
        try {
            System.out.println("callbackResponse: " + callbackResponse);

            String base64Response = (String) callbackResponse.get("response");
            byte[] decodedBytes = Base64.getDecoder().decode(base64Response);
            String decodedJson = new String(decodedBytes, StandardCharsets.UTF_8);

            System.out.println("Decoded JSON: " + decodedJson);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> decodedResponseMap = objectMapper.readValue(decodedJson, Map.class);
            
            

            Boolean success = (Boolean) decodedResponseMap.get("success");
            String code = (String) decodedResponseMap.get("code");
            String message = (String) decodedResponseMap.get("message");
            Map<String, Object> data = (Map<String, Object>) decodedResponseMap.get("data");
            String merchantTransactionId = (String) data.get("merchantTransactionId");

            TransactionDetail transactionDetail = transactionDetailRepository.findByMerchantTransactionId(merchantTransactionId);
            
            if (transactionDetail == null) {
                System.out.println("No transaction found for merchantTransactionId: " + merchantTransactionId);
                return; 
            }
            
            transactionDetail.setSuccess(success);
            transactionDetail.setCode(code);
            transactionDetail.setMessage(message);

            transactionDetail.setMerchantId((String) data.get("merchantId"));
            transactionDetail.setMerchantTransactionId((String) data.get("merchantTransactionId"));
            transactionDetail.setTransactionId((String) data.get("transactionId"));
            transactionDetail.setAmount(Double.parseDouble(data.get("amount").toString()));
            transactionDetail.setState((String) data.get("state"));
            transactionDetail.setResponseCode((String) data.get("responseCode"));

            Map<String, Object> paymentInstrument = (Map<String, Object>) data.get("paymentInstrument");
            transactionDetail.setPaymentMode((String) paymentInstrument.get("type"));

            transactionDetailRepository.save(transactionDetail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   
	


	public String getPaymentStatus(String orderId) {
        TransactionDetail transactionDetail = transactionDetailRepository.findByOrderId(orderId);
         String status = "pending";
         Optional<Order> order = orderService.getOrderById(orderId);
        if (transactionDetail != null) {
            if ("PAYMENT_SUCCESS".equals(transactionDetail.getCode())) {
                 status ="Payment Status: Success";
            } else {
                status = "Payment Status: Failed";
            }
            order.get().setStatus(status);
            orderService.updateOrder(order);
           
        }
        return status;
    }


	
}
