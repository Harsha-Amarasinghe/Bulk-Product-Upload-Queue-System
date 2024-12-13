package com.harsha.scv_application.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String productId;
    private String productName;
    private Double price;
    private Integer stockQuantity;
    private String description;
    private Boolean availabilityStatus;
    private LocalDateTime dateUploaded;
}

