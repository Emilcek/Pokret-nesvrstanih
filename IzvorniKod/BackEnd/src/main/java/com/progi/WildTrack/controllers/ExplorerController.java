package com.progi.WildTrack.controllers;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Task;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/explorer")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class ExplorerController {

    @GetMapping("/tasks")
    public ResponseEntity<Task> getTasks() {
        //todo
        return ResponseEntity.ok().build();
    }
}
