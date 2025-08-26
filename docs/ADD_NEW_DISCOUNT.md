# Adding New Discount Types

## Overview
This document explains how to add new discount strategies to the Parking Calculator system. The architecture follows the Strategy Pattern for easy extensibility.

## Step-by-Step Process

### 1. Add New Discount Type to Enum

```
package com.paymeter.parking_calculator.domain.model;

import lombok.Getter;

@Getter
public enum DiscountType {

    NO_DISCOUNT("No discount"),
    MAX_DAILY_15("Maximum 15€ per day"), 
    MAX_12H_20_HOURS_FREE("Maximum 20€ per 12 hours, with hours free"),
    NEW_DISCOUNT_TYPE_STRATEGY("New discount type strategy description");

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
```

### 2. Create Discount Strategy Implementation

```
package com.paymeter.parking_calculator.domain.service.discount;

import com.paymeter.parking_calculator.domain.model.Parking;
import org.springframework.stereotype.Component;

@Component
public class NewDiscountTypeStrategy implements DiscountStrategy {

    private static final int MAX_HOURLY_RATE = 50;
    private static final int FREE_HOURS = 2;

    @Override
    public double calculateFinalPrice(Parking parking, long totalMinutes) {
        double totalHours = Math.ceil(totalMinutes / 60.0);
        double basePrice = totalHours * parking.getHourlyRate();
        
        // Example: First 2 hours free, then normal rate with max cap
        double freeHours = Math.min(FREE_HOURS, totalHours);
        double paidHours = Math.max(0, totalHours - freeHours);
        double calculatedPrice = paidHours * parking.getHourlyRate();
        
        return Math.min(calculatedPrice, MAX_HOURLY_RATE * paidHours);
    }

    @Override
    public boolean supports(String discountType) {
        return DiscountType.NEW_DISCOUNT_TYPE_STRATEGY.name().equalsIgnoreCase(discountType);
    }
}
```

### 3. Update Database with New Parking Configuration

#### For new parking lots:
```
INSERT INTO parkings (id, hourly_rate, discount_name) 
VALUES ('P999999', 5.00, 'NEW_DISCOUNT_TYPE_STRATEGY')
ON CONFLICT (id) DO NOTHING;
```

#### To update existing parking lots:
```
UPDATE parkings 
SET discount_name = 'NEW_DISCOUNT_TYPE_STRATEGY', 
    hourly_rate = 5.00
WHERE id = 'P000123';
```

### 4. Database Schema Reference

```
CREATE TABLE parkings (
    id VARCHAR(20) PRIMARY KEY,
    hourly_rate NUMERIC(10, 2) NOT NULL,
    discount_name VARCHAR(50) NOT NULL
);
```

## Testing the New Discount

1. **Unit Tests**: Create tests for your new strategy
2. **Integration Test**: Verify the full flow
3. **Database Test**: Ensure SQL commands work correctly

## Best Practices

1. **Naming**: Use descriptive names for discount types
2. **Constants**: Define magic numbers as constants
3. **Testing**: Write comprehensive unit tests
4. **Documentation**: Update this guide with real examples
5. **Validation**: Add input validation in strategy methods

## Common Discount Patterns

- **Time-based**: Free hours, then paid
- **Cap-based**: Maximum charge per period
- **Tiered**: Different rates for different time blocks
- **Conditional**: Discounts based on specific conditions

## Troubleshooting

- Verify the discount name matches exactly between enum and strategy
- Check Spring component scanning includes the new strategy
- Ensure database migrations are applied correctly
- Verify unit tests cover edge cases