package com.example.notifyapplicant.service;

import com.example.notifyapplicant.config.SnsConfig;
import com.example.shared.events.VehicleRegistrationSubmittedEvent;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

/**
 * ============================================================================
 * NotificationService
 *
 * Sends notifications using Amazon SNS.
 *
 * Responsibilities:
 * - Build notification messages
 * - Publish notifications to Amazon SNS
 * ============================================================================
 */
public class NotificationService {

    private static final String TOPIC_ARN =
            System.getenv("SNS_TOPIC_ARN");

    private final SnsClient snsClient =
            SnsConfig.getSnsClient();

    public void notifyApplicant(
            VehicleRegistrationSubmittedEvent event) {

        String message = """
                Vehicle Registration Submitted Successfully

                Request Id     : %s
                Owner Name     : %s
                Vehicle Number : %s
                """
                .formatted(
                        event.requestId(),
                        event.ownerName(),
                        event.vehicleNumber()
                );

        PublishRequest request =
                PublishRequest.builder()
                        .topicArn(TOPIC_ARN)
                        .subject("Vehicle Registration")
                        .message(message)
                        .build();

        snsClient.publish(request);

    }

}