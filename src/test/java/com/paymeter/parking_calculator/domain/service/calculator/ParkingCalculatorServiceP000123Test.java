package com.paymeter.parking_calculator.domain.service.calculator;

import com.paymeter.parking_calculator.domain.config.ParkingConfigProperties;
import com.paymeter.parking_calculator.domain.exception.InvalidDateRangeException;
import com.paymeter.parking_calculator.domain.exception.ParkingNotFoundException;
import com.paymeter.parking_calculator.domain.model.Parking;
import com.paymeter.parking_calculator.domain.port.ParkingRepositoryPort;
import com.paymeter.parking_calculator.domain.service.discount.DiscountStrategy;
import com.paymeter.parking_calculator.domain.service.discount.DiscountStrategyFactory;
import com.paymeter.parking_calculator.domain.service.model.constants.TestConstants;
import com.paymeter.parking_calculator.domain.service.model.mothers.DateTimeMother;
import com.paymeter.parking_calculator.domain.service.model.mothers.ParkingMother;
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
class ParkingCalculatorServiceP000123Test {

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
    private LocalDateTime february27_9am;

    @BeforeEach
    void setUp() {
        client1Parking = ParkingMother.createClient1Parking();
        february27_9am = DateTimeMother.createFebruary27_9am();
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
        when(parkingConfigProperties.getFreeMinutesThreshold()).thenReturn(TestConstants.FREE_MINUTES_THRESHOLD);
        when(parkingRepositoryPort.findById(TestConstants.PARKING_ID_1)).thenReturn(Optional.of(client1Parking));

        LocalDateTime to = DateTimeMother.createDateTimeWithSecondsAdded(february27_9am, TestConstants.THIRTY_MINUTES);

        // Act
        var result = parkingCalculatorService.calculatePrice(TestConstants.PARKING_ID_1, february27_9am, to);

        // Assert
        assertEquals(TestConstants.PRICE_0_EUR, result.getPrice());
        assertEquals(0, result.getDuration());
        assertEquals(february27_9am, result.getFrom());
        assertEquals(to, result.getTo());
    }

    @Test
    @DisplayName("Should charge for 1 hour when duration is 30 minutes (fraction rounds up)")
    void calculatePrice30MinutesShouldChargeFor1Hour() {
        // Arrange
        when(parkingConfigProperties.getFreeMinutesThreshold()).thenReturn(TestConstants.FREE_MINUTES_THRESHOLD);
        when(parkingRepositoryPort.findById(TestConstants.PARKING_ID_1)).thenReturn(Optional.of(client1Parking));
        setupDiscountStrategyMocks();

        LocalDateTime to = DateTimeMother.createDateTimeWithMinutesAdded(february27_9am, TestConstants.THIRTY_MINUTES);

        // Act
        var result = parkingCalculatorService.calculatePrice(TestConstants.PARKING_ID_1, february27_9am, to);

        // Assert
        assertEquals(TestConstants.PRICE_2_EUR, result.getPrice());
        assertEquals(TestConstants.THIRTY_MINUTES, result.getDuration());
    }

    @Test
    @DisplayName("Should charge for 1 hour when duration is 59 minutes (fraction rounds up)")
    void calculatePrice59MinutesShouldChargeFor1Hour() {
        // Arrange
        when(parkingConfigProperties.getFreeMinutesThreshold()).thenReturn(TestConstants.FREE_MINUTES_THRESHOLD);
        when(parkingRepositoryPort.findById(TestConstants.PARKING_ID_1)).thenReturn(Optional.of(client1Parking));
        setupDiscountStrategyMocks();

        LocalDateTime to = DateTimeMother.createDateTimeWithMinutesAdded(february27_9am, TestConstants.FIFTY_NINE_MINUTES);

        // Act
        var result = parkingCalculatorService.calculatePrice(TestConstants.PARKING_ID_1, february27_9am, to);

        // Assert
        assertEquals(TestConstants.PRICE_2_EUR, result.getPrice());
        assertEquals(TestConstants.FIFTY_NINE_MINUTES, result.getDuration());
    }

