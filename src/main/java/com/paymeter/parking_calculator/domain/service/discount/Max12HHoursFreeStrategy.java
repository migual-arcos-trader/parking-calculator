package com.paymeter.parking_calculator.domain.service.discount;

import com.paymeter.parking_calculator.application.commons.constants.Hours;
import com.paymeter.parking_calculator.application.helpers.CalculatorPriceHelper;
import com.paymeter.parking_calculator.application.helpers.TimeHelper;
import com.paymeter.parking_calculator.domain.model.DiscountType;
import com.paymeter.parking_calculator.domain.model.Parking;
import org.springframework.stereotype.Component;


@Component
public class Max12HHoursFreeStrategy implements DiscountStrategy {

    public static final double INIT_TOTAL_PRICE = 0.0;
    private static final int HOURS_FREE = 1;
    private static final int MAX_12H_PRICE = 20;

    @Override
    public double calculateFinalPrice(Parking parking, long totalMinutes) {
        double totalHours = TimeHelper.calculateTotalHours(totalMinutes);
        double totalPrice = INIT_TOTAL_PRICE;
        if (totalHours > HOURS_FREE) {
            totalHours -= HOURS_FREE;
            totalPrice = CalculatorPriceHelper.getTotalPriceWithMaxTime(
                    parking.getHourlyRate(),
                    totalHours,
                    Hours.TWELVE_HOURS,
                    MAX_12H_PRICE);
        }
        return totalPrice;
    }

    @Override
    public boolean supports(String discountType) {
        return DiscountType.MAX_12H_20_HOURS_FREE.name().equalsIgnoreCase(discountType);
    }

}
