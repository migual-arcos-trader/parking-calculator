package com.paymeter.parking_calculator;

import org.springframework.boot.SpringApplication;

public class TestParkingCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.from(ParkingCalculatorApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
