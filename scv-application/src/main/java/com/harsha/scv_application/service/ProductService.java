package com.harsha.scv_application.service;

import com.harsha.scv_application.entity.Product;
import com.harsha.scv_application.entity.ErrorLog;
import com.harsha.scv_application.repository.ProductRepository;
import com.harsha.scv_application.repository.ErrorLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    // Fetch successful products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Fetch error logs
    public List<ErrorLog> getErrorLogs() {
        return errorLogRepository.findAll();
    }

    // Generate CSV Report
    public String generateCSVReport(String type) {
        String reportPath = "report.csv";
        try (FileWriter writer = new FileWriter(reportPath)) {
            if (type.equals("successProducts")){
                writer.append("Product ID,Product Name,Price,Stock,Description,Availability,Uploaded Date\n");
                List<Product> products = productRepository.findAll();

                for (Product product : products) {
                    writer.append(product.getProductId()).append(",")
                            .append(product.getProductName()).append(",")
                            .append(String.valueOf(product.getPrice())).append(",")
                            .append(String.valueOf(product.getStockQuantity())).append(",")
                            .append(product.getDescription()).append(",")
                            .append(String.valueOf(product.getAvailabilityStatus())).append(",")
                            .append(String.valueOf(product.getDateUploaded())).append("\n");
                }
            } else {
                writer.append("Product ID,Product Name,Price,Stock,Description,Availability,Uploaded Date\n");
                List<ErrorLog> errorLogProducts = errorLogRepository.findAll();

                for (ErrorLog errorLogProduct : errorLogProducts) {
                    writer.append(errorLogProduct.getProductId()).append(",")
                            .append(errorLogProduct.getProductName()).append(",")
                            .append(String.valueOf(errorLogProduct.getPrice())).append(",")
                            .append(errorLogProduct.getDescription()).append(",")
                            .append(String.valueOf(errorLogProduct.getErrorMessage())).append(",")
                            .append(String.valueOf(errorLogProduct.getDateLogged())).append("\n");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error generating report: " + e.getMessage());
        }
        return reportPath;
    }
}
