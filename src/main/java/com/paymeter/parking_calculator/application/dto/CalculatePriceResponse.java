package com.paymeter.parking_calculator.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response with calculated parking price")
public class CalculatePriceResponse {

    @Schema(description = "Parking ID", example = "P000123")
    private String parkingId;

    @Schema(description = "Start time of parking", example = "2024-02-27T09:00:00")
    private LocalDateTime from;

    @Schema(description = "End time of parking", example = "2024-02-27T12:00:00")
    private LocalDateTime to;

    @Schema(description = "Duration in minutes", example = "180")
    private Integer duration;

    @Schema(description = "Formatted price with currency", example = "6.00 EUR")
    private String price;

}