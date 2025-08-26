package com.paymeter.parking_calculator.domain.service.discount;

import com.paymeter.parking_calculator.application.commons.constants.Hours;
import com.paymeter.parking_calculator.application.utils.CalculatorPriceUtil;
import com.paymeter.parking_calculator.application.utils.TimeUtil;
import com.paymeter.parking_calculator.domain.model.DiscountType;
import com.paymeter.parking_calculator.domain.model.Parking;
import org.springframework.stereotype.Component;

@Component
public class MaxDaily15Strategy implements DiscountStrategy {

    private static final int MAX_DAILY_PRICE = 15;

    @Override
    public double calculateFinalPrice(Parking parking, long totalMinutes) {
        return CalculatorPriceUtil.getTotalPriceWithMaxTime(
                parking.getHourlyRate(),
                TimeUtil.calculateTotalHours(totalMinutes),
                Hours.TWENTY_FOUR_HOURS,
                MAX_DAILY_PRICE);
    }

    @Override
    public boolean supports(String discountType) {
        return DiscountType.MAX_DAILY_15.name().equalsIgnoreCase(discountType);
    }

}
