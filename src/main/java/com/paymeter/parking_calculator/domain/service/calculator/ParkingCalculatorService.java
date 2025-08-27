package com.paymeter.parking_calculator.domain.service.calculator;

import com.paymeter.parking_calculator.domain.config.ParkingConfigProperties;
import com.paymeter.parking_calculator.domain.exception.ParkingNotFoundException;
import com.paymeter.parking_calculator.domain.model.Parking;
import com.paymeter.parking_calculator.domain.model.ParkingCalculation;
import com.paymeter.parking_calculator.domain.port.ParkingCalculatorPort;
import com.paymeter.parking_calculator.domain.port.ParkingRepositoryPort;
import com.paymeter.parking_calculator.domain.service.discount.DiscountStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingCalculatorService implements ParkingCalculatorPort {

    public static final int FORMAT_PRICE_BASE = 100;
    private final ParkingRepositoryPort parkingRepositoryPort;
    private final ParkingConfigProperties parkingConfigProperties;
    private final DiscountStrategyFactory discountStrategyFactory;

    @Override
    public ParkingCalculation calculatePrice(String parkingId, LocalDateTime from, LocalDateTime to) {
        log.info("Calculating price for parkingId: {}, from: {}, to: {}", parkingId, from, to);
        Parking parking = this.getParking(parkingId);
        LocalDateTime endTime = this.getEndTime(to);
        this.validateFromAndEndTime(from, endTime);
        long totalMinutes = this.getTotalMinutes(from, endTime);
        double totalPrice = this.calculateTotalPrice(parking, totalMinutes);
        return buildParkingCalculation(parking.getId(), from, endTime, (int) totalMinutes, totalPrice);
    }

    private Parking getParking(String parkingId) {
        return parkingRepositoryPort.findById(parkingId)
                .orElseThrow(() -> {
                    log.warn("Parking id not found: {}", parkingId);
                    return new ParkingNotFoundException("Parking id not found");
                });
    }

    private LocalDateTime getEndTime(LocalDateTime to) {
        return Objects.nonNull(to) ? to : LocalDateTime.now();
    }

    private void validateFromAndEndTime(LocalDateTime from, LocalDateTime endTime) {
        if (Objects.isNull(from)) {
            log.info("Invalid date range: from is null");
            throw new IllegalArgumentException("Invalid date range: 'from' is null");
        }

        if (from.isAfter(endTime)) {
            log.info("Invalid date range: from {} is after to {}", from, endTime);
            throw new IllegalArgumentException("Invalid date range: 'from' cannot be after 'to'");
        }
    }

    private long getTotalMinutes(LocalDateTime from, LocalDateTime endTime) {
        return Duration.between(from, endTime).toMinutes();
    }

    private double calculateTotalPrice(Parking parking, long totalMinutes) {
        return totalMinutes < parkingConfigProperties.getFreeMinutesThreshold() ?
                0.0 :
                discountStrategyFactory.getStrategy(parking.getDiscountName()).calculateFinalPrice(parking, totalMinutes);
    }

    private ParkingCalculation buildParkingCalculation(String parkingId, LocalDateTime from, LocalDateTime to, int duration, double totalPrice) {
        return ParkingCalculation.builder()
                .parkingId(parkingId)
                .from(from)
                .to(to)
                .duration(duration)
                .price(formatPrice(totalPrice))
                .build();
    }

    private String formatPrice(double price) {
        int amountInCents = (int) Math.round(price * FORMAT_PRICE_BASE);
        int euros = amountInCents / FORMAT_PRICE_BASE;
        int cents = amountInCents % FORMAT_PRICE_BASE;
        return String.format("%d.%02d EUR", euros, cents);
    }

}
