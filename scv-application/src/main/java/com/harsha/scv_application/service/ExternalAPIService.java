package com.harsha.scv_application.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Service
public class ExternalAPIService {
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean checkAvailability(String productId) {
        try {
            // Simulate external API behavior for testing
            return productId.toUpperCase().startsWith("P"); // Mock logic for testing
        } catch (Exception e) {
            System.err.println("External API error: " + e.getMessage());
            return false;
        }
    }
}

