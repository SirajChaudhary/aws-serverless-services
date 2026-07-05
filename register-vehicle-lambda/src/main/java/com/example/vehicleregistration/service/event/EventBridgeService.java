package com.example.vehicleregistration.service.event;

import com.example.shared.events.VehicleRegistrationSubmittedEvent;
import com.example.shared.util.JsonUtil;
import com.example.vehicleregistration.config.EventBridgeConfig;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequest;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry;

/**
 * ============================================================================
 * EventBridgeService
 *
 * Publishes application events to Amazon EventBridge.
 *
 * Responsibilities:
 * - Publish vehicle registration events
 * - Trigger downstream Lambda functions
 * ============================================================================
 */
public class EventBridgeService {

    private static final String EVENT_BUS_NAME =
            System.getenv("EVENT_BUS_NAME");

    private final EventBridgeClient eventBridgeClient =
            EventBridgeConfig.getEventBridgeClient();

    public void publishVehicleRegistrationSubmitted(
            VehicleRegistrationSubmittedEvent event) {

        try {

            PutEventsRequestEntry eventEntry =
                    PutEventsRequestEntry.builder()
                            .eventBusName(EVENT_BUS_NAME)
                            .source("vehicle.registration")
                            .detailType("VehicleRegistrationSubmitted")
                            .detail(JsonUtil.OBJECT_MAPPER.writeValueAsString(event))
                            .build();

            PutEventsRequest request =
                    PutEventsRequest.builder()
                            .entries(eventEntry)
                            .build();

            eventBridgeClient.putEvents(request);

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Failed to publish event to Amazon EventBridge.",
                    exception);

        }

    }

}