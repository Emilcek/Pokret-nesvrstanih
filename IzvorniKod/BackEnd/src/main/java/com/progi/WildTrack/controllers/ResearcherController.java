package com.progi.WildTrack.controllers;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/researcher")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class ResearcherController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<ClientDetailsDTO> getResearcher() {
        return ResponseEntity.ok(clientService.getClient());
    }

    @PostMapping
    public ResponseEntity<ClientDetailsDTO> updateResearcher(@RequestBody ClientUpdateDTO client) {
        return ResponseEntity.ok(clientService.updateClient(client));
    }
}
