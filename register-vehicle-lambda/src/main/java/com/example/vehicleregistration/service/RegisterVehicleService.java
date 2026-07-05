package com.example.vehicleregistration.service;

import com.example.shared.dto.RegisterVehicleRequest;
import com.example.shared.dto.RegisterVehicleResponse;
import com.example.shared.events.VehicleRegistrationSubmittedEvent;
import com.example.vehicleregistration.service.event.EventBridgeService;
import com.example.vehicleregistration.service.s3.S3Service;

import java.util.UUID;

/**
 * ============================================================================
 * RegisterVehicleService
 *
 * Contains the business logic for vehicle registration.
 * ============================================================================
 */
public class RegisterVehicleService {

    private final S3Service s3Service = new S3Service();

    private final EventBridgeService eventBridgeService =
            new EventBridgeService();

    public RegisterVehicleResponse registerVehicle(RegisterVehicleRequest request) {

        for (String document : request.documents()) {

            s3Service.uploadDocument(
                    request.vehicleNumber(),
                    document
            );

        }

        String requestId = UUID.randomUUID().toString();

        VehicleRegistrationSubmittedEvent event =
                new VehicleRegistrationSubmittedEvent(
                        requestId,
                        request.ownerName(),
                        request.vehicleNumber(),
                        request.documents()
                );

        eventBridgeService.publishVehicleRegistrationSubmitted(event);

        return RegisterVehicleResponse.builder()
                .requestId(requestId)
                .build();

    }
}