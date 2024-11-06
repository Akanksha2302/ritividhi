package com.dev.ritividhi.models;

import lombok.Data;

@Data
public class PaymentRequest {
	
	private String merchantUserId;
	private String orderId;
    private long amount;

}
