package com.paymeter.parking_calculator.domain.model;

import lombok.Getter;

@Getter
public enum DiscountType {

    NO_DISCOUNT("No discount"),
    MAX_DAILY_15("Maximum 15€ per day"),
    MAX_12H_20_HOURS_FREE("Maximum 20€ per 12 hours, with hours free");

    private final String description;

    DiscountType(String description) {
        this.description = description;
    }

    public static DiscountType fromString(String value) {
        for (DiscountType type : DiscountType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown discount type: " + value);
    }

}
