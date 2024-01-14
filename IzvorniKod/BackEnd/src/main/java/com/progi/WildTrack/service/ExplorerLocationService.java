package com.progi.WildTrack.service;

import com.progi.WildTrack.dto.ExplorerLocationDTO;
import org.springframework.http.ResponseEntity;

public interface ExplorerLocationService {
    ResponseEntity addExplorerLocation(String explorerName,ExplorerLocationDTO explorerLocationDTO);

    ResponseEntity getExplorerLocation(String explorerName);

    ResponseEntity getExplorerLocations(String explorerName);

    ResponseEntity getAllExplorersAllLocations();

    ResponseEntity getAllExplorersCurrentLocations();
}
