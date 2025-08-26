package com.paymeter.parking_calculator.infrastructure.controller;

import com.paymeter.parking_calculator.application.dto.CalculatePriceRequest;
import com.paymeter.parking_calculator.application.dto.CalculatePriceResponse;
import com.paymeter.parking_calculator.domain.model.ParkingCalculation;
import com.paymeter.parking_calculator.domain.port.ParkingCalculatorPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Parking Calculator", description = "API for calculating parking prices")
public class ParkingCalculatorController {

    private final ParkingCalculatorPort parkingCalculatorPort;

    @Operation(
            summary = "Calculate parking price",
            description = "Calculates the parking price based on duration and parking rules"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful calculation",
                    content = @Content(schema = @Schema(implementation = CalculatePriceResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input parameters"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Parking not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    @PostMapping("/calculate")
    public ResponseEntity<CalculatePriceResponse> calculatePrice(@Valid @RequestBody CalculatePriceRequest request) {
        try {
            return ResponseEntity.ok(getCalculatePriceResponse(getParkingCalculation(request)));
        } catch (Exception e) {
            log.error("Error calculating price: ", e);
            throw e;
        }
    }

    private ParkingCalculation getParkingCalculation(CalculatePriceRequest request) {
        return parkingCalculatorPort.calculatePrice(
                request.getParkingId(),
                request.getFrom(),
                request.getTo()
        );
    }

    private static CalculatePriceResponse getCalculatePriceResponse(ParkingCalculation calculation) {
        var response = CalculatePriceResponse.builder()
                .parkingId(calculation.getParkingId())
                .from(calculation.getFrom())
                .to(calculation.getTo())
                .duration(calculation.getDuration())
                .price(calculation.getPrice())
                .build();
        log.info("Calculation successful: {}", response);
        return response;
    }
}
