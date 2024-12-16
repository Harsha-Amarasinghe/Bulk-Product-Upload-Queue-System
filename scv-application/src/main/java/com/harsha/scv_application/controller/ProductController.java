package com.harsha.scv_application.controller;

import com.harsha.scv_application.entity.ErrorLog;
import com.harsha.scv_application.entity.Product;
import com.harsha.scv_application.service.BulkUploadService;
import com.harsha.scv_application.service.ProductService;
import com.harsha.scv_application.service.StatusNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private BulkUploadService bulkUploadService;

    @Autowired
    private ProductService productService;

    @Autowired
    private StatusNotificationService statusNotificationService;

    @GetMapping("/test/status")
    public String testWebSocket() {
        statusNotificationService.notifyStatus("Test message from backend");
        return "Status sent";
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".csv")) {
            return ResponseEntity.badRequest().body("Invalid CSV file!");
        }
        bulkUploadService.processCSV(file);
        return ResponseEntity.ok("File upload initiated. Check progress in the dashboard.");
    }

    @GetMapping("/success")
    public ResponseEntity<List<Product>> getSuccessfulProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/errors")
    public ResponseEntity<List<ErrorLog>> getErrorLogs() {
        return ResponseEntity.ok(productService.getErrorLogs());
    }

    @GetMapping("/report")
    public ResponseEntity<Resource> downloadReport(@RequestParam String type) {
        statusNotificationService.notifyStatus("Generating report of type: "+type);
        String reportPath = productService.generateCSVReport(type);
        statusNotificationService.notifyStatus("Report generation completed");

        Resource file = new FileSystemResource(reportPath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+type+"-report.csv")
                .body(file);
    }
}
