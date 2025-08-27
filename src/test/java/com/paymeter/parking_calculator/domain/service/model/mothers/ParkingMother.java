package com.paymeter.parking_calculator.domain.service.model.mothers;

import com.paymeter.parking_calculator.domain.model.Parking;
import com.paymeter.parking_calculator.domain.service.model.constants.TestConstants;

public final class ParkingMother {

    private ParkingMother() {
        // Utility class
    }

    public static Parking createClient0Parking() {
        return Parking.builder()
                .id(TestConstants.PARKING_ID_0)
                .hourlyRate(TestConstants.HOURLY_RATE_5_EURO)
                .discountName(TestConstants.DISCOUNT_NONE)
                .build();
    }

    public static Parking createClient1Parking() {
        return Parking.builder()
                .id(TestConstants.PARKING_ID_1)
                .hourlyRate(TestConstants.HOURLY_RATE_2_EURO)
                .discountName(TestConstants.DISCOUNT_MAX_DAILY_15)
                .build();
    }

    public static Parking createClient2Parking() {
        return Parking.builder()
                .id(TestConstants.PARKING_ID_2)
                .hourlyRate(TestConstants.HOURLY_RATE_3_EURO)
                .discountName(TestConstants.DISCOUNT_MAX_12H_20_FREE)
                .build();
    }

}
