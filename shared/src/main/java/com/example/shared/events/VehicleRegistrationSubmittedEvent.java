package com.example.shared.events;

import java.util.List;

/**
 * ============================================================================
 * VehicleRegistrationSubmittedEvent
 *
 * Event published to Amazon EventBridge after a vehicle registration
 * request is submitted.
 *
 * Responsibilities:
 * - Share vehicle registration details
 * - Trigger downstream Lambda functions
 * ============================================================================
 */
public record VehicleRegistrationSubmittedEvent(

        String requestId,

        String ownerName,

        String vehicleNumber,

        List<String> documents

) {
}