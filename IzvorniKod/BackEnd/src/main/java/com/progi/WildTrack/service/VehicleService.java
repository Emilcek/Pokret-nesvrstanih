package com.progi.WildTrack.service;

import com.progi.WildTrack.domain.Description;
import com.progi.WildTrack.domain.Explorer;

public interface VehicleService {
    void createVehicle(String vehicleType);

    void addExplorerToVehicle(Long vehicleId, Explorer explorer);
}
