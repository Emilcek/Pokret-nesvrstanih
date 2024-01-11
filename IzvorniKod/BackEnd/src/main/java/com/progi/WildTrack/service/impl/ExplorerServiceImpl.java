package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ExplorerRepository;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Explorer;
import com.progi.WildTrack.domain.Station;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.service.ExplorerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExplorerServiceImpl implements ExplorerService {

    @Autowired
    private ExplorerRepository explorerRepo;

    @Override
    public ResponseEntity getExplorers() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Explorer> explorers = explorerRepo.findAllByStationStationName(client.getStationLead().getStation().getStationName());
        return ResponseEntity.ok(explorers.stream().map(explorer -> new ClientDetailsDTO(explorer.getClient(), explorer)).toList());
    }
}
