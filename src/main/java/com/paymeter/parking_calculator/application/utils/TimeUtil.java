package com.paymeter.parking_calculator.application.utils;

public class TimeUtil {

    public static double calculateTotalHours(long totalMinutes) {
        return Math.ceil(totalMinutes / 60.0);
    }

}
