package com.example.zeepwifi.repositories;

import com.example.zeepwifi.dto.AccountsCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    @Query("SELECT new com.example.zeepwifi.dto.AccountsDTO(a.id, a.accountUsername, a.firstName, a.middleName, a.lastName) FROM Accounts a ORDER BY a.id")
    Page<AccountsDTO> getAllAccounts(Pageable pageable);

    // Method to get account by username (returns AccountsDTO for login purposes)
    @Query("SELECT new com.example.zeepwifi.dto.AccountsCheckDTO(a.accountUsername, a.accountPassword) FROM Accounts a WHERE a.accountUsername = :accountUsername")
    Optional<AccountsCheckDTO> findAccountByUsername(@Param("accountUsername") String accountUsername);

    // Method to update account by id
    @Modifying
    @Query("UPDATE Accounts a " +
            "SET a.firstName=:#{#accounts.firstName}, " +
            "   a.middleName=:#{#accounts.middleName}, " +
            "   a.lastName=:#{#accounts.lastName}, " +
            "   a.accountUsername=:#{#accounts.accountUsername}, " +
            "   a.accountPassword=:#{#accounts.accountPassword} " +
            "WHERE a.id=:id")
    void updateAccount(@Param("id") Long id, @Param("accounts")AccountsCreateDTO accounts);

    // Method to check account login by username and password (returns AccountsCheckDTO)
    @Query("SELECT new com.example.zeepwifi.dto.AccountsCheckDTO(a.accountUsername, a.accountPassword) FROM Accounts a WHERE a.accountUsername = :accountUsername AND a.accountPassword = :accountPassword")
    Optional<AccountsCheckDTO> checkAccountLogin(@Param("accountUsername") String accountUsername, @Param("accountPassword") String accountPassword);

    // Method to check if an account exists by username
    boolean existsByAccountUsername(String accountUsername);
}