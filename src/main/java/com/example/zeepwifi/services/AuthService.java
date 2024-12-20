package com.example.zeepwifi.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.zeepwifi.dto.LoginDto;
import com.example.zeepwifi.dto.RegisterDto;
import com.example.zeepwifi.models.Accounts;
import com.example.zeepwifi.models.JwtClaim;
import com.example.zeepwifi.models.JwtType;
import com.example.zeepwifi.models.UserLogin;
import com.example.zeepwifi.repositories.AccountsRepository;
import com.example.zeepwifi.utils.JwtUtil;
import com.example.zeepwifi.utils.PasswordUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private AccountsRepository accountsRepository;

    public ResponseEntity<?> signup(RegisterDto registerDto) {
        try {
            Accounts newAccount = new Accounts();
            newAccount.setAccountUsername(registerDto.getAccountUsername());
            newAccount.setFirstName(registerDto.getFirstName());
            newAccount.setMiddleName(registerDto.getMiddleName());
            newAccount.setLastName(registerDto.getLastName());
            newAccount.setAccountPassword(passwordUtil.hash(registerDto.getAccountPassword()));

            accountsRepository.save(newAccount);

            return new ResponseEntity<>(Collections.singletonMap("message", "Successfully created user"),
            HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", "Username is already in use"),
            HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> login(UserLogin userLogin) {
        Optional<LoginDto> accounts = accountsRepository.findAccountByUsername(userLogin.accountUsername);

        if (accounts.isEmpty() || (!passwordUtil.verify(userLogin.accountPassword, accounts.get().accountPassword))) {
            return new ResponseEntity<>(Collections.singletonMap("message", "Incorrect username and/or password"), HttpStatus.NOT_FOUND);
        }

        final JwtClaim claim = new JwtClaim();
        claim.setAccountUsername(accounts.get().accountUsername);

        final String accessToken = jwtUtil.generateToken(JwtType.Access, claim);
        final String refreshToken = jwtUtil.generateToken(JwtType.Refresh, claim);

        Optional<HashMap<String, String>> map = Optional.of(new HashMap<>());
        map.get().put("accessToken", accessToken);
        map.get().put("refreshToken", refreshToken);

        return new ResponseEntity<>(Collections.singletonMap("data", map), HttpStatus.OK);
    }

    public ResponseEntity<?> refresh(String refreshToken) {
        if (!jwtUtil.verifyToken(refreshToken)) {
            return new ResponseEntity<>(Collections.singletonMap("message", "Access token is expired and/or invalid"), HttpStatus.UNAUTHORIZED);
        }

        ObjectMapper map = new ObjectMapper();
        map.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JwtClaim claim = map.convertValue(jwtUtil.extractAllClaims(refreshToken).get("data"), JwtClaim.class);
        System.out.println(claim);

        return new ResponseEntity<>(
            Collections.singletonMap("accessToken", jwtUtil.generateToken(JwtType.Access, claim)),
            HttpStatus.OK);
    }
}
