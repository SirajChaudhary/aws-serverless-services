package com.example.shared.dto;

import java.util.List;

/**
 * ============================================================================
 * RegisterVehicleRequest
 *
 * Request received from the client to register a vehicle.
 *
 * Responsibilities:
 * - Capture vehicle owner details
 * - Capture vehicle information
 * - Capture uploaded document names
 * ============================================================================
 */
public record RegisterVehicleRequest(

        String ownerName,

        String vehicleNumber,

        String vehicleType,

        String manufacturer,

        String model,

        Integer manufacturingYear,

        List<String> documents

) {
}