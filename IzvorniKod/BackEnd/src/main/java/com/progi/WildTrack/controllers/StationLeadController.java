package com.progi.WildTrack.controllers;

import com.progi.WildTrack.domain.Station;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.ExplorerTaskDTO;
import com.progi.WildTrack.service.*;
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
    private final ExplorerService explorerService;
    private final ActionService actionService;

    @GetMapping
    public ResponseEntity<ClientDetailsDTO> getStationLead() {
        return ResponseEntity.ok(clientService.getClient());
    }

    @GetMapping("/myStation")
    public ResponseEntity<Station> getStation() {
        return ResponseEntity.ok(stationService.getStation());
    }

    @GetMapping("/stations")
    public ResponseEntity<List<Station>> getAllStations() {
        return ResponseEntity.ok(stationService.getAllStations());
    }

    @GetMapping("/requests")
    public ResponseEntity getRequests() {
        return actionService.getStationLeadRequests();
    }

    @GetMapping("/explorers")
    public ResponseEntity getExplorers() {
        return explorerService.getAvailableExplorers();
    }

    @PutMapping("/request/{requestId}/declined")
    public ResponseEntity updateRequest(@PathVariable Long requestId) {
        return actionService.declineRequest(requestId);
    }
    @PutMapping("/request/{requestId}/accepted")
    public ResponseEntity updateRequest(@PathVariable Long requestId,
                                        @RequestBody List<ExplorerTaskDTO> explorerTaskDTO) {
        return actionService.acceptRequest(requestId, explorerTaskDTO);
    }
}
