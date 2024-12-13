package com.harsha.scv_application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productId;
    private String errorMessage;
    private LocalDateTime dateLogged;

    public ErrorLog(String productId, String errorMessage, LocalDateTime dateLogged) {
        this.productId = productId;
        this.errorMessage = errorMessage;
        this.dateLogged = dateLogged;
    }
}

