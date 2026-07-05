package com.example.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ============================================================================
 * ApiResponse
 *
 * Standard API response returned by all Lambda functions.
 *
 * Responsibilities:
 * - Indicate request status
 * - Return response message
 * - Return response data
 * ============================================================================
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;

    private String message;

    private T data;
}