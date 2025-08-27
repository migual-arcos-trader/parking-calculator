package com.paymeter.parking_calculator.domain.service.model;

import com.paymeter.parking_calculator.domain.service.TestConstants;
import com.paymeter.parking_calculator.domain.model.Parking;

public final class ParkingMother {

    private ParkingMother() {
        // Utility class
    }

    public static Parking createClient1Parking() {
        return Parking.builder()
                .id(TestConstants.PARKING_ID_1)
                .hourlyRate(TestConstants.HOURLY_RATE_2_EURO)
                .discountName(TestConstants.DISCOUNT_MAX_DAILY_15)
                .build();
    }

}
