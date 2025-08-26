package com.paymeter.parking_calculator.domain.exception;

public class ParkingNotFoundException extends RuntimeException {

    public ParkingNotFoundException(String message) {
        super(message);
    }

}
