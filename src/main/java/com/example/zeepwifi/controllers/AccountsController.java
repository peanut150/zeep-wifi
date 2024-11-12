package com.example.zeepwifi.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import com.example.zeepwifi.models.Accounts;
import com.example.zeepwifi.services.AccountsService;


@RestController
public class AccountsController {
    
    @Autowired
    private AccountsService zeepaccountService;

    // Retrieve all accounts route
    @GetMapping("/getAccounts")
    public ResponseEntity<List<Accounts>> getAccounts() {
        List<Accounts> accounts = zeepaccountService.getAccounts();
        return ResponseEntity.ok(accounts);
    }

    // Retrieve account by accountUsername route
    @GetMapping("/getAccountByUsername")
    public ResponseEntity<Accounts> getAccountByUsername(@RequestParam String accountUsername) {
        Optional<Accounts> account = zeepaccountService.getAccountByUsername(accountUsername);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Create new account route
    @Async("asyncExecutor")
    @PostMapping("/addnewaccount")
    public ResponseEntity<Map<String, Object>> addAccount(@RequestBody Accounts account) {
        Map<String, Object> response = zeepaccountService.addAccount(account);
        if (Boolean.TRUE.equals(response.get("success"))) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
}