package com.progi.WildTrack.controllers;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class AdminController {

    private final ClientService service;

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDetailsDTO>> getAllClients() {
        return ResponseEntity.ok(service.getAllClients());
    }

    @GetMapping("/clients/{clientName}")
    public ResponseEntity<ClientDetailsDTO> getClientByClientName(@PathVariable String clientName) {
        return service.getClientByClientName(clientName);
    }
    @PostMapping("/clients/update")
    public ResponseEntity UpdateClient(@ModelAttribute ClientUpdateDTO client) {
        return service.updateClient(client);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<ClientDetailsDTO>> getAllRequests() {
        return ResponseEntity.ok(service.getAllRequests());
    }

    @GetMapping("/requests/{clientName}/accepted")
    public ResponseEntity<ClientDetailsDTO> acceptRequest(@PathVariable String clientName) {
        return ResponseEntity.ok(service.updateClientStatusByClientName(clientName, 2));
    }

    @GetMapping("/requests/{clientName}/rejected")
    public ResponseEntity<ClientDetailsDTO> rejectRequest(@PathVariable String clientName) {
        return ResponseEntity.ok(service.updateClientStatusByClientName(clientName, 3));
    }
}
