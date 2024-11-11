package com.example.zeepwifi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.zeepwifi.entity.ZeepAccountEntity;

import java.util.Optional;

@Repository
public interface ZeepAccountRepository extends JpaRepository<ZeepAccountEntity, Long> {
    Optional<ZeepAccountEntity> findByAccountUsername(String accountUsername);
}