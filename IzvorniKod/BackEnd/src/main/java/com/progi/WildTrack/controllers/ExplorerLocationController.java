package com.progi.WildTrack.controllers;



import com.progi.WildTrack.dto.ExplorerLocationDTO;
import com.progi.WildTrack.service.ExplorerLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/explorerlocation")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class ExplorerLocationController {
    private final ExplorerLocationService explorerLocationService;

    //save explorers current location
    @PostMapping("/add/{explorerName}")
    public ResponseEntity addExplorerLocation(@PathVariable String explorerName,@RequestBody ExplorerLocationDTO explorerLocationDTO) {
        return explorerLocationService.addExplorerLocation(explorerName,explorerLocationDTO);
    }
    //get explorers current location
    @GetMapping("/get/{explorerName}")
    public ResponseEntity getExplorerLocation(@PathVariable String explorerName) {
        return explorerLocationService.getExplorerLocation(explorerName);
    }

    //list of all explorers locations
    @GetMapping("/all/{explorerName}")
    public ResponseEntity getExplorerLocations(@PathVariable String explorerName) {
        return explorerLocationService.getExplorerLocations(explorerName);
    }

    //list of all explorers current location
    @GetMapping("/current/all")
    public ResponseEntity getAllExplorersCurrentLocations() {
        return explorerLocationService.getAllExplorersCurrentLocations();
    }
    //list of all explorers location history
    @GetMapping("/all")
    public ResponseEntity getAllExplorersAllLocations() {
        return explorerLocationService.getAllExplorersAllLocations();
    }

}
