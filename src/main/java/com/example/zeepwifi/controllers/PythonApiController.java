package com.example.zeepwifi.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
public class PythonApiController {

    private static final Logger logger = LoggerFactory.getLogger(PythonApiController.class);

    @Autowired
    private RestTemplate restTemplate;

    // Endpoint to call Python API and retrieve CSRF token
    @GetMapping("/call-python-api-get")
    public ApiResponse getCsrfToken(@RequestParam("client_id") String clientId, @RequestParam("package_id") String packageId) {
        String getUrl = String.format("http://192.168.90.151:8080/data/?client_id=%s&package_id=%s", clientId, packageId);
    
        try {
            // Log the URL being called
            logger.info("Calling Python API with URL: {}", getUrl);
    
            // Make GET request to Python API and capture response
            ResponseEntity<String> response = restTemplate.exchange(getUrl, HttpMethod.GET, null, String.class);
    
            // Check if the response is valid
            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                if (responseBody == null || responseBody.isEmpty()) {
                    logger.error("Empty response body from Python API.");
                    return new ApiResponse("error", "Empty response body from Python API", null);
                }
    
                logger.info("Response Body: {}", responseBody); // Log entire response body
    
                JsonNode jsonResponse = new ObjectMapper().readTree(responseBody);
                logger.info("Parsed JSON: {}", jsonResponse.toString()); // Log parsed JSON
    
                String csrfToken = jsonResponse.path("csrf_token").asText();
                double limitCount = jsonResponse.path("limit_count").asDouble();
                String limitType = jsonResponse.path("limit_type").asText();
    
                // Handle missing CSRF token case
                if (csrfToken == null || csrfToken.isEmpty()) {
                    logger.error("CSRF token is missing in the response!");
                    return new ApiResponse("error", "CSRF token is missing in the response", null);
                }
    
                logger.info("Successfully retrieved CSRF token: {}", csrfToken);
                logger.info("Limit Count: {} {}", limitCount, limitType);
    
                // Retrieve session cookie from the response headers
                String sessionCookie = getSessionCookie(response.getHeaders());
                if (sessionCookie == null || sessionCookie.isEmpty()) {
                    logger.error("Session cookie missing in the response headers!");
                    return new ApiResponse("error", "Session cookie missing in the response", null);
                }
    
                // Automatically trigger the POST request with the extracted CSRF token and session cookie
                ApiResponse postResponse = triggerPostRequest(clientId, packageId, "20", csrfToken, sessionCookie);
    
                // Return a success response with the postResponse data
                return new ApiResponse("success", "CSRF token retrieved and POST request triggered successfully", postResponse.getData());
            } else {
                logger.error("Received non-2xx response: {}", response.getStatusCode());
                return new ApiResponse("error", "Non-2xx response from Python API", null);
            }
    
        } catch (Exception e) {
            logger.error("Error while fetching CSRF token for client_id: {} and package_id: {}", clientId, packageId, e);
            return new ApiResponse("error", "Error retrieving CSRF token", e.getMessage());
        }
    }
    
    // Method to extract session cookie from response headers
    private String getSessionCookie(HttpHeaders headers) {
        // Look for the session cookie (assuming it follows the standard "session=..." format)
        for (String cookie : headers.get("Set-Cookie")) {
            if (cookie.startsWith("session=")) {
                return cookie.substring("session=".length(), cookie.indexOf(";"));
            }
        }
        return null;
    }

    private ApiResponse triggerPostRequest(String clientId, String packageId, String value, String csrfToken, String sessionCookie) {
        try {
            // Construct the URL with query parameters
            String postUrl = String.format("http://192.168.90.151:8080/data/?client_id=%s&package_id=%s&value=%s", clientId, packageId, value);
        
            // Prepare headers with CSRF token and content type
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);  // Set content type as JSON
            headers.set("X-CSRFToken", csrfToken);  // Set CSRF token in header
            headers.add("Cookie", "X-CSRFToken=" + csrfToken);  // CSRF token in cookie
            headers.add("Cookie", "session=" + sessionCookie);  // Add session cookie to the request
            
            logger.info("Sending POST request with CSRF token and session cookie: {}", csrfToken);
        
            // Include `postData` in the body
            String postData = String.format("{\"client_id\": \"%s\", \"package_id\": \"%s\", \"value\": \"%s\"}", clientId, packageId, value);
            HttpEntity<String> entity = new HttpEntity<>(postData, headers);
        
            // Send the POST request to the server
            ResponseEntity<String> postResponse = restTemplate.exchange(postUrl, HttpMethod.POST, entity, String.class);
        
            // Check the response
            String responseBody = postResponse.getBody();
            if (responseBody != null) {
                if (responseBody.contains("CSRF session token is missing")) {
                    logger.error("CSRF session token is missing in the POST request!");
                    return new ApiResponse("error", "CSRF session token is missing in the POST request", responseBody);
                }
                // Log successful response
                logger.info("POST request successful with response: {}", responseBody);
                return new ApiResponse("success", "POST request was successful", responseBody);
            } else {
                logger.error("Empty response body from POST request!");
                return new ApiResponse("error", "Empty response body from POST request", null);
            }
        
        } catch (Exception e) {
            logger.error("Error while sending POST request for client_id: {}, package_id: {}, value: {}", clientId, packageId, value, e);
            return new ApiResponse("error", "Error sending POST request", e.getMessage());
        }
    }
}
