package com.example.zeepwifi.controllers;

import java.util.Collections;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.zeepwifi.dto.AccountsCreateDTO;
import com.example.zeepwifi.mapper.AccountsDTOMapper;
import com.example.zeepwifi.models.Accounts;
import com.example.zeepwifi.models.UserLogin;
import com.example.zeepwifi.services.AuthService;
import com.example.zeepwifi.utils.JwtUtil;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AccountsDTOMapper accountsDTOMapper;

    @PostMapping(value = "/signup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> signup(@RequestBody AccountsCreateDTO accountsCreateDTO) {
        try {
            Accounts accounts = accountsDTOMapper.accountsCreateDTO(accountsCreateDTO);
            return authService.signup(accounts);
        } catch (Exception e) {
            return new ResponseEntity<>(
                Collections.singletonMap("message", "An unexpected error occurred"),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin) {
        try {
            return authService.login(userLogin);
        } catch (Exception e) {
            return new ResponseEntity<>(
                Collections.singletonMap("message", "An unexpected error occurred"),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader HashMap<String, String> header) {
        try {
            return authService.refresh(header.get("refreshtoken"));
        } catch (Exception e) {
            return new ResponseEntity<>(
                Collections.singletonMap("message", "An unexpected error occurred"),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
