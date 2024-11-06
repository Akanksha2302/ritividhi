package com.dev.ritividhi.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "onlineEvents")
public class OnlineEvents {
	
	private String eventName;
	private List<String> image;
	private Date eventDate;
	private String templeId;
	private String templeName;
	//private String templeLocation;
	private String description;
	private double price;
	private List<String> templeImage;
}
