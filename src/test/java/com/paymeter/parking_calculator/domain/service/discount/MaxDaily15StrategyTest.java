package com.paymeter.parking_calculator.domain.service.discount;

import com.paymeter.parking_calculator.domain.model.Parking;
import com.paymeter.parking_calculator.domain.service.model.constants.TestConstants;
import com.paymeter.parking_calculator.domain.service.model.mothers.ParkingMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxDaily15StrategyTest {

    private MaxDaily15Strategy strategy;
    private Parking client1Parking;

    @BeforeEach
    void setUp() {
        strategy = new MaxDaily15Strategy();
        client1Parking = ParkingMother.createClient1Parking();
    }

    @Test
    @DisplayName("Should calculate normal price when under daily maximum")
    void calculateFinalPriceUnderDailyMaxShouldCalculateNormalPrice() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client1Parking, TestConstants.FIVE_HOURS_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_10_EUR_RESULT, result);
    }

    @Test
    @DisplayName("Should apply daily maximum when calculated price exceeds it")
    void calculateFinalPriceOverDailyMaxShouldApplyMaxPrice() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client1Parking, TestConstants.TEN_HOURS_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_20_EUR_RESULT, result);
    }

    @Test
    @DisplayName("Should handle exact daily maximum boundary")
    void calculateFinalPrice_ExactDailyMaxBoundary_ShouldApplyCorrectPrice() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client1Parking, TestConstants.SEVEN_AND_A_HALF_HOURS_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_16_EUR_RESULT, result);
    }

    @Test
    @DisplayName("Should handle multiple days with daily maximum applied each day")
    void calculateFinalPrice_MultipleDays_ShouldApplyMaxPerDay() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client1Parking, TestConstants.TWENTY_FIVE_HOURS_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_17_EUR_RESULT, result);
    }

    @Test
    @DisplayName("Should handle very long duration with multiple daily maximums")
    void calculateFinalPrice_VeryLongDuration_ShouldApplyMultipleDailyMaximums() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client1Parking, TestConstants.FIFTY_HOURS_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_34_EUR_RESULT, result);
    }

}
