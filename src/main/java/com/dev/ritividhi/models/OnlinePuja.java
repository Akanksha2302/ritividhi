package com.dev.ritividhi.models;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="onlinePuja")
public class OnlinePuja extends Puja {

		private List<String> type;
	    private List<String> category;
	    private List<String> god;
	    private List<String> mode; // "online" or "offline"
	    private String about;
	    private String experience;
	    private LiveEvents liveEvents;
}
