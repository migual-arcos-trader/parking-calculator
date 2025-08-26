package com.paymeter.parking_calculator.domain.service.discount;

import com.paymeter.parking_calculator.domain.model.Parking;

public interface DiscountStrategy {

    double calculateFinalPrice(Parking parking, long totalMinutes);

    boolean supports(String discountType);

}
