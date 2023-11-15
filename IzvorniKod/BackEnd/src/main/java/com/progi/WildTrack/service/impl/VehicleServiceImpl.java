package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.VehicleRepository;
import com.progi.WildTrack.domain.Explorer;
import com.progi.WildTrack.domain.Vehicle;
import com.progi.WildTrack.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepo;

    @Override
    public void createVehicle(String vehicleType) {
        Vehicle vehicle = Vehicle.builder()
                .vehicleType(vehicleType)
                .build();
        vehicleRepo.save(vehicle);
    }

    public void addExplorerToVehicle(Long vehicleId, Explorer explorer) {
        Optional<Vehicle> optionalVehicle = vehicleRepo.findById(vehicleId);
        optionalVehicle.ifPresent(vehicle -> {
            vehicle.getExplorers().add(explorer);
            vehicleRepo.save(vehicle);
        });
    }
}
