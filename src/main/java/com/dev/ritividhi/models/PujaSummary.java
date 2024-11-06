package com.dev.ritividhi.models;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "pujaSummary")
public class PujaSummary {

	@Id
	private String pujaSummaryId;
	private String pujaId;
	private String name;
	private List<String> image;
	private List<String> category;
	private List<String> type; 
	private List<String> god;
	private String zipcode;
	private double price;
	private String about;	//description
}
//temple name, descr
//puja related, temple img, 
//
//online events- at whcih temple- event name,date,img