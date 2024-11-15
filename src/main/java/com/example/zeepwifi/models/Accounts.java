package com.example.zeepwifi.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Cacheable
@Entity
@Table(name = "zeep_accounts")
public class Accounts {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public Long id;

    @Column(name = "account_username", nullable = false, unique = true)
    public String accountUsername;

    @Column(name = "first_name", nullable = false, unique = false)
    public String firstName;

    @Column(name = "middle_name", nullable = false, unique = false)
    public String middleName;

    @Column(name = "last_name", nullable = false, unique = false)
    public String lastName;

    @Column(name = "account_password", nullable = false, unique = false)
    public String accountPassword;

    @Column(name = "created_at", nullable = false, unique = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, unique = false)
    public LocalDateTime updatedAt;

}