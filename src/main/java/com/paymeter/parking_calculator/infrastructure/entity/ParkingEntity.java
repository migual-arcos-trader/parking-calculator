package com.paymeter.parking_calculator.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parkings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingEntity {

    @Id
    private String id;
    private Double hourlyRate;
    private String discountName;

}
