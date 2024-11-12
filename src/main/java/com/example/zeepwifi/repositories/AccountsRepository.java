package com.example.zeepwifi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.zeepwifi.dto.AccountsCheckDTO;
import com.example.zeepwifi.dto.AccountsDTO;
import com.example.zeepwifi.models.Accounts;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    // Method to get all accounts with pagination
    @Query("SELECT new com.example.zeepwifi.dto.AccountsDTO(u.id, u.accountUsername) FROM Accounts u ORDER BY u.id")
    Page<AccountsDTO> getAllAccounts(Pageable pageable);

    // Method to get account by username (returns AccountsDTO for login purposes)
    @Query("SELECT new com.example.zeepwifi.dto.AccountsCheckDTO(u.accountUsername, u.accountPassword) FROM Accounts u WHERE u.accountUsername = :accountUsername")
    Optional<AccountsCheckDTO> findAccountByUsername(@Param("accountUsername") String accountUsername);

    // Method to check account login by username and password (returns AccountsCheckDTO)
    @Query("SELECT new com.example.zeepwifi.dto.AccountsCheckDTO(u.accountUsername, u.accountPassword) FROM Accounts u WHERE u.accountUsername = :accountUsername AND u.accountPassword = :accountPassword")
    Optional<AccountsCheckDTO> checkAccountLogin(@Param("accountUsername") String accountUsername, @Param("accountPassword") String accountPassword);

    // Method to check if an account exists by username
    boolean existsByAccountUsername(String accountUsername);
}