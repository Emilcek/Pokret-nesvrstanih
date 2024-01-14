package com.progi.WildTrack.controllers;


import com.progi.WildTrack.service.impl.ActionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/action")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class ActionController {

    private final ActionServiceImpl actionService;

    //ide u action controller
    //list of locations of all animals that are on same action
    @GetMapping("/animals/{actionId}")
    public ResponseEntity getActionAnimals(@PathVariable Long actionId) {
        return actionService.getActionAnimalLocations(actionId);
    }
    //ide u action controller
    //list of locations of all explorers that are on same action
    @GetMapping("/explorers/{actionId}")
    public ResponseEntity getActionExplorers(@PathVariable Long actionId) {
        return actionService.getActionExplorerLocations(actionId);
    }

}
