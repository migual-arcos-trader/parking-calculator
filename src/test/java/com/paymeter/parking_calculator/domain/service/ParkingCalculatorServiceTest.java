package com.paymeter.parking_calculator.domain.service;

import com.paymeter.parking_calculator.domain.config.ParkingConfigProperties;
import com.paymeter.parking_calculator.domain.exception.ParkingNotFoundException;
import com.paymeter.parking_calculator.domain.model.Parking;
import com.paymeter.parking_calculator.domain.port.ParkingRepositoryPort;
import com.paymeter.parking_calculator.domain.service.calculator.ParkingCalculatorService;
import com.paymeter.parking_calculator.domain.service.discount.DiscountStrategy;
import com.paymeter.parking_calculator.domain.service.discount.DiscountStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingCalculatorServiceTest {

    @Mock
    private ParkingRepositoryPort parkingRepositoryPort;

    @Mock
    private ParkingConfigProperties parkingConfigProperties;

    @Mock
    private DiscountStrategyFactory discountStrategyFactory;

    @Mock
    private DiscountStrategy discountStrategy;

    @InjectMocks
    private ParkingCalculatorService parkingCalculatorService;

    private Parking client1Parking;

    @BeforeEach
    void setUp() {
        client1Parking = Parking.builder()
                .id("P000123")
                .hourlyRate(2.0)
                .discountName("MAX_DAILY_15")
                .build();
    }

    private void setupDiscountStrategyMocks() {
        when(discountStrategyFactory.getStrategy(anyString())).thenReturn(discountStrategy);
        when(discountStrategy.calculateFinalPrice(any(Parking.class), any(Long.class)))
                .thenAnswer(invocation -> {
                    Parking parking = invocation.getArgument(0);
                    Long totalMinutes = invocation.getArgument(1);
                    double totalHours = Math.ceil(totalMinutes / 60.0);
                    return totalHours * parking.getHourlyRate();
                });
    }

    @Test
    @DisplayName("Should return free parking when duration is less than 1 minute")
    void calculatePriceDurationLessThanOneMinuteShouldReturnFree() {
        // Arrange
        when(parkingConfigProperties.getFreeMinutesThreshold()).thenReturn(1);
        when(parkingRepositoryPort.findById("P000123")).thenReturn(Optional.of(client1Parking));

        LocalDateTime from = LocalDateTime.of(2024, 2, 27, 9, 0);
        LocalDateTime to = from.plusSeconds(30);

        // Act
        var result = parkingCalculatorService.calculatePrice("P000123", from, to);

        // Assert
        assertEquals("0.00 EUR", result.getPrice());
        assertEquals(0, result.getDuration());
        assertEquals(from, result.getFrom());
        assertEquals(to, result.getTo());
    }

    @Test
    @DisplayName("Should charge for 1 hour when duration is 30 minutes (fraction rounds up)")
    void calculatePrice30MinutesShouldChargeFor1Hour() {
        // Arrange
        when(parkingConfigProperties.getFreeMinutesThreshold()).thenReturn(1);
        when(parkingRepositoryPort.findById("P000123")).thenReturn(Optional.of(client1Parking));
        this.setupDiscountStrategyMocks();

        LocalDateTime from = LocalDateTime.of(2024, 2, 27, 9, 0);
        LocalDateTime to = from.plusMinutes(30);

        // Act
        var result = parkingCalculatorService.calculatePrice("P000123", from, to);

        // Assert
        assertEquals("2.00 EUR", result.getPrice());
        assertEquals(30, result.getDuration());
    }

    @Test
    @DisplayName("Should charge for 1 hour when duration is 59 minutes (fraction rounds up)")
    void calculatePrice59MinutesShouldChargeFor1Hour() {
        // Arrange
        when(parkingConfigProperties.getFreeMinutesThreshold()).thenReturn(1);
        when(parkingRepositoryPort.findById("P000123")).thenReturn(Optional.of(client1Parking));
        this.setupDiscountStrategyMocks();

        LocalDateTime from = LocalDateTime.of(2024, 2, 27, 9, 0);
        LocalDateTime to = from.plusMinutes(59);

        // Act
        var result = parkingCalculatorService.calculatePrice("P000123", from, to);

        // Assert
        assertEquals("2.00 EUR", result.getPrice());
        assertEquals(59, result.getDuration());
    }

    @Test
    @DisplayName("Should charge for 2 hours when duration is 61 minutes (fraction rounds up)")
    void calculatePrice61MinutesShouldChargeFor2Hours() {
        // Arrange
        when(parkingConfigProperties.getFreeMinutesThreshold()).thenReturn(1);
        when(parkingRepositoryPort.findById("P000123")).thenReturn(Optional.of(client1Parking));
        this.setupDiscountStrategyMocks();

        LocalDateTime from = LocalDateTime.of(2024, 2, 27, 9, 0);
        LocalDateTime to = from.plusMinutes(61);

        // Act
        var result = parkingCalculatorService.calculatePrice("P000123", from, to);

        // Assert
        assertEquals("4.00 EUR", result.getPrice());
        assertEquals(61, result.getDuration());
    }

    @Test
    @DisplayName("Should charge for exact hours without rounding")
    void calculatePriceExact2HoursShouldChargeFor2Hours() {
        // Arrange
        when(parkingConfigProperties.getFreeMinutesThreshold()).thenReturn(1);
        when(parkingRepositoryPort.findById("P000123")).thenReturn(Optional.of(client1Parking));
        this.setupDiscountStrategyMocks();

        LocalDateTime from = LocalDateTime.of(2024, 2, 27, 9, 0);
        LocalDateTime to = from.plusHours(2);

        // Act
        var result = parkingCalculatorService.calculatePrice("P000123", from, to);

        // Assert
        assertEquals("4.00 EUR", result.getPrice());
        assertEquals(120, result.getDuration());
    }

    @Test
    @DisplayName("Should handle maximum possible duration without overflow")
    void calculatePriceMaximumDurationShouldHandleWithoutOverflow() {
        // Arrange
        when(parkingConfigProperties.getFreeMinutesThreshold()).thenReturn(1);
        when(parkingRepositoryPort.findById("P000123")).thenReturn(Optional.of(client1Parking));
        this.setupDiscountStrategyMocks();

        LocalDateTime from = LocalDateTime.MIN;
        LocalDateTime to = LocalDateTime.MAX;

        // Act & Assert
        assertDoesNotThrow(() -> {
            var result = parkingCalculatorService.calculatePrice("P000123", from, to);
            assertNotNull(result);
        });
    }

    @Test
    @DisplayName("Should handle zero duration when 'from' equals 'to'")
    void calculatePriceFromEqualsToShouldReturnFree() {
        // Arrange
        when(parkingConfigProperties.getFreeMinutesThreshold()).thenReturn(1);
        when(parkingRepositoryPort.findById("P000123")).thenReturn(Optional.of(client1Parking));

        LocalDateTime from = LocalDateTime.of(2024, 2, 27, 9, 0);

        // Act
        var result = parkingCalculatorService.calculatePrice("P000123", from, from);

        // Assert
        assertEquals("0.00 EUR", result.getPrice());
        assertEquals(0, result.getDuration());
    }

    @Test
    @DisplayName("Should throw ParkingNotFoundException when parking ID does not exist")
    void calculatePriceNonExistentParkingIdShouldThrowParkingNotFoundException() {
        // Arrange
        String nonExistentParkingId = "NON_EXISTENT";
        LocalDateTime from = LocalDateTime.of(2024, 2, 27, 9, 0);
        LocalDateTime to = from.plusHours(1);

        when(parkingRepositoryPort.findById(nonExistentParkingId)).thenReturn(Optional.empty());

        // Act & Assert
        ParkingNotFoundException exception = assertThrows(ParkingNotFoundException.class,
                () -> parkingCalculatorService.calculatePrice(nonExistentParkingId, from, to));

        assertEquals("Parking id not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when 'from' date is after 'to' date")
    void calculatePriceFromAfterToShouldThrowIllegalArgumentException() {
        // Arrange
        when(parkingRepositoryPort.findById("P000123")).thenReturn(Optional.of(client1Parking));

        LocalDateTime from = LocalDateTime.of(2024, 2, 27, 10, 0);
        LocalDateTime to = LocalDateTime.of(2024, 2, 27, 9, 59);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parkingCalculatorService.calculatePrice("P000123", from, to));

        assertEquals("Invalid date range: 'from' cannot be after 'to'", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when 'from' is null")
    void calculatePriceNullFromShouldThrowIllegalArgumentException() {
        // Arrange
        when(parkingRepositoryPort.findById("P000123")).thenReturn(Optional.of(client1Parking));

        LocalDateTime to = LocalDateTime.of(2024, 2, 27, 9, 0);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parkingCalculatorService.calculatePrice("P000123", null, to));

        assertEquals("Invalid date range: 'from' is null", exception.getMessage());
    }

}