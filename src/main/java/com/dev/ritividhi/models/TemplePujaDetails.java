package com.dev.ritividhi.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "templePuja")
public class TemplePujaDetails extends Puja {
  
    private String description;
    private double price;
    private List<String> samagri;
    private List<ParticipationOption> participationOptions;
    @DBRef(lazy = false)
    private List<Package> packages;	
   // private String templeId;
}
