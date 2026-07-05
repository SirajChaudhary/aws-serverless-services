package com.example.vehicleregistration.config;

import software.amazon.awssdk.services.s3.S3Client;

/**
 * ============================================================================
 * S3Config
 *
 * Creates the Amazon S3 client.
 *
 * Responsibilities:
 * - Create a shared Amazon S3 client
 * - Provide Amazon S3 client across the application
 * ============================================================================
 */
public final class S3Config {

    private static final S3Client S3_CLIENT =
            S3Client.builder().build();

    private S3Config() {
    }

    public static S3Client getS3Client() {
        return S3_CLIENT;
    }

}