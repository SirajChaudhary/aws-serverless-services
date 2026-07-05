package com.example.vehicleregistration.config;

import software.amazon.awssdk.services.eventbridge.EventBridgeClient;

/**
 * ============================================================================
 * EventBridgeConfig
 *
 * Creates the Amazon EventBridge client.
 *
 * Responsibilities:
 * - Create a shared EventBridge client
 * - Provide EventBridge client across the application
 * ============================================================================
 */
public final class EventBridgeConfig {

    private static final EventBridgeClient EVENT_BRIDGE_CLIENT =
            EventBridgeClient.builder().build();

    private EventBridgeConfig() {
    }

    public static EventBridgeClient getEventBridgeClient() {
        return EVENT_BRIDGE_CLIENT;
    }

}