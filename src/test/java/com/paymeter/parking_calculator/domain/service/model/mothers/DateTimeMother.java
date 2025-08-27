package com.paymeter.parking_calculator.domain.service.model.mothers;

import com.paymeter.parking_calculator.domain.service.model.constants.TestConstants;

import java.time.LocalDateTime;

public final class DateTimeMother {

    private DateTimeMother() {
        // Utility class
    }

    public static LocalDateTime createFebruary27_9am() {
        return LocalDateTime.of(
                TestConstants.YEAR_2024,
                TestConstants.MONTH_FEBRUARY,
                TestConstants.DAY_27,
                TestConstants.HOUR_9,
                TestConstants.MINUTE_0
        );
    }

    public static LocalDateTime createFebruary27_10am() {
        return LocalDateTime.of(
                TestConstants.YEAR_2024,
                TestConstants.MONTH_FEBRUARY,
                TestConstants.DAY_27,
                TestConstants.HOUR_10,
                TestConstants.MINUTE_0
        );
    }

    public static LocalDateTime createFebruary27_9_59am() {
        return LocalDateTime.of(
                TestConstants.YEAR_2024,
                TestConstants.MONTH_FEBRUARY,
                TestConstants.DAY_27,
                TestConstants.HOUR_9,
                TestConstants.MINUTE_59
        );
    }

    public static LocalDateTime createDateTimeWithMinutesAdded(LocalDateTime baseTime, int minutesToAdd) {
        return baseTime.plusMinutes(minutesToAdd);
    }

    public static LocalDateTime createDateTimeWithSecondsAdded(LocalDateTime baseTime, int secondsToAdd) {
        return baseTime.plusSeconds(secondsToAdd);
    }

    public static LocalDateTime createDateTimeWithHoursAdded(LocalDateTime baseTime, int hoursToAdd) {
        return baseTime.plusHours(hoursToAdd);
    }
}
