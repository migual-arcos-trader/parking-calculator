package com.paymeter.parking_calculator.domain.service.discount;

import com.paymeter.parking_calculator.domain.model.Parking;
import com.paymeter.parking_calculator.domain.service.model.constants.TestConstants;
import com.paymeter.parking_calculator.domain.service.model.mothers.ParkingMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoDiscountStrategyTest {

    private NoDiscountStrategy strategy;
    private Parking client0Parking;

    @BeforeEach
    void setUp() {
        strategy = new NoDiscountStrategy();
        client0Parking = ParkingMother.createClient0Parking();
    }

    @Test
    @DisplayName("Should calculate price for less than one hour (rounding up)")
    void calculateFinalPriceLessThanOneHourShouldRoundUp() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client0Parking, TestConstants.THIRTY_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_5_EUR_RESULT, result);
    }

    @Test
    @DisplayName("Should calculate price for exact hours")
    void calculateFinalPriceExactHoursShouldCalculateExactly() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client0Parking, TestConstants.TWO_HOURS_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_10_EUR_RESULT, result);
    }

    @Test
    @DisplayName("Should calculate price for fractional hours (rounding up)")
    void calculateFinalPriceFractionalHoursShouldRoundUp() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client0Parking, TestConstants.SIXTY_ONE_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_10_EUR_RESULT, result);
    }

    @Test
    @DisplayName("Should calculate price for long duration")
    void calculateFinalPriceLongDurationShouldCalculateCorrectly() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client0Parking, TestConstants.TEN_HOURS_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_50_EUR_RESULT, result);
    }

    @Test
    @DisplayName("Should handle exact minute boundary")
    void calculateFinalPriceExactMinuteBoundaryShouldCalculateCorrectly() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client0Parking, TestConstants.FIFTY_NINE_MINUTES);

        // Assert
        assertEquals(TestConstants.PRICE_5_EUR_RESULT, result);
    }

    @Test
    @DisplayName("Should handle very short duration (less than minute)")
    void calculateFinalPriceVeryShortDurationShouldRoundUpToFirstHour() {
        // Arrange

        // Act
        double result = strategy.calculateFinalPrice(client0Parking, TestConstants.FREE_MINUTES_THRESHOLD);

        // Assert
        assertEquals(TestConstants.PRICE_5_EUR_RESULT, result);
    }

}