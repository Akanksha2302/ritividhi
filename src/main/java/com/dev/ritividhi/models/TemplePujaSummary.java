////package com.dev.ritividhi.models;
////
////import java.util.List;
////
////import org.springframework.data.annotation.Id;
////import org.springframework.data.mongodb.core.mapping.Document;
////
////import lombok.Data;
////
////@Data
////@Document(collection = "templePujaSummary")
////public class TemplePujaSummary {
//////	private String templeId;
//////	private String templeName;
//////	private String templeDescription;
//////	private List<String> templeImage;
//////	private TemplePuja
//////	TemplePujaDetails templePuja;
////
//	@Id
//	private String templeId;
//	private String templeName;
//	private String templeLocation;
//	private String description;
//	private double price;//Temple.class
//	
//	private List<String> templeImage;	//TemplePuja.class
//
//
//	private String pujaDescription;	//templePujaDetails.class
//	private double pujaPrice;
//}
//
//package com.dev.ritividhi.models;
//
//import java.util.List;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.DBRef;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//
//import lombok.Data;
//
//@Data
//@Document(collection = "templePujaSummary")
//public class TemplePujaSummary extends TemplePuja {
////	@Id
////	private String summaryid;
//
//	private String templeId;
//
////	@Field("temple_summary_name")
//	private String templeName;
//
//	// @Field("temple_summary_location")
//	private String templeLocation;
//
//	// @Field("temple_summary_description")
//	private String description;
//
//	// @Field("temple_summary_price")
//	private double price;
//
//	// @Field("temple_image_summary") // Different unique name
//	private List<String> templeImage; // If images are stored as separate documents, otherwise keep it as List<String>
//
//	// @Field("temple_summary_pujas")
//	private List<TemplePujaDetails> pujas;
////	@DBRef
////	private Temple temple; // Reference to the Temple document
//
////	@DBRef
////	private TemplePujaDetails templePuja; // Reference to TemplePujaDetails document
//
////	private String pujaId; // Reference to puja class
////	private String name;
////	private String significance;
////
////	private String pujaDescription; // Description from templePujaDetails
////	private double pujaPrice;
//}


package com.dev.ritividhi.models;

import java.util.List;

import lombok.Data;

@Data
public class TemplePujaSummary {
    private String templeId;
    private String templeName;
    private String templeLocation;
    private String description;
    private double price;
    private List<String> templeImage;
    
    private List<TemplePujaSummaryDetails> pujaDetails;
    
   
}

