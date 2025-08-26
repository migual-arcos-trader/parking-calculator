package com.paymeter.parking_calculator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingCalculation {

    private String parkingId;
    private LocalDateTime from;
    private LocalDateTime to;
    private Integer duration;
    private String price;

}
