package com.example.zeepwifi.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.zeepwifi.dto.RegisterDto;
import com.example.zeepwifi.services.AccountsService;


@RestController
@RequestMapping("/api/accounts")
public class AccountsController {
    @Autowired
    private AccountsService accountsService;

    // Retrieve all accounts endpoint
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> getAllAccounts(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            return accountsService.getAllAccounts(pageable);
        } catch (Exception e) {
            return new ResponseEntity<>(
                Collections.singletonMap("message", "An unexpected error occurred"),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieve account by username endpoint
    @GetMapping(value = "/{accountUsername}", produces = "application/json")
    public ResponseEntity<?> getAccountByUsername(@PathVariable String accountUsername) {
        try {
            return accountsService.getByAccountUsername(accountUsername);
        } catch (Exception e) {
            return new ResponseEntity<>(
                Collections.singletonMap("message", "An unexpected error occurred"),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update account endpoint
    @PutMapping(value = {"", "/"}, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateAccount(@RequestParam Long id, @RequestBody RegisterDto accountsCreateDTO) {
        try {
            return accountsService.updateAccount(id, accountsCreateDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(
                Collections.singletonMap("message", "An unexpected error occurred"),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete account endpoint
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        try {
            return accountsService.deleteAccount(id);
        } catch (Exception e) {
            return new ResponseEntity<>(
                Collections.singletonMap("message", "An unexpected error occurred"),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}