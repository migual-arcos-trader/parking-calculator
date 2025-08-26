package com.paymeter.parking_calculator.application.utils;

public class CalculatorPriceUtil {

    public static double getTotalPriceWithMaxTime(Double hourlyRate, double totalHours, int hoursPeriod, int maxPriceByPeriod) {
        double totalPriceWithMaxTime = 0.0;
        if (isValidInput(hourlyRate, totalHours, hoursPeriod, maxPriceByPeriod)) {
            int completePeriods = (int) (totalHours / hoursPeriod);
            int completePeriodsPrice = completePeriods * maxPriceByPeriod;
            double remainingHours = totalHours % hoursPeriod;
            double remainingHoursPrice = remainingHours * hourlyRate;
            totalPriceWithMaxTime = completePeriodsPrice + remainingHoursPrice;
        }
        return totalPriceWithMaxTime;
    }

    private static boolean isValidInput(Double hourlyRate, double totalHours, int periodHours, int periodMaxPrice) {
        return hourlyRate != null &&
                hourlyRate > 0 &&
                totalHours > 0 &&
                periodHours > 0 &&
                periodMaxPrice > 0;
    }

}
