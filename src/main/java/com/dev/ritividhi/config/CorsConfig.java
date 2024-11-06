package com.dev.ritividhi.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    
    private final List<String> setAllowedOriginPatterns = Arrays.asList(
            "http://localhost*",
            "https://*.ritividhi.com",
            "https://ritividhi.com"
    );

    // Source: https://stackoverflow.com/a/45685909
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Allow all origins
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(setAllowedOriginPatterns);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/v2/api-docs", config);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}