package com.paymeter.parking_calculator.domain.service.discount;

import com.paymeter.parking_calculator.domain.model.DiscountType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DiscountStrategyFactory {

    private final ApplicationContext applicationContext;
    private final Map<DiscountType, DiscountStrategy> strategyMap = new EnumMap<>(DiscountType.class);

    @PostConstruct
    public void init() {
        for (DiscountType discountType : DiscountType.values()) {
            DiscountStrategy strategy = applicationContext.getBean(discountType.getStrategyBeanName(), DiscountStrategy.class);
            strategyMap.put(discountType, strategy);
        }
    }

    public DiscountStrategy getStrategy(String discountTypeName) {
        return strategyMap.get(DiscountType.fromString(discountTypeName));
    }

}
