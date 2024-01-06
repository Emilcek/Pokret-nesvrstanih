package com.progi.WildTrack.controllers;

import com.progi.WildTrack.domain.Station;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.CreateRequestDTO;
import com.progi.WildTrack.service.ActionService;
import com.progi.WildTrack.service.ClientService;
import com.progi.WildTrack.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/researcher")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class ResearcherController {

    private final ClientService clientService;
    private final StationService stationService;
    private final ActionService actionService;

    @GetMapping
    public ResponseEntity<ClientDetailsDTO> getResearcher() {
        return ResponseEntity.ok(clientService.getClient());
    }


    @PostMapping("/request")
    public ResponseEntity createRequest(@RequestBody CreateRequestDTO request) {
        return actionService.createRequest(request);
    }

    @GetMapping("/requests")
    public ResponseEntity getRequests() {
        return actionService.getResearcherRequests();
    }

    @GetMapping("/actions")
    public ResponseEntity getActions() {
        return actionService.getActions();
    }

    @GetMapping("/actions/{actionId}")
    public ResponseEntity getAction(@PathVariable Long actionId) {
        return actionService.getAction(actionId);
    }

    @GetMapping("/stations")
    public ResponseEntity<List<Station>> getAllStations() {
        return ResponseEntity.ok(stationService.getAllStations());
    }
}
