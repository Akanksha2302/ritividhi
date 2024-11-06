package com.dev.ritividhi.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "bhetOptions")
public class BhetOptions {
	@Id
	private String bhetId;
	private String name;
	private String description;
	private double price;
}
