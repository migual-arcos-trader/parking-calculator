package com.paymeter.parking_calculator.infrastructure.repository;

import com.paymeter.parking_calculator.infrastructure.entity.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingJpaRepository extends JpaRepository<ParkingEntity, String> {
}
