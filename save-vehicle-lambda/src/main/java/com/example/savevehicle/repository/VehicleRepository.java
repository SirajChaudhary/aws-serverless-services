package com.example.savevehicle.repository;

import com.example.savevehicle.config.DynamoDbConfig;
import com.example.shared.model.VehicleRegistration;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================================
 * VehicleRepository
 *
 * Stores vehicle registration details in Amazon DynamoDB.
 *
 * Responsibilities:
 * - Save vehicle registration details
 * - Persist registration records in Amazon DynamoDB
 * ============================================================================
 */
public class VehicleRepository {

    private static final String TABLE_NAME =
            System.getenv("VEHICLE_TABLE");

    private final DynamoDbClient dynamoDbClient =
            DynamoDbConfig.getDynamoDbClient();

    public void save(VehicleRegistration vehicleRegistration) {

        Map<String, AttributeValue> item = new HashMap<>();

        item.put(
                "requestId",
                AttributeValue.fromS(vehicleRegistration.getRequestId()));

        item.put(
                "ownerName",
                AttributeValue.fromS(vehicleRegistration.getOwnerName()));

        item.put(
                "vehicleNumber",
                AttributeValue.fromS(vehicleRegistration.getVehicleNumber()));

        PutItemRequest request =
                PutItemRequest.builder()
                        .tableName(TABLE_NAME)
                        .item(item)
                        .build();

        dynamoDbClient.putItem(request);

    }

}