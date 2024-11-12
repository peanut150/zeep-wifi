package com.example.zeepwifi.services;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zeepwifi.models.Accounts;
import com.example.zeepwifi.repositories.AccountsRepository;

import org.springframework.dao.DataIntegrityViolationException;

@Service

public class AccountsService {
    
    @Autowired
    private AccountsRepository zeepaccountRepository;

    // Retrieve all accounts
    public List<Accounts> getAccounts() {
        return zeepaccountRepository.findAll();
    }

    // Retrieve account by accountUsername
    public Optional<Accounts> getAccountByUsername(String accountUsername) {
        return zeepaccountRepository.findByAccountUsername(accountUsername);
    }
    
    // Create new account
    public Map<String, Object> addAccount(Accounts zeepaccountEntity) {
        Map<String, Object> response = new HashMap<>();
        try {
            zeepaccountRepository.save(zeepaccountEntity);
            response.put("message", "Account added successfully!");
            response.put("success", true);
        } catch (DataIntegrityViolationException e) {
            response.put("message", "Failed to add account: Duplicate entry for accountUsername or accountPassword.");
            response.put("success", false);
        } catch (Exception e) {
            response.put("message", "An error occurred while adding the account: " + e.getMessage());
            response.put("success", false);
        }
        return response;
    }
}
