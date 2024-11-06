package com.dev.ritividhi.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "contactUs")
public class ContactUs {

	private String fullName;
	private String phone;
	private String email;
	private String comments;
}
