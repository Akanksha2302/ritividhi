package com.dev.ritividhi.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "participationOptions")
public class ParticipationOption {
	@Id
	private String optionId;
	private String name;
	private List<String> description;		//Online Puja
	//private String description;//Present in temple puja not available in online or offline puja
	private List<String> includes;
	private double price;	//present in Online & Offline Puja not in temple 
}
