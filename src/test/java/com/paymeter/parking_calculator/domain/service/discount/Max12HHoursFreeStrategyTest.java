package com.paymeter.parking_calculator.domain.service.discount;

import com.paymeter.parking_calculator.domain.model.Parking;
import com.paymeter.parking_calculator.domain.service.model.constants.TestConstants;
import com.paymeter.parking_calculator.domain.service.model.mothers.ParkingMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Max12HHoursFreeStrategyTest {

    private Max12HHoursFreeStrategy strategy;
    private Parking client2Parking;

    @BeforeEach
    void setUp() {
        strategy = new Max12HHoursFreeStrategy();
        client2Parking = ParkingMother.createClient2Parking();
    }

    @Test
    @DisplayName("Should return free for duration less than 1 hour")
    void calculateFinalPrice_LessThan1Hour_ShouldReturnFree() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client2Parking, TestConstants.THIRTY_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_0_EUR_RESULT, result);
    }

    @Test
    @DisplayName("Should charge only for hours after first free hour")
    void calculateFinalPrice_2Hours_ShouldChargeFor1Hour() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client2Parking, TestConstants.TWO_HOURS_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_3_EUR_RESULT, result);
    }

    @Test
    @DisplayName("Should handle exact 12 hours after free hour")
    void calculateFinalPrice_13Hours_ShouldApply12hMax() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client2Parking, TestConstants.THIRTEEN_HOURS_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_20_EUR_RESULT, result);
    }

    @Test
    @DisplayName("Should apply 12-hour maximum price cap")
    void calculateFinalPrice_MoreThan12Hours_ShouldApplyMaxCap() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client2Parking, TestConstants.FIFTY_HOURS_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_83_EUR_RESULT, result);
    }

    @Test
    @DisplayName("Should handle multiple 12-hour periods")
    void calculateFinalPrice_25Hours_ShouldApplyMultiplePeriods() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client2Parking, TestConstants.TWENTY_FIVE_HOURS_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_40_EUR_RESULT, result);
    }
}