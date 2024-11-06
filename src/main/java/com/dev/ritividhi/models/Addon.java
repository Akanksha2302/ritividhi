package com.dev.ritividhi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "addons")
public class Addon {

    @Id
    private String addonId;
    private String name;
    private double price;
    private String description;
}
