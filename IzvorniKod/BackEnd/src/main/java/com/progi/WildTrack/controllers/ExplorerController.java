package com.progi.WildTrack.controllers;


import com.progi.WildTrack.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/explorer")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class ExplorerController {

    private final TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity getTasks() {
        return taskService.getTasks();
    }
}
