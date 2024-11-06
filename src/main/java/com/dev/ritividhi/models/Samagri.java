package com.dev.ritividhi.models;
import java.util.List;
import lombok.Data;

@Data
public class Samagri {
    private List<String> puja;
    private List<String> havan;
    private List<String> yajman;
    private double yajmanSamagriPrice;

  

}

