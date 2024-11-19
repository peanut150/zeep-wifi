package com.example.zeepwifi.controllers;

import com.example.zeepwifi.dto.DataLimitDto;
import com.example.zeepwifi.services.DataLimitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataLimitController {

    @Autowired
    private DataLimitService dataLimitService;

    @GetMapping("/api/add-data")
    public ResponseEntity<DataLimitDto> getCsrfToken(
            @RequestParam("client_id") Integer clientId,
            @RequestParam("package_id") Integer packageId,
            @RequestParam("value") Integer value) {
        try {
            return ResponseEntity.ok(dataLimitService.getCsrfToken(clientId, packageId, value));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new DataLimitDto("error", "An unexpected error occurred", null));
    }
}
}
