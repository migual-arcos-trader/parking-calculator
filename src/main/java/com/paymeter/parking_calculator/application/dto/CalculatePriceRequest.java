package com.paymeter.parking_calculator.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request for calculating parking price")
public class CalculatePriceRequest {

    @NotNull(message = "parkingId is required")
    @Schema(description = "Parking ID", example = "P000123")
    private String parkingId;

    @NotNull(message = "from is required")
    @Schema(description = "Start time of parking", example = "2024-02-27T09:00:00")
    private LocalDateTime from;

    @Schema(description = "End time of parking (optional, defaults to current time)",
            example = "2024-02-27T12:00:00")
    private LocalDateTime to;

}
