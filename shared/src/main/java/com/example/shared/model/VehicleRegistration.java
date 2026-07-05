package com.example.shared.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ============================================================================
 * VehicleRegistration
 *
 * Represents a vehicle registration stored in Amazon DynamoDB.
 *
 * Responsibilities:
 * - Store vehicle registration details
 * - Persist registration data for future retrieval
 * ============================================================================
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRegistration {

    private String requestId;

    private String ownerName;

    private String vehicleNumber;

    private List<String> documents;

}