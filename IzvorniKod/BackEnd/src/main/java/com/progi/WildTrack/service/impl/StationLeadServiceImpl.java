package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ExplorerRepository;
import com.progi.WildTrack.dao.StationLeadRepository;
import com.progi.WildTrack.dao.StationRepository;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Explorer;
import com.progi.WildTrack.domain.Station;
import com.progi.WildTrack.domain.StationLead;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.service.StationLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationLeadServiceImpl implements StationLeadService {
    @Autowired
    private StationLeadRepository stationLeadRepo;

    @Autowired
    private StationRepository stationRepo;

    @Autowired
    private ExplorerRepository explorerRepo;

    public ResponseEntity assignStationToStationLead(String stationName){
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StationLead stationLead = stationLeadRepo.findByStationLeadName(client.getClientName());
        Station station = stationRepo.findByStationName(stationName);
        if (stationLead != null && station != null) {
            station.setStationStatus("active");
            stationRepo.save(station);
            stationLead.setStation(station);
            stationLeadRepo.save(stationLead);
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    public boolean assignStationToStationLeadByName(String stationLeadName, String stationName) {
        StationLead stationLead = stationLeadRepo.findByStationLeadName(stationLeadName);
        Station station = stationRepo.findByStationName(stationName);
        if (stationLead != null && station != null) {
            station.setStationStatus("active");
            stationRepo.save(station);
            stationLead.setStation(station);
            stationLeadRepo.save(stationLead);
        } else {
            return false;
        }
        return true;
    }

    public ResponseEntity assignExplorerToStation(String stationName, String explorerName){
        Station station = stationRepo.findByStationName(stationName);
        Explorer explorer = explorerRepo.findByExplorerName(explorerName);
        if (station != null && explorer != null) {
            List<Explorer> explorers = station.getExplorers();
            explorers.add(explorer);
            station.setExplorers(explorers);
            stationRepo.save(station);
            explorer.setStation(station);
            explorerRepo.save(explorer);
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity removeExplorerFromStation(String stationName, String explorerName){
        Station station = stationRepo.findByStationName(stationName);
        Explorer explorer = explorerRepo.findByExplorerName(explorerName);
        if (station != null && explorer != null) {
            List<Explorer> explorers = station.getExplorers();
            explorers.remove(explorer);
            station.setExplorers(explorers);
            stationRepo.save(station);
            explorer.setStation(null);
            explorerRepo.save(explorer);
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity getStation(){
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StationLead stationLead = stationLeadRepo.findByStationLeadName(client.getClientName());
        if (stationLead != null) {
            Station station = stationLead.getStation();
            if (station != null) {
                return ResponseEntity.ok(station);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
