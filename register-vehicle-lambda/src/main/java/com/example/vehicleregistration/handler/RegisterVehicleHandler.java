package com.example.vehicleregistration.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.shared.dto.ApiResponse;
import com.example.shared.dto.RegisterVehicleRequest;
import com.example.shared.dto.RegisterVehicleResponse;
import com.example.shared.util.JsonUtil;
import com.example.vehicleregistration.service.RegisterVehicleService;

/**
 * ============================================================================
 * RegisterVehicleHandler
 *
 * AWS Lambda entry point for vehicle registration requests.
 *
 * Responsibilities:
 * - Receive requests from Amazon API Gateway
 * - Convert JSON request to DTO
 * - Delegate request processing to the service layer
 * - Return API Gateway compatible response
 * ============================================================================
 */
public class RegisterVehicleHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final RegisterVehicleService registerVehicleService =
            new RegisterVehicleService();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(
            APIGatewayProxyRequestEvent event,
            Context context) {

        try {

            context.getLogger().log("Received vehicle registration request.");

            RegisterVehicleRequest request =
                    JsonUtil.OBJECT_MAPPER.readValue(
                            event.getBody(),
                            RegisterVehicleRequest.class);

            RegisterVehicleResponse response =
                    registerVehicleService.registerVehicle(request);

            ApiResponse<RegisterVehicleResponse> apiResponse =
                    ApiResponse.<RegisterVehicleResponse>builder()
                            .success(true)
                            .message("Vehicle registration request submitted successfully.")
                            .data(response)
                            .build();

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(JsonUtil.OBJECT_MAPPER.writeValueAsString(apiResponse));

        } catch (Exception exception) {

            context.getLogger().log(exception.getMessage());

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("""
                            {
                              "message":"Internal Server Error"
                            }
                            """);
        }

    }

}