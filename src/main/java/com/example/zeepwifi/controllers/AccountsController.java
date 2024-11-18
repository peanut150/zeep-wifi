package com.example.zeepwifi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.zeepwifi.dto.RegisterDto;
import com.example.zeepwifi.services.AccountsService;


@RestController
@RequestMapping("/api/accounts")
public class AccountsController {
    @Autowired
    AccountsService accountsService;

    // Retrieve all accounts endpoint
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> getAllAccounts(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return accountsService.getAllAccounts(pageable);
    }

    // Retrieve account by username endpoint
    @GetMapping(value = "/{accountUsername}", produces = "application/json")
    public ResponseEntity<?> getAccountByUsername(@PathVariable String accountUsername) {
        return accountsService.getByAccountUsername(accountUsername);
    }

    // Update account endpoint
    @PutMapping(value = {"", "/"}, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateAccount(@RequestParam Long id, @RequestBody RegisterDto accountsCreateDTO) {
        return accountsService.updateAccount(id, accountsCreateDTO);
    }

    // Delete account
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        return accountsService.deleteAccount(id);
    }
}