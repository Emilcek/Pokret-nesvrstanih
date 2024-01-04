package com.progi.WildTrack.controllers;

import com.progi.WildTrack.domain.Station;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.service.ClientService;
import com.progi.WildTrack.service.StationLeadService;
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
    private final StationLeadService stationLeadService;

    @GetMapping
    public ResponseEntity<ClientDetailsDTO> getStationLead() {
        return ResponseEntity.ok(clientService.getClient());
    }

    @PutMapping
    public ResponseEntity updateStationLead(@ModelAttribute ClientUpdateDTO clientUpdateDTO) {
        System.out.println("StationLeadController.updateStationLead" + clientUpdateDTO);
        return stationLeadService.updateClient(clientUpdateDTO);
    }

    @GetMapping("/stations")
    public ResponseEntity<List<Station>> getAllStations() {
        return ResponseEntity.ok(stationService.getAllStations());
    }

    @GetMapping("/availableExplorers")
    public ResponseEntity<List<ClientDetailsDTO>> getAllAvailableExplorers() {
        return ResponseEntity.ok(clientService.getAllAvailableExplorers());
    }
}
