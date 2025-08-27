package com.paymeter.parking_calculator.domain.service.discount;

import com.paymeter.parking_calculator.domain.exception.UnsupportedDiscountException;
import com.paymeter.parking_calculator.domain.model.DiscountType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiscountStrategyFactoryTest {

    @Autowired
    private DiscountStrategyFactory strategyFactory;

    @Test
    @DisplayName("Should return MaxDaily15Strategy for MAX_DAILY_15")
    void getStrategy_ForMaxDaily15_ShouldReturnCorrectStrategy() {
        // Act
        DiscountStrategy strategy = strategyFactory.getStrategy(DiscountType.MAX_DAILY_15.name());

        // Assert
        assertNotNull(strategy);
        assertEquals(MaxDaily15Strategy.class, strategy.getClass());
    }

    @Test
    @DisplayName("Should return Max12HHoursFreeStrategy for MAX_12H_20_HOURS_FREE")
    void getStrategy_ForMax12h20Free_ShouldReturnCorrectStrategy() {
        // Act
        DiscountStrategy strategy = strategyFactory.getStrategy(DiscountType.MAX_12H_20_HOURS_FREE.name());

        // Assert
        assertNotNull(strategy);
        assertEquals(Max12HHoursFreeStrategy.class, strategy.getClass());
    }

    @Test
    @DisplayName("Should throw exception for unknown discount type")
    void getStrategy_ForUnknownType_ShouldThrowException() {
        // Act & Assert
        assertThrows(UnsupportedDiscountException.class, () -> strategyFactory.getStrategy("UNKNOWN_TYPE"));
    }
}