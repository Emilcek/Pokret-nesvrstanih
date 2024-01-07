package com.progi.WildTrack.controllers;

import com.progi.WildTrack.domain.Station;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.ExplorerTaskDTO;
import com.progi.WildTrack.service.*;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.service.ClientService;
import com.progi.WildTrack.service.StationLeadService;
import com.progi.WildTrack.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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
    private final StationLeadService stationLeadService;

    @GetMapping
    public ResponseEntity<ClientDetailsDTO> getStationLead() {
        return ResponseEntity.ok(clientService.getClient());
    }

    @PutMapping("/myStation/{stationName}")
    public ResponseEntity assignStationToStationLead(@PathVariable String stationName){
        System.out.println("StationLeadController.assignStationToStationLead" + stationName);
        return stationLeadService.assignStationToStationLead(stationName);
    }

    @GetMapping("/myStation")
    public ResponseEntity getStation(){
        return stationLeadService.getStation();
    }

    @PutMapping("/myStation/addExplorer/{explorerName}")
    public ResponseEntity assignExplorerToStation(@PathVariable String explorerName, @RequestBody String stationName){
        return stationLeadService.assignExplorerToStation(stationName, explorerName);
    }

    @PutMapping("/myStation/removeExplorer/{explorerName}")
    public ResponseEntity removeExplorerFromStation(@PathVariable String explorerName, @RequestBody String stationName){
        return stationLeadService.removeExplorerFromStation(stationName, explorerName);
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
        return explorerService.getExplorers();
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
    @GetMapping("/availableExplorers")
    public ResponseEntity<List<ClientDetailsDTO>> getAllAvailableExplorers() {
        return ResponseEntity.ok(clientService.getAllAvailableExplorers());
    }
}
