package com.progi.WildTrack.controllers;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Station;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.service.ClientService;
import com.progi.WildTrack.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stationLead")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class StationLeadController {

    private final ClientService clientService;
    private final StationService stationService;

    @GetMapping
    public ResponseEntity<ClientDetailsDTO> getStationLead() {
        return ResponseEntity.ok(clientService.getClient());
    }

    @PostMapping
    public ResponseEntity updateStationLead(@RequestBody ClientUpdateDTO client) {
        return clientService.updateClient(client);
    }

    @GetMapping("/myStation")
    public ResponseEntity<Station> getStation() {
        return ResponseEntity.ok(stationService.getStation());
    }

    @GetMapping("/stations")
    public ResponseEntity<List<Station>> getAllStations() {
        return ResponseEntity.ok(stationService.getAllStations());
    }
}
