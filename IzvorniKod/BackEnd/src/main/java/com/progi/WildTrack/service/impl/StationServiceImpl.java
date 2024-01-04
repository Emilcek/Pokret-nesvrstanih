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

    public void createStations() {
        Station station1 = Station.builder()
                .stationName("Postaja Sljeme")
                .stationStatus("Inactive")
                .stationId(1L)
                .stationLocation("45.925800, 15.975462")
                .radius(100)
                .build();
        Station station2 = Station.builder()
                .stationName("Postaja Učka")
                .stationStatus("Inactive")
                .stationId(2L)
                .stationLocation("45.311442, 14.213432")
                .radius(100)
                .build();
        Station station3 = Station.builder()
                .stationName("Postaja Velebit")
                .stationStatus("Inactive")
                .stationId(3L)
                .stationLocation("44.527842, 15.372215")
                .radius(100)
                .build();
        Station station4 = Station.builder()
                .stationName("Postaja Papuk")
                .stationStatus("Inactive")
                .stationId(4L)
                .stationLocation("45.568000, 17.626000")
                .radius(100)
                .build();
        Station station5 = Station.builder()
                .stationName("Postaja Biokovo")
                .stationStatus("Inactive")
                .stationId(5L)
                .stationLocation("43.333333, 17.183333")
                .radius(100)
                .build();
        Station station6 = Station.builder()
                .stationName("Postaja Risnjak")
                .stationStatus("Inactive")
                .stationId(6L)
                .stationLocation("45.433333, 14.666667")
                .radius(100)
                .build();
        Station station7 = Station.builder()
                .stationName("Postaja Kopački rit")
                .stationStatus("Inactive")
                .stationId(7L)
                .stationLocation("45.433333, 18.966667")
                .radius(100)
                .build();

        stationRepo.save(station1);
        stationRepo.save(station2);
        stationRepo.save(station3);
        stationRepo.save(station4);
        stationRepo.save(station5);
        stationRepo.save(station6);
        stationRepo.save(station7);
    }

    @Override
    public List<Station> getAllStations() {
        return stationRepo.findAll();
    }
}
