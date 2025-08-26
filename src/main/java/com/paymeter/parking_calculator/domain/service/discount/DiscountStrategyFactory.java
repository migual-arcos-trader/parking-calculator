package com.paymeter.parking_calculator.domain.service.discount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscountStrategyFactory {

    private final List<DiscountStrategy> strategies;

    public DiscountStrategy getStrategy(String discountType) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(discountType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported discount type: " + discountType));
    }

}