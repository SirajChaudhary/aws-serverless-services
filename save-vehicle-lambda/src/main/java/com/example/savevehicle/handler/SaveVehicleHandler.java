package com.example.savevehicle.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.example.savevehicle.service.SaveVehicleService;
import com.example.shared.events.VehicleRegistrationSubmittedEvent;
import com.example.shared.model.VehicleRegistration;
import com.example.shared.util.JsonUtil;

/**
 * ============================================================================
 * SaveVehicleHandler
 *
 * AWS Lambda entry point for vehicle registration events.
 *
 * Responsibilities:
 * - Receive events from Amazon EventBridge
 * - Convert event payload to domain model
 * - Delegate request processing to the service layer
 * ============================================================================
 */
public class SaveVehicleHandler
        implements RequestHandler<ScheduledEvent, Void> {

    private final SaveVehicleService saveVehicleService =
            new SaveVehicleService();

    @Override
    public Void handleRequest(
            ScheduledEvent event,
            Context context) {

        VehicleRegistrationSubmittedEvent submittedEvent =
                JsonUtil.OBJECT_MAPPER.convertValue(
                        event.getDetail(),
                        VehicleRegistrationSubmittedEvent.class);

        VehicleRegistration vehicleRegistration =
                VehicleRegistration.builder()
                        .requestId(submittedEvent.requestId())
                        .ownerName(submittedEvent.ownerName())
                        .vehicleNumber(submittedEvent.vehicleNumber())
                        .documents(submittedEvent.documents())
                        .build();

        saveVehicleService.save(vehicleRegistration);

        context.getLogger().log(
                "Vehicle registration saved successfully.");

        return null;

    }

}