package com.paymeter.parking_calculator.domain.port;

import com.paymeter.parking_calculator.domain.model.Parking;

import java.util.Optional;

public interface ParkingRepositoryPort {

    Optional<Parking> findById(String parkingId);

}