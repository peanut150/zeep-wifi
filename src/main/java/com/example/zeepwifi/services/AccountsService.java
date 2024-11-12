package com.example.zeepwifi.services;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zeepwifi.dto.AccountsCheckDTO;
import com.example.zeepwifi.dto.AccountsDTO;
import com.example.zeepwifi.mapper.AccountsDTOMapper;
import com.example.zeepwifi.models.Accounts;
import com.example.zeepwifi.repositories.AccountsRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service

public class AccountsService {
    
    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    AccountsDTOMapper accountsDTOMapper;

    // Retrieve all accounts
    public ResponseEntity<?> getAllAccounts(Pageable pageable) {
        List<AccountsDTO> accounts = accountsRepository.getAllAccounts(pageable).getContent();

        try {
            if (accounts.isEmpty()) {
                return new ResponseEntity<>(Collections.singletonMap("message", "There are no accounts found"),
                HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(Collections.singletonMap("data", accounts), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("message", "An unexpected error occured"),
            HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieve account by accountUsername
    public ResponseEntity<?> getByAccountUsername(String accountUsername) {
        Optional<AccountsCheckDTO> accounts = accountsRepository.findAccountByUsername(accountUsername);

        try {
            if (accounts.isEmpty()) {
                return new ResponseEntity<>(Collections.singletonMap("message", "User not found"),
                HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(Collections.singletonMap("data", accounts), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("message", "An unexpected error occured"),
            HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Create new account
    public ResponseEntity<?> addAccount(Accounts accounts) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Check if account already exists
            if (accountsRepository.existsByAccountUsername(accounts.getAccountUsername())) {
                response.put("message", "Failed to add account: Account with this username already exists");
                response.put("success", false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Else save account
            accountsRepository.save(accounts);
            response.put("message", "Account added successfully!");
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            response.put("message", "Failed to add account: Duplicate entry for account username or password");
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
