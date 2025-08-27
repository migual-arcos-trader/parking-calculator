package com.paymeter.parking_calculator.application.helpers;

public class TimeHelper {

    public static final double ONE_HOUR = 60.0;

    public static double calculateTotalHours(long totalMinutes) {
        return Math.ceil(totalMinutes / ONE_HOUR);
    }

}
