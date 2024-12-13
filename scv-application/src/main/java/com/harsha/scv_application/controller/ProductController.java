package com.harsha.scv_application.controller;

import com.harsha.scv_application.service.BulkUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private BulkUploadService bulkUploadService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".csv")) {
            return ResponseEntity.badRequest().body("Invalid CSV file!");
        }
        bulkUploadService.processCSV(file);
        return ResponseEntity.ok("File upload initiated. Check progress in the dashboard.");
    }
}
