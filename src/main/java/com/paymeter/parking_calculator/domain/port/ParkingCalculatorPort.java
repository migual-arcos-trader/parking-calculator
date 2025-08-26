package com.paymeter.parking_calculator.domain.port;

import com.paymeter.parking_calculator.domain.model.ParkingCalculation;

import java.time.LocalDateTime;

public interface ParkingCalculatorPort {

    ParkingCalculation calculatePrice(String parkingId, LocalDateTime from, LocalDateTime to);

}
