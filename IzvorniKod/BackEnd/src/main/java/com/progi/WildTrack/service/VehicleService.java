package com.progi.WildTrack.service;

import com.progi.WildTrack.domain.Description;
import com.progi.WildTrack.domain.Explorer;
import com.progi.WildTrack.domain.Vehicle;

public interface VehicleService {
    void createVehicle(String vehicleType);

    void addExplorerToVehicle(Vehicle vehicle, Explorer explorer);

    void removeExplorerFromVehicle(Vehicle vehicle, Explorer explorer);
}
