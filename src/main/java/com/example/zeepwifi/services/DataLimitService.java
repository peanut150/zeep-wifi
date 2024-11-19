package com.example.zeepwifi.services;

import com.example.zeepwifi.dto.DataLimitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DataLimitService {

    private static final Logger logger = LoggerFactory.getLogger(DataLimitService.class);

    @Autowired
    private RestTemplate restTemplate;

    public DataLimitDto getCsrfToken(Integer clientId, Integer packageId, Integer value) {
        String getUrl = String.format("http://192.168.90.151:8080/data/?client_id=%s&package_id=%s", clientId, packageId);
    
        try {
            logger.info("Calling Python API with URL: {}", getUrl);
            ResponseEntity<String> response = restTemplate.exchange(getUrl, HttpMethod.GET, null, String.class);
    
            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                if (responseBody == null || responseBody.isEmpty()) {
                    logger.error("Empty response body from Python API.");
                    return new DataLimitDto("error", "Empty response body from Python API", null);
                }
    
                JsonNode jsonResponse = new ObjectMapper().readTree(responseBody);
                String csrfToken = jsonResponse.path("csrf_token").asText();
                double limitCount = jsonResponse.path("limit_count").asDouble();
                String limitType = jsonResponse.path("limit_type").asText();
    
                if (csrfToken == null || csrfToken.isEmpty()) {
                    logger.error("CSRF token is missing in the response!");
                    return new DataLimitDto("error", "CSRF token is missing in the response", null);
                }
    
                logger.info("Successfully retrieved CSRF token: {}", csrfToken);
    
                String sessionCookie = getSessionCookie(response.getHeaders());
                if (sessionCookie == null || sessionCookie.isEmpty()) {
                    logger.error("Session cookie missing in the response headers!");
                    return new DataLimitDto("error", "Session cookie missing in the response", null);
                }
    
                DataLimitDto postResponse = triggerPostRequest(clientId, packageId, value, csrfToken, sessionCookie);
                return new DataLimitDto("success", "CSRF token retrieved and POST request triggered successfully", postResponse.getData());
            } else {
                logger.error("Received non-2xx response: {}", response.getStatusCode());
                return new DataLimitDto("error", "Non-2xx response from Python API", null);
            }
        } catch (Exception e) {
            logger.error("Error while fetching CSRF token for client_id: {} and package_id: {}", clientId, packageId, e);
            return new DataLimitDto("error", "Error retrieving CSRF token", e.getMessage());
        }
    }

    private String getSessionCookie(HttpHeaders headers) {
        for (String cookie : headers.get("Set-Cookie")) {
            if (cookie.startsWith("session=")) {
                return cookie.substring("session=".length(), cookie.indexOf(";"));
            }
        }
        return null;
    }

    private DataLimitDto triggerPostRequest(Integer clientId, Integer packageId, Integer value, String csrfToken, String sessionCookie) {
        try {
            String postUrl = String.format("http://192.168.90.151:8080/data/?client_id=%s&package_id=%s&value=%s", clientId, packageId, value);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-CSRFToken", csrfToken);
            headers.add("Cookie", "X-CSRFToken=" + csrfToken);
            headers.add("Cookie", "session=" + sessionCookie);
            
            logger.info("Sending POST request with CSRF token and session cookie: {}", csrfToken);
        
            String postData = String.format("{\"client_id\": \"%s\", \"package_id\": \"%s\", \"value\": \"%s\"}", clientId, packageId, value);
            HttpEntity<String> entity = new HttpEntity<>(postData, headers);
        
            ResponseEntity<String> postResponse = restTemplate.exchange(postUrl, HttpMethod.POST, entity, String.class);
        
            String responseBody = postResponse.getBody();
            if (responseBody != null) {
                if (responseBody.contains("CSRF session token is missing")) {
                    logger.error("CSRF session token is missing in the POST request!");
                    return new DataLimitDto("error", "CSRF session token is missing in the POST request", responseBody);
                }
                logger.info("POST request successful with response: {}", responseBody);
                return new DataLimitDto("success", "POST request was successful", responseBody);
            } else {
                logger.error("Empty response body from POST request!");
                return new DataLimitDto("error", "Empty response body from POST request", null);
            }
        } catch (Exception e) {
            logger.error("Error while sending POST request for client_id: {}, package_id: {}, value: {}", clientId, packageId, value, e);
            return new DataLimitDto("error", "Error sending POST request", e.getMessage());
        }
    }
}
