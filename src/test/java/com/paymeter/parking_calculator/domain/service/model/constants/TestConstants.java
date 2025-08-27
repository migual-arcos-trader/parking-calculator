package com.paymeter.parking_calculator.domain.service.model.constants;

public final class TestConstants {

    private TestConstants() {
        // Utility class
    }

    // Parking IDs
    public static final String PARKING_ID_0 = "P000000";
    public static final String PARKING_ID_1 = "P000123";
    public static final String PARKING_ID_2 = "P000456";
    public static final String NON_EXISTENT_PARKING_ID = "NON_EXISTENT";

    // Hourly Rates
    public static final double HOURLY_RATE_2_EURO = 2.0;
    public static final double HOURLY_RATE_3_EURO = 3.0;
    public static final double HOURLY_RATE_5_EURO = 5.0;

    // Discount Names
    public static final String DISCOUNT_MAX_DAILY_15 = "MAX_DAILY_15";
    public static final String DISCOUNT_MAX_12H_20_FREE = "MAX_12H_20_HOURS_FREE";
    public static final String DISCOUNT_NONE = "NONE";

    // Time Constants (minutes)
    public static final int FREE_MINUTES_THRESHOLD = 1;
    public static final int THIRTY_MINUTES = 30;
    public static final int FIFTY_NINE_MINUTES = 59;
    public static final int SIXTY_ONE_MINUTES = 61;
    public static final int TWO_HOURS_MINUTES = 120;
    public static final int FIVE_HOURS_MINUTES = 300;
    public static final int SEVEN_AND_A_HALF_HOURS_MINUTES = 450;
    public static final int TEN_HOURS_MINUTES = 600;
    public static final int THIRTEEN_HOURS_MINUTES = 780;
    public static final int TWENTY_FIVE_HOURS_MINUTES = 1500;
    public static final int FIFTY_HOURS_MINUTES = 3000;

    // Price Expectations (double results)
    public static final double PRICE_0_EUR_RESULT = 0.0;
    public static final double PRICE_3_EUR_RESULT = 3.0;
    public static final double PRICE_5_EUR_RESULT = 5.0;
    public static final double PRICE_10_EUR_RESULT = 10.0;
    public static final double PRICE_16_EUR_RESULT = 16.0;
    public static final double PRICE_17_EUR_RESULT = 17.0;
    public static final double PRICE_20_EUR_RESULT = 20.0;
    public static final double PRICE_34_EUR_RESULT = 34.0;
    public static final double PRICE_40_EUR_RESULT = 40.0;
    public static final double PRICE_50_EUR_RESULT = 50.0;
    public static final double PRICE_83_EUR_RESULT = 83.0;

    // Price Format Expectations (String)
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