    @Test
    @DisplayName("Should charge for 2 hours when duration is 61 minutes (fraction rounds up)")
    void calculatePrice61MinutesShouldChargeFor2Hours() {
        // Arrange
        when(parkingConfigProperties.getFreeMinutesThreshold()).thenReturn(TestConstants.FREE_MINUTES_THRESHOLD);
        when(parkingRepositoryPort.findById(TestConstants.PARKING_ID_1)).thenReturn(Optional.of(client1Parking));
        setupDiscountStrategyMocks();

        LocalDateTime to = DateTimeMother.createDateTimeWithMinutesAdded(february27_9am, TestConstants.SIXTY_ONE_MINUTES);

        // Act
        var result = parkingCalculatorService.calculatePrice(TestConstants.PARKING_ID_1, february27_9am, to);

        // Assert
        assertEquals(TestConstants.PRICE_4_EUR, result.getPrice());
        assertEquals(TestConstants.SIXTY_ONE_MINUTES, result.getDuration());
    }

    @Test
    @DisplayName("Should charge for exact hours without rounding")
    void calculatePriceExact2HoursShouldChargeFor2Hours() {
        // Arrange
        when(parkingConfigProperties.getFreeMinutesThreshold()).thenReturn(TestConstants.FREE_MINUTES_THRESHOLD);
        when(parkingRepositoryPort.findById(TestConstants.PARKING_ID_1)).thenReturn(Optional.of(client1Parking));
        setupDiscountStrategyMocks();

        LocalDateTime to = DateTimeMother.createDateTimeWithHoursAdded(february27_9am, 2);

        // Act
        var result = parkingCalculatorService.calculatePrice(TestConstants.PARKING_ID_1, february27_9am, to);

        // Assert
        assertEquals(TestConstants.PRICE_4_EUR, result.getPrice());
        assertEquals(TestConstants.TWO_HOURS_MINUTES, result.getDuration());
    }

    @Test
    @DisplayName("Should handle maximum possible duration without overflow")
    void calculatePriceMaximumDurationShouldHandleWithoutOverflow() {
        // Arrange
        when(parkingConfigProperties.getFreeMinutesThreshold()).thenReturn(TestConstants.FREE_MINUTES_THRESHOLD);
        when(parkingRepositoryPort.findById(TestConstants.PARKING_ID_1)).thenReturn(Optional.of(client1Parking));
        setupDiscountStrategyMocks();

        // Act & Assert
        assertDoesNotThrow(() -> {
            var result = parkingCalculatorService.calculatePrice(TestConstants.PARKING_ID_1, LocalDateTime.MIN, LocalDateTime.MAX);
            assertNotNull(result);
        });
    }

    @Test
    @DisplayName("Should handle zero duration when 'from' equals 'to'")
    void calculatePriceFromEqualsToShouldReturnFree() {
        // Arrange
        when(parkingConfigProperties.getFreeMinutesThreshold()).thenReturn(TestConstants.FREE_MINUTES_THRESHOLD);
        when(parkingRepositoryPort.findById(TestConstants.PARKING_ID_1)).thenReturn(Optional.of(client1Parking));

        // Act
        var result = parkingCalculatorService.calculatePrice(TestConstants.PARKING_ID_1, february27_9am, february27_9am);

        // Assert
        assertEquals(TestConstants.PRICE_0_EUR, result.getPrice());
        assertEquals(0, result.getDuration());
    }

    @Test
    @DisplayName("Should throw ParkingNotFoundException when parking ID does not exist")
    void calculatePriceNonExistentParkingIdShouldThrowParkingNotFoundException() {
        // Arrange
        LocalDateTime to = DateTimeMother.createDateTimeWithHoursAdded(february27_9am, 1);

        when(parkingRepositoryPort.findById(TestConstants.NON_EXISTENT_PARKING_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ParkingNotFoundException exception = assertThrows(ParkingNotFoundException.class,
                () -> parkingCalculatorService.calculatePrice(TestConstants.NON_EXISTENT_PARKING_ID, february27_9am, to));

        assertEquals("Parking id not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidDateRangeException when 'from' date is after 'to' date")
    void calculatePriceFromAfterToShouldThrowInvalidDateRangeException() {
        // Arrange
        LocalDateTime from = DateTimeMother.createFebruary27_10am();
        LocalDateTime to = DateTimeMother.createFebruary27_9_59am();

        // Act & Assert
        InvalidDateRangeException exception = assertThrows(InvalidDateRangeException.class,
                () -> parkingCalculatorService.calculatePrice(TestConstants.PARKING_ID_1, from, to));

        assertEquals("Invalid date range: 'from' cannot be after 'to'", exception.getMessage());
    }

}
