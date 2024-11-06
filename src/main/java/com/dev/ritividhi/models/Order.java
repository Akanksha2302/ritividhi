package com.dev.ritividhi.models;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "orders")
public class Order {
	
	@Id
    private String orderId;
    private String userId;
    private Date orderDate;
    private String pujaId;
    @DBRef
    private Package selectedPackage;
    private Date selectedDate;
    private LocalTime selectedTime;
    @DBRef
    private Address selectedAddress;
    private String specialRequirement;
    private List<Addon> addOns;		//this is present in packages
    private Samagri samagri;
    private double totalPrice;
    private String status;
    private String paymentMethod;
    private String paymentStatus;
    private List<String> panditName;
    private Date pujaDate;
    private List<PujaDetails> pujaDetails;
    private String PujaType;  //temple, online, offline
}
