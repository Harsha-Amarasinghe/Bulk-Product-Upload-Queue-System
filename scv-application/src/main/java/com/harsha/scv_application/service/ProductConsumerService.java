package com.harsha.scv_application.service;

import com.harsha.scv_application.entity.Product;
import com.harsha.scv_application.entity.ErrorLog;
import com.harsha.scv_application.repository.ProductRepository;
import com.harsha.scv_application.repository.ErrorLogRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductConsumerService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ExternalAPIService externalAPIService;

    @RabbitListener(queues = "productQueue")
    public void processProduct(Product product) {
        try {
            boolean isAvailable = externalAPIService.checkAvailability(product.getProductId());
            product.setAvailabilityStatus(isAvailable);
            product.setDateUploaded(LocalDateTime.now());

            if (isAvailable) {
                productRepository.save(product);
            } else {
                logError(product, "Product unavailable");
            }
        } catch (Exception e) {
            logError(product, "Error processing product: " + e.getMessage());
        }
    }

    private void logError(Product product, String errorMessage) {
        ErrorLog errorLog = new ErrorLog(product.getProductId(), product.getProductName(),
                product.getPrice(), product.getDescription(),
                errorMessage, LocalDateTime.now());
        errorLogRepository.save(errorLog);
    }
}
