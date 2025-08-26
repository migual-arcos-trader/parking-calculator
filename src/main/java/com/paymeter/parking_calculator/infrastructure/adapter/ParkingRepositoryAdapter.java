package com.paymeter.parking_calculator.infrastructure.adapter;

import com.paymeter.parking_calculator.domain.model.Parking;
import com.paymeter.parking_calculator.domain.port.ParkingRepositoryPort;
import com.paymeter.parking_calculator.infrastructure.entity.ParkingEntity;
import com.paymeter.parking_calculator.infrastructure.repository.ParkingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ParkingRepositoryAdapter implements ParkingRepositoryPort {

    private final ParkingJpaRepository parkingJpaRepository;

    @Override
    public Optional<Parking> findById(String parkingId) {
        return parkingJpaRepository
                .findById(parkingId)
                .map(this::toDomain);
    }

    private Parking toDomain(ParkingEntity entity) {
        return Parking.builder()
                .id(entity.getId())
                .hourlyRate(entity.getHourlyRate())
                .discountName(entity.getDiscountName())
                .build();
    }

}
