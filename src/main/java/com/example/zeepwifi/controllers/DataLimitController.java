package com.example.zeepwifi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.zeepwifi.services.DataLimitService;

@RestController
@RequestMapping("/data")
@CrossOrigin(origins = "*")
public class DataLimitController {
    @Autowired
    private DataLimitService dataLimitService;

    // Retrieve data limit
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> getDataLimit(
            @RequestParam Integer client_id,
            @RequestParam Integer package_id,
            @PageableDefault(size = 10, page = 0)Pageable pageable) {
        
        return dataLimitService.getDataLimit(client_id, package_id, pageable);
    }

    // Add data
    @PostMapping("/")
    public ResponseEntity<?> addData(
            @RequestParam Integer client_id,
            @RequestParam Integer package_id,
            @RequestParam Integer value) {
        
        return dataLimitService.addData(client_id, package_id, value);
    }
}