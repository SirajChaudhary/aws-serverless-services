package com.example.notifyapplicant.config;

import software.amazon.awssdk.services.sns.SnsClient;

/**
 * ============================================================================
 * SnsConfig
 *
 * Creates the Amazon SNS client.
 *
 * Responsibilities:
 * - Create a shared Amazon SNS client
 * - Provide Amazon SNS client across the application
 * ============================================================================
 */
public final class SnsConfig {

    private static final SnsClient SNS_CLIENT =
            SnsClient.builder().build();

    private SnsConfig() {
    }

    public static SnsClient getSnsClient() {
        return SNS_CLIENT;
    }

}