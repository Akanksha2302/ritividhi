package com.dev.ritividhi.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="liveEvents")
public class LiveEvents {
	
	 private List<OnlineTemple> availableTemples;
}
