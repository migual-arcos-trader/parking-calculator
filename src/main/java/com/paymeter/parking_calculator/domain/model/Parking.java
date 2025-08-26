package com.paymeter.parking_calculator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parking {

    private String id;
    private Double hourlyRate;
    private String discountName;

}
