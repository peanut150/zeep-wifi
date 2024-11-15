package com.example.zeepwifi.services;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import com.example.zeepwifi.dto.DataLimitDTO;
import com.example.zeepwifi.models.DataLimit;
import com.example.zeepwifi.repositories.DataLimitRepository;

@Service
public class DataLimitService {
    @Autowired
    DataLimitRepository dataLimitRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    private String csrfToken;

    // Retrieve data limit from external API
    public ResponseEntity<?> getDataLimit(Integer client_id, Integer package_id, Pageable pageable) {
        String url = String.format("http://192.168.90.151:8080/data/?client_id=%d&package_id=%d", client_id, package_id);

        try {
            ResponseEntity<DataLimitDTO> response = restTemplate.getForEntity(url, DataLimitDTO.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                DataLimitDTO dataLimitDTO = response.getBody();

                DataLimit dataLimit = new DataLimit();
                dataLimit.setClientID(client_id);
                dataLimit.setPackageID(package_id);
                dataLimit.setCounter(dataLimitDTO.counter);
                dataLimit.setLimitCount(dataLimitDTO.limit_count);
                dataLimit.setLimitType(dataLimitDTO.limit_type);
                dataLimit.setCreatedOn(LocalDateTime.now());
                dataLimit.setLastModified(LocalDateTime.now());

                dataLimitRepository.save(dataLimit);

                this.csrfToken = dataLimitDTO.csrf_token;

                String csrfToken = dataLimitDTO.csrf_token;
                
                return ResponseEntity.ok(new DataLimitDTO(
                    dataLimit.getCounter(),
                    csrfToken,
                    dataLimit.getLimitCount(),
                    dataLimit.getLimitType()
                ));

            } else {
                return new ResponseEntity<>(Collections.singletonMap("message", "Failed to retrieve data"),
                HttpStatus.NOT_FOUND);   
            }

        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("message", "An unexpected error occurred while fetching data"),
            HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add data to external API
    public ResponseEntity<?> addData(
            Integer client_id,
            Integer package_id,
            Integer value) {
        String url = String.format("http://192.168.90.151:8080/data/?client_id=%d&package_id=%d&value=%d", client_id, package_id, value);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-CSRFToken", csrfToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Data successfully updated with value: " + value);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("message", "Failed to update data"), HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("message", "An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}