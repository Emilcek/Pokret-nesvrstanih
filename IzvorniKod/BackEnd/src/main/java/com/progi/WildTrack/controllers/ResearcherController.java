package com.progi.WildTrack.controllers;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/researcher")
@RequiredArgsConstructor
public class ResearcherController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<Client> getResearcher() {
        return ResponseEntity.ok(clientService.getClient());
    }

    @PostMapping
    public ResponseEntity<Client> updateResearcher(@RequestBody ClientUpdateDTO client) {
        return ResponseEntity.ok(clientService.updateClient(client));
    }
}
