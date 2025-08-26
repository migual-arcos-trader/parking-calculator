package com.paymeter.parking_calculator.infrastructure.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingCalculatorIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void calculatePriceValidRequestReturnsCorrectPrice() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String request = """
                {
                    "parkingId": "P000123",
                    "from": "2024-02-27T09:00:00"
                }
                """;

        HttpEntity<String> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("/tickets/calculate", entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("price"));
    }

    @Test
    void calculatePriceInvalidParkingIdReturnsNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String request = """
                {
                    "parkingId": "INVALID",
                    "from": "2024-02-27T09:00:00"
                }
                """;

        HttpEntity<String> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("/tickets/calculate", entity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void calculatePriceMissingRequiredFieldReturnsBadRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String request = """
                {
                    "from": "2024-02-27T09:00:00"
                }
                """;

        HttpEntity<String> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("/tickets/calculate", entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
