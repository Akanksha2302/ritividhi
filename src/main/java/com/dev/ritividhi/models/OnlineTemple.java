package com.dev.ritividhi.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection="onlineTemple")
public class OnlineTemple extends Temple{
	
	@DBRef
    private List<Addon> addons;
	@DBRef
    private List<ParticipationOption> participationOptions;
}
