package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.StationRepository;
import com.progi.WildTrack.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationRepository stationRepo;
}
