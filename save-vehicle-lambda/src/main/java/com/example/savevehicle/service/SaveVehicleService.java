package com.example.savevehicle.service;

import com.example.savevehicle.repository.VehicleRepository;
import com.example.shared.model.VehicleRegistration;

/**
 * ============================================================================
 * SaveVehicleService
 *
 * Contains the business logic for storing vehicle registration details.
 *
 * Responsibilities:
 * - Validate vehicle registration details
 * - Delegate data persistence to the repository
 * ============================================================================
 */
public class SaveVehicleService {

    private final VehicleRepository vehicleRepository =
            new VehicleRepository();

    public void save(VehicleRegistration vehicleRegistration) {

        vehicleRepository.save(vehicleRegistration);

    }

}