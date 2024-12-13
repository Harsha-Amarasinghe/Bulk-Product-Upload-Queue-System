package com.harsha.scv_application.service;

import com.harsha.scv_application.entity.Product;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

@Service
public class BulkUploadService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void processCSV(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true; // Skip header row
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                Product product = parseLine(line);
                if (product != null) {
                    rabbitTemplate.convertAndSend("productQueue", product);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
    }

    private Product parseLine(String line) {
        try {
            String[] fields = line.split(",");
            if (fields.length != 5) {
                throw new IllegalArgumentException("Invalid CSV format");
            }
            return new Product(
                    fields[0],
                    fields[1],
                    Double.parseDouble(fields[2]),
                    Integer.parseInt(fields[3]),
                    fields[4],
                    null,
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            System.err.println("Error parsing line: " + line + " - " + e.getMessage());
            return null;
        }
    }
}
