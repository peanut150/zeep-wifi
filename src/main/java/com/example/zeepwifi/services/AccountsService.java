package com.example.zeepwifi.services;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.zeepwifi.dto.AccountsCreateDTO;
import com.example.zeepwifi.utils.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zeepwifi.dto.AccountsCheckDTO;
import com.example.zeepwifi.dto.AccountsDTO;
import com.example.zeepwifi.models.Accounts;
import com.example.zeepwifi.repositories.AccountsRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Service

public class AccountsService {
    
    @Autowired
    AccountsRepository accountsRepository;

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
            return new ResponseEntity<>(Collections.singletonMap("message", "An unexpected error occurred"),
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
            return new ResponseEntity<>(Collections.singletonMap("message", "An unexpected error occurred"),
            HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update account
    @Transactional
    public ResponseEntity<?> updateAccount(Long id, AccountsCreateDTO accountsCreateDTO) {
        try {
            Optional<Accounts> accounts = accountsRepository.findById(id);

            if (accounts.isEmpty()) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Failed to update account"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

            accountsCreateDTO.accountPassword = new Password().hash(accountsCreateDTO.accountPassword);
            accountsRepository.updateAccount(id, accountsCreateDTO);
            return new ResponseEntity<>(Collections.singletonMap("message", "Successfully updated account"),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("message", "An unexpected error occurred"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete account
    public ResponseEntity<?> deleteAccount(Long id) {
        try {
            Optional<Accounts> accounts = accountsRepository.findById(id);

            if (accounts.isEmpty()) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Failed to delete account"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

            accountsRepository.deleteById(id);
            return new ResponseEntity<>(Collections.singletonMap("message", "Successfully deleted account"),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("message", "An unexpected error occurred"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
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
     */
}
