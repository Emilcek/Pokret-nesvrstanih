package com.progi.WildTrack.controllers;



import com.progi.WildTrack.dto.ExplorerLocationDTO;
import com.progi.WildTrack.service.ActionService;
import com.progi.WildTrack.service.ExplorerLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/explorerGPS")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class ExplorerGPSController {
    private final ActionService actionService;
    private final ExplorerLocationService explorerLocationService;

    //save explorers current location
    @PostMapping("/")
    public ResponseEntity saveExplorerLocation(@RequestBody ExplorerLocationDTO explorerLocationDTO) {
        return explorerLocationService.saveExplorerLocation(explorerLocationDTO);
    }
    //get explorers current location
    @GetMapping("/get")
    public ResponseEntity getExplorerLocation(@RequestParam String explorerName) {
        return explorerLocationService.getExplorerLocation(explorerName);
    }

    //list of locations of all animals that are on same action
    @GetMapping("/animals/{actionId}")
    public ResponseEntity getActionAnimals(@PathVariable Long actionId, @RequestParam String clientName) {
        return actionService.getActionAnimalLocations(actionId, clientName);
    }
    //list of locations of all explorers that are on same action
    @GetMapping("/explorers/{actionId}")
    public ResponseEntity getActionExplorers(@PathVariable Long actionId, @RequestParam String clientName) {
        return actionService.getActionExplorerLocations(actionId, clientName);
    }
    //list of all explorers locations
    @GetMapping("/explorer/{explorerName}")
    public ResponseEntity getExplorerLocations(@PathVariable String explorerName) {
        return explorerLocationService.getExplorerLocations(explorerName);
    }
    //list of all explorers location history
    @GetMapping("/all/explorers/all/location")
    public ResponseEntity getAllExplorersAllLocations() {
        return explorerLocationService.getAllExplorersAllLocations();
    }
    @GetMapping("/all/explorers/all/currentLocation")
    public ResponseEntity getAllExplorersCurrentLocations() {
        return explorerLocationService.getAllExplorersCurrentLocations();
    }

}
