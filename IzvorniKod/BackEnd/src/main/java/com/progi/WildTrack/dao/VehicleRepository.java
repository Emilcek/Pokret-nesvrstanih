package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
