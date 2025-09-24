# Adding New Discount Types

## Overview

This document explains how to add new discount strategies to the Parking Calculator system. The architecture follows the
Strategy Pattern for easy extensibility.

## Step-by-Step Process

### 1. Add New Discount Type to Enum

[🎯 DiscountType.java](../src/main/java/com/paymeter/parking_calculator/domain/model/DiscountType.java)

```
package com.paymeter.parking_calculator.domain.model;

import com.paymeter.parking_calculator.domain.exception.UnsupportedDiscountException;
import lombok.Getter;

@Getter
public enum DiscountType {

    NO_DISCOUNT("No discount", "noDiscountStrategy"),
    MAX_DAILY_15("Maximum 15€ per day", "maxDaily15Strategy"),
    MAX_12H_20_HOURS_FREE("Maximum 20€ per 12 hours, with hours free", "max12h20HoursFreeStrategy");,
    // Add the new discount type with description and bean name
    // The bean is the hey for the EnumMap in the DiscountFactory
    NEW_DISCOUNT_TYPE_STRATEGY("New discount type strategy description", "newDiscountTypeStrategy");

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

```

### 2. Create Discount Strategy Implementation

From [🎪 DiscountStrategy.java](../src/main/java/com/paymeter/parking_calculator/domain/service/discount/DiscountStrategy.java)

Create the new implementation

```
package com.paymeter.parking_calculator.domain.service.discount;

import com.paymeter.parking_calculator.application.commons.constants.Hours;
import com.paymeter.parking_calculator.application.helpers.CalculatorPriceHelper;
import com.paymeter.parking_calculator.application.helpers.TimeHelper;
import com.paymeter.parking_calculator.domain.model.Parking;
import org.springframework.stereotype.Component;

// Bean name is mandatory
@Component("newDiscountTypeStrategy")
public class NewDiscountTypeStrategy implements DiscountStrategy {

    // Add varaibles and constants

    @Override
    public double calculateFinalPrice(Parking parking, long totalMinutes) {
        double totalHours = Math.ceil(totalMinutes / 60.0);
        double basePrice = totalHours * parking.getHourlyRate();
        
        // Add the strategy to calculate the tinal price
        
        return Final Price;
    }
}
```

### 3. Update Database with New Parking Configuration

#### For new parking lots at [📊 data.sql](../src/main/resources/data.sql) file:

```
INSERT INTO parkings (id, hourly_rate, discount_name) 
VALUES ('P999999', 5.00, 'NEW_DISCOUNT_TYPE_STRATEGY')
ON CONFLICT (id) DO NOTHING;
```

#### To update existing parking lots at [📊 data.sql](../src/main/resources/data.sql) file:

```
UPDATE parkings 
SET discount_name = 'NEW_DISCOUNT_TYPE_STRATEGY', 
    hourly_rate = 5.00
WHERE id = 'P000123';
```

### 4. Database Schema Reference at [📊 schema.sql](../src/main/resources/schema.sql)

```
CREATE TABLE parkings (
    id VARCHAR(20) PRIMARY KEY,
    hourly_rate NUMERIC(10, 2) NOT NULL,
    discount_name VARCHAR(50) NOT NULL
);
```

## Testing the New Discount

1. **Unit Tests**: Create tests for your new strategy, for example:

[👁‍ Max12HHoursFreeStrategyTest.java](../src/test/java/com/paymeter/parking_calculator/domain/service/discount/Max12HHoursFreeStrategyTest.java)

Applying

- Object mothers
    - [👩‍🍼 DateTimeMother.java](../src/test/java/com/paymeter/parking_calculator/domain/service/model/mothers/DateTimeMother.java)
    - [👩‍🍼 ParkingMother.java](../src/test/java/com/paymeter/parking_calculator/domain/service/model/mothers/ParkingMother.java)
- Constants
    - [👩‍🍼 TestConstants.java](../src/test/java/com/paymeter/parking_calculator/domain/service/model/constants/TestConstants.java)
- Border cases

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
