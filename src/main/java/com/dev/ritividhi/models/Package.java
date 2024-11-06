package com.dev.ritividhi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.util.List;

@Data
@Document(collection = "packages")
public class Package {

    @Id
    private String packageId;
    private String name;
    private double price;
    private String duration;
    private String description;
    private List<String> descriptionPoints;
    @DBRef
    private List<Addon> addOns;			//not in offline puja & online Puja
//    @DBRef
//    private List<BhetOptions> bhetOptions; //not in offline puja & Onine puja
    private String resources;	//present in offline Puja
    }



































































