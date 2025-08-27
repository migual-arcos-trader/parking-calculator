package com.paymeter.parking_calculator.domain.service.discount;

import com.paymeter.parking_calculator.application.commons.constants.Hours;
import com.paymeter.parking_calculator.application.helpers.CalculatorPriceHelper;
import com.paymeter.parking_calculator.application.helpers.TimeHelper;
import com.paymeter.parking_calculator.domain.model.Parking;
import org.springframework.stereotype.Component;

@Component("maxDaily15Strategy")
public class MaxDaily15Strategy implements DiscountStrategy {

    private static final int MAX_DAILY_PRICE = 15;

    @Override
    public double calculateFinalPrice(Parking parking, long totalMinutes) {
        return CalculatorPriceHelper.getTotalPriceWithMaxTime(
                parking.getHourlyRate(),
                TimeHelper.calculateTotalHours(totalMinutes),
                Hours.TWENTY_FOUR_HOURS,
                MAX_DAILY_PRICE);
    }

}
