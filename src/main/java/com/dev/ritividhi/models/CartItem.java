package com.dev.ritividhi.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Data
@Document(collection = "cartItems")
public class CartItem {

    private String cartItemId;
    @DBRef
    private Package selectedPackage;
    @DBRef
    private List<Addon> addons;
    private LocalDate preferredDate;
    private LocalTime preferredTime;
    private String notes;
	
}
