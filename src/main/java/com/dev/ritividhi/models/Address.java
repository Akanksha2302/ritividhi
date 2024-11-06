package com.dev.ritividhi.models;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Address {
	
	   @Id
	    private String id;
	    private String street;
	    private String city;
	    private String state;
	    private String zipCode;
	    private String country;
}
