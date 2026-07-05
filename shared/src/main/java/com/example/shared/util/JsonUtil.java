package com.example.shared.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ============================================================================
 * JsonUtil
 *
 * Provides a shared ObjectMapper instance for JSON serialization and
 * deserialization across all Lambda functions.
 *
 * Responsibilities:
 * - Serialize Java objects to JSON
 * - Deserialize JSON to Java objects
 * ============================================================================
 */
public final class JsonUtil {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtil() {
    }

}