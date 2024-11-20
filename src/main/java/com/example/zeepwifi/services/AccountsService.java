package com.example.zeepwifi.services;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.zeepwifi.dto.RegisterDto;
import com.example.zeepwifi.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zeepwifi.dto.LoginDto;
import com.example.zeepwifi.dto.AccountsDto;
import com.example.zeepwifi.models.Accounts;
import com.example.zeepwifi.repositories.AccountsRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Service

public class AccountsService {
    
    @Autowired
    private AccountsRepository accountsRepository;

    // Retrieve all accounts
    public ResponseEntity<?> getAllAccounts(Pageable pageable) {
        List<AccountsDto> accounts = accountsRepository.getAllAccounts(pageable).getContent();

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

    // Retrieve account by account username
    public ResponseEntity<?> getByAccountUsername(String accountUsername) {
        Optional<LoginDto> accounts = accountsRepository.findAccountByUsername(accountUsername);

        try {
            if (accounts.isEmpty()) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Account not found"),
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
    public ResponseEntity<?> updateAccount(Long id, RegisterDto registerDto) {
        try {
            Optional<Accounts> accounts = accountsRepository.findById(id);

            if (accounts.isEmpty()) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Failed to update account"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

            registerDto.accountPassword = new PasswordUtil().hash(registerDto.accountPassword);
            accountsRepository.updateAccount(id, registerDto);
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
    
}
