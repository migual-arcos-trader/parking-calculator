package com.paymeter.parking_calculator.domain.service.discount;

import com.paymeter.parking_calculator.application.helpers.TimeHelper;
import com.paymeter.parking_calculator.domain.model.Parking;
import org.springframework.stereotype.Component;


@Component("noDiscountStrategy")
public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculateFinalPrice(Parking parking, long totalMinutes) {
        return TimeHelper.calculateTotalHours(totalMinutes) * parking.getHourlyRate();
    }

}
