package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Explorer;
import com.progi.WildTrack.service.ExplorerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExplorerServiceImpl implements ExplorerService {

    @Override
    public ResponseEntity getAvailableExplorers() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Explorer> explorers = client.getStationLead().getStation().getExplorers();
        List<Explorer> availableExplorers = new ArrayList<>();
        for (Explorer explorer : explorers) {
            if (explorer.getExplorerStatus().equals("Available")) {
                availableExplorers.add(explorer);
            }
        }
        return ResponseEntity.ok(availableExplorers);
    }
}
