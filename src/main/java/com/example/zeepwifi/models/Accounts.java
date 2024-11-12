package com.example.zeepwifi.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Cacheable
@Data
@Entity
@Table(name = "zeep_accounts")
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public Long id;

    @Column(name = "account_username", nullable = false, unique = true)
    public String accountUsername;

    @Column(name = "account_password", nullable = false, unique = false)
    public String accountPassword;
    
    @Column(name = "created_at", nullable = false, unique = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, unique = false)
    public LocalDateTime updatedAt;

}