package com.example.zeepwifi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.zeepwifi.dto.AccountsCreateDTO;
import com.example.zeepwifi.mapper.AccountsDTOMapper;
import com.example.zeepwifi.models.Accounts;
import com.example.zeepwifi.services.AccountsService;


@RestController
@RequestMapping("/api/accounts")
public class AccountsController {
    @Autowired
    AccountsService accountsService;

    @Autowired
    AccountsDTOMapper accountsDTOMapper;

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
    
    // Create new account endpoint
    @PostMapping(value = "/addnewaccount", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addAccount(@RequestBody AccountsCreateDTO accountsCreateDTO) {
        Accounts accounts = accountsDTOMapper.accountsCreateDTO(accountsCreateDTO);
        return accountsService.addAccount(accounts);
    }
    
}