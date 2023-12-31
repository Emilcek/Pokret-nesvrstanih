package com.progi.WildTrack.controllers;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.dto.CreateRequestDTO;
import com.progi.WildTrack.service.ActionService;
import com.progi.WildTrack.service.ClientService;
import com.progi.WildTrack.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/researcher")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class ResearcherController {

    private final ClientService clientService;
    private final RequestService requestService;
    private final ActionService actionService;

    @GetMapping
    public ResponseEntity<ClientDetailsDTO> getResearcher() {
        return ResponseEntity.ok(clientService.getClient());
    }


    @PostMapping("/request")
    public ResponseEntity createRequest(@RequestBody CreateRequestDTO request) {
        return requestService.createRequest(request);
    }

    @GetMapping("/requests")
    public ResponseEntity getRequests() {
        return requestService.getResearcherRequests();
    }

    @GetMapping("/actions")
    public ResponseEntity getActions() {
        return actionService.getActions();
    }

    @GetMapping("/actions/{actionId}")
    public ResponseEntity getAction(@PathVariable Long actionId) {
        return actionService.getAction(actionId);
    }
}
