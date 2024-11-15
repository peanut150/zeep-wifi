package com.example.zeepwifi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.zeepwifi.dto.DataLimitDTO;
import com.example.zeepwifi.models.DataLimit;

@Repository
public interface DataLimitRepository extends JpaRepository<DataLimit, Long> {
    // Method to get data
    @Query("SELECT new com.example.zeepwifi.dto.DataLimitDTO(d.counter, d.limit_count, d.limit_type) FROM DataLimit d")
    Page<DataLimitDTO> getDataLimit(Pageable pageable);
}
