package com.example.zeepwifi.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.zeepwifi.dto.AccountsCreateDTO;
import com.example.zeepwifi.models.Accounts;
import com.example.zeepwifi.utils.Password;

@Service
public class AccountsDTOMapper {
    public Accounts accountsCreateDTO(AccountsCreateDTO accountsCreateDTO) {
        Accounts accounts = new Accounts();

        accounts.setAccountUsername(accountsCreateDTO.accountUsername);
        accounts.setFirstName(accountsCreateDTO.firstName);
        accounts.setMiddleName(accountsCreateDTO.middleName);
        accounts.setLastName(accountsCreateDTO.lastName);
        accounts.setAccountPassword(new Password().hash(accountsCreateDTO.accountPassword));
        accounts.setCreatedAt(LocalDateTime.now());
        accounts.setUpdatedAt(LocalDateTime.now());

        return accounts;
    }
}
