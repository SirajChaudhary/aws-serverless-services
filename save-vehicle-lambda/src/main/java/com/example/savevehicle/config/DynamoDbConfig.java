package com.example.savevehicle.config;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * ============================================================================
 * DynamoDbConfig
 *
 * Creates the Amazon DynamoDB client.
 *
 * Responsibilities:
 * - Create a shared Amazon DynamoDB client
 * - Provide Amazon DynamoDB client across the application
 * ============================================================================
 */
public final class DynamoDbConfig {

    private static final DynamoDbClient DYNAMO_DB_CLIENT =
            DynamoDbClient.builder().build();

    private DynamoDbConfig() {
    }

    public static DynamoDbClient getDynamoDbClient() {
        return DYNAMO_DB_CLIENT;
    }

}