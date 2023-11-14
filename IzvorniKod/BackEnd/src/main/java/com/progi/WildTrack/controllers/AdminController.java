package com.progi.WildTrack.controllers;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ClientService service;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(service.getAllClients());
    }

    @GetMapping("/clients/{clientName}")
    public ResponseEntity<Client> getClientByClientName(@PathVariable String clientName) {
        return ResponseEntity.ok(service.getClientByClientName(clientName));
    }
    @PostMapping("/clients/update")
    public ResponseEntity<Client> UpdateClient(@RequestBody ClientUpdateDTO client) {
        return ResponseEntity.ok(service.updateClient(client));
    }

    @GetMapping("/requests")
    public ResponseEntity<List<Client>> getAllRequests() {
        return ResponseEntity.ok(service.getAllRequests());
    }

    @GetMapping("/requests/{clientName}/accepted")
    public ResponseEntity<Client> acceptRequest(@PathVariable String clientName) {
        return ResponseEntity.ok(service.updateClientByClientName(clientName, 2));
    }

    @GetMapping("/requests/{clientName}/rejected")
    public ResponseEntity<Client> rejectRequest(@PathVariable String clientName) {
        return ResponseEntity.ok(service.updateClientByClientName(clientName, 3));
    }
}
