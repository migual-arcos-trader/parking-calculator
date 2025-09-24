package com.paymeter.parking_calculator.domain.model;

import com.paymeter.parking_calculator.domain.exception.UnsupportedDiscountException;
import lombok.Getter;

@Getter
public enum DiscountType {

    NO_DISCOUNT("No discount", "noDiscountStrategy"),
    MAX_DAILY_15("Maximum 15€ per day", "maxDaily15Strategy"),
    MAX_12H_20_HOURS_FREE("Maximum 20€ per 12 hours, with hours free", "max12h20HoursFreeStrategy");

    private final String description;
    private final String strategyBeanName;

    DiscountType(String description, String strategyBeanName) {
        this.description = description;
        this.strategyBeanName = strategyBeanName;
    }

    public static DiscountType fromString(String value) {
        for (DiscountType type : DiscountType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new UnsupportedDiscountException("Unknown discount type: " + value);
    }

}
