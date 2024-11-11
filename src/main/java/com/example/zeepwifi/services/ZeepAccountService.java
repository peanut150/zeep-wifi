package com.example.zeepwifi.services;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zeepwifi.entity.ZeepAccountEntity;
import com.example.zeepwifi.repositories.ZeepAccountRepository;

import org.springframework.dao.DataIntegrityViolationException;

@Service

public class ZeepAccountService {
    
    @Autowired
    private ZeepAccountRepository zeepaccountRepository;

    // Retrieve all accounts
    public List<ZeepAccountEntity> getAccounts() {
        return zeepaccountRepository.findAll();
    }

    // Retrieve account by accountUsername
    public Optional<ZeepAccountEntity> getAccountByUsername(String accountUsername) {
        return zeepaccountRepository.findByAccountUsername(accountUsername);
    }
    
    // Create new account
    public Map<String, Object> addAccount(ZeepAccountEntity zeepaccountEntity) {
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
