package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.StationLeadRepository;
import com.progi.WildTrack.dao.StationRepository;
import com.progi.WildTrack.domain.Station;
import com.progi.WildTrack.domain.StationLead;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.service.StationLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StationLeadServiceImpl implements StationLeadService {
    @Autowired
    private StationLeadRepository stationLeadRepo;

    @Autowired
    private StationRepository stationRepo;

    public ResponseEntity updateClient(ClientUpdateDTO client){

        StationLead stationLead = stationLeadRepo.findByStationLeadName(client.getClientName());
        if (stationLead != null) {
            stationLead.setStationLeadName(client.getClientName());
            Station station = stationRepo.findByStationName(client.getStationName());
            if (station != null) {
                stationLead.setStation(station);
                stationLeadRepo.save(stationLead);
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
