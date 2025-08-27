package com.paymeter.parking_calculator.infrastructure.controller;

import com.paymeter.parking_calculator.domain.service.model.constants.IntegrationTestConstants;
import org.junit.jupiter.api.BeforeEach;
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

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void calculatePriceValidRequestReturnsCorrectPrice() {
        // Arrange

        // Act
        HttpEntity<String> entity = new HttpEntity<>(IntegrationTestConstants.PARKING_ID_P_000123_FROM_2024_02_27_T_09_00_00, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(IntegrationTestConstants.PATH, entity, String.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains(IntegrationTestConstants.PRICE));
    }

    @Test
    void calculatePriceInvalidParkingIdReturnsNotFound() {
        // Arrange

        // Act
        HttpEntity<String> entity = new HttpEntity<>(IntegrationTestConstants.PARKING_ID_INVALID_FROM_2024_02_27_T_09_00_00, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(IntegrationTestConstants.PATH, entity, String.class);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void calculatePriceMissingRequiredFieldReturnsBadRequest() {
        // Arrange

        // Act
        HttpEntity<String> entity = new HttpEntity<>(IntegrationTestConstants.FROM_2024_02_27_T_09_00_00, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(IntegrationTestConstants.PATH, entity, String.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
