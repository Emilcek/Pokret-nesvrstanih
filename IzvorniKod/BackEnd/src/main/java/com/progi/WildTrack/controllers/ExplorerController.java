package com.progi.WildTrack.controllers;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Task;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/explorer")
@RequiredArgsConstructor
public class ExplorerController {

    private final ClientService clientService;
    @GetMapping
    public ResponseEntity<Client> getExplorer() {
        return ResponseEntity.ok(clientService.getClient());
    }

    @PostMapping
    public ResponseEntity<Client> updateExplorer(@RequestBody ClientUpdateDTO client) {
        return ResponseEntity.ok(clientService.updateClient(client));
    }

    @GetMapping("/tasks")
    public ResponseEntity<Task> getTasks() {
        //todo
        return ResponseEntity.ok().build();
    }
}
