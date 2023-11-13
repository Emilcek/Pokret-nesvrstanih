package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.VehicleRepository;
import com.progi.WildTrack.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepo;
}
