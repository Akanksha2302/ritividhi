package com.dev.ritividhi.controllers;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ritividhi/healthcheck")
public class HealthCheckController {


    

    @GetMapping
    public ResponseEntity<String> getHealthCheck() {
       
        return ResponseEntity.ok("OK");
    }
}