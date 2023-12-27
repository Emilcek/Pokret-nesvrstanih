package com.progi.WildTrack.controllers;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.dto.RegisterDto;
import com.progi.WildTrack.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping
    public ResponseEntity<ClientDetailsDTO> getClient() {
        return ResponseEntity.ok(service.getClient());
    }

    @PutMapping
    public ResponseEntity updateClient(@ModelAttribute ClientUpdateDTO client) {
        System.out.println("ClientController.updateClient" + client);
        return service.updateClient(client);
    }
}
