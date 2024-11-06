package com.dev.ritividhi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "temple")
public class Temple {
	@Id
	private String templeId;
	//@Field("temple_name")
	private String templeName;

	//@Field("temple_location")
	private String templeLocation;
	
	//@Field("temple_description")
	private String description;
	
	//@Field("temple_price")
	private double price;

}
