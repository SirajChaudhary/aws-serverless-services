package com.example.notifyapplicant.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.example.notifyapplicant.service.NotificationService;
import com.example.shared.events.VehicleRegistrationSubmittedEvent;
import com.example.shared.util.JsonUtil;

/**
 * ============================================================================
 * NotifyApplicantHandler
 *
 * AWS Lambda entry point for vehicle registration events.
 *
 * Responsibilities:
 * - Receive events from Amazon EventBridge
 * - Convert event payload to domain model
 * - Delegate notification processing to the service layer
 * ============================================================================
 */
public class NotifyApplicantHandler
        implements RequestHandler<ScheduledEvent, Void> {

    private final NotificationService notificationService =
            new NotificationService();

    @Override
    public Void handleRequest(
            ScheduledEvent event,
            Context context) {

        VehicleRegistrationSubmittedEvent submittedEvent =
                JsonUtil.OBJECT_MAPPER.convertValue(
                        event.getDetail(),
                        VehicleRegistrationSubmittedEvent.class);

        notificationService.notifyApplicant(submittedEvent);

        context.getLogger().log(
                "Notification sent successfully.");

        return null;

    }

}