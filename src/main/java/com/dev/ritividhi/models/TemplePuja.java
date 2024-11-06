package com.dev.ritividhi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import java.util.List;

@Data
//@Document(collection = "templePuja")
public class TemplePuja extends Temple {
	//@Field("temple_image")
	private List<String> templeImage;

	@DBRef
	//@Field("pujas_temple")
	private List<TemplePujaDetails> pujas; // present in temple puja only
}