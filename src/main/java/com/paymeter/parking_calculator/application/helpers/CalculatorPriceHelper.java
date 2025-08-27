package com.paymeter.parking_calculator.application.helpers;

public class CalculatorPriceHelper {

    public static final double INIT_TOTAL_PRICE_WITH_MAX_TIME = 0.0;
    public static final int POSITIVE_TIME = 0;

    public static double getTotalPriceWithMaxTime(Double hourlyRate, double totalHours, int hoursPeriod, int maxPriceByPeriod) {
        double totalPriceWithMaxTime = INIT_TOTAL_PRICE_WITH_MAX_TIME;
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
                hourlyRate > POSITIVE_TIME &&
                totalHours > POSITIVE_TIME &&
                periodHours > POSITIVE_TIME &&
                periodMaxPrice > POSITIVE_TIME;
    }

}
