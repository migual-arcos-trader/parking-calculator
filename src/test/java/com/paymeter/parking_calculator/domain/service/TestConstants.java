package com.paymeter.parking_calculator.domain.service;

public final class TestConstants {

    private TestConstants() {
        // Utility class
    }

    // Parking IDs
    public static final String PARKING_ID_1 = "P000123";
    public static final String NON_EXISTENT_PARKING_ID = "NON_EXISTENT";

    // Hourly Rates
    public static final double HOURLY_RATE_2_EURO = 2.0;

    // Discount Names
    public static final String DISCOUNT_MAX_DAILY_15 = "MAX_DAILY_15";

    // Time Constants
    public static final int FREE_MINUTES_THRESHOLD = 1;
    public static final int THIRTY_MINUTES = 30;
    public static final int FIFTY_NINE_MINUTES = 59;
    public static final int SIXTY_ONE_MINUTES = 61;
    public static final int TWO_HOURS_MINUTES = 120;

    // Price Expectations
    public static final String PRICE_0_EUR = "0.00 EUR";
    public static final String PRICE_2_EUR = "2.00 EUR";
    public static final String PRICE_4_EUR = "4.00 EUR";

    // Date Constants
    public static final int YEAR_2024 = 2024;
    public static final int MONTH_FEBRUARY = 2;
    public static final int DAY_27 = 27;
    public static final int HOUR_9 = 9;
    public static final int HOUR_10 = 10;
    public static final int MINUTE_0 = 0;
    public static final int MINUTE_59 = 59;
}