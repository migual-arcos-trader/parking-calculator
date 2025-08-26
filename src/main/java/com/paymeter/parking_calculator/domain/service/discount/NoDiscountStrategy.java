package com.paymeter.parking_calculator.domain.service.discount;

import com.paymeter.parking_calculator.application.utils.TimeUtil;
import com.paymeter.parking_calculator.domain.model.DiscountType;
import com.paymeter.parking_calculator.domain.model.Parking;
import org.springframework.stereotype.Component;


@Component
public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculateFinalPrice(Parking parking, long totalMinutes) {
        return TimeUtil.calculateTotalHours(totalMinutes) * parking.getHourlyRate();
    }

    @Override
    public boolean supports(String discountType) {
        return DiscountType.NO_DISCOUNT.name().equalsIgnoreCase(discountType);
    }

}
