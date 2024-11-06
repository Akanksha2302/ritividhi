package com.dev.ritividhi.models;

import lombok.Data;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class HomePuja {
	@DBRef(lazy = false)
    private List<Package> packages;
}

