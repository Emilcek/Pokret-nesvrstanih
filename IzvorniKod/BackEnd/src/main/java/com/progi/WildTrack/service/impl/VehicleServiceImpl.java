package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.VehicleRepository;
import com.progi.WildTrack.domain.Explorer;
import com.progi.WildTrack.domain.Vehicle;
import com.progi.WildTrack.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepo;

    @Override
    public void createVehicle(String vehicleType) {
        if (vehicleRepo.findByVehicleType(vehicleType).isPresent()) {
            return;
        }
        Vehicle vehicle = Vehicle.builder()
                .vehicleType(vehicleType)
                .build();
        vehicleRepo.save(vehicle);
    }

    public void addExplorerToVehicle(Vehicle vehicle, Explorer explorer) {
        System.out.println("add explorer: " + vehicle.getVehicleType() + " " + explorer);
        vehicle.getExplorers().add(explorer);
        vehicleRepo.save(vehicle);
    }
}
