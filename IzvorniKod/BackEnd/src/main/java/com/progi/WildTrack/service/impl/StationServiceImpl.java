package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.StationRepository;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Station;
import com.progi.WildTrack.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationRepository stationRepo;

    @Override
    public Station getStation() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return stationRepo.findByStationLead(client.getStationLead());
    }

    @Override
    public List<Station> getAllStations() {
        return stationRepo.findAll();
    }
}
