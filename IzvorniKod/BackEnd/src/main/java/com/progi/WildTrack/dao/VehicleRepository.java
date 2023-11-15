package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Object> findByVehicleType(String i);


}
