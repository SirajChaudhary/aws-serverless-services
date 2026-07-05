package com.example.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ============================================================================
 * RegisterVehicleResponse
 *
 * Response returned after a successful vehicle registration request.
 *
 * Responsibilities:
 * - Return generated request ID
 * ============================================================================
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVehicleResponse {

    private String requestId;
}