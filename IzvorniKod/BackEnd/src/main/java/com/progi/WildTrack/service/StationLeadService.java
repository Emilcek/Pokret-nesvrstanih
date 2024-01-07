package com.progi.WildTrack.service;

import com.progi.WildTrack.dto.ClientUpdateDTO;
import org.springframework.http.ResponseEntity;

public interface StationLeadService {

    ResponseEntity updateClient(ClientUpdateDTO client);

    ResponseEntity assignStationToStationLead(String stationName);

    ResponseEntity assignExplorerToStation(String stationName, String explorerName);

    ResponseEntity removeExplorerFromStation(String stationName, String explorerName);

    ResponseEntity getStation();

}
